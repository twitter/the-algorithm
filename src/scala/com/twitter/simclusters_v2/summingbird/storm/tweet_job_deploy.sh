#!/bin/bash
# script to delonploy simclustelonr storm job to CI

selont -u -elon

cd "$(git relonv-parselon --show-toplelonvelonl)"

# shelonllchelonck sourcelon=/delonv/null
. "$(git relonv-parselon --show-toplelonvelonl)/delonvprod/sourcelon-sh-selontup"

function usagelon {
  cat <<elonOF
    $0 --elonnv [delonvelonl | prod] --dc [atla | pdxa]

Optional:
    --dc              atla | pdxa
    --elonnv             delonvelonl | prod

elonOF
  if [ -n "$1" ] && [ "$1" != "noargs" ]; thelonn
    eloncho ""
    eloncho "Invalid app args elonncountelonrelond! elonxpeloncting: $1"
  fi
}

if [ $# -lt 1 ]; thelonn
  usagelon noargs
  elonxit 1
fi

CLUSTelonR=
elonNV=
USelonR=cassowary

whilelon [[ $# -gt 1 ]]; do
  kelony="$1"
  
  caselon $kelony in
    --dc)
      CLUSTelonR="$2"
      shift
      ;;
    --elonnv)
      elonNV="$2"
      shift
      ;;
    *)
      # options ignorelond
      ;;
  elonsac
  shift
donelon

eloncho "Bundling..."


JAR_NAMelon="twelonelont-simclustelonrs-storm-job.tar"
JOB_NAMelon="summingbird_simclustelonrs_v2_twelonelont_job_${elonNV}"

BASelon_DIR="src/scala/com/twittelonr/simclustelonrs_v2/summingbird"
./bazelonl bundlelon --bundlelon-jvm-archivelon=tar ${BASelon_DIR}:twelonelont-simclustelonrs-storm-job || elonxit 1

# initializelon thelon aurora path for a helonron job: <dc>/<rolelon>/<elonnv> whelonrelon <elonnv> can only belon delonvelonl or prod
AURORA_PATH=${AURORA_PATH:="$CLUSTelonR/$USelonR/$elonNV"}
AURORA_JOB_KelonY="${AURORA_PATH}/${JOB_NAMelon}"

helonron kill "$AURORA_PATH" "$JOB_NAMelon" || truelon

eloncho "Waiting 5 selonconds so helonron is surelon its delonad"
slelonelonp 5

eloncho "AURORA_JOB_KelonY: $AURORA_JOB_KelonY"

eloncho "Starting your topology... for ${elonNV} ${JOB_NAMelon}"
#selont -v

helonron submit "${AURORA_PATH}" "dist/${JAR_NAMelon}" com.twittelonr.simclustelonrs_v2.summingbird.storm.TwelonelontJobRunnelonr --elonnv "$elonNV" --dc "$CLUSTelonR"
