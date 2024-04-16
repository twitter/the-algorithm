#!/b n/bash
# scr pt to deploy s mclusters pers stent storm job to C 

set -u -e

cd "$(g  rev-parse --show-toplevel)"

# s llc ck s ce=/dev/null
. "$(g  rev-parse --show-toplevel)/devprod/s ce-sh-setup"

funct on usage {
  cat <<EOF
    $0 --env [devel | prod] --dc [atla | pdxa]

Opt onal:
    --dc              atla | pdxa
    --env             devel | prod

EOF
   f [ -n "$1" ] && [ "$1" != "noargs" ]; t n
    echo ""
    echo " nval d app args encountered! Expect ng: $1"
  f 
}

 f [ $# -lt 1 ]; t n
  usage noargs
  ex  1
f 

CLUSTER=
ENV=
USER=cassowary

wh le [[ $# -gt 1 ]]; do
  key="$1"
  
  case $key  n
    --dc)
      CLUSTER="$2"
      sh ft
      ;;
    --env)
      ENV="$2"
      sh ft
      ;;
    *)
      # opt ons  gnored
      ;;
  esac
  sh ft
done

echo "Bundl ng..."


JAR_NAME="pers stent-t et-s mclusters-storm-job.tar"
JOB_NAME="summ ngb rd_s mclusters_v2_pers stent_t et_job_${ENV}"

BASE_D R="src/scala/com/tw ter/s mclusters_v2/summ ngb rd"
./bazel bundle --bundle-jvm-arch ve=tar ${BASE_D R}:pers stent-t et-s mclusters-storm-job || ex  1

#  n  al ze t  aurora path for a  ron job: <dc>/<role>/<env> w re <env> can only be devel or prod 
AURORA_PATH=${AURORA_PATH:="$CLUSTER/$USER/$ENV"}
AURORA_JOB_KEY="${AURORA_PATH}/${JOB_NAME}"

 ron k ll "$AURORA_PATH" "$JOB_NAME" || true

echo "Wa  ng 5 seconds so  ron  s sure  s dead"
sleep 5

echo "AURORA_JOB_KEY: $AURORA_JOB_KEY"

echo "Start ng y  topology... for ${ENV} ${JOB_NAME}"
#set -v

 ron subm  "${AURORA_PATH}" "d st/${JAR_NAME}" com.tw ter.s mclusters_v2.summ ngb rd.storm.Pers stentT etJobRunner --env "$ENV" --dc "$CLUSTER"
