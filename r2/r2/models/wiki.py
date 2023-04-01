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

from ConfigParser import SafeConfigParser
from datetime import datetime, timedelta
from r2.lib.db import tdb_cassandra
from r2.lib.db.thing import NotFound
from r2.lib.merge import *
from r2.models.last_modified import LastModified
from pycassa.system_manager import TIME_UUID_TYPE
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.controllers.util import abort
from r2.lib.db.tdb_cassandra import NotFound
from r2.models.printable import Printable
from r2.models.account import Account
from collections import OrderedDict
from StringIO import StringIO

import pycassa.types

# Used for the key/id for pages,
PAGE_ID_SEP = '\t'

# Number of days to keep recent revisions for
WIKI_RECENT_DAYS = g.wiki_keep_recent_days

# Max length of a single page in bytes
MAX_PAGE_LENGTH_BYTES = g.wiki_max_page_length_bytes

# Page names which should never be
impossible_namespaces = ('edit/', 'revisions/', 'settings/', 'discussions/', 
                         'revisions/', 'pages/', 'create/')

# Namespaces in which access is denied to do anything but view
restricted_namespaces = ('reddit/', 'config/', 'special/')

# Pages which may only be edited by mods, must be within restricted namespaces
special_pages = {
    'config/automoderator',
    'config/description',
    'config/sidebar',
    'config/stylesheet',
    'config/submit_text',
}

special_page_view_permlevels = {
    "config/automoderator": 2,
}

# Pages that get created automatically from the subreddit settings page
automatically_created_pages = {
    'config/description',
    'config/sidebar',
    'config/stylesheet',
    'config/submit_text',
}

# Pages which have a special length restrictions (In bytes)
special_length_restrictions_bytes = {
    'config/stylesheet': 128*1024,
    'config/submit_text': 1024,
    'config/sidebar': 5120,
    'config/description': 500,
    'usernotes': 1024*1024,
}

modactions = {
    "config/automoderator": "Updated AutoModerator configuration",
    "config/description": "Updated subreddit description",
    "config/sidebar": "Updated subreddit sidebar",
    "config/submit_text": "Updated submission text",
}

# Page "index" in the subreddit "reddit.com" and a seperator of "\t" becomes:
#   "reddit.com\tindex"
def wiki_id(sr, page):
    return ('%s%s%s' % (sr, PAGE_ID_SEP, page)).lower()

class ContentLengthError(Exception):
    def __init__(self, max_length):
        Exception.__init__(self)
        self.max_length = max_length

class WikiPageExists(Exception):
    pass

class WikiBadRevision(Exception):
    pass

class WikiPageEditors(tdb_cassandra.View):
    _use_db = True
    _value_type = 'str'
    _connection_pool = 'main'

class WikiRevision(tdb_cassandra.UuidThing, Printable):
    """ Contains content (markdown), author of the edit, page the edit belongs to, and datetime of the edit """
    
    _use_db = True
    _connection_pool = 'main'
    
    _str_props = ('pageid', 'content', 'author', 'reason')
    _bool_props = ('hidden', 'admin_deleted')
    _defaults = {'admin_deleted': False}

    cache_ignore = set(list(_str_props)).union(Printable.cache_ignore).union(['wikipage'])
    
    def get_author(self):
        author = self._get('author')
        return Account._byID36(author, data=True) if author else None
    
    @classmethod
    def get_authors(cls, revisions):
        authors = [r._get('author') for r in revisions]
        authors = filter(None, authors)
        return Account._byID36(authors, data=True)
    
    @classmethod
    def get_printable_authors(cls, revisions):
        from r2.lib.pages import WrappedUser
        authors = cls.get_authors(revisions)
        return dict([(id36, WrappedUser(v))
                     for id36, v in authors.iteritems() if v])
    
    @classmethod
    def add_props(cls, user, wrapped):
        authors = cls.get_printable_authors(wrapped)
        pages = {r.page: None for r in wrapped}
        pages = WikiPage.get_multiple((c.site, page) for page in pages)
        for item in wrapped:
            item._hidden = item.is_hidden
            item._spam = False
            item.wikipage = pages[item.pageid]
            author = item._get('author')
            item.printable_author = authors.get(author, '[unknown]')
            item.reported = False
    
    @classmethod
    def get(cls, revid, pageid):
        wr = cls._byID(revid)
        if wr.pageid != pageid:
            raise WikiBadRevision('Revision is not for the expected page')
        return wr
    
    def toggle_hide(self):
        self.hidden = not self.is_hidden
        self._commit()
        return self.hidden

    @classmethod
    def create(cls, pageid, content, author=None, reason=None):
        kw = dict(pageid=pageid, content=content)
        if author:
            kw['author'] = author
        if reason:
            kw['reason'] = reason
        wr = cls(**kw)
        wr._commit()
        WikiRevisionHistoryByPage.add_object(wr)
        WikiRevisionsRecentBySR.add_object(wr)
        return wr

    def _on_commit(self):
        WikiRevisionHistoryByPage.add_object(self)
        WikiRevisionsRecentBySR.add_object(self)

    @classmethod
    def get_recent(cls, sr, count=100):
        return WikiRevisionsRecentBySR.query([sr._id36], count=count)
    
    @property
    def is_hidden(self):
        return bool(getattr(self, 'hidden', False))
    
    @property
    def info(self, sep=PAGE_ID_SEP):
        info = self.pageid.split(sep, 1)
        try:
            return {'sr': info[0], 'page': info[1]}
        except IndexError:
            g.log.error('Broken wiki page ID "%s" did PAGE_ID_SEP change?', self.pageid)
            return {'sr': 'broken', 'page': 'broken'}
    
    @property
    def page(self):
        return self.info['page']
    
    @property
    def sr(self):
        return self.info['sr']


class WikiPage(tdb_cassandra.Thing):
    """ Contains permissions, current content (markdown), subreddit, and current revision (ID)
        Key is subreddit-pagename """
    
    _use_db = True
    _connection_pool = 'main'
    
    _read_consistency_level = tdb_cassandra.CL.QUORUM
    _write_consistency_level = tdb_cassandra.CL.QUORUM
    
    _date_props = ('last_edit_date')
    _str_props = ('revision', 'name', 'last_edit_by', 'content', 'sr')
    _int_props = ('permlevel')
    _bool_props = ('listed')
    _defaults = {'listed': True}

    def get_author(self):
        if self._get('last_edit_by'):
            return Account._byID36(self.last_edit_by, data=True)
        return None
    
    @classmethod
    def id_for(cls, sr, name):
        id = getattr(sr, '_id36', None)
        if not id:
            raise tdb_cassandra.NotFound
        return wiki_id(id, name)
    
    @classmethod
    def get_multiple(cls, pages):
        """Get multiple wiki pages.
        
        Arguments:
        pages -- list of tuples in the form of [(sr, names),..]
        """
        return cls._byID([cls.id_for(sr, name) for sr, name in pages])
    
    @classmethod
    def get(cls, sr, name):
        return cls._byID(cls.id_for(sr, name))

    @classmethod
    def create(cls, sr, name):
        if not name or not sr:
            raise ValueError

        name = name.lower()
        _id = wiki_id(sr._id36, name)
        lock_key = "wiki_create_%s:%s" % (sr._id36, name)
        with g.make_lock("wiki", lock_key):
            try:
                cls._byID(_id)
            except tdb_cassandra.NotFound:
                pass
            else:
                raise WikiPageExists

            page = cls(_id=_id, sr=sr._id36, name=name, permlevel=0, content='')
            page._commit()
            return page

    @property
    def restricted(self):
        return WikiPage.is_restricted(self.name)

    @classmethod
    def is_impossible(cls, page):
        return ("%s/" % page) in impossible_namespaces or page.startswith(impossible_namespaces)
    
    @classmethod
    def is_restricted(cls, page):
        return ("%s/" % page) in restricted_namespaces or page.startswith(restricted_namespaces)
    
    @classmethod
    def is_special(cls, page):
        return page in special_pages

    @classmethod
    def get_special_view_permlevel(cls, page):
        return special_page_view_permlevels.get(page, 0)

    @classmethod
    def is_automatically_created(cls, page):
        return page in automatically_created_pages
    
    @property
    def special(self):
        return WikiPage.is_special(self.name)
    
    def add_to_listing(self):
        WikiPagesBySR.add_object(self)
    
    def _on_create(self):
        self.add_to_listing()
    
    def _on_commit(self):
         self.add_to_listing()
    
    def remove_editor(self, user):
        WikiPageEditors._remove(self._id, [user])
    
    def add_editor(self, user):
        WikiPageEditors._set_values(self._id, {user: ''})
    
    @classmethod
    def get_pages(cls, sr, after=None, filter_check=None):
        NUM_AT_A_TIME = num = 1000
        pages = []
        while num >= NUM_AT_A_TIME:
            wikipages = WikiPagesBySR.query([sr._id36],
                                            after=after,
                                            count=NUM_AT_A_TIME)
            wikipages = list(wikipages)
            num = len(wikipages)
            pages += wikipages
            after = wikipages[-1] if num else None
        return filter(filter_check, pages)
    
    @classmethod
    def get_listing(cls, sr, filter_check=None):
        """
            Create a tree of pages from their path.
        """
        page_tree = OrderedDict()
        pages = cls.get_pages(sr, filter_check=filter_check)
        pages = sorted(pages, key=lambda page: page.name)
        for page in pages:
            p = page.name.split('/')
            cur_node = page_tree
            # Loop through all elements of the path except the page name portion
            for name in p[:-1]:
                next_node = cur_node.get(name)
                # If the element did not already exist in the tree, create it
                if not next_node:
                    new_node = OrderedDict()
                    cur_node[name] = [None, new_node]
                else:
                    # Otherwise, continue through
                    new_node = next_node[1]
                cur_node = new_node
            # Get the actual page name portion of the path
            pagename = p[-1]
            node = cur_node.get(pagename)
            # The node may already exist as a path name in the tree
            if node:
                node[0] = page
            else:
                cur_node[pagename] = [page, OrderedDict()]

        return page_tree, pages
    
    def get_editor_accounts(self):
        editors = self.get_editors()
        accounts = [Account._byID36(editor, data=True)
                    for editor in self.get_editors()]
        accounts = [account for account in accounts
                    if not account._deleted]
        return accounts
    
    def get_editors(self, properties=None):
        try:
            return WikiPageEditors._byID(self._id, properties=properties)._values().keys() or []
        except tdb_cassandra.NotFoundException:
            return []
    
    def has_editor(self, editor):
        return bool(self.get_editors(properties=[editor]))
    
    def revise(self, content, previous = None, author=None, force=False, reason=None):
        if content is None:
            content = ""
        if self.content == content:
            return
        force = True if previous is None else force
        max_length = special_length_restrictions_bytes.get(self.name, MAX_PAGE_LENGTH_BYTES)
        if len(content) > max_length:
            raise ContentLengthError(max_length)
        
        revision = getattr(self, 'revision', None)
        
        if not force and (revision and previous != revision):
            if previous:
                origcontent = WikiRevision.get(previous, pageid=self._id).content
            else:
                origcontent = ''
            try:
                content = threewaymerge(origcontent, content, self.content)
            except ConflictException as e:
                e.new_id = revision
                raise e
        
        wr = WikiRevision.create(self._id, content, author, reason)
        self.content = content
        self.last_edit_by = author
        self.last_edit_date = wr.date
        self.revision = str(wr._id)
        self._commit()

        LastModified.touch(self._fullname, "Edit")

        return wr
    
    def change_permlevel(self, permlevel, force=False):
        NUM_PERMLEVELS = 3
        if permlevel == self.permlevel:
            return
        if not force and int(permlevel) not in range(NUM_PERMLEVELS):
            raise ValueError('Permlevel not valid')
        self.permlevel = permlevel
        self._commit()

    def get_revisions(self, after=None, count=100):
        return WikiRevisionHistoryByPage.query(
            rowkeys=[self._id], after=after, count=count)


class WikiRevisionHistoryByPage(tdb_cassandra.View):
    """Create a time ordered index of revisions for a wiki page"""
    _use_db = True
    _connection_pool = 'main'
    _view_of = WikiRevision
    _compare_with = TIME_UUID_TYPE

    @classmethod
    def _rowkey(cls, wikirevision):
        return wikirevision.pageid

    @classmethod
    def _obj_to_column(cls, wikirevision):
        return {wikirevision._id: ''}


class WikiPagesBySR(tdb_cassandra.DenormalizedView):
    """ Associate revisions with subreddits, store only recent """
    _use_db = True
    _connection_pool = 'main'
    _view_of = WikiPage
    
    @classmethod
    def _rowkey(cls, wp):
        return wp.sr

class WikiRevisionsRecentBySR(tdb_cassandra.DenormalizedView):
    """ Associate revisions with subreddits, store only recent """
    _use_db = True
    _connection_pool = 'main'
    _view_of = WikiRevision
    _compare_with = TIME_UUID_TYPE
    _ttl = timedelta(days=WIKI_RECENT_DAYS)
    
    @classmethod
    def _rowkey(cls, wr):
        return wr.sr


class ImagesByWikiPage(tdb_cassandra.View):
    _use_db = True
    _read_consistency_level = tdb_cassandra.CL.QUORUM
    _write_consistency_level = tdb_cassandra.CL.QUORUM
    _extra_schema_creation_args = {
        "key_validation_class": pycassa.types.AsciiType(),
        "column_name_class": pycassa.types.UTF8Type(),
        "default_validation_class": pycassa.types.UTF8Type(),
    }

    @classmethod
    def add_image(cls, sr, page_name, image_name, url):
        rowkey = WikiPage.id_for(sr, page_name)
        cls._set_values(rowkey, {image_name: url})

    @classmethod
    def get_images(cls, sr, page_name):
        try:
            rowkey = WikiPage.id_for(sr, page_name)
            return cls._byID(rowkey)._values()
        except tdb_cassandra.NotFound:
            return {}

    @classmethod
    def get_image_count(cls, sr, page_name):
        rowkey = WikiPage.id_for(sr, page_name)
        return cls._cf.get_count(rowkey,
            read_consistency_level=cls._read_consistency_level)

    @classmethod
    def delete_image(cls, sr, page_name, image_name):
        rowkey = WikiPage.id_for(sr, page_name)
        cls._remove(rowkey, [image_name])


class WikiPageIniItem(object):
    _bool_values = ("is_enabled", "is_new")

    @classmethod
    def get_all(cls, return_dict=False):
        items = OrderedDict()
        try:
            wp = WikiPage.get(*cls._get_wiki_config())
        except NotFound:
            return items if return_dict else items.values()
        wp_content = StringIO(wp.content)
        cfg = SafeConfigParser(allow_no_value=True)
        cfg.readfp(wp_content)

        for section in cfg.sections():
            def_values = {'id': section}
            for name, value in cfg.items(section):
                # coerce boolean variables
                if name in cls._bool_values:
                    def_values[name] = cfg.getboolean(section, name)
                else:
                    def_values[name] = value

            try:
                item = cls(**def_values)
            except TypeError:
                # a required variable wasn't set for this item, skip
                continue

            if item.is_enabled:
                items[section] = item
        
        return items if return_dict else items.values()
