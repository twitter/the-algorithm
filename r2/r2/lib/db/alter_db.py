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

import tdb_sql
import sqlalchemy as sa

def thing_tables():
    for type in tdb_sql.types_id.values():
        yield type.thing_table

    for table in tdb_sql.extra_thing_tables.values():
        yield table

def rel_tables():
    for type in tdb_sql.rel_types_id.values():
        yield type.rel_table[0]

def dtables():
    for type in tdb_sql.types_id.values():
        yield type.data_table[0]

    for type in tdb_sql.rel_types_id.values():
        yield type.rel_table[3]

def exec_all(command, data=False, rel = False, print_only = False):
    if data:
        tables = dtables()
    elif rel:
        tables = rel_tables()
    else:
        tables = thing_tables()

    for tt in tables:
        #print tt
        engine = tt.bind
        if print_only:
            print command % dict(type=tt.name)
        else:
            try:
                engine.execute(command % dict(type=tt.name))
            except:
                print "FAILED!"

"alter table %(type)s add primary key (thing_id, key)"
"drop index idx_thing_id_%(type)s"

"create index concurrently idx_thing1_name_date_%(type)s on %(type)s (thing1_id, name, date);"
