import os
import shutil

COURSE_NAME = 'LEPL1402-0821'

script_dir = os.path.abspath(os.path.dirname(__file__))
source_dir = os.path.join(script_dir, 'src', 'main', 'java')
test_dir = os.path.join(script_dir, 'src', 'main', 'java')
inginious_dir = os.path.join(script_dir, 'inginious')


def _safe_mkdir(directory, delete=False):
    if not os.path.exists(directory):
        os.mkdir(directory)
    elif delete:
        shutil.rmtree(directory)
        os.mkdir(directory)


def create_inginious_dir():
    _safe_mkdir(inginious_dir, delete=True)


def get_modules():
    modules = os.listdir(source_dir)
    modules.sort()
    return modules


def get_exercises(modules):
    exercises = {}
    i = 0
    for module in modules:
        exos = []
        for e in os.listdir(os.path.join(source_dir, module)):
            exos.append((i, e.replace(".java", "")))
            i += 1
        exercises[module] = exos
    return exercises


def generate_inginious_tasks(exercises):
    for module, exos in exercises.items():
        for exo in exos:
            _safe_mkdir(os.path.join(inginious_dir, exo[1]))
            generate_task_yaml(module, exo)


def generate_task_yaml(module, exercise):
    yaml_body = f"""accessible: true
author: ''
categories:
- {module}
contact_url: ''
environment: java
evaluate: best
file: ''
groups: false
input_random: '0'
limits:
    memory: '100'
    output: '2'
    time: '60'
name: {exercise[1]}
network_grading: false
order: {exercise[0]}
problems:
    student_code:
        type: code
        header: ''
        language: java
        name: ''
        default: ''
run_cmd: ''
stored_submissions: 0
submission_limit:
    amount: -1
    period: -1
weight: 1.0
"""
    with open(os.path.join(inginious_dir, exercise[1], 'task.yaml'), 'w') as f:
        f.write(yaml_body)


def generate_course_yaml(exercises):
    yaml_body = f"""accessible: true
name: {COURSE_NAME}
description: ''
admins:
- pschaus
- raminsadre
- aldubray
- jodogne
- fdekeersmaek
- ldierckx
tutors: []
groups_student_choice: false
use_classrooms: true
allow_unregister: true
allow_preview: false
registration: true
registration_password: null
registration_ac: null
registration_ac_list: []
is_lti: false
lti_keys: {{}}
lti_send_back_grade: false
lti_url: ''
tags:
"""
    for module, exos in exercises.items():
        yaml_body += f"    {module}:\n"
        yaml_body += f"        name: {module}"
        yaml_body += """
        type: 2
        visible: true
        description: ''
"""
    yaml_body += "toc:"
    for module, exos in exercises.items():
        yaml_body += f"""
-   id: {module}
    title: {module}
    rank: 0
    task_list:
"""
        for exo in exos:
            yaml_body += f"        {exo[0]}: {exo[1]}\n"

    with open(os.path.join(inginious_dir, 'course.yaml'), 'w') as f:
        f.write(yaml_body)


def main():
    modules = get_modules()
    exercises = get_exercises(modules)

    create_inginious_dir()

    generate_inginious_tasks(exercises)
    generate_course_yaml(exercises)


if __name__ == '__main__':
    main()
