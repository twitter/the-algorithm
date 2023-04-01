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

from collections import Counter

from r2.lib.db.thing import Relation, MultiRelation
from r2.lib.utils import tup
from r2.models import Link, Comment, Message, Subreddit, Account

from pylons import tmpl_context as c
from pylons import app_globals as g


_LinkReport = Relation(Account, Link)
_CommentReport = Relation(Account, Comment)
_SubredditReport = Relation(Account, Subreddit)
_MessageReport = Relation(Account, Message)
REPORT_RELS = (_LinkReport, _CommentReport, _SubredditReport, _MessageReport)

for report_cls in REPORT_RELS:
    report_cls._cache = g.thingcache

_LinkReport._cache_prefix = classmethod(lambda cls: "reportlink:")
_CommentReport._cache_prefix = classmethod(lambda cls: "reportcomment:")
_SubredditReport._cache_prefix = classmethod(lambda cls: "reportsr:")
_MessageReport._cache_prefix = classmethod(lambda cls: "reportmessage:")


class Report(MultiRelation('report', *REPORT_RELS)):
    _field = 'reported'

    @classmethod
    def new(cls, user, thing, reason=None):
        from r2.lib.db import queries

        # check if this report exists already!
        rel = cls.rel(user, thing)
        q = rel._fast_query(user, thing, ['-1', '0', '1'])
        q = [ report for (tupl, report) in q.iteritems() if report ]
        if q:
            # stop if we've seen this before, so that we never get the
            # same report from the same user twice
            oldreport = q[0]
            g.log.debug("Ignoring duplicate report %s" % oldreport)
            return oldreport

        kw = {}
        if reason:
            kw['reason'] = reason

        r = Report(user, thing, '0', **kw)

        # mark item as reported
        try:
            thing._incr(cls._field)
        except (ValueError, TypeError):
            g.log.error("%r has bad field %r = %r" % (thing, cls._field,
                         getattr(thing, cls._field, "(nonexistent)")))
            raise

        r._commit()

        if hasattr(thing, 'author_id'):
            author = Account._byID(thing.author_id, data=True)
            author._incr('reported')

        if not getattr(thing, "ignore_reports", False):
            # update the reports queue if it exists
            queries.new_report(thing, r)

            # if the thing is already marked as spam, accept the report
            if thing._spam:
                cls.accept(thing)

        return r

    @classmethod
    def for_thing(cls, thing):
        rel = cls.rel(Account, thing.__class__)
        rels = rel._query(rel.c._thing2_id == thing._id, data=True)

        return list(rels)

    @classmethod
    def accept(cls, things, correct = True):
        from r2.lib.db import queries

        things = tup(things)

        things_by_cls = {}
        for thing in things:
            things_by_cls.setdefault(thing.__class__, []).append(thing)

        for thing_cls, cls_things in things_by_cls.iteritems():
            to_clear = []
            # look up all of the reports for each thing
            rel_cls = cls.rel(Account, thing_cls)
            thing_ids = [t._id for t in cls_things]
            rels = rel_cls._query(rel_cls.c._thing2_id == thing_ids)
            for r in rels:
                if r._name == '0':
                    r._name = '1' if correct else '-1'
                    r._commit()

            for thing in cls_things:
                if thing.reported > 0:
                    thing.reported = 0
                    thing._commit()
                    to_clear.append(thing)

            queries.clear_reports(to_clear, rels)

    @classmethod
    def get_reports(cls, wrapped, max_user_reasons=20):
        """Get two lists of mod and user reports on the item."""
        if (wrapped.reported > 0 and
                (wrapped.can_ban or
                 getattr(wrapped, "promoted", None) and c.user_is_sponsor)):
            from r2.models import SRMember

            reports = cls.for_thing(wrapped.lookups[0])

            query = SRMember._query(SRMember.c._thing1_id == wrapped.sr_id,
                                    SRMember.c._name == "moderator")
            mod_dates = {rel._thing2_id: rel._date for rel in query}

            if g.automoderator_account:
                automoderator = Account._by_name(g.automoderator_account)
            else:
                automoderator = None

            mod_reports = []
            user_reports = []

            for report in reports:
                # always include AutoModerator reports
                if automoderator and report._thing1_id == automoderator._id:
                    mod_reports.append(report)
                # include in mod reports if made after the user became a mod
                elif (report._thing1_id in mod_dates and
                        report._date >= mod_dates[report._thing1_id]):
                    mod_reports.append(report)
                else:
                    user_reports.append(report)

            # mod reports return as tuples with (reason, name)
            mods = Account._byID([report._thing1_id
                                  for report in mod_reports],
                                 data=True, return_dict=True)
            mod_reports = [(getattr(report, "reason", None),
                            mods[report._thing1_id].name)
                            for report in mod_reports]

            # user reports return as tuples with (reason, count)
            user_reports = Counter([getattr(report, "reason", None)
                                    for report in user_reports])
            user_reports = user_reports.most_common(max_user_reasons)

            return mod_reports, user_reports
        else:
            return [], []

    @classmethod
    def get_reasons(cls, wrapped):
        """Transition method in case API clients were already using this."""
        if wrapped.can_ban and wrapped.reported > 0:
            return [("This attribute is deprecated. Please use mod_reports "
                     "and user_reports instead.")]
        else:
            return []
