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
from datetime import datetime

from pycassa.system_manager import UTF8_TYPE, TIME_UUID_TYPE
from pycassa.util import convert_uuid_to_time
from pylons import app_globals as g

from r2.lib.db import tdb_cassandra


class AdminNotesBySystem(tdb_cassandra.View):
    _use_db = True
    _read_consistency_level = tdb_cassandra.CL.QUORUM
    _write_consistency_level = tdb_cassandra.CL.ONE
    _compare_with = TIME_UUID_TYPE
    _extra_schema_creation_args = {
        "key_validation_class": UTF8_TYPE,
        "default_validation_class": UTF8_TYPE,
    }

    @classmethod
    def add(cls, system_name, subject, note, author, when=None):
        if not when:
            when = datetime.now(g.tz)
        jsonpacked = json.dumps({"note": note, "author": author})
        updatedict = {when: jsonpacked}
        key = cls._rowkey(system_name, subject)
        cls._set_values(key, updatedict)

    @classmethod
    def in_display_order(cls, system_name, subject):
        key = cls._rowkey(system_name, subject)
        try:
            query = cls._cf.get(key, column_reversed=True)
        except tdb_cassandra.NotFoundException:
            return []
        result = []
        for uuid, json_blob in query.iteritems():
            when = datetime.fromtimestamp(convert_uuid_to_time(uuid), tz=g.tz)
            payload = json.loads(json_blob)
            payload['when'] = when
            result.append(payload)
        return result

    @classmethod
    def _rowkey(cls, system_name, subject):
        return "%s:%s" % (system_name, subject)
