import sys

from inginious import feedback, rst

from fragments import helper
from fragments.extraction import *


# Throw a fatal error if the given code doesn't compile
def compilation_feedback(result):
    if result.returncode != 0:
        msg = "Your file did not compile : please don't use INGINIOUS as an IDE ..."
        print(result.stderr)
        feedback.set_global_feedback(msg)
        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
        sys.exit(0)


# Generate the final message(s) to student
def result_feedback(result, feedback_settings):
    # Top level message
    msg = "{}\n".format(feedback_settings["status_message"].get(result.returncode, "Uncommon Failure"))

    # if we have a feedback, use it
    if feedback_settings["has_feedback"]:

        # JavaGrading
        if feedback_settings["feedback_kind"] == "JavaGrading":
            score_ratio, msg = extract_java_grading_result(result, feedback_settings)
            # To prevent some genius to have success grade with a prohibited
            score_ratio = 0.0 if result.returncode == 2 else score_ratio
            feedback_result(score_ratio, feedback_settings)
            feedback.set_global_feedback(msg, True)

        # JaCoCo
        if feedback_settings["feedback_kind"] == "JaCoCo":
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

    # For exercises with binary result : 0 or 100
    else:
        feedback.set_global_feedback(msg, True)
        score_ratio = 1.0 if result.returncode == 0 else 0.0
        feedback_result(score_ratio, feedback_settings)


# Decision function to decide if the student pass the required level for this task
def feedback_result(score_ratio, feedback_settings):
    result = "success" if score_ratio >= feedback_settings["quorum"] else "failed"
    feedback.set_global_result(result)
    # If coverage exercise, give him 100% if >= quorum else the basic score
    updated_score_ratio = 1.0 if result == "success" and feedback_settings["feedback_kind"] == "JaCoCo" else score_ratio
    feedback.set_grade(updated_score_ratio * 100)


def handle_prohibited_statements(feedback_settings):
    result = helper.contains_prohibited_statement(feedback_settings)
    if result:
        msg = feedback_settings["status_message"].get(2, "Uncommon Failure")
        feedback.set_global_feedback(msg)
        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
        sys.exit(0)
