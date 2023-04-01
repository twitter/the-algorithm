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

import pytz
from datetime import datetime

DATE_RFC822 = '%a, %d %b %Y %H:%M:%S %Z'
DATE_RFC850 = '%A, %d-%b-%y %H:%M:%S %Z'
DATE_ANSI = '%a %b %d %H:%M:%S %Y'


def read_http_date(date_str):
    try:
        date = datetime.strptime(date_str, DATE_RFC822)
    except ValueError:
        try:
            date = datetime.strptime(date_str, DATE_RFC850)
        except ValueError:
            try:
                date = datetime.strptime(date_str, DATE_ANSI)
            except ValueError:
                return None
    date = date.replace(tzinfo = pytz.timezone('GMT'))
    return date


def http_date_str(date):
    date = date.astimezone(pytz.timezone('GMT'))
    return date.strftime(DATE_RFC822)


def get_requests_resp_json(resp):
    """Kludge so we can use `requests` versions below or above 1.x"""
    if callable(resp.json):
        return resp.json()
    return resp.json
