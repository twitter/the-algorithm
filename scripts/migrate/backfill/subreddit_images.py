
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

import urllib2

from pylons import app_globals as g

from r2.lib.db.operators import desc
from r2.lib.utils import fetch_things2
from r2.lib.media import upload_media
from r2.models.subreddit import Subreddit
from r2.models.wiki import WikiPage, ImagesByWikiPage


all_subreddits = Subreddit._query(sort=desc("_date"))
for sr in fetch_things2(all_subreddits):
    images = sr.images.copy()
    images.pop("/empties/", None)

    if not images:
        continue

    print 'Processing /r/%s (id36: %s)' % (sr.name, sr._id36)

    # upgrade old-style image ids to urls
    for name, image_url in images.items():
        if not isinstance(image_url, int):
            continue

        print "  upgrading image %r" % image_url
        url = "http://%s/%s_%d.png" % (g.s3_old_thumb_bucket,
                                       sr._fullname, image_url)
        image_data = urllib2.urlopen(url).read()
        new_url = upload_media(image_data, file_type=".png")
        images[name] = new_url

    # use a timestamp of zero to make sure that we don't overwrite any changes
    # from live dual-writes.
    rowkey = WikiPage.id_for(sr, "config/stylesheet")
    ImagesByWikiPage._cf.insert(rowkey, images, timestamp=0)
