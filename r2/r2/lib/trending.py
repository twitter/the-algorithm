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

import re

from pylons import app_globals as g

from r2.models.keyvalue import NamedGlobals
from r2.models import NotFound, Subreddit, Thing

_SUBREDDIT_RE = re.compile(r'/r/(\w+)')
TRENDING_SUBREDDITS_KEY = 'trending_subreddits'


def get_trending_subreddits():
    return NamedGlobals.get(TRENDING_SUBREDDITS_KEY, None)


def update_trending_subreddits():
    try:
        trending_sr = Subreddit._by_name(g.config['trending_sr'])
    except NotFound:
        g.log.info("Unknown trending subreddit %r or trending_sr config "
                   "not set. Not updating.", g.config['trending_sr'])
        return

    link = _get_newest_link(trending_sr)
    if not link:
        g.log.info("Unable to find active link in subreddit %r. Not updating.",
                   g.config['trending_sr'])
        return

    subreddit_names = _SUBREDDIT_RE.findall(link.title)
    trending_data = {
        'subreddit_names': subreddit_names,
        'permalink': link.make_permalink(trending_sr),
        'link_id': link._id,
    }
    NamedGlobals.set(TRENDING_SUBREDDITS_KEY, trending_data)
    g.log.debug("Trending subreddit data set to %r", trending_data)


def _get_newest_link(sr):
    for fullname in sr.get_links('new', 'all'):
        link = Thing._by_fullname(fullname, data=True)
        if not link._spam and not link._deleted:
            return link

    return None
