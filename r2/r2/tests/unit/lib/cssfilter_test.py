#!/usr/bin/env python
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

from r2.lib.cssfilter import validate_css


class TestCSSFilter(unittest.TestCase):
    def assertInvalid(self, css):
        serialized, errors = validate_css(css, {})
        self.assertNotEqual(errors, [])

    def test_offsite_url(self):
        testcase = u"*{background-image:url('http://foobar/')}"
        self.assertInvalid(testcase)

    def test_nested_url(self):
        testcase = u"*{background-image:calc(url('http://foobar/'))}"
        self.assertInvalid(testcase)

    def test_url_prelude(self):
        testcase = u"*[foo=url('http://foobar/')]{color:red;}"
        self.assertInvalid(testcase)

    def test_invalid_property(self):
        testcase = u"*{foo: red;}"
        self.assertInvalid(testcase)

    def test_import(self):
        testcase = u"@import 'foobar'; *{}"
        self.assertInvalid(testcase)

    def test_import_rule(self):
        testcase = u"*{ @import 'foobar'; }"
        self.assertInvalid(testcase)

    # IE<8 XSS
    def test_invalid_function(self):
        testcase = u"*{color:expression(alert(1));}"
        self.assertInvalid(testcase)

    def test_invalid_function_prelude(self):
        testcase = u"*[foo=expression(alert(1))]{color:red;}"
        self.assertInvalid(testcase)

    # Safari 5.x parser resynchronization issues
    def test_semicolon_function(self):
        testcase = u"*{color: calc(;color:red;);}"
        self.assertInvalid(testcase)

    def test_semicolon_block(self):
        testcase = u"*{color: [;color:red;];}"
        self.assertInvalid(testcase)

    # Safari 5.x prelude escape
    def test_escape_prelude(self):
        testcase = u"*[foo=bar{}*{color:blue}]{color:red;}"
        self.assertInvalid(testcase)

    # Multi-browser url() escape via spaces inside quotes
    def test_escape_url(self):
        testcase = u"*{background-image: url('foo bar');}"
        self.assertInvalid(testcase)

    # Control chars break out of quotes in multiple browsers
    def test_control_chars(self):
        testcase = u"*{font-family:'foobar\x03;color:red;';}"
        self.assertInvalid(testcase)

    def test_embedded_nulls(self):
        testcase = u"*{font-family:'foo\x00bar'}"
        self.assertInvalid(testcase)

    # Firefox allows backslashes in function names
    def test_escaped_url(self):
        testcase = u"*{background-image:\\u\\r\\l('http://foobar/')}"
        self.assertInvalid(testcase)

    # IE<8 allows backslash escapes in place of pretty much any char
    def test_escape_function_obfuscation(self):
        testcase = u"*{color: expression\\28 alert\\28 1 \\29 \\29 }"
        self.assertInvalid(testcase)

    # This is purely speculative, and may never affect actual browsers
    # https://developer.mozilla.org/en-US/docs/Web/CSS/attr
    def test_attr_url(self):
        testcase = u"*{background-image:attr(foobar url);}"
        self.assertInvalid(testcase)
