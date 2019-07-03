#!/bin/python3

# les imports
import subprocess
import shlex
from inginious import input
from collections import namedtuple
from pathlib import Path

ProcessOutput = namedtuple('ProcessOutput', ['returncode', 'stdout', 'stderr'])


# Libraries to be add to run/compile via option -cp of java(c)
def librairies():
    lib = ''
    # Rest later : for example
    # lib += ':/usr/share/java/powermock-mockito2-junit-1.7.1/*'
    return lib


# Wrapper to execute system commands and easily get stdout, stderr steams and return code
def run_command(cmd):
    proc = subprocess.Popen(shlex.split(cmd), stderr=subprocess.PIPE, stdout=subprocess.PIPE)
    proc.wait()  # waiting the child process to finish

    stdout = proc.stdout.read().decode('utf-8')
    stderr = proc.stderr.read()  # .decode('utf-8')

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


# Apply parse_template on each file stored in base_path
def apply_templates(base_path):
    basepath = Path(base_path)
    only_top_level_files = [
        Path(base_path)/item.name
        for item in basepath.iterdir()
        if item.is_file()
    ]
    for file in only_top_level_files:
        input.parse_template(file)


# Generate compile/run command for given file
# Bonus : it removes the ".java" extension for javac
def generate_java_command_string(filename, classpath, command="java"):
    libs = librairies()
    command_code = "{} -classpath {} {} {}" \
        .format(
            command,
            classpath,
            # Something, no need of libraries so include only if needed
            '' if libs == '' else '-cp {}'.format(libs),
            filename if command == "javac" else basename_filename(filename)
        )
    return command_code


# to append ccommand with args
def append_args_to_command(cmd, args=[]):
    return "{} {}".format(cmd, *args)


# Extract the filename basename without extension
# And returns only the name
def basename_filename(filename):
    # extract the basename
    basename = Path(filename).name
    # the file extension is .java so 5 characters
    # Better than using os.path.splittext : https://stackoverflow.com/a/39648242/6149867
    return basename[:-5]
