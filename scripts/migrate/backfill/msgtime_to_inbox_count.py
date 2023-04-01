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
"""Converts msgtime for users to inbox_count, for inbox count tracking."""

import sys

from r2.lib.db import queries
from r2.lib.db.operators import desc
from r2.lib.utils import fetch_things2, progress
from r2.models import Account, Message

from pylons import app_globals as g


def _keep(msg, account):
    """Adapted from listingcontroller.MessageController's keep_fn."""
    if msg._deleted:
        return False

    if msg._spam and msg.author_id != account._id:
        return False

    if msg.author_id in account.enemies:
        return False

    # do not keep messages which were deleted on recipient
    if (isinstance(msg, Message) and
            msg.to_id == account._id and msg.del_on_recipient):
        return False

    # don't show user their own unread stuff
    if msg.author_id == account._id:
        return False

    return True

resume_id = long(sys.argv[1]) if len(sys.argv) > 1 else None

msg_accounts = Account._query(sort=desc("_date"), data=True)

if resume_id:
    msg_accounts._filter(Account.c._id < resume_id)

for account in progress(fetch_things2(msg_accounts), estimate=resume_id):
    current_inbox_count = account.inbox_count
    unread_messages = list(queries.get_unread_inbox(account))

    if account._id % 100000 == 0:
        g.reset_caches()

    if not len(unread_messages):
        if current_inbox_count:
            account._incr('inbox_count', -current_inbox_count)
    else:
        msgs = Message._by_fullname(
            unread_messages,
            data=True,
            return_dict=False,
            ignore_missing=True,
        )
        kept_msgs = sum(1 for msg in msgs if _keep(msg, account))

        if kept_msgs or current_inbox_count:
            account._incr('inbox_count', kept_msgs - current_inbox_count)
