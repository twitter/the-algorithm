#!/usr/bin/env python
# coding=utf-8
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

import datetime

import pytz
import json
from pylons import app_globals as g
from mock import MagicMock, patch

from r2.tests import RedditTestCase
from r2.models import Link
from r2.lib import hooks
from r2 import models


FAKE_DATE = datetime.datetime(2005, 6, 23, 3, 14, 0, tzinfo=pytz.UTC)


class TestEventCollector(RedditTestCase):

    def setUp(self):
        super(TestEventCollector, self).setUp()
        self.mock_eventcollector()
        self.autopatch(hooks, "get_hook")

    def test_vote_event(self):
        self.patch_liveconfig("events_collector_vote_sample_rate", 1.0)
        enum_name = "foo"
        enum_note = "bar"
        notes = "%s(%s)" % (enum_name, enum_note)
        initial_vote = MagicMock(is_upvote=True, is_downvote=False,
                                 is_automatic_initial_vote=True,
                                 previous_vote=None,
                                 data={"rank": MagicMock()},
                                 name="initial_vote",
                                 effects=MagicMock(
                                     note_codes=[enum_name],
                                     serializable_data={"notes": notes}))
        g.events.vote_event(initial_vote)

        self.amqp.assert_event_item(
            dict(
                event_topic="vote_server",
                event_type="server_vote",
                payload={
                    'vote_direction': 'up',
                    'target_type': 'magicmock',
                    'target_age_seconds': initial_vote.thing._age.total_seconds(),
                    'target_rank': initial_vote.data['rank'],
                    'sr_id': initial_vote.thing.subreddit_slow._id,
                    'sr_name': initial_vote.thing.subreddit_slow.name,
                    'target_fullname': initial_vote.thing._fullname,
                    'target_name': initial_vote.thing.name,
                    'target_id': initial_vote.thing._id,
                    'details_text': notes,
                    'process_notes': enum_name,
                    'auto_self_vote': True,
                }
            )
        )

    def test_vote_event_with_prev(self):
        self.patch_liveconfig("events_collector_vote_sample_rate", 1.0)
        upvote = MagicMock(name="upvote",
                           is_automatic_initial_vote=False,
                           data={"rank": MagicMock()})
        upvote.previous_vote = MagicMock(name="previous_vote",
                                         is_upvote=False, is_downvote=True)
        g.events.vote_event(upvote)

        self.amqp.assert_event_item(
            dict(
                event_topic="vote_server",
                event_type="server_vote",
                payload={
                    'vote_direction': 'up',
                    'target_type': 'magicmock',
                    'target_age_seconds': upvote.thing._age.total_seconds(),
                    'target_rank': upvote.data['rank'],
                    'sr_id': upvote.thing.subreddit_slow._id,
                    'sr_name': upvote.thing.subreddit_slow.name,
                    'target_fullname': upvote.thing._fullname,
                    'target_name': upvote.thing.name,
                    'target_id': upvote.thing._id,
                    'prev_vote_ts': self.created_ts_mock,
                    'prev_vote_direction': 'down',
                }
            )
        )

    def test_submit_event(self):
        self.patch_liveconfig("events_collector_submit_sample_rate", 1.0)
        new_link = MagicMock(name="new_link")
        context = MagicMock(name="context")
        request = MagicMock(name="request")
        request.ip = "1.2.3.4"
        g.events.submit_event(new_link, context=context, request=request)

        self.amqp.assert_event_item(
            dict(
                event_topic="submit_events",
                event_type="ss.submit",
                payload={
                    'domain': request.host,
                    'user_id': context.user._id,
                    'user_name': context.user.name,
                    'user_neutered': new_link.author_slow._spam,
                    'post_id': new_link._id,
                    'post_fullname': new_link._fullname,
                    'post_title': new_link.title,
                    'post_type': "self",
                    'post_body': new_link.selftext,
                    'sr_id': new_link.subreddit_slow._id,
                    'sr_name': new_link.subreddit_slow.name,
                    'geoip_country': context.location,
                    'oauth2_client_id': context.oauth2_client._id,
                    'oauth2_client_app_type': context.oauth2_client.app_type,
                    'oauth2_client_name': context.oauth2_client.name,
                    'referrer_domain': self.domain_mock(),
                    'referrer_url': request.headers.get(),
                    'user_agent': request.user_agent,
                    'user_agent_parsed': request.parsed_agent.to_dict(),
                    'obfuscated_data': {
                        'client_ip': request.ip,
                        'client_ipv4_24': "1.2.3",
                        'client_ipv4_16': "1.2",
                    }
                }
            )
        )

    def test_report_event_link(self):
        self.patch_liveconfig("events_collector_report_sample_rate", 1.0)

        target = MagicMock(name="target")
        target.__class__ = Link
        target._deleted = False
        target.author_slow._deleted = False

        context = MagicMock(name="context")
        request = MagicMock(name="request")
        request.ip = "1.2.3.4"
        g.events.report_event(
            target=target, context=context, request=request
        )

        self.amqp.assert_event_item(
            {
                'event_type': "ss.report",
                'event_topic': 'report_events',
                'payload': {
                    'process_notes': "CUSTOM",
                    'target_fullname': target._fullname,
                    'target_name': target.name,
                    'target_title': target.title,
                    'target_type': "self",
                    'target_author_id': target.author_slow._id,
                    'target_author_name': target.author_slow.name,
                    'target_id': target._id,
                    'target_age_seconds': target._age.total_seconds(),
                    'target_created_ts': self.created_ts_mock,
                    'domain': request.host,
                    'user_agent': request.user_agent,
                    'user_agent_parsed': request.parsed_agent.to_dict(),
                    'referrer_url': request.headers.get(),
                    'user_id': context.user._id,
                    'user_name': context.user.name,
                    'oauth2_client_id': context.oauth2_client._id,
                    'oauth2_client_app_type': context.oauth2_client.app_type,
                    'oauth2_client_name': context.oauth2_client.name,
                    'referrer_domain': self.domain_mock(),
                    'geoip_country': context.location,
                    'obfuscated_data': {
                        'client_ip': request.ip,
                        'client_ipv4_24': "1.2.3",
                        'client_ipv4_16': "1.2",
                    }
                }
            }
        )

    def test_mod_event(self):
        self.patch_liveconfig("events_collector_mod_sample_rate", 1.0)
        mod = MagicMock(name="mod")
        modaction = MagicMock(name="modaction")
        subreddit = MagicMock(name="subreddit")
        context = MagicMock(name="context")
        request = MagicMock(name="request")
        request.ip = "1.2.3.4"
        g.events.mod_event(
            modaction, subreddit, mod, context=context, request=request
        )

        self.amqp.assert_event_item(
            {
                'event_type': modaction.action,
                'event_topic': 'mod_events',
                'payload': {
                    'sr_id': subreddit._id,
                    'sr_name': subreddit.name,
                    'domain': request.host,
                    'user_agent': request.user_agent,
                    'user_agent_parsed': request.parsed_agent.to_dict(),
                    'referrer_url': request.headers.get(),
                    'user_id': context.user._id,
                    'user_name': context.user.name,
                    'oauth2_client_id': context.oauth2_client._id,
                    'oauth2_client_app_type': context.oauth2_client.app_type,
                    'oauth2_client_name': context.oauth2_client.name,
                    'referrer_domain': self.domain_mock(),
                    'details_text': modaction.details_text,
                    'geoip_country': context.location,
                    'obfuscated_data': {
                        'client_ip': request.ip,
                        'client_ipv4_24': "1.2.3",
                        'client_ipv4_16': "1.2",
                    }
                }
            }
        )

    def test_quarantine_event(self):
        self.patch_liveconfig("events_collector_quarantine_sample_rate", 1.0)
        event_type = MagicMock(name="event_type")
        subreddit = MagicMock(name="subreddit")
        context = MagicMock(name="context")
        request = MagicMock(name="request")
        request.ip = "1.2.3.4"
        g.events.quarantine_event(
            event_type, subreddit, context=context, request=request
        )

        self.amqp.assert_event_item(
            {
                'event_type': event_type,
                'event_topic': 'quarantine',
                "payload": {
                    'domain': request.host,
                    'referrer_domain': self.domain_mock(),
                    'verified_email': context.user.email_verified,
                    'user_id': context.user._id,
                    'sr_name': subreddit.name,
                    'referrer_url': request.headers.get(),
                    'user_agent': request.user_agent,
                    'user_agent_parsed': request.parsed_agent.to_dict(),
                    'sr_id': subreddit._id,
                    'user_name': context.user.name,
                    'oauth2_client_id': context.oauth2_client._id,
                    'oauth2_client_app_type': context.oauth2_client.app_type,
                    'oauth2_client_name': context.oauth2_client.name,
                    'geoip_country': context.location,
                    'obfuscated_data': {
                        'client_ip': request.ip,
                        'client_ipv4_24': "1.2.3",
                        'client_ipv4_16': "1.2",
                    }
                }
            }
        )

    def test_modmail_event(self):
        self.patch_liveconfig("events_collector_modmail_sample_rate", 1.0)
        message = MagicMock(name="message", _date=FAKE_DATE)
        first_message = MagicMock(name="first_message")
        message_cls = self.autopatch(models, "Message")
        message_cls._byID.return_value = first_message
        context = MagicMock(name="context")
        request = MagicMock(name="request")
        request.ip = "1.2.3.4"
        g.events.modmail_event(
            message, context=context, request=request
        )

        self.amqp.assert_event_item(
            {
                'event_type': "ss.send_message",
                'event_topic': "message_events",
                "payload": {
                    'domain': request.host,
                    'referrer_domain': self.domain_mock(),
                    'user_id': message.author_slow._id,
                    'user_name': message.author_slow.name,
                    'message_id': message._id,
                    'message_fullname': message._fullname,
                    'message_kind': "modmail",
                    'message_body': message.body,
                    'message_subject': message.subject,
                    'first_message_fullname': first_message._fullname,
                    'first_message_id': first_message._id,
                    'sender_type': "moderator",
                    'is_third_party': True,
                    'third_party_metadata': "mailgun",
                    'referrer_url': request.headers.get(),
                    'user_agent': request.user_agent,
                    'user_agent_parsed': request.parsed_agent.to_dict(),
                    'sr_id': message.subreddit_slow._id,
                    'sr_name': message.subreddit_slow.name,
                    'oauth2_client_id': context.oauth2_client._id,
                    'oauth2_client_app_type': context.oauth2_client.app_type,
                    'oauth2_client_name': context.oauth2_client.name,
                    'geoip_country': context.location,
                    'obfuscated_data': {
                        'client_ip': request.ip,
                        'client_ipv4_24': "1.2.3",
                        'client_ipv4_16': "1.2",
                    },
                },
            }
        )

    def test_message_event(self):
        self.patch_liveconfig("events_collector_modmail_sample_rate", 1.0)
        message = MagicMock(name="message", _date=FAKE_DATE)
        first_message = MagicMock(name="first_message")
        message_cls = self.autopatch(models, "Message")
        message_cls._byID.return_value = first_message
        context = MagicMock(name="context")
        request = MagicMock(name="request")
        request.ip = "1.2.3.4"
        g.events.message_event(
            message, context=context, request=request
        )

        self.amqp.assert_event_item(
            {
                'event_type': "ss.send_message",
                'event_topic': "message_events",
                "payload": {
                    'domain': request.host,
                    'referrer_domain': self.domain_mock(),
                    'user_id': message.author_slow._id,
                    'user_name': message.author_slow.name,
                    'message_id': message._id,
                    'message_fullname': message._fullname,
                    'message_kind': "message",
                    'message_body': message.body,
                    'message_subject': message.subject,
                    'first_message_fullname': first_message._fullname,
                    'first_message_id': first_message._id,
                    'sender_type': "user",
                    'is_third_party': True,
                    'third_party_metadata': "mailgun",
                    'referrer_url': request.headers.get(),
                    'user_agent': request.user_agent,
                    'user_agent_parsed': request.parsed_agent.to_dict(),
                    'oauth2_client_id': context.oauth2_client._id,
                    'oauth2_client_app_type': context.oauth2_client.app_type,
                    'oauth2_client_name': context.oauth2_client.name,
                    'geoip_country': context.location,
                    'obfuscated_data': {
                        'client_ip': request.ip,
                        'client_ipv4_24': "1.2.3",
                        'client_ipv4_16': "1.2",
                    },
                },
            }
        )
