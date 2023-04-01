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

import urllib

from pylons import request
from pylons import app_globals as g

from r2.lib.configparse import ConfigValue
from r2.lib.providers.auth import AuthenticationProvider
from r2.lib.utils import constant_time_compare


class CookieAuthenticationProvider(AuthenticationProvider):
    """An authentication provider that uses standard HTTP cookies.

    """

    config = {
        ConfigValue.str: [
            "login_cookie",
        ],
    }

    def is_logout_allowed(self):
        return True

    def get_authenticated_account(self):
        from r2.models import Account, NotFound

        quoted_session_cookie = request.cookies.get(g.login_cookie)
        if not quoted_session_cookie:
            return None
        session_cookie = urllib.unquote(quoted_session_cookie)

        try:
            uid, timestr, hash = session_cookie.split(",")
            uid = int(uid)
        except:
            return None

        try:
            account = Account._byID(uid, data=True)
        except NotFound:
            return None

        expected_cookie = account.make_cookie(timestr)
        if not constant_time_compare(session_cookie, expected_cookie):
            return None
        return account
