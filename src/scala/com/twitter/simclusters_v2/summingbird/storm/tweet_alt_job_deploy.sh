#!/bin/bash
# script to deploy simcluster storm job to CI

set -u -e

cd "$(git rev-parse --show-toplevel)"

# shellcheck source=/dev/null
. "$(git rev-parse --show-toplevel)/devprod/source-sh-setup"

function usage {
  cat <<EOF
    $0 --env [devel | prod] --dc [atla | pdxa]

Optional:
    --dc              atla | pdxa
    --env             devel | prod

EOF
  if [ -n "$1" ] && [ "$1" != "noargs" ]; then
    echo ""
    echo "Invalid app args encountered! Expecting: $1"
  fi
}

if [ $# -lt 1 ]; then
  usage noargs
  exit 1
fi

CLUSTER=
ENV=
USER=cassowary

while [[ $# -gt 1 ]]; do
  key="$1"
  
  case $key in
    --dc)
      CLUSTER="$2"
      shift
      ;;
    --env)
      ENV="$2"
      shift
      ;;
    *)
      # options ignored
      ;;
  esac
  shift
done

echo "Bundling..."


JAR_NAME="tweet-simclusters-storm-job.tar"
JOB_NAME="summingbird_simclusters_v2_tweet_alt_job_${ENV}"

BASE_DIR="src/scala/com/twitter/simclusters_v2/summingbird"
./bazel bundle --bundle-jvm-archive=tar ${BASE_DIR}:tweet-simclusters-storm-job || exit 1

# initialize the aurora path for a heron job: <dc>/<role>/<env> where <env> can only be devel or prod 
AURORA_PATH=${AURORA_PATH:="$CLUSTER/$USER/$ENV"}
AURORA_JOB_KEY="${AURORA_PATH}/${JOB_NAME}"

heron kill "$AURORA_PATH" "$JOB_NAME" || true

echo "Waiting 5 seconds so heron is sure its dead"
sleep 5

echo "AURORA_JOB_KEY: $AURORA_JOB_KEY"

echo "Starting your topology... for ${ENV} ${JOB_NAME}"
#set -v

heron submit "${AURORA_PATH}" "dist/${JAR_NAME}" com.twitter.simclusters_v2.summingbird.storm.TweetJobRunner --env "$ENV" --dc "$CLUSTER" --alt "alt" --usingLogFavScore

