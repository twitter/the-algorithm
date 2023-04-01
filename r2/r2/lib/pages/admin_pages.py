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

from pylons import config
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import N_

from r2.lib.wrapped import Templated
from r2.lib.pages import LinkInfoBar, Reddit
from r2.lib.menus import (
    NamedButton,
    NavButton,
    menu,
    NavMenu,
    OffsiteButton,
)
from r2.lib.utils import timesince

def admin_menu(**kwargs):
    buttons = [
        OffsiteButton("traffic", "/traffic"),
        NavButton(menu.awards, "awards"),
        NavButton(menu.errors, "error log"),
    ]

    admin_menu = NavMenu(buttons, title='admin tools', base_path='/admin',
                         type="lightdrop", **kwargs)
    return admin_menu

class AdminSidebar(Templated):
    def __init__(self, user):
        Templated.__init__(self)
        self.user = user


class SponsorSidebar(Templated):
    def __init__(self, user):
        Templated.__init__(self)
        self.user = user


class Details(Templated):
    def __init__(self, link, *a, **kw):
        Templated.__init__(self, *a, **kw)
        self.link = link


class AdminPage(Reddit):
    create_reddit_box  = False
    submit_box         = False
    extension_handling = False
    show_sidebar = False

    def __init__(self, nav_menus = None, *a, **kw):
        Reddit.__init__(self, nav_menus = nav_menus, *a, **kw)

class AdminProfileMenu(NavMenu):
    def __init__(self, path):
        NavMenu.__init__(self, [], base_path = path,
                         title = 'admin', type="tabdrop")


class AdminLinkMenu(NavMenu):
    def __init__(self, link):
        NavMenu.__init__(self, [], title='admin', type="tabdrop")


class AdminNotesSidebar(Templated):
    EMPTY_MESSAGE = {
        "domain": N_("No notes for this domain"),
        "ip": N_("No notes for this IP address"),
        "subreddit": N_("No notes for this subreddit"),
        "user": N_("No notes for this user"),
    }

    SYSTEMS = {
        "domain": N_("domain"),
        "ip": N_("IP address"),
        "subreddit": N_("subreddit"),
        "user": N_("user"),
    }

    def __init__(self, system, subject):
        from r2.models.admin_notes import AdminNotesBySystem

        self.system = system
        self.subject = subject
        self.author = c.user.name
        self.notes = AdminNotesBySystem.in_display_order(system, subject)
        # Convert timestamps for easier reading/translation
        for note in self.notes:
            note["timesince"] = timesince(note["when"])
        Templated.__init__(self)


class AdminLinkInfoBar(LinkInfoBar):
    pass


class AdminDetailsBar(Templated):
    pass


if config['r2.import_private']:
    from r2admin.lib.pages import *
