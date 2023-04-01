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

import functools
import types

from r2.lib.db.thing import CreationError
from r2.lib.memoize import memoize


class UserRelManager(object):
    """Manages access to a relation between a type of thing and users."""

    def __init__(self, name, relation, permission_class):
        self.name = name
        self.relation = relation
        self.permission_class = permission_class

    def get(self, thing, user):
        if user:
            q = self.relation._fast_query([thing], [user], self.name)
            rel = q.get((thing, user, self.name))
            if rel:
                rel._permission_class = self.permission_class
            return rel

    def add(self, thing, user, permissions=None, **attrs):
        if self.get(thing, user):
            return None
        r = self.relation(thing, user, self.name, **attrs)
        if permissions is not None:
            r.set_permissions(permissions)

        try:
            r._commit()
        except CreationError:
            return None

        r._permission_class = self.permission_class
        return r

    def remove(self, thing, user):
        r = self.get(thing, user)
        if r:
            r._delete()
            return True
        return False

    def mutate(self, thing, user, **attrs):
        r = self.get(thing, user)
        if r:
            for k, v in attrs.iteritems():
                setattr(r, k, v)
            r._commit()
            r._permission_class = self.permission_class
            return r
        else:
            return self.add(thing, user, **attrs)

    def ids(self, thing):
        q = self.relation._simple_query(
            ["_thing2_id"],
            self.relation.c._thing1_id == thing._id,
            self.relation.c._name == self.name,
            sort='_date',
        )
        return [r._thing2_id for r in q]

    def reverse_ids(self, user):
        q = self.relation._simple_query(
            ["_thing1_id"],
            self.relation.c._thing2_id == user._id,
            self.relation.c._name == self.name,
        )
        return [r._thing1_id for r in q]

    def by_thing(self, thing):
        q = self.relation._query(
            self.relation.c._thing1_id == thing._id,
            self.relation.c._name == self.name,
            sort='_date',
            data=True,
        )

        for r in q:
            r._permission_class = self.permission_class
            yield r


class MemoizedUserRelManager(UserRelManager):
    """Memoized manager for a relation to users."""

    def __init__(self, name, relation, permission_class,
                 disable_ids_fn=False, disable_reverse_ids_fn=False):
        super(MemoizedUserRelManager, self).__init__(
            name, relation, permission_class)

        self.disable_ids_fn = disable_ids_fn
        self.disable_reverse_ids_fn = disable_reverse_ids_fn
        self.ids_fn_name = self.name + '_ids'
        self.reverse_ids_fn_name = 'reverse_' + self.name + '_ids'

        sup = super(MemoizedUserRelManager, self)
        self.ids = memoize(self.ids_fn_name)(sup.ids)
        self.reverse_ids = memoize(self.reverse_ids_fn_name)(sup.reverse_ids)
        self.add = self._update_caches_on_success(sup.add)
        self.remove = self._update_caches_on_success(sup.remove)

    def _update_caches(self, thing, user):
        if not self.disable_ids_fn:
            self.ids(thing, _update=True)
        if not self.disable_reverse_ids_fn:
            self.reverse_ids(user, _update=True)

    def _update_caches_on_success(self, method):
        @functools.wraps(method)
        def wrapper(thing, user, *args, **kwargs):
            try:
                result = method(thing, user, *args, **kwargs)
            except:
                raise
            else:
                self._update_caches(thing, user)
            return result
        return wrapper


def UserRel(name, relation, disable_ids_fn=False, disable_reverse_ids_fn=False,
            permission_class=None):
    """Mixin for Thing subclasses for managing a relation to users.

    Provides the following suite of methods for a relation named "<relation>":

      - is_<relation>(self, user) - whether user is related to self
      - add_<relation>(self, user) - relates user to self
      - remove_<relation>(self, user) - dissolves relation of user to self

    This suite also usually includes (unless explicitly disabled):

      - <relation>_ids(self) - list of user IDs related to self
      - (static) reverse_<relation>_ids(user) - list of thing IDs user is
          related to
    """
    mgr = MemoizedUserRelManager(
        name, relation, permission_class,
        disable_ids_fn, disable_reverse_ids_fn)

    class UR:
        @classmethod
        def _bind(cls, fn):
            return types.UnboundMethodType(fn, None, cls)

    setattr(UR, 'is_' + name, UR._bind(mgr.get))
    setattr(UR, 'get_' + name, UR._bind(mgr.get))
    setattr(UR, 'add_' + name, UR._bind(mgr.add))
    setattr(UR, 'remove_' + name, UR._bind(mgr.remove))
    setattr(UR, 'each_' + name, UR._bind(mgr.by_thing))
    setattr(UR, name + '_permission_class', permission_class)
    if not disable_ids_fn:
        setattr(UR, mgr.ids_fn_name, UR._bind(mgr.ids))
    if not disable_reverse_ids_fn:
        setattr(UR, mgr.reverse_ids_fn_name, staticmethod(mgr.reverse_ids))

    return UR


def MigratingUserRel(name, relation, disable_ids_fn=False,
                     disable_reverse_ids_fn=False, permission_class=None):
    """
    Replacement for UserRel to be used during migrations away from the system.

    The resulting "UserRel" classes generated are to be used as standalones and
    not included in Subreddit.__bases__.

    """

    mgr = MemoizedUserRelManager(
        name, relation, permission_class,
        disable_ids_fn, disable_reverse_ids_fn)

    class URM: pass

    setattr(URM, 'is_' + name, mgr.get)
    setattr(URM, 'get_' + name, mgr.get)
    setattr(URM, 'add_' + name, staticmethod(mgr.add))
    setattr(URM, 'remove_' + name, staticmethod(mgr.remove))
    setattr(URM, 'each_' + name, mgr.by_thing)
    setattr(URM, name + '_permission_class', permission_class)

    if not disable_ids_fn:
        setattr(URM, mgr.ids_fn_name, mgr.ids)

    if not disable_reverse_ids_fn:
        setattr(URM, mgr.reverse_ids_fn_name, staticmethod(mgr.reverse_ids))

    return URM
