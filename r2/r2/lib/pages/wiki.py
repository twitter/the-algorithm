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

from r2.lib.pages.pages import (
    AutoModeratorConfig,
    RawCode,
    Reddit,
    SubredditStylesheetSource,
)
from pylons import tmpl_context as c
from r2.lib.wrapped import Templated
from r2.lib.menus import PageNameNav
from r2.lib.validator.wiki import this_may_revise
from r2.lib.filters import wikimarkdown, safemarkdown
from pylons.i18n import _

class WikiView(Templated):
    def __init__(self, content, edit_by, edit_date, may_revise=False,
                 page=None, diff=None, renderer='wiki'):
        self.page_content_md = content
        if renderer == 'wiki':
            self.page_content = wikimarkdown(content)
        elif renderer == 'reddit':
            self.page_content = safemarkdown(content)
        elif renderer == 'stylesheet':
            self.page_content = SubredditStylesheetSource(content).render()
        elif renderer == "automoderator":
            self.page_content = AutoModeratorConfig(content).render()
        elif renderer == "rawcode":
            self.page_content = RawCode(content).render()

        self.renderer = renderer
        self.page = page
        self.diff = diff
        self.edit_by = edit_by
        self.may_revise = may_revise
        self.edit_date = edit_date
        self.base_url = c.wiki_base_url
        Templated.__init__(self)

class WikiPageNotFound(Templated):
    def __init__(self, page):
        self.page = page
        self.base_url = c.wiki_base_url
        Templated.__init__(self)

class WikiPageListing(Templated):
    def __init__(self, pages, linear_pages, page=None):
        self.pages = pages
        self.page = page
        self.linear_pages = linear_pages
        self.base_url = c.wiki_base_url
        Templated.__init__(self)

class WikiEditPage(Templated):
    def __init__(self, page_content='', previous='', page=None):
        self.page_content = page_content
        self.page = page
        self.previous = previous
        self.base_url = c.wiki_base_url
        Templated.__init__(self)

class WikiPageSettings(Templated):
    def __init__(self, settings, mayedit, show_editors=True,
                 show_settings=True, page=None, **context):
        self.permlevel = settings['permlevel']
        self.listed = settings['listed']
        self.show_settings = show_settings
        self.show_editors = show_editors
        self.page = page
        self.base_url = c.wiki_base_url
        self.mayedit = mayedit
        Templated.__init__(self)

class WikiPageRevisions(Templated):
    def __init__(self, revisions, page=None):
        self.listing = revisions
        self.page = page
        Templated.__init__(self)

class WikiPageDiscussions(Templated):
    def __init__(self, listing, page=None):
        self.listing = listing
        self.page = page
        Templated.__init__(self)

class WikiBasePage(Reddit):
    extra_page_classes = ['wiki-page']
    
    def __init__(self, content, page=None, may_revise=False,
                 actionless=False, alert=None, description=None, 
                 showtitle=False, **context):
        pageactions = []
        if not actionless and page:
            pageactions += [(page, _("view"), False, 'wikiview')]
            if may_revise:
                pageactions += [('edit', _("edit"), True, 'wikiedit')]
            pageactions += [('revisions/%s' % page, _("history"), False, 'wikirevisions')]
            pageactions += [('discussions', _("talk"), True, 'wikidiscussions')]
            if c.is_wiki_mod and may_revise:
                pageactions += [('settings', _("settings"), True, 'wikisettings')]

        action = context.get('wikiaction', (page, 'wiki'))
        
        if alert:
            context['infotext'] = alert
        elif c.wikidisabled:
            context['infotext'] = _("this wiki is currently disabled, only mods may interact with this wiki")
        
        self.pageactions = pageactions
        self.page = page
        self.base_url = c.wiki_base_url
        self.action = action
        self.description = description
        
        if showtitle:
            self.pagetitle = action[1]
        else:
            self.pagetitle = None

        page_classes = None

        if page and "title" not in context:
            context["title"] = _("%(page)s - %(site)s") % {
                "site": c.site.name,
                "page": page}
            page_classes = ['wiki-page-%s' % page.replace('/', '-')]

        Reddit.__init__(self, extra_js_config={'wiki_page': page}, 
                        show_wiki_actions=True, page_classes=page_classes,
                        content=content, short_title=page, **context)

    def content(self):
        return self._content

class WikiPageView(WikiBasePage):
    def __init__(self, content, page, diff=None, renderer='wiki', **context):
        may_revise = context.get('may_revise')
        if not content and not context.get('alert'):
            if may_revise:
                context['alert'] = _("this page is empty, edit it to add some content.")
        content = WikiView(content, context.get('edit_by'), context.get('edit_date'), 
                           may_revise=may_revise, page=page, diff=diff, renderer=renderer)
        WikiBasePage.__init__(self, content, page=page, **context)

class WikiNotFound(WikiBasePage):
    def __init__(self, page, **context):
        content = WikiPageNotFound(page)
        context['alert'] = _("page %s does not exist in this subreddit") % page
        context['actionless'] = True
        WikiBasePage.__init__(self, content, page=page, **context)

class WikiCreate(WikiBasePage):
    def __init__(self, page, **context):
        context['alert'] = _("page %s does not exist in this subreddit") % page
        context['actionless'] = True
        content = WikiEditPage(page=page)
        WikiBasePage.__init__(self, content, page, **context)

class WikiEdit(WikiBasePage):
    def __init__(self, content, previous, page, **context):
        content = WikiEditPage(content, previous, page)
        context['wikiaction'] = ('edit', _("editing"))
        WikiBasePage.__init__(self, content, page=page, **context)

class WikiSettings(WikiBasePage):
    def __init__(self, settings, mayedit, page, restricted, **context):
        content = WikiPageSettings(settings, mayedit, page=page, **context)
        if restricted:
            context['alert'] = _("This page is restricted, only moderators may edit it.")
        context['wikiaction'] = ('settings', _("settings"))
        WikiBasePage.__init__(self, content, page=page, **context)

class WikiRevisions(WikiBasePage):
    def __init__(self, revisions, page, **context):
        content = WikiPageRevisions(revisions, page)
        context['wikiaction'] = ('revisions/%s' % page, _("revisions"))
        WikiBasePage.__init__(self, content, page=page, **context)

class WikiRecent(WikiBasePage):
    def __init__(self, revisions, **context):
        content = WikiPageRevisions(revisions)
        context['wikiaction'] = ('revisions', _("Viewing recent revisions for /r/%s") % c.wiki_id)
        WikiBasePage.__init__(self, content, showtitle=True, **context)

class WikiListing(WikiBasePage):
    def __init__(self, pages, linear_pages, **context):
        content = WikiPageListing(pages, linear_pages)
        context['wikiaction'] = ('pages', _("Viewing pages for /r/%s") % c.wiki_id)
        description = [_("Below is a list of pages in this wiki visible to you in this subreddit.")]
        WikiBasePage.__init__(self, content, description=description, showtitle=True, **context)

class WikiDiscussions(WikiBasePage):
    def __init__(self, listing, page, **context):
        content = WikiPageDiscussions(listing, page)
        context['wikiaction'] = ('discussions', _("discussions"))
        description = [_("Discussions are site-wide links to this wiki page."),
                       _("Submit a link to this wiki page or see other discussions about this wiki page.")]
        WikiBasePage.__init__(self, content, page=page, description=description, **context)

