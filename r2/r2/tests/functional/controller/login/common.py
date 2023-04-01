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
# All portions of the code written by reddit are Copyright (c) 2006-2016 reddit
# Inc. All Rights Reserved.
###############################################################################
import contextlib
import unittest
from mock import patch, MagicMock

from pylons import app_globals as g

from r2.lib.validator import VThrottledLogin, VUname, validator
from r2.models import Account, NotFound


class LoginRegBase(object):
    """Mixin for login-centered controller tests.

    This class is (purposely) not a test case that'll be picked up by nose
    but rather should be added as a mixin on a RedditControllerTestCase
    subclass. The subclass needs to implement

     * assert_success - passed a result of do_post, and invoked in places
       where we expect the request to have succeeded
     * assert_failure - same, for failed and error cases from the server.

    Included are base test cases that should be common to all controllers
    which use r2.lib.controlers.login as part of the flow.
    """
    def do_login(self, user="test", passwd="test123", **kw):
        return self.do_post("login", {"user": user, "passwd": passwd}, **kw)

    def do_register(
        self, user="test", passwd="test123", passwd2="test123", **kw
    ):
        return self.do_post("register", {
            "user": user,
            "passwd": passwd,
            "passwd2": passwd2,
        }, **kw)

    def mock_login(self, name="test", cookie="cookievaluehere"):
        """Context manager for mocking login.

        Patches VThrottledLogin to always return a mock with the provided
        name and cookie value
        """
        account = MagicMock()
        account.name = name
        account.make_cookie.return_value = cookie
        return patch.object(VThrottledLogin, "run", return_value=account)

    def mock_register(self):
        """Context manager for mocking out registration.

        Within this context, new users can be registered but they will
        be mock objects.  Also all usernames can be registered as the account
        lookup is bypassed and Account._by_name always raises NotFound.
        """
        from r2.controllers import login
        return contextlib.nested(
            patch.object(login, "register"),
            patch.object(VUname, "run", return_value="test"),
            # ensure this user does not currently exist
            patch.object(Account, "_by_name", side_effect=NotFound),
        )

    def failed_captcha(self):
        """Context manager for mocking a failed captcha."""
        return contextlib.nested(
            # ensure that a captcha is needed
            patch.object(
                validator,
                "need_provider_captcha",
                return_value=True,
            ),
            # ensure that the captcha is invalid
            patch.object(
                g.captcha_provider,
                "validate_captcha",
                return_value=False,
            ),
        )

    def disabled_captcha(self):
        """Context manager for mocking a disabled captcha.

        Will raise an AssertionError if the captcha code is called.
        """
        return contextlib.nested(
            # ensure that a captcha is not needed
            patch.object(
                validator,
                "need_provider_captcha",
                return_value=False,
            ),
            # ensure that the captcha is unused
            patch.object(
                g.captcha_provider,
                "validate_captcha",
                side_effect=AssertionError,
            ),
        )

    def find_headers(self, res, name):
        """Find header in res"""
        for k, v in res.headers:
            if k == name.lower():
                yield v

    def assert_headers(self, res, name, test):
        """Assert header value with test (lambda function or value)"""
        for value in self.find_headers(res, name):
            if callable(test) and test(value):
                return
            elif value == test:
                return
        raise AssertionError("No matching %s header found" % name)

    def assert_success(self, res):
        """Test that is run when we expect the post to succeed."""
        raise NotImplementedError

    def assert_failure(self, res, code=None):
        """Test that is run when we expect the post to fail."""
        raise NotImplementedError

    def test_login(self):
        with self.mock_login():
            res = self.do_login()
            self.assert_success(res)

    def test_login_wrong_password(self):
        with patch.object(Account, "_by_name", side_effect=NotFound):
            res = self.do_login()
            self.assert_failure(res, "WRONG_PASSWORD")

    def test_register(self):
        with self.mock_register():
            res = self.do_register()
            self.assert_success(res)

    def test_register_username_taken(self):
        with patch.object(
            Account, "_by_name", return_value=MagicMock(_deleted=False)
        ):
            res = self.do_register()
            self.assert_failure(res, "USERNAME_TAKEN")

    @unittest.skip("registration captcha is unfinished")
    def test_captcha_blocking(self):
        with contextlib.nested(
            self.mock_register(),
            self.failed_captcha()
        ):
            res = self.do_register()
            self.assert_failure(res, "BAD_CAPTCHA")

    @unittest.skip("registration captcha is unfinished")
    def test_captcha_disabling(self):
        with contextlib.nested(
            self.mock_register(),
            self.disabled_captcha()
        ):
            res = self.do_register()
            self.assert_success(res)
