import os
import shutil

COURSE_NAME = 'LEPL1402-0821'

script_dir = os.path.abspath(os.path.dirname(__file__))
source_dir = os.path.join(script_dir, 'src', 'main', 'java')
test_dir = os.path.join(script_dir, 'src', 'main', 'java')
inginious_dir = os.path.join(script_dir, 'inginious')
templates_dir = os.path.join(script_dir, 'templates')


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
            _safe_mkdir(os.path.join(inginious_dir, exo[1]), delete=True)
            generate_task_yaml(module, exo)


def generate_task_yaml(module, exercise):
    with open(os.path.join(templates_dir, 'task.yaml.tpl'), 'r') as f:
              tpl = ''.join(f.readlines())
    with open(os.path.join(inginious_dir, exercise[1], 'task.yaml'), 'w') as f:
        f.write(tpl.format(COURSE_NAME, exercise[1], 'placeholder.zip', exercise[0]))


def generate_course_yaml(exercises):
    with open(os.path.join(templates_dir, 'course.yaml.tpl'), 'r') as f:
              tpl = ''.join(f.readlines())

    dispenser_data = ""
    for module, exos in exercises.items():
        dispenser_data += f"""
-   id: {module}
    title: {module}
    rank: 0
    task_list:
"""
        for exo in exos:
            dispenser_data += f"        {exo[0]}: {exo[1]}\n"

    with open(os.path.join(inginious_dir, 'course.yaml'), 'w') as f:
        f.write(tpl.format(dispenser_data))


def main():
    modules = get_modules()
    exercises = get_exercises(modules)

    create_inginious_dir()

    generate_inginious_tasks(exercises)
    generate_course_yaml(exercises)


if __name__ == '__main__':
    main()
