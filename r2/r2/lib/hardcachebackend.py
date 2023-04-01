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

from pylons import app_globals as g
from datetime import timedelta as timedelta
from datetime import datetime
import sqlalchemy as sa
from r2.lib.db.tdb_lite import tdb_lite
import pytz
import random

COUNT_CATEGORY = 'hc_count'
ELAPSED_CATEGORY = 'hc_elapsed'
TZ = pytz.timezone("MST")

def expiration_from_time(time):
    if time <= 0:
        raise ValueError ("HardCache items *must* have an expiration time")
    return datetime.now(TZ) + timedelta(0, time)

class HardCacheBackend(object):
    def __init__(self, gc):
        self.tdb = tdb_lite(gc)
        self.profile_categories = {}
        TZ = gc.display_tz

        def _table(metadata):
            return sa.Table(gc.db_app_name + '_hardcache', metadata,
                            sa.Column('category', sa.String, nullable = False,
                                      primary_key = True),
                            sa.Column('ids', sa.String, nullable = False,
                                      primary_key = True),
                            sa.Column('value', sa.String, nullable = False),
                            sa.Column('kind', sa.String, nullable = False),
                            sa.Column('expiration',
                                      sa.DateTime(timezone = True),
                                      nullable = False)
                            )
        enginenames_by_category = {}
        all_enginenames = set()
        for item in gc.hardcache_categories:
            chunks = item.split(":")
            if len(chunks) < 2:
                raise ValueError("Invalid hardcache_overrides")
            category = chunks.pop(0)
            enginenames_by_category[category] = []
            for c in chunks:
                if c == '!profile':
                    self.profile_categories[category] = True
                elif c.startswith("!"):
                    raise ValueError("WTF is [%s] in hardcache_overrides?" % c)
                else:
                    all_enginenames.add(c)
                    enginenames_by_category[category].append(c)

        assert('*' in enginenames_by_category.keys())

        engines_by_enginename = {}
        for enginename in all_enginenames:
            engine = gc.dbm.get_engine(enginename)
            md = self.tdb.make_metadata(engine)
            table = _table(md)
            indstr = self.tdb.index_str(table, 'expiration', 'expiration')
            self.tdb.create_table(table, [ indstr ])
            engines_by_enginename[enginename] = table

        self.mapping = {}
        for category, enginenames in enginenames_by_category.iteritems():
            self.mapping[category] = [ engines_by_enginename[e]
                                       for e in enginenames]

    def engine_by_category(self, category, type="master"):
        if category not in self.mapping:
            category = '*'
        engines = self.mapping[category]
        if type == 'master':
            return engines[0]
        elif type == 'readslave':
            return random.choice(engines[1:])
        else:
            raise ValueError("invalid type %s" % type)

    def profile_start(self, operation, category):
        if category == COUNT_CATEGORY:
            return None

        if category == ELAPSED_CATEGORY:
            return None

        if category in self.mapping:
            effective_category = category
        else:
            effective_category = '*'

        if effective_category not in self.profile_categories:
            return None

        return (datetime.now(TZ), operation, category)

    def profile_stop(self, t):
        if t is None:
            return

        start_time, operation, category = t

        end_time = datetime.now(TZ)

        period = end_time.strftime("%Y/%m/%d_%H:%M")[:-1] + 'x'

        elapsed = end_time - start_time
        msec = elapsed.seconds * 1000 + elapsed.microseconds / 1000

        ids = "-".join((operation, category, period))

        self.add(COUNT_CATEGORY, ids, 0, time=86400)
        self.add(ELAPSED_CATEGORY, ids, 0, time=86400)

        self.incr(COUNT_CATEGORY, ids, time=86400)
        self.incr(ELAPSED_CATEGORY, ids, time=86400, delta=msec)


    def set(self, category, ids, val, time):

        self.delete(category, ids) # delete it if it already exists

        value, kind = self.tdb.py2db(val, True)

        expiration = expiration_from_time(time)

        prof = self.profile_start('set', category)

        engine = self.engine_by_category(category, "master")

        engine.insert().execute(
            category=category,
            ids=ids,
            value=value,
            kind=kind,
            expiration=expiration
            )

        self.profile_stop(prof)

    def add(self, category, ids, val, time=0):
        self.delete_if_expired(category, ids)

        expiration = expiration_from_time(time)

        value, kind = self.tdb.py2db(val, True)

        prof = self.profile_start('add', category)

        engine = self.engine_by_category(category, "master")

        try:
            rp = engine.insert().execute(
                category=category,
                ids=ids,
                value=value,
                kind=kind,
                expiration=expiration
                )
            self.profile_stop(prof)
            return value

        except sa.exc.IntegrityError, e:
            self.profile_stop(prof)
            return self.get(category, ids, force_write_table=True)

    def incr(self, category, ids, time=0, delta=1):
        self.delete_if_expired(category, ids)

        expiration = expiration_from_time(time)

        prof = self.profile_start('incr', category)

        engine = self.engine_by_category(category, "master")

        rp = engine.update(sa.and_(engine.c.category==category,
                                   engine.c.ids==ids,
                                   engine.c.kind=='num'),
                           values = {
                                   engine.c.value:
                                           sa.cast(
                                           sa.cast(engine.c.value, sa.Integer)
                                           + delta, sa.String),
                                   engine.c.expiration: expiration
                                   }
                           ).execute()

        self.profile_stop(prof)

        if rp.rowcount == 1:
            return self.get(category, ids, force_write_table=True)
        elif rp.rowcount == 0:
            existing_value = self.get(category, ids, force_write_table=True)
            if existing_value is None:
                raise ValueError("[%s][%s] can't be incr()ed -- it's not set" %
                                 (category, ids))
            else:
                raise ValueError("[%s][%s] has non-integer value %r" %
                                 (category, ids, existing_value))
        else:
            raise ValueError("Somehow %d rows got updated" % rp.rowcount)

    def get(self, category, ids, force_write_table=False):
        if force_write_table:
            type = "master"
        else:
            type = "readslave"

        engine = self.engine_by_category(category, type)

        prof = self.profile_start('get', category)

        s = sa.select([engine.c.value,
                       engine.c.kind,
                       engine.c.expiration],
                      sa.and_(engine.c.category==category,
                              engine.c.ids==ids),
                      limit = 1)
        rows = s.execute().fetchall()

        self.profile_stop(prof)

        if len(rows) < 1:
            return None
        elif rows[0].expiration < datetime.now(TZ):
            return None
        else:
            return self.tdb.db2py(rows[0].value, rows[0].kind)

    def get_multi(self, category, idses):
        prof = self.profile_start('get_multi', category)

        engine = self.engine_by_category(category, "readslave")

        s = sa.select([engine.c.ids,
                       engine.c.value,
                       engine.c.kind,
                       engine.c.expiration],
                      sa.and_(engine.c.category==category,
                              sa.or_(*[engine.c.ids==ids
                                       for ids in idses])))
        rows = s.execute().fetchall()

        self.profile_stop(prof)

        results = {}

        for row in rows:
          if row.expiration >= datetime.now(TZ):
              k = "%s-%s" % (category, row.ids)
              results[k] = self.tdb.db2py(row.value, row.kind)

        return results

    def delete(self, category, ids):
        prof = self.profile_start('delete', category)
        engine = self.engine_by_category(category, "master")
        engine.delete(
            sa.and_(engine.c.category==category,
                    engine.c.ids==ids)).execute()
        self.profile_stop(prof)

    def ids_by_category(self, category, limit=1000):
        prof = self.profile_start('ids_by_category', category)
        engine = self.engine_by_category(category, "readslave")
        s = sa.select([engine.c.ids],
                      sa.and_(engine.c.category==category,
                              engine.c.expiration > datetime.now(TZ)),
                      limit = limit)
        rows = s.execute().fetchall()
        self.profile_stop(prof)
        return [ r.ids for r in rows ]

    def clause_from_expiration(self, engine, expiration):
        if expiration is None:
            return True
        elif expiration == "now":
            return engine.c.expiration < datetime.now(TZ)
        else:
            return engine.c.expiration < expiration

    def expired(self, engine, expiration_clause, limit=1000):
        s = sa.select([engine.c.category,
                       engine.c.ids,
                       engine.c.expiration],
                      expiration_clause,
                      limit = limit,
                      order_by = engine.c.expiration
                      )
        rows = s.execute().fetchall()
        return [ (r.expiration, r.category, r.ids) for r in rows ]

    def delete_if_expired(self, category, ids, expiration="now"):
        prof = self.profile_start('delete_if_expired', category)
        engine = self.engine_by_category(category, "master")
        expiration_clause = self.clause_from_expiration(engine, expiration)
        engine.delete(sa.and_(engine.c.category==category,
                              engine.c.ids==ids,
                              expiration_clause)).execute()
        self.profile_stop(prof)


def delete_expired(expiration="now", limit=5000):
    # the following depends on the structure of g.hardcache not changing
    backend = g.hardcache.caches[1].backend
    # localcache = g.hardcache.caches[0]

    masters = set()

    for engines in backend.mapping.values():
        masters.add(engines[0])

    for engine in masters:
        expiration_clause = backend.clause_from_expiration(engine, expiration)

        # Get all the expired keys
        rows = backend.expired(engine, expiration_clause, limit)

        if len(rows) == 0:
            continue

        # Delete from the backend.
        engine.delete(expiration_clause).execute()
