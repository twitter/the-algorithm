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

import cgi
import json
import re

from collections import Counter

import snudown

from BeautifulSoup import BeautifulSoup, Tag
from pylons import tmpl_context as c
from pylons import app_globals as g

from r2.lib.souptest import (
    souptest_fragment,
    SoupError,
    SoupUnsupportedEntityError,
)
from r2.lib.unicode import _force_utf8, _force_unicode

SC_OFF = "<!-- SC_OFF -->"
SC_ON = "<!-- SC_ON -->"

MD_START = '<div class="md">'
MD_END = '</div>'

WIKI_MD_START = '<div class="md wiki">'
WIKI_MD_END = '</div>'

custom_img_url = re.compile(r'\A%%([a-zA-Z0-9\-]+)%%$')

def python_websafe(text):
    return text.replace('&', "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace('"', "&quot;")

def python_websafe_json(text):
    return text.replace('&', "&amp;").replace("<", "&lt;").replace(">", "&gt;")

try:
    from Cfilters import uwebsafe as c_websafe, uspace_compress, \
        uwebsafe_json as c_websafe_json
    def spaceCompress(text):
        try:
            text = unicode(text, 'utf-8')
        except TypeError:
            text = unicode(text)
        return uspace_compress(text)
except ImportError:
    c_websafe      = python_websafe
    c_websafe_json = python_websafe_json
    _between_tags1 = re.compile('> +')
    _between_tags2 = re.compile(' +<')
    _spaces = re.compile('[\s]+')
    _ignore = re.compile('(' + SC_OFF + '|' + SC_ON + ')', re.S | re.I)
    def spaceCompress(content):
        res = ''
        sc = True
        for p in _ignore.split(content):
            if p == SC_ON:
                sc = True
            elif p == SC_OFF:
                sc = False
            elif sc:
                p = _spaces.sub(' ', p)
                p = _between_tags1.sub('>', p)
                p = _between_tags2.sub('<', p)
                res += p
            else:
                res += p

        return res


class _Unsafe(unicode):
    # Necessary so Wrapped instances with these can get cached
    def cache_key(self, style):
        return unicode(self)


def unsafe(text=''):
    return _Unsafe(_force_unicode(text))

def websafe_json(text=""):
    return c_websafe_json(_force_unicode(text))

def double_websafe(text=""):
    # RSS requires double escaping on fields that could be interpreted as HTML
    return unsafe(python_websafe(python_websafe(text)))

def conditional_websafe(text = ''):
    from wrapped import Templated, CacheStub

    if text.__class__ == _Unsafe:
        return text
    elif isinstance(text, Templated):
        return _Unsafe(text.render())
    elif isinstance(text, CacheStub):
        return _Unsafe(text)
    elif text is None:
        return ""
    elif text.__class__ != unicode:
        text = _force_unicode(text)
    return c_websafe(text)


def mako_websafe(text=''):
    """Wrapper for conditional_websafe so cached templates don't explode"""
    return conditional_websafe(text)


def websafe(text=''):
    if text.__class__ != unicode:
        text = _force_unicode(text)
    #wrap the response in _Unsafe so make_websafe doesn't unescape it
    return _Unsafe(c_websafe(text))


# From https://github.com/django/django/blob/master/django/utils/html.py
_js_escapes = {
    ord('\\'): u'\\u005C',
    ord('\''): u'\\u0027',
    ord('"'): u'\\u0022',
    ord('>'): u'\\u003E',
    ord('<'): u'\\u003C',
    ord('&'): u'\\u0026',
    ord('='): u'\\u003D',
    ord('-'): u'\\u002D',
    ord(';'): u'\\u003B',
    ord(u'\u2028'): u'\\u2028',
    ord(u'\u2029'): u'\\u2029',
}
# Escape every ASCII character with a value less than 32.
_js_escapes.update((ord('%c' % z), u'\\u%04X' % z) for z in range(32))


def jssafe(text=u''):
    """Prevents text from breaking outside of string literals in JS"""
    if text.__class__ != unicode:
        text = _force_unicode(text)
    # wrap the response in _Unsafe so conditional_websafe doesn't touch it
    return _Unsafe(text.translate(_js_escapes))


_json_escapes = {
    ord('>'): u'\\u003E',
    ord('<'): u'\\u003C',
    ord('&'): u'\\u0026',
}


def scriptsafe_dumps(obj, **kwargs):
    """
    Like `json.dumps()`, but safe for use in `<script>` blocks.

    Also nice for response bodies that might be consumed by terrible browsers!

    You should avoid using this to template data into inline event handlers.
    When possible, you should do something like this instead:
    ```
    <button
      onclick="console.log($(this).data('json-thing'))"
      data-json-thing="${json_thing}">
    </button>
    ```
    """
    text = _force_unicode(json.dumps(obj, **kwargs))
    # wrap the response in _Unsafe so conditional_websafe doesn't touch it
    # TODO: this might be a hot path soon, C-ify it?
    return _Unsafe(text.translate(_json_escapes))


def markdown_souptest(text, nofollow=False, target=None, renderer='reddit'):
    if not text:
        return text
    
    if renderer == 'reddit':
        smd = safemarkdown(text, nofollow=nofollow, target=target)
    elif renderer == 'wiki':
        smd = wikimarkdown(text)

    souptest_fragment(smd)

    return smd

def safemarkdown(text, nofollow=False, wrap=True, **kwargs):
    if not text:
        return None

    target = kwargs.get("target", None)
    text = snudown.markdown(_force_utf8(text), nofollow, target)

    if wrap:
        return SC_OFF + MD_START + text + MD_END + SC_ON
    else:
        return SC_OFF + text + SC_ON

def wikimarkdown(text, include_toc=True, target=None):
    from r2.lib.template_helpers import make_url_protocol_relative

    # this hard codes the stylesheet page for now, but should be parameterized
    # in the future to allow per-page images.
    from r2.models.wiki import ImagesByWikiPage
    from r2.lib.utils import UrlParser
    from r2.lib.template_helpers import add_sr
    page_images = ImagesByWikiPage.get_images(c.site, "config/stylesheet")
    
    def img_swap(tag):
        name = tag.get('src')
        name = custom_img_url.search(name)
        name = name and name.group(1)
        if name and name in page_images:
            url = page_images[name]
            url = make_url_protocol_relative(url)
            tag['src'] = url
        else:
            tag.extract()
    
    nofollow = True
    
    text = snudown.markdown(_force_utf8(text), nofollow, target,
                            renderer=snudown.RENDERER_WIKI)
    
    # TODO: We should test how much of a load this adds to the app
    soup = BeautifulSoup(text.decode('utf-8'))
    images = soup.findAll('img')
    
    if images:
        [img_swap(image) for image in images]

    def add_ext_to_link(link):
        url = UrlParser(link.get('href'))
        if url.is_reddit_url():
            link['href'] = add_sr(link.get('href'), sr_path=False)

    if c.render_style == 'compact':
        links = soup.findAll('a')
        [add_ext_to_link(a) for a in links]

    if include_toc:
        tocdiv = generate_table_of_contents(soup, prefix="wiki")
        if tocdiv:
            soup.insert(0, tocdiv)
    
    text = str(soup)
    
    return SC_OFF + WIKI_MD_START + text + WIKI_MD_END + SC_ON

title_re = re.compile('[^\w.-]')
header_re = re.compile('^h[1-6]$')
def generate_table_of_contents(soup, prefix):
    header_ids = Counter()
    headers = soup.findAll(header_re)
    if not headers:
        return
    tocdiv = Tag(soup, "div", [("class", "toc")])
    parent = Tag(soup, "ul")
    parent.level = 0
    tocdiv.append(parent)
    level = 0
    previous = 0
    for header in headers:
        contents = u''.join(header.findAll(text=True))
        
        # In the event of an empty header, skip
        if not contents:
            continue
        
        # Convert html entities to avoid ugly header ids
        aid = unicode(BeautifulSoup(contents, convertEntities=BeautifulSoup.XML_ENTITIES))
        # Prefix with PREFIX_ to avoid ID conflict with the rest of the page
        aid = u'%s_%s' % (prefix, aid.replace(" ", "_").lower())
        # Convert down to ascii replacing special characters with hex
        aid = str(title_re.sub(lambda c: '.%X' % ord(c.group()), aid))
        
        # Check to see if a tag with the same ID exists
        id_num = header_ids[aid] + 1
        header_ids[aid] += 1
        # Only start numbering ids with the second instance of an id
        if id_num > 1:
            aid = '%s%d' % (aid, id_num)
        
        header['id'] = aid
        
        li = Tag(soup, "li", [("class", aid)])
        a = Tag(soup, "a", [("href", "#%s" % aid)])
        a.string = contents
        li.append(a)
        
        thislevel = int(header.name[-1])
        
        if previous and thislevel > previous:
            newul = Tag(soup, "ul")
            newul.level = thislevel
            newli = Tag(soup, "li", [("class", "toc_child")])
            newli.append(newul)
            parent.append(newli)
            parent = newul
            level += 1
        elif level and thislevel < previous:
            while level and parent.level > thislevel:
                parent = parent.findParent("ul")
                level -= 1
        
        previous = thislevel
        parent.append(li)
    
    return tocdiv


def keep_space(text):
    text = websafe(text)
    for i in " \n\r\t":
        text=text.replace(i,'&#%02d;' % ord(i))
    return unsafe(text)


def unkeep_space(text):
    return text.replace('&#32;', ' ').replace('&#10;', '\n').replace('&#09;', '\t')
