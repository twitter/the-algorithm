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

"""
Script for backunfilling data from deleted users.

You might want to change `run_changed()` to `run_changed(use_safe_get=True)`
in `reddit-consumer-cloudsearch_q.conf` unless you're sure *everything* in
`LinksByAccount` is a valid `Link`. Otherwise, you're gonna back up the
cloudsearch queue.
"""

import time
import sys

from r2.lib.db.operators import desc
from r2.lib.utils import fetch_things2, progress
from r2.lib import amqp
from r2.models import Account


def get_queue_length(name):
    # https://stackoverflow.com/questions/1038318/check-rabbitmq-queue-size-from-client
    chan = amqp.connection_manager.get_channel()
    queue_response = chan.queue_declare(name, passive=True)
    return queue_response[1]


def backfill_deleted_accounts(resume_id=None):
    del_accts = Account._query(Account.c._deleted == True, sort=desc('_date'))
    if resume_id:
        del_accts._filter(Account.c._id < resume_id)

    for i, account in enumerate(progress(fetch_things2(del_accts))):
        # Don't kill the rabbit! Wait for the relevant queues to calm down.
        if i % 1000 == 0:
            del_len = get_queue_length('del_account_q')
            cs_len = get_queue_length('cloudsearch_changes')
            while (del_len > 1000 or
                    cs_len > 10000):
                sys.stderr.write(("CS: %d, DEL: %d" % (cs_len, del_len)) + "\n")
                sys.stderr.flush()
                time.sleep(1)
                del_len = get_queue_length('del_account_q')
                cs_len = get_queue_length('cloudsearch_changes')
        amqp.add_item('account_deleted', account._fullname)
