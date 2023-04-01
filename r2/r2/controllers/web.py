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

import hashlib
import hmac
import json
import re

from pylons import request, response
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _

from r2.controllers.reddit_base import RedditController, abort_with_error
from r2.lib.base import abort
from r2.lib.cache_poisoning import make_poisoning_report_mac
from r2.lib.csrf import csrf_exempt
from r2.lib.utils import constant_time_compare, UrlParser, is_subdomain
from r2.lib.validator import (
    nop,
    validate,
    VFloat,
    VInt,
    VOneOf,
    VPrintable,
    VRatelimit,
    VValidatedJSON,
)


class WebLogController(RedditController):
    on_validation_error = staticmethod(abort_with_error)

    @csrf_exempt
    @validate(
        VRatelimit(rate_user=False, rate_ip=True, prefix='rate_weblog_'),
        level=VOneOf('level', ('error',)),
        logs=VValidatedJSON('logs',
            VValidatedJSON.ArrayOf(VValidatedJSON.PartialObject({
                'msg': VPrintable('msg', max_length=256),
                'url': VPrintable('url', max_length=256),
                'tag': VPrintable('tag', max_length=32),
            }))
        ),
    )
    def POST_message(self, level, logs):
        # Whitelist tags to keep the frontend from creating too many keys in statsd
        valid_frontend_log_tags = {
            'unknown',
            'reddit-config-migrate-error',
        }

        # prevent simple CSRF by requiring a custom header
        if not request.headers.get('X-Loggit'):
            abort(403)

        uid = c.user._id if c.user_is_loggedin else '-'

        # only accept a maximum of 3 entries per request
        for log in logs[:3]:
            if 'msg' not in log or 'url' not in log:
                continue

            tag = 'unknown'

            if log.get('tag') in valid_frontend_log_tags:
                tag = log['tag']

            g.stats.simple_event('frontend.error.' + tag)

            g.log.warning('[web frontend] %s: %s | U: %s FP: %s UA: %s',
                          level, log['msg'], uid, log['url'],
                          request.user_agent)

        VRatelimit.ratelimit(rate_user=False, rate_ip=True,
                             prefix="rate_weblog_", seconds=10)

    def OPTIONS_report_cache_poisoning(self):
        """Send CORS headers for cache poisoning reports."""
        if "Origin" not in request.headers:
            return
        origin = request.headers["Origin"]
        parsed_origin = UrlParser(origin)
        if not is_subdomain(parsed_origin.hostname, g.domain):
            return
        response.headers["Access-Control-Allow-Origin"] = origin
        response.headers["Access-Control-Allow-Methods"] = "POST"
        response.headers["Access-Control-Allow-Headers"] = \
            "Authorization, X-Loggit, "
        response.headers["Access-Control-Allow-Credentials"] = "false"
        response.headers['Access-Control-Expose-Headers'] = \
            self.COMMON_REDDIT_HEADERS

    @csrf_exempt
    @validate(
        VRatelimit(rate_user=False, rate_ip=True, prefix='rate_poison_'),
        report_mac=VPrintable('report_mac', 255),
        poisoner_name=VPrintable('poisoner_name', 255),
        poisoner_id=VInt('poisoner_id'),
        poisoner_canary=VPrintable('poisoner_canary', 2, min_length=2),
        victim_canary=VPrintable('victim_canary', 2, min_length=2),
        render_time=VInt('render_time'),
        route_name=VPrintable('route_name', 255),
        url=VPrintable('url', 2048),
        # To differentiate between web and mweb in the future
        source=VOneOf('source', ('web', 'mweb')),
        cache_policy=VOneOf('cache_policy',
            ('loggedin_www', 'loggedin_www_new', 'loggedin_mweb')
        ),
        # JSON-encoded response headers from when our script re-requested
        # the poisoned page
        resp_headers=nop('resp_headers'),
    )
    def POST_report_cache_poisoning(
            self,
            report_mac,
            poisoner_name,
            poisoner_id,
            poisoner_canary,
            victim_canary,
            render_time,
            route_name,
            url,
            source,
            cache_policy,
            resp_headers,
    ):
        """Report an instance of cache poisoning and its details"""

        self.OPTIONS_report_cache_poisoning()

        if c.errors:
            abort(400)

        # prevent simple CSRF by requiring a custom header
        if not request.headers.get('X-Loggit'):
            abort(403)

        # Eh? Why are you reporting this if the canaries are the same?
        if poisoner_canary == victim_canary:
            abort(400)

        expected_mac = make_poisoning_report_mac(
            poisoner_canary=poisoner_canary,
            poisoner_name=poisoner_name,
            poisoner_id=poisoner_id,
            cache_policy=cache_policy,
            source=source,
            route_name=route_name,
        )
        if not constant_time_compare(report_mac, expected_mac):
            abort(403)

        if resp_headers:
            try:
                resp_headers = json.loads(resp_headers)
                # Verify this is a JSON map of `header_name => [value, ...]`
                if not isinstance(resp_headers, dict):
                    abort(400)
                for hdr_name, hdr_vals in resp_headers.iteritems():
                    if not isinstance(hdr_name, basestring):
                        abort(400)
                    if not all(isinstance(h, basestring) for h in hdr_vals):
                        abort(400)
            except ValueError:
                abort(400)

        if not resp_headers:
            resp_headers = {}

        poison_info = dict(
            poisoner_name=poisoner_name,
            poisoner_id=str(poisoner_id),
            # Convert the JS timestamp to a standard one
            render_time=render_time * 1000,
            route_name=route_name,
            url=url,
            source=source,
            cache_policy=cache_policy,
            resp_headers=resp_headers,
        )

        # For immediate feedback when tracking the effects of caching changes
        g.stats.simple_event("cache.poisoning.%s.%s" % (source, cache_policy))
        # For longer-term diagnosing of caching issues
        g.events.cache_poisoning_event(poison_info, request=request, context=c)

        VRatelimit.ratelimit(rate_ip=True, prefix="rate_poison_", seconds=10)

        return self.api_wrapper({})
