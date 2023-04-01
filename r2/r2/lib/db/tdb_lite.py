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

import sqlalchemy as sa
import cPickle as pickle

class tdb_lite(object):
    def __init__(self, gc):
        self.gc = gc

    def make_metadata(self, engine):
        metadata = sa.MetaData(engine)
        metadata.bind.echo = self.gc.sqlprinting
        return metadata

    def index_str(self, table, name, on, where = None):
        index_str = 'create index idx_%s_' % name
        index_str += table.name
        index_str += ' on '+ table.name + ' (%s)' % on
        if where:
            index_str += ' where %s' % where
        return index_str

    def create_table(self, table, index_commands=None):
        t = table
        if self.gc.db_create_tables:
            #@@hackish?
            if not t.bind.has_table(t.name):
                t.create(checkfirst = False)
                if index_commands:
                    for i in index_commands:
                        t.bind.execute(i)

    def py2db(self, val, return_kind=False):
        if isinstance(val, bool):
            val = 't' if val else 'f'
            kind = 'bool'
        elif isinstance(val, (str, unicode)):
            kind = 'str'
        elif isinstance(val, (int, float, long)):
            kind = 'num'
        elif val is None:
            kind = 'none'
        else:
            kind = 'pickle'
            val = pickle.dumps(val)

        if return_kind:
            return (val, kind)
        else:
            return val

    def db2py(self, val, kind):
        if kind == 'bool':
            val = True if val is 't' else False
        elif kind == 'num':
            try:
                val = int(val)
            except ValueError:
                val = float(val)
        elif kind == 'none':
            val = None
        elif kind == 'pickle':
            val = pickle.loads(val)

        return val
