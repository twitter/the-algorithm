#!/bin/sh

if (( RANDOM % 5 ))
then
    exit 0
else
    echo "Bad luck man, you'll need to retry"
    exit 1
fi
