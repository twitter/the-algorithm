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
import unittest

from pylons import tmpl_context as c
from webob.exc import HTTPException

# Needs to be done before other r2 imports, since some code run on module import
# expects a sane pylons env
from r2.tests import RedditTestCase

from r2.lib.db.thing import NotFound
from r2.lib.errors import errors, ErrorSet, UserRequiredException
from r2.lib.validator import VVerifyPassword
from r2.models import Account, AccountExists, bcrypt_password


class TestVVerifyPassword(unittest.TestCase):
    """Test that only the current user's password satisfies VVerifyPassword"""
    @classmethod
    def setUpClass(cls):
        # Create a dummy account for testing with; won't touch the database
        # as long as we don't `._commit()`
        name = "unit_tester_%s" % uuid.uuid4().hex
        cls._password = uuid.uuid4().hex
        cls._account = Account(
            name=name,
            password=bcrypt_password(cls._password)
        )

    def setUp(self):
        c.user_is_loggedin = True
        c.user = self._account

    def _checkFails(self, password, fatal=False, error=errors.WRONG_PASSWORD):
        # So we don't have any stale errors laying around
        c.errors = ErrorSet()
        validator = VVerifyPassword('dummy', fatal=fatal)

        if fatal:
            try:
                validator.run(password)
            except HTTPException:
                return True
            return False
        else:
            validator.run(password)

            return validator.has_errors or c.errors.get((error, None))

    def test_loggedout(self):
        c.user = ""
        c.user_is_loggedin = False
        self.assertRaises(UserRequiredException, self._checkFails, "dummy")

    def test_right_password(self):
        self.assertFalse(self._checkFails(self._password, fatal=False))
        self.assertFalse(self._checkFails(self._password, fatal=True))

    def test_wrong_password(self):
        bad_pass = "~" + self._password[1:]
        self.assertTrue(self._checkFails(bad_pass, fatal=False))
        self.assertTrue(self._checkFails(bad_pass, fatal=True))

    def test_no_password(self):
        self.assertTrue(self._checkFails(None, fatal=False))
        self.assertTrue(self._checkFails(None, fatal=True))

        self.assertTrue(self._checkFails("", fatal=False))
        self.assertTrue(self._checkFails("", fatal=True))
