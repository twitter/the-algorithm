#!/bin/bash

export CANARY_CHECK_ROLE="representation-scorer"
export CANARY_CHECK_NAME="representation-scorer"
export CANARY_CHECK_INSTANCES="0-19"

python3 relevance-platform/tools/canary_check.py "$@"

