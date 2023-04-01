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

import collections
import json
import re
import simplejson
import socket
import itertools

from Cookie import CookieError
from copy import copy
from datetime import datetime, timedelta
from functools import wraps
from hashlib import sha1, md5
from urllib import quote, unquote
from urlparse import urlparse

import babel.core
import pylibmc

from mako.filters import url_escape
from pylons import request, response
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _
from pylons.i18n.translation import LanguageError

from r2.config import feature
from r2.config.extensions import is_api, set_extension
from r2.lib import (
    baseplate_integration,
    filters,
    geoip,
    hooks,
    pages,
    ratelimit,
    utils,
)
from r2.lib.base import BaseController, abort
from r2.lib.cookies import (
    change_user_cookie_security,
    Cookies,
    Cookie,
    delete_secure_session_cookie,
    have_secure_session_cookie,
    upgrade_cookie_security,
    NEVER,
    DELETE,
)
from r2.lib.errors import (
    ErrorSet,
    BadRequestError,
    ForbiddenError,
    errors,
    reddit_http_error,
)
from r2.lib.filters import _force_utf8, _force_unicode, scriptsafe_dumps
from r2.lib.loid import LoId
from r2.lib.require import RequirementException, require, require_split
from r2.lib.strings import strings
from r2.lib.template_helpers import add_sr, JSPreload
from r2.lib.tracking import encrypt, decrypt, get_pageview_pixel_url
from r2.lib.translation import set_lang
from r2.lib.utils import (
    SimpleSillyStub,
    UniqueIterator,
    extract_subdomain,
    http_utils,
    is_subdomain,
    is_throttled,
    tup,
    UrlParser,
)
from r2.lib.validator import (
    build_arg_list,
    fullname_regex,
    valid_jsonp_callback,
    validate,
    VBoolean,
    VByName,
    VCount,
    VLang,
    VLength,
    VLimit,
    VTarget,
)
from r2.models import (
    Account,
    All,
    AllFiltered,
    AllMinus,
    DefaultSR,
    DomainSR,
    FakeAccount,
    FakeSubreddit,
    Friends,
    Frontpage,
    LabeledMulti,
    Link,
    Mod,
    ModFiltered,
    ModMinus,
    MultiReddit,
    NotFound,
    OAuth2AccessToken,
    OAuth2Client,
    OAuth2Scope,
    Random,
    RandomNSFW,
    RandomSubscription,
    Subreddit,
    valid_admin_cookie,
    valid_feed,
    valid_otp_cookie,
)
from r2.lib.db import tdb_cassandra


# Cookies which may be set in a response without making it uncacheable
CACHEABLE_COOKIES = ()


class UnloggedUser(FakeAccount):
    COOKIE_NAME = "_options"
    allowed_prefs = {
        "pref_lang": VLang.validate_lang,
        "pref_hide_locationbar": bool,
        "pref_use_global_defaults": bool,
    }

    def __init__(self, browser_langs, *a, **kw):
        FakeAccount.__init__(self, *a, **kw)
        lang = browser_langs[0] if browser_langs else g.lang
        self._defaults = self._defaults.copy()
        self._defaults['pref_lang'] = lang
        self._defaults['pref_hide_locationbar'] = False
        self._defaults['pref_use_global_defaults'] = False
        self._t.update(self._from_cookie())

    @property
    def name(self):
        raise NotImplementedError

    def _decode_json(self, json_blob):
        data = json.loads(json_blob)
        validated = {}
        for k, v in data.iteritems():
            validator = self.allowed_prefs.get(k)
            if validator:
                try:
                    validated[k] = validator(v)
                except ValueError:
                    pass  # don't override defaults for bad data
        return validated

    def _from_cookie(self):
        cookie = c.cookies.get(self.COOKIE_NAME)
        if not cookie:
            return {}

        try:
            return self._decode_json(cookie.value)
        except ValueError:
            # old-style _options cookies are encrypted
            try:
                plaintext = decrypt(cookie.value)
                values = self._decode_json(plaintext)
            except (TypeError, ValueError):
                # this cookie is totally invalid, delete it
                c.cookies[self.COOKIE_NAME] = Cookie(value="", expires=DELETE)
                return {}
            else:
                self._to_cookie(values)  # upgrade the cookie
                return values

    def _to_cookie(self, data):
        allowed_data = {k: v for k, v in data.iteritems()
                        if k in self.allowed_prefs}
        jsonified = json.dumps(allowed_data, sort_keys=True)
        c.cookies[self.COOKIE_NAME] = Cookie(value=jsonified)

    def _subscribe(self, sr):
        pass

    def _unsubscribe(self, sr):
        pass

    def _commit(self):
        if self._dirty:
            for k, (oldv, newv) in self._dirties.iteritems():
                self._t[k] = newv
            self._to_cookie(self._t)

def read_user_cookie(name):
    uname = c.user.name if c.user_is_loggedin else ""
    cookie_name = uname + '_' + name
    if cookie_name in c.cookies:
        return c.cookies[cookie_name].value
    else:
        return ''

def set_user_cookie(name, val, **kwargs):
    uname = c.user.name if c.user_is_loggedin else ""
    c.cookies[uname + '_' + name] = Cookie(value=val, **kwargs)


valid_click_cookie = fullname_regex(Link, True).match
def set_recent_clicks():
    c.recent_clicks = []
    if not c.user_is_loggedin:
        return

    click_cookie = read_user_cookie('recentclicks2')
    if click_cookie:
        if valid_click_cookie(click_cookie):
            names = [ x for x in UniqueIterator(click_cookie.split(',')) if x ]

            if len(names) > 5:
                names = names[:5]
                set_user_cookie('recentclicks2', ','.join(names))
            #eventually this will look at the user preference
            names = names[:5]

            try:
                c.recent_clicks = Link._by_fullname(names, data=True,
                                                    return_dict=False)
            except NotFound:
                # clear their cookie because it's got bad links in it
                set_user_cookie('recentclicks2', '')
        else:
            #if the cookie wasn't valid, clear it
            set_user_cookie('recentclicks2', '')

def delete_obsolete_cookies():
    for cookie_name in c.cookies:
        if cookie_name.endswith(("_last_thing", "_mod")):
            c.cookies[cookie_name] = Cookie("", expires=DELETE)

def over18():
    if c.user_is_loggedin:
        return c.user.pref_over_18 or c.user_is_admin
    else:
        if 'over18' in c.cookies:
            cookie = c.cookies['over18'].value
            if cookie == "1":
                return True
            else:
                delete_over18_cookie()


def set_over18_cookie():
    c.cookies.add("over18", "1")


def delete_over18_cookie():
    c.cookies["over18"] = Cookie(value="", expires=DELETE)


def set_obey_over18():
    "querystring parameter for API to obey over18 filtering rules"
    c.obey_over18 = request.GET.get("obey_over18") == "true"

valid_ascii_domain = re.compile(r'\A(\w[-\w]*\.)+[\w]+\Z')
def set_subreddit():
    #the r parameter gets added by javascript for API requests so we
    #can reference c.site in api.py
    sr_name = request.environ.get("subreddit", request.params.get('r'))
    domain = request.environ.get("domain")

    can_stale = request.method.upper() in ('GET', 'HEAD')

    c.site = Frontpage
    if not sr_name:
        #check for cnames
        cname = request.environ.get('legacy-cname')
        if cname:
            sr = Subreddit._by_domain(cname) or Frontpage
            domain = g.domain
            if g.domain_prefix:
                domain = ".".join((g.domain_prefix, domain))
            path = '%s://%s%s' % (g.default_scheme, domain, sr.path)
            abort(301, location=BaseController.format_output_url(path))
    elif '+' in sr_name:
        name_filter = lambda name: Subreddit.is_valid_name(name,
            allow_language_srs=True)
        sr_names = filter(name_filter, sr_name.split('+'))
        srs = Subreddit._by_name(sr_names, stale=can_stale).values()
        if All in srs:
            c.site = All
        elif Friends in srs:
            c.site = Friends
        else:
            srs = [sr for sr in srs if not isinstance(sr, FakeSubreddit)]
            if len(srs) == 1:
                c.site = srs[0]
            elif srs:
                found = {sr.name.lower() for sr in srs}
                sr_names = filter(lambda name: name.lower() in found, sr_names)
                sr_name = '+'.join(sr_names)
                multi_path = '/r/' + sr_name
                c.site = MultiReddit(multi_path, srs)
            elif not c.error_page:
                abort(404)
    elif '-' in sr_name:
        sr_names = sr_name.split('-')
        base_sr_name, exclude_sr_names = sr_names[0], sr_names[1:]
        srs = Subreddit._by_name(sr_names, stale=can_stale)
        base_sr = srs.pop(base_sr_name, None)
        exclude_srs = [sr for sr in srs.itervalues()
                          if not isinstance(sr, FakeSubreddit)]

        if base_sr == All:
            if exclude_srs:
                c.site = AllMinus(exclude_srs)
            else:
                c.site = All
        elif base_sr == Mod:
            if exclude_srs:
                c.site = ModMinus(exclude_srs)
            else:
                c.site = Mod
        else:
            path = "/subreddits/search?q=%s" % sr_name
            abort(302, location=BaseController.format_output_url(path))
    else:
        try:
            c.site = Subreddit._by_name(sr_name, stale=can_stale)
        except NotFound:
            if Subreddit.is_valid_name(sr_name):
                path = "/subreddits/search?q=%s" % sr_name
                abort(302, location=BaseController.format_output_url(path))
            elif not c.error_page and not request.path.startswith("/api/login/") :
                abort(404)

    #if we didn't find a subreddit, check for a domain listing
    if not sr_name and isinstance(c.site, DefaultSR) and domain:
        # Redirect IDN to their IDNA name if necessary
        try:
            idna = _force_unicode(domain).encode("idna")
            if idna != domain:
                path_info = request.environ["PATH_INFO"]
                path = "/domain/%s%s" % (idna, path_info)
                abort(302, location=BaseController.format_output_url(path))
        except UnicodeError:
            domain = ''  # Ensure valid_ascii_domain fails
        if not c.error_page and not valid_ascii_domain.match(domain):
            abort(404)
        c.site = DomainSR(domain)

    if isinstance(c.site, FakeSubreddit):
        c.default_sr = True

_FILTER_SRS = {"mod": ModFiltered, "all": AllFiltered}
def set_multireddit():
    routes_dict = request.environ["pylons.routes_dict"]
    if "multipath" in routes_dict or ("m" in request.GET and is_api()):
        fullpath = routes_dict.get("multipath", "").lower()
        multipaths = fullpath.split("+")
        multi_ids = None
        logged_in_username = c.user.name.lower() if c.user_is_loggedin else None
        multiurl = None

        if c.user_is_loggedin and routes_dict.get("my_multi"):
            multi_ids = ["/user/%s/m/%s" % (logged_in_username, multipath)
                         for multipath in multipaths]
            multiurl = "/me/m/" + fullpath
        elif "username" in routes_dict:
            username = routes_dict["username"].lower()

            if c.user_is_loggedin:
                # redirect /user/foo/m/... to /me/m/... for user foo.
                if username == logged_in_username and not is_api():
                    # trim off multi id
                    url_parts = request.path_qs.split("/")[5:]
                    url_parts.insert(0, "/me/m/%s" % fullpath)
                    path = "/".join(url_parts)
                    abort(302, location=BaseController.format_output_url(path))

            multiurl = "/user/" + username + "/m/" + fullpath
            multi_ids = ["/user/%s/m/%s" % (username, multipath)
                        for multipath in multipaths]
        elif "m" in request.GET and is_api():
            # Only supported via API as we don't have a valid non-query
            # parameter equivalent for cross-user multis, which means
            # we can't generate proper links to /new, /top, etc in HTML
            multi_ids = [m.lower() for m in request.GET.getall("m") if m]
            multiurl = ""

        if multi_ids is not None:
            multis = LabeledMulti._byID(multi_ids, return_dict=False) or []
            multis = [m for m in multis if m.can_view(c.user)]
            if not multis:
                abort(404)
            elif len(multis) == 1:
                c.site = multis[0]
            else:
                sr_ids = Subreddit.random_reddits(
                    logged_in_username,
                    list(set(itertools.chain.from_iterable(
                        multi.sr_ids for multi in multis
                    ))),
                    LabeledMulti.MAX_SR_COUNT,
                )
                srs = Subreddit._byID(sr_ids, data=True, return_dict=False)
                c.site = MultiReddit(multiurl, srs)
                if any(m.weighting_scheme == "fresh" for m in multis):
                    c.site.weighting_scheme = "fresh"

    elif "filtername" in routes_dict:
        if not c.user_is_loggedin:
            abort(404)
        filtername = routes_dict["filtername"].lower()
        filtersr = _FILTER_SRS.get(filtername)
        if not filtersr:
            abort(404)
        c.site = filtersr()


def set_content_type():
    e = request.environ
    c.render_style = e['render_style']
    response.content_type = e['content_type']

    if e.has_key('extension'):
        c.extension = ext = e['extension']
        if ext in ('embed', 'widget'):
            wrapper = request.params.get("callback", "document.write")
            wrapper = filters._force_utf8(wrapper)
            if not valid_jsonp_callback(wrapper):
                abort(BadRequestError(errors.BAD_JSONP_CALLBACK))

            # force logged-out state since these can be accessed cross-domain
            c.user = UnloggedUser(get_browser_langs())
            c.user_is_loggedin = False
            c.forced_loggedout = True

            def to_js(content):
                # Add a comment to the beginning to prevent the "Rosetta Flash"
                # XSS when an attacker controls the beginning of a resource
                return "/**/" + wrapper + "(" + utils.string2js(content) + ");"

            c.response_wrapper = to_js
        if ext in ("rss", "api", "json") and request.method.upper() == "GET":
            user = valid_feed(request.GET.get("user"),
                              request.GET.get("feed"),
                              request.path)
            if user and not g.read_only_mode:
                c.user = user
                c.user_is_loggedin = True
        if ext in ("mobile", "m") and not request.GET.get("keep_extension"):
            try:
                if request.cookies['reddit_mobility'] == "compact":
                    c.extension = "compact"
                    c.render_style = "compact"
            except (ValueError, KeyError):
                c.suggest_compact = True
        if ext in ("mobile", "m", "compact"):
            if request.GET.get("keep_extension"):
                c.cookies['reddit_mobility'] = Cookie(ext, expires=NEVER)

    # allow content and api calls to set an loid
    if is_api() or c.render_style in ("html", "mobile", "compact"):
        c.loid = LoId.load(request, c)

    # allow JSONP requests to generate callbacks, but do not allow
    # the user to be logged in for these
    callback = request.GET.get("jsonp")
    if is_api() and request.method.upper() == "GET" and callback:
        if not valid_jsonp_callback(callback):
            abort(BadRequestError(errors.BAD_JSONP_CALLBACK))
        c.allowed_callback = callback
        c.user = UnloggedUser(get_browser_langs())
        c.user_is_loggedin = False
        c.forced_loggedout = True
        response.content_type = "application/javascript"

def get_browser_langs():
    browser_langs = []
    langs = request.environ.get('HTTP_ACCEPT_LANGUAGE')
    if langs:
        langs = langs.split(',')
        browser_langs = []
        seen_langs = set()
        # extract languages from browser string
        for l in langs:
            if ';' in l:
                l = l.split(';')[0]
            if l not in seen_langs and l in g.languages:
                browser_langs.append(l)
                seen_langs.add(l)
            if '-' in l:
                l = l.split('-')[0]
            if l not in seen_langs and l in g.languages:
                browser_langs.append(l)
                seen_langs.add(l)
    return browser_langs

def set_iface_lang():
    host_lang = request.environ.get('reddit-prefer-lang')
    lang = host_lang or c.user.pref_lang

    if getattr(g, "lang_override") and lang == "en":
        lang = g.lang_override

    c.lang = lang

    try:
        set_lang(lang, fallback_lang=g.lang)
    except LanguageError:
        lang = g.lang
        set_lang(lang, graceful_fail=True)

    try:
        c.locale = babel.core.Locale.parse(lang, sep='-')
    except (babel.core.UnknownLocaleError, ValueError):
        c.locale = babel.core.Locale.parse(g.lang, sep='-')


def set_colors():
    theme_rx = re.compile(r'')
    color_rx = re.compile(r'\A([a-fA-F0-9]){3}(([a-fA-F0-9]){3})?\Z')
    c.theme = None
    if color_rx.match(request.GET.get('bgcolor') or ''):
        c.bgcolor = request.GET.get('bgcolor')
    if color_rx.match(request.GET.get('bordercolor') or ''):
        c.bordercolor = request.GET.get('bordercolor')


def ratelimit_agent(agent, limit=10, slice_size=10):

    # Ensure the agent regex is a valid memcached key
    h = md5()
    h.update(agent)
    hashed_agent = h.hexdigest()

    slice_size = min(slice_size, 60)
    time_slice = ratelimit.get_timeslice(slice_size)
    usage = ratelimit.record_usage("rl-agent-" + hashed_agent, time_slice)
    if usage > limit:
        request.environ['retry_after'] = time_slice.remaining
        abort(429)


appengine_re = re.compile(r'AppEngine-Google; \(\+http://code.google.com/appengine; appid: (?:dev|s)~([a-z0-9-]{6,30})\)\Z')
def ratelimit_agents():
    user_agent = request.user_agent

    if not user_agent:
        return

    # parse out the appid for appengine apps
    appengine_match = appengine_re.search(user_agent)
    if appengine_match:
        appid = appengine_match.group(1)
        ratelimit_agent(appid)
        return

    # Search anywhere in the useragent for the given regex
    for agent_re, limit in g.user_agent_ratelimit_regexes.iteritems():
        if agent_re.search(user_agent):
            ratelimit_agent(agent_re.pattern, limit)
            return


def ratelimit_throttled():
    ip = request.ip.strip()
    if is_throttled(ip):
        abort(429)


def paginated_listing(default_page_size=25, max_page_size=100, backend='sql'):
    def decorator(fn):
        @validate(num=VLimit('limit', default=default_page_size,
                             max_limit=max_page_size),
                  after=VByName('after', backend=backend),
                  before=VByName('before', backend=backend),
                  count=VCount('count'),
                  target=VTarget("target"),
                  sr_detail=VBoolean(
                      "sr_detail", docs={"sr_detail": "(optional) expand subreddits"}),
                  show=VLength('show', 3, empty_error=None,
                               docs={"show": "(optional) the string `all`"}),
        )
        @wraps(fn)
        def new_fn(self, before, **env):
            if c.render_style == "htmllite":
                c.link_target = env.get("target")
            elif "target" in env:
                del env["target"]

            if "show" in env and env['show'] == 'all':
                c.ignore_hide_rules = True
            kw = build_arg_list(fn, env)

            #turn before into after/reverse
            kw['reverse'] = False
            if before:
                kw['after'] = before
                kw['reverse'] = True

            return fn(self, **kw)

        if hasattr(fn, "_api_doc"):
            notes = fn._api_doc["notes"] or []
            if paginated_listing.doc_note not in notes:
                notes.append(paginated_listing.doc_note)
            fn._api_doc["notes"] = notes

        return new_fn
    return decorator

paginated_listing.doc_note = "*This endpoint is [a listing](#listings).*"

#TODO i want to get rid of this function. once the listings in front.py are
#moved into listingcontroller, we shouldn't have a need for this
#anymore
def base_listing(fn):
    return paginated_listing()(fn)

def is_trusted_origin(origin):
    try:
        origin = urlparse(origin)
    except ValueError:
        return False

    return any(is_subdomain(origin.hostname, domain) for domain in g.trusted_domains)

def cross_domain(origin_check=is_trusted_origin, **options):
    """Set up cross domain validation and hoisting for a request handler."""
    def cross_domain_wrap(fn):
        cors_perms = {
            "origin_check": origin_check,
            "allow_credentials": bool(options.get("allow_credentials"))
        }

        @wraps(fn)
        def cross_domain_handler(self, *args, **kwargs):
            if request.params.get("hoist") == "cookie":
                # Cookie polling response
                if cors_perms["origin_check"](g.origin):
                    name = request.environ["pylons.routes_dict"]["action_name"]
                    resp = fn(self, *args, **kwargs)
                    c.cookies.add('hoist_%s' % name, ''.join(tup(resp)))
                    response.content_type = 'text/html'
                    return ""
                else:
                    abort(403)
            else:
                self.check_cors()
                return fn(self, *args, **kwargs)

        cross_domain_handler.cors_perms = cors_perms
        return cross_domain_handler
    return cross_domain_wrap


def make_url_https(url):
    """Turn a possibly relative URL into a fully-qualified HTTPS URL."""
    new_url = UrlParser(url)
    new_url.scheme = "https"
    if not new_url.hostname:
        new_url.hostname = request.host.lower()
    return new_url.unparse()


def generate_modhash():
    # OAuth clients should never receive a modhash of any kind as they could
    # use it in a CSRF attack to bypass their permitted OAuth scopes
    if c.oauth_user:
        return None

    modhash = hooks.get_hook("modhash.generate").call_until_return()
    if modhash is not None:
        return modhash

    # if no plugins generate a modhash, just use the user name
    return c.user.name


def enforce_https():
    """Enforce policy for forced usage of HTTPS."""

    # OAuth HTTPS enforcement is dealt with elsewhere
    if c.oauth_user:
        return

    # This is likely a cross-domain request, the initiator has no way of
    # respecting the user's HTTPS preferences and redirecting them is unlikely
    # to stop them from making future requests via HTTP.
    if c.forced_loggedout or c.render_style == "js":
        return

    # It's not possible to redirect inside the error handler
    if request.environ.get('pylons.error_call', False):
        return

    is_api_request = is_api() or request.path.startswith("/api/")
    redirect_url = None

    # This is likely a request from an API client. Redirecting them or giving
    # them an HSTS grant is unlikely to stop them from making requests to HTTP.
    if is_api_request and not c.secure:
        # Record the violation so we know who to talk to to get this fixed.
        # This is preferable to redirecting insecure API reqs right away
        # because a lot of clients just break on redirect, it would create two
        # requests for every request, and it wouldn't increase security.
        ua = request.user_agent
        g.stats.count_string('https.security_violation', ua)
        # It's especially bad to send credentials over HTTP
        if c.user_is_loggedin:
            g.stats.count_string('https.loggedin_security_violation', ua)

        # They didn't send a login cookie, but their cookies indicate they won't
        # be authed properly unless we redirect them to the secure version.
        if have_secure_session_cookie() and not c.user_is_loggedin:
            redirect_url = make_url_https(request.fullurl)

    # These are all safe to redirect to HTTPS
    if c.render_style in {"html", "compact", "mobile"} and not is_api_request:
        want_redirect = (feature.is_enabled("force_https") or
                         feature.is_enabled("https_redirect"))
        if not c.secure and want_redirect:
            redirect_url = make_url_https(request.fullurl)

    if redirect_url:
        headers = {"Cache-Control": "private, no-cache", "Pragma": "no-cache"}
        # Browsers change the method to GET on 301, and 308 is ill-supported.
        status_code = 301 if request.method == "GET" else 307
        abort(status_code, location=redirect_url, headers=headers)


def require_https():
    if not c.secure:
        abort(ForbiddenError(errors.HTTPS_REQUIRED))


def require_domain(required_domain):
    if not is_subdomain(request.host, required_domain):
        abort(ForbiddenError(errors.WRONG_DOMAIN))


def disable_subreddit_css():
    def wrap(f):
        @wraps(f)
        def no_funny_business(*args, **kwargs):
            c.allow_styles = False
            return f(*args, **kwargs)
        return no_funny_business
    return wrap


def request_timer_name(action):
    return "service_time.web." + action


def flatten_response(content):
    """Convert a content iterable to a string, properly handling unicode."""
    # TODO: it would be nice to replace this with response.body someday
    # once unicode issues are ironed out.
    return "".join(_force_utf8(x) for x in tup(content) if x)


def abort_with_error(error, code=None):
    if not code and not error.code:
        raise ValueError('Error %r missing status code' % error)

    abort(reddit_http_error(
        code=code or error.code,
        error_name=error.name,
        explanation=error.message,
        fields=error.fields,
    ))


class MinimalController(BaseController):

    allow_stylesheets = False
    defer_ratelimiting = False

    def run_sitewide_ratelimits(self):
        """Ratelimit users and add ratelimit headers to the response.

        Headers added are:
        X-Ratelimit-Used: Number of requests used in this period
        X-Ratelimit-Remaining: Number of requests left to use
        X-Ratelimit-Reset: Approximate number of seconds to end of period

        This function only has an effect if one of
        g.RL_SITEWIDE_ENABLED or g.RL_OAUTH_SITEWIDE_ENABLED
        are set to 'true' in the app configuration

        If the ratelimit is exceeded, a 429 response will be sent,
        unless the app configuration has g.ENFORCE_RATELIMIT off.
        Headers will be sent even on aborted requests.

        """
        if c.error_page:
            # ErrorController is re-running pre, don't double ratelimit
            return

        if c.oauth_user and g.RL_OAUTH_SITEWIDE_ENABLED:
            type_ = "oauth"
            period = g.RL_OAUTH_RESET_SECONDS
            max_reqs = c.oauth2_client._max_reqs
            # Convert client_id to ascii str for use as memcache key
            client_id = c.oauth2_access_token.client_id.encode("ascii")
            # OAuth2 ratelimits are per user-app combination
            key = 'siterl-oauth-' + c.user._id36 + ":" + client_id
        elif c.cdn_cacheable:
            type_ = "cdn"
        elif not is_api():
            type_ = "web"
        elif g.RL_SITEWIDE_ENABLED:
            type_ = "api"
            max_reqs = g.RL_MAX_REQS
            period = g.RL_RESET_SECONDS
            # API (non-oauth) limits are per-ip
            key = 'siterl-api-' + request.ip
        else:
            type_ = "none"

        g.stats.event_count("ratelimit.type", type_, sample_rate=0.01)
        if type_ in ("cdn", "web", "none"):
            # No ratelimiting or headers for:
            # * Web requests (HTML)
            # * CDN requests (logged out via www.reddit.com)
            return

        time_slice = ratelimit.get_timeslice(period)

        try:
            recent_reqs = ratelimit.record_usage(key, time_slice)
        except ratelimit.RatelimitError as e:
            # Ratelimiting is non-critical; if the system is
            # having issues, just skip adding the headers
            g.log.info("ratelimit error: %s", e)
            return
        reqs_remaining = max(0, max_reqs - recent_reqs)

        c.ratelimit_headers = {
            "X-Ratelimit-Used": str(recent_reqs),
            "X-Ratelimit-Reset": str(time_slice.remaining),
            "X-Ratelimit-Remaining": str(reqs_remaining),
        }

        event_type = None

        if reqs_remaining <= 0:
            if recent_reqs > (2 * max_reqs):
                event_type = "hyperbolic"
            else:
                event_type = "over"
            if g.ENFORCE_RATELIMIT:
                # For non-abort situations, the headers will be added in post()
                request.environ['retry_after'] = time_slice.remaining
                response.headers.update(c.ratelimit_headers)
                abort(429)
        elif reqs_remaining < (0.1 * max_reqs):
            event_type = "close"

        if event_type is not None:
            g.stats.event_count("ratelimit.exceeded", event_type)
            if type_ == "oauth":
                g.stats.count_string("oauth.{}".format(event_type), client_id)

    def pre(self):
        action = request.environ["pylons.routes_dict"].get("action")
        if action:
            if not self._get_action_handler():
                action = 'invalid'
            controller = request.environ["pylons.routes_dict"]["controller"]
            key = "{}.{}".format(controller, action)
            c.request_timer = g.stats.get_timer(request_timer_name(key))
        else:
            c.request_timer = SimpleSillyStub()

        baseplate_integration.make_server_span(span_name=key).start()

        c.response_wrapper = None
        c.start_time = datetime.now(g.tz)
        c.request_timer.start()
        g.reset_caches()

        c.domain_prefix = request.environ.get("reddit-domain-prefix",
                                              g.domain_prefix)
        c.secure = request.environ["wsgi.url_scheme"] == "https"
        c.request_origin = request.host_url

        #check if user-agent needs a dose of rate-limiting
        if not c.error_page:
            ratelimit_throttled()
            ratelimit_agents()

        # Allow opting out of the `websafe_json` madness
        if "WANT_RAW_JSON" not in request.environ:
            want_raw_json = request.params.get("raw_json", "") == "1"
            request.environ["WANT_RAW_JSON"] = want_raw_json

        c.allow_framing = False

        # According to http://www.w3.org/TR/2014/WD-referrer-policy-20140807/
        # we really want "origin-when-crossorigin", but that isn't widely
        # supported yet.
        c.referrer_policy = "origin"

        c.cdn_cacheable = (request.via_cdn and
                           g.login_cookie not in request.cookies)

        c.extension = request.environ.get('extension')
        # the domain has to be set before Cookies get initialized
        set_subreddit()
        c.subdomain = extract_subdomain()
        c.errors = ErrorSet()
        c.cookies = Cookies()
        # if an rss feed, this will also log the user in if a feed=
        # GET param is included
        set_content_type()

        c.request_timer.intermediate("minimal-pre")
        # True/False forces. None updates for most non-POST requests
        c.update_last_visit = None

        if is_subdomain(request.host, g.oauth_domain):
            self.check_cors()

        if not self.defer_ratelimiting:
            self.run_sitewide_ratelimits()
            c.request_timer.intermediate("minimal-ratelimits")

        hooks.get_hook("reddit.request.minimal_begin").call()

    def post(self):
        c.request_timer.intermediate("action")

        # if the action raised an HTTPException (i.e. it aborted) then pylons
        # will have replaced response with the exception itself.
        c.is_exception_response = getattr(response, "_exception", False)

        if c.response_wrapper and not c.is_exception_response:
            content = flatten_response(response.content)
            wrapped_content = c.response_wrapper(content)
            response.content = wrapped_content

        # we need to not add X-Frame-Options to requests (such as media embeds)
        # that intend to allow framing.
        if not c.allow_framing:
            response.headers["X-Frame-Options"] = "SAMEORIGIN"

        # set some headers related to client security
        response.headers['X-Content-Type-Options'] = 'nosniff'
        response.headers['X-XSS-Protection'] = '1; mode=block'

        if (feature.is_enabled("force_https")
                and feature.is_enabled("upgrade_cookies")):
            upgrade_cookie_security()

        # Don't poison the cache with uncacheable cookies
        dirty_cookies = (k for k, v in c.cookies.iteritems() if v.dirty)
        would_poison = any((k not in CACHEABLE_COOKIES) for k in dirty_cookies)

        if c.user_is_loggedin or would_poison:
            # Based off logged in <https://en.wikipedia.org/>,
            # must-revalidate might not be necessary, but should force
            # similar behaviour to no-cache (in theory.)
            # Normally you'd prefer `no-store`, but many of reddit's
            # listings are ephemeral, and the content might not even
            # exist anymore if you force a refresh when hitting back.
            cache_control = (
                'private',
                's-maxage=0',
                'max-age=0',
                'must-revalidate',
            )
            response.headers['Expires'] = '-1'
            response.headers['Cache-Control'] = ', '.join(cache_control)

        if c.ratelimit_headers:
            response.headers.update(c.ratelimit_headers)

        # write loid cookie if necessary
        if c.loid:
            c.loid.save(domain=g.domain)

        # send cookies
        secure_cookies = feature.is_enabled("force_https")
        for k, v in c.cookies.iteritems():
            if v.dirty:
                v_secure = v.secure if v.secure is not None else secure_cookies
                response.set_cookie(key=k,
                                    value=quote(v.value),
                                    domain=v.domain,
                                    expires=v.expires,
                                    secure=v_secure,
                                    httponly=getattr(v, 'httponly', False))


        if not isinstance(c.site, FakeSubreddit) and not g.disallow_db_writes:
            if c.user_is_loggedin:
                c.site.record_visitor_activity("logged_in", c.user._fullname)

        if self.should_update_last_visit():
            c.user.update_last_visit(c.start_time)

        hooks.get_hook("reddit.request.end").call()

        # this thread is probably going to be reused, but it could be
        # a while before it is. So we might as well dump the cache in
        # the mean time so that we don't have dead objects hanging
        # around taking up memory
        g.reset_caches()

        c.request_timer.intermediate("post")

        # add tags to the trace
        c.trace.set_tag("user", c.user._fullname if c.user_is_loggedin else None)
        c.trace.set_tag("render_style", c.render_style)

        # push data to statsd
        baseplate_integration.finish_server_span()
        c.request_timer.stop()
        g.stats.flush()

    def on_validation_error(self, error):
        if error.name == errors.USER_REQUIRED:
            self.intermediate_redirect('/login')
        elif error.name == errors.VERIFIED_USER_REQUIRED:
            self.intermediate_redirect('/verify')

    def abort404(self):
        abort(404, "not found")

    def abort403(self):
        abort(403, "forbidden")

    COMMON_REDDIT_HEADERS = ", ".join((
        "X-Ratelimit-Used",
        "X-Ratelimit-Remaining",
        "X-Ratelimit-Reset",
        "X-Moose",
    ))

    def check_cors(self):
        origin = request.headers.get("Origin")
        if c.cors_checked or not origin:
            return

        method = request.method
        if method == 'OPTIONS':
            # preflight request
            method = request.headers.get("Access-Control-Request-Method")
            if not method:
                self.abort403()

        via_oauth = is_subdomain(request.host, g.oauth_domain)
        if via_oauth:
            response.headers["Access-Control-Allow-Origin"] = "*"
            response.headers["Access-Control-Allow-Methods"] = \
                "GET, POST, PUT, PATCH, DELETE"
            response.headers["Access-Control-Allow-Headers"] = \
                "Authorization, "
            response.headers["Access-Control-Allow-Credentials"] = "false"
            response.headers['Access-Control-Expose-Headers'] = \
                self.COMMON_REDDIT_HEADERS
        else:
            action = request.environ["pylons.routes_dict"]["action_name"]

            handler = self._get_action_handler(action, method)
            cors = handler and getattr(handler, "cors_perms", None)

            if cors and cors["origin_check"](origin):
                response.headers["Access-Control-Allow-Origin"] = origin
                if cors.get("allow_credentials"):
                    response.headers["Access-Control-Allow-Credentials"] = "true"
        c.cors_checked = True

    def OPTIONS(self):
        """Return empty responses for CORS preflight requests"""
        self.check_cors()

    def update_qstring(self, dict):
        merged = copy(request.GET)
        merged.update(dict)
        return request.path + utils.query_string(merged)

    def api_wrapper(self, kw):
        if request.environ.get("WANT_RAW_JSON"):
            return scriptsafe_dumps(kw)
        return filters.websafe_json(simplejson.dumps(kw))

    def should_update_last_visit(self):
        if g.disallow_db_writes:
            return False

        if not c.user_is_loggedin:
            return False

        if c.update_last_visit is not None:
            return c.update_last_visit

        return request.method.upper() != "POST"


class OAuth2ResourceController(MinimalController):
    defer_ratelimiting = True

    def authenticate_with_token(self):
        set_extension(request.environ, "json")
        set_content_type()
        require_https()
        require_domain(g.oauth_domain)

        try:
            access_token = OAuth2AccessToken.get_token(self._get_bearer_token())
            require(access_token)
            require(access_token.check_valid())
            c.oauth2_access_token = access_token
            if access_token.user_id:
                account = Account._byID36(access_token.user_id, data=True)
                require(account)
                require(not account._deleted)
                c.user = c.oauth_user = account
                c.user_is_loggedin = True
            else:
                c.user = UnloggedUser(get_browser_langs())
                c.user_is_loggedin = False
            c.oauth2_client = OAuth2Client._byID(access_token.client_id)
        except RequirementException:
            self._auth_error(401, "invalid_token")

        handler = self._get_action_handler()
        if handler:
            oauth2_perms = getattr(handler, "oauth2_perms", {})
            if oauth2_perms.get("oauth2_allowed", False):
                grant = OAuth2Scope(access_token.scope)
                required = set(oauth2_perms['required_scopes'])
                if not grant.has_access(c.site.name, required):
                    self._auth_error(403, "insufficient_scope")
                c.oauth_scope = grant
            else:
                self._auth_error(400, "invalid_request")

    def check_for_bearer_token(self):
        if self._get_bearer_token(strict=False):
            self.authenticate_with_token()

    def _auth_error(self, code, error):
        abort(code, headers=[("WWW-Authenticate", 'Bearer realm="reddit", error="%s"' % error)])

    def _get_bearer_token(self, strict=True):
        auth = request.headers.get("Authorization")
        if not auth:
            return None
        try:
            auth_scheme, bearer_token = require_split(auth, 2)
            require(auth_scheme.lower() == "bearer")
            return bearer_token
        except RequirementException:
            if strict:
                self._auth_error(400, "invalid_request")
            else:
                return None

    def set_up_user_context(self):
        if c.user.inbox_count > 0:
            c.have_messages = True
        c.have_mod_messages = bool(c.user.modmsgtime)

        c.user_special_distinguish = c.user.special_distinguish()


class OAuth2OnlyController(OAuth2ResourceController):
    """Base controller for endpoints that may only be accessed via OAuth 2"""

    # OAuth2 doesn't rely on ambient credentials for authentication,
    # so CSRF prevention is unnecessary.
    handles_csrf = True

    def pre(self):
        OAuth2ResourceController.pre(self)
        if request.method != "OPTIONS":
            self.authenticate_with_token()
            self.set_up_user_context()
            self.run_sitewide_ratelimits()

    def on_validation_error(self, error):
        abort_with_error(error, error.code or 400)


class RedditController(OAuth2ResourceController):

    @staticmethod
    def login(user, rem=False):
        # This can't be handled in post() due to PRG and ErrorController fun.
        user.update_last_visit(c.start_time)
        force_https = feature.is_enabled("force_https", user)
        c.cookies[g.login_cookie] = Cookie(value=user.make_cookie(),
                                           expires=NEVER if rem else None,
                                           httponly=True,
                                           secure=force_https)
        # Make sure user-specific cookies get the secure flag set properly
        change_user_cookie_security(secure=force_https, remember=rem)

    @staticmethod
    def logout():
        c.cookies[g.login_cookie] = Cookie(value='', expires=DELETE)
        delete_secure_session_cookie()

    @staticmethod
    def enable_admin_mode(user, first_login=None):
        # no expiration time so the cookie dies with the browser session
        admin_cookie = user.make_admin_cookie(first_login=first_login)
        c.cookies[g.admin_cookie] = Cookie(
            value=admin_cookie,
            httponly=True,
            secure=feature.is_enabled("force_https"),
        )

    @staticmethod
    def remember_otp(user):
        cookie = user.make_otp_cookie()
        expiration = datetime.utcnow() + timedelta(seconds=g.OTP_COOKIE_TTL)
        set_user_cookie(g.otp_cookie,
                        cookie,
                        secure=True,
                        httponly=True,
                        expires=expiration)

    @staticmethod
    def disable_admin_mode(user):
        c.cookies[g.admin_cookie] = Cookie(value='', expires=DELETE)

    def pre(self):
        record_timings = g.admin_cookie in request.cookies or g.debug
        admin_bar_eligible = response.content_type == 'text/html'
        if admin_bar_eligible and record_timings:
            g.stats.start_logging_timings()

        # set up stuff needed in base templates at error time here.
        c.js_preload = JSPreload()

        MinimalController.pre(self)

        # Set IE to always use latest rendering engine
        response.headers["X-UA-Compatible"] = "IE=edge"

        # populate c.cookies unless we're on the unsafe media_domain
        if request.host != g.media_domain or g.media_domain == g.domain:
            cookie_counts = collections.Counter()
            for k, v in request.cookies.iteritems():
                # minimalcontroller can still set cookies
                if k not in c.cookies:
                    # we can unquote even if it's not quoted
                    c.cookies[k] = Cookie(value=unquote(v), dirty=False)
                    cookie_counts[Cookie.classify(k)] += 1

            for cookietype, count in cookie_counts.iteritems():
                g.stats.simple_event("cookie.%s" % cookietype, count)

        delete_obsolete_cookies()

        # the user could have been logged in via one of the feeds
        maybe_admin = False
        is_otpcookie_valid = False

        self.check_for_bearer_token()

        # no logins for RSS feed unless valid_feed has already been called
        if not c.user:
            if c.extension != "rss":
                if not g.read_only_mode:
                    c.user = g.auth_provider.get_authenticated_account()

                    if c.user and c.user._deleted:
                        c.user = None
                else:
                    c.user = None
                c.user_is_loggedin = bool(c.user)

                admin_cookie = c.cookies.get(g.admin_cookie)
                if c.user_is_loggedin and admin_cookie:
                    maybe_admin, first_login = valid_admin_cookie(admin_cookie.value)

                    if maybe_admin:
                        self.enable_admin_mode(c.user, first_login=first_login)
                    else:
                        self.disable_admin_mode(c.user)

                otp_cookie = read_user_cookie(g.otp_cookie)
                if c.user_is_loggedin and otp_cookie:
                    is_otpcookie_valid = valid_otp_cookie(otp_cookie)

            if not c.user:
                c.user = UnloggedUser(get_browser_langs())
                # patch for fixing mangled language preferences
                if not isinstance(c.user.pref_lang, basestring):
                    c.user.pref_lang = g.lang
                    c.user._commit()

        if c.user_is_loggedin:
            self.set_up_user_context()
            c.modhash = generate_modhash()
            c.user_is_admin = maybe_admin and c.user.name in g.admins
            c.user_is_sponsor = c.user_is_admin or c.user.name in g.sponsors
            c.otp_cached = is_otpcookie_valid

        enforce_https()

        c.request_timer.intermediate("base-auth")

        self.run_sitewide_ratelimits()
        c.request_timer.intermediate("base-ratelimits")

        c.over18 = over18()
        set_obey_over18()

        # looking up the multireddit requires c.user.
        set_multireddit()

        #set_browser_langs()
        set_iface_lang()
        set_recent_clicks()
        # used for HTML-lite templates
        set_colors()

        # set some environmental variables in case we hit an abort
        if not isinstance(c.site, FakeSubreddit):
            request.environ['REDDIT_NAME'] = c.site.name

        # random reddit trickery
        if c.site == Random:
            c.site = Subreddit.random_reddit(user=c.user)
            site_path = c.site.path.strip('/')
            path = "/" + site_path + request.path_qs
            abort(302, location=self.format_output_url(path))
        elif c.site == RandomSubscription:
            if not c.user.gold:
                abort(302, location=self.format_output_url('/gold/about'))
            c.site = Subreddit.random_subscription(c.user)
            site_path = c.site.path.strip('/')
            path = '/' + site_path + request.path_qs
            abort(302, location=self.format_output_url(path))
        elif c.site == RandomNSFW:
            c.site = Subreddit.random_reddit(over18=True, user=c.user)
            site_path = c.site.path.strip('/')
            path = '/' + site_path + request.path_qs
            abort(302, location=self.format_output_url(path))

        if not request.path.startswith("/api/login/"):
            # is the subreddit banned?
            if c.site.spammy() and not c.user_is_admin and not c.error_page:
                ban_info = getattr(c.site, "ban_info", {})
                if "message" in ban_info and ban_info['message']:
                    message = ban_info['message']
                else:
                    message = None

                errpage = pages.InterstitialPage(
                    _("banned"),
                    content=pages.BannedInterstitial(
                        message=message,
                        ban_time=ban_info.get("banned_at"),
                    ),
                )

                request.environ['usable_error_content'] = errpage.render()
                self.abort404()

            # check if the user has access to this subreddit
            # Allow OPTIONS requests through, as no response body
            # is sent in those cases - just a set of headers
            if (not c.site.can_view(c.user) and not c.error_page and
                    request.method != "OPTIONS"):
                allowed_to_view = c.site.is_allowed_to_view(c.user)

                if isinstance(c.site, LabeledMulti):
                    # do not leak the existence of multis via 403.
                    self.abort404()
                elif not allowed_to_view and c.site.type == 'gold_only':
                    errpage = pages.InterstitialPage(
                        _("gold members only"),
                        content=pages.GoldOnlyInterstitial(
                            sr_name=c.site.name,
                            sr_description=c.site.public_description,
                        ),
                    )
                    request.environ['usable_error_content'] = errpage.render()
                    self.abort403()
                elif not allowed_to_view:
                    errpage = pages.InterstitialPage(
                        _("private"),
                        content=pages.PrivateInterstitial(
                            sr_name=c.site.name,
                            sr_description=c.site.public_description,
                        ),
                    )
                    request.environ['usable_error_content'] = errpage.render()
                    self.abort403()
                else:
                    if c.render_style != 'html':
                        self.abort403()
                    g.events.quarantine_event('quarantine_interstitial_view', c.site,
                        request=request, context=c)
                    return self.intermediate_redirect("/quarantine", sr_path=False)

            # check over 18
            if (
                c.site.over_18 and not c.over18 and
                request.path != "/over18" and
                c.render_style == 'html' and
                not request.parsed_agent.bot
            ):
                return self.intermediate_redirect("/over18", sr_path=False)

        #check whether to allow custom styles
        c.allow_styles = True
        c.can_apply_styles = self.allow_stylesheets

        # use override stylesheet if one exists and:
        #   this page has no custom stylesheet
        #   or the user disabled the stylesheet for this sr (indiv or global)
        has_style_override = (c.user.pref_default_theme_sr and
                feature.is_enabled('stylesheets_everywhere') and
                Subreddit._by_name(c.user.pref_default_theme_sr).can_view(c.user))
        sr_stylesheet_enabled = c.user.use_subreddit_style(c.site)

        if (not sr_stylesheet_enabled and
                not has_style_override):
            c.can_apply_styles = False

        c.bare_content = request.GET.pop('bare', False)

        c.show_admin_bar = admin_bar_eligible and (c.user_is_admin or g.debug)
        if not c.show_admin_bar:
            g.stats.end_logging_timings()

        hooks.get_hook("reddit.request.begin").call()

        c.request_timer.intermediate("base-pre")

    def post(self):
        MinimalController.post(self)
        if response.content_type == "text/html":
            self._embed_html_timing_data()

        # allow logged-out JSON requests to be read cross-domain
        if (not c.cors_checked and request.method.upper() == "GET" and
                not c.user_is_loggedin and c.render_style == "api"):
            response.headers["Access-Control-Allow-Origin"] = "*"

            request_origin = request.headers.get('Origin')
            if request_origin and request_origin != g.origin:
                g.stats.simple_event('cors.api_request')
                g.stats.count_string('origins', request_origin)

        if g.tracker_url and request.method.upper() == "GET" and is_api():
            tracking_url = make_url_https(get_pageview_pixel_url())
            response.headers["X-Reddit-Tracking"] = tracking_url

    def _embed_html_timing_data(self):
        timings = g.stats.end_logging_timings()

        if not timings or not c.show_admin_bar or c.is_exception_response:
            return

        timings = [{
            "key": timing.key,
            "start": round(timing.start, 4),
            "end": round(timing.end, 4),
        } for timing in timings]

        content = flatten_response(response.content)
        # inject stats script tag at the end of the <body>
        body_parts = list(content.rpartition("</body>"))
        if body_parts[1]:
            script = ('<script type="text/javascript">'
                      'window.r = window.r || {};'
                      'r.timings = %s'
                      '</script>') % simplejson.dumps(timings)
            body_parts.insert(1, script)
            response.content = "".join(body_parts)

    def search_fail(self, exception):
        errpage = pages.RedditError(_("search failed"),
                                    strings.search_failed)

        request.environ['usable_error_content'] = errpage.render()
        request.environ['retry_after'] = 60
        abort(503)
