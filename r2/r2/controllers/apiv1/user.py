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
from pylons import response
from pylons import tmpl_context as c

from r2.controllers.api_docs import api_doc, api_section
from r2.controllers.oauth2 import require_oauth2_scope
from r2.controllers.reddit_base import OAuth2OnlyController
from r2.lib.jsontemplates import (
    FriendTableItemJsonTemplate,
    get_usertrophies,
    IdentityJsonTemplate,
    KarmaListJsonTemplate,
    PrefsJsonTemplate,
)
from r2.lib.pages import FriendTableItem
from r2.lib.validator import (
    validate,
    VAccountByName,
    VFriendOfMine,
    VLength,
    VList,
    VUser,
    VValidatedJSON,
)
from r2.models import Account, Trophy
import r2.lib.errors as errors
import r2.lib.validator.preferences as vprefs


PREFS_JSON_SPEC = VValidatedJSON.PartialObject({
    k[len("pref_"):]: v for k, v in
    vprefs.PREFS_VALIDATORS.iteritems()
})


class APIv1UserController(OAuth2OnlyController):
    @require_oauth2_scope("identity")
    @validate(
        VUser(),
    )
    @api_doc(api_section.account)
    def GET_me(self):
        "Returns the identity of the user currently authenticated via OAuth."
        resp = IdentityJsonTemplate().data(c.oauth_user)
        return self.api_wrapper(resp)

    @require_oauth2_scope("identity")
    @validate(
        VUser(),
        fields=VList(
            "fields",
            choices=PREFS_JSON_SPEC.spec.keys(),
            error=errors.errors.NON_PREFERENCE,
        ),
    )
    @api_doc(api_section.account, uri='/api/v1/me/prefs')
    def GET_prefs(self, fields):
        """Return the preference settings of the logged in user"""
        resp = PrefsJsonTemplate(fields).data(c.oauth_user)
        return self.api_wrapper(resp)

    @require_oauth2_scope("read")
    @validate(
        user=VAccountByName('username'),
    )
    @api_doc(
        section=api_section.users,
        uri='/api/v1/user/{username}/trophies',
    )
    def GET_usertrophies(self, user):
        """Return a list of trophies for the a given user."""
        return self.api_wrapper(get_usertrophies(user))

    @require_oauth2_scope("identity")
    @validate(
        VUser(),
    )
    @api_doc(
        section=api_section.account,
        uri='/api/v1/me/trophies',
    )
    def GET_trophies(self):
        """Return a list of trophies for the current user."""
        return self.api_wrapper(get_usertrophies(c.oauth_user))

    @require_oauth2_scope("mysubreddits")
    @validate(
        VUser(),
    )
    @api_doc(
        section=api_section.account,
        uri='/api/v1/me/karma',
    )
    def GET_karma(self):
        """Return a breakdown of subreddit karma."""
        karmas = c.oauth_user.all_karmas(include_old=False)
        resp = KarmaListJsonTemplate().render(karmas)
        return self.api_wrapper(resp.finalize())

    PREFS_JSON_VALIDATOR = VValidatedJSON("json", PREFS_JSON_SPEC,
                                          body=True)

    @require_oauth2_scope("account")
    @validate(
        VUser(),
        validated_prefs=PREFS_JSON_VALIDATOR,
    )
    @api_doc(api_section.account, json_model=PREFS_JSON_VALIDATOR,
             uri='/api/v1/me/prefs')
    def PATCH_prefs(self, validated_prefs):
        user_prefs = c.user.preferences()
        for short_name, new_value in validated_prefs.iteritems():
            pref_name = "pref_" + short_name
            user_prefs[pref_name] = new_value
        vprefs.filter_prefs(user_prefs, c.user)
        vprefs.set_prefs(c.user, user_prefs)
        c.user._commit()
        return self.api_wrapper(PrefsJsonTemplate().data(c.user))

    FRIEND_JSON_SPEC = VValidatedJSON.PartialObject({
        "name": VAccountByName("name"),
        "note": VLength("note", 300),
    })
    FRIEND_JSON_VALIDATOR = VValidatedJSON("json", spec=FRIEND_JSON_SPEC,
                                           body=True)
    @require_oauth2_scope('subscribe')
    @validate(
        VUser(),
        friend=VAccountByName('username'),
        notes_json=FRIEND_JSON_VALIDATOR,
    )
    @api_doc(api_section.users, json_model=FRIEND_JSON_VALIDATOR,
             uri='/api/v1/me/friends/{username}')
    def PUT_friends(self, friend, notes_json):
        """Create or update a "friend" relationship.

        This operation is idempotent. It can be used to add a new
        friend, or update an existing friend (e.g., add/change the
        note on that friend)

        """
        err = None
        if 'name' in notes_json and notes_json['name'] != friend:
            # The 'name' in the JSON is optional, but if present, must
            # match the username from the URL
            err = errors.RedditError('BAD_USERNAME', fields='name')
        if 'note' in notes_json and not c.user.gold:
            err = errors.RedditError('GOLD_REQUIRED', fields='note')
        if err:
            self.on_validation_error(err)

        # See if the target is already an existing friend.
        # If not, create the friend relationship.
        friend_rel = Account.get_friend(c.user, friend)
        rel_exists = bool(friend_rel)
        if not friend_rel:
            friend_rel = c.user.add_friend(friend)
            response.status = 201

        if 'note' in notes_json:
            note = notes_json['note'] or ''
            if not rel_exists:
                # If this is a newly created friend relationship,
                # the cache needs to be updated before a note can
                # be applied
                c.user.friend_rels_cache(_update=True)
            c.user.add_friend_note(friend, note)
        rel_view = FriendTableItem(friend_rel)
        return self.api_wrapper(FriendTableItemJsonTemplate().data(rel_view))

    @require_oauth2_scope('mysubreddits')
    @validate(
        VUser(),
        friend_rel=VFriendOfMine('username'),
    )
    @api_doc(api_section.users, uri='/api/v1/me/friends/{username}')
    def GET_friends(self, friend_rel):
        """Get information about a specific 'friend', such as notes."""
        rel_view = FriendTableItem(friend_rel)
        return self.api_wrapper(FriendTableItemJsonTemplate().data(rel_view))

    @require_oauth2_scope('subscribe')
    @validate(
        VUser(),
        friend_rel=VFriendOfMine('username'),
    )
    @api_doc(api_section.users, uri='/api/v1/me/friends/{username}')
    def DELETE_friends(self, friend_rel):
        """Stop being friends with a user."""
        c.user.remove_friend(friend_rel._thing2)
        if c.user.gold:
            c.user.friend_rels_cache(_update=True)
        response.status = 204
