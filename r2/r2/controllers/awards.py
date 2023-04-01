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
from pylons import app_globals as g
from reddit_base import RedditController
from r2.lib.pages import AdminPage, AdminAwards
from r2.lib.pages import AdminAwardGive, AdminAwardWinners
from r2.lib.validator import *

class AwardsController(RedditController):

    @validate(VAdmin())
    def GET_index(self):
        res = AdminPage(content = AdminAwards(),
                        title = 'awards').render()
        return res

    @validate(VAdmin(),
              award = VAwardByCodename('awardcn'),
              recipient = nop('recipient'),
              desc = nop('desc'),
              url = nop('url'),
              hours = nop('hours'))
    def GET_give(self, award, recipient, desc, url, hours):
        if award is None:
            abort(404, 'page not found')

        res = AdminPage(content = AdminAwardGive(award, recipient, desc,
                                                 url, hours),
                        title='give an award').render()
        return res

    @validate(VAdmin(),
              award = VAwardByCodename('awardcn'))
    def GET_winners(self, award):
        if award is None:
            abort(404, 'page not found')

        res = AdminPage(content = AdminAwardWinners(award),
                        title='award winners').render()
        return res
