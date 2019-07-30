#!/usr/bin/env python3
import os
import shutil

folder_dir = 'LEPL1402'
os.mkdir(folder_dir)

items = [item.name
         for item in os.scandir('.')
         if item.is_dir() and item.name != folder_dir]

# Copy tasks to create folder
for item in items:
    os.mkdir(os.path.join(item, 'test'))
    shutil.copytree('./' + item, "{}/".format(folder_dir) + item)

# Copy course.yaml
shutil.copy2('./course.yaml', "{}/course.yaml".format(folder_dir))