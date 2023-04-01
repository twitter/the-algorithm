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

from datetime import datetime, timedelta

from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons import request

from r2.lib import utils
from r2.models import COOKIE_TIMESTAMP_FORMAT

NEVER = datetime(2037, 12, 31, 23, 59, 59)
DELETE = datetime(1970, 01, 01, 0, 0, 1)


class Cookies(dict):
    def add(self, name, value, *k, **kw):
        name = name.encode('utf-8')
        self[name] = Cookie(value, *k, **kw)


class Cookie(object):
    def __init__(self, value, expires=None, domain=None,
                 dirty=True, secure=None, httponly=False):
        self.value = value
        self.expires = expires
        self.dirty = dirty
        self.secure = secure
        self.httponly = httponly
        if domain:
            self.domain = domain
        else:
            self.domain = g.domain

    @staticmethod
    def classify(cookie_name):
        if cookie_name == g.login_cookie:
            return "session"
        elif cookie_name == g.admin_cookie:
            return "admin"
        elif cookie_name == "reddit_first":
            return "first"
        elif cookie_name == "over18":
            return "over18"
        elif cookie_name == "secure_session":
            return "secure_session"
        elif cookie_name.endswith("_last_thing"):
            return "last_thing"
        elif cookie_name.endswith("_options"):
            return "options"
        elif cookie_name.endswith("_recentclicks2"):
            return "clicks"
        elif cookie_name.startswith("__utm"):
            return "ga"
        elif cookie_name.startswith("beta_"):
            return "beta"
        else:
            return "other"

    def __repr__(self):
        return ("Cookie(value=%r, expires=%r, domain=%r, dirty=%r)"
                % (self.value, self.expires, self.domain, self.dirty))


# Cookies that might need the secure flag toggled
PRIVATE_USER_COOKIES = ["recentclicks2"]
PRIVATE_SESSION_COOKIES = [g.login_cookie, g.admin_cookie, "_options"]


def have_secure_session_cookie():
    cookie = c.cookies.get("secure_session", None)
    return cookie and cookie.value == "1"


def change_user_cookie_security(secure, remember):
    """Mark a user's cookies as either secure or insecure.

    (Un)set the secure flag on sensitive cookies, and add / remove
    the cookie marking the session as HTTPS-only
    """
    if secure:
        set_secure_session_cookie(remember)
    else:
        delete_secure_session_cookie()

    if not c.user_is_loggedin:
        return

    user_name = c.user.name
    securable = (PRIVATE_SESSION_COOKIES +
                 [user_name + "_" + c_name for c_name in PRIVATE_USER_COOKIES])
    for name, cookie in c.cookies.iteritems():
        if name in securable:
            cookie.secure = secure
            if name in PRIVATE_SESSION_COOKIES:
                if name != "_options":
                    cookie.httponly = True
                # TODO: need a way to tell if a session is supposed to last
                # forever. We don't get to see the expiry date of a cookie
                if remember and name == g.login_cookie:
                    cookie.expires = NEVER
            cookie.dirty = True


def set_secure_session_cookie(remember=False):
    expires = NEVER if remember else None
    c.cookies["secure_session"] = Cookie(
        value="1",
        httponly=True,
        expires=expires,
        secure=False,
    )


def delete_secure_session_cookie():
    c.cookies["secure_session"] = Cookie(
        value="",
        httponly=True,
        expires=DELETE,
    )


def upgrade_cookie_security():
    # We only upgrade on POSTs over HTTPS to prevent cookies from being cached
    # by bad proxies
    if not c.secure or request.method != "POST":
        return

    # There's likely not any cookies we need to upgrade
    if not c.user_is_loggedin or c.oauth_user or have_secure_session_cookie():
        return

    # If they authed using a feedhash they might not even have this cookie
    if g.login_cookie not in c.cookies:
        return

    sess_split = c.cookies[g.login_cookie].value.split(",")
    if len(sess_split) != 3:
        return

    # If the cookie's old enough, just pretend we know they had "remember me"
    # ticked.
    sess_start_time = datetime.strptime(sess_split[1], COOKIE_TIMESTAMP_FORMAT)
    rem = (datetime.now() - sess_start_time > timedelta(days=30))
    change_user_cookie_security(secure=True, remember=rem)
