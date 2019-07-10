#!/bin/python3

# les imports
import subprocess
from inginious import input
from collections import namedtuple
from pathlib import Path

from fragments.constants import *

ProcessOutput = namedtuple('ProcessOutput', ['returncode', 'stdout', 'stderr'])


# Libraries to be add to run/compile via option -cp of java(c)
def librairies():
    
    # Current folder (not really used but why not)
    libs = '.'
    libs_to_include = ["junit-4.12.jar", "hamcrest-core-1.3.jar", "JavaGrading-0.3.2.jar"]

    # other librarires if provided
    libs += ":{}".format(
        ':'.join([
            str(Path(LIBS_FOLDER) / lib)
            for lib in libs_to_include
        ])
    ) if libs_to_include else ""

    return libs


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
    filename_with_path = Path(base_path) / filename

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
        dst = str(Path(out_path) / file)
        src = str(Path(base_path) / file)
        input.parse_template(src, dst)


# Generate compile/run command for given file
# the files_input argument is either an array of string or a string :
# 1. If it is a string, it means we want to run "java" command with only one file
# 2. Else , it means we want to run "javac" command (possible to use globing characters * )
def generate_java_command_string(files_input, command="java", libs=librairies(), coverage=False, is_jar=False):
    # file(s) to compile or to execute
    files = ' '.join([str(v) for v in files_input]) if command == "javac" else without_extension(files_input)

    # options to be used
    # space in key is needed as we simply concat key/value strings
    options = [
        # Only add the coverage option when needed
        ("-javaagent:", str(Path(LIBS_FOLDER) / "jacocoagent.jar") if coverage else None),
        # Include libraries if not a jar file
        # See N°2 : https://javarevisited.blogspot.com/2012/10/5-ways-to-add-multiple-jar-to-classpath-java.html
        ("-cp ", libs if not is_jar else None),
        # If we use a jar file for coverage
        ("-jar ", "{}.jar".format(files) if is_jar else None)
    ]

    # only include not null options values
    str_options = ' '.join(
        ["{}{}".format(option, value) for (option, value) in options if value]
    )

    # If jar, no need to format the last argument
    if is_jar:
        return "{} {}".format(command, str_options)
    else:
        return "{} {} {}".format(command, str_options, files)


# to append ccommand with args
def append_args_to_command(cmd, args):
    return "{} {}".format(cmd, ' '.join([str(v) for v in args]))


# filename without extension
def without_extension(path):
    return path.replace(Path(path).suffix, "")


# Give relative path : Java interpret /task as package ( when it's not be the case)
def relative_path(path):
    return str(Path(path).relative_to(CWD))


# Since using a jar simply ignore -cp option, we have no other choice to create a manifest to add the libraries
def create_manifest():
    with open(MANIFEST_FILE, 'w+') as manifest:
        libs = librairies().split(":")
        libs_str = "{} {}\n".format("Class-Path:", ' '.join(libs))
        manifest.write(libs_str)
        manifest.close()


# For jacoco , only way to proceed
# Files to compile need just to refactor the string
def generate_jar_file(class_folders, main_class=RUNNER_JAVA_NAME, dst=JAR_FILE, manifest=MANIFEST_FILE):
    return "jar -cmvfe {} {} {} {}".format(manifest, dst, without_extension(main_class), ' '.join([relative_path(path) for path in class_folders]))

