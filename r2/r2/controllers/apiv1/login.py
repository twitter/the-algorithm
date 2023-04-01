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

from r2.config.extensions import set_extension
from r2.controllers.reddit_base import RedditController, generate_modhash
from r2.controllers.login import handle_login, handle_register
from r2.lib.csrf import csrf_exempt
from r2.lib.validator import (
    json_validate,
    ValidEmail,
    VPasswordChange,
    VRatelimit,
    VSigned,
    VThrottledLogin,
    VUname,
)


class APIv1LoginController(RedditController):

    def pre(self):
        super(APIv1LoginController, self).pre()
        c.extension = "json"
        set_extension(request.environ, "json")

    @csrf_exempt
    @json_validate(
        VRatelimit(rate_ip=True, prefix="rate_register_"),
        signature=VSigned(),
        name=VUname(['user']),
        email=ValidEmail("email"),
        password=VPasswordChange(['passwd', 'passwd2']),
    )
    def POST_register(self, responder, name, email, password, **kwargs):
        kwargs.update(dict(
            controller=self,
            form=responder("noop"),
            responder=responder,
            name=name,
            email=email,
            password=password,
        ))
        return handle_register(**kwargs)

    @csrf_exempt
    @json_validate(
        signature=VSigned(),
        user=VThrottledLogin(['user', 'passwd']),
    )
    def POST_login(self, responder, user, **kwargs):
        kwargs.update(dict(
            controller=self,
            form=responder("noop"),
            responder=responder,
            user=user,
        ))
        return handle_login(**kwargs)

    def _login(self, responder, user, rem=None):
        """Login the user.

        AJAX login handler, used by both login and register to set the
        user cookie and send back a redirect.
        """
        c.user = user
        c.user_is_loggedin = True
        self.login(user, rem=rem)

        responder._send_data(modhash=generate_modhash())
        responder._send_data(cookie=user.make_cookie())
