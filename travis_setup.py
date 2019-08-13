#!/usr/bin/env python3
import os
import shutil
from pathlib import Path

folder_dir = 'LEPL1402'
os.mkdir(folder_dir)

items = [item.name
         for item in os.scandir('.')
         if item.is_dir() and item.name not in {folder_dir, "$test"}]

# Copy tasks to create folder
for item in items:
    shutil.copytree('./' + item, "{}/".format(folder_dir) + item)
    os.mkdir(os.path.join("{}/{}/".format(folder_dir, item), 'test'))

# Copy course.yaml
shutil.copy2('./course.yaml', "{}/course.yaml".format(folder_dir))

# Set up some example tasks for Integration testing
for item in [item.name for item in Path("$test$").iterdir() if item.is_dir()]:
    shutil.copytree('$test$/' + item, "{}/".format(folder_dir) + item)
