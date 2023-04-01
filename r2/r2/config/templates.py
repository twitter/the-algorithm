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

from r2.lib.manager import tp_manager
from r2.lib.jsontemplates import *

tpm = tp_manager.tp_manager()

def api(type, cls):
    tpm.add_handler(type, 'api', cls())
    tpm.add_handler(type, 'api-html', cls())
    tpm.add_handler(type, 'api-compact', cls())


def register_api_templates(template_name, template_class):
    for style in ('api', 'api-html', 'api-compact'):
        tpm.add_handler(
            name=template_name,
            style=style,
            handler=template_class,
        )


# blanket fallback rule
api('templated', NullJsonTemplate)

# class specific overrides
api('link',          LinkJsonTemplate)
api('promotedlink',  PromotedLinkJsonTemplate)
api('message',       MessageJsonTemplate)
api('subreddit',     SubredditJsonTemplate)
api('labeledmulti',  LabeledMultiJsonTemplate)
api('reddit',        RedditJsonTemplate)
api('panestack',     PanestackJsonTemplate)
api('htmlpanestack', NullJsonTemplate)
api('listing',       ListingJsonTemplate)
api('searchlisting', SearchListingJsonTemplate)
api('userlisting',   UserListingJsonTemplate)
api('usertableitem', UserTableItemJsonTemplate)
api('account',       AccountJsonTemplate)

api('reltableitem', RelTableItemJsonTemplate)
api('bannedtableitem', BannedTableItemJsonTemplate)
api('mutedtableitem', MutedTableItemJsonTemplate)
api('invitedmodtableitem', InvitedModTableItemJsonTemplate)
api('friendtableitem', FriendTableItemJsonTemplate)

api('organiclisting',       OrganicListingJsonTemplate)
api('subreddittraffic', TrafficJsonTemplate)
api('takedownpane', TakedownJsonTemplate)
api('policyview', PolicyViewJsonTemplate)

api('wikibasepage', WikiJsonTemplate)
api('wikipagerevisions', WikiJsonTemplate)
api('wikiview', WikiViewJsonTemplate)
api('wikirevision', WikiRevisionJsonTemplate)

api('wikipagelisting', WikiPageListingJsonTemplate)
api('wikipagediscussions', WikiJsonTemplate)
api('wikipagesettings', WikiSettingsJsonTemplate)

api('flairlist', FlairListJsonTemplate)
api('flaircsv', FlairCsvJsonTemplate)
api('flairselector', FlairSelectorJsonTemplate)

api('subredditstylesheet', StylesheetTemplate)
api('subredditstylesheetsource', StylesheetTemplate)
api('createsubreddit', SubredditSettingsTemplate)
api('uploadedimage', UploadedImageJsonTemplate)

api('modaction', ModActionTemplate)

api('trophy', TrophyJsonTemplate)
api('rules', RulesJsonTemplate)


register_api_templates('comment', CommentJsonTemplate)
register_api_templates('morerecursion', MoreCommentJsonTemplate)
register_api_templates('morechildren', MoreCommentJsonTemplate)
