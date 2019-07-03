import sys
from pathlib import Path
from inginious import feedback


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
    with open(result_file_path, "w+" if Path(result_file_path).is_file() else "a+") as file:
        file.write("{}:{}:{}".format(
            "E" if kind == "Exception" else "F",
            name,
            method
        )
        )


# common behaviour message for "return code" result
return_messages = {
    0: "Votre code a passé avec succès tous les tests pour cette mission.",
    252: "La limite de mémoire de votre programme est dépassée",
    253: "La limite de temps d'exécution de votre programme est dépassée"
}


# Generate the final message(s) to student
def generate_result_feedback(result, result_file_path):
    msg = return_messages.get(result.returncode, "Il semble que vous ayiez fait des erreurs dans votre code")
    # for success only, display only the message and nothing else
    if result.returncode == 0:
        feedback.set_global_result(msg)
        sys.exit(0)
    else:
        feedback.set_global_result(msg, True)
        # In case the result feedback is not present
        if Path(result_file_path).is_file():
            generate_feedback(result_file_path)
        else:
            unknown = "Cannot find your result file to generate advanced feedback for you :( "
            feedback.set_global_result(unknown, True)
            sys.exit(0)


# Generate advanced feedback if the student
def generate_feedback(result_file_path):
    with open(result_file_path, 'r', encoding='utf-8') as file:
        # not empty file ; each problem is encoded on one line with the following format
        # Kind:Class:....
        for line in file:
            line = line.strip()

            # A identified failure type ( from now : Exception and Assertion )
            if line[0] in ['E', 'F']:

                # by default, there is at least the name of class; second one is optional
                elems = line.split(':')
                name = elems[0]
                method = elems[1]

                # Two kind of problems : exception (E) and failure (F)
                # Messages follows the same structure
                msg = "{}  : la classe {} {} {} ) \n" \
                    .format(
                        "Exception" if line[0] == 'E' else "Erreur",
                        name,
                        "a lancé une exception" if line[0] == 'E' else "n'a pas passé les tests",
                        "( méthode {} )".format(method) if method != '' else ''
                    )

                # Appends global message
                feedback.set_global_result(msg, True)

            # Other
            else:

                # Since we don't know the failure (yet), let's throw it at the face of the student
                msg = "{} \n".format(line)

                # Appends global message
                feedback.set_global_result(msg, True)
