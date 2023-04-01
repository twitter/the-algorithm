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
"""
Tools to check if arbitrary HTML fragments would be safe to embed inline
"""


import os
import re
import sys
import urllib
import urlparse

import lxml.etree

from cStringIO import StringIO

valid_link_schemes = (
    '/',
    '#',
    'http://',
    'https://',
    'ftp://',
    'mailto:',
    'steam://',
    'irc://',
    'ircs://',
    'news://',
    'mumble://',
    'ssh://',
    'git://',
    'ts3server://',
)

allowed_tags = {
    'div': {'class'},
    'a': {'href', 'title', 'target', 'nofollow', 'rel'},
    'img': {'src', 'alt', 'title'},
}

markdown_boring_tags = {
    'p', 'em', 'strong', 'br', 'ol', 'ul', 'hr', 'li', 'pre', 'code',
    'blockquote', 'center', 'sup', 'del', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6',
}

markdown_user_tags = {
    'table', 'th', 'tr', 'td', 'tbody', 'thead', 'tfoot', 'caption',
}

for bt in markdown_boring_tags:
    allowed_tags[bt] = {'id', 'class'}

for bt in markdown_user_tags:
    allowed_tags[bt] = {
        'colspan', 'rowspan', 'cellspacing', 'cellpadding', 'align', 'scope',
    }


def souptest_sniff_node(node):
    """Check that a node from an (X)HTML document passes the sniff test"""
    # Because IE loves conditional comments.
    if node.tag is lxml.etree.Comment:
        # Benign, used to turn space compression on and off.
        if node.text.strip() not in {"SC_ON", "SC_OFF"}:
            raise SoupUnexpectedCommentError(node)
    # Looks like all nodes but `Element` have functions in `.tag`
    elif isinstance(node.tag, basestring):
        # namespaces are tacked onto the front of the tag / attr name if
        # applicable so we don't need to worry about checking for those.
        tag_name = node.tag
        if tag_name not in allowed_tags:
            raise SoupUnsupportedTagError(tag_name)

        for attr, val in node.items():
            if attr not in allowed_tags[tag_name]:
                raise SoupUnsupportedAttrError(attr)

            if tag_name == 'a' and attr == 'href':
                lv = val.lower()
                if not lv.startswith(valid_link_schemes):
                    raise SoupUnsupportedSchemeError(val)
                # work around CRBUG-464270
                parsed_url = urlparse.urlparse(lv)
                if parsed_url.hostname and len(parsed_url.hostname) > 255:
                    raise SoupDetectedCrasherError(parsed_url.hostname)
                # work around for Chrome crash with "%%30%30" - Sep 2015
                if "%00" in urllib.unquote(parsed_url.path):
                    raise SoupDetectedCrasherError(lv)
    else:
        # Processing instructions and friends fall down here.
        raise SoupUnsupportedNodeError(node)


ENTITY_DTD_PATH = os.path.join(
    os.path.dirname(os.path.abspath(__file__)),
    'contrib/dtds/allowed_entities.dtd')

SOUPTEST_DOCTYPE_FMT = '<!DOCTYPE div- [\n %s \n]>'

UNDEFINED_ENTITY_RE = re.compile(r"\AEntity '([^']*)' not defined,.*")


def souptest_fragment(fragment):
    """Check if an HTML fragment is sane and safe to embed.

    For checking markup is safe, and that it will embed properly in both HTML
    and XML documents. In practice, this means we check that all
    tags / attributes are safe, and that the syntax conforms to a restricted
    subset of XHTML.

    No processing instructions, only specific comments, no CDATA sections,
    and a whitelist of named character entities to deal with inconsistencies
    between how parsers handle lookup failures for them.
    """
    # We lazily load this so processes that don't souptest don't have to load
    # it every startup.
    souptest_doctype = getattr(souptest_fragment, 'souptest_doctype', None)
    if souptest_doctype is None:
        # Slurp in all of the entity definitions so we can avoid a read every
        # time we parse
        with open(ENTITY_DTD_PATH, 'r') as ent_file:
            souptest_doctype = SOUPTEST_DOCTYPE_FMT % ent_file.read()
        souptest_fragment.souptest_doctype = souptest_doctype

    # lxml makes it *very* difficult to tell if there's a CDATA node in the
    # tree, even if you tell it not to fold them into adjacent text sections.
    # CDATA sections will be ignored and their innards parsed in most doctypes,
    # so we need this hack to keep them out of markup.
    if "<![CDATA" in fragment:
        raise SoupUnexpectedCDataSectionError(fragment)

    # We need to prepend our doctype + DTD or lxml can't resolve entities.
    # We also need to wrap everything in a div, as lxml throws out
    # comments outside the root tag. This also ensures that attempting an
    # entity declaration or similar shenanigans will cause a syntax error.
    documentized_fragment = "%s<div>%s</div>" % (souptest_doctype, fragment)
    s = StringIO(documentized_fragment)

    try:
        parser = lxml.etree.XMLParser()
        # Can't use a SAX interface because lxml's doesn't give you a handler
        # for comments or entities unless you use Python 3. Oh well.
        for node in lxml.etree.parse(s, parser).iter():
            souptest_sniff_node(node)
    except lxml.etree.XMLSyntaxError:
        # Wrap the exception while keeping the original traceback
        type_, value, trace = sys.exc_info()
        # In XML some characters are illegal even as references, thankfully
        # they're almost all control codes: (`&#x00;`, `&#x1c;`, etc.)
        if value.msg.startswith('xmlParseCharRef: invalid xmlChar '):
            raise SoupUnsupportedEntityError, (value,), trace
        undef_ent = re.match(UNDEFINED_ENTITY_RE, value.msg)
        if undef_ent:
            raise SoupUnsupportedEntityError, (value, undef_ent.group(1)), trace

        raise SoupSyntaxError, (value,), trace


class SoupError(Exception):
    """An error specific to the souptesting process"""
    pass


class SoupReprError(SoupError):
    """Give a class-defined message as well as a repr of the passed object"""
    HUMAN_MESSAGE = None

    def __init__(self, obj):
        self.obj = obj

    def __str__(self):
        return "HAX: %s: %r" % (self.HUMAN_MESSAGE, self.obj)


class SoupSyntaxError(SoupReprError):
    """Found a general syntax error"""
    HUMAN_MESSAGE = "XML Parsing error"


class SoupUnsupportedNodeError(SoupReprError):
    """Found a weird node, like a processing instruction"""
    HUMAN_MESSAGE = "Unsupported node type"


class SoupUnexpectedCommentError(SoupReprError):
    """Found a comment that hasn't been specifically whitelisted"""
    HUMAN_MESSAGE = "Unexpected comment"


class SoupUnexpectedCDataSectionError(SoupReprError):
    """Found a CDATA section, which have no meaning in HTML5"""
    HUMAN_MESSAGE = "Unexpected CDATA section"


class SoupUnsupportedSchemeError(SoupReprError):
    """Found a URL whose scheme hasn't been explicitly whitelisted"""
    HUMAN_MESSAGE = "Unsupported URL scheme"


class SoupUnsupportedAttrError(SoupReprError):
    """Found an element attribute that hasn't been explicitly whitelisted"""
    HUMAN_MESSAGE = "Unsupported attribute"


class SoupUnsupportedTagError(SoupReprError):
    """Found an element that hasn't been explicitly whitelisted"""
    HUMAN_MESSAGE = "Unsupported tag"


class SoupDetectedCrasherError(SoupReprError):
    HUMAN_MESSAGE = "Known crasher posted"


class SoupUnsupportedEntityError(SoupReprError):
    """Found an otherwise well-formed entity that couldn't be accepted"""
    HUMAN_MESSAGE = "Invalid or unrecognized entity"

    # `entity` is optional, because we can't get the passed-in entity in
    # the case of an exception because of invalid numeric entities.
    def __init__(self, obj, entity=None):
        self.entity = entity
        SoupReprError.__init__(self, obj)
