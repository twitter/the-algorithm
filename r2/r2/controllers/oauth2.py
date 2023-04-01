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

from datetime import datetime
from urllib import urlencode
import base64
import simplejson

from pylons import request, response
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _

from r2.config.extensions import set_extension
from r2.lib.base import abort
from reddit_base import RedditController, MinimalController, require_https
from r2.lib.db import tdb_cassandra
from r2.lib.db.thing import NotFound
from r2.lib.pages import RedditError
from r2.lib.strings import strings
from r2.models import Account, admintools, create_gift_gold, send_system_message
from r2.models.token import (
    OAuth2Client, OAuth2AuthorizationCode, OAuth2AccessToken,
    OAuth2RefreshToken, OAuth2Scope)
from r2.lib.errors import BadRequestError, ForbiddenError, errors
from r2.lib.pages import OAuth2AuthorizationPage
from r2.lib.require import RequirementException, require, require_split
from r2.lib.utils import constant_time_compare, parse_http_basic, UrlParser
from r2.lib.validator import (
    nop,
    validate,
    VRequired,
    VThrottledLogin,
    VOneOf,
    VUser,
    VModhash,
    VOAuth2ClientID,
    VOAuth2Scope,
    VOAuth2RefreshToken,
    VRatelimit,
    VLength,
)


def _update_redirect_uri(base_redirect_uri, params, as_fragment=False):
    parsed = UrlParser(base_redirect_uri)
    if as_fragment:
        parsed.fragment = urlencode(params)
    else:
        parsed.update_query(**params)
    return parsed.unparse()


def get_device_id(client):
    if client.is_first_party():
        return request.POST.get('device_id')


class OAuth2FrontendController(RedditController):
    def check_for_bearer_token(self):
        pass

    def pre(self):
        RedditController.pre(self)
        require_https()

    def _abort_oauth_error(self, error):
        g.stats.simple_event('oauth2.errors.%s' % error)
        abort(BadRequestError(error))

    def _check_redirect_uri(self, client, redirect_uri):
        if (errors.OAUTH2_INVALID_CLIENT, 'client_id') in c.errors:
            self._abort_oauth_error(errors.OAUTH2_INVALID_CLIENT)

        if not redirect_uri or redirect_uri != client.redirect_uri:
            self._abort_oauth_error(errors.OAUTH2_INVALID_REDIRECT_URI)

    def _check_response_type_and_scope(self, response_type, scope):
        if (errors.INVALID_OPTION, 'response_type') in c.errors:
            self._abort_oauth_error(errors.OAUTH2_INVALID_RESPONSE_TYPE)

        if (errors.OAUTH2_INVALID_SCOPE, 'scope') in c.errors:
            self._abort_oauth_error(errors.OAUTH2_INVALID_SCOPE)

    def _check_client_type_and_duration(self, response_type, client, duration):
        if response_type == "token" and client.is_confidential():
            # Prevent "confidential" clients from distributing tokens
            # in a non-confidential manner
            self._abort_oauth_error(errors.OAUTH2_CONFIDENTIAL_TOKEN)

        if response_type == "token" and duration != "temporary":
            # implicit grant -> No refresh tokens allowed
            self._abort_oauth_error(errors.OAUTH2_NO_REFRESH_TOKENS_ALLOWED)

    def _error_response(self, state, redirect_uri, as_fragment=False):
        """Return an error redirect."""
        resp = {"state": state}

        if (errors.OAUTH2_ACCESS_DENIED, "authorize") in c.errors:
            resp["error"] = "access_denied"
        elif (errors.INVALID_MODHASH, None) in c.errors:
            resp["error"] = "access_denied"
        else:
            resp["error"] = "invalid_request"

        final_redirect = _update_redirect_uri(redirect_uri, resp, as_fragment)
        return self.redirect(final_redirect, code=302)

    def _check_employee_grants(self, client, scope):
        if not c.user.employee or not client or not scope:
            return
        if client._id in g.employee_approved_clients:
            return
        if client._id in g.mobile_auth_allowed_clients:
            return
        # The identity scope doesn't leak much, and we don't mind if employees
        # prove their identity to some external service
        if scope.scopes == {"identity"}:
            return
        error_page = RedditError(
            title=_('this app has not been approved for use with employee accounts'),
            message="",
        )
        request.environ["usable_error_content"] = error_page.render()
        self.abort403()

    @validate(VUser(),
              response_type = VOneOf("response_type", ("code", "token")),
              client = VOAuth2ClientID(),
              redirect_uri = VRequired("redirect_uri", errors.OAUTH2_INVALID_REDIRECT_URI),
              scope = VOAuth2Scope(),
              state = VRequired("state", errors.NO_TEXT),
              duration = VOneOf("duration", ("temporary", "permanent"),
                                default="temporary"))
    def GET_authorize(self, response_type, client, redirect_uri, scope, state,
                      duration):
        """
        First step in [OAuth 2.0](http://oauth.net/2/) authentication.
        End users will be prompted for their credentials (username/password)
        and asked if they wish to authorize the application identified by
        the **client_id** parameter with the permissions specified by the
        **scope** parameter.  They are then redirected to the endpoint on
        the client application's side specified by **redirect_uri**.

        If the user granted permission to the application, the response will
        contain a **code** parameter with a temporary authorization code
        which can be exchanged for an access token at
        [/api/v1/access_token](#api_method_access_token).

        **redirect_uri** must match the URI configured for the client in the
        [app preferences](/prefs/apps).  All errors will show a 400 error
        page along with some information on what option was wrong.
        """
        self._check_employee_grants(client, scope)

        # Check redirect URI first; it will ensure client exists
        self._check_redirect_uri(client, redirect_uri)

        self._check_response_type_and_scope(response_type, scope)

        self._check_client_type_and_duration(response_type, client, duration)

        if not c.errors:
            return OAuth2AuthorizationPage(client, redirect_uri, scope, state,
                                           duration, response_type).render()
        else:
            self._abort_oauth_error(errors.INVALID_OPTION)

    @validate(VUser(),
              VModhash(fatal=False),
              client = VOAuth2ClientID(),
              redirect_uri = VRequired("redirect_uri", errors.OAUTH2_INVALID_REDIRECT_URI),
              scope = VOAuth2Scope(),
              state = VRequired("state", errors.NO_TEXT),
              duration = VOneOf("duration", ("temporary", "permanent"),
                                default="temporary"),
              authorize = VRequired("authorize", errors.OAUTH2_ACCESS_DENIED),
              response_type = VOneOf("response_type", ("code", "token"),
                                     default="code"))
    def POST_authorize(self, authorize, client, redirect_uri, scope, state,
                       duration, response_type):
        """Endpoint for OAuth2 authorization."""

        self._check_employee_grants(client, scope)

        self._check_redirect_uri(client, redirect_uri)

        self._check_response_type_and_scope(response_type, scope)

        self._check_client_type_and_duration(response_type, client, duration)

        if c.errors:
            return self._error_response(state, redirect_uri,
                                        as_fragment=(response_type == "token"))

        if response_type == "code":
            code = OAuth2AuthorizationCode._new(client._id, redirect_uri,
                                            c.user._id36, scope,
                                            duration == "permanent")
            resp = {"code": code._id, "state": state}
            final_redirect = _update_redirect_uri(redirect_uri, resp)
            g.stats.simple_event('oauth2.POST_authorize.authorization_code_create')
        elif response_type == "token":
            device_id = get_device_id(client)
            token = OAuth2AccessToken._new(
                client_id=client._id,
                user_id=c.user._id36,
                scope=scope,
                device_id=device_id,
            )
            resp = OAuth2AccessController._make_new_token_response(token)
            resp["state"] = state
            final_redirect = _update_redirect_uri(redirect_uri, resp, as_fragment=True)
            g.stats.simple_event('oauth2.POST_authorize.access_token_create')

        # If this is the first time the user is logging in with an official
        # mobile app, gild them
        if (g.live_config.get('mobile_gild_first_login') and
                not c.user.has_used_mobile_app and
                client._id in g.mobile_auth_gild_clients):
            buyer = Account.system_user()
            admintools.adjust_gold_expiration(
                c.user, days=g.mobile_auth_gild_time)
            create_gift_gold(
                buyer._id, c.user._id, g.mobile_auth_gild_time,
                datetime.now(g.tz), signed=True, note='first_mobile_auth')
            subject = 'Let there be gold! Reddit just sent you Reddit gold!'
            message = (
                "Thank you for using the Reddit mobile app!  As a thank you "
                "for logging in during launch week, you've been gifted %s of "
                "Reddit Gold.\n\n"
                "Reddit Gold is Reddit's premium membership program, which "
                "grants you: \n"
                "An ads-free experience in Reddit's mobile apps, and\n"
                "Extra site features on desktop\n\n"
                "Discuss and get help on the features and perks at "
                "r/goldbenefits."
            ) % g.mobile_auth_gild_message
            message += '\n\n' + strings.gold_benefits_msg
            send_system_message(c.user, subject, message, add_to_sent=False)
            c.user.has_used_mobile_app = True
            c.user._commit()

        return self.redirect(final_redirect, code=302)

class OAuth2AccessController(MinimalController):
    handles_csrf = True

    def pre(self):
        if g.disallow_db_writes:
            abort(403)

        set_extension(request.environ, "json")
        MinimalController.pre(self)
        require_https()
        if request.method != "OPTIONS":
            c.oauth2_client = self._get_client_auth()

    def _get_client_auth(self):
        auth = request.headers.get("Authorization")
        try:
            client_id, client_secret = parse_http_basic(auth)
            require(client_id)
            client = OAuth2Client.get_token(client_id)
            require(client)
            if client.is_confidential():
                require(client_secret)
                require(constant_time_compare(client.secret, client_secret))
            return client
        except RequirementException:
            abort(401, headers=[("WWW-Authenticate", 'Basic realm="reddit"')])

    def OPTIONS_access_token(self):
        """Send CORS headers for access token requests

        * Allow all origins
        * Only POST requests allowed to /api/v1/access_token
        * No ambient credentials
        * Authorization header required to identify the client
        * Expose common reddit headers

        """
        if "Origin" in request.headers:
            response.headers["Access-Control-Allow-Origin"] = "*"
            response.headers["Access-Control-Allow-Methods"] = \
                "POST"
            response.headers["Access-Control-Allow-Headers"] = \
                    "Authorization, "
            response.headers["Access-Control-Allow-Credentials"] = "false"
            response.headers['Access-Control-Expose-Headers'] = \
                self.COMMON_REDDIT_HEADERS

    @validate(
        grant_type=VOneOf("grant_type",
            (
                 "authorization_code",
                 "refresh_token",
                 "password",
                 "client_credentials",
                 "https://oauth.reddit.com/grants/installed_client",
            )
        ),
    )
    def POST_access_token(self, grant_type):
        """
        Exchange an [OAuth 2.0](http://oauth.net/2/) authorization code
        or refresh token (from [/api/v1/authorize](#api_method_authorize)) for
        an access token.

        On success, returns a URL-encoded dictionary containing
        **access_token**, **token_type**, **expires_in**, and **scope**.
        If an authorization code for a permanent grant was given, a
        **refresh_token** will be included. If there is a problem, an **error**
        parameter will be returned instead.

        Must be called using SSL, and must contain a HTTP `Authorization:`
        header which contains the application's client identifier as the
        username and client secret as the password.  (The client id and secret
        are visible on the [app preferences page](/prefs/apps).)

        Per the OAuth specification, **grant_type** must be one of:

        * ``authorization_code`` for the initial access token ("standard" OAuth2 flow)
        * ``refresh_token`` for renewing the access token.
        * ``password`` for script-type apps using password auth
        * ``client_credentials`` for application-only (signed out) access - confidential clients
        * ``https://oauth.reddit.com/grants/installed_client`` extension grant for application-only (signed out)
                access - non-confidential (installed) clients

        **redirect_uri** must exactly match the value that was used in the call
        to [/api/v1/authorize](#api_method_authorize) that created this grant.

        See reddit's [OAuth2 wiki](https://github.com/reddit/reddit/wiki/OAuth2) for
        more information.

        """
        self.OPTIONS_access_token()
        if grant_type == "authorization_code":
            return self._access_token_code()
        elif grant_type == "refresh_token":
            return self._access_token_refresh()
        elif grant_type == "password":
            return self._access_token_password()
        elif grant_type == "client_credentials":
            return self._access_token_client_credentials()
        elif grant_type == "https://oauth.reddit.com/grants/installed_client":
            return self._access_token_extension_client_credentials()
        else:
            resp = {"error": "unsupported_grant_type"}
            return self.api_wrapper(resp)

    def _check_for_errors(self):
        resp = {}
        if (errors.INVALID_OPTION, "scope") in c.errors:
            resp["error"] = "invalid_scope"
        else:
            resp["error"] = "invalid_request"
        return resp

    @classmethod
    def _make_new_token_response(cls, access_token, refresh_token=None):
        if not access_token:
            return {"error": "invalid_grant"}
        expires_in = int(access_token._ttl) if access_token._ttl else None
        resp = {
            "access_token": access_token._id,
            "token_type": access_token.token_type,
            "expires_in": expires_in,
            "scope": access_token.scope,
        }
        if refresh_token:
            resp["refresh_token"] = refresh_token._id

        if access_token.device_id:
            resp['device_id'] = access_token.device_id

        return resp

    @validate(code=nop("code"),
              redirect_uri=VRequired("redirect_uri",
                                     errors.OAUTH2_INVALID_REDIRECT_URI))
    def _access_token_code(self, code, redirect_uri):
        if not code:
            c.errors.add("NO_TEXT", field="code")
        if c.errors:
            return self.api_wrapper(self._check_for_errors())

        access_token = None
        refresh_token = None

        client = c.oauth2_client
        auth_token = OAuth2AuthorizationCode.use_token(code, client._id, redirect_uri)

        if auth_token:
            if auth_token.refreshable:
                refresh_token = OAuth2RefreshToken._new(
                    client_id=auth_token.client_id,
                    user_id=auth_token.user_id,
                    scope=auth_token.scope,
                )
                g.stats.simple_event(
                    'oauth2.access_token_code.refresh_token_create')

            device_id = get_device_id(client)
            access_token = OAuth2AccessToken._new(
                client_id=auth_token.client_id,
                user_id=auth_token.user_id,
                scope=auth_token.scope,
                refresh_token=refresh_token._id if refresh_token else "",
                device_id=device_id,
            )
            g.stats.simple_event(
                'oauth2.access_token_code.access_token_create')

        resp = self._make_new_token_response(access_token, refresh_token)

        return self.api_wrapper(resp)

    @validate(refresh_token=VOAuth2RefreshToken("refresh_token"))
    def _access_token_refresh(self, refresh_token):
        access_token = None
        if refresh_token:
            if refresh_token.client_id == c.oauth2_client._id:
                access_token = OAuth2AccessToken._new(
                    refresh_token.client_id, refresh_token.user_id,
                    refresh_token.scope,
                    refresh_token=refresh_token._id)
                g.stats.simple_event(
                    'oauth2.access_token_refresh.access_token_create')
            else:
                g.stats.simple_event(
                    'oauth2.errors.OAUTH2_INVALID_REFRESH_TOKEN')
                c.errors.add(errors.OAUTH2_INVALID_REFRESH_TOKEN)
        else:
            g.stats.simple_event('oauth2.errors.NO_TEXT')
            c.errors.add("NO_TEXT", field="refresh_token")

        if c.errors:
            resp = self._check_for_errors()
            response.status = 400
        else:
            g.stats.simple_event('oauth2.access_token_refresh.success')
            resp = self._make_new_token_response(access_token)
        return self.api_wrapper(resp)

    @validate(user=VThrottledLogin(["username", "password"]),
              scope=nop("scope"))
    def _access_token_password(self, user, scope):
        # username:password auth via OAuth is only allowed for
        # private use scripts
        client = c.oauth2_client
        if client.app_type != "script":
            g.stats.simple_event('oauth2.errors.PASSWORD_UNAUTHORIZED_CLIENT')
            return self.api_wrapper({"error": "unauthorized_client",
                "error_description": "Only script apps may use password auth"})
        dev_ids = client._developer_ids
        if not user or user._id not in dev_ids:
            g.stats.simple_event('oauth2.errors.INVALID_GRANT')
            return self.api_wrapper({"error": "invalid_grant"})
        if c.errors:
            return self.api_wrapper(self._check_for_errors())

        if scope:
            scope = OAuth2Scope(scope)
            if not scope.is_valid():
                g.stats.simple_event('oauth2.errors.PASSWORD_INVALID_SCOPE')
                c.errors.add(errors.INVALID_OPTION, "scope")
                return self.api_wrapper({"error": "invalid_scope"})
        else:
            scope = OAuth2Scope(OAuth2Scope.FULL_ACCESS)

        device_id = get_device_id(client)
        access_token = OAuth2AccessToken._new(
            client_id=client._id,
            user_id=user._id36,
            scope=scope,
            device_id=device_id,
        )
        g.stats.simple_event(
            'oauth2.access_token_password.access_token_create')
        resp = self._make_new_token_response(access_token)
        return self.api_wrapper(resp)

    @validate(
        scope=nop("scope"),
    )
    def _access_token_client_credentials(self, scope):
        client = c.oauth2_client
        if not client.is_confidential():
            g.stats.simple_event(
                'oauth2.errors.CLIENT_CREDENTIALS_UNAUTHORIZED_CLIENT')
            return self.api_wrapper({"error": "unauthorized_client",
                "error_description": "Only confidential clients may use client_credentials auth"})
        if scope:
            scope = OAuth2Scope(scope)
            if not scope.is_valid():
                g.stats.simple_event(
                    'oauth2.errors.CLIENT_CREDENTIALS_INVALID_SCOPE')
                c.errors.add(errors.INVALID_OPTION, "scope")
                return self.api_wrapper({"error": "invalid_scope"})
        else:
            scope = OAuth2Scope(OAuth2Scope.FULL_ACCESS)

        device_id = get_device_id(client)
        access_token = OAuth2AccessToken._new(
            client_id=client._id,
            user_id="",
            scope=scope,
            device_id=device_id,
        )
        g.stats.simple_event(
            'oauth2.access_token_client_credentials.access_token_create')
        resp = self._make_new_token_response(access_token)
        return self.api_wrapper(resp)

    @validate(
        scope=nop("scope"),
        device_id=VLength("device_id", 50, min_length=20),
    )
    def _access_token_extension_client_credentials(self, scope, device_id):
        if ((errors.NO_TEXT, "device_id") in c.errors or
                (errors.TOO_SHORT, "device_id") in c.errors or
                (errors.TOO_LONG, "device_id") in c.errors):
            g.stats.simple_event('oauth2.errors.BAD_DEVICE_ID')
            return self.api_wrapper({
                "error": "invalid_request",
                "error_description": "bad device_id",
            })

        client = c.oauth2_client
        if scope:
            scope = OAuth2Scope(scope)
            if not scope.is_valid():
                g.stats.simple_event(
                    'oauth2.errors.EXTENSION_CLIENT_CREDENTIALS_INVALID_SCOPE')
                c.errors.add(errors.INVALID_OPTION, "scope")
                return self.api_wrapper({"error": "invalid_scope"})
        else:
            scope = OAuth2Scope(OAuth2Scope.FULL_ACCESS)

        access_token = OAuth2AccessToken._new(
            client_id=client._id,
            user_id="",
            scope=scope,
            device_id=device_id,
        )
        g.stats.simple_event(
            'oauth2.access_token_extension_client_credentials.'
            'access_token_create')
        resp = self._make_new_token_response(access_token)
        return self.api_wrapper(resp)

    def OPTIONS_revoke_token(self):
        """Send CORS headers for token revocation requests

        * Allow all origins
        * Only POST requests allowed to /api/v1/revoke_token
        * No ambient credentials
        * Authorization header required to identify the client
        * Expose common reddit headers

        """
        if "Origin" in request.headers:
            response.headers["Access-Control-Allow-Origin"] = "*"
            response.headers["Access-Control-Allow-Methods"] = \
                "POST"
            response.headers["Access-Control-Allow-Headers"] = \
                    "Authorization, "
            response.headers["Access-Control-Allow-Credentials"] = "false"
            response.headers['Access-Control-Expose-Headers'] = \
                self.COMMON_REDDIT_HEADERS

    @validate(
        VRatelimit(rate_user=False, rate_ip=True, prefix="rate_revoke_token_"),
        token_id=nop("token"),
        token_hint=VOneOf("token_type_hint", ("access_token", "refresh_token")),
    )
    def POST_revoke_token(self, token_id, token_hint):
        '''Revoke an OAuth2 access or refresh token.

        token_type_hint is optional, and hints to the server
        whether the passed token is a refresh or access token.

        A call to this endpoint is considered a success if
        the passed `token_id` is no longer valid. Thus, if an invalid
        `token_id` was passed in, a successful 204 response will be returned.

        See [RFC7009](http://tools.ietf.org/html/rfc7009)

        '''
        self.OPTIONS_revoke_token()
        # In success cases, this endpoint returns no data.
        response.status = 204

        if not token_id:
            return

        types = (OAuth2AccessToken, OAuth2RefreshToken)
        if token_hint == "refresh_token":
            types = reversed(types)

        for token_type in types:
            try:
                token = token_type._byID(token_id)
            except tdb_cassandra.NotFound:
                g.stats.simple_event(
                    'oauth2.POST_revoke_token.cass_not_found.%s'
                    % token_type.__name__)
                continue
            else:
                break
        else:
            # No Token found. The given token ID is already gone
            # or never existed. Either way, from the client's perspective,
            # the passed in token is no longer valid.
            return

        if constant_time_compare(token.client_id, c.oauth2_client._id):
            token.revoke()
        else:
            # RFC 7009 is not clear on how to handle this case.
            # Given that a malicious client could do much worse things
            # with a valid token then revoke it, returning an error
            # here is best as it may help certain clients debug issues
            response.status = 400
            g.stats.simple_event(
                'oauth2.errors.REVOKE_TOKEN_UNAUTHORIZED_CLIENT')
            return self.api_wrapper({"error": "unauthorized_client"})


def require_oauth2_scope(*scopes):
    def oauth2_scope_wrap(fn):
        fn.oauth2_perms = {"required_scopes": scopes, "oauth2_allowed": True}
        return fn
    return oauth2_scope_wrap


def allow_oauth2_access(fn):
    fn.oauth2_perms = {"required_scopes": [], "oauth2_allowed": True}
    return fn
