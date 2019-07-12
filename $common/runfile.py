#!/bin/python3
import importlib.util
import os

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
    #   CREATE A CLASSES FOLDER         #
    #####################################
    os.makedirs(PATH_CLASSES, exist_ok=True)
    print("SET UP CLASSES FOLDER FOR COMPILATION")

    #####################################
    #   COMPILE ALL CODE IN SRC         #
    #####################################

    # If we are in "default" exercise kind, we don't need a FLAVOUR folder
    folders_to_compile = [PATH_SRC] if feedback_settings["exercise_kind"] == "default" else [PATH_FLAVOUR, PATH_SRC]
    
    # Need that for custom structure, for example many packages in folders_to_compile

    all_folders_to_compile = [
        item 
        for sublist in 
            [ 
                helper.find_files_folder_in_path(folder) 
                for folder in folders_to_compile 
            ] 
        for item in sublist
    ]

    # Files that must be compiled by javac
    files_to_compile = [
        "{}/{}{}".format(folder, "*", FILE_EXTENSION)
        for folder in all_folders_to_compile
    ]
    
    compile_cmd = helper.generate_java_command_string(files_to_compile, "javac")
    print("COMPILING CODE : {}".format(compile_cmd))
    result = helper.run_command(compile_cmd)

    # handle compilation errors
    feedback.compilation_feedback(result)

    #####################################
    #       GENERATE A JAR FILE         #
    #####################################

    # We need a manifest in order to make the created jar runnable
    helper.create_manifest()
    
    # Create a jar file
    create_jar = helper.generate_jar_file()
    print("GENERATING JAR : {}".format(create_jar))
    # WARNING ; JUST FOR JAVA TO NOT MISUNDERSTAND OUR STRUCTURE, we have to change the CWD in the command
    result = helper.run_command(create_jar, PATH_CLASSES)
    # For debug the jar construction
    # print(result.stdout)

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
