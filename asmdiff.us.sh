#!/bin/bash

OBJDUMP="mips-linux-gnu-objdump -D -z -bbinary -mmips -EB"
OPTIONS="--start-address=$(($1)) --stop-address=$(($2))"
$OBJDUMP $OPTIONS baserom.us.z64 > baserom.us.dump
$OBJDUMP $OPTIONS build/us/sm64.us.z64 > sm64.us.dump
diff baserom.us.dump sm64.us.dump | colordiff

