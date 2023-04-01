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

import logging
import os
import random
import socket
import sqlalchemy
import time
import traceback


logger = logging.getLogger('dm_manager')
logger.addHandler(logging.StreamHandler())
APPLICATION_NAME = "reddit@%s:%d" % (socket.gethostname(), os.getpid())


def get_engine(name, db_host='', db_user='', db_pass='', db_port='5432',
               pool_size=5, max_overflow=5, g_override=None):
    db_port = int(db_port)

    arguments = {
        "dbname": name,
        "host": db_host,
        "port": db_port,
        "application_name": APPLICATION_NAME,
    }
    if db_user:
        arguments["user"] = db_user
    if db_pass:
        arguments["password"] = db_pass
    dsn = "%20".join("%s=%s" % x for x in arguments.iteritems())

    engine = sqlalchemy.create_engine(
        'postgresql:///?dsn=' + dsn,
        strategy='threadlocal',
        pool_size=int(pool_size),
        max_overflow=int(max_overflow),
        # our code isn't ready for unicode to appear
        # in place of strings yet
        use_native_unicode=False,
    )

    if g_override:
        sqlalchemy.event.listens_for(engine, 'before_cursor_execute')(
            g_override.stats.pg_before_cursor_execute)
        sqlalchemy.event.listens_for(engine, 'after_cursor_execute')(
            g_override.stats.pg_after_cursor_execute)

    return engine


class db_manager:
    def __init__(self):
        self.type_db = None
        self.relation_type_db = None
        self._things = {}
        self._relations = {}
        self._engines = {}
        self.avoid_master_reads = {}
        self.dead = {}

    def add_thing(self, name, thing_dbs, avoid_master=False, **kw):
        """thing_dbs is a list of database engines. the first in the
        list is assumed to be the master, the rest are slaves."""
        self._things[name] = thing_dbs
        self.avoid_master_reads[name] = avoid_master

    def add_relation(self, name, type1, type2, relation_dbs,
                     avoid_master=False, **kw):
        self._relations[name] = (type1, type2, relation_dbs)
        self.avoid_master_reads[name] = avoid_master

    def setup_db(self, db_name, g_override=None, **params):
        engine = get_engine(g_override=g_override, **params)
        self._engines[db_name] = engine

        if db_name not in ("email", "authorize", "hc", "traffic"):
            # test_engine creates a connection to the database, for some less
            # important and less used databases we will skip this and only
            # create the connection if it's needed
            self.test_engine(engine, g_override)

    def things_iter(self):
        for name, engines in self._things.iteritems():
            # ensure we ALWAYS return the actual master as the first,
            # regardless of if we think it's dead or not.
            yield name, [engines[0]] + [e for e in engines[1:]
                                        if e not in self.dead]

    def rels_iter(self):
        for name, (t1_name, t2_name, engines) in self._relations.iteritems():
            engines = [engines[0]] + [e for e in engines[1:]
                                      if e not in self.dead]
            yield name, (t1_name, t2_name, engines)

    def mark_dead(self, engine, g_override=None):
        logger.error("db_manager: marking connection dead: %r", engine)
        self.dead[engine] = time.time()

    def test_engine(self, engine, g_override=None):
        try:
            list(engine.execute("select 1"))
            if engine in self.dead:
                logger.error("db_manager: marking connection alive: %r",
                             engine)
                del self.dead[engine]
            return True
        except Exception:
            logger.error(traceback.format_exc())
            logger.error("connection failure: %r" % engine)
            self.mark_dead(engine, g_override)
            return False

    def get_engine(self, name):
        return self._engines[name]

    def get_engines(self, names):
        return [self._engines[name] for name in names if name in self._engines]

    def get_read_table(self, tables):
        if len(tables) == 1:
            return tables[0]
        return  random.choice(list(tables))
