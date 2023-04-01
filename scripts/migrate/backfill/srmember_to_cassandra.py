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

from collections import defaultdict

import time

from r2.lib.db.operators import desc
from r2.lib.utils import fetch_things2, to36
from r2.models.subreddit import SRMember, SubscribedSubredditsByAccount


def get_query(after_user_id):
    q = SRMember._query(
        SRMember.c._name == "subscriber",
        SRMember.c._thing2_id < after_user_id,
        sort=desc("_thing2_id"),
    )
    return q


def get_srmembers(after_user_id):
    previous_user_id = None

    while True:
        # there isn't a good index on rel_id so we need to get a new query
        # for each batch rather than relying solely on fetch_things2
        q = get_query(after_user_id)
        users_seen = 0

        for rel in fetch_things2(q):
            user_id = rel._thing2_id

            if user_id != previous_user_id:
                if users_seen >= 20:
                    # set after_user_id to the previous id so we will pick up
                    # the query at this same point
                    after_user_id = previous_user_id
                    break

                users_seen += 1
                previous_user_id = user_id

            yield rel


def migrate_srmember_subscribers(after_user_id=39566712):
    columns = {}
    rowkey = None
    proc_time = time.time()

    for i, rel in enumerate(get_srmembers(after_user_id)):
        sr_id = rel._thing1_id
        user_id = rel._thing2_id
        action_date = rel._date
        new_rowkey = to36(user_id)

        if new_rowkey != rowkey and columns:
            SubscribedSubredditsByAccount._cf.insert(
                rowkey, columns, timestamp=1434403336829573)
            columns = {}

        columns[to36(sr_id)] = action_date
        rowkey = new_rowkey

        if i % 1000 == 0:
            new_proc_time = time.time()
            duration = new_proc_time - proc_time
            print "%s (%.3f): %s - %s" % (i, duration, user_id, action_date)
            proc_time = new_proc_time
