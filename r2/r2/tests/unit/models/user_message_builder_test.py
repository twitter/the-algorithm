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
import contextlib

from r2.tests import RedditTestCase

from mock import patch, MagicMock

from r2.models import Message
from r2.models.builder import UserMessageBuilder, MessageBuilder

from pylons import tmpl_context as c


class UserMessageBuilderTest(RedditTestCase):
    def setUp(self):
        super(UserMessageBuilderTest, self).setUp()
        self.user = MagicMock(name="user")
        self.message = MagicMock(spec=Message)

    def test_view_message_on_receiver_side_and_spam(self):
        user = MagicMock(name="user")
        userMessageBuilder = UserMessageBuilder(user)

        self.user._id = 1
        self.message.author_id = 2
        self.message._spam = True

        with self.mock_preparation():
            self.assertFalse(
                    userMessageBuilder._viewable_message(self.message))

    def test_view_message_on_receiver_side_and_del(self):
        user = MagicMock(name="user")
        userMessageBuilder = UserMessageBuilder(user)

        self.user._id = 1
        self.message.author_id = 2
        self.message.to_id = self.user._id
        self.message._spam = False
        self.message.del_on_recipient = True

        with self.mock_preparation():
            self.assertFalse(
                    userMessageBuilder._viewable_message(self.message))

    def test_view_message_on_receiver_side(self):
        user = MagicMock(name="user")
        userMessageBuilder = UserMessageBuilder(user)

        self.user._id = 1
        self.message.author_id = 2
        self.message.to_id = self.user._id
        self.message._spam = False
        self.message.del_on_recipient = False

        with self.mock_preparation():
            self.assertTrue(
                userMessageBuilder._viewable_message(self.message))

    def test_view_message_on_sender_side_and_del(self):
        user = MagicMock(name="user")
        userMessageBuilder = UserMessageBuilder(user)

        self.message.to_id = 1
        self.user._id = 2
        self.message.author_id = self.user._id
        self.message._spam = False
        self.message.del_on_recipient = True

        with self.mock_preparation():
            self.assertTrue(
                userMessageBuilder._viewable_message(self.message))

    def test_view_message_on_admin_and_del(self):
        user = MagicMock(name="user")
        userMessageBuilder = UserMessageBuilder(user)

        self.user._id = 1
        self.message.author_id = 2
        self.message.to_id = self.user._id
        self.message._spam = False
        self.message.del_on_recipient = True

        with self.mock_preparation(True):
            self.assertTrue(
                userMessageBuilder._viewable_message(self.message))

    def mock_preparation(self, is_admin=False):
        """ Context manager for mocking function calls. """

        return contextlib.nested(
            patch.object(c, "user", self.user, create=True),
            patch.object(c, "user_is_admin", is_admin, create=True),
            patch.object(MessageBuilder,
                         "_viewable_message", return_value=True)
        )

