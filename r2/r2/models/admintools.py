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

from r2.lib import amqp
from r2.lib.db import tdb_cassandra
from r2.lib.db.thing import NotFound
from r2.lib.errors import MessageError
from r2.lib.utils import tup, fetch_things2
from r2.lib.filters import websafe
from r2.lib.hooks import HookRegistrar
from r2.models import (
    Account,
    Comment,
    Link,
    Message,
    NotFound,
    Report,
    Subreddit,
)
from r2.models.award import Award
from r2.models.gold import append_random_bottlecap_phrase, creddits_lock
from r2.models.token import AwardClaimToken
from r2.models.wiki import WikiPage

from _pylibmc import MemcachedError
from pylons import config
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _

from datetime import datetime, timedelta
from copy import copy

admintools_hooks = HookRegistrar()

class AdminTools(object):

    def spam(self, things, auto=True, moderator_banned=False,
             banner=None, date=None, train_spam=True, **kw):
        from r2.lib.db import queries

        all_things = tup(things)
        new_things = [x for x in all_things if not x._spam]

        Report.accept(all_things, True)

        for t in all_things:
            if getattr(t, "promoted", None) is not None:
                g.log.debug("Refusing to mark promotion %r as spam" % t)
                continue

            if not t._spam and train_spam:
                note = 'spam'
            elif not t._spam and not train_spam:
                note = 'remove not spam'
            elif t._spam and not train_spam:
                note = 'confirm spam'
            elif t._spam and train_spam:
                note = 'reinforce spam'

            t._spam = True

            if moderator_banned:
                t.verdict = 'mod-removed'
            elif not auto:
                t.verdict = 'admin-removed'

            ban_info = copy(getattr(t, 'ban_info', {}))
            if isinstance(banner, dict):
                ban_info['banner'] = banner[t._fullname]
            else:
                ban_info['banner'] = banner
            ban_info.update(auto=auto,
                            moderator_banned=moderator_banned,
                            banned_at=date or datetime.now(g.tz),
                            **kw)
            ban_info['note'] = note

            t.ban_info = ban_info
            t._commit()

            if auto:
                amqp.add_item("auto_removed", t._fullname)

        if not auto:
            self.author_spammer(new_things, True)
            self.set_last_sr_ban(new_things)

        queries.ban(all_things, filtered=auto)

        for t in all_things:
            if auto:
                amqp.add_item("auto_removed", t._fullname)

            if isinstance(t, Comment):
                amqp.add_item("removed_comment", t._fullname)
            elif isinstance(t, Link):
                amqp.add_item("removed_link", t._fullname)

    def unspam(self, things, moderator_unbanned=True, unbanner=None,
               train_spam=True, insert=True):
        from r2.lib.db import queries

        things = tup(things)

        # We want to make unban-all moderately efficient, so when
        # mass-unbanning, we're going to skip the code below on links that
        # are already not banned.  However, when someone manually clicks
        # "approve" on an unbanned link, and there's just one, we want do
        # want to run the code below. That way, the little green checkmark
        # will have the right mouseover details, the reports will be
        # cleared, etc.

        if len(things) > 1:
            things = [x for x in things if x._spam]

        Report.accept(things, False)
        for t in things:
            ban_info = copy(getattr(t, 'ban_info', {}))
            ban_info['unbanned_at'] = datetime.now(g.tz)
            if unbanner:
                ban_info['unbanner'] = unbanner
            if ban_info.get('reset_used', None) == None:
                ban_info['reset_used'] = False
            else:
                ban_info['reset_used'] = True
            t.ban_info = ban_info
            t._spam = False
            if moderator_unbanned:
                t.verdict = 'mod-approved'
            else:
                t.verdict = 'admin-approved'
            t._commit()

            if isinstance(t, Comment):
                amqp.add_item("approved_comment", t._fullname)
            elif isinstance(t, Link):
                amqp.add_item("approved_link", t._fullname)

        self.author_spammer(things, False)
        self.set_last_sr_ban(things)
        queries.unban(things, insert)
    
    def report(self, thing):
        pass

    def author_spammer(self, things, spam):
        """incr/decr the 'spammer' field for the author of every
           passed thing"""
        by_aid = {}
        for thing in things:
            if (hasattr(thing, 'author_id')
                and not getattr(thing, 'ban_info', {}).get('auto',True)):
                # only decrement 'spammer' for items that were not
                # autobanned
                by_aid.setdefault(thing.author_id, []).append(thing)

        if by_aid:
            authors = Account._byID(by_aid.keys(), data=True, return_dict=True)

            for aid, author_things in by_aid.iteritems():
                author = authors[aid]
                author._incr('spammer', len(author_things) if spam else -len(author_things))

    def set_last_sr_ban(self, things):
        by_srid = {}
        for thing in things:
            if getattr(thing, 'sr_id', None) is not None:
                by_srid.setdefault(thing.sr_id, []).append(thing)

        if by_srid:
            srs = Subreddit._byID(by_srid.keys(), data=True, return_dict=True)
            for sr_id, sr_things in by_srid.iteritems():
                sr = srs[sr_id]

                sr.last_mod_action = datetime.now(g.tz)
                sr._commit()
                sr._incr('mod_actions', len(sr_things))

    def adjust_gold_expiration(self, account, days=0, months=0, years=0):
        now = datetime.now(g.display_tz)
        if months % 12 == 0:
            years += months / 12
        else:
            days += months * 31
        days += years * 366

        existing_expiration = getattr(account, "gold_expiration", None)
        if existing_expiration is None or existing_expiration < now:
            existing_expiration = now
        account.gold_expiration = existing_expiration + timedelta(days)
        
        if account.gold_expiration > now and not account.gold:
            self.engolden(account)
        elif account.gold_expiration <= now and account.gold:
            self.degolden(account)

        account._commit()     

    def engolden(self, account):
        now = datetime.now(g.display_tz)
        account.gold = True
        description = "Since " + now.strftime("%B %Y")
        
        trophy = Award.give_if_needed("reddit_gold", account,
                                     description=description,
                                     url="/gold/about")
        if trophy and trophy.description.endswith("Member Emeritus"):
            trophy.description = description
            trophy._commit()

        account._commit()
        account.friend_rels_cache(_update=True)

    def degolden(self, account):
        Award.take_away("reddit_gold", account)
        account.gold = False
        account._commit()

    def admin_list(self):
        return list(g.admins)

    def create_award_claim_code(self, unique_award_id, award_codename,
                                description, url):
        '''Create a one-time-use claim URL for a user to claim a trophy.

        `unique_award_id` - A string that uniquely identifies the kind of
                            Trophy the user would be claiming.
                            See: token.py:AwardClaimToken.uid
        `award_codename` - The codename of the Award the user will claim
        `description` - The description the Trophy will receive
        `url` - The URL the Trophy will receive

        '''
        award = Award._by_codename(award_codename)
        token = AwardClaimToken._new(unique_award_id, award, description, url)
        return token.confirm_url()

admintools = AdminTools()

def cancel_subscription(subscr_id):
    q = Account._query(Account.c.gold_subscr_id == subscr_id, data=True)
    l = list(q)
    if len(l) != 1:
        g.log.warning("Found %d matches for canceled subscription %s"
                      % (len(l), subscr_id))
    for account in l:
        account.gold_subscr_id = None
        account._commit()
        g.log.info("%s canceled their recurring subscription %s" %
                   (account.name, subscr_id))

def all_gold_users():
    q = Account._query(Account.c.gold == True, Account.c._spam == (True, False),
                       data=True, sort="_id")
    return fetch_things2(q)

def accountid_from_subscription(subscr_id):
    if subscr_id is None:
        return None

    q = Account._query(Account.c.gold_subscr_id == subscr_id,
                       Account.c._spam == (True, False),
                       Account.c._deleted == (True, False), data=False)
    l = list(q)
    if l:
        return l[0]._id
    else:
        return None

def update_gold_users():
    now = datetime.now(g.display_tz)
    warning_days = 3
    renew_msg = _("[Click here for details on how to set up an "
                  "automatically-renewing subscription or to renew.]"
                  "(/gold) If you have any thoughts, complaints, "
                  "rants, suggestions about reddit gold, please write "
                  "to us at %(gold_email)s. Your feedback would be "
                  "much appreciated.\n\nThank you for your past "
                  "patronage.") % {'gold_email': g.goldsupport_email}

    for account in all_gold_users():
        days_left = (account.gold_expiration - now).days
        if days_left < 0:
            if account.pref_creddit_autorenew:
                with creddits_lock(account):
                    if account.gold_creddits > 0:
                        admintools.adjust_gold_expiration(account, days=31)
                        account.gold_creddits -= 1
                        account._commit()
                        continue

            admintools.degolden(account)

            subject = _("Your reddit gold subscription has expired.")
            message = _("Your subscription to reddit gold has expired.")
            message += "\n\n" + renew_msg
            message = append_random_bottlecap_phrase(message)

            send_system_message(account, subject, message,
                                distinguished='gold-auto')
        elif days_left <= warning_days and not account.gold_will_autorenew:
            hc_key = "gold_expiration_notice-" + account.name
            already_warned = g.hardcache.get(hc_key)
            if not already_warned:
                g.hardcache.set(hc_key, True, 86400 * (warning_days + 1))
                
                subject = _("Your reddit gold subscription is about to "
                            "expire!")
                message = _("Your subscription to reddit gold will be "
                            "expiring soon.")
                message += "\n\n" + renew_msg
                message = append_random_bottlecap_phrase(message)

                send_system_message(account, subject, message,
                                    distinguished='gold-auto')


def is_banned_domain(dom):
    return None

def is_shamed_domain(dom):
    return False, None, None

def bans_for_domain_parts(dom):
    return []


def apply_updates(user, timer):
    pass


def ip_span(ip):
    ip = websafe(ip)
    return '<!-- %s -->' % ip


def wiki_template(template_slug, sr=None):
    """Pull content from a subreddit's wiki page for internal use."""
    if not sr:
        try:
            sr = Subreddit._by_name(g.default_sr)
        except NotFound:
            return None

    try:
        wiki = WikiPage.get(sr, "templates/%s" % template_slug)
    except tdb_cassandra.NotFound:
        return None

    return wiki._get("content")


@admintools_hooks.on("account.registered")
def send_welcome_message(user):
    welcome_title = wiki_template("welcome_title")
    welcome_message = wiki_template("welcome_message")

    if not welcome_title or not welcome_message:
        g.log.warning("Unable to send welcome message: invalid wiki templates.")
        return

    welcome_title = welcome_title.format(username=user.name)
    welcome_message = welcome_message.format(username=user.name)

    return send_system_message(user, welcome_title, welcome_message)


def send_system_message(user, subject, body, system_user=None,
                        distinguished='admin', repliable=False,
                        add_to_sent=True, author=None, signed=False):
    from r2.lib.db import queries

    if system_user is None:
        system_user = Account.system_user()
    if not system_user:
        g.log.warning("Can't send system message "
                      "- invalid system_user or g.system_user setting")
        return
    if not author:
        author = system_user

    item, inbox_rel = Message._new(author, user, subject, body,
                                   ip='0.0.0.0')
    item.distinguished = distinguished
    item.repliable = repliable
    item.display_author = system_user._id
    item.signed = signed
    item._commit()

    try:
        queries.new_message(item, inbox_rel, add_to_sent=add_to_sent)
    except MemcachedError:
        raise MessageError('reddit_inbox')


if config['r2.import_private']:
    from r2admin.models.admintools import *
