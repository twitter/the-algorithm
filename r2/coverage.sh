#!/bin/bash
set -e

BASEDIR=$(readlink -f $(dirname $0))
cd $BASEDIR

VERSION=$(git rev-parse HEAD)
COVERDIR="$BASEDIR/build/cover-$VERSION"

function usage() {
    echo "Run unit tests and coverage reports on reddit codebase with optional"
    echo "http server to the report"
    echo
    echo "Usage: `basename $0` [options]";
    echo
    echo "  -h           show this message"
    echo "  -p \$PORT     run an simple http server on \$PORT to view results"
    echo "  -v           verbose mode (set -x)"
    echo
}

while getopts ":vhp:" opt; do
  case $opt in
    p) PORT="$OPTARG" ;;
    v) set -x ;;
    h)
      usage
      exit 0
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      usage
      exit 1
      ;;
    :)
      echo "Option -$OPTARG requires an argument." >&2
      exit 1
      ;;
  esac
done

nosetests \
    --with-coverage \
    --cover-html \
    --cover-html-dir=$COVERDIR \
    --cover-erase \
    --cover-package=r2

if [ "$PORT" != "" ]; then
    echo "Starting http server on :$PORT (^C to exit)"
    pushd $COVERDIR
    python -m SimpleHTTPServer $PORT || echo "Done."
    popd
fi
rm -r $COVERDIR
