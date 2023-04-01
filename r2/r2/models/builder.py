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

from collections import defaultdict, namedtuple
from copy import deepcopy
import datetime
import heapq
from random import shuffle
import time

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _

from r2.config import feature
from r2.config.extensions import API_TYPES, RSS_TYPES
from r2.lib import hooks
from r2.lib.comment_tree import (
    conversation,
    get_comment_scores,
    moderator_messages,
    sr_conversation,
    subreddit_messages,
    tree_sort_fn,
    user_messages,
)
from r2.lib.wrapped import Wrapped
from r2.lib.db import operators, tdb_cassandra
from r2.lib.filters import _force_unicode
from r2.lib.jsontemplates import get_trimmed_sr_dicts
from r2.lib.utils import (
    long_datetime,
    SimpleSillyStub,
    Storage,
    to36,
    tup,
)

from r2.models import (
    Account,
    Comment,
    CommentSavesByAccount,
    Link,
    LinkSavesByAccount,
    Message,
    MoreChildren,
    MoreMessages,
    MoreRecursion,
    Subreddit,
    Thing,
    wiki,
)
from r2.models.admintools import ip_span
from r2.models.comment_tree import CommentTree
from r2.models.flair import Flair
from r2.models.listing import Listing
from r2.models.vote import Vote


EXTRA_FACTOR = 1.5
MAX_RECURSION = 10


class InconsistentCommentTreeError(Exception):
  pass


class Builder(object):
    def __init__(self, wrap=Wrapped, prewrap_fn=None, keep_fn=None, stale=True,
                 spam_listing=False):
        self.wrap = wrap
        self.prewrap_fn = prewrap_fn
        self.keep_fn = keep_fn
        self.stale = stale
        self.spam_listing = spam_listing

    def keep_item(self, item):
        if self.keep_fn:
            return self.keep_fn(item)
        else:
            return item.keep_item(item)

    def wrap_items(self, items):
        from r2.lib.db import queries
        from r2.lib.template_helpers import (
            add_friend_distinguish,
            add_admin_distinguish,
            add_moderator_distinguish,
            add_cakeday_distinguish,
            add_special_distinguish,
        )

        user = c.user if c.user_is_loggedin else None
        aids = set(l.author_id for l in items if hasattr(l, 'author_id')
                   and l.author_id is not None)

        authors = Account._byID(aids, data=True, stale=self.stale)
        now = datetime.datetime.now(g.tz)
        cakes = {a._id for a in authors.itervalues()
                       if a.cake_expiration and a.cake_expiration >= now}
        friend_rels = user.friend_rels() if user and user.gold else {}

        subreddits = Subreddit.load_subreddits(items, stale=self.stale)
        can_ban_set = set()

        if user:
            for sr_id, sr in subreddits.iteritems():
                if sr.can_ban(user):
                    can_ban_set.add(sr_id)

        #get likes/dislikes
        try:
            likes = queries.get_likes(user, items)
        except tdb_cassandra.TRANSIENT_EXCEPTIONS as e:
            g.log.warning("Cassandra vote lookup failed: %r", e)
            likes = {}

        types = {}
        wrapped = []

        for item in items:
            w = self.wrap(item)
            wrapped.append(w)
            # add for caching (plus it should be bad form to use _
            # variables in templates)
            w.fullname = item._fullname
            types.setdefault(w.render_class, []).append(w)

            w.author = None
            w.friend = False
            w.enemy = False

            w.distinguished = None
            if hasattr(item, "distinguished"):
                if item.distinguished == 'yes':
                    w.distinguished = 'moderator'
                elif item.distinguished in ('admin', 'special',
                                            'gold', 'gold-auto'):
                    w.distinguished = item.distinguished

            if getattr(item, "author_id", None):
                w.author = authors.get(item.author_id)

            if hasattr(item, "sr_id") and item.sr_id is not None:
                w.subreddit = subreddits[item.sr_id]

            distinguish_attribs_list = []

            # if display_author exists, then w.author is unknown to the
            # receiver, so we can't check for friend or cakeday
            author_is_hidden = hasattr(item, 'display_author')

            if user and w.author:
                # the enemy flag will trigger keep_item to fail in Printable
                if w.author._id in user.enemies:
                    w.enemy = True

                elif not author_is_hidden and w.author._id in user.friends:
                    w.friend = True
                    if item.author_id in friend_rels:
                        note = getattr(friend_rels[w.author._id], "note", None)
                    else:
                        note = None
                    add_friend_distinguish(distinguish_attribs_list, note)

            if (w.distinguished == 'admin' and w.author):
                add_admin_distinguish(distinguish_attribs_list)

            if w.distinguished == 'moderator':
                add_moderator_distinguish(distinguish_attribs_list, w.subreddit)

            if w.distinguished == 'special':
                add_special_distinguish(distinguish_attribs_list, w.author)

            if (not author_is_hidden and
                    w.author and w.author._id in cakes and not c.profilepage):
                add_cakeday_distinguish(distinguish_attribs_list, w.author)

            w.attribs = distinguish_attribs_list

            user_vote_dir = likes.get((user, item))

            if user_vote_dir == Vote.DIRECTIONS.up:
                w.likes = True
            elif user_vote_dir == Vote.DIRECTIONS.down:
                w.likes = False
            else:
                w.likes = None

            w.upvotes = item._ups
            w.downvotes = item._downs

            total_votes = max(item.num_votes, 1)
            w.upvote_ratio = float(item._ups) / total_votes

            w.is_controversial = self._is_controversial(w)

            w.score = w.upvotes - w.downvotes

            if user_vote_dir == Vote.DIRECTIONS.up:
                base_score = w.score - 1
            elif user_vote_dir == Vote.DIRECTIONS.down:
                base_score = w.score + 1
            else:
                base_score = w.score

            # store the set of available scores based on the vote
            # for ease of i18n when there is a label
            w.voting_score = [(base_score + x - 1) for x in range(3)]

            w.deleted = item._deleted

            w.link_notes = []

            if c.user_is_admin:
                if item._deleted:
                    w.link_notes.append("deleted link")
                if getattr(item, "verdict", None):
                    if not item.verdict.endswith("-approved"):
                        w.link_notes.append(w.verdict)

            if c.user_is_admin and getattr(item, 'ip', None):
                w.ip_span = ip_span(item.ip)
            else:
                w.ip_span = ""

            # if the user can ban things on a given subreddit, or an
            # admin, then allow them to see that the item is spam, and
            # add the other spam-related display attributes
            w.show_reports = False
            w.show_spam    = False
            w.can_ban      = False
            w.use_big_modbuttons = self.spam_listing

            if (c.user_is_admin
                or (user
                    and hasattr(item,'sr_id')
                    and item.sr_id in can_ban_set)):
                if getattr(item, "promoted", None) is None:
                    w.can_ban = True

                ban_info = getattr(item, 'ban_info', {})
                w.unbanner = ban_info.get('unbanner')

                if item._spam:
                    w.show_spam = True
                    w.moderator_banned = ban_info.get('moderator_banned', False)
                    w.autobanned = ban_info.get('auto', False)
                    w.banner = ban_info.get('banner')
                    w.banned_at = ban_info.get("banned_at", None)
                    if ban_info.get('note', None) and w.banner:
                        w.banner += ' (%s)' % ban_info['note']
                    w.use_big_modbuttons = True
                    if getattr(w, "author", None) and w.author._spam:
                        w.show_spam = "author"

                    if c.user == w.author and c.user._spam:
                        w.show_spam = False
                        w._spam = False
                        w.use_big_modbuttons = False

                elif (getattr(item, 'reported', 0) > 0
                      and (not getattr(item, 'ignore_reports', False) or
                           c.user_is_admin)):
                    w.show_reports = True
                    w.use_big_modbuttons = True

                    # report_count isn't used in any template, but add it to
                    # the Wrapped so it's pulled into the render cache key in
                    # instances when reported will be used in the template
                    w.report_count = item.reported

            w.approval_checkmark = None
            if w.can_ban:
                verdict = getattr(w, "verdict", None)
                if verdict in ('admin-approved', 'mod-approved'):
                    approver = None
                    approval_time = None
                    baninfo = getattr(w, "ban_info", None)
                    if baninfo:
                        approver = baninfo.get("unbanner", None)
                        approval_time = baninfo.get("unbanned_at", None)

                    approver = approver or _("a moderator")

                    if approval_time:
                        text = _("approved by %(who)s at %(when)s") % {
                                    "who": approver,
                                    "when": long_datetime(approval_time)}
                    else:
                        text = _("approved by %s") % approver
                    w.approval_checkmark = text

            hooks.get_hook("builder.wrap_items").call(item=item, wrapped=w)

        # recache the user object: it may be None if user is not logged in,
        # whereas now we are happy to have the UnloggedUser object
        user = c.user
        for cls in types.keys():
            cls.add_props(user, types[cls])

        return wrapped

    def get_items(self):
        raise NotImplementedError

    def convert_items(self, items):
        """Convert a list of items to the desired output format"""
        if self.prewrap_fn:
            items = [self.prewrap_fn(i) for i in items]

        if self.wrap:
            items = self.wrap_items(items)
        else:
            # make a copy of items so the converted items can be mutated without
            # changing the original items
            items = items[:]
        return items

    def valid_after(self, after):
        """
        Return whether `after` could ever be shown to the user.

        Necessary because an attacker could use info about the presence
        and position of `after` within a listing to leak info about `after`s
        that the attacker could not normally access.
        """
        w = self.convert_items((after,))[0]
        return not self.must_skip(w)

    def item_iter(self, a):
        """Iterates over the items returned by get_items"""
        raise NotImplementedError

    def must_skip(self, item):
        """whether or not to skip any item regardless of whether the builder
        was contructed with skip=true"""
        user = c.user if c.user_is_loggedin else None

        if hasattr(item, "promoted") and item.promoted is not None:
            return False

        # can_view_slow only exists for Messages, but checking was_comment
        # is also necessary because items may also be comments that are being
        # viewed from the inbox page where their render class is overridden.
        # This check needs to be done before looking at whether they can view
        # the subreddit, or modmail to/from private subreddits that the user
        # doesn't have access to will be skipped.
        if hasattr(item, 'can_view_slow') and not item.was_comment:
            return not item.can_view_slow()

        if hasattr(item, 'subreddit') and not item.subreddit.can_view(user):
            return True

    def _is_controversial(self, wrapped):
        """Determine if an item meets all criteria to display as controversial."""

        # A sample-size threshold before posts can be considered controversial
        num_votes = wrapped.upvotes + wrapped.downvotes
        if num_votes < g.live_config['cflag_min_votes']:
            return False

        # If an item falls within a boundary of upvote ratios, it's controversial
        # e.g. 0.4 < x < 0.6
        lower = g.live_config['cflag_lower_bound']
        upper = g.live_config['cflag_upper_bound']
        if lower <= wrapped.upvote_ratio <= upper:
            return True

        return False


class QueryBuilder(Builder):
    def __init__(self, query, skip=False, num=None, sr_detail=None, count=0,
                 after=None, reverse=False, **kw):
        self.query = query
        self.skip = skip
        self.num = num
        self.sr_detail = sr_detail
        self.start_count = count or 0
        self.after = after
        self.reverse = reverse
        Builder.__init__(self, **kw)

    def __repr__(self):
        return "<%s(%r)>" % (self.__class__.__name__, self.query)

    def item_iter(self, a):
        """Iterates over the items returned by get_items"""
        for i in a[0]:
            yield i

    def init_query(self):
        q = self.query

        if self.reverse:
            q._reverse()

        q._data = True
        self.orig_rules = deepcopy(q._rules)
        if self.after:
            q._after(self.after)

    def fetch_more(self, last_item, num_have):
        done = False
        q = self.query
        if self.num:
            num_need = self.num - num_have
            if num_need <= 0:
                #will cause the loop below to break
                return True, None
            else:
                #q = self.query
                #check last_item if we have a num because we may need to iterate
                if last_item:
                    q._rules = deepcopy(self.orig_rules)
                    q._after(last_item)
                    last_item = None
                q._limit = max(int(num_need * EXTRA_FACTOR), self.num // 2, 1)
        else:
            done = True
        new_items = list(q)

        return done, new_items

    def get_items(self):
        self.init_query()

        num_have = 0
        done = False
        items = []
        count = self.start_count
        fetch_after = None
        loopcount = 0
        stopped_early = False

        while not done:
            done, fetched_items = self.fetch_more(fetch_after, num_have)

            #log loop
            loopcount += 1
            if loopcount == 20:
                done = True
                stopped_early = True

            #no results, we're done
            if not fetched_items:
                break

            #if fewer results than we wanted, we're done
            elif self.num and len(fetched_items) < self.num - num_have:
                done = True

            # Wrap the fetched items if necessary
            new_items = self.convert_items(fetched_items)

            #skip and count
            while new_items and (not self.num or num_have < self.num):
                i = new_items.pop(0)

                if not (self.must_skip(i) or
                        self.skip and not self.keep_item(i)):
                    items.append(i)
                    num_have += 1
                    count = count - 1 if self.reverse else count + 1
                    if self.wrap:
                        i.num = count

            fetch_after = fetched_items[-1]

        # Is there a next page or not?
        have_next = True
        if self.num and num_have < self.num and not stopped_early:
            have_next = False

        if getattr(self, "sr_detail", False) and c.render_style in API_TYPES:
            items_by_subreddit = defaultdict(list)
            for item in items:
                if isinstance(item.lookups[0], Link):
                    items_by_subreddit[item.subreddit].append(item)

            srs = items_by_subreddit.keys()
            sr_dicts = get_trimmed_sr_dicts(srs, c.user)

            for sr, sr_items in items_by_subreddit.iteritems():
                sr_detail = sr_dicts[sr._id]
                for item in sr_items:
                    item.sr_detail = sr_detail

        # Make sure first_item and last_item refer to things in items
        # NOTE: could retrieve incorrect item if there were items with
        # duplicate _id
        first_item = None
        last_item = None
        if items:
            if self.start_count > 0:
                first_item = items[0]
            last_item = items[-1]

        if self.reverse:
            items.reverse()
            last_item, first_item = first_item, have_next and last_item
            before_count = count
            after_count = self.start_count - 1
        else:
            last_item = have_next and last_item
            before_count = self.start_count + 1
            after_count = count

        #listing is expecting (things, prev, next, bcount, acount)
        return (items,
                first_item,
                last_item,
                before_count,
                after_count)

class IDBuilder(QueryBuilder):
    def thing_lookup(self, names):
        return Thing._by_fullname(names, data=True, return_dict=False,
                                  stale=self.stale)

    def init_query(self):
        names = list(tup(self.query))

        after = self.after._fullname if self.after else None

        self.names = self._get_after(names,
                                     after,
                                     self.reverse)

    @staticmethod
    def _get_after(l, after, reverse):
        names = list(l)

        if reverse:
            names.reverse()

        if after:
            try:
                i = names.index(after)
            except ValueError:
                names = ()
            else:
                names = names[i + 1:]

        return names

    def fetch_more(self, last_item, num_have):
        done = False
        names = self.names
        if self.num:
            num_need = self.num - num_have
            if num_need <= 0:
                return True, None
            else:
                if last_item:
                    last_item = None
                slice_size = max(int(num_need * EXTRA_FACTOR), self.num // 2, 1)
        else:
            slice_size = len(names)
            done = True

        self.names, new_names = names[slice_size:], names[:slice_size]
        new_items = self.thing_lookup(new_names)
        return done, new_items


class ActionBuilder(IDBuilder):
    def init_query(self):
        self.actions = {}
        ids = []
        for id, date, action in self.query:
            ids.append(id)
            self.actions[id] = action
        self.query = ids

        super(ActionBuilder, self).init_query()

    def thing_lookup(self, names):
        items = super(ActionBuilder, self).thing_lookup(names)

        for item in items:
            if item._fullname in self.actions:
                item.action_type = self.actions[item._fullname]
        return items


class CampaignBuilder(IDBuilder):
    """Build on a list of PromoTuples."""
    @staticmethod
    def _get_after(promo_tuples, after, reverse):
        promo_tuples = list(promo_tuples)

        if not after:
            return promo_tuples

        if reverse:
            promo_tuples.reverse()

        fullname_to_index = {pt.link: i for i, pt in enumerate(promo_tuples)}
        try:
            i = fullname_to_index[after]
        except KeyError:
            promo_tuples = ()
        else:
            promo_tuples = promo_tuples[i + 1:]

        return promo_tuples

    def thing_lookup(self, tuples):
        links = Link._by_fullname([t.link for t in tuples], data=True,
                                  return_dict=True, stale=self.stale)

        return [Storage({'thing': links[t.link],
                         '_id': links[t.link]._id,
                         '_fullname': links[t.link]._fullname,
                         'weight': t.weight,
                         'campaign': t.campaign}) for t in tuples]

    def wrap_items(self, items):
        links = [i.thing for i in items]
        wrapped = IDBuilder.wrap_items(self, links)
        by_link = defaultdict(list)
        for w in wrapped:
            by_link[w._fullname].append(w)

        ret = []
        for i in items:
            w = by_link[i.thing._fullname].pop()
            w.campaign = i.campaign
            w.weight = i.weight
            ret.append(w)

        return ret

    def valid_after(self, after):
        # CampaignBuilder has special wrapping logic to operate on
        # PromoTuples and PromoCampaigns. `after` is just a Link, so bypass
        # the special wrapping logic and use the base class.
        if self.prewrap_fn:
            after = self.prewrap_fn(after)
        if self.wrap:
            after = Builder.wrap_items(self, (after,))[0]
        return not self.must_skip(after)


class ModActionBuilder(QueryBuilder):
    def wrap_items(self, items):
        wrapped = []
        by_render_class = defaultdict(list)

        for item in items:
            w = self.wrap(item)
            wrapped.append(w)
            w.fullname = item._fullname
            by_render_class[w.render_class].append(w)

        for render_class, _items in by_render_class.iteritems():
            render_class.add_props(c.user, _items)

        return wrapped


class SimpleBuilder(IDBuilder):
    def thing_lookup(self, names):
        return names

    def init_query(self):
        items = list(tup(self.query))

        if self.reverse:
            items.reverse()

        if self.after:
            for i, item in enumerate(items):
                if item._id == self.after:
                    self.names = items[i + 1:]
                    break
            else:
                self.names = ()
        else:
            self.names = items

    def get_items(self):
        items, prev_item, next_item, bcount, acount = IDBuilder.get_items(self)
        prev_item_id = prev_item._id if prev_item else None
        next_item_id = next_item._id if next_item else None
        return (items, prev_item_id, next_item_id, bcount, acount)


class SearchBuilder(IDBuilder):
    def __init__(self, query, skip_deleted_authors=True, **kw):
        self.skip_deleted_authors = skip_deleted_authors
        IDBuilder.__init__(self, query, **kw)

    def init_query(self):
        self.skip = True

        self.start_time = time.time()

        self.results = self.query.run()
        names = list(self.results.docs)
        self.total_num = self.results.hits
        self.subreddit_facets = self.results.subreddit_facets

        after = self.after._fullname if self.after else None

        self.names = self._get_after(names,
                                     after,
                                     self.reverse)

    def keep_item(self, item):
        # doesn't use the default keep_item because we want to keep
        # things that were voted on, even if they've chosen to hide
        # them in normal listings
        user = c.user if c.user_is_loggedin else None

        if item._spam or item._deleted:
            return False
        # If checking (wrapped) links, filter out banned subreddits
        elif hasattr(item, 'subreddit') and item.subreddit.spammy():
            return False
        elif (hasattr(item, 'subreddit') and
              not c.user_is_admin and
              not item.subreddit.is_exposed(user)):
            return False
        elif (self.skip_deleted_authors and
              getattr(item, "author", None) and item.author._deleted):
            return False
        elif isinstance(item.lookups[0], Subreddit) and not item.is_exposed(user):
            return False

        # show NSFW to API and RSS users unless obey_over18=true
        is_api_or_rss = (c.render_style in API_TYPES
                         or c.render_style in RSS_TYPES)
        if is_api_or_rss:
            include_over18 = not c.obey_over18 or c.over18
        elif feature.is_enabled('safe_search'):
            include_over18 = c.over18
        else:
            include_over18 = True

        is_nsfw = (item.over_18 or
            (hasattr(item, 'subreddit') and item.subreddit.over_18))
        if is_nsfw and not include_over18:
            return False

        return True


class WikiRevisionBuilder(QueryBuilder):
    show_extended = True

    def __init__(self, revisions, user=None, sr=None, page=None, **kw):
        self.user = user
        self.sr = sr
        self.page = page
        QueryBuilder.__init__(self, revisions, **kw)

    def wrap_items(self, items):
        from r2.lib.validator.wiki import this_may_revise
        types = {}
        wrapped = []
        extended = self.show_extended and c.is_wiki_mod
        extended = extended and this_may_revise(self.page)
        for item in items:
            w = self.wrap(item)
            w.show_extended = extended
            w.show_compare = self.show_extended
            types.setdefault(w.render_class, []).append(w)
            wrapped.append(w)

        user = c.user
        for cls in types.keys():
            cls.add_props(user, types[cls])

        return wrapped

    def must_skip(self, item):
        return item.admin_deleted and not c.user_is_admin

    def keep_item(self, item):
        from r2.lib.validator.wiki import may_view
        return ((not item.is_hidden) and
                may_view(self.sr, self.user, item.wikipage))

class WikiRecentRevisionBuilder(WikiRevisionBuilder):
    show_extended = False

    def must_skip(self, item):
        if WikiRevisionBuilder.must_skip(self, item):
            return True
        item_age = datetime.datetime.now(g.tz) - item.date
        return item_age.days >= wiki.WIKI_RECENT_DAYS


CommentTuple = namedtuple("CommentTuple",
    ["comment_id", "depth", "parent_id", "num_children", "child_ids"])


MissingChildrenTuple = namedtuple("MissingChildrenTuple",
    ["num_children", "child_ids"])


class CommentOrdererBase(object):
    def __init__(self, link, sort, max_comments, max_depth, timer):
        self.link = link
        self.sort = sort
        self.rev_sort = isinstance(sort, operators.desc)
        self.max_comments = max_comments
        self.max_depth = max_depth
        self.timer = timer

    def get_comment_order(self):
        """Return a list of CommentTuples in tree insertion order.

        Also add a MissingChildrenTuple to the end of the list if there
        are missing root level comments.

        """

        with g.stats.get_timer('comment_tree.get.1') as comment_tree_timer:
            comment_tree = CommentTree.by_link(self.link, comment_tree_timer)
            sort_name = self.sort.col
            sorter = get_comment_scores(
                self.link, sort_name, comment_tree.cids, comment_tree_timer)
            comment_tree_timer.intermediate('get_scores')

        if isinstance(self.sort, operators.shuffled):
            # randomize the scores of top level comments
            top_level_ids = comment_tree.tree.get(None, [])
            top_level_scores = [
                sorter[comment_id] for comment_id in top_level_ids]
            shuffle(top_level_scores)
            for i, comment_id in enumerate(top_level_ids):
                sorter[comment_id] = top_level_scores[i]

        self.timer.intermediate("load_storage")

        comment_tree = self.modify_comment_tree(comment_tree)
        self.timer.intermediate("modify_comment_tree")

        initial_candidates, offset_depth = self.get_initial_candidates(comment_tree)

        comment_tuples = self.get_initial_comment_list(comment_tree)
        if comment_tuples:
            # some comments have bypassed the sorting/inserting process, remove
            # them from `initial_candidates` so they won't be inserted again
            comment_tuple_ids = {
                comment_tuple.comment_id for comment_tuple in comment_tuples}
            initial_candidates = [
                comment_id for comment_id in initial_candidates
                if comment_id not in comment_tuple_ids
            ]

        candidates = []
        self.update_candidates(candidates, sorter, initial_candidates)
        self.timer.intermediate("pick_candidates")

        # choose which comments to show
        while candidates and len(comment_tuples) < self.max_comments:
            sort_val, comment_id = heapq.heappop(candidates)
            if comment_id not in comment_tree.cids:
                continue

            comment_depth = comment_tree.depth[comment_id] - offset_depth
            if comment_depth >= self.max_depth:
                continue

            child_ids = comment_tree.tree.get(comment_id, [])

            comment_tuples.append(CommentTuple(
                comment_id=comment_id,
                depth=comment_depth,
                parent_id=comment_tree.parents[comment_id],
                num_children=comment_tree.num_children[comment_id],
                child_ids=child_ids,
            ))

            child_depth = comment_depth + 1
            if child_depth < self.max_depth:
                self.update_candidates(candidates, sorter, child_ids)

        self.timer.intermediate("pick_comments")

        # add all not-selected top level comments to the comment_tuples list
        # so we can make MoreChildren for them later
        top_level_not_visible = {
            comment_id for sort_val, comment_id in candidates
            if comment_tree.depth.get(comment_id, 0) - offset_depth == 0
        }

        if top_level_not_visible:
            num_children_not_visible = sum(
                1 + comment_tree.num_children[comment_id]
                for comment_id in top_level_not_visible
            )
            comment_tuples.append(MissingChildrenTuple(
                num_children=num_children_not_visible,
                child_ids=top_level_not_visible,
            ))

        self.timer.intermediate("handle_morechildren")
        return comment_tuples

    def modify_comment_tree(self, comment_tree):
        """Potentially rewrite parts of comment_tree."""
        return comment_tree

    def get_initial_candidates(self, comment_tree):
        """Return comments to start building the tree from and offset_depth."""
        raise NotImplementedError

    def get_initial_comment_list(self, comment_tree):
        """Return the starting list of CommentTuples, possibly inserting some
        and bypassing the regular sorting/inserting process."""
        return []

    def update_candidates(self, candidates, sorter, to_add=None):
        for comment in (comment for comment in tup(to_add)
                                if comment in sorter):
            sort_val = -sorter[comment] if self.rev_sort else sorter[comment]
            heapq.heappush(candidates, (sort_val, comment))


SORT_OPERATOR_BY_NAME = {
    "new": operators.desc('_date'),
    "old": operators.asc('_date'),
    "controversial": operators.desc('_controversy'),
    "confidence": operators.desc('_confidence'),
    "qa": operators.desc('_qa'),
    "hot": operators.desc('_hot'),
    "top": operators.desc('_score'),
    "random": operators.shuffled('_confidence'),
}


class CommentOrderer(CommentOrdererBase):
    def get_initial_candidates(self, comment_tree):
        """Build the tree starting from all root level comments."""
        initial_candidates = comment_tree.tree.get(None, [])
        if initial_candidates:
            offset_depth = min(comment_tree.depth[comment_id]
                for comment_id in initial_candidates)
        else:
            offset_depth = 0
        return initial_candidates, offset_depth

    def get_initial_comment_list(self, comment_tree):
        """Promote the sticky comment, if any."""
        comment_tuples = []

        if self.link.sticky_comment_id:
            root_level_comments = comment_tree.tree.get(None, [])
            sticky_comment_id = self.link.sticky_comment_id
            if sticky_comment_id in root_level_comments:
                comment_tuples.append(CommentTuple(
                    comment_id=sticky_comment_id,
                    depth=0,
                    parent_id=None,
                    num_children=comment_tree.num_children[sticky_comment_id],
                    child_ids=comment_tree.tree.get(sticky_comment_id, []),
                ))
            else:
                g.log.warning("Non-top-level sticky comment detected on "
                              "link %r.", self.link)
        return comment_tuples

    def cache_key(self):
        key = "order:{link}_{operator}{column}".format(
            link=self.link._id36,
            operator=self.sort.__class__.__name__,
            column=self.sort.col,
        )
        return key

    @classmethod
    def write_cache(cls, link, sort, timer):
        comment_orderer = cls(link, sort,
            max_comments=g.max_comments_gold,
            max_depth=MAX_RECURSION,
            timer=timer,
        )
        comment_tuples = comment_orderer._get_comment_order()

        key = comment_orderer.cache_key()
        existing_tuples = g.permacache.get(key) or []

        if comment_tuples != existing_tuples:
            # don't write cache if the order hasn't changed
            g.permacache.set(key, comment_tuples)

    def should_read_cache(self):
        if self.link.precomputed_sorts:
            precomputed_sorts = [
                SORT_OPERATOR_BY_NAME[sort_name]
                for sort_name in self.link.precomputed_sorts
                if sort_name in SORT_OPERATOR_BY_NAME
            ]
            return self.sort in precomputed_sorts
        else:
            return False

    def read_cache(self):
        key = self.cache_key()
        comment_tuples = g.permacache.get(key) or []
        self.timer.intermediate("read_precomputed")

        # precomputed order might have returned more than max_comments. before
        # dealing with that we need to preserve the MissingChildrenTuple for
        # missing root level comments
        if comment_tuples and isinstance(comment_tuples[-1], MissingChildrenTuple):
            mct = comment_tuples.pop(-1)
            top_level_not_visible = mct.child_ids
            num_children_not_visible = mct.num_children
        else:
            top_level_not_visible = set()
            num_children_not_visible = 0

        # precomputed order uses the default max_depth. filter the list
        # if we need a different max_depth. NOTE: we may end up with fewer
        # comments than were requested.
        if self.max_depth < MAX_RECURSION:
            comment_tuples = [
                comment_tuple for comment_tuple in comment_tuples
                if comment_tuple.depth < self.max_depth
            ]

        if len(comment_tuples) > self.max_comments:
            top_level_not_visible.update({
                comment_tuple.comment_id
                for comment_tuple in comment_tuples[self.max_comments:]
                if comment_tuple.depth == 0
            })
            num_children_not_visible += sum(
                1 + comment_tuple.num_children
                for comment_tuple in comment_tuples[self.max_comments:]
                if comment_tuple.depth == 0
            )
            comment_tuples = comment_tuples[:self.max_comments]

        if top_level_not_visible:
            comment_tuples.append(MissingChildrenTuple(
                num_children=num_children_not_visible,
                child_ids=top_level_not_visible,
            ))

        self.timer.intermediate("prune_precomputed")

        return comment_tuples

    def _get_comment_order(self):
        return CommentOrdererBase.get_comment_order(self)

    def get_comment_order(self):
        num_comments = self.link.num_comments
        if num_comments == 0:
            bucket = "0"
        elif num_comments >= 100:
            bucket = "100_plus"
        else:
            bucket_start = num_comments / 5 * 5
            bucket_end = bucket_start + 5
            bucket = "%s_%s" % (bucket_start, bucket_end)

        # record the number of comments on this link so we can get an idea of
        # what value to use for 'precomputed_comment_sort_min_comments'
        g.stats.simple_event("CommentOrderer.num_comments.%s" % bucket)

        if self.link.num_comments <= 0:
            return []

        if self.should_read_cache():
            with g.stats.get_timer("CommentOrderer.read_cache") as timer:
                return self.read_cache()
        else:
            if bucket == "100_plus":
                for sort_name, operator in SORT_OPERATOR_BY_NAME.iteritems():
                    if operator == self.sort:
                        break
                else:
                    sort_name = "None"
                g.stats.simple_event("CommentOrderer.100_plus_sort.%s" % sort_name)

            timer_name = "CommentOrderer.by_num_comments.%s" % bucket
            with g.stats.get_timer(timer_name) as timer:
                return self._get_comment_order()


class QACommentOrderer(CommentOrderer):
    def _get_comment_order(self):
        """Filter out the comments we don't want to show in QA sort.

        QA sort only displays comments that are:
        1. Top-level
        2. Responses from the OP(s)
        3. Within one level of an OP reply
        4. Distinguished

        All ancestors of comments meeting the above rules will also be shown.
        This ensures the question responded to by OP is shown.

        """

        comment_tuples = CommentOrdererBase.get_comment_order(self)
        if not comment_tuples:
            return comment_tuples
        elif isinstance(comment_tuples[-1], MissingChildrenTuple):
            missing_children_tuple = comment_tuples.pop()
        else:
            missing_children_tuple = None

        special_responder_ids = self.link.responder_ids

        # unfortunately we need to look up all the Comments for QA
        comment_ids = {ct.comment_id for ct in comment_tuples}
        comments_by_id = Comment._byID(comment_ids, data=True)

        # figure out which comments will be kept (all others are discarded)
        kept_comment_ids = set()
        for comment_tuple in comment_tuples:
            if comment_tuple.depth == 0:
                kept_comment_ids.add(comment_tuple.comment_id)
                continue

            comment = comments_by_id[comment_tuple.comment_id]
            parent = comments_by_id[comment.parent_id] if comment.parent_id else None

            if comment.author_id in special_responder_ids:
                kept_comment_ids.add(comment_tuple.comment_id)
                continue

            if parent and parent.author_id in special_responder_ids:
                kept_comment_ids.add(comment_tuple.comment_id)
                continue

            if hasattr(comment, "distinguished") and comment.distinguished != "no":
                kept_comment_ids.add(comment_tuple.comment_id)
                continue

        # add all ancestors to kept_comment_ids
        for comment_id in sorted(kept_comment_ids):
            # sort the comments so we start with the most root level comments
            comment = comments_by_id[comment_id]
            parent_id = comment.parent_id

            counter = 0
            while (parent_id and
                        parent_id not in kept_comment_ids and
                        counter < g.max_comment_parent_walk):
                kept_comment_ids.add(parent_id)
                counter += 1

                comment = comments_by_id[parent_id]
                parent_id = comment.parent_id

        # remove all comment tuples that aren't in kept_comment_ids
        comment_tuples = [comment_tuple for comment_tuple in comment_tuples
            if comment_tuple.comment_id in kept_comment_ids
        ]

        if missing_children_tuple:
            comment_tuples.append(missing_children_tuple)

        return comment_tuples


def get_active_sort_orders_for_link(link):
    # only activate precomputed sorts for links with enough comments.
    # (value of 0 means not active for any value of link.num_comments)
    min_comments = g.live_config['precomputed_comment_sort_min_comments']
    if min_comments <= 0 or link.num_comments < min_comments:
        return set()

    active_sorts = set(g.live_config['precomputed_comment_sorts'])
    if g.live_config['precomputed_comment_suggested_sort']:
        suggested_sort = link.sort_if_suggested()
        if suggested_sort:
            active_sorts.add(suggested_sort)

    return active_sorts


def write_comment_orders(link):
    # we don't really care about getting detailed timings here, the entire
    # process will be timed by the caller
    timer = SimpleSillyStub()

    precomputed_sorts = set()
    for sort_name in get_active_sort_orders_for_link(link):
        sort = SORT_OPERATOR_BY_NAME.get(sort_name)
        if not sort:
            continue

        if sort_name == "qa":
            QACommentOrderer.write_cache(link, sort, timer)
        else:
            CommentOrderer.write_cache(link, sort, timer)

        precomputed_sorts.add(sort_name)

    if precomputed_sorts:
        g.stats.simple_event("CommentOrderer.write_comment_orders.write")
    else:
        g.stats.simple_event("CommentOrderer.write_comment_orders.noop")

    # replace empty set with None to match the Link._defaults value
    link.precomputed_sorts = precomputed_sorts or None
    link._commit()


class PermalinkCommentOrderer(CommentOrdererBase):
    def __init__(self, link, sort, max_comments, max_depth, timer, comment,
                 context):
        CommentOrdererBase.__init__(
            self, link, sort, max_comments, max_depth, timer)
        self.comment = comment
        self.context = context

    @classmethod
    def get_path_to_comment(cls, comment, context, comment_tree):
        """Return the path back to top level from comment.

        Restrict the path to a maximum of `context` levels deep."""

        if comment._id not in comment_tree.cids:
            # the comment isn't in the tree
            raise InconsistentCommentTreeError

        comment_id = comment._id
        path = []
        while comment_id and len(path) <= context:
            path.append(comment_id)
            try:
                comment_id = comment_tree.parents[comment_id]
            except KeyError:
                # the comment's parent is missing from the tree. this might
                # just mean that the child was added to the tree first and
                # the tree will be correct when the parent is added.
                raise InconsistentCommentTreeError

        # reverse the list so the first element is the most root level comment
        path.reverse()
        return path

    def modify_comment_tree(self, comment_tree):
        path = self.get_path_to_comment(
            self.comment, self.context, comment_tree)

        # work through the path in reverse starting with the requested comment
        for comment_id in reversed(path):
            # rewrite parent's tree so it leads only to the requested comment
            parent_id = comment_tree.parents[comment_id]
            comment_tree.tree[parent_id] = [comment_id]

            # rewrite parent's num_children to count only this branch
            if parent_id is not None:
                branch_num_children = comment_tree.num_children[comment_id]
                comment_tree.num_children[parent_id] = branch_num_children + 1

        return comment_tree

    def get_initial_candidates(self, comment_tree):
        """Start the tree from the first ancestor of requested comment."""
        path = self.get_path_to_comment(
            self.comment, self.context, comment_tree)

        # get_path_to_comment returns path ordered from ancestor to
        # selected comment
        root_comment = path[0]
        initial_candidates = [root_comment]
        offset_depth = comment_tree.depth[root_comment]
        return initial_candidates, offset_depth


class ChildrenCommentOrderer(CommentOrdererBase):
    def __init__(self, link, sort, max_comments, max_depth, timer, children):
        CommentOrdererBase.__init__(
            self, link, sort, max_comments, max_depth, timer)
        self.children = children

    def get_initial_candidates(self, comment_tree):
        """Start the tree from the requested children."""

        children = [
            comment_id for comment_id in self.children
            if comment_id in comment_tree.depth
        ]

        if children:
            children_depth = min(
                comment_tree.depth[comment_id] for comment_id in children)

            children = [
                comment_id for comment_id in children
                if comment_tree.depth[comment_id] == children_depth
            ]

        initial_candidates = children

        # BUG: current viewing depth isn't considered, so requesting children
        # of a deep comment can return nothing. the fix is to send the current
        # offset_depth along with the MoreChildren request
        offset_depth = 0

        return initial_candidates, offset_depth


def make_child_listing():
    l = Listing(builder=None, nextprev=None)
    l.things = []
    child = Wrapped(l)
    return child


def add_to_child_listing(parent, child_thing):
    if not hasattr(parent, 'child'):
        child = make_child_listing()
        child.parent_name = "deleted" if parent.deleted else parent._fullname
        parent.child = child

    parent.child.things.append(child_thing)


class CommentBuilder(Builder):
    """Build (lookup and wrap) comments for display."""
    def __init__(self, link, sort, comment=None, children=None, context=None,
                 load_more=True, continue_this_thread=True,
                 max_depth=MAX_RECURSION, edits_visible=True, num=None,
                 show_deleted=False, **kw):
        self.link = link
        self.sort = sort

        # arguments for permalink mode
        self.comment = comment
        self.context = context or 0

        # argument for morechildren mode
        self.children = children

        # QA mode only activates for the full comments view
        self.in_qa_mode = sort.col == '_qa' and not (comment or children)

        self.load_more = load_more
        self.max_depth = max_depth
        self.show_deleted = show_deleted or c.user_is_admin

        # uncollapse everything in QA mode because the sorter will prune
        self.uncollapse_all = c.user_is_admin or self.in_qa_mode
        self.edits_visible = edits_visible
        self.num = num
        self.continue_this_thread = continue_this_thread

        self.comments = None
        Builder.__init__(self, **kw)

    def get_items(self):
        if self.comments is None:
            self._get_comments()
        return self._make_wrapped_tree()

    def _get_comments(self):
        self.load_comment_order()
        comment_ids = {
            comment_tuple.comment_id
            for comment_tuple in self.ordered_comment_tuples
        }
        self.comments = Comment._byID(
            comment_ids, data=True, return_dict=False, stale=self.stale)
        self.timer.intermediate("lookup_comments")

    def load_comment_order(self):
        self.timer = g.stats.get_timer("CommentBuilder.get_items")
        self.timer.start()

        if self.comment:
            orderer = PermalinkCommentOrderer(
                self.link, self.sort, self.num, self.max_depth, self.timer,
                self.comment, self.context)

            try:
                comment_tuples = orderer.get_comment_order()
            except InconsistentCommentTreeError:
                g.log.error("Hack - self.comment (%d) not in depth. Defocusing..."
                            % self.comment._id)
                self.comment = None
                orderer = CommentOrderer(
                    self.link, self.sort, self.num, self.max_depth, self.timer)
                comment_tuples = orderer.get_comment_order()

        elif self.children:
            orderer = ChildrenCommentOrderer(
                self.link, self.sort, self.num, self.max_depth, self.timer,
                self.children)
            comment_tuples = orderer.get_comment_order()
        elif self.in_qa_mode:
            orderer = QACommentOrderer(
                self.link, self.sort, self.num, self.max_depth, self.timer)
            comment_tuples = orderer.get_comment_order()
        else:
            orderer = CommentOrderer(
                self.link, self.sort, self.num, self.max_depth, self.timer)
            comment_tuples = orderer.get_comment_order()

        if (comment_tuples and
                isinstance(comment_tuples[-1], MissingChildrenTuple)):
            mct = comment_tuples.pop(-1)
            missing_root_comments = mct.child_ids
            missing_root_count = mct.num_children
        else:
            missing_root_comments = set()
            missing_root_count = 0

        self.ordered_comment_tuples = comment_tuples
        self.missing_root_comments = missing_root_comments
        self.missing_root_count = missing_root_count

    def keep_item(self, item):
        if not self.show_deleted:
            if item.deleted and not item.num_children:
                return False
        return item.keep_item(item)

    def _make_wrapped_tree(self):
        timer = self.timer
        ordered_comment_tuples = self.ordered_comment_tuples
        missing_root_comments = self.missing_root_comments
        missing_root_count = self.missing_root_count

        if not ordered_comment_tuples:
            self.timer.stop()
            return []

        comment_order = [
            comment_tuple.comment_id for comment_tuple in ordered_comment_tuples
        ]

        wrapped = self.make_wrapped_items(ordered_comment_tuples)
        timer.intermediate("wrap_comments")

        wrapped_by_id = {
            comment._id: comment for comment in wrapped}
        self.uncollapse_special_comments(wrapped_by_id)

        redacted_ids = set()

        visible_ids = {
            comment._id for comment in wrapped
            if not getattr(comment, 'hidden', False)
        }

        final = []
        for comment_id in comment_order:
            comment = wrapped_by_id[comment_id]

            if getattr(comment, 'hidden', False):
                continue

            if not self.keep_item(comment):
                redacted_ids.add(comment_id)
                continue

            # add the comment as a child of its parent or to the top level of
            # the tree if it has no parent or the parent isn't in the listing
            parent = wrapped_by_id.get(comment.parent_id)
            if parent:
                add_to_child_listing(parent, comment)
            else:
                final.append(comment)

        self.timer.intermediate("build_comments")

        if not self.load_more:
            timer.stop()
            return final

        # add MoreRecursion and MoreChildren last so they'll be the last item in
        # a comment's child listing
        for comment in wrapped:
            if (self.continue_this_thread and
                    comment.depth == self.max_depth - 1 and
                    comment.num_children > 0):
                # only comments with depth < max_depth are visible
                # if this comment is as deep as we can go and has children then
                # we need to insert a MoreRecursion child
                mr = MoreRecursion(self.link, depth=0, parent_id=comment._id)
                w = Wrapped(mr)
                add_to_child_listing(comment, w)
            elif comment.depth < self.max_depth - 1:
                missing_child_ids = (
                    set(comment.child_ids) - visible_ids - redacted_ids
                )
                if missing_child_ids:
                    missing_depth = comment.depth + 1
                    mc = MoreChildren(self.link, self.sort, depth=missing_depth,
                            parent_id=comment._id)
                    w = Wrapped(mc)
                    visible_count = sum(
                        1 + wrapped_by_id[child_id].num_children
                        for child_id in comment.child_ids
                        if child_id in visible_ids
                    )
                    w.count = comment.num_children - visible_count
                    w.children.extend(missing_child_ids)
                    add_to_child_listing(comment, w)

        if missing_root_comments:
            mc = MoreChildren(self.link, self.sort, depth=0, parent_id=None)
            w = Wrapped(mc)
            w.count = missing_root_count
            w.children.extend(missing_root_comments)
            final.append(w)

        self.timer.intermediate("build_morechildren")
        self.timer.stop()
        return final

    def make_wrapped_items(self, comment_tuples):
        wrapped = Builder.wrap_items(self, self.comments)
        wrapped_by_id = {comment._id: comment for comment in wrapped}

        for comment_tuple in comment_tuples:
            comment = wrapped_by_id[comment_tuple.comment_id]
            comment.num_children = comment_tuple.num_children
            comment.child_ids = comment_tuple.child_ids
            comment.depth = comment_tuple.depth
            comment.edits_visible = self.edits_visible

            if self.children:
                # rewrite the parent links to use anchor tags because the parent
                # is on the page but wasn't included in this batch
                if comment.parent_id:
                    comment.parent_permalink = '#' + to36(comment.parent_id)

        return wrapped

    def uncollapse_special_comments(self, wrapped_by_id):
        """Undo collapsing for special comments.

        The builder may have set `collapsed` and `hidden` attributes for
        comments that we want to ensure are shown.

        """

        if self.uncollapse_all:
            dont_collapse = set(wrapped_by_id.keys())
        elif self.comment:
            dont_collapse = set([self.comment._id])
            parent_id = self.comment.parent_id
            while parent_id:
                dont_collapse.add(parent_id)
                if parent_id in wrapped_by_id:
                    parent_id = wrapped_by_id[parent_id].parent_id
                else:
                    parent_id = None
        elif self.children:
            dont_collapse = set(self.children)
        else:
            dont_collapse = set()

        # we only care about preventing collapse of wrapped comments
        dont_collapse &= set(wrapped_by_id.keys())

        maybe_collapse = set(wrapped_by_id.keys()) - dont_collapse

        for comment_id in maybe_collapse:
            comment = wrapped_by_id[comment_id]
            if comment.distinguished and comment.distinguished != "no":
                dont_collapse.add(comment_id)

        maybe_collapse -= dont_collapse

        # ensure all ancestors of dont_collapse comments are not collapsed
        if maybe_collapse:
            for comment_id in sorted(dont_collapse):
                # sort comments so we start with the most root level comments
                comment = wrapped_by_id[comment_id]
                parent_id = comment.parent_id

                counter = 0
                while (parent_id and
                            parent_id not in dont_collapse and
                            parent_id in wrapped_by_id and
                            counter < g.max_comment_parent_walk):
                    dont_collapse.add(parent_id)
                    counter += 1

                    comment = wrapped_by_id[parent_id]
                    parent_id = comment.parent_id

        for comment_id in dont_collapse:
            comment = wrapped_by_id[comment_id]
            if comment.collapsed:
                comment.collapsed = False
                comment.hidden = False

    def item_iter(self, a):
        for i in a:
            yield i
            if hasattr(i, 'child'):
                for j in self.item_iter(i.child.things):
                    yield j


class MessageBuilder(Builder):
    def __init__(self, skip=True, num=None, parent=None, after=None,
                 reverse=False, threaded=False, **kw):
        self.skip = skip
        self.num = num
        self.parent = parent
        self.after = after
        self.reverse = reverse
        self.threaded = threaded
        Builder.__init__(self, **kw)

    def get_tree(self):
        raise NotImplementedError, "get_tree"

    def valid_after(self, after):
        w = self.convert_items((after,))[0]
        return self._viewable_message(w)

    def _viewable_message(self, m):
        if (c.user_is_admin or
                getattr(m, "author_id", 0) == c.user._id or
                getattr(m, "to_id", 0) == c.user._id):
            return True

        # m is wrapped at this time, so it should have an SR
        subreddit = getattr(m, "subreddit", None)
        if subreddit and subreddit.is_moderator_with_perms(c.user, 'mail'):
            return True

        return False

    def _apply_pagination(self, tree):
        if self.parent or self.num is None:
            return tree, None, None

        prev_item = None
        next_item = None

        if self.after:
            # truncate the tree to only show before/after requested message
            if self.reverse:
                next_item = self.after._id
                tree = [
                    (parent_id, child_ids) for parent_id, child_ids in tree
                    if tree_sort_fn((parent_id, child_ids)) >= next_item
                ]

                # special handling for after+reverse (before link): truncate
                # the tree so it has num messages before the requested one
                if len(tree) > self.num:
                    first_id, first_children = tree[-(self.num + 1)]
                    prev_item = tree_sort_fn((first_id, first_children))
                    tree = tree[-self.num:]
            else:
                prev_item = self.after._id
                tree = [
                    (parent_id, child_ids) for parent_id, child_ids in tree
                    if tree_sort_fn((parent_id, child_ids)) < prev_item
                ]

        if len(tree) > self.num:
            # truncate the tree to show only num conversations
            tree = tree[:self.num]
            last_id, last_children = tree[-1]
            next_item = tree_sort_fn((last_id, last_children))
        return tree, prev_item, next_item

    @classmethod
    def should_collapse(cls, message):
        # don't collapse this message if it has a new direct child
        if hasattr(message, "child"):
            has_new_child = any(child.new for child in message.child.things)
        else:
            has_new_child = False

        return (message.is_collapsed and
            not message.new and
            not has_new_child)

    def get_items(self):
        tree = self.get_tree()
        tree, prev_item, next_item = self._apply_pagination(tree)

        message_ids = []
        for parent_id, child_ids in tree:
            message_ids.append(parent_id)
            message_ids.extend(child_ids)

        if prev_item:
            message_ids.append(prev_item)

        messages = Message._byID(message_ids, data=True, return_dict=False)
        wrapped = {m._id: m for m in self.wrap_items(messages)}

        if prev_item:
            prev_item = wrapped[prev_item]
        if next_item:
            next_item = wrapped[next_item]

        final = []
        for parent_id, child_ids in tree:
            if parent_id not in wrapped:
                continue

            parent = wrapped[parent_id]

            if not self._viewable_message(parent):
                continue

            children = [
                wrapped[child_id] for child_id in child_ids
                if child_id in wrapped
            ]

            depth = {parent_id: 0}
            substitute_parents = {}

            if (children and self.skip and not self.threaded and
                    not self.parent and not parent.new and parent.is_collapsed):
                for i, child in enumerate(children):
                    if child.new or not child.is_collapsed:
                        break
                else:
                    i = -1
                # in flat view replace collapsed chain with MoreMessages
                child = make_child_listing()
                child.parent_name = "deleted" if parent.deleted else parent._fullname
                parent = Wrapped(MoreMessages(parent, child))
                children = children[i:]

            for child in sorted(children, key=lambda child: child._id):
                # iterate from the root outwards so we can check the depth
                if self.threaded:
                    try:
                        child_parent = wrapped[child.parent_id]
                    except KeyError:
                        # the stored comment tree was missing this message's
                        # parent, treat it as a top level reply
                        child_parent = parent
                else:
                    # for flat view all messages are decendants of the
                    # parent message
                    child_parent = parent
                parent_depth = depth[child_parent._id]
                child_depth = parent_depth + 1
                depth[child._id] = child_depth

                if child_depth == MAX_RECURSION:
                    # current message is at maximum depth level, all its
                    # children will be displayed as children of its parent
                    substitute_parents[child._id] = child_parent._id

                if child_depth > MAX_RECURSION:
                    child_parent_id = substitute_parents[child.parent_id]
                    substitute_parents[child._id] = child_parent_id
                    child_parent = wrapped[child_parent_id]

                child.is_child = True
                add_to_child_listing(child_parent, child)

            for child in children:
                # look over the children again to decide whether they can be
                # collapsed
                child.threaded = self.threaded
                child.collapsed = self.should_collapse(child)

            if self.threaded and children:
                most_recent_child_id = max(child._id for child in children)
                most_recent_child = wrapped[most_recent_child_id]
                most_recent_child.most_recent = True

            parent.is_parent = True
            parent.threaded = self.threaded
            parent.collapsed = self.should_collapse(parent)
            final.append(parent)

        return (final, prev_item, next_item, len(final), len(final))

    def item_iter(self, builder_items):
        items = builder_items[0]

        def _item_iter(_items):
            for i in _items:
                yield i
                if hasattr(i, "child"):
                    for j in _item_iter(i.child.things):
                        yield j

        return _item_iter(items)


class ModeratorMessageBuilder(MessageBuilder):
    def __init__(self, user, **kw):
        self.user = user
        MessageBuilder.__init__(self, **kw)

    def get_tree(self):
        if self.parent:
            sr = Subreddit._byID(self.parent.sr_id)
            return sr_conversation(sr, self.parent)
        sr_ids = Subreddit.reverse_moderator_ids(self.user)
        return moderator_messages(sr_ids)


class MultiredditMessageBuilder(MessageBuilder):
    def __init__(self, sr, **kw):
        self.sr = sr
        MessageBuilder.__init__(self, **kw)

    def get_tree(self):
        if self.parent:
            sr = Subreddit._byID(self.parent.sr_id)
            return sr_conversation(sr, self.parent)
        return moderator_messages(self.sr.sr_ids)


class TopCommentBuilder(CommentBuilder):
    """A comment builder to fetch only the top-level, non-spam,
       non-deleted comments"""
    def __init__(self, link, sort, num=None, wrap=Wrapped):
        CommentBuilder.__init__(self, link, sort, load_more=False,
            continue_this_thread=False, max_depth=1, wrap=wrap, num=num)

    def get_items(self):
        final = CommentBuilder.get_items(self)
        return [ cm for cm in final if not cm.deleted ]


class SrMessageBuilder(MessageBuilder):
    def __init__(self, sr, **kw):
        self.sr = sr
        MessageBuilder.__init__(self, **kw)

    def get_tree(self):
        if self.parent:
            return sr_conversation(self.sr, self.parent)
        return subreddit_messages(self.sr)


class UserMessageBuilder(MessageBuilder):
    def __init__(self, user, **kw):
        self.user = user
        MessageBuilder.__init__(self, **kw)

    def _viewable_message(self, message):
        is_author = message.author_id == c.user._id
        if not c.user_is_admin:
            if not is_author and message._spam:
                return False

            if message.author_id in self.user.enemies:
                return False

            # do not show messages which were deleted on recipient
            if (hasattr(message, "del_on_recipient") and
                    message.to_id == c.user._id and message.del_on_recipient):
                return False

        return super(UserMessageBuilder, self)._viewable_message(message)

    def get_tree(self):
        if self.parent:
            return conversation(self.user, self.parent)
        return user_messages(self.user)

    def valid_after(self, after):
        # Messages that have been spammed are still valid afters
        w = self.convert_items((after,))[0]
        return MessageBuilder._viewable_message(self, w)


class UserListBuilder(QueryBuilder):
    def thing_lookup(self, rels):
        accounts = Account._byID([rel._thing2_id for rel in rels], data=True)
        for rel in rels:
            rel._thing2 = accounts.get(rel._thing2_id)
        return rels

    def must_skip(self, item):
        return item.user._deleted

    def valid_after(self, after):
        # Users that have been deleted are still valid afters
        return True

    def wrap_items(self, rels):
        return [self.wrap(rel) for rel in rels]

class SavedBuilder(IDBuilder):
    def wrap_items(self, items):
        categories = LinkSavesByAccount.fast_query(c.user, items).items()
        categories += CommentSavesByAccount.fast_query(c.user, items).items()
        categories = {item[1]._id: category for item, category in categories if category}
        wrapped = QueryBuilder.wrap_items(self, items)
        for w in wrapped:
            category = categories.get(w._id, '')
            w.savedcategory = category
        return wrapped


class FlairListBuilder(UserListBuilder):
    def init_query(self):
        q = self.query

        if self.reverse:
            q._reverse()

        q._data = True
        self.orig_rules = deepcopy(q._rules)
        # FlairLists use Accounts for afters
        if self.after:
            if self.reverse:
                q._filter(Flair.c._thing2_id < self.after._id)
            else:
                q._filter(Flair.c._thing2_id > self.after._id)
