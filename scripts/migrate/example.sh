#!/bin/bash
# This is an example of how regenerating the query cache
# works. You will need to configure several things first:
#
# * configure postgres environment, see run-query.sh
# * prepare pig:
#     * make sure the environment is set up for pig
#     * rearrange the values in TypeIDs.java to match your
#       instance's IDs
#     * compile the pig UDFs (ant build in udfs/)
# * ensure the Cassandra JARs are on the CLASSPATH

# dump the relevant postgres tables to input/
./dump-all.sh

# process the postgres dumps in hadoop and generate
# output data suitable for cassandra
pig regenerate-query-cache.py

# for each column family we'll be writing to, generate
# sstables from the map-reduce output
for $cf in $(ls output/); do
    jython tuples_to_sstables.py $cf output/$cf/*/part*
done

# bulk-load the sstables into cassandra
sstableloader reddit/
