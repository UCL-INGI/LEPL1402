#!/bin/python3
import importlib.util

#####################################
# Our import for common function    #
#####################################

from fragments import helper, feedback
from fragments.constants import *


# For extreme usage only XD
# Dynamically load modules we need
# Credits to https://stackoverflow.com/a/67692/6149867
# And for the explanation : http://www.blog.pythonlibrary.org/2016/05/27/python-201-an-intro-to-importlib/
# "/course/common/{}.py" as path ; module is the filename
def dynamically_load_module(module, path):
    spec = importlib.util.spec_from_file_location(module, path)
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


def main():
    #####################################
    #   Load feedback task settings     #
    #####################################
    feedback_settings = feedback.config_file_to_dict(FEEDBACK_REVIEW_PATH)

    #####################################
    #       Apply templates             #
    #####################################

    # we have a folder where we have only these files
    helper.apply_templates(PATH_TEMPLATES, PATH_SRC)

    #####################################
    #   COMPILE ALL CODE IN SRC         #
    #####################################

    compile_cmd = helper.generate_java_command_string(FILES_TO_COMPILE, "javac")
    print(compile_cmd)
    result = helper.run_command(compile_cmd)

    # handle compilation errors
    feedback.compilation_feedback(result)

    #####################################
    #   RUN  TEST  RUNNER               #
    #####################################

    # invoke runner with classes as arg
    # in the case of code coverage ( Jacoco ) , we need to generate also the report file (exec ) by the JaCoco agent
    coverage_required = True if feedback_settings["feedback_kind"] == "JaCoCo" else False

    run_code = helper.generate_java_command_string(RUNNER_JAVA_NAME, coverage=coverage_required)
    print("{} \n".format(run_code))

    result = helper.run_command(run_code)

    #####################################
    #   Show and handle results         #
    #####################################
    feedback.result_feedback(result, feedback_settings)


if __name__ == "__main__":
    main()
