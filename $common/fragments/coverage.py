from xml.etree import ElementTree as ET
from fragments.constants import *
from itertools import chain
combine_list = chain.from_iterable


# https://docs.python.org/3/library/xml.etree.elementtree.html
# Extract the stats given by Jacoco into a list so that we can use that later
def extract_stats(xpaths, path_to_xml_file=JACOCO_RESULT_FILE):
    tree = ET.parse(path_to_xml_file)
    root = tree.getroot()
    return [
        {
            "covered": int(coverage_data.get("covered")),
            "missed": int(coverage_data.get("missed")),
            "type": coverage_data.get("type")
        }
        for coverage_data in
            combine_list([
                root.findall(xpath_rule)
                for xpath_rule
                in xpaths
            ])
    ]


# Command to generate the result as a xml file from JaCoCo
# https://stackoverflow.com/questions/47717538/usage-of-jacococli-jar-with-multiple-classfiles
def generate_coverage_report(exec_file=JACOCO_EXEC_FILE,
                             classes_path=JACOCO_CLASS_FILES,
                             xml_output=JACOCO_RESULT_FILE):
    return "{} -jar {} report {} {} --xml {}".format(
        "java",
        str(Path(LIBS_FOLDER) / "jacococli.jar"),
        exec_file,
        ' '.join(["--classfiles {}".format(str(c)) for c in classes_path]),
        xml_output
    )
