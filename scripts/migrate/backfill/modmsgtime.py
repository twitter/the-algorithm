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
"""Ensure modmsgtime is properly set on all accounts.

See the comment in Account.is_moderator_somewhere for possible values of this
attribute now.

"""

from r2.lib.db.operators import desc
from r2.lib.utils import fetch_things2, progress
from r2.models import Account, Subreddit


all_accounts = Account._query(sort=desc("_date"))
for account in progress(fetch_things2(all_accounts)):
    is_moderator_somewhere = bool(Subreddit.reverse_moderator_ids(account))
    if is_moderator_somewhere:
        if not account.modmsgtime:
            account.modmsgtime = False
        else:
            # the account already has a date for modmsgtime meaning unread mail
            pass
    else:
        account.modmsgtime = None
    account._commit()
