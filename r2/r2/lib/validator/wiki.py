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

from os.path import normpath
from functools import wraps
import datetime
import re

from pylons.i18n import _

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g

from r2.models.wiki import WikiPage, WikiRevision, WikiBadRevision
from r2.lib.validator import (
    Validator,
    VSrModerator,
    set_api_docs,
)
from r2.lib.db import tdb_cassandra

MAX_PAGE_NAME_LENGTH = g.wiki_max_page_name_length

MAX_SEPARATORS = g.wiki_max_page_separators

def this_may_revise(page=None):
    if not c.user_is_loggedin:
        return False
    
    if c.user_is_admin:
        return True
    
    return may_revise(c.site, c.user, page)

def this_may_view(page):
    user = c.user if c.user_is_loggedin else None
    if user and c.user_is_admin:
        return True
    return may_view(c.site, user, page)

def may_revise(sr, user, page=None):    
    if sr.is_moderator_with_perms(user, 'wiki'):
        # Mods may always contribute to non-config pages
        if not page or not page.special:
            return True
    
    if page and page.restricted and not page.special:
        # People may not contribute to restricted pages
        # (Except for special pages)
        return False

    if sr.is_wikibanned(user):
        # Users who are wiki banned in the subreddit may not contribute
        return False
    
    if sr.is_banned(user):
        # If the user is banned from the subreddit, do not allow them to contribute
        return False
    
    if page and not may_view(sr, user, page):
        # Users who are not allowed to view the page may not contribute to the page
        return False
    
    if user.wiki_override == False:
        # global ban
        return False
    
    if page and page.has_editor(user._id36):
        # If the user is an editor on the page, they may edit
        return True

    if (page and page.special and
            sr.is_moderator_with_perms(user, 'config')):
        return True

    if page and page.special:
        # If this is a special page
        # (and the user is not a mod or page editor)
        # They should not be allowed to revise
        return False
    
    if page and page.permlevel > 0:
        # If the page is beyond "anyone may contribute"
        # A normal user should not be allowed to revise
        return False
    
    if sr.is_wikicontributor(user):
        # If the user is a wiki contributor, they may revise
        return True
    
    if sr.wikimode != 'anyone':
        # If the user is not a page editor or wiki contributor,
        # and the mode is not everyone,
        # the user may not edit.
        return False
    
    if not sr.wiki_can_submit(user):
        # If the user can not submit to the subreddit
        # They should not be able to contribute
        return False

    # Use global karma for the frontpage wiki
    karma_sr = sr if sr.wiki_use_subreddit_karma else None

    # Use link or comment karma, whichever is greater
    karma = max(user.karma('link', karma_sr), user.karma('comment', karma_sr))

    if karma < (sr.wiki_edit_karma or 0):
        # If the user has too few karma, they should not contribute
        return False
    
    age = (datetime.datetime.now(g.tz) - user._date).days
    if age < (sr.wiki_edit_age or 0):
        # If they user's account is too young
        # They should not contribute
        return False
    
    # Otherwise, allow them to contribute
    return True

def may_view(sr, user, page):
    # User being None means not logged in
    mod = sr.is_moderator_with_perms(user, 'wiki') if user else False
    
    if mod:
        # Mods may always view
        return True
    
    if page.special:
        level = WikiPage.get_special_view_permlevel(page.name)
    else:
        level = page.permlevel
    
    if level < 2:
        # Everyone may view in levels below 2
        return True
    
    if level == 2:
        # Only mods may view in level 2
        return mod
    
    # In any other obscure level,
    # (This should not happen but just in case)
    # nobody may view.
    return False

def normalize_page(page):
    # Ensure there is no side effect if page is None
    page = page or ""
    
    # Replace spaces with underscores
    page = page.replace(' ', '_')
    
    # Case insensitive page names
    page = page.lower()
    
    # Normalize path (And avoid normalizing empty to ".")
    if page:
        page = normpath(page)
    
    # Chop off initial "/", just in case it exists
    page = page.lstrip('/')
    
    return page

class AbortWikiError(Exception):
    pass

page_match_regex = re.compile(r'^[\w_\-/]+\Z')

class VWikiModerator(VSrModerator):
    def __init__(self, fatal=False, *a, **kw):
        VSrModerator.__init__(self, param='page', fatal=fatal, *a, **kw)

    def run(self, page):
        self.perms = ['wiki']
        if page and WikiPage.is_special(page):
            self.perms += ['config']
        VSrModerator.run(self)

class VWikiPageName(Validator):
    def __init__(self, param, error_on_name_normalized=False, *a, **kw):
        self.error_on_name_normalized = error_on_name_normalized
        Validator.__init__(self, param, *a, **kw)
    
    def run(self, page):
        original_page = page
        
        try:
            page = str(page) if page else ""
        except UnicodeEncodeError:
            return self.set_error('INVALID_PAGE_NAME', code=400)
        
        page = normalize_page(page)
        
        if page and not page_match_regex.match(page):
            return self.set_error('INVALID_PAGE_NAME', code=400)
        
        # If no page is specified, give the index page
        page = page or "index"
        
        if WikiPage.is_impossible(page):
            return self.set_error('INVALID_PAGE_NAME', code=400)
        
        if self.error_on_name_normalized and page != original_page:
            self.set_error('PAGE_NAME_NORMALIZED')
        
        return page

class VWikiPage(VWikiPageName):
    def __init__(self, param, required=True, restricted=True, modonly=False,
                 allow_hidden_revision=True, **kw):
        self.restricted = restricted
        self.modonly = modonly
        self.allow_hidden_revision = allow_hidden_revision
        self.required = required
        VWikiPageName.__init__(self, param, **kw)
    
    def run(self, page):
        page = VWikiPageName.run(self, page)
        
        if self.has_errors:
            return
        
        if (not c.is_wiki_mod) and self.modonly:
            return self.set_error('MOD_REQUIRED', code=403)
        
        try:
            wp = self.validpage(page)
        except AbortWikiError:
            return

        return wp
    
    def validpage(self, page):
        try:
            wp = WikiPage.get(c.site, page)
            if self.restricted and wp.restricted:
                if not (c.is_wiki_mod or wp.special):
                    self.set_error('RESTRICTED_PAGE', code=403)
                    raise AbortWikiError
            if not this_may_view(wp):
                self.set_error('MAY_NOT_VIEW', code=403)
                raise AbortWikiError
            return wp
        except tdb_cassandra.NotFound:
            if self.required:
                self.set_error('PAGE_NOT_FOUND', code=404)
                raise AbortWikiError
            return None
    
    def validversion(self, version, pageid=None):
        if not version:
            return
        try:
            r = WikiRevision.get(version, pageid)
            if r.admin_deleted and not c.user_is_admin:
                self.set_error('INVALID_REVISION', code=404)
                raise AbortWikiError
            if not self.allow_hidden_revision and (r.is_hidden and not c.is_wiki_mod):
                self.set_error('HIDDEN_REVISION', code=403)
                raise AbortWikiError
            return r
        except (tdb_cassandra.NotFound, WikiBadRevision, ValueError):
            self.set_error('INVALID_REVISION', code=404)
            raise AbortWikiError

    def param_docs(self, param=None):
        return {param or self.param: _('the name of an existing wiki page')}

class VWikiPageAndVersion(VWikiPage):    
    def run(self, page, *versions):
        wp = VWikiPage.run(self, page)
        if self.has_errors:
            return
        validated = []
        for v in versions:
            try:
                validated += [self.validversion(v, wp._id) if v and wp else None]
            except AbortWikiError:
                return
        return tuple([wp] + validated)
    
    def param_docs(self):
        doc = dict.fromkeys(self.param, _('a wiki revision ID'))
        doc.update(VWikiPage.param_docs(self, self.param[0]))
        return doc

class VWikiPageRevise(VWikiPage):
    def __init__(self, param, required=False, *k, **kw):
        VWikiPage.__init__(self, param, required=required, *k, **kw)
    
    def may_not_create(self, page):
        if not page:
            # Should not happen, but just in case
            self.set_error('EMPTY_PAGE_NAME', 403)
            return
        
        page = normalize_page(page)
        
        if WikiPage.is_automatically_created(page):
            return {'reason': 'PAGE_CREATED_ELSEWHERE'}
        elif WikiPage.is_special(page):
            if not (c.user_is_admin or
                    c.site.is_moderator_with_perms(c.user, 'config')):
                self.set_error('RESTRICTED_PAGE', code=403)
                return
        elif (not c.user_is_admin) and WikiPage.is_restricted(page):
            self.set_error('RESTRICTED_PAGE', code=403)
            return
        elif page.count('/') > MAX_SEPARATORS:
            return {'reason': 'PAGE_NAME_MAX_SEPARATORS', 'max_separators': MAX_SEPARATORS}
        elif len(page) > MAX_PAGE_NAME_LENGTH:
            return {'reason': 'PAGE_NAME_LENGTH', 'max_length': MAX_PAGE_NAME_LENGTH}
    
    def run(self, page, previous=None):
        wp = VWikiPage.run(self, page)
        if self.has_errors:
            return
        if not this_may_revise(wp):
            if not wp:
                return self.set_error('PAGE_NOT_FOUND', code=404)
            # No abort code on purpose, controller will handle
            self.set_error('MAY_NOT_REVISE')
            return (None, None)
        if not wp:
            # No abort code on purpose, controller will handle
            error = self.may_not_create(page)
            if error:
                self.set_error('WIKI_CREATE_ERROR', msg_params=error)
            return (None, None)
        if previous:
            try:
                prev = self.validversion(previous, wp._id)
            except AbortWikiError:
                return
            return (wp, prev)
        return (wp, None)
    
    def param_docs(self):
        docs = {self.param[0]: _('the name of an existing page or a new page to create')}
        if 'previous' in self.param:
            docs['previous'] = _('the starting point revision for this edit')
        return docs
