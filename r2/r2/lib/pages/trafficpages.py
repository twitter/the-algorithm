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
"""View models for the traffic statistic pages on reddit."""

import collections
import datetime
import pytz
import urllib

from pylons.i18n import _
from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g

import babel.core
from babel.dates import format_datetime
from babel.numbers import format_currency

from r2.lib import promote
from r2.lib.db.sorts import epoch_seconds
from r2.lib.menus import menu
from r2.lib.menus import NavButton, NamedButton, PageNameNav, NavMenu
from r2.lib.pages.pages import Reddit, TimeSeriesChart, TabbedPane
from r2.lib.promote import cost_per_mille, cost_per_click
from r2.lib.template_helpers import format_number
from r2.lib.utils import Storage, to_date, timedelta_by_name
from r2.lib.wrapped import Templated
from r2.models import Thing, Link, PromoCampaign, traffic
from r2.models.subreddit import Subreddit, _DefaultSR


COLORS = Storage(UPVOTE_ORANGE="#ff5700",
                 DOWNVOTE_BLUE="#9494ff",
                 MISCELLANEOUS="#006600")


class TrafficPage(Reddit):
    """Base page template for pages rendering traffic graphs."""

    extension_handling = False
    extra_page_classes = ["traffic"]

    def __init__(self, content):
        Reddit.__init__(self, title=_("traffic stats"), content=content)

    def build_toolbars(self):
        main_buttons = [NavButton(menu.sitewide, "/"),
                        NamedButton("languages"),
                        NamedButton("adverts")]

        toolbar = [PageNameNav("nomenu", title=self.title),
                   NavMenu(main_buttons, base_path="/traffic", type="tabmenu")]

        return toolbar


class SitewideTrafficPage(TrafficPage):
    """Base page for sitewide traffic overview."""

    extra_page_classes = TrafficPage.extra_page_classes + ["traffic-sitewide"]

    def __init__(self):
        TrafficPage.__init__(self, SitewideTraffic())


class LanguageTrafficPage(TrafficPage):
    """Base page for interface language traffic summaries or details."""

    def __init__(self, langcode):
        if langcode:
            content = LanguageTraffic(langcode)
        else:
            content = LanguageTrafficSummary()

        TrafficPage.__init__(self, content)


class AdvertTrafficPage(TrafficPage):
    """Base page for advert traffic summaries or details."""

    def __init__(self, code):
        if code:
            content = AdvertTraffic(code)
        else:
            content = AdvertTrafficSummary()
        TrafficPage.__init__(self, content)


class RedditTraffic(Templated):
    """A generalized content pane for traffic reporting."""

    make_period_link = None

    def __init__(self, place):
        self.place = place

        self.traffic_last_modified = traffic.get_traffic_last_modified()
        self.traffic_lag = (datetime.datetime.utcnow() -
                            self.traffic_last_modified)

        self.make_tables()

        Templated.__init__(self)

    def make_tables(self):
        """Create tables to put in the main table area of the page.

        See the stub implementations below for ways to hook into this process
        without completely overriding this method.

        """

        self.tables = []

        for interval in ("month", "day", "hour"):
            columns = [
                dict(color=COLORS.UPVOTE_ORANGE,
                     title=_("uniques by %s" % interval),
                     shortname=_("uniques")),
                dict(color=COLORS.DOWNVOTE_BLUE,
                     title=_("pageviews by %s" % interval),
                     shortname=_("pageviews")),
            ]

            data = self.get_data_for_interval(interval, columns)

            title = _("traffic by %s" % interval)
            graph = TimeSeriesChart("traffic-" + interval,
                                    title,
                                    interval,
                                    columns,
                                    data,
                                    self.traffic_last_modified,
                                    classes=["traffic-table"],
                                    make_period_link=self.make_period_link,
                                   )
            self.tables.append(graph)

        try:
            self.dow_summary = self.get_dow_summary()
        except NotImplementedError:
            self.dow_summary = None
        else:
            uniques_total = collections.Counter()
            pageviews_total = collections.Counter()
            days_total = collections.Counter()

            # don't include the latest (likely incomplete) day
            for date, (uniques, pageviews) in self.dow_summary[1:]:
                dow = date.weekday()
                uniques_total[dow] += uniques
                pageviews_total[dow] += pageviews
                days_total[dow] += 1

            # make a summary of the averages for each day of the week
            self.dow_summary = []
            for dow in xrange(7):
                day_count = days_total[dow]
                if day_count:
                    avg_uniques = uniques_total[dow] / day_count
                    avg_pageviews = pageviews_total[dow] / day_count
                    self.dow_summary.append((dow,
                                             (avg_uniques, avg_pageviews)))
                else:
                    self.dow_summary.append((dow, (0, 0)))

            # calculate the averages for *any* day of the week
            mean_uniques = sum(r[1][0] for r in self.dow_summary) / 7.0
            mean_pageviews = sum(r[1][1] for r in self.dow_summary) / 7.0
            self.dow_means = (round(mean_uniques), round(mean_pageviews))

    def get_dow_summary(self):
        """Return day-interval data to be aggregated by day of week.

        If implemented, a summary table will be shown on the traffic page
        with the average per day of week over the data interval given.

        """
        raise NotImplementedError()

    def get_data_for_interval(self, interval, columns):
        """Return data for the main overview at the interval given.

        This data will be shown as a set of graphs at the top of the page and a
        table for monthly and daily data (hourly is present but hidden by
        default.)

        """
        raise NotImplementedError()


def make_subreddit_traffic_report(subreddits=None, num=None):
    """Return a report of subreddit traffic in the last full month.

    If given a list of subreddits, those subreddits will be put in the report
    otherwise the top subreddits by pageviews will be automatically chosen.

    """

    if subreddits:
        subreddit_summary = traffic.PageviewsBySubreddit.last_month(subreddits)
    else:
        subreddit_summary = traffic.PageviewsBySubreddit.top_last_month(num)

    report = []
    for srname, data in subreddit_summary:
        if srname == _DefaultSR.name:
            name = _("[frontpage]")
            url = None
        elif srname in Subreddit._specials:
            name = "[%s]" % srname
            url = None
        else:
            name = "/r/%s" % srname
            url = name + "/about/traffic"

        report.append(((name, url), data))
    return report


class SitewideTraffic(RedditTraffic):
    """An overview of all traffic to the site."""
    def __init__(self):
        self.subreddit_summary = make_subreddit_traffic_report(num=250)
        RedditTraffic.__init__(self, g.domain)

    def get_dow_summary(self):
        return traffic.SitewidePageviews.history("day")

    def get_data_for_interval(self, interval, columns):
        return traffic.SitewidePageviews.history(interval)


class LanguageTrafficSummary(Templated):
    """An overview of traffic by interface language on the site."""

    def __init__(self):
        # convert language codes to real names
        language_summary = traffic.PageviewsByLanguage.top_last_month()
        locale = c.locale
        self.language_summary = []
        for language_code, data in language_summary:
            name = LanguageTraffic.get_language_name(language_code, locale)
            self.language_summary.append(((language_code, name), data))
        Templated.__init__(self)


class AdvertTrafficSummary(RedditTraffic):
    """An overview of traffic for all adverts on the site."""

    def __init__(self):
        RedditTraffic.__init__(self, _("adverts"))

    def make_tables(self):
        # overall promoted link traffic
        impressions = traffic.AdImpressionsByCodename.historical_totals("day")
        clicks = traffic.ClickthroughsByCodename.historical_totals("day")
        data = traffic.zip_timeseries(impressions, clicks)

        columns = [
            dict(color=COLORS.UPVOTE_ORANGE,
                 title=_("total impressions by day"),
                 shortname=_("impressions")),
            dict(color=COLORS.DOWNVOTE_BLUE,
                 title=_("total clicks by day"),
                 shortname=_("clicks")),
        ]

        self.totals = TimeSeriesChart("traffic-ad-totals",
                                      _("ad totals"),
                                      "day",
                                      columns,
                                      data,
                                      self.traffic_last_modified,
                                      classes=["traffic-table"])

        # get summary of top ads
        advert_summary = traffic.AdImpressionsByCodename.top_last_month()
        things = AdvertTrafficSummary.get_things(ad for ad, data
                                                 in advert_summary)
        self.advert_summary = []
        for id, data in advert_summary:
            name = AdvertTrafficSummary.get_ad_name(id, things=things)
            url = AdvertTrafficSummary.get_ad_url(id, things=things)
            self.advert_summary.append(((name, url), data))

    @staticmethod
    def split_codename(codename):
        """Codenames can be "fullname_campaign". Rend the parts asunder."""
        split_code = codename.split("_")
        fullname = "_".join(split_code[:2])
        campaign = "_".join(split_code[2:])
        return fullname, campaign

    @staticmethod
    def get_things(codes):
        """Fetch relevant things for a list of ad codenames in batch."""
        fullnames = [AdvertTrafficSummary.split_codename(code)[0]
                     for code in codes
                     if code.startswith(Thing._type_prefix)]
        return Thing._by_fullname(fullnames, data=True, return_dict=True)

    @staticmethod
    def get_sr_name(name):
        """Return the display name for a subreddit."""
        if name == g.default_sr:
            return _("frontpage")
        else:
            return "/r/" + name

    @staticmethod
    def get_ad_name(code, things=None):
        """Return a human-readable name for an ad given its codename.

        Optionally, a dictionary of things can be passed in so lookups can
        be done in batch upstream.

        """

        if not things:
            things = AdvertTrafficSummary.get_things([code])

        thing = things.get(code)
        campaign = None

        # if it's not at first a thing, see if it's a thing with campaign
        # appended to it.
        if not thing:
            fullname, campaign = AdvertTrafficSummary.split_codename(code)
            thing = things.get(fullname)

        if not thing:
            if code.startswith("dart_"):
                srname = code.split("_", 1)[1]
                srname = AdvertTrafficSummary.get_sr_name(srname)
                return "DART: " + srname
            else:
                return code
        elif isinstance(thing, Link):
            return "Link: " + thing.title
        elif isinstance(thing, Subreddit):
            srname = AdvertTrafficSummary.get_sr_name(thing.name)
            name = "300x100: " + srname
            if campaign:
                name += " (%s)" % campaign
            return name

    @staticmethod
    def get_ad_url(code, things):
        """Given a codename, return the canonical URL for its traffic page."""
        thing = things.get(code)
        if isinstance(thing, Link):
            return "/traffic/%s" % thing._id36
        return "/traffic/adverts/%s" % code


class LanguageTraffic(RedditTraffic):
    def __init__(self, langcode):
        self.langcode = langcode
        name = LanguageTraffic.get_language_name(langcode)
        RedditTraffic.__init__(self, name)

    def get_data_for_interval(self, interval, columns):
        return traffic.PageviewsByLanguage.history(interval, self.langcode)

    @staticmethod
    def get_language_name(language_code, locale=None):
        if not locale:
            locale = c.locale

        try:
            lang_locale = babel.core.Locale.parse(language_code, sep="-")
        except (babel.core.UnknownLocaleError, ValueError):
            return language_code
        else:
            return lang_locale.get_display_name(locale)


class AdvertTraffic(RedditTraffic):
    def __init__(self, code):
        self.code = code
        name = AdvertTrafficSummary.get_ad_name(code)
        RedditTraffic.__init__(self, name)

    def get_data_for_interval(self, interval, columns):
        columns[1]["title"] = _("impressions by %s" % interval)
        columns[1]["shortname"] = _("impressions")

        columns += [
            dict(shortname=_("unique clicks")),
            dict(color=COLORS.MISCELLANEOUS,
                 title=_("clicks by %s" % interval),
                 shortname=_("total clicks")),
        ]

        imps = traffic.AdImpressionsByCodename.history(interval, self.code)
        clicks = traffic.ClickthroughsByCodename.history(interval, self.code)
        return traffic.zip_timeseries(imps, clicks)


class SubredditTraffic(RedditTraffic):
    def __init__(self):
        RedditTraffic.__init__(self, "/r/" + c.site.name)

        if c.user_is_sponsor:
            fullname = c.site._fullname
            codes = traffic.AdImpressionsByCodename.recent_codenames(fullname)
            self.codenames = [(code,
                               AdvertTrafficSummary.split_codename(code)[1])
                               for code in codes]

    @staticmethod
    def make_period_link(interval, date):
        date = date.replace(tzinfo=g.tz)  # won't be necessary after tz fixup
        if interval == "month":
            if date.month != 12:
                end = date.replace(month=date.month + 1)
            else:
                end = date.replace(month=1, year=date.year + 1)
        else:
            end = date + timedelta_by_name(interval)

        query = urllib.urlencode({
            "syntax": "cloudsearch",
            "restrict_sr": "on",
            "sort": "top",
            "q": "timestamp:{:d}..{:d}".format(int(epoch_seconds(date)),
                                               int(epoch_seconds(end))),
        })
        return "/r/%s/search?%s" % (c.site.name, query)

    def get_dow_summary(self):
        return traffic.PageviewsBySubreddit.history("day", c.site.name)

    def get_data_for_interval(self, interval, columns):
        pageviews = traffic.PageviewsBySubreddit.history(interval, c.site.name)

        if interval == "day":
            columns.append(dict(color=COLORS.MISCELLANEOUS,
                                title=_("subscriptions by day"),
                                shortname=_("subscriptions")))

            sr_name = c.site.name
            subscriptions = traffic.SubscriptionsBySubreddit.history(interval,
                                                                     sr_name)

            return traffic.zip_timeseries(pageviews, subscriptions)
        else:
            return pageviews


def _clickthrough_rate(impressions, clicks):
    """Return the click-through rate percentage."""
    if impressions:
        return (float(clicks) / impressions) * 100.
    else:
        return 0


def _is_promo_preliminary(end_date):
    """Return if results are preliminary for this promotion.

    Results are preliminary until 1 day after the promotion ends.

    """

    now = datetime.datetime.now(g.tz)
    return end_date + datetime.timedelta(days=1) > now


def get_promo_traffic(thing, start, end):
    """Get traffic for a Promoted Link or PromoCampaign"""
    if isinstance(thing, Link):
        imp_fn = traffic.AdImpressionsByCodename.promotion_history
        click_fn = traffic.ClickthroughsByCodename.promotion_history
    elif isinstance(thing, PromoCampaign):
        imp_fn = traffic.TargetedImpressionsByCodename.promotion_history
        click_fn = traffic.TargetedClickthroughsByCodename.promotion_history

    imps = imp_fn(thing._fullname, start.replace(tzinfo=None),
                  end.replace(tzinfo=None))
    clicks = click_fn(thing._fullname, start.replace(tzinfo=None),
                      end.replace(tzinfo=None))

    if imps and not clicks:
        clicks = [(imps[0][0], (0,))]
    elif clicks and not imps:
        imps = [(clicks[0][0], (0,))]

    history = traffic.zip_timeseries(imps, clicks, order="ascending")
    return history


def get_billable_traffic(campaign):
    """Get traffic for dates when PromoCampaign is active."""
    start, end = promote.get_traffic_dates(campaign)
    return get_promo_traffic(campaign, start, end)


def is_early_campaign(campaign):
    # traffic by campaign was only recorded starting 2012/9/12
    return campaign.end_date < datetime.datetime(2012, 9, 12, 0, 0, tzinfo=g.tz)


def is_launched_campaign(campaign):
    now = datetime.datetime.now(g.tz).date()
    return (promote.charged_or_not_needed(campaign) and
            campaign.start_date.date() <= now)


class PromotedLinkTraffic(Templated):
    def __init__(self, thing, campaign, before, after):
        self.thing = thing
        self.campaign = campaign
        self.before = before
        self.after = after
        self.period = datetime.timedelta(days=7)
        self.prev = None
        self.next = None
        self.has_live_campaign = False
        self.has_early_campaign = False
        self.detail_name = ('campaign %s' % campaign._id36 if campaign
                                                           else 'all campaigns')

        editable = c.user_is_sponsor or c.user._id == thing.author_id
        self.traffic_last_modified = traffic.get_traffic_last_modified()
        self.traffic_lag = (datetime.datetime.utcnow() -
                            self.traffic_last_modified)
        self.make_hourly_table(campaign or thing)
        self.make_campaign_table()
        Templated.__init__(self)

    @classmethod
    def make_campaign_table_row(cls, id, start, end, target, location,
            budget_dollars, spent, paid_impressions, impressions, clicks,
            is_live, is_active, url, is_total):

        if impressions:
            cpm = format_currency(promote.cost_per_mille(spent, impressions),
                                  'USD', locale=c.locale)
        else:
            cpm = '---'

        if clicks:
            cpc = format_currency(promote.cost_per_click(spent, clicks), 'USD',
                                  locale=c.locale)
            ctr = format_number(_clickthrough_rate(impressions, clicks))
        else:
            cpc = '---'
            ctr = '---'

        return {
            'id': id,
            'start': start,
            'end': end,
            'target': target,
            'location': location,
            'budget': format_currency(budget_dollars, 'USD', locale=c.locale),
            'spent': format_currency(spent, 'USD', locale=c.locale),
            'impressions_purchased': format_number(paid_impressions),
            'impressions_delivered': format_number(impressions),
            'cpm': cpm,
            'clicks': format_number(clicks),
            'cpc': cpc,
            'ctr': ctr,
            'live': is_live,
            'active': is_active,
            'url': url,
            'csv': url + '.csv',
            'total': is_total,
        }

    def make_campaign_table(self):
        campaigns = PromoCampaign._by_link(self.thing._id)

        total_budget_dollars = 0.
        total_spent = 0
        total_paid_impressions = 0
        total_impressions = 0
        total_clicks = 0

        self.campaign_table = []
        for camp in campaigns:
            if not is_launched_campaign(camp):
                continue

            is_live = camp.is_live_now()
            self.has_early_campaign |= is_early_campaign(camp)
            self.has_live_campaign |= is_live

            history = get_billable_traffic(camp)
            impressions, clicks = 0, 0
            for date, (imp, click) in history:
                impressions += imp
                clicks += click

            start = to_date(camp.start_date).strftime('%Y-%m-%d')
            end = to_date(camp.end_date).strftime('%Y-%m-%d')
            target = camp.target.pretty_name
            location = camp.location_str
            spent = promote.get_spent_amount(camp)
            is_active = self.campaign and self.campaign._id36 == camp._id36
            url = '/traffic/%s/%s' % (self.thing._id36, camp._id36)
            is_total = False
            campaign_budget_dollars = camp.total_budget_dollars
            row = self.make_campaign_table_row(camp._id36,
                                               start=start,
                                               end=end,
                                               target=target,
                                               location=location,
                                               budget_dollars=campaign_budget_dollars,
                                               spent=spent,
                                               paid_impressions=camp.impressions,
                                               impressions=impressions,
                                               clicks=clicks,
                                               is_live=is_live,
                                               is_active=is_active,
                                               url=url,
                                               is_total=is_total)
            self.campaign_table.append(row)

            total_budget_dollars += campaign_budget_dollars
            total_spent += spent
            total_paid_impressions += camp.impressions
            total_impressions += impressions
            total_clicks += clicks

        # total row
        start = '---'
        end = '---'
        target = '---'
        location = '---'
        is_live = False
        is_active = not self.campaign
        url = '/traffic/%s' % self.thing._id36
        is_total = True
        row = self.make_campaign_table_row(_('total'),
                                           start=start,
                                           end=end,
                                           target=target,
                                           location=location,
                                           budget_dollars=total_budget_dollars,
                                           spent=total_spent,
                                           paid_impressions=total_paid_impressions,
                                           impressions=total_impressions,
                                           clicks=total_clicks,
                                           is_live=is_live,
                                           is_active=is_active,
                                           url=url,
                                           is_total=is_total)
        self.campaign_table.append(row)

    def check_dates(self, thing):
        """Shorten range for display and add next/prev buttons."""
        start, end = promote.get_traffic_dates(thing)

        # Check date of latest traffic (campaigns can end early).
        history = list(get_promo_traffic(thing, start, end))
        if history:
            end = max(date for date, data in history)
            end = end.replace(tzinfo=g.tz)  # get_promo_traffic returns tz naive
                                            # datetimes but is actually g.tz

        if self.period:
            display_start = self.after
            display_end = self.before

            if not display_start and not display_end:
                display_end = end
                display_start = end - self.period
            elif not display_end:
                display_end = display_start + self.period
            elif not display_start:
                display_start = display_end - self.period

            if display_start > start:
                p = request.GET.copy()
                p.update({
                    'after': None,
                    'before': display_start.strftime('%Y%m%d%H'),
                })
                self.prev = '%s?%s' % (request.path, urllib.urlencode(p))
            else:
                display_start = start

            if display_end < end:
                p = request.GET.copy()
                p.update({
                    'after': display_end.strftime('%Y%m%d%H'),
                    'before': None,
                })
                self.next = '%s?%s' % (request.path, urllib.urlencode(p))
            else:
                display_end = end
        else:
            display_start, display_end = start, end

        return display_start, display_end

    @classmethod
    def get_hourly_traffic(cls, thing, start, end):
        """Retrieve hourly traffic for a Promoted Link or PromoCampaign."""
        history = get_promo_traffic(thing, start, end)
        computed_history = []
        for date, data in history:
            imps, clicks = data
            ctr = _clickthrough_rate(imps, clicks)

            date = date.replace(tzinfo=pytz.utc)
            date = date.astimezone(pytz.timezone("EST"))
            datestr = format_datetime(
                date,
                locale=c.locale,
                format="yyyy-MM-dd HH:mm zzz",
            )
            computed_history.append((date, datestr, data + (ctr,)))
        return computed_history

    def make_hourly_table(self, thing):
        start, end = self.check_dates(thing)
        self.history = self.get_hourly_traffic(thing, start, end)

        self.total_impressions, self.total_clicks = 0, 0
        for date, datestr, data in self.history:
            imps, clicks, ctr = data
            self.total_impressions += imps
            self.total_clicks += clicks
        if self.total_impressions > 0:
            self.total_ctr = _clickthrough_rate(self.total_impressions,
                                                self.total_clicks)
        # XXX: _is_promo_preliminary correctly expects tz-aware datetimes
        # because it's also used with datetimes from promo code. this hack
        # relies on the fact that we're storing UTC w/o timezone info.
        # TODO: remove this when traffic is correctly using timezones.
        end_aware = end.replace(tzinfo=g.tz)
        self.is_preliminary = _is_promo_preliminary(end_aware)

    @classmethod
    def as_csv(cls, thing):
        """Return the traffic data in CSV format for reports."""

        import csv
        import cStringIO

        start, end = promote.get_traffic_dates(thing)
        history = cls.get_hourly_traffic(thing, start, end)

        out = cStringIO.StringIO()
        writer = csv.writer(out)

        writer.writerow((_("date and time (UTC)"),
                         _("impressions"),
                         _("clicks"),
                         _("click-through rate (%)")))
        for date, datestr, values in history:
            # flatten (date, datestr, value-tuple) to (date, value1, value2...)
            writer.writerow((date,) + values)

        return out.getvalue()


class SubredditTrafficReport(Templated):
    def __init__(self):
        self.srs, self.invalid_srs, self.report = [], [], []

        self.textarea = request.params.get("subreddits")
        if self.textarea:
            requested_srs = [srname.strip()
                             for srname in self.textarea.splitlines()]
            subreddits = Subreddit._by_name(requested_srs)

            for srname in requested_srs:
                if srname in subreddits:
                    self.srs.append(srname)
                else:
                    self.invalid_srs.append(srname)

            if subreddits:
                self.report = make_subreddit_traffic_report(subreddits.values())

            param = urllib.quote(self.textarea)
            self.csv_url = "/traffic/subreddits/report.csv?subreddits=" + param

        Templated.__init__(self)

    def as_csv(self):
        """Return the traffic data in CSV format for reports."""

        import csv
        import cStringIO

        out = cStringIO.StringIO()
        writer = csv.writer(out)

        writer.writerow((_("subreddit"),
                         _("uniques"),
                         _("pageviews")))
        for (name, url), (uniques, pageviews) in self.report:
            writer.writerow((name, uniques, pageviews))

        return out.getvalue()
