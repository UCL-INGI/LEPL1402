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
    print("FEEDBACK SETTINGS LOADED")

    #####################################
    #       Apply templates             #
    #####################################

    # we have a folder where we have only these files
    helper.apply_templates(PATH_TEMPLATES, PATH_SRC)
    print("TEMPLATE(S) APPLIED")

    #####################################
    #   COMPILE ALL CODE IN SRC         #
    #####################################

    compile_cmd = helper.generate_java_command_string(FILES_TO_COMPILE, "javac")
    print("COMPILING CODE : {}".format(compile_cmd))
    result = helper.run_command(compile_cmd)

    # handle compilation errors
    feedback.compilation_feedback(result)

    #####################################
    #       GENERATE A JAR FILE         #
    #####################################

    # We need a jar file
    create_jar = helper.generate_jar_file()
    print("GENERATING JAR : {}".format(create_jar))
    # Execute this
    result = helper.run_command(create_jar)

    # handle compilation errors
    feedback.compilation_feedback(result)

    #####################################
    #   RUN  TEST  RUNNER               #
    #####################################

    # invoke runner with classes as arg
    # in the case of code coverage ( Jacoco ) , we need to generate also the report file (exec ) by the JaCoco agent
    coverage_required = True if feedback_settings["feedback_kind"] == "JaCoCo" else False
    run_code = helper.generate_java_command_string(JAR_FILE, coverage=coverage_required, is_jar=True)
    print("RUNNING CODE : {}".format(run_code))
    result = helper.run_command(run_code)

    #####################################
    #   Show and handle results         #
    #####################################
    feedback.result_feedback(result, feedback_settings)


if __name__ == "__main__":
    main()
