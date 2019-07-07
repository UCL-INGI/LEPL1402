#!/bin/python3

# les imports
import subprocess
from inginious import input
from collections import namedtuple
from pathlib import Path

ProcessOutput = namedtuple('ProcessOutput', ['returncode', 'stdout', 'stderr'])


# Libraries to be add to run/compile via option -cp of java(c)
def librairies():
    # JUnit and JavaGrading
    lib = '.'
    lib += ':/course/common/junit-4.12.jar'
    lib += ':/course/common/hamcrest-core-1.3.jar'
    lib += ':/course/common/JavaGrading-0.3.2.jar'

    # Rest later : for example
    # lib += ':/usr/share/java/powermock-mockito2-junit-1.7.1/*'
    return lib


# Wrapper to execute system commands and easily get stdout, stderr steams and return code
def run_command(cmd):
    proc = subprocess.Popen(cmd, shell=True, stderr=subprocess.PIPE, stdout=subprocess.PIPE)
    proc.wait()  # waiting the child process to finish

    stdout = proc.stdout.read().decode('utf-8')
    stderr = proc.stderr.read().decode('utf-8')

    return ProcessOutput(proc.returncode, stdout, stderr)


# Store the given problemid code into the base_path folder ( "student" by default) and return the filename
def store_uploaded_file(problem_id, base_path):
    student_upload = input.get_input(problem_id)
    filename = input.get_input("{}:filename".format(problem_id))
    filename_with_path = Path(base_path)/filename

    # Stores a copy of this file inside "student" folder
    with open(filename_with_path, 'w+') as fileUpload:
        fileUpload.write(student_upload.decode('utf-8'))
        fileUpload.close()

    return filename_with_path


# Apply parse_template on each file stored in base_path to out_path
def apply_templates(base_path, out_path):
    basepath = Path(base_path)
    only_top_level_files = [
        item.name
        for item in basepath.iterdir()
        if item.is_file()
    ]
    for file in only_top_level_files:
        dst = str(Path(out_path)/file)
        src = str(Path(base_path)/file)
        input.parse_template(src, dst)


# Generate compile/run command for given file
# It removes the ".java" extension for javac
# the files_input argument is either an array of string or a string :
# 1. If it is a string, it means we want to run "java" command with only one file
# 2. Else , it means we want to run "javac" command (possible to use globing characters * )
def generate_java_command_string(files_input, command="java"):
    libs = librairies()
    # the file extension is .java so 5 characters
    # Better than using os.path.splittext : https://stackoverflow.com/a/39648242/6149867
    files = ' '.join([str(v) for v in files_input]) if command == "javac" else files_input[:-5]

    command_code = "{} {} {}" \
        .format(
            command,
            "-cp {}".format(libs),
            files
        )
    return command_code


# to append ccommand with args
def append_args_to_command(cmd, args=[]):
    return "{} {}".format(cmd, ' '.join([str(v) for v in args]))
