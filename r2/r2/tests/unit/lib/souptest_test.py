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

from r2.lib.souptest import (
    souptest_fragment,
    SoupDetectedCrasherError,
    SoupError,
    SoupSyntaxError,
    SoupUnexpectedCDataSectionError,
    SoupUnexpectedCommentError,
    SoupUnsupportedAttrError,
    SoupUnsupportedEntityError,
    SoupUnsupportedNodeError,
    SoupUnsupportedSchemeError,
    SoupUnsupportedTagError,
)


class TestSoupTest(unittest.TestCase):
    def assertFragmentRaises(self, fragment, error):
        self.assertRaises(error, souptest_fragment, fragment)

    def assertFragmentValid(self, fragment):
        souptest_fragment(fragment)

    def test_benign(self):
        """A typical example of what we might get out of `safemarkdown()`"""
        testcase = """
            <!-- SC_OFF -->
            <div class="md"><a href="http://zombo.com/">Welcome</a></div>
            <!-- SC_ON -->
        """
        self.assertFragmentValid(testcase)

    def test_unbalanced(self):
        self.assertFragmentRaises("<div></div></div>", SoupSyntaxError)

    def test_unclosed_comment(self):
        self.assertFragmentRaises("<!--", SoupSyntaxError)

    def test_invalid_comment(self):
        testcase = "<!--[if IE 6]>WHAT YEAR IS IT?<![endif]-->"
        self.assertFragmentRaises(testcase, SoupUnexpectedCommentError)

    def test_quoting(self):
        self.assertFragmentRaises("<div class=`poor IE`></div>",
                                  SoupSyntaxError)

    def test_processing_instruction(self):
        self.assertFragmentRaises("<?php not even once ?>",
                                  SoupUnsupportedNodeError)

    def test_doctype(self):
        self.assertFragmentRaises('<!DOCTYPE VRML>', SoupSyntaxError)

    def test_entity_declarations(self):
        testcase = '<!ENTITY lol "bad things">'
        self.assertFragmentRaises(testcase, SoupSyntaxError)
        testcase = '<!DOCTYPE div- [<!ENTITY lol "bad things">]>'
        self.assertFragmentRaises(testcase, SoupSyntaxError)

    def test_cdata_section(self):
        testcase = '<![CDATA[If only XHTML 2 went anywhere]]>'
        self.assertFragmentRaises(testcase, SoupUnexpectedCDataSectionError)

    def test_entities(self):
        self.assertFragmentRaises('&xml:what;', SoupError)
        self.assertFragmentRaises('&foo,bar;', SoupError)
        self.assertFragmentRaises('&#999999999999;', SoupUnsupportedEntityError)
        self.assertFragmentRaises('&#00;', SoupUnsupportedEntityError)
        self.assertFragmentRaises('&foo-bar;', SoupUnsupportedEntityError)
        self.assertFragmentRaises('&foobar;', SoupUnsupportedEntityError)
        self.assertFragmentValid('&nbsp;')
        self.assertFragmentValid('&Omicron;')

    def test_tag_whitelist(self):
        testcase = "<div><a><a><script>alert(1)</script></a></a></div>"
        self.assertFragmentRaises(testcase, SoupUnsupportedTagError)

    def test_attr_whitelist(self):
        testcase = '<div><a><a><em onclick="alert(1)">FOO!</em></a></a></div>'
        self.assertFragmentRaises(testcase, SoupUnsupportedAttrError)

    def test_tag_xmlns(self):
        self.assertFragmentRaises('<xml:div></xml:div>',
                                  SoupUnsupportedTagError)
        self.assertFragmentRaises('<div xmlns="http://zombo.com/foo"></div>',
                                  SoupError)

    def test_attr_xmlns(self):
        self.assertFragmentRaises('<div xml:class="baz"></div>',
                                  SoupUnsupportedAttrError)

    def test_schemes(self):
        self.assertFragmentValid('<a href="http://google.com">a</a>')
        self.assertFragmentValid('<a href="Http://google.com">a</a>')
        self.assertFragmentValid('<a href="/google.com">a</a>')
        self.assertFragmentRaises('<a href="javascript://google.com">a</a>',
                                  SoupUnsupportedSchemeError)

    def test_crashers(self):
        # Chrome crashes on weirdly encoded nulls.
        self.assertFragmentRaises('<a href="http://example.com/%%30%30">foo</a>',
                                  SoupDetectedCrasherError)
        self.assertFragmentRaises('<a href="http://example.com/%0%30">foo</a>',
                                  SoupDetectedCrasherError)
        self.assertFragmentRaises('<a href="http://example.com/%%300">foo</a>',
                                  SoupDetectedCrasherError)
        # Chrome crashes on extremely long hostnames
        self.assertFragmentRaises('<a href="http://%s.com">foo</a>' % ("x" * 300),
                                  SoupDetectedCrasherError)
