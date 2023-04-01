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

"""Generates all the data used in making sitemaps and sitemap links.

Currently only supports subreddit links but will soon support comment links.
"""

import tempfile

from boto.s3.connection import S3Connection
from pylons import app_globals as g

from r2.lib.hadoop_decompress import hadoop_decompress


def _read_subreddit_etl_from_s3(s3path):
    s3conn = S3Connection()
    bucket = s3conn.get_bucket(s3path.bucket, validate=False)
    s3keys = bucket.list(s3path.key)

    key_count = 0
    for s3key in s3keys:
        g.log.info("Importing key %r", s3key)

        with tempfile.TemporaryFile(mode='rw+b') as ntf_download:
            with tempfile.TemporaryFile(mode='rw+b') as ntf_decompress:

                # download it
                g.log.debug("Downloading %r", s3key)
                s3key.get_contents_to_file(ntf_download)

                # decompress it
                ntf_download.flush()
                ntf_download.seek(0)
                g.log.debug("Decompressing %r", s3key)
                hadoop_decompress(ntf_download, ntf_decompress)
                ntf_decompress.flush()
                ntf_decompress.seek(0)

                # import it
                g.log.debug("Starting import of %r", s3key)
                for line in ntf_decompress:
                    yield line
        key_count += 1

    if key_count == 0:
        raise ValueError('{0} contains no readable keys.'.format(s3path))


def find_all_subreddits(s3path):
    for line in _read_subreddit_etl_from_s3(s3path):
        _, subreddit, __ = line.split('\x01')
        yield subreddit
