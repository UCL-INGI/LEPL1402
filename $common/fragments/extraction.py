import re

from fragments import coverage, helper


# Extract score and message that use JavaGrading
def extract_java_grading_result(result, feedback_settings):
    # we have a feedback from JavaGrading
    # Fetch total for INGInious

    # WARNING : there could be multiple TOTAL in the stdout
    # So we must merge everything

    # Strips not useful things of JavaGrading

    # Display array of test suite results only if student didn't use prohib' instruction
    display = False if result.returncode == 2 else True

    result_string = result.stdout.replace("--- GRADE ---", "").replace("--- END GRADE ---", "")
    regex_strip = r"TOTAL \d*[.]?\d*\/\d*[.]?\d*"
    regex_strip2 = r"TOTAL WITHOUT IGNORED \d*[.]?\d*\/\d*[.]?\d*"

    # Remove match
    result_string = re.sub(regex_strip, '', result_string)
    result_string = re.sub(regex_strip2, '', result_string)

    regex = '\*\*TOTAL\*\*",,\*\*(\d*[.]?\d*\/\d*[.]?\d*)\*\*'
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

    return student_result / total_result, result_string if display \
        else feedback_settings["status_message"].get(result.returncode, "Uncommon Failure")


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
