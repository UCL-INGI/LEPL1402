from xml.etree import ElementTree as ET


# https://docs.python.org/3/library/xml.etree.elementtree.html
# Extract the stats given by Jacoco into a list so that we can use that later
def extract_stats(path_to_xml_file):
    tree = ET.parse(path_to_xml_file)
    root = tree.getroot()
    return [
        {
            "covered": int(coverage_data.get("covered")),
            "missed": int(coverage_data.get("missed")),
            "type": coverage_data.get("type")
        }
        for coverage_data in root.findall("/report/counter")
    ]


# TODO A Tester
# Command to generate a exec file from JaCoCo
def exec_file_generation_command(file):
    return "{} –javaagent:{} {}".format(
        "java",
        "/course/common/jacocoagent.jar",
        file[:-5]
    )


# TODO A Tester
# Command to generate the result as a xml file from JaCoCo
# https://stackoverflow.com/questions/47717538/usage-of-jacococli-jar-with-multiple-classfiles
def generate_coverage_report(exec_file, classes_path, xml_output):
    return "{} -jar {} report {} {} --xml {}".format(
        "java",
        "/course/common/jacococli.jar",
        exec_file,
        ' '.join(["--classfiles {}".format(str(c)) for c in classes_path]),
        xml_output
    )
