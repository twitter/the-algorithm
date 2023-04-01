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

from r2.tests import RedditControllerTestCase
from mock import patch, MagicMock
from r2.lib.validator import VByName, VUser, VModhash

from r2.models import Link, Message, Account

from pylons import app_globals as g


class DelMsgTest(RedditControllerTestCase):
    CONTROLLER = "api"

    def setUp(self):
        super(DelMsgTest, self).setUp()

        self.id = 1

    def test_del_msg_success(self):
        """Del_msg succeeds: Returns 200 and sets del_on_recipient."""
        message = MagicMock(spec=Message)
        message.name = "msg_1"
        message.to_id = self.id
        message.del_on_recipient = False

        with self.mock_del_msg(message):
            res = self.do_del_msg(message.name)

            self.assertEqual(res.status, 200)
            self.assertTrue(message.del_on_recipient)

    def test_del_msg_failure_with_link(self):
        """Del_msg fails: Returns 200 and does not set del_on_recipient."""
        link = MagicMock(spec=Link)
        link.del_on_recipient = False
        link.name = "msg_2"

        with self.mock_del_msg(link):
            res = self.do_del_msg(link.name)

            self.assertEqual(res.status, 200)
            self.assertFalse(link.del_on_recipient)

    def test_del_msg_failure_with_null_msg(self):
        """Del_msg fails: Returns 200 and does not set del_on_recipient."""
        message = MagicMock(spec=Message)
        message.name = "msg_3"
        message.to_id = self.id
        message.del_on_recipient = False

        with self.mock_del_msg(message, False):
            res = self.do_del_msg(message.name)

            self.assertEqual(res.status, 200)
            self.assertFalse(message.del_on_recipient)

    def test_del_msg_failure_with_sender(self):
        """Del_msg fails: Returns 200 and does not set del_on_recipient."""
        message = MagicMock(spec=Message)
        message.name = "msg_3"
        message.to_id = self.id + 1
        message.del_on_recipient = False

        with self.mock_del_msg(message):
            res = self.do_del_msg(message.name)

            self.assertEqual(res.status, 200)
            self.assertFalse(message.del_on_recipient)

    def mock_del_msg(self, thing, ret=True):
        """Context manager for mocking del_msg."""

        return contextlib.nested(
            patch.object(VByName, "run", return_value=thing if ret else None),
            patch.object(VModhash, "run", side_effect=None),
            patch.object(VUser, "run", side_effect=None),
            patch.object(thing, "_commit", side_effect=None),
            patch.object(Account, "_id", self.id, create=True),
            patch.object(g.events, "message_event", side_effect=None),
        )

    def do_del_msg(self, name, **kw):
        return self.do_post("del_msg", {"id": name}, **kw)
