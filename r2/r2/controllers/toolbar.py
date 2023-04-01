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
import re
import string

from pylons import tmpl_context as c
from pylons import app_globals as g

from reddit_base import RedditController
from r2.lib import utils
from r2.lib.pages import *
from r2.lib.pages.things import hot_links_by_url_listing
from r2.lib.template_helpers import add_sr
from r2.lib.validator import *
from r2.models import *
from r2.models.admintools import is_shamed_domain

# strips /r/foo/, /s/, or both
strip_sr          = re.compile('\A/r/[a-zA-Z0-9_-]+')
strip_s_path      = re.compile('\A/s/')
leading_slash     = re.compile('\A/+')
has_protocol      = re.compile('\A[a-zA-Z_-]+:')
allowed_protocol  = re.compile('\Ahttps?:')
need_insert_slash = re.compile('\Ahttps?:/[^/]')
def demangle_url(path):
    # there's often some URL mangling done by the stack above us, so
    # let's clean up the URL before looking it up
    path = strip_sr.sub('', path)
    path = strip_s_path.sub('', path)
    path = leading_slash.sub("", path)

    if has_protocol.match(path):
        if not allowed_protocol.match(path):
            return None
    else:
        path = '%s://%s' % (g.default_scheme, path)

    if need_insert_slash.match(path):
        path = string.replace(path, '/', '//', 1)

    try:
        path = utils.sanitize_url(path)
    except TypeError:
        return None

    return path

def force_html():
    """Because we can take URIs like /s/http://.../foo.png, and we can
       guarantee that the toolbar will never be used with a non-HTML
       render style, we don't want to interpret the extension from the
       target URL. So here we rewrite Middleware's interpretation of
       the extension to force it to be HTML
    """

    c.render_style = 'html'
    c.extension = None
    c.content_type = 'text/html; charset=UTF-8'


class ToolbarController(RedditController):

    allow_stylesheets = True

    @validate(link1 = VByName('id'),
              link2 = VLink('id', redirect = False))
    def GET_goto(self, link1, link2):
        """Support old /goto?id= urls. deprecated"""
        link = link2 if link2 else link1
        if link:
            return self.redirect(add_sr("/tb/" + link._id36))
        return self.abort404()

    @validate(urloid=nop('urloid'))
    def GET_s(self, urloid):
        """/s/http://..., show a given URL with the toolbar. if it's
           submitted, redirect to /tb/$id36"""
        force_html()
        path = demangle_url(request.fullpath)

        if not path:
            # it was malformed
            self.abort404()

        # if the domain is shame-banned, bail out.
        if is_shamed_domain(path)[0]:
            self.abort404()

        listing = hot_links_by_url_listing(path, sr=c.site, num=1)
        link = listing.things[0] if listing.things else None

        if link:
            # we were able to find it, let's send them to the
            # toolbar (if enabled) or comments (if not)
            return self.redirect(add_sr("/tb/" + link._id36))
        else:
            # It hasn't been submitted yet. Give them a chance to
            qs = utils.query_string({"url": path})
            return self.redirect(add_sr("/submit" + qs))

    def GET_redirect(self):
        return self.redirect('/', code=301)
