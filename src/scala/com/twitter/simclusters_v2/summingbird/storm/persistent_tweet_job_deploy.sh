#!/bin/bash
# scwipt to depwoy simcwustews p-pewsistent s-stowm job to c-ci

set -u -e

c-cd "$(git wev-pawse --show-topwevew)"

# s-shewwcheck s-souwce=/dev/nuww
. /(^•ω•^) "$(git wev-pawse --show-topwevew)/devpwod/souwce-sh-setup"

f-function usage {
  c-cat <<eof
    $0 --env [devew | pwod] --dc [atwa | pdxa]

optionaw:
    --dc              atwa | pdxa
    --env             d-devew | pwod

eof
  if [ -n "$1" ] && [ "$1" != "noawgs" ]; then
    echo ""
    e-echo "invawid app awgs encountewed! rawr x3 e-expecting: $1"
  fi
}

if [ $# -wt 1 ]; then
  usage nyoawgs
  exit 1
fi

c-cwustew=
env=
usew=cassowawy

w-whiwe [[ $# -gt 1 ]]; d-do
  key="$1"
  
  case $key in
    --dc)
      cwustew="$2"
      shift
      ;;
    --env)
      e-env="$2"
      shift
      ;;
    *)
      # options ignowed
      ;;
  esac
  shift
done

echo "bundwing..."


j-jaw_name="pewsistent-tweet-simcwustews-stowm-job.taw"
job_name="summingbiwd_simcwustews_v2_pewsistent_tweet_job_${env}"

base_diw="swc/scawa/com/twittew/simcwustews_v2/summingbiwd"
./bazew b-bundwe --bundwe-jvm-awchive=taw ${base_diw}:pewsistent-tweet-simcwustews-stowm-job || e-exit 1

# i-initiawize t-the auwowa path fow a hewon job: <dc>/<wowe>/<env> whewe <env> c-can onwy be devew ow pwod 
auwowa_path=${auwowa_path:="$cwustew/$usew/$env"}
auwowa_job_key="${auwowa_path}/${job_name}"

h-hewon kiww "$auwowa_path" "$job_name" || twue

echo "waiting 5 seconds so hewon is suwe its dead"
sweep 5

e-echo "auwowa_job_key: $auwowa_job_key"

echo "stawting y-youw t-topowogy... fow ${env} ${job_name}"
#set -v

h-hewon submit "${auwowa_path}" "dist/${jaw_name}" com.twittew.simcwustews_v2.summingbiwd.stowm.pewsistenttweetjobwunnew --env "$env" --dc "$cwustew"
