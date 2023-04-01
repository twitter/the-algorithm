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

from r2.lib import count
from r2.models import Subreddit


def set_downs():
    sr_counts = count.get_sr_counts()
    names = [k for k, v in sr_counts.iteritems() if v != 0]
    srs = Subreddit._by_fullname(names)
    for name in names:
        sr,c = srs[name], sr_counts[name]
        if c != sr._downs and c > 0:
            sr._downs = max(c, 0)
            sr._commit()


def run():
    set_downs()
