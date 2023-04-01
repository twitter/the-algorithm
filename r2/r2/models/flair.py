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

import uuid

from pylons import app_globals as g

from r2.lib.db import tdb_cassandra
from r2.lib.db.thing import Relation
from r2.lib.db.userrel import UserRel
from r2.lib.utils import to36
from r2.models import Account, Subreddit


USER_FLAIR = 'USER_FLAIR'
LINK_FLAIR = 'LINK_FLAIR'


class Flair(Relation(Subreddit, Account)):
    _cache = g.thingcache

    @classmethod
    def _cache_prefix(cls):
        return "flair:"


Subreddit.__bases__ += (
    UserRel(
        name='flair',
        relation=Flair,
        disable_ids_fn=True,
        disable_reverse_ids_fn=True,
    ),
)


class FlairTemplate(tdb_cassandra.Thing):
    """A template for some flair."""
    _defaults = dict(text='',
                     css_class='',
                     text_editable=False,
                    )

    _bool_props = ('text_editable',)

    _use_db = True
    _connection_pool = 'main'

    @classmethod
    def _new(cls, text='', css_class='', text_editable=False):
        if text is None:
            text = ''
        if css_class is None:
            css_class = ''
        ft = cls(text=text, css_class=css_class, text_editable=text_editable)
        ft._commit()
        return ft

    def _commit(self, *a, **kw):
        # Make sure an _id is always assigned before committing.
        if not self._id:
            self._id = str(uuid.uuid1())
        return tdb_cassandra.Thing._commit(self, *a, **kw)

    def covers(self, other_template):
        """Returns true if other_template is a subset of this one.

        The value for other_template may be another FlairTemplate, or a tuple
        of (text, css_class). The latter case is treated like a FlairTemplate
        that doesn't permit editable text.

        For example, if self permits editable text, then this method will return
        True as long as just the css_classes match. On the other hand, if self
        doesn't permit editable text but other_template does, this method will
        return False.
        """
        if isinstance(other_template, FlairTemplate):
            text_editable = other_template.text_editable
            text, css_class = other_template.text, other_template.css_class
        else:
            text_editable = False
            text, css_class = other_template

        if self.css_class != css_class:
            return False
        return self.text_editable or (not text_editable and self.text == text)


class FlairTemplateBySubredditIndex(tdb_cassandra.Thing):
    """Lists of FlairTemplate IDs for a subreddit.

    The FlairTemplate references are stored as an arbitrary number of attrs.
    The lexicographical ordering of these attr names gives the ordering for
    flair templates within the subreddit.
    """

    MAX_FLAIR_TEMPLATES = 350

    _int_props = ('sr_id',)
    _use_db = True
    _connection_pool = 'main'

    _key_prefixes = {
        USER_FLAIR: 'ft_',
        LINK_FLAIR: 'link_ft_',
    }

    @classmethod
    def _new(cls, sr_id, flair_type=USER_FLAIR):
        idx = cls(_id=to36(sr_id), sr_id=sr_id)
        idx._commit()
        return idx

    @classmethod
    def by_sr(cls, sr_id, create=False):
        try:
            return cls._byID(to36(sr_id))
        except tdb_cassandra.NotFound:
            if create:
                return cls._new(sr_id)
            raise

    @classmethod
    def create_template(cls, sr_id, text='', css_class='', text_editable=False,
                        flair_type=USER_FLAIR):
        idx = cls.by_sr(sr_id, create=True)

        if len(idx._index_keys(flair_type)) >= cls.MAX_FLAIR_TEMPLATES:
            raise OverflowError

        ft = FlairTemplate._new(text=text, css_class=css_class,
                                text_editable=text_editable)
        idx.insert(ft._id, flair_type=flair_type)
        return ft

    @classmethod
    def get_template_ids(cls, sr_id, flair_type=USER_FLAIR):
        try:
            return list(cls.by_sr(sr_id).iter_template_ids(flair_type))
        except tdb_cassandra.NotFound:
            return []

    @classmethod
    def get_template(cls, sr_id, ft_id, flair_type=None):
        if flair_type:
            flair_types = [flair_type]
        else:
            flair_types = [USER_FLAIR, LINK_FLAIR]
        for flair_type in flair_types:
            if ft_id in cls.get_template_ids(sr_id, flair_type=flair_type):
                return FlairTemplate._byID(ft_id)
        return None

    @classmethod
    def clear(cls, sr_id, flair_type=USER_FLAIR):
        try:
            idx = cls.by_sr(sr_id)
        except tdb_cassandra.NotFound:
            # Everything went better than expected.
            return

        for k in idx._index_keys(flair_type):
            del idx[k]
            # TODO: delete the orphaned FlairTemplate row

        idx._commit()

    def _index_keys(self, flair_type):
        keys = set(self._dirties.iterkeys())
        keys |= frozenset(self._orig.iterkeys())
        keys -= self._deletes
        key_prefix = self._key_prefixes[flair_type]
        return [k for k in keys if k.startswith(key_prefix)]

    @classmethod
    def _make_index_key(cls, position, flair_type):
        return '%s%08d' % (cls._key_prefixes[flair_type], position)

    def iter_template_ids(self, flair_type):
        return (getattr(self, key)
                for key in sorted(self._index_keys(flair_type)))

    def insert(self, ft_id, position=None, flair_type=USER_FLAIR):
        """Insert template reference into index at position.

        A position value of None means to simply append.
        """
        ft_ids = list(self.iter_template_ids(flair_type))
        if position is None:
            position = len(ft_ids)
        if position < 0 or position > len(ft_ids):
            raise IndexError(position)
        ft_ids.insert(position, ft_id)

        # Rewrite ALL the things.
        for k in self._index_keys(flair_type):
            del self[k]
        for i, ft_id in enumerate(ft_ids):
            setattr(self, self._make_index_key(i, flair_type), ft_id)
        self._commit()

    def delete_by_id(self, ft_id, flair_type=None):
        if flair_type:
            flair_types = [flair_type]
        else:
            flair_types = [USER_FLAIR, LINK_FLAIR]
        for flair_type in flair_types:
            if self._delete_by_id(ft_id, flair_type):
                return True
        g.log.debug("couldn't find %s to delete", ft_id)
        return False

    def _delete_by_id(self, ft_id, flair_type):
        for key in self._index_keys(flair_type):
            ft = getattr(self, key)
            if ft == ft_id:
                # TODO: delete the orphaned FlairTemplate row
                g.log.debug('deleting ft %s (%s)', ft, key)
                del self[key]
                self._commit()
                return True
        return False
