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

# Current path of the run script (/task)
CWD = Path(os.getcwd())

# Our code
# For Python 3.5 , we cannot use Path like object directly yet ( fixed in 3.6)
# we have to do : str(PathLibObject)

# Where we keep the good and wrong implementations
PATH_FLAVOUR = str(CWD/"flavour")

# Our templates
PATH_TEMPLATES = str(CWD/"templates")

# Test runner
RUNNER_PATH = str(CWD/"src"/"StudentTestRunner.java")

# Source code to be tested
PATH_SRC = str(CWD/"src")

# File extension
FILE_EXTENSION = ".java"

#####################################
#       Apply templates             #
#####################################

# we have a folder where we have only these files
helper.apply_templates(PATH_TEMPLATES, PATH_SRC)

#####################################
#   COMPILE ALL CODE IN SRC         #
#####################################

# code to be compiled is in SRC AND FLAVOUR
code_to_compile = [
    str(Path(PATH_FLAVOUR)/"{}{}".format("*", FILE_EXTENSION)),
    str(Path(PATH_SRC)/"{}{}".format("*", FILE_EXTENSION))
]
compile_cmd = helper.generate_java_command_string(code_to_compile, "javac")
print(compile_cmd)
result = helper.run_command(compile_cmd)

# handle compilation errors
feedback.compilation_feedback("student_code", result)

#####################################
#   RUN  TEST  RUNNER               #
#####################################

# invoke runner with classes as arg

# Java wants a package name and not the full path so
package_name_string = str(Path(RUNNER_PATH).relative_to(CWD))
package_name = package_name_string.replace("/", ".")

run_code = helper.generate_java_command_string(package_name)
print("{} \n".format(run_code))

result = helper.run_command(run_code)


#####################################
#   Show and handle results         #
#####################################
feedback.result_feedback(result)
