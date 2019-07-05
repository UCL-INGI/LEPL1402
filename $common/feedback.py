import sys
import re
from inginious import feedback


# Throw a fatal error if the given code doesn't compile
def compilation_feedback(problem_id, result):
    if result.stderr:
        msg = "Your file did not compile : INGINIOUS is not your IDE ..."
        feedback.set_problem_feedback(msg, problem_id)
        feedback.set_global_result("Compilation Error")
        sys.exit(0)


# common behaviour message for "return code" result
return_messages = {
    0: "Your code has successfully passed all tests for this mission.",
    1: "Your code failed all tests for this mission.",
    252: "The memory limit of your program is exceeded.",
    253: "The time limit for running your program has been exceeded."
}


# Generate the final message(s) to student
# has_feedback : to tell us if feedback is expected
# quorum : float between 0.0 (0%) and 1.0 (100%) that the user should reach in order to success the task
# feedback_kind : JavaGrading / JaCoCo / etc (useful only when you have has_feedback=True)
def result_feedback(result, has_feedback=False, quorum=1.0, feedback_kind=''):
    
    # Top level message
    msg = "{}\n".format(return_messages.get(result.returncode, "Uncommon Failure"))
    feedback.set_global_feedback(msg, True) 

    # For advanced feedback
    score_ratio = 0

    # if we have a feedback, use it
    if has_feedback:
        
        # Each one has its own way to express itself
        msg = ""

        # JavaGrading
        if feedback_kind == "JavaGrading":
            score_ratio, msg = extract_java_grading_result(result)
        
        # JaCoCo
        # TODO

        # Print the rest
        feedback_result(score_ratio)
        feedback.set_grade(score_ratio * 100)

    # For exercises with binary result : 0 or 100
    else:
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