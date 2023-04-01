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

import datetime

from pylons import request
from pylons import app_globals as g
from sqlalchemy import (
    and_,
    Boolean,
    BigInteger,
    Column,
    DateTime,
    Date,
    distinct,
    Float,
    func as safunc,
    Integer,
    String,
)
from sqlalchemy.dialects.postgresql.base import PGInet as Inet
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm.exc import NoResultFound

from r2.lib.db.thing import Thing, NotFound
from r2.lib.memoize import memoize
from r2.lib.utils import Enum, to_date, tup
from r2.models.account import Account
from r2.models import Link, Frontpage


engine = g.dbm.get_engine('authorize')
# Allocate a session maker for communicating object changes with the back end  
Session = sessionmaker(autocommit = True, autoflush = True, bind = engine)
# allocate a SQLalchemy base class for auto-creation of tables based
# on class fields.  
# NB: any class that inherits from this class will result in a table
# being created, and subclassing doesn't work, hence the
# object-inheriting interface classes.
Base = declarative_base(bind = engine)

class Sessionized(object):
    """
    Interface class for wrapping up the "session" in the 0.5 ORM
    required for all database communication.  This allows subclasses
    to have a "query" and "commit" method that doesn't require
    managing of the session.
    """
    session = Session()

    def __init__(self, *a, **kw):
        """
        Common init used by all other classes in this file.  Allows
        for object-creation based on the __table__ field which is
        created by Base (further explained in _disambiguate_args).
        """
        for k, v in self._disambiguate_args(None, *a, **kw):
            setattr(self, k.name, v)
    
    @classmethod
    def _new(cls, *a, **kw):
        """
        Just like __init__, except the new object is committed to the
        db before being returned.
        """
        obj = cls(*a, **kw)
        obj._commit()
        return obj

    def _commit(self):
        """
        Commits current object to the db.
        """
        with self.session.begin():
            self.session.add(self)

    def _delete(self):
        """
        Deletes current object from the db. 
        """
        with self.session.begin():
            self.session.delete(self)

    @classmethod
    def query(cls, **kw):
        """
        Ubiquitous class-level query function. 
        """
        q = cls.session.query(cls)
        if kw:
            q = q.filter_by(**kw)
        return q

    @classmethod
    def _disambiguate_args(cls, filter_fn, *a, **kw):
        """
        Used in _lookup and __init__ to interpret *a as being a list
        of args to match columns in the same order as __table__.c

        For example, if a class Foo has fields a and b, this function
        allows the two to work identically:
        
        >>> foo = Foo(a = 'arg1', b = 'arg2')
        >>> foo = Foo('arg1', 'arg2')

        Additionally, this function invokes _make_storable on each of
        the values in the arg list (including *a as well as
        kw.values())

        """
        args = []
        if filter_fn is None:
            cols = cls.__table__.c
        else:
            cols = filter(filter_fn, cls.__table__.c)
        for k, v in zip(cols, a):
            if not kw.has_key(k.name):
                args.append((k, cls._make_storable(v)))
            else:
                raise TypeError,\
                      "got multiple arguments for '%s'" % k.name

        cols = dict((x.name, x) for x in cls.__table__.c)
        for k, v in kw.iteritems():
            if cols.has_key(k):
                args.append((cols[k], cls._make_storable(v)))
        return args

    @classmethod
    def _make_storable(self, val):
        if isinstance(val, Account):
            return val._id
        elif isinstance(val, Thing):
            return val._fullname
        else:
            return val

    @classmethod
    def _lookup(cls, multiple, *a, **kw):
        """
        Generates an executes a query where it matches *a to the
        primary keys of the current class's table.

        The primary key nature can be overridden by providing an
        explicit list of columns to search.

        This function is only a convenience function, and is called
        only by one() and lookup().
        """
        args = cls._disambiguate_args(lambda x: x.primary_key, *a, **kw)
        res = cls.query().filter(and_(*[k == v for k, v in args]))
        try:
            res = res.all() if multiple else res.one()
            # res.one() will raise NoResultFound, while all() will
            # return an empty list.  This will make the response
            # uniform
            if not res:
                raise NoResultFound
        except NoResultFound: 
            raise NotFound, "%s with %s" % \
                (cls.__name__,
                 ",".join("%s=%s" % x for x in args))
        return res

    @classmethod
    def lookup(cls, *a, **kw):
        """
        Returns all objects which match the kw list, or primary keys
        that match the *a.
        """
        return cls._lookup(True, *a, **kw)

    @classmethod
    def one(cls, *a, **kw):
        """
        Same as lookup, but returns only one argument. 
        """
        return cls._lookup(False, *a, **kw)

    @classmethod
    def add(cls, key, *a):
        try:
            cls.one(key, *a)
        except NotFound:
            cls(key, *a)._commit()
    
    @classmethod
    def delete(cls, key, *a):
        try:
            cls.one(key, *a)._delete()
        except NotFound:
            pass
    
    @classmethod
    def get(cls, key):
        try:
            return cls.lookup(key)
        except NotFound:
            return []

class CustomerID(Sessionized, Base):
    __tablename__  = "authorize_account_id"

    account_id    = Column(BigInteger, primary_key = True,
                           autoincrement = False)
    authorize_id  = Column(BigInteger)

    def __repr__(self):
        return "<AuthNetID(%s)>" % self.authorize_id

    @classmethod
    def set(cls, user, _id):
        try:
            existing = cls.one(user)
            existing.authorize_id = _id
            existing._commit()
        except NotFound:
            cls(user, _id)._commit()
    
    @classmethod
    def get_id(cls, user):
        try:
            return cls.one(user).authorize_id
        except NotFound:
            return

class PayID(Sessionized, Base):
    __tablename__ = "authorize_pay_id"

    account_id    = Column(BigInteger, primary_key = True,
                           autoincrement = False)
    pay_id        = Column(BigInteger, primary_key = True,
                           autoincrement = False)

    def __repr__(self):
        return "<%s(%d)>" % (self.__class__.__name__, self.authorize_id)

    @classmethod
    def get_ids(cls, key):
        return [int(x.pay_id) for x in cls.get(key)]

class Bid(Sessionized, Base):
    __tablename__ = "bids"

    STATUS        = Enum("AUTH", "CHARGE", "REFUND", "VOID")

    # will be unique from authorize
    transaction   = Column(BigInteger, primary_key = True,
                           autoincrement = False)

    # identifying characteristics
    account_id    = Column(BigInteger, index = True, nullable = False)
    pay_id        = Column(BigInteger, index = True, nullable = False)
    thing_id      = Column(BigInteger, index = True, nullable = False)

    # breadcrumbs
    ip            = Column(Inet)
    date          = Column(DateTime(timezone = True), default = safunc.now(),
                           nullable = False)

    # bid information:
    bid           = Column(Float, nullable = False)
    charge        = Column(Float)

    status        = Column(Integer, nullable = False,
                           default = STATUS.AUTH)

    # make this a primary key as well so that we can have more than
    # one freebie per campaign
    campaign      = Column(Integer, default = 0, primary_key = True)

    @classmethod
    def _new(cls, trans_id, user, pay_id, thing_id, bid, campaign = 0):
        bid = Bid(trans_id, user, pay_id, 
                  thing_id, getattr(request, 'ip', '0.0.0.0'), bid = bid,
                  campaign = campaign)
        bid._commit()
        return bid

#    @classmethod
#    def for_transactions(cls, transids):
#        transids = filter(lambda x: x != 0, transids)
#        if transids:
#            q = cls.query()
#            q = q.filter(or_(*[cls.transaction == i for i in transids]))
#            return dict((p.transaction, p) for p in q)
#        return {}

    def set_status(self, status):
        if self.status != status:
            self.status = status
            self._commit()

    def auth(self):
        self.set_status(self.STATUS.AUTH)

    def is_auth(self):
        return (self.status == self.STATUS.AUTH)

    def void(self):
        self.set_status(self.STATUS.VOID)

    def is_void(self):
        return (self.status == self.STATUS.VOID)

    def charged(self):
        self.charge = self.bid
        self.set_status(self.STATUS.CHARGE)
        self._commit()

    def is_charged(self):
        """
        Returns True if transaction has been charged with authorize.net or is
        a freebie with "charged" status.
        """
        return (self.status == self.STATUS.CHARGE)

    def refund(self, amount):
        current_charge = self.charge or self.bid    # needed if charged() not
                                                    # setting charge attr
        self.charge = current_charge - amount
        self.set_status(self.STATUS.REFUND)
        self._commit()

    def is_refund(self):
        return (self.status == self.STATUS.REFUND)

    @property
    def charge_amount(self):
        return self.charge or self.bid


class PromotionWeights(Sessionized, Base):
    __tablename__ = "promotion_weight"

    thing_name = Column(String, primary_key = True,
                        nullable = False, index = True)

    promo_idx    = Column(BigInteger, index = True, autoincrement = False,
                          primary_key = True)

    sr_name    = Column(String, primary_key = True,
                        nullable = True,  index = True)
    date       = Column(Date(), primary_key = True,
                        nullable = False, index = True)

    # because we might want to search by account
    account_id   = Column(BigInteger, index = True, autoincrement = False)

    # bid and weight should always be the same, but they don't have to be
    bid        = Column(Float, nullable = False)
    weight     = Column(Float, nullable = False)
    finished   = Column(Boolean)
    # NOTE: bid, weight, finished columns are not used

    @classmethod
    def filter_sr_name(cls, sr_name):
        # LEGACY: use empty string to indicate Frontpage
        return '' if sr_name == Frontpage.name else sr_name

    @classmethod
    def reschedule(cls, link, campaign):
        cls.delete(link, campaign)
        cls.add(link, campaign)

    @classmethod
    def add(cls, link, campaign):
        start_date = to_date(campaign.start_date)
        end_date = to_date(campaign.end_date)
        ndays = (end_date - start_date).days
        # note that end_date is not included
        dates = [start_date + datetime.timedelta(days=i) for i in xrange(ndays)]

        sr_names = campaign.target.subreddit_names
        sr_names = {cls.filter_sr_name(sr_name) for sr_name in sr_names}

        with cls.session.begin():
            for sr_name in sr_names:
                for date in dates:
                    obj = cls(
                        thing_name=link._fullname,
                        promo_idx=campaign._id,
                        sr_name=sr_name,
                        date=date,
                        account_id=link.author_id,
                        bid=0.,
                        weight=0.,
                        finished=False,
                    )
                    cls.session.add(obj)

    @classmethod
    def delete(cls, link, campaign):
        query = cls.query(thing_name=link._fullname, promo_idx=campaign._id)
        with cls.session.begin():
            for item in query:
                cls.session.delete(item)

    @classmethod
    def _filter_query(cls, query, start, end=None, link=None,
                      author_id=None, sr_names=None):
        start = to_date(start)

        if end:
            end = to_date(end)
            query = query.filter(and_(cls.date >= start, cls.date < end))
        else:
            query = query.filter(cls.date == start)

        if link:
            query = query.filter(cls.thing_name == link._fullname)

        if author_id:
            query = query.filter(cls.account_id == author_id)

        if sr_names:
            sr_names = [cls.filter_sr_name(sr_name) for sr_name in sr_names]
            query = query.filter(cls.sr_name.in_(sr_names))

        return query

    @classmethod
    def get_campaign_ids(cls, start, end=None, link=None, author_id=None,
                         sr_names=None):
        query = cls.session.query(distinct(cls.promo_idx))
        query = cls._filter_query(query, start, end, link, author_id, sr_names)
        return {i[0] for i in query}

    @classmethod
    def get_link_names(cls, start, end=None, link=None, author_id=None,
                       sr_names=None):
        query = cls.session.query(distinct(cls.thing_name))
        query = cls._filter_query(query, start, end, link, author_id, sr_names)
        return {i[0] for i in query}


# do all the leg work of creating/connecting to tables
if g.db_create_tables:
    Base.metadata.create_all()

