# -*- coding: utf-8 -*-
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

from collections import Counter, OrderedDict

from r2.config import feature
from r2.lib.contrib.ipaddress import ip_address
from r2.lib.db.operators import asc
from r2.lib.wrapped import Wrapped, Templated, CachedTemplate
from r2.models import (
    Account,
    All,
    AllMinus,
    AllSR,
    Comment,
    DefaultSR,
    DomainSR,
    FakeSubreddit,
    Filtered,
    Flair,
    FlairListBuilder,
    FlairTemplate,
    FlairTemplateBySubredditIndex,
    Friends,
    Frontpage,
    LINK_FLAIR,
    LabeledMulti,
    Link,
    ReadNextLink,
    ReadNextListing,
    Mod,
    ModSR,
    MultiReddit,
    NotFound,
    OLD_SITEWIDE_RULES,
    Printable,
    PromoCampaign,
    PromotionPrices,
    IDBuilder,
    Random,
    RandomNSFW,
    RandomSubscription,
    SITEWIDE_RULES,
    StylesheetsEverywhere,
    Subreddit,
    SubredditRules,
    Target,
    Trophy,
    USER_FLAIR,
    make_feedurl,
)
from r2.models.bidding import Bid
from r2.models.gold import (
    calculate_server_seconds,
    days_to_pennies,
    paypal_subscription_url,
    gold_payments_by_user,
    gold_received_by_user,
    get_current_value_of_month,
    gold_goal_on,
    gold_revenue_steady,
    gold_revenue_volatile,
    get_subscription_details,
    TIMEZONE as GOLD_TIMEZONE,
)
from r2.models.promo import (
    NO_TRANSACTION,
    PROMOTE_COST_BASIS,
    PROMOTE_PRIORITIES,
    PromotionLog,
    Collection,
)
from r2.models.token import OAuth2Client, OAuth2AccessToken
from r2.models import traffic
from r2.models import ModAction
from r2.models import Thing
from r2.models.wiki import WikiPage, ImagesByWikiPage
from r2.lib.db import tdb_cassandra, queries
from r2.config.extensions import is_api
from r2.lib.menus import CommentSortMenu

from pylons.i18n import _, ungettext
from pylons import request, config
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.controllers.util import abort

from r2.lib import hooks, inventory, media
from r2.lib import promote, tracking
from r2.lib.captcha import get_iden
from r2.lib.filters import (
    scriptsafe_dumps,
    spaceCompress,
    _force_unicode,
    _force_utf8,
    unsafe,
    websafe,
    SC_ON,
    SC_OFF,
    websafe_json,
    wikimarkdown,
)
from r2.lib.menus import NavButton, NamedButton, NavMenu, PageNameNav, JsButton
from r2.lib.menus import SubredditButton, SubredditMenu, ModeratorMailButton
from r2.lib.menus import OffsiteButton, menu, JsNavMenu
from r2.lib.normalized_hot import normalized_hot
from r2.lib.providers import image_resizing
from r2.lib.strings import (
    get_funny_translated_string,
    plurals,
    Score,
    strings,
)
from r2.lib.utils import is_subdomain, title_to_url, UrlParser
from r2.lib.utils import url_links_builder, median, to36
from r2.lib.utils import trunc_time, timesince, timeuntil, weighted_lottery
from r2.lib.template_helpers import (
    add_sr,
    comment_label,
    format_number,
    get_domain,
    make_url_https,
    make_url_protocol_relative,
    static,
)
from r2.lib.subreddit_search import popular_searches
from r2.lib.memoize import memoize
from r2.lib.utils import trunc_string as _truncate, to_date
from r2.lib.filters import safemarkdown
from r2.lib.utils import (
    Storage,
    feature_utils,
    precise_format_timedelta,
    tup,
    url_is_embeddable_image,
)
from r2.lib.cache import make_key_id, MemcachedError

from babel.numbers import format_currency
from babel.dates import format_date
from collections import defaultdict, namedtuple
import csv
import hmac
import hashlib
import cStringIO
import sys, random, datetime, calendar, simplejson, re, time
import time
from itertools import chain, product
from urllib import quote, urlencode
from urlparse import urlparse

from r2.lib.ip_events import ips_by_account_id

from things import wrap_links, wrap_things, default_thing_wrapper

datefmt = _force_utf8(_('%d %b %Y'))

MAX_DESCRIPTION_LENGTH = 150

def get_captcha():
    if not c.user_is_loggedin or c.user.needs_captcha():
        return get_iden()

def responsive(res, space_compress=None):
    """
    Use in places where the template is returned as the result of the
    controller so that it becomes compatible with the page cache.
    """
    if space_compress is None:
        space_compress = not g.template_debug

    if is_api():
        res = res or u''
        if not c.allowed_callback and request.environ.get("WANT_RAW_JSON"):
            res = scriptsafe_dumps(res)
        else:
            res = websafe_json(simplejson.dumps(res))

        if c.allowed_callback:
            # Add a comment to the beginning to prevent the "Rosetta Flash"
            # XSS when an attacker controls the beginning of a resource
            res = "/**/%s(%s)" % (websafe_json(c.allowed_callback), res)
    elif space_compress:
        res = spaceCompress(res)
    return res


class Robots(Templated):

    def __init__(self, **context):
        Templated.__init__(self, **context)
        self.subreddit_sitemap = g.sitemap_subreddit_static_url

class CrossDomain(Templated):
    pass


class Reddit(Templated):
    '''Base class for rendering a page on reddit.  Handles toolbar creation,
    content of the footers, and content of the corner buttons.

    Constructor arguments:

        space_compress -- run r2.lib.filters.spaceCompress on render
        loginbox -- enable/disable rendering of the small login box in the right margin
          (only if no user is logged in; login box will be disabled for a logged in user)
        show_sidebar -- enable/disable content in the right margin

        infotext -- text to display in a <p class="infotext"> above the content
        nav_menus -- list of Menu objects to be shown in the area below the header
        content -- renderable object to fill the main content well in the page.

    settings determined at class-declaration time

      create_reddit_box -- enable/disable display of the "Create a reddit" box
      submit_box        -- enable/disable display of the "Submit" box
      searchbox         -- enable/disable the "search" box in the header
      extension_handling -- enable/disable rendering using non-html templates
                            (e.g. js, xml for rss, etc.)
    '''

    create_reddit_box  = True
    submit_box         = True
    header             = True
    searchbox          = True
    extension_handling = True
    enable_login_cover = True
    site_tracking      = True
    show_infobar       = True
    content_id         = None
    css_class          = None
    extra_page_classes = None
    extra_stylesheets  = []

    def __init__(self, space_compress=None, nav_menus=None, loginbox=True,
                 infotext='', infotext_class=None, infotext_show_icon=False,
                 content=None, short_description='', title='',
                 robots=None, show_sidebar=True, show_chooser=False,
                 header=True, srbar=True, page_classes=None, short_title=None,
                 show_wiki_actions=False, extra_js_config=None,
                 show_locationbar=False, auction_announcement=False,
                 show_newsletterbar=False, canonical_link=None,
                 **context):
        Templated.__init__(self, **context)
        self.title = title
        self.short_title = short_title
        self.short_description = short_description
        self.robots = robots
        self.infotext = infotext
        self.extra_js_config = extra_js_config
        self.show_wiki_actions = show_wiki_actions
        self.loginbox = loginbox
        self.show_sidebar = show_sidebar
        self.space_compress = space_compress
        self.dnt_enabled = feature.is_enabled("do_not_track")
        self.header = header
        self.footer = RedditFooter()
        self.debug_footer = DebugFooter()
        self.supplied_page_classes = page_classes or []
        self.show_newsletterbar = show_newsletterbar

        self.auction_announcement = auction_announcement

        #put the sort menus at the top
        self.nav_menu = MenuArea(menus = nav_menus) if nav_menus else None

        #add the infobar
        self.welcomebar = None
        self.newsletterbar = None
        self.locationbar = None
        self.infobar = None
        self.mobilewebredirectbar = None
        self.show_timeout_modal = False

        if feature.is_enabled("new_expando_icons"):
            self.feature_new_expando_icons = True
        if feature.is_enabled("expando_nsfw_flow"):
            self.feature_expando_nsfw_flow = True

        # generate a canonical link for google
        canonical_url = UrlParser(canonical_link or request.url)
        canonical_url.canonicalize()
        self.canonical_link = canonical_url.unparse()
        if c.render_style != "html":
            u = UrlParser(request.fullpath)
            u.set_extension("")
            u.hostname = g.domain
            u.scheme = g.default_scheme
            if g.domain_prefix:
                u.hostname = "%s.%s" % (g.domain_prefix, u.hostname)
            self.canonical_link = u.unparse()

        if self.show_infobar:
            if not infotext:
                if g.heavy_load_mode:
                    # heavy load mode message overrides read only
                    infotext = strings.heavy_load_msg
                elif g.read_only_mode:
                    infotext = strings.read_only_msg
                elif g.live_config.get("announcement_message"):
                    infotext = g.live_config["announcement_message"]
            if c.user_is_loggedin and c.user.in_timeout:
                timeout_days_remaining = c.user.days_remaining_in_timeout

                if timeout_days_remaining:
                    days = ungettext('day', 'days', timeout_days_remaining)
                    days_str = '%(num)s %(days)s' % {
                        'num': timeout_days_remaining,
                        'days': days,
                    }
                    message = strings.in_temp_timeout_msg % {'days': days_str}
                else:
                    message = strings.in_perma_timeout_msg

                self.infobar = RedditInfoBar(
                    message=message,
                    extra_class='timeout-infobar',
                    show_icon=True,
                )
            elif infotext:
                self.infobar = RedditInfoBar(
                    message=infotext,
                    extra_class=infotext_class,
                    show_icon=infotext_show_icon,
                )
            elif isinstance(c.site, AllMinus) and not c.user.gold:
                self.infobar = RedditInfoBar(message=strings.all_minus_gold_only,
                                       extra_class="gold")

            if not c.user_is_loggedin:
                if getattr(self, "show_welcomebar", True):
                    self.welcomebar = WelcomeBar()
                if self.show_newsletterbar:
                    self.newsletterbar = NewsletterBar()

            if (c.render_style == "compact" and
                    getattr(self, "show_mobilewebredirectbar", True)):
                self.mobilewebredirectbar = MobileWebRedirectBar()

            show_locationbar &= not c.user.pref_hide_locationbar
            if (show_locationbar and c.used_localized_defaults and
                    (not c.user_is_loggedin or
                     not c.user.has_subscribed)):
                self.locationbar = LocationBar()

        self.srtopbar = None
        if srbar and not is_api():
            self.srtopbar = SubredditTopBar()

        panes = [content]

        if c.user_is_loggedin and not is_api() and not self.show_wiki_actions:
            # insert some form templates for js to use
            # TODO: move these to client side templates
            gold_link = GoldPayment("gift",
                                    "monthly",
                                    months=1,
                                    signed=False,
                                    recipient="",
                                    giftmessage=None,
                                    passthrough=None,
                                    thing=None,
                                    clone_template=True,
                                    thing_type="link",
                                   )
            gold_comment = GoldPayment("gift",
                                       "monthly",
                                       months=1,
                                       signed=False,
                                       recipient="",
                                       giftmessage=None,
                                       passthrough=None,
                                       thing=None,
                                       clone_template=True,
                                       thing_type="comment",
                                      )

            report_form_templates = ReportFormTemplates()

            panes.append(report_form_templates)

            if self.show_sidebar:
                panes.extend([gold_comment, gold_link])

            if c.user_is_sponsor:
                panes.append(FraudForm())

        if c.user_is_loggedin and c.user.in_timeout:
            self.show_timeout_modal = True
            self.timeout_days_remaining = c.user.days_remaining_in_timeout

        self.popup_panes = self.build_popup_panes()
        panes.append(self.popup_panes)

        self._content = PaneStack(panes)

        self.show_chooser = (
            show_chooser and
            c.render_style == "html" and
            c.user_is_loggedin and
            (
                isinstance(c.site, (DefaultSR, AllSR, ModSR, LabeledMulti)) or
                c.site.name == g.live_config["listing_chooser_explore_sr"]
            )
        )

        self.toolbars = self.build_toolbars()

        has_style_override = (c.user_is_loggedin and
                c.user.pref_default_theme_sr and
                feature.is_enabled('stylesheets_everywhere') and
                c.user.pref_enable_default_themes)
        # if there is no style or the style is disabled for this subreddit
        self.no_sr_styles = (isinstance(c.site, DefaultSR) or
            (not self.get_subreddit_stylesheet_url(c.site) and not c.site.header) or
            (c.user and not c.user.use_subreddit_style(c.site)))

        self.default_theme_sr = DefaultSR()
        # use override stylesheet if they have custom styles disabled or
        # this subreddit has no custom stylesheet (or is the front page)
        if self.no_sr_styles:
            self.subreddit_stylesheet_url = self.get_subreddit_stylesheet_url(
                self.default_theme_sr)
        else:
            self.subreddit_stylesheet_url = self.get_subreddit_stylesheet_url(c.site)

        if has_style_override and self.no_sr_styles:
            sr = Subreddit._by_name(c.user.pref_default_theme_sr)
            # make sure they can still view their override subreddit
            if sr.can_view(c.user) and sr.stylesheet_url:
                self.subreddit_stylesheet_url = self.get_subreddit_stylesheet_url(sr)
                if c.can_apply_styles and c.allow_styles and sr.header:
                    self.default_theme_sr = sr


    @staticmethod
    def get_subreddit_stylesheet_url(sr):
        if not g.css_killswitch and c.can_apply_styles and c.allow_styles:
            if c.secure:
                if sr.stylesheet_url:
                    return make_url_https(sr.stylesheet_url)
                elif sr.stylesheet_url_https:
                    return sr.stylesheet_url_https
            else:
                if sr.stylesheet_url:
                    return sr.stylesheet_url
                elif sr.stylesheet_url_http:
                    return sr.stylesheet_url_http

    def wiki_actions_menu(self, moderator=False):
        data_attrs = lambda event: (
            {'type': 'subreddit', 'event-action': 'pageview', 'event-detail': event})

        buttons = []

        buttons.append(NamedButton(
            "wikirecentrevisions",
            css_class="wikiaction-revisions",
            dest="/wiki/revisions",
            data=data_attrs('wikirevisions')))
        buttons.append(NamedButton(
            "wikipageslist",
            css_class="wikiaction-pages",
            dest="/wiki/pages",
            data=data_attrs('wikipages')))

        if moderator:
            buttons.append(NamedButton(
                'wikibanned',
                css_class='reddit-ban access-required',
                dest='/about/wikibanned',
                data=data_attrs('wikibanned')))
            buttons.append(NamedButton(
                'wikicontributors',
                css_class='reddit-contributors access-required',
                dest='/about/wikicontributors',
                data=data_attrs('wikicontributors')))

        return SideContentBox(_('wiki tools'),
                      [NavMenu(buttons,
                               type="flat_vert",
                               css_class="icon-menu",
                               separator="")],
                      _id="wikiactions",
                      collapsible=True)

    def sr_admin_menu(self):
        buttons = []
        is_single_subreddit = not isinstance(c.site, (ModSR, MultiReddit))
        is_admin = c.user_is_loggedin and c.user_is_admin
        is_moderator_with_perms = lambda *perms: (
            is_admin or c.site.is_moderator_with_perms(c.user, *perms))
        data_attrs = lambda event: (
            {'type': 'subreddit', 'event-action': 'pageview', 'event-detail': event})

        if is_single_subreddit and is_moderator_with_perms('config'):
            buttons.append(NavButton(
                menu.community_settings,
                css_class="reddit-edit access-required",
                dest="edit",
                data=data_attrs('editsubreddit')))
            buttons.append(NavButton(
                menu.edit_stylesheet,
                css_class="edit-stylesheet access-required",
                dest="stylesheet",
                data=data_attrs('stylesheet')))
            if feature.is_enabled("subreddit_rules", subreddit=c.site.name):
                buttons.append(NavButton(
                    menu.community_rules,
                    css_class="community-rules access-required",
                    dest="rules",
                    data=data_attrs('rules')))

        if is_moderator_with_perms('mail'):
            buttons.append(NamedButton(
                "modmail",
                dest="message/inbox",
                css_class="moderator-mail access-required",
                data=data_attrs('modmail')))

        if is_single_subreddit:
            if is_moderator_with_perms('access'):
                buttons.append(NamedButton(
                    "moderators",
                    css_class="reddit-moderators",
                    data=data_attrs('moderators')))

                if not c.site.hide_contributors:
                    buttons.append(NavButton(
                        menu.contributors,
                        "contributors",
                        css_class="reddit-contributors access-required",
                        data=data_attrs('contributors')))

            buttons.append(NamedButton(
                "traffic",
                css_class="reddit-traffic access-required",
                data=data_attrs('traffic')))

        if is_moderator_with_perms('posts'):
            buttons.append(NamedButton(
                "modqueue",
                css_class="reddit-modqueue access-required",
                data=data_attrs('modqueue')))
            buttons.append(NamedButton(
                "reports",
                css_class="reddit-reported access-required",
                data=data_attrs('reports')))
            buttons.append(NamedButton(
                "spam",
                css_class="reddit-spam access-required",
                data=data_attrs('spam')))
            buttons.append(NamedButton(
                "edited",
                css_class="reddit-edited access-required",
                data=data_attrs('edited')))

        if is_single_subreddit:
            if is_moderator_with_perms('access'):
                buttons.append(NamedButton(
                    "banned",
                    css_class="reddit-ban access-required",
                    data=data_attrs('banned')))
            if is_moderator_with_perms('access', 'mail'):
                buttons.append(NamedButton(
                    "muted",
                    css_class="reddit-mute access-required",
                    data=data_attrs('muted')))
            if is_moderator_with_perms('flair'):
                buttons.append(NamedButton(
                    "flair",
                    css_class="reddit-flair access-required",
                    data=data_attrs('flair')))

        # append AutoMod button if it's enabled and they have perms to change it
        if (g.automoderator_account and
                is_single_subreddit and
                is_moderator_with_perms('config')):
            # link to their config if they have one, or the docs if not
            try:
                WikiPage.get(c.site, "config/automoderator")
                buttons.append(NamedButton(
                    "automod",
                    dest="../wiki/edit/config/automoderator",
                    css_class="reddit-automod access-required",
                    data=data_attrs('automoderator')))
            except tdb_cassandra.NotFound:
                buttons.append(NamedButton(
                    "new_automod",
                    sr_path=False,
                    dest="../wiki/automoderator",
                    css_class="reddit-automod access-required",
                ))

        buttons.append(NamedButton(
            "log",
            css_class="reddit-moderationlog access-required",
            data=data_attrs('moderationlog')))
        if is_moderator_with_perms('posts'):
            buttons.append(NamedButton(
                    "unmoderated",
                    css_class="reddit-unmoderated access-required",
                    data=data_attrs('unmoderated')))

        return SideContentBox(_('moderation tools'),
                              [NavMenu(buttons,
                                       type="flat_vert",
                                       base_path="/about/",
                                       css_class="icon-menu",
                                       separator="")],
                              _id="moderation_tools",
                              collapsible=True)

    def rightbox(self):
        """generates content in <div class="rightbox">"""

        ps = PaneStack(css_class='spacer')

        if self.searchbox:
            ps.append(SearchForm())

        sidebar_message = g.live_config.get("sidebar_message")
        if sidebar_message and isinstance(c.site, DefaultSR):
            ps.append(SidebarMessage(sidebar_message[0]))

        gold_sidebar_message = g.live_config.get("gold_sidebar_message")
        if (c.user_is_loggedin and c.user.gold and
                gold_sidebar_message and isinstance(c.site, DefaultSR)):
            ps.append(SidebarMessage(gold_sidebar_message[0],
                                     extra_class="gold"))

        if not c.user_is_loggedin and self.loginbox and not g.read_only_mode:
            ps.append(LoginFormWide())

        if isinstance(c.site, DomainSR) and c.user_is_admin:
            from r2.lib.pages.admin_pages import AdminNotesSidebar
            notebar = AdminNotesSidebar('domain', c.site.domain)
            ps.append(notebar)

        if isinstance(c.site, Subreddit) and c.user_is_admin:
            from r2.lib.pages.admin_pages import AdminNotesSidebar
            notebar = AdminNotesSidebar('subreddit', c.site.name)
            ps.append(notebar)

        if not c.user.pref_hide_ads or not c.user.gold:
            ps.append(SponsorshipBox())

        if (isinstance(c.site, Filtered) and not
            (isinstance(c.site, AllSR) and not c.user.gold)):
            ps.append(FilteredInfoBar())
        elif isinstance(c.site, AllSR):
            ps.append(AllInfoBar(c.site, c.user))
        elif isinstance(c.site, ModSR):
            ps.append(ModSRInfoBar())

        if isinstance(c.site, (MultiReddit, ModSR)):
            srs = Subreddit._byID(c.site.sr_ids, data=True,
                                  return_dict=False, stale=True)

            if (srs and c.user_is_loggedin and
                    (c.user_is_admin or c.site.is_moderator(c.user))):
                ps.append(self.sr_admin_menu())

            if isinstance(c.site, LabeledMulti):
                ps.append(MultiInfoBar(c.site, srs, c.user))
                c.js_preload.set_wrapped(
                    '/api/multi/%s' % c.site.path.lstrip('/'), c.site)
            elif srs:
                if isinstance(c.site, ModSR):
                    box = SubscriptionBox(srs, multi_text=strings.mod_multi)
                else:
                    box = SubscriptionBox(srs)
                ps.append(SideContentBox(_('these subreddits'), [box]))

        user_banned = c.user_is_loggedin and c.site.is_banned(c.user)

        if (self.submit_box
                and (c.user_is_loggedin or not g.read_only_mode)
                and not user_banned):
            if (not isinstance(c.site, FakeSubreddit)
                    and c.site.type in ("archived",
                                        "restricted",
                                        "gold_restricted")
                    and not (c.user_is_loggedin
                             and c.site.can_submit(c.user))):
                if c.site.type == "archived":
                    subtitle = _('this subreddit is archived '
                                 'and no longer accepting submissions.')
                    ps.append(SideBox(title=_('Submissions disabled'),
                                      css_class="submit",
                                      disabled=True,
                                      subtitles=[subtitle],
                                      show_icon=False))
                else:
                    if c.site.type == 'restricted':
                        subtitle = _('Only approved users may post in this '
                                     'community.')
                    elif c.site.type == 'gold_restricted':
                        subtitle = _('Anyone can view or comment, but only '
                                     'Reddit Gold members can post in this '
                                     'community.')
                    ps.append(SideBox(title=_('Submissions restricted'),
                                      css_class="submit",
                                      disabled=True,
                                      subtitles=[subtitle],
                                      show_icon=False))
            else:
                fake_sub = isinstance(c.site, FakeSubreddit)
                is_multi = isinstance(c.site, MultiReddit)
                mod_link_override = mod_self_override = False

                if isinstance(c.site, FakeSubreddit):
                    submit_buttons = set(("link", "self"))
                else:
                    # we want to show submit buttons for logged-out users too
                    # so we can't just use can_submit_link/text
                    submit_buttons = c.site.allowed_types

                    if c.user_is_loggedin:
                        if ("link" not in submit_buttons and
                                c.site.can_submit_link(c.user)):
                            submit_buttons.add("link")
                            mod_link_override = True
                        if ("self" not in submit_buttons and
                                c.site.can_submit_text(c.user)):
                            submit_buttons.add("self")
                            mod_self_override = True

                if "link" in submit_buttons:
                    css_class = "submit submit-link"
                    if mod_link_override:
                        css_class += " mod-override"
                    data_attrs = {
                        'type': 'subreddit',
                        'event-action': 'submit',
                        'event-detail': 'link',
                    }
                    ps.append(SideBox(title=c.site.submit_link_label or
                                            strings.submit_link_label,
                                      css_class=css_class,
                                      link="/submit",
                                      sr_path=not fake_sub or is_multi,
                                      data_attrs=data_attrs,
                                      show_cover=True))
                if "self" in submit_buttons:
                    css_class = "submit submit-text"
                    if mod_self_override:
                        css_class += " mod-override"
                    data_attrs = {
                        'type': 'subreddit',
                        'event-action': 'submit',
                        'event-detail': 'self',
                    }
                    ps.append(SideBox(title=c.site.submit_text_label or
                                            strings.submit_text_label,
                                      css_class=css_class,
                                      link="/submit?selftext=true",
                                      sr_path=not fake_sub or is_multi,
                                      data_attrs=data_attrs,
                                      show_cover=True))

        no_ads_yet = True
        user_disabled_ads = c.user.gold and c.user.pref_hide_ads
        show_adbox = c.site.allow_ads and not (user_disabled_ads or g.disable_ads)

        # don't show the subreddit info bar on cnames unless the option is set
        if not isinstance(c.site, FakeSubreddit):
            ps.append(SubredditInfoBar())
            moderator = c.user_is_loggedin and (c.user_is_admin or
                                          c.site.is_moderator(c.user))
            wiki_moderator = c.user_is_loggedin and (
                c.user_is_admin
                or c.site.is_moderator_with_perms(c.user, 'wiki'))
            if self.show_wiki_actions:
                menu = self.wiki_actions_menu(moderator=wiki_moderator)
                ps.append(menu)
            if moderator:
                ps.append(self.sr_admin_menu())
            if show_adbox:
                ps.append(Ads())
            no_ads_yet = False
        elif self.show_wiki_actions:
            ps.append(self.wiki_actions_menu())

        if self.create_reddit_box and c.user_is_loggedin:
            if (c.user._age.days >= g.min_membership_create_community and
                    c.user.can_create_subreddit):
                subtitles = get_funny_translated_string("create_subreddit", 2)
                data_attrs = {'event-action': 'createsubreddit'}
                ps.append(SideBox(_('Create your own subreddit'),
                           '/subreddits/create', 'create',
                           subtitles=subtitles,
                           data_attrs=data_attrs,
                           show_cover = True))

        if c.default_sr:
            hook = hooks.get_hook('home.add_sidebox')
            extra_sidebox = hook.call_until_return()
            if extra_sidebox:
                ps.append(extra_sidebox)

        if not isinstance(c.site, FakeSubreddit):
            moderator_ids = c.site.moderator_ids()
            if moderator_ids:
                sidebar_list_length = 10
                allow_stale = (not c.user_is_loggedin or
                    c.user._id not in moderator_ids)
                moderators = Account._byID(
                    moderator_ids[:sidebar_list_length], data=True,
                    return_dict=False, stale=allow_stale)
                num_not_shown = len(moderator_ids) - sidebar_list_length

                if num_not_shown > 0:
                    more_text = _("...and %d more") % (num_not_shown)
                else:
                    more_text = _("about moderation team")

                is_admin_sr = '/r/%s' % c.site.name == g.admin_message_acct

                if is_admin_sr:
                    label = _('message the admins')
                else:
                    label = _('message the moderators')

                wrapped_moderators = [WrappedUser(mod) for mod in moderators
                    if not mod._deleted]
                helplink = HelpLink(
                    "/message/compose?to=%%2Fr%%2F%s" % c.site.name,
                    label,
                    access_required=not is_admin_sr,
                    data_attrs={
                        'type': 'subreddit',
                        'fullname': c.site._fullname,
                        'event-action': 'compose',
                    })

                mod_href = c.site.path + 'about/moderators'
                ps.append(SideContentBox(_('moderators'),
                                         wrapped_moderators,
                                         helplink = helplink,
                                         more_href = mod_href,
                                         more_text = more_text))

        if no_ads_yet and show_adbox:
            ps.append(Ads())
            if g.live_config["gold_revenue_goal"]:
                ps.append(Goldvertisement())

        if c.user.pref_clickgadget and c.recent_clicks:
            ps.append(SideContentBox(_("Recently viewed links"),
                                     [ClickGadget(c.recent_clicks)]))

        if c.user_is_loggedin:
            activity_link = AccountActivityBox()
            ps.append(activity_link)

        return ps

    def render(self, *a, **kw):
        """Overrides default Templated.render with two additions
           * support for rendering API requests with proper wrapping
           * support for space compression of the result
        In adition, unlike Templated.render, the result is in the form of a pylons
        Response object with it's content set.
        """
        if c.bare_content:
            res = self.content().render()
        else:
            res = Templated.render(self, *a, **kw)

        return responsive(res, self.space_compress)

    def corner_buttons(self):
        """set up for buttons in upper right corner of main page."""
        buttons = []
        if c.user_is_loggedin:
            if c.user.name in g.admins:
                if c.user_is_admin:
                    buttons += [OffsiteButton(
                        _("turn admin off"),
                        dest="%s/adminoff?dest=%s" %
                            (g.https_endpoint, quote(request.fullpath)),
                        target = "_self",
                    )]
                else:
                    buttons += [OffsiteButton(
                        _("turn admin on"),
                        dest="%s/adminon?dest=%s" %
                            (g.https_endpoint, quote(request.fullpath)),
                        target = "_self",
                    )]
            buttons += [NamedButton("prefs", False,
                                  css_class = "pref-lang")]
        else:
            lang = c.lang.split('-')[0] if c.lang else ''
            lang_name = g.lang_name.get(lang) or [lang, '']
            lang_name = "".join(lang_name)
            buttons += [JsButton(lang_name,
                                 onclick = "return showlang();",
                                 css_class = "pref-lang")]
        return NavMenu(buttons, base_path = "/", type = "flatlist")

    def build_toolbars(self):
        """Sets the layout of the navigation topbar on a Reddit.  The result
        is a list of menus which will be rendered in order and
        displayed at the top of the Reddit."""
        if c.site == Friends:
            main_buttons = [NamedButton('new', dest='', aliases=['/hot']),
                            NamedButton('comments'),
                            NamedButton('gilded'),
                            ]
        else:
            main_buttons = [NamedButton('hot', dest='', aliases=['/hot']),
                            NamedButton('new'),
                            NamedButton('rising'),
                            NamedButton('controversial'),
                            NamedButton('top'),
                            ]

            if c.site.allow_gilding:
                main_buttons.append(NamedButton('gilded',
                                                aliases=['/comments/gilded']))

            mod = False
            if c.user_is_loggedin:
                mod = bool(c.user_is_admin
                           or c.site.is_moderator_with_perms(c.user, 'wiki'))
            if c.site._should_wiki and (c.site.wikimode != 'disabled' or mod):
                if not g.disable_wiki:
                    main_buttons.append(NavButton('wiki', 'wiki'))

            if (isinstance(c.site, (Subreddit, DefaultSR, MultiReddit)) and
                    c.site.allow_ads):
                main_buttons.append(NavButton(menu.promoted, 'ads'))

        more_buttons = []

        if c.user_is_loggedin and c.site.allow_ads:
            if c.user_is_sponsor:
                sponsor_button = NavButton(
                    menu.sponsor, dest='/sponsor', sr_path=False)
                more_buttons.append(sponsor_button)
            elif c.user.pref_show_promote:
                more_buttons.append(NavButton(menu.promote, 'promoted', False))

        #if there's only one button in the dropdown, get rid of the dropdown
        if len(more_buttons) == 1:
            main_buttons.append(more_buttons[0])
            more_buttons = []

        toolbar = [NavMenu(main_buttons, type='tabmenu')]
        if more_buttons:
            toolbar.append(NavMenu(more_buttons, title=menu.more, type='tabdrop'))

        if not isinstance(c.site, DefaultSR):
            func = 'subreddit'
            if isinstance(c.site, DomainSR):
                func = 'domain'
            toolbar.insert(0, PageNameNav(func))

        return toolbar

    def __repr__(self):
        return "<Reddit>"

    @staticmethod
    def content_stack(panes, css_class = None):
        """Helper method for reordering the content stack."""
        return PaneStack(filter(None, panes), css_class = css_class)

    def content(self):
        """returns a Wrapped (or renderable) item for the main content div."""
        if self.newsletterbar:
            self.welcomebar = None

        return self.content_stack((
            self.welcomebar,
            self.newsletterbar,
            self.infobar,
            self.locationbar,
            self.mobilewebredirectbar,
            self.nav_menu,
            self._content,
        ))

    def build_popup_panes(self):
        panes = []

        panes.append(Popup('archived-popup', ArchivedInterstitial()))

        if self.show_timeout_modal:
            popup_content = InTimeoutInterstitial(
                timeout_days_remaining=self.timeout_days_remaining,
                hide_message=True,
            )
            panes.append(Popup('access-popup', popup_content))

        return HtmlPaneStack(panes)

    def is_gold_page(self):
        return "gold-page-ga-tracking" in self.supplied_page_classes

    def page_classes(self):
        classes = set()

        if c.user_is_loggedin:
            classes.add('loggedin')
            if not isinstance(c.site, FakeSubreddit):
                if c.site.is_subscriber(c.user):
                    classes.add('subscriber')
                if c.site.is_contributor(c.user):
                    classes.add('contributor')
            if c.site.is_moderator(c.user):
                classes.add('moderator')
            if c.user.gold:
                classes.add('gold')
            if c.user.pref_highlight_controversial:
                classes.add('show-controversial')

        if c.user_is_admin:
            if not isinstance(c.site, FakeSubreddit) and c.site._spam:
                classes.add("banned")

        if isinstance(c.site, MultiReddit):
            classes.add('multi-page')

        if self.show_chooser:
            classes.add('with-listing-chooser')
            if c.user.pref_collapse_left_bar:
                classes.add('listing-chooser-collapsed')

        if c.user_is_loggedin and c.user.pref_compress:
            classes.add('compressed-display')

        if getattr(c.site, 'type', None) == 'gold_only':
            classes.add('gold-only')

        if getattr(c.site, 'quarantine', False):
            classes.add('quarantine')

        if self.extra_page_classes:
            classes.update(self.extra_page_classes)
        if self.supplied_page_classes:
            classes.update(self.supplied_page_classes)

        return classes


class DebugFooter(Templated):
    def __init__(self):
        if request.via_cdn:
            cdn_geoinfo = g.cdn_provider.get_client_location(request.environ)
            if cdn_geoinfo:
                c.location_info = "country code: %s" % cdn_geoinfo
        Templated.__init__(self)


class AccountActivityBox(Templated):
    def __init__(self):
        super(AccountActivityBox, self).__init__()


class RedditFooter(CachedTemplate):
    def cachable_attrs(self):
        return [('path', request.path),
                ('buttons', [[(x.title, x.path) for x in y] for y in self.nav])]

    def __init__(self):
        self.nav = [
            NavMenu([
                    NamedButton("blog", False, dest="/blog"),
                    OffsiteButton("about", "https://about.reddit.com/"),
                    NamedButton("source_code", False, dest="/code"),
                    NamedButton("advertising", False),
                    NamedButton("jobs", False),
                ],
                title = _("about"),
                type = "flat_vert",
                separator = ""),

            NavMenu([
                    NamedButton("rules", False),
                    OffsiteButton(_("FAQ"), "https://reddit.zendesk.com"),
                    NamedButton("wiki", False),
                    NamedButton("reddiquette", False, dest="/wiki/reddiquette"),
                    NamedButton("transparency", False, dest="/wiki/transparency"),
                    NamedButton("contact", False),
                ],
                title = _("help"),
                type = "flat_vert",
                separator = ""),

            NavMenu([
                    OffsiteButton(_("Reddit for iPhone"),
                        "https://itunes.apple.com/us/app/reddit-the-official-app/id1064216828?mt=8"),
                    OffsiteButton(_("Reddit for Android"),
                        "https://play.google.com/store/apps/details?id=com.reddit.frontpage"),
                    OffsiteButton(_("mobile website"), "https://m.reddit.com"),
                    NamedButton("buttons", False),
                ],
                title = _("apps & tools"),
                type = "flat_vert",
                separator = ""),

            NavMenu([
                    NamedButton("gold", False, dest="/gold/about", css_class="buygold"),
                    OffsiteButton(_("redditgifts"), "//redditgifts.com"),
                ],
                title = _("<3"),
                type = "flat_vert",
                separator = "")
        ]
        CachedTemplate.__init__(self)

class ClickGadget(Templated):
    def __init__(self, links, *a, **kw):
        self.links = links
        self.content = ''
        if c.user_is_loggedin and self.links:
            self.content = self.make_content()
        Templated.__init__(self, *a, **kw)

    def make_content(self):
        #this will disable the hardcoded widget styles
        request.GET["style"] = "off"
        wrapper = default_thing_wrapper(embed_voting_style = 'votable',
                                        style = "htmllite")
        content = wrap_links(self.links, wrapper = wrapper)

        return content.render(style = "htmllite")


class LoginFormWide(CachedTemplate):
    """generates a login form suitable for the 300px rightbox."""
    pass



class SubredditInfoBar(CachedTemplate):
    """When not on Default, renders a sidebox which gives info about
    the current reddit, including links to the moderator and
    contributor pages, as well as links to the banning page if the
    current user is a moderator."""

    def __init__(self, site = None):
        site = site or c.site

        # hackity hack. do i need to add all the others props?
        self.sr = list(wrap_links(site))[0]
        self.description_usertext = UserText(self.sr, self.sr.description)

        # we want to cache on the number of subscribers
        self.subscribers = self.sr._ups

        # so the menus cache properly
        self.path = request.path

        self.active_visitors = self.sr.count_activity()

        if c.user_is_loggedin and c.user.pref_show_flair:
            self.flair_prefs = FlairPrefs()
        else:
            self.flair_prefs = None

        self.sr_style_toggle = False
        self.use_subreddit_style = True

        self.quarantine = self.sr.quarantine

        has_custom_stylesheet = (self.sr.stylesheet_url or
            self.sr.stylesheet_url_https or self.sr.stylesheet_url_http)
        if (c.user_is_loggedin and
                (has_custom_stylesheet or self.sr.header) and
                feature.is_enabled('stylesheets_everywhere')):
            # defaults to c.user.pref_show_stylesheets if a match doesn't exist
            self.sr_style_toggle = True
            self.use_subreddit_style = c.user.use_subreddit_style(c.site)

        if c.user_is_admin and hasattr(self.sr, 'ban_info'):
            self.sr_ban_info = self.sr.ban_info

        CachedTemplate.__init__(self)

    @property
    def creator_text(self):
        if self.sr.author:
            if self.sr.is_moderator(self.sr.author) or self.sr.author._deleted:
                return WrappedUser(self.sr.author).render()
            else:
                return self.sr.author.name
        return None


class SponsorshipBox(Templated):
    pass


class HelpLink(Templated):
    def __init__(self, url, label, access_required=False, data_attrs={}):
        Templated.__init__(self, url=url, label=label,
                           access_required=access_required,
                           data_attrs=data_attrs)


class SideContentBox(Templated):
    def __init__(self, title, content, helplink=None, _id=None, extra_class=None,
                 more_href=None, more_text="more", collapsible=False):
        Templated.__init__(self, title=title, helplink = helplink,
                           content=content, _id=_id, extra_class=extra_class,
                           more_href = more_href, more_text = more_text,
                           collapsible=collapsible)

class SideBox(CachedTemplate):
    """
    Generic sidebox used to generate the 'submit' and 'create a reddit' boxes.
    """
    def __init__(self, title, link=None, css_class='', subtitles = [],
                 show_cover = False, sr_path = False,
                 disabled=False, show_icon=True, target='_top', data_attrs={}):
        CachedTemplate.__init__(self, link = link, target = target,
                                title = title, css_class = css_class,
                                sr_path = sr_path, subtitles = subtitles,
                                show_cover = show_cover,
                                disabled=disabled, show_icon=show_icon,
                                data_attrs=data_attrs)


class PrefsPage(Reddit):
    """container for pages accessible via /prefs.  No extension handling."""

    extension_handling = False

    def __init__(self, show_sidebar = False, title=None, *a, **kw):
        title = title or "%s (%s)" % (_("preferences"), c.site.name.strip(' '))
        Reddit.__init__(self, show_sidebar = show_sidebar,
                        title=title,
                        *a, **kw)

    def build_toolbars(self):
        buttons = [NavButton(menu.options, ''),
                   NamedButton('apps')]

        if c.user.pref_private_feeds:
            buttons.append(NamedButton('feeds'))

        buttons.extend([
            NamedButton('friends'),
            NamedButton('blocked'),
            NamedButton('update'),
        ])

        if c.user.name in g.admins:
            buttons += [NamedButton('security')]

        buttons += [NamedButton('deactivate')]

        return [PageNameNav('nomenu', title = _("preferences")),
                NavMenu(buttons, base_path = "/prefs", type="tabmenu")]


class PrefOptions(Templated):
    """Preference form for updating language and display options"""
    def __init__(self, done=False, error_style_override=None, generic_error=None):
        themes = []
        use_other_theme = True
        if feature.is_enabled('stylesheets_everywhere'):
            for theme in StylesheetsEverywhere.get_all():
                if theme.is_enabled:
                    themes.append(theme)
                if theme.id == c.user.pref_default_theme_sr:
                    use_other_theme = False
                    theme.checked = True

        feature_autoexpand_media_previews = feature.is_enabled("autoexpand_media_previews")
        Templated.__init__(self, done=done,
                error_style_override=error_style_override,
                feature_autoexpand_media_previews=feature_autoexpand_media_previews,
                generic_error=generic_error, themes=themes, use_other_theme=use_other_theme)


class PrefFeeds(Templated):
    pass

class PrefSecurity(Templated):
    pass


re_promoted = re.compile(r"/promoted.*", re.I)

class PrefUpdate(Templated):
    """Preference form for updating email address and passwords"""
    def __init__(self, email=True, password=True, verify=False, dest=None, subscribe=False):
        is_promoted = dest and re.match(re_promoted, urlparse(dest).path) != None
        self.email = email
        self.password = password
        self.verify = verify
        self.dest = dest
        self.subscribe = subscribe or is_promoted
        Templated.__init__(self)

class PrefApps(Templated):
    """Preference form for managing authorized third-party applications."""

    def __init__(self, my_apps, developed_apps):
        self.my_apps = my_apps
        self.developed_apps = developed_apps
        super(PrefApps, self).__init__()

    def render_developed_app(self, app, collapsed):
        base_template = self.template()
        developed_app_fn = base_template.get_def('developed_app')
        res = developed_app_fn.render(app, collapsed=collapsed)
        return spaceCompress(res)

    def render_editable_developer(self, app, dev):
        base_template = self.template()
        editable_developer_fn = base_template.get_def('editable_developer')
        res = editable_developer_fn.render(app, dev)
        return spaceCompress(res)


class PrefDeactivate(Templated):
    """Preference form for deactivating a user's own account."""
    def __init__(self):
        self.has_paypal_subscription = c.user.has_paypal_subscription
        if self.has_paypal_subscription:
            self.paypal_subscr_id = c.user.gold_subscr_id
            self.paypal_url = paypal_subscription_url()
        Templated.__init__(self)


class MessagePage(Reddit):
    """Defines the content for /message/*"""
    def __init__(self, *a, **kw):
        if not kw.has_key('show_sidebar'):
            kw['show_sidebar'] = False

        source = kw.pop("source", None)

        Reddit.__init__(self, *a, **kw)

        if is_api():
            self.replybox = None
        else:
            self.replybox = UserText(
                item=None,
                creating=True,
                post_form='comment',
                display=False,
                cloneable=True,
                source=source,
            )

    def content(self):
        return self.content_stack((self.replybox,
                                   self.infobar,
                                   self.nav_menu,
                                   self._content))

    def build_toolbars(self):
        if isinstance(c.site, MultiReddit):
            mod_srs = c.site.srs_with_perms(c.user, "mail")
            sr_path = bool(mod_srs)
        elif (not isinstance(c.site, FakeSubreddit) and
                c.site.is_moderator_with_perms(c.user, "mail")):
            sr_path = True
        else:
            sr_path = False

        buttons =  [NamedButton('compose', sr_path=sr_path),
                    NamedButton('inbox', aliases = ["/message/comments",
                                                    "/message/unread",
                                                    "/message/messages",
                                                    "/message/mentions",
                                                    "/message/selfreply"],
                                sr_path = False),
                    NamedButton('sent', sr_path = False)]
        if c.user_is_loggedin and c.user.is_moderator_somewhere:
            buttons.append(ModeratorMailButton(menu.modmail, "moderator",
                                               sr_path = False))
        if not c.default_sr:
            buttons.append(ModeratorMailButton(
                _("%(site)s mail") % {'site': c.site.name}, "moderator",
                aliases = ["/about/message/inbox",
                           "/about/message/unread"]))
        return [PageNameNav('nomenu', title = _("message")),
                NavMenu(buttons, base_path = "/message", type="tabmenu")]

class MessageCompose(Templated):
    """Compose message form."""
    def __init__(self, to='', subject='', message='', captcha=None,
                 admin_check=True, restrict_recipient=False):
        from r2.models.admintools import admintools

        if admin_check:
            self.admins = admintools.admin_list()

        Templated.__init__(self, to=to, subject=subject, message=message,
                           captcha=captcha, admin_check=admin_check,
                           restrict_recipient=restrict_recipient)


class ModeratorMessageCompose(MessageCompose):
    def __init__(self, mod_srs, only_as_subreddit=False, **kw):
        self.mod_srs = sorted(mod_srs, key=lambda sr: sr.name.lower())
        self.only_as_subreddit = only_as_subreddit
        MessageCompose.__init__(self, admin_check=False, **kw)


class BoringPage(Reddit):
    """parent class For rendering all sorts of uninteresting,
    sortless, navless form-centric pages.  The top navmenu is
    populated only with the text provided with pagename and the page
    title is 'reddit.com: pagename'"""

    extension_handling= False

    def __init__(self, pagename, css_class=None, **context):
        self.pagename = pagename
        name = c.site.name or g.default_sr
        if css_class:
            self.css_class = css_class
        if "title" not in context:
            context['title'] = "%s: %s" % (name, pagename)

        Reddit.__init__(self, **context)

    def build_toolbars(self):
        if not isinstance(c.site, DefaultSR):
            return [PageNameNav('subreddit', title = self.pagename)]
        else:
            return [PageNameNav('nomenu', title = self.pagename)]

class HelpPage(BoringPage):
    def build_toolbars(self):
        return [PageNameNav('help', title = self.pagename)]

class FormPage(BoringPage):
    create_reddit_box  = False
    submit_box         = False
    """intended for rendering forms with no rightbox needed or wanted"""
    def __init__(self, pagename, show_sidebar = False, *a, **kw):
        BoringPage.__init__(self, pagename,  show_sidebar = show_sidebar,
                            *a, **kw)

class LoginPage(BoringPage):
    enable_login_cover = False
    short_title = "log in"

    """a boring page which provides the Login/register form"""
    def __init__(self, **context):
        self.dest = context.get('dest', '')
        context['loginbox'] = False
        context['show_sidebar'] = False
        context['page_classes'] = ['login-page']

        if c.render_style == "compact":
            title = self.short_title
        else:
            title = _("sign up or log in")

        BoringPage.__init__(self, title, **context)

        if self.dest:
            u = UrlParser(self.dest)
            # Display a preview message for OAuth2 client authorizations
            if u.path in ['/api/v1/authorize', '/api/v1/authorize.compact']:
                client_id = u.query_dict.get("client_id")
                self.client = client_id and OAuth2Client.get_token(client_id)
                if self.client:
                    self.infobar = ClientInfoBar(self.client,
                                                 strings.oauth_login_msg)
                else:
                    self.infobar = None

    def content(self):
        kw = {}
        for x in ('user_login', 'user_reg'):
            kw[x] = getattr(self, x) if hasattr(self, x) else ''
        login_content = self.login_template(dest = self.dest, **kw)
        return self.content_stack((self.infobar, login_content))

    @classmethod
    def login_template(cls, **kw):
        return Login(**kw)

class RegisterPage(LoginPage):
    short_title = "sign up"
    @classmethod
    def login_template(cls, **kw):
        return Register(**kw)


class Login(Templated):
    """The two-unit login and register form."""
    def __init__(self, user_reg = '', user_login = '', dest='', is_popup=False):
        Templated.__init__(self, user_reg = user_reg, user_login = user_login,
                           dest = dest, captcha = Captcha(),
                           is_popup=is_popup,
                           registration_info=RegistrationInfo())

class Register(Login):
    pass


class RegistrationInfo(Templated):
    def __init__(self):
        html = unsafe(self.get_registration_info_html())
        Templated.__init__(self, content_html=html)

    @classmethod
    @memoize('registration_info_html', stale=True, time=10*60)
    def get_registration_info_html(cls):
        try:
            wp = WikiPage.get(Frontpage, g.wiki_page_registration_info)
        except tdb_cassandra.NotFound:
            return ''
        else:
            return wikimarkdown(wp.content, include_toc=False, target='_blank')


class OAuth2AuthorizationPage(BoringPage):
    show_mobilewebredirectbar = False

    def __init__(self, client, redirect_uri, scope, state, duration,
                 response_type):
        if duration == "permanent":
            expiration = None
        else:
            expiration = (
                datetime.datetime.now(g.tz)
                + datetime.timedelta(seconds=OAuth2AccessToken._ttl + 1))
        content = OAuth2Authorization(client=client,
                                      redirect_uri=redirect_uri,
                                      scope=scope,
                                      state=state,
                                      duration=duration,
                                      expiration=expiration,
                                      response_type=response_type,
                                      )
        BoringPage.__init__(self, _("request for permission"),
                            show_sidebar=False, content=content,
                            short_title=_("permission"))

class OAuth2Authorization(Templated):
    pass

class SearchPage(BoringPage):
    """Search results page"""
    searchbox = False
    extra_page_classes = ['search-page']

    def __init__(self, pagename, prev_search,
                 search_params={},
                 simple=False, restrict_sr=False, site=None,
                 syntax=None, converted_data=None, facets={}, sort=None,
                 recent=None, subreddits=None,
                 *a, **kw):
        if not (feature.is_enabled('legacy_search') or c.user.pref_legacy_search):
            self.extra_page_classes = self.extra_page_classes + ['combined-search-page']
        self.searchbar = SearchBar(prev_search=prev_search,
                                   search_params=search_params,
                                   site=site,
                                   simple=simple, restrict_sr=restrict_sr,
                                   syntax=syntax, converted_data=converted_data)
        self.subreddits = subreddits

        # generate the over18 redirect url for the current search if needed
        if kw['nav_menus'] and not c.over18 and feature.is_enabled('safe_search'):
            u = UrlParser(add_sr('/search'))
            if prev_search:
                u.update_query(q=prev_search)
            if restrict_sr:
                u.update_query(restrict_sr='on')
            u.update_query(**search_params)
            u.update_query(over18='yes')
            over18_url = u.unparse()
            kw['nav_menus'].append(MenuLink(title=_('enable NSFW results'),
                                            url=over18_url))

        self.sr_facets = SubredditFacets(prev_search=prev_search, facets=facets,
                                         sort=sort, recent=recent)
        BoringPage.__init__(self, pagename, robots='noindex', *a, **kw)

    def content(self):
        if feature.is_enabled('legacy_search') or c.user.pref_legacy_search:
            return self.content_stack((self.searchbar, self.sr_facets, self.infobar,
                                   self.nav_menu, self.subreddits, self._content))

        return self.content_stack((self.searchbar, self.infobar,
                                   self.subreddits, self._content,
                                   self.sr_facets))


class MenuLink(Templated):
    pass


class TakedownPage(BoringPage):
    def __init__(self, link):
        BoringPage.__init__(self, getattr(link, "takedown_title", _("bummer")),
                            content = TakedownPane(link))

    def render(self, *a, **kw):
        response = BoringPage.render(self, *a, **kw)
        return response


class TakedownPane(Templated):
    def __init__(self, link, *a, **kw):
        self.link = link
        self.explanation = getattr(self.link, "explanation",
                                   _("this page is no longer available due to a copyright claim."))
        Templated.__init__(self, *a, **kw)


class CommentVisitsBox(Templated):
    def __init__(self, visits, *a, **kw):
        self.visits = list(reversed(visits))
        Templated.__init__(self, *a, **kw)

class LinkInfoPage(Reddit):
    """Renders the varied /info pages for a link.  The Link object is
    passed via the link argument and the content passed to this class
    will be rendered after a one-element listing consisting of that
    link object.

    In addition, the rendering is reordered so that any nav_menus
    passed to this class will also be rendered underneath the rendered
    Link.
    """

    create_reddit_box = False
    extra_page_classes = ['single-page']
    metadata_image_widths = (320, 216)

    def __init__(self, link = None, comment = None, disable_comments=False,
                 link_title = '', subtitle = None, num_duplicates = None,
                 show_promote_button=False, sr_detail=False,
                 campaign_fullname=promote.NO_CAMPAIGN, click_url=None,
                 *a, **kw):

        c.permalink_page = True
        expand_children = kw.get("expand_children", not bool(comment))

        wrapper = default_thing_wrapper(expand_children=expand_children)


        # link_listing will be the one-element listing at the top
        self.link_listing = wrap_links(link, wrapper=wrapper, sr_detail=sr_detail)
        things = self.link_listing.things
        self.link = things[0]

        # add click tracker
        self.link.campaign = campaign_fullname

        # don't need to track clicks on self posts since they've
        # already been clicked at this point
        if not self.link.is_self:
            promote.add_trackers(
                things,
                c.site,
                adserver_click_urls={campaign_fullname: click_url},
            )

        self.disable_comments = disable_comments

        if promote.is_promo(self.link) and not promote.is_promoted(self.link):
            self.link.votable = False

        link_title = ((self.link.title) if hasattr(self.link, 'title') else '')

        # defaults whether or not there is a comment
        params = {'title':_force_unicode(link_title), 'site' : c.site.name}
        title = strings.link_info_title % params
        short_description = None
        if link and link.selftext and not (link._spam or link._deleted):
            short_description = _truncate(link.selftext.strip(), MAX_DESCRIPTION_LENGTH)
        # only modify the title if the comment/author are neither deleted nor spam
        if comment and not comment._deleted and not comment._spam:
            author = Account._byID(comment.author_id, data=True)

            if not author._deleted and not author._spam:
                params = {'author' : author.name, 'title' : _force_unicode(link_title)}
                title = strings.permalink_title % params
                short_description = _truncate(comment.body.strip(), MAX_DESCRIPTION_LENGTH) if comment.body else None

        self.subtitle = subtitle

        if hasattr(self.link, "shortlink"):
            self.shortlink = self.link.shortlink

        self.og_data = self._build_og_data(
            _force_unicode(link_title),
            short_description,
        )

        self.twitter_card = self._build_twitter_card_data(
            _force_unicode(link_title),
            short_description,
        )
        hook = hooks.get_hook('comments_page.twitter_card')
        hook.call(tags=self.twitter_card, sr_name=c.site.name,
                  id36=self.link._id36)

        if hasattr(self.link, "dart_keyword"):
            c.custom_dart_keyword = self.link.dart_keyword

        # if we're already looking at the 'duplicates' page, we can
        # avoid doing this lookup twice
        if num_duplicates is None:
            builder = url_links_builder(self.link.url,
                                        exclude=self.link._fullname,
                                        public_srs_only=True)
            self.num_duplicates = len(builder.get_items()[0])
        else:
            self.num_duplicates = num_duplicates

        self.show_promote_button = show_promote_button
        if link._deleted or link._spam:
            robots = "noindex,nofollow"
        elif comment:
            # We don't want crawlers to index the comment permalink pages.
            robots = "noindex"
        else:
            robots = None

        if 'extra_js_config' not in kw:
            kw['extra_js_config'] = {}

        kw['extra_js_config'].update({
            "cur_link": link._fullname,
        });

        if c.can_embed:
            from r2.lib import embeds
            kw['extra_js_config'].update({
                "embed_inject_template": websafe(embeds.get_inject_template()),
            })

        Reddit.__init__(self, title = title, short_description=short_description, robots=robots, *a, **kw)

    def _build_og_data(self, link_title, meta_description):
        sr_fragment = "/r/" + c.site.name if not c.default_sr else get_domain()
        data = {
            "site_name": "reddit",
            "title": u"%s • %s" % (link_title, sr_fragment),
            "description": self._build_og_description(meta_description),
            "ttl": "600",  # re-fetch frequently to update vote/comment count
        }
        if not self.link.nsfw:
            image_data = self._build_og_image()
            for key, value in image_data.iteritems():
                # Although the spec[0] and their docs[1] say 'og:image' and
                # 'og:image:url' are equivalent, Facebook doesn't actually take
                # the thumbnail from the latter form.  Even if that gets fixed,
                # it's likely the authors of other scrapers haven't read the
                # spec in-depth, either, so we'll just keep on doing the more
                # well-supported thing.
                #
                # [0]: http://ogp.me/#structured
                # [1]: https://developers.facebook.com/docs/sharing/webmasters#images
                if key == 'url':
                    data['image'] = value
                else:
                    data["image:%s" % key] = value

        return data

    def _build_og_image(self):
        if self.link.media_object:
            media_embed = media.get_media_embed(self.link.media_object)
            if media_embed and media_embed.public_thumbnail_url:
                return {
                    'url': media_embed.public_thumbnail_url,
                    'width': media_embed.width,
                    'height': media_embed.height,
                }

        if self.link.url and url_is_embeddable_image(self.link.url):
            return {'url': self.link.url}

        preview_object = self.link.preview_image
        if preview_object:
            for width in self.metadata_image_widths:
                try:
                    return {
                        'url': g.image_resizing_provider.resize_image(
                                    preview_object, width),
                        'width': width,
                    }
                except image_resizing.NotLargeEnough:
                    pass

        if self.link.has_thumbnail and self.link.thumbnail:
            # This is really not a great thumbnail for facebook right now
            # because it's so small, but it's better than nothing.
            data = {'url': self.link.thumbnail}

            # Some old posts don't have a recorded size for whatever reason, so
            # let's just ignore dimensions for them.
            if hasattr(self.link, 'thumbnail_size'):
                width, height = self.link.thumbnail_size
                data['width'] = width
                data['height'] = height
            return data

        # Default to the reddit icon if we've got nothing else.  Force it to be
        # absolute because not all scrapers handle relative protocols or paths
        # well.
        return {'url': static('icon.png', absolute=True)}

    def _build_og_description(self, meta_description):
        if self.link.selftext:
            return meta_description

        return strings.link_info_og_description % {
            "score": self.link.score,
            "num_comments": self.link.num_comments,
        }

    def _build_twitter_card_data(self, link_title, meta_description):
        """Build a set of data for Twitter's Summary Cards:
        https://dev.twitter.com/cards/types/summary
        https://dev.twitter.com/cards/markup
        """

        # Twitter limits us to 70 characters for the title.  Even though it's
        # at the end, we'd like to always show the whole subreddit name, so
        # let's truncate the title while still ensuring the entire thing is
        # under the limit.
        sr_fragment = u" • /r/" + c.site.name if not c.default_sr else get_domain()
        max_link_title_length = 70 - len(sr_fragment)

        return {
            "site": "reddit", # The twitter account of the site.
            "card": "summary",
            "title": _truncate(link_title, max_link_title_length) + sr_fragment
            # Twitter will fall back to any defined OpenGraph attributes, so we
            # don't need to define 'twitter:image' or 'twitter:description'.
        }

    def build_toolbars(self):
        base_path = "/%s/%s/" % (self.link._id36, title_to_url(self.link.title))
        base_path = _force_utf8(base_path)


        def info_button(name, **fmt_args):
            return NamedButton(name, dest = '/%s%s' % (name, base_path),
                               aliases = ['/%s/%s' % (name, self.link._id36)],
                               fmt_args = fmt_args)
        buttons = []
        if not self.disable_comments:
            buttons.append(info_button('comments'))

            if self.num_duplicates > 0:
                buttons.append(info_button('duplicates', num=self.num_duplicates))

        if self.show_promote_button:
            buttons.append(NavButton(menu.promote, 'promoted', sr_path=False))

        toolbar = [NavMenu(buttons, base_path = "", type="tabmenu")]

        if not isinstance(c.site, DefaultSR):
            toolbar.insert(0, PageNameNav('subreddit'))

        if c.user_is_admin:
            from admin_pages import AdminLinkMenu
            toolbar.append(AdminLinkMenu(self.link))

        return toolbar

    def content(self):
        if self.disable_comments:
            comment_area = InfoBar(message=_("comments disabled"))
        else:
            panes = [self.nav_menu, self._content]

            comment_area = PaneStack([
                PaneStack(
                    panes
                )],
                title=self.subtitle,
                title_buttons=getattr(self, "subtitle_buttons", []),
                css_class="commentarea",
            )

        return self.content_stack((
            self.infobar,
            self.link_listing,
            comment_area,
            self.popup_panes,
        ))

    def build_popup_panes(self):
        panes = super(LinkInfoPage, self).build_popup_panes()

        if self.link.locked:
            panes.append(Popup('locked-popup', LockedInterstitial()))

        return panes


    def rightbox(self):
        rb = Reddit.rightbox(self)

        if (c.site and not c.default_sr and c.render_style == 'html' and
                feature.is_enabled('read_next')):
            link = self.link

            def wrapper_fn(thing):
                w = Wrapped(thing)
                w.render_class = ReadNextLink
                return w

            query_obj = c.site.get_links('hot', 'all')
            builder = IDBuilder(query_obj,
                                wrap=wrapper_fn,
                                skip=True, num=10)
            listing = ReadNextListing(builder).listing()
            if len(listing.things):
                rb.append(ReadNext(c.site, listing.render()))

        if not (self.link.promoted and not c.user_is_sponsor):
            if c.user_is_admin:
                from admin_pages import AdminLinkInfoBar
                rb.insert(1, AdminLinkInfoBar(a=self.link))
            else:
                rb.insert(1, LinkInfoBar(a=self.link))
        return rb

    def page_classes(self):
        classes = Reddit.page_classes(self)

        if self.link.flair_css_class:
            for css_class in self.link.flair_css_class.split():
                classes.add('post-linkflair-' + css_class)

        if c.user_is_loggedin and self.link.author == c.user:
            classes.add("post-submitter")

        time_ago = datetime.datetime.now(g.tz) - self.link._date
        delta = datetime.timedelta
        steps = [
            delta(minutes=10),
            delta(hours=6),
            delta(hours=24),
        ]
        for step in steps:
            if time_ago < step:
                if step < delta(hours=1):
                    step_str = "%dm" % (step.total_seconds() / 60)
                else:
                    step_str = "%dh" % (step.total_seconds() / (60 * 60))
                classes.add("post-under-%s-old" % step_str)

        return classes

class LinkCommentSep(Templated):
    pass

class CommentPane(Templated):
    def cache_key(self):
        num = self.article.num_comments
        # bit of triage: we don't care about 10% changes in comment
        # trees once they get to a certain length.  The cache is only a few
        # min long anyway.
        if num > 1000:
            num = (num / 100) * 100
        elif num > 100:
            num = (num / 10) * 10

        cache_key_args = [
            self.article._fullname,
            self.article.contest_mode,
            self.article.locked,
            num,
            self.sort,
            self.num,
            c.lang,
            self.can_reply,
            c.render_style,
            c.domain_prefix,
            c.secure,
            c.user.pref_show_flair,
            c.can_embed,
            self.max_depth,
            self.edits_visible,
        ]

        if feature_utils.is_tracking_link_enabled(self.article):
            cache_key_args.append("utm_comment_links")

        _id = make_key_id(*cache_key_args)
        key = "pane:%s" % _id
        return key

    def __init__(self, article, sort, comment, context, num, **kw):
        from r2.models import Builder, CommentBuilder, NestedListing
        from r2.controllers.reddit_base import UnloggedUser

        self.sort = sort
        self.num = num
        self.article = article

        self.max_depth = kw.get('max_depth')
        self.edits_visible = kw.get("edits_visible")

        is_html = c.render_style == "html"

        if is_html:
            timer = g.stats.get_timer("service_time.CommentPaneCache")
        else:
            timer = g.stats.get_timer(
                "service_time.CommentPaneCache.%s" % c.render_style)
        timer.start()

        try_cache = (
            not comment and
            not context and
            is_html and
            not c.user_is_admin and
            not (c.user_is_loggedin and c.user._id == article.author_id)
        )

        if c.user_is_loggedin:
            sr = article.subreddit_slow
            try_cache &= not bool(sr.can_ban(c.user))

            user_threshold = c.user.pref_min_comment_score
            default_threshold = Account._defaults["pref_min_comment_score"]
            try_cache &= user_threshold == default_threshold

        if c.user_is_loggedin:
            self.can_reply = article.can_comment_slow(c.user)
        else:
            # assume that the common case is for loggedin users to see reply
            # buttons and do the same for loggedout users so they can use the
            # same cached page. reply buttons will be hidden client side for
            # loggedout users
            self.can_reply = not article.archived_slow and not article.locked

        builder = CommentBuilder(
            article, sort, comment=comment, context=context, num=num, **kw)

        if try_cache and c.user_is_loggedin:
            builder._get_comments()
            timer.intermediate("build_comments")
            for comment in builder.comments:
                if comment.author_id == c.user._id:
                    try_cache = False
                    break

        if not try_cache:
            listing = NestedListing(builder, parent_name=article._fullname)
            listing_for_user = listing.listing()
            timer.intermediate("build_listing")
            self.rendered = listing_for_user.render()
            timer.intermediate("render_listing")
        else:
            g.log.debug("using comment page cache")
            key = self.cache_key()
            self.rendered = g.commentpanecache.get(key)

            if self.rendered:
                cache_hit = True

                if c.user_is_loggedin:
                    # don't need the builder to make a listing so stop its timer
                    builder.timer.stop("waiting")

            else:
                cache_hit = False

                # spoof an unlogged in user
                user = c.user
                logged_in = c.user_is_loggedin
                try:
                    c.user = UnloggedUser([c.lang])
                    # Preserve the viewing user's flair preferences.
                    c.user.pref_show_flair = user.pref_show_flair

                    c.user_is_loggedin = False

                    # make the comment listing. if the user is loggedin we
                    # already made the builder retrieve/build the comment tree
                    # and lookup the comments.
                    listing = NestedListing(
                        builder, parent_name=article._fullname)
                    generic_listing = listing.listing()

                    if logged_in:
                        timer.intermediate("build_listing")
                    else:
                        timer.intermediate("build_comments_and_listing")

                    self.rendered = generic_listing.render()
                    timer.intermediate("render_listing")
                finally:
                    # undo the spoofing
                    c.user = user
                    c.user_is_loggedin = logged_in

                try:
                    g.commentpanecache.set(
                        key,
                        self.rendered,
                        time=g.commentpane_cache_time
                    )
                except MemcachedError as e:
                    g.log.warning("Ignored exception (%r) on commentpane "
                                  "write for %r", e, request.path)

            # figure out what needs to be updated on the listing
            if c.user_is_loggedin:
                updates = []

                # wrap the comments so the builder will customize them for
                # the loggedin user
                wrapped_for_user = Builder.wrap_items(builder, builder.comments)
                timer.intermediate("wrap_comments_for_user")

                for t in wrapped_for_user:
                    if not hasattr(t, "likes"):
                        # this is for MoreComments and MoreRecursion
                        continue

                    is_friend = (getattr(t, "friend", False) and
                                 not t.author._deleted)
                    is_enemy = getattr(t, "enemy", False)

                    update = {}
                    if is_friend:
                        update['friend'] = True
                    if is_enemy:
                        update['enemy'] = True
                    if t.likes:
                        update['voted'] = 1
                    if t.likes is False:
                        update['voted'] = -1
                    if t.saved:
                        update['saved'] = True
                    if t.user_gilded:
                        update['gilded'] = (t.gilded_message, t.gildings)

                    if update:
                        update['id'] = t._fullname
                        updates.append(update)

                self.rendered += ThingUpdater(updates=updates).render()
                timer.intermediate("thingupdater")

        if try_cache:
            if cache_hit:
                timer.stop("hit")
            else:
                timer.stop("miss")
        else:
            timer.stop("uncached")

    def listing_iter(self, l):
        for t in l:
            yield t
            for x in self.listing_iter(getattr(t, "child", [])):
                yield x

    def render(self, *a, **kw):
        return self.rendered

class ThingUpdater(Templated):
    pass


class LinkInfoBar(Templated):
    """Right box for providing info about a link."""
    def __init__(self, a = None):
        if a:
            a = Wrapped(a)
        Templated.__init__(self, a = a, datefmt = datefmt)

class EditReddit(Reddit):
    """Container for the about page for a reddit"""
    extension_handling= False

    def __init__(self, *a, **kw):
        from r2.lib.menus import menu

        try:
            key = kw.pop("location")
            title = menu[key]
        except KeyError:
            is_moderator = c.user_is_loggedin and \
                c.site.is_moderator(c.user) or c.user_is_admin

            title = (_('subreddit settings') if is_moderator else
                     _('about %(site)s') % dict(site=c.site.name))

        Reddit.__init__(self, title=title, *a, **kw)

    def build_toolbars(self):
        return [PageNameNav('subreddit', title=self.title)]


class SubredditsPage(Reddit):
    """container for rendering a list of reddits.  The corner
    searchbox is hidden and its functionality subsumed by an in page
    SearchBar for searching over reddits.  As a result this class
    takes the same arguments as SearchBar, which it uses to construct
    self.searchbar"""
    searchbox    = False
    submit_box   = False
    def __init__(self, prev_search = '',
                 title = '', loginbox = True, infotext = None, show_interestbar=False,
                 search_params = {}, *a, **kw):
        Reddit.__init__(self, title = title, loginbox = loginbox, infotext = infotext,
                        *a, **kw)
        self.searchbar = SearchBar(
            prev_search = prev_search,
            header=_('search subreddits by name'),
            search_params={},
            simple=True,
            subreddit_search=True,
            search_path="/subreddits/search",
        )
        self.sr_infobar = InfoBar(message = strings.sr_subscribe)
        self.interestbar = InterestBar(True) if show_interestbar else None

    def build_toolbars(self):
        buttons =  [NavButton(menu.popular, ""),
                    NamedButton("new")]
        if c.user_is_admin:
            buttons.append(NamedButton("banned"))
        if c.user.employee:
            buttons.append(NamedButton("employee"))
        if c.user.gold or c.user.gold_charter:
            buttons.append(NamedButton("gold"))
        if c.user_is_admin:
            buttons.append(NamedButton("quarantine"))
        if c.user_is_admin:
            buttons.append(NamedButton("featured"))
        if c.user_is_loggedin:
            #add the aliases to "my reddits" stays highlighted
            buttons.append(NamedButton("mine",
                                       aliases=['/subreddits/mine/subscriber',
                                                '/subreddits/mine/contributor',
                                                '/subreddits/mine/moderator']))

        return [PageNameNav('subreddits'),
                NavMenu(buttons, base_path = '/subreddits', type="tabmenu")]

    def content(self):
        return self.content_stack((self.interestbar, self.searchbar,
                                   self.nav_menu, self.sr_infobar,
                                   self._content))

    def rightbox(self):
        ps = Reddit.rightbox(self)
        srs = Subreddit.user_subreddits(c.user, ids=False, limit=None)
        srs.sort(key=lambda sr: sr.name.lower())
        subscribe_box = SubscriptionBox(srs,
                                        multi_text=strings.subscribed_multi)
        num_reddits = len(subscribe_box.srs)
        ps.append(SideContentBox(_("your front page subreddits (%s)") %
                                 num_reddits, [subscribe_box]))
        return ps

class MySubredditsPage(SubredditsPage):
    """Same functionality as SubredditsPage, without the search box."""

    def content(self):
        return self.content_stack((self.nav_menu, self.infobar, self._content))


def votes_visible(user):
    """Determines whether to show/hide a user's votes.  They are visible:
     * if the current user is the user in question
     * if the user has a preference showing votes
     * if the current user is an administrator
    """
    return ((c.user_is_loggedin and c.user.name == user.name) or
            user.pref_public_votes or
            c.user_is_admin)


class ProfilePage(Reddit):
    """Container for a user's profile page.  As such, the Account
    object of the user must be passed in as the first argument, along
    with the current sub-page (to determine the title to be rendered
    on the page)"""

    searchbox         = False
    create_reddit_box = False
    submit_box        = False
    extra_page_classes = ['profile-page']

    def __init__(self, user, *a, **kw):
        self.user     = user
        Reddit.__init__(self, *a, **kw)

    def build_toolbars(self):
        path = "/user/%s/" % self.user.name
        main_buttons = [NavButton(menu.overview, '/', aliases = ['/overview']),
                   NamedButton('comments'),
                   NamedButton('submitted'),
                   NamedButton('gilded')]

        if votes_visible(self.user):
            main_buttons += [
                NamedButton('upvoted'),
                NamedButton('downvoted'),
            ]

        if c.user_is_loggedin and (c.user._id == self.user._id or
                                   c.user_is_admin):
            main_buttons += [NamedButton('hidden'), NamedButton('saved')]

        if c.user_is_sponsor:
            main_buttons += [NamedButton('promoted')]

        toolbar = [PageNameNav('nomenu', title = self.user.name),
                   NavMenu(main_buttons, base_path = path, type="tabmenu")]

        if c.user_is_admin:
            from admin_pages import AdminProfileMenu
            toolbar.append(AdminProfileMenu(path))

        return toolbar

    def page_classes(self):
        classes = Reddit.page_classes(self)

        if c.user_is_admin:
            if self.user.in_timeout:
                if self.user.timeout_expiration:
                    classes.add("user-in-timeout-temp")
                else:
                    classes.add("user-in-timeout-perma")
            if self.user._spam:
                classes.add("user-spam")
            if self.user._banned:
                classes.add("user-banned")
            if self.user._deleted:
                classes.add("user-deleted")

        return classes

    def rightbox(self):
        rb = Reddit.rightbox(self)

        tc = TrophyCase(self.user)
        helplink = HelpLink("/wiki/awards", _("what's this?"))
        scb = SideContentBox(title=_("trophy case"),
                 helplink=helplink, content=[tc],
                 extra_class="trophy-area")

        rb.push(scb)

        multis = LabeledMulti.by_owner(self.user, load_subreddits=False)

        public_multis = [m for m in multis if m.is_public()]
        if public_multis:
            scb = SideContentBox(title=_("public multireddits"), content=[
                SidebarMultiList(public_multis)
            ])
            rb.push(scb)

        hidden_multis = [m for m in multis if m.is_hidden()]
        if c.user == self.user and hidden_multis:
            scb = SideContentBox(title=_("hidden multireddits"), content=[
                SidebarMultiList(hidden_multis)
            ])
            rb.push(scb)

        if c.user_is_admin:
            from r2.lib.pages.admin_pages import AdminNotesSidebar
            from admin_pages import AdminSidebar

            rb.push(AdminSidebar(self.user))
            rb.push(AdminNotesSidebar('user', self.user.name))
        elif c.user_is_sponsor:
            from admin_pages import SponsorSidebar
            rb.push(SponsorSidebar(self.user))

        mod_sr_ids = Subreddit.reverse_moderator_ids(self.user)
        all_mod_srs = Subreddit._byID(mod_sr_ids, data=True,
                                      return_dict=False, stale=True)
        mod_srs = [sr for sr in all_mod_srs if sr.can_view_in_modlist(c.user)]
        if mod_srs:
            rb.push(SideContentBox(title=_("moderator of"),
                                   content=[SidebarModList(mod_srs)]))

        if (c.user == self.user or c.user.employee or
            self.user.pref_public_server_seconds):
            seconds_bar = ServerSecondsBar(self.user)
            if seconds_bar.message or seconds_bar.gift_message:
                rb.push(seconds_bar)

        rb.push(ProfileBar(self.user))

        return rb

class TrophyCase(Templated):
    def __init__(self, user):
        self.user = user
        self.trophies = []
        self.invisible_trophies = []

        for trophy in Trophy.by_account(user):
            if trophy._thing2.awardtype == 'invisible':
                self.invisible_trophies.append(trophy)
            else:
                self.trophies.append(trophy)

        Templated.__init__(self)


class SidebarMultiList(Templated):
    def __init__(self, multis):
        Templated.__init__(self)
        multis.sort(key=lambda multi: multi.name.lower())
        self.multis = multis


class SidebarModList(Templated):
    def __init__(self, subreddits):
        Templated.__init__(self)
        # primary sort is desc. subscribers, secondary is name
        self.subreddits = sorted(subreddits,
                                 key=lambda sr: (-sr._ups, sr.name.lower()))


class ProfileBar(Templated):
    """Draws a right box for info about the user (karma, etc)"""
    def __init__(self, user):
        Templated.__init__(self, user=user)
        if c.user_is_loggedin:
            self.viewing_self = user._id == c.user._id
            self.show_private_info = self.viewing_self or c.user_is_admin
        else:
            self.viewing_self = False
            self.show_private_info = False

        self.show_users_gold_expiration = (self.show_private_info or
            user.pref_show_gold_expiration) and user.gold
        self.show_private_gold_info = (self.show_private_info and
            (user.gold or user.gold_creddits > 0 or user.num_gildings > 0))

        if self.show_users_gold_expiration:
            gold_days_left = (user.gold_expiration -
                              datetime.datetime.now(g.tz)).days

            if gold_days_left < 1:
                self.gold_remaining = _("less than a day")
            else:
                # Round remaining gold to number of days
                precision = 60 * 60 * 24
                self.gold_remaining = timeuntil(user.gold_expiration,
                                                precision)

        if c.user_is_loggedin:
            if user.gold and self.show_private_info:
                if user.has_paypal_subscription:
                    self.paypal_subscr_id = user.gold_subscr_id
                    self.paypal_url = paypal_subscription_url()
                if user.has_stripe_subscription:
                    self.stripe_customer_id = user.gold_subscr_id

            if user.gold_creddits > 0 and self.show_private_info:
                msg = ungettext("%(creddits)s gold creddit to give",
                                "%(creddits)s gold creddits to give",
                                user.gold_creddits)
                msg = msg % dict(creddits=user.gold_creddits)
                self.gold_creddit_message = msg

            if user.num_gildings > 0 and self.show_private_info:
                gildings_msg = ungettext(
                    "%(gildings)s gilding given out",
                    "%(gildings)s gildings given out",
                    user.num_gildings)
                gildings_msg = gildings_msg % dict(gildings=user.num_gildings)
                self.num_gildings_message = gildings_msg

            if not self.viewing_self:
                self.goldlink = "/gold?goldtype=gift&recipient=" + user.name
                self.giftmsg = _("give reddit gold to %(user)s to show "
                                 "your appreciation") % {'user': user.name}
            elif not user.gold:
                self.goldlink = "/gold/about"
                self.giftmsg = _("get extra features and help support reddit "
                                 "with a reddit gold subscription")
            elif gold_days_left < 7 and not user.gold_will_autorenew:
                self.goldlink = "/gold/about"
                self.giftmsg = _("renew your reddit gold")

            if not self.viewing_self:
                self.is_friend = user._id in c.user.friends

            if self.show_private_info:
                self.all_karmas = user.all_karmas()


class ServerSecondsBar(Templated):
    my_message = _("you have helped pay for *%(time)s* of reddit server time.")
    their_message = _("/u/%(user)s has helped pay for *%%(time)s* of reddit server "
                      "time.")

    my_gift_message = _("gifts on your behalf have helped pay for *%(time)s* of "
                        "reddit server time.")
    their_gift_message = _("gifts on behalf of /u/%(user)s have helped pay for "
                           "*%%(time)s* of reddit server time.")

    def make_message(self, seconds, my_message, their_message):
        if not seconds:
            return ''

        delta = datetime.timedelta(seconds=seconds)
        server_time = precise_format_timedelta(delta, threshold=5,
                                                locale=c.locale)
        if c.user == self.user:
            message = my_message
        else:
            message = their_message % {'user': self.user.name}
        return message % {'time': server_time}

    def __init__(self, user):
        Templated.__init__(self)

        self.is_public = user.pref_public_server_seconds
        self.is_user = c.user == user
        self.user = user

        seconds = 0.
        gold_payments = gold_payments_by_user(user)

        for payment in gold_payments:
            seconds += calculate_server_seconds(payment.pennies, payment.date)

        try:
            q = (Bid.query().filter(Bid.account_id == user._id)
                    .filter(Bid.status == Bid.STATUS.CHARGE)
                    .filter(Bid.transaction > 0))
            selfserve_payments = list(q)
        except NotFound:
            selfserve_payments = []

        for payment in selfserve_payments:
            pennies = payment.charge_amount * 100
            seconds += calculate_server_seconds(pennies, payment.date)
        self.message = self.make_message(seconds, self.my_message,
                                         self.their_message)

        seconds = 0.
        gold_gifts = gold_received_by_user(user)

        for payment in gold_gifts:
            pennies = days_to_pennies(payment.days)
            seconds += calculate_server_seconds(pennies, payment.date)
        self.gift_message = self.make_message(seconds, self.my_gift_message,
                                              self.their_gift_message)


class MenuArea(Templated):
    """Draws the gray box at the top of a page for sort menus"""
    def __init__(self, menus = []):
        Templated.__init__(self, menus = menus)


class InfoBar(Templated):
    """Draws the yellow box at the top of a page for info"""
    def __init__(self, message='', extra_class=''):
        Templated.__init__(self, message=message, extra_class=extra_class)


class RedditInfoBar(InfoBar):
    def __init__(self, message='', extra_class='', show_icon=False):
        self.show_icon = show_icon
        super(RedditInfoBar, self).__init__(
            message=message,
            extra_class=extra_class,
        )


class WelcomeBar(InfoBar):
    def __init__(self):
        messages = g.live_config.get("welcomebar_messages")
        if messages:
            message = random.choice(messages).split(" / ")
        else:
            message = (_("reddit is a platform for internet communities"),
                       _("where your votes shape what the world is talking about."))
        InfoBar.__init__(self, message=message)

class NewsletterBar(InfoBar):
    pass

class ClientInfoBar(InfoBar):
    """Draws the message the top of a login page before OAuth2 authorization"""
    def __init__(self, client, *args, **kwargs):
        kwargs.setdefault("extra_class", "client-info")
        InfoBar.__init__(self, *args, **kwargs)
        self.client = client


class LocationBar(Templated): pass

class MobileWebRedirectBar(Templated):
    pass

class SidebarMessage(Templated):
    """An info message box on the sidebar."""
    def __init__(self, message, extra_class=None):
        Templated.__init__(self, message=message, extra_class=extra_class)

class RedditError(BoringPage):
    show_infobar = False
    site_tracking = False

    def __init__(self, title, message, image=None, sr_description=None,
            include_message_mods_link=False, explanation=None):
        content = ErrorPage(
            title=title,
            message=message,
            image=image,
            sr_description=sr_description,
            include_message_mods_link=include_message_mods_link,
            explanation=explanation,
        )
        BoringPage.__init__(self, title, loginbox=False,
                            show_sidebar = False,
                            content=content)

class ErrorPage(Templated):
    """Wrapper for an error message"""
    def __init__(self, title, message, image=None, explanation=None, **kwargs):
        if not image:
            letter = random.choice(['a', 'b', 'c', 'd', 'e'])
            image = 'reddit404' + letter + '.png'
        # Normalize explanation strings.
        if explanation:
            explanation = explanation.lower().rstrip('.') + '.'
        Templated.__init__(self,
                           title=title,
                           message=message,
                           image_url=image,
                           explanation=explanation,
                           **kwargs)


class InterstitialPage(BoringPage):
    show_infobar = False

    def __init__(self, title, content=None):
        BoringPage.__init__(
            self,
            title,
            loginbox=False,
            show_sidebar=False,
            show_welcomebar=False,
            robots='noindex,nofollow',
            content=content,
        )

    def page_classes(self):
        classes = super(BoringPage, self).page_classes()
        if 'quarantine' in classes:
            classes.remove('quarantine')
        return classes


class Interstitial(Templated):
    """Generic template for rendering an interstitial page's content."""

    def __init__(self, image=None, title=None, message=None, sr_name=None,
                 sr_description=None, **kwargs):
        Templated.__init__(
            self,
            image=image,
            title=title,
            message=message,
            sr_name=sr_name,
            sr_description=sr_description,
            **kwargs
        )


class AdminInterstitial(Interstitial):
    """The admin password verification form."""
    pass


class BannedInterstitial(Interstitial):
    """The banned subreddit message."""
    pass


class BannedUserInterstitial(BannedInterstitial):
    """The message shown when viewing a banned user profile."""
    pass


class UserBlockedInterstitial(BannedInterstitial):
    """The message shown when viewing a blocked user profile."""
    pass


class InTimeoutInterstitial(BannedInterstitial):
    """The message shown to a user in timeout."""
    def __init__(self, timeout_days_remaining=0, hide_message=False):
        self.timeout_days_remaining = timeout_days_remaining
        self.hide_message = hide_message
        super(InTimeoutInterstitial, self).__init__()


class PrivateInterstitial(Interstitial):
    """The interstitial shown on private subreddits."""
    pass


class GoldOnlyInterstitial(Interstitial):
    """Interstitial for gold-only subreddits."""
    pass


class QuarantineInterstitial(Interstitial):
    """The opt in page for viewing quarantined content."""

    def __init__(self, sr_name, logged_in, email_verified):
        can_opt_in = logged_in and email_verified
        Interstitial.__init__(
            self,
            sr_name=sr_name,
            logged_in=logged_in,
            can_opt_in=can_opt_in,
        )


class Over18Interstitial(Interstitial):
    """The no-longer-creepy 'over 18' check page for nsfw content."""
    pass


class LockedInterstitial(Interstitial):
    """The error message shown when attempting to comment on a locked post."""
    pass


class ArchivedInterstitial(Interstitial):
    """The error message shown when attempting to comment on an archived post."""
    def __init__(self):
        days = g.ARCHIVE_AGE.days
        months = days // 30
        super(ArchivedInterstitial, self).__init__(
            archive_age_months=months,
        )


class DeletedUserInterstitial(Interstitial):
    """The deleted user message."""
    pass


class Popup(Templated):
    """Generic template for rendering a modal."""
    def __init__(self, popup_id=None, content=None, **kwargs):
        Templated.__init__(
            self,
            popup_id=popup_id,
            content=content,
            **kwargs
        )


class SubredditTopBar(CachedTemplate):

    """The horizontal strip at the top of most pages for navigating
    user-created reddits."""
    def __init__(self):
        self._my_reddits = None
        self._pop_reddits = None
        name = '' if not c.user_is_loggedin else c.user.name
        # poor man's expiration, with random initial time
        t = int(time.time()) / 3600
        if c.user_is_loggedin:
            t += c.user._id

        # HACK: depends on something in the page's content calling
        # Subreddit.default_subreddits so that c.location is set prior to this
        # template being added to the header. set c.location as an attribute so
        # it is added to the render cache key.
        self.location = c.location or "no_location"
        self.my_subreddits_dropdown = self.my_reddits_dropdown()
        CachedTemplate.__init__(self, name=name, t=t, over18=c.over18)

    @property
    def my_reddits(self):
        if self._my_reddits is None:
            self._my_reddits = Subreddit.user_subreddits(c.user, ids=False)
        return self._my_reddits

    @property
    def pop_reddits(self):
        if self._pop_reddits is None:
            defaults = Subreddit.default_subreddits(ids=False)
            # sort the default subreddits by "popularity" descending
            defaults = sorted(defaults, key=lambda sr: sr._downs, reverse=True)
            self._pop_reddits = defaults
        return self._pop_reddits

    def my_reddits_dropdown(self):
        drop_down_buttons = []
        for sr in sorted(self.my_reddits, key = lambda sr: sr.name.lower()):
            drop_down_buttons.append(SubredditButton(sr))
        drop_down_buttons.append(NavButton(menu.edit_subscriptions,
                                           sr_path = False,
                                           css_class = 'bottom-option',
                                           dest = '/subreddits/'))
        return SubredditMenu(drop_down_buttons,
                             title = _('my subreddits'),
                             type = 'srdrop')

    def subscribed_reddits(self):
        srs = [SubredditButton(sr) for sr in
                        sorted(self.my_reddits,
                               key = lambda sr: sr._downs,
                               reverse=True)
                        ]
        return NavMenu(srs,
                       type='flatlist', separator = '-',
                       css_class = 'sr-bar')

    def popular_reddits(self, exclude_mine=False):
        exclude = self.my_reddits if exclude_mine else []
        buttons = [SubredditButton(sr) for sr in self.pop_reddits
                                       if sr not in exclude]

        return NavMenu(buttons,
                       type='flatlist', separator = '-',
                       css_class = 'sr-bar', _id = 'sr-bar')

    def special_reddits(self):
        css_classes = {Random: "random",
                       RandomSubscription: "gold"}
        reddits = [Frontpage, All, Random]
        if getattr(c.site, "over_18", False):
            reddits.append(RandomNSFW)
        if c.user_is_loggedin:
            if c.user.gold:
                reddits.append(RandomSubscription)
            if c.user.friends:
                reddits.append(Friends)
            if c.user.is_moderator_somewhere:
                reddits.append(Mod)
        return NavMenu([SubredditButton(sr, css_class=css_classes.get(sr))
                        for sr in reddits],
                       type = 'flatlist', separator = '-',
                       css_class = 'sr-bar')

    def sr_bar (self):
        sep = '<span class="separator">&nbsp;|&nbsp;</span>'
        menus = []
        menus.append(self.special_reddits())
        menus.append(RawString(sep))

        if not c.user_is_loggedin:
            menus.append(self.popular_reddits())
        else:
            menus.append(self.subscribed_reddits())

            # if the user has more than ~10 subscriptions the top bar will be
            # completely full and anything we add to it won't be seen
            if len(self.my_reddits) < 10:
                menus.append(RawString(sep))
                menus.append(self.popular_reddits(exclude_mine=True))

        return menus


class MultiInfoBar(Templated):
    def __init__(self, multi, srs, user):
        Templated.__init__(self)
        self.multi = wrap_things(multi)[0]
        self.can_edit = multi.can_edit(user)
        self.can_copy = c.user_is_loggedin
        self.can_rename = c.user_is_loggedin and multi.owner == c.user
        srs.sort(key=lambda sr: sr.name.lower())
        self.description_md = multi.description_md
        self.srs = srs
        self.subreddit_selector = SubredditSelector(
                placeholder=_("add subreddit"),
                class_name="sr-name",
                include_user_subscriptions=False,
                show_add=True,
            )

        self.color_options = Subreddit.KEY_COLORS

        self.icon_options = g.multi_icons

        explore_sr = g.live_config["listing_chooser_explore_sr"]
        if explore_sr:
            self.share_url = "/r/%(sr)s/submit?url=%(url)s" % {
                "sr": explore_sr,
                "url": g.origin + self.multi.path,
            }
        else:
            self.share_url = None


class SubscriptionBox(Templated):
    """The list of reddits a user is currently subscribed to to go in
    the right pane."""
    def __init__(self, srs, multi_text=None):
        self.srs = srs
        self.goldlink = None
        self.goldmsg = None
        self.prelink = None
        self.multi_path = None
        self.multi_text = multi_text

        # Construct MultiReddit path
        if multi_text:
            self.multi_path = '/r/' + '+'.join([sr.name for sr in srs])

        if len(srs) > Subreddit.sr_limit and c.user_is_loggedin:
            if not c.user.gold:
                self.goldlink = "/gold"
                self.goldmsg = _("raise it to %s") % Subreddit.gold_limit
                self.prelink = ["/wiki/faq#wiki_how_many_subreddits_can_i_subscribe_to.3F",
                                _("%s visible") % Subreddit.sr_limit]
            else:
                self.goldlink = "/gold/about"
                extra = min(len(srs) - Subreddit.sr_limit,
                            Subreddit.gold_limit - Subreddit.sr_limit)
                visible = min(len(srs), Subreddit.gold_limit)
                bonus = {"bonus": extra}
                self.goldmsg = _("%(bonus)s bonus subreddits") % bonus
                self.prelink = ["/wiki/faq#wiki_how_many_subreddits_can_i_subscribe_to.3F",
                                _("%s visible") % visible]

        Templated.__init__(self, srs=srs, goldlink=self.goldlink,
                           goldmsg=self.goldmsg)

    @property
    def reddits(self):
        return wrap_links(self.srs)


class ModSRInfoBar(Templated):
    pass


class FilteredInfoBar(Templated):
    def __init__(self):
        self.css_class = None
        if c.site.filtername == "all":
            self.css_class = "gold-accent"
        Templated.__init__(self)


class AllInfoBar(Templated):
    def __init__(self, site, user):
        self.sr = site
        self.allminus_url = None
        self.css_class = None
        if isinstance(site, AllMinus) and c.user.gold:
            self.description = (strings.r_all_minus_description + "\n\n" +
                " ".join("/r/" + sr.name for sr in site.exclude_srs))
            self.css_class = "gold-accent"
        else:
            self.description = strings.r_all_description
            sr_ids = Subreddit.user_subreddits(user)
            srs = Subreddit._byID(
                sr_ids, data=True, return_dict=False, stale=True)
            if srs:
                self.allminus_url = '/r/all-' + '-'.join([sr.name for sr in srs])

        self.gilding_listing = False
        if request.path.startswith("/comments/gilded"):
            self.gilding_listing = True

        Templated.__init__(self)


class CreateSubreddit(Templated):
    """reddit creation form."""
    def __init__(self, site = None, name = '', captcha=None):
        allow_image_upload = site and not site.quarantine
        feature_autoexpand_media_previews = feature.is_enabled("autoexpand_media_previews")
        Templated.__init__(self,
                           site=site,
                           name=name,
                           captcha=captcha,
                           comment_sorts=CommentSortMenu.visible_options(),
                           allow_image_upload=allow_image_upload,
                           feature_autoexpand_media_previews=feature_autoexpand_media_previews,
                           )
        self.color_options = Subreddit.KEY_COLORS
        self.subreddit_selector = SubredditSelector(
                placeholder=_("add subreddit"),
                class_name="sr-name",
                include_user_subscriptions=False,
                show_add=True,
            )


class SubredditStylesheetBase(Templated):
    """Base subreddit stylesheet page."""
    def __init__(self, stylesheet_contents, **kwargs):
        raw_images = ImagesByWikiPage.get_images(c.site, "config/stylesheet")
        images = {name: make_url_protocol_relative(url)
                  for name, url in raw_images.iteritems()}
        super(SubredditStylesheetBase, self).__init__(
            images=images,
            stylesheet_contents=stylesheet_contents,
            **kwargs
        )


class SubredditStylesheet(SubredditStylesheetBase):
    """form for editing or creating subreddit stylesheets"""
    def __init__(self, site=None, stylesheet_contents=''):
        allow_image_upload = site and not site.quarantine
        super(SubredditStylesheet, self).__init__(
            stylesheet_contents=stylesheet_contents,
            site=site,
            allow_image_upload=allow_image_upload,
        )

    @staticmethod
    def find_preview_comments(sr):
        comments = queries.get_sr_comments(sr)
        comments = list(comments)
        if not comments:
            comments = queries.get_all_comments()
            comments = list(comments)

        return Thing._by_fullname(comments[:25], data=True, return_dict=False)

    @staticmethod
    def find_preview_links(sr):
        # try to find a link to use, otherwise give up and return
        links = normalized_hot([sr._id])
        if not links:
            links = normalized_hot(Subreddit.default_subreddits())

        if links:
            links = links[:25]
            links = Link._by_fullname(links, data=True, return_dict=False)

        return links

    @staticmethod
    def rendered_link(links, media, compress, stickied=False):
        with c.user.safe_set_attr:
            c.user.pref_compress = compress
            c.user.pref_media = media
        links = wrap_links(links, show_nums=True, num=1)
        for wrapped in links:
            wrapped.stickied = stickied
        delattr(c.user, "pref_compress")
        delattr(c.user, "pref_media")
        return links.render(style="html")

    @staticmethod
    def rendered_comment(comments, gilded=False):
        wrapped = wrap_links(comments, num=1)
        if gilded:
            for w in wrapped:
                w.gilded_message = "this comment was fake-gilded"
        return wrapped.render(style="html")


class SubredditStylesheetSource(SubredditStylesheetBase):
    """A view of the unminified source of a subreddit's stylesheet."""
    pass


class AutoModeratorConfig(Templated):
    """A view of a subreddit's AutoModerator configuration."""
    def __init__(self, automoderator_config):
        Templated.__init__(self, automoderator_config=automoderator_config)


class RawCode(Templated):
    """A "raw code" view of a wiki page - not rendered as markdown."""
    def __init__(self, code):
        Templated.__init__(self, code=code)


class CssError(Templated):
    """Rendered error returned to the stylesheet editing page via ajax"""
    def __init__(self, error):
        # error is an instance of cssfilter.py:ValidationError
        Templated.__init__(self, error = error)

    @property
    def message(self):
        return _(self.error.message_key) % self.error.message_params

class UploadedImage(Templated):
    "The page rendered in the iframe during an upload of a header image"
    def __init__(self,status,img_src, name="", errors = {}, form_id = ""):
        self.errors = list(errors.iteritems())
        Templated.__init__(self, status=status, img_src=img_src, name = name,
                           form_id = form_id)

    def render(self, *a, **kw):
        return responsive(Templated.render(self, *a, **kw))

class Thanks(Templated):
    """The page to claim reddit gold trophies"""
    def __init__(self, secret=None):
        if secret and secret.startswith("cr_"):
            status = "creddits"
        elif c.user.gold:
            status = "gold"
        else:
            status = "mundane"

        Templated.__init__(self, status=status, secret=secret)

class GoldThanks(Templated):
    """An actual 'Thanks for buying gold!' landing page"""
    pass

class Gold(Templated):
    def __init__(self, goldtype, period, months, signed,
                 email, recipient, giftmessage, can_subscribe=True,
                 edit=False):

        if c.user.employee:
            user_creddits = 50
        else:
            user_creddits = c.user.gold_creddits

        Templated.__init__(self, goldtype = goldtype, period = period,
                           months = months, signed = signed,
                           email=email,
                           recipient=recipient,
                           giftmessage=giftmessage,
                           user_creddits = user_creddits,
                           can_subscribe=can_subscribe,
                           edit=edit)


class Creddits(Templated):
    pass


class GoldPayment(Templated):
    def __init__(self, goldtype, period, months, signed,
                 recipient, giftmessage, passthrough, thing,
                 clone_template=False, thing_type=None):
        desc = None

        if period == "monthly" or 1 <= months < 12:
            unit_price = g.gold_month_price
            if period == 'monthly':
                price = unit_price
            else:
                price = unit_price * months
        else:
            unit_price = g.gold_year_price
            if period == 'yearly':
                price = unit_price
            else:
                years = months / 12
                price = unit_price * years

        if c.user.employee:
            user_creddits = 50
        else:
            user_creddits = c.user.gold_creddits

        if (goldtype in ("gift", "code", "onetime") and
                months <= user_creddits):
            can_use_creddits = True
        else:
            can_use_creddits = False

        if goldtype == "autorenew":
            if period == "monthly":
                paypal_buttonid = g.PAYPAL_BUTTONID_AUTORENEW_BYMONTH
                summary = strings.gold_summary_autorenew_monthly % dict(
                    user=c.user.name,
                    price=price,
                )
            elif period == "yearly":
                paypal_buttonid = g.PAYPAL_BUTTONID_AUTORENEW_BYYEAR
                summary = strings.gold_summary_autorenew_yearly % dict(
                    user=c.user.name,
                    price=price,
                )

            quantity = None
            stripe_key = g.secrets['stripe_public_key']
            coinbase_button_id = None

        elif goldtype == "onetime":
            if months < 12:
                paypal_buttonid = g.PAYPAL_BUTTONID_ONETIME_BYMONTH
                quantity = months
                coinbase_name = 'COINBASE_BUTTONID_ONETIME_%sMO' % quantity
                coinbase_button_id = getattr(g, coinbase_name, None)
            else:
                paypal_buttonid = g.PAYPAL_BUTTONID_ONETIME_BYYEAR
                quantity = months / 12
                months = quantity * 12
                coinbase_name = 'COINBASE_BUTTONID_ONETIME_%sYR' % quantity
                coinbase_button_id = getattr(g, coinbase_name, None)

            summary = strings.gold_summary_onetime % dict(
                amount=Score.somethings(months, "month"),
                user=c.user.name,
                price=price,
            )

            stripe_key = g.secrets['stripe_public_key']

        else:
            if months < 12:
                if goldtype == "code":
                    paypal_buttonid = g.PAYPAL_BUTTONID_GIFTCODE_BYMONTH
                else:
                    paypal_buttonid = g.PAYPAL_BUTTONID_CREDDITS_BYMONTH
                quantity = months
                coinbase_name = 'COINBASE_BUTTONID_ONETIME_%sMO' % quantity
                coinbase_button_id = getattr(g, coinbase_name, None)
            else:
                if goldtype == "code":
                    paypal_buttonid = g.PAYPAL_BUTTONID_GIFTCODE_BYYEAR
                else:
                    paypal_buttonid = g.PAYPAL_BUTTONID_CREDDITS_BYYEAR
                quantity = months / 12
                months = quantity * 12
                coinbase_name = 'COINBASE_BUTTONID_ONETIME_%sYR' % quantity
                coinbase_button_id = getattr(g, coinbase_name, None)

            if goldtype == "creddits":
                summary = strings.gold_summary_creddits % dict(
                    amount=Score.somethings(months, "creddit"),
                    price=price,
                )
            elif goldtype == "gift":
                if clone_template:
                    if thing_type == "comment":
                        format = strings.gold_summary_gilding_comment
                    elif thing_type == "link":
                        format = strings.gold_summary_gilding_link
                elif thing:
                    if isinstance(thing, Comment):
                        format = strings.gold_summary_gilding_page_comment
                        desc = thing.body
                    else:
                        format = strings.gold_summary_gilding_page_link
                        desc = thing.markdown_link_slow()
                elif signed:
                    format = strings.gold_summary_signed_gift
                else:
                    format = strings.gold_summary_anonymous_gift

                if not clone_template:
                    summary = format % dict(
                        amount=Score.somethings(months, "month"),
                        recipient=recipient and
                                  recipient.name.replace('_', '&#95;'),
                        price=price,
                    )
                else:
                    # leave the replacements to javascript
                    summary = format
            elif goldtype == "code":
                summary = strings.gold_summary_gift_code % dict(
                    amount=Score.somethings(months, "month"),
                    price=price,
                )
            else:
                raise ValueError("wtf is %r" % goldtype)

            stripe_key = g.secrets['stripe_public_key']

        Templated.__init__(self, goldtype=goldtype, period=period,
                           months=months, quantity=quantity,
                           unit_price=unit_price, price=price,
                           summary=summary, giftmessage=giftmessage,
                           can_use_creddits=can_use_creddits,
                           passthrough=passthrough,
                           thing=thing, clone_template=clone_template,
                           description=desc, thing_type=thing_type,
                           paypal_buttonid=paypal_buttonid,
                           stripe_key=stripe_key,
                           coinbase_button_id=coinbase_button_id,
                           user_creddits=user_creddits,
                           )


class GoldSubscription(Templated):
    def __init__(self, user):
        if user.has_stripe_subscription:
            details = get_subscription_details(user)
        else:
            details = None

        if details:
            self.has_stripe_subscription = True
            date = details['next_charge_date']
            next_charge_date = format_date(date, format="short",
                                           locale=c.locale)
            credit_card_last4 = details['credit_card_last4']
            amount = format_currency(float(details['pennies']) / 100, 'USD',
                                     locale=c.locale)
            text = _("you have a credit card gold subscription. your card "
                     "(ending in %(last4)s) will be charged %(amount)s on "
                     "%(date)s.")
            self.text = text % dict(last4=credit_card_last4,
                                    amount=amount,
                                    date=next_charge_date)
            self.user_fullname = user._fullname
        else:
            self.has_stripe_subscription = False

        if user.has_paypal_subscription:
            self.has_paypal_subscription = True
            self.paypal_subscr_id = user.gold_subscr_id
            self.paypal_url = paypal_subscription_url()
        else:
            self.has_paypal_subscription = False

        self.stripe_key = g.secrets['stripe_public_key']
        Templated.__init__(self)

class CreditGild(Templated):
    """Page for credit card payments for gilding."""
    pass

class GoldGiftCodeEmail(Templated):
    """Email sent to a logged-out person that purchases a reddit
    gold gift code."""
    pass


class Gilding(Templated):
    pass


class ReportForm(CachedTemplate):
    def __init__(self, thing=None, **kw):
        self.rules = []
        self.system_rules = []
        self.thing_fullname = thing._fullname
        self.kind = None
        subreddit = None

        if isinstance(thing, (Comment, Link)):
            subreddit = thing.subreddit_slow
            self.kind = thing.__class__.__name__.lower()

        if (subreddit and
                feature.is_enabled("subreddit_rules", subreddit=subreddit.name)):
            for rule in SubredditRules.get_rules(subreddit, self.kind):
                self.rules.append(rule["short_name"])
            if self.rules:
                self.system_rules = SITEWIDE_RULES
                self.rules_page_link = "/r/%s/about/rules" % subreddit.name
        if not self.rules:
            self.rules = OLD_SITEWIDE_RULES
            self.rules_page_link = "/help/contentpolicy"

        Templated.__init__(self)


class SubredditReportForm(CachedTemplate):
    def __init__(self, thing=None, filter_by_kind=True, **kw):
        self.rules = []
        self.system_rules = SITEWIDE_RULES
        self.thing_fullname = thing._fullname
        self.kind = None
        subreddit = None

        if isinstance(thing, Comment, Link):
            subreddit = thing.subreddit_slow
            self.sr_name = subreddit.name
            if filter_by_kind:
                self.kind = thing.__class__.__name__.lower()
        else:
            self.sr_name = None

        if (subreddit and
                feature.is_enabled("subreddit_rules", subreddit=subreddit.name)):
            self.rules = SubredditRules.get_rules(subreddit, self.kind)

        Templated.__init__(self)


class ReportFormTemplates(Templated):
    def __init__(self):
        super(ReportFormTemplates, self).__init__(
            system_rules=SITEWIDE_RULES,
            rules_page_link="/help/contentpolicy",
        )


class FraudForm(Templated):
    pass


class Password(Templated):
    """Form encountered when 'recover password' is clicked in the LoginFormWide."""
    def __init__(self, success=False):
        Templated.__init__(self, success = success)

class PasswordReset(Templated):
    """Template for generating an email to the user who wishes to
    reset their password (step 2 of password recovery, after they have
    entered their user name in Password.)"""
    pass

class MessageNotificationEmail(Templated):
    """Notification e-mail that a user has received a new message."""
    pass

class MessageNotificationEmailsUnsubscribe(Templated):
    """The page we show users when they unsubscribe from notification
    emails."""
    pass

class PasswordChangeEmail(Templated):
    """Notification e-mail that a user's password has changed."""
    pass

class EmailChangeEmail(Templated):
    """Notification e-mail that a user's e-mail has changed."""
    pass

class VerifyEmail(Templated):
    pass

class Promo_Email(Templated):
    def __init__(self, *args, **kwargs):
        # if total_budget_dollars is passed,
        # format into printable_total_budget
        if 'total_budget_dollars' in kwargs:
            locale = c.locale or g.locale
            self.printable_total_budget = format_currency(
                kwargs['total_budget_dollars'], 'USD', locale=locale)
        super(Promo_Email, self).__init__(*args, **kwargs)

class SuspiciousPaymentEmail(Templated):
    def __init__(self, user, link):
        Templated.__init__(self, user=user, link=link)


class ResetPassword(Templated):
    """Form for actually resetting a lost password, after the user has
    clicked on the link provided to them in the Password_Reset email
    (step 3 of password recovery.)"""
    pass


class Captcha(Templated):
    """Container for rendering robot detection device."""
    def __init__(self, error=None):
        self.error = _('try entering those letters again') if error else ""
        self.iden = get_captcha()
        Templated.__init__(self)

class PermalinkMessage(Templated):
    """renders the box on comment pages that state 'you are viewing a
    single comment's thread'"""
    def __init__(self, comments_url):
        Templated.__init__(self, comments_url = comments_url)

class PaneStack(Templated):
    """Utility class for storing and rendering a list of block elements."""

    def __init__(self, panes=[], div_id = None, css_class=None, div=False,
                 title="", title_buttons = []):
        div = div or div_id or css_class or False
        self.div_id    = div_id
        self.css_class = css_class
        self.div       = div
        self.stack     = list(panes)
        self.title = title
        self.title_buttons = title_buttons
        Templated.__init__(self)

    def append(self, item):
        """Appends an element to the end of the current stack"""
        self.stack.append(item)

    def push(self, item):
        """Prepends an element to the top of the current stack"""
        self.stack.insert(0, item)

    def insert(self, *a):
        """inerface to list.insert on the current stack"""
        return self.stack.insert(*a)


class HtmlPaneStack(PaneStack):
    """Same as panestack, but won't show up in json responses."""
    pass


class SearchForm(Templated):
    """The simple search form in the header of the page.  prev_search
    is the previous search."""
    def __init__(self, prev_search='', search_params={}, site=None,
                 simple=True, restrict_sr=False, subreddit_search=False,
                 syntax=None, search_path="/search"):
        Templated.__init__(self, prev_search=prev_search,
                           search_params=search_params, site=site,
                           simple=simple, restrict_sr=restrict_sr,
                           subreddit_search=subreddit_search, syntax=syntax,
                           search_path=search_path)

        # generate the over18 redirect url for the current search if needed
        if not c.over18 and feature.is_enabled('safe_search'):
            u = UrlParser(add_sr(search_path))
            if prev_search:
                u.update_query(q=prev_search)
            if restrict_sr:
                u.update_query(restrict_sr='on')
            u.update_query(**search_params)
            u.update_query(over18='yes')
            self.over18_url = u.unparse()
        else:
            self.over18_url = None


class SearchBar(Templated):
    """More detailed search box for /search and /subreddits pages.

    Displays the previous search as well

    """
    def __init__(self, header=None, prev_search='', search_params={},
                 simple=False, restrict_sr=False, site=None, syntax=None,
                 subreddit_search=False, converted_data=None,
                 search_path="/search"):
        if header is None:
            header = _("search")
        self.header = header
        self.prev_search  = prev_search
        self.converted_data = converted_data

        self.search_form = SearchForm(
            prev_search=prev_search,
            search_params=search_params,
            site=site,
            subreddit_search=subreddit_search,
            simple=simple,
            restrict_sr=restrict_sr,
            syntax=syntax,
            search_path=search_path,
        )
        Templated.__init__(self)


class SubredditFacets(Templated):
    def __init__(self, prev_search='', facets={}, sort=None, recent=None):
        self.prev_search = prev_search

        Templated.__init__(self, facets=facets, sort=sort, recent=recent)


class NewLink(Templated):
    """Render the link submission form"""
    def __init__(self, captcha=None, url='', title='', text='', selftext='',
                 resubmit=False, default_sr=None,
                 extra_subreddits=None, show_link=True, show_self=True):

        self.show_link = show_link
        self.show_self = show_self

        tabs = []
        if show_link:
            tabs.append(('link', ('link-desc', 'url-field')))
        if show_self:
            tabs.append(('text', ('text-desc', 'text-field')))

        if self.show_self and self.show_link:
            all_fields = set(chain(*(parts for (tab, parts) in tabs)))
            buttons = []

            if selftext == 'true' or text != '':
                self.default_tab = tabs[1][0]
            else:
                self.default_tab = tabs[0][0]

            for tab_name, parts in tabs:
                to_show = ','.join('#' + p for p in parts)
                to_hide = ','.join('#' + p for p in all_fields if p not in parts)
                onclick = "return select_form_tab(this, '%s', '%s');"
                onclick = onclick % (to_show, to_hide)
                if tab_name == self.default_tab:
                    self.default_show = to_show
                    self.default_hide = to_hide

                buttons.append(JsButton(tab_name, onclick=onclick, css_class=tab_name + "-button"))

            self.formtabs_menu = JsNavMenu(buttons, type = 'formtab')

        self.resubmit = resubmit
        self.default_sr = default_sr
        self.extra_subreddits = extra_subreddits

        Templated.__init__(self, captcha = captcha, url = url,
                         title = title, text = text)


class Share(Templated):
    pass

class Mail_Opt(Templated):
    pass

class OptOut(Templated):
    pass

class OptIn(Templated):
    pass


class Button(Wrapped):
    cachable = True
    extension_handling = False
    def __init__(self, link, **kw):
        Wrapped.__init__(self, link, **kw)
        if link is None:
            self.title = ""
            self.add_props(c.user, [self])


    @classmethod
    def add_props(cls, user, wrapped):
        # unlike most wrappers we can guarantee that there is a link
        # that this wrapper is wrapping.
        Link.add_props(user, [w for w in wrapped if hasattr(w, "_fullname")])
        for w in wrapped:
            # caching: store the user name since each button has a modhash
            w.user_name = c.user.name if c.user_is_loggedin else ""
            if not hasattr(w, '_fullname'):
                w._fullname = None

    def render(self, *a, **kw):
        res = Wrapped.render(self, *a, **kw)
        return responsive(res, True)

class ButtonLite(Button):
    def render(self, *a, **kw):
        return Wrapped.render(self, *a, **kw)

class ButtonDemoPanel(Templated):
    """The page for showing the different styles of embedable voting buttons"""
    pass

class ContactUs(Templated):
    pass


class WidgetDemoPanel(Templated):
    """Demo page for the .embed widget."""
    pass


class UserAwards(Templated):
    """For drawing the regular-user awards page."""
    def __init__(self):
        from r2.models import Award, Trophy
        Templated.__init__(self)

        self.regular_winners = []
        self.manuals = []
        self.invisibles = []

        for award in Award._all_awards():
            if award.awardtype == 'regular':
                trophies = Trophy.by_award(award)
                # Don't show awards that nobody's ever won
                # (e.g., "9-Year Club")
                if trophies:
                    winner = trophies[0]._thing1.name
                    self.regular_winners.append( (award, winner, trophies[0]) )
            elif award.awardtype == 'manual':
                self.manuals.append(award)
            elif award.awardtype == 'invisible':
                self.invisibles.append(award)
            else:
                raise NotImplementedError


class AdminAwards(Templated):
    """The admin page for editing awards"""
    def __init__(self):
        from r2.models import Award
        Templated.__init__(self)
        self.awards = Award._all_awards()

class AdminAwardGive(Templated):
    """The interface for giving an award"""
    def __init__(self, award, recipient='', desc='', url='', hours=''):
        now = datetime.datetime.now(g.display_tz)
        if desc:
            self.description = desc
        elif award.awardtype == 'regular':
            self.description = "??? -- " + now.strftime("%Y-%m-%d")
        else:
            self.description = ""
        self.url = url
        self.recipient = recipient
        self.hours = hours

        Templated.__init__(self, award = award)

class AdminAwardWinners(Templated):
    """The list of winners of an award"""
    def __init__(self, award):
        trophies = Trophy.by_award(award)
        Templated.__init__(self, award = award, trophies = trophies)


class AdminCreddits(Templated):
    """The admin interface for giving creddits to a user."""
    def __init__(self, recipient):
        self.recipient = recipient
        Templated.__init__(self)


class AdminGold(Templated):
    """The admin interface for giving or taking days of gold for a user."""
    def __init__(self, recipient):
        self.recipient = recipient
        Templated.__init__(self)


class Ads(Templated):
    def __init__(self):
        Templated.__init__(self)
        self.ad_url = g.ad_domain + "/ads/"
        self.frame_id = "ad-frame"


class ReadNext(Templated):
    def __init__(self, sr, links):
        Templated.__init__(self)
        self.sr = sr
        self.links = links


class Embed(Templated):
    """wrapper for embedding /help into reddit as if it were not on a separate wiki."""
    def __init__(self,content = ''):
        Templated.__init__(self, content = content)


def wrapped_flair(user, subreddit, force_show_flair):
    if isinstance(subreddit, FakeSubreddit):
        # FakeSubreddits don't show user flair
        return False, 'right', '', ''
    elif not (force_show_flair or subreddit.flair_enabled):
        return False, 'right', '', ''

    enabled = user.flair_enabled_in_sr(subreddit._id)
    position = subreddit.flair_position
    text = user.flair_text(subreddit._id)
    css_class = user.flair_css_class(subreddit._id)

    return enabled, position, text, css_class

class WrappedUser(CachedTemplate):
    cachable = False
    FLAIR_CSS_PREFIX = 'flair-'

    def __init__(self, user, attribs = [], context_thing = None, gray = False,
                 subreddit = None, force_show_flair = None,
                 flair_template = None, flair_text_editable = False,
                 include_flair_selector = False):
        if not subreddit:
            subreddit = c.site

        attribs.sort()
        author_cls = 'author'

        author_title = ''
        if gray:
            author_cls += ' gray'
        for tup in attribs:
            author_cls += " " + tup[2]
            # Hack: '(' should be in tup[3] iff this friend has a note
            if tup[1] == 'F' and '(' in tup[3]:
                author_title = tup[3]

        flair = wrapped_flair(user, subreddit or c.site, force_show_flair)
        flair_enabled, flair_position, flair_text, flair_css_class = flair
        has_flair = bool(
            c.user.pref_show_flair and (flair_text or flair_css_class))

        if flair_template:
            flair_template_id = flair_template._id
            flair_text = flair_template.text
            flair_css_class = flair_template.css_class
            has_flair = True
        else:
            flair_template_id = None

        if flair_css_class:
            # This is actually a list of CSS class *suffixes*. E.g., "a b c"
            # should expand to "flair-a flair-b flair-c".
            flair_css_class = ' '.join(self.FLAIR_CSS_PREFIX + c
                                       for c in flair_css_class.split())

        if include_flair_selector:
            if (not getattr(c.site, 'flair_self_assign_enabled', True)
                and not (c.user_is_admin
                         or c.site.is_moderator_with_perms(c.user, 'flair'))):
                include_flair_selector = False

        target = None
        ip_span = None
        context_deleted = None
        if context_thing:
            target = getattr(context_thing, 'target', None)
            ip_span = getattr(context_thing, 'ip_span', None)
            context_deleted = context_thing.deleted

        karma = ''
        context_thing_fullname = ''
        show_details_link = False

        if c.user_is_admin:
            karma = ' (%d)' % user.link_karma

            if user.in_timeout:
                if user.timeout_expiration:
                    author_cls += " user-in-timeout-temp"
                else:
                    author_cls += " user-in-timeout-perma"

            if context_thing:
                context_thing_fullname = context_thing._fullname

                if isinstance(context_thing, Wrapped):
                    unwrapped_thing = context_thing.lookups[0]
                else:
                    unwrapped_thing = context_thing
                if isinstance(unwrapped_thing, (Link, Comment)):
                    show_details_link = True

            if user._spam:
                author_cls += " user-spam"
            if user._banned:
                author_cls += " user-banned"

        CachedTemplate.__init__(self,
                                name = user.name,
                                force_show_flair = force_show_flair,
                                has_flair = has_flair,
                                flair_enabled = flair_enabled,
                                flair_position = flair_position,
                                flair_text = flair_text,
                                flair_text_editable = flair_text_editable,
                                flair_css_class = flair_css_class,
                                flair_template_id = flair_template_id,
                                include_flair_selector = include_flair_selector,
                                author_cls = author_cls,
                                author_title = author_title,
                                attribs = attribs,
                                context_thing_fullname = context_thing_fullname,
                                show_details_link = show_details_link,
                                karma = karma,
                                ip_span = ip_span,
                                context_deleted = context_deleted,
                                fullname = user._fullname,
                                user_deleted = user._deleted)

class UserTableItem(Templated):
    type = ''
    remove_action = 'unfriend'
    cells = ('user', 'age', 'sendmessage', 'remove')

    @property
    def executed_message(self):
        return _("added")

    def __init__(self, user, editable=True, **kw):
        self.user = user
        self.editable = editable
        self.author_cls = ''

        if c.user_is_admin and user._spam:
            self.author_cls = 'banned-user'

        Templated.__init__(self, **kw)

    def __repr__(self):
        return '<UserTableItem "%s">' % self.user.name

class RelTableItem(UserTableItem):
    def __init__(self, rel, **kw):
        self._id = rel._id
        self.rel = rel
        UserTableItem.__init__(self, rel._thing2, **kw)

    @property
    def _fullname(self):
        # needed for paging (see Listing.listing())
        return self.rel._fullname

    @property
    def container_name(self):
        return c.site._fullname

class FriendTableItem(RelTableItem):
    remove_access_required = False
    type = 'friend'

    @property
    def cells(self):
        if c.user.gold:
            return ('user', 'sendmessage', 'note', 'age', 'remove')
        return ('user', 'sendmessage', 'remove')

    @property
    def container_name(self):
        return c.user._fullname

class EnemyTableItem(RelTableItem):
    remove_access_required = False
    type = 'enemy'
    cells = ('user', 'age', 'remove')

    @property
    def container_name(self):
        return c.user._fullname

class BannedTableItem(RelTableItem):
    type = 'banned'
    cells = ('user', 'age', 'sendmessage', 'remove', 'note', 'temp')

    @property
    def executed_message(self):
        return _("banned")


class MutedTableItem(RelTableItem):
    type = 'muted'
    cells = ('user', 'age', 'remove', 'note')

    @property
    def executed_message(self):
        return _("muted")


class WikiBannedTableItem(BannedTableItem):
    type = 'wikibanned'

class ContributorTableItem(RelTableItem):
    type = 'contributor'

class WikiMayContributeTableItem(RelTableItem):
    type = 'wikicontributor'

class InvitedModTableItem(RelTableItem):
    type = 'moderator_invite'
    cells = ('user', 'age', 'permissions', 'permissionsctl')

    @property
    def executed_message(self):
        return _("invited")

    def is_editable(self, user):
        if not c.user_is_loggedin:
            return False
        elif c.user_is_admin:
            return True
        return c.site.is_unlimited_moderator(c.user)

    def __init__(self, rel, editable=True, **kw):
        if editable:
            self.cells += ('remove',)
        editable = self.is_editable(rel._thing2)
        self.permissions = ModeratorPermissions(rel._thing2, self.type,
                                                rel.get_permissions(),
                                                editable=editable)
        RelTableItem.__init__(self, rel, editable=editable, **kw)

class ModTableItem(InvitedModTableItem):
    type = 'moderator'

    @property
    def executed_message(self):
        return _("added")

    def is_editable(self, user):
        if not c.user_is_loggedin:
            return False
        elif c.user_is_admin:
            return True
        return c.user != user and c.site.can_demod(c.user, user)


class ModToolsPage(Reddit):
    """A mod tool page."""

    def __init__(self, **kwargs):
        super(ModToolsPage, self).__init__(
            page_classes=['modtools-page'],
            **kwargs
        )


class Rules(Templated):
    """Show subreddit rules for everyone and add edit controls for mods."""
    def __init__(self, title, kind_labels):
        self.title = title
        self.can_edit = c.user_is_loggedin and (c.user_is_admin or
            c.site.is_moderator_with_perms(c.user, 'config'))
        self.rules = SubredditRules.get_rules(c.site)
        self.site_rules = SITEWIDE_RULES
        self.kind_labels = kind_labels
        Templated.__init__(self)


class FlairPane(Templated):
    def __init__(self, num, after, reverse, name, user):
        # Make sure c.site isn't stale before rendering.
        c.site = Subreddit._byID(c.site._id, data=True, stale=False)

        tabs = [
            ('grant', _('grant flair'), FlairList(num, after, reverse, name,
                                                  user)),
            ('templates', _('user flair templates'),
             FlairTemplateList(USER_FLAIR)),
            ('link_templates', _('link flair templates'),
             FlairTemplateList(LINK_FLAIR)),
        ]

        Templated.__init__(
            self,
            tabs=TabbedPane(tabs, linkable=True),
            flair_enabled=c.site.flair_enabled,
            flair_position=c.site.flair_position,
            link_flair_position=c.site.link_flair_position,
            flair_self_assign_enabled=c.site.flair_self_assign_enabled,
            link_flair_self_assign_enabled=
                c.site.link_flair_self_assign_enabled)

class FlairList(Templated):
    """List of users who are tagged with flair within a subreddit."""

    def __init__(self, num, after, reverse, name, user):
        Templated.__init__(self, num=num, after=after, reverse=reverse,
                           name=name, user=user)

    @property
    def flair(self):
        if self.user:
            return [FlairListRow(self.user)]

        if self.name:
            # user lookup was requested, but no user was found, so abort
            return []

        query = Flair._query(
            Flair.c._thing1_id == c.site._id,
            Flair.c._name == 'flair',
            sort=asc('_thing2_id'),
            eager_load=True,
            thing_data=True,
        )

        # To maintain API compatibility we can't use the `before` or `after`s
        # returned by Builder.get_items(), since we use different logic to
        # determine them. We also need to fetch an extra item to be *sure*
        # there's a next page.
        builder = FlairListBuilder(query, wrap=FlairListRow.from_rel,
                                   after=self.after, reverse=self.reverse,
                                   num=self.num + 1)

        items = builder.get_items()[0]

        if not items:
            return []

        have_more = False
        if self.num and len(items) > self.num:
            if self.reverse:
                have_more = items.pop(0)
            else:
                have_more = items.pop()

        # FlairLists are unusual in that afters that aren't in the queryset
        # work correctly due to the filter just doing a gt (or lt) on
        # the after's `_id`. They also use _thing2's fullname instead
        # of the fullname of the rel for pagination.
        before = items[0].user._fullname
        after = items[-1].user._fullname

        links = []
        show_next = have_more or self.reverse
        if (not self.reverse and self.after) or (self.reverse and have_more):
            links.append(FlairNextLink(before, previous=True,
                                       needs_border=show_next))
        if show_next:
            links.append(FlairNextLink(after, previous=False))

        return items + links


class FlairListRow(Templated):
    def __init__(self, user):
        self.user = user
        Templated.__init__(self,
                           flair_text=user.flair_text(c.site._id),
                           flair_css_class=user.flair_css_class(c.site._id))

    @classmethod
    def from_rel(cls, rel):
        instance = cls(rel._thing2)
        # Needed by the builder to do wrapped -> unwrapped lookups
        instance._id = rel._id
        return instance


class FlairNextLink(Templated):
    def __init__(self, after, previous=False, needs_border=False):
        Templated.__init__(self, after=after, previous=previous,
                           needs_border=needs_border)

class FlairCsv(Templated):
    class LineResult:
        def __init__(self):
            self.errors = {}
            self.warnings = {}
            self.status = 'skipped'
            self.ok = False

        def error(self, field, desc):
            self.errors[field] = desc

        def warn(self, field, desc):
            self.warnings[field] = desc

    def __init__(self):
        Templated.__init__(self, results_by_line=[])

    def add_line(self):
        self.results_by_line.append(self.LineResult())
        return self.results_by_line[-1]

class FlairTemplateList(Templated):
    def __init__(self, flair_type):
        Templated.__init__(self, flair_type=flair_type)

    @property
    def templates(self):
        ids = FlairTemplateBySubredditIndex.get_template_ids(
                c.site._id, flair_type=self.flair_type)
        fts = FlairTemplate._byID(ids)
        return [FlairTemplateEditor(fts[i], self.flair_type) for i in ids]

class FlairTemplateEditor(Templated):
    def __init__(self, flair_template, flair_type):
        Templated.__init__(self,
                           id=flair_template._id,
                           text=flair_template.text,
                           css_class=flair_template.css_class,
                           text_editable=flair_template.text_editable,
                           sample=FlairTemplateSample(flair_template,
                                                      flair_type),
                           position=getattr(c.site, 'flair_position', 'right'),
                           flair_type=flair_type)

    def render(self, *a, **kw):
        res = Templated.render(self, *a, **kw)
        if not g.template_debug:
            res = spaceCompress(res)
        return res

class FlairTemplateSample(Templated):
    """Like a read-only version of FlairTemplateEditor."""
    def __init__(self, flair_template, flair_type):
        if flair_type == USER_FLAIR:
            wrapped_user = WrappedUser(c.user, subreddit=c.site,
                                       force_show_flair=True,
                                       flair_template=flair_template)
        else:
            wrapped_user = None
        Templated.__init__(self,
                           flair_template=flair_template,
                           wrapped_user=wrapped_user, flair_type=flair_type)

class FlairPrefs(CachedTemplate):
    def __init__(self):
        sr_flair_enabled = getattr(c.site, 'flair_enabled', False)
        user_flair_enabled = getattr(c.user, 'flair_%s_enabled' % c.site._id,
                                     True)
        sr_flair_self_assign_enabled = getattr(
            c.site, 'flair_self_assign_enabled', True)
        wrapped_user = WrappedUser(c.user, subreddit=c.site,
                                   force_show_flair=True,
                                   include_flair_selector=True)
        CachedTemplate.__init__(
            self,
            sr_flair_enabled=sr_flair_enabled,
            sr_flair_self_assign_enabled=sr_flair_self_assign_enabled,
            user_flair_enabled=user_flair_enabled,
            wrapped_user=wrapped_user)

class FlairSelectorLinkSample(CachedTemplate):
    def __init__(self, link, site, flair_template):
        flair_position = getattr(site, 'link_flair_position', 'right')
        admin = bool(c.user_is_admin
                     or site.is_moderator_with_perms(c.user, 'flair'))
        CachedTemplate.__init__(
            self,
            title=link.title,
            flair_position=flair_position,
            flair_template_id=flair_template._id,
            flair_text=flair_template.text,
            flair_css_class=flair_template.css_class,
            flair_text_editable=admin or flair_template.text_editable,
            )

class FlairSelector(CachedTemplate):
    """Provide user with flair options according to subreddit settings."""
    def __init__(self, user, site, link=None):
        admin = bool(
            c.user_is_admin or site.is_moderator_with_perms(c.user, 'flair'))

        if link:
            flair_type = LINK_FLAIR
            target = link
            target_name = link._fullname
            attr_pattern = 'flair_%s'
            position = getattr(site, 'link_flair_position', 'right')
            target_wrapper = (
                lambda flair_template: FlairSelectorLinkSample(
                    link, site, flair_template))
            self_assign_enabled = (
                c.user._id == link.author_id
                and site.link_flair_self_assign_enabled)
        else:
            flair_type = USER_FLAIR
            target = user
            target_name = user.name
            position = getattr(site, 'flair_position', 'right')
            attr_pattern = 'flair_%s_%%s' % c.site._id
            target_wrapper = (
                lambda flair_template: WrappedUser(
                    user, subreddit=site, force_show_flair=True,
                    flair_template=flair_template,
                    flair_text_editable=admin or template.text_editable))
            self_assign_enabled = site.flair_self_assign_enabled

        text = getattr(target, attr_pattern % 'text', '')
        css_class = getattr(target, attr_pattern % 'css_class', '')
        templates, matching_template = self._get_templates(
                site, flair_type, text, css_class)

        if self_assign_enabled or admin:
            choices = [target_wrapper(template) for template in templates]
        else:
            choices = []

        # If one of the templates is already selected, modify its text to match
        # the user's current flair.
        if matching_template:
            for choice in choices:
                if choice.flair_template_id == matching_template:
                    if choice.flair_text_editable:
                        choice.flair_text = text
                    break

        Templated.__init__(self, text=text, css_class=css_class,
                           position=position, choices=choices,
                           matching_template=matching_template,
                           target_name=target_name)

    def render(self, *a, **kw):
        return responsive(CachedTemplate.render(self, *a, **kw), True)

    def _get_templates(self, site, flair_type, text, css_class):
        ids = FlairTemplateBySubredditIndex.get_template_ids(
            site._id, flair_type)
        template_dict = FlairTemplate._byID(ids)
        templates = [template_dict[i] for i in ids]
        for template in templates:
            if template.covers((text, css_class)):
                matching_template = template._id
                break
        else:
             matching_template = None
        return templates, matching_template


class DetailsPage(LinkInfoPage):
    extension_handling= False

    def __init__(self, thing, *args, **kwargs):
        from admin_pages import Details
        after = kwargs.pop('after', None)
        reverse = kwargs.pop('reverse', False)
        count = kwargs.pop('count', None)
        self.details = None

        if isinstance(thing, (Link, Comment)):
            self.details = Details(thing, after=after, reverse=reverse,
                                   count=count)

        if isinstance(thing, Link):
            link = thing
            comment = None
            content = self.details
        elif isinstance(thing, Comment):
            comment = thing
            link = Link._byID(comment.link_id, data=True)
            content = PaneStack()
            content.append(PermalinkMessage(link.make_permalink_slow()))
            content.append(LinkCommentSep())
            content.append(CommentPane(link, CommentSortMenu.operator('new'),
                                   comment, None, 1))
            content.append(self.details)

        kwargs['content'] = content
        LinkInfoPage.__init__(self, link, comment, *args, **kwargs)

    def rightbox(self):
        rb = LinkInfoPage.rightbox(self)

        if c.user_is_admin:
            from admin_pages import AdminDetailsBar
            rb.append(AdminDetailsBar(from_page='details'))

        return rb


class PromotePage(Reddit):
    create_reddit_box  = False
    submit_box         = False
    extension_handling = False
    searchbox          = False

    @classmethod
    def get_menu(cls):
        if c.user_is_sponsor:
            buttons = [
                NavButton(menu['new_promo'], dest='/promoted/new_promo'),
                NavButton(menu['current_promos'], dest='/sponsor/promoted',
                          aliases=['/sponsor']),
                NavButton('inventory', '/sponsor/inventory'),
                NavButton('report', '/sponsor/report'),
                NavButton('underdelivered', '/sponsor/promoted/underdelivered'),
                NavButton('house ads', '/sponsor/promoted/house'),
                NavButton('reported links', '/sponsor/promoted/reported'),
                NavButton('fraud', '/sponsor/promoted/fraud'),
                NavButton('lookup user', '/sponsor/lookup_user'),
            ]
            return NavMenu(buttons, type='flatlist')
        else:
            buttons = [
                NamedButton('new_promo'),
                NamedButton('my_current_promos', dest=''),
            ]
            return NavMenu(buttons, base_path='/promoted', type='flatlist')

    def __init__(self, nav_menus=None, *a, **kw):
        menu = self.get_menu()

        if nav_menus:
            nav_menus.insert(0, menu)
        else:
            nav_menus = [menu]

        kw['show_sidebar'] = False
        auction_announcement = not feature.is_enabled('ads_auction')
        Reddit.__init__(self, nav_menus=nav_menus,
            auction_announcement=auction_announcement, *a, **kw)


class PromoteLinkBase(Templated):
    min_start = None
    max_start = None
    max_end = None

    def __init__(self, **kw):
        self.mobile_targeting_enabled = feature.is_enabled("mobile_targeting")
        Templated.__init__(self, **kw)

    def get_locations(self):
        # geotargeting
        def location_sort(location_tuple):
            code, name, default = location_tuple
            if code == '':
                return -2
            elif code == 'US':
                return -1
            else:
                return name

        countries = [(code, country['name'], False) for code, country
                                                    in g.locations.iteritems()]
        countries.append(('', _('none'), True))

        countries = sorted(countries, key=location_sort)
        regions = {}
        metros = {}
        for code, country in g.locations.iteritems():
            if 'regions' in country and country['regions']:
                regions[code] = [('', _('all'), True)]

                for region_code, region in country['regions'].iteritems():
                    if region['metros']:
                        region_tuple = (region_code, region['name'], False)
                        regions[code].append(region_tuple)
                        if c.user_is_sponsor:
                            metros[region_code] = []
                        else:
                            metros[region_code] = [('', _('all'), True)]

                        for metro_code, metro in region['metros'].iteritems():
                            metro_tuple = (metro_code, metro['name'], False)
                            metros[region_code].append(metro_tuple)
                        metros[region_code].sort(key=location_sort)
                regions[code].sort(key=location_sort)

        self.countries = countries
        self.regions = regions
        self.metros = metros

        ads_auction_enabled = feature.is_enabled('ads_auction')
        self.force_auction = (ads_auction_enabled and not c.user_is_sponsor)
        self.auction_optional = (ads_auction_enabled and c.user_is_sponsor)

        self.cpc_pricing = feature.is_enabled('cpc_pricing')

    def get_collections(self):
        self.collections = [cl.__dict__ for cl in Collection.get_all()]

    def get_mobile_versions(self):
        self.ios_versions = g.ios_versions
        self.android_versions = g.android_versions


class PromoteLinkNew(PromoteLinkBase):
    def __init__(self, images=None, *a, **kw):
        images = images or {}
        self.images = images
        PromoteLinkBase.__init__(self, **kw)


class PromoteLinkEdit(PromoteLinkBase):
    def __init__(self, link, listing, *a, **kw):
        self.setup(link, listing)
        PromoteLinkBase.__init__(self, **kw)

    def setup(self, link, listing):
        self.bids = []
        self.author = Account._byID(link.author_id, data=True)

        if c.user_is_sponsor:
            try:
                bids = Bid.lookup(thing_id=link._id)
            except NotFound:
                pass
            else:
                bids.sort(key=lambda x: x.date, reverse=True)
                bidders = Account._byID(set(bid.account_id for bid in bids),
                                        data=True, return_dict=True)
                for bid in bids:
                    status = Bid.STATUS.name[bid.status].lower()
                    bidder = bidders[bid.account_id]
                    row = Storage(
                        status=status,
                        bidder=bidder.name,
                        date=bid.date,
                        transaction=bid.transaction,
                        campaign=bid.campaign,
                        pay_id=bid.pay_id,
                        amount_str=format_currency(bid.bid, 'USD',
                                                   locale=c.locale),
                        charge_str=format_currency(bid.charge or bid.bid, 'USD',
                                                   locale=c.locale),
                    )
                    self.bids.append(row)

        min_start, max_start, max_end = promote.get_date_limits(
            link, c.user_is_sponsor)

        default_end = min_start + datetime.timedelta(days=7)
        default_start = min_start

        self.min_start = min_start.strftime("%m/%d/%Y")
        self.max_start = max_start.strftime("%m/%d/%Y")
        self.max_end = max_end.strftime("%m/%d/%Y")
        self.default_start = default_start.strftime("%m/%d/%Y")
        self.default_end = default_end.strftime("%m/%d/%Y")

        self.link = link
        self.listing = listing
        campaigns = list(PromoCampaign._by_link(link._id))
        self.campaigns = RenderableCampaign.from_campaigns(link, campaigns)
        self.promotion_log = PromotionLog.get(link)

        if c.user_is_sponsor:
            self.min_budget_dollars = 0
            self.max_budget_dollars = 0
        else:
            self.min_budget_dollars = g.min_total_budget_pennies / 100.
            self.max_budget_dollars = g.max_total_budget_pennies / 100.

        self.default_budget_dollars = g.default_total_budget_pennies / 100.

        if c.user_is_sponsor:
            self.min_bid_dollars = 0.
            self.max_bid_dollars = 0.
        else:
            self.min_bid_dollars = g.min_bid_pennies / 100.
            self.max_bid_dollars = g.max_bid_pennies / 100.

        self.priorities = [
            (p.name, p.text, p.description, p.default,
            p.inventory_override, p == PROMOTE_PRIORITIES['auction'])
            for p in PROMOTE_PRIORITIES.values()
        ]

        self.get_locations()
        self.get_collections()
        self.get_mobile_versions()

        user_srs = [sr for sr in Subreddit.user_subreddits(c.user, ids=False)
                    if sr.can_submit(c.user, promotion=True) and sr.allow_ads]
        top_srs = sorted(user_srs, key=lambda sr: sr._ups, reverse=True)[:20]
        extra_subreddits = [(_("suggestions:"), top_srs)]
        self.subreddit_selector = SubredditSelector(
            extra_subreddits=extra_subreddits, include_user_subscriptions=False)
        self.inventory = {}
        message = _("Create your ad on this page. Have questions? "
                    "Check out the [Help Center](%(help_center)s) "
                    "or [/r/selfserve](%(selfserve)s).")
        message %= {
            'help_center': 'https://reddit.zendesk.com/hc/en-us/categories/200352595-Advertising',
            'selfserve': 'https://www.reddit.com/r/selfserve'
        }
        self.infobar = RedditInfoBar(message=message)
        self.price_dict = PromotionPrices.get_price_dict(self.author)

        self.frequency_cap_min = g.frequency_cap_min

        self.ads_auction_enabled = feature.is_enabled('ads_auction')


class RenderableCampaign(Templated):
    def __init__(self, link, campaign, transaction, is_pending, is_live,
                 is_complete, is_edited_live, full_details=True,
                 hide_after_seen=False):
        self.link = link
        self.campaign = campaign

        self.ads_auction_enabled = feature.is_enabled('ads_auction')
        if self.ads_auction_enabled:
            self.is_auction = campaign.is_auction
        else:
            self.is_auction = False

        # Permission to edit is always granted when:
        # 1) Advertiser is sponsor
        # 2) Campaign is auction
        # 3) Auction is not enabled and campaign is not live
        if (c.user_is_sponsor or campaign.is_auction or
                (not self.ads_auction_enabled and not is_live)):
            self.editable = True
        else:
            self.editable = False

        # Convert total_budget_pennies to dollars for UI
        self.total_budget_dollars = campaign.total_budget_pennies / 100.

        if full_details:
            if not self.campaign.is_house and not self.campaign.is_auction:
                self.spent = promote.get_spent_amount(campaign)
            else:
                self.spent = campaign.adserver_spent_pennies / 100.
        else:
            self.spent = 0.

        self.paid = bool(transaction and not transaction.is_void())
        self.free = campaign.is_freebie()
        self.is_pending = is_pending
        self.is_live = is_live
        self.is_complete = is_complete
        self.is_edited_live = is_edited_live
        self.needs_refund = (is_complete and c.user_is_sponsor and
                             (transaction and not transaction.is_refund()) and
                             self.spent < campaign.total_budget_dollars)
        self.pay_url = promote.pay_url(link, campaign)
        sr_name = random.choice(campaign.target.subreddit_names)
        self.view_live_url = promote.view_live_url(link, campaign, sr_name)
        self.refund_url = promote.refund_url(link, campaign)

        if campaign.location:
            self.country = campaign.location.country or ''
            self.region = campaign.location.region or ''
            self.metro = campaign.location.metro or ''
        else:
            self.country, self.region, self.metro = '', '', ''
        self.location_str = campaign.location_str
        if campaign.target.is_collection:
            self.targeting_data = campaign.target.collection.name
        else:
            sr_name = campaign.target.subreddit_name
            # LEGACY: sponsored.js uses blank to indicate no targeting, meaning
            # targeted to the frontpage
            self.targeting_data = '' if sr_name == Frontpage.name else sr_name

        self.platform = campaign.platform
        self.mobile_os = campaign.mobile_os
        self.ios_devices = campaign.ios_devices
        self.ios_versions = campaign.ios_version_range
        self.android_devices = campaign.android_devices
        self.android_versions = campaign.android_version_range

        self.pause_ads_enabled = feature.is_enabled('pause_ads')

        # If ads_auction not enabled, default cost_basis to fixed_cpm
        if not feature.is_enabled('ads_auction'):
            self.cost_basis = PROMOTE_COST_BASIS.name[PROMOTE_COST_BASIS.fixed_cpm]
        elif campaign.cost_basis != PROMOTE_COST_BASIS.fixed_cpm:
            self.cost_basis = PROMOTE_COST_BASIS.name[campaign.cost_basis]
        else:
            self.cost_basis = PROMOTE_COST_BASIS.name[PROMOTE_COST_BASIS.cpm]
        self.bid_pennies = campaign.bid_pennies

        self.printable_bid = format_currency(campaign.bid_dollars, 'USD',
            locale=c.locale)

        Templated.__init__(self)

    @classmethod
    def from_campaigns(cls, link, campaigns,
                       full_details=True, hide_after_seen=False):
        campaigns, is_single = tup(campaigns, ret_is_single=True)

        if full_details:
            transactions = promote.get_transactions(link, campaigns)
            live_campaigns = promote.live_campaigns_by_link(link)
        else:
            transactions = {}
            live_campaigns = []

        ret = []
        now = promote.promo_datetime_now()
        for camp in campaigns:
            transaction = transactions.get(camp._id)
            is_pending = promote.is_pending(camp)
            is_live = camp in live_campaigns
            is_charged_or_refunded = (transaction and
                (transaction.is_charged() or transaction.is_refund()))
            is_expired_house = camp.is_house and camp.end_date < now
            is_live_or_pending = is_live or is_pending
            is_edited_live = promote.is_edited_live(link)
            is_complete = (not is_edited_live and
                (is_charged_or_refunded and
                not is_live_or_pending) or
                is_expired_house)
            rc = cls(link, camp, transaction, is_pending, is_live, is_complete,
                     is_edited_live, full_details, hide_after_seen)
            ret.append(rc)
        if is_single:
            return ret[0]
        else:
            return ret

    def render_html(self):
        return spaceCompress(self.render(style='html'))


class RefundPage(Reddit):
    def __init__(self, link, campaign):
        self.link = link
        self.campaign = campaign
        self.listing = wrap_links(link, skip=False)
        billable_impressions = promote.get_billable_impressions(campaign)
        billable_amount = promote.get_billable_amount(campaign,
                                                      billable_impressions)
        refund_amount = promote.get_refund_amount(campaign, billable_amount)
        self.billable_impressions = billable_impressions
        self.billable_amount = billable_amount
        self.refund_amount = refund_amount
        self.printable_total_budget = format_currency(
            campaign.total_budget_dollars, 'USD', locale=c.locale)
        self.printable_bid = format_currency(campaign.bid_dollars, 'USD',
            locale=c.locale)
        self.traffic_url = '/traffic/%s/%s' % (link._id36, campaign._id36)
        Reddit.__init__(self, title="refund", show_sidebar=False)

class PromotePost(PromoteLinkBase):
    def __init__(self):
        PromoteLinkBase.__init__(self)


class SponsorLookupUser(PromoteLinkBase):
    def __init__(self, id_user=None, email=None, email_users=None):
        PromoteLinkBase.__init__(
            self, id_user=id_user, email=email, email_users=email_users or [])




class SponsorLookupUser(PromoteLinkBase):
    def __init__(self, id_user=None, email=None, email_users=None):
        PromoteLinkBase.__init__(
            self, id_user=id_user, email=email, email_users=email_users or [])


class TabbedPane(Templated):
    def __init__(self, tabs, linkable=False):
        """Renders as tabbed area where you can choose which tab to
        render. Tabs is a list of tuples (tab_name, tab_pane)."""
        buttons = []
        for tab_name, title, pane in tabs:
            onclick = "return select_tab_menu(this, '%s')" % tab_name
            buttons.append(JsButton(title, tab_name=tab_name, onclick=onclick))

        self.tabmenu = JsNavMenu(buttons, type = 'tabmenu')
        self.tabs = tabs

        Templated.__init__(self, linkable=linkable)

class LinkChild(object):
    def __init__(self, link, load=False, expand=False, nofollow=False,
                 position_inline=False):
        self.link = link
        self.expand = expand
        self.load = load or expand
        self.nofollow = nofollow
        self.position_inline = position_inline

    def content(self):
        return ''

def make_link_child(item, show_media_preview=False):
    link_child = None
    editable = False
    expandable = getattr(item, 'expand_children', False)

    # if the item has a media_object, try to make a MediaEmbed for rendering
    if not c.secure:
        media_object = item.media_object
    else:
        media_object = item.secure_media_object

    if media_object:
        media_embed = None
        expand = False
        position_inline = False

        if isinstance(media_object, basestring):
            media_embed = media_object
        else:
            is_autoexpand_type = media_object.get('type') in g.autoexpand_media_types
            expand = expandable and (show_media_preview or is_autoexpand_type)
            position_inline = expandable and is_autoexpand_type

            try:
                media_embed = media.get_media_embed(media_object)
            except TypeError:
                g.log.warning("link %s has a bad media object" % item)
                media_embed = None

            if media_embed:
                if media_embed.sandbox:
                    should_authenticate = (item.subreddit.type in Subreddit.private_types or
                        item.subreddit.quarantine)
                    media_embed = MediaEmbed(
                        media_domain=g.media_domain,
                        height=media_embed.height + 10,
                        width=media_embed.width + 10,
                        scrolling=media_embed.scrolling,
                        id36=item._id36,
                        authenticated=should_authenticate,
                    )
                else:
                    media_embed = media_embed.content
            else:
                g.log.debug("media_object without media_embed %s" % item)

        if media_embed:
            link_child = MediaChild(item,
                                    media_embed,
                                    load=True,
                                    expand=expand,
                                    position_inline=position_inline)

    # if the item is_self, add a selftext child
    elif item.is_self:
        if not item.selftext: item.selftext = u''

        expand = expandable
        position_inline = expandable
        editable = (expand and
                    item.author == c.user and
                    not item._deleted)
        link_child = SelfTextChild(item,
                                   expand=expand,
                                   nofollow=item.nofollow,
                                   position_inline=position_inline)
    # if the item has a preview image and is on the whitelist, show it
    elif (feature.is_enabled('media_previews') and
            item.preview_object and
            media.allowed_media_preview_url(item.url)):
        media_object = media.get_preview_image(
            item.preview_object,
            include_censored=item.nsfw,
        )
        expand = show_media_preview and expandable

        if media_object:
            media_preview = MediaPreview(
                media_object=media_object,
                id36=item._id36,
                url=item.url,
            )
            link_child = MediaChild(
                item,
                media_preview,
                load=True,
                expand=expand,
                position_inline=False,
            )

    return link_child, editable


class MediaChild(LinkChild):
    """renders when the user hits the expando button to expand media
       objects, like embedded videos"""
    css_style = "video"
    def __init__(self, link, content, **kw):
        self._content = content
        LinkChild.__init__(self, link, **kw)

    def content(self):
        if isinstance(self._content, basestring):
            return self._content
        return self._content.render()

class MediaEmbed(Templated):
    """The actual rendered iframe for a media child"""

    def __init__(self, *args, **kwargs):
        authenticated = kwargs.pop("authenticated", False)
        if authenticated:
            mac = hmac.new(g.secrets["media_embed"], kwargs["id36"],
                           hashlib.sha1)
            self.credentials = "/" + mac.hexdigest()
        else:
            self.credentials = ""
        Templated.__init__(self, *args, **kwargs)


class MediaPreview(Templated):
    """Rendered html container for a media child"""

    def __init__(self, media_object, id36, url, **kwargs):
        self.media_content = media_object["content"]
        self.width = media_object["width"]
        self.id36 = id36
        self.url = url
        super(MediaPreview, self).__init__(**kwargs)


class SelfTextChild(LinkChild):
    css_style = "selftext"

    def content(self):
        u = UserText(self.link, self.link.selftext,
                     editable = c.user == self.link.author,
                     nofollow = self.nofollow,
                     expunged=self.link.expunged)
        return u.render()

class UserText(CachedTemplate):
    cachable = False

    def __init__(self,
                 item,
                 text = '',
                 have_form = True,
                 editable = False,
                 creating = False,
                 nofollow = False,
                 target = None,
                 display = True,
                 post_form = 'editusertext',
                 cloneable = False,
                 extra_css = '',
                 textarea_class = '',
                 name = "text",
                 expunged=False,
                 include_errors=True,
                 show_embed_help=False,
                 admin_takedown=False,
                 data_attrs={},
                 source=None,
                ):

        css_class = "usertext"
        if cloneable:
            css_class += " cloneable"
        if extra_css:
            css_class += " " + extra_css

        if text is None:
            text = ''

        # set the attribute for admin takedowns
        if getattr(item, 'admin_takedown', False):
            admin_takedown = True

        fullname = ''
        # Do not pass fullname on deleted things, unless we're admin
        if hasattr(item, '_fullname'):
            if not getattr(item, 'deleted', False) or c.user_is_admin:
                fullname = item._fullname

        CachedTemplate.__init__(self,
                                fullname = fullname,
                                text = text,
                                have_form = have_form,
                                editable = editable,
                                creating = creating,
                                nofollow = nofollow,
                                target = target,
                                display = display,
                                post_form = post_form,
                                cloneable = cloneable,
                                css_class = css_class,
                                textarea_class = textarea_class,
                                name = name,
                                expunged=expunged,
                                include_errors=include_errors,
                                show_embed_help=show_embed_help,
                                admin_takedown=admin_takedown,
                                data_attrs=data_attrs,
                                source=source,
                               )

class MediaEmbedBody(CachedTemplate):
    """What's rendered inside the iframe that contains media objects"""
    def render(self, *a, **kw):
        res = CachedTemplate.render(self, *a, **kw)
        return responsive(res, True)


class PaymentForm(Templated):
    countries = sorted({c['name'] for c in g.locations.values()})

    default_country = g.locations.get("US").get("name")

    def __init__(self, link, campaign, **kw):
        self.link = link
        self.duration = strings.time_label
        self.duration %= {'num': campaign.ndays,
                          'time': ungettext("day", "days", campaign.ndays)}
        self.start_date = campaign.start_date.strftime("%m/%d/%Y")
        self.end_date = campaign.end_date.strftime("%m/%d/%Y")
        self.campaign_id36 = campaign._id36
        self.budget = format_currency(campaign.total_budget_dollars, 'USD',
            locale=c.locale)
        Templated.__init__(self, **kw)


class Bookings(object):
    def __init__(self):
        self.subreddit = 0
        self.collection = 0

    def __add__(self, other):
        if isinstance(other, int) and other == 0:
            return self

        added = Bookings()
        added.subreddit = self.subreddit + other.subreddit
        added.collection = self.collection + other.collection

        return added

    def __radd__(self, other):
        return self.__add__(other)

    def __repr__(self):
        if self.subreddit and not self.collection:
            return format_number(self.subreddit)
        elif self.collection and not self.subreddit:
            return "%s*" % format_number(self.collection)
        elif not self.subreddit and not self.collection:
            return format_number(0)
        else:
            nums = tuple(map(format_number, (self.subreddit, self.collection)))
            return "%s (%s*)" % nums


class PromoteInventory(PromoteLinkBase):
    def __init__(self, start, end, target):
        Templated.__init__(self)
        self.start = start
        self.end = end
        self.default_start = start.strftime('%m/%d/%Y')
        self.default_end = end.strftime('%m/%d/%Y')
        self.target = target
        self.display_name = target.pretty_name
        p = request.GET.copy()
        self.csv_url = '%s.csv?%s' % (request.path, urlencode(p))
        if target.is_collection:
            self.sr_input = None
            self.collection_input = target.collection.name
            self.targeting_type = "collection"
        else:
            self.sr_input = target.subreddit_name
            self.collection_input = None
            self.targeting_type = "collection" if target.subreddit_name == Frontpage.name else "one"
        self.setup()

    def as_csv(self):
        out = cStringIO.StringIO()
        writer = csv.writer(out)

        writer.writerow(tuple(self.header))

        for row in self.rows:
            if not row.is_total:
                outrow = [row.info['author']]
            else:
                outrow = [row.info['title']]
            outrow.extend(row.columns)
            writer.writerow(outrow)

        return out.getvalue()

    def setup(self):
        srs = self.target.subreddits_slow
        campaigns_by_date = inventory.get_campaigns_by_date(
            srs, self.start, self.end)
        link_ids = {camp.link_id for camp
                    in chain.from_iterable(campaigns_by_date.itervalues())}
        links_by_id = Link._byID(link_ids, data=True)
        dates = inventory.get_date_range(self.start, self.end)
        total_by_date = {date: Bookings() for date in dates}
        imps_by_link = defaultdict(lambda: {date: Bookings() for date in dates})
        for date, campaigns in campaigns_by_date.iteritems():
            for camp in campaigns:
                link = links_by_id[camp.link_id]
                daily_impressions = camp.impressions / camp.ndays
                if camp.target.is_collection:
                    total_by_date[date].collection += daily_impressions
                    imps_by_link[link._id][date].collection += daily_impressions
                else:
                    total_by_date[date].subreddit += daily_impressions
                    imps_by_link[link._id][date].subreddit += daily_impressions

        account_ids = {link.author_id for link in links_by_id.itervalues()}
        accounts_by_id = Account._byID(account_ids, data=True)

        self.header = ['link'] + [date.strftime("%m/%d/%Y") for date in dates] + ['total']
        rows = []
        for link_id, imps_by_date in imps_by_link.iteritems():
            link = links_by_id[link_id]
            author = accounts_by_id[link.author_id]
            info = {
                'author': author.name,
                'edit_url': promote.promo_edit_url(link),
            }
            row = Storage(info=info, is_total=False)
            row.columns = ([str(imps_by_date[date]) for date in dates] +
                [str(sum(imps_by_date.values()))])
            rows.append(row)
        rows.sort(key=lambda row: row.info['author'].lower())

        total_row = Storage(
            info={'title': 'total'},
            is_total=True,
            columns=([str(total_by_date[date]) for date in dates] +
                [str(sum(total_by_date.values()))]),
        )
        rows.append(total_row)

        predicted_pageviews_by_sr = inventory.get_predicted_pageviews(srs)
        predicted_pageviews = sum(pageviews for pageviews
                                  in predicted_pageviews_by_sr.itervalues())
        predicted_row = Storage(
            info={'title': 'predicted'},
            is_total=True,
            columns=([format_number(predicted_pageviews) for date in dates] +
                [format_number(sum([predicted_pageviews for date in dates]))]),
        )
        rows.append(predicted_row)

        available_pageviews = inventory.get_available_pageviews(
            self.target, self.start, self.end)
        remaining_row = Storage(
            info={'title': 'remaining'},
            is_total=True,
            columns=([format_number(available_pageviews[date]) for date in dates] +
                [format_number(sum(available_pageviews.values()))]),
        )
        rows.append(remaining_row)

        self.rows = rows

        default_sr = None
        if not self.target.is_collection and self.sr_input:
            default_sr = Subreddit._by_name(self.sr_input)
        self.subreddit_selector = SubredditSelector(
                default_sr=default_sr,
                include_user_subscriptions=False)

        self.get_locations()
        self.get_collections()


ReportKey = namedtuple("ReportKey", ["date", "link", "campaign"])
ReportItem = namedtuple("ReportItem",
    ["bid", "fp_imps", "sr_imps", "fp_clicks", "sr_clicks"])


class PromoteReport(PromoteLinkBase):
    def __init__(self, links, link_text, owner_name, bad_links, start, end,
                 group_by_date=False):
        self.links = links
        self.start = start
        self.end = end
        self.default_start = start.strftime('%m/%d/%Y')
        self.default_end = end.strftime('%m/%d/%Y')
        self.group_by_date = group_by_date

        if links:
            self.make_report()
            p = request.GET.copy()
            self.csv_url = '%s.csv?%s' % (request.path, urlencode(p))
        else:
            self.link_report = []
            self.campaign_report = []
            self.csv_url = None

        Templated.__init__(self, link_text=link_text, owner_name=owner_name,
                           bad_links=bad_links)

    def as_csv(self):
        out = cStringIO.StringIO()
        writer = csv.writer(out)

        writer.writerow((_("start date"), self.start.strftime('%m/%d/%Y')))
        writer.writerow((_("end date"), self.end.strftime('%m/%d/%Y')))
        writer.writerow([])
        writer.writerow((_("links"),))
        if self.group_by_date:
            outrow = [_("date")]
        else:
            outrow = []
        outrow.extend([_("id"), _("owner"), _("url"), _("comments"),
            _("upvotes"), _("downvotes"), _("clicks"), _("impressions")])
        writer.writerow(outrow)
        for row in self.link_report:
            if self.group_by_date:
                outrow = [row['date']]
            else:
                outrow = []
            outrow.extend([row['id36'], row['owner'], row['url'],
                row['comments'], row['upvotes'], row['downvotes'],
                row['clicks'], row['impressions']])
            writer.writerow(outrow)

        writer.writerow([])
        writer.writerow((_("campaigns"),))
        if self.group_by_date:
            outrow = [_("date")]
        else:
            outrow = []
        outrow.extend([_("link id"), _("owner"), _("campaign id"), _("target"),
            _("bid"), _("frontpage clicks"), _("frontpage impressions"),
            _("subreddit clicks"), _("subreddit impressions"),
            _("total clicks"), _("total impressions")])
        writer.writerow(outrow)
        for row in self.campaign_report:
            if self.group_by_date:
                outrow = [row['date']]
            else:
                outrow = []
            outrow.extend([row['link'], row['owner'], row['campaign'],
                row['target'], row['bid'], row['fp_clicks'],
                row['fp_impressions'], row['sr_clicks'], row['sr_impressions'],
                row['total_clicks'], row['total_impressions']])
            writer.writerow(outrow)
        return out.getvalue()

    @classmethod
    def get_traffic(self, campaigns, start, end):
        campaigns_by_name = {camp._fullname: camp for camp in campaigns}
        codenames = campaigns_by_name.keys()

        start_date = start.date()
        ndays = (end - start).days
        dates = {start_date + datetime.timedelta(days=i) for i in xrange(ndays)}

        # traffic database uses datetimes with no timezone, also need to shift
        # start, end to account for campaigns launching at 12:00 EST
        start = (start - promote.timezone_offset).replace(tzinfo=None)
        end = (end - promote.timezone_offset).replace(tzinfo=None)

        # start and end are dates so we need to subtract an hour from end to
        # only include 24 hours per day
        end -= datetime.timedelta(hours=1)

        fp_imps_by_date = {d: defaultdict(int) for d in dates}
        sr_imps_by_date = {d: defaultdict(int) for d in dates}
        fp_clicks_by_date = {d: defaultdict(int) for d in dates}
        sr_clicks_by_date = {d: defaultdict(int) for d in dates}

        imps = traffic.TargetedImpressionsByCodename.campaign_history(
            codenames, start, end)
        clicks = traffic.TargetedClickthroughsByCodename.campaign_history(
            codenames, start, end)

        for date, codename, sr, (uniques, pageviews) in imps:
            # convert from utc hour to campaign date
            traffic_date = (date + promote.timezone_offset).date()

            if sr == '':
                # LEGACY: traffic uses '' to indicate Frontpage
                fp_imps_by_date[traffic_date][codename] += pageviews
            else:
                sr_imps_by_date[traffic_date][codename] += pageviews

        for date, codename, sr, (uniques, pageviews) in clicks:
            traffic_date = (date + promote.timezone_offset).date()

            if sr == '':
                # NOTE: clicks use hourly uniques
                fp_clicks_by_date[traffic_date][codename] += uniques
            else:
                sr_clicks_by_date[traffic_date][codename] += uniques

        traffic_by_key = {}
        for camp in campaigns:
            fullname = camp._fullname
            bid = camp.total_budget_pennies / max(camp.ndays, 1)
            camp_ndays = max(1, (camp.end_date - camp.start_date).days)
            camp_start = camp.start_date.date()
            days = xrange(camp_ndays)
            camp_dates = {camp_start + datetime.timedelta(days=i) for i in days}

            for date in camp_dates.intersection(dates):
                fp_imps = fp_imps_by_date[date][fullname]
                sr_imps = sr_imps_by_date[date][fullname]
                fp_clicks = fp_clicks_by_date[date][fullname]
                sr_clicks = sr_clicks_by_date[date][fullname]
                key = ReportKey(date, camp.link_id, camp._fullname)
                item = ReportItem(bid, fp_imps, sr_imps, fp_clicks, sr_clicks)
                traffic_by_key[key] = item
        return traffic_by_key

    def make_report(self):
        campaigns = PromoCampaign._by_link([link._id for link in self.links])
        campaigns = filter(promote.charged_or_not_needed, campaigns)
        traffic_by_key = self.get_traffic(campaigns, self.start, self.end)

        def group_and_combine(items_by_key, group_on=None):
            # combine all items whose keys have the same value for the
            # attributes in group_on, and create new keys with None values for
            # the attributes we aren't grouping on.
            by_group = defaultdict(list)
            for item_key, item in items_by_key.iteritems():
                attrs = [getattr(item_key, a) if a in group_on else None
                    for a in ReportKey._fields]
                group_key = ReportKey(*attrs)
                by_group[group_key].append(item)

            new_items_by_key = {}
            for group_key, items in by_group.iteritems():
                bid = fp_imps = sr_imps = fp_clicks = sr_clicks = 0
                for item in items:
                    bid += item.bid
                    fp_imps += item.fp_imps
                    sr_imps += item.sr_imps
                    fp_clicks += item.fp_clicks
                    sr_clicks += item.sr_clicks
                item = ReportItem(bid, fp_imps, sr_imps, fp_clicks, sr_clicks)
                new_items_by_key[group_key] = item
            return new_items_by_key

        # make the campaign report
        if not self.group_by_date:
            traffic_by_key = group_and_combine(
                traffic_by_key, group_on=["link", "campaign"])

        owners = Account._byID([link.author_id for link in self.links],
                               data=True)
        links_by_id = {link._id: link for link in self.links}
        camps_by_name = {camp._fullname: camp for camp in campaigns}

        self.campaign_report_totals = {
            'fp_clicks': 0,
            'fp_imps': 0,
            'sr_clicks': 0,
            'sr_imps': 0,
            'total_clicks': 0,
            'total_imps': 0,
            'bid': 0,
        }
        self.campaign_report = []
        for rk in sorted(traffic_by_key):
            item = traffic_by_key[rk]
            link = links_by_id[rk.link]
            camp = camps_by_name[rk.campaign]

            self.campaign_report_totals['fp_clicks'] += item.fp_clicks
            self.campaign_report_totals['fp_imps'] += item.fp_imps
            self.campaign_report_totals['sr_clicks'] += item.sr_clicks
            self.campaign_report_totals['sr_imps'] += item.sr_imps
            self.campaign_report_totals['bid'] += item.bid

            self.campaign_report.append({
                'date': rk.date,
                'link': link._id36,
                'owner': owners[link.author_id].name,
                'campaign': camp._id36,
                'target': camp.target.pretty_name,
                'bid': format_currency(item.bid, 'USD', locale=c.locale),
                'fp_impressions': item.fp_imps,
                'sr_impressions': item.sr_imps,
                'fp_clicks': item.fp_clicks,
                'sr_clicks': item.sr_clicks,
                'total_impressions': item.fp_imps + item.sr_imps,
                'total_clicks': item.fp_clicks + item.sr_clicks,
            })
        crt = self.campaign_report_totals
        crt['total_clicks'] = crt['sr_clicks'] + crt['fp_clicks']
        crt['total_imps'] = crt['sr_imps'] + crt['fp_imps']
        crt['bid'] = format_currency(crt['bid'], 'USD', locale=c.locale)
        # make the link report
        traffic_by_key = group_and_combine(
                traffic_by_key, group_on=["link", "date"])

        self.link_report = []
        for rk in sorted(traffic_by_key):
            item = traffic_by_key[rk]
            link = links_by_id[rk.link]
            self.link_report.append({
                'date': rk.date,
                'owner': owners[link.author_id].name,
                'id36': link._id36,
                'comments': link.num_comments,
                'upvotes': link._ups,
                'downvotes': link._downs,
                'clicks': item.fp_clicks + item.sr_clicks,
                'impressions': item.fp_imps + item.sr_imps,
                'url': link.url,
            })


class RawString(Templated):
   def __init__(self, s):
       self.s = s

   def render(self, *a, **kw):
       return unsafe(self.s)


class TryCompact(Reddit):
    def __init__(self, dest, **kw):
        dest = dest or "/"
        u = UrlParser(dest)
        u.set_extension("compact")
        self.compact = u.unparse()

        u.update_query(keep_extension = True)
        self.like = u.unparse()

        u.set_extension("mobile")
        self.mobile = u.unparse()
        Reddit.__init__(self, **kw)

class AccountActivityPage(BoringPage):
    def __init__(self):
        super(AccountActivityPage, self).__init__(_("account activity"))

    def content(self):
        return UserIPHistory()

class UserIPHistory(Templated):
    def __init__(self):
        self.my_apps = OAuth2Client._by_user_grouped(c.user)
        self.ips = ips_by_account_id(c.user._id)

        if not c.user_is_admin:
            self.ips = [
                ip
                for ip in self.ips
                if not ip_address(ip[0]).is_private
            ]
        super(UserIPHistory, self).__init__()

class ApiHelp(Templated):
    def __init__(self, api_docs, *a, **kw):
        self.api_docs = api_docs
        super(ApiHelp, self).__init__(*a, **kw)


class AwardReceived(Templated):
    pass

class ConfirmAwardClaim(Templated):
    pass

class TimeSeriesChart(Templated):
    def __init__(self, id, title, interval, columns, rows,
                 latest_available_data=None, classes=[],
                 make_period_link=None):
        self.id = id
        self.title = title
        self.interval = interval
        self.columns = columns
        self.rows = rows
        self.latest_available_data = (latest_available_data or
                                      datetime.datetime.utcnow())
        self.classes = " ".join(classes)
        self.make_period_link = make_period_link

        Templated.__init__(self)

class InterestBar(Templated):
    def __init__(self, has_subscribed):
        self.has_subscribed = has_subscribed
        Templated.__init__(self)

class Goldvertisement(Templated):
    def __init__(self):
        now = datetime.datetime.now(GOLD_TIMEZONE)
        today = now.date()
        tomorrow = now + datetime.timedelta(days=1)
        end_time = tomorrow.replace(hour=0, minute=0, second=0, microsecond=0)
        revenue_today = gold_revenue_volatile(today)
        yesterday = today - datetime.timedelta(days=1)
        revenue_yesterday = gold_revenue_steady(yesterday)
        revenue_goal = gold_goal_on(today)
        revenue_goal_yesterday = gold_goal_on(yesterday)

        if revenue_goal:
            self.percent_filled = int((revenue_today / revenue_goal) * 100)
        else:
            self.percent_filled = 0

        if revenue_goal_yesterday:
            self.percent_filled_yesterday = int((revenue_yesterday /
                                                 revenue_goal_yesterday) * 100)
        else:
            self.percent_filled_yesterday = 0

        seconds = get_current_value_of_month()
        delta = datetime.timedelta(seconds=seconds)
        self.hours_paid = precise_format_timedelta(
            delta, threshold=5, locale=c.locale)

        self.time_left_today = timeuntil(end_time, precision=60)
        if c.user.employee:
            self.goal_today = revenue_goal / 100.0
            self.goal_yesterday = revenue_goal_yesterday / 100.0

        if c.user_is_loggedin:
            self.default_type = "autorenew"
        else:
            self.default_type = "code"

        Templated.__init__(self)

class LinkCommentsSettings(Templated):
    def __init__(self, link, sort, suggested_sort):
        Templated.__init__(self)
        self.sr = link.subreddit_slow
        self.link = link
        self.is_author = c.user_is_loggedin and c.user._id == link.author_id
        self.contest_mode = link.contest_mode
        self.stickied = link.is_stickied(self.sr)
        self.stickies_full = self.sr.has_max_stickies
        self.sendreplies = link.sendreplies
        self.can_edit = (
            c.user_is_loggedin and
            (c.user_is_admin or
                self.sr.is_moderator_with_perms(c.user, "posts"))
        )
        self.can_sticky = False
        if self.can_edit:
            if self.stickied:
                # always allow un-stickying things
                self.can_sticky = True
            # non deleted/spam self-posts by mods are eligible for stickying
            else:
                self.can_sticky = link.is_stickyable()
        self.sort = sort
        self.suggested_sort = suggested_sort

class ModeratorPermissions(Templated):
    def __init__(self, user, permissions_type, permissions,
                 editable=False, embedded=False):
        self.user = user
        self.permissions = permissions
        Templated.__init__(self, permissions_type=permissions_type,
                           editable=editable, embedded=embedded)

    def items(self):
        return self.permissions.iteritems()

class ListingChooser(Templated):
    def __init__(self):
        Templated.__init__(self)
        self.sections = defaultdict(list)
        self.add_item("global", _("subscribed"), site=Frontpage,
                      description=_("your front page"))
        self.add_item("global", _("explore"), path="/explore")
        if c.user_is_loggedin and c.user.gold:
            self.add_item("other", _("everything"),
                          path="/me/f/all",
                          extra_class="gold-perks",
                          description=_("from all subreddits"))
        else:
            self.add_item("other", _("everything"), site=All,
                          description=_("from all subreddits"))
        if c.user_is_loggedin and c.user.is_moderator_somewhere:
            self.add_item("other", _("moderating"), site=Mod,
                          description=_("subreddits you mod"))

        self.add_item("other", _("saved"), path='/user/%s/saved' % c.user.name)

        gold_multi = g.live_config["listing_chooser_gold_multi"]
        if c.user_is_loggedin and c.user.gold and gold_multi:
            self.add_item("other", name=_("gold perks"), path=gold_multi,
                          extra_class="gold-perks")

        self.show_samples = False
        if c.user_is_loggedin:
            multis = LabeledMulti.by_owner(c.user, load_subreddits=False)
            multis.sort(key=lambda multi: multi.name.lower())
            for multi in multis:
                if not multi.is_hidden():
                    self.add_item("multi", multi.name, site=multi)

            explore_sr = g.live_config["listing_chooser_explore_sr"]
            if explore_sr:
                sr = Subreddit._by_name(explore_sr, stale=True)
                self.add_item("multi", name=_("explore multis"), site=sr)

            self.show_samples = not multis

        if self.show_samples:
            self.add_samples()

        self.selected_item = self.find_selected()
        if self.selected_item:
            self.selected_item["selected"] = True

    def add_item(self, section, name, path=None, site=None, description=None,
                 extra_class=None):
        self.sections[section].append({
            "name": name,
            "description": description,
            "path": path or site.user_path,
            "site": site,
            "selected": False,
            "extra_class": extra_class,
        })

    def add_samples(self):
        for path in g.live_config["listing_chooser_sample_multis"]:
            self.add_item(
                section="sample",
                name=path.rpartition('/')[2],
                path=path,
            )

    def find_selected(self):
        path = request.path
        matching = []
        for item in chain(*self.sections.values()):
            if item["site"]:
                if item["site"] == c.site:
                    matching.append(item)
            elif path.startswith(item["path"]):
                matching.append(item)

        matching.sort(key=lambda item: len(item["path"]), reverse=True)
        return matching[0] if matching else None

class PolicyView(Templated):
    pass


class PolicyPage(BoringPage):
    css_class = 'policy-page'
    show_infobar = False

    def __init__(self, pagename=None, content=None, **kw):
        BoringPage.__init__(self, pagename=pagename, show_sidebar=False,
            content=content, **kw)
        self.welcomebar = None

    def build_toolbars(self):
        toolbars = BoringPage.build_toolbars(self)
        policies_buttons = [
            NavButton(_('privacy policy'), '/privacypolicy'),
            NavButton(_('user agreement'), '/useragreement'),
            NavButton(_('content policy'), '/contentpolicy'),
        ]
        policies_menu = NavMenu(policies_buttons, type='tabmenu',
                                base_path='/help')
        toolbars.append(policies_menu)
        return toolbars


class GoogleTagManagerJail(Templated):
    pass


class GoogleTagManager(Templated):
    pass


class Newsletter(BoringPage):
    extra_page_classes = ['newsletter']

    def __init__(self, pagename=None, content=None, **kw):
        BoringPage.__init__(self, pagename=pagename, show_sidebar=False,
                            content=content, **kw)


class SubscribeButton(Templated):
    def __init__(self, sr, bubble_class=None):
        Templated.__init__(self)
        self.sr = sr
        self.data_attrs = {"sr_name": sr.name}
        if bubble_class:
            self.data_attrs["bubble_class"] = bubble_class


class QuarantineOptoutButton(Templated):
    def __init__(self, sr, bubble_class=None):
        Templated.__init__(self)
        self.sr = sr
        self.data_attrs = {"sr_name": sr.name}
        if bubble_class:
            self.data_attrs["bubble_class"] = bubble_class


class SubredditSelector(Templated):
    def __init__(self, default_sr=None, extra_subreddits=None, required=False,
                 include_searches=True, include_user_subscriptions=True, class_name=None,
                 placeholder=None, show_add=False):
        Templated.__init__(self)

        self.placeholder = placeholder
        self.class_name = class_name
        self.show_add = show_add

        if extra_subreddits:
            self.subreddits = extra_subreddits
        else:
            self.subreddits = []

        if include_user_subscriptions:
            self.subreddits.append((
                _('your subscribed subreddits'),
                Subreddit.user_subreddits(c.user, ids=False)
            ))

        self.default_sr = default_sr
        self.required = required
        if include_searches:
            self.sr_searches = simplejson.dumps(
                popular_searches(include_over_18=c.over18)
            )
        else:
            self.sr_searches = simplejson.dumps({})
        self.include_searches = include_searches

    @property
    def subreddit_names(self):
        groups = []
        for title, subreddits in self.subreddits:
            names = [sr.name for sr in subreddits if sr.can_submit(c.user)]
            names.sort(key=str.lower)
            groups.append((title, names))
        return groups


class ListingSuggestions(Templated):
    def __init__(self):
        Templated.__init__(self)

        self.suggestion_type = None
        if c.default_sr:
            if c.user_is_loggedin and random.randint(0, 1) == 1:
                self.suggestion_type = "explore"
                return

            if c.user_is_loggedin:
                multis = LabeledMulti.by_owner(c.user, load_subreddits=False)
            else:
                multis = []

            if multis and c.site in multis:
                multis.remove(c.site)

            if multis:
                self.suggestion_type = "multis"
                if len(multis) <= 3:
                    self.suggestions = multis
                else:
                    self.suggestions = random.sample(multis, 3)
            else:
                self.suggestion_type = "random"


class UnreadMessagesSuggestions(Templated):
    """Let a user mark all as read if they have > 1 page of unread messages."""
    pass


class ExploreItem(Templated):
    """For managing recommended content."""

    def __init__(self, item_type, rec_src, sr, link, comment=None):
        """Constructor.

        item_type - string that helps templates know how to render this item.
        rec_src - code that lets us track where the rec originally came from,
            useful for comparing performance of data sources or algorithms
        sr and link are required
        comment is optional

        See r2.lib.recommender for valid values of item_type and rec_src.

        """
        self.sr = sr
        self.link = link
        self.comment = comment
        self.type = item_type
        self.src = rec_src
        Templated.__init__(self)

    def is_over18(self):
        return self.sr.over_18 or self.link.is_nsfw


class ExploreItemListing(Templated):
    def __init__(self, recs, settings):
        self.things = []
        self.settings = settings
        if recs:
            links, srs = zip(*[(rec.link, rec.sr) for rec in recs])
            wrapped_links = {l._id: l for l in wrap_links(links).things}
            wrapped_srs = {sr._id: sr for sr in wrap_things(*srs)}
            for rec in recs:
                if rec.link._id in wrapped_links:
                    rec.link = wrapped_links[rec.link._id]
                    rec.sr = wrapped_srs[rec.sr._id]
                    self.things.append(rec)
        Templated.__init__(self)


class TrendingSubredditsBar(Templated):
    def __init__(self, subreddit_names, comment_url, comment_count):
        Templated.__init__(self)
        self.subreddit_names = subreddit_names
        self.comment_url = comment_url
        self.comment_count = comment_count
        self.comment_label, self.comment_label_cls = \
            comment_label(comment_count)


class GeotargetNotice(Templated):
    def __init__(self, city_target=False):
        self.targeting_level = "city" if city_target else "country"
        if city_target:
            text = _("this promoted link uses city level targeting and may "
                     "have been shown to you because of your location. "
                     "([learn more](%(link)s))")
        else:
            text = _("this promoted link uses country level targeting and may "
                     "have been shown to you because of your location. "
                     "([learn more](%(link)s))")
        more_link = "/wiki/targetingbycountrycity"
        self.text = text % {"link": more_link}
        Templated.__init__(self)


class ShareClose(Templated):
    pass
