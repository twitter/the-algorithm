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

from collections import OrderedDict
from datetime import datetime
from uuid import uuid1

from pycassa.system_manager import INT_TYPE, TIME_UUID_TYPE, UTF8_TYPE
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _, N_

from r2.config import feature
from r2.lib.unicode import _force_unicode
from r2.lib.db import tdb_cassandra
from r2.lib.db.thing import Thing
from r2.lib.utils import Enum, to_datetime
from r2.models.subreddit import Subreddit, Frontpage


PROMOTE_STATUS = Enum("unpaid", "unseen", "accepted", "rejected",
                      "pending", "promoted", "finished", "edited_live")

PROMOTE_COST_BASIS = Enum('fixed_cpm', 'cpm', 'cpc',)


class PriorityLevel(object):
    name = ''
    _text = N_('')
    _description = N_('')
    default = False
    inventory_override = False

    def __repr__(self):
        return "<PriorityLevel %s>" % self.name

    @property
    def text(self):
        return _(self._text) if self._text else ''

    @property
    def description(self):
        return _(self._description) if self._description else ''


class HighPriority(PriorityLevel):
    name = 'high'
    _text = N_('highest')


class MediumPriority(PriorityLevel):
    name = 'standard'
    _text = N_('standard')
    default = True


class RemnantPriority(PriorityLevel):
    name = 'remnant'
    _text = N_('remnant')
    _description = N_('lower priority, impressions are not guaranteed')
    inventory_override = True


class HousePriority(PriorityLevel):
    name = 'house'
    _text = N_('house')
    _description = N_('non-CPM, displays in all unsold impressions')
    inventory_override = True


class AuctionPriority(PriorityLevel):
    name = 'auction'
    _text = N_('auction')
    _description = N_('auction priority; all self-serve are auction priority')
    inventory_override = True


HIGH, MEDIUM, REMNANT, HOUSE, AUCTION = (HighPriority(), MediumPriority(),
                                         RemnantPriority(), HousePriority(),
                                         AuctionPriority(),)
PROMOTE_PRIORITIES = OrderedDict((p.name, p) for p in (HIGH, MEDIUM, REMNANT,
                                                       HOUSE, AUCTION,))


def PROMOTE_DEFAULT_PRIORITY(context=None):
    if (context and (not feature.is_enabled('ads_auction') or
                     context.user_is_sponsor)):
        return MEDIUM
    else:
        return AUCTION

class Location(object):
    DELIMITER = '-'
    def __init__(self, country, region=None, metro=None):
        self.country = country or None
        self.region = region or None
        self.metro = metro or None

    def __repr__(self):
        return '<%s (%s/%s/%s)>' % (self.__class__.__name__, self.country,
                                    self.region, self.metro)

    def to_code(self):
        fields = [self.country, self.region, self.metro]
        return self.DELIMITER.join(i or '' for i in fields)

    @classmethod
    def from_code(cls, code):
        country, region, metro = [i or None for i in code.split(cls.DELIMITER)]
        return cls(country, region, metro)

    def contains(self, other):
        if not self.country:
            # self is set of all countries, it includes all possible
            # values of other.country
            return True
        elif not other or not other.country:
            # self is more specific than other
            return False
        else:
            # both self and other specify a country
            if self.country != other.country:
                # countries don't match
                return False
            else:
                # countries match
                if not self.metro:
                    # self.metro is set of all metros within country, it
                    # includes all possible values of other.metro
                    return True
                elif not other.metro:
                    # self is more specific than other
                    return False
                else:
                    return self.metro == other.metro

    def __eq__(self, other):
        if not isinstance(other, Location):
            return False

        return (self.country == other.country and
                self.region == other.region and
                self.metro == other.metro)

    def __ne__(self, other):
        return not self.__eq__(other)


def calc_impressions(total_budget_pennies, cpm_pennies):
    return int(total_budget_pennies / cpm_pennies * 1000)


NO_TRANSACTION = 0


class Collection(object):
    def __init__(self, name, sr_names, over_18=False, description=None,
            is_spotlight=False):
        self.name = name
        self.over_18 = over_18
        self.sr_names = sr_names
        self.description = description
        self.is_spotlight = is_spotlight

    @classmethod
    def by_name(cls, name):
        return CollectionStorage.get_collection(name)

    @classmethod
    def get_all(cls):
        """
        Return collections in this order:
        1. SFW/NSFW
        2. Spotlighted
        3. Alphabetical
        """
        all_collections = CollectionStorage.get_all()
        sorted_collections = sorted(all_collections, key=lambda collection:
            (collection.over_18, -collection.is_spotlight,
            collection.name.lower()))
        return sorted_collections

    def __repr__(self):
        return "<%s: %s>" % (self.__class__.__name__, self.name)


class CollectionStorage(tdb_cassandra.View):
    _use_db = True
    _connection_pool = 'main'
    _extra_schema_creation_args = {
        "key_validation_class": UTF8_TYPE,
        "column_name_class": UTF8_TYPE,
        "default_validation_class": UTF8_TYPE,
    }
    _compare_with = UTF8_TYPE
    _read_consistency_level = tdb_cassandra.CL.ONE
    _write_consistency_level = tdb_cassandra.CL.QUORUM
    SR_NAMES_DELIM = '|'

    @classmethod
    def _from_columns(cls, name, columns):
        description = columns['description']
        sr_names = columns['sr_names'].split(cls.SR_NAMES_DELIM)
        over_18 = columns.get("over_18") == "True"
        is_spotlight = columns.get("is_spotlight") == "True"
        return Collection(name, sr_names, over_18=over_18,
            description=description, is_spotlight=is_spotlight)

    @classmethod
    def _to_columns(cls, description, srs, over_18, is_spotlight):
        columns = {
            'description': description,
            'sr_names': cls.SR_NAMES_DELIM.join(sr.name for sr in srs),
            'over_18': str(over_18),
            'is_spotlight': str(is_spotlight),
        }
        return columns

    @classmethod
    def set(cls, name, description, srs, over_18=False, is_spotlight=False):
        rowkey = name
        columns = cls._to_columns(description, srs, over_18, is_spotlight)
        cls._set_values(rowkey, columns)

    @classmethod
    def _set_attributes(cls, name, attributes):
        rowkey = name
        for key in attributes:
            if not hasattr(Collection.by_name(name), key):
                raise AttributeError('No attribute on %s called %s'
                    % (name, key))

        columns = attributes
        cls._set_values(rowkey, columns)

    @classmethod
    def set_over_18(cls, name, over_18):
        cls._set_attributes(name, {'over_18': str(over_18)})

    @classmethod
    def set_is_spotlight(cls, name, is_spotlight):
        cls._set_attributes(name, {'is_spotlight': str(is_spotlight)})

    @classmethod
    def get_collection(cls, name):
        if not name:
            return None

        rowkey = name
        try:
            columns = cls._cf.get(rowkey)
        except tdb_cassandra.NotFoundException:
            return None

        return cls._from_columns(name, columns)

    @classmethod
    def get_all(cls):
        ret = []
        for name, columns in cls._cf.get_range():
            ret.append(cls._from_columns(name, columns))
        return ret

    @classmethod
    def delete(cls, name):
        rowkey = name
        cls._cf.remove(rowkey)


class Target(object):
    """Wrapper around either a Collection or a Subreddit name"""
    def __init__(self, target):
        if isinstance(target, Collection):
            self.collection = target
            self.is_collection = True
        elif isinstance(target, basestring):
            self.subreddit_name = target
            self.is_collection = False
        else:
            raise ValueError("target must be a Collection or Subreddit name")

        # defer looking up subreddits, we might only need their names
        self._subreddits = None

    @property
    def over_18(self):
        if self.is_collection:
            return self.collection.over_18
        else:
            subreddits = self.subreddits_slow
            return subreddits and subreddits[0].over_18

    @property
    def subreddit_names(self):
        if self.is_collection:
            return self.collection.sr_names
        else:
            return [self.subreddit_name]

    @property
    def subreddits_slow(self):
        if self._subreddits is not None:
            return self._subreddits

        sr_names = self.subreddit_names
        srs = Subreddit._by_name(sr_names).values()
        self._subreddits = srs
        return srs

    def __eq__(self, other):
        if self.is_collection != other.is_collection:
            return False

        return set(self.subreddit_names) == set(other.subreddit_names)

    def __ne__(self, other):
        return not self.__eq__(other)

    @property
    def pretty_name(self):
        if self.is_collection:
            return _("collection: %(name)s") % {'name': self.collection.name}
        elif self.subreddit_name == Frontpage.name:
            return _("frontpage")
        else:
            return "/r/%s" % self.subreddit_name

    def __repr__(self):
        return "<%s: %s>" % (self.__class__.__name__, self.pretty_name)


class PromoCampaign(Thing):
    _cache = g.thingcache
    _defaults = dict(
        priority_name=PROMOTE_DEFAULT_PRIORITY().name,
        trans_id=NO_TRANSACTION,
        trans_ip=None,
        trans_ip_country=None,
        trans_billing_country=None,
        trans_country_match=None,
        location_code=None,
        platform='desktop',
        mobile_os_names=None,
        ios_device_names=None,
        ios_version_names=None,
        android_device_names=None,
        android_version_names=None,
        frequency_cap=None,
        has_served=False,
        paused=False,
        total_budget_pennies=0,
        cost_basis=PROMOTE_COST_BASIS.fixed_cpm,
        bid_pennies=g.default_bid_pennies,
        adserver_spent_pennies=0,
    )

    # special attributes that shouldn't set Thing data attributes because they
    # have special setters that set other data attributes
    _derived_attrs = (
        "location",
        "priority",
        "target",
        "mobile_os",
        "ios_devices",
        "ios_version_range",
        "android_devices",
        "android_version_range",
        "is_auction",
    )

    SR_NAMES_DELIM = '|'
    SUBREDDIT_TARGET = "subreddit"
    MOBILE_TARGET_DELIM = ','

    @classmethod
    def _cache_prefix(cls):
        return "campaign:"

    def __getattr__(self, attr):
        val = super(PromoCampaign, self).__getattr__(attr)

        if (attr == 'total_budget_pennies' and hasattr(self, 'bid') and
                not getattr(self, 'bid_migrated', False)):
            old_bid = int(super(PromoCampaign, self).__getattr__('bid') * 100)
            self.total_budget_pennies = old_bid
            self.bid_migrated = True
            return self.total_budget_pennies

        if (attr == 'bid_pennies' and hasattr(self, 'cpm') and
                not getattr(self, 'cpm_migrated', False)):
            old_cpm = super(PromoCampaign, self).__getattr__('cpm')
            self.bid_pennies = old_cpm
            self.cpm_migrated = True
            return self.bid_pennies

        if attr in ('start_date', 'end_date'):
            val = to_datetime(val)
            if not val.tzinfo:
                val = val.replace(tzinfo=g.tz)
        return val

    def __setattr__(self, attr, val, make_dirty=True):
        if attr in self._derived_attrs:
            object.__setattr__(self, attr, val)
        else:
            Thing.__setattr__(self, attr, val, make_dirty=make_dirty)

    def __getstate__(self):
        """
        Remove _target before returning object state for pickling.

        Thing objects are pickled for caching. The state of the object is
        obtained by calling the __getstate__ method. Remove the _target
        attribute because it may contain Subreddits or other non-trivial objects
        that shouldn't be included.

        """

        state = self.__dict__
        if "_target" in state:
            state = {k: v for k, v in state.iteritems() if k != "_target"}
        return state

    @property
    def is_auction(self):
        if (self.cost_basis is not PROMOTE_COST_BASIS.fixed_cpm):
            return True

        return False

    def priority_name_from_priority(self, priority):
        if not priority in PROMOTE_PRIORITIES.values():
            raise ValueError("%s is not a valid priority" % priority.name)
        return priority.name

    @classmethod
    def location_code_from_location(cls, location):
        return location.to_code() if location else None

    @classmethod
    def unpack_target(cls, target):
        """Convert a Target into attributes suitable for storage."""
        sr_names = target.subreddit_names
        target_sr_names = cls.SR_NAMES_DELIM.join(sr_names)
        target_name = (target.collection.name if target.is_collection
                                              else cls.SUBREDDIT_TARGET)
        return target_sr_names, target_name

    @classmethod
    def create(cls, link, target, start_date, end_date,
               frequency_cap, priority, location,
               platform, mobile_os, ios_devices, ios_version_range,
               android_devices, android_version_range, total_budget_pennies,
               cost_basis, bid_pennies):
        pc = PromoCampaign(
            link_id=link._id,
            start_date=start_date,
            end_date=end_date,
            trans_id=NO_TRANSACTION,
            owner_id=link.author_id,
            total_budget_pennies=total_budget_pennies,
            cost_basis=cost_basis,
            bid_pennies=bid_pennies,
        )
        pc.frequency_cap = frequency_cap
        pc.priority = priority
        pc.location = location
        pc.target = target
        pc.platform = platform
        pc.mobile_os = mobile_os
        pc.ios_devices = ios_devices
        pc.ios_version_range = ios_version_range
        pc.android_devices = android_devices
        pc.android_version_range = android_version_range
        pc._commit()
        return pc

    @classmethod
    def _by_link(cls, link_id):
        '''
        Returns an iterable of campaigns associated with link_id or an empty
        list if there are none.
        '''
        return cls._query(PromoCampaign.c.link_id == link_id, data=True)

    @classmethod
    def _by_user(cls, account_id):
        '''
        Returns an iterable of all campaigns owned by account_id or an empty
        list if there are none.
        '''
        return cls._query(PromoCampaign.c.owner_id == account_id, data=True)

    @property
    def ndays(self):
        return (self.end_date - self.start_date).days

    @property
    def impressions(self):
        if self.cost_basis == PROMOTE_COST_BASIS.fixed_cpm:
            return calc_impressions(self.total_budget_pennies, self.bid_pennies)

        return 0

    @property
    def priority(self):
        return PROMOTE_PRIORITIES[self.priority_name]

    @priority.setter
    def priority(self, priority):
        self.priority_name = self.priority_name_from_priority(priority)

    @property
    def location(self):
        if self.location_code is not None:
            return Location.from_code(self.location_code)
        else:
            return None

    @location.setter
    def location(self, location):
        self.location_code = self.location_code_from_location(location)

    @property
    def target(self):
        if hasattr(self, "_target"):
            return self._target

        sr_names = self.target_sr_names.split(self.SR_NAMES_DELIM)
        if self.target_name == self.SUBREDDIT_TARGET:
            sr_name = sr_names[0]
            target = Target(sr_name)
        else:
            collection = Collection(self.target_name, sr_names)
            target = Target(collection)

        self._target = target
        return target

    @target.setter
    def target(self, target):
        self.target_sr_names, self.target_name = self.unpack_target(target)

        # set _target so we don't need to lookup on subsequent access
        self._target = target

    def _mobile_target_getter(self, target):
        if not target:
            return None
        else:
            return target.split(self.MOBILE_TARGET_DELIM)

    def _mobile_target_setter(self, target_names):
        if not target_names:
            return None
        else:
            return self.MOBILE_TARGET_DELIM.join(target_names)

    @property
    def mobile_os(self):
        return self._mobile_target_getter(self.mobile_os_names)

    @mobile_os.setter
    def mobile_os(self, mobile_os_names):
        self.mobile_os_names = self._mobile_target_setter(mobile_os_names)

    @property
    def ios_devices(self):
        return self._mobile_target_getter(self.ios_device_names)

    @ios_devices.setter
    def ios_devices(self, ios_device_names):
        self.ios_device_names = self._mobile_target_setter(ios_device_names)

    @property
    def android_devices(self):
        return self._mobile_target_getter(self.android_device_names)

    @android_devices.setter
    def android_devices(self, android_device_names):
        self.android_device_names = self._mobile_target_setter(android_device_names)

    @property
    def ios_version_range(self):
        return self._mobile_target_getter(self.ios_version_names)

    @ios_version_range.setter
    def ios_version_range(self, ios_version_names):
        self.ios_version_names = self._mobile_target_setter(ios_version_names)

    @property
    def android_version_range(self):
        return self._mobile_target_getter(self.android_version_names)

    @android_version_range.setter
    def android_version_range(self, android_version_names):
        self.android_version_names = self._mobile_target_setter(android_version_names)

    @property
    def location_str(self):
        if not self.location:
            return ''
        elif self.location.region:
            country = self.location.country
            region = self.location.region
            if self.location.metro:
                metro_str = (g.locations[country]['regions'][region]
                             ['metros'][self.location.metro]['name'])
                return '/'.join([country, region, metro_str])
            else:
                region_name = g.locations[country]['regions'][region]['name']
                return ('%s, %s' % (region_name, country))
        else:
            return g.locations[self.location.country]['name']

    @property
    def is_paid(self):
        return self.trans_id != 0 or self.priority == HOUSE

    def is_freebie(self):
        return self.trans_id < 0

    def is_live_now(self):
        now = datetime.now(g.tz)
        return self.start_date < now and self.end_date > now

    @property
    def is_house(self):
       return self.priority == HOUSE

    @property
    def total_budget_dollars(self):
        return self.total_budget_pennies / 100.

    @property
    def bid_dollars(self):
        return self.bid_pennies / 100.

    def delete(self):
        self._deleted = True
        self._commit()


def backfill_campaign_targets():
    from r2.lib.db.operators import desc
    from r2.lib.utils import fetch_things2

    q = PromoCampaign._query(sort=desc("_date"), data=True)
    for campaign in fetch_things2(q):
        sr_name = campaign.sr_name or Frontpage.name
        campaign.target = Target(sr_name)
        campaign._commit()

class PromotionLog(tdb_cassandra.View):
    _use_db = True
    _connection_pool = 'main'
    _compare_with = TIME_UUID_TYPE

    @classmethod
    def _rowkey(cls, link):
        return link._fullname

    @classmethod
    def add(cls, link, text):
        name = c.user.name if c.user_is_loggedin else "<AUTOMATED>"
        now = datetime.now(g.tz).strftime("%Y-%m-%d %H:%M:%S")
        text = "[%s: %s] %s" % (name, now, text)
        rowkey = cls._rowkey(link)
        column = {uuid1(): _force_unicode(text)}
        cls._set_values(rowkey, column)
        return text

    @classmethod
    def get(cls, link):
        rowkey = cls._rowkey(link)
        try:
            row = cls._byID(rowkey)
        except tdb_cassandra.NotFound:
            return []
        tuples = sorted(row._values().items(), key=lambda t: t[0].time)
        return [t[1] for t in tuples]


class PromotionPrices(tdb_cassandra.View):
    """
    Check all the following potentially specially priced conditions:
    * metro level targeting
    * country level targeting (but not if the metro targeting is used)
    * collection targeting
    * frontpage targeting
    * subreddit targeting

    The price is the maximum price for all matching conditions. If no special
    conditions are met use the global price.

    """

    _use_db = True
    _connection_pool = 'main'
    _read_consistency_level = tdb_cassandra.CL.ONE
    _write_consistency_level = tdb_cassandra.CL.ALL
    _extra_schema_creation_args = {
        "key_validation_class": UTF8_TYPE,
        "column_name_class": UTF8_TYPE,
        "default_validation_class": INT_TYPE,
    }

    COLLECTION_DEFAULT = g.cpm_selfserve_collection.pennies
    SUBREDDIT_DEFAULT = g.cpm_selfserve.pennies
    COUNTRY_DEFAULT = g.cpm_selfserve_geotarget_country.pennies
    METRO_DEFAULT = g.cpm_selfserve_geotarget_metro.pennies

    @classmethod
    def _rowkey_and_column_from_target(cls, target):
        rowkey = column_name = None

        if isinstance(target, Target):
            if target.is_collection:
                rowkey = "COLLECTION"
                column_name = target.collection.name
            else:
                rowkey = "SUBREDDIT"
                column_name = target.subreddit_name

        if not rowkey or not column_name:
            raise ValueError("target must be Target")

        return rowkey, column_name

    @classmethod
    def _rowkey_and_column_from_location(cls, location):
        if not isinstance(location, Location):
            raise ValueError("location must be Location")

        if location.metro:
            rowkey = "METRO"
            # NOTE: the column_name will also be the key used in the frontend
            # to determine pricing
            column_name = ''.join(map(str, (location.country, location.metro)))
        else:
            rowkey = "COUNTRY"
            column_name = location.country
        return rowkey, column_name

    @classmethod
    def set_target_price(cls, target, cpm):
        rowkey, column_name = cls._rowkey_and_column_from_target(target)
        cls._cf.insert(rowkey, {column_name: cpm})

    @classmethod
    def set_location_price(cls, location, cpm):
        rowkey, column_name = cls._rowkey_and_column_from_location(location)
        cls._cf.insert(rowkey, {column_name: cpm})

    @classmethod
    def lookup_target_price(cls, target, default):
        rowkey, column_name = cls._rowkey_and_column_from_target(target)
        target_price = cls._lookup_price(rowkey, column_name)
        return target_price or default

    @classmethod
    def lookup_location_price(cls, location, default):
        rowkey, column_name = cls._rowkey_and_column_from_location(location)
        location_price = cls._lookup_price(rowkey, column_name)
        return location_price or default

    @classmethod
    def _lookup_price(cls, rowkey, column_name):
        try:
            columns = cls._cf.get(rowkey, columns=[column_name])
        except tdb_cassandra.NotFoundException:
            columns = {}

        return columns.get(column_name)

    @classmethod
    def get_price(cls, user, target, location):
        if user.selfserve_cpm_override_pennies:
            return user.selfserve_cpm_override_pennies

        prices = []

        # set location specific prices or use defaults
        if location and location.metro:
            metro_price = cls.lookup_location_price(location, cls.METRO_DEFAULT)
            prices.append(metro_price)
        elif location:
            country_price = cls.lookup_location_price(
                location, cls.COUNTRY_DEFAULT)
            prices.append(country_price)

        # set target specific prices or use default
        if (not target.is_collection and
                target.subreddit_name == Frontpage.name):
            # Frontpage is priced as a collection
            prices.append(cls.COLLECTION_DEFAULT)
        elif target.is_collection:
            collection_price = cls.lookup_target_price(
                target, cls.COLLECTION_DEFAULT)
            prices.append(collection_price)
        else:
            subreddit_price = cls.lookup_target_price(
                target, cls.SUBREDDIT_DEFAULT)
            prices.append(subreddit_price)

        return max(prices)

    @classmethod
    def get_price_dict(cls, user):
        if user.selfserve_cpm_override_pennies:
            r = {
                "COLLECTION": {},
                "SUBREDDIT": {},
                "COUNTRY": {},
                "METRO": {},
                "COLLECTION_DEFAULT": user.selfserve_cpm_override_pennies,
                "SUBREDDIT_DEFAULT": user.selfserve_cpm_override_pennies,
                "COUNTRY_DEFAULT": user.selfserve_cpm_override_pennies,
                "METRO_DEFAULT": user.selfserve_cpm_override_pennies,
            }
        else:
            r = {
                "COLLECTION": {},
                "SUBREDDIT": {},
                "COUNTRY": {},
                "METRO": {},
                "COLLECTION_DEFAULT": g.cpm_selfserve_collection.pennies,
                "SUBREDDIT_DEFAULT": g.cpm_selfserve.pennies,
                "COUNTRY_DEFAULT": g.cpm_selfserve_geotarget_country.pennies,
                "METRO_DEFAULT": g.cpm_selfserve_geotarget_metro.pennies,
            }

            try:
                collections = cls._cf.get("COLLECTION")
            except tdb_cassandra.NotFoundException:
                collections = {}

            try:
                subreddits = cls._cf.get("SUBREDDIT")
            except tdb_cassandra.NotFoundException:
                subreddits = {}

            try:
                countries = cls._cf.get("COUNTRY")
            except tdb_cassandra.NotFoundException:
                countries = {}

            try:
                metros = cls._cf.get("METRO")
            except tdb_cassandra.NotFoundException:
                metros = {}

            for name, cpm in collections.iteritems():
                r["COLLECTION"][name] = cpm

            for name, cpm in subreddits.iteritems():
                r["SUBREDDIT"][name] = cpm

            for name, cpm in countries.iteritems():
                r["COUNTRY"][name] = cpm

            for name, cpm in metros.iteritems():
                r["METRO"][name] = cpm

        return r
