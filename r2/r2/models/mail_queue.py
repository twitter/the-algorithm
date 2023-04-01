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
import hashlib
import time
import email.utils
from email.MIMEText import MIMEText
from email.errors import HeaderParseError

import sqlalchemy as sa
from sqlalchemy.dialects.postgresql.base import PGInet

from r2.lib.db.tdb_sql import make_metadata, index_str, create_table
from r2.lib.utils import Enum, tup
from r2.lib.memoize import memoize
from pylons import request
from pylons import app_globals as g
from pylons.i18n import _

def mail_queue(metadata):
    return sa.Table(g.db_app_name + '_mail_queue', metadata,
                    sa.Column("uid", sa.Integer,
                              sa.Sequence('queue_id_seq'), primary_key=True),

                    # unique hash of the message to carry around
                    sa.Column("msg_hash", sa.String),

                    # the id of the account who started it
                    sa.Column('account_id', sa.BigInteger),

                    # the name (not email) for the from
                    sa.Column('from_name', sa.String),

                    # the "To" address of the email
                    sa.Column('to_addr', sa.String),

                    # the "From" address of the email
                    sa.Column('fr_addr', sa.String),

                    # the "Reply-To" address of the email
                    sa.Column('reply_to', sa.String),

                    # fullname of the thing
                    sa.Column('fullname', sa.String),

                    # when added to the queue
                    sa.Column('date',
                              sa.DateTime(timezone = True),
                              nullable = False),

                    # IP of original request
                    sa.Column('ip', PGInet),

                    # enum of kind of event
                    sa.Column('kind', sa.Integer),

                    # any message that may have been included
                    sa.Column('body', sa.String),

                    )

def sent_mail_table(metadata, name = 'sent_mail'):
    return sa.Table(g.db_app_name + '_' + name, metadata,
                    # tracking hash of the email
                    sa.Column('msg_hash', sa.String, primary_key=True),

                    # the account who started it
                    sa.Column('account_id', sa.BigInteger),

                    # the "To" address of the email
                    sa.Column('to_addr', sa.String),

                    # the "From" address of the email
                    sa.Column('fr_addr', sa.String),

                    # the "reply-to" address of the email
                    sa.Column('reply_to', sa.String),

                    # IP of original request
                    sa.Column('ip', PGInet),

                    # fullname of the reference thing
                    sa.Column('fullname', sa.String),

                    # send date
                    sa.Column('date',
                              sa.DateTime(timezone = True),
                              default = sa.func.now(),
                              nullable = False),

                    # enum of kind of event
                    sa.Column('kind', sa.Integer),

                    )


def opt_out(metadata):
    return sa.Table(g.db_app_name + '_opt_out', metadata,
                    sa.Column('email', sa.String, primary_key = True),
                    # when added to the list
                    sa.Column('date',
                              sa.DateTime(timezone = True),
                              default = sa.func.now(),
                              nullable = False),
                    # why did they do it!?
                    sa.Column('msg_hash', sa.String),
                    )

class EmailHandler(object):
    def __init__(self, force = False):
        engine = g.dbm.get_engine('email')
        self.metadata = make_metadata(engine)
        self.queue_table = mail_queue(self.metadata)
        indices = [index_str(self.queue_table, "date", "date"),
                   index_str(self.queue_table, 'kind', 'kind')]
        create_table(self.queue_table, indices)

        self.opt_table = opt_out(self.metadata)
        indices = [index_str(self.opt_table, 'email', 'email')]
        create_table(self.opt_table, indices)

        self.track_table = sent_mail_table(self.metadata)
        self.reject_table = sent_mail_table(self.metadata, name = "reject_mail")

        def sent_indices(tab):
            indices = [index_str(tab, 'to_addr', 'to_addr'),
                       index_str(tab, 'date', 'date'),
                       index_str(tab, 'ip', 'ip'),
                       index_str(tab, 'kind', 'kind'),
                       index_str(tab, 'fullname', 'fullname'),
                       index_str(tab, 'account_id', 'account_id'),
                       index_str(tab, 'msg_hash', 'msg_hash'),
                       ]

        create_table(self.track_table, sent_indices(self.track_table))
        create_table(self.reject_table, sent_indices(self.reject_table))

    def __repr__(self):
        return "<email-handler>"

    def has_opted_out(self, email):
        o = self.opt_table
        s = sa.select([o.c.email], o.c.email == email, limit = 1)
        res = s.execute()
        return bool(res.fetchall())


    def opt_out(self, msg_hash):
        """Adds the recipient of the email to the opt-out list and returns
        that address."""
        email = self.get_recipient(msg_hash)
        if email:
            o = self.opt_table
            try:
                o.insert().values({o.c.email: email,
                                   o.c.msg_hash: msg_hash}).execute()
                g.stats.simple_event('share.opt_out')

                #clear caches
                has_opted_out(email, _update = True)
                opt_count(_update = True)
                return (email, True)
            except sa.exc.DBAPIError:
                return (email, False)
        return (None, False)

    def opt_in(self, msg_hash):
        """Removes recipient of the email from the opt-out list"""
        email = self.get_recipient(msg_hash)
        if email:
            o = self.opt_table
            if self.has_opted_out(email):
                sa.delete(o, o.c.email == email).execute()
                g.stats.simple_event('share.opt_in')

                #clear caches
                has_opted_out(email, _update = True)
                opt_count(_update = True)
                return (email, True)
            else:
                return (email, False)
        return (None, False)

    def get_recipient(self, msg_hash):
        t = self.track_table
        s = sa.select([t.c.to_addr], t.c.msg_hash == msg_hash).execute()
        res = s.fetchall()
        return res[0][0] if res and res[:1] else None


    def add_to_queue(self, user, emails, from_name, fr_addr, kind,
                     date = None, ip = None,
                     body = "", reply_to = "", thing = None):
        s = self.queue_table
        hashes = []
        if not date:
            date = datetime.datetime.now(g.tz)
        if not ip:
            ip = getattr(request, "ip", "127.0.0.1")
        for email in tup(emails):
            uid = user._id if user else 0
            tid = thing._fullname if thing else ""
            key = hashlib.sha1(str((email, from_name, uid, tid, ip, kind, body,
                               datetime.datetime.now(g.tz)))).hexdigest()
            s.insert().values({s.c.to_addr : email,
                               s.c.account_id : uid,
                               s.c.from_name : from_name,
                               s.c.fr_addr : fr_addr,
                               s.c.reply_to : reply_to,
                               s.c.fullname: tid,
                               s.c.ip : ip,
                               s.c.kind: kind,
                               s.c.body: body,
                               s.c.date : date,
                               s.c.msg_hash : key}).execute()
            hashes.append(key)
        return hashes


    def from_queue(self, max_date, batch_limit = 50, kind = None):
        from r2.models import Account, Thing
        keep_trying = True
        min_id = None
        s = self.queue_table
        while keep_trying:
            where = [s.c.date < max_date]
            if min_id:
                where.append(s.c.uid > min_id)
            if kind:
                where.append(s.c.kind == kind)

            res = sa.select([s.c.to_addr, s.c.account_id,
                             s.c.from_name, s.c.fullname, s.c.body,
                             s.c.kind, s.c.ip, s.c.date, s.c.uid,
                             s.c.msg_hash, s.c.fr_addr, s.c.reply_to],
                            sa.and_(*where),
                            order_by = s.c.uid, limit = batch_limit).execute()
            res = res.fetchall()

            if not res: break

            # batch load user accounts
            aids = [x[1] for x in res if x[1] > 0]
            accts = Account._byID(aids, data = True,
                                  return_dict = True) if aids else {}

            # batch load things
            tids = [x[3] for x in res if x[3]]
            things = Thing._by_fullname(tids, data = True,
                                        return_dict = True) if tids else {}

            # get the lower bound date for next iteration
            min_id = max(x[8] for x in res)

            # did we not fetch them all?
            keep_trying = (len(res) == batch_limit)

            for (addr, acct, fname, fulln, body, kind, ip, date, uid,
                 msg_hash, fr_addr, reply_to) in res:
                yield (accts.get(acct), things.get(fulln), addr,
                       fname, date, ip, kind, msg_hash, body,
                       fr_addr, reply_to)

    def clear_queue(self, max_date, kind = None):
        s = self.queue_table
        where = [s.c.date < max_date]
        if kind:
            where.append([s.c.kind == kind])
        sa.delete(s, sa.and_(*where)).execute()


class Email(object):
    handler = EmailHandler()

    # Do not modify in any way other than appending new items!
    # Database tables storing mail stuff use an int column as an index into 
    # this Enum, so anything other than appending new items breaks mail history.
    Kind = Enum("SHARE", "FEEDBACK", "ADVERTISE", "OPTOUT", "OPTIN",
                "VERIFY_EMAIL", "RESET_PASSWORD",
                "BID_PROMO",
                "ACCEPT_PROMO",
                "REJECT_PROMO",
                "QUEUED_PROMO",
                "LIVE_PROMO",
                "FINISHED_PROMO",
                "NEW_PROMO",
                "NERDMAIL",
                "GOLDMAIL",
                "PASSWORD_CHANGE",
                "EMAIL_CHANGE",
                "REFUNDED_PROMO",
                "VOID_PAYMENT",
                "GOLD_GIFT_CODE",
                "SUSPICIOUS_PAYMENT",
                "FRAUD_ALERT",
                "USER_FRAUD",
                "MESSAGE_NOTIFICATION",
                "ADS_ALERT",
                "EDITED_LIVE_PROMO",
                )

    # Do not remove anything from this dictionary!  See above comment.
    subjects = {
        Kind.SHARE : _("[reddit] %(user)s has shared a link with you"),
        Kind.FEEDBACK : _("[feedback] feedback from '%(user)s'"),
        Kind.ADVERTISE :  _("[advertising] feedback from '%(user)s'"),
        Kind.OPTOUT : _("[reddit] email removal notice"),
        Kind.OPTIN  : _("[reddit] email addition notice"),
        Kind.RESET_PASSWORD : _("[reddit] reset your password"),
        Kind.VERIFY_EMAIL : _("[reddit] verify your email address"),
        Kind.BID_PROMO : _("[reddit] your budget has been accepted"),
        Kind.ACCEPT_PROMO : _("[reddit] your promotion has been accepted"),
        Kind.REJECT_PROMO : _("[reddit] your promotion has been rejected"),
        Kind.QUEUED_PROMO : _("[reddit] your promotion has been charged"),
        Kind.LIVE_PROMO   : _("[reddit] your promotion is now live"),
        Kind.FINISHED_PROMO : _("[reddit] your promotion has finished"),
        Kind.NEW_PROMO : _("[reddit] your promotion has been created"),
        Kind.EDITED_LIVE_PROMO : _("[reddit] your promotion edit is being approved"),
        Kind.NERDMAIL : _("[reddit] hey, nerd!"),
        Kind.GOLDMAIL : _("[reddit] reddit gold activation link"),
        Kind.PASSWORD_CHANGE : _("[reddit] your password has been changed"),
        Kind.EMAIL_CHANGE : _("[reddit] your email address has been changed"),
        Kind.REFUNDED_PROMO: _("[reddit] your campaign didn't get enough impressions"),
        Kind.VOID_PAYMENT: _("[reddit] your payment has been voided"),
        Kind.GOLD_GIFT_CODE: _("[reddit] your reddit gold gift code"),
        Kind.SUSPICIOUS_PAYMENT: _("[selfserve] suspicious payment alert"),
        Kind.FRAUD_ALERT: _("[selfserve] fraud alert"),
        Kind.USER_FRAUD: _("[selfserve] a user has committed fraud"),
        Kind.MESSAGE_NOTIFICATION: _("[reddit] message notification"),
        Kind.ADS_ALERT: _("[reddit] Ads Alert"),
        }

    def __init__(self, user, thing, email, from_name, date, ip,
                 kind, msg_hash, body = '', from_addr = '',
                 reply_to = ''):
        self.user = user
        self.thing = thing
        self.to_addr = email
        self.fr_addr = from_addr
        self._from_name = from_name
        self.date = date
        self.ip = ip
        self.kind = kind
        self.sent = False
        self.body = body
        self.msg_hash = msg_hash
        self.reply_to = reply_to
        self.subject = self.subjects.get(kind, "")
        try:
            self.subject = self.subject % dict(user = self.from_name())
        except UnicodeDecodeError:
            self.subject = self.subject % dict(user = "a user")


    def from_name(self):
        if not self.user:
            name = "%(name)s"
        elif self._from_name != self.user.name:
            name = "%(name)s (%(uname)s)"
        else:
            name = "%(uname)s"
        return name % dict(name = self._from_name,
                           uname = self.user.name if self.user else '')

    @classmethod
    def get_unsent(cls, max_date, batch_limit = 50, kind = None):
        for e in cls.handler.from_queue(max_date, batch_limit = batch_limit,
                                        kind = kind):
            yield cls(*e)

    def should_queue(self):
        return (not self.user  or not self.user._spam) and \
               (not self.thing or not self.thing._spam) and \
               (self.kind == self.Kind.OPTOUT or
                not has_opted_out(self.to_addr))

    def set_sent(self, date = None, rejected = False):
        if not self.sent:
            self.date = date or datetime.datetime.now(g.tz)
            t = self.handler.reject_table if rejected else self.handler.track_table
            try:
                t.insert().values({t.c.account_id:
                                       self.user._id if self.user else 0,
                                   t.c.to_addr :   self.to_addr,
                                   t.c.fr_addr :   self.fr_addr,
                                   t.c.reply_to :  self.reply_to,
                                   t.c.ip :        self.ip,
                                   t.c.fullname:
                                       self.thing._fullname if self.thing else "",
                                   t.c.date:       self.date,
                                   t.c.kind :      self.kind,
                                   t.c.msg_hash :  self.msg_hash,
                                   }).execute()
            except:
                print "failed to send message"

            self.sent = True

    def to_MIMEText(self):
        def utf8(s, reject_newlines=True):
            if reject_newlines and '\n' in s:
                raise HeaderParseError(
                    'header value contains unexpected newline: {!r}'.format(s))
            return s.encode('utf8') if isinstance(s, unicode) else s

        fr = '"%s" <%s>' % (
            self.from_name().replace('"', ''),
            self.fr_addr.replace('>', ''),
        )

        # Addresses that start with a dash could confuse poorly-written
        # software's argument parsers, and thus are disallowed by default in
        # Postfix: http://www.postfix.org/postconf.5.html#allow_min_user
        if not fr.startswith('-') and not self.to_addr.startswith('-'):
            msg = MIMEText(utf8(self.body, reject_newlines=False))
            msg.set_charset('utf8')
            msg['To']      = utf8(self.to_addr)
            msg['From']    = utf8(fr)
            msg['Subject'] = utf8(self.subject)
            timestamp = time.mktime(self.date.timetuple())
            msg['Date'] = utf8(email.utils.formatdate(timestamp))
            if self.user:
                msg['X-Reddit-username'] = utf8(self.user.name)
            msg['X-Reddit-ID'] = self.msg_hash
            if self.reply_to:
                msg['Reply-To'] = utf8(self.reply_to)
            return msg
        return None

@memoize('r2.models.mail_queue.has_opted_out')
def has_opted_out(email):
    o = Email.handler.opt_table
    s = sa.select([o.c.email], o.c.email == email, limit = 1)
    res = s.execute()
    return bool(res.fetchall())


@memoize('r2.models.mail_queue.opt_count')
def opt_count():
    o = Email.handler.opt_table
    s = sa.select([sa.func.count(o.c.email)])
    res = s.execute().fetchone()
    return int(res[0])
