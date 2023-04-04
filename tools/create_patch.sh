#!/bin/sh
#
# create_patch.sh - Creates an enhancement patch based on the current Git changes
#

if [ "$#" -ne 1 ]
then
    echo "Usage: $0 patch_file"
    echo '    Creates a patch file based on the changes made to the current directory'
    exit 1
fi

# Make sure this is a git repository
if [ ! -d .git ]
then
    echo 'Error: The current directory is not a Git repository.'
    exit 1
fi

# 'git diff' is stupid and doesn't show new untracked files, so we must add them first.
git add .
# Generate the patch.
git diff -p --staged > "$1"
# Undo the 'git add'. 
git reset
