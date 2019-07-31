import sys
import re
import yaml
from pathlib import Path
from inginious import feedback, rst
from fragments import coverage, helper


# Throw a fatal error if the given code doesn't compile
def compilation_feedback(result):
    if result.returncode != 0:
        msg = "Your file did not compile : INGINIOUS is not your IDE ..."
        print(result.stderr)
        feedback.set_global_feedback(msg)
        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
        sys.exit(0)


# common behaviour message for "return code" result
return_messages = {
    0: "Your code has successfully passed all tests for this mission.",
    1: "Your code failed all tests for this mission.",
    2: "You used prohibited instructions ( such as System.exit(127) ) : This incident will be reported.",
    252: "The memory limit of your program is exceeded.",
    253: "The time limit for running your program has been exceeded."
}


# Generate the final message(s) to student
def result_feedback(result, feedback_settings):
    # Top level message
    msg = "{}\n".format(return_messages.get(result.returncode, "Uncommon Failure")) 

    # if we have a feedback, use it
    if feedback_settings["has_feedback"]:

        # JavaGrading
        if feedback_settings["feedback_kind"] == "JavaGrading":
            score_ratio, msg = extract_java_grading_result(result)
            print(repr(msg))
            # To prevent some genius to have success grade with a probited
            score_ratio = 0.0 if result.returncode == 2 else score_ratio 
            feedback_result(score_ratio, feedback_settings)
            feedback.set_global_feedback(msg, True) 
        
        # JaCoCo
        if feedback_settings["feedback_kind"] == "JaCoCo":
            if result.returncode == 0:
                score_ratio, msg = extract_jacoco_result(feedback_settings)
                feedback_result(score_ratio, feedback_settings)
                message_index = 0 if score_ratio >= feedback_settings["quorum"] else 1
                msg2 = "{}\n".format(return_messages.get(message_index, "Uncommon Failure")) 
                feedback.set_global_feedback(msg2, True) 
                feedback.set_global_feedback(msg, True)
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


# Extract score and message that use JavaGrading
def extract_java_grading_result(result):
    # we have a feedback from JavaGrading
    # Fetch total for INGInious
    # example : text = "\nTOTAL 5/10\n"

    # WARNING : there could be multiple TOTAL in the stdout
    # So we must merge everything

    regex = "\nTOTAL (\d*[.]?\d*\/\d*[.]?\d*)"
    matches = re.findall(regex, result.stdout)

    # convert everything in float
    converted_results = [
        [
            float(item)
            for item in match.split("/")
        ]
        for match in matches
    ]
    
    student_result, total_result = [sum(i) for i in zip(*converted_results)]

    return student_result / total_result, result.stdout


# Using a yaml file, we can extract the kind of exercise/feedback
def config_file_to_dict(file_path):

    # Check github wiki for more information
    default_values = {
        "has_feedback": False,
        "quorum": 1.0,
        "feedback_kind": None,
        "coverage_stats": None,
        "prohibited": {}
    }

    # no config file so use basic settings
    if not Path(file_path).exists():
        return default_values
    else:

        with open(file_path, "r") as stream:
            # File must not be empty
            load_config = yaml.load(stream)
            # Merge dictionnaries
            return default_values if load_config is None else {**default_values, **load_config}


# Extract result from JaCoCo
def extract_jacoco_result(feedback_settings):
    coverage_stats = feedback_settings["coverage_stats"]
    # No coverage stats , cannot evaluate this
    if not coverage_stats:
        return 0.0, "NO COVERAGE CRITERIA WERE GIVEN"
    else:
        # Generate the xml report file
        gen_report = coverage.generate_coverage_report()
        print("GENERATING THE EXEC FILE : {}".format(gen_report))
        helper.run_command(gen_report)

        # extract stats
        coverage_result = coverage.extract_stats()
        filtered_coverage_result = [x for x in coverage_result if x["type"] in coverage_stats]
        print(filtered_coverage_result)

        # generate score and message

        covered = sum(x["covered"] for x in filtered_coverage_result)
        total = covered + sum(x["missed"] for x in filtered_coverage_result)

        msg = '\n'.join(
            [
                "{}:\t{}/{}".format(c["type"], c["covered"], c["covered"] + c["missed"])
                for c in filtered_coverage_result
            ]
        )

        # For security (if report gives 0 at total, don't try to apply division)
        ratio = covered / total if total > 0 else 0.0

        return ratio, msg

def handle_prohibited_statments(feedback_settings):
    result = helper.contains_prohibited_statment(feedback_settings)
    if result:
        msg = return_messages.get(2, "Uncommon Failure")
        feedback.set_global_feedback(msg)
        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
        sys.exit(0)        