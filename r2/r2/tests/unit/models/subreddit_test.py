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

import unittest

from mock import MagicMock
from pylons import app_globals as g

from r2.lib.permissions import PermissionSet

from r2.models import NotFound
from r2.models.account import Account
from r2.models.subreddit import SRMember, Subreddit

class TestPermissionSet(PermissionSet):
    info = dict(x={}, y={})


class SRMemberTest(unittest.TestCase):
    def setUp(self):
        a = Account()
        a._id = 1
        sr = Subreddit()
        sr._id = 2
        self.rel = SRMember(sr, a, 'test')

    def test_get_permissions(self):
        self.assertRaises(NotImplementedError, self.rel.get_permissions)
        self.rel._permission_class = TestPermissionSet
        self.assertEquals('', self.rel.get_permissions().dumps())
        self.rel.encoded_permissions = '+x,-y'
        self.assertEquals('+x,-y', self.rel.get_permissions().dumps())

    def test_has_permission(self):
        self.assertRaises(NotImplementedError, self.rel.has_permission, 'x')
        self.rel._permission_class = TestPermissionSet
        self.assertFalse(self.rel.has_permission('x'))
        self.rel.encoded_permissions = '+x,-y'
        self.assertTrue(self.rel.has_permission('x'))
        self.assertFalse(self.rel.has_permission('y'))
        self.rel.encoded_permissions = '+all'
        self.assertTrue(self.rel.has_permission('x'))
        self.assertTrue(self.rel.has_permission('y'))
        self.assertFalse(self.rel.has_permission('z'))

    def test_update_permissions(self):
        self.assertRaises(NotImplementedError,
                          self.rel.update_permissions, x=True)
        self.rel._permission_class = TestPermissionSet
        self.rel.update_permissions(x=True, y=False)
        self.assertEquals('+x,-y', self.rel.encoded_permissions)
        self.rel.update_permissions(x=None)
        self.assertEquals('-y', self.rel.encoded_permissions)
        self.rel.update_permissions(y=None, z=None)
        self.assertEquals('', self.rel.encoded_permissions)
        self.rel.update_permissions(x=True, y=False, all=True)
        self.assertEquals('+all', self.rel.encoded_permissions)

    def test_set_permissions(self):
        self.rel.set_permissions(PermissionSet(x=True, y=False))
        self.assertEquals('+x,-y', self.rel.encoded_permissions)

    def test_is_superuser(self):
        self.assertRaises(NotImplementedError, self.rel.is_superuser)
        self.rel._permission_class = TestPermissionSet
        self.assertFalse(self.rel.is_superuser())
        self.rel.encoded_permissions = '+all'
        self.assertTrue(self.rel.is_superuser())


class IsValidNameTest(unittest.TestCase):
    def test_empty(self):
        self.assertFalse(Subreddit.is_valid_name(None))

    def test_short(self):
        self.assertTrue(Subreddit.is_valid_name('aaa'))

    def test_too_short(self):
        self.assertFalse(Subreddit.is_valid_name('aa'))

    def test_long(self):
        self.assertTrue(Subreddit.is_valid_name('aaaaaaaaaaaaaaaaaaaaa'))

    def test_too_long(self):
        self.assertFalse(Subreddit.is_valid_name('aaaaaaaaaaaaaaaaaaaaaa'))

    def test_underscore(self):
        self.assertTrue(Subreddit.is_valid_name('a_a'))

    def test_leading_underscore(self):
        self.assertFalse(Subreddit.is_valid_name('_aa'))

    def test_capitals(self):
        self.assertTrue(Subreddit.is_valid_name('AZA'))

    def test_numerics(self):
        self.assertTrue(Subreddit.is_valid_name('090'))


class ByNameTest(unittest.TestCase):
    def setUp(self):
        self.cache = MagicMock()
        g.gencache = self.cache

        self.subreddit_byID = MagicMock()
        Subreddit._byID = self.subreddit_byID

        self.subreddit_query = MagicMock()
        Subreddit._query = self.subreddit_query

    def testSingleCached(self):
        subreddit = Subreddit(id=1, name="exists")
        self.cache.get_multi.return_value = {"exists": subreddit._id}
        self.subreddit_byID.return_value = [subreddit]

        ret = Subreddit._by_name("exists")

        self.assertEqual(ret, subreddit)
        self.assertEqual(self.subreddit_query.call_count, 0)

    def testSingleFromDB(self):
        subreddit = Subreddit(id=1, name="exists")
        self.cache.get_multi.return_value = {}
        self.subreddit_query.return_value = [subreddit]
        self.subreddit_byID.return_value = [subreddit]

        ret = Subreddit._by_name("exists")

        self.assertEqual(ret, subreddit)
        self.assertEqual(self.cache.set_multi.call_count, 1)

    def testSingleNotFound(self):
        self.cache.get_multi.return_value = {}
        self.subreddit_query.return_value = []

        with self.assertRaises(NotFound):
            Subreddit._by_name("doesnotexist")

    def testSingleInvalid(self):
        with self.assertRaises(NotFound):
            Subreddit._by_name("_illegalunderscore")

        self.assertEqual(self.cache.get_multi.call_count, 0)
        self.assertEqual(self.subreddit_query.call_count, 0)

    def testMultiCached(self):
        srs = [
            Subreddit(id=1, name="exists"),
            Subreddit(id=2, name="also"),
        ]
        self.cache.get_multi.return_value = {sr.name: sr._id for sr in srs}
        self.subreddit_byID.return_value = srs

        ret = Subreddit._by_name(["exists", "also"])

        self.assertEqual(ret, {sr.name: sr for sr in srs})
        self.assertEqual(self.subreddit_query.call_count, 0)

    def testMultiCacheMissesAllExist(self):
        srs = [
            Subreddit(id=1, name="exists"),
            Subreddit(id=2, name="also"),
        ]

        self.cache.get_multi.return_value = {}
        self.subreddit_query.return_value = srs
        self.subreddit_byID.return_value = srs

        ret = Subreddit._by_name(["exists", "also"])

        self.assertEqual(ret, {sr.name: sr for sr in srs})
        self.assertEqual(self.cache.get_multi.call_count, 1)
        self.assertEqual(self.subreddit_query.call_count, 1)

    def testMultiSomeDontExist(self):
        sr = Subreddit(id=1, name="exists")
        self.cache.get_multi.return_value = {sr.name: sr._id}
        self.subreddit_query.return_value = []
        self.subreddit_byID.return_value = [sr]

        ret = Subreddit._by_name(["exists", "doesnt"])

        self.assertEqual(ret, {sr.name: sr})
        self.assertEqual(self.cache.get_multi.call_count, 1)
        self.assertEqual(self.subreddit_query.call_count, 1)

    def testMultiSomeInvalid(self):
        sr = Subreddit(id=1, name="exists")
        self.cache.get_multi.return_value = {sr.name: sr._id}
        self.subreddit_query.return_value = []
        self.subreddit_byID.return_value = [sr]

        ret = Subreddit._by_name(["exists", "_illegalunderscore"])

        self.assertEqual(ret, {sr.name: sr})
        self.assertEqual(self.cache.get_multi.call_count, 1)
        self.assertEqual(self.subreddit_query.call_count, 0)

    def testForceUpdate(self):
        sr = Subreddit(id=1, name="exists")
        self.cache.get_multi.return_value = {sr.name: sr._id}
        self.subreddit_query.return_value = [sr]
        self.subreddit_byID.return_value = [sr]

        ret = Subreddit._by_name("exists", _update=True)

        self.assertEqual(ret, sr)
        self.cache.set_multi.assert_called_once_with(
            keys={sr.name: sr._id},
            prefix="srid:",
            time=43200,
        )

    def testCacheNegativeResults(self):
        self.cache.get_multi.return_value = {}
        self.subreddit_query.return_value = []
        self.subreddit_byID.return_value = []

        with self.assertRaises(NotFound):
            Subreddit._by_name("doesnotexist")

        self.cache.set_multi.assert_called_once_with(
            keys={"doesnotexist": Subreddit.SRNAME_NOTFOUND},
            prefix="srid:",
            time=43200,
        )

    def testExcludeNegativeLookups(self):
        self.cache.get_multi.return_value = {"doesnotexist": Subreddit.SRNAME_NOTFOUND}

        with self.assertRaises(NotFound):
            Subreddit._by_name("doesnotexist")
        self.assertEqual(self.subreddit_query.call_count, 0)
        self.assertEqual(self.subreddit_byID.call_count, 0)
        self.assertEqual(self.cache.set_multi.call_count, 0)


if __name__ == '__main__':
    unittest.main()
