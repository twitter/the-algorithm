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
"""Fill in the num_gildings for users

This is used to determine which gilding trophy level they should have.
"""
from pylons import app_globals as g

from r2.models import Account
from r2.models.gold import gold_table, ENGINE
from r2admin.lib.trophies import add_to_trophy_queue
from sqlalchemy.sql.expression import select
from sqlalchemy.sql.functions import count as sa_count


def update_num_gildings(update_trophy=True, user_id=None):
    """Returns total number of link, comment, and user gildings"""
    query = (select([gold_table.c.paying_id, sa_count(gold_table.c.trans_id)])
        .where(gold_table.c.trans_id.like('X%'))
        .group_by(gold_table.c.paying_id)
        .order_by(sa_count(gold_table.c.trans_id).desc())
    )
    if user_id:
        query = query.where(gold_table.c.paying_id == str(user_id))

    rows = ENGINE.execute(query)
    total_updated = 0
    for paying_id, count in rows:
        try:
            a = Account._byID(int(paying_id), data=True)
            a.num_gildings = count
            a._commit()
            total_updated += 1
            #if 'server seconds paid' for are public, update gilding trophies
            if update_trophy and a.pref_public_server_seconds:
                add_to_trophy_queue(a, "gilding")
        except:
            g.log.debug("update_num_gildings: paying_id %s is invalid" % paying_id)

    g.log.debug("update_num_gildings: updated %s accounts" % total_updated)
