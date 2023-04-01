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
One-time use functions to migrate from one reddit-version to another
"""
from r2.lib.promote import *

def add_allow_top_to_srs():
    "Add the allow_top property to all stored subreddits"
    from r2.models import Subreddit
    from r2.lib.db.operators import desc
    from r2.lib.utils import fetch_things2

    q = Subreddit._query(Subreddit.c._spam == (True,False),
                         sort = desc('_date'))
    for sr in fetch_things2(q):
        sr.allow_top = True; sr._commit()

def subscribe_to_blog_and_annoucements(filename):
    import re
    from time import sleep
    from r2.models import Account, Subreddit

    r_blog = Subreddit._by_name("blog")
    r_announcements = Subreddit._by_name("announcements")

    contents = file(filename).read()
    numbers = [ int(s) for s in re.findall("\d+", contents) ]

#    d = Account._byID(numbers, data=True)

#   for i, account in enumerate(d.values()):
    for i, account_id in enumerate(numbers):
        account = Account._byID(account_id, data=True)

        for sr in r_blog, r_announcements:
            if sr.add_subscriber(account):
                sr._incr("_ups", 1)
                print ("%d: subscribed %s to %s" % (i, account.name, sr.name))
            else:
                print ("%d: didn't subscribe %s to %s" % (i, account.name, sr.name))


def recompute_unread(min_date = None):
    from r2.models import Inbox, Account, Comment, Message
    from r2.lib.db import queries

    def load_accounts(inbox_rel):
        accounts = set()
        q = inbox_rel._query(eager_load = False, data = False,
                             sort = desc("_date"))
        if min_date:
            q._filter(inbox_rel.c._date > min_date)

        for i in fetch_things2(q):
            accounts.add(i._thing1_id)

        return accounts

    accounts_m = load_accounts(Inbox.rel(Account, Message))
    for i, a in enumerate(accounts_m):
        a = Account._byID(a)
        print "%s / %s : %s" % (i, len(accounts_m), a)
        queries.get_unread_messages(a).update()
        queries.get_unread_comments(a).update()
        queries.get_unread_selfreply(a).update()

    accounts = load_accounts(Inbox.rel(Account, Comment)) - accounts_m
    for i, a in enumerate(accounts):
        a = Account._byID(a)
        print "%s / %s : %s" % (i, len(accounts), a)
        queries.get_unread_comments(a).update()
        queries.get_unread_selfreply(a).update()



def pushup_permacache(verbosity=1000):
    """When putting cassandra into the permacache chain, we need to
       push everything up into the rest of the chain, so this is
       everything that uses the permacache, as of that check-in."""
    from pylons import app_globals as g
    from r2.models import Link, Subreddit, Account
    from r2.lib.db.operators import desc
    from r2.lib.comment_tree import comments_key, messages_key
    from r2.lib.utils import fetch_things2, in_chunks
    from r2.lib.utils import last_modified_key
    from r2.lib.promote import promoted_memo_key
    from r2.lib.subreddit_search import load_all_reddits
    from r2.lib.db import queries
    from r2.lib.cache import CassandraCacheChain

    authority = g.permacache.caches[-1]
    nonauthority = CassandraCacheChain(g.permacache.caches[1:-1])

    def populate(keys):
        vals = authority.simple_get_multi(keys)
        if vals:
            nonauthority.set_multi(vals)

    def gen_keys():
        yield promoted_memo_key

        # just let this one do its own writing
        load_all_reddits()

        yield queries.get_all_comments().iden

        l_q = Link._query(Link.c._spam == (True, False),
                          Link.c._deleted == (True, False),
                          sort=desc('_date'),
                          data=True,
                          )
        for link in fetch_things2(l_q, verbosity):
            yield comments_key(link._id)
            yield last_modified_key(link, 'comments')

        a_q = Account._query(Account.c._spam == (True, False),
                             sort=desc('_date'),
                             )
        for account in fetch_things2(a_q, verbosity):
            yield messages_key(account._id)
            yield last_modified_key(account, 'overview')
            yield last_modified_key(account, 'commented')
            yield last_modified_key(account, 'submitted')
            yield last_modified_key(account, 'liked')
            yield last_modified_key(account, 'disliked')
            yield queries.get_comments(account, 'new', 'all').iden
            yield queries.get_submitted(account, 'new', 'all').iden
            yield queries.get_liked(account).iden
            yield queries.get_disliked(account).iden
            yield queries.get_hidden(account).iden
            yield queries.get_saved(account).iden
            yield queries.get_inbox_messages(account).iden
            yield queries.get_unread_messages(account).iden
            yield queries.get_inbox_comments(account).iden
            yield queries.get_unread_comments(account).iden
            yield queries.get_inbox_selfreply(account).iden
            yield queries.get_unread_selfreply(account).iden
            yield queries.get_sent(account).iden

        sr_q = Subreddit._query(Subreddit.c._spam == (True, False),
                                sort=desc('_date'),
                                )
        for sr in fetch_things2(sr_q, verbosity):
            yield last_modified_key(sr, 'stylesheet_contents')
            yield queries.get_links(sr, 'hot', 'all').iden
            yield queries.get_links(sr, 'new', 'all').iden

            for sort in 'top', 'controversial':
                for time in 'hour', 'day', 'week', 'month', 'year', 'all':
                    yield queries.get_links(sr, sort, time,
                                            merge_batched=False).iden
            yield queries.get_spam_links(sr).iden
            yield queries.get_spam_comments(sr).iden
            yield queries.get_reported_links(sr).iden
            yield queries.get_reported_comments(sr).iden
            yield queries.get_subreddit_messages(sr).iden
            yield queries.get_unread_subreddit_messages(sr).iden

    done = 0
    for keys in in_chunks(gen_keys(), verbosity):
        g.reset_caches()
        done += len(keys)
        print 'Done %d: %r' % (done, keys[-1])
        populate(keys)


def port_cassaurls(after_id=None, estimate=15231317):
    from r2.models import Link, LinksByUrlAndSubreddit
    from r2.lib.db import tdb_cassandra
    from r2.lib.db.operators import desc
    from r2.lib.db.tdb_cassandra import CL
    from r2.lib.utils import fetch_things2, in_chunks, progress

    q = Link._query(Link.c._spam == (True, False),
                    sort=desc('_date'), data=True)
    if after_id:
        q._after(Link._byID(after_id,data=True))
    q = fetch_things2(q, chunk_size=500)
    q = progress(q, estimate=estimate)
    q = (l for l in q
         if getattr(l, 'url', 'self') != 'self'
         and not getattr(l, 'is_self', False))
    chunks = in_chunks(q, 500)

    for chunk in chunks:
        for l in chunk:
            LinksByUrlAndSubreddit.add_link(l)

def port_deleted_links(after_id=None):
    from r2.models import Link
    from r2.lib.db.operators import desc
    from r2.models.query_cache import CachedQueryMutator
    from r2.lib.db.queries import get_deleted_links
    from r2.lib.utils import fetch_things2, in_chunks, progress

    q = Link._query(Link.c._deleted == True,
                    Link.c._spam == (True, False),
                    sort=desc('_date'), data=True)
    q = fetch_things2(q, chunk_size=500)
    q = progress(q, verbosity=1000)

    for chunk in in_chunks(q):
        with CachedQueryMutator() as m:
            for link in chunk:
                query = get_deleted_links(link.author_id)
                m.insert(query, [link])

def convert_query_cache_to_json():
    import cPickle
    from r2.models.query_cache import json, UserQueryCache

    with UserQueryCache._cf.batch() as m:
        for key, columns in UserQueryCache._cf.get_range():
            out = {}
            for ckey, cvalue in columns.iteritems():
                try:
                    raw = cPickle.loads(cvalue)
                except cPickle.UnpicklingError:
                    continue
                out[ckey] = json.dumps(raw)
            m.insert(key, out)

def populate_spam_filtered():
    from r2.lib.db.queries import get_spam_links, get_spam_comments
    from r2.lib.db.queries import get_spam_filtered_links, get_spam_filtered_comments
    from r2.models.query_cache import CachedQueryMutator

    def was_filtered(thing):
        if thing._spam and not thing._deleted and \
           getattr(thing, 'verdict', None) != 'mod-removed':
            return True
        else:
            return False

    q = Subreddit._query(sort = asc('_date'))
    for sr in fetch_things2(q):
        print 'Processing %s' % sr.name
        links = Thing._by_fullname(get_spam_links(sr), data=True,
                                   return_dict=False)
        comments = Thing._by_fullname(get_spam_comments(sr), data=True,
                                      return_dict=False)
        insert_links = [l for l in links if was_filtered(l)]
        insert_comments = [c for c in comments if was_filtered(c)]
        with CachedQueryMutator() as m:
            m.insert(get_spam_filtered_links(sr), insert_links)
            m.insert(get_spam_filtered_comments(sr), insert_comments)
