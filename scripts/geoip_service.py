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
"""
This is a tiny Flask app used for geoip lookups against a maxmind database.

If you are using this service be sure to set `geoip_location` in your ini file.

"""

import json

import GeoIP
from flask import Flask, make_response

application = Flask(__name__)

# SET THESE PATHS TO YOUR MAXMIND GEOIP LEGACY DATABASES
# http://dev.maxmind.com/geoip/legacy/geolite/
COUNTRY_DB_PATH = '/usr/share/GeoIP/GeoIP.dat'
CITY_DB_PATH = '/var/lib/GeoIP/GeoIPCity.dat'
ORG_DB_PATH = '/var/lib/GeoIP/GeoIPOrg.dat'


try:
    gc = GeoIP.open(COUNTRY_DB_PATH, GeoIP.GEOIP_MEMORY_CACHE)
except:
    gc = None

try:
    gi = GeoIP.open(CITY_DB_PATH, GeoIP.GEOIP_MEMORY_CACHE)
except:
    gi = None

try:
    go = GeoIP.open(ORG_DB_PATH, GeoIP.GEOIP_MEMORY_CACHE)
except:
    go = None


def json_response(result):
    json_output = json.dumps(result, ensure_ascii=False, encoding='iso-8859-1')
    response = make_response(json_output.encode('utf-8'), 200)
    response.headers['Content-Type'] = 'application/json; charset=utf-8'
    return response


@application.route('/geoip/<ips>')
def get_record(ips):
    if gi:
        result = {ip: gi.record_by_addr(ip) for ip in ips.split('+')}
    elif gc:
        result = {
            ip : {
                'country_code': gc.country_code_by_addr(ip),
                'country_name': gc.country_name_by_addr(ip),
            } for ip in ips.split('+')
        }
    else:
        result = {}

    return json_response(result)


@application.route('/org/<ips>')
def get_organizations(ips):
    if go:
        return json_response({ip: go.org_by_addr(ip) for ip in ips.split('+')})
    else:
        return json_response({})


if __name__ == "__main__":
    application.run()
