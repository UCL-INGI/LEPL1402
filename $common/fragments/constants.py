import os
from pathlib import Path

#####################################
#             CONSTANTS             #
#####################################

# Current path of the run script of the task (/task)
CWD = Path(os.getcwd())

# File extension
FILE_EXTENSION = ".java"

# Our code
# For Python 3.5 , we cannot use Path like object directly yet ( fixed in 3.6)
# we have to do : str(PathLibObject)

# Where we keep the good and wrong implementations
PATH_FLAVOUR = str(CWD / "flavour")

# Our templates
PATH_TEMPLATES = str(CWD / "templates")

# Source code to be tested
PATH_SRC = str(CWD / "src")

# Files that must be compiled
# code to be compiled is in SRC AND FLAVOUR
FILES_TO_COMPILE = [
    str(Path(PATH_FLAVOUR) / "{}{}".format("*", FILE_EXTENSION)),
    str(Path(PATH_SRC) / "{}{}".format("*", FILE_EXTENSION))
]

# Test runner
RUNNER_PATH = str(CWD / "src" / "StudentTestRunner.java")

# Runner name as expected from java (Java wants a package name and not the full path so)
RUNNER_JAVA_NAME = str(Path(RUNNER_PATH).relative_to(CWD)).replace("/", ".")

# Config file to generate feedback for every kind of exercises
FEEDBACK_REVIEW_PATH = str(CWD / "feedback_settings.yaml")

# JaCoCo coverage file path
JACOCO_EXEC_FILE = str(CWD / "jacoco.exec")

# JaCoCo classfiles
# By default, use the flavour folder (so that we can efficiently
JACOCO_CLASS_FILES = [PATH_FLAVOUR]

# JaCoCo result file in xml
JACOCO_RESULT_FILE = str(CWD / "coverage_result.xml")

# JaCoCo needs a jar to execute its stuff
JACOCO_JAR_FILE = str(CWD / "JaCoCo-compiled.jar")
