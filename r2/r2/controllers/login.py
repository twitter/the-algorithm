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

from r2.lib.validator import VRatelimit
from r2.lib import amqp
from r2.lib import emailer
from r2.lib import hooks
from r2.lib import newsletter
from r2.lib.base import abort
from r2.lib.errors import errors, reddit_http_error

from r2.models.account import register, AccountExists


def handle_login(
    controller, form, responder, user, rem=None, signature=None, **kwargs
):
    def _event(error):
        g.events.login_event(
            'login_attempt',
            error_msg=error,
            user_name=request.urlvars.get('url_user'),
            remember_me=rem,
            signature=signature,
            request=request,
            context=c)

    if signature and not signature.is_valid():
        _event(error="SIGNATURE")
        abort(403)

    hook_error = hooks.get_hook("account.login").call_until_return(
        responder=responder,
        request=request,
        context=c,
    )
    # if any of the hooks returned an error, abort the login.  The
    # set_error in this case also needs to exist in the hook.
    if hook_error:
        _event(error=hook_error)
        return

    exempt_ua = (request.user_agent and
                 any(ua in request.user_agent for ua
                     in g.config.get('exempt_login_user_agents', ())))
    if (errors.LOGGED_IN, None) in c.errors:
        if user == c.user or exempt_ua:
            # Allow funky clients to re-login as the current user.
            c.errors.remove((errors.LOGGED_IN, None))
        else:
            _event(error='LOGGED_IN')
            abort(reddit_http_error(409, errors.LOGGED_IN))

    if responder.has_errors("ratelimit", errors.RATELIMIT):
        _event(error='RATELIMIT')

    elif responder.has_errors("passwd", errors.WRONG_PASSWORD):
        _event(error='WRONG_PASSWORD')

    else:
        controller._login(responder, user, rem)
        _event(error=None)


def handle_register(
    controller, form, responder, name, email,
    password, rem=None, newsletter_subscribe=False,
    sponsor=False, signature=None, **kwargs
):

    def _event(error):
        g.events.login_event(
            'register_attempt',
            error_msg=error,
            user_name=request.urlvars.get('url_user'),
            email=request.POST.get('email'),
            remember_me=rem,
            newsletter=newsletter_subscribe,
            signature=signature,
            request=request,
            context=c)

    if signature and not signature.is_valid():
        _event(error="SIGNATURE")
        abort(403)

    if responder.has_errors('user', errors.USERNAME_TOO_SHORT):
        _event(error='USERNAME_TOO_SHORT')

    elif responder.has_errors('user', errors.USERNAME_INVALID_CHARACTERS):
        _event(error='USERNAME_INVALID_CHARACTERS')

    elif responder.has_errors('user', errors.USERNAME_TAKEN_DEL):
        _event(error='USERNAME_TAKEN_DEL')

    elif responder.has_errors('user', errors.USERNAME_TAKEN):
        _event(error='USERNAME_TAKEN')

    elif responder.has_errors('email', errors.BAD_EMAIL):
        _event(error='BAD_EMAIL')

    elif responder.has_errors('passwd', errors.SHORT_PASSWORD):
        _event(error='SHORT_PASSWORD')

    elif responder.has_errors('passwd', errors.BAD_PASSWORD):
        # BAD_PASSWORD is set when SHORT_PASSWORD is set
        _event(error='BAD_PASSWORD')

    elif responder.has_errors('passwd2', errors.BAD_PASSWORD_MATCH):
        _event(error='BAD_PASSWORD_MATCH')

    elif responder.has_errors('ratelimit', errors.RATELIMIT):
        _event(error='RATELIMIT')

    elif (not g.disable_captcha and
            responder.has_errors('captcha', errors.BAD_CAPTCHA)):
        _event(error='BAD_CAPTCHA')

    elif newsletter_subscribe and not email:
        c.errors.add(errors.NEWSLETTER_NO_EMAIL, field="email")
        form.has_errors("email", errors.NEWSLETTER_NO_EMAIL)
        _event(error='NEWSLETTER_NO_EMAIL')

    elif sponsor and not email:
        c.errors.add(errors.SPONSOR_NO_EMAIL, field="email")
        form.has_errors("email", errors.SPONSOR_NO_EMAIL)
        _event(error='SPONSOR_NO_EMAIL')

    else:
        try:
            user = register(name, password, request.ip)
        except AccountExists:
            c.errors.add(errors.USERNAME_TAKEN, field="user")
            form.has_errors("user", errors.USERNAME_TAKEN)
            _event(error='USERNAME_TAKEN')
            return

        VRatelimit.ratelimit(rate_ip=True, prefix="rate_register_")

        # anything else we know (email, languages)?
        if email:
            user.set_email(email)
            emailer.verify_email(user)

        user.pref_lang = c.lang
        user._commit()

        amqp.add_item('new_account', user._fullname)

        hooks.get_hook("account.registered").call(user=user)

        reject = hooks.get_hook("account.spotcheck").call(account=user)
        if any(reject):
            _event(error='ACCOUNT_SPOTCHECK')
            return

        if newsletter_subscribe and email:
            try:
                newsletter.add_subscriber(email, source="register")
            except newsletter.NewsletterError as e:
                g.log.warning("Failed to subscribe: %r" % e)

        controller._login(responder, user, rem)
        _event(error=None)
