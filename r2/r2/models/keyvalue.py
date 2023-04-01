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

import json

from pycassa import NotFoundException
from pycassa.system_manager import UTF8_TYPE

from r2.lib.db.tdb_cassandra import ThingMeta

NoDefault = object()

class KeyValueStore(object):
    __metaclass__ = ThingMeta

    _use_db = False
    _cf_name = None
    _type_prefix = None
    _compare_with = UTF8_TYPE

    _extra_schema_creation_args = dict(
        key_validation_class=UTF8_TYPE,
        default_validation_class=UTF8_TYPE,
    )

    @classmethod
    def get(cls, key, default=NoDefault):
        try:
            return json.loads(cls._cf.get(key)["data"])
        except NotFoundException:
            if default is not NoDefault:
                return default
            else:
                raise

    @classmethod
    def set(cls, key, data):
        cls._cf.insert(key, {"data": json.dumps(data)})


class NamedGlobals(KeyValueStore):
    _use_db = True

