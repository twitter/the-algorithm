#!/usr/bin/python
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


import os
import argparse
import mimetypes
import urlparse

import boto


NEVER = 'Thu, 31 Dec 2037 23:59:59 GMT'


def upload(static_root, bucket_url):
    s3 = boto.connect_s3()
    bucket = s3.get_bucket(bucket_url.netloc, validate=False)

    # build a list of files already in the bucket
    print "checking existing files on s3..."
    remote_files = {key.name : key.etag.strip('"') for key in bucket.list()}

    # upload local files not already in the bucket
    for root, dirs, files in os.walk(static_root):
        for file in files:
            absolute_path = os.path.join(root, file)
            relative_path = os.path.relpath(absolute_path, start=static_root)
            key_name = os.path.join(bucket_url.path, relative_path).lstrip("/")

            type, encoding = mimetypes.guess_type(file)
            if not type:
                continue
            headers = {}
            headers['Expires'] = NEVER
            headers['Content-Type'] = type
            if encoding:
                headers['Content-Encoding'] = encoding

            key = bucket.new_key(key_name)
            with open(absolute_path, 'rb') as f:
                etag, base64_tag = key.compute_md5(f)

                # don't upload the file if it already exists unmodified in the bucket
                if remote_files.get(key_name, None) == etag:
                    continue

                print "uploading", key_name, "to S3..."
                key.set_contents_from_file(
                    f,
                    headers=headers,
                    policy='public-read',
                    md5=(etag, base64_tag),
                )

    print "all done"


def s3_url(text):
    parsed = urlparse.urlparse(text)
    if parsed.scheme != "s3":
        raise ValueError("not an s3 url")
    if parsed.params or parsed.query or parsed.fragment:
        raise ValueError("params, query, and fragment not supported")
    return parsed


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("root")
    parser.add_argument("bucket", type=s3_url)
    args = parser.parse_args()
    upload(args.root, args.bucket)


if __name__ == "__main__":
    main()
