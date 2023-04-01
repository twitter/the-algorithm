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

from datetime import datetime

from pycassa.util import convert_uuid_to_time
from pylons import app_globals as g

from r2.models.trylater import TryLaterBySubject


class UserTempBan(object):
    @classmethod
    def schedule(cls, victim, duration):
        TryLaterBySubject.schedule(
            cls.cancel_rowkey(),
            cls.cancel_colkey(victim.name),
            victim._fullname,
            duration,
        )

    @classmethod
    def unschedule(cls, victim):
        TryLaterBySubject.unschedule(
            cls.cancel_rowkey(),
            cls.cancel_colkey(victim.name),
            cls.schedule_rowkey(),
        )

    @classmethod
    def search(cls, subjects):
        results = TryLaterBySubject.search(cls.cancel_rowkey(), subjects)

        def convert_uuid_to_datetime(uu):
            return datetime.fromtimestamp(convert_uuid_to_time(uu), g.tz)
        return {
            name: convert_uuid_to_datetime(uu)
                for name, uu in results.iteritems()
        }

    @classmethod
    def cancel_colkey(cls, name):
        return name


class TempTimeout(UserTempBan):
    @classmethod
    def cancel_rowkey(cls):
        return "untimeout"

    @classmethod
    def schedule_rowkey(cls):
        return "untimeout"
