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
from pylons import app_globals as g

from reddit_base import RedditController
from r2.controllers.oauth2 import require_oauth2_scope
from r2.lib.utils import url_links_builder
from reddit_base import paginated_listing
from r2.models.wiki import (
    ContentLengthError,
    modactions,
    WikiPage,
    WikiPageExists,
    WikiRevision,
)
from r2.models.subreddit import Subreddit
from r2.models.modaction import ModAction
from r2.models.builder import WikiRevisionBuilder, WikiRecentRevisionBuilder

from r2.lib.template_helpers import join_urls


from r2.lib.validator import (
    nop,
    validate,
    VAdmin,
    VBoolean,
    VExistingUname,
    VInt,
    VMarkdown,
    VModhash,
    VNotInTimeout,
    VOneOf,
    VPrintable,
    VRatelimit,
)

from r2.lib.validator.wiki import (
    VWikiPage,
    VWikiPageAndVersion,
    VWikiModerator,
    VWikiPageRevise,
    this_may_revise,
    this_may_view,
    VWikiPageName,
)
from r2.controllers.api_docs import api_doc, api_section
from r2.lib.pages.wiki import (WikiPageView, WikiNotFound, WikiRevisions,
                              WikiEdit, WikiSettings, WikiRecent,
                              WikiListing, WikiDiscussions,
                              WikiCreate)

from r2.config.extensions import set_extension
from r2.lib.template_helpers import add_sr
from r2.lib.db import tdb_cassandra
from r2.models.listing import WikiRevisionListing
from r2.lib.pages.things import default_thing_wrapper
from r2.lib.pages import BoringPage, CssError
from reddit_base import base_listing
from r2.models import IDBuilder, LinkListing, DefaultSR
from r2.lib.merge import ConflictException, make_htmldiff
from pylons.i18n import _
from r2.lib.pages import PaneStack
from r2.lib.utils import timesince
from r2.config import extensions
from r2.lib.base import abort
from r2.lib.errors import reddit_http_error
from r2.lib.automoderator import Ruleset

import json

page_descriptions = {
    "config/stylesheet": _("This page is the subreddit stylesheet, changes here apply to the subreddit css"),
    "config/submit_text": _("The contents of this page appear on the submit page"),
    "config/sidebar": _("The contents of this page appear on the subreddit sidebar"),
    "config/description": _("The contents of this page appear in the public subreddit description and when the user does not have access to the subreddit"),
    "config/automoderator": _("This page is used to configure AutoModerator for the subreddit, please see [the full documentation](/wiki/automoderator/full-documentation) for information"),
}

ATTRIBUTE_BY_PAGE = {"config/sidebar": "description",
                     "config/submit_text": "submit_text",
                     "config/description": "public_description"}
RENDERERS_BY_PAGE = {
    "config/automoderator": "automoderator",
    "config/description": "reddit",
    "config/sidebar": "reddit",
    "config/stylesheet": "stylesheet",
    "config/submit_text": "reddit",
    "toolbox": "rawcode",
    "usernotes": "rawcode",
}

class WikiController(RedditController):
    allow_stylesheets = True

    @require_oauth2_scope("wikiread")
    @api_doc(api_section.wiki, uri='/wiki/{page}', uses_site=True)
    @validate(pv=VWikiPageAndVersion(('page', 'v', 'v2'),
                                     required=False,
                                     restricted=False,
                                     allow_hidden_revision=False),
              page_name=VWikiPageName('page',
                                      error_on_name_normalized=True))
    def GET_wiki_page(self, pv, page_name):
        """Return the content of a wiki page

        If `v` is given, show the wiki page as it was at that version
        If both `v` and `v2` are given, show a diff of the two

        """
        message = None

        if c.errors.get(('PAGE_NAME_NORMALIZED', 'page')):
            url = join_urls(c.wiki_base_url, page_name)
            return self.redirect(url)

        page, version, version2 = pv

        if not page:
            is_api = c.render_style in extensions.API_TYPES
            if this_may_revise():
                if is_api:
                    self.handle_error(404, 'PAGE_NOT_CREATED')
                errorpage = WikiNotFound(page=page_name)
                request.environ['usable_error_content'] = errorpage.render()
            elif is_api:
                self.handle_error(404, 'PAGE_NOT_FOUND')
            self.abort404()

        if version:
            edit_by = version.get_author()
            edit_date = version.date
        else:
            edit_by = page.get_author()
            edit_date = page._get('last_edit_date')

        diffcontent = None
        if not version:
            content = page.content
            if c.is_wiki_mod and page.name in page_descriptions:
                message = page_descriptions[page.name]
        else:
            message = _("viewing revision from %s") % timesince(version.date)
            if version2:
                t1 = timesince(version.date)
                t2 = timesince(version2.date)
                timestamp1 = _("%s ago") % t1
                timestamp2 = _("%s ago") % t2
                message = _("comparing revisions from %(date_1)s and %(date_2)s") \
                          % {'date_1': t1, 'date_2': t2}
                diffcontent = make_htmldiff(version.content, version2.content, timestamp1, timestamp2)
                content = version2.content
            else:
                message = _("viewing revision from %s ago") % timesince(version.date)
                content = version.content

        renderer = RENDERERS_BY_PAGE.get(page.name, 'wiki') 

        return WikiPageView(content, alert=message, v=version, diff=diffcontent,
                            may_revise=this_may_revise(page), edit_by=edit_by,
                            edit_date=edit_date, page=page.name,
                            renderer=renderer).render()

    @require_oauth2_scope("wikiread")
    @api_doc(api_section.wiki, uri='/wiki/revisions/{page}', uses_site=True)
    @paginated_listing(max_page_size=100, backend='cassandra')
    @validate(page=VWikiPage(('page'), restricted=False))
    def GET_wiki_revisions(self, num, after, reverse, count, page):
        """Retrieve a list of revisions of this wiki `page`"""
        revisions = page.get_revisions()
        wikiuser = c.user if c.user_is_loggedin else None
        builder = WikiRevisionBuilder(revisions, user=wikiuser, sr=c.site,
                                      num=num, reverse=reverse, count=count,
                                      after=after, skip=not c.is_wiki_mod,
                                      wrap=default_thing_wrapper(),
                                      page=page)
        listing = WikiRevisionListing(builder).listing()
        return WikiRevisions(listing, page=page.name, may_revise=this_may_revise(page)).render()

    @validate(wp=VWikiPageRevise('page'),
              page=VWikiPageName('page'))
    def GET_wiki_create(self, wp, page):
        api = c.render_style in extensions.API_TYPES
        error = c.errors.get(('WIKI_CREATE_ERROR', 'page'))
        if error:
            error = error.msg_params
        if wp[0]:
            VNotInTimeout().run(action_name="wikirevise",
                details_text="create", target=page)
            return self.redirect(join_urls(c.wiki_base_url, wp[0].name))
        elif api:
            if error:
                self.handle_error(403, **error)
            else:
                self.handle_error(404, 'PAGE_NOT_CREATED')
        elif error:
            error_msg = ''
            if error['reason'] == 'PAGE_NAME_LENGTH':
                error_msg = _("this wiki cannot handle page names of that magnitude!  please select a page name shorter than %d characters") % error['max_length']
            elif error['reason'] == 'PAGE_CREATED_ELSEWHERE':
                error_msg = _("this page is a special page, please go into the subreddit settings and save the field once to create this special page")
            elif error['reason'] == 'PAGE_NAME_MAX_SEPARATORS':
                error_msg = _('a max of %d separators "/" are allowed in a wiki page name.') % error['max_separators']
            return BoringPage(_("Wiki error"), infotext=error_msg).render()
        else:
            VNotInTimeout().run(action_name="wikirevise",
                details_text="create")
            return WikiCreate(page=page, may_revise=True).render()

    @validate(wp=VWikiPageRevise('page', restricted=True, required=True))
    def GET_wiki_revise(self, wp, page, message=None, **kw):
        wp = wp[0]
        VNotInTimeout().run(action_name="wikirevise", details_text="revise",
            target=wp)
        error = c.errors.get(('MAY_NOT_REVISE', 'page'))
        if error:
            self.handle_error(403, **(error.msg_params or {}))
        
        previous = kw.get('previous', wp._get('revision'))
        content = kw.get('content', wp.content)
        if not message and wp.name in page_descriptions:
            message = page_descriptions[wp.name]
        return WikiEdit(content, previous, alert=message, page=wp.name,
                        may_revise=True).render()

    @require_oauth2_scope("wikiread")
    @api_doc(api_section.wiki, uri='/wiki/revisions', uses_site=True)
    @paginated_listing(max_page_size=100, backend='cassandra')
    def GET_wiki_recent(self, num, after, reverse, count):
        """Retrieve a list of recently changed wiki pages in this subreddit"""
        revisions = WikiRevision.get_recent(c.site)
        wikiuser = c.user if c.user_is_loggedin else None
        builder = WikiRecentRevisionBuilder(revisions,  num=num, count=count,
                                            reverse=reverse, after=after,
                                            wrap=default_thing_wrapper(),
                                            skip=not c.is_wiki_mod,
                                            user=wikiuser, sr=c.site)
        listing = WikiRevisionListing(builder).listing()
        return WikiRecent(listing).render()

    @require_oauth2_scope("wikiread")
    @api_doc(api_section.wiki, uri='/wiki/pages', uses_site=True)
    def GET_wiki_listing(self):
        """Retrieve a list of wiki pages in this subreddit"""
        def check_hidden(page):
            return page.listed and this_may_view(page)
        pages, linear_pages = WikiPage.get_listing(c.site, filter_check=check_hidden)
        return WikiListing(pages, linear_pages).render()

    def GET_wiki_redirect(self, page='index'):
        return self.redirect(str("%s/%s" % (c.wiki_base_url, page)), code=301)

    @require_oauth2_scope("wikiread")
    @api_doc(api_section.wiki, uri='/wiki/discussions/{page}', uses_site=True)
    @base_listing
    @validate(page=VWikiPage('page', restricted=True))
    def GET_wiki_discussions(self, page, num, after, reverse, count):
        """Retrieve a list of discussions about this wiki `page`"""
        page_url = add_sr("%s/%s" % (c.wiki_base_url, page.name))
        builder = url_links_builder(page_url, num=num, after=after,
                                    reverse=reverse, count=count)
        listing = LinkListing(builder).listing()
        return WikiDiscussions(listing, page=page.name,
                               may_revise=this_may_revise(page)).render()

    @require_oauth2_scope("modwiki")
    @api_doc(api_section.wiki, uri='/wiki/settings/{page}', uses_site=True)
    @validate(page=VWikiPage('page', restricted=True, modonly=True))
    def GET_wiki_settings(self, page):
        """Retrieve the current permission settings for `page`"""
        settings = {'permlevel': page._get('permlevel', 0),
                    'listed': page.listed}
        VNotInTimeout().run(action_name="pageview",
                details_text="wikisettings", target=page)
        mayedit = page.get_editor_accounts()
        restricted = (not page.special) and page.restricted
        show_editors = not restricted
        return WikiSettings(settings, mayedit, show_settings=not page.special,
                            page=page.name, show_editors=show_editors,
                            restricted=restricted,
                            may_revise=True).render()

    @require_oauth2_scope("modwiki")
    @api_doc(api_section.wiki, uri='/wiki/settings/{page}', uses_site=True)
    @validate(
        VModhash(),
        page=VWikiPage('page', restricted=True, modonly=True),
        permlevel=VInt('permlevel'),
        listed=VBoolean('listed'),
    )
    def POST_wiki_settings(self, page, permlevel, listed):
        """Update the permissions and visibility of wiki `page`"""
        oldpermlevel = page.permlevel
        if oldpermlevel != permlevel:
            VNotInTimeout().run(action_name="wikipermlevel",
                details_text="edit", target=page)
        if page.listed != listed:
            VNotInTimeout().run(action_name="wikipagelisted",
                details_text="edit", target=page)

        try:
            page.change_permlevel(permlevel)
        except ValueError:
            self.handle_error(403, 'INVALID_PERMLEVEL')
        if page.listed != listed:
            page.listed = listed
            page._commit()
            verb = 'Relisted' if listed else 'Delisted'
            description = '%s page %s' % (verb, page.name)
            ModAction.create(c.site, c.user, 'wikipagelisted',
                             description=description)
        if oldpermlevel != permlevel:
            description = 'Page: %s, Changed from %s to %s' % (
                page.name, oldpermlevel, permlevel
            )
            ModAction.create(c.site, c.user, 'wikipermlevel',
                             description=description)
        return self.GET_wiki_settings(page=page.name)

    def on_validation_error(self, error):
        RedditController.on_validation_error(self, error)
        if error.code:
            self.handle_error(error.code, error.name)

    def handle_error(self, code, reason=None, **data):
        abort(reddit_http_error(code, reason, **data))

    def pre(self):
        RedditController.pre(self)
        if g.disable_wiki and not c.user_is_admin:
            self.handle_error(403, 'WIKI_DOWN')
        if not c.site._should_wiki:
            self.handle_error(404, 'NOT_WIKIABLE')  # /r/mod for an example
        frontpage = isinstance(c.site, DefaultSR)
        c.wiki_base_url = join_urls(c.site.path, 'wiki')
        c.wiki_api_url = join_urls(c.site.path, '/api/wiki')
        c.wiki_id = g.default_sr if frontpage else c.site.name
        self.editconflict = False
        c.is_wiki_mod = (
            c.user_is_admin or c.site.is_moderator_with_perms(c.user, 'wiki')
            ) if c.user_is_loggedin else False
        c.wikidisabled = False

        mode = c.site.wikimode
        if not mode or mode == 'disabled':
            if not c.is_wiki_mod:
                self.handle_error(403, 'WIKI_DISABLED')
            else:
                c.wikidisabled = True

    # Redirects from the old wiki
    def GET_faq(self):
        return self.GET_wiki_redirect(page='faq')

    GET_help = GET_wiki_redirect


class WikiApiController(WikiController):
    @require_oauth2_scope("wikiedit")
    @validate(VModhash(),
              pageandprevious=VWikiPageRevise(('page', 'previous'), restricted=True),
              content=nop(('content')),
              page_name=VWikiPageName('page'),
              reason=VPrintable('reason', 256, empty_error=None))
    @api_doc(api_section.wiki, uri='/api/wiki/edit', uses_site=True)
    def POST_wiki_edit(self, pageandprevious, content, page_name, reason):
        """Edit a wiki `page`"""
        page, previous = pageandprevious

        if c.user._spam:
            error = _("You are doing that too much, please try again later.")
            self.handle_error(415, 'SPECIAL_ERRORS', special_errors=[error])

        if not page:
            error = c.errors.get(('WIKI_CREATE_ERROR', 'page'))
            if error:
                self.handle_error(403, **(error.msg_params or {}))

            VNotInTimeout().run(action_name="wikirevise", details_text="create")
            try:
                page = WikiPage.create(c.site, page_name)
            except WikiPageExists:
                self.handle_error(400, 'WIKI_CREATE_ERROR')

        else:
            VNotInTimeout().run(action_name="wikirevise", details_text="edit",
                target=page)
            error = c.errors.get(('MAY_NOT_REVISE', 'page'))
            if error:
                self.handle_error(403, **(error.msg_params or {}))

        renderer = RENDERERS_BY_PAGE.get(page.name, 'wiki')
        if renderer in ('wiki', 'reddit'):
            content = VMarkdown(('content'), renderer=renderer).run(content)

        # Use the raw POST value as we need to tell the difference between
        # None/Undefined and an empty string.  The validators use a default
        # value with both of those cases and would need to be changed.
        # In order to avoid breaking functionality, this was done instead.
        previous = previous._id if previous else request.POST.get('previous')
        try:
            # special validation methods
            if page.name == 'config/stylesheet':
                css_errors, parsed = c.site.parse_css(content, verify=False)
                if g.css_killswitch:
                    self.handle_error(403, 'STYLESHEET_EDIT_DENIED')
                if css_errors:
                    error_items = [CssError(x).message for x in css_errors]
                    self.handle_error(415, 'SPECIAL_ERRORS', special_errors=error_items)
            elif page.name == "config/automoderator":
                try:
                    rules = Ruleset(content)
                except ValueError as e:
                    error_items = [e.message]
                    self.handle_error(415, "SPECIAL_ERRORS", special_errors=error_items)

            # special saving methods
            if page.name == "config/stylesheet":
                c.site.change_css(content, parsed, previous, reason=reason)
            else:
                try:
                    page.revise(content, previous, c.user._id36, reason=reason)
                except ContentLengthError as e:
                    self.handle_error(403, 'CONTENT_LENGTH_ERROR', max_length=e.max_length)

                # continue storing the special pages as data attributes on the subreddit
                # object. TODO: change this to minimize subreddit get sizes.
                if page.special and page.name in ATTRIBUTE_BY_PAGE:
                    setattr(c.site, ATTRIBUTE_BY_PAGE[page.name], content)
                    c.site._commit()

                if page.special or c.is_wiki_mod:
                    description = modactions.get(page.name, 'Page %s edited' % page.name)
                    ModAction.create(c.site, c.user, "wikirevise",
                        details=description, description=reason)
        except ConflictException as e:
            self.handle_error(409, 'EDIT_CONFLICT', newcontent=e.new, newrevision=page.revision, diffcontent=e.htmldiff)
        return json.dumps({})

    @require_oauth2_scope("modwiki")
    @validate(VModhash(),
              VWikiModerator(),
              page=VWikiPage('page'),
              act=VOneOf('act', ('del', 'add')),
              user=VExistingUname('username'))
    @api_doc(api_section.wiki, uri='/api/wiki/alloweditor/{act}',
             uses_site=True,
             uri_variants=['/api/wiki/alloweditor/%s' % act for act in ('del', 'add')])
    def POST_wiki_allow_editor(self, act, page, user):
        """Allow/deny `username` to edit this wiki `page`"""
        if not user:
            self.handle_error(404, 'UNKNOWN_USER')
        elif act == 'del':
            VNotInTimeout().run(action_name="wikipermlevel",
                details_text="del_editor", target=user)
            page.remove_editor(user._id36)
        elif act == 'add':
            VNotInTimeout().run(action_name="wikipermlevel",
                details_text="allow_editor", target=user)
            page.add_editor(user._id36)
        else:
            self.handle_error(400, 'INVALID_ACTION')
        return json.dumps({})

    @validate(
        VModhash(),
        VAdmin(),
        pv=VWikiPageAndVersion(('page', 'revision')),
        deleted=VBoolean('deleted'),
    )
    def POST_wiki_revision_delete(self, pv, deleted):
        page, revision = pv
        if not revision:
            self.handle_error(400, 'INVALID_REVISION')
        if deleted and page.revision == str(revision._id):
            self.handle_error(400, 'REVISION_IS_CURRENT')
        revision.admin_deleted = deleted
        revision._commit()
        return json.dumps({'status': revision.admin_deleted})

    @require_oauth2_scope("modwiki")
    @validate(VModhash(),
              VWikiModerator(),
              pv=VWikiPageAndVersion(('page', 'revision')))
    @api_doc(api_section.wiki, uri='/api/wiki/hide', uses_site=True)
    def POST_wiki_revision_hide(self, pv):
        """Toggle the public visibility of a wiki page revision"""
        page, revision = pv
        if not revision:
            self.handle_error(400, 'INVALID_REVISION')

        VNotInTimeout().run(action_name="wikirevise",
                details_text="revision_hide", target=page)
        return json.dumps({'status': revision.toggle_hide()})

    @require_oauth2_scope("modwiki")
    @validate(VModhash(),
              VWikiModerator(),
              pv=VWikiPageAndVersion(('page', 'revision')))
    @api_doc(api_section.wiki, uri='/api/wiki/revert', uses_site=True)
    def POST_wiki_revision_revert(self, pv):
        """Revert a wiki `page` to `revision`"""
        page, revision = pv
        if not revision:
            self.handle_error(400, 'INVALID_REVISION')
        VNotInTimeout().run(action_name="wikirevise",
                details_text="revision_revert", target=page)
        content = revision.content
        reason = 'reverted back %s' % timesince(revision.date)
        if page.name == 'config/stylesheet':
            css_errors, parsed = c.site.parse_css(content)
            if css_errors:
                self.handle_error(403, 'INVALID_CSS')
            c.site.change_css(content, parsed, prev=None, reason=reason, force=True)
        else:
            try:
                page.revise(content, author=c.user._id36, reason=reason, force=True)

                # continue storing the special pages as data attributes on the subreddit
                # object. TODO: change this to minimize subreddit get sizes.
                if page.name in ATTRIBUTE_BY_PAGE:
                    setattr(c.site, ATTRIBUTE_BY_PAGE[page.name], content)
                    c.site._commit()
            except ContentLengthError as e:
                self.handle_error(403, 'CONTENT_LENGTH_ERROR', max_length=e.max_length)
        return json.dumps({})

    def pre(self):
        WikiController.pre(self)
        c.render_style = 'api'
        set_extension(request.environ, 'json')

