#!/usr/bin/env python3
import sys
import yaml
import importlib.util
import unittest
from unittest.mock import MagicMock, patch
from inspect import signature
from colored import fg, bg, attr
from beautifultable import BeautifulTable
from pathlib import Path


def text_with_color(text, color):
    return "%s%s %s %s" % (fg(color), bg(0), text, attr(0))


# Dynamically load modules we need
# Credits to https://stackoverflow.com/a/67692/6149867
# And for the explanation : http://www.blog.pythonlibrary.org/2016/05/27/python-201-an-intro-to-importlib/
def dynamically_load_module(module, path):
    spec = importlib.util.spec_from_file_location(module, path)
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


# The used validations for feedback_settings.yml
# task_folder is a Path object of a task
# feedback_settings is the yaml of this task
def validations(feedback_settings, folder, items_in_task):
    def verification_result(validation_kind, validation_fct):
        return validation_fct() if validation_kind in feedback_settings else ""

    def feedback_kind_check():
        return "{}{}".format(
            text_with_color("You must set has_feedback to True in order to have feedback on INGInious", 196) + "\n"
            if ("has_feedback" not in feedback_settings) or not feedback_settings["has_feedback"] else "",
            text_with_color("You must have a flavour folder for coverage testing", 196) + "\n"
            if feedback_settings["feedback_kind"] == "JaCoCo" and "flavour" not in items_in_task else ""
        )

    def quorum_check():
        if not isinstance(feedback_settings["quorum"], float):
            return text_with_color("Quorum should be a float", 196) + "\n"
        else:
            if feedback_settings["quorum"] > 1.0 or feedback_settings["quorum"] < 0.0:
                return text_with_color("Quorum should be a float between 0.0 and 1.0", 196) + "\n"
            else:
                return ""

    def coverage_stats_check():
        if not isinstance(feedback_settings["coverage_stats"], list):
            return text_with_color("coverage_stats should be a sequence of string", 196) + "\n"
        else:
            if any(
                    [item not in ["INSTRUCTION", "BRANCH", "LINE", "METHOD", "CLASS"]
                     for item in feedback_settings["coverage_stats"]]
            ):
                return text_with_color("Unknown value inside coverage_stats", 196) + "\n"
            else:
                return ""

    def coverage_xpaths_check():
        if not isinstance(feedback_settings["coverage_xpaths"], list):
            return text_with_color("coverage_xpaths should be a sequence of string", 196) + "\n"
        else:
            if any(
                    [(not isinstance(item, str))
                     for item in feedback_settings["coverage_xpaths"]]
            ):
                return text_with_color("Unknown value inside coverage_xpaths", 196) + "\n"
            else:
                return ""

    def statement_presence_check(statement_kind):
        def checker():
            if not isinstance(feedback_settings[statement_kind], dict):
                return text_with_color("{} should be a dictionary (String, List/Sequence) "
                                       .format(statement_kind), 196) + "\n"
            else:
                if any([
                    (not isinstance(problem_id, str)) or (not isinstance(statements, list))
                    for (problem_id, statements) in feedback_settings[statement_kind].items()
                ]):
                    return text_with_color("{} is improperly constructed".format(statement_kind), 196) + "\n"
                else:
                    return ""

        return checker

    def plagiarism_check():
        if not isinstance(feedback_settings["plagiarism"], bool):
            return text_with_color("plagiarism should be a boolean", 196) + "\n"
        else:
            return ""

    def external_libraries_check():
        if not isinstance(feedback_settings["external_libraries"], list):
            return text_with_color("external_libraries should be a sequence of string", 196) + "\n"
        else:
            if any([
                not isinstance(item, str) or not (folder / item).exists()
                for item in feedback_settings["external_libraries"]]
            ):
                return text_with_color("One or more path(s) in external_libraries isn't correct", 196) + "\n"
            else:
                return ""

    def custom_feedback_script_check():
        if not isinstance(feedback_settings["custom_feedback_script"], str):
            return text_with_color("custom_feedback_script should be a string", 196) + "\n"
        else:
            if not (folder / feedback_settings["custom_feedback_script"]).exists():
                return text_with_color("The path in custom_feedback_script isn't correct", 196) + "\n"
            else:
                # check that the given script follows the given format :
                # def main(result, feedback_settings)
                try:
                    path_to_script = str((folder/feedback_settings["custom_feedback_script"]).absolute())
                    custom_feedback_script = dynamically_load_module("custom_feedback_script", path_to_script)
                    if not hasattr(custom_feedback_script, "main") \
                            or len(signature(custom_feedback_script.main).parameters) != 2:
                        return text_with_color("The custom_feedback_script must follow the format : "
                                               "def main(result, feedback_settings)", 196) + "\n"
                    else:
                        return ""
                except (ModuleNotFoundError, BaseException) as err:
                    return text_with_color("Error when loading custom_feedback_script : {}".format(err),
                                           196) + "\n"

    def status_message_check():
        if not isinstance(feedback_settings["status_message"], dict):
            return text_with_color("status_message should be a dictionary (integer, String) ", 196) + "\n"
        else:
            if any([
                (not isinstance(status_code, int)) or (not isinstance(message, str))
                for (status_code, message) in feedback_settings["status_message"].items()
            ]):
                return text_with_color("status_message is improperly constructed", 196) + "\n"
            else:
                return ""

    return iter([
        verification_result("feedback_kind", feedback_kind_check),
        verification_result("quorum", quorum_check),
        verification_result("coverage_stats", coverage_stats_check),
        verification_result("coverage_xpaths", coverage_xpaths_check),
        verification_result("prohibited", statement_presence_check("prohibited")),
        verification_result("required", statement_presence_check("required")),
        verification_result("plagiarism", plagiarism_check),
        verification_result("external_libraries", external_libraries_check),
        verification_result("custom_feedback_script", custom_feedback_script_check),
        verification_result("status_message", status_message_check)
    ])


# Because custom script use INGINIOUS methods I cannot import, I must hide the import error
# when testing custom_script
# credits to https://turlucode.com/mock-python-imports-in-unit-tests/
sys.modules['inginious'] = MagicMock()


print("CHECK THAT ALL TASKS FOLLOWS THE EXPLAINED STRUCTURE IN DOC")

# As inginious-autotest required a folder where everything is stored, use that
path = Path("LEPL1402")
task_folders = [x for x in path.iterdir() if x.is_dir() and x.name not in {"$common", "$test$"}]

# Creates a table
table = BeautifulTable()
table.column_headers = ["task folder", "reasons"]

# fill the table
for task_folder in task_folders:
    files_and_folder_in_task = [x.name for x in task_folder.iterdir()
                                if
                                x.name in ["src", "templates", "flavour", "feedback_settings.yaml", "run", "task.yaml"]]

    # skip folder with only task ( QCM )
    if len(files_and_folder_in_task) == 1 and "task.yaml" in files_and_folder_in_task:
        continue

    # check if feedback_settings
    if "feedback_settings.yaml" in files_and_folder_in_task:
        feedback_path = str(task_folder / "feedback_settings.yaml")
        problem = ""

        # check existence of folders src and templates (TODO maybe also check emptiness of folder )
        if "src" not in files_and_folder_in_task or "templates" not in files_and_folder_in_task:
            problem += text_with_color("Missing any of these critical folder(s) : src / templates", 196) + "\n"

        with open(feedback_path, "r") as stream:
            try:
                result = yaml.safe_load(stream)
                problem += "".join(validations(result, task_folder, files_and_folder_in_task))
            except yaml.YAMLError as exc:
                problem += text_with_color("Parsing error in feedback_settings.yaml\n", 196)

        if problem != "":
            table.append_row([task_folder.name, problem])

# if there is errors(s), tell Travis
if len(table) > 0:
    print(table)
    sys.exit(42)
else:
    print("Nothing wrong : good job :) ")
    sys.exit(0)
