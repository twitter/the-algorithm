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
from collections import defaultdict
from datetime import datetime, timedelta

from babel.dates import format_date
from babel.numbers import format_number
import hashlib
import hmac
import json
import urllib
import mimetypes
import os

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _, N_

from r2.config import feature
from r2.controllers.api import ApiController
from r2.controllers.listingcontroller import ListingController
from r2.controllers.reddit_base import RedditController
from r2.lib.authorize import (
    get_or_create_customer_profile,
    add_or_update_payment_method,
    PROFILE_LIMIT,
)
from r2.lib.authorize.api import AuthorizeNetException
from r2.lib import (
    hooks,
    inventory,
    media,
    promote,
    s3_helpers,
)
from r2.lib.base import abort
from r2.lib.db import queries
from r2.lib.errors import errors
from r2.lib.filters import (
    jssafe,
    scriptsafe_dumps,
    websafe,
)
from r2.lib.template_helpers import (
    add_sr,
    format_html,
)
from r2.lib.memoize import memoize
from r2.lib.menus import NamedButton, NavButton, NavMenu, QueryButton
from r2.lib.pages import (
    LinkInfoPage,
    PaymentForm,
    PromoteInventory,
    PromotePage,
    PromoteLinkEdit,
    PromoteLinkNew,
    PromoteReport,
    Reddit,
    RefundPage,
    RenderableCampaign,
    SponsorLookupUser,
)
from r2.lib.pages.things import default_thing_wrapper, wrap_links
from r2.lib.system_messages import user_added_messages
from r2.lib.utils import (
    constant_time_compare,
    is_subdomain,
    to_date,
    to36,
    UrlParser,
)
from r2.lib.validator import (
    json_validate,
    nop,
    noresponse,
    VAccountByName,
    ValidAddress,
    validate,
    validatedMultipartForm,
    validatedForm,
    ValidCard,
    ValidEmail,
    VBoolean,
    VByName,
    VCollection,
    VDate,
    VExistingUname,
    VFloat,
    VFrequencyCap,
    VImageType,
    VInt,
    VLength,
    VLink,
    VList,
    VLocation,
    VModhash,
    VOneOf,
    VOSVersion,
    VPrintable,
    VPriority,
    VPromoCampaign,
    VPromoTarget,
    VRatelimit,
    VMarkdownLength,
    VShamedDomain,
    VSponsor,
    VSponsorAdmin,
    VSponsorAdminOrAdminSecret,
    VVerifiedSponsor,
    VSubmitSR,
    VTitle,
    VUploadLength,
    VUrl,
)
from r2.models import (
    Account,
    AccountsByCanonicalEmail,
    calc_impressions,
    Collection,
    Frontpage,
    Link,
    Message,
    NotFound,
    PromoCampaign,
    PromotionLog,
    PromotionPrices,
    PromotionWeights,
    PROMOTE_STATUS,
    Subreddit,
    Target,
)
from r2.models.promo import PROMOTE_COST_BASIS, PROMOTE_PRIORITIES

IOS_DEVICES = ('iPhone', 'iPad', 'iPod',)
ANDROID_DEVICES = ('phone', 'tablet',)

ADZERK_URL_MAX_LENGTH = 499

EXPIRES_DATE_FORMAT = "%Y-%m-%dT%H:%M:%S"
ALLOWED_IMAGE_TYPES = set(["image/jpg", "image/jpeg", "image/png"])

def _format_expires(expires):
    return expires.strftime(EXPIRES_DATE_FORMAT)


def _get_callback_hmac(username, key, expires):
    secret = g.secrets["s3_direct_post_callback"]
    expires_str = _format_expires(expires)
    data = "|".join([username, key, expires_str])

    return hmac.new(secret, data, hashlib.sha256).hexdigest()


def _force_images(link, thumbnail, mobile):
    changed = False

    if thumbnail:
        media.force_thumbnail(link, thumbnail["data"], thumbnail["ext"])
        changed = True

    if feature.is_enabled("mobile_targeting") and mobile:
        media.force_mobile_ad_image(link, mobile["data"], mobile["ext"])
        changed = True

    return changed


def campaign_has_oversold_error(form, campaign):
    if campaign.priority.inventory_override:
        return

    return has_oversold_error(
        form,
        campaign,
        start=campaign.start_date,
        end=campaign.end_date,
        total_budget_pennies=campaign.total_budget_pennies,
        cpm=campaign.bid_pennies,
        target=campaign.target,
        location=campaign.location,
    )


def has_oversold_error(form, campaign, start, end, total_budget_pennies, cpm,
        target, location):
    ndays = (to_date(end) - to_date(start)).days
    total_request = calc_impressions(total_budget_pennies, cpm)
    daily_request = int(total_request / ndays)
    oversold = inventory.get_oversold(
        target, start, end, daily_request, ignore=campaign, location=location)

    if oversold:
        min_daily = min(oversold.values())
        available = min_daily * ndays
        msg_params = {
            'available': format_number(available, locale=c.locale),
            'target': target.pretty_name,
            'start': start.strftime('%m/%d/%Y'),
            'end': end.strftime('%m/%d/%Y'),
        }
        c.errors.add(errors.OVERSOLD_DETAIL, field='total_budget_dollars',
                     msg_params=msg_params)
        form.has_errors('total_budget_dollars', errors.OVERSOLD_DETAIL)
        return True


def _key_to_dict(key, data=False):
    timer = g.stats.get_timer("providers.s3.get_ads_key_meta.with_%s" %
        ("data" if data else "no_data"))
    timer.start()

    url = key.generate_url(expires_in=0, query_auth=False)
    # Generating an S3 url without authentication fails for IAM roles.
    # This removes the bad query params.
    # see: https://github.com/boto/boto/issues/2043
    url = promote.update_query(url, {"x-amz-security-token": None}, unset=True)

    result = {
        "url": url,
        "data": key.get_contents_as_string() if data else None,
        "ext": key.get_metadata("ext"),
    }

    timer.stop()

    return result


def _get_ads_keyspace(thing):
    return "ads/%s/" % thing._fullname


def _get_ads_images(thing, data=False, **kwargs):
    images = {}

    timer = g.stats.get_timer("providers.s3.get_ads_image_keys")
    timer.start()

    keys = s3_helpers.get_keys(g.s3_client_uploads_bucket, prefix=_get_ads_keyspace(thing), **kwargs)

    timer.stop()

    for key in keys:
        filename = os.path.basename(key.key)
        name, ext = os.path.splitext(filename)

        if name not in ("mobile", "thumbnail"):
            continue

        images[name] = _key_to_dict(key, data=data)

    return images


def _clear_ads_images(thing):
    timer = g.stats.get_timer("providers.s3.delete_ads_image_keys")
    timer.start()

    s3_helpers.delete_keys(g.s3_client_uploads_bucket, prefix=_get_ads_keyspace(thing))

    timer.stop()


class PromoteController(RedditController):
    @validate(VSponsor())
    def GET_new_promo(self):
        ads_images = _get_ads_images(c.user)
        images = {k: v.get("url") for k, v in ads_images.iteritems()}

        return PromotePage(title=_("create sponsored link"),
                           content=PromoteLinkNew(images),
                           extra_js_config={
                            "ads_virtual_page": "new-promo",
                           }).render()

    @validate(VSponsor('link'),
              link=VLink('link'))
    def GET_edit_promo(self, link):
        if not link or link.promoted is None:
            return self.abort404()
        rendered = wrap_links(link, skip=False)
        form = PromoteLinkEdit(link, rendered)
        page = PromotePage(title=_("edit sponsored link"), content=form,
                      show_sidebar=False, extension_handling=False)
        return page.render()

    @validate(VSponsorAdmin(),
              link=VLink("link"),
              campaign=VPromoCampaign("campaign"))
    def GET_refund(self, link, campaign):
        if link._id != campaign.link_id:
            return self.abort404()

        content = RefundPage(link, campaign)
        return Reddit("refund", content=content, show_sidebar=False).render()

    @validate(VVerifiedSponsor("link"),
              link=VLink("link"),
              campaign=VPromoCampaign("campaign"))
    def GET_pay(self, link, campaign):
        if link._id != campaign.link_id:
            return self.abort404()

        # no need for admins to play in the credit card area
        if c.user_is_loggedin and c.user._id != link.author_id:
            return self.abort404()

        if g.authorizenetapi:
            data = get_or_create_customer_profile(c.user)
            content = PaymentForm(link, campaign,
                                  customer_id=data.customerProfileId,
                                  profiles=data.paymentProfiles,
                                  max_profiles=PROFILE_LIMIT)
        else:
            content = None
        res = LinkInfoPage(link=link,
                            content=content,
                            show_sidebar=False,
                            extra_js_config={
                              "ads_virtual_page": "checkout",
                            })
        return res.render()


class SponsorController(PromoteController):
    @validate(VSponsorAdminOrAdminSecret('secret'),
              start=VDate('startdate'),
              end=VDate('enddate'),
              link_text=nop('link_text'),
              owner=VAccountByName('owner'),
              grouping=VOneOf("grouping", ("total", "day"), default="total"))
    def GET_report(self, start, end, grouping, link_text=None, owner=None):
        now = datetime.now(g.tz).replace(hour=0, minute=0, second=0,
                                         microsecond=0)
        if not start or not end:
            start = promote.promo_datetime_now(offset=1).date()
            end = promote.promo_datetime_now(offset=8).date()
            c.errors.remove((errors.BAD_DATE, 'startdate'))
            c.errors.remove((errors.BAD_DATE, 'enddate'))
        end = end or now - timedelta(days=1)
        start = start or end - timedelta(days=7)

        links = []
        bad_links = []
        owner_name = owner.name if owner else ''

        if owner:
            campaign_ids = PromotionWeights.get_campaign_ids(
                start, end, author_id=owner._id)
            campaigns = PromoCampaign._byID(campaign_ids, data=True)
            link_ids = {camp.link_id for camp in campaigns.itervalues()}
            links.extend(Link._byID(link_ids, data=True, return_dict=False))

        if link_text is not None:
            id36s = link_text.replace(',', ' ').split()
            try:
                links_from_text = Link._byID36(id36s, data=True)
            except NotFound:
                links_from_text = {}

            bad_links = [id36 for id36 in id36s if id36 not in links_from_text]
            links.extend(links_from_text.values())

        content = PromoteReport(links, link_text, owner_name, bad_links, start,
                                end, group_by_date=grouping == "day")
        if c.render_style == 'csv':
            return content.as_csv()
        else:
            return PromotePage(title=_("sponsored link report"),
                               content=content).render()

    @validate(
        VSponsorAdmin(),
        start=VDate('startdate'),
        end=VDate('enddate'),
        sr_name=nop('sr_name'),
        collection_name=nop('collection_name'),
    )
    def GET_promote_inventory(self, start, end, sr_name, collection_name):
        if not start or not end:
            start = promote.promo_datetime_now(offset=1).date()
            end = promote.promo_datetime_now(offset=8).date()
            c.errors.remove((errors.BAD_DATE, 'startdate'))
            c.errors.remove((errors.BAD_DATE, 'enddate'))

        target = Target(Frontpage.name)
        if sr_name:
            try:
                sr = Subreddit._by_name(sr_name)
                target = Target(sr.name)
            except NotFound:
                c.errors.add(errors.SUBREDDIT_NOEXIST, field='sr_name')
        elif collection_name:
            collection = Collection.by_name(collection_name)
            if not collection:
                c.errors.add(errors.COLLECTION_NOEXIST, field='collection_name')
            else:
                target = Target(collection)

        content = PromoteInventory(start, end, target)

        if c.render_style == 'csv':
            return content.as_csv()
        else:
            return PromotePage(title=_("sponsored link inventory"),
                               content=content).render()

    @validate(
        VSponsorAdmin(),
        id_user=VByName('name', thing_cls=Account),
        email=ValidEmail("email"),
    )
    def GET_lookup_user(self, id_user, email):
        email_users = AccountsByCanonicalEmail.get_accounts(email)
        content = SponsorLookupUser(
            id_user=id_user, email=email, email_users=email_users)
        return PromotePage(title="look up user", content=content).render()


class PromoteListingController(ListingController):
    where = 'promoted'
    render_cls = PromotePage
    titles = {
        'future_promos': N_('unapproved promoted links'),
        'pending_promos': N_('accepted promoted links'),
        'unpaid_promos': N_('unpaid promoted links'),
        'rejected_promos': N_('rejected promoted links'),
        'live_promos': N_('live promoted links'),
        'edited_live_promos': N_('edited live promoted links'),
        'all': N_('all promoted links'),
    }
    base_path = '/promoted'

    default_filters = [
        NamedButton('all_promos', dest='',
                    use_params=False,
                    aliases=['/sponsor']),
        NamedButton('future_promos',
                    use_params=False),
        NamedButton('unpaid_promos',
                    use_params=False),
        NamedButton('rejected_promos',
                    use_params=False),
        NamedButton('pending_promos',
                    use_params=False),
        NamedButton('live_promos',
                    use_params=False),
        NamedButton('edited_live_promos',
                    use_params=False),
    ]

    def title(self):
        return _(self.titles[self.sort])

    @property
    def title_text(self):
        return _('promoted by you')

    @property
    def menus(self):
        filters = [
            NamedButton('all_promos', dest='',
                        use_params=False,
                        aliases=['/sponsor']),
            NamedButton('future_promos',
                        use_params=False),
            NamedButton('unpaid_promos',
                        use_params=False),
            NamedButton('rejected_promos',
                        use_params=False),
            NamedButton('pending_promos',
                        use_params=False),
            NamedButton('live_promos',
                        use_params=False),
        ]
        menus = [NavMenu(filters, base_path=self.base_path, title='show',
                         type='lightdrop')]
        return menus

    def builder_wrapper(self, thing):
        builder_wrapper = default_thing_wrapper()
        w = builder_wrapper(thing)
        w.hide_after_seen = self.sort == "future_promos"

        return w

    def keep_fn(self):
        def keep(item):
            if self.sort == "future_promos":
                # this sort is used to review links that need to be approved
                # skip links that don't have any paid campaigns
                campaigns = list(PromoCampaign._by_link(item._id))
                if not any(promote.authed_or_not_needed(camp)
                           for camp in campaigns):
                    return False

            if item.promoted and not item._deleted:
                return True
            else:
                return False
        return keep

    def query(self):
        if self.sort == "future_promos":
            return queries.get_unapproved_links(c.user._id)
        elif self.sort == "pending_promos":
            return queries.get_accepted_links(c.user._id)
        elif self.sort == "unpaid_promos":
            return queries.get_unpaid_links(c.user._id)
        elif self.sort == "rejected_promos":
            return queries.get_rejected_links(c.user._id)
        elif self.sort == "live_promos":
            return queries.get_live_links(c.user._id)
        elif self.sort == "edited_live_promos":
            return queries.get_edited_live_links(c.user._id)
        elif self.sort == "all":
            return queries.get_promoted_links(c.user._id)

    @validate(VSponsor())
    def GET_listing(self, sort="all", **env):
        self.sort = sort
        return ListingController.GET_listing(self, **env)


class SponsorListingController(PromoteListingController):
    titles = dict(PromoteListingController.titles.items() + {
        'underdelivered': N_('underdelivered promoted links'),
        'reported': N_('reported promoted links'),
        'house': N_('house promoted links'),
        'fraud': N_('fraud suspected promoted links'),
    }.items())
    base_path = '/sponsor/promoted'

    @property
    def title_text(self):
        return _('promos on reddit')

    @property
    def menus(self):
        managed_menu = NavMenu([
            QueryButton("exclude managed", dest=None,
                        query_param='include_managed'),
            QueryButton("include managed", dest="yes",
                        query_param='include_managed'),
        ], base_path=request.path, type='lightdrop')

        if self.sort in {'underdelivered', 'reported', 'house', 'fraud'}:
            menus = []

            if self.sort == 'fraud':
                fraud_menu = NavMenu([
                    QueryButton("exclude unpaid", dest=None,
                                query_param='exclude_unpaid'),
                    QueryButton("include unpaid", dest="no",
                                query_param='exclude_unpaid'),
                ], base_path=request.path, type='lightdrop')
                menus.append(fraud_menu)
            if self.sort in ('house', 'fraud'):
                menus.append(managed_menu)
        else:
            menus = super(SponsorListingController, self).menus
            menus.append(managed_menu)

        if self.sort == 'live_promos':
            srnames = promote.all_live_promo_srnames()
            buttons = [NavButton('all', '', use_params=True)]
            try:
                srnames.remove(Frontpage.name)
                frontbutton = NavButton('FRONTPAGE', Frontpage.name,
                                        use_params=True,
                                        aliases=['/promoted/live_promos/%s' %
                                                 urllib.quote(Frontpage.name)])
                buttons.append(frontbutton)
            except KeyError:
                pass

            srnames = sorted(srnames, key=lambda name: name.lower())
            buttons.extend(
                NavButton(name, name, use_params=True) for name in srnames)
            base_path = self.base_path + '/live_promos'
            menus.append(NavMenu(buttons, base_path=base_path,
                                 title='subreddit', type='lightdrop'))
        return menus

    @classmethod
    @memoize('live_by_subreddit', time=300)
    def _live_by_subreddit(cls, sr_names):
        promotuples = promote.get_live_promotions(sr_names)
        return [pt.link for pt in promotuples]

    def live_by_subreddit(cls, sr):
        return cls._live_by_subreddit([sr.name])

    @classmethod
    @memoize('house_link_names', time=60)
    def get_house_link_names(cls):
        now = promote.promo_datetime_now()
        campaign_ids = PromotionWeights.get_campaign_ids(now)
        q = PromoCampaign._query(PromoCampaign.c._id.in_(campaign_ids),
                                 PromoCampaign.c.priority_name == 'house',
                                 data=True)
        link_names = {Link._fullname_from_id36(to36(camp.link_id))
                      for camp in q}
        return sorted(link_names, reverse=True)

    def keep_fn(self):
        base_keep_fn = PromoteListingController.keep_fn(self)

        if self.exclude_unpaid:
            exclude = set(queries.get_all_unpaid_links())
        else:
            exclude = set()

        def keep(item):
            if not self.include_managed and item.managed_promo:
                return False

            if self.exclude_unpaid and item._fullname in exclude:
                return False

            return base_keep_fn(item)
        return keep

    def query(self):
        if self.sort == "future_promos":
            return queries.get_all_unapproved_links()
        elif self.sort == "pending_promos":
            return queries.get_all_accepted_links()
        elif self.sort == "unpaid_promos":
            return queries.get_all_unpaid_links()
        elif self.sort == "rejected_promos":
            return queries.get_all_rejected_links()
        elif self.sort == "live_promos" and self.sr:
            return self.live_by_subreddit(self.sr)
        elif self.sort == 'live_promos':
            return queries.get_all_live_links()
        elif self.sort == 'edited_live_promos':
            return queries.get_all_edited_live_links()
        elif self.sort == 'underdelivered':
            q = queries.get_underdelivered_campaigns()
            campaigns = PromoCampaign._by_fullname(list(q), data=True,
                                                   return_dict=False)
            link_ids = [camp.link_id for camp in campaigns]
            return [Link._fullname_from_id36(to36(id)) for id in link_ids]
        elif self.sort == 'reported':
            return queries.get_reported_links(Subreddit.get_promote_srid())
        elif self.sort == 'fraud':
            return queries.get_payment_flagged_links()
        elif self.sort == 'house':
            return self.get_house_link_names()
        elif self.sort == 'all':
            return queries.get_all_promoted_links()

    def listing(self):
        """For sponsors, update wrapped links to include their campaigns."""
        pane = super(self.__class__, self).listing()

        if c.user_is_sponsor:
            link_ids = {item._id for item in pane.things}
            campaigns = PromoCampaign._by_link(link_ids)
            campaigns_by_link = defaultdict(list)
            for camp in campaigns:
                campaigns_by_link[camp.link_id].append(camp)

            for item in pane.things:
                campaigns = campaigns_by_link[item._id]
                item.campaigns = RenderableCampaign.from_campaigns(
                    item, campaigns, full_details=False)
                item.cachable = False
                item.show_campaign_summary = True
        return pane

    @validate(
        VSponsorAdmin(),
        srname=nop('sr'),
        include_managed=VBoolean("include_managed"),
        exclude_unpaid=VBoolean("exclude_unpaid"),
    )
    def GET_listing(self, srname=None, include_managed=False,
                    exclude_unpaid=None, sort="all", **kw):
        self.sort = sort
        self.sr = None
        self.include_managed = include_managed

        if "exclude_unpaid" not in request.GET:
            self.exclude_unpaid = self.sort == "fraud"
        else:
            self.exclude_unpaid = exclude_unpaid

        if srname:
            try:
                self.sr = Subreddit._by_name(srname)
            except NotFound:
                pass
        return ListingController.GET_listing(self, **kw)


def allowed_location_and_target(location, target):
    if c.user_is_sponsor or feature.is_enabled('ads_auction'):
        return True

    # regular users can only use locations when targeting frontpage
    is_location = location and location.country
    is_frontpage = (not target.is_collection and
                    target.subreddit_name == Frontpage.name)
    return not is_location or is_frontpage


class PromoteApiController(ApiController):
    @json_validate(sr=VSubmitSR('sr', promotion=True),
                   collection=VCollection('collection'),
                   location=VLocation(),
                   start=VDate('startdate'),
                   end=VDate('enddate'),
                   platform=VOneOf('platform', ('mobile', 'desktop', 'all'),
                                   default='all'))
    def GET_check_inventory(self, responder, sr, collection, location, start,
                            end, platform):
        if collection:
            target = Target(collection)
            sr = None
        else:
            sr = sr or Frontpage
            target = Target(sr.name)

        if not allowed_location_and_target(location, target):
            return abort(403, 'forbidden')

        available = inventory.get_available_pageviews(
                        target, start, end, location=location, platform=platform,
                        datestr=True)

        return {'inventory': available}

    @validatedForm(VSponsorAdmin(),
                   VModhash(),
                   link=VLink("link_id36"),
                   campaign=VPromoCampaign("campaign_id36"))
    def POST_freebie(self, form, jquery, link, campaign):
        if not link or not campaign or link._id != campaign.link_id:
            return abort(404, 'not found')

        if campaign_has_oversold_error(form, campaign):
            form.set_text(".freebie", _("target oversold, can't freebie"))
            return

        if promote.is_promo(link) and campaign:
            promote.free_campaign(link, campaign, c.user)
            form.redirect(promote.promo_edit_url(link))

    @validatedForm(VSponsorAdmin(),
                   VModhash(),
                   link=VByName("link"),
                   note=nop("note"))
    def POST_promote_note(self, form, jquery, link, note):
        if promote.is_promo(link):
            text = PromotionLog.add(link, note)
            form.find(".notes").children(":last").after(
                format_html("<p>%s</p>", text))

    @validatedForm(
        VSponsorAdmin(),
        VModhash(),
        thing = VByName("thing_id"),
        is_fraud=VBoolean("fraud"),
    )
    def POST_review_fraud(self, form, jquery, thing, is_fraud):
        if not thing or not getattr(thing, "promoted", False):
            return

        promote.review_fraud(thing, is_fraud)

        button = jquery(".id-%s .fraud-button" % thing._fullname)
        button.text(_("fraud" if is_fraud else "not fraud"))
        form.parents('.link').fadeOut()

    @noresponse(VSponsorAdmin(),
                VModhash(),
                thing=VByName('id'))
    def POST_promote(self, thing):
        if promote.is_promo(thing):
            promote.accept_promotion(thing)

    @noresponse(VSponsorAdmin(),
                VModhash(),
                thing=VByName('id'),
                reason=nop("reason"))
    def POST_unpromote(self, thing, reason):
        if promote.is_promo(thing):
            promote.reject_promotion(thing, reason=reason)

    @validatedForm(VSponsorAdmin(),
                   VModhash(),
                   link=VLink('link'),
                   campaign=VPromoCampaign('campaign'))
    def POST_refund_campaign(self, form, jquery, link, campaign):
        if not link or not campaign or link._id != campaign.link_id:
            return abort(404, 'not found')

        # If created before switch to auction, use old billing method
        if hasattr(campaign, 'cpm'):
            billable_impressions = promote.get_billable_impressions(campaign)
            billable_amount = promote.get_billable_amount(campaign,
                billable_impressions)
            refund_amount = promote.get_refund_amount(campaign, billable_amount)
        # Otherwise, use adserver_spent_pennies
        else:
            billable_amount = campaign.total_budget_pennies / 100.
            refund_amount = (billable_amount -
                (campaign.adserver_spent_pennies / 100.))
            billable_impressions = None

        if refund_amount <= 0:
            form.set_text('.status', _('refund not needed'))
            return

        if promote.refund_campaign(link, campaign, refund_amount,
                billable_amount, billable_impressions):
            form.set_text('.status', _('refund succeeded'))
        else:
            form.set_text('.status', _('refund failed'))

    @validatedForm(
        VSponsor('link_id36'),
        VModhash(),
        VRatelimit(rate_user=True,
                   rate_ip=True,
                   prefix='create_promo_'),
        VShamedDomain('url'),
        username=VLength('username', 100, empty_error=None),
        title=VTitle('title'),
        url=VUrl('url', allow_self=False),
        selftext=VMarkdownLength('text', max_length=40000),
        kind=VOneOf('kind', ['link', 'self']),
        disable_comments=VBoolean("disable_comments"),
        sendreplies=VBoolean("sendreplies"),
        media_url=VUrl("media_url", allow_self=False,
                       valid_schemes=('http', 'https')),
        gifts_embed_url=VUrl("gifts_embed_url", allow_self=False,
                             valid_schemes=('http', 'https')),
        media_url_type=VOneOf("media_url_type", ("redditgifts", "scrape")),
        media_autoplay=VBoolean("media_autoplay"),
        media_override=VBoolean("media-override"),
        domain_override=VLength("domain", 100),
        third_party_tracking=VUrl("third_party_tracking"),
        third_party_tracking_2=VUrl("third_party_tracking_2"),
        is_managed=VBoolean("is_managed"),
    )
    def POST_create_promo(self, form, jquery, username, title, url,
                          selftext, kind, disable_comments, sendreplies,
                          media_url, media_autoplay, media_override,
                          iframe_embed_url, media_url_type, domain_override,
                          third_party_tracking, third_party_tracking_2,
                          is_managed):

        images = _get_ads_images(c.user, data=True, meta=True)

        return self._edit_promo(
            form, jquery, username, title, url,
            selftext, kind, disable_comments, sendreplies,
            media_url, media_autoplay, media_override,
            iframe_embed_url, media_url_type, domain_override,
            third_party_tracking, third_party_tracking_2, is_managed,
            thumbnail=images.get("thumbnail", None),
            mobile=images.get("mobile", None),
        )

    @validatedForm(
        VSponsor('link_id36'),
        VModhash(),
        VRatelimit(rate_user=True,
                   rate_ip=True,
                   prefix='create_promo_'),
        VShamedDomain('url'),
        username=VLength('username', 100, empty_error=None),
        title=VTitle('title'),
        url=VUrl('url', allow_self=False),
        selftext=VMarkdownLength('text', max_length=40000),
        kind=VOneOf('kind', ['link', 'self']),
        disable_comments=VBoolean("disable_comments"),
        sendreplies=VBoolean("sendreplies"),
        media_url=VUrl("media_url", allow_self=False,
                       valid_schemes=('http', 'https')),
        gifts_embed_url=VUrl("gifts_embed_url", allow_self=False,
                             valid_schemes=('http', 'https')),
        media_url_type=VOneOf("media_url_type", ("redditgifts", "scrape")),
        media_autoplay=VBoolean("media_autoplay"),
        media_override=VBoolean("media-override"),
        domain_override=VLength("domain", 100),
        third_party_tracking=VUrl("third_party_tracking"),
        third_party_tracking_2=VUrl("third_party_tracking_2"),
        is_managed=VBoolean("is_managed"),
        l=VLink('link_id36'),
    )
    def POST_edit_promo(self, form, jquery, username, title, url,
                        selftext, kind, disable_comments, sendreplies,
                        media_url, media_autoplay, media_override,
                        iframe_embed_url, media_url_type, domain_override,
                        third_party_tracking, third_party_tracking_2,
                        is_managed, l):

        images = _get_ads_images(l, data=True, meta=True)

        return self._edit_promo(
            form, jquery, username, title, url,
            selftext, kind, disable_comments, sendreplies,
            media_url, media_autoplay, media_override,
            iframe_embed_url, media_url_type, domain_override,
            third_party_tracking, third_party_tracking_2, is_managed,
            l=l,
            thumbnail=images.get("thumbnail", None),
            mobile=images.get("mobile", None),
        )

    def _edit_promo(self, form, jquery, username, title, url,
                    selftext, kind, disable_comments, sendreplies,
                    media_url, media_autoplay, media_override,
                    iframe_embed_url, media_url_type, domain_override,
                    third_party_tracking, third_party_tracking_2,
                    is_managed, l=None, thumbnail=None, mobile=None):
        should_ratelimit = False
        is_self = (kind == "self")
        is_link = not is_self
        is_new_promoted = not l
        if not c.user_is_sponsor:
            should_ratelimit = True

        if not should_ratelimit:
            c.errors.remove((errors.RATELIMIT, 'ratelimit'))

        # check for user override
        if is_new_promoted and c.user_is_sponsor and username:
            try:
                user = Account._by_name(username)
            except NotFound:
                c.errors.add(errors.USER_DOESNT_EXIST, field="username")
                form.set_error(errors.USER_DOESNT_EXIST, "username")
                return

            if not user.email:
                c.errors.add(errors.NO_EMAIL_FOR_USER, field="username")
                form.set_error(errors.NO_EMAIL_FOR_USER, "username")
                return

            if not user.email_verified:
                c.errors.add(errors.NO_VERIFIED_EMAIL, field="username")
                form.set_error(errors.NO_VERIFIED_EMAIL, "username")
                return
        else:
            user = c.user

        # check for shame banned domains
        if form.has_errors("url", errors.DOMAIN_BANNED):
            g.stats.simple_event('spam.shame.link')
            return

        # demangle URL in canonical way
        if url:
            if isinstance(url, (unicode, str)):
                form.set_inputs(url=url)
            elif isinstance(url, tuple) or isinstance(url[0], Link):
                # there's already one or more links with this URL, but
                # we're allowing mutliple submissions, so we really just
                # want the URL
                url = url[0].url

            # Adzerk limits URLs length for creatives
            if len(url) > ADZERK_URL_MAX_LENGTH:
                c.errors.add(errors.TOO_LONG, field='url',
                    msg_params={'max_length': PROMO_URL_MAX_LENGTH})

        if is_link:
            if form.has_errors('url', errors.NO_URL, errors.BAD_URL,
                    errors.TOO_LONG):
                return

        # users can change the disable_comments on promoted links
        if ((is_new_promoted or not promote.is_promoted(l)) and
            (form.has_errors('title', errors.NO_TEXT, errors.TOO_LONG) or
             jquery.has_errors('ratelimit', errors.RATELIMIT))):
            return

        if is_self and form.has_errors('text', errors.TOO_LONG):
            return

        if is_new_promoted:
            # creating a new promoted link
            l = promote.new_promotion(
                is_self=is_self,
                title=title,
                content=(selftext if is_self else url),
                author=user,
                ip=request.ip,
            )

            if c.user_is_sponsor:
                l.managed_promo = is_managed
                l.domain_override = domain_override or None
                l.third_party_tracking = third_party_tracking or None
                l.third_party_tracking_2 = third_party_tracking_2 or None
            l._commit()

            _force_images(l, thumbnail=thumbnail, mobile=mobile)

            form.redirect(promote.promo_edit_url(l))

        elif not promote.is_promo(l):
            return

        changed = False
        if title and title != l.title:
            l.title = title
            changed = True

        if _force_images(l, thumbnail=thumbnail, mobile=mobile):
            changed = True

        # type changing
        if is_self != l.is_self:
            l.set_content(is_self, selftext if is_self else url)
            changed = True

        if is_link and url and url != l.url:
            l.url = url
            changed = True

        # only trips if changed by a non-sponsor
        if changed and not c.user_is_sponsor and promote.is_promoted(l):
            promote.edited_live_promotion(l)

        # selftext can be changed at any time
        if is_self:
            l.selftext = selftext

        # comment disabling and sendreplies is free to be changed any time.
        l.disable_comments = disable_comments
        l.sendreplies = sendreplies

        if c.user_is_sponsor:
            if (form.has_errors("media_url", errors.BAD_URL) or
                    form.has_errors("gifts_embed_url", errors.BAD_URL)):
                return

        scraper_embed = media_url_type == "scrape"
        media_url = media_url or None
        gifts_embed_url = gifts_embed_url or None

        if c.user_is_sponsor and scraper_embed and media_url != l.media_url:
            if media_url:
                scraped = media._scrape_media(
                    media_url, autoplay=media_autoplay,
                    save_thumbnail=False, use_cache=True)

                if scraped:
                    l.set_media_object(scraped.media_object)
                    l.set_secure_media_object(scraped.secure_media_object)
                    l.media_url = media_url
                    l.gifts_embed_url = None
                    l.media_autoplay = media_autoplay
                else:
                    c.errors.add(errors.SCRAPER_ERROR, field="media_url")
                    form.set_error(errors.SCRAPER_ERROR, "media_url")
                    return
            else:
                l.set_media_object(None)
                l.set_secure_media_object(None)
                l.media_url = None
                l.gifts_embed_url = None
                l.media_autoplay = False

        if (c.user_is_sponsor and not scraper_embed and
                gifts_embed_url != l.gifts_embed_url):
            if gifts_embed_url:
                parsed = UrlParser(gifts_embed_url)
                if not is_subdomain(parsed.hostname, "redditgifts.com"):
                    c.errors.add(errors.BAD_URL, field="gifts_embed_url")
                    form.set_error(errors.BAD_URL, "gifts_embed_url")
                    return

                sandbox = (
                    'allow-popups',
                    'allow-forms',
                    'allow-same-origin',
                    'allow-scripts',
                )
                iframe_attributes = {
                    'embed_url': websafe(iframe_embed_url),
                    'sandbox': ' '.join(sandbox),
                }
                iframe = """
                    <iframe class="redditgifts-embed"
                            src="%(embed_url)s"
                            width="710" height="500" scrolling="no"
                            frameborder="0" allowfullscreen
                            sandbox="%(sandbox)s">
                    </iframe>
                """ % iframe_attributes
                media_object = {
                    'oembed': {
                        'description': 'redditgifts embed',
                        'height': 500,
                        'html': iframe,
                        'provider_name': 'redditgifts',
                        'provider_url': 'http://www.redditgifts.com/',
                        'title': 'redditgifts secret santa 2014',
                        'type': 'rich',
                        'width': 710},
                        'type': 'redditgifts'
                }
                l.set_media_object(media_object)
                l.set_secure_media_object(media_object)
                l.media_url = None
                l.gifts_embed_url = gifts_embed_url
                l.media_autoplay = False
            else:
                l.set_media_object(None)
                l.set_secure_media_object(None)
                l.media_url = None
                l.gifts_embed_url = None
                l.media_autoplay = False

        if c.user_is_sponsor:
            l.media_override = media_override
            l.domain_override = domain_override or None
            l.third_party_tracking = third_party_tracking or None
            l.third_party_tracking_2 = third_party_tracking_2 or None
            l.managed_promo = is_managed

        l._commit()

        # ensure plugins are notified of the final edits to the link.
        # other methods also call this hook earlier in the process.
        # see: `promote.unapprove_promotion`
        if not is_new_promoted:
            hooks.get_hook('promote.edit_promotion').call(link=l)

        # clean up so the same images don't reappear if they create
        # another link
        _clear_ads_images(thing=c.user if is_new_promoted else l)

        form.redirect(promote.promo_edit_url(l))

    def _lowest_max_cpm_bid_dollars(self, total_budget_dollars, bid_dollars,
                                    start, end):
        """
        Calculate the lower between g.max_bid_pennies
        and maximum bid per day by budget
        """
        ndays = (to_date(end) - to_date(start)).days
        max_daily_bid = total_budget_dollars / ndays
        max_bid_dollars = g.max_bid_pennies / 100.

        return min(max_daily_bid, max_bid_dollars)

    @validatedForm(
        VSponsor('link_id36'),
        VModhash(),
        is_auction=VBoolean('is_auction'),
        start=VDate('startdate', required=False),
        end=VDate('enddate'),
        link=VLink('link_id36'),
        target=VPromoTarget(),
        campaign_id36=nop("campaign_id36"),
        frequency_cap=VFrequencyCap(("frequency_capped",
                                     "frequency_cap"),),
        priority=VPriority("priority"),
        location=VLocation(),
        platform=VOneOf("platform", ("mobile", "desktop", "all"), default="desktop"),
        mobile_os=VList("mobile_os", choices=["iOS", "Android"]),
        os_versions=VOneOf('os_versions', ('all', 'filter'), default='all'),
        ios_devices=VList('ios_device', choices=IOS_DEVICES),
        android_devices=VList('android_device', choices=ANDROID_DEVICES),
        ios_versions=VOSVersion('ios_version_range', 'ios'),
        android_versions=VOSVersion('android_version_range', 'android'),
        total_budget_dollars=VFloat('total_budget_dollars', coerce=False),
        cost_basis=VOneOf('cost_basis', ('cpc', 'cpm',), default=None),
        bid_dollars=VFloat('bid_dollars', coerce=True),
    )
    def POST_edit_campaign(self, form, jquery, is_auction, link, campaign_id36,
                           start, end, target, frequency_cap,
                           priority, location, platform, mobile_os,
                           os_versions, ios_devices, ios_versions,
                           android_devices, android_versions,
                           total_budget_dollars, cost_basis, bid_dollars):
        if not link:
            return

        if (form.has_errors('frequency_cap', errors.INVALID_FREQUENCY_CAP) or
                form.has_errors('frequency_cap', errors.FREQUENCY_CAP_TOO_LOW)):
            return

        if not target:
            # run form.has_errors to populate the errors in the response
            form.has_errors('sr', errors.SUBREDDIT_NOEXIST,
                            errors.SUBREDDIT_NOTALLOWED,
                            errors.SUBREDDIT_REQUIRED)
            form.has_errors('collection', errors.COLLECTION_NOEXIST)
            form.has_errors('targeting', errors.INVALID_TARGET)
            return

        if form.has_errors('location', errors.INVALID_LOCATION):
            return

        if not allowed_location_and_target(location, target):
            return abort(403, 'forbidden')

        if (form.has_errors('startdate', errors.BAD_DATE) or
                form.has_errors('enddate', errors.BAD_DATE)):
            return

        if not campaign_id36 and not start:
            c.errors.add(errors.BAD_DATE, field='startdate')
            form.set_error('startdate', errors.BAD_DATE)

        if (not feature.is_enabled('mobile_targeting') and
                platform != 'desktop'):
            return abort(403, 'forbidden')

        if link.over_18 and not target.over_18:
            c.errors.add(errors.INVALID_NSFW_TARGET, field='targeting')
            form.has_errors('targeting', errors.INVALID_NSFW_TARGET)
            return

        if not feature.is_enabled('cpc_pricing'):
            cost_basis = 'cpm'

        # Setup campaign details for existing campaigns
        campaign = None
        if campaign_id36:
            try:
                campaign = PromoCampaign._byID36(campaign_id36, data=True)
            except NotFound:
                pass

            if (not campaign
                    or (campaign._deleted or link._id != campaign.link_id)):
                return abort(404, 'not found')

            requires_reapproval = False
            is_live = promote.is_live_promo(link, campaign)
            is_complete = promote.is_complete_promo(link, campaign)

            if not c.user_is_sponsor:
                # If campaign is live, start_date and total_budget_dollars
                # must not be changed
                if is_live:
                    start = campaign.start_date
                    total_budget_dollars = campaign.total_budget_dollars

        # Configure priority, cost_basis, and bid_pennies
        if feature.is_enabled('ads_auction'):
            if c.user_is_sponsor:
                if is_auction:
                    priority = PROMOTE_PRIORITIES['auction']
                    cost_basis = PROMOTE_COST_BASIS[cost_basis]
                else:
                    cost_basis = PROMOTE_COST_BASIS.fixed_cpm
            else:
                # if non-sponsor, is_auction is not part of the POST request,
                # so must be set independently
                is_auction = True
                priority = PROMOTE_PRIORITIES['auction']
                cost_basis = PROMOTE_COST_BASIS[cost_basis]

                # Error if bid is outside acceptable range
                min_bid_dollars = g.min_bid_pennies / 100.
                max_bid_dollars = self._lowest_max_bid_dollars(
                    total_budget_dollars=total_budget_dollars,
                    bid_dollars=bid_dollars,
                    start=start,
                    end=end)

                if bid_dollars < min_bid_dollars or bid_dollars > max_bid_dollars:
                    c.errors.add(errors.BAD_BID, field='bid',
                        msg_params={'min': '%.2f' % round(min_bid_dollars, 2),
                                    'max': '%.2f' % round(max_bid_dollars, 2)}
                    )
                    form.has_errors('bid', errors.BAD_BID)
                    return

        else:
            cost_basis = PROMOTE_COST_BASIS.fixed_cpm

        if priority == PROMOTE_PRIORITIES['auction']:
            bid_pennies = bid_dollars * 100
        else:
            link_owner = Account._byID(link.author_id)
            bid_pennies = PromotionPrices.get_price(link_owner, target,
                location)

        if platform == 'desktop':
            mobile_os = None
        else:
            # check if platform includes mobile, but no mobile OS is selected
            if not mobile_os:
                c.errors.add(errors.BAD_PROMO_MOBILE_OS, field='mobile_os')
                form.set_error(errors.BAD_PROMO_MOBILE_OS, 'mobile_os')
                return
            elif os_versions == 'filter':
                # check if OS is selected, but OS devices are not
                if (('iOS' in mobile_os and not ios_devices) or
                        ('Android' in mobile_os and not android_devices)):
                    c.errors.add(errors.BAD_PROMO_MOBILE_DEVICE, field='os_versions')
                    form.set_error(errors.BAD_PROMO_MOBILE_DEVICE, 'os_versions')
                    return
                # check if OS versions are invalid
                if form.has_errors('os_version', errors.INVALID_OS_VERSION):
                    c.errors.add(errors.INVALID_OS_VERSION, field='os_version')
                    form.set_error(errors.INVALID_OS_VERSION, 'os_version')
                    return

        min_start, max_start, max_end = promote.get_date_limits(
            link, c.user_is_sponsor)

        if campaign:
            if feature.is_enabled('ads_auction'):
                # non-sponsors cannot update fixed CPM campaigns,
                # even if they haven't launched (due to auction)
                if not c.user_is_sponsor and not campaign.is_auction:
                    c.errors.add(errors.COST_BASIS_CANNOT_CHANGE,
                        field='cost_basis')
                    form.set_error(errors.COST_BASIS_CANNOT_CHANGE, 'cost_basis')
                    return

            if not c.user_is_sponsor:
                # If target is changed, require reapproval
                if campaign.target != target:
                    requires_reapproval = True

            if campaign.start_date.date() != start.date():
                # Can't edit the start date of campaigns that have served
                if campaign.has_served:
                    c.errors.add(errors.START_DATE_CANNOT_CHANGE, field='startdate')
                    form.has_errors('startdate', errors.START_DATE_CANNOT_CHANGE)
                    return

                if is_live or is_complete:
                    c.errors.add(errors.START_DATE_CANNOT_CHANGE, field='startdate')
                    form.has_errors('startdate', errors.START_DATE_CANNOT_CHANGE)
                    return

        elif start.date() < min_start:
            c.errors.add(errors.DATE_TOO_EARLY,
                         msg_params={'day': min_start.strftime("%m/%d/%Y")},
                         field='startdate')
            form.has_errors('startdate', errors.DATE_TOO_EARLY)
            return

        if start.date() > max_start:
            c.errors.add(errors.DATE_TOO_LATE,
                         msg_params={'day': max_start.strftime("%m/%d/%Y")},
                         field='startdate')
            form.has_errors('startdate', errors.DATE_TOO_LATE)
            return

        if end.date() > max_end:
            c.errors.add(errors.DATE_TOO_LATE,
                         msg_params={'day': max_end.strftime("%m/%d/%Y")},
                         field='enddate')
            form.has_errors('enddate', errors.DATE_TOO_LATE)
            return

        if end < start:
            c.errors.add(errors.BAD_DATE_RANGE, field='enddate')
            form.has_errors('enddate', errors.BAD_DATE_RANGE)
            return

        # Limit the number of PromoCampaigns a Link can have
        # Note that the front end should prevent the user from getting
        # this far
        existing_campaigns = list(PromoCampaign._by_link(link._id))
        if len(existing_campaigns) > g.MAX_CAMPAIGNS_PER_LINK:
            c.errors.add(errors.TOO_MANY_CAMPAIGNS,
                         msg_params={'count': g.MAX_CAMPAIGNS_PER_LINK},
                         field='title')
            form.has_errors('title', errors.TOO_MANY_CAMPAIGNS)
            return

        if not priority == PROMOTE_PRIORITIES['house']:
            # total_budget_dollars is submitted as a float;
            # convert it to pennies
            total_budget_pennies = int(total_budget_dollars * 100)
            if c.user_is_sponsor:
                min_total_budget_pennies = 0
                max_total_budget_pennies = 0
            else:
                min_total_budget_pennies = g.min_total_budget_pennies
                max_total_budget_pennies = g.max_total_budget_pennies

            if (total_budget_pennies is None or
                    total_budget_pennies < min_total_budget_pennies or
                    (max_total_budget_pennies and
                    total_budget_pennies > max_total_budget_pennies)):
                c.errors.add(errors.BAD_BUDGET, field='total_budget_dollars',
                             msg_params={'min': min_total_budget_pennies,
                                         'max': max_total_budget_pennies or
                                         g.max_total_budget_pennies})
                form.has_errors('total_budget_dollars', errors.BAD_BUDGET)
                return

            # you cannot edit the bid of a live ad unless it's a freebie
            if (campaign and
                    total_budget_pennies != campaign.total_budget_pennies and
                    promote.is_live_promo(link, campaign) and
                    not campaign.is_freebie()):
                c.errors.add(errors.BUDGET_LIVE, field='total_budget_dollars')
                form.has_errors('total_budget_dollars', errors.BUDGET_LIVE)
                return
        else:
            total_budget_pennies = 0

        # Check inventory
        campaign = campaign if campaign_id36 else None
        if not priority.inventory_override:
            oversold = has_oversold_error(form, campaign, start, end,
                                          total_budget_pennies, bid_pennies,
                                          target, location)
            if oversold:
                return

        # Always set frequency_cap_default for auction campaign if frequency_cap
        # is not set
        if not frequency_cap and is_auction:
            frequency_cap = g.frequency_cap_default

        dates = (start, end)

        campaign_dict = {
            'dates': dates,
            'target': target,
            'frequency_cap': frequency_cap,
            'priority': priority,
            'location': location,
            'total_budget_pennies': total_budget_pennies,
            'cost_basis': cost_basis,
            'bid_pennies': bid_pennies,
            'platform': platform,
            'mobile_os': mobile_os,
            'ios_devices': ios_devices,
            'ios_version_range': ios_versions,
            'android_devices': android_devices,
            'android_version_range': android_versions,
        }

        if campaign:
            if requires_reapproval and promote.is_accepted(link):
                campaign_dict['is_approved'] = False

            promote.edit_campaign(
                link,
                campaign,
                **campaign_dict
            )
        else:
            campaign = promote.new_campaign(
                link,
                **campaign_dict
            )
        rc = RenderableCampaign.from_campaigns(link, campaign)
        jquery.update_campaign(campaign._fullname, rc.render_html())

    @validatedForm(VSponsor('link_id36'),
                   VModhash(),
                   l=VLink('link_id36'),
                   campaign=VPromoCampaign("campaign_id36"))
    def POST_delete_campaign(self, form, jquery, l, campaign):
        if not campaign or not l or l._id != campaign.link_id:
            return abort(404, 'not found')

        promote.delete_campaign(l, campaign)

    @validatedForm(
        VSponsor('link_id36'),
        VModhash(),
        link=VLink('link_id36'),
        campaign=VPromoCampaign('campaign_id36'),
        should_pause=VBoolean('should_pause'),)
    def POST_toggle_pause_campaign(self, form, jquery, link, campaign,
            should_pause=False):
        if (not link or not campaign or link._id != campaign.link_id
                or not feature.is_enabled('pause_ads')):
            return abort(404, 'not found')

        if campaign.paused == should_pause:
            return

        promote.toggle_pause_campaign(link, campaign, should_pause)
        rc = RenderableCampaign.from_campaigns(link, campaign)
        jquery.update_campaign(campaign._fullname, rc.render_html())

    @validatedForm(VSponsorAdmin(),
                   VModhash(),
                   link=VLink('link_id36'),
                   campaign=VPromoCampaign("campaign_id36"))
    def POST_terminate_campaign(self, form, jquery, link, campaign):
        if not link or not campaign or link._id != campaign.link_id:
            return abort(404, 'not found')

        promote.terminate_campaign(link, campaign)
        rc = RenderableCampaign.from_campaigns(link, campaign)
        jquery.update_campaign(campaign._fullname, rc.render_html())

    @validatedForm(
        VVerifiedSponsor('link'),
        VModhash(),
        link=VByName("link"),
        campaign=VPromoCampaign("campaign"),
        customer_id=VInt("customer_id", min=0),
        pay_id=VInt("account", min=0),
        edit=VBoolean("edit"),
        address=ValidAddress(
            ["firstName", "lastName", "company", "address", "city", "state",
             "zip", "country", "phoneNumber"]
        ),
        creditcard=ValidCard(["cardNumber", "expirationDate", "cardCode"]),
    )
    def POST_update_pay(self, form, jquery, link, campaign, customer_id, pay_id,
                        edit, address, creditcard):

        def _handle_failed_payment(reason=None):
            promote.failed_payment_method(c.user, link)
            msg = reason or _("failed to authenticate card. sorry.")
            form.set_text(".status", msg)

        if not g.authorizenetapi:
            return

        if not link or not campaign or link._id != campaign.link_id:
            return abort(404, 'not found')

        # Check inventory
        if not campaign.is_auction:
            if campaign_has_oversold_error(form, campaign):
                return

        # check the campaign dates are still valid (user may have created
        # the campaign a few days ago)
        min_start, max_start, max_end = promote.get_date_limits(
            link, c.user_is_sponsor)

        if campaign.start_date.date() > max_start:
            msg = _("please change campaign start date to %(date)s or earlier")
            date = format_date(max_start, format="short", locale=c.locale)
            msg %= {'date': date}
            form.set_text(".status", msg)
            return

        if campaign.start_date.date() < min_start:
            msg = _("please change campaign start date to %(date)s or later")
            date = format_date(min_start, format="short", locale=c.locale)
            msg %= {'date': date}
            form.set_text(".status", msg)
            return

        new_payment = not pay_id

        address_modified = new_payment or edit
        if address_modified:
            address_fields = ["firstName", "lastName", "company", "address",
                              "city", "state", "zip", "country", "phoneNumber"]
            card_fields = ["cardNumber", "expirationDate", "cardCode"]

            if (form.has_errors(address_fields, errors.BAD_ADDRESS) or
                    form.has_errors(card_fields, errors.BAD_CARD)):
                return

            try:
                pay_id = add_or_update_payment_method(
                    c.user, address, creditcard, pay_id)

                if pay_id:
                    promote.new_payment_method(user=c.user,
                                               ip=request.ip,
                                               address=address,
                                               link=link)

            except AuthorizeNetException:
                _handle_failed_payment()
                return

        if pay_id:
            success, reason = promote.auth_campaign(link, campaign, c.user,
                                                    pay_id)

            if success:
                hooks.get_hook("promote.campaign_paid").call(link=link, campaign=campaign)
                if not address and g.authorizenetapi:
                    profiles = get_or_create_customer_profile(c.user).paymentProfiles
                    profile = {p.customerPaymentProfileId: p for p in profiles}[pay_id]

                    address = profile.billTo

                promote.successful_payment(link, campaign, request.ip, address)

                jquery.payment_redirect(promote.promo_edit_url(link),
                        new_payment, campaign.total_budget_pennies)
                return
            else:
                _handle_failed_payment(reason)

        else:
            _handle_failed_payment()

    @json_validate(
        VSponsor("link"),
        VModhash(),
        link=VLink("link"),
        kind=VOneOf("kind", ["thumbnail", "mobile"]),
        filepath=nop("filepath"),
        ajax=VBoolean("ajax", default=True)
    )
    def POST_ad_s3_params(self, responder, link, kind, filepath, ajax):
        filename, ext = os.path.splitext(filepath)
        mime_type, encoding = mimetypes.guess_type(filepath)

        if not mime_type or mime_type not in ALLOWED_IMAGE_TYPES:
            request.environ["extra_error_data"] = {
                "message": _("image must be a jpg or png"),
            }
            abort(403)

        keyspace = _get_ads_keyspace(link if link else c.user)
        key = os.path.join(keyspace, kind)
        redirect = None

        if not ajax:
            now = datetime.now().replace(tzinfo=g.tz)
            signature = _get_callback_hmac(
                username=c.user.name,
                key=key,
                expires=now,
            )
            path = ("/api/ad_s3_callback?hmac=%s&ts=%s" %
                (signature, _format_expires(now)))
            redirect = add_sr(path, sr_path=False)

        return s3_helpers.get_post_args(
            bucket=g.s3_client_uploads_bucket,
            key=key,
            success_action_redirect=redirect,
            success_action_status="201",
            content_type=mime_type,
            meta={
                "x-amz-meta-ext": ext,
            },
        )

    @validate(
        VSponsor(),
        expires=VDate("ts", format=EXPIRES_DATE_FORMAT),
        signature=VPrintable("hmac", 255),
        callback=nop("callback"),
        key=nop("key"),
    )
    def GET_ad_s3_callback(self, expires, signature, callback, key):
        now = datetime.now(tz=g.tz)
        if (expires + timedelta(minutes=10) < now):
            self.abort404()

        expected_mac = _get_callback_hmac(
            username=c.user.name,
            key=key,
            expires=expires,
        )

        if not constant_time_compare(signature, expected_mac):
            self.abort404()

        template = "<script>parent.__s3_callbacks__[%(callback)s](%(data)s);</script>"
        image = _key_to_dict(
            s3_helpers.get_key(g.s3_client_uploads_bucket, key))
        response = {
            "callback": scriptsafe_dumps(callback),
            "data": scriptsafe_dumps(image),
        }

        return format_html(template, response)
