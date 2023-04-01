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

from pylons import tmpl_context as c
from pylons import app_globals as g

from r2.lib.db import queries
from r2.lib.db.tdb_sql import CreationError
from r2.lib import amqp
from r2.lib.utils import extract_user_mentions
from r2.models import query_cache, Thing, Comment, Account, Inbox, NotFound


def notify_mention(user, thing):
    try:
        inbox_rel = Inbox._add(user, thing, "mention")
    except CreationError:
        # this mention was already inserted, ignore it
        g.log.error("duplicate mention for (%s, %s)", user, thing)
        return

    with query_cache.CachedQueryMutator() as m:
        m.insert(queries.get_inbox_comment_mentions(user), [inbox_rel])
        queries.set_unread(thing, user, unread=True, mutator=m)


def remove_mention_notification(mention):
    inbox_owner = mention._thing1
    thing = mention._thing2
    with query_cache.CachedQueryMutator() as m:
        m.delete(queries.get_inbox_comment_mentions(inbox_owner), [mention])
        queries.set_unread(thing, inbox_owner, unread=False, mutator=m)


def readd_mention_notification(mention):
    """Reinsert into inbox after a comment has been unspammed"""
    inbox_owner = mention._thing1
    thing = mention._thing2
    with query_cache.CachedQueryMutator() as m:
        m.insert(queries.get_inbox_comment_mentions(inbox_owner), [mention])
        unread = getattr(mention, 'unread_preremoval', True)
        queries.set_unread(thing, inbox_owner, unread=unread, mutator=m)


def monitor_mentions(comment):
    if comment._spam or comment._deleted:
        return

    sender = comment.author_slow
    if getattr(sender, "butler_ignore", False):
        # this is an account that generates false notifications, e.g.
        # LinkFixer
        return

    if sender.in_timeout:
        return

    subreddit = comment.subreddit_slow
    usernames = extract_user_mentions(comment.body)
    inbox_class = Inbox.rel(Account, Comment)

    # If more than our allowed number of mentions were passed, don't highlight
    # any of them.
    if len(usernames) > g.butler_max_mentions:
        return

    # Subreddit.can_view stupidly requires this.
    c.user_is_loggedin = True

    for username in usernames:
        try:
            account = Account._by_name(username)
        except NotFound:
            continue

        # most people are aware of when they mention themselves.
        if account == sender:
            continue

        # bail out if that user has the feature turned off
        if not account.pref_monitor_mentions:
            continue

        # don't notify users of things they can't see
        if not subreddit.can_view(account):
            continue

        # don't notify users when a person they've blocked mentions them
        if account.is_enemy(sender):
            continue

        # ensure this comment isn't already in the user's inbox already
        rels = inbox_class._fast_query(
            account,
            comment,
            ("inbox", "selfreply", "mention"),
        )
        if filter(None, rels.values()):
            continue

        notify_mention(account, comment)


def run():
    @g.stats.amqp_processor("butler_q")
    def process_message(msg):
        fname = msg.body
        item = Thing._by_fullname(fname, data=True)
        monitor_mentions(item)

    amqp.consume_items("butler_q",
                       process_message,
                       verbose=True)
