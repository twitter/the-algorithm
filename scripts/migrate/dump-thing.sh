#!/bin/bash

set -x
set -e

THING=$1

cat <<DUMP_THING | ./run-query.sh
SET enable_bitmapscan=false;

\\copy (SELECT thing_id, ups, downs, deleted, spam, EXTRACT(epoch FROM date) FROM reddit_thing_$THING) to 'input/$THING.dump';
DUMP_THING

cat <<DUMP_THINGDATA | ./run-query.sh
SET enable_bitmapscan=false;

\\copy (SELECT thing_id, key, value FROM reddit_data_$THING WHERE key IN ('author_id', 'reported', 'sr_id', 'url', 'verdict', 'link_id')) to 'input/$THING-data.dump';
DUMP_THINGDATA
