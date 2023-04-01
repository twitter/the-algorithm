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
"""
This module provides a Cassandra-backed lockless query cache.  Rather than
doing complicated queries on the fly to populate listings, a list of items that
would be in that listing are maintained in Cassandra for fast lookup.  The
result can then be fed to IDBuilder to generate a final result.

Whenever an operation occurs that would modify the contents of the listing, the
listing should be updated somehow.  In some cases, this can be done by directly
mutating the listing and in others it must be done offline in batch processing
jobs.

"""

import json
import random
import datetime
import collections

from pylons import app_globals as g
from pycassa.system_manager import ASCII_TYPE, UTF8_TYPE
from pycassa.batch import Mutator

from r2.models import Thing
from r2.lib.db import tdb_cassandra
from r2.lib.db.operators import asc, desc, BooleanOp
from r2.lib.db.sorts import epoch_seconds
from r2.lib.utils import flatten, to36


CONNECTION_POOL = g.cassandra_pools['main']
PRUNE_CHANCE = g.querycache_prune_chance
MAX_CACHED_ITEMS = 1000
LOG = g.log


class ThingTupleComparator(object):
    """A callable usable for comparing sort-data in a cached query.

    The query cache stores minimal sort data on each thing to be able to order
    the items in a cached query.  This class provides the ordering for those
    thing tuples.

    """

    def __init__(self, sorts):
        self.sorts = sorts

    def __call__(self, t1, t2):
        for i, s in enumerate(self.sorts):
            # t1 and t2 are tuples of (fullname, *sort_cols), so we
            # can get the value to compare right out of the tuple
            v1, v2 = t1[i + 1], t2[i + 1]
            if v1 != v2:
                return cmp(v1, v2) if isinstance(s, asc) else cmp(v2, v1)
        #they're equal
        return 0


class _CachedQueryBase(object):
    def __init__(self, sort):
        self.sort = sort
        self.sort_cols = [s.col for s in self.sort]
        self.data = []
        self._fetched = False

    def fetch(self, force=False):
        """Fill the cached query's sorted item list from Cassandra.

        If the query has already been fetched, this method is a no-op unless
        force=True.

        """
        if not force and self._fetched:
            return

        self._fetch()
        self._sort_data()
        self._fetched = True

    def _fetch(self):
        raise NotImplementedError()

    def _sort_data(self):
        comparator = ThingTupleComparator(self.sort_cols)
        self.data.sort(cmp=comparator)

    def __iter__(self):
        self.fetch()

        for x in self.data[:MAX_CACHED_ITEMS]:
            yield x[0]


class CachedQuery(_CachedQueryBase):
    """A materialized view of a complex query.

    Complicated queries can take way too long to sort in the databases.  This
    class provides a fast-access view of a given listing's items.  The cache
    stores each item's ID and a minimal subset of its data as required for
    sorting.

    Each time the listing is fetched, it is sorted. Because of this, we need to
    ensure the listing does not grow too large.  On each insert, a "pruning"
    can occur (with a configurable probability) which will remove excess items
    from the end of the listing.

    Use CachedQueryMutator to make changes to the cached query's item list.

    """

    def __init__(self, model, key, sort, filter_fn, is_precomputed):
        self.model = model
        self.key = key
        self.filter = filter_fn
        self.timestamps = None  # column timestamps, for safe pruning
        self.is_precomputed = is_precomputed
        super(CachedQuery, self).__init__(sort)

    def _make_item_tuple(self, item):
        """Return an item tuple from the result of a query.

        The item tuple is used to sort the items in a query without having to
        look them up.

        """
        filtered_item = self.filter(item)
        lst = [filtered_item._fullname]
        for col in self.sort_cols:
            # take the property of the original
            attr = getattr(item, col)
            # convert dates to epochs to take less space
            if isinstance(attr, datetime.datetime):
                attr = epoch_seconds(attr)
            lst.append(attr)
        return tuple(lst)

    def _fetch(self):
        self._fetch_multi([self])

    @classmethod
    def _fetch_multi(self, queries):
        """Fetch the unsorted query results for multiple queries at once.

        In the case of precomputed queries, do an extra lookup first to
        determine which row key to find the latest precomputed values for the
        query in.

        """

        by_model = collections.defaultdict(list)
        for q in queries:
            by_model[q.model].append(q)

        cached_queries = {}
        for model, queries in by_model.iteritems():
            pure, need_mangling = [], []
            for q in queries:
                if not q.is_precomputed:
                    pure.append(q.key)
                else:
                    need_mangling.append(q.key)

            mangled = model.index_mangle_keys(need_mangling)
            fetched = model.get(pure + mangled.keys())
            for key, values in fetched.iteritems():
                key = mangled.get(key, key)
                cached_queries[key] = values

        for q in queries:
            cached_query = cached_queries.get(q.key)
            if cached_query:
                q.data, q.timestamps = cached_query

    def _cols_from_things(self, things):
        cols = {}
        for thing in things:
            t = self._make_item_tuple(thing)
            cols[t[0]] = tuple(t[1:])
        return cols

    def _insert(self, mutator, things):
        if not things:
            return

        cols = self._cols_from_things(things)
        self.model.insert(mutator, self.key, cols)

    def _replace(self, mutator, things, ttl):
        cols = self._cols_from_things(things)
        self.model.replace(mutator, self.key, cols, ttl)

    def _delete(self, mutator, things):
        if not things:
            return

        fullnames = [self.filter(x)._fullname for x in things]
        self.model.remove(mutator, self.key, fullnames)

    def _prune(self, mutator):
        to_keep = [t[0] for t in self.data[:MAX_CACHED_ITEMS]]
        to_prune = [t[0] for t in self.data[MAX_CACHED_ITEMS:]]

        if to_prune:
            oldest_keep = min(self.timestamps[_id] for _id in to_keep)
            fast_prunable = [_id for _id in to_prune
                if self.timestamps[_id] < oldest_keep]

            num_to_prune = len(to_prune)
            num_fast_prunable = len(fast_prunable)
            num_unpruned_if_fast = num_to_prune - num_fast_prunable
            if (num_fast_prunable > num_to_prune * 0.5 and
                    num_unpruned_if_fast < MAX_CACHED_ITEMS * 0.5):
                # do a fast prune if we can remove a good number of items but
                # don't let the cached query grow too large
                newest_prune = max(self.timestamps[_id] for _id in fast_prunable)
                self.model.remove_older_than(mutator, self.key, newest_prune)
                event_name = 'fast_pruned'
                num_pruned = num_fast_prunable
            else:
                # if something has gone wrong with previous prunings, there may
                # be a lot of items to prune.
                #
                # On each attempt we have PRUNE_CHANCE likelihood that we will
                # get to prune. Assume that each prune attempt occurs as the
                # result of adding one item to the `CachedQuery`. So, to prevent
                # unbounded growth we need to remove on average at least one
                # item per prune attempt.
                # so:
                # N_avg = 1 = PRUNE_CHANCE * PRUNE_SIZE
                # PRUNE_SIZE = 1 / PRUNE_CHANCE
                # We'll multiply this value by 1.5 to ensure that we return
                # quickly to the maximum allowed size.
                prune_size = int(1.5 * 1 / PRUNE_CHANCE)
                to_prune = to_prune[-prune_size:]

                self.model.remove_if_unchanged(mutator, self.key,
                                               to_prune, self.timestamps)
                event_name = 'pruned'
                num_pruned = len(to_prune)

            cf_name = self.model.__name__
            query_name = self.key.split('.')[0]
            counter_key = "cache.%s.%s" % (cf_name, query_name)
            counter = g.stats.get_counter(counter_key)
            if counter:
                counter.increment(event_name, delta=num_pruned)

    @classmethod
    def _prune_multi(cls, queries):
        cls._fetch_multi(queries)

        with Mutator(CONNECTION_POOL) as m:
            for q in queries:
                q._sort_data()
                q._prune(m)

    def __hash__(self):
        return hash(self.key)

    def __eq__(self, other):
        return self.key == other.key

    def __ne__(self, other):
        return not self.__eq__(other)

    def __repr__(self):
        return "%s(%s, %r)" % (self.__class__.__name__,
                               self.model.__name__, self.key)


class MergedCachedQuery(_CachedQueryBase):
    """A cached query built by merging multiple sub-queries.

    Merged queries can be read, but cannot be modified as it is not easy to
    determine from a given item which sub-query should get modified.

    """

    def __init__(self, queries):
        self.queries = queries

        if queries:
            sort = queries[0].sort
            assert all(sort == q.sort for q in queries)
        else:
            sort = []
        super(MergedCachedQuery, self).__init__(sort)

    def _fetch(self):
        CachedQuery._fetch_multi(self.queries)
        self.data = flatten([q.data for q in self.queries])


class CachedQueryMutator(object):
    """Utility to manipulate cached queries with batching.

    This implements the context manager protocol so it can be used with the
    with statement for clean batches.

    """

    def __init__(self):
        self.mutator = Mutator(CONNECTION_POOL)
        self.to_prune = set()

    def __enter__(self):
        return self

    def __exit__(self, type, value, traceback):
        self.send()

    def insert(self, query, things):
        """Insert items into the given cached query.

        If the items are already in the query, they will have their sorts
        updated.

        This will sometimes trigger pruning with a configurable probability
        (see g.querycache_prune_chance).

        """
        if not things:
            return

        LOG.debug("Inserting %r into query %r", things, query)

        assert not query.is_precomputed
        query._insert(self.mutator, things)

        if (random.random() / len(things)) < PRUNE_CHANCE:
            self.to_prune.add(query)

    def replace(self, query, things, ttl=None):
        """Replace a precomputed query with a new set of things.

        The query index will be updated. If a TTL is specified, it will be
        applied to all columns generated by this action allowing old
        precomputed queries to fall away after they're no longer useful.

        """
        assert query.is_precomputed

        if isinstance(ttl, datetime.timedelta):
            ttl = ttl.total_seconds()

        query._replace(self.mutator, things, ttl)

    def delete(self, query, things):
        """Remove things from the query."""
        if not things:
            return

        LOG.debug("Deleting %r from query %r", things, query)

        query._delete(self.mutator, things)

    def send(self):
        """Commit the mutations batched up so far and potentially do pruning.

        This is automatically called by __exit__ when used as a context
        manager.

        """
        self.mutator.send()

        if self.to_prune:
            LOG.debug("Pruning queries %r", self.to_prune)
            CachedQuery._prune_multi(self.to_prune)


def filter_identity(x):
    """Return the same thing given.

    Use this as the filter_fn of simple Thing-based cached queries so that
    the enumerated things will be returned for rendering.

    """
    return x


def filter_thing2(x):
    """Return the thing2 of a given relationship.

    Use this as the filter_fn of a cached Relation query so that the related
    things will be returned for rendering.

    """
    return x._thing2


def filter_thing(x):
    """Return "thing" from a proxy object.

    Use this as the filter_fn when some object that's not a Thing or Relation
    is used as the basis of a cached query.

    """
    return x.thing


def _is_query_precomputed(query):
    """Return if this query must be updated offline in a batch job.

    Simple queries can be modified in place in the query cache, but ones
    with more complicated eligibility criteria, such as a time limit ("top
    this month") cannot be modified this way and must instead be
    recalculated periodically.  Rather than replacing a single row
    repeatedly, the precomputer stores in a new row every time it runs and
    updates an index of the latest run.

    """

    # visit all the nodes in the rule tree to see if there are time limitations
    # if we find one, this query is one that must be precomputed
    rules = list(query._rules)
    while rules:
        rule = rules.pop()

        if isinstance(rule, BooleanOp):
            rules.extend(rule.ops)
            continue

        if rule.lval.name == "_date":
            return True
    return False


class FakeQuery(object):
    """A somewhat query-like object for conveying sort information."""

    def __init__(self, sort, precomputed=False):
        self._sort = sort
        self.precomputed = precomputed


def cached_query(model, filter_fn=filter_identity):
    """Decorate a function describing a cached query.

    The decorated function is expected to follow the naming convention common
    in queries.py -- "get_something".  The cached query's key will be generated
    from the combination of the function name and its arguments separated by
    periods.

    The decorated function should return a raw thingdb query object
    representing the query that is being cached. If there is no valid
    underlying query to build off of, a FakeQuery specifying the correct
    sorting criteria for the enumerated objects can be returned.

    """
    def cached_query_decorator(fn):
        def cached_query_wrapper(*args):
            # build the row key from the function name and arguments
            assert fn.__name__.startswith("get_")
            row_key_components = [fn.__name__[len('get_'):]]

            if len(args) > 0:
                # we want to accept either a Thing or a thing's ID at this
                # layer, but the query itself should always get just an ID
                if isinstance(args[0], Thing):
                    args = list(args)
                    args[0] = args[0]._id

                if isinstance(args[0], (int, long)):
                    serialized = to36(args[0])
                else:
                    serialized = str(args[0])
                row_key_components.append(serialized)

            row_key_components.extend(str(x) for x in args[1:])
            row_key = '.'.join(row_key_components)

            query = fn(*args)

            query_sort = query._sort
            try:
                is_precomputed = query.precomputed
            except AttributeError:
                is_precomputed = _is_query_precomputed(query)

            return CachedQuery(model, row_key, query_sort, filter_fn,
                               is_precomputed)
        return cached_query_wrapper
    return cached_query_decorator


def merged_cached_query(fn):
    """Decorate a function describing a cached query made up of others.

    The decorated function should return a sequence of cached queries whose
    results will be merged together into a final listing.

    """
    def merge_wrapper(*args, **kwargs):
        queries = fn(*args, **kwargs)
        return MergedCachedQuery(queries)
    return merge_wrapper


class _BaseQueryCache(object):
    """The model through which cached queries to interact with Cassandra.

    Each cached query is stored as a distinct row in Cassandra.  The row key is
    given by higher level code (see the cached_query decorator above).  Each
    item in the materialized result of the query is stored as a separate
    column.  Each column name is the fullname of the item, while each value is
    the stuff CachedQuery needs to be able to sort the items (see
    CachedQuery._make_item_tuple).

    """

    __metaclass__ = tdb_cassandra.ThingMeta
    _connection_pool = 'main'
    _extra_schema_creation_args = dict(key_validation_class=ASCII_TYPE,
                                       default_validation_class=UTF8_TYPE)
    _compare_with = ASCII_TYPE
    _use_db = False
    _type_prefix = None
    _cf_name = None

    @classmethod
    def get(cls, keys):
        """Retrieve the items in a set of cached queries.

        For each cached query, this returns the thing tuples and the column
        timestamps for them.  The latter is useful for conditional removal
        during pruning.

        """
        rows = cls._cf.multiget(keys, include_timestamp=True,
                                column_count=tdb_cassandra.max_column_count)

        res = {}
        for row, columns in rows.iteritems():
            data = []
            timestamps = []

            for (key, (value, timestamp)) in columns.iteritems():
                value = json.loads(value)
                data.append((key,) + tuple(value))
                timestamps.append((key, timestamp))

            res[row] = (data, dict(timestamps))

        return res

    @classmethod
    def index_mangle_keys(cls, keys):
        if not keys:
            return {}

        index_keys = ["/".join((key, "index")) for key in keys]
        rows = cls._cf.multiget(index_keys,
                                column_reversed=True,
                                column_count=1)

        res = {}
        for key, columns in rows.iteritems():
            root_key = key.rsplit("/")[0]
            index_component = columns.keys()[0]
            mangled = "/".join((root_key, index_component))
            res[mangled] = root_key
        return res

    @classmethod
    @tdb_cassandra.will_write
    def insert(cls, mutator, key, columns, ttl=None):
        """Insert things into the cached query.

        This works as an upsert; if the thing already exists, it is updated. If
        not, it is actually inserted.

        """
        updates = dict((key, json.dumps(value))
                       for key, value in columns.iteritems())
        mutator.insert(cls._cf, key, updates, ttl=ttl)

    @classmethod
    @tdb_cassandra.will_write
    def replace(cls, mutator, key, columns, ttl):
        # XXX: this assumes that precomputed queries aren't updated at a
        # frequency / simultaneously in a way that could collide.
        job_key = datetime.datetime.now(g.tz).isoformat()
        cls.insert(mutator, key + "/" + job_key, columns, ttl=ttl)
        mutator.insert(cls._cf, key + "/index", {job_key: ""}, ttl=ttl)

    @classmethod
    @tdb_cassandra.will_write
    def remove(cls, mutator, key, columns):
        """Unconditionally remove things from the cached query."""
        mutator.remove(cls._cf, key, columns=columns)

    @classmethod
    @tdb_cassandra.will_write
    def remove_if_unchanged(cls, mutator, key, columns, timestamps):
        """Remove things from the cached query if unchanged.

        If the things have been changed since the specified timestamps, they
        will not be removed.  This is useful for avoiding race conditions while
        pruning.

        """
        for col in columns:
            mutator.remove(cls._cf, key, columns=[col],
                           timestamp=timestamps.get(col))

    @classmethod
    @tdb_cassandra.will_write
    def remove_older_than(cls, mutator, key, removal_timestamp):
        """Remove things older than the specified timestamp.

        Removing specific columns can cause tombstones to build up. When a row
        has tons of tombstones fetching that row gets slow because Cassandra
        must retrieve all the tombstones as well. Issuing a row remove with
        the timestamp specified clears out all the columns modified before
        that timestamp and somehow doesn't result in tombstones being left
        behind. This behavior was verified via request tracing.

        """

        mutator.remove(cls._cf, key, timestamp=removal_timestamp)


class UserQueryCache(_BaseQueryCache):
    """A query cache column family for user-keyed queries."""
    _use_db = True


class SubredditQueryCache(_BaseQueryCache):
    """A query cache column family for subreddit-keyed queries."""
    _use_db = True
