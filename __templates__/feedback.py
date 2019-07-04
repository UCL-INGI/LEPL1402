import sys
import re
from inginious import feedback


# Throw a fatal error if the given code doesn't compile
def compilation_feedback(problem_id, result):
    if result.stderr:
        msg = "Votre fichier n'a pas compilé ou une erreur s'est produite : {}".format(result.stderr)
        feedback.set_problem_feedback(msg, problem_id)
        feedback.set_global_result("Erreur de compilation (ou autre)")
        sys.exit(0)


# common behaviour message for "return code" result
# TODO les utiliser dans les annotations grades de
return_messages = {
    0: "Your code has successfully passed all tests for this mission.",
    252: "The memory limit of your program is exceeded.",
    253: "The time limit for running your program has been exceeded."
}


# Generate the final message(s) to student
def result_feedback(result):
    # for security , if the grader has issues
    if result.returncode != 0:
        feedback.set_global_result("JavaGrading fails to provide an answer {}".format(result.stderr))
        feedback.set_grade(0.0)
        sys.exit(0)
    else:
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

        # Time to compute the result
        feedback.set_grade(student_result / total_result)

        # Display grader message (useful if there is a mistake)
        feedback.set_global_result(result.stdout)
