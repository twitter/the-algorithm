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

from account import *
from link import *
from vote import *
from report import *
from subreddit import DefaultSR, AllSR, Frontpage, Subreddit
from pylons import i18n, request
from pylons import app_globals as g
from pylons.i18n import _

from r2.config import feature
from r2.lib.wrapped import Wrapped, CachedVariable
from r2.lib import utils
from r2.lib.db import operators
from r2.models import rules

from collections import namedtuple
from copy import deepcopy, copy
import time


class Listing(object):
    # class used in Javascript to manage these objects
    _js_cls = "Listing"

    def __init__(self, builder, nextprev = True, next_link = True,
                 prev_link = True, params = None, **kw):
        self.builder = builder
        self.nextprev = nextprev
        self.next_link = True
        self.prev_link = True
        self.next = None
        self.prev = None
        self.params = params or request.GET.copy()
        self._max_num = 1

    @property
    def max_score(self):
        scores = [x.score for x in self.things if hasattr(x, 'score')]
        return max(scores) if scores else 0

    @property
    def max_num(self):
        return self._max_num

    def get_items(self, *a, **kw):
        """Wrapper around builder's get_items that caches the rendering."""
        from r2.lib.template_helpers import replace_render
        builder_items = self.builder.get_items(*a, **kw)
        for item in self.builder.item_iter(builder_items):
            # rewrite the render method
            if c.render_style != "api" and not hasattr(item, "render_replaced"):
                item.render = replace_render(self, item, item.render)
                item.render_replaced = True
        return builder_items

    def listing(self, next_suggestions=None):
        self.things, prev, next, bcount, acount = self.get_items()

        self.next_suggestions = next_suggestions
        self._max_num = max(acount, bcount)
        self.after = None
        self.before = None

        if self.nextprev and self.prev_link and prev and bcount > 1:
            p = self.params.copy()
            p.update({'after':None, 'before':prev._fullname, 'count':bcount})
            self.before = prev._fullname
            self.prev = (request.path + utils.query_string(p))
            p_first = self.params.copy()
            p_first.update({'after':None, 'before':None, 'count':None})
            self.first = (request.path + utils.query_string(p_first))
        if self.nextprev and self.next_link and next:
            p = self.params.copy()
            p.update({'after':next._fullname, 'before':None, 'count':acount})
            self.after = next._fullname
            self.next = (request.path + utils.query_string(p))

        for count, thing in enumerate(self.things):
            thing.rowstyle_cls = getattr(thing, 'rowstyle_cls', "")
            thing.rowstyle_cls += ' ' + ('even' if (count % 2) else 'odd')
            thing.rowstyle = CachedVariable("rowstyle")

        #TODO: need name for template -- must be better way
        return Wrapped(self)

    def __iter__(self):
        return iter(self.things)

class TableListing(Listing): pass

class ModActionListing(TableListing): pass

class WikiRevisionListing(TableListing): pass

class UserListing(TableListing):
    type = ''
    _class = ''
    title = ''
    form_title = ''
    destination = 'friend'
    has_add_form = True
    headers = None
    permissions_form = None

    def __init__(self,
                 builder,
                 show_jump_to=False,
                 show_not_found=False,
                 jump_to_value=None,
                 addable=True, **kw):
        self.addable = addable
        self.show_not_found = show_not_found
        self.show_jump_to = show_jump_to
        self.jump_to_value = jump_to_value
        TableListing.__init__(self, builder, **kw)

    @property
    def container_name(self):
        return c.site._fullname

class FriendListing(UserListing):
    type = 'friend'

    @property
    def _class(self):
        return '' if not c.user.gold else 'gold-accent rounded'

    @property
    def headers(self):
        if c.user.gold:
            return (_('user'), '', _('note'), _('friendship'), '')

    @property
    def form_title(self):
        return _('add a friend')

    @property
    def container_name(self):
        return c.user._fullname


class EnemyListing(UserListing):
    type = 'enemy'
    has_add_form = False

    @property
    def title(self):
        return _('blocked users')

    @property
    def container_name(self):
        return c.user._fullname

class BannedListing(UserListing):
    type = 'banned'

    def __init__(self, builder, show_jump_to=False, show_not_found=False,
            jump_to_value=None, addable=True, **kw):
        self.rules = rules.SubredditRules.get_rules(c.site)
        self.system_rules = rules.SITEWIDE_RULES
        UserListing.__init__(self, builder, show_jump_to, show_not_found,
            jump_to_value, addable, **kw)

    @classmethod
    def populate_from_tempbans(cls, item, tempbans=None):
        if not tempbans:
            return
        time = tempbans.get(item.user.name)
        if time:
            delay = time - datetime.now(g.tz)
            item.tempban = max(delay.days, 0)

    @property
    def form_title(self):
        return _("ban users")

    @property
    def title(self):
        return _("users banned from"
                 " /r/%(subreddit)s") % dict(subreddit=c.site.name)

    def get_items(self, *a, **kw):
        items = UserListing.get_items(self, *a, **kw)
        wrapped_items = items[0]
        names = [item.user.name for item in wrapped_items]
        tempbans = c.site.get_tempbans(self.type, names)
        for wrapped in wrapped_items:
            BannedListing.populate_from_tempbans(wrapped, tempbans)
        return items


class MutedListing(UserListing):
    type = 'muted'

    @classmethod
    def populate_from_muted(cls, item, muted=None):
        if not muted:
            return
        time = muted.get(item.user.name)
        if time:
            delay = time - datetime.now(g.tz)
            item.muted = max(int(delay.total_seconds()), 0)

    @property
    def form_title(self):
        return _("mute users")

    @property
    def title(self):
        return _("users muted from"
                 " /r/%(subreddit)s") % dict(subreddit=c.site.name)

    def get_items(self, *a, **kw):
        items = UserListing.get_items(self, *a, **kw)
        wrapped_items = items[0]
        names = [item.user.name for item in wrapped_items]
        muted = c.site.get_muted_items(names)
        for wrapped in wrapped_items:
            MutedListing.populate_from_muted(wrapped, muted)
        return items


class WikiBannedListing(BannedListing):
    type = 'wikibanned'

    @property
    def form_title(self):
        return _("ban wiki contibutors")

    @property
    def title(self):
        return _("wiki contibutors banned from"
                 " /r/%(subreddit)s") % dict(subreddit=c.site.name)

class ContributorListing(UserListing):
    type = 'contributor'

    @property
    def title(self):
        return _("approved submitters for"
                 " /r/%(subreddit)s") % dict(subreddit=c.site.name)

    @property
    def form_title(self):
        return _("add approved submitter")

class WikiMayContributeListing(ContributorListing):
    type = 'wikicontributor'

    @property
    def title(self):
        return _("approved wiki contributors"
                 " for /r/%(subreddit)s") % dict(subreddit=c.site.name)

    @property
    def form_title(self):
        return _("add approved wiki contributor")

class InvitedModListing(UserListing):
    type = 'moderator_invite'
    form_title = _('invite moderator')
    remove_self_title = _('you are a moderator of this subreddit. %(action)s')

    @property
    def permissions_form(self):
        from r2.lib.permissions import ModeratorPermissionSet
        from r2.lib.pages import ModeratorPermissions
        return ModeratorPermissions(
            user=None,
            permissions_type=self.type,
            permissions=ModeratorPermissionSet(all=True),
            editable=True,
            embedded=True,
        )

    @property
    def title(self):
        return _("invited moderators for"
                 " %(subreddit)s") % dict(subreddit=c.site.name)

class ModListing(InvitedModListing):
    type = 'moderator'
    form_title = _('force add moderator')

    @property
    def has_add_form(self):
        return c.user_is_admin

    @property
    def can_remove_self(self):
        return c.user_is_loggedin and c.site.is_moderator(c.user)

    @property
    def has_invite(self):
        return c.user_is_loggedin and c.site.is_moderator_invite(c.user)

    @property
    def title(self):
        return _("moderators of /r/%(subreddit)s") % dict(subreddit=c.site.name)

class LinkListing(Listing):
    def __init__(self, *a, **kw):
        Listing.__init__(self, *a, **kw)

        self.show_nums = kw.get('show_nums', False)

    def listing(self, *args, **kwargs):
        wrapped = Listing.listing(self, *args, **kwargs)
        self.rank_width = len(str(self.max_num)) * 1.1
        self.midcol_width = max(len(str(self.max_score)), 2) + 1.1
        return wrapped


class SearchListing(LinkListing):
    def __init__(self, *a, **kw):
        LinkListing.__init__(self, *a, **kw)
        self.heading = kw.get('heading', None)
        self.nav_menus = kw.get('nav_menus', None)

    def listing(self, legacy_render_class=False, *args, **kwargs):
        wrapped = LinkListing.listing(self, *args, **kwargs)
        if hasattr(self.builder, 'subreddit_facets'):
            self.subreddit_facets = self.builder.subreddit_facets
        if hasattr(self.builder, 'start_time'):
            self.timing = time.time() - self.builder.start_time

        if legacy_render_class:
            wrapped.render_class = LinkListing

        return wrapped


class ReadNextListing(Listing):
    pass


class NestedListing(Listing):
    def __init__(self, *a, **kw):
        Listing.__init__(self, *a, **kw)

        self.num = kw.get('num', g.num_comments)
        self.parent_name = kw.get('parent_name')

    def listing(self):
        ##TODO use the local builder with the render cache. this may
        ##require separating the builder's get_items and tree-building
        ##functionality
        wrapped_items = self.get_items()

        self.things = wrapped_items

        #make into a tree thing
        return Wrapped(self)

SpotlightTuple = namedtuple('SpotlightTuple',
                            ['link', 'is_promo', 'campaign', 'weight'])

class SpotlightListing(Listing):
    # class used in Javascript to manage these objects
    _js_cls = "OrganicListing"

    def __init__(self, *a, **kw):
        self.nextprev   = False
        self.show_nums  = True
        self._parent_max_num   = kw.get('max_num', 0)
        self._parent_max_score = kw.get('max_score', 0)
        self.interestbar = kw.get('interestbar')
        self.interestbar_prob = kw.get('interestbar_prob', 0.)
        self.show_promo = kw.get('show_promo', False)
        keywords = kw.get('keywords', [])
        self.keywords = '+'.join([keyword if keyword else Frontpage.name
                                 for keyword in keywords])
        self.navigable = kw.get('navigable', True)
        self.things = kw.get('organic_links', [])
        self.show_placeholder = isinstance(c.site, (DefaultSR, AllSR))

    def get_items(self):
        from r2.lib.template_helpers import replace_render
        things = self.things
        for t in things:
            if not hasattr(t, "render_replaced"):
                t.render = replace_render(self, t, t.render)
                t.render_replaced = True
        return things, None, None, 0, 0

    def listing(self):
        res = Listing.listing(self)
        for t in res.things:
            t.num_text = ""
        return Wrapped(self)
