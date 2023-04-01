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

from itertools import chain
import math
import random
from collections import defaultdict
from datetime import timedelta
from operator import itemgetter
from pycassa.types import LongType

from r2.lib import rising
from r2.lib.db import operators, tdb_cassandra
from r2.lib.pages import ExploreItem
from r2.lib.normalized_hot import normalized_hot
from r2.lib.utils import roundrobin, tup, to36
from r2.models import Link, Subreddit
from r2.models.builder import CommentBuilder
from r2.models.listing import NestedListing
from r2.models.recommend import (
    AccountSRPrefs,
    AccountSRFeedback,
)

from pylons import app_globals as g
from pylons.i18n import _

# recommendation sources
SRC_MULTIREDDITS = 'mr'
SRC_EXPLORE = 'e'  # favors lesser known srs

# explore item types
TYPE_RISING = _("rising")
TYPE_DISCOVERY = _("discovery")
TYPE_HOT = _("hot")
TYPE_COMMENT = _("comment")


def get_recommendations(srs,
                        count=10,
                        source=SRC_MULTIREDDITS,
                        to_omit=None,
                        match_set=True,
                        over18=False):
    """Return subreddits recommended if you like the given subreddits.

    Args:
    - srs is one Subreddit object or a list of Subreddits
    - count is total number of results to return
    - source is a prefix telling which set of recommendations to use
    - to_omit is a single or list of subreddit id36s that should not be
        be included. (Useful for omitting recs that were already rejected.)
    - match_set=True will return recs that are similar to each other, useful
        for matching the "theme" of the original set
    - over18 content is filtered unless over18=True or one of the original srs
        is over18

    """
    srs = tup(srs)
    to_omit = tup(to_omit) if to_omit else []

    # fetch more recs than requested because some might get filtered out
    rec_id36s = SRRecommendation.for_srs([sr._id36 for sr in srs],
                                          to_omit,
                                          count * 2,
                                          source,
                                          match_set=match_set)

    # always check for private subreddits at runtime since type might change
    rec_srs = Subreddit._byID36(rec_id36s, return_dict=False)
    filtered = [sr for sr in rec_srs if is_visible(sr)]

    # don't recommend adult srs unless one of the originals was over_18
    if not over18 and not any(sr.over_18 for sr in srs):
        filtered = [sr for sr in filtered if not sr.over_18]

    return filtered[:count]


def get_recommended_content_for_user(account,
                                     settings,
                                     record_views=False,
                                     src=SRC_EXPLORE):
    """Wrapper around get_recommended_content() that fills in user info.

    If record_views == True, the srs will be noted in the user's preferences
    to keep from showing them again too soon.

    settings is an ExploreSettings object that controls what types of content
    will be included.

    Returns a list of ExploreItems.

    """
    prefs = AccountSRPrefs.for_user(account)
    recs = get_recommended_content(prefs, src, settings)
    if record_views:
        # mark as seen so they won't be shown again too soon
        sr_data = {r.sr: r.src for r in recs}
        AccountSRFeedback.record_views(account, sr_data)
    return recs


def get_recommended_content(prefs, src, settings):
    """Get a mix of content from subreddits recommended for someone with
    the given preferences (likes and dislikes.)

    Returns a list of ExploreItems.

    """
    # numbers chosen empirically to give enough results for explore page
    num_liked = 10  # how many liked srs to use when generating the recs
    num_recs = 20  # how many recommended srs to ask for
    num_discovery = 2  # how many discovery-related subreddits to mix in
    num_rising = 4  # how many rising links to mix in
    num_items = 20  # total items to return
    rising_items = discovery_items = comment_items = hot_items = []

    # make a list of srs that shouldn't be recommended
    default_srid36s = [to36(srid) for srid in Subreddit.default_subreddits()]
    omit_srid36s = list(prefs.likes.union(prefs.dislikes,
                                          prefs.recent_views,
                                          default_srid36s))
    # pick random subset of the user's liked srs
    liked_srid36s = random_sample(prefs.likes, num_liked) if settings.personalized else []
    # pick random subset of discovery srs
    candidates = set(get_discovery_srid36s()).difference(prefs.dislikes)
    discovery_srid36s = random_sample(candidates, num_discovery)
    # multiget subreddits
    to_fetch = liked_srid36s + discovery_srid36s
    srs = Subreddit._byID36(to_fetch)
    liked_srs = [srs[sr_id36] for sr_id36 in liked_srid36s]
    discovery_srs = [srs[sr_id36] for sr_id36 in discovery_srid36s]
    if settings.personalized:
        # generate recs from srs we know the user likes
        recommended_srs = get_recommendations(liked_srs,
                                              count=num_recs,
                                              to_omit=omit_srid36s,
                                              source=src,
                                              match_set=False,
                                              over18=settings.nsfw)
        random.shuffle(recommended_srs)
        # split list of recommended srs in half
        midpoint = len(recommended_srs) / 2
        srs_slice1 = recommended_srs[:midpoint]
        srs_slice2 = recommended_srs[midpoint:]
        # get hot links plus top comments from one half
        comment_items = get_comment_items(srs_slice1, src)
        # just get hot links from the other half
        hot_items = get_hot_items(srs_slice2, TYPE_HOT, src)
    if settings.discovery:
        # get links from subreddits dedicated to discovery
        discovery_items = get_hot_items(discovery_srs, TYPE_DISCOVERY, 'disc')
    if settings.rising:
        # grab some (non-personalized) rising items
        omit_sr_ids = set(int(id36, 36) for id36 in omit_srid36s)
        rising_items = get_rising_items(omit_sr_ids, count=num_rising)
    # combine all items and randomize order to get a mix of types
    all_recs = list(chain(rising_items,
                          comment_items,
                          discovery_items,
                          hot_items))
    random.shuffle(all_recs)
    # make sure subreddits aren't repeated
    seen_srs = set()
    recs = []
    for r in all_recs:
        if not settings.nsfw and r.is_over18():
            continue
        if not is_visible(r.sr):  # could happen in rising items
            continue
        if r.sr._id not in seen_srs:
            recs.append(r)
            seen_srs.add(r.sr._id)
        if len(recs) >= num_items:
            break
    return recs


def get_hot_items(srs, item_type, src):
    """Get hot links from specified srs."""
    hot_srs = {sr._id: sr for sr in srs}  # for looking up sr by id
    hot_link_fullnames = normalized_hot([sr._id for sr in srs])
    hot_links = Link._by_fullname(hot_link_fullnames, return_dict=False)
    hot_items = []
    for l in hot_links:
        hot_items.append(ExploreItem(item_type, src, hot_srs[l.sr_id], l))
    return hot_items


def get_rising_items(omit_sr_ids, count=4):
    """Get links that are rising right now."""
    all_rising = rising.get_all_rising()
    candidate_sr_ids = {sr_id for link, score, sr_id in all_rising}.difference(omit_sr_ids)
    link_fullnames = [link for link, score, sr_id in all_rising if sr_id in candidate_sr_ids]
    link_fullnames_to_show = random_sample(link_fullnames, count)
    rising_links = Link._by_fullname(link_fullnames_to_show,
                                     return_dict=False,
                                     data=True)
    rising_items = [ExploreItem(TYPE_RISING, 'ris', Subreddit._byID(l.sr_id), l)
                   for l in rising_links]
    return rising_items


def get_comment_items(srs, src, count=4):
    """Get hot links from srs, plus top comment from each link."""
    link_fullnames = normalized_hot([sr._id for sr in srs])
    hot_links = Link._by_fullname(link_fullnames[:count], return_dict=False)
    top_comments = []
    for link in hot_links:
        builder = CommentBuilder(link,
                                 operators.desc('_confidence'),
                                 comment=None,
                                 context=None,
                                 num=1,
                                 load_more=False)
        listing = NestedListing(builder, parent_name=link._fullname).listing()
        top_comments.extend(listing.things)
    srs = Subreddit._byID([com.sr_id for com in top_comments])
    links = Link._byID([com.link_id for com in top_comments])
    comment_items = [ExploreItem(TYPE_COMMENT,
                                 src,
                                 srs[com.sr_id],
                                 links[com.link_id],
                                 com) for com in top_comments]
    return comment_items


def get_discovery_srid36s():
    """Get list of srs that help people discover other srs."""
    srs = Subreddit._by_name(g.live_config['discovery_srs'])
    return [sr._id36 for sr in srs.itervalues()]


def random_sample(items, count):
    """Safe random sample that won't choke if len(items) < count."""
    sample_size = min(count, len(items))
    return random.sample(items, sample_size)


def is_visible(sr):
    """True if sr is visible to regular users, false if private or banned."""
    return (
        sr.type not in Subreddit.private_types and
        not sr._spam and
        sr.discoverable
    )


class SRRecommendation(tdb_cassandra.View):
    _use_db = True

    _compare_with = LongType()

    # don't keep these around if a run hasn't happened lately, or if the last
    # N runs didn't generate recommendations for a given subreddit
    _ttl = timedelta(days=7, hours=12)

    # we know that we mess with these but it's okay
    _warn_on_partial_ttl = False

    @classmethod
    def for_srs(cls, srid36, to_omit, count, source, match_set=True):
        # It's usually better to use get_recommendations() than to call this
        # function directly because it does privacy filtering.

        srid36s = tup(srid36)
        to_omit = set(to_omit)
        to_omit.update(srid36s)  # don't show the originals
        rowkeys = ['%s.%s' % (source, srid36) for srid36 in srid36s]

        # fetch multiple sets of recommendations, one for each input srid36
        rows = cls._byID(rowkeys, return_dict=False)

        if match_set:
            sorted_recs = cls._merge_and_sort_by_count(rows)
            # heuristic: if input set is large, rec should match more than one
            min_count = math.floor(.1 * len(srid36s))
            sorted_recs = (rec[0] for rec in sorted_recs if rec[1] > min_count)
        else:
            sorted_recs = cls._merge_roundrobin(rows)
        # remove duplicates and ids listed in to_omit
        filtered = []
        for r in sorted_recs:
            if r not in to_omit:
                filtered.append(r)
                to_omit.add(r)
        return filtered[:count]

    @classmethod
    def _merge_roundrobin(cls, rows):
        """Combine multiple sets of recs, preserving order.

        Picks items equally from each input sr, which can be useful for
        getting a diverse set of recommendations instead of one that matches
        a theme. Preserves ordering, so all rank 1 recs will be listed first,
        then all rank 2, etc.

        Returns a list of id36s.

        """
        return roundrobin(*[row._values().itervalues() for row in rows])

    @classmethod
    def _merge_and_sort_by_count(cls, rows):
        """Combine and sort multiple sets of recs.

        Combines multiple sets of recs and sorts by number of times each rec
        appears, the reasoning being that an item recommended for several of
        the original srs is more likely to match the "theme" of the set.

        """
        # combine recs from all input srs
        rank_id36_pairs = chain.from_iterable(row._values().iteritems()
                                              for row in rows)
        ranks = defaultdict(list)
        for rank, id36 in rank_id36_pairs:
            ranks[id36].append(rank)
        recs = [(id36, len(ranks), max(ranks))
                for id36, ranks in ranks.iteritems()]
        # first, sort ascending by rank
        recs = sorted(recs, key=itemgetter(2))
        # next, sort descending by number of times the rec appeared. since
        # python sort is stable, tied items will still be ordered by rank
        return sorted(recs, key=itemgetter(1), reverse=True)
