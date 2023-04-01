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
Fix the urls of previously-uploaded preview images so they all work.
"""

import sys

import boto
import pycassa

from boto.s3.key import Key
from pylons import app_globals as g

from r2.lib.media import _get_scrape_url
from r2.lib.providers.media.s3 import S3MediaProvider
from r2.lib.utils import UrlParser
from r2.models.link import Link, LinksByImage
from r2.models.media_cache import MediaByURL

def good_preview_object(preview_object):
    if not preview_object or not 'url' in preview_object:
        print '  aborting - bad preview object: %s' % preview_object
        return False
    if not preview_object['url']:
        print '  aborting - bad preview url: %s' % preview_object['url']
        return False
    return True

s3 = boto.connect_s3(g.S3KEY_ID or None, g.S3SECRET_KEY or None)

for uid, columns in LinksByImage._cf.get_range():
# When resuming, use:
#for uid, columns in LinksByImage._cf.get_range(start='<uid>'):
    print 'Looking at image %s' % uid
    link_ids = columns.keys()
    links = Link._byID36(link_ids, return_dict=False, data=True)
    if not links:
        continue

    # Pull information about the image from the first link (they *should* all
    # be the same).
    link = links[0]
    preview_object = link.preview_object
    if not good_preview_object(preview_object):
        continue

    u = UrlParser(preview_object['url'])
    if preview_object['url'].startswith(g.media_fs_base_url_http):
        # Uploaded to the local filesystem instead of s3.  Should only be in
        # dev.
        print '  non-s3 image'
        continue
    elif u.hostname == 's3.amazonaws.com':
        parts = u.path.lstrip('/').split('/')

        bucket = parts.pop(0)
        filename = '/'.join(parts)
    else:
        bucket = u.hostname
        filename = u.path.lstrip('/')

    print '  bucket: %s' % bucket
    print '  filename: %s' % filename

    if bucket in g.s3_image_buckets:
        print '  skipping - already in correct place'
        continue

    k = Key(s3.get_bucket(bucket))
    k.key = filename
    k.copy(s3.get_bucket(g.s3_image_buckets[0]), filename)
    url = 'http://s3.amazonaws.com/%s/%s' % (g.s3_image_buckets[0], filename)
    print '  new url: %s' % url
    for link in links:
        print '  altering Link %s' % link
        if not good_preview_object(link.preview_object):
            continue
        if not link.preview_object == preview_object:
            print "  aborting - preview objects don't match"
            print '    first: %s' % preview_object
            print '    ours:  %s' % link.preview_object
            continue

        link.preview_object['url'] = url
        link._commit()
        # Guess at the key that'll contain the (now-incorrect) cache of the
        # preview object so we can delete it and not end up inserting old info
        # into new Links.
        #
        # These parameters are what's used in most of the code; the only place
        # they're overridden is for promoted links, where they could be
        # anything.  We'll just have to deal with those as they come up.
        image_url = _get_scrape_url(link)
        cache_key = MediaByURL._rowkey(image_url, autoplay=False, maxwidth=600)
        print '  deleting cache with key %s' % cache_key
        cache = MediaByURL(_id=cache_key)
        cache._committed = True
        try:
            cache._destroy()
        except pycassa.cassandra.ttypes.InvalidRequestException as e:
            print '    skipping cache deletion (%s)' % e.why
            continue
    # Delete *after* we've updated all the Links so they'll continue to work
    # while we're in the migration process.
    k.delete()
