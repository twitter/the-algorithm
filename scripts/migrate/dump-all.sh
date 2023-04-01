#!/bin/bash

set -x -e

RELS=savehide vote_account_link inbox_account_comment inbox_account_message moderatorinbox
THINGS=link comment message

for rel in $RELS; do
    ./dump-rel.sh $rel
done

for thing in $THINGS; do
    ./dump-thing.sh $thing
done
