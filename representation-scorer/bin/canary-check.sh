#!/bin/bash

etwitterport CANARY_CHECK_ROLE="representation-scorer"
etwitterport CANARY_CHECK_NAME="representation-scorer"
etwitterport CANARY_CHECK_INSTANCES="0-19"

python3 relevance-platform/tools/canary_check.py "$@"

