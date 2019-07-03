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
