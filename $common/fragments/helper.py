#!/bin/python3

# les imports
import subprocess
from inginious import input
from collections import namedtuple
from pathlib import Path

from fragments.constants import *

ProcessOutput = namedtuple('ProcessOutput', ['returncode', 'stdout', 'stderr'])


# Libraries to be add to run/compile via option -cp of java(c)
def libraries(external_libraries):
    
    # Current folder (not really used but why not)
    libs = '.'

    # other librarires if provided
    libs += ":{}".format(
        ':'.join([
            str(Path(LIBS_FOLDER) / lib)
            for lib in DEFAULT_LIBRARIES
        ])
    ) if DEFAULT_LIBRARIES else ""

    # We want to include specific libraries for this task
    if external_libraries:
        libs += ":{}".format(
            ':'.join([
                str(CWD / lib)
                for lib in external_libraries
            ])
        )

    return libs


# Wrapper to execute system commands and easily get stdout, stderr steams and return code
def run_command(cmd, cwd=None, universal_newlines=None):
    proc = subprocess.Popen(cmd, cwd=cwd, shell=True,
                            stderr=subprocess.PIPE,
                            stdout=subprocess.PIPE,
                            universal_newlines=universal_newlines)
    proc.wait()  # waiting the child process to finish

    stdout = proc.stdout.read().decode('utf-8')
    stderr = proc.stderr.read().decode('utf-8')

    return ProcessOutput(proc.returncode, stdout, stderr)


# Store the given problemid code into the base_path folder ( "student" by default) and return the filename
# Warning : this function is not used in the runfile but I keep it just to remember how to deal with get_input
def store_uploaded_file(problem_id, base_path):
    student_upload = input.get_input(problem_id)
    filename = input.get_input("{}:filename".format(problem_id))
    filename_with_path = Path(base_path) / filename

    # Stores a copy of this file inside "student" folder
    with open(filename_with_path, 'w+') as fileUpload:
        fileUpload.write(student_upload.decode('utf-8'))
        fileUpload.close()

    return filename_with_path


# Find all java packages folder inside path recursively ( useful for javac )
def find_files_folder_in_path(path):
    base_folder = Path(path)
    files = Path(path).rglob("*{}".format(FILE_EXTENSION)) if base_folder.exists() else []
    return { 
        str(Path(relative_path(file)).parent)
        for file in files
    }


# Find all java files
def find_files_in_path(path):
    base_folder = Path(path)
    files = Path(path).rglob("*{}".format(FILE_EXTENSION)) if base_folder.exists() else []
    return {
        relative_path(file)
        for file in files
    }


# Apply parse_template on each file stored in base_path
def apply_templates(base_path):
    basepath = Path(base_path)
    files = basepath.rglob("*{}".format(FILE_EXTENSION)) 

    for file in files:
        src = str(file)
        input.parse_template(src)


# Generate compile/run command for given file
# the files_input argument is either an array of string or a string :
# 1. If it is a string, it means we want to run "java" command with only one file
# 2. Else , it means we want to run "javac" command (possible to use globing characters * )
def generate_java_command_string(files_input, libs, command="java", coverage=False, is_jar=False):
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
        # enable assertions
        ("-ea", "" if command == "java" else None),
        # If we use a jar file for coverage
        ("-jar ", "{}.jar".format(files) if is_jar else None),
        # If javac , stores classes into one folder
        ("-d ", PATH_CLASSES if command == "javac" else None)
    ]

    # only include not null options values
    str_options = ' '.join(
        ["{}{}".format(option, value) for (option, value) in options if value is not None]
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
def relative_path(path, base_path=CWD):
    return str(Path(path).relative_to(base_path))


# Since using a jar simply ignore -cp option, we have no other choice to create a manifest to add the libraries
def create_manifest(libraries):
    with open(MANIFEST_FILE, 'w+') as manifest:
        libs = libraries.split(":")
        libs_str = "{} {}\n".format("Class-Path:", ' '.join(libs))
        manifest.write(libs_str)
        manifest.close()


# Create a jar file using the class files inside the PATH_CLASSES
def generate_jar_file(main_class=RUNNER_JAVA_NAME, dst=JAR_FILE, manifest=MANIFEST_FILE):
    return "jar -cmvfe {} {} {} {}".format(manifest, dst, without_extension(main_class), ".")


# Check out if any statment of the prohibited array is contained in the @problem input
def contains_prohibited_statement_in_input(prohibited_array, problem_id):
    student_upload = input.get_input(problem_id)
    # Extract the given code into a single String file with no space, leading stuff, etc...
    # FYI: It is done to simplify the verification as statements in java could be on multiple lines
    source_code_as_string = student_upload.strip().replace("\n", '').replace("\t", '').replace(" ", '')
    
    # if any match , student tried to cheat
    return any(
        prohibited_statment.strip().replace(" ", '') in source_code_as_string 
        for prohibited_statment in prohibited_array
    )


# Main method that will be invoked by runfile.py to check all given problem inputs for prohibited instructions
def contains_prohibited_statement(feedback_settings):
    return any(
        contains_prohibited_statement_in_input(statements, problem_id)
        for (problem_id, statements) in feedback_settings["prohibited"].items()
    )
