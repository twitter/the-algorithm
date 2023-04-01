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

from r2.models import Subreddit
from r2.lib.memoize import memoize
from r2.lib.db.operators import desc
from r2.lib import utils
from r2.lib.db import tdb_cassandra
from r2.lib.cache import CL_ONE

class SubredditsByPartialName(tdb_cassandra.View):
    _use_db = True
    _value_type = 'pickle'
    _connection_pool = 'main'
    _read_consistency_level = CL_ONE

def load_all_reddits():
    query_cache = {}

    q = Subreddit._query(Subreddit.c.type == 'public',
                         Subreddit.c._spam == False,
                         Subreddit.c._downs > 1,
                         sort = (desc('_downs'), desc('_ups')),
                         data = True)
    for sr in utils.fetch_things2(q):
        if sr.quarantine:
            continue
        name = sr.name.lower()
        for i in xrange(len(name)):
            prefix = name[:i + 1]
            names = query_cache.setdefault(prefix, [])
            if len(names) < 10:
                names.append((sr.name, sr.over_18))

    for name_prefix, subreddits in query_cache.iteritems():
        SubredditsByPartialName._set_values(name_prefix, {'tups': subreddits})

def search_reddits(query, include_over_18=True):
    query = str(query.lower())

    try:
        result = SubredditsByPartialName._byID(query)
        return [name for (name, over_18) in getattr(result, 'tups', [])
                if not over_18 or include_over_18]
    except tdb_cassandra.NotFound:
        return []

@memoize('popular_searches', stale=True, time=3600)
def popular_searches(include_over_18=True):
    top_reddits = Subreddit._query(Subreddit.c.type == 'public',
                                   sort = desc('_downs'),
                                   limit = 100,
                                   data = True)
    top_searches = {}
    for sr in top_reddits:
        if sr.quarantine:
            continue
        if sr.over_18 and not include_over_18:
            continue
        name = sr.name.lower()
        for i in xrange(min(len(name), 3)):
            query = name[:i + 1]
            r = search_reddits(query, include_over_18)
            top_searches[query] = r
    return top_searches

