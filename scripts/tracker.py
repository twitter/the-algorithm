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
This is a tiny Flask app used for a couple of self-serve ad tracking
mechanisms. The URLs it provides are:

/click

    Promoted links have their URL replaced with a /click URL by the JS
    (after a call to /fetch-trackers). Redirect to the actual URL after logging
    the click. This must be run in a place whose logs are stored for traffic
    analysis.

For convenience, the script can compile itself into a Zip archive suitable for
use on Amazon Elastic Beanstalk (and possibly other systems).

"""


import cStringIO
import os
import hashlib
import hmac
import time
import urllib
from urlparse import parse_qsl, urlparse, urlunparse

from ConfigParser import RawConfigParser
from wsgiref.handlers import format_date_time

from flask import Flask, request, json, make_response, abort, redirect


application = Flask(__name__)
REQUIRED_PACKAGES = [
    "flask",
]


class ApplicationConfig(object):
    """A thin wrapper around ConfigParser that remembers what we read.

    The remembered settings can then be written out to a minimal config file
    when building the Elastic Beanstalk zipfile.

    """
    def __init__(self):
        self.input = RawConfigParser()
        config_filename = os.environ.get("CONFIG", "production.ini")
        with open(config_filename) as f:
            self.input.readfp(f)
        self.output = RawConfigParser()

    def get(self, section, key):
        value = self.input.get(section, key)

        # remember that we needed this configuration value
        if (section.upper() != "DEFAULT" and
            not self.output.has_section(section)):
            self.output.add_section(section)
        self.output.set(section, key, value)

        return value

    def to_config(self):
        io = cStringIO.StringIO()
        self.output.write(io)
        return io.getvalue()


config = ApplicationConfig()
tracking_secret = config.get('DEFAULT', 'tracking_secret')
reddit_domain = config.get('DEFAULT', 'domain')
reddit_domain_prefix = config.get('DEFAULT', 'domain_prefix')


@application.route("/")
def healthcheck():
    return "I am healthy."


@application.route('/click')
def click_redirect():
    destination = request.args['url'].encode('utf-8')
    fullname = request.args['id'].encode('utf-8')
    observed_mac = request.args['hash']

    expected_hashable = ''.join((destination, fullname))
    expected_mac = hmac.new(
            tracking_secret, expected_hashable, hashlib.sha1).hexdigest()

    if not constant_time_compare(expected_mac, observed_mac):
        abort(403)

    # fix encoding in the query string of the destination
    u = urlparse(destination)
    if u.query:
        u = _fix_query_encoding(u)
        destination = u.geturl()

    return _redirect_nocache(destination)


@application.route('/event_redirect')
def event_redirect():
    destination = request.args['url'].encode('utf-8')

    # Parse and avoid open redirects
    netloc = "%s.%s" % (reddit_domain_prefix, reddit_domain)
    u = urlparse(destination)._replace(netloc=netloc, scheme="https")

    if u.query:
        u = _fix_query_encoding(u)
        destination = u.geturl()

    return _redirect_nocache(destination)


@application.route('/event_click')
def event_click():
    """Take in an evented request, append session data to payload, and redirect.

    This is only useful for situations in which we're navigating from a request
    that does not have session information - i.e. served from redditmedia.com.
    If we want to track a click and the user that did so from these pages,
    we need to identify the user before sending the payload.

    Note: If we add hmac validation, this will need verify and resign before
    redirecting. We can also probably drop a redirect here once we're not
    relying on log files for event tracking and have a proper events endpoint.
    """
    try:
        session_str = urllib.unquote(request.cookies.get('reddit_session', ''))
        user_id = int(session_str.split(',')[0])
    except ValueError:
        user_id = None

    args = request.args.to_dict()
    if user_id:
        payload = args.get('data').encode('utf-8')
        try:
            payload_json = json.loads(payload)
        except ValueError:
            # if we fail to load the JSON, continue on to the redirect to not
            # block the user - ETL can deal with/report the malformed data.
            pass
        else:
            payload_json['user_id'] = user_id
            args['data'] = json.dumps(payload_json)

    return _redirect_nocache('/event_redirect?%s' % urllib.urlencode(args))


def _fix_query_encoding(parse_result):
    "Fix encoding in the query string."
    query_params = parse_qsl(parse_result.query, keep_blank_values=True)

    # this effectively calls urllib.quote_plus on every query value
    return parse_result._replace(query=urllib.urlencode(query_params))


def _redirect_nocache(destination):
    now = format_date_time(time.time())
    response = redirect(destination)
    response.headers['Cache-control'] = 'no-cache'
    response.headers['Pragma'] = 'no-cache'
    response.headers['Date'] = now
    response.headers['Expires'] = now
    return response


# copied from r2.lib.utils
def constant_time_compare(actual, expected):
    """
    Returns True if the two strings are equal, False otherwise

    The time taken is dependent on the number of characters provided
    instead of the number of characters that match.
    """
    actual_len   = len(actual)
    expected_len = len(expected)
    result = actual_len ^ expected_len
    if expected_len > 0:
        for i in xrange(actual_len):
            result |= ord(actual[i]) ^ ord(expected[i % expected_len])
    return result == 0


if __name__ == "__main__":
    # package up for elastic beanstalk
    import zipfile

    with zipfile.ZipFile("/tmp/tracker.zip", "w", zipfile.ZIP_DEFLATED) as zip:
        zip.write(__file__, "application.py")
        zip.writestr("production.ini", config.to_config())
        zip.writestr("requirements.txt", "\n".join(REQUIRED_PACKAGES) + "\n")
