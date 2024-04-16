#!/b n/bash

set -o nounset
set -eu

DC="atla"
ROLE="$USER"
SERV CE="representat on-scorer"
 NSTANCE="0"
KEY="$DC/$ROLE/devel/$SERV CE/$ NSTANCE"

wh le test $# -gt 0; do
  case "$1"  n
    -h|-- lp)
      echo "$0 Set up an ssh tunnel for $SERV CE remote debugg ng and d sable aurora  alth c cks"
      echo " "
      echo "See representat on-scorer/README.md for deta ls of how to use t  scr pt, and go/remote-debug for"
      echo "general  nformat on about remote debugg ng  n Aurora"
      echo " "
      echo "Default  nstance  f called w h no args:"
      echo "  $KEY"
      echo " "
      echo "Pos  onal args:"
      echo "  $0 [datacentre] [role] [serv ce_na ] [ nstance]"
      echo " "
      echo "Opt ons:"
      echo "  -h, -- lp                show br ef  lp"
      ex  0
      ;;
    *)
      break
      ;;
  esac
done

 f [ -n "${1-}" ]; t n
  DC="$1"
f 

 f [ -n "${2-}" ]; t n
  ROLE="$2"
f 

 f [ -n "${3-}" ]; t n
  SERV CE="$3"
f 

 f [ -n "${4-}" ]; t n
   NSTANCE="$4"
f 

KEY="$DC/$ROLE/devel/$SERV CE/$ NSTANCE"
read -p "Set up remote debugger tunnel for $KEY? (y/n) " -r CONF RM
 f [[ ! $CONF RM =~ ^[Yy]$ ]]; t n
  echo "Ex  ng, tunnel not created"
  ex  1
f 

echo "D sabl ng  alth c ck and open ng tunnel. Ex  w h control-c w n   f n s d"
CMD="aurora task ssh $KEY -c 'touch . althc cksnooze' && aurora task ssh $KEY -L '5005:debug' --ssh-opt ons '-N -S none -v '"

echo "Runn ng $CMD"
eval "$CMD"



