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

from datetime import datetime
from pylons import tmpl_context as c
from pylons import app_globals as g
import collections
import httplib
import time
import re

import r2.lib.utils as r2utils
from r2.models import (Link, NotFound, Subreddit)


class InvalidQuery(Exception):
    pass


class SearchError(Exception):
    pass


class SearchHTTPError(httplib.HTTPException):
    pass


def safe_xml_str(s, use_encoding="utf-8"):
    '''Replace invalid-in-XML unicode control characters with '\uFFFD'.
    Also, coerces result to unicode
    
    '''
    illegal_xml = re.compile(u'[\x00-\x08\x0b\x0c\x0e-\x1F\uD800-\uDFFF\uFFFE\uFFFF]')

    if not isinstance(s, unicode):
        if isinstance(s, str):
            s = unicode(s, use_encoding, errors="replace")
        else:
            # ints will raise TypeError if the "errors" kwarg
            # is passed, but since it's not a str no problem
            s = unicode(s)
    s = illegal_xml.sub(u"\uFFFD", s)
    return s


def safe_get(get_fn, ids, return_dict=True, **kw):
    items = {}
    for i in ids:
        try:
            item = get_fn(i, **kw)
        except NotFound:
            g.log.info("%s failed for %r", get_fn.__name__, i)
        else:
            items[i] = item
    if return_dict:
        return items
    else:
        return items.values()



SAME_AS_CLOUDSEARCH = object()
FIELD_TYPES = (int, str, datetime, SAME_AS_CLOUDSEARCH, "yesno")

def field(name=None, cloudsearch_type=str, lucene_type=SAME_AS_CLOUDSEARCH):
    Field = collections.namedtuple("Field", "name cloudsearch_type "
                                   "lucene_type function")
    if lucene_type is SAME_AS_CLOUDSEARCH:
        lucene_type = cloudsearch_type
    if cloudsearch_type not in FIELD_TYPES + (None,):
        raise ValueError("cloudsearch_type %r not in %r" %
                         (cloudsearch_type, FIELD_TYPES))
    if lucene_type not in FIELD_TYPES + (None,):
        raise ValueError("lucene_type %r not in %r" %
                         (lucene_type, FIELD_TYPES))
    if callable(name):
        # Simple case; decorated as '@field'; act as a decorator instead
        # of a decorator factory
        function = name
        name = None
    else:
        function = None

    def field_inner(fn):
        fn.field = Field(name or fn.func_name, cloudsearch_type,
                         lucene_type, fn)
        return fn

    if function:
        return field_inner(function)
    else:
        return field_inner


class FieldsMeta(type):
    def __init__(cls, name, bases, attrs):
        type.__init__(cls, name, bases, attrs)
        fields = []
        for attr in attrs.itervalues():
            if hasattr(attr, "field"):
                fields.append(attr.field)
        cls._fields = tuple(fields)


class FieldsBase(object):
    __metaclass__ = FieldsMeta

    def fields(self):
        data = {}
        for field in self._fields:
            if field.cloudsearch_type is None:
                continue
            val = field.function(self)
            if val is not None:
                data[field.name] = val
        return data

    @classmethod
    def all_fields(cls):
        return cls._fields

    @classmethod
    def cloudsearch_fields(cls, type_=None, types=FIELD_TYPES):
        types = (type_,) if type_ else types
        return [f for f in cls._fields if f.cloudsearch_type in types]

    @classmethod
    def lucene_fields(cls, type_=None, types=FIELD_TYPES):
        types = (type_,) if type_ else types
        return [f for f in cls._fields if f.lucene_type in types]

    @classmethod
    def cloudsearch_fieldnames(cls, type_=None, types=FIELD_TYPES):
        return [f.name for f in cls.cloudsearch_fields(type_=type_,
                                                       types=types)]

    @classmethod
    def lucene_fieldnames(cls, type_=None, types=FIELD_TYPES):
        return [f.name for f in cls.lucene_fields(type_=type_, types=types)]


class LinkFields(FieldsBase):
    def __init__(self, link, author, sr):
        self.link = link
        self.author = author
        self.sr = sr

    @field(cloudsearch_type=int, lucene_type=None)
    def ups(self):
        return max(0, self.link._ups)

    @field(cloudsearch_type=int, lucene_type=None)
    def downs(self):
        return max(0, self.link._downs)

    @field(cloudsearch_type=int, lucene_type=None)
    def num_comments(self):
        return max(0, getattr(self.link, 'num_comments', 0))

    @field
    def fullname(self):
        return self.link._fullname

    @field
    def subreddit(self):
        return self.sr.name

    @field
    def reddit(self):
        return self.sr.name

    @field
    def title(self):
        return self.link.title

    @field(cloudsearch_type=int)
    def sr_id(self):
        return self.link.sr_id

    @field(cloudsearch_type=int, lucene_type=datetime)
    def timestamp(self):
        return int(time.mktime(self.link._date.utctimetuple()))

    @field(cloudsearch_type=int, lucene_type="yesno")
    def over18(self):
        nsfw = self.sr.over_18 or self.link.is_nsfw
        return (1 if nsfw else 0)

    @field(cloudsearch_type=None, lucene_type="yesno")
    def nsfw(self):
        return NotImplemented

    @field(cloudsearch_type=int, lucene_type="yesno")
    def is_self(self):
        return (1 if self.link.is_self else 0)

    @field(name="self", cloudsearch_type=None, lucene_type="yesno")
    def self_(self):
        return NotImplemented

    @field
    def author_fullname(self):
        return None if self.author._deleted else self.author._fullname

    @field(name="author")
    def author_field(self):
        return None if self.author._deleted else self.author.name

    @field(cloudsearch_type=int)
    def type_id(self):
        return self.link._type_id

    @field
    def site(self):
        if self.link.is_self:
            return g.domain
        else:
            try:
                url = r2utils.UrlParser(self.link.url)
                return list(url.domain_permutations())
            except ValueError:
                return None

    @field
    def selftext(self):
        if self.link.is_self and self.link.selftext:
            return self.link.selftext
        else:
            return None

    @field
    def url(self):
        if not self.link.is_self:
            return self.link.url
        else:
            return None

    @field
    def flair_css_class(self):
        return self.link.flair_css_class

    @field
    def flair_text(self):
        return self.link.flair_text

    @field(cloudsearch_type=None, lucene_type=str)
    def flair(self):
        return NotImplemented


class SubredditFields(FieldsBase):
    def __init__(self, sr):
        self.sr = sr

    @field
    def name(self):
        return self.sr.name

    @field
    def title(self):
        return self.sr.title

    @field(name="type")
    def type_(self):
        return self.sr.type

    @field
    def language(self):
        return self.sr.lang

    @field
    def header_title(self):
        return None if self.sr.type == 'private' else self.sr.header_title

    @field
    def description(self):
        return self.sr.public_description

    @field
    def sidebar(self):
        return None if self.sr.type == 'private' else self.sr.description

    @field(cloudsearch_type=int)
    def over18(self):
        return 1 if self.sr.over_18 else 0

    @field
    def link_type(self):
        return self.sr.link_type

    @field
    def activity(self):
        return self.sr._downs

    @field
    def subscribers(self):
        return self.sr._ups

    @field
    def type_id(self):
        return self.sr._type_id


class Results(object):
    def __init__(self, docs, hits, facets):
        self.docs = docs
        self.hits = hits
        self._facets = facets
        self._subreddits = []

    def __repr__(self):
        return '%s(%r, %r, %r)' % (self.__class__.__name__,
                                   self.docs,
                                   self.hits,
                                   self._facets)

    @property
    def subreddit_facets(self):
        '''Filter out subreddits that the user isn't allowed to see'''
        if not self._subreddits and 'reddit' in self._facets:
            sr_facets = [(sr['value'], sr['count']) for sr in
                         self._facets['reddit']]

            # look up subreddits
            srs_by_name = Subreddit._by_name([name for name, count
                                              in sr_facets])

            sr_facets = [(srs_by_name[name], count) for name, count
                         in sr_facets if name in srs_by_name]

            # filter by can_view
            self._subreddits = [(sr, count) for sr, count in sr_facets
                                if sr.can_view(c.user)]

        return self._subreddits
