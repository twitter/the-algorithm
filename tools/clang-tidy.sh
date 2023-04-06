#!/usr/bin/env bash

# Use clang-tidy to brace if statements and loops. This should be a feature of clang-format.
# The brace check is the only check being applied right now, 
# but other checks (like naming scheme) may be added at a later time. 

# '--fix-errors` due to generated text_strings.h as well as the enhancement inc.c files
TIDY_OPTS="-p . --fix --fix-errors" 
COMPILER_OPTS="-nostdinc -fno-builtin -std=gnu90 -Iinclude -Isrc -D_LANGUAGE_C"
VERSIONS="-DVERSION_US -DVERSION_JP -DVERSION_EU"

# run script from the root of the repository
cd "$( dirname $0 )" >/dev/null 2>&1; cd ../

if (( $# > 0 )); then
    printf "Tidy file(s) $*"
    echo
    for VER in ${VERSIONS}; do  
        echo "with compiler version flag ${VER}"
        clang-tidy ${TIDY_OPTS} "$@" -- ${COMPILER_OPTS} ${VER}
    done
    printf "Done tidying file(s) $*"
    echo
    exit
fi

echo "Tidying all C files for all versions. This will take a bit"
for VER in ${VERSIONS}; do  
        echo "Tidying for compiler version flag ${VER}"
        # Don't run clang-tidy on behaviors
        clang-tidy ${TIDY_OPTS} src/audio/*.c -- ${COMPILER_OPTS} ${VER}
        clang-tidy ${TIDY_OPTS} src/engine/*.c -- ${COMPILER_OPTS} ${VER}
        clang-tidy ${TIDY_OPTS} src/game/*.c -- ${COMPILER_OPTS} ${VER}
        clang-tidy ${TIDY_OPTS} src/goddard/*.c -- ${COMPILER_OPTS} ${VER}
        clang-tidy ${TIDY_OPTS} lib/src/*.c -- ${COMPILER_OPTS} ${VER}
        clang-tidy ${TIDY_OPTS} enhancements/*.inc.c -- ${COMPILER_OPTS} ${VER}
done
echo "Done tidying all C files. Hope you did something fun during the tidying"
