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

from random import shuffle
from mock import MagicMock, Mock

from r2.models import Collection, CollectionStorage
from r2.tests import RedditTestCase


class CollectionStorageTest(RedditTestCase):

    def setUp(self):
        self.name = 'fake name'

    def test_set_attributes(self):
        """Assert that _set_attributes properly handles invalid attributes"""
        attribute_error_message = 'No attribute on %s called %s'
        invalid_attribute = {'invalid_attribute': None}
        valid_attribute = {'is_spotlight': None}

        collection = Collection(name=self.name, sr_names=[])

        Collection.by_name = Mock()
        Collection.by_name.return_value = collection

        # Assert that a bad attribute will raise NotFoundException
        with self.assertRaises(AttributeError) as e:
            CollectionStorage._set_attributes(self.name, invalid_attribute)
        self.assertEqual(e.exception.message, attribute_error_message %
            (self.name, invalid_attribute.keys()[0]))

        # Should throw even if there's a bad attribute AND valid attribute
        with self.assertRaises(AttributeError) as e:
            CollectionStorage._set_attributes(self.name,
            dict(invalid_attribute, **valid_attribute))
        self.assertEqual(e.exception.message, attribute_error_message %
            (self.name, invalid_attribute.keys()[0]))

        CollectionStorage._set_values = MagicMock()
        CollectionStorage._set_attributes(self.name,
            valid_attribute)

        # Assert that no exception thrown if valid attributes passed
        CollectionStorage._set_values.assert_called_once_with(self.name,
            valid_attribute)

    def test_set_over_18(self):
        """Assert that set_over_18 invokes _set_attributes"""
        CollectionStorage._set_attributes = MagicMock()
        CollectionStorage.set_over_18(self.name, True)

        CollectionStorage._set_attributes.assert_called_once_with(self.name,
            {'over_18': 'True'})

    def test_set_is_spotlight(self):
        """Assert that set_is_spotlight invokes _set_attributes"""
        CollectionStorage._set_attributes = MagicMock()
        CollectionStorage.set_is_spotlight(self.name, True)

        CollectionStorage._set_attributes.assert_called_once_with(self.name,
            {'is_spotlight': 'True'})


class CollectionTest(RedditTestCase):

    def test_is_spotlight_default(self):
        """Assert that is_spotlight defaults to False"""
        collection = Collection(name='fake name', sr_names=[])
        self.assertFalse(collection.is_spotlight)

        setattr(collection, 'is_spotlight', True)
        self.assertTrue(collection.is_spotlight)


class CollectionOrderTest(RedditTestCase):
    """
    Assert that Collection.get_all() sorts in the following sequence:
    1. SFW/NSFW
    2. Spotlighted
    3. Alphabetical
    """

    def setUp(self):
        # Setup all collections
        self.spotlight_a = Collection(name='spotlight_a', sr_names=[],
            is_spotlight=True)
        self.spotlight_z = Collection(name='spotlight_z', sr_names=[],
            is_spotlight=True)
        self.sfw_a = Collection(name='sfw_a', sr_names=[])
        self.sfw_b = Collection(name='sfw_B', sr_names=[])
        self.sfw_z = Collection(name='sfw_z', sr_names=[])
        self.nsfw_spotlight = Collection(name='nsfw_spotlight', sr_names=[],
            over_18=True, is_spotlight=True)
        self.nsfw_non_spotlight = Collection(name='nsfw_non_spotlight',
            sr_names=[], over_18=True)

        self.correct_order = [
            'spotlight_a',
            'spotlight_z',
            'sfw_a',
            'sfw_B',
            'sfw_z',
            'nsfw_spotlight',
            'nsfw_non_spotlight',
        ]

        # Mock the get_all method on CollectionStorage,
        # which returns an unordered list of collections
        CollectionStorage.get_all = MagicMock()

    def _assert_scenario(self, unordered_collections):
        CollectionStorage.get_all.return_value = unordered_collections
        self.assertEqual(
            [collection.name for collection in Collection.get_all()],
            self.correct_order)

    def test_scenario_reversed(self):
        """Assert that reversed order will order correctly"""
        unordered_collections = [
            self.nsfw_spotlight,
            self.nsfw_non_spotlight,
            self.sfw_z,
            self.sfw_b,
            self.sfw_a,
            self.spotlight_z,
            self.spotlight_a,
        ]
        self._assert_scenario(unordered_collections)

    def test_scenario_semi_sorted(self):
        """
        Assert that SFW and spotlight sorted list that is
        unordered alphabetically will order correctly
        """
        unordered_collections = [
            self.spotlight_z,
            self.spotlight_a,
            self.sfw_z,
            self.sfw_b,
            self.sfw_a,
            self.nsfw_spotlight,
            self.nsfw_non_spotlight,
        ]
        self._assert_scenario(unordered_collections)

    def test_scenario_random(self):
        """Assert that totally random list will order correctly"""
        unordered_collections = [
            self.sfw_z,
            self.nsfw_non_spotlight,
            self.sfw_a,
            self.spotlight_a,
            self.nsfw_spotlight,
            self.spotlight_z,
            self.sfw_b,
        ]
        self._assert_scenario(unordered_collections)

    def test_scenario_casing(self):
        """Assert that ordering is case-insensitive"""
        unordered_collections = [
            self.sfw_b,
            self.sfw_a,
            self.sfw_z,
            self.spotlight_a,
            self.spotlight_z,
            self.nsfw_spotlight,
            self.nsfw_non_spotlight,
        ]
        self._assert_scenario(unordered_collections)
