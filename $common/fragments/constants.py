from pathlib import Path

#####################################
#             CONSTANTS             #
#####################################

# Current path of the run script of the task (/task)
CWD = Path.cwd()

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

# .class storage path
PATH_CLASSES = str(CWD / "classes")

# Test runner
RUNNER_PATH = str(CWD / "src" / "StudentTestRunner.java")

# Runner name as expected from java (Java wants a package name and not the full path so)
RUNNER_JAVA_NAME = str(Path(RUNNER_PATH).relative_to(CWD)).replace("/", ".")

# Config file to generate feedback for every kind of exercises
FEEDBACK_REVIEW_PATH = str(CWD / "feedback_settings.yaml")

# JaCoCo needs a jar to execute its stuff
JAR_FILE = str(CWD / "task_evaluation.jar")

# Manifest for JAR FILE (since it ignores -cp option)
MANIFEST_FILE = str(CWD / "MANIFEST.MF")

# JaCoCo coverage file path
JACOCO_EXEC_FILE = str(CWD / "jacoco.exec")

# JaCoCo classfiles for report ( only take the useful one in flavour)
JACOCO_CLASS_FILES = [str(Path(PATH_CLASSES) / "flavour")]

# JaCoCo result file in xml
JACOCO_RESULT_FILE = str(CWD / "coverage_result.xml")

# Libraries folder
LIBS_FOLDER = "/course/common/libs"

# Default Libraries used in the runscript ( stored in LIBS_FOLDER )
DEFAULT_LIBRARIES = ["junit-4.12.jar", "hamcrest-core-1.3.jar", "JavaGrading.jar"]
