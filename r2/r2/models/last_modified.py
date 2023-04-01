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

import datetime

from pylons import app_globals as g
from pycassa.system_manager import ASCII_TYPE, DATE_TYPE

from r2.lib.db import tdb_cassandra
from r2.lib.utils import tup


class LastModified(tdb_cassandra.View):
    _use_db = True
    _value_type = "date"
    _connection_pool = "main"
    _read_consistency_level = tdb_cassandra.CL.ONE
    _extra_schema_creation_args = dict(key_validation_class=ASCII_TYPE,
                                       default_validation_class=DATE_TYPE)

    @classmethod
    def touch(cls, fullname, names):
        names = tup(names)
        now = datetime.datetime.now(g.tz)
        values = dict.fromkeys(names, now)
        cls._set_values(fullname, values)
        return now

    @classmethod
    def get(cls, fullname, name, touch_if_not_set=False):
        try:
            obj = cls._byID(fullname)
        except tdb_cassandra.NotFound:
            if touch_if_not_set:
                time = cls.touch(fullname, name)
                return time
            else:
                return None

        return getattr(obj, name, None)

    @classmethod
    def get_multi(cls, fullnames, name):
        res = cls._byID(fullnames, return_dict=True)

        return dict((k, getattr(v, name, None))
                    for k, v in res.iteritems())
