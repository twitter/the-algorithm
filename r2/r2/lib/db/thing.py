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

from copy import copy, deepcopy
import cPickle as pickle
from datetime import datetime, timedelta
import hashlib
import itertools
import new
import sys

from _pylibmc import MemcachedError
from pylons import app_globals as g

from r2.lib import amqp, hooks
from r2.lib.db import tdb_sql as tdb, sorts, operators
from r2.lib.sgm import sgm
from r2.lib.utils import class_property, Results, tup, to36


class NotFound(Exception):
    pass


CreationError = tdb.CreationError

thing_types = {}
rel_types = {}


class SafeSetAttr:
    def __init__(self, cls):
        self.cls = cls

    def __enter__(self):
        self.cls.__safe__ = True

    def __exit__(self, type, value, tb):
        self.cls.__safe__ = False


class TdbTransactionContext(object):
    def __enter__(self):
        tdb.transactions.begin()

    def __exit__(self, exc_type, exc_value, traceback):
        if exc_type:
            tdb.transactions.rollback()
            raise
        else:
            tdb.transactions.commit()


class DataThing(object):
    _base_props = ()
    _int_props = ()
    _data_int_props = ()
    _int_prop_suffix = None
    _defaults = {}
    _essentials = ()
    c = operators.Slots()
    __safe__ = False
    _cache = None
    _cache_ttl = int(timedelta(hours=12).total_seconds())

    def __init__(self):
        safe_set_attr = SafeSetAttr(self)
        with safe_set_attr:
            self.safe_set_attr = safe_set_attr
            self._dirties = {}
            self._t = {}
            self._created = False

    #TODO some protection here?
    def __setattr__(self, attr, val, make_dirty=True):
        if attr.startswith('__') or self.__safe__:
            object.__setattr__(self, attr, val)
            return 

        if attr.startswith('_'):
            #assume baseprops has the attr
            if make_dirty and hasattr(self, attr):
                old_val = getattr(self, attr)
            object.__setattr__(self, attr, val)
            if not attr in self._base_props:
                return
        else:
            old_val = self._t.get(attr, self._defaults.get(attr))
            self._t[attr] = val
        if make_dirty and val != old_val:
            self._dirties[attr] = (old_val, val)

    def __setstate__(self, state):
        # pylibmc's automatic unpicking will call __setstate__ if it exists.
        # if we don't implement __setstate__ the check for existence will fail
        # in an atypical (and not properly handled) way because we override
        # __getattr__. the implementation provided here is identical to what
        # would happen in the default unimplemented case.
        self.__dict__ = state

    def __getattr__(self, attr):
        try:
            return self._t[attr]
        except KeyError:
            try:
                return self._defaults[attr]
            except KeyError:
                # attr didn't exist--continue on to error recovery below
                pass

        try:
            _id = object.__getattribute__(self, "_id")
        except AttributeError:
            _id = "???"

        try:
            class_name = object.__getattribute__(self, "__class__").__name__
        except AttributeError:
            class_name = "???"

        try:
            id_str = "%d" % _id
        except TypeError:
            id_str = "%r" % _id

        error_msg = "{cls}({id}).{attr} not found".format(
            cls=class_name,
            id=_id,
            attr=attr,
        )

        raise AttributeError, error_msg

    @classmethod
    def _cache_prefix(cls):
        return cls.__name__ + '_'

    def _cache_key(self):
        prefix = self._cache_prefix()
        return "{prefix}{id}".format(prefix=prefix, id=self._id)

    @classmethod
    def get_things_from_db(cls, ids):
        """Read props from db and return id->thing dict."""
        raise NotImplementedError

    @classmethod
    def get_things_from_cache(cls, ids, stale=False, allow_local=True):
        """Read things from cache and return id->thing dict."""
        cache = cls._cache
        prefix = cls._cache_prefix()
        things_by_id = cache.get_multi(
            ids, prefix=prefix, stale=stale, allow_local=allow_local,
            stat_subname=cls.__name__)
        return things_by_id

    @classmethod
    def write_things_to_cache(cls, things_by_id):
        """Write id->thing dict to cache.

        Used to populate the cache after a cache miss/db read. To ensure we
        don't clobber a write by another process (we don't have a lock) we use
        add_multi to only set the values that don't exist.

        """

        cache = cls._cache
        prefix = cls._cache_prefix()
        try:
            cache.add_multi(things_by_id, prefix=prefix, time=cls._cache_ttl)
        except MemcachedError as e:
            g.log.warning("write_things_to_cache error: %s", e)

    def get_read_modify_write_lock(self):
        """Return the lock to be used when doing a read-modify-write.

        When modifying a Thing we must read its current version from cache and
        update that to avoid clobbering modifications made by other processes
        after we first read the Thing.

        """

        return g.make_lock("thing_commit", 'commit_' + self._fullname)

    def write_new_thing_to_db(self):
        """Write the new thing to db and return its id."""
        raise NotImplementedError

    def write_props_to_db(self, props, data_props, brand_new_thing):
        """Write the props to db."""
        raise NotImplementedError

    def write_changes_to_db(self, changes, brand_new_thing=False):
        """Write changes to db."""
        if not changes:
            return

        data_props = {}
        props = {}
        for prop, (old_value, new_value) in changes.iteritems():
            if prop.startswith('_'):
                props[prop[1:]] = new_value
            else:
                data_props[prop] = new_value

        self.write_props_to_db(props, data_props, brand_new_thing)

    def write_thing_to_cache(self, lock, brand_new_thing=False):
        """After modifying a thing write the entire object to cache.

        The caller must either pass in the read_modify_write lock or be acting
        for a newly created thing (that has therefore never been cached before).

        """

        assert brand_new_thing or lock.have_lock

        cache = self.__class__._cache
        key = self._cache_key()
        cache.set(key, self, time=self.__class__._cache_ttl)

    def update_from_cache(self, lock):
        """Read the current value of thing from cache and update self.

        To be used before writing cache to avoid clobbering changes made by a
        different process. Must be called under write lock.

        """

        assert lock.have_lock

        # disallow reading from local cache because we want to pull in changes
        # made by other processes since we first read this thing.
        other_selfs = self.__class__.get_things_from_cache(
            [self._id], allow_local=False)
        if not other_selfs:
            return
        other_self = other_selfs[self._id]

        # update base_props
        for base_prop in self._base_props:
            other_self_val = getattr(other_self, base_prop)
            self.__setattr__(base_prop, other_self_val, make_dirty=False)

        # update data_props
        self._t = other_self._t

        # reapply changes made to self
        self_changes = self._dirties
        self._dirties = {}
        for data_prop, (old_val, new_val) in self_changes.iteritems():
            setattr(self, data_prop, new_val)

    @classmethod
    def record_cache_write(cls, event, delta=1):
        raise NotImplementedError

    def _commit(self):
        """Write changes to db and write the full object to cache.

        When writing to postgres we write only the changes. The data in postgres
        is the canonical version.

        For a few reasons (speed, decreased load on postgres, postgres
        replication lag) we want to keep a perfectly consistent copy of the
        thing in cache.

        To achieve this we read the current value of the thing from cache to
        pull in any changes made by other processes, apply our changes to the
        thing, and finally set it in cache. This is done under lock to ensure
        read/write safety.

        If the cached thing is evicted or expires we must read from postgres.

        Failure cases:
        * Write to cache fails. The cache now contains stale/incorrect data. To
          ensure we recover quickly TTLs should be set as low as possible
          without overloading postgres.
        * There is long replication lag and high cache pressure. When an object
          is modified it is written to cache, but quickly evicted, The next
          lookup might read from a postgres secondary before the changes have
          been replicated there. To protect against this replication lag and
          cache pressure should be monitored and kept at acceptable levels.
        * Near simultaneous writes that create a logical inconsistency. Say
          request 1 and request 2 both read state 0 of a Thing. Request 1
          changes Thing.prop from True to False and writes to cache and
          postgres. Request 2 examines the value of Thing.prop, sees that it is
          True, and due to logic in the app sets Thing.prop_is_true to True and
          writes to cache and postgres. Request 2 didn't clobber the change
          made by request 1, but it made a logically incorrect change--the
          resulting state is Thing.prop = False and Thing.prop_is_true = True.
          Logic like this should be identified and avoided wherever possible, or
          protected against using locks.

        """

        if not self._created:
            with TdbTransactionContext():
                _id = self.write_new_thing_to_db()
                self._id = _id
                self._created = True

                changes = self._dirties.copy()
                self.write_changes_to_db(changes, brand_new_thing=True)
                self._dirties.clear()

            self.write_thing_to_cache(lock=None, brand_new_thing=True)
            self.record_cache_write(event="create")
        else:
            with self.get_read_modify_write_lock() as lock:
                self.update_from_cache(lock)
                if not self._dirty:
                    return

                with TdbTransactionContext():
                    changes = self._dirties.copy()
                    self.write_changes_to_db(changes, brand_new_thing=False)
                    self._dirties.clear()

                self.write_thing_to_cache(lock)
                self.record_cache_write(event="modify")

        hooks.get_hook("thing.commit").call(thing=self, changes=changes)

    def _incr(self, prop, amt=1):
        raise NotImplementedError

    @property
    def _id36(self):
        return to36(self._id)

    @class_property
    def _fullname_prefix(cls):
        return cls._type_prefix + to36(cls._type_id)

    @classmethod
    def _fullname_from_id36(cls, id36):
        return cls._fullname_prefix + '_' + id36

    @property
    def _fullname(self):
        return self._fullname_from_id36(self._id36)

    @classmethod
    def _byID(cls, ids, data=True, return_dict=True, stale=False,
              ignore_missing=False):
        # data props are ALWAYS loaded, data keyword is meaningless
        ids, single = tup(ids, ret_is_single=True)

        for x in ids:
            if not isinstance(x, (int, long)):
                raise ValueError('non-integer thing_id in %r' % ids)
            if x > tdb.MAX_THING_ID:
                raise NotFound('huge thing_id in %r' % ids)
            elif x < tdb.MIN_THING_ID:
                raise NotFound('negative thing_id in %r' % ids)

        if not single and not ids:
            if return_dict:
                return {}
            else:
                return []

        things_by_id = cls.get_things_from_cache(ids, stale=stale)
        missing_ids = [_id
            for _id in ids
            if _id not in things_by_id
        ]

        if missing_ids:
            from_db_by_id = cls.get_things_from_db(missing_ids)
        else:
            from_db_by_id = {}

        if from_db_by_id:
            cls.write_things_to_cache(from_db_by_id)
            cls.record_cache_write(event="cache", delta=len(from_db_by_id))

        things_by_id.update(from_db_by_id)

        # Check to see if we found everything we asked for
        missing = [_id for _id in ids if _id not in things_by_id]
        if missing and not ignore_missing:
            raise NotFound, '%s %s' % (cls.__name__, missing)

        if missing:
            ids = [_id for _id in ids if _id not in missing]

        if single:
            return things_by_id[ids[0]] if ids else None
        elif return_dict:
            return things_by_id
        else:
            return filter(None, (things_by_id.get(_id) for _id in ids))

    @classmethod
    def _byID36(cls, id36s, return_dict = True, **kw):

        id36s, single = tup(id36s, True)

        # will fail if it's not a string
        ids = [ int(x, 36) for x in id36s ]

        things = cls._byID(ids, return_dict=True, **kw)
        things = {thing._id36: thing for thing in things.itervalues()}

        if single:
            return things.values()[0]
        elif return_dict:
            return things
        else:
            return filter(None, (things.get(i) for i in id36s))

    @classmethod
    def _by_fullname(cls, names,
                     return_dict = True, 
                     ignore_missing=False,
                     **kw):
        names, single = tup(names, True)

        table = {}
        lookup = {}
        # build id list by type
        for fullname in names:
            try:
                real_type, thing_id = fullname.split('_')
                #distinguish between things and realtions
                if real_type[0] == 't':
                    type_dict = thing_types
                elif real_type[0] == 'r':
                    type_dict = rel_types
                else:
                    raise NotFound
                real_type = type_dict[int(real_type[1:], 36)]
                thing_id = int(thing_id, 36)
                lookup[fullname] = (real_type, thing_id)
                table.setdefault(real_type, []).append(thing_id)
            except (KeyError, ValueError):
                if single:
                    raise NotFound

        # lookup ids for each type
        identified = {}
        for real_type, thing_ids in table.iteritems():
            i = real_type._byID(thing_ids, ignore_missing=ignore_missing, **kw)
            identified[real_type] = i

        # interleave types in original order of the name
        res = []
        for fullname in names:
            if lookup.has_key(fullname):
                real_type, thing_id = lookup[fullname]
                thing = identified.get(real_type, {}).get(thing_id)
                if not thing and ignore_missing:
                    continue
                res.append((fullname, thing))

        if single:
            return res[0][1] if res else None
        elif return_dict:
            return dict(res)
        else:
            return [x for i, x in res]

    @property
    def _dirty(self):
        return bool(len(self._dirties))

    @classmethod
    def _query(cls, *a, **kw):
        raise NotImplementedError()


class ThingMeta(type):
    def __init__(cls, name, bases, dct):
        if name == 'Thing' or hasattr(cls, '_nodb') and cls._nodb: return
        if g.env == 'unit_test':
            return

        #TODO exceptions
        cls._type_name = name.lower()
        try:
            cls._type_id = tdb.types_name[cls._type_name].type_id
        except KeyError:
            raise KeyError, 'is the thing database %s defined?' % name

        global thing_types
        thing_types[cls._type_id] = cls

        super(ThingMeta, cls).__init__(name, bases, dct)
    
    def __repr__(cls):
        return '<thing: %s>' % cls._type_name

class Thing(DataThing):
    __metaclass__ = ThingMeta
    _base_props = ('_ups', '_downs', '_date', '_deleted', '_spam')
    _int_props = ('_ups', '_downs')
    _type_prefix = 't'

    is_votable = False

    def __init__(self, ups = 0, downs = 0, date = None, deleted = False,
                 spam = False, id = None, **attrs):
        DataThing.__init__(self)

        with self.safe_set_attr:
            if id:
                self._id = id
                self._created = True

            if not date:
                date = datetime.now(g.tz)
            else:
                date = date.astimezone(g.tz)

            self._ups = ups
            self._downs = downs
            self._date = date
            self._deleted = deleted
            self._spam = spam

        #new way
        for k, v in attrs.iteritems():
            self.__setattr__(k, v, not self._created)

    @classmethod
    def record_cache_write(cls, event, delta=1):
        name = cls.__name__.lower()
        event_name = "thing.{event}.{name}".format(event=event, name=name)
        g.stats.simple_event(event_name, delta)

    def __repr__(self):
        return '<%s %s>' % (self.__class__.__name__,
                            self._id if self._created else '[unsaved]')

    @classmethod
    def get_things_from_db(cls, ids):
        """Read props from db and return id->thing dict."""
        props_by_id = tdb.get_thing(cls._type_id, ids)
        data_props_by_id = tdb.get_thing_data(cls._type_id, ids)

        things_by_id = {}
        for _id, props in props_by_id.iteritems():
            data_props = data_props_by_id.get(_id, {})
            thing = cls(
                ups=props.ups,
                downs=props.downs,
                date=props.date,
                deleted=props.deleted,
                spam=props.spam,
                id=_id,
            )
            thing._t.update(data_props)

            if not all(data_prop in thing._t for data_prop in cls._essentials):
                # a Thing missing an essential prop is invalid
                # this can happen if a process looks up the Thing as it's
                # created but between when the props and the data props are
                # written
                g.log.error("%s missing essentials, got %s", thing, thing._t)
                g.stats.simple_event("thing.load.missing_essentials")
                continue

            things_by_id[_id] = thing

        return things_by_id

    def write_new_thing_to_db(self):
        """Write the new thing to db and return its id."""
        assert not self._created

        _id = tdb.make_thing(
            type_id=self.__class__._type_id,
            ups=self._ups,
            downs=self._downs,
            date=self._date,
            deleted=self._deleted,
            spam=self._spam,
        )
        return _id

    def write_props_to_db(self, props, data_props, brand_new_thing):
        """Write the props to db."""
        if data_props:
            tdb.set_thing_data(
                type_id=self.__class__._type_id,
                thing_id=self._id,
                brand_new_thing=brand_new_thing,
                **data_props
            )

        if props and not brand_new_thing:
            # if the thing is brand new its props have just been written by
            # write_new_thing_to_db
            tdb.set_thing_props(
                type_id=self.__class__._type_id,
                thing_id=self._id,
                **props
            )

    def _incr(self, prop, amt=1):
        """Increment self.prop."""
        assert not self._dirty

        is_base_prop = prop in self._int_props
        is_data_prop = (prop in self._data_int_props or
            self._int_prop_suffix and prop.endswith(self._int_prop_suffix))
        db_prop = prop[1:] if is_base_prop else prop

        assert is_base_prop or is_data_prop

        with self.get_read_modify_write_lock() as lock:
            self.update_from_cache(lock)
            old_val = getattr(self, prop)
            new_val = old_val + amt

            self.__setattr__(prop, new_val, make_dirty=False)

            with TdbTransactionContext():
                if is_base_prop:
                    # can just incr a base prop because it must have been set
                    # when the object was created
                    tdb.incr_thing_prop(
                        type_id=self.__class__._type_id,
                        thing_id=self._id,
                        prop=db_prop,
                        amount=amt,
                    )
                elif (prop in self.__class__._defaults and
                        self.__class__._defaults[prop] == old_val):
                    # when updating a data prop from the default value assume
                    # the value was never actually set so it's not safe to incr
                    tdb.set_thing_data(
                        type_id=self.__class__._type_id,
                        thing_id=self._id,
                        brand_new_thing=False,
                        **{db_prop: new_val}
                    )
                else:
                    tdb.incr_thing_data(
                        type_id=self.__class__._type_id,
                        thing_id=self._id,
                        prop=db_prop,
                        amount=amt,
                    )

                # write to cache within the transaction context so an exception
                # will cause a transaction rollback
                self.write_thing_to_cache(lock)
        self.record_cache_write(event="incr")

    @property
    def _age(self):
        return datetime.now(g.tz) - self._date

    @property
    def _hot(self):
        return sorts.hot(self._ups, self._downs, self._date)

    @property
    def _score(self):
        return sorts.score(self._ups, self._downs)

    @property
    def _controversy(self):
        return sorts.controversy(self._ups, self._downs)

    @property
    def _confidence(self):
        return sorts.confidence(self._ups, self._downs)

    @property
    def num_votes(self):
        return self._ups + self._downs

    @property
    def is_distinguished(self):
        """Return whether this Thing has a special flag on it (mod, admin, etc).

        Done this way because distinguish is implemented in such a way where it
        does not exist by default, but can also be set to a string of 'no',
        which also means it is not distinguished.
        """
        return getattr(self, 'distinguished', 'no') != 'no'

    @classmethod
    def _query(cls, *all_rules, **kw):
        need_deleted = True
        need_spam = True
        #add default spam/deleted
        rules = []
        optimize_rules = kw.pop('optimize_rules', False)
        for r in all_rules:
            if not isinstance(r, operators.op):
                continue
            if r.lval_name == '_deleted':
                need_deleted = False
                # if the caller is explicitly unfiltering based on this column,
                # we don't need this rule at all. taking this out can save us a
                # join that is very expensive on pg9.
                if optimize_rules and r.rval == (True, False):
                    continue
            elif r.lval_name == '_spam':
                need_spam = False
                # see above for explanation
                if optimize_rules and r.rval == (True, False):
                    continue
            rules.append(r)

        if need_deleted:
            rules.append(cls.c._deleted == False)

        if need_spam:
            rules.append(cls.c._spam == False)

        return Things(cls, *rules, **kw)

    @classmethod
    def sort_ids_by_data_value(cls, thing_ids, value_name,
            limit=None, desc=False):
        return tdb.sort_thing_ids_by_data_value(
            cls._type_id, thing_ids, value_name, limit, desc)

    def update_search_index(self, boost_only=False):
        msg = {'fullname': self._fullname}
        if boost_only:
            msg['boost_only'] = True

        amqp.add_item('search_changes', pickle.dumps(msg),
                      message_id=self._fullname,
                      delivery_mode=amqp.DELIVERY_TRANSIENT)


class RelationMeta(type):
    def __init__(cls, name, bases, dct):
        if name == 'RelationCls': return
        #print "checking relation", name

        if g.env == 'unit_test':
            return

        cls._type_name = name.lower()
        try:
            cls._type_id = tdb.rel_types_name[cls._type_name].type_id
        except KeyError:
            raise KeyError, 'is the relationship database %s defined?' % name

        global rel_types
        rel_types[cls._type_id] = cls

        super(RelationMeta, cls).__init__(name, bases, dct)

    def __repr__(cls):
        return '<relation: %s>' % cls._type_name

def Relation(type1, type2):
    class RelationCls(DataThing):
        __metaclass__ = RelationMeta
        if not (issubclass(type1, Thing) and issubclass(type2, Thing)):
                raise TypeError('Relation types must be subclass of %s' % Thing)

        _type1 = type1
        _type2 = type2

        _base_props = ('_thing1_id', '_thing2_id', '_name', '_date')
        _type_prefix = Relation._type_prefix

        _cache_ttl = int(timedelta(hours=1).total_seconds())

        _enable_fast_query = True
        _rel_cache = g.relcache
        _rel_cache_ttl = int(timedelta(hours=1).total_seconds())

        @classmethod
        def get_things_from_db(cls, ids):
            """Read props from db and return id->rel dict."""
            props_by_id = tdb.get_rel(cls._type_id, ids)
            data_props_by_id = tdb.get_rel_data(cls._type_id, ids)

            rels_by_id = {}
            for _id, props in props_by_id.iteritems():
                data_props = data_props_by_id.get(_id, {})
                rel = cls(
                    thing1=props.thing1_id,
                    thing2=props.thing2_id,
                    name=props.name,
                    date=props.date,
                    id=_id,
                )
                rel._t.update(data_props)

                if not all(data_prop in rel._t for data_prop in cls._essentials):
                    # a Relation missing an essential prop is invalid
                    # this can happen if a process looks up the Relation as it's
                    # created but between when the props and the data props are
                    # written
                    g.log.error("%s missing essentials, got %s", rel, rel._t)
                    g.stats.simple_event("thing.load.missing_essentials")
                    continue

                rels_by_id[_id] = rel

            return rels_by_id

        def write_new_thing_to_db(self):
            """Write the new rel to db and return its id."""
            assert not self._created

            _id = tdb.make_relation(
                rel_type_id=self.__class__._type_id,
                thing1_id=self._thing1_id,
                thing2_id=self._thing2_id,
                name=self._name,
                date=self._date,
            )
            return _id

        def write_props_to_db(self, props, data_props, brand_new_thing):
            """Write the props to db."""
            if data_props:
                tdb.set_rel_data(
                    rel_type_id=self.__class__._type_id,
                    thing_id=self._id,
                    brand_new_thing=brand_new_thing,
                    **data_props
                )

            if props and not brand_new_thing:
                tdb.set_rel_props(
                    rel_type_id=self.__class__._type_id,
                    rel_id=self._id,
                    **props
                )

        # eager_load means, do you load thing1 and thing2 immediately. It calls
        # _byID(xxx).
        @classmethod
        def _byID_rel(cls, ids, data=True, return_dict=True,
                      eager_load=False, thing_data=True, thing_stale=False,
                      ignore_missing=False):
            # data props are ALWAYS loaded, data and thing_data keywords are
            # meaningless

            ids, single = tup(ids, True)

            bases = cls._byID(
                ids, return_dict=True, ignore_missing=ignore_missing)

            values = bases.values()

            if values and eager_load:
                load_things(values, stale=thing_stale)

            if single:
                return bases[ids[0]]
            elif return_dict:
                return bases
            else:
                return filter(None, (bases.get(i) for i in ids))

        def __init__(self, thing1, thing2, name, date = None, id = None, **attrs):
            DataThing.__init__(self)

            def id_and_obj(in_thing):
                if isinstance(in_thing, (int, long)):
                    return in_thing
                else:
                    return in_thing._id

            with self.safe_set_attr:
                if id:
                    self._id = id
                    self._created = True

                if not date:
                    date = datetime.now(g.tz)
                else:
                    date = date.astimezone(g.tz)

                #store the id, and temporarily store the actual object
                #because we may need it later
                self._thing1_id = id_and_obj(thing1)
                self._thing2_id = id_and_obj(thing2)
                self._name = name
                self._date = date

            for k, v in attrs.iteritems():
                self.__setattr__(k, v, not self._created)

        @classmethod
        def record_cache_write(cls, event, delta=1):
            name = cls.__name__.lower()
            event_name = "rel.{event}.{name}".format(event=event, name=name)
            g.stats.simple_event(event_name, delta)

        def __getattr__(self, attr):
            if attr == '_thing1':
                return self._type1._byID(self._thing1_id)
            elif attr == '_thing2':
                return self._type2._byID(self._thing2_id)
            elif attr.startswith('_t1'):
                return getattr(self._thing1, attr[3:])
            elif attr.startswith('_t2'):
                return getattr(self._thing2, attr[3:])
            else:
                return DataThing.__getattr__(self, attr)

        def __repr__(self):
            return ('<%s %s: <%s %s> - <%s %s> %s>' %
                    (self.__class__.__name__, self._name,
                     self._type1.__name__, self._thing1_id,
                     self._type2.__name__,self._thing2_id,
                     '[unsaved]' if not self._created else '\b'))

        @classmethod
        def _rel_cache_prefix(cls):
            return "rel:"

        @classmethod
        def _rel_cache_key_from_parts(cls, thing1_id, thing2_id, name):
            key = "{prefix}{cls}_{t1}_{t2}_{name}".format(
                prefix=cls._rel_cache_prefix(),
                cls=cls.__name__,
                t1=str(thing1_id),
                t2=str(thing2_id),
                name=name,
            )
            return key

        def _rel_cache_key(self):
            return self._rel_cache_key_from_parts(
                self._thing1_id,
                self._thing2_id,
                self._name,
            )

        def _commit(self):
            DataThing._commit(self)

            if self.__class__._enable_fast_query:
                ttl = self.__class__._rel_cache_ttl
                self._rel_cache.set(self._rel_cache_key(), self._id, time=ttl)

        def _delete(self):
            tdb.del_rel(self._type_id, self._id)

            self._cache.delete(self._cache_key())

            if self.__class__._enable_fast_query:
                ttl = self.__class__._rel_cache_ttl
                self._rel_cache.set(self._rel_cache_key(), None, time=ttl)

            # temporarily set this property so the rest of this request
            # knows it's deleted. save -> unsave, hide -> unhide
            self._name = 'un' + self._name

        @classmethod
        def _fast_query(cls, thing1s, thing2s, name, data=True, eager_load=True,
                        thing_data=True, thing_stale=False):
            """looks up all the relationships between thing1_ids and
               thing2_ids and caches them"""

            if not cls._enable_fast_query:
                raise ValueError("%s._fast_query is disabled" % cls.__name__)

            cache_key_lookup = dict()

            # We didn't find these keys in the cache, look them up in the
            # database
            def lookup_rel_ids(uncached_keys):
                rel_ids = {}

                # Lookup thing ids and name from cache key
                t1_ids = set()
                t2_ids = set()
                names = set()
                for cache_key in uncached_keys:
                    (thing1, thing2, name) = cache_key_lookup[cache_key]
                    t1_ids.add(thing1._id)
                    t2_ids.add(thing2._id)
                    names.add(name)

                q = cls._query(
                        cls.c._thing1_id == t1_ids,
                        cls.c._thing2_id == t2_ids,
                        cls.c._name == names)

                for rel in q:
                    rel_cache_key = cls._rel_cache_key_from_parts(
                        rel._thing1_id,
                        rel._thing2_id,
                        str(rel._name),
                    )
                    rel_ids[rel_cache_key] = rel._id

                for cache_key in uncached_keys:
                    if cache_key not in rel_ids:
                        rel_ids[cache_key] = None

                return rel_ids

            # make lookups for thing ids and names
            thing1_dict = dict((t._id, t) for t in tup(thing1s))
            thing2_dict = dict((t._id, t) for t in tup(thing2s))

            names = map(str, tup(name))

            # permute all of the pairs via cartesian product
            rel_tuples = itertools.product(
                thing1_dict.values(),
                thing2_dict.values(),
                names)

            # create cache keys for all permutations and initialize lookup
            for t in rel_tuples:
                thing1, thing2, name = t
                rel_cache_key = cls._rel_cache_key_from_parts(
                    thing1._id,
                    thing2._id,
                    name,
                )
                cache_key_lookup[rel_cache_key] = t

            # get the relation ids from the cache or query the db
            res = sgm(
                cache=cls._rel_cache,
                keys=cache_key_lookup.keys(),
                miss_fn=lookup_rel_ids,
                time=cls._rel_cache_ttl,
                ignore_set_errors=True,
            )

            # get the relation objects
            rel_ids = {rel_id for rel_id in res.itervalues()
                              if rel_id is not None}
            rels = cls._byID_rel(
                rel_ids,
                eager_load=eager_load,
                thing_stale=thing_stale)

            # Takes aggregated results from cache and db (res) and transforms
            # the values from ids to Relations.
            res_obj = {}
            for cache_key, rel_id in res.iteritems():
                t = cache_key_lookup[cache_key]
                rel = rels[rel_id] if rel_id is not None else None
                res_obj[t] = rel

            return res_obj

        @classmethod
        def _query(cls, *a, **kw):
            return Relations(cls, *a, **kw)

        @classmethod
        def _simple_query(cls, props, *rules, **kw):
            """Return only the requested props rather than Relation objects."""
            return RelationsPropsOnly(cls, props, *rules, **kw)

    return RelationCls
Relation._type_prefix = 'r'


class Query(object):
    def __init__(self, kind, *rules, **kw):
        self._rules = []
        self._kind = kind

        self._read_cache = kw.get('read_cache')
        self._write_cache = kw.get('write_cache')
        self._cache_time = kw.get('cache_time', 86400)
        self._limit = kw.get('limit')
        self._offset = kw.get('offset')
        self._stale = kw.get('stale', False)
        self._sort = kw.get('sort', ())
        self._filter_primary_sort_only = kw.get('filter_primary_sort_only', False)

        self._filter(*rules)

    def _setsort(self, sorts):
        sorts = tup(sorts)
        #make sure sorts are wrapped in a Sort obj
        have_date = False
        op_sorts = []
        for s in sorts:
            if not isinstance(s, operators.sort):
                s = operators.asc(s)
            op_sorts.append(s)
            if s.col.endswith('_date'):
                have_date = True
        if op_sorts and not have_date:
            op_sorts.append(operators.desc('_date'))

        self._sort_param = op_sorts
        return self

    def _getsort(self):
        return self._sort_param

    _sort = property(_getsort, _setsort)

    def _reverse(self):
        for s in self._sort:
            if isinstance(s, operators.asc):
                s.__class__ = operators.desc
            else:
                s.__class__ = operators.asc

    def _list(self, data=True):
        return list(self)

    def _dir(self, thing, reverse):
        ors = []

        # this fun hack lets us simplify the query on /r/all 
        # for postgres-9 compatibility. please remove it when
        # /r/all is precomputed.
        sorts = range(len(self._sort))
        if self._filter_primary_sort_only:
            sorts = [0]

        #for each sort add and a comparison operator
        for i in sorts:
            s = self._sort[i]

            if isinstance(s, operators.asc):
                op = operators.gt
            else:
                op = operators.lt

            if reverse:
                op = operators.gt if op == operators.lt else operators.lt

            #remember op takes lval and lval_name
            ands = [op(s.col, s.col, getattr(thing, s.col))]

            #for each sort up to the last add an equals operator
            for j in range(0, i):
                s = self._sort[j]
                ands.append(thing.c[s.col] == getattr(thing, s.col))

            ors.append(operators.and_(*ands))

        return self._filter(operators.or_(*ors))

    def _before(self, thing):
        return self._dir(thing, True)

    def _after(self, thing):
        return self._dir(thing, False)

    def _filter(*a, **kw):
        raise NotImplementedError

    def _cursor(*a, **kw):
        raise NotImplementedError

    def _cache_key(self):
        fingerprint = str(self._sort) + str(self._limit) + str(self._offset)
        if self._rules:
            rules = copy(self._rules)
            rules.sort()
            for rule in rules:
                fingerprint += str(rule)

        cache_key = "query:{kind}.{id}".format(
            kind=self._kind.__name__,
            id=hashlib.sha1(fingerprint).hexdigest()
        )
        return cache_key

    def _get_results(self):
        things = self._cursor().fetchall()
        return things

    def get_from_cache(self, allow_local=True):
        thing_fullnames = g.gencache.get(
            self._cache_key(), allow_local=allow_local)
        if thing_fullnames:
            things = Thing._by_fullname(thing_fullnames, return_dict=False,
                                        stale=self._stale)
            return things

    def set_to_cache(self, things):
        thing_fullnames = [thing._fullname for thing in things]
        g.gencache.set(self._cache_key(), thing_fullnames, self._cache_time)

    def __iter__(self):
        if self._read_cache:
            things = self.get_from_cache()
        else:
            things = None

        if things is None and not self._write_cache:
            things = self._get_results()
        elif things is None:
            # it's not in the cache, and we have the power to
            # update it, which we should do in a lock to prevent
            # concurrent requests for the same data
            with g.make_lock("thing_query", "lock_%s" % self._cache_key()):
                # see if it was set while we were waiting for our
                # lock
                if self._read_cache:
                    things = self.get_from_cache(allow_local=False)
                else:
                    things = None

                if things is None:
                    things = self._get_results()
                    self.set_to_cache(things)

        for thing in things:
            yield thing


class Things(Query):
    def __init__(self, kind, *rules, **kw):
        self._use_data = False
        Query.__init__(self, kind, *rules, **kw)

    def _filter(self, *rules):
        for op in operators.op_iter(rules):
            if not op.lval_name.startswith('_'):
                self._use_data = True

        self._rules += rules
        return self

    def _cursor(self):
        if self._use_data:
            find_fn = tdb.find_data
        else:
            find_fn = tdb.find_things

        cursor = find_fn(
            type_id=self._kind._type_id,
            sort=self._sort,
            limit=self._limit,
            offset=self._offset,
            constraints=self._rules,
        )

        #called on a bunch of rows to fetch their properties in batch
        def row_fn(ids):
            return self._kind._byID(ids, return_dict=False, stale=self._stale)

        return Results(cursor, row_fn, do_batch=True)

def load_things(rels, stale=False):
    rels = tup(rels)
    kind = rels[0].__class__

    t1_ids = set()
    if kind._type1 == kind._type2:
        t2_ids = t1_ids
    else:
        t2_ids = set()

    for rel in rels:
        t1_ids.add(rel._thing1_id)
        t2_ids.add(rel._thing2_id)

    kind._type1._byID(t1_ids, stale=stale)
    if kind._type1 != kind._type2:
        t2_items = kind._type2._byID(t2_ids, stale=stale)

class Relations(Query):
    def __init__(self, kind, *rules, **kw):
        self._eager_load = kw.get('eager_load')
        self._thing_stale = kw.get('thing_stale')
        Query.__init__(self, kind, *rules, **kw)

    def _filter(self, *rules):
        self._rules += rules
        return self

    def _eager(self, eager):
        #load the things (id, ups, down, etc.)
        self._eager_load = eager
        return self

    def _make_rel(self, rows):
        rel_ids = [row._rel_id for row in rows]
        rels = self._kind._byID(rel_ids, return_dict=False)
        if rels and self._eager_load:
            load_things(rels, stale=self._thing_stale)
        return rels

    def _cursor(self):
        c = tdb.find_rels(
            ret_props=["_rel_id"],
            rel_type_id=self._kind._type_id,
            sort=self._sort,
            limit=self._limit,
            offset=self._offset,
            constraints=self._rules,
        )
        return Results(c, self._make_rel, do_batch=True)


class RelationsPropsOnly(Relations):
    def __init__(self, kind, props, *rules, **kw):
        self.props = props
        Relations.__init__(self, kind, *rules, **kw)

    def _cursor(self):
        c = tdb.find_rels(
            ret_props=self.props,
            rel_type_id=self._kind._type_id,
            sort=self._sort,
            limit=self._limit,
            offset=self._offset,
            constraints=self._rules,
        )
        return c

    def _cache_key(self):
        fingerprint = str(self._sort) + str(self._limit) + str(self._offset)
        if self._rules:
            rules = copy(self._rules)
            rules.sort()
            for rule in rules:
                fingerprint += str(rule)
        fingerprint += '|'.join(sorted(self.props))

        cache_key = "query:{kind}.{id}".format(
            kind=self._kind.__name__,
            id=hashlib.sha1(fingerprint).hexdigest()
        )
        return cache_key

    def _get_results(self):
        rows = self._cursor().fetchall()
        return rows

    def get_from_cache(self, allow_local=True):
        return g.gencache.get(self._cache_key(), allow_local=allow_local)

    def set_to_cache(self, rows):
        g.gencache.set(self._cache_key(), rows, self._cache_time)


class MultiCursor(object):
    def __init__(self, *execute_params):
        self._execute_params = execute_params
        self._cursor = None

    def fetchone(self):
        if not self._cursor:
            self._cursor = self._execute(*self._execute_params)
            
        return self._cursor.next()
                
    def fetchall(self):
        if not self._cursor:
            self._cursor = self._execute(*self._execute_params)

        return [i for i in self._cursor]

class MergeCursor(MultiCursor):
    def _execute(self, cursors, sorts):
        #a "pair" is a (cursor, item, done) tuple
        def safe_next(c):
            try:
                #hack to keep searching even if fetching a thing returns notfound
                while True:
                    try:
                        return [c, c.fetchone(), False]
                    except NotFound:
                        #skips the broken item
                        pass
            except StopIteration:
                return c, None, True

        def undone(pairs):
            return [p for p in pairs if not p[2]]

        pairs = undone(safe_next(c) for c in cursors)

        while pairs:
            #only one query left, just dump it
            if len(pairs) == 1:
                c, item, done = pair = pairs[0]
                while not done:
                    yield item
                    c, item, done = safe_next(c)
                    pair[:] = c, item, done
            else:
                #by default, yield the first item
                yield_pair = pairs[0]
                for s in sorts:
                    col = s.col
                    #sort direction?
                    max_fn = min if isinstance(s, operators.asc) else max

                    #find the max (or min) val
                    vals = [(getattr(i[1], col), i) for i in pairs]
                    max_pair = vals[0]
                    all_equal = True
                    for pair in vals[1:]:
                        if all_equal and pair[0] != max_pair[0]:
                            all_equal = False
                        max_pair = max_fn(max_pair, pair, key=lambda x: x[0])

                    if not all_equal:
                        yield_pair = max_pair[1]
                        break

                c, item, done = yield_pair
                yield item
                yield_pair[:] = safe_next(c)

            pairs = undone(pairs)
        raise StopIteration


class MultiQuery(Query):
    def __init__(self, queries, *rules, **kw):
        self._queries = queries
        Query.__init__(self, None, *rules, **kw)

        assert not self._read_cache
        assert not self._write_cache

    def get_from_cache(self):
        raise NotImplementedError()

    def set_to_cache(self):
        raise NotImplementedError()

    def _cursor(self):
        raise NotImplementedError()

    def _reverse(self):
        for q in self._queries:
            q._reverse()

    def _setdata(self, data):
        return

    def _getdata(self):
        return True

    _data = property(_getdata, _setdata)

    def _setsort(self, sorts):
        for q in self._queries:
            q._sort = deepcopy(sorts)

    def _getsort(self):
        if self._queries:
            return self._queries[0]._sort

    _sort = property(_getsort, _setsort)

    def _filter(self, *rules):
        for q in self._queries:
            q._filter(*rules)

    def _getrules(self):
        return [q._rules for q in self._queries]

    def _setrules(self, rules):
        for q,r in zip(self._queries, rules):
            q._rules = r

    _rules = property(_getrules, _setrules)

    def _getlimit(self):
        return self._queries[0]._limit

    def _setlimit(self, limit):
        for q in self._queries:
            q._limit = limit

    _limit = property(_getlimit, _setlimit)


class Merge(MultiQuery):
    def _cursor(self):
        if (any(q._sort for q in self._queries) and
            not reduce(lambda x,y: (x == y) and x,
                      (q._sort for q in self._queries))):
            raise "The sorts should be the same"

        return MergeCursor((q._cursor() for q in self._queries),
                           self._sort)

def MultiRelation(name, *relations):
    rels_tmp = {}
    for rel in relations:
        t1, t2 = rel._type1, rel._type2
        clsname = name + '_' + t1.__name__.lower() + '_' + t2.__name__.lower()
        cls = new.classobj(clsname, (rel,), {'__module__':t1.__module__})
        setattr(sys.modules[t1.__module__], clsname, cls)
        rels_tmp[(t1, t2)] = cls

    class MultiRelationCls(object):
        c = operators.Slots()
        rels = rels_tmp

        def __init__(self, thing1, thing2, *a, **kw):
            r = self.rel(thing1, thing2)
            self.__class__ = r
            self.__init__(thing1, thing2, *a, **kw)

        @classmethod
        def rel(cls, thing1, thing2):
            t1 = thing1 if isinstance(thing1, ThingMeta) else thing1.__class__
            t2 = thing2 if isinstance(thing2, ThingMeta) else thing2.__class__
            return cls.rels[(t1, t2)]

        @classmethod
        def _query(cls, *rules, **kw):
            #TODO it should be possible to send the rules and kw to
            #the merge constructor
            queries = [r._query(*rules, **kw) for r in cls.rels.values()]
            if "sort" in kw:
                print "sorting MultiRelations is not supported"
            return Merge(queries)

        @classmethod
        def _fast_query(cls, sub, obj, name, data=True, eager_load=True,
                        thing_data=True):
            #divide into types
            def type_dict(items):
                types = {}
                for i in items:
                    types.setdefault(i.__class__, []).append(i)
                return types

            sub_dict = type_dict(tup(sub))
            obj_dict = type_dict(tup(obj))

            #for each pair of types, see if we have a query to send
            res = {}
            for types, rel in cls.rels.iteritems():
                t1, t2 = types
                if sub_dict.has_key(t1) and obj_dict.has_key(t2):
                    res.update(rel._fast_query(
                        sub_dict[t1], obj_dict[t2], name, eager_load=eager_load))

            return res

    return MultiRelationCls
