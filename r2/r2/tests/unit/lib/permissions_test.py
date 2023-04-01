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

from r2.lib.permissions import PermissionSet, ModeratorPermissionSet

class TestPermissionSet(PermissionSet):
    info = dict(x={}, y={})

class PermissionSetTest(unittest.TestCase):
    def test_dumps(self):
        self.assertEquals(
            '+all', PermissionSet(all=True).dumps())
        self.assertEquals(
            '+all', PermissionSet(all=True, other=True).dumps())
        self.assertEquals(
            '+a,-b', PermissionSet(a=True, b=False).dumps())

    def test_loads(self):
        self.assertEquals("", TestPermissionSet.loads(None).dumps())
        self.assertEquals("", TestPermissionSet.loads("").dumps())
        self.assertEquals("+x,+y", TestPermissionSet.loads("+x,+y").dumps())
        self.assertEquals("+x,-y", TestPermissionSet.loads("+x,-y").dumps())
        self.assertEquals("+all", TestPermissionSet.loads("+x,-y,+all").dumps())
        self.assertEquals("+x,-y,+z",
                          TestPermissionSet.loads("+x,-y,+z").dumps())
        self.assertRaises(ValueError,
                          TestPermissionSet.loads, "+x,-y,+z", validate=True)
        self.assertEquals(
            "+x,-y",
            TestPermissionSet.loads("-all,+x,-y", validate=True).dumps())

    def test_is_superuser(self):
        perm_set = PermissionSet()
        self.assertFalse(perm_set.is_superuser())
        perm_set[perm_set.ALL] = True
        self.assertTrue(perm_set.is_superuser())
        perm_set[perm_set.ALL] = False
        self.assertFalse(perm_set.is_superuser())

    def test_is_valid(self):
        perm_set = PermissionSet()
        self.assertFalse(perm_set.is_valid())

        perm_set = TestPermissionSet()
        self.assertTrue(perm_set.is_valid())
        perm_set['x'] = True
        self.assertTrue(perm_set.is_valid())
        perm_set[perm_set.ALL] = True
        self.assertTrue(perm_set.is_valid())
        perm_set['z'] = True
        self.assertFalse(perm_set.is_valid())

    def test_getitem(self):
        perm_set = PermissionSet()
        perm_set[perm_set.ALL] = True
        self.assertFalse(perm_set['x'])

        perm_set = TestPermissionSet()
        perm_set['x'] = True
        self.assertTrue(perm_set['x'])
        self.assertFalse(perm_set['y'])
        perm_set['x'] = False
        self.assertFalse(perm_set['x'])
        perm_set[perm_set.ALL] = True
        self.assertTrue(perm_set['x'])
        self.assertTrue(perm_set['y'])
        self.assertFalse(perm_set['z'])
        self.assertTrue(perm_set.get('x', False))
        self.assertFalse(perm_set.get('z', False))
        self.assertTrue(perm_set.get('z', True))


class ModeratorPermissionSetTest(unittest.TestCase):
    def test_loads(self):
        self.assertTrue(ModeratorPermissionSet.loads(None).is_superuser())
        self.assertFalse(ModeratorPermissionSet.loads('').is_superuser())

