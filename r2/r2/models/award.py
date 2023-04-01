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

from r2.lib.db.operators import asc, desc, lower
from r2.lib.db.thing import Thing, Relation, NotFound
from r2.lib.memoize import memoize
from r2.models import Account


class Award(Thing):
    _cache = g.thingcache
    _defaults = dict(
        awardtype='regular',
        api_ok=False,
    )

    @classmethod
    def _cache_prefix(cls):
        return "award:"

    @classmethod
    @memoize('award.all_awards')
    def _all_awards_cache(cls):
        return [ a._id for a in Award._query(sort=asc('_date'), limit=100) ]

    @classmethod
    def _all_awards(cls, _update=False):
        all = Award._all_awards_cache(_update=_update)
        # Can't just return Award._byID() results because
        # the ordering will be lost
        d = Award._byID(all, data=True)
        return [ d[id] for id in all ]

    @classmethod
    def _new(cls, codename, title, awardtype, imgurl, api_ok):
        a = Award(codename=codename, title=title, awardtype=awardtype,
                  imgurl=imgurl, api_ok=api_ok)
        a._commit()
        Award._all_awards_cache(_update=True)

    @classmethod
    def _by_codename(cls, codename):
        q = cls._query(lower(Award.c.codename) == codename.lower())
        q._limit = 1
        award = list(q)

        if award:
            return cls._byID(award[0]._id, True)
        else:
            raise NotFound, 'Award %s' % codename

    @classmethod
    def give_if_needed(cls, codename, user,
                       description=None, url=None):
        """Give an award to a user, unless they already have it.
           Returns the trophy. Does nothing and prints nothing
           (except for g.log.debug) if the award doesn't exist."""

        try:
            award = Award._by_codename(codename)
        except NotFound:
            g.log.debug("No award named '%s'" % codename)
            return None

        trophies = Trophy.by_account(user)

        for trophy in trophies:
            if trophy._thing2.codename == codename:
                g.log.debug("%s already has %s" % (user, codename))
                return trophy

        g.log.debug("Gave %s to %s" % (codename, user))
        return Trophy._new(user, award, description=description,
                        url=url)

    @classmethod
    def take_away(cls, codename, user):
        """Takes an award out of a user's trophy case.  Returns silently
           (except for g.log.debug) if there's no such award."""

        found = False

        try:
            award = Award._by_codename(codename)
        except NotFound:
            g.log.debug("No award named '%s'" % codename)
            return

        trophies = Trophy.by_account(user)

        for trophy in trophies:
            if trophy._thing2.codename == codename:
                if found:
                    g.log.debug("%s had multiple %s awards!" % (user, codename))
                trophy._delete()
                Trophy.by_account(user, _update=True)
                Trophy.by_award(award, _update=True)
                found = True

        if found:
            g.log.debug("Took %s from %s" % (codename, user))
        else:
            g.log.debug("%s didn't have %s" % (user, codename))


class FakeTrophy(object):
    def __init__(self, recipient, award, description=None, url=None):
        self._thing2 = award
        self._thing1 = recipient
        self.description = description
        self.url = url
        self.trophy_url = getattr(self, "url",
                                  getattr(self._thing2, "url", None))
        self._id = self._id36 = None


class Trophy(Relation(Account, Award)):
    _cache = g.thingcache
    _enable_fast_query = False

    @classmethod
    def _cache_prefix(cls):
        return "trophy:"

    @classmethod
    def _new(cls, recipient, award, description=None, url=None):
        # The "name" column of the Relation can't be a constant or else a
        # given account would not be allowed to win a given award more than
        # once.
        t = Trophy(recipient, award, name="trophy")
        t._name = str(t._date)

        if description:
            t.description = description

        if url:
            t.url = url

        t._commit()
        t.update_caches()
        return t
    
    def update_caches(self):
        self.by_account(self._thing1, _update=True)
        self.by_award(self._thing2, _update=True)

    @classmethod
    @memoize('trophy.by_account2')
    def by_account_cache(cls, account_id):
        q = Trophy._query(Trophy.c._thing1_id == account_id,
                          sort = desc('_date'))
        q._limit = 500
        return [ t._id for t in q ]

    @classmethod
    def by_account(cls, account, _update=False):
        rel_ids = cls.by_account_cache(account._id, _update=_update)
        trophies = Trophy._byID_rel(rel_ids, data=True, eager_load=True,
            thing_data=True, return_dict=False, ignore_missing=True)
        return trophies

    @classmethod
    @memoize('trophy.by_award2')
    def by_award_cache(cls, award_id):
        q = Trophy._query(Trophy.c._thing2_id == award_id,
                          sort = desc('_date'))
        q._limit = 50
        return [ t._id for t in q ]

    @classmethod
    def by_award(cls, award, _update=False):
        rel_ids = cls.by_award_cache(award._id, _update=_update)
        trophies = Trophy._byID_rel(rel_ids, data=True, eager_load=True,
                                    thing_data=True, return_dict = False)
        return trophies

    @classmethod
    def claim(cls, user, uid, award, description, url):
        with g.make_lock("claim_award", str("%s_%s" % (user.name, uid))):
            existing_trophy_id = user.get_trophy_id(uid)
            if existing_trophy_id:
                trophy = cls._byID(existing_trophy_id)
                preexisting = True
            else:
                preexisting = False
                trophy = cls._new(user, award, description=description,
                                  url=url)
                user.set_trophy_id(uid, trophy._id)
                user._commit()
        return trophy, preexisting

    @property
    def trophy_url(self):
        return getattr(self, "url", getattr(self._thing2, "url", None))
