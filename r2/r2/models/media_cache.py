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
# All portions of the code written by reddit are Copyright (c) 2013-2015 reddit
# Inc. All Rights Reserved.
###############################################################################

import collections
import json

from datetime import (
    datetime,
    timedelta,
)
from pycassa.system_manager import ASCII_TYPE, UTF8_TYPE
from r2.lib.db import tdb_cassandra


Media = collections.namedtuple('_Media', ("media_object",
                                          "secure_media_object",
                                          "preview_object",
                                          "thumbnail_url",
                                          "thumbnail_size"))

ERROR_MEDIA = Media(None, None, None, None, None)


class MediaByURL(tdb_cassandra.View):
    _use_db = True
    _connection_pool = 'main'
    _ttl = timedelta(minutes=720)

    _read_consistency_level = tdb_cassandra.CL.QUORUM
    _write_consistency_level = tdb_cassandra.CL.QUORUM
    _int_props = {"thumbnail_width", "thumbnail_height"}
    _date_props = {"last_modified"}
    _extra_schema_creation_args = {
        "key_validation_class": ASCII_TYPE,
        "column_name_class": UTF8_TYPE,
    }

    _defaults = {
        "state": "enqueued",
        "error": "",
        "thumbnail_url": "",
        "thumbnail_width": 0,
        "thumbnail_height": 0,
        "media_object": "",
        "secure_media_object": "",
        "preview_object": "",
        "last_modified": datetime.utcfromtimestamp(0),
    }

    @classmethod
    def _rowkey(cls, url, **kwargs):
        return (
            url +
            # pipe is not allowed in URLs, so use it as a delimiter
            "|" +

            # append the extra cache keys in kwargs as a canonical JSON string
            json.dumps(
                kwargs,
                ensure_ascii=True,
                encoding="ascii",
                indent=None,
                separators=(",", ":"),
                sort_keys=True,
            )
        )

    @classmethod
    def add_placeholder(cls, url, **kwargs):
        rowkey = cls._rowkey(url, **kwargs)
        cls._set_values(rowkey, {
            "state": "enqueued",
            "error": "",
            "last_modified": datetime.utcnow(),
        })

    @classmethod
    def add(cls, url, media, **kwargs):
        rowkey = cls._rowkey(url, **kwargs)
        columns = cls._defaults.copy()

        columns.update({
            "state": "processed",
            "error": "",
            "last_modified": datetime.utcnow(),
        })

        if media.thumbnail_url and media.thumbnail_size:
            columns.update({
                "thumbnail_url": media.thumbnail_url,
                "thumbnail_width": media.thumbnail_size[0],
                "thumbnail_height": media.thumbnail_size[1],
            })

        if media.media_object:
            columns.update({
                "media_object": json.dumps(media.media_object),
            })

        if media.secure_media_object:
            columns.update({
                "secure_media_object": (json.
                                        dumps(media.secure_media_object)),
            })

        if media.preview_object:
            columns.update({
                "preview_object": json.dumps(media.preview_object),
            })

        cls._set_values(rowkey, columns)

    @classmethod
    def add_error(cls, url, error, **kwargs):
        rowkey = cls._rowkey(url, **kwargs)
        columns = {
            "error": error,
            "state": "processed",
            "last_modified": datetime.utcnow(),
        }
        cls._set_values(rowkey, columns)

    @classmethod
    def get(cls, url, max_cache_age=None, **kwargs):
        rowkey = cls._rowkey(url, **kwargs)
        try:
            temp = cls._byID(rowkey)

            # Return None if this cache entry is too old
            if (max_cache_age is not None and
                datetime.datetime.utcnow() - temp.last_modified >
                max_cache_age):
                return None
            else:
                return temp
        except tdb_cassandra.NotFound:
            return None

    @property
    def media(self):
        if self.state == "processed":
            if not self.error:
                media_object = secure_media_object = preview_object = None
                thumbnail_url = thumbnail_size = None

                if (self.thumbnail_width and self.thumbnail_height and
                    self.thumbnail_url):
                    thumbnail_url = self.thumbnail_url
                    thumbnail_size = (self.thumbnail_width,
                                      self.thumbnail_height)

                if self.media_object:
                    media_object = json.loads(self.media_object)

                if self.secure_media_object:
                    secure_media_object = json.loads(self.secure_media_object)

                if self.preview_object:
                    preview_object = json.loads(self.preview_object)

                return Media(media_object, secure_media_object, preview_object,
                             thumbnail_url, thumbnail_size)
            else:
                return ERROR_MEDIA
        else:
            return None
