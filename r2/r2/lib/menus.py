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

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _, N_

from r2.config import feature
from r2.lib.db import operators
from r2.lib.filters import _force_unicode
from r2.lib.strings import StringHandler, plurals
from r2.lib.utils import  class_property, query_string, timeago
from r2.lib.wrapped import Styled


class MenuHandler(StringHandler):
    """Bastard child of StringHandler and plurals.  Menus are
    typically a single word (and in some cases, a single plural word
    like 'moderators' or 'contributors' so this class first checks its
    own dictionary of string translations before falling back on the
    plurals list."""
    def __getattr__(self, attr):
        try:
            return StringHandler.__getattr__(self, attr)
        except KeyError:
            return getattr(plurals, attr)

# translation strings for every menu on the site
menu =   MenuHandler(hot          = _('hot'),
                     new          = _('new'),
                     old          = _('old'),
                     ups          = _('ups'),
                     downs        = _('downs'),
                     top          = _('top'),
                     more         = _('more'),
                     relevance    = _('relevance'),
                     controversial  = _('controversial'),
                     gilded       = _('gilded'),
                     confidence   = _('best'),
                     random       = _('random'),
                     qa           = _('q&a'),
                     saved        = _('saved {toolbar}'),
                     recommended  = _('recommended'),
                     rising       = _('rising'),
                     admin        = _('admin'),

                     # time sort words
                     hour         = _('past hour'),
                     day          = _('past 24 hours'),
                     week         = _('past week'),
                     month        = _('past month'),
                     year         = _('past year'),
                     all          = _('all time'),

                     # "kind" words
                     spam         = _("spam"),
                     autobanned   = _("autobanned"),

                     # reddit header strings
                     prefs        = _("preferences"),
                     submit       = _("submit"),
                     wiki         = _("wiki"),
                     blog         = _("blog"),
                     logout       = _("logout"),

                     #reddit footer strings
                     reddiquette  = _("reddiquette"),
                     contact      = _("contact us"),
                     buttons      = _("buttons"),
                     widget       = _("widget"),
                     mobile       = _("mobile"),
                     advertising  = _("advertise"),
                     gold         = _('reddit gold'),
                     reddits      = _('subreddits'),
                     rules        = _('site rules'),
                     jobs         = _('jobs'),
                     transparency = _("transparency"),
                     source_code  = _("source code"),
                     values       = _("values"),

                     #preferences
                     options      = _('options'),
                     apps         = _("apps"),
                     feeds        = _("RSS feeds"),
                     friends      = _("friends"),
                     blocked      = _("blocked"),
                     update       = _("password/email"),
                     deactivate   = _("deactivate"),
                     security     = _("security"),

                     # messages
                     compose      = _("send a private message"),
                     inbox        = _("inbox"),
                     sent         = _("sent"),

                     # comments
                     comments     = _("comments {toolbar}"),
                     details      = _("details"),
                     duplicates   = _("other discussions (%(num)s)"),
                     traffic      = _("traffic stats"),
                     stylesheet   = _("stylesheet"),

                     # reddits
                     home         = _("home"),
                     about        = _("about"),
                     edit_subscriptions = _("edit subscriptions"),
                     community_settings = _("subreddit settings"),
                     edit_stylesheet    = _("edit stylesheet"),
                     community_rules    = _("rules"),
                     moderators   = _("moderators"),
                     modmail      = _("moderator mail"),
                     contributors = _("approved submitters"),
                     banned       = _("ban users"),
                     banusers     = _("ban users"),
                     muted        = _("mute users"),
                     flair        = _("edit flair"),
                     log          = _("moderation log"),
                     modqueue     = _("moderation queue"),
                     unmoderated  = _("unmoderated posts"),
                     edited       = _("edited"),
                     employee     = _("employee"),
                     automod      = _("automoderator config"),
                     new_automod  = _("get started with automoderator"),

                     wikibanned        = _("ban wiki contributors"),
                     wikicontributors  = _("add wiki contributors"),

                     wikirecentrevisions = _("recent wiki revisions"),
                     wikipageslist = _("wiki page list"),

                     popular      = _("popular"),
                     create       = _("create"),
                     mine         = _("my subreddits"),
                     quarantine   = _("quarantine"),
                     featured     = _("featured"),

                     i18n         = _("help translate"),
                     errors       = _("errors"),
                     awards       = _("awards"),
                     ads          = _("ads"),
                     promoted     = _("promoted"),
                     sponsor      = _("sponsor"),
                     reporters    = _("reporters"),
                     reports      = _("reports"),
                     reportedauth = _("reported authors"),
                     info         = _("info"),
                     share        = _("share"),

                     overview     = _("overview"),
                     submitted    = _("submitted"),
                     upvoted      = _("upvoted"),
                     downvoted    = _("downvoted"),
                     hidden       = _("hidden {toolbar}"),
                     deleted      = _("deleted"),
                     reported     = _("reported"),
                     voting       = _("voting"),

                     promote        = _('advertising'),
                     new_promo      = _('create promotion'),
                     my_current_promos = _('my promoted links'),
                     current_promos = _('all promoted links'),
                     all_promos     = _('all'),
                     future_promos  = _('unseen'),
                     unapproved_campaigns = _('unapproved campaigns'),
                     inventory      = _('inventory'),
                     live_promos    = _('live'),
                     unpaid_promos  = _('unpaid'),
                     pending_promos = _('pending'),
                     rejected_promos = _('rejected'),
                     edited_live_promos = _('edited live'),

                     sitewide = _('sitewide'),
                     languages = _('languages'),
                     adverts = _('adverts'),

                     whitelist = _("whitelist")
                     )

def menu_style(type):
    """Simple manager function for the styled menus.  Returns a
    (style, css_class) pair given a 'type', defaulting to style =
    'dropdown' with no css_class."""
    default = ('dropdown', '')
    d = dict(lightdrop = ('dropdown', 'lightdrop'),
             tabdrop = ('dropdown', 'tabdrop'),
             srdrop = ('dropdown', 'srdrop'),
             flatlist =  ('flatlist', 'flat-list'),
             tabmenu = ('tabmenu', ''),
             formtab = ('tabmenu', 'formtab'),
             flat_vert = ('flatlist', 'flat-vert'),
             )
    return d.get(type, default)


class NavMenu(Styled):
    """generates a navigation menu.  The intention here is that the
    'style' parameter sets what template/layout to use to differentiate, say,
    a dropdown from a flatlist, while the optional _class, and _id attributes
    can be used to set individualized CSS."""

    def __init__(self, options, default=None, title='', type="dropdown",
                 base_path='', separator='|', _id='', css_class=''):
        self.options = options
        self.default = default
        self.title = title
        self.base_path = base_path
        self.separator = separator

        # add the menu style, but preserve existing css_class parameter
        style, base_css_class = menu_style(type)
        css_class = base_css_class + ((' ' + css_class) if css_class else '')

        # since the menu contains the path info, it's buttons need a
        # configuration pass to get them pointing to the proper urls
        for opt in self.options:
            opt.build(self.base_path)

            # add "choice" css class to each button
            if opt.css_class:
                opt.css_class += " choice"
            else:
                opt.css_class = "choice"

        self.selected = self.find_selected()

        Styled.__init__(self, style, _id=_id, css_class=css_class)

    def find_selected(self):
        maybe_selected = [o for o in self.options if o.is_selected()]
        if maybe_selected:
            # pick the button with the most restrictive pathing
            maybe_selected.sort(lambda x, y:
                                len(y.bare_path) - len(x.bare_path))
            return maybe_selected[0]
        elif self.default:
            #lookup the menu with the 'dest' that matches 'default'
            for opt in self.options:
                if opt.dest == self.default:
                    return opt

    def __iter__(self):
        for opt in self.options:
            yield opt

    def cachable_attrs(self):
        return [
            ('options', self.options),
            ('title', self.title),
            ('selected', self.selected),
            ('separator', self.separator),
        ]


class NavButton(Styled):
    """Smallest unit of site navigation.  A button once constructed
    must also have its build() method called with the current path to
    set self.path.  This step is done automatically if the button is
    passed to a NavMenu instance upon its construction."""

    _style = "plain"

    def __init__(self, title, dest, sr_path=True, aliases=None,
                 target="", use_params=False, css_class='', data=None):
        aliases = aliases or []
        aliases = set(_force_unicode(a.rstrip('/')) for a in aliases)
        if dest:
            aliases.add(_force_unicode(dest.rstrip('/')))

        self.title = title
        self.dest = dest
        self.selected = False

        self.sr_path = sr_path
        self.aliases = aliases
        self.target = target
        self.use_params = use_params
        self.data = data

        Styled.__init__(self, self._style, css_class=css_class)

    def build(self, base_path=''):
        base_path = ("%s/%s/" % (base_path, self.dest)).replace('//', '/')
        self.bare_path = _force_unicode(base_path.replace('//', '/')).lower()
        self.bare_path = self.bare_path.rstrip('/')
        self.base_path = base_path

        if self.use_params:
            base_path += query_string(dict(request.GET))

        # since we've been sloppy of keeping track of "//", get rid
        # of any that may be present
        self.path = base_path.replace('//', '/')

    def is_selected(self):
        stripped_path = _force_unicode(request.path.rstrip('/').lower())

        if not (self.sr_path or c.default_sr):
            return False
        if stripped_path == self.bare_path:
            return True
        site_path = c.site.user_path.lower() + self.bare_path
        if self.sr_path and stripped_path == site_path:
            return True
        if self.bare_path and stripped_path.startswith(self.bare_path):
            return True
        if stripped_path in self.aliases:
            return True

    def selected_title(self):
        """returns the title of the button when selected (for cases
        when it is different from self.title)"""
        return self.title

    def cachable_attrs(self):
        return [
            ('selected', self.selected),
            ('title', self.title),
            ('path', self.path),
            ('sr_path', self.sr_path),
            ('target', self.target),
            ('css_class', self.css_class),
            ('_id', self._id),
            ('data', self.data),
        ]


class QueryButton(NavButton):
    def __init__(self, title, dest, query_param, sr_path=True, aliases=None,
                 target="", css_class='', data=None):
        self.query_param = query_param
        NavButton.__init__(self, title, dest, sr_path=sr_path,
                           aliases=aliases, target=target, use_params=False,
                           css_class=css_class, data=data)

    def build(self, base_path=''):
        params = dict(request.GET)
        if self.dest:
            params[self.query_param] = self.dest
        elif self.query_param in params:
            del params[self.query_param]

        self.base_path = base_path
        base_path += query_string(params)
        self.path = base_path.replace('//', '/')

    def is_selected(self):
        if not self.dest and self.query_param not in dict(request.GET):
            return True
        return dict(request.GET).get(self.query_param, '') in self.aliases


class PostButton(NavButton):
    _style = "post"

    def __init__(self, title, dest, input_name, sr_path=True, aliases=None,
                 target="", css_class='', data=None):
        self.input_name = input_name
        NavButton.__init__(self, title, dest, sr_path=sr_path,
                           aliases=aliases, target=target, use_params=False,
                           css_class=css_class, data=data)

    def build(self, base_path=''):
        self.base_path = base_path
        self.action_params = {self.input_name: self.dest}

    def cachable_attrs(self):
        return [
            ('selected', self.selected),
            ('title', self.title),
            ('base_path', self.base_path),
            ('action_params', self.action_params),
            ('sr_path', self.sr_path),
            ('target', self.target),
            ('css_class', self.css_class),
            ('_id', self._id),
            ('data', self.data),
        ]

    def is_selected(self):
        return False


class ModeratorMailButton(NavButton):
    def is_selected(self):
        if c.default_sr and not self.sr_path:
            return NavButton.is_selected(self)
        elif not c.default_sr and self.sr_path:
            return NavButton.is_selected(self)


class OffsiteButton(NavButton):
    def build(self, base_path=''):
        self.sr_path = False
        self.path = self.bare_path = self.dest

    def cachable_attrs(self):
        return [
            ('path', self.path),
            ('title', self.title),
            ('css_class', self.css_class),
        ]


class SubredditButton(NavButton):
    from r2.models.subreddit import Frontpage, Mod, All, Random, RandomSubscription
    # TRANSLATORS: This refers to /r/mod
    name_overrides = {Mod: N_("mod"),
    # TRANSLATORS: This refers to the user's front page
                      Frontpage: N_("front"),
                      All: N_("all"),
                      Random: N_("random"),
    # TRANSLATORS: Gold feature, "myrandom", a random subreddit from your subscriptions
                      RandomSubscription: N_("myrandom")}

    def __init__(self, sr, css_class='', data=None):
        self.path = sr.path
        name = self.name_overrides.get(sr)
        name = _(name) if name else sr.name
        self.isselected = (c.site == sr)
        NavButton.__init__(self, name, sr.path, sr_path=False,
                           css_class=css_class, data=data)

    def build(self, base_path=''):
        self.bare_path = ""

    def is_selected(self):
        return self.isselected

    def cachable_attrs(self):
        return [
            ('path', self.path),
            ('title', self.title),
            ('isselected', self.isselected),
            ('css_class', self.css_class),
            ('data', self.data),
        ]


class NamedButton(NavButton):
    """Convenience class for handling the majority of NavButtons
    whereby the 'title' is just the translation of 'name' and the
    'dest' defaults to the 'name' as well (unless specified
    separately)."""

    def __init__(self, name, sr_path=True, aliases=None,
                 dest=None, fmt_args={}, use_params=False, css_class='',
                 data=None):
        self.name = name.strip('/')
        menutext = menu[self.name] % fmt_args
        dest = dest if dest is not None else name
        NavButton.__init__(self, menutext, dest, sr_path=sr_path,
                           aliases=aliases,
                           use_params=use_params, css_class=css_class,
                           data=data)


class JsButton(NavButton):
    """A button which fires a JS event and thus has no path and cannot
    be in the 'selected' state"""

    _style = "js"

    def __init__(self, title, tab_name=None, onclick='', css_class='',
                 data=None):
        self.tab_name = tab_name
        self.onclick = onclick
        dest = '#'
        NavButton.__init__(self, title, dest, sr_path=False,
                           css_class=css_class, data=data)

    def build(self, base_path=''):
        if self.tab_name:
            self.path = '#' + self.tab_name
        else:
            self.path = 'javascript:void(0)'

    def is_selected(self):
        return False

    def cachable_attrs(self):
        return [
            ('title', self.title),
            ('path', self.path),
            ('target', self.target),
            ('css_class', self.css_class),
            ('_id', self._id),
            ('tab_name', self.tab_name),
            ('onclick', self.onclick),
            ('data', self.data),
        ]


class PageNameNav(Styled):
    """generates the links and/or labels which live in the header
    between the header image and the first nav menu (e.g., the
    subreddit name, the page name, etc.)"""
    pass


class SortMenu(NavMenu):
    name = 'sort'
    hidden_options = []
    button_cls = QueryButton

    # these are _ prefixed to avoid colliding with NavMenu attributes
    _default = 'hot'
    _options = ('hot', 'new', 'top', 'old', 'controversial')
    _type = 'lightdrop'
    _title = N_("sorted by")

    def __init__(self, default=None, title='', base_path='', separator='|',
                 _id='', css_class=''):
        options = self.make_buttons()
        default = default or self._default
        base_path = base_path or request.path
        title = title or _(self._title)
        NavMenu.__init__(self, options, default=default, title=title,
                         type=self._type, base_path=base_path,
                         separator=separator, _id=_id, css_class=css_class)

    def make_buttons(self):
        buttons = []
        for name in self._options:
            css_class = 'hidden' if name in self.hidden_options else ''
            button = self.button_cls(self.make_title(name), name, self.name,
                                     css_class=css_class)
            buttons.append(button)
        return buttons

    def make_title(self, attr):
        return menu[attr]

    _mapping = {
        "hot": operators.desc('_hot'),
        "new": operators.desc('_date'),
        "old": operators.asc('_date'),
        "top": operators.desc('_score'),
        "controversial": operators.desc('_controversy'),
        "confidence": operators.desc('_confidence'),
        "random": operators.shuffled('_confidence'),
        "qa": operators.desc('_qa'),
    }
    _reverse_mapping = {v: k for k, v in _mapping.iteritems()}

    @classmethod
    def operator(cls, sort):
        return cls._mapping.get(sort)

    @classmethod
    def sort(cls, operator):
        return cls._reverse_mapping.get(operator)


class ProfileSortMenu(SortMenu):
    _default = 'new'
    _options = ('hot', 'new', 'top', 'controversial')


class CommentSortMenu(SortMenu):
    """Sort menu for comments pages"""
    _default = 'confidence'
    _options = ('confidence', 'top', 'new', 'controversial', 'old', 'random',
                'qa',)
    hidden_options = ['random']

    # Links may have a suggested sort of 'blank', which is an explicit None -
    # that is, do not check the subreddit for a suggested sort, either.
    suggested_sort_options = _options + ('blank',)

    def __init__(self, *args, **kwargs):
        self.suggested_sort = kwargs.pop('suggested_sort', None)
        super(CommentSortMenu, self).__init__(*args, **kwargs)

    @classmethod
    def visible_options(cls):
        return set(cls._options) - set(cls.hidden_options)

    def make_title(self, attr):
        title = super(CommentSortMenu, self).make_title(attr)
        if attr == self.suggested_sort:
            return title + ' ' + _('(suggested)')
        else:
            return title


class SearchSortMenu(SortMenu):
    """Sort menu for search pages."""
    _default = 'relevance'
    _options = ('relevance', 'hot', 'top', 'new', 'comments')

    @class_property
    def hidden_options(cls):
        return ['hot']

    def make_buttons(self):
        buttons = super(SearchSortMenu, self).make_buttons()
        if feature.is_enabled('link_relevancy'):
            button = self.button_cls('relevance2', 'relevance2', self.name)
            buttons.append(button)
        return buttons


class SubredditSearchSortMenu(SortMenu):
    """Sort menu for subreddit search pages."""
    _default = 'relevance'
    _options = ('relevance', 'activity')


class RecSortMenu(SortMenu):
    """Sort menu for recommendation page"""
    _default = 'new'
    _options = ('hot', 'new', 'top', 'controversial', 'relevance')


class KindMenu(SortMenu):
    name = 'kind'
    _default = 'all'
    _options = ('links', 'comments', 'messages', 'all')
    _title = N_("kind")

    def make_title(self, attr):
        if attr == "all":
            return _("all")
        return menu[attr]


class TimeMenu(SortMenu):
    """Menu for setting the time interval of the listing (from 'hour' to 'all')"""
    name = 't'
    _default = 'all'
    _options = ('hour', 'day', 'week', 'month', 'year', 'all')
    _title = N_("links from")

    @classmethod
    def operator(self, time):
        from r2.models import Link
        if time != 'all':
            return Link.c._date >= timeago(time)

class CommentsTimeMenu(TimeMenu):
    """Time Menu with the title changed for comments"""
    _title = N_("comments from")


class ProfileOverviewTimeMenu(TimeMenu):
    """Time Menu with the title changed for a user overview"""
    _title = N_("links and comments from")


class ControversyTimeMenu(TimeMenu):
    """time interval for controversial sort.  Make default time 'day' rather than 'all'"""
    _default = 'day'
    button_cls = PostButton


class SubredditMenu(NavMenu):
    def find_selected(self):
        """Always return False so the title is always displayed"""
        return None


class JsNavMenu(NavMenu):
    def find_selected(self):
        """Always return the first element."""
        return self.options[0]

# --------------------
# TODO: move to admin area
class AdminReporterMenu(SortMenu):
    default = 'top'
    options = ('hot', 'new', 'top')

class AdminKindMenu(KindMenu):
    options = ('all', 'links', 'comments', 'spam', 'autobanned')


class AdminTimeMenu(TimeMenu):
    get_param = 't'
    _default = 'day'
    _options = ('hour', 'day', 'week')
