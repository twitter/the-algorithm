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

import crypt

import bcrypt

from pylons import request
from pylons import app_globals as g

from r2.lib.configparse import ConfigValue
from r2.lib.providers.auth import AuthenticationProvider
from r2.lib.require import RequirementException
from r2.lib.utils import constant_time_compare, parse_http_basic


class HttpAuthenticationProvider(AuthenticationProvider):
    """An authentication provider based on HTTP basic authentication.

    This provider uses HTTP basic authentication (via the Authorization header)
    to authenticate the user. Logout is disallowed.

    If auth_trust_http_authorization is set to true, the Authorization header
    is fully trusted. The password will not be checked and accounts will be
    automatically registered when not already present.

    """

    config = {
        ConfigValue.bool: [
            "auth_trust_http_authorization",
        ],
    }

    def is_logout_allowed(self):
        return False

    def get_authenticated_account(self):
        from r2.models import Account, NotFound, register

        try:
            authorization = request.environ.get("HTTP_AUTHORIZATION")
            username, password = parse_http_basic(authorization)
        except RequirementException:
            return None

        try:
            account = Account._by_name(username)
        except NotFound:
            if g.auth_trust_http_authorization:
                # note: we're explicitly allowing automatic re-registration of
                # _deleted accounts and login of _banned accounts here because
                # we're trusting you know what you're doing in an SSO situation
                account = register(username, password, request.ip)
            else:
                return None

        # if we're to trust the authorization headers, don't check passwords
        if g.auth_trust_http_authorization:
            return account

        # not all systems support bcrypt in the standard crypt
        if account.password.startswith("$2a$"):
            expected_hash = bcrypt.hashpw(password, account.password)
        else:
            expected_hash = crypt.crypt(password, account.password)

        if not constant_time_compare(expected_hash, account.password):
            return None
        return account
