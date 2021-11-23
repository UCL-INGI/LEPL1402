import os
import shutil
import re

COURSE_NAME = 'LEPL1402-0821'

script_dir = os.path.abspath(os.path.dirname(__file__))
source_dir = os.path.join(script_dir, 'src', 'main', 'java')
test_dir = os.path.join(script_dir, 'src', 'test', 'java')
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


def get_exercises():
    exercises = {}
    i = 0
    for module in get_modules():
        exos = []
        for e in os.listdir(os.path.join(source_dir, module)):
            exos.append((i, e.replace(".java", "")))
            i += 1
        exercises[module] = exos
    return exercises


def generate_inginious_tasks(exercises):
    for module, exos in exercises.items():
        for exo in exos:
            folder = os.path.join(inginious_dir, exo[1])
            _safe_mkdir(folder, delete=True)

            generate_task_yaml(folder, exo)
            generate_task_public(folder)
            generate_task_test(folder, module, exo)
            copy_run_file(folder)
            copy_libs(folder)


def generate_task_yaml(folder, exercise):
    with open(os.path.join(templates_dir, 'task.yaml.tpl'), 'r') as f:
              tpl = ''.join(f.readlines())
    with open(os.path.join(folder, 'task.yaml'), 'w') as f:
        f.write(tpl.format(COURSE_NAME, exercise[1], 'placeholder.zip', exercise[0]))


def generate_task_public(folder):
    _safe_mkdir(os.path.join(folder, 'public'), delete=True)


def generate_task_test(folder, module, exercise):
    in_name = exercise[1] + 'Test.java'
    out_name = exercise[1] + 'TestInginious.java'
    outfile = open(os.path.join(folder, out_name), 'w')
    with open(os.path.join(test_dir, module, in_name)) as f:
        buffer = list()
        in_test = False
        count_open_acc = 0
        for line in f:
            if line.startswith(f'public class {exercise[1]}Test'):
                outfile.write('import com.github.guillaumederval.javagrading.*;\n')
                outfile.write(line.replace(f'{exercise[1]}Test', f'{exercise[1]}TestInginious'))
            elif '@Test' in line:
                outfile.write(line)
                outfile.write('\t@Grade(value=1, cpuTimeout=1000)\n')
                in_test = True
            elif in_test:
                count_open_acc += line.count('{')
                count_open_acc -= line.count('}')
                if count_open_acc == 0:
                    # End of the test function
                    in_test = False
                    for buffered_line in buffer:
                        outfile.write(buffered_line)
                    outfile.write(line)
                    buffer = list()
                else:
                    if 'assert' in line:
                        rgx = re.match('.*"(.*)".*', line)
                        if rgx is not None:
                            assertMsg = rgx.group(1)
                            outfile.write(f'\t@GradeFeedback(message="{assertMsg}", onFail=true)\n')
                    buffer.append(line)
            else:
                outfile.write(line)
    outfile.close()


def copy_run_file(folder):
    run_file = 'run'
    source = os.path.join(templates_dir, run_file)
    dest = os.path.join(folder, run_file)
    shutil.copyfile(source, dest)


def copy_libs(folder):
    shutil.copytree(os.path.join(templates_dir, 'libs'),
                    os.path.join(folder, 'libs'))


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
    exercises = get_exercises()

    create_inginious_dir()

    generate_inginious_tasks(exercises)
    generate_course_yaml(exercises)


if __name__ == '__main__':
    main()
