#!/bin/python3
import importlib.util
import os
from pathlib import Path


# Dynamically load modules we need
# Credits to https://stackoverflow.com/a/67692/6149867
# And for the explanation : http://www.blog.pythonlibrary.org/2016/05/27/python-201-an-intro-to-importlib/
def dynamically_load_module(module, path):
    spec = importlib.util.spec_from_file_location(module, path)
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


#####################################
# Our import for common function    #
#####################################

helper, feedback = [
    dynamically_load_module(mod, "/course/common/{}.py".format(mod))
    for mod in ["helper", "feedback"]
]

#####################################
#             CONSTANTS             #
#####################################

# Current path of the run script
CWD = Path(os.getcwd())

# Template folder location
TEMPLATES = CWD/"templates"

# Our code
FLAVOUR = CWD/"flavour"

# Src folder location ( that will contains the templates with code
DST_TEMPLATES = CWD/"src"

# Test runner
runner_path = CWD/"Runner.java"

##########################################
#   Warning : create each new dir before #
##########################################


#####################################
#       Apply templates             #
#####################################

helper.apply_templates(TEMPLATES, DST_TEMPLATES)

# TODO le(s) compiler par la suite si pas le meme folder

#####################################
#   COMPILE  TEST  RUNNER           #
#####################################

runner_compile = helper.generate_java_command_string(runner_path, CWD, "javac")
result = helper.run_command(runner_path)

# handle compilation errors
feedback.compilation_feedback("some_problem_id", result)

#####################################
#   COMPILE OTHER JAVA CODE         #
#####################################

# Get the classes names
flavor_classes = helper.find_files_in_folder(FLAVOUR)

# Compile each one and throw error if cannot compile
for flavor in flavor_classes:
    filename = "{}.java".format(flavor)
    code_compile = helper.generate_java_command_string(FLAVOUR/filename, FLAVOUR, "javac")
    result = helper.run_command(code_compile)
    feedback.compilation_feedback("some_problem_id", result)


#####################################
#   RUN  TEST  RUNNER               #
#####################################

# invoke runner with classes as arg
run_code = helper.generate_java_command_string(runner_path, CWD)
run_code = helper.append_args_to_command(run_code, [flavor_classes])

result = helper.run_command(run_code)


#####################################
#   Show and handle results         #
#####################################
feedback.result_feedback(result)
