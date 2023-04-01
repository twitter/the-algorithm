#!/bin/bash
###############################################################################
# reddit dev environment installer
# --------------------------------
# This script installs a reddit stack suitable for development. DO NOT run this
# on a system that you use for other purposes as it might delete important
# files, truncate your databases, and otherwise do mean things to you.
#
# By default, this script will install the reddit code in the current user's
# home directory and all of its dependencies (including libraries and database
# servers) at the system level. The installed reddit will expect to be visited
# on the domain "reddit.local" unless specified otherwise.  Configuring name
# resolution for the domain is expected to be done outside the installed
# environment (e.g. in your host machine's /etc/hosts file) and is not
# something this script handles.
#
# Several configuration options (listed in the "Configuration" section below)
# are overridable with environment variables. e.g.
#
#    sudo REDDIT_DOMAIN=example.com ./install/reddit.sh
#
###############################################################################
set -e

if [[ $EUID -ne 0 ]]; then
    echo "ERROR: Must be run with root privileges."
    exit 1
fi

# load configuration
RUNDIR=$(dirname $0)
SCRIPTDIR="$RUNDIR/install"

# the canonical source of all installers
GITREPO="https://raw.github.com/reddit/reddit/master/install"
NEEDED=(
    "done.sh"
    "install_apt.sh"
    "install_cassandra.sh"
    "install_services.sh"
    "install_zookeeper.sh"
    "reddit.sh"
    "setup_cassandra.sh"
    "setup_mcrouter.sh"
    "setup_postgres.sh"
    "setup_rabbitmq.sh"
    "travis.sh"
)

MISSING=""
for item in ${NEEDED[*]}; do
    if [ ! -x $SCRIPTDIR/$item ]; then
        MISSING="1"
        break
    fi
done

if [ ! -e $SCRIPTDIR/install.cfg ]; then
    NEEDED+=("install.cfg")
    MISSING="1"
fi


function important() {
    echo -e "\033[31m${1}\033[0m"
}

if [ "$MISSING" != "" ]; then
    important "It looks like you're installing without a local repo.  No problem!"
    important "We're going to grab the scripts we need and show you where you can"
    important "edit the config to suit your environment."

    mkdir -p $SCRIPTDIR
    pushd $SCRIPTDIR > /dev/null
    for item in ${NEEDED[*]}; do
        echo "Grabbing '${item}'..."
        wget -q $GITREPO/$item
        chmod +x $item
    done
    popd > /dev/null

    echo "Done!"
fi

echo "#######################################################################"
echo "# Base configuration:"
echo "#######################################################################"
source $SCRIPTDIR/install.cfg
set +x

echo
important "Before proceeding, make sure that these look reasonable.  If not,"
important "you can either edit install/install.cfg or set overrides when running"
important "(they will be respected)."
echo
important "Seriously, if this is your first time installing, stop here and read"
important "the script (install/reddit.sh) and that config. It's got some helpful"
important "information that can prevent common issues."
echo
important "Resolving to the appropriate domain name is beyond the scope of this document,"
important "but the easiest thing is probably editing /etc/hosts on the host machine."
echo
read -er -n1 -p "proceed? [Y/n]" response
if [[ $response =~ ^[Yy]$ || $response == "" ]]; then
    echo "Excellent. Here we go!"
    $SCRIPTDIR/reddit.sh
fi
