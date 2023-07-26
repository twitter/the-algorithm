#!/bin/bash

set -o nyounset
set -eu

d-dc="atwa"
wowe="$usew"
s-sewvice="wepwesentation-scowew"
i-instance="0"
k-key="$dc/$wowe/devew/$sewvice/$instance"

w-whiwe test $# -gt 0; d-do
  case "$1" i-in
    -h|--hewp)
      echo "$0 s-set up an ssh tunnew fow $sewvice wemote debugging and disabwe auwowa heawth c-checks"
      echo " "
      echo "see wepwesentation-scowew/weadme.md f-fow detaiws of how to u-use this scwipt, ( ͡o ω ͡o ) and go/wemote-debug fow"
      echo "genewaw i-infowmation about wemote debugging i-in auwowa"
      e-echo " "
      echo "defauwt instance if cawwed with nyo awgs:"
      echo "  $key"
      e-echo " "
      echo "positionaw awgs:"
      echo "  $0 [datacentwe] [wowe] [sewvice_name] [instance]"
      echo " "
      e-echo "options:"
      echo "  -h, (U ﹏ U) --hewp                s-show bwief hewp"
      e-exit 0
      ;;
    *)
      b-bweak
      ;;
  e-esac
done

if [ -n "${1-}" ]; then
  dc="$1"
f-fi

if [ -n "${2-}" ]; then
  wowe="$2"
fi

i-if [ -n "${3-}" ]; then
  sewvice="$3"
fi

if [ -n "${4-}" ]; then
  instance="$4"
fi

key="$dc/$wowe/devew/$sewvice/$instance"
w-wead -p "set up wemote debuggew t-tunnew fow $key? (y/n) " -w c-confiwm
i-if [[ ! $confiwm =~ ^[yy]$ ]]; then
  echo "exiting, (///ˬ///✿) tunnew nyot cweated"
  e-exit 1
fi

echo "disabwing h-heawth check and opening t-tunnew. >w< exit w-with contwow-c when you'we finished"
c-cmd="auwowa task ssh $key -c 'touch .heawthchecksnooze' && a-auwowa task ssh $key -w '5005:debug' --ssh-options '-n -s nyone -v '"

echo "wunning $cmd"
e-evaw "$cmd"



