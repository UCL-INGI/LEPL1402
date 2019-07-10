import sys
import re
import yaml
from pathlib import Path
from inginious import feedback
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
    2: "You used prohibited instructions ( such as System.exit(127) ) : Cheating is not a solution to your problems in life.",
    252: "The memory limit of your program is exceeded.",
    253: "The time limit for running your program has been exceeded."
}


# Generate the final message(s) to student
# feedback_settings (a dict) contains the following field(s) ( check config_file_to_dict for more details ):
#   has_feedback : to tell us if feedback is expected
#   quorum : float between 0.0 (0%) and 1.0 (100%) that the user should reach in order to success the task
#   feedback_kind : JavaGrading / JaCoCo / etc (useful only when you have has_feedback=True)
#   coverage_stats:
def result_feedback(result, feedback_settings):
    # print(result.stderr)
    # Top level message
    msg = "{}\n".format(return_messages.get(result.returncode, "Uncommon Failure"))
    feedback.set_global_feedback(msg, True) 

    # if we have a feedback, use it
    if feedback_settings["has_feedback"]:

        # JavaGrading
        if feedback_settings["feedback_kind"] == "JavaGrading":
            score_ratio, msg = extract_java_grading_result(result)
            feedback_result(score_ratio, feedback_settings["quorum"])
            feedback.set_grade(score_ratio * 100)
            feedback.set_global_feedback(msg, True)
        
        # JaCoCo
        if feedback_settings["feedback_kind"] == "JaCoCo":
            score_ratio, msg = extract_jacoco_result(feedback_settings)
            feedback_result(score_ratio, feedback_settings["quorum"])
            feedback.set_grade(score_ratio * 100)
            feedback.set_global_feedback(msg, True)

    # For exercises with binary result : 0 or 100
    else:
        # TODO stdout 
        # print(result.stdout)
        # feedback.set_global_feedback(result.stdout, True)
        score_ratio = 1.0 if result.returncode == 0 else 0.0
        feedback_result(score_ratio)
        feedback.set_grade(score_ratio * 100) 


# Decision function to decide if the student pass the required level for this task
def feedback_result(score_ratio, quorum=1.0):    
    result = "success" if score_ratio >= quorum else "failed"
    feedback.set_global_result(result)


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
        "exercise_kind": "default"
    }

    # no config file so use basic settings
    if not Path(file_path).exists():
        return default_values
    else:

        with open(file_path, "r") as stream:
            # File must not be empty
            load_config = yaml.load(stream)
            # Merge dictionnaries
            return default_values if load_config is not None else {**default_values, **load_config}


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

        # TODO preparer les messages en RST
        msg = '\n'.join(
            [
                "{}:\t{}/{}".format(c["type"], c["covered"], c["covered"] + c["missed"])
                for c in filtered_coverage_result
            ]
        )

        return covered / total, msg

