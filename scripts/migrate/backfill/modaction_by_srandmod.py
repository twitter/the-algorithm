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
from r2.lib.db.operators import asc
from r2.lib.utils import fetch_things2
from r2.models import ModAction, ModActionBySRActionMod, Subreddit

def backfill(after=None):
    q = Subreddit._query(sort=asc('_date'))
    if after:
        sr = Subreddit._by_name(after)
        q = q._after(sr)

    for sr in fetch_things2(q):
        backfill_sr(sr)


def backfill_sr(sr):
    print "processing %s" % sr.name
    after = None
    count = 100
    q = ModAction.get_actions(sr, after=after, count=count)
    actions = list(q)
    while actions:
        for ma in actions:
            ModActionBySRActionMod.add_object(ma)
        q = ModAction.get_actions(sr, after=actions[-1], count=count)
        actions = list(q)
