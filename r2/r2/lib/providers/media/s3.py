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

import mimetypes
import os
import re

import boto

from pylons import app_globals as g

from r2.lib.configparse import ConfigValue
from r2.lib.providers.media import MediaProvider


_NEVER = "Thu, 31 Dec 2037 23:59:59 GMT"


class S3MediaProvider(MediaProvider):
    """A media provider using Amazon S3.

    Credentials for uploading objects can be provided via `S3KEY_ID` and
    `S3SECRET_KEY`. If not provided, boto will search for credentials in
    alternate venues including environment variables and EC2 instance roles if
    on Amazon EC2.

    The `s3_media_direct` option configures how URLs are generated. When true,
    URLs will use Amazon's domain name meaning a zero-DNS configuration. If
    false, the bucket name will be assumed to be a valid domain name that is
    appropriately CNAME'd to S3 and URLs will be generated accordingly.

    If more than one bucket is provided in `s3_media_buckets`, items will be
    sharded out to the various buckets based on their filename. This allows for
    hostname parallelization in the non-direct HTTP case.

    """
    config = {
        ConfigValue.str: [
            "S3KEY_ID",
            "S3SECRET_KEY",
            "s3_media_domain",
        ],
        ConfigValue.bool: [
            "s3_media_direct",
        ],
        ConfigValue.tuple: [
            "s3_media_buckets",
            "s3_image_buckets",
        ],
    }

    buckets = {
        'thumbs': 's3_media_buckets',
        'stylesheets': 's3_media_buckets',
        'icons': 's3_media_buckets',
        'previews': 's3_image_buckets',
    }
 
    def _get_bucket(self, bucket_name, validate=False):
     
        s3 = boto.connect_s3(g.S3KEY_ID or None, g.S3SECRET_KEY or None)
        bucket = s3.get_bucket(bucket_name, validate=validate)

        return bucket

    def _get_bucket_key_from_url(self, url):
        if g.s3_media_domain in url:
            r_bucket = re.compile('.*\://(?:%s.)?([^\/]+)' % g.s3_media_domain)
        else:
            r_bucket = re.compile('.*\://?([^\/]+)')

        bucket_name = r_bucket.findall(url)[0]
        key_name = url.split('/')[-1]

        return bucket_name, key_name
     
    def make_inaccessible(self, url):
        """Make the content unavailable, but do not remove."""
        bucket_name, key_name = self._get_bucket_key_from_url(url)

        timer = g.stats.get_timer("providers.s3.key_set_private")
        timer.start()

        bucket = self._get_bucket(bucket_name, validate=False)

        key = bucket.get_key(key_name)
        if key:
            # set the file as private, but don't delete it, if it exists
            key.set_acl('private')

        timer.stop()

        return True

    def put(self, category, name, contents, headers=None):
        buckets = getattr(g, self.buckets[category])
        # choose a bucket based on the filename
        name_without_extension = os.path.splitext(name)[0]
        index = ord(name_without_extension[-1]) % len(buckets)
        bucket_name = buckets[index]

        # guess the mime type
        mime_type, encoding = mimetypes.guess_type(name)

        # build up the headers
        s3_headers = {
            "Content-Type": mime_type,
            "Expires": _NEVER,
        }
        if headers:
            s3_headers.update(headers)

        # send the key
        bucket = self._get_bucket(bucket_name, validate=False)
        key = bucket.new_key(name)

        if isinstance(contents, basestring):
            set_fn = key.set_contents_from_string
        else:
            set_fn = key.set_contents_from_file

        set_fn(
            contents,
            headers=s3_headers,
            policy="public-read",
            reduced_redundancy=True,
            replace=True,
        )

        if g.s3_media_direct:
            return "http://%s/%s/%s" % (g.s3_media_domain, bucket_name, name)
        else:
            return "http://%s/%s" % (bucket_name, name)

    def purge(self, url):
        """Deletes the key as specified by the url"""
        bucket_name, key_name = self._get_bucket_key_from_url(url)

        timer = g.stats.get_timer("providers.s3.key_set_private")
        timer.start()

        bucket = self._get_bucket(bucket_name, validate=False)

        key_name = url.split('/')[-1]
        key = bucket.get_key(key_name)
        if key:
            # delete the key if it exists
            key.delete()

        timer.stop()

        return True
