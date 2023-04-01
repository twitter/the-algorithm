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

import json
import os

import pylibmc
from pylons import request, response
from pylons import app_globals as g
from pylons.controllers.util import abort

from r2.controllers.reddit_base import MinimalController
from r2.lib import promote, cache


class HealthController(MinimalController):
    def pre(self):
        pass

    def post(self):
        pass

    def GET_health(self):
        if os.path.exists("/var/opt/reddit/quiesce"):
            request.environ["usable_error_content"] = "No thanks, I'm full."
            abort(503)

        response.content_type = "application/json"
        return json.dumps(g.versions, sort_keys=True, indent=4)

    def GET_promohealth(self):
        response.content_type = "application/json"
        return json.dumps(promote.health_check())

    def GET_cachehealth(self):
        results = {}
        behaviors = {
            # Passed on to poll(2) in milliseconds
            "connect_timeout": 1000,
            # Passed on to setsockopt(2) in microseconds
            "receive_timeout": int(1e6),
            "send_timeout": int(1e6),
        }
        for server in cache._CACHE_SERVERS:
            try:
                if server.startswith("udp:"):
                    # libmemcached doesn't support UDP get/fetch operations
                    continue
                mc = pylibmc.Client([server], behaviors=behaviors)
                # it's ok that not all caches are mcrouter, we'll just ignore
                # the miss either way
                mc.get("__mcrouter__.version")
                results[server] = "OK"
            except pylibmc.Error as e:
                g.log.warning("Health check for %s FAILED: %s", server, e)
                results[server] = "FAILED %s" % e
        return json.dumps(results)
