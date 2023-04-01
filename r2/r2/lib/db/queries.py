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

import collections
from copy import deepcopy, copy
import cPickle as pickle
from datetime import datetime
from functools import partial
import hashlib
import itertools
import pytz
from time import mktime

from pylons import app_globals as g
from pylons import tmpl_context as c
from pylons import request

from r2.lib import amqp
from r2.lib import filters
from r2.lib.comment_tree import add_comments
from r2.lib.db import tdb_cassandra
from r2.lib.db.operators import and_, or_
from r2.lib.db.operators import asc, desc, timeago
from r2.lib.db.sorts import epoch_seconds
from r2.lib.db.thing import Thing, Merge
from r2.lib import utils
from r2.lib.utils import in_chunks, is_subdomain, SimpleSillyStub
from r2.lib.utils import fetch_things2, tup, UniqueIterator
from r2.lib.voting import prequeued_vote_key
from r2.models import (
    Account,
    Comment,
    Inbox,
    Link,
    LinksByAccount,
    Message,
    ModContribSR,
    ModeratorInbox,
    MultiReddit,
    PromoCampaign,
    Report,
    Subreddit,
    VotesByAccount,
)
from r2.models.last_modified import LastModified
from r2.models.promo import PROMOTE_STATUS, PromotionLog
from r2.models.query_cache import (
    cached_query,
    CachedQuery,
    CachedQueryMutator,
    filter_thing,
    FakeQuery,
    merged_cached_query,
    MergedCachedQuery,
    SubredditQueryCache,
    ThingTupleComparator,
    UserQueryCache,
)
from r2.models.vote import Vote


precompute_limit = 1000

db_sorts = dict(hot = (desc, '_hot'),
                new = (desc, '_date'),
                top = (desc, '_score'),
                controversial = (desc, '_controversy'))

def db_sort(sort):
    cls, col = db_sorts[sort]
    return cls(col)

db_times = dict(all = None,
                hour = Thing.c._date >= timeago('1 hour'),
                day = Thing.c._date >= timeago('1 day'),
                week = Thing.c._date >= timeago('1 week'),
                month = Thing.c._date >= timeago('1 month'),
                year = Thing.c._date >= timeago('1 year'))

# sorts for which there can be a time filter (by day, by week,
# etc). All of these but 'all' are done in mr_top, who knows about the
# structure of the stored CachedResults (so changes here may warrant
# changes there)
time_filtered_sorts = set(('top', 'controversial'))

#we need to define the filter functions here so cachedresults can be pickled
def filter_identity(x):
    return x

def filter_thing2(x):
    """A filter to apply to the results of a relationship query returns
    the object of the relationship."""
    return x._thing2


class CachedResults(object):
    """Given a query returns a list-like object that will lazily look up
    the query from the persistent cache. """
    def __init__(self, query, filter):
        self.query = query
        self.query._limit = precompute_limit
        self.filter = filter
        self.iden = self.get_query_iden(query)
        self.sort_cols = [s.col for s in self.query._sort]
        self.data = []
        self._fetched = False

    @classmethod
    def get_query_iden(cls, query):
        # previously in Query._iden()
        i = str(query._sort) + str(query._kind) + str(query._limit)

        if query._offset:
            i += str(query._offset)

        if query._rules:
            rules = copy(query._rules)
            rules.sort()
            for r in rules:
                i += str(r)

        return hashlib.sha1(i).hexdigest()

    @property
    def sort(self):
        return self.query._sort

    def fetch(self, force=False, stale=False):
        """Loads the query from the cache."""
        self.fetch_multi([self], force=force, stale=stale)

    @classmethod
    def fetch_multi(cls, crs, force=False, stale=False):
        unfetched = filter(lambda cr: force or not cr._fetched, crs)
        if not unfetched:
            return

        keys = [cr.iden for cr in unfetched]
        cached = g.permacache.get_multi(
            keys=keys,
            allow_local=not force,
            stale=stale,
        )
        for cr in unfetched:
            cr.data = cached.get(cr.iden) or []
            cr._fetched = True

    def make_item_tuple(self, item):
        """Given a single 'item' from the result of a query build the tuple
        that will be stored in the query cache. It is effectively the
        fullname of the item after passing through the filter plus the
        columns of the unfiltered item to sort by."""
        filtered_item = self.filter(item)
        lst = [filtered_item._fullname]
        for col in self.sort_cols:
            #take the property of the original 
            attr = getattr(item, col)
            #convert dates to epochs to take less space
            if isinstance(attr, datetime):
                attr = epoch_seconds(attr)
            lst.append(attr)
        return tuple(lst)

    def can_insert(self):
        """True if a new item can just be inserted rather than
           rerunning the query."""
         # This is only true in some circumstances: queries where
         # eligibility in the list is determined only by its sort
         # value (e.g. hot) and where addition/removal from the list
         # incurs an insertion/deletion event called on the query. So
         # the top hottest items in X some subreddit where the query
         # is notified on every submission/banning/unbanning/deleting
         # will work, but for queries with a time-component or some
         # other eligibility factor, it cannot be inserted this way.
        if self.query._sort in ([desc('_date')],
                                [desc('_hot'), desc('_date')],
                                [desc('_score'), desc('_date')],
                                [desc('_controversy'), desc('_date')]):
            if not any(r for r in self.query._rules
                       if r.lval.name == '_date'):
                # if no time-rule is specified, then it's 'all'
                return True
        return False

    def can_delete(self):
        "True if a item can be removed from the listing, always true for now."
        return True

    def _mutate(self, fn, willread=True):
        self.data = g.permacache.mutate(
            key=self.iden,
            mutation_fn=fn,
            default=[],
            willread=willread,
        )
        self._fetched=True

    def insert(self, items):
        """Inserts the item into the cached data. This only works
           under certain criteria, see can_insert."""
        self._insert_tuples([self.make_item_tuple(item) for item in tup(items)])

    def _insert_tuples(self, tuples):
        def _mutate(data):
            data = data or []
            item_tuples = tuples or []

            existing_fnames = {item[0] for item in data}
            new_fnames = {item[0] for item in item_tuples}

            mutated_length = len(existing_fnames.union(new_fnames))
            would_truncate = mutated_length >= precompute_limit
            if would_truncate and data:
                # only insert items that are already stored or new items
                # that are large enough that they won't be immediately truncated
                # out of storage
                # item structure is (name, sortval1[, sortval2, ...])
                smallest = data[-1]
                item_tuples = [item for item in item_tuples
                                    if (item[0] in existing_fnames or
                                        item[1:] >= smallest[1:])]

            if not item_tuples:
                return data

            # insert the items, remove the duplicates (keeping the
            # one being inserted over the stored value if applicable),
            # and sort the result
            data = filter(lambda x: x[0] not in new_fnames, data)
            data.extend(item_tuples)
            data.sort(reverse=True, key=lambda x: x[1:])
            if len(data) > precompute_limit:
                data = data[:precompute_limit]
            return data

        self._mutate(_mutate)

    def delete(self, items):
        """Deletes an item from the cached data."""
        fnames = set(self.filter(x)._fullname for x in tup(items))

        def _mutate(data):
            data = data or []
            return filter(lambda x: x[0] not in fnames,
                          data)

        self._mutate(_mutate)

    def _replace(self, tuples, lock=True):
        """Take pre-rendered tuples from mr_top and replace the
           contents of the query outright. This should be considered a
           private API"""
        if lock:
            def _mutate(data):
                return tuples
            self._mutate(_mutate, willread=False)
        else:
            self._fetched = True
            self.data = tuples
            g.permacache.pessimistically_set(self.iden, tuples)

    def update(self):
        """Runs the query and stores the result in the cache. This is
           only run by hand."""
        self.data = [self.make_item_tuple(i) for i in self.query]
        self._fetched = True
        g.permacache.set(self.iden, self.data)

    def __repr__(self):
        return '<CachedResults %s %s>' % (self.query._rules, self.query._sort)

    def __iter__(self):
        self.fetch()

        for x in self.data:
            yield x[0]

class MergedCachedResults(object):
    """Given two CachedResults, merges their lists based on the sorts
       of their queries."""
    # normally we'd do this by having a superclass of CachedResults,
    # but we have legacy pickled CachedResults that we don't want to
    # break

    def __init__(self, results):
        self.cached_results = results
        CachedResults.fetch_multi([r for r in results
                                   if isinstance(r, CachedResults)])
        CachedQuery._fetch_multi([r for r in results
                                   if isinstance(r, CachedQuery)])
        self._fetched = True

        self.sort = results[0].sort
        comparator = ThingTupleComparator(self.sort)
        # make sure they're all the same
        assert all(r.sort == self.sort for r in results[1:])

        all_items = []
        for cr in results:
            all_items.extend(cr.data)
        all_items.sort(cmp=comparator)
        self.data = all_items


    def __repr__(self):
        return '<MergedCachedResults %r>' % (self.cached_results,)

    def __iter__(self):
        for x in self.data:
            yield x[0]

    def update(self):
        for x in self.cached_results:
            x.update()

def make_results(query, filter = filter_identity):
    return CachedResults(query, filter)

def merge_results(*results):
    if not results:
        return []
    return MergedCachedResults(results)

def migrating_cached_query(model, filter_fn=filter_identity):
    """Returns a CachedResults object that has a new-style cached query
    attached as "new_query". This way, reads will happen from the old
    query cache while writes can be made to go to both caches until a
    backfill migration is complete."""

    decorator = cached_query(model, filter_fn)
    def migrating_cached_query_decorator(fn):
        wrapped = decorator(fn)
        def migrating_cached_query_wrapper(*args):
            new_query = wrapped(*args)
            old_query = make_results(new_query.query, filter_fn)
            old_query.new_query = new_query
            return old_query
        return migrating_cached_query_wrapper
    return migrating_cached_query_decorator


@cached_query(UserQueryCache)
def get_deleted_links(user_id):
    return Link._query(Link.c.author_id == user_id,
                       Link.c._deleted == True,
                       Link.c._spam == (True, False),
                       sort=db_sort('new'))


@cached_query(UserQueryCache)
def get_deleted_comments(user_id):
    return Comment._query(Comment.c.author_id == user_id,
                          Comment.c._deleted == True,
                          Comment.c._spam == (True, False),
                          sort=db_sort('new'))


@merged_cached_query
def get_deleted(user):
    return [get_deleted_links(user),
            get_deleted_comments(user)]


def get_links(sr, sort, time):
    return _get_links(sr._id, sort, time)

def _get_links(sr_id, sort, time):
    """General link query for a subreddit."""
    q = Link._query(Link.c.sr_id == sr_id,
                    sort = db_sort(sort),
                    data = True)

    if time != 'all':
        q._filter(db_times[time])

    res = make_results(q)

    return res

@cached_query(SubredditQueryCache)
def get_spam_links(sr_id):
    return Link._query(Link.c.sr_id == sr_id,
                       Link.c._spam == True,
                       sort = db_sort('new'))

@cached_query(SubredditQueryCache)
def get_spam_comments(sr_id):
    return Comment._query(Comment.c.sr_id == sr_id,
                          Comment.c._spam == True,
                          sort = db_sort('new'))


@cached_query(SubredditQueryCache)
def get_edited_comments(sr_id):
    return FakeQuery(sort=[desc("editted")])


@cached_query(SubredditQueryCache)
def get_edited_links(sr_id):
    return FakeQuery(sort=[desc("editted")])


@merged_cached_query
def get_edited(sr, user=None, include_links=True, include_comments=True):
    sr_ids = moderated_srids(sr, user)
    queries = []

    if include_links:
        queries.append(get_edited_links)
    if include_comments:
        queries.append(get_edited_comments)
    return [query(sr_id) for sr_id, query in itertools.product(sr_ids, queries)]


def moderated_srids(sr, user):
    if isinstance(sr, (ModContribSR, MultiReddit)):
        srs = Subreddit._byID(sr.sr_ids, return_dict=False)
        if user:
            srs = [sr for sr in srs
                   if sr.is_moderator_with_perms(user, 'posts')]
        return [sr._id for sr in srs]
    else:
        return [sr._id]

@merged_cached_query
def get_spam(sr, user=None, include_links=True, include_comments=True):
    sr_ids = moderated_srids(sr, user)
    queries = []

    if include_links:
        queries.append(get_spam_links)
    if include_comments:
        queries.append(get_spam_comments)
    return [query(sr_id) for sr_id, query in itertools.product(sr_ids, queries)]

@cached_query(SubredditQueryCache)
def get_spam_filtered_links(sr_id):
    """ NOTE: This query will never run unless someone does an "update" on it,
        but that will probably timeout. Use insert_spam_filtered_links."""
    return Link._query(Link.c.sr_id == sr_id,
                       Link.c._spam == True,
                       Link.c.verdict != 'mod-removed',
                       sort = db_sort('new'))

@cached_query(SubredditQueryCache)
def get_spam_filtered_comments(sr_id):
    return Comment._query(Comment.c.sr_id == sr_id,
                          Comment.c._spam == True,
                          Comment.c.verdict != 'mod-removed',
                          sort = db_sort('new'))

@merged_cached_query
def get_spam_filtered(sr):
    return [get_spam_filtered_links(sr),
            get_spam_filtered_comments(sr)]

@cached_query(SubredditQueryCache)
def get_reported_links(sr_id):
    q = Link._query(Link.c.reported != 0,
                    Link.c._spam == False,
                    sort = db_sort('new'))
    if sr_id is not None:
        q._filter(Link.c.sr_id == sr_id)
    return q

@cached_query(SubredditQueryCache)
def get_reported_comments(sr_id):
    q = Comment._query(Comment.c.reported != 0,
                          Comment.c._spam == False,
                          sort = db_sort('new'))

    if sr_id is not None:
        q._filter(Comment.c.sr_id == sr_id)
    return q

@merged_cached_query
def get_reported(sr, user=None, include_links=True, include_comments=True):
    sr_ids = moderated_srids(sr, user)
    queries = []

    if include_links:
        queries.append(get_reported_links)
    if include_comments:
        queries.append(get_reported_comments)
    return [query(sr_id) for sr_id, query in itertools.product(sr_ids, queries)]

@cached_query(SubredditQueryCache)
def get_unmoderated_links(sr_id):
    q = Link._query(Link.c.sr_id == sr_id,
                    Link.c._spam == (True, False),
                    sort = db_sort('new'))

    # Doesn't really work because will not return Links with no verdict
    q._filter(or_(and_(Link.c._spam == True, Link.c.verdict != 'mod-removed'),
                  and_(Link.c._spam == False, Link.c.verdict != 'mod-approved')))
    return q

@merged_cached_query
def get_modqueue(sr, user=None, include_links=True, include_comments=True):
    sr_ids = moderated_srids(sr, user)
    queries = []

    if include_links:
        queries.append(get_reported_links)
        queries.append(get_spam_filtered_links)
    if include_comments:
        queries.append(get_reported_comments)
        queries.append(get_spam_filtered_comments)
    return [query(sr_id) for sr_id, query in itertools.product(sr_ids, queries)]

@merged_cached_query
def get_unmoderated(sr, user=None):
    sr_ids = moderated_srids(sr, user)
    queries = [get_unmoderated_links]
    return [query(sr_id) for sr_id, query in itertools.product(sr_ids, queries)]

def get_domain_links(domain, sort, time):
    from r2.lib.db import operators
    q = Link._query(operators.domain(Link.c.url) == filters._force_utf8(domain),
                    sort = db_sort(sort),
                    data = True)
    if time != "all":
        q._filter(db_times[time])

    return make_results(q)

def user_query(kind, user_id, sort, time):
    """General profile-page query."""
    q = kind._query(kind.c.author_id == user_id,
                    kind.c._spam == (True, False),
                    sort = db_sort(sort))
    if time != 'all':
        q._filter(db_times[time])
    return make_results(q)

def get_all_comments():
    """the master /comments page"""
    q = Comment._query(sort = desc('_date'))
    return make_results(q)

def get_sr_comments(sr):
    return _get_sr_comments(sr._id)

def _get_sr_comments(sr_id):
    """the subreddit /r/foo/comments page"""
    q = Comment._query(Comment.c.sr_id == sr_id,
                       sort = desc('_date'))
    return make_results(q)

def _get_comments(user_id, sort, time):
    return user_query(Comment, user_id, sort, time)

def get_comments(user, sort, time):
    return _get_comments(user._id, sort, time)

def _get_submitted(user_id, sort, time):
    return user_query(Link, user_id, sort, time)

def get_submitted(user, sort, time):
    return _get_submitted(user._id, sort, time)


def get_user_actions(user, sort, time):
    results = []
    unique_ids = set()

    # Order is important as a listing will only have the action_type of the
    # first occurrance (aka: posts trump comments which trump likes)
    actions_by_type = ((get_submitted(user, sort, time), 'submit'),
                       (get_comments(user, sort, time), 'comment'),
                       (get_liked(user), 'like'))

    for cached_result, action_type in actions_by_type:
        cached_result.fetch()
        for thing in cached_result.data:
            if thing[0] not in unique_ids:
                results.append(thing + (action_type,))
                unique_ids.add(thing[0])

    return sorted(results, key=lambda x: x[1], reverse=True)


def get_overview(user, sort, time):
    return merge_results(get_comments(user, sort, time),
                         get_submitted(user, sort, time))

def rel_query(rel, thing_id, name, filters = []):
    """General relationship query."""

    q = rel._query(rel.c._thing1_id == thing_id,
                   rel.c._t2_deleted == False,
                   rel.c._name == name,
                   sort = desc('_date'),
                   eager_load = True,
                   )
    if filters:
        q._filter(*filters)

    return q

cached_userrel_query = cached_query(UserQueryCache, filter_thing2)
cached_srrel_query = cached_query(SubredditQueryCache, filter_thing2)

@cached_query(UserQueryCache, filter_thing)
def get_liked(user):
    return FakeQuery(sort=[desc("date")])

@cached_query(UserQueryCache, filter_thing)
def get_disliked(user):
    return FakeQuery(sort=[desc("date")])

@cached_query(UserQueryCache)
def get_hidden_links(user_id):
    return FakeQuery(sort=[desc("action_date")])

def get_hidden(user):
    return get_hidden_links(user)

@cached_query(UserQueryCache)
def get_categorized_saved_links(user_id, sr_id, category):
    return FakeQuery(sort=[desc("action_date")])

@cached_query(UserQueryCache)
def get_categorized_saved_comments(user_id, sr_id, category):
    return FakeQuery(sort=[desc("action_date")])

@cached_query(UserQueryCache)
def get_saved_links(user_id, sr_id):
    return FakeQuery(sort=[desc("action_date")])

@cached_query(UserQueryCache)
def get_saved_comments(user_id, sr_id):
    return FakeQuery(sort=[desc("action_date")])

def get_saved(user, sr_id=None, category=None):
    sr_id = sr_id or 'none'
    if not category:
        queries = [get_saved_links(user, sr_id),
                   get_saved_comments(user, sr_id)]
    else:
        queries = [get_categorized_saved_links(user, sr_id, category),
                   get_categorized_saved_comments(user, sr_id, category)]
    return MergedCachedQuery(queries)

@cached_srrel_query
def get_subreddit_messages(sr):
    return rel_query(ModeratorInbox, sr, 'inbox')

@cached_srrel_query
def get_unread_subreddit_messages(sr):
    return rel_query(ModeratorInbox, sr, 'inbox',
                          filters = [ModeratorInbox.c.new == True])

def get_unread_subreddit_messages_multi(srs):
    if not srs:
        return []
    queries = [get_unread_subreddit_messages(sr) for sr in srs]
    return MergedCachedQuery(queries)

inbox_message_rel = Inbox.rel(Account, Message)
@cached_userrel_query
def get_inbox_messages(user):
    return rel_query(inbox_message_rel, user, 'inbox')

@cached_userrel_query
def get_unread_messages(user):
    return rel_query(inbox_message_rel, user, 'inbox',
                          filters = [inbox_message_rel.c.new == True])

inbox_comment_rel = Inbox.rel(Account, Comment)
@cached_userrel_query
def get_inbox_comments(user):
    return rel_query(inbox_comment_rel, user, 'inbox')

@cached_userrel_query
def get_unread_comments(user):
    return rel_query(inbox_comment_rel, user, 'inbox',
                          filters = [inbox_comment_rel.c.new == True])

@cached_userrel_query
def get_inbox_selfreply(user):
    return rel_query(inbox_comment_rel, user, 'selfreply')

@cached_userrel_query
def get_unread_selfreply(user):
    return rel_query(inbox_comment_rel, user, 'selfreply',
                          filters = [inbox_comment_rel.c.new == True])


@cached_userrel_query
def get_inbox_comment_mentions(user):
    return rel_query(inbox_comment_rel, user, "mention")


@cached_userrel_query
def get_unread_comment_mentions(user):
    return rel_query(inbox_comment_rel, user, "mention",
                     filters=[inbox_comment_rel.c.new == True])


def get_inbox(user):
    return merge_results(get_inbox_comments(user),
                         get_inbox_messages(user),
                         get_inbox_comment_mentions(user),
                         get_inbox_selfreply(user))

@cached_query(UserQueryCache)
def get_sent(user_id):
    return Message._query(Message.c.author_id == user_id,
                          Message.c._spam == (True, False),
                          sort = desc('_date'))

def get_unread_inbox(user):
    return merge_results(get_unread_comments(user),
                         get_unread_messages(user),
                         get_unread_comment_mentions(user),
                         get_unread_selfreply(user))

def _user_reported_query(user_id, thing_cls):
    rel_cls = Report.rel(Account, thing_cls)
    return rel_query(rel_cls, user_id, ('-1', '0', '1'))
    # -1: rejected report
    # 0: unactioned report
    # 1: accepted report

@cached_userrel_query
def get_user_reported_links(user_id):
    return _user_reported_query(user_id, Link)

@cached_userrel_query
def get_user_reported_comments(user_id):
    return _user_reported_query(user_id, Comment)

@cached_userrel_query
def get_user_reported_messages(user_id):
    return _user_reported_query(user_id, Message)

@merged_cached_query
def get_user_reported(user_id):
    return [get_user_reported_links(user_id),
            get_user_reported_comments(user_id),
            get_user_reported_messages(user_id)]


def set_promote_status(link, promote_status):
    all_queries = [promote_query(link.author_id) for promote_query in 
                   (get_unpaid_links, get_unapproved_links, 
                    get_rejected_links, get_live_links, get_accepted_links,
                    get_edited_live_links)]
    all_queries.extend([get_all_unpaid_links(), get_all_unapproved_links(),
                        get_all_rejected_links(), get_all_live_links(),
                        get_all_accepted_links(), get_all_edited_live_links()])

    if promote_status == PROMOTE_STATUS.unpaid:
        inserts = [get_unpaid_links(link.author_id), get_all_unpaid_links()]
    elif promote_status == PROMOTE_STATUS.unseen:
        inserts = [get_unapproved_links(link.author_id),
                   get_all_unapproved_links()]
    elif promote_status == PROMOTE_STATUS.rejected:
        inserts = [get_rejected_links(link.author_id), get_all_rejected_links()]
    elif promote_status == PROMOTE_STATUS.promoted:
        inserts = [get_live_links(link.author_id), get_all_live_links()]
    elif promote_status == PROMOTE_STATUS.edited_live:
        inserts = [
            get_edited_live_links(link.author_id),
            get_all_edited_live_links()
        ]
    elif promote_status in (PROMOTE_STATUS.accepted, PROMOTE_STATUS.pending,
                            PROMOTE_STATUS.finished):
        inserts = [get_accepted_links(link.author_id), get_all_accepted_links()]

    deletes = list(set(all_queries) - set(inserts))
    with CachedQueryMutator() as m:
        for q in inserts:
            m.insert(q, [link])
        for q in deletes:
            m.delete(q, [link])

    link.promote_status = promote_status
    link._commit()

    text = "set promote status to '%s'" % PROMOTE_STATUS.name[promote_status]
    PromotionLog.add(link, text)


def _promoted_link_query(user_id, status):
    STATUS_CODES = {'unpaid': PROMOTE_STATUS.unpaid,
                    'unapproved': PROMOTE_STATUS.unseen,
                    'rejected': PROMOTE_STATUS.rejected,
                    'live': PROMOTE_STATUS.promoted,
                    'accepted': (PROMOTE_STATUS.accepted,
                                 PROMOTE_STATUS.pending,
                                 PROMOTE_STATUS.finished),
                    'edited_live': PROMOTE_STATUS.edited_live}

    q = Link._query(Link.c.sr_id == Subreddit.get_promote_srid(),
                    Link.c._spam == (True, False),
                    Link.c._deleted == (True, False),
                    Link.c.promote_status == STATUS_CODES[status],
                    sort=db_sort('new'))
    if user_id:
        q._filter(Link.c.author_id == user_id)
    return q


@cached_query(UserQueryCache)
def get_unpaid_links(user_id):
    return _promoted_link_query(user_id, 'unpaid')


@cached_query(UserQueryCache)
def get_all_unpaid_links():
    return _promoted_link_query(None, 'unpaid')


@cached_query(UserQueryCache)
def get_unapproved_links(user_id):
    return _promoted_link_query(user_id, 'unapproved')


@cached_query(UserQueryCache)
def get_all_unapproved_links():
    return _promoted_link_query(None, 'unapproved')


@cached_query(UserQueryCache)
def get_rejected_links(user_id):
    return _promoted_link_query(user_id, 'rejected')


@cached_query(UserQueryCache)
def get_all_rejected_links():
    return _promoted_link_query(None, 'rejected')


@cached_query(UserQueryCache)
def get_live_links(user_id):
    return _promoted_link_query(user_id, 'live')


@cached_query(UserQueryCache)
def get_all_live_links():
    return _promoted_link_query(None, 'live')


@cached_query(UserQueryCache)
def get_accepted_links(user_id):
    return _promoted_link_query(user_id, 'accepted')


@cached_query(UserQueryCache)
def get_all_accepted_links():
    return _promoted_link_query(None, 'accepted')


@cached_query(UserQueryCache)
def get_edited_live_links(user_id):
    return _promoted_link_query(user_id, 'edited_live')


@cached_query(UserQueryCache)
def get_all_edited_live_links():
    return _promoted_link_query(None, 'edited_live')


@cached_query(UserQueryCache)
def get_payment_flagged_links():
    return FakeQuery(sort=[desc("_date")])


def set_payment_flagged_link(link):
    with CachedQueryMutator() as m:
        q = get_payment_flagged_links()
        m.insert(q, [link])


def unset_payment_flagged_link(link):
    with CachedQueryMutator() as m:
        q = get_payment_flagged_links()
        m.delete(q, [link])


@cached_query(UserQueryCache)
def get_underdelivered_campaigns():
    return FakeQuery(sort=[desc("_date")])


def set_underdelivered_campaigns(campaigns):
    campaigns = tup(campaigns)
    with CachedQueryMutator() as m:
        q = get_underdelivered_campaigns()
        m.insert(q, campaigns)


def unset_underdelivered_campaigns(campaigns):
    campaigns = tup(campaigns)
    with CachedQueryMutator() as m:
        q = get_underdelivered_campaigns()
        m.delete(q, campaigns)


@merged_cached_query
def get_promoted_links(user_id):
    queries = [get_unpaid_links(user_id), get_unapproved_links(user_id),
               get_rejected_links(user_id), get_live_links(user_id),
               get_accepted_links(user_id), get_edited_live_links(user_id)]
    return queries


@merged_cached_query
def get_all_promoted_links():
    queries = [get_all_unpaid_links(), get_all_unapproved_links(),
               get_all_rejected_links(), get_all_live_links(),
               get_all_accepted_links(), get_all_edited_live_links()]
    return queries


@cached_query(SubredditQueryCache, filter_fn=filter_thing)
def get_all_gilded_comments():
    return FakeQuery(sort=[desc("date")])


@cached_query(SubredditQueryCache, filter_fn=filter_thing)
def get_all_gilded_links():
    return FakeQuery(sort=[desc("date")])


@merged_cached_query
def get_all_gilded():
    return [get_all_gilded_comments(), get_all_gilded_links()]


@cached_query(SubredditQueryCache, filter_fn=filter_thing)
def get_gilded_comments(sr_id):
    return FakeQuery(sort=[desc("date")])


@cached_query(SubredditQueryCache, filter_fn=filter_thing)
def get_gilded_links(sr_id):
    return FakeQuery(sort=[desc("date")])


@merged_cached_query
def get_gilded(sr_ids):
    queries = [get_gilded_links, get_gilded_comments]
    return [query(sr_id)
            for sr_id, query in itertools.product(tup(sr_ids), queries)]


@cached_query(UserQueryCache, filter_fn=filter_thing)
def get_gilded_user_comments(user_id):
    return FakeQuery(sort=[desc("date")])


@cached_query(UserQueryCache, filter_fn=filter_thing)
def get_gilded_user_links(user_id):
    return FakeQuery(sort=[desc("date")])


@merged_cached_query
def get_gilded_users(user_ids):
    queries = [get_gilded_user_links, get_gilded_user_comments]
    return [query(user_id)
            for user_id, query in itertools.product(tup(user_ids), queries)]


@cached_query(UserQueryCache, filter_fn=filter_thing)
def get_user_gildings(user_id):
    return FakeQuery(sort=[desc("date")])


@merged_cached_query
def get_gilded_user(user):
    return [get_gilded_user_comments(user), get_gilded_user_links(user)]


def add_queries(queries, insert_items=None, delete_items=None):
    """Adds multiple queries to the query queue. If insert_items or
       delete_items is specified, the query may not need to be
       recomputed against the database."""

    for q in queries:
        if insert_items and q.can_insert():
            g.log.debug("Inserting %s into query %s" % (insert_items, q))
            with g.stats.get_timer('permacache.foreground.insert'):
                q.insert(insert_items)
        elif delete_items and q.can_delete():
            g.log.debug("Deleting %s from query %s" % (delete_items, q))
            with g.stats.get_timer('permacache.foreground.delete'):
                q.delete(delete_items)
        else:
            raise Exception("Cannot update query %r!" % (q,))

    # dual-write any queries that are being migrated to the new query cache
    with CachedQueryMutator() as m:
        new_queries = [getattr(q, 'new_query') for q in queries if hasattr(q, 'new_query')]

        if insert_items:
            for query in new_queries:
                m.insert(query, tup(insert_items))

        if delete_items:
            for query in new_queries:
                m.delete(query, tup(delete_items))

#can be rewritten to be more efficient
def all_queries(fn, obj, *param_lists):
    """Given a fn and a first argument 'obj', calls the fn(obj, *params)
    for every permutation of the parameters in param_lists"""
    results = []
    params = [[obj]]
    for pl in param_lists:
        new_params = []
        for p in pl:
            for c in params:
                new_param = list(c)
                new_param.append(p)
                new_params.append(new_param)
        params = new_params

    results = [fn(*p) for p in params]
    return results

## The following functions should be called after their respective
## actions to update the correct listings.
def new_link(link):
    "Called on the submission and deletion of links"
    sr = Subreddit._byID(link.sr_id)
    author = Account._byID(link.author_id)

    # just update "new" here, new_vote will handle hot/top/controversial
    results = [get_links(sr, 'new', 'all')]
    results.append(get_submitted(author, 'new', 'all'))

    for domain in utils.UrlParser(link.url).domain_permutations():
        results.append(get_domain_links(domain, 'new', "all"))

    with CachedQueryMutator() as m:
        if link._spam:
            m.insert(get_spam_links(sr), [link])
        if not (sr.exclude_banned_modqueue and author._spam):
            m.insert(get_unmoderated_links(sr), [link])

    add_queries(results, insert_items = link)
    amqp.add_item('new_link', link._fullname)


def add_to_commentstree_q(comment):
    if utils.to36(comment.link_id) in g.live_config["fastlane_links"]:
        amqp.add_item('commentstree_fastlane_q', comment._fullname)
    elif g.shard_commentstree_queues:
        amqp.add_item('commentstree_%d_q' % (comment.link_id % 10),
                      comment._fullname)
    else:
        amqp.add_item('commentstree_q', comment._fullname)


def update_comment_notifications(comment, inbox_rels):
    is_visible = not comment._deleted and not comment._spam

    with CachedQueryMutator() as mutator:
        for inbox_rel in tup(inbox_rels):
            inbox_owner = inbox_rel._thing1
            unread = (is_visible and
                getattr(inbox_rel, 'unread_preremoval', True))

            if inbox_rel._name == "inbox":
                query = get_inbox_comments(inbox_owner)
            elif inbox_rel._name == "selfreply":
                query = get_inbox_selfreply(inbox_owner)
            else:
                raise ValueError("wtf is " + inbox_rel._name)

            # mentions happen in butler_q

            if is_visible:
                mutator.insert(query, [inbox_rel])
            else:
                mutator.delete(query, [inbox_rel])

            set_unread(comment, inbox_owner, unread=unread, mutator=mutator)


def new_comment(comment, inbox_rels):
    author = Account._byID(comment.author_id)

    # just update "new" here, new_vote will handle hot/top/controversial
    job = [get_comments(author, 'new', 'all')]

    sr = Subreddit._byID(comment.sr_id)

    if comment._deleted:
        job_key = "delete_items"
        job.append(get_sr_comments(sr))
        job.append(get_all_comments())
    else:
        job_key = "insert_items"
        if comment._spam:
            with CachedQueryMutator() as m:
                m.insert(get_spam_comments(sr), [comment])
                if (was_spam_filtered(comment) and
                        not (sr.exclude_banned_modqueue and author._spam)):
                    m.insert(get_spam_filtered_comments(sr), [comment])

        amqp.add_item('new_comment', comment._fullname)
        add_to_commentstree_q(comment)

    job_dict = { job_key: comment }
    add_queries(job, **job_dict)

    # note that get_all_comments() is updated by the amqp process
    # r2.lib.db.queries.run_new_comments (to minimise lock contention)

    if inbox_rels:
        update_comment_notifications(comment, inbox_rels)


def new_subreddit(sr):
    "no precomputed queries here yet"
    amqp.add_item('new_subreddit', sr._fullname)


def new_message(message, inbox_rels, add_to_sent=True, update_modmail=True):
    from r2.lib.comment_tree import add_message

    from_user = Account._byID(message.author_id)

    # check if the from_user is exempt from ever adding to sent
    if not from_user.update_sent_messages:
        add_to_sent = False

    if message.display_author:
        add_to_sent = False

    modmail_rel_included = False
    update_recipient = False
    add_to_user = None

    with CachedQueryMutator() as m:
        if add_to_sent:
            m.insert(get_sent(from_user), [message])

        for inbox_rel in tup(inbox_rels):
            to = inbox_rel._thing1

            if isinstance(inbox_rel, ModeratorInbox):
                m.insert(get_subreddit_messages(to), [inbox_rel])
                modmail_rel_included = True
                set_sr_unread(message, to, unread=True, mutator=m)
            else:
                m.insert(get_inbox_messages(to), [inbox_rel])
                update_recipient = True
                # make sure we add this message to the user's inbox
                add_to_user = to
                set_unread(message, to, unread=True, mutator=m)

    update_modmail = update_modmail and modmail_rel_included

    amqp.add_item('new_message', message._fullname)
    add_message(message, update_recipient=update_recipient,
                update_modmail=update_modmail, add_to_user=add_to_user)
    
    # light up the modmail icon for all other mods with mail access
    if update_modmail:
        mod_perms = message.subreddit_slow.moderators_with_perms()
        mod_ids = [mod_id for mod_id, perms in mod_perms.iteritems()
            if mod_id != from_user._id and perms.get('mail', False)]
        moderators = Account._byID(mod_ids, data=True, return_dict=False)
        for mod in moderators:
            if not mod.modmsgtime:
                mod.modmsgtime = message._date
                mod._commit()


def set_unread(messages, user, unread, mutator=None):
    messages = tup(messages)

    inbox_rels = Inbox.get_rels(user, messages)
    Inbox.set_unread(inbox_rels, unread)

    update_unread_queries(inbox_rels, insert=unread, mutator=mutator)


def update_unread_queries(inbox_rels, insert=True, mutator=None):
    """Update all the cached queries related to the inbox relations"""
    if not mutator:
        m = CachedQueryMutator()
    else:
        m = mutator

    inbox_rels = tup(inbox_rels)
    for inbox_rel in inbox_rels:
        thing = inbox_rel._thing2
        user = inbox_rel._thing1

        if isinstance(thing, Comment):
            if inbox_rel._name == "inbox":
                query = get_unread_comments(user._id)
            elif inbox_rel._name == "selfreply":
                query = get_unread_selfreply(user._id)
            elif inbox_rel._name == "mention":
                query = get_unread_comment_mentions(user._id)
        elif isinstance(thing, Message):
            query = get_unread_messages(user._id)
        else:
            raise ValueError("can't handle %s" % thing.__class__.__name__)

        if insert:
            m.insert(query, [inbox_rel])
        else:
            m.delete(query, [inbox_rel])

    if not mutator:
        m.send()


def set_sr_unread(messages, sr, unread, mutator=None):
    messages = tup(messages)

    inbox_rels = ModeratorInbox.get_rels(sr, messages)
    ModeratorInbox.set_unread(inbox_rels, unread)

    update_unread_sr_queries(inbox_rels, insert=unread, mutator=mutator)


def update_unread_sr_queries(inbox_rels, insert=True, mutator=None):
    if not mutator:
        m = CachedQueryMutator()
    else:
        m = mutator

    inbox_rels = tup(inbox_rels)
    for inbox_rel in inbox_rels:
        sr = inbox_rel._thing1
        query = get_unread_subreddit_messages(sr)

        if insert:
            m.insert(query, [inbox_rel])
        else:
            m.delete(query, [inbox_rel])

    if not mutator:
        m.send()


def unread_handler(things, user, unread):
    """Given a user and Things of varying types, set their unread state."""
    sr_messages = collections.defaultdict(list)
    comments = []
    messages = []
    # Group things by subreddit or type
    for thing in things:
        if isinstance(thing, Message):
            if getattr(thing, 'sr_id', False):
                sr_messages[thing.sr_id].append(thing)
            else:
                messages.append(thing)
        else:
            comments.append(thing)

    if sr_messages:
        mod_srs = Subreddit.reverse_moderator_ids(user)
        srs = Subreddit._byID(sr_messages.keys())
    else:
        mod_srs = []

    with CachedQueryMutator() as m:
        for sr_id, things in sr_messages.items():
            # Remove the item(s) from the user's inbox
            set_unread(things, user, unread, mutator=m)

            if sr_id in mod_srs:
                # Only moderators can change the read status of that
                # message in the modmail inbox
                sr = srs[sr_id]
                set_sr_unread(things, sr, unread, mutator=m)

        if comments:
            set_unread(comments, user, unread, mutator=m)

        if messages:
            set_unread(messages, user, unread, mutator=m)


def unnotify(thing, possible_recipients=None):
    """Given a Thing, remove any notifications to its possible recipients.

    `possible_recipients` is a list of account IDs to unnotify. If not passed,
    deduce all possible recipients and remove their notifications.
    """
    from r2.lib import butler
    error_message = ("Unable to unnotify thing of type: %r" % thing)
    notification_handler(thing,
        notify_function=butler.remove_mention_notification,
        error_message=error_message,
        possible_recipients=possible_recipients,
    )


def renotify(thing, possible_recipients=None):
    """Given a Thing, reactivate notifications for possible recipients.

    `possible_recipients` is a list of account IDs to renotify. If not passed,
    deduce all possible recipients and add their notifications.
    This is used when unspamming comments.
    """
    from r2.lib import butler
    error_message = ("Unable to renotify thing of type: %r" % thing)
    notification_handler(thing,
        notify_function=butler.readd_mention_notification,
        error_message=error_message,
        possible_recipients=possible_recipients,
    )


def notification_handler(thing, notify_function,
        error_message, possible_recipients=None):
    if not possible_recipients:
        possible_recipients = Inbox.possible_recipients(thing)

    if not possible_recipients:
        return

    accounts = Account._byID(
        possible_recipients,
        return_dict=False,
        ignore_missing=True,
    )

    if isinstance(thing, Comment):
        rels = Inbox._fast_query(
            accounts,
            thing,
            ("inbox", "selfreply", "mention"),
        )

        # if the comment has been spammed, remember the previous
        # new value in case it becomes unspammed
        if thing._spam:
            for (tupl, rel) in rels.iteritems():
                if rel:
                    rel.unread_preremoval = rel.new
                    rel._commit()

        replies, mentions = utils.partition(
            lambda r: r._name == "mention",
            filter(None, rels.values()),
        )

        for mention in mentions:
            notify_function(mention)

        replies = list(replies)
        if replies:
            update_comment_notifications(thing, replies)
    else:
        raise ValueError(error_message)


def _by_srid(things, srs=True):
    """Takes a list of things and returns them in a dict separated by
       sr_id, in addition to the looked-up subreddits"""
    ret = {}

    for thing in tup(things):
        if getattr(thing, 'sr_id', None) is not None:
            ret.setdefault(thing.sr_id, []).append(thing)

    if srs:
        _srs = Subreddit._byID(ret.keys(), return_dict=True) if ret else {}
        return ret, _srs
    else:
        return ret


def _by_author(things, authors=True):
    ret = collections.defaultdict(list)

    for thing in tup(things):
        author_id = getattr(thing, 'author_id')
        if author_id:
            ret[author_id].append(thing)

    if authors:
        _authors = Account._byID(ret.keys(), return_dict=True) if ret else {}
        return ret, _authors
    else:
        return ret

def _by_thing1_id(rels):
    ret = {}
    for rel in tup(rels):
        ret.setdefault(rel._thing1_id, []).append(rel)
    return ret


def was_spam_filtered(thing):
    if (thing._spam and not thing._deleted and
        getattr(thing, 'verdict', None) != 'mod-removed'):
        return True
    else:
        return False


def delete(things):
    query_cache_inserts, query_cache_deletes = _common_del_ban(things)
    by_srid, srs = _by_srid(things)
    by_author, authors = _by_author(things)

    for sr_id, sr_things in by_srid.iteritems():
        sr = srs[sr_id]
        links = [x for x in sr_things if isinstance(x, Link)]
        comments = [x for x in sr_things if isinstance(x, Comment)]

        if links:
            query_cache_deletes.append((get_spam_links(sr), links))
            query_cache_deletes.append((get_spam_filtered_links(sr), links))
            query_cache_deletes.append((get_unmoderated_links(sr_id),
                                            links))
            query_cache_deletes.append((get_edited_links(sr_id), links))
        if comments:
            query_cache_deletes.append((get_spam_comments(sr), comments))
            query_cache_deletes.append((get_spam_filtered_comments(sr),
                                        comments))
            query_cache_deletes.append((get_edited_comments(sr), comments))

    for author_id, a_things in by_author.iteritems():
        author = authors[author_id]
        links = [x for x in a_things if isinstance(x, Link)]
        comments = [x for x in a_things if isinstance(x, Comment)]

        if links:
            results = [get_submitted(author, 'hot', 'all'),
                       get_submitted(author, 'new', 'all')]
            for sort in time_filtered_sorts:
                for time in db_times.keys():
                    results.append(get_submitted(author, sort, time))
            add_queries(results, delete_items=links)
            query_cache_inserts.append((get_deleted_links(author_id), links))
        if comments:
            results = [get_comments(author, 'hot', 'all'),
                       get_comments(author, 'new', 'all')]
            for sort in time_filtered_sorts:
                for time in db_times.keys():
                    results.append(get_comments(author, sort, time))
            add_queries(results, delete_items=comments)
            query_cache_inserts.append((get_deleted_comments(author_id),
                                        comments))

    with CachedQueryMutator() as m:
        for q, inserts in query_cache_inserts:
            m.insert(q, inserts)
        for q, deletes in query_cache_deletes:
            m.delete(q, deletes)

    for thing in tup(things):
        thing.update_search_index()


def edit(thing):
    if isinstance(thing, Link):
        query = get_edited_links
    elif isinstance(thing, Comment):
        query = get_edited_comments

    with CachedQueryMutator() as m:
        m.delete(query(thing.sr_id), [thing])
        m.insert(query(thing.sr_id), [thing])


def ban(things, filtered=True):
    query_cache_inserts, query_cache_deletes = _common_del_ban(things)
    by_srid = _by_srid(things, srs=False)

    for sr_id, sr_things in by_srid.iteritems():
        links = []
        modqueue_links = []
        comments = []
        modqueue_comments = []
        for item in sr_things:
            # don't add posts by banned users if subreddit prefs exclude them
            add_to_modqueue = (filtered and
                       not (item.subreddit_slow.exclude_banned_modqueue and
                            item.author_slow._spam))

            if isinstance(item, Link):
                links.append(item)
                if add_to_modqueue:
                    modqueue_links.append(item)
            elif isinstance(item, Comment):
                comments.append(item)
                if add_to_modqueue:
                    modqueue_comments.append(item)

        if links:
            query_cache_inserts.append((get_spam_links(sr_id), links))
            if not filtered:
                query_cache_deletes.append(
                        (get_spam_filtered_links(sr_id), links))
                query_cache_deletes.append(
                        (get_unmoderated_links(sr_id), links))

        if modqueue_links:
            query_cache_inserts.append(
                    (get_spam_filtered_links(sr_id), modqueue_links))

        if comments:
            query_cache_inserts.append((get_spam_comments(sr_id), comments))
            if not filtered:
                query_cache_deletes.append(
                        (get_spam_filtered_comments(sr_id), comments))

        if modqueue_comments:
            query_cache_inserts.append(
                    (get_spam_filtered_comments(sr_id), modqueue_comments))

    with CachedQueryMutator() as m:
        for q, inserts in query_cache_inserts:
            m.insert(q, inserts)
        for q, deletes in query_cache_deletes:
            m.delete(q, deletes)

    for thing in tup(things):
        thing.update_search_index()


def _common_del_ban(things):
    query_cache_inserts = []
    query_cache_deletes = []
    by_srid, srs = _by_srid(things)

    for sr_id, sr_things in by_srid.iteritems():
        sr = srs[sr_id]
        links = [x for x in sr_things if isinstance(x, Link)]
        comments = [x for x in sr_things if isinstance(x, Comment)]

        if links:
            results = [get_links(sr, 'hot', 'all'), get_links(sr, 'new', 'all')]
            for sort in time_filtered_sorts:
                for time in db_times.keys():
                    results.append(get_links(sr, sort, time))
            add_queries(results, delete_items=links)
            query_cache_deletes.append([get_reported_links(sr), links])
            query_cache_deletes.append([get_reported_links(None), links])
        if comments:
            query_cache_deletes.append([get_reported_comments(sr), comments])
            query_cache_deletes.append([get_reported_comments(None), comments])

    return query_cache_inserts, query_cache_deletes


def unban(things, insert=True):
    query_cache_deletes = []

    by_srid, srs = _by_srid(things)
    if not by_srid:
        return

    for sr_id, things in by_srid.iteritems():
        sr = srs[sr_id]
        links = [x for x in things if isinstance(x, Link)]
        comments = [x for x in things if isinstance(x, Comment)]

        if insert and links:
            # put it back in the listings
            results = [get_links(sr, 'hot', 'all'),
                       get_links(sr, 'top', 'all'),
                       get_links(sr, 'controversial', 'all'),
                       ]
            # the time-filtered listings will have to wait for the
            # next mr_top run
            add_queries(results, insert_items=links)

            # Check if link is being unbanned and should be put in
            # 'new' with current time
            new_links = []
            for l in links:
                ban_info = l.ban_info
                if ban_info.get('reset_used', True) == False and \
                    ban_info.get('auto', False):
                    l_copy = deepcopy(l)
                    l_copy._date = ban_info['unbanned_at']
                    new_links.append(l_copy)
                else:
                    new_links.append(l)
            add_queries([get_links(sr, 'new', 'all')], insert_items=new_links)
            query_cache_deletes.append([get_spam_links(sr), links])

        if insert and comments:
            add_queries([get_all_comments(), get_sr_comments(sr)],
                        insert_items=comments)
            query_cache_deletes.append([get_spam_comments(sr), comments])

        if links:
            query_cache_deletes.append((get_unmoderated_links(sr), links))
            query_cache_deletes.append([get_spam_filtered_links(sr), links])

        if comments:
            query_cache_deletes.append([get_spam_filtered_comments(sr), comments])

    with CachedQueryMutator() as m:
        for q, deletes in query_cache_deletes:
            m.delete(q, deletes)

    for thing in tup(things):
        thing.update_search_index()

def new_report(thing, report_rel):
    reporter_id = report_rel._thing1_id

    # determine if the report is for spam so we can update the global
    # report queue as well as the per-subreddit one
    reason = getattr(report_rel, "reason", None)
    is_spam_report = reason and "spam" in reason.lower()

    with CachedQueryMutator() as m:
        if isinstance(thing, Link):
            m.insert(get_reported_links(thing.sr_id), [thing])
            if is_spam_report:
                m.insert(get_reported_links(None), [thing])
            m.insert(get_user_reported_links(reporter_id), [report_rel])
        elif isinstance(thing, Comment):
            m.insert(get_reported_comments(thing.sr_id), [thing])
            if is_spam_report:
                m.insert(get_reported_comments(None), [thing])
            m.insert(get_user_reported_comments(reporter_id), [report_rel])
        elif isinstance(thing, Message):
            m.insert(get_user_reported_messages(reporter_id), [report_rel])

    amqp.add_item("new_report", thing._fullname)


def clear_reports(things, rels):
    query_cache_deletes = []

    by_srid = _by_srid(things, srs=False)

    for sr_id, sr_things in by_srid.iteritems():
        links = [ x for x in sr_things if isinstance(x, Link) ]
        comments = [ x for x in sr_things if isinstance(x, Comment) ]

        if links:
            query_cache_deletes.append([get_reported_links(sr_id), links])
            query_cache_deletes.append([get_reported_links(None), links])
        if comments:
            query_cache_deletes.append([get_reported_comments(sr_id), comments])
            query_cache_deletes.append([get_reported_comments(None), comments])

    # delete from user_reported if the report was correct
    rels = [r for r in rels if r._name == '1']
    if rels:
        link_rels = [r for r in rels if r._type2 == Link]
        comment_rels = [r for r in rels if r._type2 == Comment]
        message_rels = [r for r in rels if r._type2 == Message]

        rels_to_query = ((link_rels, get_user_reported_links),
                         (comment_rels, get_user_reported_comments),
                         (message_rels, get_user_reported_messages))

        for thing_rels, query in rels_to_query:
            if not thing_rels:
                continue

            by_thing1_id = _by_thing1_id(thing_rels)
            for reporter_id, reporter_rels in by_thing1_id.iteritems():
                query_cache_deletes.append([query(reporter_id), reporter_rels])

    with CachedQueryMutator() as m:
        for q, deletes in query_cache_deletes:
            m.delete(q, deletes)


def add_all_srs():
    """Recalculates every listing query for every subreddit. Very,
       very slow."""
    q = Subreddit._query(sort = asc('_date'))
    for sr in fetch_things2(q):
        for q in all_queries(get_links, sr, ('hot', 'new'), ['all']):
            q.update()
        for q in all_queries(get_links, sr, time_filtered_sorts, db_times.keys()):
            q.update()
        get_spam_links(sr).update()
        get_spam_comments(sr).update()
        get_reported_links(sr).update()
        get_reported_comments(sr).update()

def update_user(user):
    if isinstance(user, str):
        user = Account._by_name(user)
    elif isinstance(user, int):
        user = Account._byID(user)

    results = [get_inbox_messages(user),
               get_inbox_comments(user),
               get_inbox_selfreply(user),
               get_sent(user),
               get_liked(user),
               get_disliked(user),
               get_submitted(user, 'new', 'all'),
               get_comments(user, 'new', 'all')]
    for q in results:
        q.update()

def add_all_users():
    q = Account._query(sort = asc('_date'))
    for user in fetch_things2(q):
        update_user(user)

# amqp queue processing functions

def run_new_comments(limit=1000):
    """Add new incoming comments to the /comments page"""
    # this is done as a queue because otherwise the contention for the
    # lock on the query would be very high

    @g.stats.amqp_processor('newcomments_q')
    def _run_new_comments(msgs, chan):
        fnames = [msg.body for msg in msgs]

        comments = Comment._by_fullname(fnames, data=True, return_dict=False)
        add_queries([get_all_comments()],
                    insert_items=comments)

        bysrid = _by_srid(comments, False)
        for srid, sr_comments in bysrid.iteritems():
            add_queries([_get_sr_comments(srid)],
                        insert_items=sr_comments)

    amqp.handle_items('newcomments_q', _run_new_comments, limit=limit)

def run_commentstree(qname="commentstree_q", limit=400):
    """Add new incoming comments to their respective comments trees"""

    @g.stats.amqp_processor(qname)
    def _run_commentstree(msgs, chan):
        comments = Comment._by_fullname([msg.body for msg in msgs],
                                        data = True, return_dict = False)
        print 'Processing %r' % (comments,)

        if comments:
            add_comments(comments)

    # High velocity threads put additional pressure on Cassandra.
    if qname == "commentstree_fastlane_q":
        limit = max(1000, limit)
    amqp.handle_items(qname, _run_commentstree, limit=limit)


def _by_type(items):
    by_type = collections.defaultdict(list)
    for item in items:
        by_type[item.__class__].append(item)
    return by_type


def get_stored_votes(user, things):
    if not user or not things:
        return {}

    results = {}
    things_by_type = _by_type(things)

    for thing_class, items in things_by_type.iteritems():
        if not thing_class.is_votable:
            continue

        rel_class = VotesByAccount.rel(thing_class)
        votes = rel_class.fast_query(user, items)
        for cross, direction in votes.iteritems():
            results[cross] = Vote.deserialize_direction(int(direction))

    return results


def get_likes(user, requested_items):
    if not user or not requested_items:
        return {}

    res = {}

    try:
        last_modified = LastModified._byID(user._fullname)
    except tdb_cassandra.NotFound:
        last_modified = None

    items_in_grace_period = {}
    items_by_type = _by_type(requested_items)
    for type_, items in items_by_type.iteritems():
        if not type_.is_votable:
            # these items can't be voted on. just mark 'em as None and skip.
            for item in items:
                res[(user, item)] = None
            continue

        rel_cls = VotesByAccount.rel(type_)
        last_vote = getattr(last_modified, rel_cls._last_modified_name, None)
        if last_vote:
            time_since_last_vote = datetime.now(pytz.UTC) - last_vote

        # only do prequeued_vote lookups if we've voted within the grace period
        # and therefore might have votes in flight in the queues.
        if last_vote and time_since_last_vote < g.vote_queue_grace_period:
            too_new = 0

            for item in items:
                if item._age > time_since_last_vote:
                    key = prequeued_vote_key(user, item)
                    items_in_grace_period[key] = (user, item)
                else:
                    # the item is newer than our last vote, we can't have
                    # possibly voted on it.
                    res[(user, item)] = None
                    too_new += 1

            if too_new:
                g.stats.simple_event("vote.prequeued.too-new", delta=too_new)
        else:
            g.stats.simple_event("vote.prequeued.graceless", delta=len(items))

    # look up votes in memcache for items that could have been voted on
    # but not processed by a queue processor yet.
    if items_in_grace_period:
        g.stats.simple_event(
            "vote.prequeued.fetch", delta=len(items_in_grace_period))
        r = g.gencache.get_multi(items_in_grace_period.keys())
        for key, v in r.iteritems():
            res[items_in_grace_period[key]] = Vote.deserialize_direction(v)

    cassavotes = get_stored_votes(
        user, [i for i in requested_items if (user, i) not in res])
    res.update(cassavotes)

    return res


def consume_mark_all_read():
    @g.stats.amqp_processor('markread_q')
    def process_mark_all_read(msg):
        user = Account._by_fullname(msg.body)
        inbox_fullnames = get_unread_inbox(user)
        for inbox_chunk in in_chunks(inbox_fullnames, size=100):
            things = Thing._by_fullname(inbox_chunk, return_dict=False)
            unread_handler(things, user, unread=False)

    amqp.consume_items('markread_q', process_mark_all_read)


def consume_deleted_accounts():
    @g.stats.amqp_processor('del_account_q')
    def process_deleted_accounts(msg):
        account = Thing._by_fullname(msg.body)
        assert isinstance(account, Account)

        if account.has_stripe_subscription:
            from r2.controllers.ipn import cancel_stripe_subscription
            cancel_stripe_subscription(account.gold_subscr_id)

        # Mark their link submissions for updating on cloudsearch
        query = LinksByAccount._cf.xget(account._id36)
        for link_id36, unused in query:
            fullname = Link._fullname_from_id36(link_id36)
            msg = pickle.dumps({"fullname": fullname})
            amqp.add_item("search_changes", msg, message_id=fullname,
                delivery_mode=amqp.DELIVERY_TRANSIENT)

    amqp.consume_items('del_account_q', process_deleted_accounts)
