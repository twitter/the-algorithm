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

"""Pylons middleware initialization"""
import importlib
import re
import urllib
import tempfile
import urlparse
from threading import Lock
import itertools
import simplejson

from paste.cascade import Cascade
from paste.errordocument import StatusBasedForward
from paste.recursive import RecursiveMiddleware
from paste.registry import RegistryManager
from paste.urlparser import StaticURLParser
from paste.deploy.converters import asbool
from paste.request import path_info_split
from pylons import response
from pylons.middleware import ErrorHandler
from pylons.wsgiapp import PylonsApp
from routes.middleware import RoutesMiddleware

from r2.config import hooks
from r2.config.environment import load_environment
from r2.config.extensions import extension_mapping, set_extension
from r2.lib.utils import is_subdomain, is_language_subdomain
from r2.lib import csrf, filters


# patch in WebOb support for HTTP 429 "Too Many Requests"
import webob.exc
import webob.util

class HTTPTooManyRequests(webob.exc.HTTPClientError):
    code = 429
    title = 'Too Many Requests'
    explanation = ('The server has received too many requests from the client.')

webob.exc.status_map[429] = HTTPTooManyRequests
webob.util.status_reasons[429] = HTTPTooManyRequests.title

# patch out SSRFable/XSSable endpoints in older versions of weberror
import weberror.evalexception


# We could probably just set `.exposed = False`, but this makes me feel better
def _stub(*args, **kwargs):
    pass

weberror.evalexception.EvalException.post_traceback = _stub
weberror.evalexception.EvalException.relay = _stub


def error_mapper(code, message, environ, global_conf=None, **kw):
    if environ.get('pylons.error_call'):
        return None
    else:
        environ['pylons.error_call'] = True

    from pylons import tmpl_context as c

    if global_conf is None:
        global_conf = {}
    codes = [304, 400, 401, 403, 404, 409, 415, 429, 503]
    if not asbool(global_conf.get('debug')):
        codes.append(500)
    if code in codes:
        # StatusBasedForward expects a relative URL (no SCRIPT_NAME)
        d = dict(code = code, message = message)

        exception = environ.get('r2.controller.exception')
        if exception:
            d['explanation'] = exception.explanation
            error_data = getattr(exception, 'error_data', None)
            if error_data:
                environ['extra_error_data'] = error_data

        if environ.get('REDDIT_NAME'):
            d['srname'] = environ.get('REDDIT_NAME')
        if environ.get('REDDIT_TAKEDOWN'):
            d['takedown'] = environ.get('REDDIT_TAKEDOWN')
        if environ.get('REDDIT_ERROR_NAME'):
            d['error_name'] = environ.get('REDDIT_ERROR_NAME')

        # preserve x-frame-options when 304ing
        if code == 304:
            d['allow_framing'] = 1 if c.allow_framing else 0

        extension = environ.get("extension")
        if extension:
            url = '/error/document/.%s?%s' % (extension, urllib.urlencode(d))
        else:
            url = '/error/document/?%s' % (urllib.urlencode(d))
        return url


# from pylons < 1.0
def ErrorDocuments(app, global_conf, mapper, **kw):
    """Wraps the app in error docs using Paste RecursiveMiddleware and
    ErrorDocumentsMiddleware
    """
    if global_conf is None:
        global_conf = {}

    return RecursiveMiddleware(StatusBasedForward(
        app, global_conf=global_conf, mapper=mapper, **kw))


class ProfilingMiddleware(object):
    def __init__(self, app, directory):
        self.app = app
        self.directory = directory

    def __call__(self, environ, start_response):
        import cProfile

        try:
            tmpfile = tempfile.NamedTemporaryFile(prefix='profile',
                                                  dir=self.directory,
                                                  delete=False)

            profile = cProfile.Profile()
            result = profile.runcall(self.app, environ, start_response)
            profile.dump_stats(tmpfile.name)

            return result
        finally:
            tmpfile.close()


class DomainMiddleware(object):

    def __init__(self, app, config):
        self.app = app
        self.config = config

    def __call__(self, environ, start_response):
        g = self.config['pylons.app_globals']
        http_host = environ.get('HTTP_HOST', 'localhost').lower()
        domain, s, port = http_host.partition(':')

        # remember the port
        try:
            environ['request_port'] = int(port)
        except ValueError:
            pass

        # localhost is exempt so paster run/shell will work
        # media_domain doesn't need special processing since it's just ads
        is_media_only_domain = (is_subdomain(domain, g.media_domain) and
                                g.domain != g.media_domain)
        if domain == "localhost" or is_media_only_domain:
            return self.app(environ, start_response)

        # tell reddit_base to redirect to the appropriate subreddit for
        # a legacy CNAME
        if not is_subdomain(domain, g.domain):
            environ['legacy-cname'] = domain
            return self.app(environ, start_response)

        # How many characters to chop off the end of the hostname before
        # we start looking at subdomains
        ignored_suffix_len = len(g.domain)

        # figure out what subdomain we're on, if any
        subdomains = domain[:-ignored_suffix_len - 1].split('.')

        sr_redirect = None
        prefix_parts = []
        for subdomain in subdomains[:]:
            extension = g.extension_subdomains.get(subdomain)
            # These subdomains are reserved, don't treat them as SR
            # or language subdomains.
            if subdomain in g.reserved_subdomains:
                # Some subdomains are reserved, but also can't be mixed into
                # the domain prefix for various reasons (permalinks will be
                # broken, etc.)
                if subdomain in g.ignored_subdomains:
                    continue
                prefix_parts.append(subdomain)
            elif extension:
                environ['reddit-domain-extension'] = extension
            elif is_language_subdomain(subdomain):
                environ['reddit-prefer-lang'] = subdomain
            else:
                sr_redirect = subdomain
                subdomains.remove(subdomain)

        if 'reddit-prefer-lang' in environ:
            prefix_parts.insert(0, environ['reddit-prefer-lang'])
        if prefix_parts:
            environ['reddit-domain-prefix'] = '.'.join(prefix_parts)

        # if there was a subreddit subdomain, redirect
        if sr_redirect and environ.get("FULLPATH"):
            if not subdomains and g.domain_prefix:
                subdomains.append(g.domain_prefix)
            subdomains.append(g.domain)
            redir = "%s/r/%s/%s" % ('.'.join(subdomains),
                                    sr_redirect, environ['FULLPATH'])
            redir = g.default_scheme + "://" + redir.replace('//', '/')

            start_response("301 Moved Permanently", [("Location", redir)])
            return [""]

        return self.app(environ, start_response)


class SubredditMiddleware(object):
    sr_pattern = re.compile(r'^/r/([^/]{2,})')

    def __init__(self, app):
        self.app = app

    def __call__(self, environ, start_response):
        path = environ['PATH_INFO']
        sr = self.sr_pattern.match(path)
        if sr:
            environ['subreddit'] = sr.groups()[0]
            environ['PATH_INFO'] = self.sr_pattern.sub('', path) or '/'
        return self.app(environ, start_response)


class DomainListingMiddleware(object):
    def __init__(self, app):
        self.app = app

    def __call__(self, environ, start_response):
        if not environ.has_key('subreddit'):
            path = environ['PATH_INFO']
            domain, rest = path_info_split(path)
            if domain == "domain" and rest:
                domain, rest = path_info_split(rest)
                environ['domain'] = domain
                environ['PATH_INFO'] = rest or '/'
        return self.app(environ, start_response)


class ExtensionMiddleware(object):
    ext_pattern = re.compile(r'\.([^/]+)\Z')

    def __init__(self, app):
        self.app = app

    def __call__(self, environ, start_response):
        path = environ['PATH_INFO']
        fname, sep, path_ext = path.rpartition('.')
        domain_ext = environ.get('reddit-domain-extension')

        ext = None
        if path_ext in extension_mapping:
            ext = path_ext
            # Strip off the extension.
            environ['PATH_INFO'] = path[:-(len(ext) + 1)]
        elif domain_ext in extension_mapping:
            ext = domain_ext

        if ext:
            set_extension(environ, ext)
        else:
            environ['render_style'] = 'html'
            environ['content_type'] = 'text/html; charset=UTF-8'

        return self.app(environ, start_response)

class FullPathMiddleware(object):
    # Debt: we have a lot of middleware which (unfortunately) modify the
    # global URL PATH_INFO string. To work with the original request URL, we
    # save it to a different location here.
    def __init__(self, app):
        self.app = app

    def __call__(self, environ, start_response):
        environ['FULLPATH'] = environ.get('PATH_INFO')
        qs = environ.get('QUERY_STRING')
        if qs:
            environ['FULLPATH'] += '?' + qs
        return self.app(environ, start_response)

class StaticTestMiddleware(object):
    def __init__(self, app, static_path, domain):
        self.app = app
        self.static_path = static_path
        self.domain = domain

    def __call__(self, environ, start_response):
        if environ['HTTP_HOST'] == self.domain:
            environ['PATH_INFO'] = self.static_path.rstrip('/') + environ['PATH_INFO']
            return self.app(environ, start_response)
        raise webob.exc.HTTPNotFound()


def _wsgi_json(start_response, status_int, message=""):
    status_message = webob.util.status_reasons[status_int]
    message = message or status_message

    start_response(
        "%s %s" % (status_int, status_message),
        [("Content-Type", "application/json")])

    data = simplejson.dumps({
        "error": status_int,
        "message": message
    })
    return [filters.websafe_json(data).encode("utf-8")]


class LimitUploadSize(object):
    """
    Middleware for restricting the size of uploaded files (such as
    image files for the CSS editing capability).
    """
    def __init__(self, app, max_size=1024*500):
        self.app = app
        self.max_size = max_size

    def __call__(self, environ, start_response):
        cl_key = 'CONTENT_LENGTH'
        is_error = environ.get("pylons.error_call", False)
        is_api = environ.get("render_style").startswith("api")
        if not is_error and environ['REQUEST_METHOD'] == 'POST':
            if cl_key not in environ:

                if is_api:
                    return _wsgi_json(start_response, 411)
                else:
                    start_response("411 Length Required", [])
                    return ['<html><body>length required</body></html>']

            try:
                cl_int = int(environ[cl_key])
            except ValueError:
                if is_api:
                    return _wsgi_json(start_response, 400)
                else:
                    start_response("400 Bad Request", [])
                    return ['<html><body>bad request</body></html>']

            if cl_int > self.max_size:
                error_msg = "too big. keep it under %d KiB" % (
                    self.max_size / 1024)

                if is_api:
                    return _wsgi_json(start_response, 413, error_msg)
                else:
                    start_response("413 Too Big", [])
                    return ["<html>"
                            "<head>"
                            "<script type='text/javascript'>"
                            "parent.completedUploadImage('failed',"
                            "'',"
                            "'',"
                            "[['BAD_CSS_NAME', ''], ['IMAGE_ERROR', '", error_msg,"']],"
                            "'');"
                            "</script></head><body>you shouldn\'t be here</body></html>"]

        return self.app(environ, start_response)

# TODO CleanupMiddleware seems to exist because cookie headers are being duplicated
# somewhere in the response processing chain. It should be removed as soon as we
# find the underlying issue.
class CleanupMiddleware(object):
    """
    Put anything here that should be called after every other bit of
    middleware. This currently includes the code for removing
    duplicate headers (such as multiple cookie setting).  The behavior
    here is to disregard all but the last record.
    """
    def __init__(self, app):
        self.app = app

    def __call__(self, environ, start_response):
        def custom_start_response(status, headers, exc_info = None):
            fixed = []
            seen = set()
            for head, val in reversed(headers):
                head = head.lower()
                key = (head, val.split("=", 1)[0])
                if key not in seen:
                    fixed.insert(0, (head, val))
                    seen.add(key)
            return start_response(status, fixed, exc_info)
        return self.app(environ, custom_start_response)


class SafetyMiddleware(object):
    """Clean up any attempts at response splitting in headers."""

    has_bad_characters = re.compile("[\r\n]")
    sanitizer = re.compile("[\r\n]+[ \t]*")

    def __init__(self, app):
        self.app = app

    def __call__(self, environ, start_response):
        def safe_start_response(status, headers, exc_info=None):
            sanitized = []
            for name, value in headers:
                if self.has_bad_characters.search(value):
                    value = self.sanitizer.sub("", value)
                sanitized.append((name, value))
            return start_response(status, sanitized, exc_info)
        return self.app(environ, safe_start_response)


class RedditApp(PylonsApp):

    test_mode = False

    def __init__(self, *args, **kwargs):
        super(RedditApp, self).__init__(*args, **kwargs)
        self._loading_lock = Lock()
        self._controllers = None
        self._hooks_registered = False

    def setup_app_env(self, environ, start_response):
        PylonsApp.setup_app_env(self, environ, start_response)

        if not self.test_mode:
            if self._controllers and self._hooks_registered:
                return

            with self._loading_lock:
                self.load_controllers()
                self.register_hooks()

    def _check_csrf_prevention(self):
        from r2 import controllers
        from pylons import app_globals as g

        if not g.running_as_script:
            controllers_iter = itertools.chain(
                controllers._reddit_controllers.itervalues(),
                controllers._plugin_controllers.itervalues(),
            )
            for controller in controllers_iter:
                csrf.check_controller_csrf_prevention(controller)

    def load_controllers(self):
        if self._controllers:
            return

        controllers = importlib.import_module(self.package_name +
                                              '.controllers')
        controllers.load_controllers()
        self.config['r2.plugins'].load_controllers()
        self._controllers = controllers
        self._check_csrf_prevention()

    def register_hooks(self):
        if self._hooks_registered:
            return

        hooks.register_hooks()
        self._hooks_registered = True

    def find_controller(self, controller_name):
        if controller_name in self.controller_classes:
            return self.controller_classes[controller_name]

        controller_cls = self._controllers.get_controller(controller_name)
        self.controller_classes[controller_name] = controller_cls
        return controller_cls

def make_app(global_conf, full_stack=True, **app_conf):
    """Create a Pylons WSGI application and return it

    `global_conf`
        The inherited configuration for this application. Normally from the
        [DEFAULT] section of the Paste ini file.

    `full_stack`
        Whether or not this application provides a full WSGI stack (by default,
        meaning it handles its own exceptions and errors). Disable full_stack
        when this application is "managed" by another WSGI middleware.

    `app_conf`
        The application's local configuration. Normally specified in the
        [app:<name>] section of the Paste ini file (where <name> defaults to
        main).
    """

    # Configure the Pylons environment
    config = load_environment(global_conf, app_conf)
    g = config['pylons.app_globals']

    # The Pylons WSGI app
    app = RedditApp(config=config)
    app = RoutesMiddleware(app, config["routes.map"])

    # CUSTOM MIDDLEWARE HERE (filtered by the error handling middlewares)

    # last thing first from here down
    app = CleanupMiddleware(app)

    app = LimitUploadSize(app)

    profile_directory = g.config.get('profile_directory')
    if profile_directory:
        app = ProfilingMiddleware(app, profile_directory)

    app = DomainListingMiddleware(app)
    app = SubredditMiddleware(app)
    app = ExtensionMiddleware(app)
    app = DomainMiddleware(app, config=config)

    if asbool(full_stack):
        # Handle Python exceptions
        app = ErrorHandler(app, global_conf, **config['pylons.errorware'])

        # Display error documents for 401, 403, 404 status codes (and 500 when
        # debug is disabled)
        app = ErrorDocuments(app, global_conf, error_mapper, **app_conf)

    # Establish the Registry for this application
    app = RegistryManager(app)

    # Static files
    static_app = StaticURLParser(config['pylons.paths']['static_files'])
    static_cascade = [static_app, app]

    if config['r2.plugins'] and g.config['uncompressedJS']:
        plugin_static_apps = Cascade([StaticURLParser(plugin.static_dir)
                                      for plugin in config['r2.plugins']])
        static_cascade.insert(0, plugin_static_apps)
    app = Cascade(static_cascade)

    app = FullPathMiddleware(app)

    if not g.config['uncompressedJS'] and g.config['debug']:
        static_fallback = StaticTestMiddleware(static_app, g.config['static_path'], g.config['static_domain'])
        app = Cascade([static_fallback, app])

    app = SafetyMiddleware(app)

    app.config = config

    return app
