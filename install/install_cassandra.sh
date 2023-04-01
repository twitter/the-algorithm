#!/bin/bash
# The contents of this file are subject to the Common Public Attribution
# License Version 1.0. (the "License"); you may not use this file except in
# compliance with the License. You may obtain a copy of the License at
# http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
# License Version 1.1, but Sections 14 and 15 have been added to cover use of
# software over a computer network and provide for limited attribution for the
# Original Developer. In addition, Exhibit A has been modified to be consistent
# with Exhibit B.
#
# Software distributed under the License is distributed on an "AS IS" basis,
# WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
# the specific language governing rights and limitations under the License.
#
# The Original Code is reddit.
#
# The Original Developer is the Initial Developer.  The Initial Developer of
# the Original Code is reddit Inc.
#
# All portions of the code written by reddit are Copyright (c) 2006-2015 reddit
# Inc. All Rights Reserved.
###############################################################################

# load configuration
RUNDIR=$(dirname $0)
source $RUNDIR/install.cfg

if [ ! -e $CASSANDRA_SOURCES_LIST ]; then
    echo "Cassandra repo not added.  Running `install_apt.sh`"
    $RUNDIR/install_apt.sh
fi

# install cassandra
sudo apt-get install $APTITUDE_OPTIONS cassandra=1.2.19

# we don't want to upgrade to C* 2.0 yet, so we'll put it on hold
apt-mark hold cassandra || true

# cassandra doesn't auto-start after install
sudo service cassandra start

# check each port for connectivity
echo "Waiting for cassandra to be available..."
while ! nc -vz localhost 9160; do
    sleep 1
done
