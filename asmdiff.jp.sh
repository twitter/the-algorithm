#!/bin/bash

OBJDUMP="mips-linux-gnu-objdump -D -z -bbinary -mmips -EB"
OPTIONS="--start-address=$(($1)) --stop-address=$(($2))"
$OBJDUMP $OPTIONS baserom.jp.z64 > baserom.jp.dump
$OBJDUMP $OPTIONS build/jp/sm64.jp.z64 > sm64.jp.dump
diff baserom.jp.dump sm64.jp.dump | colordiff
