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


import json
import re

import requests

from r2.lib.configparse import ConfigValue
from r2.lib.providers.email import (
    EmailProvider,
    EmailSendError,
)
from r2.lib.utils import tup


class MailgunEmailProvider(EmailProvider):
    """A provider that uses mailgun to send emails."""

    config = {
        ConfigValue.str: [
            "mailgun_api_base_url",
        ],
        # This also requires the secret "mailgun_api_key"
    }

    def send_email(self, to_address, from_address, subject, text, reply_to,
                   parent_email_id=None, other_email_ids=None):
        from pylons import app_globals as g

        if not text and not html:
            msg = "must provide either text or html in email body"
            raise TypeError(msg)

        # pick out the mailgun domain from the from_address field domain
        from_domain_match = re.search("@([\w.]+)", from_address)

        if from_domain_match is None:
            raise ValueError("from address is malformed")

        mailgun_domain = from_domain_match.group(1)

        if mailgun_domain not in g.mailgun_domains:
            raise ValueError("from address must be from an approved domain")

        message_post_url = "/".join((
            g.mailgun_api_base_url, mailgun_domain, "messages"))

        to_address = tup(to_address)
        parent_email_id = parent_email_id or ''
        other_email_ids = other_email_ids or []

        response = requests.post(
            message_post_url,
            auth=("api", g.secrets['mailgun_api_key']),
            data={
                "from": from_address,
                "to": to_address,
                "subject": subject,
                "text": text,
                "o:tracking": False,    # disable link rewriting
                "h:Reply-To": reply_to,
                "h:In-Reply-To": parent_email_id,
                "h:References": " ".join(other_email_ids),
            },
        )

        if response.status_code != 200:
            msg = "mailgun sending email failed {status}: {text}".format(
                status=response.status_code, text=response.text)
            raise EmailSendError(msg)

        try:
            body = json.loads(response.text)
        except ValueError:
            msg = "mailgun sending email bad response {status}: {text}".format(
                status=response.status_code, text=response.text)
            g.stats.simple_event("mailgun.outgoing.failure")
            raise EmailSendError(msg)

        g.stats.simple_event("mailgun.outgoing.success")
        email_id = body["id"]
        return email_id
