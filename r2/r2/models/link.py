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

from pycassa.system_manager import ASCII_TYPE, UTF8_TYPE
from pycassa.types import LongType

from r2.config import feature
from r2.lib.db.thing import (
    Thing, Relation, NotFound, MultiRelation, CreationError)
from r2.lib.db.operators import desc
from r2.lib.errors import RedditError
from r2.lib.tracking import (
    get_site,
)
from r2.lib.utils import (
    base_url,
    domain,
    epoch_timestamp,
    feature_utils,
    strip_www,
    timesince,
    title_to_url,
    tup,
    UrlParser,
)
from account import (
    Account,
    BlockedSubredditsByAccount,
    DeletedUser,
    SubredditParticipationByAccount,
)
from subreddit import (
    DefaultSR,
    DomainSR,
    FakeSubreddit,
    Subreddit,
    SubredditsActiveForFrontPage,
)
from printable import Printable
from r2.config import extensions
from r2.lib.memoize import memoize
from r2.lib.wrapped import Wrapped
from r2.lib.filters import _force_utf8, _force_unicode
from r2.lib import hooks, utils
from mako.filters import url_escape
from r2.lib.strings import strings, Score
from r2.lib.db import tdb_cassandra, sorts
from r2.lib.db.tdb_cassandra import view_of
from r2.lib.utils import sanitize_url
from r2.models.gold import (
    GildedCommentsByAccount,
    GildedLinksByAccount,
    make_gold_message,
)
from r2.models.modaction import ModAction
from r2.models.subreddit import MultiReddit
from r2.models.trylater import TryLater
from r2.models.query_cache import CachedQueryMutator
from r2.models.promo import PROMOTE_STATUS
from r2.models.vote import Vote

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _
from datetime import datetime, timedelta
from hashlib import md5
import simplejson as json

import random, re
import pycassa
from collections import defaultdict
from itertools import cycle
from pycassa.cassandra.ttypes import NotFoundException
from pycassa.system_manager import (
    ASCII_TYPE,
    DOUBLE_TYPE,
)
import pytz

NOTIFICATION_EMAIL_DELAY = timedelta(hours=1)

class LinkExists(Exception): pass


class Link(Thing, Printable):
    _cache = g.thingcache
    _data_int_props = Thing._data_int_props + (
        'num_comments', 'reported', 'gildings')
    _defaults = dict(is_self=False,
                     suggested_sort=None,
                     over_18=False,
                     over_18_override=False,
                     reported=0, num_comments=0,
                     moderator_banned=False,
                     banned_before_moderator=False,
                     media_object=None,
                     secure_media_object=None,
                     preview_object=None,
                     media_url=None,
                     gifts_embed_url=None,
                     media_autoplay=False,
                     domain_override=None,
                     third_party_tracking=None,
                     third_party_tracking_2=None,
                     promoted=None,
                     payment_flagged_reason="",
                     fraud=None,
                     managed_promo=False,
                     pending=False,
                     disable_comments=False,
                     locked=False,
                     selftext='',
                     sendreplies=True,
                     ip='0.0.0.0',
                     flair_text=None,
                     flair_css_class=None,
                     contest_mode=False,
                     sticky_comment_id=None,
                     ignore_reports=False,
                     gildings=0,
                     mobile_ad_url="",
                     admin_takedown=False,
                     removed_link_child=None,
                     precomputed_sorts=None,
                     )
    _essentials = ('sr_id', 'author_id')
    _nsfw = re.compile(r"\bnsf[wl]\b", re.I)

    SELFTEXT_MAX_LENGTH = 40000

    is_votable = True

    @classmethod
    def _cache_prefix(cls):
        return "link:"

    def __init__(self, *a, **kw):
        Thing.__init__(self, *a, **kw)

    @property
    def affects_karma_type(self):
        if self.is_self:
            return "self"

        return "link"

    @property
    def body(self):
        if self.is_self:
            return self.selftext
        else:
            raise AttributeError

    @property
    def has_thumbnail(self):
        return self._t.get('has_thumbnail', hasattr(self, 'thumbnail_url'))

    @property
    def is_nsfw(self):
        return self.over_18

    @property
    def is_embeddable(self):
        return not self.is_nsfw and self.subreddit_slow.is_embeddable

    @classmethod
    def _by_url(cls, url, sr):
        if isinstance(sr, FakeSubreddit):
            sr = None

        link_ids = LinksByUrlAndSubreddit.get_link_ids(url, sr)
        links = Link._byID(link_ids, data=True, return_dict=False)
        links = [l for l in links if not l._deleted]

        if not links:
            raise NotFound('Link "%s"' % url)

        return links

    def already_submitted_link(self, url, title):
        permalink = self.make_permalink_slow()
        p = UrlParser(permalink)
        p.update_query(already_submitted="true", submit_url=url,
                       submit_title=title)
        return p.unparse()

    @classmethod
    def resubmit_link(cls, url, title):
        p = UrlParser("/submit")
        p.update_query(resubmit="true", url=url, title=title)
        return p.unparse()

    @classmethod
    def _submit(cls, is_self, title, content, author, sr, ip,
                sendreplies=True):
        from r2.lib.voting import cast_vote
        from r2.models import admintools
        from r2.models.comment_tree import CommentTree

        if is_self:
            url = "self"
            selftext = content
        else:
            url = content
            selftext = cls._defaults["selftext"]

        over_18 = False
        if cls._nsfw.search(title):
            over_18 = True

        # determine whether the post should go straight into spam
        spam = author._spam
        if is_self:
            spam_filter_level = sr.spam_selfposts
        else:
            spam_filter_level = sr.spam_links
        if spam_filter_level == "all" and not sr.is_special(author):
            spam = True

        l = cls(
            _ups=1,
            title=title,
            url=url,
            selftext=selftext,
            _spam=spam,
            author_id=author._id,
            sendreplies=sendreplies,
            sr_id=sr._id,
            lang=sr.lang,
            ip=ip,
            is_self=is_self,
            over_18=over_18,
        )

        l._commit()
        # Note: this does not provide atomicity, so for self posts when
        # a request dies after the previous line but before the next line
        # you will have a link whose URL is literally 'self'
        if is_self:
            l.url = l.make_permalink_slow()
            l._commit()
        else:
            LinksByUrlAndSubreddit.add_link(l)

        LinksByAccount.add_link(author, l)
        SubredditParticipationByAccount.mark_participated(author, sr)
        SubredditsActiveForFrontPage.mark_new_post(sr)
        author.last_submit_time = int(epoch_timestamp(datetime.now(g.tz)))
        author._commit()

        # if the link is coming in removed, we still need to run it through
        # admintools.spam() to set data properly and update queries
        if l._spam:
            if author._spam:
                g.stats.simple_event('spam.autoremove.link')
                reason = "banned user"
            elif spam_filter_level == "all":
                reason = "subreddit setting"

            admintools.spam(l, banner=reason)

        hooks.get_hook('link.new').call(link=l)

        CommentTree.on_new_link(l)

        cast_vote(author, l, Vote.DIRECTIONS.up)

        return l

    def set_content(self, is_self, content):
        if not self.promoted:
            raise ValueError("set_content is only supported for promoted links")

        was_self = self.is_self
        self.is_self = is_self

        if is_self:
            if not was_self:
                LinksByUrlAndSubreddit.remove_link(self)

            self.url = self.make_permalink_slow()
            self.selftext = content
        else:
            if not was_self:
                LinksByUrlAndSubreddit.remove_link(self)

            self.url = content
            self.selftext = self._defaults.get("selftext", "")
            LinksByUrlAndSubreddit.add_link(self)

        self._commit()

    def _save(self, user, category=None):
        LinkSavesByAccount._save(user, self, category)

    def _unsave(self, user):
        LinkSavesByAccount._unsave(user, self)

    def _hide(self, user):
        LinkHidesByAccount._hide(user, self)

    def _unhide(self, user):
        LinkHidesByAccount._unhide(user, self)

    def _commit(self):
        # If we've updated the (denormalized) preview object, we also need to
        # update the metadata that keeps track of the denormalizations.
        if 'preview_object' in self._dirties:
            (old_val, val) = self._dirties['preview_object']
            if old_val:
                LinksByImage.remove_link(old_val['uid'], self)
            if val:
                LinksByImage.add_link(val['uid'], self)
        Thing._commit(self)

    def link_domain(self):
        if self.is_self:
            return 'self'
        else:
            return domain(self.url)

    @property
    def num_comments(self):
        # Paper over obviously broken comment counts (those that are negative).
        return max(self.__getattr__('num_comments'), 0)

    def keep_item(self, wrapped):
        user = c.user if c.user_is_loggedin else None
        if not c.user_is_admin and self._deleted:
            return False

        is_mod = wrapped.subreddit.is_moderator(user)

        if not (c.user_is_admin or (isinstance(c.site, DomainSR) and is_mod)):
            if self._spam and (not user or
                               (user and self.author_id != user._id)):
                return False

        if not (c.user_is_admin or is_mod) and wrapped.enemy:
            return False

        if user and not c.ignore_hide_rules:
            if wrapped.hidden:
                return False

            # determine if the post can be auto-hidden due to voting/score
            if self.author_id == user._id:
                # not if it's the user's own post
                allow_auto_hide = False
            elif wrapped.stickied and not wrapped.different_sr:
                # not if it's stickied and we're inside the subreddit
                allow_auto_hide = False
            else:
                allow_auto_hide = True

            if (allow_auto_hide and
                    ((user.pref_hide_ups and wrapped.likes == True) or
                     (user.pref_hide_downs and wrapped.likes == False) or
                     wrapped.score < user.pref_min_link_score)):
                return False

        # show NSFW to API and RSS users unless obey_over18=true
        is_api = c.render_style in extensions.API_TYPES
        is_rss = c.render_style in extensions.RSS_TYPES
        if (is_api or is_rss) and not c.obey_over18:
            return True

        is_nsfw = wrapped.over_18 or wrapped.subreddit.over_18
        return c.over18 or not is_nsfw

    cache_ignore = {
        'subreddit',
        'num_comments',
        'link_child',
        'fresh',
        'media_object',
        'secure_media_object',
    }.union(Printable.cache_ignore)

    @staticmethod
    def wrapped_cache_key(wrapped, style):
        s = Printable.wrapped_cache_key(wrapped, style)
        if wrapped.promoted is not None:
            s.extend([
                getattr(wrapped, "promote_status", -1),
                getattr(wrapped, "disable_comments", False),
                getattr(wrapped, "media_override", False),
                c.user_is_sponsor,
                wrapped.url,
                repr(wrapped.title),
            ])

        if style == "htmllite":
             s.extend([
                 request.GET.has_key('twocolumn'),
                 c.link_target,
            ])
        elif style == "xml":
            s.append(request.GET.has_key("nothumbs"))
        elif style == "compact":
            s.append(c.permalink_page)

        # add link flair to the key if the user and site have enabled it and it
        # exists
        if (c.user.pref_show_link_flair and
                c.site.link_flair_position and
                (wrapped.flair_text or wrapped.flair_css_class)):
            s.append(wrapped.flair_text)
            s.append(wrapped.flair_css_class)
            s.append(c.site.link_flair_position)

        if wrapped.locked:
            s.append('locked')

        return s

    def make_permalink(self, sr, force_domain=False):
        from r2.lib.template_helpers import get_domain
        p = "comments/%s/%s/" % (self._id36, title_to_url(self.title))
        # promoted links belong to a separate subreddit and shouldn't
        # include that in the path
        if self.promoted is not None:
            if force_domain:
                permalink_domain = get_domain(subreddit=False)
                res = "%s://%s/%s" % (g.default_scheme, permalink_domain, p)
            else:
                res = "/%s" % p
        elif not force_domain:
            res = "/r/%s/%s" % (sr.name, p)
        elif sr != c.site or force_domain:
            permalink_domain = get_domain(subreddit=False)
            res = "%s://%s/r/%s/%s" % (g.default_scheme, permalink_domain,
                                       sr.name, p)
        else:
            res = "/%s" % p

        # WARNING: If we ever decide to add any ?foo=bar&blah parameters
        # here, Comment.make_permalink will need to be updated or else
        # it will fail.

        return res

    def make_canonical_link(self, sr, subdomain='www'):
        domain = '%s.%s' % (subdomain, g.domain)
        path = 'comments/%s/%s/' % (self._id36, title_to_url(self.title))
        return '%s://%s/r/%s/%s' % (g.default_scheme, domain, sr.name, path)

    def make_permalink_slow(self, force_domain=False):
        return self.make_permalink(self.subreddit_slow,
                                   force_domain=force_domain)

    def markdown_link_slow(self):
        title = _force_unicode(self.title)
        title = title.replace("[", r"\[")
        title = title.replace("]", r"\]")
        return "[%s](%s)" % (title, self.make_permalink_slow())

    @classmethod
    def tracking_link(cls,
                      link,
                      wrapped_thing=None,
                      element_name=None,
                      context=None,
                      site_name=None):
        """Add utm query parameters to reddit.com links to track navigation.

        context => ?utm_medium (listing page, post listing on hybrid page)
        site_name => ?utm_name (subreddit that user is currently browsing)
        element_name => ?utm_content (what element leads to this link)
        """

        if (c.user_is_admin or
                not feature.is_enabled('utm_comment_links')):
            return link

        urlparser = UrlParser(link)
        if not urlparser.path:
            # `href="#some_anchor"`
            return link
        if urlparser.scheme == 'javascript':
            return link
        if not urlparser.is_reddit_url():
            return link

        query_params = {}

        query_params["utm_source"] = "reddit"

        if context is None:
            if (hasattr(wrapped_thing, 'context') and
                    wrapped_thing.context != cls.get_default_context()):
                context = wrapped_thing.context
            else:
                context = request.route_dict["controller"]
        if context:
            query_params["utm_medium"] = context

        if element_name:
            query_params["utm_content"] = element_name

        if site_name is None:
            site_name = get_site()
        if site_name:
            query_params["utm_name"] = site_name

        query_params = {k: v for (k, v) in query_params.iteritems() if (
                        v is not None)}

        if query_params:
            urlparser.update_query(**query_params)
            return urlparser.unparse()
        return link

    def _gild(self, user):
        now = datetime.now(g.tz)

        self._incr("gildings")
        self.subreddit_slow.add_gilding_seconds()

        GildedLinksByAccount.gild(user, self)

        from r2.lib.db import queries
        with CachedQueryMutator() as m:
            gilding = utils.Storage(thing=self, date=now)
            m.insert(queries.get_all_gilded_links(), [gilding])
            m.insert(queries.get_gilded_links(self.sr_id), [gilding])
            m.insert(queries.get_gilded_user_links(self.author_id),
                     [gilding])
            m.insert(queries.get_user_gildings(user), [gilding])

        hooks.get_hook('link.gild').call(link=self, gilder=user)

    @staticmethod
    def _should_expunge_selftext(link):
        if not link._spam:
            return False
        if not c.user_is_loggedin:
            return True
        if c.user_is_admin:
            return False
        if c.user == link.author:
            return False
        if link.can_ban:
            return False
        return True

    @classmethod
    def update_nofollow(cls, user, wrapped):
        user_is_loggedin = c.user_is_loggedin
        for item in wrapped:
            if user_is_loggedin and item.author_id == user._id:
                item.nofollow = False
            elif item._spam or item.author._spam:
                item.nofollow = True
            else:
                item.nofollow = False

        hooks.get_hook('link.update_nofollow').call(
            user=user,
            wrapped=wrapped,
        )

    @classmethod
    def add_props(cls, user, wrapped):
        from r2.lib.pages import make_link_child
        from r2.lib.count import incr_counts
        from r2.lib import media
        from r2.lib.utils import timeago
        from r2.lib.template_helpers import get_domain, unsafe, format_html
        from r2.models.report import Report
        from r2.lib.wrapped import CachedVariable

        # referencing c's getattr is cheap, but not as cheap when it
        # is in a loop that calls it 30 times on 25-200 things.
        user_is_admin = c.user_is_admin
        user_is_loggedin = c.user_is_loggedin
        pref_media = user.pref_media
        pref_media_preview = user.pref_media_preview
        site = c.site

        saved = hidden = visited = {}

        if user_is_admin:
            # Checking if a domain's banned isn't even cheap
            urls = [item.url for item in wrapped if hasattr(item, 'url')]
            # bans_for_domain_parts is just a generator; convert to a set for
            # easy use of 'intersection'
            from r2.models.admintools import bans_for_domain_parts
            banned_domains = {ban.domain
                              for ban in bans_for_domain_parts(urls)}

        if user_is_loggedin:
            gilded = [thing for thing in wrapped if thing.gildings > 0]
            try:
                user_gildings = GildedLinksByAccount.fast_query(user, gilded)
            except tdb_cassandra.TRANSIENT_EXCEPTIONS as e:
                g.log.warning("Cassandra gilding lookup failed: %r", e)
                user_gildings = {}

            try:
                saved = LinkSavesByAccount.fast_query(user, wrapped)
                hidden = LinkHidesByAccount.fast_query(user, wrapped)

                if user.gold and user.pref_store_visits:
                    visited = LinkVisitsByAccount.fast_query(user, wrapped)

            except tdb_cassandra.TRANSIENT_EXCEPTIONS as e:
                # saved or hidden or may have been done properly, so go ahead
                # with what we do have
                g.log.warning("Cassandra save/hide/visited lookup failed: %r", e)

        # determine which subreddits the user could assign link flair in
        if user_is_loggedin:
            srs = {item.subreddit for item in wrapped
                                  if item.subreddit.link_flair_position}
            mod_flair_srids = {sr._id for sr in srs
                               if (user_is_admin or
                                   sr.is_moderator_with_perms(c.user, 'flair'))}
            author_flair_srids = {sr._id for sr in srs
                                  if sr.link_flair_self_assign_enabled}

        if user_is_loggedin:
            srs = {item.subreddit for item in wrapped}
            is_moderator_srids = {sr._id for sr in srs if sr.is_moderator(user)}
        else:
            is_moderator_srids = set()

        # set the nofollow state where needed
        cls.update_nofollow(user, wrapped)

        for item in wrapped:
            show_media = False
            if not hasattr(item, "score_fmt"):
                item.score_fmt = Score.number_only
            if c.render_style in ('compact', extensions.api_type("compact")):
                item.score_fmt = Score.safepoints
            item.pref_compress = user.pref_compress
            if user.pref_compress:
                item.extra_css_class = "compressed"
                item.score_fmt = Score.safepoints
            elif pref_media == 'on' and not user.pref_compress:
                show_media = True
            elif pref_media == 'subreddit' and item.subreddit.show_media:
                show_media = True
            elif item.promoted and item.has_thumbnail:
                if user_is_loggedin and item.author_id == user._id:
                    show_media = True
                elif pref_media != 'off' and not user.pref_compress:
                    show_media = True

            show_media_preview = False
            if feature.is_enabled('autoexpand_media_previews'):
                if pref_media_preview == "on":
                    show_media_preview = True
                elif pref_media_preview == "subreddit" and item.subreddit.show_media_preview:
                    show_media_preview = True

            item.over_18 = item.over_18 or item.subreddit.over_18
            item.nsfw = item.over_18 and user.pref_label_nsfw

            item.quarantine = item.subreddit.quarantine

            item.is_author = (user == item.author)

            item.thumbnail_sprited = False

            if item.quarantine:
                item.thumbnail = ""
                item.preview_image = None
            # always show a promo author their own thumbnail
            elif item.promoted and (user_is_admin or item.is_author) and item.has_thumbnail:
                item.thumbnail = media.thumbnail_url(item)
                item.preview_image = getattr(item, 'preview_object', None)
            elif user.pref_no_profanity and item.over_18 and not c.site.over_18:
                if show_media:
                    item.thumbnail = "nsfw"
                    item.thumbnail_sprited = True
                else:
                    item.thumbnail = ""
                item.preview_image = None
            elif not show_media:
                item.thumbnail = ""
                item.preview_image = None
            elif (item._deleted or
                  item._spam and item._date < timeago("6 hours")):
                item.thumbnail = "default"
                item.thumbnail_sprited = True
                item.preview_image = None
            elif item.has_thumbnail:
                item.thumbnail = media.thumbnail_url(item)
                item.preview_image = getattr(item, 'preview_object', None)
            elif item.is_self:
                item.thumbnail = "self"
                item.thumbnail_sprited = True
                item.preview_image = getattr(item, 'preview_object', None)
            else:
                item.thumbnail = "default"
                item.thumbnail_sprited = True
                item.preview_image = getattr(item, 'preview_object', None)

            item.show_media_preview = show_media_preview

            item.score = max(0, item.score)

            if item.domain_override:
                item.domain = item.domain_override
            else:
                item.domain = (domain(item.url) if not item.is_self
                               else 'self.' + item.subreddit.name)

            if user_is_loggedin:
                item.user_gilded = (user, item) in user_gildings
                item.saved = (user, item) in saved
                item.hidden = (user, item) in hidden
                item.visited = (user, item) in visited

            else:
                item.user_gilded = False
                item.saved = item.hidden = item.visited = False

            if c.permalink_page or c.profilepage:
                item.gilded_message = make_gold_message(item, item.user_gilded)
            else:
                item.gilded_message = ''

            item.can_gild = (
                c.user_is_loggedin and
                # you can't gild your own submission
                not (item.author and
                     item.author._id == user._id) and
                # no point in showing the button for things you've already gilded
                not item.user_gilded and
                # ick, if the author deleted their account we shouldn't waste gold
                not item.author._deleted and
                # some subreddits can have gilding disabled
                item.subreddit.allow_gilding and
                not item._deleted
            )

            # set an attribute on the Wrapped item so that it will be
            # added to the render cache key
            if item.can_ban:
                item.ignore_reports_key = item.ignore_reports

            if c.user_is_loggedin and c.user.in_timeout:
                item.mod_reports, item.user_reports = [], []
            else:
                item.mod_reports, item.user_reports = Report.get_reports(item)

            item.num = None
            item.permalink = item.make_permalink(item.subreddit)
            if item.is_self:
                item.url = item.make_permalink(item.subreddit,
                                               force_domain=True)

            if g.shortdomain:
                item.shortlink = g.shortdomain + '/' + item._id36

            item.domain_str = None
            if c.user.pref_domain_details:
                urlparser = UrlParser(item.url)
                if (not item.is_self and urlparser.is_reddit_url() and
                        urlparser.is_web_safe_url()):
                    url_subreddit = urlparser.get_subreddit()
                    if (url_subreddit and
                            not isinstance(url_subreddit, DefaultSR)):
                        item.domain_str = ('{0}/r/{1}'
                                           .format(item.domain,
                                                   url_subreddit.name))
                elif isinstance(item.media_object, dict):
                    try:
                        author_url = item.media_object['oembed']['author_url']
                        if domain(author_url) == item.domain:
                            urlparser = UrlParser(author_url)
                            item.domain_str = strip_www(urlparser.hostname)
                            item.domain_str += urlparser.path
                    except KeyError:
                        pass

                    if not item.domain_str:
                        try:
                            author = item.media_object['oembed']['author_name']
                            author = _force_unicode(author)
                            item.domain_str = (_force_unicode('{0}: {1}')
                                               .format(item.domain, author))
                        except KeyError:
                            pass

            if not item.domain_str:
                item.domain_str = item.domain

            item.user_is_moderator = item.sr_id in is_moderator_srids

            # do we hide the score?
            if user_is_admin:
                item.hide_score = False
            elif user_is_loggedin and item.user_is_moderator:
                item.hide_score = False
            elif item.promoted and item.score <= 0:
                item.hide_score = True
            elif user == item.author:
                item.hide_score = False
            elif item._date > timeago("2 hours"):
                item.hide_score = True
            else:
                item.hide_score = False

            # is this link a member of a different (non-c.site) subreddit?
            item.different_sr = (isinstance(site, FakeSubreddit) or
                                 site.name != item.subreddit.name)

            item.stickied = item.is_stickied(item.subreddit)

            # we only want to style a sticky specially if we're inside the
            # subreddit that it's stickied in (not in places like front page)
            item.use_sticky_style = item.stickied and not item.different_sr

            item.subreddit_path = item.subreddit.path
            item.domain_path = "/domain/%s/" % item.domain
            if item.is_self:
                item.domain_path = item.subreddit_path

            # attach video or selftext as needed
            item.link_child, item.editable = make_link_child(item, show_media_preview)
            item.feature_media_previews = feature.is_enabled("media_previews")

            if item.is_self and not item.promoted:
                item.href_url = item.permalink
            else:
                item.href_url = item.url

            item.fresh = not any((item.likes != None,
                                  item.saved,
                                  item.visited,
                                  item.hidden,
                                  item._deleted,
                                  item._spam))

            # bits that we will render stubs (to make the cached
            # version more flexible)
            item.num_text = CachedVariable("num")
            item.commentcls = CachedVariable("commentcls")
            item.comment_label = CachedVariable("numcomments")
            item.lastedited = CachedVariable("lastedited")

            item.as_deleted = False
            if item.deleted and not c.user_is_admin:
                item.author = DeletedUser()
                item.as_deleted = True
                item.selftext = '[deleted]'

            item.archived = item.is_archived(item.subreddit)
            item.votable = not item.archived

            item.expunged = False
            if item.is_self:
                item.expunged = Link._should_expunge_selftext(item)

            item.editted = getattr(item, "editted", False)

            if user_is_loggedin:
                can_mod_flair = item.subreddit._id in mod_flair_srids
                can_author_flair = (item.is_author and
                                    item.subreddit._id in author_flair_srids)
                item.can_flair = can_mod_flair or can_author_flair
            else:
                item.can_flair = False

            taglinetext = ''
            if item.different_sr:
                author_text = format_html(" <span>%s</span>",
                                          _("by %(author)s to %(reddit)s"))
            else:
                author_text = format_html(" <span>%s</span>",
                                          _("by %(author)s"))
            if item.editted:
                if item.score_fmt in (Score.points, Score.safepoints):
                    taglinetext = format_html("<span>%s</span>",
                                              _("%(score)s submitted %(when)s "
                                                "%(lastedited)s"))
                    taglinetext = unsafe(taglinetext + author_text)
                elif item.different_sr:
                    taglinetext = _("submitted %(when)s %(lastedited)s "
                                    "by %(author)s to %(reddit)s")
                else:
                    taglinetext = _("submitted %(when)s %(lastedited)s "
                                    "by %(author)s")
            else:
                if item.score_fmt in (Score.points, Score.safepoints):
                    taglinetext = format_html("<span>%s</span>",
                                              _("%(score)s submitted %(when)s"))
                    taglinetext = unsafe(taglinetext + author_text)
                elif item.different_sr:
                    taglinetext = _("submitted %(when)s by %(author)s "
                                    "to %(reddit)s")
                else:
                    taglinetext = _("submitted %(when)s by %(author)s")
            item.taglinetext = taglinetext

            if item.is_author:
                item.should_incr_counts = False

            if user_is_admin:
                # Link notes
                url = getattr(item, 'url')
                # Pull just the relevant portions out of the url
                urlf = sanitize_url(_force_unicode(url))
                if urlf:
                    urlp = UrlParser(urlf)
                    hostname = urlp.hostname
                    if hostname:
                        parts = (hostname.encode("utf-8").rstrip(".").
                            split("."))
                        subparts = {".".join(parts[y:])
                                    for y in xrange(len(parts))}
                        if subparts.intersection(banned_domains):
                            item.link_notes.append('banned domain')

            # This is passed in promotedlink.html
            item.ads_auction_enabled = feature.is_enabled('ads_auction')

            if feature_utils.is_tracking_link_enabled(item):
                # Split cache for template rendered with tracking link
                item.use_tracking_link = True

        if user_is_loggedin:
            incr_counts(wrapped)


        # Run this last
        Printable.add_props(user, wrapped)

    @classmethod
    def get_default_context(cls):
        return request.route_dict["action_name"]

    @property
    def post_hint(self):
        """Returns a string that suggests the content of this link.

        As a hint, this is lossy and may be inaccurate in some cases.

        Currently one of:
            * self
            * video (a video file, like an mp4)
            * image (an image file, like a gif or png)
            * rich:video (a video embedded in HTML - like youtube or vimeo)
            * link (catch-all)
        """
        if self.is_self:
            return 'self'

        try:
            oembed_type = self.media_object['oembed']['type']
        except (KeyError, TypeError):
            oembed_type = None

        if oembed_type == 'photo':
            return 'image'

        if oembed_type == 'video':
            return 'rich:video'

        if oembed_type in {'link', 'rich'}:
            return 'link'

        p = UrlParser(self.url)
        if p.has_image_extension():
            return 'image'

        if p.path_extension().lower() in {'mp4', 'webm'}:
            return 'video'

        return 'link'

    @property
    def subreddit_slow(self):
        # The subreddit is often already on the wrapped link as .subreddit
        # If available, that should be used instead of calling this
        return Subreddit._byID(self.sr_id, stale=True)

    @property
    def author_slow(self):
        """Returns the link's author."""
        # The author is often already on the wrapped link as .author
        # If available, that should be used instead of calling this
        return Account._byID(self.author_id, data=True, return_dict=False)

    @property
    def responder_ids(self):
        """Returns an iterable of the OP and other official responders in a
        thread.

        Designed for Q&A-type threads (eg /r/iama).
        """
        return (self.author_id,)

    @property
    def archived_slow(self):
        sr = self.subreddit_slow
        return self.is_archived(sr)

    def is_archived(self, sr):
        return self._age >= sr.archive_age

    def can_view_promo(self, user):
        if self.promoted:
            # promos are visible only if the user is either the author
            # or the link is live/previously live.
            is_author = c.user_is_loggedin and user._id == self.author_id
            return (c.user_is_sponsor or
                    is_author or
                    self.promote_status >= PROMOTE_STATUS.promoted)

        # not a promo, therefore it is visible
        # this preserves the original behavior of `visible_promo()`
        return True

    def can_comment_slow(self, user):
        sr = self.subreddit_slow

        if self.is_archived(sr):
            return False

        if self.locked and not sr.can_distinguish(user):
            return False

        return sr.can_comment(user) and self.can_view_promo(user)

    def sort_if_suggested(self, sr=None):
        """Returns a sort, if the link or its subreddit has suggested one."""
        if self.suggested_sort == "blank":
            # A suggested sort of "blank" means explicitly empty: Do not obey
            # the subreddit's suggested sort, either.
            return None

        if self.suggested_sort:
            return self.suggested_sort

        sr = sr or self.subreddit_slow
        if sr.suggested_comment_sort:
            return sr.suggested_comment_sort

        return None

    def can_flair_slow(self, user):
        """Returns whether the specified user can flair this link"""
        site = self.subreddit_slow
        can_assign_own = (self.author_id == user._id and
                          site.link_flair_self_assign_enabled)

        return site.is_moderator_with_perms(user, 'flair') or can_assign_own

    def set_flair(self, text=None, css_class=None, set_by=None):
        self.flair_text = text
        self.flair_css_class = css_class
        self._commit()
        self.update_search_index()

        if set_by and set_by._id != self.author_id:
            ModAction.create(self.subreddit_slow, set_by, action='editflair',
                target=self, details='flair_edit')

    def set_sticky_comment(self, comment, set_by=None):
        """Given a comment, set it as the sticky (top) comment for this link.

        Only one comment may be stickied at a time, and stickied comments must
        be top level. `set_by` is an optional Account, which if set will add
        a ModAction event to the mod log.

        Raises `RedditError` on an attempt to sticky non-top-level comments.
        """
        if not comment.is_stickyable:
            raise RedditError('COMMENT_NOT_STICKYABLE', code=400)

        if self.sticky_comment_id == comment._id:
            return

        # Remove the current sticky if it exists before setting a new one
        self.remove_sticky_comment(set_by=set_by)

        self.sticky_comment_id = comment._id
        self._commit()

        if set_by:
            ModAction.create(
                self.subreddit_slow,
                set_by,
                action='sticky',
                target=comment,
            )

    def remove_sticky_comment(self, comment=None, set_by=None):
        """Remove the sticky (top) comment for this link, if it exists.

        `comment` is an optional argument that, if set, will ensure the
        sticky comment matches. If it does not match, it will not remove. If
        `comment` is unset, it will remove regardless of ID.

        `set_by` is an optional Account, which if set will add a ModAction
        event to the mod log.
        """
        if self.sticky_comment_id is None:
            return  # nothing to do

        if comment and self.sticky_comment_id != comment._id:
            return

        prev_sticky_comment = Comment._byID(self.sticky_comment_id)

        self.sticky_comment_id = None
        self._commit()

        if set_by:
            ModAction.create(
                self.subreddit_slow,
                set_by,
                action='unsticky',
                target=prev_sticky_comment,
            )

    @classmethod
    def _utf8_encode(cls, value):
        """
        Returns a deep copy of the parameter, UTF-8-encoding any strings
        encountered.
        """
        if isinstance(value, dict):
            return {cls._utf8_encode(key): cls._utf8_encode(value)
                    for key, value in value.iteritems()}
        elif isinstance(value, list):
            return [cls._utf8_encode(item)
                    for item in value]
        elif isinstance(value, unicode):
            return value.encode('utf-8')
        else:
            return value

    # There's an issue where pickling fails for collections with string values
    # that have unicode codepoints between 128 and 256.  Encoding the strings
    # as UTF-8 before storing them works around this.
    def set_media_object(self, value):
        self.media_object = Link._utf8_encode(value)

    def set_secure_media_object(self, value):
        self.secure_media_object = Link._utf8_encode(value)

    def set_preview_object(self, value):
        self.preview_object = Link._utf8_encode(value)

    def is_stickyable(self):
        if self._deleted or self._spam:
            return False

        return True

    @property
    def is_stickied_slow(self):
        return self.is_stickied(self.subreddit_slow)

    def is_stickied(self, subreddit):
        if not subreddit.sticky_fullnames:
            return False

        if self._fullname in subreddit.sticky_fullnames:
            return True

        return False


class LinksByUrlAndSubreddit(tdb_cassandra.View):
    _use_db = True
    _connection_pool = 'main'
    _read_consistency_level = tdb_cassandra.CL.ONE
    _compare_with = LongType()
    _extra_schema_creation_args = {
        "key_validation_class": UTF8_TYPE,
    }

    @classmethod
    def make_canonical_url(cls, url):
        if not utils.domain(url) in g.case_sensitive_domains:
            keyurl = _force_utf8(UrlParser.base_url(url.lower()))
        else:
            # Convert only hostname to lowercase
            up = UrlParser(url)
            up.hostname = up.hostname.lower()
            keyurl = _force_utf8(UrlParser.base_url(up.unparse()))

        # Cassandra max key length is 65535, truncate url if it's near that
        # (leaving some space for the prefix)
        keyurl = keyurl[:65000]

        return keyurl

    @classmethod
    def make_sr_rowkey(cls, canonical_url, sr_id):
        return "{sr}:{url}".format(sr=sr_id, url=canonical_url)

    @classmethod
    def make_all_rowkey(cls, canonical_url):
        return "all:{url}".format(url=canonical_url)

    @classmethod
    def add_link(cls, link):
        canonical_url = cls.make_canonical_url(link.url)
        sr_rowkey = cls.make_sr_rowkey(canonical_url, link.sr_id)
        all_rowkey = cls.make_all_rowkey(canonical_url)
        column = {link._id: ""}
        cls._set_values(sr_rowkey, column)
        cls._set_values(all_rowkey, column)

    @classmethod
    def remove_link(cls, link):
        canonical_url = cls.make_canonical_url(link.url)
        sr_rowkey = cls.make_sr_rowkey(canonical_url, link.sr_id)
        all_rowkey = cls.make_all_rowkey(canonical_url)
        column = {link._id: ""}
        cls._remove(sr_rowkey, column)
        cls._remove(all_rowkey, column)

    @classmethod
    def get_link_ids(cls, url, sr=None, limit=1000):
        canonical_url = cls.make_canonical_url(url)
        if sr:
            rowkey = cls.make_sr_rowkey(canonical_url, sr._id)
        else:
            rowkey = cls.make_all_rowkey(canonical_url)
        try:
            columns = cls._cf.get(
                rowkey, column_reversed=True, column_count=limit)
        except tdb_cassandra.NotFoundException:
            return []

        link_ids = columns.keys()
        return link_ids


# Note that there are no instances of PromotedLink or LinkCompressed,
# so overriding their methods here will not change their behaviour
# (except for add_props). These classes are used to override the
# render_class on a Wrapped to change the template used for rendering

class PromotedLink(Link):
    _nodb = True

    # embeds are editable by users (advertisers) so they can change and should
    # be considered in the render cache key
    cache_ignore = Link.cache_ignore - {"media_object", "secure_media_object"}

    @classmethod
    def add_props(cls, user, wrapped):
        Link.add_props(user, wrapped)

        for item in wrapped:
            item.nofollow = True
            item.user_is_sponsor = c.user_is_sponsor

            status = "promoted"

            # change the status class if the viewer is the author or a sponsor
            if item.is_author or item.user_is_sponsor:
                try:
                    status = PROMOTE_STATUS.name[item.promote_status]
                except (AttributeError, IndexError):
                    pass

            item.rowstyle_cls = "link %s" % status

        # Run this last
        Printable.add_props(user, wrapped)


class ReadNextLink(Link):
    _nodb = True


class SearchResultLink(Link):
    _nodb = True

    @classmethod
    def add_props(cls, user, wrapped):
        Link.add_props(user, wrapped)
        for item in wrapped:
            url = UrlParser(item.permalink)
            url.update_query(ref="search_posts")
            item.permalink = url.unparse()
        Printable.add_props(user, wrapped)


class LegacySearchResultLink(Link):
    _nodb = True

    @classmethod
    def add_props(cls, user, wrapped):
        Link.add_props(user, wrapped)
        for item in wrapped:
            url = UrlParser(item.permalink)
            url.update_query(ref="search_posts")
            item.permalink = url.unparse()
            item.render_css_class = 'link'
        Printable.add_props(user, wrapped)


class Comment(Thing, Printable):
    _cache = g.thingcache
    _data_int_props = Thing._data_int_props + ('reported', 'gildings')
    _defaults = dict(
        reported=0,
        parent_id=None,
        moderator_banned=False,
        new=False,
        gildings=0,
        banned_before_moderator=False,
        ignore_reports=False,
        sendreplies=True,
        admin_takedown=False,
    )
    _essentials = ('link_id', 'author_id')

    is_votable = True

    @classmethod
    def _cache_prefix(cls):
        return "comment:"

    def _markdown(self):
        pass

    @property
    def affects_karma_type(self):
        return "comment"

    @classmethod
    def _new(cls, author, link, parent, body, ip):
        from r2.lib.emailer import message_notification_email
        from r2.lib.voting import cast_vote

        subreddit = link.subreddit_slow

        # determine whether the comment should go straight into spam
        spam = False
        if link.promoted and link.author_id == author._id:
            # don't spam promoted link authors commenting on their own promos
            spam = False
        elif author._spam:
            spam = True
            g.stats.simple_event('spam.autoremove.comment')
        elif (subreddit.spam_comments == "all" and
                not subreddit.is_special(author)):
            spam = True

        comment = Comment(
            _ups=1,
            body=body,
            link_id=link._id,
            sr_id=link.sr_id,
            author_id=author._id,
            ip=ip,
            _spam=spam,
        )

        # these props aren't relations
        if parent:
            comment.parent_id = parent._id

        comment._commit()

        link._incr('num_comments', 1)

        to = None
        name = 'inbox'
        if parent and parent.sendreplies:
            to = Account._byID(parent.author_id, True)
        if not parent and not link._deleted and link.sendreplies:
            to = Account._byID(link.author_id, True)
            name = 'selfreply'

        g.events.comment_event(comment, request=request, context=c)

        cast_vote(author, comment, Vote.DIRECTIONS.up)

        if link.num_comments < 20 or link.num_comments % 10 == 0:
            # link's number of comments changed so re-index it, but don't bother
            # re-indexing so often when it gets many comments
            link.update_search_index(boost_only=True)

        CommentsByAccount.add_comment(author, comment)
        SubredditParticipationByAccount.mark_participated(author, subreddit)
        author.last_comment_time = int(epoch_timestamp(datetime.now(g.tz)))
        author._commit()

        def should_send():
            # don't send the message to author if replying to own comment
            if author._id == to._id:
                return False
            # only global admins can be message spammed
            if to.name in g.admins:
                return True
            # don't send the message if spam
            # don't send the message if the recipient has blocked the author
            if comment._spam or author._id in to.enemies:
                return False
            return True

        inbox_rel = None
        if to and should_send():
            # Record the inbox relation and give the user an orangered
            inbox_rel = Inbox._add(to, comment, name, orangered=True)

            if to.pref_email_messages:
                data = {
                    'to': to._id36,
                    'from': '/u/%s' % author.name,
                    'comment': comment._fullname,
                    'permalink': comment.make_permalink_slow(force_domain=True),
                }
                data = json.dumps(data)
                TryLater.schedule('message_notification_email', data,
                                  NOTIFICATION_EMAIL_DELAY)

        hooks.get_hook('comment.new').call(comment=comment)

        return (comment, inbox_rel)

    def _save(self, user, category=None):
        CommentSavesByAccount._save(user, self, category)

    def _unsave(self, user):
        CommentSavesByAccount._unsave(user, self)

    @property
    def link_slow(self):
        """Fetch a comment's Link and return it.

        In most cases the Link is already on the wrapped comment (as .link),
        and that should be used when possible.
        """
        return Link._byID(self.link_id, data=True, return_dict=False)

    @property
    def subreddit_slow(self):
        # When the Comment is Wrapped the subreddit is available as .subreddit
        # and that should be used
        return Subreddit._byID(self.sr_id, stale=True)

    @property
    def author_slow(self):
        """Returns the comment's author."""
        # The author is often already on the wrapped comment as .author
        # If available, that should be used instead of calling this
        return Account._byID(self.author_id, data=True, return_dict=False)

    @property
    def archived_slow(self):
        sr = self.subreddit_slow
        return self.is_archived(sr)

    def is_archived(self, sr):
        return self._age >= sr.archive_age

    @property
    def is_stickyable(self):
        if self.parent_id is not None:
            return False

        return True

    def keep_item(self, wrapped):
        if c.user_is_admin:
            return True

        if c.user_is_loggedin:
            if wrapped.subreddit.is_moderator(c.user):
                return True
            if wrapped.author_id == c.user._id:
                return True
            if wrapped.author_id in c.user.enemies:
                return False

        return True

    cache_ignore = set((
        "subreddit",
        "link",
        "to",
        "num_children",
        "depth",
        "child_ids",
    )).union(Printable.cache_ignore)

    @staticmethod
    def wrapped_cache_key(wrapped, style):
        s = Printable.wrapped_cache_key(wrapped, style)
        s.extend([wrapped.body])
        s.extend([hasattr(wrapped, "link") and wrapped.link.contest_mode])
        if hasattr(wrapped, "link") and wrapped.link.locked:
            s.append('locked')
        return s

    def make_permalink(self, link, sr=None, context=None, anchor=False,
                       force_domain=False):
        url = link.make_permalink(sr, force_domain=force_domain) + self._id36
        if context:
            url += "?context=%d" % context
        if anchor:
            url += "#%s" % self._id36
        return url

    def make_permalink_slow(self, context=None, anchor=False,
                            force_domain=False):
        l = Link._byID(self.link_id, data=True)
        return self.make_permalink(l, l.subreddit_slow,
                                   context=context, anchor=anchor,
                                   force_domain=force_domain)

    def _gild(self, user):
        now = datetime.now(g.tz)

        self._incr("gildings")
        self.subreddit_slow.add_gilding_seconds()

        GildedCommentsByAccount.gild(user, self)

        from r2.lib.db import queries
        with CachedQueryMutator() as m:
            gilding = utils.Storage(thing=self, date=now)
            m.insert(queries.get_all_gilded_comments(), [gilding])
            m.insert(queries.get_gilded_comments(self.sr_id), [gilding])
            m.insert(queries.get_gilded_user_comments(self.author_id),
                     [gilding])
            m.insert(queries.get_user_gildings(user), [gilding])

        hooks.get_hook('comment.gild').call(comment=self, gilder=user)

    def _qa(self, children, responder_ids):
        """Sort a comment according to the Q&A-type sort.

        Arguments:

        * children -- a list of the children of this comment.
        * responder_ids -- a set of ids of users categorized as "answerers" for
          this thread.
        """
        # This sort type only makes sense for comments, unlike the other sorts
        # that can be applied to any Things, which is why it's defined here
        # instead of in Thing.

        op_children = [c for c in children if c.author_id in responder_ids]
        score = sorts.qa(self._ups, self._downs, len(self.body), op_children)

        # When replies to a question, we want to rank OP replies higher than
        # non-OP replies (generally).  This is a rough way to do so.
        # Don't add extra scoring when we've already added it due to replies,
        # though (because an OP responds to themselves).
        if self.author_id in responder_ids and not op_children:
            score *= 2

        return score

    @classmethod
    def update_nofollow(cls, user, wrapped):
        user_is_loggedin = c.user_is_loggedin
        for item in wrapped:
            if user_is_loggedin and item.author_id == user._id:
                item.nofollow = False
            elif item._spam or item.link._spam or item.author._spam:
                item.nofollow = True
            else:
                item.nofollow = False

        hooks.get_hook("comment.update_nofollow").call(
            user=user,
            wrapped=wrapped,
        )

    @classmethod
    def add_props(cls, user, wrapped):
        from r2.lib.template_helpers import add_submitter_distinguish, get_domain
        from r2.lib.utils import timeago
        from r2.lib.wrapped import CachedVariable
        from r2.lib.pages import WrappedUser
        from r2.models.report import Report

        #fetch parent links
        links = Link._byID(set(l.link_id for l in wrapped), data=True,
                           return_dict=True, stale=True)

        # fetch authors
        authors = Account._byID(set(l.author_id for l in links.values()), data=True,
                                return_dict=True, stale=True)

        #get srs for comments that don't have them (old comments)
        for cm in wrapped:
            if not hasattr(cm, 'sr_id'):
                cm.sr_id = links[cm.link_id].sr_id

        subreddits = {item.subreddit for item in wrapped}

        if c.user_is_loggedin:
            is_moderator_subreddits = {
                sr._id for sr in subreddits if sr.is_moderator(user)}
            can_reply_srs = set(
                sr._id for sr in subreddits if sr.can_comment(user))
            can_distinguish_srs = set(
                sr._id for sr in subreddits if sr.can_distinguish(user))
            promo_sr_id = Subreddit.get_promote_srid()
            if promo_sr_id:
                can_reply_srs.add(promo_sr_id)
        else:
            is_moderator_subreddits = set()
            can_reply_srs = set()
            can_distinguish_srs = set()

        cids = dict((w._id, w) for w in wrapped)
        parent_ids = set(cm.parent_id for cm in wrapped
                         if getattr(cm, 'parent_id', None)
                         and cm.parent_id not in cids)
        parents = Comment._byID(
            parent_ids, data=True, stale=True, ignore_missing=True)

        profilepage = c.profilepage
        user_is_admin = c.user_is_admin
        user_is_loggedin = c.user_is_loggedin
        focal_comment = c.focal_comment
        site = c.site

        if user_is_loggedin:
            gilded = [thing for thing in wrapped if thing.gildings > 0]
            try:
                user_gildings = GildedCommentsByAccount.fast_query(user, gilded)
            except tdb_cassandra.TRANSIENT_EXCEPTIONS as e:
                g.log.warning("Cassandra gilding lookup failed: %r", e)
                user_gildings = {}

            try:
                saved = CommentSavesByAccount.fast_query(user, wrapped)
            except tdb_cassandra.TRANSIENT_EXCEPTIONS as e:
                g.log.warning("Cassandra comment save lookup failed: %r", e)
                saved = {}
        else:
            user_gildings = {}
            saved = {}

        for item in wrapped:
            # for caching:
            item.profilepage = c.profilepage
            item.link = Wrapped(links.get(item.link_id))
            item.link.author = authors.get(item.link.author_id)
            item.show_admin_context = user_is_admin

            if not hasattr(item, 'subreddit'):
                item.subreddit = item.subreddit_slow

        cls.update_nofollow(user, wrapped)

        for item in wrapped:
            if item.author_id == item.link.author_id and not item.link._deleted:
                add_submitter_distinguish(item.attribs, item.link, item.subreddit)

            if not hasattr(item, 'target'):
                item.target = None

            parent = None
            if item.parent_id:
                if item.parent_id in parents:
                    parent = parents[item.parent_id]
                elif item.parent_id in cids:
                    parent = cids[item.parent_id]

            if parent and not parent.deleted:
                if item.parent_id in cids:
                    # parent is displayed on the page, use an anchor tag
                    item.parent_permalink = '#' + utils.to36(item.parent_id)
                else:
                    item.parent_permalink = parent.make_permalink(item.link, item.subreddit)
            else:
                item.parent_permalink = None

            item.archived = item.is_archived(item.subreddit)

            link_is_archived = item.link.is_archived(item.subreddit)
            link_is_locked = item.link.locked
            sr_can_distinguish = item.sr_id in can_distinguish_srs
            sr_can_reply = item.sr_id in can_reply_srs

            if user_is_loggedin:
                item.can_reply = (
                    not link_is_archived and
                    (not link_is_locked or sr_can_distinguish) and
                    sr_can_reply
                )
            else:
                item.can_reply = not link_is_archived and not link_is_locked

            item.can_embed = c.can_embed or False

            if user_is_loggedin:
                item.user_gilded = (user, item) in user_gildings
                item.saved = (user, item) in saved
            else:
                item.user_gilded = False
                item.saved = False
            item.gilded_message = make_gold_message(item, item.user_gilded)

            item.can_gild = (
                # you can't gild your own comment
                not (c.user_is_loggedin and
                     item.author and
                     item.author._id == user._id) and
                # no point in showing the button for things you've already gilded
                not item.user_gilded and
                # ick, if the author deleted their account we shouldn't waste gold
                not item.author._deleted and
                # some subreddits can have gilding disabled
                item.subreddit.allow_gilding
            )

            if c.user_is_loggedin and c.user.in_timeout:
                item.mod_reports, item.user_reports = [], []
            else:
                item.mod_reports, item.user_reports = Report.get_reports(item)

            # not deleted on profile pages,
            # deleted if spam and not author or admin
            item.deleted = (not profilepage and
                           (item._deleted or
                            (item._spam and
                             item.author != user and
                             not item.show_spam)))

            item.have_form = not item.deleted

            extra_css = ''
            if item.deleted:
                extra_css += "grayed"
                if not user_is_admin:
                    item.author = DeletedUser()
                    item.gildings = 0
                    item.distinguished = None
                    # If removed by an admin or moderator, distinguish that
                    # from being deleted by the user.
                    if item._spam:
                        item.body = '[removed]'
                    else:
                        item.body = '[deleted]'

            if focal_comment == item._id36:
                extra_css += " border"

            if profilepage:
                item.nsfw = user.pref_label_nsfw and (item.link.is_nsfw or item.subreddit.over_18)

                link_author = item.link.author
                if ((item.link._deleted or link_author._deleted) and
                        not user_is_admin):
                    link_author = DeletedUser()
                item.link_author = WrappedUser(link_author)
                item.full_comment_path = item.link.make_permalink(item.subreddit)
                item.full_comment_count = item.link.num_comments

                if item.sr_id == Subreddit.get_promote_srid():
                    item.taglinetext = _("%(link)s by %(author)s [sponsored link]")
                else:
                    item.taglinetext = _("%(link)s by %(author)s in %(subreddit)s")

            else:
                # these aren't used so set them to constant values to avoid
                # invalidating items in render cache
                item.full_comment_path = ''
                item.full_comment_count = 0

            item.subreddit_path = item.subreddit.path

            # always use the default collapse threshold in contest mode threads
            # if the user has a custom collapse threshold
            if (item.link.contest_mode and
                    user.pref_min_comment_score is not None):
                min_score = Account._defaults['pref_min_comment_score']
            else:
                min_score = user.pref_min_comment_score

            item.collapsed = False
            distinguished = item.distinguished and item.distinguished != "no"
            prevent_collapse = profilepage or user_is_admin or distinguished

            if (item.deleted and item.subreddit.collapse_deleted_comments and
                    not prevent_collapse):
                item.collapsed = True
            elif item.score < min_score and not prevent_collapse:
                item.collapsed = True
                item.collapsed_reason = _("comment score below threshold")
            elif user_is_loggedin and item.author_id in c.user.enemies:
                if "grayed" not in extra_css:
                    extra_css += " grayed"
                item.collapsed = True
                item.collapsed_reason = _("blocked user")

            item.editted = getattr(item, "editted", False)

            item.is_sticky = (item.link.sticky_comment_id == item._id)

            item.render_css_class = "comment"

            #will get updated in builder
            item.num_children = 0
            item.numchildren_text = CachedVariable("numchildren_text")
            item.score_fmt = Score.points
            item.permalink = item.make_permalink(item.link, item.subreddit)

            item.is_author = (user == item.author)
            item.is_focal = (focal_comment == item._id36)

            item.votable = item._age < item.subreddit.archive_age

            hide_period = ('{0} minutes'
                          .format(item.subreddit.comment_score_hide_mins))

            if item.is_sticky or item.link.contest_mode:
                item.score_hidden = True
            elif item._date > timeago(hide_period):
                item.score_hidden = not item.is_author
            else:
                item.score_hidden = False

            item.user_is_moderator = item.sr_id in is_moderator_subreddits

            if item.score_hidden and c.user_is_loggedin:
                if c.user_is_admin or item.user_is_moderator:
                    item.score_hidden = False

            if item.score_hidden:
                item.upvotes = 1
                item.downvotes = 0
                item.score = 1
                item.voting_score = [1, 1, 1]
                item.render_css_class += " score-hidden"

            # in contest mode, use only upvotes for the score if the subreddit
            # has been (manually) set to do so
            if (item.link.contest_mode and
                    item.subreddit.contest_mode_upvotes_only and
                    not item.score_hidden):
                item.score = item._ups
                item.voting_score = [
                    item.score - 1, item.score, item.score + 1]
                item.collapsed = False

            if item.is_author:
                item.inbox_replies_enabled = item.sendreplies

            #will seem less horrible when add_props is in pages.py
            from r2.lib.pages import UserText
            item.usertext = UserText(item, item.body,
                                     editable=item.is_author,
                                     nofollow=item.nofollow,
                                     target=item.target,
                                     extra_css=extra_css,
                                     have_form=item.have_form)

            item.lastedited = CachedVariable("lastedited")

        # Run this last
        Printable.add_props(user, wrapped)

    def update_search_index(self, boost_only=False):
        # no-op because Comments are not indexed
        return


class CommentScoresByLink(tdb_cassandra.View):
    _use_db = True
    _connection_pool = 'main'
    _read_consistency_level = tdb_cassandra.CL.ONE
    _fetch_all_columns = True

    _extra_schema_creation_args = {
        "column_name_class": ASCII_TYPE,
        "default_validation_class": DOUBLE_TYPE,
        "key_validation_class": ASCII_TYPE,
    }
    _value_type = "bytes"
    _compare_with = ASCII_TYPE

    @classmethod
    def _rowkey(cls, link, sort):
        assert sort.startswith('_')
        return '%s%s' % (link._id36, sort)

    @classmethod
    def set_scores(cls, link, sort, scores_by_comment):
        rowkey = cls._rowkey(link, sort)
        cls._set_values(rowkey, scores_by_comment)

    @classmethod
    def get_scores(cls, link, sort):
        rowkey = cls._rowkey(link, sort)
        try:
            return CommentScoresByLink._byID(rowkey)._values()
        except tdb_cassandra.NotFound:
            return {}


class MoreMessages(Printable):
    cachable = False
    display = ""
    new = False
    was_comment = False
    is_collapsed = True

    def __init__(self, parent, child):
        self.parent = parent
        self.child = child

    @staticmethod
    def wrapped_cache_key(item, style):
        return False

    @property
    def _fullname(self):
        return self.parent._fullname

    @property
    def _id36(self):
        return self.parent._id36

    @property
    def _id(self):
        return self.parent._id

    @property
    def subject(self):
        return self.parent.subject

    @property
    def childlisting(self):
        return self.child

    @property
    def to(self):
        return self.parent.to

    @property
    def author(self):
        return self.parent.author

    @property
    def user_is_recipient(self):
        return self.parent.user_is_recipient

    @property
    def sr_id(self):
        return self.parent.sr_id

    @property
    def subreddit(self):
        return self.parent.subreddit

    @property
    def accent_color(self):
        return getattr(self.parent, "accent_color", None)


class MoreComments(Printable):
    cachable = False
    display = ""

    @staticmethod
    def wrapped_cache_key(item, style):
        return False

    def __init__(self, link, depth, parent_id=None):
        if parent_id is not None:
            id36 = utils.to36(parent_id)
            self.parent_id = parent_id
            self.parent_name = "t%s_%s" % (utils.to36(Comment._type_id), id36)
            self.parent_permalink = link.make_permalink_slow() + id36
        self.link_name = link._fullname
        self.link_id = link._id
        self.depth = depth
        self.children = []
        self.count = 0

    @property
    def _fullname(self):
        return "t%s_%s" % (utils.to36(Comment._type_id), self._id36)

    @property
    def _id36(self):
        return utils.to36(self.children[0]) if self.children else '_'


class MoreRecursion(MoreComments):
    pass


class MoreChildren(MoreComments):
    def __init__(self, link, sort_operator, depth, parent_id=None):
        from r2.lib.menus import CommentSortMenu
        self.sort = CommentSortMenu.sort(sort_operator)
        MoreComments.__init__(self, link, depth, parent_id)


class Message(Thing, Printable):
    _cache = g.thingcache
    _defaults = dict(reported=0,
                     was_comment=False,
                     parent_id=None,
                     new=False,
                     first_message=None,
                     to_id=None,
                     sr_id=None,
                     to_collapse=None,
                     author_collapse=None,
                     from_sr=False,
                     display_author=None,
                     display_to=None,
                     email_id=None,
                     sent_via_email=False,
                     del_on_recipient=False,
                     )
    _data_int_props = Thing._data_int_props + ('reported',)
    _essentials = ('author_id',)
    cache_ignore = set(["to", "subreddit"]).union(Printable.cache_ignore)

    @classmethod
    def _cache_prefix(cls):
        return "message:"

    @classmethod
    def _new(cls, author, to, subject, body, ip, parent=None, sr=None,
             from_sr=False, can_send_email=True, sent_via_email=False,
             email_id=None):
        from r2.lib.emailer import message_notification_email
        from r2.lib.message_to_email import queue_modmail_email

        m = Message(subject=subject, body=body, author_id=author._id, new=True,
                    ip=ip, from_sr=from_sr, sent_via_email=sent_via_email,
                    email_id=email_id)
        m._spam = author._spam

        if author._spam:
            g.stats.simple_event('spam.autoremove.message')

        sr_id = None
        # check to see if the recipient is a subreddit and swap args accordingly
        if to and isinstance(to, Subreddit):
            if from_sr:
                raise CreationError("Cannot send from SR to SR")
            to_subreddit = True
            to, sr = None, to
        else:
            to_subreddit = False

        if sr:
            sr_id = sr._id

        if parent:
            m.parent_id = parent._id
            if parent.first_message:
                m.first_message = parent.first_message
            else:
                m.first_message = parent._id

            if parent.sr_id:
                sr_id = parent.sr_id

            if parent.display_author and not getattr(parent, "signed", False):
                m.display_to = parent.display_author

        if not to and not sr_id:
            raise CreationError("Message created with neither to nor sr_id")
        if from_sr and not sr_id:
            raise CreationError("Message sent from_sr without setting sr")

        m.to_id = to._id if to else None
        if sr_id is not None:
            m.sr_id = sr_id

        m._commit()

        hooks.get_hook('message.new').call(message=m)

        MessagesByAccount.add_message(author, m)

        if sr_id and not sr:
            sr = Subreddit._byID(sr_id)

        if to_subreddit:
            SubredditParticipationByAccount.mark_participated(author, sr)

        if sr_id:
            g.stats.simple_event("modmail.received_message")

        inbox_rel = []

        inbox_hook = hooks.get_hook("message.skip_inbox")
        skip_inbox = inbox_hook.call_until_return(message=m)
        if skip_inbox:
            m._spam = True
            m._commit()

        if not skip_inbox and sr_id:
            if parent or to_subreddit or from_sr:
                inbox_rel.append(ModeratorInbox._add(sr, m))

            if sr.is_moderator(author):
                m.distinguished = 'yes'
                m._commit()

            if (can_send_email and
                    sr.name in g.live_config['modmail_forwarding_email']):
                queue_modmail_email(m)

        if author.name in g.admins:
            m.distinguished = 'admin'
            m._commit()

        # if there is a "to" we may have to create an inbox relation as well
        # also, only global admins can be message spammed.
        if not skip_inbox and to and (not m._spam or to.name in g.admins):
            # if "to" is not a sr moderator they need to be notified
            if not sr_id or not sr.is_moderator(to):
                inbox_rel.append(Inbox._add(to, m, 'inbox'))

                if (
                    to.pref_email_messages and
                    m.author_id not in to.enemies and
                    to._id != m.author_id
                ):
                    from r2.lib.template_helpers import get_domain
                    if from_sr:
                        sender_name = '/r/%s' % sr.name
                    else:
                        sender_name = '/u/%s' % author.name
                    permalink = 'http://%(domain)s%(path)s' % {
                        'domain': get_domain(),
                        'path': m.permalink,
                    }
                    data = {
                        'to': to._id36,
                        'from': sender_name,
                        'comment': m._fullname,
                        'permalink': permalink,
                    }
                    data = json.dumps(data)
                    TryLater.schedule('message_notification_email', data,
                                      NOTIFICATION_EMAIL_DELAY)

        # update user inboxes for non-mods involved in a modmail conversation
        if not skip_inbox and sr_id and m.first_message:
            first_message = Message._byID(m.first_message, data=True)
            first_sender = Account._byID(first_message.author_id, data=True)
            first_sender_modmail = sr.is_moderator_with_perms(
                first_sender, 'mail')

            if (first_sender != author and
                    first_sender != to and
                    not first_sender_modmail):
                inbox_rel.append(Inbox._add(first_sender, m, 'inbox'))

            if first_message.to_id:
                first_recipient = Account._byID(first_message.to_id, data=True)
                first_recipient_modmail = sr.is_moderator_with_perms(
                    first_recipient, 'mail')
                if (first_recipient != author and
                        first_recipient != to and
                        not first_recipient_modmail):
                    inbox_rel.append(Inbox._add(first_recipient, m, 'inbox'))

        if sr_id:
            g.events.modmail_event(m, request=request, context=c)
        else:
            g.events.message_event(m, request=request, context=c)

        return (m, inbox_rel)

    @property
    def permalink(self):
        return "/message/messages/%s" % self._id36

    def make_permalink(self, force_domain=False):
        from r2.lib.template_helpers import get_domain
        p = self.permalink
        if force_domain:
            permalink_domain = get_domain(subreddit=False)
            res = "%s://%s%s" % (g.default_scheme, permalink_domain, p)
        else:
            res = p
        return res

    def make_permalink_slow(self, context=None, anchor=False, force_domain=False):
        return self.make_permalink(force_domain)

    def can_view_slow(self):
        if c.user_is_loggedin:
            if (c.user_is_admin or
                    c.user._id in (self.author_id, self.to_id)):
                return True
            elif self.sr_id:
                sr = Subreddit._byID(self.sr_id, data=True, stale=True)

                if sr.is_moderator_with_perms(c.user, 'mail'):
                    return True
                elif self.first_message:
                    first = Message._byID(self.first_message, data=True)
                    return c.user._id in (first.author_id, first.to_id)

    def get_muted_user_in_conversation(self):
        """Return the muted user involved in a modmail conversation (if any)."""
        if not self.sr_id:
            return None

        sr = self.subreddit_slow

        if self.first_message:
            first = Message._byID(self.first_message, data=True)
        else:
            first = self

        first_author = first.author_slow
        first_recipient = first.recipient_slow if first.to_id else None

        if sr.is_muted(first_author):
            return first_author
        elif first_recipient and sr.is_muted(first_recipient):
            return first_recipient
        else:
            return None

    @classmethod
    def add_props(cls, user, wrapped):
        from r2.lib.db import queries

        # make sure there is a sr_id set:
        for w in wrapped:
            if not hasattr(w, "sr_id"):
                w.sr_id = None

        to_ids = {w.to_id for w in wrapped if w.to_id}
        other_account_ids = {w.display_author or w.display_to for w in wrapped
            if not (w.was_comment or w.sr_id) and
                (w.display_author or w.display_to)}
        account_ids = to_ids | other_account_ids
        accounts = Account._byID(account_ids, data=True)

        link_ids = {w.link_id for w in wrapped if w.was_comment}
        links = Link._byID(link_ids, data=True)

        srs = {w.subreddit._id: w.subreddit for w in wrapped if w.sr_id}

        parent_ids = {w.parent_id for w in wrapped
            if w.parent_id and w.was_comment}
        parents = Comment._byID(parent_ids, data=True)

        # load full modlist for all subreddit messages
        mods_by_srid = {sr._id: sr.moderator_ids() for sr in srs.itervalues()}
        user_mod_sr_ids = {sr_id for sr_id, mod_ids in mods_by_srid.iteritems()
            if user._id in mod_ids}

        # special handling for mod replies to mod PMs
        mod_message_authors = {}
        mod_messages = [
            item for item in wrapped
            if (item.to_id is None and
                    item.sr_id and
                    item.parent_id and
                    (c.user_is_admin or item.sr_id in user_mod_sr_ids))
        ]
        if mod_messages:
            parent_ids = [item.parent_id for item in mod_messages]
            parents = Message._byID(parent_ids, data=True, return_dict=True)
            author_ids = {item.author_id for item in parents.itervalues()}
            authors = Account._byID(author_ids, data=True, return_dict=True)

            for item in mod_messages:
                parent = parents[item.parent_id]
                author = authors[parent.author_id]
                mod_message_authors[item._id] = author

        # load the unread list to determine message newness
        unread = set(queries.get_unread_inbox(user))

        # load the unread mod list for the same reason
        mod_msg_srs = {srs[w.sr_id] for w in wrapped
            if w.sr_id and not w.was_comment and w.sr_id in user_mod_sr_ids}
        mod_unread = set(
            queries.get_unread_subreddit_messages_multi(mod_msg_srs))

        # load blocked subreddits
        sr_blocks = BlockedSubredditsByAccount.fast_query(user, srs.values())
        blocked_srids = {sr._id for _user, sr in sr_blocks.iterkeys()}

        can_set_unread = (user.pref_mark_messages_read and
                            c.extension not in ("rss", "xml", "api", "json"))
        to_set_unread = []

        # accent colors for color coded modmail
        sr_colors = None
        if isinstance(c.site, FakeSubreddit):
            mod_sr_ids = Subreddit.reverse_moderator_ids(user)
            if len(mod_sr_ids) > 1:
                sr_colors = dict(zip(mod_sr_ids, cycle(Subreddit.ACCENT_COLORS)))

        for item in wrapped:
            user_is_recipient = item.to_id == user._id
            user_is_sender = (item.author_id == user._id and
                not getattr(item, "display_author", None))
            sent_by_sr = item.sr_id and getattr(item, 'from_sr', None)
            sent_to_sr = item.sr_id and not item.to_id

            item.to = accounts[item.to_id] if item.to_id else None
            item.is_mention = False
            item.is_collapsed = None
            item.score_fmt = Score.none
            item.hide_author = False

            if item.was_comment:
                item.user_is_recipient = user_is_recipient
                link = links[item.link_id]
                sr = srs[link.sr_id]
                item.to_collapse = False
                item.author_collapse = False
                item.link_title = link.title
                item.permalink = item.lookups[0].make_permalink(link, sr=sr)
                item.link_permalink = link.make_permalink(sr)
                item.full_comment_count = link.num_comments
                parent = parents[item.parent_id] if item.parent_id else None

                if parent:
                    item.parent = parent._fullname
                    item.parent_permalink = parent.make_permalink(link, sr)

                if parent and parent.author_id == user._id:
                    item.subject = _('comment reply')
                elif not parent and link.author_id == user._id:
                    item.subject = _('post reply')
                else:
                    item.subject = _('username mention')
                    item.is_mention = True

                item.taglinetext = _(
                    "from %(author)s via %(subreddit)s sent %(when)s")
            elif item.sr_id:
                item.user_is_recipient = not user_is_sender
                item.user_is_moderator = item.sr_id in user_mod_sr_ids

                if sr_colors and item.user_is_moderator:
                    item.accent_color = sr_colors.get(item.sr_id)

                if item.subreddit.is_muted(item.author):
                    item.sr_muted = True

                if sent_by_sr:
                    if item.sr_id in blocked_srids:
                        item.subject = _('[message from blocked subreddit]')
                        item.sr_blocked = True
                        item.is_collapsed = True

                    # use special handling of admin distinguish because ALL
                    # messages from admin accounts are marked for admin
                    # distinguish, but we only want to use the admin distinguish
                    # on messages sent from /r/reddit.com
                    if (item.distinguished == "admin" and
                            "/r/%s" % item.subreddit.name == g.admin_message_acct):
                        subreddit_distinguish = "admin"
                    elif (item.distinguished == "moderator" or
                            item.distinguished == "admin"):
                        subreddit_distinguish = "moderator"
                    else:
                        subreddit_distinguish = None

                    if item.sent_via_email:
                        item.hide_author = True
                        item.distinguished = "yes"
                        item.taglinetext = _(
                            "subreddit message via %(subreddit)s sent %(when)s")
                    elif not item.user_is_moderator and not c.user_is_admin:
                        item.author = item.subreddit
                        item.hide_author = True
                        item.taglinetext = _(
                            "subreddit message via %(subreddit)s sent %(when)s")
                        item.subreddit_distinguish = subreddit_distinguish
                    elif user_is_sender:
                        item.taglinetext = _(
                            "to %(dest)s via %(subreddit)s sent %(when)s")
                        item.subreddit_distinguish = subreddit_distinguish
                    else:
                        item.taglinetext = _(
                            "from %(author)s via %(subreddit)s to %(dest)s sent"
                            " %(when)s")
                        # don't set item.subreddit_distinguish because any
                        # distinguish will be associated with the author
                else:
                    if item._id in mod_message_authors:
                        # let moderators see the original author when a regular
                        # user responds to a modmail message from subreddit.
                        # item.to_id is not set, but we found the original
                        # sender by inspecting the parent message
                        item.to = mod_message_authors[item._id]

                    if user_is_recipient:
                        item.taglinetext = _(
                            "from %(author)s via %(subreddit)s sent %(when)s")
                    elif user_is_sender and sent_to_sr:
                        item.taglinetext = _("to %(subreddit)s sent %(when)s")
                    elif user_is_sender:
                        item.taglinetext = _(
                            "to %(dest)s via %(subreddit)s sent %(when)s")
                    elif sent_to_sr:
                        item.taglinetext = _(
                            "from %(author)s to %(subreddit)s sent %(when)s")
                    else:
                        item.taglinetext = _(
                            "from %(author)s via %(subreddit)s to %(dest)s sent"
                            " %(when)s")
            else:
                item.user_is_recipient = user_is_recipient

                if item.display_author:
                    item.author = accounts[item.display_author]

                if item.display_to:
                    item.to = accounts[item.display_to]
                    if item.to_id == user._id:
                        item.body = (strings.anonymous_gilder_warning +
                            _force_unicode(item.body))

                if user_is_recipient:
                    item.taglinetext = _("from %(author)s sent %(when)s")
                elif user_is_sender:
                    item.taglinetext = _("to %(dest)s sent %(when)s")
                else:
                    item.taglinetext = _(
                        "to %(dest)s from %(author)s sent %(when)s")

            if user_is_sender:
                item.new = False
            elif item._fullname in unread:
                item.new = True

                if can_set_unread:
                    to_set_unread.append(item.lookups[0])
            else:
                item.new = item._fullname in mod_unread

            if not item.new:
                if item.user_is_recipient:
                    item.is_collapsed = item.to_collapse
                if item.author_id == user._id:
                    item.is_collapsed = item.author_collapse
                if user.pref_collapse_read_messages:
                    item.is_collapsed = (item.is_collapsed is not False)

            if item.author_id in user.enemies and not item.was_comment:
                item.is_collapsed = True
                if not c.user_is_admin:
                    item.subject = _('[message from blocked user]')
                    item.body = _('[unblock user to see this message]')

            if item.sr_id and item.to:
                item.to_is_moderator = item.to._id in mods_by_srid[item.sr_id]

        if to_set_unread:
            queries.set_unread(to_set_unread, user, unread=False)

        Printable.add_props(user, wrapped)

    @property
    def subreddit_slow(self):
        from subreddit import Subreddit
        if self.sr_id:
            return Subreddit._byID(self.sr_id, data=True)

    @property
    def author_slow(self):
        """Returns the message's author."""
        # The author is often already on the wrapped message as .author
        # If available, that should be used instead of calling this
        return Account._byID(self.author_id, data=True, return_dict=False)

    @property
    def recipient_slow(self):
        """Returns the message's recipient."""
        return Account._byID(self.to_id, data=True, return_dict=False)

    @staticmethod
    def wrapped_cache_key(wrapped, style):
        s = Printable.wrapped_cache_key(wrapped, style)
        s.extend([wrapped.new, wrapped.collapsed])
        return s

    def keep_item(self, wrapped):
        if c.user_is_admin:
            return True
        # do not keep message which were deleted on recipient
        if (isinstance(self, Message) and
                self.to_id == c.user._id and self.del_on_recipient):
            return False
        return not wrapped.enemy


class _SaveHideByAccount(tdb_cassandra.DenormalizedRelation):
    @classmethod
    def value_for(cls, thing1, thing2):
        return ''

    @classmethod
    def _cached_queries(cls, user, thing):
        return []

    @classmethod
    def _savehide(cls, user, things, **kw):
        things = tup(things)
        now = datetime.now(g.tz)
        with CachedQueryMutator() as m:
            for thing in things:
                # action_date is only used by the cached queries as the sort
                # value, we don't want to write it. Report.new(link) needs to
                # incr link.reported but will fail if the link is dirty.
                thing.__setattr__('action_date', now, make_dirty=False)
                for q in cls._cached_queries(user, thing, **kw):
                    m.insert(q, [thing])
        cls.create(user, things, **kw)

    @classmethod
    def destroy(cls, user, things, **kw):
        things = tup(things)
        cls._cf.remove(user._id36, (things._id36 for things in things))

        for view in cls._views:
            view.destroy(user, things, **kw)

    @classmethod
    def _unsavehide(cls, user, things, **kw):
        things = tup(things)
        with CachedQueryMutator() as m:
            for thing in things:
                for q in cls._cached_queries(user, thing, **kw):
                    m.delete(q, [thing])
        cls.destroy(user, things, **kw)


class _ThingSavesByAccount(_SaveHideByAccount):
    _read_consistency_level = tdb_cassandra.CL.QUORUM
    _write_consistency_level = tdb_cassandra.CL.QUORUM

    @classmethod
    def value_for(cls, thing1, thing2, category=None):
        return category or ''

    @classmethod
    def _remove_from_category_listings(cls, user, things, category):
        things = tup(things)
        oldcategories = cls.fast_query(user, things)
        changedthings = []
        for thing in things:
            oldcategory = oldcategories.get((user, thing)) or None
            if oldcategory != category:
                changedthings.append(thing)
        cls._unsavehide(user, changedthings, categories=oldcategories)

    @classmethod
    def _save(cls, user, things, category=None):
        category = category.lower() if category else None
        cls._remove_from_category_listings(user, things, category=category)
        cls._savehide(user, things, category=category)

    @classmethod
    def _unsave(cls, user, things):
        # Ensure we delete from existing category cached queries
        categories = cls.fast_query(user, tup(things))
        cls._unsavehide(user, things, categories=categories)

    @classmethod
    def _unsavehide(cls, user, things, categories=None):
        things = tup(things)
        with CachedQueryMutator() as m:
            for thing in things:
                category = categories.get((user, thing)) if categories else None
                for q in cls._cached_queries(user, thing, category=category):
                    m.delete(q, [thing])
        cls.destroy(user, things, categories=categories)

    @classmethod
    def _cached_queries_category(cls, user, thing,
                                 querycatfn, queryfn,
                                 category=None, only_category=False):
        from r2.lib.db import queries
        cached_queries = []
        if not only_category:
            cached_queries = [queryfn(user, 'none'), queryfn(user, thing.sr_id)]
        if category:
            cached_queries.append(querycatfn(user, 'none', category))
            cached_queries.append(querycatfn(user, thing.sr_id, category))
        return cached_queries

class LinkSavesByAccount(_ThingSavesByAccount):
    _use_db = True
    _last_modified_name = 'Save'
    _views = []

    @classmethod
    def _cached_queries(cls, user, thing, **kw):
        from r2.lib.db import queries
        return cls._cached_queries_category(
            user,
            thing,
            queries.get_categorized_saved_links,
            queries.get_saved_links,
            **kw)

class CommentSavesByAccount(_ThingSavesByAccount):
    _use_db = True
    _last_modified_name = 'CommentSave'
    _views = []

    @classmethod
    def _cached_queries(cls, user, thing, **kw):
        from r2.lib.db import queries
        return cls._cached_queries_category(
            user,
            thing,
            queries.get_categorized_saved_comments,
            queries.get_saved_comments,
            **kw)

class _ThingHidesByAccount(_SaveHideByAccount):
    @classmethod
    def _hide(cls, user, things):
        cls._savehide(user, things)

    @classmethod
    def _unhide(cls, user, things):
        cls._unsavehide(user, things)


class LinkHidesByAccount(_ThingHidesByAccount):
    _use_db = True
    _last_modified_name = 'Hide'
    _views = []

    @classmethod
    def _cached_queries(cls, user, thing):
        from r2.lib.db import queries
        return [queries.get_hidden_links(user)]

class LinkVisitsByAccount(_SaveHideByAccount):
    _use_db = True
    _last_modified_name = 'Visit'
    _views = []
    _ttl = timedelta(days=7)
    _write_consistency_level = tdb_cassandra.CL.ONE

    @classmethod
    def _visit(cls, user, things):
        cls._savehide(user, things)

    @classmethod
    def _unvisit(cls, user, things):
        cls._unsavehide(user, things)

class _ThingSavesBySubreddit(tdb_cassandra.View):
    @classmethod
    def _rowkey(cls, user, thing):
        return user._id36

    @classmethod
    def _column(cls, user, thing):
        return {utils.to36(thing.sr_id): ''}

    @classmethod
    def get_saved_values(cls, user):
        rowkey = cls._rowkey(user, None)
        try:
            columns = cls._cf.get(rowkey,
                                  column_count=tdb_cassandra.max_column_count)
        except NotFoundException:
            return []

        return columns.keys()

    @classmethod
    def get_saved_subreddits(cls, user):
        sr_id36s = cls.get_saved_values(user)
        srs = Subreddit._byID36(sr_id36s, return_dict=False, data=True)
        return sorted([sr.name for sr in srs])

    @classmethod
    def create(cls, user, things, **kw):
        for thing in things:
            rowkey = cls._rowkey(user, thing)
            column = cls._column(user, thing)
            cls._set_values(rowkey, column)

    @classmethod
    def _check_empty(cls, user, sr_id):
        return False

    @classmethod
    def destroy(cls, user, things, **kw):
        # See if thing's sr is present anymore
        sr_ids = set([thing.sr_id for thing in things])
        for sr_id in set(sr_ids):
            if cls._check_empty(user, sr_id):
                cls._cf.remove(user._id36, [utils.to36(sr_id)])

class _ThingSavesByCategory(_ThingSavesBySubreddit):
    @classmethod
    def create(cls, user, things, category=None):
        if not category:
            return
        for thing in things:
            rowkey = cls._rowkey(user, thing)
            column = {category: None}
            cls._set_values(rowkey, column)

    @classmethod
    def _get_query_fn():
        raise NotImplementedError

    @classmethod
    def _check_empty(cls, user, category):
        from r2.lib.db import queries
        q = cls._get_query_fn()(user, 'none', category)
        q.fetch()
        return not q.data

    @classmethod
    def get_saved_categories(cls, user):
        return cls.get_saved_values(user)

    @classmethod
    def destroy(cls, user, things, categories=None):
        if not categories:
            return
        for category in set(categories.values()):
            if not category or not cls._check_empty(user, category):
                continue
            cls._cf.remove(user._id36, [category])

@view_of(LinkSavesByAccount)
class LinkSavesByCategory(_ThingSavesByCategory):
    _use_db = True

    @classmethod
    def _get_query_fn(cls):
        from r2.lib.db import queries
        return queries.get_categorized_saved_links

@view_of(LinkSavesByAccount)
class LinkSavesBySubreddit(_ThingSavesBySubreddit):
    _use_db = True

    @classmethod
    def _check_empty(cls, user, sr_id):
        from r2.lib.db import queries
        q = queries.get_saved_links(user, sr_id)
        q.fetch()
        return not q.data


@view_of(CommentSavesByAccount)
class CommentSavesBySubreddit(_ThingSavesBySubreddit):
    _use_db = True

    @classmethod
    def _check_empty(cls, user, sr_id):
        from r2.lib.db import queries
        q = queries.get_saved_comments(user, sr_id)
        q.fetch()
        return not q.data

@view_of(CommentSavesByAccount)
class CommentSavesByCategory(_ThingSavesByCategory):
    _use_db = True

    @classmethod
    def _get_query_fn(cls):
        from r2.lib.db import queries
        return queries.get_categorized_saved_comments

class LinksByImage(tdb_cassandra.View):
    _use_db = True

    # If a popular site uses the same oembed image everywhere (*cough* reddit),
    # we may have a shitton of links pointing to the same image.
    _fetch_all_columns = True

    _extra_schema_creation_args = {
        'key_validation_class': ASCII_TYPE,
    }

    @classmethod
    def _rowkey(cls, image_uid):
        return image_uid

    @classmethod
    def add_link(cls, image_uid, link):
        rowkey = cls._rowkey(image_uid)
        column = {link._id36: ''}
        cls._set_values(rowkey, column)

    @classmethod
    def remove_link(cls, image_uid, link):
        """A weakly-guaranteed removal of the record tying a Link to an image."""
        rowkey = cls._rowkey(image_uid)
        columns = (link._id36,)
        cls._remove(rowkey, columns)

    @classmethod
    def get_link_id36s(cls, image_uid):
        rowkey = cls._rowkey(image_uid)
        try:
            columns = cls._byID(rowkey)._values()
        except NotFoundException:
            return []
        return columns.iterkeys()


_CommentInbox = Relation(Account, Comment)
_CommentInbox._defaults = {
    "new": True,
}
_CommentInbox._cache = g.thingcache
_CommentInbox._cache_prefix = classmethod(lambda cls: "inboxcomment:")


_MessageInbox = Relation(Account, Message)
_MessageInbox._defaults = {
    "new": True,
}
_MessageInbox._cache = g.thingcache
_MessageInbox._cache_prefix = classmethod(lambda cls: "inboxmessage:")


class Inbox(MultiRelation('inbox', _CommentInbox, _MessageInbox)):
    @classmethod
    def _add(cls, to, obj, name, orangered=True):
        if isinstance(obj, Comment):
            assert name in ("inbox", "selfreply", "mention")
        elif isinstance(obj, Message):
            assert name == "inbox"

        # don't orangered (ever!) for enemies
        if obj.author_id in to.enemies:
            orangered = False
        # don't get an orangered for messaging yourself.
        elif to._id == obj.author_id:
            orangered = False

        i = Inbox(to, obj, name)
        i._commit()

        if orangered:
            to._incr('inbox_count', 1)

        return i

    @classmethod
    def possible_recipients(cls, obj):
        """Determine all possible recipients of Inboxes for this object.
           `obj` may be one of (Comment, Message).
        """

        possible_recipients = []
        if isinstance(obj, Comment):
            # Item is a comment. Eligible types of inboxes: mentions,
            # selfreply (which can exist on all posts if sendreplies=True),
            # inbox (which is a comment reply)

            parent_id = getattr(obj, 'parent_id', None)
            if parent_id:
                # Comment reply
                parent_comment = Comment._byID(parent_id, data=True)
                possible_recipients.append(parent_comment.author_id)
            else:
                # Selfreply
                # Do not check sendreplies, as they may have flagged it off
                # between when the comment was created and when we are checking
                parent_link = Link._byID(obj.link_id, data=True)
                possible_recipients.append(parent_link.author_id)

            mentions = utils.extract_user_mentions(obj.body)
            if len(mentions) <= g.butler_max_mentions:
                possible_recipients.extend(Account._names_to_ids(
                    mentions,
                    ignore_missing=True,
                ))
        elif isinstance(obj, Message):
            if obj.to_id:
                possible_recipients.append(obj.to_id)
        else:
            g.log.warning("Unknown object type for recipients: %r", obj)

        return possible_recipients


    @classmethod
    def get_rels(cls, user, things):
        things = tup(things)
        messages = [t for t in things if isinstance(t, Message)]
        comments = [t for t in things if isinstance(t, Comment)]

        res = {}

        if messages:
            inbox_rel_cls = cls.rel(Account, Message)
            message_res = inbox_rel_cls._fast_query(
                thing1s=user,
                thing2s=messages,
                name="inbox",
            )
            res.update(message_res)

        if comments:
            inbox_rel_cls = cls.rel(Account, Comment)
            comment_res = inbox_rel_cls._fast_query(
                thing1s=user,
                thing2s=comments,
                name=("inbox", "selfreply", "mention"),
            )
            res.update(comment_res)

        # _fast_query returns a dict of {(t1, t2, name): rel}, with rel of None
        # if the relation doesn't exist
        inbox_rels = [inbox_rel for inbox_rel in res.itervalues() if inbox_rel]
        return inbox_rels

    @classmethod
    def set_unread(cls, inbox_rels, unread=True):
        inbox_rels = tup(inbox_rels)
        unread_count_by_user = defaultdict(int)
        for inbox_rel in inbox_rels:
            if inbox_rel.new != unread:
                user = inbox_rel._thing1
                unread_count_by_user[user] += 1 if unread else -1
                inbox_rel.new = unread
                inbox_rel._commit()

        for user, unread_count in unread_count_by_user.iteritems():
            if unread_count == 0:
                continue

            if user.inbox_count + unread_count < 0:
                g.log.info(
                    "Inbox count for %r would be negative: %d + %d. Zeroing.",
                    user.name,
                    user.inbox_count,
                    unread_count,
                )
                g.stats.simple_event("inbox_counts.negative_total_fix")
                unread_count = -user.inbox_count

            user._incr('inbox_count', unread_count)


class ModeratorInbox(Relation(Subreddit, Message)):
    _cache = g.thingcache
    _defaults = {
        "new": True,
    }

    @classmethod
    def _cache_prefix(cls):
        return "modinbox:"

    @classmethod
    def _add(cls, sr, obj):
        i = cls(sr, obj, name="inbox")
        i._commit()
        return i

    @classmethod
    def get_rels(cls, sr, messages):
        messages = tup(messages)
        res = ModeratorInbox._fast_query(
            thing1s=sr,
            thing2s=messages,
            name="inbox",
        )
        # _fast_query returns a dict of {(t1, t2, name): rel}, with rel of None
        # if the relation doesn't exist
        inbox_rels = [inbox_rel for inbox_rel in res.itervalues() if inbox_rel]
        return inbox_rels

    @classmethod
    def set_unread(cls, inbox_rels, unread=True):
        inbox_rels = tup(inbox_rels)
        for inbox_rel in inbox_rels:
            if inbox_rel.new != unread:
                inbox_rel.new = unread
                inbox_rel._commit()


class CommentsByAccount(tdb_cassandra.DenormalizedRelation):
    _use_db = True
    _write_last_modified = False
    _views = []

    @classmethod
    def value_for(cls, thing1, thing2):
        return ''

    @classmethod
    def add_comment(cls, account, comment):
        cls.create(account, [comment])


class LinksByAccount(tdb_cassandra.DenormalizedRelation):
    _use_db = True
    _write_last_modified = False
    _views = []

    @classmethod
    def value_for(cls, thing1, thing2):
        return ''

    @classmethod
    def add_link(cls, account, link):
        cls.create(account, [link])


class MessagesByAccount(tdb_cassandra.DenormalizedRelation):
    _use_db = True
    _write_last_modified = False
    _views = []

    @classmethod
    def value_for(cls, thing1, thing2):
        return ''

    @classmethod
    def add_message(cls, account, message):
        cls.create(account, [message])


class CommentVisitsByUser(tdb_cassandra.View):
    _use_db = True
    _connection_pool = 'main'
    _read_consistency_level = tdb_cassandra.CL.ONE
    _write_consistency_level = tdb_cassandra.CL.ONE
    _ttl = timedelta(days=2)
    _compare_with = tdb_cassandra.DateType()
    _extra_schema_creation_args = {
        "key_validation_class": ASCII_TYPE,
    }
    MAX_VISITS = 10

    @classmethod
    def _rowkey(cls, user, link):
        return "%s-%s" % (user._id36, link._id36)

    @classmethod
    def get_previous_visits(cls, user, link):
        rowkey = cls._rowkey(user, link)
        try:
            columns = cls._cf.get(
                rowkey, column_count=cls.MAX_VISITS, column_reversed=True)
        except NotFoundException:
            return []
        # NOTE: dates return from pycassa are UTC but missing their timezone
        dates = [date.replace(tzinfo=pytz.UTC) for date in columns.keys()]
        return sorted(dates)

    @classmethod
    def add_visit(cls, user, link, visit_time):
        rowkey = cls._rowkey(user, link)
        column = {visit_time: ''}
        cls._set_values(rowkey, column)

    @classmethod
    def get_and_update(cls, user, link, visit_time):
        visits = cls.get_previous_visits(user, link)
        if visits:
            previous_visit = visits[-1]
            time_since_previous = visit_time - previous_visit

            if time_since_previous.total_seconds() <= g.comment_visits_period:
                visits.pop()
                return visits

        cls.add_visit(user, link, visit_time)
        return visits
