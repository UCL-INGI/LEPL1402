#!/bin/python3
from pathlib import Path
from inginious import input

#####################################
# Our import for common function    #
#####################################

from fragments import helper, feedback, config
from fragments.constants import *


def main():

    username = input.get_input("@username")

    #####################################
    #   Load feedback task settings     #
    #####################################

    feedback_settings = config.config_file_to_dict(FEEDBACK_REVIEW_PATH)
    print("FEEDBACK SETTINGS LOADED")

    #####################################
    #   Check prohibited statements      #
    #####################################

    feedback.handle_verification(feedback_settings)
    print("NO PROHIBITED STATEMENT(S) OR MISSING REQUIRED STATEMENT(S) DETECTED")

    #####################################
    #       Apply templates             #
    #####################################

    # we have a folder where we have only these files
    helper.apply_templates(PATH_TEMPLATES)
    print("TEMPLATE(S) APPLIED")

    #####################################
    #   CREATE A CLASSES FOLDER         #
    #####################################

    Path(PATH_CLASSES).mkdir(parents=True, exist_ok=True)
    print("SET UP CLASSES FOLDER FOR COMPILATION")

    #####################################
    #  EXTERNAL LIBRARIES TO USE  ?     #
    #####################################

    libs = helper.libraries(feedback_settings["external_libraries"])

    #####################################
    #   COMPILE ALL CODE IN SRC         #
    #####################################

    # Possible paths where we could keep source code : src, templates and flavour (optional)
    folders_to_compile = [PATH_SRC, PATH_TEMPLATES, PATH_FLAVOUR]

    # For custom structure, for example many packages in folders_to_compile
    # we need a generic way to find all files to compiles
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

    compile_cmd = helper.generate_java_command_string(files_to_compile, libs, "javac")
    print("COMPILING CODE : {}".format(compile_cmd))
    result = helper.run_command(compile_cmd)

    # handle compilation errors
    # For teaching assistant that test some tasks might want the full feedback
    full_log = config["has_feedback"] and username == config["username_for_full_log"]
    feedback.compilation_feedback(result, full_log)

    #####################################
    #       GENERATE A JAR FILE         #
    #####################################

    # We need a manifest in order to make the created jar runnable
    helper.create_manifest(libs)

    # Create a jar file
    create_jar = helper.generate_jar_file()
    print("GENERATING JAR : {}".format(create_jar))

    # WARNING ; JUST FOR JAVA TO NOT MISUNDERSTAND OUR STRUCTURE, we have to change the CWD in the command
    result = helper.run_command(create_jar, PATH_CLASSES)

    # For debug the jar construction
    # print(result.stdout)

    # handle jar creation errors
    feedback.jar_feedback(result)

    #####################################
    #   RUN  TEST  RUNNER               #
    #####################################

    # invoke runner with classes as arg
    # in the case of code coverage ( Jacoco ) , we need to generate also the report file (exec ) by the JaCoco agent
    coverage_required = True if feedback_settings["feedback_kind"] == "JaCoCo" else False

    run_code = helper.generate_java_command_string(JAR_FILE, libs, coverage=coverage_required, is_jar=True)
    print("RUNNING CODE : {}".format(run_code))
    result = helper.run_command(run_code)

    #####################################
    #   Show and handle results         #
    #####################################

    # Must we use a custom python script for that part ? If not, use the classical script ( feedback.result_feedback )
    feedback_fct = feedback.result_feedback \
        if not feedback_settings["custom_feedback_script"] else feedback.custom_result_feedback
    feedback_fct(result, feedback_settings)

    #####################################
    #   Prepare archive for JPlag       #
    #####################################
    if feedback_settings["plagiarism"]:

        # The files filled by the student are all inside PATH_TEMPLATES
        files_to_be_tested = helper.find_files_in_path(PATH_TEMPLATES)

        # Creates the archive like expected by JPlag
        for student_file in files_to_be_tested:
            command = "archive -a {} -o {}".format(student_file, username)
            helper.run_command(command, universal_newlines=True)


if __name__ == "__main__":
    main()
