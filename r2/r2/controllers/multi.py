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

from pylons import request, response
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _

from r2.config.extensions import set_extension
from r2.controllers.api_docs import api_doc, api_section
from r2.controllers.reddit_base import RedditController, abort_with_error
from r2.controllers.oauth2 import require_oauth2_scope
from r2.models.account import Account
from r2.models.subreddit import (
    FakeSubreddit,
    Subreddit,
    LabeledMulti,
    TooManySubredditsError,
)
from r2.lib.db import tdb_cassandra
from r2.lib.validator import (
    validate,
    VAccountByName,
    VBoolean,
    VColor,
    VLength,
    VMarkdownLength,
    VModhash,
    VMultiPath,
    VMultiByPath,
    VOneOf,
    VSubredditName,
    VSRByName,
    VUser,
    VValidatedJSON,
)
from r2.lib.pages.things import wrap_things
from r2.lib.jsontemplates import (
    LabeledMultiJsonTemplate,
    LabeledMultiDescriptionJsonTemplate,
)
from r2.lib.errors import RedditError


multi_sr_data_json_spec = VValidatedJSON.Object({
    'name': VSubredditName('name', allow_language_srs=True),
})

MAX_DESC = 10000
MAX_DISP_NAME = 50
WRITABLE_MULTI_FIELDS = ('visibility', 'description_md', 'display_name',
                         'key_color', 'weighting_scheme')

multi_json_spec = VValidatedJSON.PartialObject({
    'description_md': VMarkdownLength('description_md', max_length=MAX_DESC,
                                      empty_error=None),
    'display_name': VLength('display_name', max_length=MAX_DISP_NAME),
    'icon_name': VOneOf('icon_name', g.multi_icons + ("", None)),
    'key_color': VColor('key_color'),
    'visibility': VOneOf('visibility', ('private', 'public', 'hidden')),
    'weighting_scheme': VOneOf('weighting_scheme', ('classic', 'fresh')),
    'subreddits': VValidatedJSON.ArrayOf(multi_sr_data_json_spec),
})


multi_description_json_spec = VValidatedJSON.Object({
    'body_md': VMarkdownLength('body_md', max_length=MAX_DESC, empty_error=None),
})


class MultiApiController(RedditController):
    def on_validation_error(self, error):
        abort_with_error(error, error.code or 400)

    def pre(self):
        set_extension(request.environ, "json")
        RedditController.pre(self)

    def _format_multi_list(self, multis, viewer, expand_srs):
        templ = LabeledMultiJsonTemplate(expand_srs)
        resp = [templ.render(multi).finalize() for multi in multis
                if multi.can_view(viewer)]
        return self.api_wrapper(resp)

    @require_oauth2_scope("read")
    @validate(
        user=VAccountByName("username"),
        expand_srs=VBoolean("expand_srs"),
    )
    @api_doc(api_section.multis, uri="/api/multi/user/{username}")
    def GET_list_multis(self, user, expand_srs):
        """Fetch a list of public multis belonging to `username`"""
        multis = LabeledMulti.by_owner(user)
        return self._format_multi_list(multis, c.user, expand_srs)

    @require_oauth2_scope("read")
    @validate(VUser(), expand_srs=VBoolean("expand_srs"))
    @api_doc(api_section.multis, uri="/api/multi/mine")
    def GET_my_multis(self, expand_srs):
        """Fetch a list of multis belonging to the current user."""
        multis = LabeledMulti.by_owner(c.user)
        return self._format_multi_list(multis, c.user, expand_srs)

    def _format_multi(self, multi, expand_sr_info=False):
        multi_info = LabeledMultiJsonTemplate(expand_sr_info).render(multi)
        return self.api_wrapper(multi_info.finalize())

    @require_oauth2_scope("read")
    @validate(
        multi=VMultiByPath("multipath", require_view=True),
        expand_srs=VBoolean("expand_srs"),
    )
    @api_doc(
        api_section.multis,
        uri="/api/multi/{multipath}",
        uri_variants=['/api/filter/{filterpath}'],
    )
    def GET_multi(self, multi, expand_srs):
        """Fetch a multi's data and subreddit list by name."""
        return self._format_multi(multi, expand_srs)

    def _check_new_multi_path(self, path_info):
        if path_info['owner'].lower() != c.user.name.lower():
            raise RedditError('MULTI_CANNOT_EDIT', code=403,
                              fields='multipath')
        return c.user

    def _add_multi_srs(self, multi, sr_datas):
        srs = Subreddit._by_name(sr_data['name'] for sr_data in sr_datas)

        for sr in srs.itervalues():
            if isinstance(sr, FakeSubreddit):
                raise RedditError('MULTI_SPECIAL_SUBREDDIT',
                                  msg_params={'path': sr.path},
                                  code=400)

        sr_props = {}
        for sr_data in sr_datas:
            try:
                sr = srs[sr_data['name']]
            except KeyError:
                raise RedditError('SUBREDDIT_NOEXIST', code=400)
            else:
                # name is passed in via the API data format, but should not be
                # stored on the model.
                del sr_data['name']
                sr_props[sr] = sr_data

        try:
            multi.add_srs(sr_props)
        except TooManySubredditsError as e:
            raise RedditError('MULTI_TOO_MANY_SUBREDDITS', code=409)

        return sr_props

    def _write_multi_data(self, multi, data):
        srs = data.pop('subreddits', None)
        if srs is not None:
            multi.clear_srs()
            try:
                self._add_multi_srs(multi, srs)
            except:
                multi._revert()
                raise

        if 'icon_name' in data:
            try:
                multi.set_icon_by_name(data.pop('icon_name'))
            except:
                multi._revert()
                raise

        for key, val in data.iteritems():
            if key in WRITABLE_MULTI_FIELDS:
                setattr(multi, key, val)

        multi._commit()
        return multi

    @require_oauth2_scope("subscribe")
    @validate(
        VUser(),
        VModhash(),
        path_info=VMultiPath("multipath", required=False),
        data=VValidatedJSON("model", multi_json_spec),
    )
    @api_doc(api_section.multis, extends=GET_multi)
    def POST_multi(self, path_info, data):
        """Create a multi. Responds with 409 Conflict if it already exists."""

        if not path_info and "path" in data:
            path_info = VMultiPath("").run(data["path"])
        elif 'display_name' in data:
            # if path not provided, create multi for user
            path = LabeledMulti.slugify(c.user, data['display_name'])
            path_info = VMultiPath("").run(path)

        if not path_info:
            raise RedditError('BAD_MULTI_PATH', code=400)

        owner = self._check_new_multi_path(path_info)

        try:
            LabeledMulti._byID(path_info['path'])
        except tdb_cassandra.NotFound:
            multi = LabeledMulti.create(path_info['path'], owner)
            response.status = 201
        else:
            raise RedditError('MULTI_EXISTS', code=409, fields='multipath')

        self._write_multi_data(multi, data)
        return self._format_multi(multi)

    @require_oauth2_scope("subscribe")
    @validate(
        VUser(),
        VModhash(),
        path_info=VMultiPath("multipath"),
        data=VValidatedJSON("model", multi_json_spec),
    )
    @api_doc(api_section.multis, extends=GET_multi)
    def PUT_multi(self, path_info, data):
        """Create or update a multi."""

        owner = self._check_new_multi_path(path_info)

        try:
            multi = LabeledMulti._byID(path_info['path'])
        except tdb_cassandra.NotFound:
            multi = LabeledMulti.create(path_info['path'], owner)
            response.status = 201

        self._write_multi_data(multi, data)
        return self._format_multi(multi)

    @require_oauth2_scope("subscribe")
    @validate(
        VUser(),
        VModhash(),
        multi=VMultiByPath("multipath", require_edit=True),
    )
    @api_doc(api_section.multis, extends=GET_multi)
    def DELETE_multi(self, multi):
        """Delete a multi."""
        multi.delete()

    def _copy_multi(self, from_multi, to_path_info, rename=False):
        """Copy a multi to a user account."""

        to_owner = self._check_new_multi_path(to_path_info)

        # rename requires same owner
        if rename and from_multi.owner != to_owner:
            raise RedditError('MULTI_CANNOT_EDIT', code=400)

        try:
            LabeledMulti._byID(to_path_info['path'])
        except tdb_cassandra.NotFound:
            to_multi = LabeledMulti.copy(to_path_info['path'], from_multi,
                                         owner=to_owner)
        else:
            raise RedditError('MULTI_EXISTS', code=409, fields='multipath')

        return to_multi

    @require_oauth2_scope("subscribe")
    @validate(
        VUser(),
        VModhash(),
        from_multi=VMultiByPath("from", require_view=True, kinds='m'),
        to_path_info=VMultiPath("to", required=False,
            docs={"to": "destination multireddit url path"},
        ),
        display_name=VLength("display_name", max_length=MAX_DISP_NAME,
                             empty_error=None),
    )
    @api_doc(
        api_section.multis,
        uri="/api/multi/copy",
    )
    def POST_multi_copy(self, from_multi, to_path_info, display_name):
        """Copy a multi.

        Responds with 409 Conflict if the target already exists.

        A "copied from ..." line will automatically be appended to the
        description.

        """
        if not to_path_info:
            if display_name:
                # if path not provided, copy multi to same owner
                path = LabeledMulti.slugify(from_multi.owner, display_name)
                to_path_info = VMultiPath("").run(path)
            else:
                raise RedditError('BAD_MULTI_PATH', code=400)

        to_multi = self._copy_multi(from_multi, to_path_info)

        from_path = from_multi.path
        to_multi.copied_from = from_path
        if to_multi.description_md:
            to_multi.description_md += '\n\n'
        to_multi.description_md += _('copied from %(source)s') % {
            # force markdown linking since /user/foo is not autolinked
            'source': '[%s](%s)' % (from_path, from_path)
        }
        to_multi.visibility = 'private'
        if display_name:
            to_multi.display_name = display_name
        to_multi._commit()

        return self._format_multi(to_multi)

    @require_oauth2_scope("subscribe")
    @validate(
        VUser(),
        VModhash(),
        from_multi=VMultiByPath("from", require_edit=True, kinds='m'),
        to_path_info=VMultiPath("to", required=False,
            docs={"to": "destination multireddit url path"},
        ),
        display_name=VLength("display_name", max_length=MAX_DISP_NAME,
                             empty_error=None),
    )
    @api_doc(
        api_section.multis,
        uri="/api/multi/rename",
    )
    def POST_multi_rename(self, from_multi, to_path_info, display_name):
        """Rename a multi."""
        if not to_path_info:
            if display_name:
                path = LabeledMulti.slugify(from_multi.owner, display_name)
                to_path_info = VMultiPath("").run(path)
            else:
                raise RedditError('BAD_MULTI_PATH', code=400)

        to_multi = self._copy_multi(from_multi, to_path_info, rename=True)

        if display_name:
            to_multi.display_name = display_name
            to_multi._commit()
        from_multi.delete()

        return self._format_multi(to_multi)

    def _get_multi_subreddit(self, multi, sr):
        resp = LabeledMultiJsonTemplate.sr_props(multi, [sr])[0]
        return self.api_wrapper(resp)

    @require_oauth2_scope("read")
    @validate(
        VUser(),
        multi=VMultiByPath("multipath", require_view=True),
        sr=VSRByName('srname'),
    )
    @api_doc(
        api_section.multis,
        uri="/api/multi/{multipath}/r/{srname}",
        uri_variants=['/api/filter/{filterpath}/r/{srname}'],
    )
    def GET_multi_subreddit(self, multi, sr):
        """Get data about a subreddit in a multi."""
        return self._get_multi_subreddit(multi, sr)

    @require_oauth2_scope("subscribe")
    @validate(
        VUser(),
        VModhash(),
        multi=VMultiByPath("multipath", require_edit=True),
        sr_name=VSubredditName('srname', allow_language_srs=True),
        data=VValidatedJSON("model", multi_sr_data_json_spec),
    )
    @api_doc(api_section.multis, extends=GET_multi_subreddit)
    def PUT_multi_subreddit(self, multi, sr_name, data):
        """Add a subreddit to a multi."""

        new = not any(sr.name.lower() == sr_name.lower() for sr in multi.srs)

        data['name'] = sr_name
        sr_props = self._add_multi_srs(multi, [data])
        sr = sr_props.items()[0][0]
        multi._commit()

        if new:
            response.status = 201

        return self._get_multi_subreddit(multi, sr)

    @require_oauth2_scope("subscribe")
    @validate(
        VUser(),
        VModhash(),
        multi=VMultiByPath("multipath", require_edit=True),
        sr=VSRByName('srname'),
    )
    @api_doc(api_section.multis, extends=GET_multi_subreddit)
    def DELETE_multi_subreddit(self, multi, sr):
        """Remove a subreddit from a multi."""
        multi.del_srs(sr)
        multi._commit()

    def _format_multi_description(self, multi):
        resp = LabeledMultiDescriptionJsonTemplate().render(multi).finalize()
        return self.api_wrapper(resp)

    @require_oauth2_scope("read")
    @validate(
        VUser(),
        multi=VMultiByPath("multipath", require_view=True, kinds='m'),
    )
    @api_doc(
        api_section.multis,
        uri="/api/multi/{multipath}/description",
    )
    def GET_multi_description(self, multi):
        """Get a multi's description."""
        return self._format_multi_description(multi)

    @require_oauth2_scope("read")
    @validate(
        VUser(),
        VModhash(),
        multi=VMultiByPath("multipath", require_edit=True, kinds='m'),
        data=VValidatedJSON('model', multi_description_json_spec),
    )
    @api_doc(api_section.multis, extends=GET_multi_description)
    def PUT_multi_description(self, multi, data):
        """Change a multi's markdown description."""
        multi.description_md = data['body_md']
        multi._commit()
        return self._format_multi_description(multi)
