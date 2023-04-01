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

"""Store sitemaps in s3.

This module is uploads all subreddit sitemaps as well as the sitemap index
to s3. The basic idea is that amazon will be serving the static sitemaps for
us.

The binary data we send to s3 is a gzipped xml file. In addition we also
send the appropriate type and encoding headers so this is understood
correctly by the browser.

The only file expected to be used outside this module is:

store_sitemaps_in_s3(subreddits)

Even though the subreddits are expected to be generated and passed into this
function, the sitemap index is created here. The reasoning is that in order
to create the sitemap index we need to know how many sitemaps we have.
If we simply queried the subreddit iterator for it's length then we would
have to load all of the subreddits into memory, which would be ... bad.
"""


import gzip
from StringIO import StringIO

from boto.s3.connection import S3Connection
from boto.s3.key import Key
from pylons import app_globals as g

from r2.lib.sitemaps.generate import subreddit_sitemaps, sitemap_index


HEADERS = {
    'Content-Type': 'text/xml',
    'Content-Encoding': 'gzip',
}


def zip_string(string):
    zipbuffer = StringIO()
    with gzip.GzipFile(mode='w', fileobj=zipbuffer) as f:
        f.write(string)
    return zipbuffer.getvalue()


def upload_sitemap(key, sitemap):
    key.set_contents_from_string(zip_string(sitemap), headers=HEADERS)


def store_subreddit_sitemap(bucket, index, sitemap):
    key = Key(bucket)
    key.key = 'subreddit_sitemap/{0}.xml'.format(index)
    g.log.debug("Uploading %r", key)

    upload_sitemap(key, sitemap)


def store_sitemap_index(bucket, count):
    key = Key(bucket)
    key.key = g.sitemap_subreddit_keyname
    g.log.debug("Uploading %r", key)

    upload_sitemap(key, sitemap_index(count))


def store_sitemaps_in_s3(subreddits):
    s3conn = S3Connection()
    bucket = s3conn.get_bucket(g.sitemap_upload_s3_bucket, validate=False)

    sitemap_count = 0
    for i, sitemap in enumerate(subreddit_sitemaps(subreddits)):
        store_subreddit_sitemap(bucket, i, sitemap)
        sitemap_count += 1

    store_sitemap_index(bucket, sitemap_count)
