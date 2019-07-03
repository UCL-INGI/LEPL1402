#!/bin/python3

# les imports
import os
from pathlib import Path
from .__templates__.helper import *
from .__templates__.feedback import *

# Current path of the run script
CWD = Path(os.getcwd())

# paths to store
student_path = CWD/"student"

# Test runner
runner_path = CWD/"Runner.java"

# Get and store student uploaded file(s)
filename = store_uploaded_file("studentupload", student_path)

# Parse templates
# helper.apply_templates("somePath")

# Compile the java student code
compile_code = generate_command_string(filename, CWD, "javac")
result = run_command(compile_code)

# handle compilation errors
compilation_feedback("studentupload", result)

# Execute the java student code
run_code = generate_command_string(runner_path, CWD)
result = run_command(run_code)

# TODO result stdout

