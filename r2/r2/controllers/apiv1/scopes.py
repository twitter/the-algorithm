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
from webob.exc import HTTPBadRequest
from r2.controllers import api_docs
from r2.controllers.oauth2 import allow_oauth2_access
from r2.controllers.reddit_base import RedditController
from r2.lib.base import abort
from r2.lib.validator import validate, nop
from r2.models import OAuth2Scope


class APIv1ScopesController(RedditController):
    THREE_SIXTY = OAuth2Scope.FULL_ACCESS

    @allow_oauth2_access
    @validate(
        scope_str=nop("scopes",
                   docs={"scopes": "(optional) An OAuth2 scope string"}),
    )
    @api_docs.api_doc(api_docs.api_section.misc)
    def GET_scopes(self, scope_str):
        """Retrieve descriptions of reddit's OAuth2 scopes.

        If no scopes are given, information on all scopes are returned.

        Invalid scope(s) will result in a 400 error with body that indicates
        the invalid scope(s).

        """
        scopes = OAuth2Scope(scope_str or self.THREE_SIXTY)
        if scope_str and not scopes.is_valid():
            invalid = [s for s in scopes.scopes if s not in scopes.scope_info]
            error = {"error": "invalid_scopes", "invalid_scopes": invalid}
            http_err = HTTPBadRequest()
            http_err.error_data = error
            abort(http_err)
        return self.api_wrapper({k: v for k, v in scopes.details() if k})
