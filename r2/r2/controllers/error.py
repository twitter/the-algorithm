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

import json
import os
import random

import pylons

from webob.exc import HTTPFound, HTTPMovedPermanently
from pylons.i18n import _
from pylons import request, response
from pylons import tmpl_context as c
from pylons import app_globals as g


try:
    # place all r2 specific imports in here.  If there is a code error, it'll
    # get caught and the stack trace won't be presented to the user in
    # production
    from r2.config import extensions
    from r2.controllers.reddit_base import RedditController, UnloggedUser
    from r2.lib.cookies import Cookies
    from r2.lib.errors import ErrorSet
    from r2.lib.filters import (
        safemarkdown,
        scriptsafe_dumps,
        websafe,
        websafe_json,
    )
    from r2.lib import log, pages
    from r2.lib.strings import get_funny_translated_string
    from r2.lib.template_helpers import static
    from r2.lib.base import abort
    from r2.models.link import Link
    from r2.models.subreddit import DefaultSR, Subreddit
except Exception, e:
    if g.debug:
        # if debug mode, let the error filter up to pylons to be handled
        raise e
    else:
        # production environment: protect the code integrity!
        print "HuffmanEncodingError: make sure your python compiles before deploying, stupid!"
        # kill this app
        os._exit(1)


redditbroke =  \
'''<html>
  <head>
    <title>reddit broke!</title>
  </head>
  <body>
    <div style="margin: auto; text-align: center">
      <p>
        <a href="/">
          <img border="0" src="%s" alt="you broke reddit" />
        </a>
      </p>
      <p>
        %s
      </p>
  </body>
</html>
'''


FAILIEN_COUNT = 3
def make_failien_url():
    failien_number = random.randint(1, FAILIEN_COUNT)
    failien_name = "youbrokeit%d.png" % failien_number
    return static(failien_name)


class ErrorController(RedditController):
    """Generates error documents as and when they are required.

    The ErrorDocuments middleware forwards to ErrorController when error
    related status codes are returned from the application.

    This behaviour can be altered by changing the parameters to the
    ErrorDocuments middleware in your config/middleware.py file.
    """
    # Handle POST endpoints redirecting to the error controller
    handles_csrf = True

    def check_for_bearer_token(self):
        pass

    allowed_render_styles = ('html', 'xml', 'js', 'embed', '', "compact", 'api')
    # List of admins to blame (skip the first admin, "reddit")
    # If list is empty, just blame "an admin"
    admins = g.admins[1:] or ["an admin"]
    def __before__(self):
        try:
            c.error_page = True
            RedditController.__before__(self)
        except (HTTPMovedPermanently, HTTPFound):
            # ignore an attempt to redirect from an error page
            pass
        except Exception as e:
            handle_awful_failure("ErrorController.__before__: %r" % e)

        # c.error_page is special-cased in a couple places to bypass
        # c.site checks. We shouldn't allow the user to get here other
        # than through `middleware.py:error_mapper`.
        if not request.environ.get('pylons.error_call'):
            abort(403, "direct access to error controller disallowed")

    def __after__(self): 
        try:
            RedditController.__after__(self)
        except Exception as e:
            handle_awful_failure("ErrorController.__after__: %r" % e)

    def __call__(self, environ, start_response):
        try:
            return RedditController.__call__(self, environ, start_response)
        except Exception as e:
            return handle_awful_failure("ErrorController.__call__: %r" % e)


    def send400(self):
        if 'usable_error_content' in request.environ:
            return request.environ['usable_error_content']
        else:
            res = pages.RedditError(
                title=_("bad request (%(domain)s)") % dict(domain=g.domain),
                message=_("you sent an invalid request"),
                explanation=request.GET.get('explanation'))
            return res.render()

    def send403(self):
        c.site = DefaultSR()
        if 'usable_error_content' in request.environ:
            return request.environ['usable_error_content']
        else:
            res = pages.RedditError(
                title=_("forbidden (%(domain)s)") % dict(domain=g.domain),
                message=_("you are not allowed to do that"),
                explanation=request.GET.get('explanation'))
            return res.render()

    def send404(self):
        if 'usable_error_content' in request.environ:
            return request.environ['usable_error_content']
        return pages.RedditError(_("page not found"),
                                 _("the page you requested does not exist")).render()

    def send429(self):
        retry_after = request.environ.get("retry_after")
        if retry_after:
            response.headers["Retry-After"] = str(retry_after)
            template_name = '/ratelimit_toofast.html'
        else:
            template_name = '/ratelimit_throttled.html'

        template = g.mako_lookup.get_template(template_name)
        return template.render(
            logo_url=static(g.default_header_url),
            retry_after=retry_after,
        )

    def send503(self):
        retry_after = request.environ.get("retry_after")
        if retry_after:
            response.headers["Retry-After"] = str(retry_after)
        return request.environ['usable_error_content']

    def GET_document(self):
        try:
            c.errors = c.errors or ErrorSet()
            # clear cookies the old fashioned way 
            c.cookies = Cookies()

            code =  request.GET.get('code', '')
            try:
                code = int(code)
            except ValueError:
                code = 404
            srname = request.GET.get('srname', '')
            takedown = request.GET.get('takedown', '')
            error_name = request.GET.get('error_name', '')

            if isinstance(c.user, basestring):
                # somehow requests are getting here with c.user unset
                c.user_is_loggedin = False
                c.user = UnloggedUser(browser_langs=None)

            if srname:
                c.site = Subreddit._by_name(srname)

            if request.GET.has_key('allow_framing'):
                c.allow_framing = bool(request.GET['allow_framing'] == '1')

            if (error_name == 'IN_TIMEOUT' and
                    not 'usable_error_content' in request.environ):
                timeout_days_remaining = c.user.days_remaining_in_timeout

                errpage = pages.InterstitialPage(
                    _("suspended"),
                    content=pages.InTimeoutInterstitial(
                        timeout_days_remaining=timeout_days_remaining,
                    ),
                )
                request.environ['usable_error_content'] = errpage.render()

            if code in (204, 304):
                # NEVER return a content body on 204/304 or downstream
                # caches may become very confused.
                return ""
            elif c.render_style not in self.allowed_render_styles:
                return str(code)
            elif c.render_style in extensions.API_TYPES:
                data = request.environ.get('extra_error_data', {'error': code})
                message = request.GET.get('message', '')
                if message:
                    data['message'] = message
                if request.environ.get("WANT_RAW_JSON"):
                    return scriptsafe_dumps(data)
                return websafe_json(json.dumps(data))
            elif takedown and code == 404:
                link = Link._by_fullname(takedown)
                return pages.TakedownPage(link).render()
            elif code == 400:
                return self.send400()
            elif code == 403:
                return self.send403()
            elif code == 429:
                return self.send429()
            elif code == 500:
                failien_url = make_failien_url()
                sad_message = get_funny_translated_string("500_page")
                sad_message %= {'admin': random.choice(self.admins)}
                sad_message = safemarkdown(sad_message)
                return redditbroke % (failien_url, sad_message)
            elif code == 503:
                return self.send503()
            elif c.site:
                return self.send404()
            else:
                return "page not found"
        except Exception as e:
            return handle_awful_failure("ErrorController.GET_document: %r" % e)

    POST_document = GET_document
    PUT_document = GET_document
    PATCH_document = GET_document
    DELETE_document = GET_document


def handle_awful_failure(fail_text):
    """
    Makes sure that no errors generated in the error handler percolate
    up to the user unless debug is enabled.
    """
    if g.debug:
        import sys
        s = sys.exc_info()
        # reraise the original error with the original stack trace
        raise s[1], None, s[2]
    try:
        # log the traceback, and flag the "path" as the error location
        import traceback
        log.write_error_summary(fail_text)
        for line in traceback.format_exc().splitlines():
            g.log.error(line)
        return redditbroke % (make_failien_url(), websafe(fail_text))
    except:
        # we are doomed.  Admit defeat
        return "This is an error that should never occur.  You win."
