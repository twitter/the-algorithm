#!/usr/bin/env bash
###############################################################################
# git diff style checker
# ----------------------
# This script runs a style check within our Drone setup, or within the
# `drone exec` runner.
#
# Since the codebase has a substantial body of non-conformant code, style
# checks are only ran on the diffs (compared to master). As a consequence of
# this, style checks also only run on non-master branches.
###############################################################################

if [[ ${CI_BRANCH} = "master" ]]; then
    echo "Skipping style checks on commit(s) to the master branch."
    exit 0
fi

if [[ ${CI_REPO:=} = "" ]]; then
    # This assumed to be `drone exec`.
    echo "Running style checks on staged local changes..."
    git diff --cached | pep8 --diff
else
    echo "Running style checks within Drone..."
    git fetch --no-tags --depth=10 origin master
    git diff origin/${CI_BRANCH} origin/master | pep8 --diff
fi
error_encountered=$?

if [[ ${error_encountered} = 1 ]]; then
    echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
    echo "pep8 issues found. reddit follows pep8: https://github.com/reddit/styleguide"
    echo "              Please commit a fix or ignore inline with: noqa"
    echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
    exit 1
fi

echo "Style checks passed. Good jerb!"
