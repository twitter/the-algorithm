#!/usr/bin/python
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
"""Tools for evaluating promoted link distribution."""

from collections import defaultdict
import datetime
from math import sqrt

from pylons import app_globals as g
from sqlalchemy.sql.functions import sum as sa_sum

from r2.lib import promote
from r2.lib.db.operators import and_, or_
from r2.lib.utils import to36, weighted_lottery
from r2.models.traffic import (
    Session,
    TargetedImpressionsByCodename,
    PageviewsBySubredditAndPath,
)
from r2.models.bidding import PromotionWeights
from r2.models import (
    Link,
    PromoCampaign,
    DefaultSR,
)

LINK_PREFIX = Link._type_prefix + str(Link._type_id)
PC_PREFIX = PromoCampaign._type_prefix + str(PromoCampaign._type_id)


def error_statistics(errors):
    mean_error = sum(errors) / len(errors)
    min_error = min([abs(i) for i in errors])
    max_error = max([abs(i) for i in errors])
    stdev_error = sqrt(
        (sum([i ** 2 for i in errors]) / len(errors))
        - mean_error ** 2)
    return (mean_error, min_error, max_error, stdev_error)


def get_scheduled(date, sr_name=''):
    campaign_ids = PromotionWeights.get_campaign_ids(date, sr_names=[sr_name])
    campaigns = PromoCampaign._byID(campaign_ids, return_dict=False, data=True)
    links = Link._by_fullname({camp.link_id for camp in campaigns},
                              return_dict=False, data=True)
    links = {l._id: l for l in links}
    kept = []
    for camp in campaigns:
        if camp.trans_id == 0:
            continue

        link = links[camp.link_id]
        if link._spam or not promote.is_accepted(link):
            continue

        kept.append(camp._id)

    return [(camp._fullname, camp.link_id, camp.total_budget_dollars)
        for camp in kept]


def get_campaign_pageviews(date, sr_name=''):
    # ads go live at hour=5
    start = datetime.datetime(date.year, date.month, date.day, 5, 0)
    hours = [start + datetime.timedelta(hours=i) for i in xrange(24)]

    traffic_cls = TargetedImpressionsByCodename
    codename_string = PC_PREFIX + '_%'
    q = (Session.query(traffic_cls.codename,
                       sa_sum(traffic_cls.pageview_count).label('daily'))
            .filter(traffic_cls.subreddit == sr_name)
            .filter(traffic_cls.codename.like(codename_string))
            .filter(traffic_cls.interval == 'hour')
            .filter(traffic_cls.date.in_(hours))
            .group_by(traffic_cls.codename))

    pageviews = dict(q)
    return pageviews


def filter_campaigns(date, fullnames):
    campaigns = PromoCampaign._by_fullname(fullnames, data=True,
                                           return_dict=False)

    # filter out campaigns that shouldn't be live
    pc_date = datetime.datetime(date.year, date.month, date.day, 0, 0,
                                tzinfo=g.tz)

    campaigns = [camp for camp in campaigns
                 if camp.start_date <= pc_date <= camp.end_date]

    # check for links with targeted campaigns - we can't handle them now
    has_targeted = [camp.link_id for camp in campaigns if camp.sr_name != '']
    return [camp for camp in campaigns if camp.link_id not in has_targeted]


def get_frontpage_pageviews(date):
    sr_name = DefaultSR.name
    traffic_cls = PageviewsBySubredditAndPath
    q = (Session.query(traffic_cls.srpath, traffic_cls.pageview_count)
           .filter(traffic_cls.interval == 'day')
           .filter(traffic_cls.date == date)
           .filter(traffic_cls.srpath == '%s-GET_listing' % sr_name))
    r = list(q)
    return r[0][1]


def compare_pageviews(daysago=0, verbose=False):
    """Evaluate past delivery for promoted links.

    Check frontpage promoted links for their actual delivery compared to what
    would be expected based on their bids.

    """

    date = (datetime.datetime.now(g.tz) -
            datetime.timedelta(days=daysago)).date()

    scheduled = get_scheduled(date)
    pageviews_by_camp = get_campaign_pageviews(date)
    campaigns = filter_campaigns(date, pageviews_by_camp.keys())
    actual = []
    for camp in campaigns:
        link_fullname = '%s_%s' % (LINK_PREFIX, to36(camp.link_id))
        i = (camp._fullname, link_fullname, pageviews_by_camp[camp._fullname])
        actual.append(i)

    scheduled_links = {link for camp, link, pageviews in scheduled}
    actual_links = {link for camp, link, pageviews in actual}

    bid_by_link = defaultdict(int)
    total_bid = 0

    pageviews_by_link = defaultdict(int)
    total_pageviews = 0

    for camp, link, bid in scheduled:
        if link not in actual_links:
            if verbose:
                print '%s not found in actual, skipping' % link
            continue

        bid_by_link[link] += bid
        total_bid += bid

    for camp, link, pageviews in actual:
        # not ideal: links shouldn't be here
        if link not in scheduled_links:
            if verbose:
                print '%s not found in schedule, skipping' % link
            continue

        pageviews_by_link[link] += pageviews
        total_pageviews += pageviews

    errors = []
    for link, bid in sorted(bid_by_link.items(), key=lambda t: t[1]):
        pageviews = pageviews_by_link.get(link, 0)
        expected = bid / total_bid
        realized = float(pageviews) / total_pageviews
        difference = (realized - expected) / expected
        errors.append(difference)
        if verbose:
            print '%s - %s - %s - %s' % (link, expected, realized, difference)

    mean_error, min_error, max_error, stdev_error = error_statistics(errors)

    print '%s' % date
    print ('error %s max, %s min, %s +- %s' %
           (max_error, min_error, mean_error, stdev_error))
    print 'total bid %s' % total_bid
    print ('pageviews for promoted links targeted only to frontpage %s' %
           total_pageviews)
    print ('frontpage pageviews for all promoted links %s' %
           sum(pageviews_by_camp.values()))
    print 'promoted eligible pageviews %s' % get_frontpage_pageviews(date)


PROMOS = [('promo_%s' % i, i + 1) for i in xrange(100)]


def select_subset(n, weighted=False):
    promos = copy(PROMOS)
    selected = []

    if weighted:
        d = {(name, weight): weight for name, weight in promos}
        while len(selected) < n and d:
            i = weighted_lottery(d)
            del d[i]
            selected.append(i)
    else:
        # Sample without replacement
        if n > len(promos):
            return promos
        else:
            return random.sample(promos, n)
    return selected


def pick(subset, weighted=False):
    if weighted:
        d = {(name, weight): weight for name, weight in subset}
        picked = weighted_lottery(d)
    else:
        picked = random.choice(subset)
    return picked


def benchmark(subsets=1440, picks=6945, weighted_subset=False,
              weighted_pick=True, subset_size=10, verbose=False):
    """Test 2 stage randomization.

    First stage picks a subset of promoted links, second stage picks a single
    promoted link. This is to simulate the server side subset plus client side
    randomization of promoted link display.

    """

    counts = {(name, weight): 0 for name, weight in PROMOS}

    for i in xrange(subsets):
        subset = select_subset(subset_size, weighted=weighted_subset)

        for j in xrange(picks):
            name, weight = pick(subset, weighted=weighted_pick)
            counts[(name, weight)] += 1

    total_weight = sum(counts.values())
    errors = []
    for name, weight in sorted(counts.keys(), key=lambda t: t[1]):
        count = counts[(name, weight)]
        actual = float(count) / (subsets * picks)
        expected = float(weight) / total_weight
        error = (actual - expected) / expected
        errors.append(error)
        if verbose:
            print ('%s - expected: %s - actual: %s - error %s' %
                   (name, expected, actual, error))

    mean_error, min_error, max_error, stdev_error = error_statistics(errors)

    if verbose:
        print ('Error %s max, %s min, %s +- %s' %
               (max_error, min_error, mean_error, stdev_error))

    return (max_error, min_error, mean_error, stdev_error)
