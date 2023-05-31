#!/bin/bash

set -o nounset
set -eu

DC="atla"
ROLE="$USER"
SERVICE="representation-scorer"
INSTANCE="0"
KEY="$DC/$ROLE/devel/$SERVICE/$INSTANCE"

while test $# -gt 0; do
  case "$1" in
    -h|--help)
      echo "$0 Set up an ssh tunnel for $SERVICE remote debugging and disable aurora health checks"
      echo " "
      echo "See representation-scorer/README.md for details of how to use this script, and go/remote-debug for"
      echo "general information about remote debugging in Aurora"
      echo " "
      echo "Default instance if called with no args:"
      echo "  $KEY"
      echo " "
      echo "Positional args:"
      echo "  $0 [datacentre] [role] [service_name] [instance]"
      echo " "
      echo "Options:"
      echo "  -h, --help                show brief help"
      exit 0
      ;;
    *)
      break
      ;;
  esac
done

if [ -n "${1-}" ]; then
  DC="$1"
fi

if [ -n "${2-}" ]; then
  ROLE="$2"
fi

if [ -n "${3-}" ]; then
  SERVICE="$3"
fi

if [ -n "${4-}" ]; then
  INSTANCE="$4"
fi

KEY="$DC/$ROLE/devel/$SERVICE/$INSTANCE"
read -p "Set up remote debugger tunnel for $KEY? (y/n) " -r CONFIRM
if [[ ! $CONFIRM =~ ^[Yy]$ ]]; then
  echo "Exiting, tunnel not created"
  exit 1
fi

echo "Disabling health check and opening tunnel. Exit with control-c when you're finished"
CMD="aurora task ssh $KEY -c 'touch .healthchecksnooze' && aurora task ssh $KEY -L '5005:debug' --ssh-options '-N -S none -v '"

echo "Running $CMD"
eval "$CMD"



