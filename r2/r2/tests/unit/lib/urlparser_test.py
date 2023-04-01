#!/usr/bin/env python
# coding=utf-8
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
import unittest

from r2.lib.utils import UrlParser
from r2.tests import RedditTestCase
from pylons import app_globals as g


class TestIsRedditURL(RedditTestCase):

    def setUp(self):
        self.patch_g(offsite_subdomains=['blog'])

    def _is_safe_reddit_url(self, url, subreddit=None):
        web_safe = UrlParser(url).is_web_safe_url()
        return web_safe and UrlParser(url).is_reddit_url(subreddit)

    def assertIsSafeRedditUrl(self, url, subreddit=None):
        self.assertTrue(self._is_safe_reddit_url(url, subreddit))

    def assertIsNotSafeRedditUrl(self, url, subreddit=None):
        self.assertFalse(self._is_safe_reddit_url(url, subreddit))

    def test_normal_urls(self):
        self.assertIsSafeRedditUrl("https://%s/" % g.domain)
        self.assertIsSafeRedditUrl("https://en.%s/" % g.domain)
        self.assertIsSafeRedditUrl("https://foobar.baz.%s/quux/?a" % g.domain)
        self.assertIsSafeRedditUrl("#anchorage")
        self.assertIsSafeRedditUrl("?path_relative_queries")
        self.assertIsSafeRedditUrl("/")
        self.assertIsSafeRedditUrl("/cats")
        self.assertIsSafeRedditUrl("/cats/")
        self.assertIsSafeRedditUrl("/cats/#maru")
        self.assertIsSafeRedditUrl("//foobaz.%s/aa/baz#quux" % g.domain)
        # XXX: This is technically a legal relative URL, are there any UAs
        # stupid enough to treat this as absolute?
        self.assertIsSafeRedditUrl("path_relative_subpath.com")
        # "blog.reddit.com" is not a reddit URL.
        self.assertIsNotSafeRedditUrl("http://blog.%s/" % g.domain)
        self.assertIsNotSafeRedditUrl("http://foo.blog.%s/" % g.domain)

    def test_incorrect_anchoring(self):
        self.assertIsNotSafeRedditUrl("http://www.%s.whatever.com/" % g.domain)

    def test_protocol_relative(self):
        self.assertIsNotSafeRedditUrl("//foobaz.example.com/aa/baz#quux")

    def test_weird_protocols(self):
        self.assertIsNotSafeRedditUrl(
            "javascript://%s/%%0d%%0aalert(1)" % g.domain
        )
        self.assertIsNotSafeRedditUrl("hackery:whatever")

    def test_http_auth(self):
        # There's no legitimate reason to include HTTP auth details in the URL,
        # they only serve to confuse everyone involved.
        # For example, this used to be the behaviour of `UrlParser`, oops!
        # > UrlParser("http://everyoneforgets:aboutthese@/baz.com/").unparse()
        # 'http:///baz.com/'
        self.assertIsNotSafeRedditUrl("http://foo:bar@/example.com/")

    def test_browser_quirks(self):
        # Some browsers try to be helpful and ignore characters in URLs that
        # they think might have been accidental (I guess due to things like:
        # `<a href=" http://badathtml.com/ ">`. We need to ignore those when
        # determining if a URL is local.
        self.assertIsNotSafeRedditUrl("/\x00/example.com")
        self.assertIsNotSafeRedditUrl("\x09//example.com")
        self.assertIsNotSafeRedditUrl(" http://example.com/")

        # This is makes sure we're not vulnerable to a bug in
        # urlparse / urlunparse.
        # urlunparse(urlparse("////foo.com")) == "//foo.com"! screwy!
        self.assertIsNotSafeRedditUrl("////example.com/")
        self.assertIsNotSafeRedditUrl("//////example.com/")
        # Similar, but with a scheme
        self.assertIsNotSafeRedditUrl(r"http:///example.com/")
        # Webkit and co like to treat backslashes as equivalent to slashes in
        # different places, maybe to make OCD Windows users happy.
        self.assertIsNotSafeRedditUrl(r"/\example.com/")
        # On chrome this goes to example.com, not a subdomain of reddit.com!
        self.assertIsNotSafeRedditUrl(
            r"http://\\example.com\a.%s/foo" % g.domain
        )

        # Combo attacks!
        self.assertIsNotSafeRedditUrl(r"///\example.com/")
        self.assertIsNotSafeRedditUrl(r"\\example.com")
        self.assertIsNotSafeRedditUrl("/\x00//\\example.com/")
        self.assertIsNotSafeRedditUrl(
            "\x09javascript://%s/%%0d%%0aalert(1)" % g.domain
        )
        self.assertIsNotSafeRedditUrl(
            "http://\x09example.com\\%s/foo" % g.domain
        )

    def test_url_mutation(self):
        u = UrlParser("http://example.com/")
        u.hostname = g.domain
        self.assertTrue(u.is_reddit_url())

        u = UrlParser("http://%s/" % g.domain)
        u.hostname = "example.com"
        self.assertFalse(u.is_reddit_url())

    def test_nbsp_allowances(self):
        # We have to allow nbsps in URLs, let's just allow them where they can't
        # do any damage.
        self.assertIsNotSafeRedditUrl("http://\xa0.%s/" % g.domain)
        self.assertIsNotSafeRedditUrl("\xa0http://%s/" % g.domain)
        self.assertIsSafeRedditUrl("http://%s/\xa0" % g.domain)
        self.assertIsSafeRedditUrl("/foo/bar/\xa0baz")
        # Make sure this works if the URL is unicode
        self.assertIsNotSafeRedditUrl(u"http://\xa0.%s/" % g.domain)
        self.assertIsNotSafeRedditUrl(u"\xa0http://%s/" % g.domain)
        self.assertIsSafeRedditUrl(u"http://%s/\xa0" % g.domain)
        self.assertIsSafeRedditUrl(u"/foo/bar/\xa0baz")


class TestSwitchSubdomainByExtension(RedditTestCase):
    def setUp(self):
        self.patch_g(
            domain='reddit.com',
            domain_prefix='www',
        )

    def test_normal_urls(self):
        u = UrlParser('http://www.reddit.com/r/redditdev')
        u.switch_subdomain_by_extension('compact')
        result = u.unparse()
        self.assertEquals('http://i.reddit.com/r/redditdev', result)

        u = UrlParser(result)
        u.switch_subdomain_by_extension('mobile')
        result = u.unparse()
        self.assertEquals('http://simple.reddit.com/r/redditdev', result)

    def test_default_prefix(self):
        u = UrlParser('http://i.reddit.com/r/redditdev')
        u.switch_subdomain_by_extension()
        self.assertEquals('http://www.reddit.com/r/redditdev', u.unparse())

        u = UrlParser('http://i.reddit.com/r/redditdev')
        u.switch_subdomain_by_extension('does-not-exist')
        self.assertEquals('http://www.reddit.com/r/redditdev', u.unparse())


class TestPathExtension(unittest.TestCase):
    def test_no_path(self):
        u = UrlParser('http://example.com')
        self.assertEquals('', u.path_extension())

    def test_directory(self):
        u = UrlParser('http://example.com/')
        self.assertEquals('', u.path_extension())

        u = UrlParser('http://example.com/foo/')
        self.assertEquals('', u.path_extension())

    def test_no_extension(self):
        u = UrlParser('http://example.com/a')
        self.assertEquals('', u.path_extension())

    def test_root_file(self):
        u = UrlParser('http://example.com/a.jpg')
        self.assertEquals('jpg', u.path_extension())

    def test_nested_file(self):
        u = UrlParser('http://example.com/foo/a.jpg')
        self.assertEquals('jpg', u.path_extension())

    def test_empty_extension(self):
        u = UrlParser('http://example.com/a.')
        self.assertEquals('', u.path_extension())

    def test_two_extensions(self):
        u = UrlParser('http://example.com/a.jpg.exe')
        self.assertEquals('exe', u.path_extension())

    def test_only_extension(self):
        u = UrlParser('http://example.com/.bashrc')
        self.assertEquals('bashrc', u.path_extension())


class TestEquality(unittest.TestCase):
    def test_different_objects(self):
        u = UrlParser('http://example.com')
        self.assertNotEquals(u, None)

    def test_different_protocols(self):
        u = UrlParser('http://example.com')
        u2 = UrlParser('https://example.com')
        self.assertNotEquals(u, u2)

    def test_different_domains(self):
        u = UrlParser('http://example.com')
        u2 = UrlParser('http://example.org')
        self.assertNotEquals(u, u2)

    def test_different_ports(self):
        u = UrlParser('http://example.com')
        u2 = UrlParser('http://example.com:8000')
        u3 = UrlParser('http://example.com:8008')
        self.assertNotEquals(u, u2)
        self.assertNotEquals(u2, u3)

    def test_different_paths(self):
        u = UrlParser('http://example.com')
        u2 = UrlParser('http://example.com/a')
        u3 = UrlParser('http://example.com/b')
        self.assertNotEquals(u, u2)
        self.assertNotEquals(u2, u3)

    def test_different_params(self):
        u = UrlParser('http://example.com/')
        u2 = UrlParser('http://example.com/;foo')
        u3 = UrlParser('http://example.com/;bar')
        self.assertNotEquals(u, u2)
        self.assertNotEquals(u2, u3)

    def test_different_queries(self):
        u = UrlParser('http://example.com/')
        u2 = UrlParser('http://example.com/?foo')
        u3 = UrlParser('http://example.com/?foo=bar')
        self.assertNotEquals(u, u2)
        self.assertNotEquals(u2, u3)

    def test_different_fragments(self):
        u = UrlParser('http://example.com/')
        u2 = UrlParser('http://example.com/#foo')
        u3 = UrlParser('http://example.com/#bar')
        self.assertNotEquals(u, u2)
        self.assertNotEquals(u2, u3)

    def test_same_url(self):
        u = UrlParser('http://example.com:8000/a;b?foo=bar&bar=baz#spam')
        u2 = UrlParser('http://example.com:8000/a;b?bar=baz&foo=bar#spam')
        self.assertEquals(u, u2)

        u3 = UrlParser('')
        u3.scheme = 'http'
        u3.hostname = 'example.com'
        u3.port = 8000
        u3.path = '/a'
        u3.params = 'b'
        u3.update_query(foo='bar', bar='baz')
        u3.fragment = 'spam'
        self.assertEquals(u, u3)

    def test_integer_query_params(self):
        u = UrlParser('http://example.com/?page=1234')
        u2 = UrlParser('http://example.com/')
        u2.update_query(page=1234)
        self.assertEquals(u, u2)

    def test_unicode_query_params(self):
        u = UrlParser(u'http://example.com/?page=ｕｎｉｃｏｄｅ：（')
        u2 = UrlParser('http://example.com/')
        u2.update_query(page=u'ｕｎｉｃｏｄｅ：（')
        self.assertEquals(u, u2)
