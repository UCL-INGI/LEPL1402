#!/usr/bin/env python3
import os
import shutil

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

# Prepare the $test$ folder for unit test of feedback
shutil.copytree('./$common', "$test$/$common")
shutil.copy2('./course.yaml', "$test$/course.yaml")
