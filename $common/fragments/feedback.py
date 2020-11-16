import sys
import importlib.util
import traceback

from inginious import feedback, rst

from fragments import helper
from fragments.extraction import *


# Throw a fatal error if we cannot create the jar
def jar_feedback(result):
    if result.returncode != 0:
        msg = "A technical problem has occurred: please report it !"
        print(result.stderr)
        feedback.set_global_feedback(msg)
        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
        sys.exit(0)


# Throw a fatal error if the given code doesn't compile
def compilation_feedback(result, full_log):
    if result.returncode != 0:
        errors = extract_compilation_errors(result.stderr)

        # If any errors come from the templates, blame the student with this code
        templates_folder = helper.relative_path(PATH_TEMPLATES)
        if any(error.get("source", "templates") == templates_folder for error in errors):
            # Generate an custom RST report that will see the student
            msg = generate_compilation_errors_table(errors, [PATH_TEMPLATES])

            # Send it to Inginious
            feedback.set_global_feedback(msg, True)

        else:
            if full_log:
                # A teaching assitant wants to debug this task and no error is coming from template folder
                msg = generate_compilation_errors_table(errors, [PATH_SRC, PATH_FLAVOUR])

                # Send it to Inginious
                feedback.set_global_feedback(msg, True)

                # For debug purposes
                print(result.stderr)
            else:

                # Either the student has made mistake(s) eg in the signature(s) of method(s) ....
                # Or a TA adds code that doesn't compile
                feedback.set_global_feedback(
                    "{}{}".format(
                        "You modified the signature of at least a function or modified the content of a class.",
                        "You should not; the tests fail to compile"
                    ))

        # Final instructions commons for all scenario

        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
        sys.exit(0)

# Generate compilation error table
def generate_compilation_errors_table(errors, allowed_sources):
    # Generate an custom RST report that will see the student
    msg = ""
    # Don't add \r to that as it produces strange results
    next_line = "\n"
    indentation = 4
    headers = ["File", "Line", "Error Message", "Code"]

    # Headers
    msg += ".. csv-table:: " + next_line
    # Need a symbol that isn't in the Java
    msg += " " * indentation + ":quote: §" + next_line
    msg += " " * indentation + ":header: " + ",".join(headers) + next_line
    msg += " " * indentation + ":widths: auto" + next_line * 2

    # Allowed sources for that table
    sources = [helper.relative_path(source) for source in allowed_sources]

    # Contents
    for error in [error for error in errors if error.get("source", "Unknown Source") in sources]:
        # Print File , Line and Error message without problem
        msg += " " * indentation + "§{}§".format(error.get("file", "Unknown Filename"))
        msg += ",§{}§".format(error.get("line", "Unknown Line Number"))
        msg += ",§{}§".format(error.get("message", "Unknown Message"))

        # Print the code
        code_lines = error.get("code", [])

        # For whatever reason, INGINIOUS might truncate the stderr message if too long
        # It might break the CSV table ... so we need this fix
        if not code_lines:

            # Might be confusing but they are the rules of RST for empty block
            msg += ",§§" + next_line

        else:

            msg += ",§.. code-block:: java" + next_line * 2
            indentation_for_code = indentation + 1

            for code_line in code_lines:
                # as we are in a code block, we need indentation + 1 in order to create compilable code in RST
                msg += " " * indentation_for_code + code_line

                # needed test to correctly format things
                if code_line != code_lines[-1]:
                    msg += next_line

            # At the end , we should not forget the quote symbol and the next line
            msg += "§" + next_line

    return msg


# All the feedback functions for all known "feedback_kind"
def build_strategies():

    def java_grading(result, feedback_settings, msg):
        score_ratio, msg2 = extract_java_grading_result(result, feedback_settings)
        # To prevent some genius to have success grade with a prohibited
        score_ratio = 0.0 if result.returncode == 2 else score_ratio
        feedback_result(score_ratio, feedback_settings)
        feedback.set_global_feedback(msg, True)
        feedback.set_global_feedback(msg2, True)

    def jacoco(result, feedback_settings, msg):
        if result.returncode == 0:
            score_ratio, msg = extract_jacoco_result(feedback_settings)
            feedback_result(score_ratio, feedback_settings)
            message_index = 0 if score_ratio >= feedback_settings["quorum"] else 3
            msg2 = "{}\n".format(feedback_settings["status_message"].get(message_index, "Uncommon Failure"))
            feedback.set_global_feedback(msg2, True)
            feedback.set_global_feedback(rst.get_codeblock("java", msg), True)
        else:
            feedback.set_global_feedback(msg, True)
            feedback_result(0.0, feedback_settings)

    return {
        "JavaGrading": java_grading,
        "JaCoCo": jacoco
    }


# No need to rebuild that every thing we need that
FEEDBACK_STRATEGIES = build_strategies()


# Generate the final message(s) to student
def result_feedback(result, feedback_settings):
    # Top level message
    msg = "{}\n".format(feedback_settings["status_message"].get(result.returncode, "Uncommon Failure"))

    # if we have a feedback, use it
    if feedback_settings["has_feedback"]:

        try:
            strategy = FEEDBACK_STRATEGIES[feedback_settings["feedback_kind"]]
            strategy(result, feedback_settings, msg)
        except (KeyError, RuntimeError, BaseException):
            traceback.print_exc() # useful for debugging a custom script that failed
            feedback.set_global_result("failed")
            msg = "A technical problem has occurred using the feedback strategy : {} . Please report it !" \
                .format(feedback_settings["feedback_kind"])
            feedback.set_global_feedback(msg)
            feedback.set_grade(0.0)
            sys.exit(0)

    # For exercises with binary result : 0 or 100
    else:
        feedback.set_global_feedback(msg, True)
        score_ratio = 1.0 if result.returncode == 0 else 0.0
        feedback_result(score_ratio, feedback_settings)


# If we want to use another script for that part ( rather that result_feedback )
def custom_result_feedback(result, feedback_settings):
    # Dynamically load modules we need
    # Credits to https://stackoverflow.com/a/67692/6149867
    # And for the explanation : http://www.blog.pythonlibrary.org/2016/05/27/python-201-an-intro-to-importlib/
    def dynamically_load_module(module, path):
        spec = importlib.util.spec_from_file_location(module, path)
        mod = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(mod)
        return mod
    try:
        custom_feedback_path = str(CWD / feedback_settings["custom_feedback_script"])
        custom_feedback_module = dynamically_load_module("custom_feedback_module", custom_feedback_path)
        custom_feedback_module.main(result, feedback_settings)
    except (RuntimeError, ImportError, BaseException):
        traceback.print_exc() # useful for debugging a custom script that failed
        feedback.set_global_feedback("A technical problem has occurred in the custom feedback script: please report it !")
        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
        sys.exit(0)


# Decision function to decide if the student pass the required level for this task
def feedback_result(score_ratio, feedback_settings):
    result = "success" if score_ratio >= feedback_settings["quorum"] else "failed"
    feedback.set_global_result(result)
    # If coverage exercise, give him 100% if >= quorum else the basic score
    updated_score_ratio = 1.0 if result == "success" and feedback_settings["feedback_kind"] == "JaCoCo" else score_ratio
    feedback.set_grade(updated_score_ratio * 100)


def handle_verification(feedback_settings):
    result = helper.statement_verification(feedback_settings)
    # If not empty, there is error(s) in student code 
    if result:
        msg = feedback_settings["status_message"].get(2, "Uncommon Failure")
        feedback.set_global_feedback(msg)
        # Add message(s) to tell student where are his/her errors
        for (check, problem_id) in result:
            message = check.capitalize() + " statement(s) " \
                      + (" found " if check == "prohibited" else " missing ") + "HERE"
            feedback.set_problem_feedback(message, problem_id, True)

        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
        sys.exit(0)
