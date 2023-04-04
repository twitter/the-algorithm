#!/usr/bin/env bash
shopt -s globstar
if (( $# > 0 )); then
    printf "formatting file(s) $*"
    echo
    clang-format -i -style=file "$@"
    echo done.
    exit
fi
echo formatting...
clang-format -i -style=file src/**/*.c # src
clang-format -i -style=file lib/src/*.c # libultra
clang-format -i -style=file enhancements/*.inc.c # enhancements
echo done.
