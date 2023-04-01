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

from r2.controllers.reddit_base import RedditController
from r2.lib.base import proxyurl
from r2.lib.csrf import csrf_exempt
from r2.lib.template_helpers import get_domain
from r2.lib.pages import Embed, BoringPage, HelpPage
from r2.lib.filters import websafe, SC_OFF, SC_ON
from r2.lib.memoize import memoize

from pylons.i18n import _
from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g

from BeautifulSoup import BeautifulSoup, Tag

from urllib2 import HTTPError

@memoize("renderurl_cached", time=60)
def renderurl_cached(path):
    # Needed so http://reddit.com/help/ works
    fp = path.rstrip("/")
    u = "https://code.reddit.com/wiki" + fp + '?stripped=1'

    g.log.debug("Pulling %s for help" % u)

    try:
        return fp, proxyurl(u)
    except HTTPError, e:
        if e.code != 404:
            print "error %s" % e.code
            print e.fp.read()
        return (None, None)

class EmbedController(RedditController):
    allow_stylesheets = True

    def rendercontent(self, input, fp):
        soup = BeautifulSoup(input)

        output = soup.find("div", { 'class':'wiki', 'id':'content'} )

        # Replace all links to "/wiki/help/..." with "/help/..."
        for link in output.findAll('a'):
            if link.has_key('href') and link['href'].startswith("/wiki/help"):
                link['href'] = link['href'][5:]

        output = SC_OFF + unicode(output) + SC_ON

        return HelpPage(_("help"),
                        content = Embed(content=output),
                        show_sidebar = None).render()

    @csrf_exempt
    def renderurl(self, override=None):
        if override:
            path = override
        else:
            path = request.path

        fp, content = renderurl_cached(path)
        if content is None:
            self.abort404()
        return self.rendercontent(content, fp)

    GET_help = POST_help = renderurl

    def GET_blog(self):
        return self.redirect("https://blog.%s/" %
                             get_domain(subreddit=False, no_www=True))

    def GET_faq(self):
        if c.default_sr:
            return self.redirect('/help/faq')
        else:
            return self.renderurl('/help/faqs/' + c.site.name)
