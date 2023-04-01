#!/bin/bash

set -x
set -e

RELNAME=$1

cat <<DUMP_REL | ./run-query.sh
SET enable_bitmapscan=false;

\\copy(SELECT rel_id, thing1_id, thing2_id, name, EXTRACT(epoch FROM date) FROM reddit_rel_$RELNAME) to 'input/$RELNAME.dump';
DUMP_REL

cat <<DUMP_RELDATA | ./run-query.sh
SET enable_bitmapscan=false;

\\copy (SELECT thing_id, key, value FROM reddit_data_rel_$RELNAME WHERE key = 'new') to 'input/$RELNAME-data.dump';
DUMP_RELDATA
