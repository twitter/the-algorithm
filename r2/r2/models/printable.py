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

from pylons import request
from pylons import tmpl_context as c

from r2.lib.strings import Score
from r2.lib import hooks


class Printable(object):
    show_spam = False
    show_reports = False
    is_special = False
    can_ban = False
    deleted = False
    rowstyle_cls = ''
    collapsed = False
    author = None
    margin = 0
    is_focal = False
    childlisting = None
    cache_ignore = set(['c', 'author', 'score_fmt', 'child',
                        # displayed score is cachable, so remove score
                        # related fields.
                        'voting_score', 'display_score',
                        'render_score', 'score', '_score', 
                        'upvotes', '_ups',
                        'downvotes', '_downs',
                        'subreddit_slow', '_deleted', '_spam',
                        'cachable', 'make_permalink', 'permalink',
                        'timesince',
                        'num',  # listings only, replaced by CachedVariable
                        'rowstyle_cls',  # listings only, replaced by CachedVariable
                        'upvote_ratio',
                        'should_incr_counts',
                        'keep_item',
                        ])

    @classmethod
    def update_nofollow(cls, user, wrapped):
        pass

    @classmethod
    def add_props(cls, user, wrapped):
        from r2.lib.wrapped import CachedVariable
        for item in wrapped:
            # insert replacement variable for timesince to allow for
            # caching of thing templates
            item.display = CachedVariable("display")
            item.timesince = CachedVariable("timesince")
            item.childlisting = CachedVariable("childlisting")

            score_fmt = getattr(item, "score_fmt", Score.number_only)
            item.display_score = map(score_fmt, item.voting_score)

            if item.cachable:
                item.render_score  = item.display_score
                item.display_score = map(CachedVariable,
                                         ["scoredislikes", "scoreunvoted",
                                          "scorelikes"])

        hooks.get_hook("add_props").call(items=wrapped)

    @property
    def permalink(self, *a, **kw):
        raise NotImplementedError

    def keep_item(self, wrapped):
        return True

    @staticmethod
    def wrapped_cache_key(wrapped, style):
        s = [wrapped._fullname, wrapped._spam]

        # Printables can contain embedded WrappedUsers, which need to consider
        # the site and user's flair settings. Add something to the key
        # indicating there might be flair--we haven't built the WrappedUser yet
        # so we can't check to see if there's actually flair.
        if c.site.flair_enabled and c.user.pref_show_flair:
            s.append('user_flair_enabled')

        if style == 'htmllite':
            s.extend([c.bgcolor, c.bordercolor, 
                      request.GET.has_key('style'),
                      request.GET.get("expanded"),
                      getattr(wrapped, 'embed_voting_style', None)])
        return s
