#!/bin/python3

# les imports
import os
from .__templates__.helper import *
from .__templates__.feedback import *

#####################################
#             CONSTANTS             #
#####################################

# Current path of the run script
CWD = Path(os.getcwd())

# paths to store
student_path = CWD/"student"

# Test runner
runner_path = CWD/"Runner.java"

#####################################
#   COMPILATION DU RUNNER           #
#####################################

# Compile the runner
runner_compile = generate_java_command_string(runner_path, CWD, "javac")
result = run_command(runner_path)

# handle compilation errors
compilation_feedback("studentupload", result)

#####################################
#   EXEMPLE COMPILATION TEMPLATES   #
#####################################

# Parse templates
# helper.apply_templates("somePath")

#####################################
#   EXEMPLE LECTURE DE FICHIER STU  #
#####################################

# Get and store student uploaded file(s)
filename = store_uploaded_file("studentupload", student_path)

# Compile the java student code
compile_code = generate_java_command_string(filename, CWD, "javac")
result = run_command(compile_code)

# handle compilation errors
compilation_feedback("studentupload", result)

#####################################
#   EXEMPLE LANCEMENT DU RUNNER     #
#####################################

# runner default command
run_code = generate_java_command_string(runner_path, CWD)

# append filepaths so that we can lookup for the file in Runner
run_code = append_args_to_command(run_code, [filename])


# TODO result stdout

