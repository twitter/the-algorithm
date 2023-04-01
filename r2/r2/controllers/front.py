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

from pylons.i18n import _, ungettext
from r2.controllers.reddit_base import (
    base_listing,
    disable_subreddit_css,
    paginated_listing,
    RedditController,
    require_https,
)
from r2 import config
from r2.models import *
from r2.models.recommend import ExploreSettings
from r2.config import feature
from r2.config.extensions import is_api, API_TYPES, RSS_TYPES
from r2.lib import hooks, recommender, embeds, pages
from r2.lib.pages import *
from r2.lib.pages.things import hot_links_by_url_listing
from r2.lib.pages import trafficpages
from r2.lib.menus import *
from r2.lib.csrf import csrf_exempt
from r2.lib.utils import to36, sanitize_url, title_to_url
from r2.lib.utils import query_string, UrlParser, url_links_builder
from r2.lib.template_helpers import get_domain
from r2.lib.filters import unsafe, _force_unicode, _force_utf8
from r2.lib.emailer import Email, generate_notification_email_unsubscribe_token
from r2.lib.db.operators import desc
from r2.lib.db import queries
from r2.lib.db.tdb_cassandra import MultiColumnQuery
from r2.lib.strings import strings
from r2.lib.validator import *
from r2.lib import jsontemplates
import r2.lib.db.thing as thing
from r2.lib.errors import errors, ForbiddenError
from listingcontroller import ListingController
from oauth2 import require_oauth2_scope
from api_docs import api_doc, api_section

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g

from r2.models.token import EmailVerificationToken
from r2.controllers.ipn import generate_blob, validate_blob, GoldException

from operator import attrgetter
import string
import random as rand
import re
from urllib import quote_plus

class FrontController(RedditController):

    allow_stylesheets = True

    @validate(link=VLink('link_id'))
    def GET_link_id_redirect(self, link):
        if not link:
            abort(404)
        elif not link.subreddit_slow.can_view(c.user):
            # don't disclose the subreddit/title of a post via the redirect url
            abort(403)
        else:
            redirect_url = link.make_permalink_slow(force_domain=True)

        query_params = dict(request.GET)
        if query_params:
            url = UrlParser(redirect_url)
            url.update_query(**query_params)
            redirect_url = url.unparse()

        return self.redirect(redirect_url, code=301)

    @validate(article=VLink('article'),
              comment=VCommentID('comment'))
    def GET_oldinfo(self, article, type, dest, rest=None, comment=''):
        """Legacy: supporting permalink pages from '06,
           and non-search-engine-friendly links"""
        if not (dest in ('comments','details')):
                dest = 'comments'
        if type == 'ancient':
            #this could go in config, but it should never change
            max_link_id = 10000000
            new_id = max_link_id - int(article._id)
            return self.redirect('/info/' + to36(new_id) + '/' + rest)
        if type == 'old':
            if not article.subreddit_slow.can_view(c.user):
                self.abort403()

            new_url = "/%s/%s/%s" % \
                      (dest, article._id36,
                       quote_plus(title_to_url(article.title).encode('utf-8')))
            if not c.default_sr:
                new_url = "/r/%s%s" % (c.site.name, new_url)
            if comment:
                new_url = new_url + "/%s" % comment._id36
            if c.extension:
                new_url = new_url + "/.%s" % c.extension

            new_url = new_url + query_string(request.GET)

            # redirect should be smarter and handle extensions, etc.
            return self.redirect(new_url, code=301)

    @require_oauth2_scope("read")
    @api_doc(api_section.listings, uses_site=True)
    def GET_random(self):
        """The Serendipity button"""
        sort = rand.choice(('new','hot'))
        q = c.site.get_links(sort, 'all')
        if isinstance(q, thing.Query):
            q._limit = g.num_serendipity
            names = [link._fullname for link in q]
        else:
            names = list(q)[:g.num_serendipity]

        rand.shuffle(names)

        def keep_fn(item):
            return (
                item.fresh and
                item.keep_item(item) and
                item.subreddit.discoverable
            )

        builder = IDBuilder(names, skip=True, keep_fn=keep_fn, num=1)
        links, first, last, before, after = builder.get_items()

        if links:
            redirect_url = links[0].make_permalink_slow(force_domain=True)
            return self.redirect(redirect_url)
        else:
            return self.redirect(add_sr('/'))

    @disable_subreddit_css()
    @validate(
        VAdmin(),
        thing=VByName('article'),
        oldid36=nop('article'),
        after=nop('after'),
        before=nop('before'),
        count=VCount('count'),
        listing_only=VBoolean('listing_only'),
    )
    def GET_details(self, thing, oldid36, after, before, count, listing_only):
        """The (now deprecated) details page.  Content on this page
        has been subsubmed by the presence of the LinkInfoBar on the
        rightbox, so it is only useful for Admin-only wizardry."""
        if not thing:
            try:
                link = Link._byID36(oldid36)
                return self.redirect('/details/' + link._fullname)
            except (NotFound, ValueError):
                abort(404)

        kw = {
            'count': count,
            'listing_only': listing_only,
        }
        if before:
            kw['after'] = before
            kw['reverse'] = True
        else:
            kw['after'] = after
            kw['reverse'] = False
        c.referrer_policy = "always"
        page = DetailsPage(thing=thing, expand_children=False, **kw)
        if listing_only:
            return page.details.listing.listing().render()
        return page.render()

    @validate(VUser())
    def GET_explore(self):
        settings = ExploreSettings.for_user(c.user)
        recs = recommender.get_recommended_content_for_user(c.user,
                                                            settings,
                                                            record_views=True)
        content = ExploreItemListing(recs, settings)
        return BoringPage(_("explore"),
                          show_sidebar=True,
                          show_chooser=True,
                          page_classes=['explore-page'],
                          content=content).render()

    @validate(article=VLink('article'))
    def GET_shirt(self, article):
        if not can_view_link_comments(article):
            abort(403, 'forbidden')
        return self.abort404()

    @require_oauth2_scope("read")
    @validate(article=VLink('article',
                  docs={"article": "ID36 of a link"}),
              comment=VCommentID('comment',
                  docs={"comment": "(optional) ID36 of a comment"}),
              context=VInt('context', min=0, max=8),
              sort=VOneOf('sort', CommentSortMenu._options),
              limit=VInt('limit',
                  docs={"limit": "(optional) an integer"}),
              depth=VInt('depth',
                  docs={"depth": "(optional) an integer"}),
              showedits=VBoolean("showedits", default=True),
              showmore=VBoolean("showmore", default=True),
              sr_detail=VBoolean(
                  "sr_detail", docs={"sr_detail": "(optional) expand subreddits"}),
              )
    @api_doc(api_section.listings,
             uri='/comments/{article}',
             uses_site=True,
             supports_rss=True)
    def GET_comments(
        self, article, comment, context, sort, limit, depth,
            showedits=True, showmore=True, sr_detail=False):
        """Get the comment tree for a given Link `article`.

        If supplied, `comment` is the ID36 of a comment in the comment tree for
        `article`. This comment will be the (highlighted) focal point of the
        returned view and `context` will be the number of parents shown.

        `depth` is the maximum depth of subtrees in the thread.

        `limit` is the maximum number of comments to return.

        See also: [/api/morechildren](#GET_api_morechildren) and
        [/api/comment](#POST_api_comment).

        """
        if not sort:
            sort = c.user.pref_default_comment_sort

            # hot sort no longer exists but might still be set as a preference
            if sort == "hot":
                sort = "confidence"

        if comment and comment.link_id != article._id:
            return self.abort404()

        sr = Subreddit._byID(article.sr_id, True)

        if sr.name == g.takedown_sr:
            request.environ['REDDIT_TAKEDOWN'] = article._fullname
            return self.abort404()

        if not c.default_sr and c.site._id != sr._id:
            return self.abort404()

        if not can_view_link_comments(article):
            abort(403, 'forbidden')

        # check over 18
        if (
            article.is_nsfw and
            not c.over18 and
            c.render_style == 'html' and
            not request.parsed_agent.bot
        ):
            return self.intermediate_redirect("/over18", sr_path=False)

        canonical_link = article.make_canonical_link(sr)

        # Determine if we should show the embed link for comments
        c.can_embed = bool(comment) and article.is_embeddable

        is_embed = embeds.prepare_embed_request()
        if is_embed and comment:
            embeds.set_up_comment_embed(sr, comment, showedits=showedits)

        # Temporary hook until IAMA app "OP filter" is moved from partners
        # Not to be open-sourced
        page = hooks.get_hook("comments_page.override").call_until_return(
            controller=self,
            article=article,
            limit=limit,
        )
        if page:
            return page

        # If there is a focal comment, communicate down to
        # comment_skeleton.html who that will be. Also, skip
        # comment_visits check
        previous_visits = None
        if comment:
            c.focal_comment = comment._id36
        elif (c.user_is_loggedin and
                (c.user.gold or sr.is_moderator(c.user)) and
                c.user.pref_highlight_new_comments):
            timer = g.stats.get_timer("gold.comment_visits")
            timer.start()
            previous_visits = CommentVisitsByUser.get_and_update(
                c.user, article, c.start_time)
            timer.stop()

        # check if we just came from the submit page
        infotext = None
        infotext_class = None
        infotext_show_icon = False
        if request.GET.get('already_submitted'):
            submit_url = request.GET.get('submit_url') or article.url
            submit_title = request.GET.get('submit_title') or ""
            resubmit_url = Link.resubmit_link(submit_url, submit_title)
            if c.user_is_loggedin and c.site.can_submit(c.user):
                resubmit_url = add_sr(resubmit_url)
            infotext = strings.already_submitted % resubmit_url
        elif article.archived_slow:
            infotext = strings.archived_post_message
            infotext_class = 'archived-infobar'
            infotext_show_icon = True
        elif article.locked:
            infotext = strings.locked_post_message
            infotext_class = 'locked-infobar'
            infotext_show_icon = True

        if not c.user.pref_num_comments:
            num = g.num_comments
        elif c.user_is_loggedin and (c.user.gold or sr.is_moderator(c.user)):
            num = min(c.user.pref_num_comments, g.max_comments_gold)
        else:
            num = min(c.user.pref_num_comments, g.max_comments)

        kw = {}
        # allow depth to be reset (I suspect I'll turn the VInt into a
        # validator on my next pass of .compact)
        if depth is not None and 0 < depth < MAX_RECURSION:
            kw['max_depth'] = depth
        elif c.render_style == "compact":
            kw['max_depth'] = 5

        kw["edits_visible"] = showedits
        kw["load_more"] = kw["continue_this_thread"] = showmore
        kw["show_deleted"] = embeds.is_embed()

        displayPane = PaneStack()

        # allow the user's total count preferences to be overwritten
        # (think of .embed as the use case together with depth=1)

        if limit and limit > 0:
            num = limit

        if c.user_is_loggedin and (c.user.gold or sr.is_moderator(c.user)):
            if num > g.max_comments_gold:
                displayPane.append(InfoBar(message =
                                           strings.over_comment_limit_gold
                                           % max(0, g.max_comments_gold)))
                num = g.max_comments_gold
        elif num > g.max_comments:
            if limit:
                displayPane.append(InfoBar(message =
                                       strings.over_comment_limit
                                       % dict(max=max(0, g.max_comments),
                                              goldmax=max(0,
                                                   g.max_comments_gold))))
            num = g.max_comments

        page_classes = ['comments-page']

        # if permalink page, add that message first to the content
        if comment:
            displayPane.append(PermalinkMessage(article.make_permalink_slow()))
            page_classes.append('comment-permalink-page')

        displayPane.append(LinkCommentSep())

        # insert reply box only for logged in user
        if (not is_api() and
                c.user_is_loggedin and
                article.can_comment_slow(c.user)):
            # no comment box for permalinks
            display = not comment

            # show geotargeting notice only if user is able to comment
            if article.promoted:
                geotargeted, city_target = promote.is_geotargeted_promo(article)
                if geotargeted:
                    displayPane.append(GeotargetNotice(city_target=city_target))

            data_attrs = {'type': 'link', 'event-action': 'comment'}

            displayPane.append(UserText(item=article, creating=True,
                                        post_form='comment',
                                        display=display,
                                        cloneable=True,
                                        data_attrs=data_attrs))

        if previous_visits:
            displayPane.append(CommentVisitsBox(previous_visits))

        if c.site.allows_referrers:
            c.referrer_policy = "always"

        suggested_sort_active = False
        if not c.user.pref_ignore_suggested_sort:
            suggested_sort = article.sort_if_suggested()
        else:
            suggested_sort = None

        # Special override: if the suggested sort is Q&A, and a responder of
        # the thread is viewing it, we don't want to suggest to them to view
        # the thread in Q&A mode (as it hides many unanswered questions)
        if (suggested_sort == "qa" and
                c.user_is_loggedin and
                c.user._id in article.responder_ids):
            suggested_sort = None

        if article.contest_mode:
            if c.user_is_loggedin and sr.is_moderator(c.user):
                # Default to top for contest mode to make determining winners
                # easier, but allow them to override it for moderation
                # purposes.
                if 'sort' not in request.params:
                    sort = "top"
            else:
                sort = "random"
        elif suggested_sort and 'sort' not in request.params:
            sort = suggested_sort
            suggested_sort_active = True

        # finally add the comment listing
        displayPane.append(CommentPane(article, CommentSortMenu.operator(sort),
                                       comment, context, num, **kw))

        subtitle_buttons = []
        disable_comments = article.promoted and article.disable_comments

        if (c.focal_comment or
            context is not None or
            disable_comments):
            subtitle = None
        elif article.num_comments == 0:
            subtitle = _("no comments (yet)")
        elif article.num_comments <= num:
            subtitle = _("all %d comments") % article.num_comments
        else:
            subtitle = _("top %d comments") % num

            if g.max_comments > num:
                self._add_show_comments_link(subtitle_buttons, article, num,
                                             g.max_comments, gold=False)

            if (c.user_is_loggedin and
                    (c.user.gold or sr.is_moderator(c.user)) and
                    article.num_comments > g.max_comments):
                self._add_show_comments_link(subtitle_buttons, article, num,
                                             g.max_comments_gold, gold=True)

        sort_menu = CommentSortMenu(
            default=sort,
            css_class='suggested' if suggested_sort_active else '',
            suggested_sort=suggested_sort,
        )

        link_settings = LinkCommentsSettings(
            article,
            sort=sort,
            suggested_sort=suggested_sort,
        )

        # Check for click urls on promoted links
        click_url = None
        campaign_fullname = None
        if article.promoted and not article.is_self:
            campaign_fullname = request.GET.get("campaign", None)
            click_url = request.GET.get("click_url", None)
            click_hash = request.GET.get("click_hash", "")

            if (click_url and not promote.is_valid_click_url(
                    link=article,
                    click_url=click_url,
                    click_hash=click_hash)):
                click_url = None

        # event target for screenviews
        if comment:
            event_target = {
                'target_type': 'comment',
                'target_fullname': comment._fullname,
                'target_id': comment._id,
            }
        elif article.is_self:
            event_target = {
                'target_type': 'self',
                'target_fullname': article._fullname,
                'target_id': article._id,
                'target_sort': sort,
            }
        else:
            event_target = {
                'target_type': 'link',
                'target_fullname': article._fullname,
                'target_id': article._id,
                'target_url': article.url,
                'target_url_domain': article.link_domain(),
                'target_sort': sort,
            }
        extra_js_config = {'event_target': event_target}

        res = LinkInfoPage(
            link=article,
            comment=comment,
            disable_comments=disable_comments,
            content=displayPane,
            page_classes=page_classes,
            subtitle=subtitle,
            subtitle_buttons=subtitle_buttons,
            nav_menus=[sort_menu, link_settings],
            infotext=infotext,
            infotext_class=infotext_class,
            infotext_show_icon=infotext_show_icon,
            sr_detail=sr_detail,
            campaign_fullname=campaign_fullname,
            click_url=click_url,
            canonical_link=canonical_link,
            extra_js_config=extra_js_config,
        )

        return res.render()

    def _add_show_comments_link(self, array, article, num, max_comm, gold=False):
        if num == max_comm:
            return
        elif article.num_comments <= max_comm:
            link_text = _("show all %d") % article.num_comments
        else:
            link_text = _("show %d") % max_comm

        limit_param = "?limit=%d" % max_comm

        if gold:
            link_class = "gold"
        else:
            link_class = ""

        more_link = article.make_permalink_slow() + limit_param
        array.append( (link_text, more_link, link_class) )

    @validate(VUser(),
              name=nop('name'))
    def GET_newreddit(self, name):
        """Create a subreddit form"""
        VNotInTimeout().run(action_name="pageview", details_text="newreddit")
        title = _('create a subreddit')
        captcha = Captcha() if c.user.needs_captcha() else None
        content = CreateSubreddit(name=name or '', captcha=captcha)
        res = FormPage(_("create a subreddit"),
                       content=content,
                       captcha=captcha,
                       ).render()
        return res

    @require_oauth2_scope("modconfig")
    @api_doc(api_section.moderation, uses_site=True)
    def GET_stylesheet(self):
        """Redirect to the subreddit's stylesheet if one exists.

        See also: [/api/subreddit_stylesheet](#POST_api_subreddit_stylesheet).

        """
        # de-stale the subreddit object so we don't poison downstream caches
        if not isinstance(c.site, FakeSubreddit):
            c.site = Subreddit._byID(c.site._id, data=True, stale=False)

        url = Reddit.get_subreddit_stylesheet_url(c.site)
        if url:
            return self.redirect(url)
        else:
            self.abort404()

    def GET_share_close(self):
        """Render a page that closes itself.

        Intended for use as a redirect target for facebook sharing.
        """
        return ShareClose().render()

    def _make_moderationlog(self, srs, num, after, reverse, count, mod=None, action=None):
        query = Subreddit.get_modactions(srs, mod=mod, action=action)
        builder = ModActionBuilder(
            query, num=num, after=after, count=count, reverse=reverse,
            wrap=default_thing_wrapper())
        listing = ModActionListing(builder)
        pane = listing.listing()
        return pane

    modname_splitter = re.compile('[ ,]+')

    @require_oauth2_scope("modlog")
    @disable_subreddit_css()
    @paginated_listing(max_page_size=500, backend='cassandra')
    @validate(
        mod=nop('mod', docs={"mod": "(optional) a moderator filter"}),
        action=VOneOf('type', ModAction.actions),
    )
    @api_doc(api_section.moderation, uses_site=True,
             uri="/about/log", supports_rss=True)
    def GET_moderationlog(self, num, after, reverse, count, mod, action):
        """Get a list of recent moderation actions.

        Moderator actions taken within a subreddit are logged. This listing is
        a view of that log with various filters to aid in analyzing the
        information.

        The optional `mod` parameter can be a comma-delimited list of moderator
        names to restrict the results to, or the string `a` to restrict the
        results to admin actions taken within the subreddit.

        The `type` parameter is optional and if sent limits the log entries
        returned to only those of the type specified.

        """
        if not c.user_is_loggedin or not (c.user_is_admin or
                                          c.site.is_moderator(c.user)):
            return self.abort404()

        VNotInTimeout().run(action_name="pageview", details_text="modlog")
        if mod:
            if mod == 'a':
                modnames = g.admins
            else:
                modnames = self.modname_splitter.split(mod)
            mod = []
            for name in modnames:
                try:
                    mod.append(Account._by_name(name, allow_deleted=True))
                except NotFound:
                    continue
            mod = mod or None

        if isinstance(c.site, (MultiReddit, ModSR)):
            srs = Subreddit._byID(c.site.sr_ids, return_dict=False)

            # grab all moderators
            mod_ids = set(Subreddit.get_all_mod_ids(srs))
            mods = Account._byID(mod_ids, data=True)

            pane = self._make_moderationlog(srs, num, after, reverse, count,
                                            mod=mod, action=action)
        elif isinstance(c.site, FakeSubreddit):
            return self.abort404()
        else:
            mod_ids = c.site.moderators
            mods = Account._byID(mod_ids, data=True)

            pane = self._make_moderationlog(c.site, num, after, reverse, count,
                                            mod=mod, action=action)

        panes = PaneStack()
        panes.append(pane)

        action_buttons = [QueryButton(_('all'), None, query_param='type',
                                      css_class='primary')]
        for a in ModAction.actions:
            button = QueryButton(ModAction._menu[a], a, query_param='type')
            action_buttons.append(button)

        mod_buttons = [QueryButton(_('all'), None, query_param='mod',
                                   css_class='primary')]
        for mod_id in mod_ids:
            mod = mods[mod_id]
            mod_buttons.append(QueryButton(mod.name, mod.name,
                                           query_param='mod'))
        # add a choice for the automoderator account if it's not a mod
        if (g.automoderator_account and
                all(mod.name != g.automoderator_account
                    for mod in mods.values())):
            automod_button = QueryButton(
                g.automoderator_account,
                g.automoderator_account,
                query_param="mod",
            )
            mod_buttons.append(automod_button)
        mod_buttons.append(QueryButton(_('admins*'), 'a', query_param='mod'))
        base_path = request.path
        menus = [NavMenu(action_buttons, base_path=base_path,
                         title=_('filter by action'), type='lightdrop', css_class='modaction-drop'),
                NavMenu(mod_buttons, base_path=base_path,
                        title=_('filter by moderator'), type='lightdrop')]
        extension_handling = "private" if c.user.pref_private_feeds else False
        return EditReddit(content=panes,
                          nav_menus=menus,
                          location="log",
                          extension_handling=extension_handling).render()

    def _make_spamlisting(self, location, only, num, after, reverse, count):
        include_links, include_comments = True, True
        if only == 'links':
            include_comments = False
        elif only == 'comments':
            include_links = False

        if location == 'reports':
            query = c.site.get_reported(include_links=include_links,
                                        include_comments=include_comments)
        elif location == 'spam':
            query = c.site.get_spam(include_links=include_links,
                                    include_comments=include_comments)
        elif location == 'modqueue':
            query = c.site.get_modqueue(include_links=include_links,
                                        include_comments=include_comments)
        elif location == 'unmoderated':
            query = c.site.get_unmoderated()
        elif location == 'edited':
            query = c.site.get_edited(include_links=include_links,
                                      include_comments=include_comments)
        else:
            raise ValueError

        if isinstance(query, thing.Query):
            builder_cls = QueryBuilder
        elif isinstance (query, list):
            builder_cls = QueryBuilder
        else:
            builder_cls = IDBuilder

        def keep_fn(x):
            # no need to bother mods with banned users, or deleted content
            if x._deleted:
                return False
            if getattr(x,'author',None) == c.user and c.user._spam:
                return False

            if location == "reports":
                return x.reported > 0 and not x._spam
            elif location == "spam":
                return x._spam
            elif location == "modqueue":
                if x.reported > 0 and not x._spam:
                    return True # reported but not banned
                if x.author._spam and x.subreddit.exclude_banned_modqueue:
                    # banned user, don't show if subreddit pref excludes
                    return False

                verdict = getattr(x, "verdict", None)
                if verdict is None:
                    return True # anything without a verdict
                if x._spam:
                    ban_info = getattr(x, "ban_info", {})
                    if ban_info.get("auto", True):
                        return True # spam, unless banned by a moderator
                return False
            elif location == "unmoderated":
                # banned user, don't show if subreddit pref excludes
                if x.author._spam and x.subreddit.exclude_banned_modqueue:
                    return False
                if x._spam:
                    ban_info = getattr(x, "ban_info", {})
                    if ban_info.get("auto", True):
                        return True
                return not getattr(x, 'verdict', None)
            elif location == "edited":
                return bool(getattr(x, "editted", False))
            else:
                raise ValueError

        builder = builder_cls(query,
                              skip=True,
                              num=num, after=after,
                              keep_fn=keep_fn,
                              count=count, reverse=reverse,
                              wrap=ListingController.builder_wrapper,
                              spam_listing=True)
        listing = LinkListing(builder)
        pane = listing.listing()

        # Indicate that the comment tree wasn't built for comments
        for i in pane.things:
            if hasattr(i, 'body'):
                i.child = None

        return pane

    def _edit_normal_reddit(self, location, created):
        if (location == 'edit' and
                c.user_is_loggedin and
                (c.user_is_admin or
                    c.site.is_moderator_with_perms(c.user, 'config'))):
            pane = PaneStack()

            if created == 'true':
                infobar_message = strings.sr_created
                pane.append(InfoBar(message=infobar_message))

            c.allow_styles = True
            c.site = Subreddit._byID(c.site._id, data=True, stale=False)
            pane.append(CreateSubreddit(site=c.site))
        elif (location == 'stylesheet'
              and c.site.can_change_stylesheet(c.user)
              and not g.css_killswitch):
            stylesheet_contents = c.site.fetch_stylesheet_source()
            c.allow_styles = True
            pane = SubredditStylesheet(site=c.site,
                                       stylesheet_contents=stylesheet_contents)
        elif (location == 'stylesheet'
              and c.site.can_view(c.user)
              and not g.css_killswitch):
            stylesheet = c.site.fetch_stylesheet_source()
            pane = SubredditStylesheetSource(stylesheet_contents=stylesheet)
        elif (location == 'traffic' and
              (c.site.public_traffic or
               (c.user_is_loggedin and
                (c.site.is_moderator(c.user) or c.user.employee)))):
            pane = trafficpages.SubredditTraffic()
        elif (location == "about") and is_api():
            return self.redirect(add_sr('about.json'), code=301)
        else:
            return self.abort404()

        return EditReddit(content=pane,
                          location=location,
                          extension_handling=False).render()

    @require_oauth2_scope("read")
    @base_listing
    @disable_subreddit_css()
    @validate(
        VSrModerator(perms='posts'),
        location=nop('location'),
        only=VOneOf('only', ('links', 'comments')),
        timeout=VNotInTimeout(),
    )
    @api_doc(
        api_section.moderation,
        uses_site=True,
        uri='/about/{location}',
        uri_variants=['/about/' + loc for loc in
                      ('reports', 'spam', 'modqueue', 'unmoderated', 'edited')],
    )
    def GET_spamlisting(self, location, only, num, after, reverse, count,
            timeout):
        """Return a listing of posts relevant to moderators.

        * reports: Things that have been reported.
        * spam: Things that have been marked as spam or otherwise removed.
        * modqueue: Things requiring moderator review, such as reported things
            and items caught by the spam filter.
        * unmoderated: Things that have yet to be approved/removed by a mod.
        * edited: Things that have been edited recently.

        Requires the "posts" moderator permission for the subreddit.

        """
        c.allow_styles = True
        c.profilepage = True
        panes = PaneStack()

        # We clone and modify this when a user clicks 'reply' on a comment.
        replyBox = UserText(item=None, display=False, cloneable=True,
                            creating=True, post_form='comment')
        panes.append(replyBox)

        spamlisting = self._make_spamlisting(location, only, num, after,
                                             reverse, count)
        panes.append(spamlisting)

        extension_handling = "private" if c.user.pref_private_feeds else False

        if location in ('reports', 'spam', 'modqueue', 'edited'):
            buttons = [
                QueryButton(_('posts and comments'), None, query_param='only'),
                QueryButton(_('posts'), 'links', query_param='only'),
                QueryButton(_('comments'), 'comments', query_param='only'),
            ]
            menus = [NavMenu(buttons, base_path=request.path, title=_('show'),
                             type='lightdrop')]
        else:
            menus = None
        return EditReddit(content=panes,
                          location=location,
                          nav_menus=menus,
                          extension_handling=extension_handling).render()

    @base_listing
    @disable_subreddit_css()
    @validate(
        VSrModerator(perms='flair'),
        name=nop('name'),
        timeout=VNotInTimeout(),
    )
    def GET_flairlisting(self, num, after, reverse, count, name, timeout):
        user = None
        if name:
            try:
                user = Account._by_name(name)
            except NotFound:
                c.errors.add(errors.USER_DOESNT_EXIST, field='name')

        c.allow_styles = True
        pane = FlairPane(num, after, reverse, name, user)
        return EditReddit(content=pane, location='flair').render()

    @require_oauth2_scope("modconfig")
    @disable_subreddit_css()
    @validate(location=nop('location'),
              created=VOneOf('created', ('true','false'),
                             default='false'))
    @api_doc(api_section.subreddits, uri="/r/{subreddit}/about/edit")
    def GET_editreddit(self, location, created):
        """Get the current settings of a subreddit.

        In the API, this returns the current settings of the subreddit as used
        by [/api/site_admin](#POST_api_site_admin).  On the HTML site, it will
        display a form for editing the subreddit.

        """
        c.profilepage = True
        if isinstance(c.site, FakeSubreddit):
            return self.abort404()
        else:
            VNotInTimeout().run(action_name="pageview",
                details_text="editreddit_%s" % location, target=c.site)
            return self._edit_normal_reddit(location, created)

    @require_oauth2_scope("read")
    @api_doc(api_section.subreddits, uri='/r/{subreddit}/about')
    def GET_about(self):
        """Return information about the subreddit.

        Data includes the subscriber count, description, and header image."""
        if not is_api() or isinstance(c.site, FakeSubreddit):
            return self.abort404()

        # we do this here so that item.accounts_active_count is only present on
        # this one endpoint, and not all the /subreddit listings etc. since
        # looking up activity across multiple subreddits is more work.
        accounts_active_count = None
        activity = c.site.count_activity()
        if activity:
            accounts_active_count = activity.logged_in.count

        item = Wrapped(c.site, accounts_active_count=accounts_active_count)
        Subreddit.add_props(c.user, [item])
        return Reddit(content=item).render()

    @require_oauth2_scope("read")
    @api_doc(api_section.subreddits, uses_site=True)
    def GET_sidebar(self):
        """Get the sidebar for the current subreddit"""
        usertext = UserText(c.site, c.site.description)
        return Reddit(content=usertext).render()

    @require_oauth2_scope("read")
    @api_doc(api_section.subreddits, uri='/r/{subreddit}/about/rules')
    def GET_rules(self):
        """Get the rules for the current subreddit"""
        if not feature.is_enabled("subreddit_rules", subreddit=c.site.name):
            abort(404)
        if isinstance(c.site, FakeSubreddit):
            abort(404)

        kind_labels = {
            "all": _("Posts & Comments"),
            "link": _("Posts only"),
            "comment": _("Comments only"),
        }
        title_string = _("Rules for r/%(subreddit)s") % { "subreddit" : c.site.name }
        content = Rules(
            title=title_string,
            kind_labels=kind_labels,
        )
        extra_js_config = {"kind_labels": kind_labels}
        return ModToolsPage(
            title=title_string,
            content=content,
            extra_js_config=extra_js_config,
        ).render()

    @require_oauth2_scope("read")
    @api_doc(api_section.subreddits, uses_site=True)
    @validate(
        num=VInt("num",
            min=1, max=Subreddit.MAX_STICKIES, num_default=1, coerce=True),
    )
    def GET_sticky(self, num):
        """Redirect to one of the posts stickied in the current subreddit

        The "num" argument can be used to select a specific sticky, and will
        default to 1 (the top sticky) if not specified.
        Will 404 if there is not currently a sticky post in this subreddit.

        """
        if not num or not c.site.sticky_fullnames:
            abort(404)

        try:
            fullname = c.site.sticky_fullnames[num-1]
        except IndexError:
            abort(404)
        sticky = Link._by_fullname(fullname, data=True)
        self.redirect(sticky.make_permalink_slow())

    def GET_awards(self):
        """The awards page."""
        return BoringPage(_("awards"), content=UserAwards()).render()

    @base_listing
    @require_oauth2_scope("read")
    @validate(article=VLink('article'))
    def GET_related(self, num, article, after, reverse, count):
        """Related page: removed, redirects to comments page."""
        if not can_view_link_comments(article):
            abort(403, 'forbidden')

        self.redirect(article.make_permalink_slow(), code=301)

    @base_listing
    @require_oauth2_scope("read")
    @validate(article=VLink('article'))
    @api_doc(
        api_section.listings,
        uri="/duplicates/{article}",
        supports_rss=True,
    )
    def GET_duplicates(self, article, num, after, reverse, count):
        """Return a list of other submissions of the same URL"""
        if not can_view_link_comments(article):
            abort(403, 'forbidden')

        builder = url_links_builder(article.url, exclude=article._fullname,
                                    num=num, after=after, reverse=reverse,
                                    count=count)
        if after and not builder.valid_after(after):
            g.stats.event_count("listing.invalid_after", "duplicates")
            self.abort403()
        num_duplicates = len(builder.get_items()[0])
        listing = LinkListing(builder).listing()

        res = LinkInfoPage(link=article,
                           comment=None,
                           num_duplicates=num_duplicates,
                           content=listing,
                           page_classes=['other-discussions-page'],
                           subtitle=_('other discussions')).render()
        return res

    @base_listing
    @require_oauth2_scope("read")
    @validate(query=nop('q', docs={"q": "a search query"}),
              sort=VMenu('sort', SubredditSearchSortMenu, remember=False))
    @api_doc(api_section.subreddits, uri='/subreddits/search', supports_rss=True)
    def GET_search_reddits(self, query, reverse, after, count, num, sort):
        """Search subreddits by title and description."""

        # trigger redirect to /over18
        if request.GET.get('over18') == 'yes':
            u = UrlParser(request.fullurl)
            del u.query_dict['over18']
            search_url = u.unparse()
            return self.intermediate_redirect('/over18', sr_path=False,
                                              fullpath=search_url)

        # show NSFW to API and RSS users unless obey_over18=true
        is_api_or_rss = (c.render_style in API_TYPES
                         or c.render_style in RSS_TYPES)
        if is_api_or_rss:
            include_over18 = not c.obey_over18 or c.over18
        elif feature.is_enabled('safe_search'):
            include_over18 = c.over18
        else:
            include_over18 = True

        if query:
            q = g.search.SubredditSearchQuery(query, sort=sort, faceting={},
                                              include_over18=include_over18)
            content = self._search(q, num=num, reverse=reverse,
                                   after=after, count=count,
                                   skip_deleted_authors=False)
        else:
            content = None

        # event target for screenviews (/subreddits/search)
        event_target = {}
        if after:
            event_target['target_count'] = count
            if reverse:
                event_target['target_before'] = after._fullname
            else:
                event_target['target_after'] = after._fullname
        extra_js_config = {'event_target': event_target}

        res = SubredditsPage(content=content,
                             prev_search=query,
                             page_classes=['subreddits-page'],
                             extra_js_config=extra_js_config,
                             # update if we ever add sorts
                             search_params={},
                             title=_("search results"),
                             simple=True).render()
        return res

    search_help_page = "/wiki/search"
    verify_langs_regex = re.compile(r"\A[a-z][a-z](,[a-z][a-z])*\Z")

    @base_listing
    @require_oauth2_scope("read")
    @validate(query=VLength('q', max_length=512),
              sort=VMenu('sort', SearchSortMenu, remember=False),
              recent=VMenu('t', TimeMenu, remember=False),
              restrict_sr=VBoolean('restrict_sr', default=False),
              include_facets=VBoolean('include_facets', default=False),
              result_types=VResultTypes('type'),
              syntax=VOneOf('syntax', options=g.search_syntaxes))
    @api_doc(api_section.search, supports_rss=True, uses_site=True)
    def GET_search(self, query, num, reverse, after, count, sort, recent,
                   restrict_sr, include_facets, result_types, syntax, sr_detail):
        """Search links page."""
        if c.site.login_required and not c.user_is_loggedin:
            raise UserRequiredException

        # trigger redirect to /over18
        if request.GET.get('over18') == 'yes':
            u = UrlParser(request.fullurl)
            del u.query_dict['over18']
            search_url = u.unparse()
            return self.intermediate_redirect('/over18', sr_path=False,
                                              fullpath=search_url)

        if query and '.' in query:
            url = sanitize_url(query, require_scheme=True)
            if url:
                return self.redirect("/submit" + query_string({'url':url}))

        if not restrict_sr:
            site = DefaultSR()
        else:
            site = c.site

        has_query = query or not isinstance(site, (DefaultSR, AllSR))

        if not syntax:
            syntax = g.search.SearchQuery.default_syntax

        # show NSFW to API and RSS users unless obey_over18=true
        is_api_or_rss = (c.render_style in API_TYPES
                         or c.render_style in RSS_TYPES)
        if is_api_or_rss:
            include_over18 = not c.obey_over18 or c.over18
        elif feature.is_enabled('safe_search'):
            include_over18 = c.over18
        else:
            include_over18 = True

        # do not request facets--they are not popular with users and result in
        # looking up unpopular subreddits (which is bad for site performance)
        faceting = {}

        # no subreddit results if fielded search or structured syntax
        if syntax == 'cloudsearch' or (query and ':' in query):
            result_types = result_types - {'sr'}

        # combined results on first page only
        if not after and not restrict_sr and result_types == {'link', 'sr'}:
            # hardcoded to 3 subreddits (or fewer)
            sr_num = min(3, int(num / 3))
            num = num - sr_num
        elif result_types == {'sr'}:
            sr_num = num
            num = 0
        else:
            sr_num = 0

        content = None
        subreddits = None
        nav_menus = None
        cleanup_message = None
        converted_data = None
        subreddit_facets = None
        legacy_render_class = feature.is_enabled('legacy_search') or c.user.pref_legacy_search

        if num > 0 and has_query:
            nav_menus = [SearchSortMenu(default=sort), TimeMenu(default=recent)]
            try:
                q = g.search.SearchQuery(query, site, sort=sort,
                                         faceting=faceting,
                                         include_over18=include_over18,
                                         recent=recent, syntax=syntax)
                content = self._search(q, num=num, after=after, reverse=reverse,
                                       count=count, sr_detail=sr_detail,
                                       heading=_('posts'), nav_menus=nav_menus,
                                       legacy_render_class=legacy_render_class)
                converted_data = q.converted_data
                subreddit_facets = content.subreddit_facets

            except g.search.InvalidQuery:
                g.stats.simple_event('cloudsearch.error.invalidquery')

                # Clean the search of characters that might be causing the
                # InvalidQuery exception. If the cleaned search boils down
                # to an empty string, the search code is expected to bail
                # out early with an empty result set.
                cleaned = re.sub("[^\w\s]+", " ", query)
                cleaned = cleaned.lower().strip()

                q = g.search.SearchQuery(cleaned, site, sort=sort,
                                         faceting=faceting,
                                         include_over18=include_over18,
                                         recent=recent)
                content = self._search(q, num=num, after=after, reverse=reverse,
                                       count=count, heading=_('posts'), nav_menus=nav_menus,
                                       legacy_render_class=legacy_render_class)
                converted_data = q.converted_data
                subreddit_facets = content.subreddit_facets

                if cleaned:
                    cleanup_message = strings.invalid_search_query % {
                                                        "clean_query": cleaned
                                                                      }
                    cleanup_message += " "
                    cleanup_message += strings.search_help % {
                                          "search_help": self.search_help_page
                                                              }
                else:
                    cleanup_message = strings.completely_invalid_search_query

        # extra search request for subreddit results
        if sr_num > 0 and has_query:
            sr_q = g.search.SubredditSearchQuery(query, sort='relevance',
                                                 faceting={},
                                                 include_over18=include_over18)
            subreddits = self._search(sr_q, num=sr_num, reverse=reverse,
                                      after=after, count=count, type='sr',
                                      skip_deleted_authors=False, heading=_('subreddits'),
                                      legacy_render_class=legacy_render_class)

            # backfill with facets if no subreddit search results
            if subreddit_facets and not subreddits.things:
                names = [sr._fullname for sr, count in subreddit_facets]
                builder = IDBuilder(names, num=sr_num)
                listing = SearchListing(builder, nextprev=False)
                subreddits = listing.listing(
                    legacy_render_class=legacy_render_class)

            # ensure response is not list for subreddit only result type
            if is_api() and not content:
                content = subreddits
                subreddits = None

        # event target for screenviews (/search)
        event_target = {
            'target_sort': sort,
            'target_filter_time': recent,
        }
        if after:
            event_target['target_count'] = count
            if reverse:
                event_target['target_before'] = after._fullname
            else:
                event_target['target_after'] = after._fullname
        extra_js_config = {'event_target': event_target}

        res = SearchPage(_('search results'), query,
                         content=content,
                         subreddits=subreddits,
                         nav_menus=nav_menus,
                         search_params=dict(sort=sort, t=recent),
                         infotext=cleanup_message,
                         simple=False, site=c.site,
                         restrict_sr=restrict_sr,
                         syntax=syntax,
                         converted_data=converted_data,
                         facets=subreddit_facets,
                         sort=sort,
                         recent=recent,
                         extra_js_config=extra_js_config,
                         ).render()

        return res

    def _search_builder_wrapper(self, q):
        query = q.query
        recent = str(q.recent) if q.recent else None
        sort = q.sort
        def wrapper_fn(thing):
            w = Wrapped(thing)
            w.prev_search = query
            w.recent = recent
            w.sort = sort

            if isinstance(thing, Link):
                w.render_class = SearchResultLink
            elif isinstance(thing, Subreddit):
                w.render_class = SearchResultSubreddit
            return w
        return wrapper_fn

    def _legacy_search_builder_wrapper(self):
        default_wrapper = default_thing_wrapper()
        def wrapper_fn(thing):
            w = default_wrapper(thing)
            if isinstance(thing, Link):
                w.render_class = LegacySearchResultLink
            return w
        return wrapper_fn

    def _search(self, query_obj, num, after, reverse, count=0, type=None,
                skip_deleted_authors=True, sr_detail=False,
                heading=None, nav_menus=None, legacy_render_class=True):
        """Helper function for interfacing with search.  Basically a
           thin wrapper for SearchBuilder."""

        if legacy_render_class:
            builder_wrapper = self._legacy_search_builder_wrapper()
        else:
            builder_wrapper = self._search_builder_wrapper(query_obj)

        builder = SearchBuilder(query_obj,
                                after=after, num=num, reverse=reverse,
                                count=count,
                                wrap=builder_wrapper,
                                skip_deleted_authors=skip_deleted_authors,
                                sr_detail=sr_detail)
        if after and not builder.valid_after(after):
            g.stats.event_count("listing.invalid_after", "search")
            self.abort403()

        params = request.GET.copy()
        if type:
            params['type'] = type

        listing = SearchListing(builder, show_nums=True, params=params,
                                heading=heading, nav_menus=nav_menus)

        try:
            res = listing.listing(legacy_render_class)
        except g.search.SearchException as e:
            return self.search_fail(e)

        return res

    @validate(VAdmin(),
              comment=VCommentByID('comment_id'))
    def GET_comment_by_id(self, comment):
        href = comment.make_permalink_slow(context=5, anchor=True)
        return self.redirect(href)

    @validate(url=VRequired('url', None),
              title=VRequired('title', None),
              text=VRequired('text', None),
              selftext=VRequired('selftext', None))
    def GET_submit(self, url, title, text, selftext):
        """Submit form."""
        resubmit = request.GET.get('resubmit')
        url = sanitize_url(url)

        if url and not resubmit:
            # check to see if the url has already been submitted

            def keep_fn(item):
                # skip promoted links
                would_keep = item.keep_item(item)
                return would_keep and getattr(item, "promoted", None) is None

            listing = hot_links_by_url_listing(
                url, sr=c.site, num=100, skip=True, keep_fn=keep_fn)
            links = listing.things

            if links and len(links) == 1:
                # redirect the user to the existing link's comments
                existing_submission_url = links[0].already_submitted_link(
                    url, title)
                return self.redirect(existing_submission_url)
            elif links:
                # show the user a listing of all the other links with this url
                # an infotext to resubmit it
                resubmit_url = Link.resubmit_link(url, title)
                sr_resubmit_url = add_sr(resubmit_url)
                infotext = strings.multiple_submitted % sr_resubmit_url
                res = BoringPage(
                    _("seen it"), content=listing, infotext=infotext).render()
                return res

        if not c.user_is_loggedin:
            raise UserRequiredException

        if not (c.default_sr or c.site.can_submit(c.user)):
            abort(403, "forbidden")

        target = c.site if not isinstance(c.site, FakeSubreddit) else None
        VNotInTimeout().run(action_name="pageview", details_text="submit",
            target=target)

        captcha = Captcha() if c.user.needs_captcha() else None

        extra_subreddits = []
        if isinstance(c.site, MultiReddit):
            extra_subreddits.append((
                _('%s subreddits') % c.site.name,
                c.site.srs
            ))

        newlink = NewLink(
            url=url or '',
            title=title or '',
            text=text or '',
            selftext=selftext or '',
            captcha=captcha,
            resubmit=resubmit,
            default_sr=c.site if not c.default_sr else None,
            extra_subreddits=extra_subreddits,
            show_link=c.default_sr or c.site.can_submit_link(c.user),
            show_self=((c.default_sr or c.site.can_submit_text(c.user))
                      and not request.GET.get('no_self')),
        )

        return FormPage(_("submit"),
                        show_sidebar=True,
                        page_classes=['submit-page'],
                        content=newlink).render()

    def GET_catchall(self):
        return self.abort404()

    @require_oauth2_scope("modtraffic")
    @validate(VSponsor('link'),
              link=VLink('link'),
              campaign=VPromoCampaign('campaign'),
              before=VDate('before', format='%Y%m%d%H'),
              after=VDate('after', format='%Y%m%d%H'))
    def GET_traffic(self, link, campaign, before, after):
        if link and campaign and link._id != campaign.link_id:
            return self.abort404()

        if c.render_style == 'csv':
            return trafficpages.PromotedLinkTraffic.as_csv(campaign or link)

        content = trafficpages.PromotedLinkTraffic(link, campaign, before,
                                                   after)
        return LinkInfoPage(link=link,
                            page_classes=["promoted-traffic"],
                            show_sidebar=False, comment=None,
                            show_promote_button=True, content=content).render()

    @validate(VEmployee())
    def GET_site_traffic(self):
        return trafficpages.SitewideTrafficPage().render()

    @validate(VEmployee())
    def GET_lang_traffic(self, langcode):
        return trafficpages.LanguageTrafficPage(langcode).render()

    @validate(VEmployee())
    def GET_advert_traffic(self, code):
        return trafficpages.AdvertTrafficPage(code).render()

    @validate(VEmployee())
    def GET_subreddit_traffic_report(self):
        content = trafficpages.SubredditTrafficReport()

        if c.render_style == 'csv':
            return content.as_csv()
        return trafficpages.TrafficPage(content=content).render()

    @validate(VUser())
    def GET_account_activity(self):
        return AccountActivityPage().render()

    def GET_contact_us(self):
        return BoringPage(_("contact us"), show_sidebar=False,
                          content=ContactUs(), page_classes=["contact-us-page"]
                          ).render()

    @validate(vendor=VOneOf("v", ("claimed-gold", "claimed-creddits",
                                  "spent-creddits", "paypal", "coinbase",
                                  "stripe"),
                            default="claimed-gold"))
    def GET_goldthanks(self, vendor):
        vendor_url = None
        lounge_md = None

        if vendor == "claimed-gold":
            claim_msg = _("Claimed! Enjoy your reddit gold membership.")
            if g.lounge_reddit:
                lounge_md = strings.lounge_msg
        elif vendor == "claimed-creddits":
            claim_msg = _("Your gold creddits have been claimed! Now go to "
                          "someone's userpage and give them a present!")
        elif vendor == "spent-creddits":
            claim_msg = _("Thanks for buying reddit gold! Your transaction "
                          "has been completed.")
        elif vendor == "paypal":
            claim_msg = _("Thanks for buying reddit gold! Your transaction "
                          "has been completed and emailed to you. You can "
                          "check the details by signing into your account "
                          "at:")
            vendor_url = "https://www.paypal.com/us"
        elif vendor in {"coinbase", "stripe"}:  # Pending vendors
            claim_msg = _("Thanks for buying reddit gold! Your transaction is "
                          "being processed. If you have any questions please "
                          "email us at %(gold_email)s")
            claim_msg = claim_msg % {'gold_email': g.goldsupport_email}
        else:
            abort(404)

        return BoringPage(_("thanks"), show_sidebar=False,
                          content=GoldThanks(claim_msg=claim_msg,
                                             vendor_url=vendor_url,
                                             lounge_md=lounge_md),
                          page_classes=["gold-page-ga-tracking"]
                         ).render()

    @validate(VUser(),
              token=VOneTimeToken(AwardClaimToken, "code"))
    def GET_confirm_award_claim(self, token):
        if not token:
            abort(403)

        award = Award._by_fullname(token.awardfullname)
        trophy = FakeTrophy(c.user, award, token.description, token.url)
        content = ConfirmAwardClaim(trophy=trophy, user=c.user.name,
                                    token=token)
        return BoringPage(_("claim this award?"), content=content).render()

    @validate(VUser(),
              VModhash(),
              token=VOneTimeToken(AwardClaimToken, "code"))
    def POST_claim_award(self, token):
        if not token:
            abort(403)

        token.consume()

        award = Award._by_fullname(token.awardfullname)
        trophy, preexisting = Trophy.claim(c.user, token.uid, award,
                                           token.description, token.url)
        redirect = '/awards/received?trophy=' + trophy._id36
        if preexisting:
            redirect += '&duplicate=true'
        self.redirect(redirect)

    @validate(trophy=VTrophy('trophy'),
              preexisting=VBoolean('duplicate'))
    def GET_received_award(self, trophy, preexisting):
        content = AwardReceived(trophy=trophy, preexisting=preexisting)
        return BoringPage(_("award claim"), content=content).render()

    def GET_gilding(self):
        return BoringPage(
            _("gilding"),
            show_sidebar=False,
            content=Gilding(),
            page_classes=["gold-page", "gilding"],
        ).render()

    @csrf_exempt
    @validate(dest=VDestination(default='/'))
    def _modify_hsts_grant(self, dest):
        """Endpoint subdomains can redirect through to update HSTS grants."""
        # TODO: remove this once it stops getting hit
        from r2.lib.base import abort
        require_https()
        if request.host != g.domain:
            abort(ForbiddenError(errors.WRONG_DOMAIN))

        # We can't send the user back to http: if they're forcing HTTPS
        dest_parsed = UrlParser(dest)
        dest_parsed.scheme = "https"
        dest = dest_parsed.unparse()

        return self.redirect(dest, code=307)

    POST_modify_hsts_grant = _modify_hsts_grant
    GET_modify_hsts_grant = _modify_hsts_grant
    DELETE_modify_hsts_grant = _modify_hsts_grant
    PUT_modify_hsts_grant = _modify_hsts_grant


class FormsController(RedditController):

    def GET_password(self):
        """The 'what is my password' page"""
        return BoringPage(_("password"), content=Password()).render()

    @validate(VUser(),
              dest=VDestination(),
              reason=nop('reason'))
    def GET_verify(self, dest, reason):
        if c.user.email_verified:
            content = InfoBar(message=strings.email_verified)
            if dest:
                return self.redirect(dest)
        else:
            if reason == "submit":
                infomsg = strings.verify_email_submit
            else:
                infomsg = strings.verify_email

            content = PaneStack(
                [InfoBar(message=infomsg),
                 PrefUpdate(email=True, verify=True,
                            password=False, dest=dest)])
        return BoringPage(_("verify email"), content=content).render()

    @validate(VUser(),
              token=VOneTimeToken(EmailVerificationToken, "key"),
              dest=VDestination(default="/prefs/update?verified=true"))
    def GET_verify_email(self, token, dest):
        fail_msg = None
        if token and token.user_id != c.user._fullname:
            fail_msg = strings.email_verify_wrong_user
        elif c.user.email_verified:
            # they've already verified.
            if token:
                # consume and ignore this token (if not already consumed).
                token.consume()
            return self.redirect(dest)
        elif token and token.valid_for_user(c.user):
            # successful verification!
            token.consume()
            c.user.email_verified = True
            c.user._commit()
            Award.give_if_needed("verified_email", c.user)
            return self.redirect(dest)

        # failure. let 'em know.
        content = PaneStack(
            [InfoBar(message=fail_msg or strings.email_verify_failed),
             PrefUpdate(email=True,
                        verify=True,
                        password=False)])
        return BoringPage(_("verify email"), content=content).render()

    @validate(token=VOneTimeToken(PasswordResetToken, "key"),
              key=nop("key"))
    def GET_resetpassword(self, token, key):
        """page hit once a user has been sent a password reset email
        to verify their identity before allowing them to update their
        password."""

        done = False
        if not key and request.referer:
            referer_path = request.referer.split(g.domain)[-1]
            done = referer_path.startswith(request.fullpath)
        elif not token:
            return self.redirect("/password?expired=true")

        token_user = Account._by_fullname(token.user_id, data=True)

        return BoringPage(
            _("reset password"),
            content=ResetPassword(
                key=key,
                done=done,
                username=token_user.name,
            )
        ).render()

    @validate(
        user_id36=nop('user'),
        provided_mac=nop('key')
    )
    def GET_unsubscribe_emails(self, user_id36, provided_mac):
        from r2.lib.utils import constant_time_compare

        expected_mac = generate_notification_email_unsubscribe_token(user_id36)
        if not constant_time_compare(provided_mac or '', expected_mac):
            error_page = pages.RedditError(
                title=_('incorrect message token'),
                message='',
            )
            request.environ["usable_error_content"] = error_page.render()
            self.abort404()
        user = Account._byID36(user_id36, data=True)
        user.pref_email_messages = False
        user._commit()

        return BoringPage(_('emails unsubscribed'),
                          content=MessageNotificationEmailsUnsubscribe()).render()

    @disable_subreddit_css()
    @validate(VUser(),
              location=nop("location"),
              verified=VBoolean("verified"))
    def GET_prefs(self, location='', verified=False):
        """Preference page"""
        content = None
        infotext = None
        if not location or location == 'options':
            content = PrefOptions(
                done=request.GET.get('done'),
                error_style_override=request.GET.get('error_style_override'),
                generic_error=request.GET.get('generic_error'),
            )
        elif location == 'update':
            if verified:
                infotext = strings.email_verified
            content = PrefUpdate()
        elif location == 'apps':
            content = PrefApps(my_apps=OAuth2Client._by_user_grouped(c.user),
                               developed_apps=OAuth2Client._by_developer(c.user))
        elif location == 'feeds' and c.user.pref_private_feeds:
            content = PrefFeeds()
        elif location == 'deactivate':
            content = PrefDeactivate()
        elif location == 'delete':
            return self.redirect('/prefs/deactivate', code=301)
        elif location == 'security':
            if c.user.name not in g.admins:
                return self.redirect('/prefs/')
            content = PrefSecurity()
        else:
            return self.abort404()

        return PrefsPage(content=content, infotext=infotext).render()

    @validate(dest=VDestination())
    def GET_login(self, dest):
        """The /login form.  No link to this page exists any more on
        the site (all actions invoking it now go through the login
        cover).  However, this page is still used for logging the user
        in during submission or voting from the bookmarklets."""

        if (c.user_is_loggedin and
            not request.environ.get('extension') == 'embed'):
            return self.redirect(dest)
        return LoginPage(dest=dest).render()


    @validate(dest=VDestination())
    def GET_register(self, dest):
        if (c.user_is_loggedin and
            not request.environ.get('extension') == 'embed'):
            return self.redirect(dest)
        return RegisterPage(dest=dest).render()

    @validate(VUser(),
              VModhash(),
              dest=VDestination())
    def GET_logout(self, dest):
        return self.redirect(dest)

    @validate(VUser(),
              VModhash(),
              dest=VDestination())
    def POST_logout(self, dest):
        """wipe login cookie and redirect to referer."""
        self.logout()
        self.redirect(dest)

    @validate(VUser(),
              dest=VDestination())
    def GET_adminon(self, dest):
        """Enable admin interaction with site"""
        #check like this because c.user_is_admin is still false
        if not c.user.name in g.admins:
            return self.abort404()

        return InterstitialPage(
            _("turn admin on"),
            content=AdminInterstitial(dest=dest)).render()

    @validate(VAdmin(),
              dest=VDestination())
    def GET_adminoff(self, dest):
        """disable admin interaction with site."""
        if not c.user.name in g.admins:
            return self.abort404()
        self.disable_admin_mode(c.user)
        return self.redirect(dest)

    def _render_opt_in_out(self, msg_hash, leave):
        """Generates the form for an optin/optout page"""
        email = Email.handler.get_recipient(msg_hash)
        if not email:
            return self.abort404()
        sent = (has_opted_out(email) == leave)
        return BoringPage(_("opt out") if leave else _("welcome back"),
                          content=OptOut(email=email, leave=leave,
                                           sent=sent,
                                           msg_hash=msg_hash)).render()

    @validate(msg_hash=nop('x'))
    def GET_optout(self, msg_hash):
        """handles /mail/optout to add an email to the optout mailing
        list.  The actual email addition comes from the user posting
        the subsequently rendered form and is handled in
        ApiController.POST_optout."""
        return self._render_opt_in_out(msg_hash, True)

    @validate(msg_hash=nop('x'))
    def GET_optin(self, msg_hash):
        """handles /mail/optin to remove an email address from the
        optout list. The actual email removal comes from the user
        posting the subsequently rendered form and is handled in
        ApiController.POST_optin."""
        return self._render_opt_in_out(msg_hash, False)

    @validate(dest=VDestination("dest"))
    def GET_try_compact(self, dest):
        c.render_style = "compact"
        return TryCompact(dest=dest).render()

    @validate(VUser(),
              secret=VPrintable("secret", 50))
    def GET_claim(self, secret):
        """The page to claim reddit gold trophies"""
        return BoringPage(_("thanks"), content=Thanks(secret)).render()

    @validate(VUser(),
              passthrough=nop('passthrough'))
    def GET_creditgild(self, passthrough):
        """Used only for setting up credit card payments for gilding."""
        try:
            payment_blob = validate_blob(passthrough)
        except GoldException:
            self.abort404()

        if c.user != payment_blob['buyer']:
            self.abort404()

        if not payment_blob['goldtype'] == 'gift':
            self.abort404()

        recipient = payment_blob['recipient']
        thing = payment_blob.get('thing')
        if not thing:
            thing = payment_blob['comment']
        if (not thing or
            thing._deleted or
            not thing.subreddit_slow.can_view(c.user)):
            self.abort404()

        if isinstance(thing, Comment):
            summary = strings.gold_summary_gilding_page_comment
        else:
            summary = strings.gold_summary_gilding_page_link
        summary = summary % {'recipient': recipient.name}
        months = 1
        price = g.gold_month_price * months

        if isinstance(thing, Comment):
            desc = thing.body
        else:
            desc = thing.markdown_link_slow()

        content = CreditGild(
            summary=summary,
            price=price,
            months=months,
            stripe_key=g.secrets['stripe_public_key'],
            passthrough=passthrough,
            description=desc,
            period=None,
        )

        return BoringPage(_("reddit gold"),
                          show_sidebar=False,
                          content=content,
                          page_classes=["gold-page-ga-tracking"]
                         ).render()

    @validate(is_payment=VBoolean("is_payment"),
              goldtype=VOneOf("goldtype",
                              ("autorenew", "onetime", "creddits", "gift",
                               "code")),
              period=VOneOf("period", ("monthly", "yearly")),
              months=VInt("months"),
              num_creddits=VInt("num_creddits"),
              # variables below are just for gifts
              signed=VBoolean("signed", default=True),
              recipient=VExistingUname("recipient", default=None),
              thing=VByName("thing"),
              giftmessage=VLength("giftmessage", 10000),
              email=ValidEmail("email"),
              edit=VBoolean("edit", default=False),
    )
    def GET_gold(self, is_payment, goldtype, period, months, num_creddits,
                 signed, recipient, giftmessage, thing, email, edit):
        VNotInTimeout().run(action_name="pageview", details_text="gold",
            target=thing)
        if thing:
            thing_sr = Subreddit._byID(thing.sr_id, data=True)
            if (thing._deleted or
                    thing._spam or
                    not thing_sr.can_view(c.user) or
                    not thing_sr.allow_gilding):
                thing = None

        start_over = False

        if edit:
            start_over = True

        if not c.user_is_loggedin:
            if goldtype != "code":
                start_over = True
            elif months is None or months < 1:
                start_over = True
            elif not email:
                start_over = True
        elif goldtype == "autorenew":
            if period is None:
                start_over = True
            elif c.user.has_gold_subscription:
                return self.redirect("/gold/subscription")
        elif goldtype in ("onetime", "code"):
            if months is None or months < 1:
                start_over = True
        elif goldtype == "creddits":
            if num_creddits is None or num_creddits < 1:
                start_over = True
            else:
                months = num_creddits
        elif goldtype == "gift":
            if months is None or months < 1:
                start_over = True

            if thing:
                recipient = Account._byID(thing.author_id, data=True)
                if recipient._deleted:
                    thing = None
                    recipient = None
                    start_over = True
            elif not recipient:
                start_over = True
        else:
            goldtype = ""
            start_over = True

        if start_over:
            # If we have a form that didn't validate, and we're on the payment
            # page, redirect to the form, passing all of our form fields
            # (which are currently GET parameters).
            if is_payment:
                g.stats.simple_event("gold.checkout_redirects.to_form")
                qs = query_string(request.GET)
                return self.redirect('/gold' + qs)

            can_subscribe = (c.user_is_loggedin and
                             not c.user.has_gold_subscription)
            if not can_subscribe and goldtype == "autorenew":
                self.redirect("/creddits", code=302)

            return BoringPage(_("reddit gold"),
                              show_sidebar=False,
                              content=Gold(goldtype, period, months, signed,
                                           email, recipient,
                                           giftmessage,
                                           can_subscribe=can_subscribe,
                                           edit=edit),
                              page_classes=["gold-page", "gold-signup", "gold-page-ga-tracking"],
                              ).render()
        else:
            # If we have a validating form, and we're not yet on the payment
            # page, redirect to it, passing all of our form fields
            # (which are currently GET parameters).
            if not is_payment:
                g.stats.simple_event("gold.checkout_redirects.to_payment")
                qs = query_string(request.GET)
                return self.redirect('/gold/payment' + qs)

            payment_blob = dict(goldtype=goldtype,
                                status="initialized")
            if c.user_is_loggedin:
                payment_blob["account_id"] = c.user._id
                payment_blob["account_name"] = c.user.name
            else:
                payment_blob["email"] = email

            if goldtype == "gift":
                payment_blob["signed"] = signed
                payment_blob["recipient"] = recipient.name
                payment_blob["giftmessage"] = _force_utf8(giftmessage)
                if thing:
                    payment_blob["thing"] = thing._fullname

            passthrough = generate_blob(payment_blob)

            page_classes = ["gold-page", "gold-payment", "gold-page-ga-tracking"]
            if goldtype == "creddits":
                page_classes.append("creddits-payment")

            return BoringPage(_("reddit gold"),
                              show_sidebar=False,
                              content=GoldPayment(goldtype, period, months,
                                                  signed, recipient,
                                                  giftmessage, passthrough,
                                                  thing),
                              page_classes=page_classes,
                              ).render()

    def GET_creddits(self):
        return BoringPage(_("purchase creddits"),
                          show_sidebar=False,
                          content=Creddits(),
                          page_classes=["gold-page", "creddits-purchase", "gold-page-ga-tracking"],
                          ).render()

    @validate(VUser())
    def GET_subscription(self):
        user = c.user
        content = GoldSubscription(user)
        return BoringPage(_("reddit gold subscription"),
                          show_sidebar=False,
                          content=content,
                          page_classes=["gold-page-ga-tracking"]
                         ).render()


class FrontUnstyledController(FrontController):
    allow_stylesheets = False
