#!/bin/python3
import importlib.util


# Dynamically load modules we need
# Credits to https://stackoverflow.com/a/67692/6149867
# And for the explanation : http://www.blog.pythonlibrary.org/2016/05/27/python-201-an-intro-to-importlib/
def dynamically_load_module(module, path):
    spec = importlib.util.spec_from_file_location(module, path)
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


# Our import for common function
helper, feedback = [
    dynamically_load_module(mod, "/course/common/{}.py".format(mod))
    for mod in ["helper", "feedback"]
]
# helper = dynamically_load_module("helper", "/course/common/helper.py")
# feedback = dynamically_load_module("feedback", "/course/common/feedback.py")

print(helper)
helper.librairies()