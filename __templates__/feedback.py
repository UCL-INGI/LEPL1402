import sys
import os
from inginious import rst, feedback


# Throw a fatal error if the given code doesn't compile
def compilation_feedback(problem_id, result):
    if result.stderr:
        msg = "Votre fichier n'a pas compilé ou une erreur s'est produite : {}".format(result.stderr)
        feedback.set_problem_feedback(msg, problem_id)
        feedback.set_global_result("Erreur de compilation (ou autre)")
        sys.exit(0)


# Append a failure/error log in the result file
def append_log(name, kind="Exception", method="", result_file_path="student/task.result"):
    # if file doesn't exist yet, creates it
    with open(result_file_path, "w+" if os.stat(result_file_path).st_size == 0 else "a+") as file:
        file.write("{}:{}:{}".format(
                "E" if kind == "Exception" else "F",
                name,
                method
            )
        )


def generate_feedback(result_file_path="student/task.result"):
    with open(result_file_path, 'r', encoding='utf-8') as file:
        # empty file : no problem
        if os.stat(result_file_path).st_size == 0:
            msg = "Votre code a passé avec succès tous les tests pour cette mission."
            feedback.set_global_result(msg)
            sys.exit(0)
        # not empty file ; each problem is encoded on one line with the following format
        # Kind:Class:....
        else:
            for line in file:
                line = line.strip()
                # by default, there is at least the name of ; second one is optional
                elems = line.split(':')
                name = elems[0]
                method = elems[1]

                # Two kind of problems : exception (E) and failure (F)
                # Messages follows the same structure
                msg = "{}  : la classe {} {} {} ) \n" \
                    .format(
                        "Exception" if line[0] == "E" else "Erreur",
                        name,
                        "a lancé une exception" if line[0] == "E" else "n'a pas passé les tests",
                        "( méthode {} )".format(method) if method != "" else ""
                    )
                # Appends global message
                feedback.set_global_result(msg, True)
