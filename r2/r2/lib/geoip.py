#!/usr/bin/python
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

import datetime
import httplib
import json
import os
import socket
import urllib2

from pylons import app_globals as g

from r2.lib.sgm import sgm
from r2.lib.utils import in_chunks, tup

# If the geoip service has nginx in front of it there is a default limit of 8kb:
#   http://wiki.nginx.org/NginxHttpCoreModule#large_client_header_buffers
# >>> len('GET /geoip/' + '+'.join(['255.255.255.255'] * 500) + ' HTTP/1.1')
# 8019
MAX_IPS_PER_GROUP = 500

GEOIP_CACHE_TIME = datetime.timedelta(days=7).total_seconds()

def _location_by_ips(ips):
    if not hasattr(g, 'geoip_location'):
        g.log.warning("g.geoip_location not set. skipping GeoIP lookup.")
        return {}

    ret = {}
    for batch in in_chunks(ips, MAX_IPS_PER_GROUP):
        ip_string = '+'.join(batch)
        url = os.path.join(g.geoip_location, 'geoip', ip_string)

        try:
            response = urllib2.urlopen(url=url, timeout=3)
            json_data = response.read()
        except (urllib2.URLError, httplib.HTTPException, socket.error) as e:
            g.log.warning("Failed to fetch GeoIP information: %r" % e)
            continue

        try:
            ret.update(json.loads(json_data))
        except ValueError, e:
            g.log.warning("Invalid JSON response for GeoIP lookup: %r" % e)
            continue
    return ret


def _organization_by_ips(ips):
    if not hasattr(g, 'geoip_location'):
        g.log.warning("g.geoip_location not set. skipping GeoIP lookup.")
        return {}

    ip_string = '+'.join(set(ips))
    url = os.path.join(g.geoip_location, 'org', ip_string)

    try:
        response = urllib2.urlopen(url=url, timeout=3)
        json_data = response.read()
    except urllib2.URLError, e:
        g.log.warning("Failed to fetch GeoIP information: %r" % e)
        return {}

    try:
        return json.loads(json_data)
    except ValueError, e:
        g.log.warning("Invalid JSON response for GeoIP lookup: %r" % e)
        return {}


def location_by_ips(ips):
    ips, is_single = tup(ips, ret_is_single=True)
    location_by_ip = sgm(
        cache=g.gencache,
        keys=ips,
        miss_fn=_location_by_ips,
        prefix='geoip:loc_',
        time=GEOIP_CACHE_TIME,
        ignore_set_errors=True,
    )
    if is_single and location_by_ip:
        return location_by_ip[ips[0]]
    else:
        return location_by_ip


def organization_by_ips(ips):
    ips, is_single = tup(ips, ret_is_single=True)
    organization_by_ip = sgm(
        cache=g.gencache,
        keys=ips,
        miss_fn=_organization_by_ips,
        prefix='geoip:org_',
        time=GEOIP_CACHE_TIME,
        ignore_set_errors=True,
    )
    if is_single and organization_by_ip:
        return organization_by_ip[ips[0]]
    else:
        return organization_by_ip


def get_request_location(request, context):
    """Determine country of origin of the `request` for the given `context`

    This is done by:
     * checking the CDN headers for country of origin if set
     * falling back on geocoding request.ip address against the geocoder service
    The resulting location is memoized on context on `context.location`

    request, context: Should be pylons.request & pylons.c respectively;
    """
    if context.location != '':
        # unset context attributes have the value ''
        return context.location

    context.location = None

    if getattr(request, 'via_cdn', False):
        g.stats.simple_event('geoip.cdn_request')
        cdn_geoinfo = g.cdn_provider.get_client_location(request.environ)
        if cdn_geoinfo:
            context.location = cdn_geoinfo
    elif getattr(request, 'ip', None):
        g.stats.simple_event('geoip.non_cdn_request')
        timer = g.stats.get_timer("providers.geoip.location_by_ips")
        timer.start()
        location = location_by_ips(request.ip)
        if location:
            context.location = location.get('country_code', None)
        timer.stop()

    return context.location
