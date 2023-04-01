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

from datetime import timedelta

from pycassa.cassandra.ttypes import NotFoundException
from pycassa.system_manager import ASCII_TYPE, UTF8_TYPE

from r2.lib.db import tdb_cassandra


class PerformedRulesByThing(tdb_cassandra.View):
    """Used to track which rules have previously matched a specific item."""
    _use_db = True
    _connection_pool = "main"
    _read_consistency_level = tdb_cassandra.CL.QUORUM
    _write_consistency_level = tdb_cassandra.CL.QUORUM
    _ttl = timedelta(days=3)
    _extra_schema_creation_args = {
        "key_validation_class": ASCII_TYPE,
        "column_name_class": ASCII_TYPE,
        "default_validation_class": UTF8_TYPE,
    }

    @classmethod
    def _rowkey(cls, thing):
        return thing._fullname

    @classmethod
    def mark_performed(cls, thing, rule):
        rowkey = cls._rowkey(thing)
        cls._set_values(rowkey, {rule.unique_id: ''})

    @classmethod
    def get_already_performed(cls, thing):
        rowkey = cls._rowkey(thing)
        try:
            columns = cls._cf.get(rowkey)
        except NotFoundException:
            return []

        return columns.keys()
