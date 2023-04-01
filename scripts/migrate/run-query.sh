#!/bin/bash

set -x
psql -h ${DB_HOST:-localhost} \
     -d ${DB_NAME:-reddit} \
     -U ${DB_USER:-reddit} \
     -p ${DB_PORT:-5432} \
     -F"\t" -A -t
