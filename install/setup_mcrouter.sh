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

###############################################################################
# Configure mcrouter
###############################################################################
if [ ! -d /etc/mcrouter ]; then
    mkdir -p /etc/mcrouter
fi

if [ ! -f /etc/mcrouter/global.conf ]; then
    cat > /etc/mcrouter/global.conf <<MCROUTER
{
  // route all valid prefixes to the local memcached
  "pools": {
    "local": {
      "servers": [
        "127.0.0.1:11211",
      ],
      "protocol": "ascii",
      "keep_routing_prefix": false,
    },
  },
  "named_handles": [
    {
      "name": "local-pool",
      "type": "PoolRoute",
      "pool": "local",
    },
  ],
  "route": {
    "type": "PrefixSelectorRoute",
    "policies": {
      "rend:": "local-pool",
      "page:": "local-pool",
      "pane:": "local-pool",
      "sr:": "local-pool",
      "account:": "local-pool",
      "link:": "local-pool",
      "comment:": "local-pool",
      "message:": "local-pool",
      "campaign:": "local-pool",
      "award:": "local-pool",
      "trophy:": "local-pool",
      "flair:": "local-pool",
      "friend:": "local-pool",
      "inboxcomment:": "local-pool",
      "inboxmessage:": "local-pool",
      "reportlink:": "local-pool",
      "reportcomment:": "local-pool",
      "reportsr:": "local-pool",
      "reportmessage:": "local-pool",
      "modinbox:": "local-pool",
      "otp:": "local-pool",
      "captcha:": "local-pool",
      "queuedvote:": "local-pool",
      "geoip:": "local-pool",
      "geopromo:": "local-pool",
      "srpromos:": "local-pool",
      "rising:": "local-pool",
      "srid:": "local-pool",
      "defaultsrs:": "local-pool",
      "featuredsrs:": "local-pool",
      "query:": "local-pool",
      "rel:": "local-pool",
      "srmember:": "local-pool",
      "srmemberrel:": "local-pool",
    },
    "wildcard": {
      "type": "PoolRoute",
      "pool": "local",
    },
  },
}
MCROUTER
fi

# this file is sourced by the default mcrouter upstart config, see
# /etc/init/mcrouter.conf
cat > /etc/default/mcrouter <<MCROUTER_DEFAULT
MCROUTER_FLAGS="-f /etc/mcrouter/global.conf -L /var/log/mcrouter/mcrouter.log -p 5050 -R /././ --stats-root=/var/mcrouter/stats"
MCROUTER_DEFAULT

# set an upstart override so mcrouter starts when reddit starts
echo "start on networking or reddit-start" > /etc/init/mcrouter.override

# restart mcrouter to read the updated config
service mcrouter restart
