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

from pylons import request, session, config, response
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.controllers import WSGIController
from pylons.i18n import N_, _, ungettext, get_lang
from webob.exc import HTTPException, status_map
from r2.lib.filters import spaceCompress, _force_unicode
from r2.lib.template_helpers import get_domain
from r2.lib.utils import Agent
from utils import string2js, read_http_date

import re, hashlib
from Cookie import CookieError
from urllib import quote
import urllib2
import sys


#TODO hack
import logging
from r2.lib.utils import UrlParser, query_string
logging.getLogger('scgi-wsgi').setLevel(logging.CRITICAL)


def is_local_address(ip):
    # TODO: support the /20 and /24 private networks? make this configurable?
    return ip.startswith('10.') or ip == "127.0.0.1"

def abort(code_or_exception=None, detail="", headers=None, comment=None,
          **kwargs):
    """Raise an HTTPException and save it in environ for use by error pages."""
    # Pylons 0.9.6 makes it really hard to get your raised HTTPException,
    # so this helper implements it manually using a familiar syntax.
    # FIXME: when we upgrade Pylons, we can replace this with raise
    #        and access environ['pylons.controller.exception']
    # NOTE: when we say "upgrade Pylons" we mean to 0.10+
    if isinstance(code_or_exception, HTTPException):
        exc = code_or_exception
    else:
        if type(code_or_exception) is type and issubclass(code_or_exception,
                                                          HTTPException):
            exc_cls = code_or_exception
        else:
            exc_cls = status_map[code_or_exception]
        exc = exc_cls(detail, headers, comment, **kwargs)
    request.environ['r2.controller.exception'] = exc
    raise exc

class BaseController(WSGIController):
    def __before__(self):
        """Perform setup tasks before the controller method/action is executed.

        Called by WSGIController.__call__.

        """

        # we override this here to ensure that this header, and only this
        # header, is trusted to reduce the number of potential
        # misconfigurations between wsgi application servers (e.g. gunicorn
        # which trusts three different headers out of the box for this) and
        # haproxy (which won't clean out bad headers by default)
        forwarded_proto = request.environ.get("HTTP_X_FORWARDED_PROTO", "http")
        forwarded_proto = forwarded_proto.lower()
        assert forwarded_proto in ("http", "https")
        request.environ["wsgi.url_scheme"] = forwarded_proto

        forwarded_for = request.environ.get('HTTP_X_FORWARDED_FOR', ())
        remote_addr = request.environ.get('REMOTE_ADDR')

        request.via_cdn = False
        cdn_ip = g.cdn_provider.get_client_ip(request.environ)
        if cdn_ip:
            request.ip = cdn_ip
            request.via_cdn = True
        elif (g.trust_local_proxies and
                forwarded_for and
                is_local_address(remote_addr)):
            request.ip = forwarded_for.split(',')[-1]
        else:
            request.ip = request.environ['REMOTE_ADDR']

        try:
            # webob can't handle non utf-8 encoded query strings or paths
            request.params
            request.path
        except UnicodeDecodeError:
            abort(400)

        #if x-dont-decode is set, pylons won't unicode all the parameters
        if request.environ.get('HTTP_X_DONT_DECODE'):
            request.charset = None

        request.referer = request.environ.get('HTTP_REFERER')
        request.user_agent = request.environ.get('HTTP_USER_AGENT')
        request.parsed_agent = Agent.parse(request.user_agent)
        request.fullpath = request.environ.get('FULLPATH', request.path)
        request.fullurl = request.host_url + request.fullpath
        request.port = request.environ.get('request_port')

        if_modified_since = request.environ.get('HTTP_IF_MODIFIED_SINCE')
        if if_modified_since:
            request.if_modified_since = read_http_date(if_modified_since)
        else:
            request.if_modified_since = None

        self.fix_cookie_header()
        self.pre()

    def __after__(self):
        self.post()

    def __call__(self, environ, start_response):
        # as defined by routing rules in in routing.py, a request to
        # /api/do_something is routed to the ApiController's do_something()
        # method (action). Rewrite this to include the HTTP verb which is the
        # real name of the controller method: GET_do_something().
        action = request.environ['pylons.routes_dict'].get('action')
        if action:
            meth = request.method.upper()
            if meth == 'HEAD':
                meth = 'GET'

            if (meth == 'OPTIONS' and
                    self._get_action_handler(action, meth) is None):
                handler_name = meth
            else:
                handler_name = meth + '_' + action

            request.environ['pylons.routes_dict']['action_name'] = action
            request.environ['pylons.routes_dict']['action'] = handler_name

        # WSGIController.__call__ will run __before__() and then execute the
        # controller method via environ['pylons.routes_dict']['action']
        return WSGIController.__call__(self, environ, start_response)

    def pre(self): pass
    def post(self): pass

    def fix_cookie_header(self):
        """
        Detect and drop busted `Cookie` headers

        We get all sorts of invalid `Cookie` headers. Just one example:

            Cookie: fo,o=bar; expires=1;

        Normally you'd do this in middleware, but `webob.cookie`'s API
        is fairly volatile while `webob.request`'s isn't. It's easier to
        do this once we've got a valid `Request` object.
        """
        try:
            # Just accessing this will cause `webob` to attempt a parse,
            # telling us if the header's broken.
            request.cookies
        except (CookieError, KeyError):
            # Someone sent a janked up cookie header, and `webob` exploded.
            # just pretend we didn't receive one at all.
            cookie_val = request.environ.get('HTTP_COOKIE', '')
            request.environ['HTTP_COOKIE'] = ''
            g.log.warning("Cleared bad cookie header: %r" % cookie_val)
            g.stats.simple_event("cookie.bad_cookie_header")

    def _get_action_handler(self, name=None, method=None):
        name = name or request.environ["pylons.routes_dict"]["action_name"]
        method = method or request.method
        action = method + "_" + name
        return getattr(self, action, None)

    @classmethod
    def format_output_url(cls, url, **kw):
        """
        Helper method used during redirect to ensure that the redirect
        url (assisted by frame busting code or javasctipt) will point
        to the correct domain and not have any extra dangling get
        parameters.  The extensions are also made to match and the
        resulting url is utf8 encoded.

        Node: for development purposes, also checks that the port
        matches the request port
        """
        preserve_extension = kw.pop("preserve_extension", True)
        u = UrlParser(url)

        if u.is_reddit_url():
            # make sure to pass the port along if not 80
            if not kw.has_key('port'):
                kw['port'] = request.port

            # make sure the extensions agree with the current page
            if preserve_extension and c.extension:
                u.set_extension(c.extension)

        # unparse and encode it un utf8
        rv = _force_unicode(u.unparse()).encode('utf8')
        if "\n" in rv or "\r" in rv:
            abort(400)
        return rv

    @classmethod
    def intermediate_redirect(cls, form_path, sr_path=True, fullpath=None):
        """
        Generates a /login or /over18 redirect from the specified or current
        fullpath, after having properly reformated the path via
        format_output_url.  The reformatted original url is encoded
        and added as the "dest" parameter of the new url.
        """
        from r2.lib.template_helpers import add_sr
        params = dict(dest=cls.format_output_url(fullpath or request.fullurl))
        if c.extension == "widget" and request.GET.get("callback"):
            params['callback'] = request.GET.get("callback")

        path = add_sr(cls.format_output_url(form_path) +
                      query_string(params), sr_path=sr_path)
        abort(302, location=path)

    @classmethod
    def redirect(cls, dest, code=302, preserve_extension=True):
        """
        Reformats the new Location (dest) using format_output_url and
        sends the user to that location with the provided HTTP code.
        """
        dest = cls.format_output_url(dest or "/",
                                     preserve_extension=preserve_extension)
        response.status_int = code
        response.headers['Location'] = dest


class EmbedHandler(urllib2.BaseHandler, urllib2.HTTPHandler,
                   urllib2.HTTPErrorProcessor, urllib2.HTTPDefaultErrorHandler):

    def http_redirect(self, req, fp, code, msg, hdrs):
        to = hdrs['Location']
        h = urllib2.HTTPRedirectHandler()
        r = h.redirect_request(req, fp, code, msg, hdrs, to)
        return embedopen.open(r)

    http_error_301 = http_redirect
    http_error_302 = http_redirect
    http_error_303 = http_redirect
    http_error_307 = http_redirect

embedopen = urllib2.OpenerDirector()
embedopen.add_handler(EmbedHandler())

def proxyurl(url):
    r = urllib2.Request(url, None, {})
    content = embedopen.open(r).read()
    return content
