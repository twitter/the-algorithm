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

import hashlib
import hmac
import json

from pylons import app_globals as g
import requests

from r2.lib import amqp
from r2.lib.filters import _force_unicode
from r2.lib.template_helpers import add_sr
from r2.lib.utils import constant_time_compare
from r2.models import (
    Account,
    Message,
    Subreddit,
)


def get_reply_to_address(message):
    """Construct a reply-to address that encodes the message id.

    The address is of the form:
        zendeskreply+{message_id36}-{email_mac}

    where the mac is generated from {message_id36} using the
    `modmail_email_secret`

    The reply address should be configured with the inbound email service so
    that replies to our messages are routed back to the app somehow. For mailgun
    this involves adding a Routes filter for messages sent to
    "zendeskreply\+*@". to be forwarded to POST /api/zendeskreply.

    """

    # all email replies are treated as replies to the first message in the
    # conversation. this is to get around some peculiarities of zendesk
    if message.first_message:
        first_message = Message._byID(message.first_message, data=True)
    else:
        first_message = message
    email_id = first_message._id36

    email_mac = hmac.new(
        g.secrets['modmail_email_secret'], email_id, hashlib.sha256).hexdigest()
    reply_id = "zendeskreply+{email_id}-{email_mac}".format(
        email_id=email_id, email_mac=email_mac)

    sr = Subreddit._byID(message.sr_id, data=True)
    return "r/{subreddit} mail <{reply_id}@{domain}>".format(
        subreddit=sr.name, reply_id=reply_id, domain=g.modmail_email_domain)


def parse_and_validate_reply_to_address(address):
    """Validate the address and parse out and return the message id.

    This is the reverse operation of `get_reply_to_address`.

    """

    recipient, sep, domain = address.partition("@")
    if not sep or not recipient or domain != g.modmail_email_domain:
        return

    main, sep, remainder = recipient.partition("+")
    if not sep or not main or main != "zendeskreply":
        return

    try:
        email_id, email_mac = remainder.split("-")
    except ValueError:
        return

    expected_mac = hmac.new(
        g.secrets['modmail_email_secret'], email_id, hashlib.sha256).hexdigest()

    if not constant_time_compare(expected_mac, email_mac):
        return

    message_id36 = email_id
    return message_id36


def get_message_subject(message):
    sr = Subreddit._byID(message.sr_id, data=True)

    if message.first_message:
        first_message = Message._byID(message.first_message, data=True)
        conversation_subject = first_message.subject
    else:
        conversation_subject = message.subject

    return u"[r/{subreddit} mail]: {subject}".format(
        subreddit=sr.name, subject=_force_unicode(conversation_subject))


def get_email_ids(message):
    parent_email_id = None
    other_email_ids = []
    if message.parent_id:
        parent = Message._byID(message.parent_id, data=True)
        if parent.email_id:
            other_email_ids.append(parent.email_id)
            parent_email_id = parent.email_id

    if message.first_message:
        first_message = Message._byID(message.first_message, data=True)
        if first_message.email_id:
            other_email_ids.append(first_message.email_id)

    return parent_email_id, other_email_ids


def get_system_from_address(sr):
    return "r/{subreddit} mail <{sender_email}>".format(
        subreddit=sr.name, sender_email=g.modmail_system_email)


def send_modmail_email(message):
    if not message.sr_id:
        return

    sr = Subreddit._byID(message.sr_id, data=True)

    forwarding_email = g.live_config['modmail_forwarding_email'].get(sr.name)
    if not forwarding_email:
        return

    sender = Account._byID(message.author_id, data=True)

    if sender.name in g.admins:
        distinguish = "[A]"
    elif sr.is_moderator(sender):
        distinguish = "[M]"
    else:
        distinguish = None

    if distinguish:
        from_address = "u/{username} {distinguish} <{sender_email}>".format(
            username=sender.name, distinguish=distinguish,
            sender_email=g.modmail_sender_email)
    else:
        from_address = "u/{username} <{sender_email}>".format(
            username=sender.name, sender_email=g.modmail_sender_email)

    reply_to = get_reply_to_address(message)
    parent_email_id, other_email_ids = get_email_ids(message)
    subject = get_message_subject(message)

    if message.from_sr and not message.first_message:
        # this is a message from the subreddit to a user. add some text that
        # shows the recipient
        recipient = Account._byID(message.to_id, data=True)
        sender_text = ("This message was sent from r/{subreddit} to "
            "u/{user}").format(subreddit=sr.name, user=recipient.name)
    else:
        userlink = add_sr("/u/{name}".format(name=sender.name), sr_path=False)
        sender_text = "This message was sent by {userlink}".format(
            userlink=userlink,
        )

    reply_footer = ("\n\n-\n{sender_text}\n\n"
        "Reply to this email directly or view it on reddit: {link}")
    reply_footer = reply_footer.format(
        sender_text=sender_text,
        link=message.make_permalink(force_domain=True),
    )
    message_text = message.body + reply_footer

    email_id = g.email_provider.send_email(
        to_address=forwarding_email,
        from_address=from_address,
        subject=subject,
        text=message_text,
        reply_to=reply_to,
        parent_email_id=parent_email_id,
        other_email_ids=other_email_ids,
    )
    if email_id:
        g.log.info("sent %s as %s", message._id36, email_id)
        message.email_id = email_id
        message._commit()
        g.stats.simple_event("modmail_email.outgoing_email")


def send_blocked_muted_email(sr, parent, sender_email, incoming_email_id):
    subject = get_message_subject(parent)
    from_address = get_system_from_address(sr)
    text = "Message was not delivered because recipient is muted."

    email_id = g.email_provider.send_email(
        to_address=sender_email,
        from_address=from_address,
        subject=subject,
        text=text,
        reply_to=from_address,
        parent_email_id=incoming_email_id,
        other_email_ids=[parent.email_id],
    )
    if email_id:
        g.log.info("sent as %s", email_id)


def queue_modmail_email(message):
    amqp.add_item(
        "modmail_email_q",
        json.dumps({
            "event": "new_message",
            "message_id36": message._id36,
        }),
    )


def queue_blocked_muted_email(sr, parent, sender_email, incoming_email_id):
    amqp.add_item(
        "modmail_email_q",
        json.dumps({
            "event": "blocked_muted",
            "subreddit_id36": sr._id36,
            "parent_id36": parent._id36,
            "sender_email": sender_email,
            "incoming_email_id": incoming_email_id,
        }),
    )


def process_modmail_email():
    @g.stats.amqp_processor("modmail_email_q")
    def process_message(msg):
        msg_dict = json.loads(msg.body)
        if msg_dict["event"] == "new_message":
            message_id36 = msg_dict["message_id36"]
            message = Message._byID36(message_id36, data=True)
            send_modmail_email(message)
        elif msg_dict["event"] == "blocked_muted":
            subreddit_id36 = msg_dict["subreddit_id36"]
            sr = Subreddit._byID36(subreddit_id36, data=True)
            parent_id36 = msg_dict["parent_id36"]
            parent = Message._byID36(parent_id36, data=True)
            sender_email = msg_dict["sender_email"]
            incoming_email_id = msg_dict["incoming_email_id"]
            send_blocked_muted_email(sr, parent, sender_email, incoming_email_id)

    amqp.consume_items("modmail_email_q", process_message)
