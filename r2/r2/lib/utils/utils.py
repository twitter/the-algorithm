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

import base64
import codecs
import ConfigParser
import cPickle as pickle
import functools
import itertools
import math
import os
import random
import re
import signal
import time
import traceback

from collections import OrderedDict
from copy import deepcopy
from datetime import date, datetime, timedelta
from decimal import Decimal
from urllib import unquote_plus, unquote
from urllib2 import urlopen, Request
from urlparse import urlparse, urlunparse

import pytz
import snudown
import unidecode
from r2.lib.utils import reddit_agent_parser

from babel.dates import TIMEDELTA_UNITS
from BeautifulSoup import BeautifulSoup, SoupStrainer
from mako.filters import url_escape
from pylons import request, config
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import ungettext, _

from r2.lib.contrib import ipaddress
from r2.lib.filters import _force_unicode, _force_utf8
from r2.lib.require import require, require_split, RequirementException
from r2.lib.utils._utils import *

iters = (list, tuple, set)

def randstr(length,
            alphabet='abcdefghijklmnopqrstuvwxyz0123456789'):
    """Return a string made up of random chars from alphabet."""
    return ''.join(random.choice(alphabet) for _ in xrange(length))


class Storage(dict):
    """
    A Storage object is like a dictionary except `obj.foo` can be used
    in addition to `obj['foo']`.

        >>> o = storage(a=1)
        >>> o.a
        1
        >>> o['a']
        1
        >>> o.a = 2
        >>> o['a']
        2
        >>> del o.a
        >>> o.a
        Traceback (most recent call last):
            ...
        AttributeError: 'a'

    """
    def __getattr__(self, key):
        try:
            return self[key]
        except KeyError, k:
            raise AttributeError, k

    def __setattr__(self, key, value):
        self[key] = value

    def __delattr__(self, key):
        try:
            del self[key]
        except KeyError, k:
            raise AttributeError, k

    def __repr__(self):
        return '<Storage ' + dict.__repr__(self) + '>'

storage = Storage


class Enum(Storage):
    def __init__(self, *a):
        self.name = tuple(a)
        Storage.__init__(self, ((e, i) for i, e in enumerate(a)))
    def __contains__(self, item):
        if isinstance(item, int):
            return item in self.values()
        else:
            return Storage.__contains__(self, item)


class class_property(object):
    """A decorator that combines @classmethod and @property.

    http://stackoverflow.com/a/8198300/120999
    """
    def __init__(self, function):
        self.function = function
    def __get__(self, instance, cls):
        return self.function(cls)


class Results():
    def __init__(self, sa_ResultProxy, build_fn, do_batch=False):
        self.rp = sa_ResultProxy
        self.fn = build_fn
        self.do_batch = do_batch

    @property
    def rowcount(self):
        return self.rp.rowcount

    def _fetch(self, res):
        if self.do_batch:
            return self.fn(res)
        else:
            return [self.fn(row) for row in res]

    def fetchall(self):
        return self._fetch(self.rp.fetchall())

    def fetchmany(self, n):
        rows = self._fetch(self.rp.fetchmany(n))
        if rows:
            return rows
        else:
            raise StopIteration

    def fetchone(self):
        row = self.rp.fetchone()
        if row:
            if self.do_batch:
                if isinstance(row, Storage):
                    rows = (row,)
                else:
                    rows = tup(row)
                return self.fn(rows)[0]
            else:
                return self.fn(row)
        else:
            raise StopIteration

r_base_url = re.compile("(?i)(?:.+?://)?([^#]*[^#/])/?")
r_domain = re.compile("(?i)(?:.+?://)?([^/:#?]*)")
r_domain_prefix = re.compile('^www\d*\.')


def strip_www(domain):
    stripped = domain
    if domain.count('.') > 1:
        prefix = r_domain_prefix.findall(domain)
        if domain.startswith("www") and len(prefix):
            stripped = '.'.join(domain.split('.')[1:])
    return stripped


def is_subdomain(subdomain, base):
    """Check if a domain is equal to or a subdomain of a base domain."""
    return subdomain == base or (
        subdomain is not None and subdomain.endswith('.' + base))


lang_re = re.compile(r"\A\w\w(-\w\w)?\Z")


def is_language_subdomain(subdomain):
    return lang_re.match(subdomain)


def base_url(url):
    res = r_base_url.findall(url)
    if res and res[0]:
        base = strip_www(res[0])
    else:
        base = url
    return base.lower()


def domain(url):
    """
        Takes a URL and returns the domain part, minus www., if
        present
    """
    match = r_domain.search(url)
    if match:
        domain = strip_www(match.group(1))
    else:
        domain = url
    return domain.lower()


def extract_subdomain(host=None, base_domain=None):
    """Try to extract a subdomain from the request, as compared to g.domain.

    host and base_domain exist as arguments primarily for the sake of unit
    tests, although their usage should not be considered restrained to that.
    """
    # These would be the argument defaults, but we need them evaluated at
    # run-time, not definition-time.
    if host is None:
        host = request.host
    if base_domain is None:
        base_domain = g.domain

    if not host:
        return ''

    end_index = host.find(base_domain) - 1 # For the conjoining dot.
    # Is either the requested domain the same as the base domain, or the
    # base is not a substring?
    if end_index < 0:
        return ''
    return host[:end_index]

r_path_component = re.compile(".*?/(.*)")
def path_component(s):
    """
        takes a url http://www.foo.com/i/like/cheese and returns
        i/like/cheese
    """
    res = r_path_component.findall(base_url(s))
    return (res and res[0]) or s

def get_title(url):
    """Fetch the contents of url and try to extract the page's title."""
    if not url or not url.startswith(('http://', 'https://')):
        return None

    try:
        req = Request(url)
        if g.useragent:
            req.add_header('User-Agent', g.useragent)
        opener = urlopen(req, timeout=15)

        # determine the encoding of the response
        for param in opener.info().getplist():
            if param.startswith("charset="):
                param_name, sep, charset = param.partition("=")
                codec = codecs.getreader(charset)
                break
        else:
            codec = codecs.getreader("utf-8")

        with codec(opener, "ignore") as reader:
            # Attempt to find the title in the first 1kb
            data = reader.read(1024)
            title = extract_title(data)

            # Title not found in the first kb, try searching an additional 10kb
            if not title:
                data += reader.read(10240)
                title = extract_title(data)

        return title

    except:
        return None

def extract_title(data):
    """Try to extract the page title from a string of HTML.

    An og:title meta tag is preferred, but will fall back to using
    the <title> tag instead if one is not found. If using <title>,
    also attempts to trim off the site's name from the end.
    """
    bs = BeautifulSoup(data, convertEntities=BeautifulSoup.HTML_ENTITIES)
    if not bs or not bs.html.head:
        return
    head_soup = bs.html.head

    title = None

    # try to find an og:title meta tag to use
    og_title = (head_soup.find("meta", attrs={"property": "og:title"}) or
                head_soup.find("meta", attrs={"name": "og:title"}))
    if og_title:
        title = og_title.get("content")

    # if that failed, look for a <title> tag to use instead
    if not title and head_soup.title and head_soup.title.string:
        title = head_soup.title.string

        # remove end part that's likely to be the site's name
        # looks for last delimiter char between spaces in strings
        # delimiters: |, -, emdash, endash,
        #             left- and right-pointing double angle quotation marks
        reverse_title = title[::-1]
        to_trim = re.search(u'\s[\u00ab\u00bb\u2013\u2014|-]\s',
                            reverse_title,
                            flags=re.UNICODE)

        # only trim if it won't take off over half the title
        if to_trim and to_trim.end() < len(title) / 2:
            title = title[:-(to_trim.end())]

    if not title:
        return

    # get rid of extraneous whitespace in the title
    title = re.sub(r'\s+', ' ', title, flags=re.UNICODE)

    return title.encode('utf-8').strip()

VALID_SCHEMES = ('http', 'https', 'ftp', 'mailto')
valid_dns = re.compile('\A[-a-zA-Z0-9_]+\Z')
def sanitize_url(url, require_scheme=False, valid_schemes=VALID_SCHEMES):
    """Validates that the url is of the form

    scheme://domain/path/to/content#anchor?cruft

    using the python built-in urlparse.  If the url fails to validate,
    returns None.  If no scheme is provided and 'require_scheme =
    False' is set, the url is returned with scheme 'http', provided it
    otherwise validates"""

    if not url:
        return None

    url = url.strip()
    if url.lower() == 'self':
        return url

    try:
        u = urlparse(url)
        # first pass: make sure a scheme has been specified
        if not require_scheme and not u.scheme:
            # "//example.com/"
            if u.hostname:
                prepend = "https:" if c.secure else "http:"
            # "example.com/"
            else:
                prepend = "http://"
            url = prepend + url
            u = urlparse(url)
    except ValueError:
        return None

    if not u.scheme:
        return None
    if valid_schemes is not None and u.scheme not in valid_schemes:
        return None

    # if there is a scheme and no hostname, it is a bad url.
    if not u.hostname:
        return None
    # work around CRBUG-464270
    if len(u.hostname) > 255:
        return None
    # work around for Chrome crash with "%%30%30" - Sep 2015
    if "%00" in unquote(u.path):
        return None
    if u.username is not None or u.password is not None:
        return None

    try:
        idna_hostname = u.hostname.encode('idna')
    except TypeError as e:
        g.log.warning("Bad hostname given [%r]: %s", u.hostname, e)
        raise
    except UnicodeError:
        return None

    # Make sure FQDNs like google.com. (with trailing dot) are allowed. This
    # is necessary to support linking to bare TLDs.
    if idna_hostname.endswith('.'):
        idna_hostname = idna_hostname[:-1]

    for label in idna_hostname.split('.'):
        if not re.match(valid_dns, label):
            return None

    if idna_hostname != u.hostname:
        url = urlunparse((u[0], idna_hostname, u[2], u[3], u[4], u[5]))
    return url

def trunc_string(text, max_length, suffix='...'):
    """Truncate a string, attempting to split on a word-break.

    If the first word is longer than max_length, then truncate within the word.

    Adapted from http://stackoverflow.com/a/250406/120999 .
    """
    if len(text) <= max_length:
        return text
    else:
        hard_truncated = text[:(max_length - len(suffix))]
        word_truncated = hard_truncated.rsplit(' ', 1)[0]
        return word_truncated + suffix

# Truncate a time to a certain number of minutes
# e.g, trunc_time(5:52, 30) == 5:30
def trunc_time(time, mins, hours=None):
    if hours is not None:
        if hours < 1 or hours > 60:
            raise ValueError("Hours %d is weird" % mins)
        time = time.replace(hour = hours * (time.hour / hours))

    if mins < 1 or mins > 60:
        raise ValueError("Mins %d is weird" % mins)

    return time.replace(minute = mins * (time.minute / mins),
                        second = 0,
                        microsecond = 0)

def long_datetime(datetime):
    return datetime.astimezone(g.tz).ctime() + " " + str(g.tz)

def median(l):
    if l:
        s = sorted(l)
        i = len(s) / 2
        return s[i]

def query_string(dict):
    pairs = []
    for k,v in dict.iteritems():
        if v is not None:
            try:
                k = url_escape(_force_unicode(k))
                v = url_escape(_force_unicode(v))
                pairs.append(k + '=' + v)
            except UnicodeDecodeError:
                continue
    if pairs:
        return '?' + '&'.join(pairs)
    else:
        return ''


# Characters that might cause parsing differences in different implementations
# Spaces only seem to cause parsing differences when occurring directly before
# the scheme
URL_PROBLEMATIC_RE = re.compile(
    ur'(\A\x20|[\x00-\x19\xA0\u1680\u180E\u2000-\u2029\u205f\u3000\\])',
    re.UNICODE
)


def paranoid_urlparser_method(check):
    """
    Decorator for checks on `UrlParser` instances that need to be paranoid
    """
    def check_wrapper(parser, *args, **kwargs):
        return UrlParser.perform_paranoid_check(parser, check, *args, **kwargs)

    return check_wrapper


class UrlParser(object):
    """
    Wrapper for urlparse and urlunparse for making changes to urls.

    All attributes present on the tuple-like object returned by
    urlparse are present on this class, and are setable, with the
    exception of netloc, which is instead treated via a getter method
    as a concatenation of hostname and port.

    Unlike urlparse, this class allows the query parameters to be
    converted to a dictionary via the query_dict method (and
    correspondingly updated via update_query).  The extension of the
    path can also be set and queried.

    The class also contains reddit-specific functions for setting,
    checking, and getting a path's subreddit.
    """

    __slots__ = ['scheme', 'path', 'params', 'query',
                 'fragment', 'username', 'password', 'hostname', 'port',
                 '_orig_url', '_orig_netloc', '_query_dict']

    valid_schemes = ('http', 'https', 'ftp', 'mailto')

    def __init__(self, url):
        u = urlparse(url)
        for s in self.__slots__:
            if hasattr(u, s):
                setattr(self, s, getattr(u, s))
        self._orig_url    = url
        self._orig_netloc = getattr(u, 'netloc', '')
        self._query_dict  = None

    def __eq__(self, other):
        """A loose equality method for UrlParsers.

        In particular, this returns true for UrlParsers whose resultant urls
        have the same query parameters, but in a different order.  These are
        treated the same most of the time, but if you need strict equality,
        compare the string results of unparse().
        """
        if not isinstance(other, UrlParser):
            return False

        (s_scheme, s_netloc, s_path, s_params, s_query, s_fragment) = self._unparse()
        (o_scheme, o_netloc, o_path, o_params, o_query, o_fragment) = other._unparse()
        # Check all the parsed components for equality, except the query, which
        # is easier to check in its pure-dictionary form.
        if (s_scheme != o_scheme or
                s_netloc != o_netloc or
                s_path != o_path or
                s_params != o_params or
                s_fragment != o_fragment):
            return False
        # Coerce query dicts from OrderedDicts to standard dicts to avoid an
        # order-sensitive comparison.
        if dict(self.query_dict) != dict(other.query_dict):
            return False

        return True

    def update_query(self, **updates):
        """Add or change query parameters."""
        # Since in HTTP everything's a string, coercing values to strings now
        # makes equality testing easier.  Python will throw an error if you try
        # to pass in a non-string key, so that's already taken care of for us.
        updates = {k: _force_unicode(v) for k, v in updates.iteritems()}
        self.query_dict.update(updates)

    @property
    def query_dict(self):
        """A dictionary of the current query parameters.

        Keys and values pulled from the original url are un-url-escaped.

        Modifying this function's return value will result in changes to the
        unparse()-d url, but it's recommended instead to make any changes via
        `update_query()`.
        """
        if self._query_dict is None:
            def _split(param):
                p = param.split('=')
                return (unquote_plus(p[0]),
                        unquote_plus('='.join(p[1:])))
            self._query_dict = OrderedDict(
                                 _split(p) for p in self.query.split('&') if p)
        return self._query_dict

    def path_extension(self):
        """Fetches the current extension of the path.

        If the url does not end in a file or the file has no extension, returns
        an empty string.
        """
        filename = self.path.split('/')[-1]
        filename_parts = filename.split('.')
        if len(filename_parts) == 1:
            return ''

        return filename_parts[-1]

    def has_image_extension(self):
        """Guess if the url leads to an image."""
        extension = self.path_extension().lower()
        return extension in {'gif', 'jpeg', 'jpg', 'png', 'tiff'}

    def has_static_image_extension(self):
        """Guess if the url leads to a non-animated image."""
        extension = self.path_extension().lower()
        return extension in {'jpeg', 'jpg', 'png', 'tiff'}

    def set_extension(self, extension):
        """
        Changes the extension of the path to the provided value (the
        "." should not be included in the extension as a "." is
        provided)
        """
        pieces = self.path.split('/')
        dirs = pieces[:-1]
        base = pieces[-1].split('.')
        base = '.'.join(base[:-1] if len(base) > 1 else base)
        if extension:
            base += '.' + extension
        dirs.append(base)
        self.path =  '/'.join(dirs)
        return self

    def canonicalize(self):
        subdomain = extract_subdomain(self.hostname)
        if subdomain == '' or is_language_subdomain(subdomain):
            self.hostname = 'www.{0}'.format(g.domain)
        if not self.path.endswith('/'):
            self.path += '/'
        self.scheme = 'https'

    def switch_subdomain_by_extension(self, extension=None):
        """Change the subdomain to the one that fits an extension.

        This should only be used on reddit URLs.

        Arguments:

        * extension: the template extension to which the middleware hints when
          parsing the subdomain resulting from this function.

        >>> u = UrlParser('http://www.reddit.com/r/redditdev')
        >>> u.switch_subdomain_by_extension('compact')
        >>> u.unparse()
        'http://i.reddit.com/r/redditdev'

        If `extension` is not provided or does not match any known extensions,
        the default subdomain (`g.domain_prefix`) will be used.

        Note that this will not remove any existing extensions; if you want to
        ensure the explicit extension does not override the subdomain hint, you
        should call `set_extension('')` first.
        """
        new_subdomain = g.domain_prefix
        for subdomain, subdomain_extension in g.extension_subdomains.iteritems():
            if extension == subdomain_extension:
                new_subdomain = subdomain
                break
        self.hostname = '%s.%s' % (new_subdomain, g.domain)

    def unparse(self):
        """
        Converts the url back to a string, applying all updates made
        to the fields thereof.

        Note: if a host name has been added and none was present
        before, will enforce scheme -> "http" unless otherwise
        specified.  Double-slashes are removed from the resultant
        path, and the query string is reconstructed only if the
        query_dict has been modified/updated.
        """
        return urlunparse(self._unparse())

    def _unparse(self):
        q = query_string(self.query_dict).lstrip('?')

        # make sure the port is not doubly specified
        if getattr(self, 'port', None) and ":" in self.hostname:
            self.hostname = self.hostname.split(':')[0]

        # if there is a netloc, there had better be a scheme
        if self.netloc and not self.scheme:
            self.scheme = "http"

        return (self.scheme, self.netloc,
                self.path.replace('//', '/'),
                self.params, q, self.fragment)

    def path_has_subreddit(self):
        """
        utility method for checking if the path starts with a
        subreddit specifier (namely /r/ or /subreddits/).
        """
        return self.path.startswith(('/r/', '/subreddits/', '/reddits/'))

    def get_subreddit(self):
        """checks if the current url refers to a subreddit and returns
        that subreddit object.  The cases here are:

          * the hostname is unset or is g.domain, in which case it
            looks for /r/XXXX or /subreddits.  The default in this case
            is Default.
          * the hostname is a cname to a known subreddit.

        On failure to find a subreddit, returns None.
        """
        from r2.models import Subreddit, NotFound, DefaultSR
        try:
            if (not self.hostname or
                    is_subdomain(self.hostname, g.domain) or
                    self.hostname.startswith(g.domain)):
                if self.path.startswith('/r/'):
                    return Subreddit._by_name(self.path.split('/')[2])
                else:
                    return DefaultSR()
            elif self.hostname:
                return Subreddit._by_domain(self.hostname)
        except NotFound:
            pass
        return None

    def perform_paranoid_check(self, check, *args, **kwargs):
        """
        Perform a check on a URL that needs to account for bugs in `unparse()`

        If you need to account for quirks in browser URL parsers, you should
        use this along with `is_web_safe_url()`. Trying to parse URLs like
        a browser would just makes things really hairy.
        """
        variants_to_check = (
            self,
            UrlParser(self.unparse())
        )
        # If the check doesn't pass on *every* variant, it's a fail.
        return all(
            check(variant, *args, **kwargs) for variant in variants_to_check
        )

    @paranoid_urlparser_method
    def is_web_safe_url(self):
        """Determine if this URL could cause issues with different parsers"""

        # There's no valid reason for this, and just serves to confuse UAs.
        # and urllib2.
        if self._orig_url.startswith("///"):
            return False

        # Double-checking the above
        if not self.hostname and self.path.startswith('//'):
            return False

        # A host-relative link with a scheme like `https:/baz` or `https:?quux`
        if self.scheme and not self.hostname:
            return False

        # Credentials in the netloc? Not on reddit!
        if "@" in self._orig_netloc:
            return False

        # `javascript://www.reddit.com/%0D%Aalert(1)` is not safe, obviously
        if self.scheme and self.scheme.lower() not in self.valid_schemes:
            return False

        # Reject any URLs that contain characters known to cause parsing
        # differences between parser implementations
        for match in re.finditer(URL_PROBLEMATIC_RE, self._orig_url):
            # XXX: Yuck. We have non-breaking spaces in title slugs! They
            # should be safe enough to allow after three slashes. Opera 12's the
            # only browser that trips over them, and it doesn't fall for
            # `http:///foo.com/`.
            # Check both in case unicode promotion fails
            if match.group(0) in {u'\xa0', '\xa0'}:
                if match.string[0:match.start(0)].count('/') < 3:
                    return False
            else:
                return False

        return True

    def is_reddit_url(self, subreddit=None):
        """utility method for seeing if the url is associated with
        reddit as we don't necessarily want to mangle non-reddit
        domains

        returns true only if hostname is nonexistant, a subdomain of
        g.domain, or a subdomain of the provided subreddit's cname.
        """

        valid_subdomain = (
            not self.hostname or
            is_subdomain(self.hostname, g.domain) or
            (subreddit and subreddit.domain and
                is_subdomain(self.hostname, subreddit.domain))
        )

        if not valid_subdomain or not self.hostname or not g.offsite_subdomains:
            return valid_subdomain
        return not any(
            is_subdomain(self.hostname, "%s.%s" % (subdomain, g.domain))
            for subdomain in g.offsite_subdomains
        )

    def path_add_subreddit(self, subreddit):
        """
        Adds the subreddit's path to the path if another subreddit's
        prefix is not already present.
        """
        if not (self.path_has_subreddit()
                or self.path.startswith(subreddit.user_path)):
            self.path = (subreddit.user_path + self.path)
        return self

    @property
    def netloc(self):
        """
        Getter method which returns the hostname:port, or empty string
        if no hostname is present.
        """
        if not self.hostname:
            return ""
        elif getattr(self, "port", None):
            return self.hostname + ":" + str(self.port)
        return self.hostname

    def __repr__(self):
        return "<URL %s>" % repr(self.unparse())

    def domain_permutations(self, fragments=False, subdomains=True):
        """
          Takes a domain like `www.reddit.com`, and returns a list of ways
          that a user might search for it, like:
          * www
          * reddit
          * com
          * www.reddit.com
          * reddit.com
          * com
        """
        ret = set()
        if self.hostname:
            r = self.hostname.split('.')

            if subdomains:
                for x in xrange(len(r)-1):
                    ret.add('.'.join(r[x:len(r)]))

            if fragments:
                for x in r:
                    ret.add(x)

        return ret

    @classmethod
    def base_url(cls, url):
        u = cls(url)

        # strip off any www and lowercase the hostname:
        netloc = strip_www(u.netloc.lower())

        # http://code.google.com/web/ajaxcrawling/docs/specification.html
        fragment = u.fragment if u.fragment.startswith("!") else ""

        return urlunparse((u.scheme.lower(), netloc,
                           u.path, u.params, u.query, fragment))


def coerce_url_to_protocol(url, protocol='http'):
    '''Given an absolute (but potentially protocol-relative) url, coerce it to
    a protocol.'''
    parsed_url = UrlParser(url)
    parsed_url.scheme = protocol
    return parsed_url.unparse()

def url_is_embeddable_image(url):
    """The url is on an oembed-friendly domain and looks like an image."""
    parsed_url = UrlParser(url)

    if parsed_url.path_extension().lower() in {"jpg", "gif", "png", "jpeg"}:
        if parsed_url.hostname not in g.known_image_domains:
            return False
        return True

    return False


def url_to_thing(url):
    """Given a reddit URL, return the Thing to which it associates.

    Examples:
        /r/somesr - Subreddit
        /r/somesr/comments/j2jx - Link
        /r/somesr/comments/j2jx/slug/k2js - Comment
    """
    from r2.models import Comment, Link, Message, NotFound, Subreddit, Thing
    from r2.config.middleware import SubredditMiddleware
    sr_pattern = SubredditMiddleware.sr_pattern

    urlparser = UrlParser(_force_utf8(url))
    if not urlparser.is_reddit_url():
        return None

    try:
        sr_name = sr_pattern.match(urlparser.path).group(1)
    except AttributeError:
        sr_name = None

    path = sr_pattern.sub('', urlparser.path)
    if not path or path == '/':
        if not sr_name:
            return None

        try:
            return Subreddit._by_name(sr_name, data=True)
        except NotFound:
            return None

    # potential TypeError raised here because of environ being None
    # when calling outside of app context
    try:
        route_dict = config['routes.map'].match(path)
    except TypeError:
        return None

    if not route_dict:
        return None

    try:
        comment = route_dict.get('comment')
        if comment:
            return Comment._byID36(comment, data=True)

        article = route_dict.get('article')
        if article:
            return Link._byID36(article, data=True)

        msg = route_dict.get('mid')
        if msg:
            return Message._byID36(msg, data=True)
    except (NotFound, ValueError):
        return None

    return None


def pload(fname, default = None):
    "Load a pickled object from a file"
    try:
        f = file(fname, 'r')
        d = pickle.load(f)
    except IOError:
        d = default
    else:
        f.close()
    return d

def psave(fname, d):
    "Save a pickled object into a file"
    f = file(fname, 'w')
    pickle.dump(d, f)
    f.close()

def unicode_safe(res):
    try:
        return str(res)
    except UnicodeEncodeError:
        try:
            return unicode(res).encode('utf-8')
        except UnicodeEncodeError:
            return res.decode('utf-8').encode('utf-8')

def decompose_fullname(fullname):
    """
        decompose_fullname("t3_e4fa") ->
            (Thing, 3, 658918)
    """
    from r2.lib.db.thing import Thing,Relation
    if fullname[0] == 't':
        type_class = Thing
    elif fullname[0] == 'r':
        type_class = Relation

    type_id36, thing_id36 = fullname[1:].split('_')

    type_id = int(type_id36,36)
    id      = int(thing_id36,36)

    return (type_class, type_id, id)

def cols(lst, ncols):
    """divides a list into columns, and returns the
    rows. e.g. cols('abcdef', 2) returns (('a', 'd'), ('b', 'e'), ('c',
    'f'))"""
    nrows = int(math.ceil(1.*len(lst) / ncols))
    lst = lst + [None for i in range(len(lst), nrows*ncols)]
    cols = [lst[i:i+nrows] for i in range(0, nrows*ncols, nrows)]
    rows = zip(*cols)
    rows = [filter(lambda x: x is not None, r) for r in rows]
    return rows

def fetch_things(t_class,since,until,batch_fn=None,
                 *query_params, **extra_query_dict):
    """
        Simple utility function to fetch all Things of class t_class
        (spam or not, but not deleted) that were created from 'since'
        to 'until'
    """

    from r2.lib.db.operators import asc

    if not batch_fn:
        batch_fn = lambda x: x

    query_params = ([t_class.c._date >= since,
                     t_class.c._date <  until,
                     t_class.c._spam == (True,False)]
                    + list(query_params))
    query_dict   = {'sort':  asc('_date'),
                    'limit': 100,
                    'data':  True}
    query_dict.update(extra_query_dict)

    q = t_class._query(*query_params,
                        **query_dict)

    orig_rules = deepcopy(q._rules)

    things = list(q)
    while things:
        things = batch_fn(things)
        for t in things:
            yield t
        q._rules = deepcopy(orig_rules)
        q._after(t)
        things = list(q)


def fetch_things2(query, chunk_size = 100, batch_fn = None, chunks = False):
    """Incrementally run query with a limit of chunk_size until there are
    no results left. batch_fn transforms the results for each chunk
    before returning."""

    assert query._sort, "you must specify the sort order in your query!"

    orig_rules = deepcopy(query._rules)
    query._limit = chunk_size
    items = list(query)
    done = False
    while items and not done:
        #don't need to query again at the bottom if we didn't get enough
        if len(items) < chunk_size:
            done = True

        after = items[-1]

        if batch_fn:
            items = batch_fn(items)

        if chunks:
            yield items
        else:
            for i in items:
                yield i

        if not done:
            query._rules = deepcopy(orig_rules)
            query._after(after)
            items = list(query)


def exponential_retrier(func_to_retry,
                        exception_filter=lambda *args, **kw: True,
                        retry_min_wait_ms=500,
                        max_retries=5):
    """Call func_to_retry and return it's results.
    If func_to_retry throws an exception, retry.

    :param Function func_to_retry: Function to execute
        and possibly retry.
    :param exception_filter:  Only retry exceptions for
        which this function returns True.  Always returns True by default.
    :param int retry_min_wait_ms: Initial wait period
        if an exception happens in milliseconds.
        After each retry this value will be multiplied by 2
        thus achieving exponential backoff algorithm.
    :param int max_retries:  How many times to wait before
        just re-throwing last exception.
        Value of zero would result in no retry attempts.
    """
    sleep_time = retry_min_wait_ms
    num_retried = 0
    while True:
        try:
            return func_to_retry()
        # StopIteration should never be retried as its part of regular logic.
        except StopIteration:
            raise
        except Exception as e:
            g.log.exception("%d number retried" % num_retried)
            num_retried += 1
            # if we ran out of retries or this Exception
            # shouldnt be retried then raise the exception instead of sleeping
            if num_retried > max_retries or not exception_filter(e):
                raise

            # convert to ms.  Use floating point literal for int -> float
            time.sleep(sleep_time / 1000.0)
            sleep_time *= 2


def fetch_things_with_retry(query,
                            chunk_size=100,
                            batch_fn=None,
                            chunks=False,
                            retry_min_wait_ms=500,
                            max_retries=0):
    """Incrementally run query with a limit of chunk_size until there are
    no results left. batch_fn transforms the results for each chunk
    before returning.

    If a query at some point generates an exception
    retry it using exponential backoff.

    By default retrying is turned off."""

    assert query._sort, "you must specify the sort order in your query!"

    retrier = functools.partial(exponential_retrier,
                                retry_min_wait_ms=retry_min_wait_ms,
                                max_retries=max_retries)

    orig_rules = deepcopy(query._rules)
    query._limit = chunk_size
    items = retrier(lambda: list(query))

    done = False
    while items and not done:
        # don't need to query again at the bottom if we didn't get enough
        if len(items) < chunk_size:
            done = True

        after = items[-1]

        if batch_fn:
                items = batch_fn(items)

        if chunks:
            yield items
        else:
            for i in items:
                yield i

        if not done:
            query._rules = deepcopy(orig_rules)
            query._after(after)
            items = retrier(lambda: list(query))


def fix_if_broken(thing, delete = True, fudge_links = False):
    from r2.models import Link, Comment, Subreddit, Message

    # the minimum set of attributes that are required
    attrs = dict((cls, cls._essentials)
                 for cls
                 in (Link, Comment, Subreddit, Message))

    if thing.__class__ not in attrs:
        raise TypeError

    for attr in attrs[thing.__class__]:
        try:
            # try to retrieve the attribute
            getattr(thing, attr)
        except AttributeError:
            if not delete:
                raise

            if isinstance(thing, Link) and fudge_links:
                if attr == "sr_id":
                    thing.sr_id = 6
                    print "Fudging %s.sr_id to %d" % (thing._fullname,
                                                      thing.sr_id)
                elif attr == "author_id":
                    thing.author_id = 8244672
                    print "Fudging %s.author_id to %d" % (thing._fullname,
                                                          thing.author_id)
                else:
                    print "Got weird attr %s; can't fudge" % attr

            if not thing._deleted:
                print "%s is missing %r, deleting" % (thing._fullname, attr)
                thing._deleted = True

            thing._commit()

            if not fudge_links:
                break


def find_recent_broken_things(from_time = None, to_time = None,
                              delete = False):
    """
        Occasionally (usually during app-server crashes), Things will
        be partially written out to the database. Things missing data
        attributes break the contract for these things, which often
        breaks various pages. This function hunts for and destroys
        them as appropriate.
    """
    from r2.models import Link, Comment
    from r2.lib.db.operators import desc

    from_time = from_time or timeago('1 hour')
    to_time = to_time or datetime.now(g.tz)

    for cls in (Link, Comment):
        q = cls._query(cls.c._date > from_time,
                       cls.c._date < to_time,
                       data=True,
                       sort=desc('_date'))
        for thing in fetch_things2(q):
            fix_if_broken(thing, delete = delete)


def timeit(func):
    "Run some function, and return (RunTimeInSeconds,Result)"
    before=time.time()
    res=func()
    return (time.time()-before,res)
def lineno():
    "Returns the current line number in our program."
    import inspect
    print "%s\t%s" % (datetime.now(),inspect.currentframe().f_back.f_lineno)

def IteratorFilter(iterator, fn):
    for x in iterator:
        if fn(x):
            yield x

def UniqueIterator(iterator, key = lambda x: x):
    """
    Takes an iterator and returns an iterator that returns only the
    first occurence of each entry
    """
    so_far = set()
    def no_dups(x):
        k = key(x)
        if k in so_far:
            return False
        else:
            so_far.add(k)
            return True

    return IteratorFilter(iterator, no_dups)

def safe_eval_str(unsafe_str):
    return unsafe_str.replace('\\x3d', '=').replace('\\x26', '&')

rx_whitespace = re.compile('\s+', re.UNICODE)
rx_notsafe = re.compile('\W+', re.UNICODE)
rx_underscore = re.compile('_+', re.UNICODE)
def title_to_url(title, max_length = 50):
    """Takes a string and makes it suitable for use in URLs"""
    title = _force_unicode(title)           #make sure the title is unicode
    title = rx_whitespace.sub('_', title)   #remove whitespace
    title = rx_notsafe.sub('', title)       #remove non-printables
    title = rx_underscore.sub('_', title)   #remove double underscores
    title = title.strip('_')                #remove trailing underscores
    title = title.lower()                   #lowercase the title

    if len(title) > max_length:
        #truncate to nearest word
        title = title[:max_length]
        last_word = title.rfind('_')
        if (last_word > 0):
            title = title[:last_word]
    return title or "_"


def unicode_title_to_ascii(title, max_length=50):
    title = _force_unicode(title)
    title = unidecode.unidecode(title)
    return title_to_url(title, max_length)


def dbg(s):
    import sys
    sys.stderr.write('%s\n' % (s,))

def trace(fn):
    def new_fn(*a,**kw):
        ret = fn(*a,**kw)
        dbg("Fn: %s; a=%s; kw=%s\nRet: %s"
            % (fn,a,kw,ret))
        return ret
    return new_fn

def common_subdomain(domain1, domain2):
    if not domain1 or not domain2:
        return ""
    domain1 = domain1.split(":")[0]
    domain2 = domain2.split(":")[0]
    if len(domain1) > len(domain2):
        domain1, domain2 = domain2, domain1

    if domain1 == domain2:
        return domain1
    else:
        dom = domain1.split(".")
        for i in range(len(dom), 1, -1):
            d = '.'.join(dom[-i:])
            if domain2.endswith(d):
                return d
    return ""


def url_links_builder(url, exclude=None, num=None, after=None, reverse=None,
                      count=None, public_srs_only=False):
    from r2.lib.template_helpers import add_sr
    from r2.models import IDBuilder, Link, NotFound, Subreddit
    from operator import attrgetter

    if url.startswith('/'):
        url = add_sr(url, force_hostname=True)

    try:
        links = Link._by_url(url, None)
    except NotFound:
        links = []

    links = [ link for link in links
                   if link._fullname != exclude ]

    if public_srs_only and not c.user_is_admin:
        subreddits = Subreddit._byID([link.sr_id for link in links], data=True)
        links = [link for link in links
                 if subreddits[link.sr_id].type not in Subreddit.private_types]

    links.sort(key=attrgetter('num_comments'), reverse=True)

    # don't show removed links in duplicates unless admin or mod
    # or unless it's your own post
    def include_link(link):
        return (not link._spam or
                (c.user_is_loggedin and
                    (link.author_id == c.user._id or
                        c.user_is_admin or
                        link.subreddit.is_moderator(c.user))))

    builder = IDBuilder([link._fullname for link in links], skip=True,
                        keep_fn=include_link, num=num, after=after,
                        reverse=reverse, count=count)

    return builder

class TimeoutFunctionException(Exception):
    pass

class TimeoutFunction:
    """Force an operation to timeout after N seconds. Works with POSIX
       signals, so it's not safe to use in a multi-treaded environment"""
    def __init__(self, function, timeout):
        self.timeout = timeout
        self.function = function

    def handle_timeout(self, signum, frame):
        raise TimeoutFunctionException()

    def __call__(self, *args, **kwargs):
        # can only be called from the main thread
        old = signal.signal(signal.SIGALRM, self.handle_timeout)
        signal.alarm(self.timeout)
        try:
            result = self.function(*args, **kwargs)
        finally:
            signal.alarm(0)
            signal.signal(signal.SIGALRM, old)
        return result


def to_date(d):
    if isinstance(d, datetime):
        return d.date()
    return d

def to_datetime(d):
    if type(d) == date:
        return datetime(d.year, d.month, d.day)
    return d

def in_chunks(it, size=25):
    chunk = []
    it = iter(it)
    try:
        while True:
            chunk.append(it.next())
            if len(chunk) >= size:
                yield chunk
                chunk = []
    except StopIteration:
        if chunk:
            yield chunk


def progress(it, verbosity=100, key=repr, estimate=None, persec=True):
    """An iterator that yields everything from `it', but prints progress
       information along the way, including time-estimates if
       possible"""
    from itertools import islice
    from datetime import datetime
    import sys

    now = start = datetime.now()
    elapsed = start - start

    # try to guess at the estimate if we can
    if estimate is None:
        try:
            estimate = len(it)
        except:
            pass

    def timedelta_to_seconds(td):
        return td.days * (24*60*60) + td.seconds + (float(td.microseconds) / 1000000)
    def format_timedelta(td, sep=''):
        ret = []
        s = timedelta_to_seconds(td)
        if s < 0:
            neg = True
            s *= -1
        else:
            neg = False

        if s >= (24*60*60):
            days = int(s//(24*60*60))
            ret.append('%dd' % days)
            s -= days*(24*60*60)
        if s >= 60*60:
            hours = int(s//(60*60))
            ret.append('%dh' % hours)
            s -= hours*(60*60)
        if s >= 60:
            minutes = int(s//60)
            ret.append('%dm' % minutes)
            s -= minutes*60
        if s >= 1:
            seconds = int(s)
            ret.append('%ds' % seconds)
            s -= seconds

        if not ret:
            return '0s'

        return ('-' if neg else '') + sep.join(ret)
    def format_datetime(dt, show_date=False):
        if show_date:
            return dt.strftime('%Y-%m-%d %H:%M')
        else:
            return dt.strftime('%H:%M:%S')
    def deq(dt1, dt2):
        "Indicates whether the two datetimes' dates describe the same (day,month,year)"
        d1, d2 = dt1.date(), dt2.date()
        return (    d1.day   == d2.day
                and d1.month == d2.month
                and d1.year  == d2.year)

    sys.stderr.write('Starting at %s\n' % (start,))

    # we're going to islice it so we need to start an iterator
    it = iter(it)

    seen = 0
    while True:
        this_chunk = 0
        thischunk_started = datetime.now()

        # the simple bit: just iterate and yield
        for item in islice(it, verbosity):
            this_chunk += 1
            seen += 1
            yield item

        if this_chunk < verbosity:
            # we're done, the iterator is empty
            break

        now = datetime.now()
        elapsed = now - start
        thischunk_seconds = timedelta_to_seconds(now - thischunk_started)

        if estimate:
            # the estimate is based on the total number of items that
            # we've processed in the total amount of time that's
            # passed, so it should smooth over momentary spikes in
            # speed (but will take a while to adjust to long-term
            # changes in speed)
            remaining = ((elapsed/seen)*estimate)-elapsed
            completion = now + remaining
            count_str = ('%d/%d %.2f%%'
                         % (seen, estimate, float(seen)/estimate*100))
            completion_str = format_datetime(completion, not deq(completion,now))
            estimate_str = (' (%s remaining; completion %s)'
                            % (format_timedelta(remaining),
                               completion_str))
        else:
            count_str = '%d' % seen
            estimate_str = ''

        if key:
            key_str = ': %s' % key(item)
        else:
            key_str = ''

        # unlike the estimate, the persec count is the number per
        # second for *this* batch only, without smoothing
        if persec and thischunk_seconds > 0:
            persec_str = ' (%.1f/s)' % (float(this_chunk)/thischunk_seconds,)
        else:
            persec_str = ''

        sys.stderr.write('%s%s, %s%s%s\n'
                         % (count_str, persec_str,
                            format_timedelta(elapsed), estimate_str, key_str))

    now = datetime.now()
    elapsed = now - start
    elapsed_seconds = timedelta_to_seconds(elapsed)
    if persec and seen > 0 and elapsed_seconds > 0:
        persec_str = ' (@%.1f/sec)' % (float(seen)/elapsed_seconds)
    else:
        persec_str = ''
    sys.stderr.write('Processed %d%s items in %s..%s (%s)\n'
                     % (seen,
                        persec_str,
                        format_datetime(start, not deq(start, now)),
                        format_datetime(now, not deq(start, now)),
                        format_timedelta(elapsed)))

class Hell(object):
    def __str__(self):
        return "boom!"

class Bomb(object):
    @classmethod
    def __getattr__(cls, key):
        raise Hell()

    @classmethod
    def __setattr__(cls, key, val):
        raise Hell()

    @classmethod
    def __repr__(cls):
        raise Hell()


class SimpleSillyStub(object):
    """A simple stub object that does nothing when you call its methods."""
    def __nonzero__(self):
        return False

    def __getattr__(self, name):
        return self.stub

    def stub(self, *args, **kwargs):
        pass

    __exit__ = __enter__ = stub


def strordict_fullname(item, key='fullname'):
    """Sometimes we migrate AMQP queues from simple strings to pickled
    dictionaries. During the migratory period there may be items in
    the queue of both types, so this function tries to detect which
    the item is. It shouldn't really be used on a given queue for more
    than a few hours or days"""
    try:
        d = pickle.loads(item)
    except:
        d = {key: item}

    if (not isinstance(d, dict)
        or key not in d
        or not isinstance(d[key], str)):
        raise ValueError('Error trying to migrate %r (%r)'
                         % (item, d))

    return d

def thread_dump(*a):
    import sys, traceback
    from datetime import datetime

    sys.stderr.write('%(t)s Thread Dump @%(d)s %(t)s\n' % dict(t='*'*15,
                                                               d=datetime.now()))

    for thread_id, stack in sys._current_frames().items():
        sys.stderr.write('\t-- Thread ID: %s--\n' %  (thread_id,))

        for filename, lineno, fnname, line in traceback.extract_stack(stack):
            sys.stderr.write('\t\t%(filename)s(%(lineno)d): %(fnname)s\n'
                             % dict(filename=filename, lineno=lineno, fnname=fnname))
            sys.stderr.write('\t\t\t%(line)s\n' % dict(line=line))


def constant_time_compare(actual, expected):
    """
    Returns True if the two strings are equal, False otherwise

    The time taken is dependent on the number of characters provided
    instead of the number of characters that match.

    When we upgrade to Python 2.7.7 or newer, we should use hmac.compare_digest
    instead.
    """
    actual_len   = len(actual)
    expected_len = len(expected)
    result = actual_len ^ expected_len
    if expected_len > 0:
        for i in xrange(actual_len):
            result |= ord(actual[i]) ^ ord(expected[i % expected_len])
    return result == 0


def extract_urls_from_markdown(md):
    "Extract URLs that will be hot links from a piece of raw Markdown."

    html = snudown.markdown(_force_utf8(md))
    links = SoupStrainer("a")

    for link in BeautifulSoup(html, parseOnlyThese=links):
        url = link.get('href')
        if url:
            yield url


def extract_user_mentions(text):
    """Return a set of all usernames (lowercased) mentioned in Markdown text.

    This function works by processing the Markdown, and then looking through
    all links in the resulting HTML. Any links that start with /u/ (as a
    relative link) are considered to be a "mention", so this will mostly just
    catch the links created by our auto-linking of /u/ and u/.

    Note that the usernames are converted to lowercase and added to a set,
    so only unique mentions will be returned.
    """
    from r2.lib.validator import chkuser
    usernames = set()

    for url in extract_urls_from_markdown(text):
        if not url.startswith("/u/"):
            continue

        username = url[len("/u/"):]
        if not chkuser(username):
            continue

        usernames.add(username.lower())

    return usernames


def summarize_markdown(md):
    """Get the first paragraph of some Markdown text, potentially truncated."""

    first_graf, sep, rest = md.partition("\n\n")
    return first_graf[:500]


def blockquote_text(text):
    """Wrap a chunk of Markdown text into a blockquote."""
    return "\n".join("> " + line for line in text.splitlines())


def find_containing_network(ip_ranges, address):
    """Find an IP network that contains the given address."""
    addr = ipaddress.ip_address(address)
    for network in ip_ranges:
        if addr in network:
            return network
    return None


def is_throttled(address):
    """Determine if an IP address is in a throttled range."""
    return bool(find_containing_network(g.throttles, address))


def parse_http_basic(authorization_header):
    """Parse the username/credentials out of an HTTP Basic Auth header.

    Raises RequirementException if anything is uncool.
    """
    auth_scheme, auth_token = require_split(authorization_header, 2)
    require(auth_scheme.lower() == "basic")
    try:
        auth_data = base64.b64decode(auth_token)
    except TypeError:
        raise RequirementException
    return require_split(auth_data, 2, ":")


def simple_traceback(limit):
    """Generate a pared-down traceback that's human readable but small.

    `limit` is how many frames of the stack to put in the traceback.

    """

    stack_trace = traceback.extract_stack(limit=limit)[:-2]
    return "\n".join("-".join((os.path.basename(filename),
                               function_name,
                               str(line_number),
                              ))
                     for filename, line_number, function_name, text
                     in stack_trace)


def weighted_lottery(weights, _random=random.random):
    """Randomly choose a key from a dict where values are weights.

    Weights should be non-negative numbers, and at least one weight must be
    non-zero. The probability that a key will be selected is proportional to
    its weight relative to the sum of all weights. Keys with zero weight will
    be ignored.

    Raises ValueError if weights is empty or contains a negative weight.
    """

    total = sum(weights.itervalues())
    if total <= 0:
        raise ValueError("total weight must be positive")

    r = _random() * total
    t = 0
    for key, weight in weights.iteritems():
        if weight < 0:
            raise ValueError("weight for %r must be non-negative" % key)
        t += weight
        if t > r:
            return key

    # this point should never be reached
    raise ValueError(
        "weighted_lottery messed up: r=%r, t=%r, total=%r" % (r, t, total))


class GoldPrice(object):
    """Simple price math / formatting type.

    Prices are assumed to be USD at the moment.

    """
    def __init__(self, decimal):
        self.decimal = Decimal(decimal)

    def __mul__(self, other):
        return type(self)(self.decimal * other)

    def __div__(self, other):
        return type(self)(self.decimal / other)

    def __str__(self):
        return "$%s" % self.decimal.quantize(Decimal("1.00"))

    def __repr__(self):
        return "%s(%s)" % (type(self).__name__, self)

    @property
    def pennies(self):
        return int(self.decimal * 100)


def config_gold_price(v, key=None, data=None):
    return GoldPrice(v)


def canonicalize_email(email):
    """Return the given email address without various localpart manglings.

    a.s.d.f+something@gmail.com --> asdf@gmail.com

    This is not at all RFC-compliant or correct. It's only intended to be a
    quick heuristic to remove commonly used mangling techniques.

    """

    if not email:
        return ""

    email = _force_utf8(email.lower())

    localpart, at, domain = email.partition("@")
    if not at or "@" in domain:
        return ""

    localpart = localpart.replace(".", "")
    localpart = localpart.partition("+")[0]

    return localpart + "@" + domain


def precise_format_timedelta(delta, locale, threshold=.85, decimals=2):
    """Like babel.dates.format_datetime but with adjustable precision"""
    seconds = delta.total_seconds()

    for unit, secs_per_unit in TIMEDELTA_UNITS:
        value = abs(seconds) / secs_per_unit
        if value >= threshold:
            plural_form = locale.plural_form(value)
            pattern = None
            for choice in (unit + ':medium', unit):
                patterns = locale._data['unit_patterns'].get(choice)
                if patterns is not None:
                    pattern = patterns[plural_form]
                    break
            if pattern is None:
                return u''
            decimals = int(decimals)
            format_string = "%." + str(decimals) + "f"
            return pattern.replace('{0}', format_string % value)
    return u''


def parse_ini_file(config_file):
    """Given an open file, read and parse it like an ini file."""

    parser = ConfigParser.RawConfigParser()
    parser.optionxform = str  # ensure keys are case-sensitive as expected
    parser.readfp(config_file)
    return parser

def fuzz_activity(count):
    """Add some jitter to an activity metric to maintain privacy."""
    # decay constant is e**(-x / 60)
    decay = math.exp(float(-count) / 60)
    jitter = round(5 * decay)
    return count + random.randint(0, jitter)


def shuffle_slice(x, start, stop=None):
    """Given a list, shuffle a portion of the list in-place, returning None.

    This uses a knuth shuffle borrowed from http://stackoverflow.com/a/11706463
    which is a slightly tweaked version of shuffle from the `random` stdlib:
    https://hg.python.org/cpython/file/8962d1c442a6/Lib/random.py#l256
    """
    if stop is None:
        stop = len(x)

    for i in reversed(xrange(start + 1, stop)):
        j = random.randint(start, i)
        x[i], x[j] = x[j], x[i]


# port of https://docs.python.org/dev/library/itertools.html#itertools-recipes
def partition(pred, iterable):
    "Use a predicate to partition entries into false entries and true entries"
    # partition(is_odd, range(10)) --> 0 2 4 6 8   and  1 3 5 7 9
    t1, t2 = itertools.tee(iterable)
    return itertools.ifilterfalse(pred, t1), itertools.ifilter(pred, t2)

# http://docs.python.org/2/library/itertools.html#recipes
def roundrobin(*iterables):
    "roundrobin('ABC', 'D', 'EF') --> A D E B F C"
    # Recipe credited to George Sakkis
    pending = len(iterables)
    nexts = itertools.cycle(iter(it).next for it in iterables)
    while pending:
        try:
            for next in nexts:
                yield next()
        except StopIteration:
            pending -= 1
            nexts = itertools.cycle(itertools.islice(nexts, pending))


def lowercase_keys_recursively(subject):
    """Return a dict with all keys lowercased (recursively)."""
    lowercased = dict()
    for key, val in subject.iteritems():
        if isinstance(val, dict):
            val = lowercase_keys_recursively(val)
        lowercased[key.lower()] = val

    return lowercased


def sampled(live_config_var):
    """Wrap a function that should only actually run occasionally

    The wrapped function will only actually execute at the rate
    specified by the live_config sample rate given.

    Example:

    @sampled("foobar_sample_rate")
    def foobar():
        ...

    If g.live_config["foobar_sample_rate"] is set to 0.5, foobar()
    will only execute 50% of the time when it is called.

    """
    def sampled_decorator(fn):
        @functools.wraps(fn)
        def sampled_fn(*a, **kw):
            if random.random() > g.live_config[live_config_var]:
                return None
            else:
                return fn(*a, **kw)
        return sampled_fn
    return sampled_decorator


def squelch_exceptions(fn):
    """Wrap a function to log and suppress all internal exceptions

    When running in debug mode, the exception will be propagated, but
    in production environments, the function exception will be logged,
    then suppressed.

    Use of this decorator is not an excuse to not handle exceptions

    """
    @functools.wraps(fn)
    def squelched_fn(*a, **kw):
        try:
            return fn(*a, **kw)
        except BaseException:
            if g.debug:
                raise
            else:
                # log.exception will send a stack trace as well
                g.log.exception("squelching exception")
    return squelched_fn


EPOCH = datetime(1970, 1, 1, tzinfo=pytz.UTC)


def epoch_timestamp(dt):
    """Returns the number of seconds from the epoch to date.

    :param datetime dt: datetime (with time zone)
    :rtype: float
    """
    return (dt - EPOCH).total_seconds()


def to_epoch_milliseconds(dt):
    """Returns the number of milliseconds from the epoch to date.

    :param datetime dt: datetime (with time zone)
    :rtype: int
    """
    return int(math.floor(1000. * epoch_timestamp(dt)))


def from_epoch_milliseconds(ms):
    """Convert milliseconds from the epoch to UTC datetime.

    :param int ms: milliseconds since the epoch
    :rtype: :py:class:`datetime.datetime`
    """
    seconds = int(ms / 1000.)
    microseconds = (ms - 1000 * seconds) * 1000.
    return EPOCH + timedelta(seconds=seconds, microseconds=microseconds)


def rate_limiter(max_per_second):
    """Limit number of calls to returned closure per second to max_per_second
    algorithm adapted from here:
        http://blog.gregburek.com/2011/12/05/Rate-limiting-with-decorators/
    """
    min_interval = 1.0 / float(max_per_second)
    # last_time_called needs to be a list so we can do a closure on it
    last_time_called = [0.0]

    def throttler():
        elapsed = time.clock() - last_time_called[0]
        left_to_wait = min_interval - elapsed
        if left_to_wait > 0:
            time.sleep(left_to_wait)
        last_time_called[0] = time.clock()
    return throttler


def rate_limited_generator(rate_limit_per_second, iterable):
    """Yield from iterable without going over rate limit"""
    throttler = rate_limiter(rate_limit_per_second)
    for i in iterable:
        throttler()
        yield i
