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

from r2.models import Link, Subreddit
from r2.lib import utils
from r2.lib.db.operators import desc
from pylons import config
from pylons import app_globals as g


count_period = g.rising_period

#stubs

def incr_counts(wrapped):
    pass

def get_link_counts(period = count_period):
    links = Link._query(Link.c._date >= utils.timeago(period),
                        limit=50, data = True)
    return dict((l._fullname, (0, l.sr_id)) for l in links)

def get_sr_counts():
    srs = utils.fetch_things2(Subreddit._query(sort=desc("_date")))

    return dict((sr._fullname, sr._ups) for sr in srs)


if config['r2.import_private']:
    from r2admin.lib.count import *
