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
def result_feedback(result, has_feedback=False):
    
    # Top level message
    msg = "{}\n".format(return_messages.get(result.returncode, "Uncommon Failure"))

    # print a message to student if we have the JavaGrading output
    if has_feedback:
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
        msg += result.stdout
        feedback.set_global_result(msg)

    # For exercises with binary result : 0 or 100
    else:
        grade = 100.0 if result.returncode == 0 else 0.0
        feedback.set_global_result(msg) 
        feedback.set_grade(0.0)
