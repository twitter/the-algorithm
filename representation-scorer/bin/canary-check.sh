#!/b n/bash

export CANARY_CHECK_ROLE="representat on-scorer"
export CANARY_CHECK_NAME="representat on-scorer"
export CANARY_CHECK_ NSTANCES="0-19"

python3 relevance-platform/tools/canary_c ck.py "$@"

