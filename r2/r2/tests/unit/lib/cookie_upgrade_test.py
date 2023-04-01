#!/usr/bin/env python
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

import uuid

import datetime as dt

from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons import request

from r2.lib.cookies import Cookies, Cookie, upgrade_cookie_security, NEVER
from r2.models import Account, bcrypt_password, COOKIE_TIMESTAMP_FORMAT
from r2.tests import RedditTestCase


class TestCookieUpgrade(RedditTestCase):

    def setUp(self):
        name = "unit_tester_%s" % uuid.uuid4().hex
        self._password = uuid.uuid4().hex
        self._account = Account(
            name=name,
            password=bcrypt_password(self._password)
        )
        self._account._id = 1337

        c.cookies = Cookies()
        c.secure = True
        c.user_is_loggedin = True
        c.user = self._account
        c.oauth_user = None
        request.method = "POST"

    def tearDown(self):
        c.cookies.clear()
        c.user_is_loggedin = False
        c.user = None

    def _setSessionCookie(self, days_old=0):
        date = dt.datetime.now() - dt.timedelta(days=days_old)
        date_str = date.strftime(COOKIE_TIMESTAMP_FORMAT)
        session_cookie = self._account.make_cookie(date_str)
        c.cookies[g.login_cookie] = Cookie(
            value=session_cookie,
            dirty=False,
        )

    def test_no_upgrade_loggedout(self):
        # We might have a now-invalid session cookie, don't bother upgrading
        # it if it's not acceptable.
        c.user_is_loggedin = False
        c.user = None
        self._setSessionCookie(days_old=60)
        upgrade_cookie_security()
        self.assertFalse(c.cookies[g.login_cookie].dirty)

    def test_no_upgrade_http(self):
        c.secure = False
        self._setSessionCookie(days_old=60)
        upgrade_cookie_security()
        self.assertFalse(c.cookies[g.login_cookie].dirty)

    def test_no_upgrade_no_cookie(self):
        # Don't send back a cookie if we didn't even use cookie auth
        upgrade_cookie_security()
        self.assertFalse(g.login_cookie in c.cookies)

    def test_no_upgrade_oauth(self):
        # When g.domain == g.oauth_domain we might send a cookie even though
        # we're not using it for auth. Don't echo it back in responses.
        c.oauth_user = self._account
        self._setSessionCookie(days_old=60)
        upgrade_cookie_security()
        self.assertFalse(c.cookies[g.login_cookie].dirty)

    def test_no_upgrade_gets(self):
        request.method = "GET"
        self._setSessionCookie(days_old=60)
        upgrade_cookie_security()
        self.assertFalse(c.cookies[g.login_cookie].dirty)

    def test_no_upgrade_secure_session(self):
        self._setSessionCookie(days_old=60)
        c.cookies["secure_session"] = Cookie(value="1")
        upgrade_cookie_security()
        self.assertFalse(c.cookies[g.login_cookie].dirty)

    def test_upgrade_posts(self):
        self._setSessionCookie(days_old=60)
        upgrade_cookie_security()
        self.assertTrue(c.cookies[g.login_cookie].dirty)
        self.assertTrue(c.cookies[g.login_cookie].secure)

    def test_cookie_unchanged(self):
        self._setSessionCookie(days_old=60)
        old_session = c.cookies[g.login_cookie].value
        upgrade_cookie_security()
        self.assertTrue(c.cookies[g.login_cookie].dirty)
        self.assertEqual(old_session, c.cookies[g.login_cookie].value)

    def test_remember_old_session(self):
        self._setSessionCookie(days_old=60)
        upgrade_cookie_security()
        self.assertTrue(c.cookies[g.login_cookie].dirty)
        self.assertEqual(c.cookies[g.login_cookie].expires, NEVER)

    def test_dont_remember_recent_session(self):
        self._setSessionCookie(days_old=5)
        upgrade_cookie_security()
        self.assertTrue(c.cookies[g.login_cookie].dirty)
        self.assertNotEqual(c.cookies[g.login_cookie].expires, NEVER)
