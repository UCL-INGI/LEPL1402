import os
import shutil
import re
import random
import string

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


def _make_archive(source, destination):
    """
    Wrapper to shutil.make_archive taken from:
    https://stackoverflow.com/questions/45245079/python-how-to-use-shutil-make-archive
    :param source: source path
    :param destination: destination (including .zip)
    :return: None
    """
    base = os.path.basename(destination)
    name = base.split('.')[0]
    format = base.split('.')[1]
    archive_from = os.path.dirname(source)
    archive_to = os.path.basename(source.strip(os.sep))
    shutil.make_archive(name, format, archive_from, archive_to)
    shutil.move('%s.%s' % (name, format), destination)


def strip_file(filename, outfile=None):
    lines = open(filename, 'r').readlines()
    out = filename if outfile is None else outfile
    with open(out, 'w') as fout:
        pruning = False
        for line in lines:
            if "BEGIN STRIP" in line:
                pruning = True
                continue
            elif "END STRIP" in line:
                pruning = False
                continue
            if not pruning:
                fout.write(line.replace("// STUDENT", ""))


def strip_directory(path):
    for file in os.listdir(path):
        strip_file(os.path.join(path, file))


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

            create_task_public(folder)
            archive_id = create_archive(os.path.join(folder, 'public'), module, exo)
            generate_task_yaml(folder, exo, archive_id)
            generate_task_test(folder, module, exo)
            copy_run_file(folder)
            copy_libs(folder)
            copy_exercise(folder, module, exo)


def generate_task_yaml(path, exercise, archive_id):
    with open(os.path.join(templates_dir, 'task.yaml.tpl'), 'r') as f:
        tpl = ''.join(f.readlines())
    with open(os.path.join(path, 'task.yaml'), 'w') as f:
        f.write(tpl.format(COURSE_NAME, exercise[1], archive_id, exercise[0]))


def create_task_public(path):
    _safe_mkdir(os.path.join(path, 'public'), delete=True)


def generate_task_test(path, module, exercise):
    in_name = exercise[1] + 'Test.java'
    out_name = exercise[1] + 'TestInginious.java'
    outfile = open(os.path.join(path, out_name), 'w')
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


def copy_run_file(path):
    run_file = 'run'
    source = os.path.join(templates_dir, run_file)
    dest = os.path.join(path, run_file + '.py')
    shutil.copyfile(source, dest)


def copy_libs(path):
    shutil.copytree(os.path.join(templates_dir, 'libs'),
                    os.path.join(path, 'libs'))


def copy_exercise(path, module, exercise):
    source = os.path.join(source_dir, module, exercise[1] + '.java')
    dest = os.path.join(path, exercise[1] + '.java')
    shutil.copyfile(source, dest)


def create_archive(path, module, exercise):
    temp_dir_path = os.path.join(path, exercise[1])
    _safe_mkdir(temp_dir_path, True)
    shutil.copyfile(os.path.join(script_dir, 'pom.xml'), os.path.join(temp_dir_path, 'pom.xml'))

    copy_libs(temp_dir_path)

    temp_src_dir = os.path.join(temp_dir_path, 'src')
    _safe_mkdir(temp_src_dir, True)
    temp_main_dir = os.path.join(temp_src_dir, 'main')
    _safe_mkdir(temp_main_dir, True)
    temp_test_folder = os.path.join(temp_src_dir, 'test')
    _safe_mkdir(temp_test_folder, True)

    src_file = exercise[1] + '.java'
    test_file = exercise[1] + 'Test.java'
    shutil.copyfile(os.path.join(source_dir, module, src_file),
                    os.path.join(temp_main_dir, src_file))
    shutil.copyfile(os.path.join(test_dir, module, test_file),
                    os.path.join(temp_test_folder, test_file))

    archive_id = ''.join(random.choice(string.ascii_letters) for _ in range(20))
    archive_id = f'{exercise[1]}-' + archive_id + '.zip'
    archive_path = os.path.join(path, archive_id)

    strip_directory(temp_main_dir)
    strip_directory(temp_test_folder)

    _make_archive(temp_dir_path, archive_path)
    shutil.rmtree(temp_dir_path)

    return archive_id


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
