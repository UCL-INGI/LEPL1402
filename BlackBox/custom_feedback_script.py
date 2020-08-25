#!/bin/python3
from inginious import feedback
import re


def main(result, feedback_settings):
    # Top level message
    msg = "{}\n".format(feedback_settings["status_message"].get(result.returncode, "Uncommon Failure"))
    feedback.set_global_feedback(msg, True)
    
    print("INSIDE CUSTOM SCRIPT")

    # No runnable method(s)
    if result.returncode == 3:
        feedback.set_global_feedback(result.stdout, True)

    # if the student didn't cheat
    if result.returncode not in [2, 3]:
        
        # Extract the data
        format = re.compile("(?P<nBugsFound>[0-9]+)\t(?P<nBugs>[0-9]+)\t(?P<status>N?FP)\n")
        regex_result = format.search(result.stdout)
        nBugsFound = int(regex_result.group("nBugsFound"))
        nBugs = int(regex_result.group("nBugs"))
        status = regex_result.group("status")
        score_ratio = nBugsFound / nBugs
        extraFeedback = result.stdout.split('\n', 1)[1].split(',')

        print("nBugsFound : {} , nBugs : {} , status : {}".format(nBugsFound, nBugs, status))

        # Notify the user its progress
        result = "success" if score_ratio >= feedback_settings["quorum"] and status == "NFP" else "failed"

        # if false positive, give him zero
        updated_score_ratio = score_ratio if status == "NFP" else 0.0
        feedback.set_global_result(result)
        feedback.set_grade(updated_score_ratio * 100)

        # Give him some explanation
        msg2 = "You have found {} bug(s) on a total of {} bugs".format(nBugsFound, nBugs)
        if status == "FP":
            feedback.set_global_feedback(msg2+" but your test suite generates a false positive: therefore you have 0.0%.\n", True)
            feedback.set_global_feedback("You should check whether a correct binary search implementation is able to pass your tests.\n", True)
        elif result == "success": # 100%, no need for extra feedback
            feedback.set_global_feedback(msg2+".\n", True)
        else: # Failure, so we give the student extra feedback
            feedback.set_global_feedback(msg2+".\n", True)
            feedback.set_global_feedback("Your tests managed to single out the following bugs:\n", True)
            for bug in extraFeedback[:-2]: # [-2] because [-1] is just an empty string, due to the way StudentTestRunner.java prints the feedback
                feedback.set_global_feedback(bug+", ", True)
            feedback.set_global_feedback(extraFeedback[-2]+".\n", True)
    
    else:
        feedback.set_global_result("failed")
        feedback.set_grade(0.0)
