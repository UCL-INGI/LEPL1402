import yaml
from pathlib import Path


# Using a yaml file, we can extract the kind of exercise/feedback
def config_file_to_dict(file_path):
    # Check github wiki for more information
    default_values = {
        "has_feedback": False,
        "quorum": 1.0,
        "feedback_kind": None,
        "coverage_stats": None,
        "prohibited": {},
        "plagiarism": False,
        "external_libraries": None,
        "status_message": {
            0: "Your code has successfully passed all tests for this mission.",
            1: "Your code failed all tests for this mission.",
            2: "You used prohibited instructions (such as System.exit) : read carefully the assignment.",
            3: "Your tests don't cover all cases.",
            252: "The memory limit of your program is exceeded.",
            253: "The time limit for running your program has been exceeded."
        }
    }

    # no config file so use basic settings
    if not Path(file_path).exists():
        return default_values
    else:

        with open(file_path, "r") as stream:
            # File must not be empty
            load_config = yaml.load(stream)
            # If no file given
            if load_config is None:
                return default_values
            else:
                # Merge dictionaries
                # The ** operator doesn't correctly merged the dictionary "status_message", so monkey patching this
                load_config["status_message"] = {
                    **default_values["status_message"],
                    **(load_config["status_message"] if "status_message" in load_config else {})
                }

                return {**default_values, **load_config}
