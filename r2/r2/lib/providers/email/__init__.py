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

class EmailProvider(object):
    """Provider for sending emails.

    """
    def send_email(self, to_address, from_address, subject, text, reply_to,
                       parent_email_id=None, other_email_ids=None):
        """Send an email.

        `to_address` is a string or list of string email addresses.

        `from_address` is a email address.

        `subject` is the email's subject.

        `text` is the text of the email.

        `reply_to` is the reply-to address, which will be sent as the "Reply-To"
        email header.

        `parent_email_id` is the Message-Id of the email this is a reply to (if
        any). This is sent as the "In-Reply-To" email header.

        `other_email_ids` is a list of Message-Ids of emails in the conversation
        and including `parent_email_id`. This will be converted to a space
        delimited string and sent as the "References" email header.

        The return value is the Message-Id of the sent email.

        """
        raise NotImplementedError


class EmailSendError(Exception): pass
