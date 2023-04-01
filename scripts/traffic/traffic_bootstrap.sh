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

TRAFFIC_SRC_DIR=$1
TRAFFIC_INSTALL_DIR=$HADOOP_HOME/traffic

# Build traffic regexes
mkdir $TRAFFIC_INSTALL_DIR
hadoop dfs -copyToLocal $TRAFFIC_SRC_DIR/* $TRAFFIC_INSTALL_DIR
cd $TRAFFIC_INSTALL_DIR
make

# Export userinfo secret
TARGET=$HADOOP_HOME/.pam_environment
TRACKING_SECRET=$2
echo "TRACKING_SECRET=$TRACKING_SECRET" >> $TARGET
