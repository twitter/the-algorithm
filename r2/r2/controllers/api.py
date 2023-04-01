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
import csv
from collections import defaultdict
import hashlib
import re
import urllib
import urllib2

from r2.controllers.reddit_base import (
    abort_with_error,
    cross_domain,
    generate_modhash,
    is_trusted_origin,
    MinimalController,
    paginated_listing,
    RedditController,
    set_user_cookie,
)

from pylons.i18n import _
from pylons import request, response
from pylons import tmpl_context as c
from pylons import app_globals as g

from r2.lib.validator import *

from r2.models import *

from r2.lib import amqp
from r2.lib import recommender
from r2.lib import hooks
from r2.lib.ratelimit import SimpleRateLimit

from r2.lib.utils import (
    blockquote_text,
    extract_user_mentions,
    get_title,
    query_string,
    randstr,
    sanitize_url,
    timefromnow,
    timeuntil,
    tup,
)

from r2.lib.pages import (
    BoringPage,
    ClickGadget,
    CssError,
    FormPage,
    Reddit,
    responsive,
    UploadedImage,
    UrlParser,
    WrappedUser,
)
from r2.lib.pages import FlairList, FlairCsv, FlairTemplateEditor, \
    FlairSelector
from r2.lib.pages import PrefApps
from r2.lib.pages import (
    BannedTableItem,
    ContributorTableItem,
    FriendTableItem,
    InvitedModTableItem,
    ModTableItem,
    MutedTableItem,
    ReportForm,
    SubredditReportForm,
    SubredditStylesheet,
    WikiBannedTableItem,
    WikiMayContributeTableItem,
)

from r2.lib.pages.things import (
    default_thing_wrapper,
    hot_links_by_url_listing,
    wrap_links,
)

from r2.lib.menus import CommentSortMenu
from r2.lib.captcha import get_iden
from r2.lib.strings import strings
from r2.lib.template_helpers import format_html, header_url
from r2.lib.filters import _force_unicode, _force_utf8, websafe_json, websafe, spaceCompress
from r2.lib.db import queries
from r2.lib import media
from r2.lib.db import tdb_cassandra
from r2.lib import promote
from r2.lib import tracking, emailer, newsletter
from r2.lib.subreddit_search import search_reddits
from r2.lib.filters import safemarkdown
from r2.lib.media import str_to_image
from r2.controllers.api_docs import api_doc, api_section
from r2.controllers.oauth2 import require_oauth2_scope, allow_oauth2_access
from r2.lib.template_helpers import (
    add_sr,
    get_domain,
    make_url_protocol_relative,
)
from r2.lib.system_messages import (
    notify_user_added,
    send_ban_message,
    send_mod_removal_message,
)
from r2.controllers.ipn import generate_blob, update_blob
from r2.controllers.login import handle_login, handle_register
from r2.lib.lock import TimeoutExpired
from r2.lib.csrf import csrf_exempt
from r2.lib.voting import cast_vote

from r2.models import wiki
from r2.models.ip import set_account_ip
from r2.models.recommend import AccountSRFeedback, FEEDBACK_ACTIONS
from r2.models.rules import SubredditRules
from r2.models.vote import Vote
from r2.lib.merge import ConflictException

from datetime import datetime, timedelta
from urlparse import urlparse


class ApiminimalController(MinimalController):
    """
    Put API calls in here which don't rely on the user being logged in
    """

    # Since this is only a MinimalController, the
    # @allow_oauth2_access decorator has little effect other than
    # (1) to add the endpoint to /dev/api/oauth, and
    # (2) to future-proof in case the function moves elsewhere
    @allow_oauth2_access
    @csrf_exempt
    @validatedForm()
    @api_doc(api_section.captcha)
    def POST_new_captcha(self, form, jquery, *a, **kw):
        """
        Responds with an `iden` of a new CAPTCHA.

        Use this endpoint if a user cannot read a given CAPTCHA,
        and wishes to receive a new CAPTCHA.

        To request the CAPTCHA image for an iden, use
        [/captcha/`iden`](#GET_captcha_{iden}).
        """

        iden = get_iden()
        jquery("body").captcha(iden)
        form._send_data(iden = iden) 


class ApiController(RedditController):
    """
    Controller which deals with almost all AJAX site interaction.  
    """
    @validatedForm()
    def ajax_login_redirect(self, form, jquery, dest):
        form.redirect("/login" + query_string(dict(dest=dest)))

    @require_oauth2_scope("read")
    @validate(
        things=VByName('id', multiple=True, ignore_missing=True, limit=100),
        url=VUrl('url'),
    )
    @api_doc(api_section.links_and_comments, uses_site=True)
    def GET_info(self, things, url):
        """
        Return a listing of things specified by their fullnames.

        Only Links, Comments, and Subreddits are allowed.

        """

        if url:
            return self.GET_url_info()

        thing_classes = (Link, Comment, Subreddit)
        things = things or []
        things = filter(lambda thing: isinstance(thing, thing_classes), things)

        c.update_last_visit = False
        listing = wrap_links(things)
        return BoringPage(_("API"), content=listing).render()

    @require_oauth2_scope("read")
    @validate(
        url=VUrl('url'),
        count=VLimit('limit'),
        things=VByName('id', multiple=True, limit=100),
    )
    def GET_url_info(self, url, count, things):
        """
        Return a list of links with the given URL.

        If a subreddit is provided, only links in that subreddit will be
        returned.

        """

        if things and not url:
            return self.GET_info()

        c.update_last_visit = False

        if url:
            listing = hot_links_by_url_listing(url, sr=c.site, num=count)
        else:
            listing = None
        return BoringPage(_("API"), content=listing).render()

    @json_validate()
    def GET_me(self, responder):
        """Get info about the currently authenticated user.

        Response includes a modhash, karma, and new mail status.

        """
        if c.user_is_loggedin:
            user_data = Wrapped(c.user).render()
            user_data['data'].update({'features': feature.all_enabled(c.user)})
            return user_data
        else:
            return {'data': {'features': feature.all_enabled(None)}}

    @json_validate(user=VUname(("user",)))
    @api_doc(api_section.users)
    def GET_username_available(self, responder, user):
        """
        Check whether a username is available for registration.
        """
        if not (responder.has_errors("user", errors.BAD_USERNAME)):
            return bool(user)

    @csrf_exempt
    @json_validate(user=VUname(("user",)))
    def POST_check_username(self, responder, user):
        """
        Check whether a username is valid.
        """

        if not (responder.has_errors("user",
                    errors.USERNAME_TOO_SHORT,
                    errors.USERNAME_INVALID_CHARACTERS,
                    errors.USERNAME_TAKEN_DEL,
                    errors.USERNAME_TAKEN)):
            # Pylons does not handle 204s correctly.
            return {}

    @csrf_exempt
    @json_validate(password=VPassword(("passwd")))
    def POST_check_password(self, responder, password):
        """
        Check whether a password is valid.
        """
    
        if not (responder.has_errors("passwd", errors.SHORT_PASSWORD) or
                responder.has_errors("passwd", errors.BAD_PASSWORD)):
            # Pylons does not handle 204s correctly.
            return {}

    @csrf_exempt
    @json_validate(email=ValidEmail("email"),
                   newsletter_subscribe=VBoolean("newsletter_subscribe", default=False),
                   sponsor=VBoolean("sponsor", default=False))
    def POST_check_email(self, responder, email, newsletter_subscribe, sponsor):
        """
        Check whether an email is valid. Allows blank emails.

        Additionally checks if a newsletter is requested, and will be strict
        on blank emails if so.
        """
        if newsletter_subscribe and not email:
            c.errors.add(errors.NEWSLETTER_NO_EMAIL, field="email")
            responder.has_errors("email", errors.NEWSLETTER_NO_EMAIL)
            return

        if sponsor and not email:
            c.errors.add(errors.SPONSOR_NO_EMAIL, field="email")
            responder.has_errors("email", errors.SPONSOR_NO_EMAIL)
            return

        if not (responder.has_errors("email", errors.BAD_EMAIL)):
            # Pylons does not handle 204s correctly.
            return {}

    @cross_domain(allow_credentials=True)
    @json_validate(
        VModhashIfLoggedIn(),
        VRatelimit(rate_ip=True, prefix="rate_newsletter_"),
        email=ValidEmail("email"),
        source=VOneOf('source', ['newsletterbar', 'standalone'])
    )
    def POST_newsletter(self, responder, email, source):
        """Add an email to our newsletter."""

        VRatelimit.ratelimit(rate_ip=True,
                             prefix="rate_newsletter_")

        try:
            newsletter.add_subscriber(email, source=source)
        except newsletter.EmailUnacceptableError as e:
            c.errors.add(errors.NEWSLETTER_EMAIL_UNACCEPTABLE, field="email")
            responder.has_errors("email", errors.NEWSLETTER_EMAIL_UNACCEPTABLE)
            return
        except newsletter.NewsletterError as e:
            g.log.warning("Failed to subscribe: %r" % e)
            abort(500)

    @allow_oauth2_access
    @json_validate()
    @api_doc(api_section.captcha)
    def GET_needs_captcha(self, responder):
        """
        Check whether CAPTCHAs are needed for API methods that define the
        "captcha" and "iden" parameters.
        """
        return bool(c.user.needs_captcha())

    @require_oauth2_scope("privatemessages")
    @validatedForm(
        VCaptcha(),
        VUser(),
        VModhash(),
        from_sr=VSRByName('from_sr', required=False),
        to=VMessageRecipient('to'),
        subject=VLength('subject', 100, empty_error=errors.NO_SUBJECT),
        body=VMarkdownLength(['text', 'message'], max_length=10000),
    )
    @api_doc(api_section.messages)
    def POST_compose(self, form, jquery, from_sr, to, subject, body):
        """
        Handles message composition under /message/compose.
        """
        if (form.has_errors("to",
                    errors.USER_DOESNT_EXIST, errors.NO_USER,
                    errors.SUBREDDIT_NOEXIST, errors.USER_BLOCKED,
                ) or
                form.has_errors("subject", errors.NO_SUBJECT) or
                form.has_errors("subject", errors.TOO_LONG) or
                form.has_errors("text", errors.NO_TEXT, errors.TOO_LONG) or
                form.has_errors("message", errors.TOO_LONG) or
                form.has_errors("captcha", errors.BAD_CAPTCHA) or
                form.has_errors("from_sr", errors.SUBREDDIT_NOEXIST)):
            return

        if form.has_errors("to", errors.USER_MUTED):
            g.events.muted_forbidden_event("muted", target=to,
                request=request, context=c)
            form.set_inputs(to="", subject="", text="", captcha="")
            return

        if from_sr and isinstance(to, Subreddit):
            c.errors.add(errors.NO_SR_TO_SR_MESSAGE, field="from")
            form.has_errors("from", errors.NO_SR_TO_SR_MESSAGE)
            return

        if from_sr and BlockedSubredditsByAccount.is_blocked(to, from_sr):
            c.errors.add(errors.USER_BLOCKED_MESSAGE, field="to")
            form.has_errors("to", errors.USER_BLOCKED_MESSAGE)
            return

        if from_sr and from_sr._spam:
            return

        if from_sr:
            if not from_sr.is_moderator_with_perms(c.user, "mail"):
                abort(403)
            elif from_sr.is_muted(to) and not c.user_is_admin:
                c.errors.add(errors.MUTED_FROM_SUBREDDIT, field="to")
                form.has_errors("to", errors.MUTED_FROM_SUBREDDIT)
                g.events.muted_forbidden_event("muted mod", subreddit=from_sr,
                    target=to, request=request, context=c)
                form.set_inputs(to="", subject="", text="", captcha="")
                return

            # Don't allow mods in timeout to send a message
            VNotInTimeout().run(target=to, subreddit=from_sr)
            m, inbox_rel = Message._new(c.user, to, subject, body, request.ip,
                                        sr=from_sr, from_sr=True)
        else:
            # Only let users in timeout message the admins
            if (to and not (isinstance(to, Subreddit) and
                    '/r/%s' % to.name == g.admin_message_acct)):
                VNotInTimeout().run(target=to)
            m, inbox_rel = Message._new(c.user, to, subject, body, request.ip)

        form.set_text(".status", _("your message has been delivered"))
        form.set_inputs(to = "", subject = "", text = "", captcha="")
        queries.new_message(m, inbox_rel)

    @require_oauth2_scope("submit")
    @json_validate()
    @api_doc(api_section.subreddits, uses_site=True)
    def GET_submit_text(self, responder):
        """Get the submission text for the subreddit.

        This text is set by the subreddit moderators and intended to be
        displayed on the submission form.

        See also: [/api/site_admin](#POST_api_site_admin).

        """
        if c.site.over_18 and not c.over18:
            submit_text = None
            submit_text_html = None
        else:
            submit_text = c.site.submit_text
            submit_text_html = safemarkdown(c.site.submit_text)
        return {'submit_text': submit_text,
                'submit_text_html': submit_text_html}

    @require_oauth2_scope("submit")
    @validatedForm(
        VUser(),
        VModhash(),
        VCaptcha(),
        VRatelimit(rate_user=True, rate_ip=True, prefix="rate_submit_"),
        VShamedDomain('url'),
        sr=VSubmitSR('sr', 'kind'),
        url=VUrl('url'),
        title=VTitle('title'),
        sendreplies=VBoolean('sendreplies'),
        selftext=VMarkdown('text'),
        kind=VOneOf('kind', ['link', 'self']),
        extension=VLength("extension", 20,
                          docs={"extension": "extension used for redirects"}),
        resubmit=VBoolean('resubmit'),
    )
    @api_doc(api_section.links_and_comments)
    def POST_submit(self, form, jquery, url, selftext, kind, title,
                    sr, extension, sendreplies, resubmit):
        """Submit a link to a subreddit.

        Submit will create a link or self-post in the subreddit `sr` with the
        title `title`. If `kind` is `"link"`, then `url` is expected to be a
        valid URL to link to. Otherwise, `text`, if present, will be the
        body of the self-post.

        If a link with the same URL has already been submitted to the specified
        subreddit an error will be returned unless `resubmit` is true.
        `extension` is used for determining which view-type (e.g. `json`,
        `compact` etc.) to use for the redirect that is generated if the
        `resubmit` error occurs.

        """

        from r2.models.admintools import is_banned_domain

        if url:
            if url.lower() == 'self':
                url = kind = 'self'

            # VUrl may have replaced 'url' by adding 'http://'
            form.set_inputs(url=url)

        is_self = (kind == "self")

        if not kind or form.has_errors('sr', errors.INVALID_OPTION):
            return

        if form.has_errors('captcha', errors.BAD_CAPTCHA):
            return

        if form.has_errors('sr',
                errors.SUBREDDIT_NOEXIST,
                errors.SUBREDDIT_NOTALLOWED,
                errors.SUBREDDIT_REQUIRED,
                errors.INVALID_OPTION,
                errors.NO_SELFS,
                errors.NO_LINKS,
                errors.IN_TIMEOUT,
        ):
            return

        if not sr.can_submit_text(c.user) and is_self:
            # this could happen if they actually typed "self" into the
            # URL box and we helpfully translated it for them
            c.errors.add(errors.NO_SELFS, field='sr')
            form.has_errors('sr', errors.NO_SELFS)
            return

        if form.has_errors("title", errors.NO_TEXT, errors.TOO_LONG):
            return

        if not sr.should_ratelimit(c.user, 'link'):
            c.errors.remove((errors.RATELIMIT, 'ratelimit'))
        else:
            if form.has_errors('ratelimit', errors.RATELIMIT):
                return

        if not is_self:
            if form.has_errors("url", errors.NO_URL, errors.BAD_URL):
                return

            if form.has_errors("url", errors.DOMAIN_BANNED):
                g.stats.simple_event('spam.shame.link')
                return

            if not resubmit:
                listing = hot_links_by_url_listing(url, sr=sr, num=1)
                links = listing.things
                if links:
                    c.errors.add(errors.ALREADY_SUB, field='url')
                    form.has_errors('url', errors.ALREADY_SUB)
                    u = links[0].already_submitted_link(url, title)
                    if extension:
                        u = UrlParser(u)
                        u.set_extension(extension)
                        u = u.unparse()
                    form.redirect(u)
                    return

        if not c.user_is_admin and is_self:
            if len(selftext) > Link.SELFTEXT_MAX_LENGTH:
                c.errors.add(errors.TOO_LONG, field='text',
                    msg_params={'max_length': Link.SELFTEXT_MAX_LENGTH})
                form.set_error(errors.TOO_LONG, 'text')
                return

        VNotInTimeout().run(action_name="submit", details_text=kind, target=sr)

        if not request.POST.get('sendreplies'):
            sendreplies = is_self

        # get rid of extraneous whitespace in the title
        cleaned_title = re.sub(r'\s+', ' ', title, flags=re.UNICODE)
        cleaned_title = cleaned_title.strip()

        l = Link._submit(
            is_self=is_self,
            title=cleaned_title,
            content=selftext if is_self else url,
            author=c.user,
            sr=sr,
            ip=request.ip,
            sendreplies=sendreplies,
        )

        if not is_self:
            ban = is_banned_domain(url)
            if ban:
                g.stats.simple_event('spam.domainban.link_url')
                admintools.spam(l, banner = "domain (%s)" % ban.banmsg)
                hooks.get_hook('banned_domain.submit').call(item=l, url=url,
                                                            ban=ban)

        if sr.should_ratelimit(c.user, 'link'):
            VRatelimit.ratelimit(rate_user=True, rate_ip = True,
                                 prefix = "rate_submit_")

        queries.new_link(l)
        l.update_search_index()
        g.events.submit_event(l, request=request, context=c)

        path = add_sr(l.make_permalink_slow())
        if extension:
            path += ".%s" % extension

        form.redirect(path)
        form._send_data(url=path)
        form._send_data(id=l._id36)
        form._send_data(name=l._fullname)

    @csrf_exempt
    @validatedForm(VRatelimit(rate_ip = True,
                              rate_user = True,
                              prefix = 'fetchtitle_'),
                   VUser(),
                   url = VSanitizedUrl('url'))
    def POST_fetch_title(self, form, jquery, url):
        if form.has_errors('ratelimit', errors.RATELIMIT):
            form.set_text(".title-status", "")
            return

        VRatelimit.ratelimit(rate_ip = True, rate_user = True,
                             prefix = 'fetchtitle_', seconds=1)
        if url:
            title = get_title(url)
            if title:
                form.set_inputs(title = title)
                form.set_text(".title-status", "")
            else:
                form.set_text(".title-status", _("no title found"))
            form._send_data(title=title)
        
    def _login(self, responder, user, rem = None):
        """
        AJAX login handler, used by both login and register to set the
        user cookie and send back a redirect.
        """
        c.user = user
        c.user_is_loggedin = True
        self.login(user, rem = rem)

        if request.params.get("hoist") != "cookie":
            responder._send_data(modhash=generate_modhash())
            responder._send_data(cookie  = user.make_cookie())
        responder._send_data(need_https=feature.is_enabled("force_https"))

    @csrf_exempt
    @cross_domain(allow_credentials=True)
    @validatedForm(
        VLoggedOut(),
        user=VThrottledLogin(['user', 'passwd']),
        rem=VBoolean('rem'),
    )
    def POST_login(self, form, responder, user, rem=None, **kwargs):
        """Log into an account.

        `rem` specifies whether or not the session cookie returned should last
        beyond the current browser session (that is, if `rem` is `True` the
        cookie will have an explicit expiration far in the future indicating
        that it is not a session cookie).

        """
        kwargs.update(dict(
            controller=self,
            form=form,
            responder=responder,
            user=user,
            rem=rem,
        ))
        return handle_login(**kwargs)

    @csrf_exempt
    @cross_domain(allow_credentials=True)
    @validatedForm(
        VRatelimit(rate_ip=True, prefix="rate_register_"),
        name=VUname(['user']),
        email=ValidEmail("email"),
        password=VPasswordChange(['passwd', 'passwd2']),
        rem=VBoolean('rem'),
        newsletter_subscribe=VBoolean('newsletter_subscribe', default=False),
        sponsor=VBoolean('sponsor', default=False),
    )
    def POST_register(self, form, responder, name, email, password, **kwargs):
        """Create a new account.

        `rem` specifies whether or not the session cookie returned should last
        beyond the current browser session (that is, if `rem` is `True` the
        cookie will have an explicit expiration far in the future indicating
        that it is not a session cookie).

        """
        kwargs.update(dict(
            controller=self,
            form=form,
            responder=responder,
            name=name,
            email=email,
            password=password,
        ))
        return handle_register(**kwargs)

    @require_oauth2_scope("modself")
    @noresponse(VUser(),
                VModhash(),
                container = VByName('id'))
    @api_doc(api_section.moderation)
    def POST_leavemoderator(self, container):
        """Abdicate moderator status in a subreddit.

        See also: [/api/friend](#POST_api_friend).

        """
        if container and container.is_moderator(c.user):
            container.remove_moderator(c.user)
            ModAction.create(container, c.user, 'removemoderator', target=c.user, 
                             details='remove_self')

    @require_oauth2_scope("modself")
    @noresponse(VUser(),
                VModhash(),
                container = VByName('id'))
    @api_doc(api_section.moderation)
    def POST_leavecontributor(self, container):
        """Abdicate approved submitter status in a subreddit.

        See also: [/api/friend](#POST_api_friend).

        """
        if container and container.is_contributor(c.user):
            container.remove_contributor(c.user)


    _sr_friend_types = (
        'moderator',
        'moderator_invite',
        'contributor',
        'banned',
        'muted',
        'wikibanned',
        'wikicontributor',
    )

    _sr_friend_types_with_permissions = (
        'moderator',
        'moderator_invite',
    )

    # Changes to this dict should also update docstrings for
    # POST_friend and POST_unfriend
    api_friend_scope_map = {
        'moderator': {"modothers"},
        'moderator_invite': {"modothers"},
        'contributor': {"modcontributors"},
        'banned': {"modcontributors"},
        'muted': {"modcontributors"},
        'wikibanned': {"modcontributors", "modwiki"},
        'wikicontributor': {"modcontributors", "modwiki"},
        'friend': None,  # Handled with API v1 endpoint
        'enemy': {"privatemessages"},  # Only valid for POST_unfriend
    }

    def check_api_friend_oauth_scope(self, type_):
        if c.oauth_user:
            needed_scopes = self.api_friend_scope_map[type_]
            if needed_scopes is None:
                # OAuth2 access not allowed for this friend rel type
                # via /api/friend
                self._auth_error(400, "invalid_request")
            if not c.oauth_scope.has_access(c.site.name, needed_scopes):
                # Token does not have the necessary scope to complete
                # this request.
                self._auth_error(403, "insufficient_scope")

    @allow_oauth2_access
    @noresponse(VUser(),
                VModhash(),
                nuser = VExistingUname('name'),
                iuser = VByName('id'),
                container = nop('container'),
                type = VOneOf('type', ('friend', 'enemy') +
                                      _sr_friend_types))
    @api_doc(api_section.users, uses_site=True)
    def POST_unfriend(self, nuser, iuser, container, type):
        """Remove a relationship between a user and another user or subreddit

        The user can either be passed in by name (nuser)
        or by [fullname](#fullnames) (iuser).  If type is friend or enemy,
        'container' MUST be the current user's fullname;
        for other types, the subreddit must be set
        via URL (e.g., /r/funny/api/unfriend)

        OAuth2 use requires appropriate scope based
        on the 'type' of the relationship:

        * moderator: `modothers`
        * moderator_invite: `modothers`
        * contributor: `modcontributors`
        * banned: `modcontributors`
        * muted: `modcontributors`
        * wikibanned: `modcontributors` and `modwiki`
        * wikicontributor: `modcontributors` and `modwiki`
        * friend: Use [/api/v1/me/friends/{username}](#DELETE_api_v1_me_friends_{username})
        * enemy: `privatemessages`

        Complement to [POST_friend](#POST_api_friend)

        """
        self.check_api_friend_oauth_scope(type)

        victim = iuser or nuser

        if not victim:
            abort(400, 'No user specified')
        
        if type in self._sr_friend_types:
            mod_action_by_type = dict(
                banned='unbanuser',
                moderator='removemoderator',
                moderator_invite='uninvitemoderator',
                wikicontributor='removewikicontributor',
                wikibanned='wikiunbanned',
                contributor='removecontributor',
                muted='unmuteuser',
            )
            action = mod_action_by_type.get(type, type)

            if isinstance(c.site, FakeSubreddit):
                abort(403, 'forbidden')
            container = c.site
            if not (c.user == victim and type == 'moderator'):
                # The requesting user is marked as spam or banned, and is
                # trying to do a mod action. The only action they should be
                # allowed to do and have it stick is demodding themself.
                if c.user._spam:
                    return
                VNotInTimeout().run(action_name=action, target=victim)
        else:
            container = VByName('container').run(container)
            if not container:
                return

        # The user who made the request must be an admin or a moderator
        # for the privilege change to succeed.
        # (Exception: a user can remove privilege from oneself)
        required_perms = []
        if c.user != victim:
            if type.startswith('wiki'):
                required_perms.append('wiki')
            else:
                required_perms.append('access')
                # ability to unmute requires access and mail permissions
                if type == 'muted':
                    required_perms.append('mail')

        if (
            not c.user_is_admin and
            type in self._sr_friend_types and
            not container.is_moderator_with_perms(c.user, *required_perms)
        ):
            abort(403, 'forbidden')
        if (
            type == "moderator" and
            not c.user_is_admin and
            not container.can_demod(c.user, victim)
        ):
            abort(403, 'forbidden')

        # if we are (strictly) unfriending, the container had better
        # be the current user.
        if type in ("friend", "enemy") and container != c.user:
            abort(403, 'forbidden')

        fn = getattr(container, 'remove_' + type)
        new = fn(victim)

        # for mod removals, let the now ex-mod know (NOTE: doing this earlier
        # will make the message show up in their mod inbox, which they will
        # immediately lose access to.)
        if new and type == 'moderator' and victim != c.user:
            send_mod_removal_message(container, c.user, victim)

        # Log this action
        if new and type in self._sr_friend_types:
            ModAction.create(container, c.user, action, target=victim)

        if type == "friend" and c.user.gold:
            c.user.friend_rels_cache(_update=True)

        if type in ('banned', 'wikibanned'):
            container.unschedule_unban(victim, type)

        if type == 'muted':
            MutedAccountsBySubreddit.unmute(container, victim)

    @require_oauth2_scope("modothers")
    @validatedForm(VSrModerator(), VModhash(),
                   target=VExistingUname('name'),
                   type_and_permissions=VPermissions('type', 'permissions'))
    @api_doc(api_section.users, uses_site=True)
    def POST_setpermissions(self, form, jquery, target, type_and_permissions):
        if form.has_errors('name', errors.USER_DOESNT_EXIST, errors.NO_USER):
            return
        if form.has_errors('type', errors.INVALID_PERMISSION_TYPE):
            return
        if form.has_errors('permissions', errors.INVALID_PERMISSIONS):
            return

        if c.user._spam:
            return

        type, permissions = type_and_permissions
        update = None

        if type in ("moderator", "moderator_invite"):
            if not c.user_is_admin:
                if type == "moderator" and (
                    c.user == target or not c.site.can_demod(c.user, target)):
                    abort(403, 'forbidden')
                if (type == "moderator_invite"
                    and not c.site.is_unlimited_moderator(c.user)):
                    abort(403, 'forbidden')
                # Don't allow mods in timeout to set permissions
                VNotInTimeout().run(action_name="editsettings",
                    details_text="set_permissions", target=target)
            if type == "moderator":
                rel = c.site.get_moderator(target)
            if type == "moderator_invite":
                rel = c.site.get_moderator_invite(target)
            rel.set_permissions(permissions)
            rel._commit()
            update = rel.encoded_permissions
            ModAction.create(c.site, c.user, action='setpermissions',
                             target=target, details='permission_' + type,
                             description=update)

        if update:
            row = form.closest('tr')
            editor = row.find('.permissions').data('PermissionEditor')
            editor.onCommit(update)

    @allow_oauth2_access
    @validatedForm(
        VUser(),
        VModhash(),
        friend=VExistingUname('name'),
        container=nop('container'),
        type=VOneOf('type', ('friend',) + _sr_friend_types),
        type_and_permissions=VPermissions('type', 'permissions'),
        note=VLength('note', 300),
        ban_reason=VLength('ban_reason', 100),
        duration=VInt('duration', min=1, max=999),
        ban_message=VMarkdownLength('ban_message', max_length=1000,
            empty_error=None),
    )
    @api_doc(api_section.users, uses_site=True)
    def POST_friend(self, form, jquery, friend,
            container, type, type_and_permissions, note, ban_reason,
            duration, ban_message):
        """Create a relationship between a user and another user or subreddit

        OAuth2 use requires appropriate scope based
        on the 'type' of the relationship:

        * moderator: Use "moderator_invite"
        * moderator_invite: `modothers`
        * contributor: `modcontributors`
        * banned: `modcontributors`
        * muted: `modcontributors`
        * wikibanned: `modcontributors` and `modwiki`
        * wikicontributor: `modcontributors` and `modwiki`
        * friend: Use [/api/v1/me/friends/{username}](#PUT_api_v1_me_friends_{username})
        * enemy: Use [/api/block](#POST_api_block)

        Complement to [POST_unfriend](#POST_api_unfriend)

        """
        self.check_api_friend_oauth_scope(type)

        if type in self._sr_friend_types:
            if isinstance(c.site, FakeSubreddit):
                abort(403, 'forbidden')
            container = c.site
        else:
            container = VByName('container').run(container)
            if not container:
                return

        if type == "moderator" and not c.user_is_admin:
            # attempts to add moderators now create moderator invites.
            type = "moderator_invite"

        fn = getattr(container, 'add_' + type)

        # Make sure the user making the request has the correct permissions
        # to be able to make this status change
        if type in self._sr_friend_types:
            mod_action_by_type = {
                "banned": "banuser",
                "muted": "muteuser",
                "contributor": "addcontributor",
                "moderator": "addmoderator",
                "moderator_invite": "invitemoderator",
            }
            action = mod_action_by_type.get(type, type)

            if c.user_is_admin:
                has_perms = True
            elif type.startswith('wiki'):
                has_perms = container.is_moderator_with_perms(c.user, 'wiki')
            elif type == 'moderator_invite':
                has_perms = container.is_unlimited_moderator(c.user)
            else:
                has_perms = container.is_moderator_with_perms(c.user, 'access')

            if not has_perms:
                abort(403, 'forbidden')

            # Don't let banned users make subreddit access changes
            if c.user._spam:
                return
            VNotInTimeout().run(action_name=action, target=friend)

        if type == 'moderator_invite':
            invites = sum(1 for i in container.each_moderator_invite())
            if invites >= g.sr_invite_limit:
                c.errors.add(errors.SUBREDDIT_RATELIMIT, field="name")
                form.set_error(errors.SUBREDDIT_RATELIMIT, "name")
                return

        if (type in self._sr_friend_types and
                not c.user_is_admin and
                container.use_quotas):
            sr_ratelimit = SimpleRateLimit(
                name="sr_%s_%s" % (str(type), container._id36),
                seconds=g.sr_quota_time,
                limit=getattr(g, "sr_%s_quota" % type),
            )
            if not sr_ratelimit.record_and_check():
                form.set_text(".status", errors.SUBREDDIT_RATELIMIT)
                c.errors.add(errors.SUBREDDIT_RATELIMIT)
                form.set_error(errors.SUBREDDIT_RATELIMIT, None)
                return

        # if we are (strictly) friending, the container
        # had better be the current user.
        if type == "friend" and container != c.user:
            abort(403,'forbidden')

        elif form.has_errors("name", errors.USER_DOESNT_EXIST, errors.NO_USER):
            return
        elif form.has_errors(("note", "ban_reason"), errors.TOO_LONG):
            return

        if type == "banned":
            if form.has_errors("ban_message", errors.TOO_LONG):
                return
            if ban_reason and note:
                note = "%s: %s" % (ban_reason, note)
            elif ban_reason:
                note = ban_reason

        if type in self._sr_friend_types_with_permissions:
            if form.has_errors('type', errors.INVALID_PERMISSION_TYPE):
                return
            if form.has_errors('permissions', errors.INVALID_PERMISSIONS):
                return
        else:
            permissions = None

        if type == "moderator_invite" and container.is_moderator(friend):
            c.errors.add(errors.ALREADY_MODERATOR, field="name")
            form.set_error(errors.ALREADY_MODERATOR, "name")
            return
        elif type in ("banned", "muted") and container.is_moderator(friend):
            c.errors.add(errors.CANT_RESTRICT_MODERATOR, field="name")
            form.set_error(errors.CANT_RESTRICT_MODERATOR, "name")
            return

        if type == "muted" and not container.can_mute(c.user, friend):
            abort(403)

        # don't allow increasing privileges of banned or muted users
        unbanned_types = ("moderator", "moderator_invite",
                          "contributor", "wikicontributor")
        if type in unbanned_types:
            if container.is_banned(friend):
                c.errors.add(errors.BANNED_FROM_SUBREDDIT, field="name")
                form.set_error(errors.BANNED_FROM_SUBREDDIT, "name")
                return
            elif container.is_muted(friend):
                c.errors.add(errors.MUTED_FROM_SUBREDDIT, field="name")
                form.set_error(errors.MUTED_FROM_SUBREDDIT, "name")
                return

        if type == "moderator":
            container.remove_moderator_invite(friend)

        new = fn(friend, permissions=type_and_permissions[1])

        if type == "friend" and c.user.gold:
            # Yes, the order of the next two lines is correct.
            # First you recalculate the rel_ids, then you find
            # the right one and update its data.
            c.user.friend_rels_cache(_update=True)
            c.user.add_friend_note(friend, note or '')

        # additional logging/info needed for bans
        tempinfo = None
        log_details = None
        log_description = None

        if type in ('banned', 'wikibanned', 'muted'):
            container.add_rel_note(type, friend, note)
            log_description = note

        if type in ('banned', 'wikibanned'):
            existing_ban = None
            if not new:
                existing_ban = container.get_tempbans(type, friend.name)
            if duration:
                ban_buffer = timedelta(hours=6)
                # Temp ban that doesn't have a preceding temp ban that ends
                # ends within ban_buffer of this new duration. this is just a
                # small buffer to prevent repetitive ban messages sent to users
                # when the mod wants to update a note but not the duration
                if existing_ban:
                    now = datetime.now(g.tz)
                    ban_remaining = existing_ban[friend.name] - now
                    timediff = abs(timedelta(days=duration) - ban_remaining)
                if not existing_ban or timediff >= ban_buffer:
                    container.unschedule_unban(friend, type)
                    tempinfo = container.schedule_unban(
                        type,
                        friend,
                        c.user,
                        duration,
                    )
                    log_details = "changed to " if not new else ""
                    log_details += "%d days" % duration
            elif not new and existing_ban:
                # Preexisting temp ban and no duration specified means turn
                # the temporary ban into a permanent one.
                container.unschedule_unban(friend, type)
                log_details = "changed to permanent"
            elif new:
                # New ban without a duration is permanent
                log_details = "permanent"
        elif new and type == 'muted':
            MutedAccountsBySubreddit.mute(container, friend, c.user)

        # Log this action
        if (new or log_details) and type in self._sr_friend_types:
            mod_action_by_type = {
                "banned": "banuser",
                "muted": "muteuser",
                "contributor": "addcontributor",
                "moderator": "addmoderator",
                "moderator_invite": "invitemoderator",
            }
            action = mod_action_by_type.get(type, type)

            ModAction.create(
                container,
                c.user,
                action,
                target=friend,
                details=log_details,
                description=log_description,
            )

        row_cls = dict(friend=FriendTableItem,
                       moderator=ModTableItem,
                       moderator_invite=InvitedModTableItem,
                       contributor=ContributorTableItem,
                       wikicontributor=WikiMayContributeTableItem,
                       banned=BannedTableItem,
                       muted=MutedTableItem,
                       wikibanned=WikiBannedTableItem).get(type)

        form.set_inputs(name = "")
        if note:
            form.set_inputs(note = "")
        form.removeClass("edited")

        if new and row_cls:
            new._thing2 = friend
            user_row = row_cls(new)
            if tempinfo:
                BannedListing.populate_from_tempbans(user_row, tempinfo)
            form.set_text(".status:first", user_row.executed_message)
            rev_types = ["moderator", "moderator_invite", "friend"]
            index = 0 if user_row.type not in rev_types else -1
            table = jquery("." + type + "-table").show().find("table")
            table.insert_table_rows(user_row, index=index)
            table.find(".notfound").hide()

        if type == "banned":
            # If the ban is new or has had the duration changed,
            # send a ban message
            if (friend.has_interacted_with(container) and 
                    (new or log_details)):
                send_ban_message(container, c.user, friend,
                    ban_message, duration, new)
        elif new:
            notify_user_added(type, c.user, friend, container)

    @validatedForm(VGold(),
                   VModhash(),
                   friend = VExistingUname('name'),
                   note = VLength('note', 300))
    def POST_friendnote(self, form, jquery, friend, note):
        if form.has_errors("note", errors.TOO_LONG):
            return
        c.user.add_friend_note(friend, note)
        form.set_text('.status', _("saved"))

    @validatedForm(
        VModhash(),
        type=VOneOf('type', ('bannednote', 'wikibannednote', 'mutednote')),
        user=VExistingUname('name'),
        note=VLength('note', 300),
    )
    def POST_relnote(self, form, jquery, type, user, note):
        perm = 'wiki' if type.startswith('wiki') else 'access'
        if (not c.user_is_admin
            and (not c.site.is_moderator_with_perms(c.user, perm))):
            if c.user._spam:
                return
            else:
                abort(403, 'forbidden')

        # Don't allow users in timeout to add relnote
        VNotInTimeout().run(action_name="editrelnote", details_text=type,
            target=user)

        if form.has_errors("note", errors.TOO_LONG):
            # NOTE: there's no error displayed in the form
            return
        c.site.add_rel_note(type[:-4], user, note)

    @require_oauth2_scope("modself")
    @validatedForm(VUser(),
                   VModhash())
    @api_doc(api_section.moderation, uses_site=True)
    def POST_accept_moderator_invite(self, form, jquery):
        """Accept an invite to moderate the specified subreddit.

        The authenticated user must have been invited to moderate the subreddit
        by one of its current moderators.

        See also: [/api/friend](#POST_api_friend) and
        [/subreddits/mine](#GET_subreddits_mine_{where}).

        """

        rel = c.site.get_moderator_invite(c.user)
        if not c.site.remove_moderator_invite(c.user):
            c.errors.add(errors.NO_INVITE_FOUND)
            form.set_error(errors.NO_INVITE_FOUND, None)
            return

        permissions = rel.get_permissions()
        ModAction.create(c.site, c.user, "acceptmoderatorinvite")
        c.site.add_moderator(c.user, permissions=rel.get_permissions())
        notify_user_added("accept_moderator_invite", c.user, c.user, c.site)
        jquery.refresh()

    @validatedForm(
        VUser(),
        VModhash(),
        password=VVerifyPassword("curpass", fatal=False),
        dest=VDestination(),
    )
    def POST_clear_sessions(self, form, jquery, password, dest):
        """Clear all session cookies and replace the current one.

        A valid password (`curpass`) must be supplied.

        """
        # password is required to proceed
        if form.has_errors("curpass", errors.WRONG_PASSWORD):
            return

        form.set_text('.status',
                      _('all other sessions have been logged out'))
        form.set_inputs(curpass = "")

        # deauthorize all access tokens
        OAuth2AccessToken.revoke_all_by_user(c.user)
        OAuth2RefreshToken.revoke_all_by_user(c.user)

    def revoke_sessions_and_login(self, user, password):
        self.revoke_sessions(user)

        # run the change password command to get a new salt
        change_password(c.user, password)
        # the password salt has changed, so the user's cookie has been
        # invalidated.  drop a new cookie.
        self.login(c.user)

    def revoke_sessions(self, user):
        # deauthorize all access tokens
        OAuth2AccessToken.revoke_all_by_user(user)
        OAuth2RefreshToken.revoke_all_by_user(user)

    @validatedForm(
        VUser(),
        VModhash(),
        VVerifyPassword("curpass", fatal=False),
        email=ValidEmails("email", num=1),
        verify=VBoolean("verify"),
        dest=VDestination(),
    )
    def POST_update_email(self, form, jquery, email, verify, dest):
        """Update account email address.

        Called by /prefs/update on the site.

        """

        if form.has_errors("curpass", errors.WRONG_PASSWORD):
            return

        if not form.has_errors("email", errors.BAD_EMAILS) and email:
            if (not hasattr(c.user, 'email') or c.user.email != email):
                if c.user.email:
                    emailer.email_change_email(c.user)

                c.user.set_email(email)
                c.user.email_verified = None
                c.user._commit()
                Award.take_away("verified_email", c.user)

            if verify:
                if dest == '/':
                    dest = None

                emailer.verify_email(c.user, dest=dest)
                form.set_inputs(curpass="")
                form.set_text('.status',
                     _("you should be getting a verification email shortly."))
            else:
                form.set_text('.status', _('your email has been updated'))

        # user is removing their email
        if (not email and c.user.email and 
            (errors.NO_EMAILS, 'email') in c.errors):
            c.errors.remove((errors.NO_EMAILS, 'email'))
            if c.user.email:
                emailer.email_change_email(c.user)
            c.user.set_email('')
            c.user.email_verified = None
            c.user.pref_email_messages = False
            c.user._commit()
            Award.take_away("verified_email", c.user)
            form.set_text('.status', _('your email has been updated'))

    @validatedForm(
        VUser(),
        VModhash(),
        VVerifyPassword("curpass", fatal=False),
        password=VPasswordChange(['newpass', 'verpass']),
        invalidate_oauth=VBoolean("invalidate_oauth"),
    )
    def POST_update_password(self, form, jquery, password, invalidate_oauth):
        """Update account password.

        Called by /prefs/update on the site. For frontend form verification
        purposes, `newpass` and `verpass` must be equal for a password change
        to succeed.

        """

        if form.has_errors("curpass", errors.WRONG_PASSWORD):
            return

        if (password and
            not (form.has_errors("newpass", errors.BAD_PASSWORD) or
                 form.has_errors("verpass", errors.BAD_PASSWORD_MATCH))):
            if invalidate_oauth:
                self.revoke_sessions(c.user)

            change_password(c.user, password)

            if c.user.email:
                emailer.password_change_email(c.user)

            form.set_text('.status', _('your password has been updated'))
            form.set_inputs(curpass="", newpass="", verpass="")

            # the password has changed, so the user's cookie has been
            # invalidated.  drop a new cookie.
            self.login(c.user)

    @validatedForm(VUser(),
                   VModhash(),
                   deactivate_message = VLength("deactivate_message", max_length=500),
                   username = VRequired("user", errors.NOT_USER),
                   user = VThrottledLogin(["user", "passwd"]),
                   confirm = VBoolean("confirm"))
    def POST_deactivate_user(self, form, jquery, deactivate_message, username, user, confirm):
        """Deactivate the currently logged in account.

        A valid username/password and confirmation must be supplied. An
        optional `deactivate_message` may be supplied to explain the reason the
        account is to be deleted.

        Called by /prefs/deactivate on the site.

        """
        if username and username.lower() != c.user.name.lower():
            c.errors.add(errors.NOT_USER, field="user")

        if not confirm:
            c.errors.add(errors.CONFIRM, field="confirm")

        if not (form.has_errors('ratelimit', errors.RATELIMIT) or
                form.has_errors("user", errors.NOT_USER) or
                form.has_errors("passwd", errors.WRONG_PASSWORD) or
                form.has_errors("deactivate_message", errors.TOO_LONG) or
                form.has_errors("confirm", errors.CONFIRM)):
            redirect_url = "/?deactivated=true"
            c.user.delete(deactivate_message)
            form.redirect(redirect_url)

    @require_oauth2_scope("edit")
    @noresponse(VUser(),
                VModhash(),
                thing = VByNameIfAuthor('id'))
    @api_doc(api_section.links_and_comments)
    def POST_del(self, thing):
        """Delete a Link or Comment."""
        if not thing: return
        was_deleted = thing._deleted
        thing._deleted = True
        if (getattr(thing, "promoted", None) is not None and
            not promote.is_promoted(thing)):
            promote.reject_promotion(thing)
        thing._commit()

        thing.update_search_index()

        if isinstance(thing, Link):
            amqp.add_item("deleted_link", thing._fullname)
            queries.delete(thing)
            thing.subreddit_slow.remove_sticky(thing)
            if thing.preview_object:
                thing.set_preview_object(None)
                thing._commit()
        elif isinstance(thing, Comment):
            link = thing.link_slow

            if not was_deleted:
                # get lock before writing to avoid multiple decrements when
                # there are simultaneous duplicate requests
                lock_key = "lock:del_{link}_{comment}".format(
                    link=link._id36,
                    comment=thing._id36,
                )
                if g.lock_cache.add(lock_key, "", time=60):
                    link._incr('num_comments', -1)

            link.remove_sticky_comment(comment=thing, set_by=c.user)

            queries.new_comment(thing, None)  # possible inbox_rels are
                                              # handled by unnotify
            queries.unnotify(thing)
            amqp.add_item("deleted_comment", thing._fullname)
            queries.delete(thing)

    @require_oauth2_scope("modposts")
    @noresponse(VUser(),
                VModhash(),
                VSrCanBan('id'),
                thing=VByName('id', thing_cls=Link))
    @api_doc(api_section.links_and_comments)
    def POST_lock(self, thing):
        """Lock a link.

        Prevents a post from receiving new comments.

        See also: [/api/unlock](#POST_api_unlock).

        """
        if thing.archived_slow:
            return abort(400, "Bad Request")
        VNotInTimeout().run(action_name="lock", target=thing)
        thing.locked = True
        thing._commit()

        ModAction.create(thing.subreddit_slow, c.user, target=thing,
                         action='lock')

    @require_oauth2_scope("modposts")
    @noresponse(VUser(),
                VModhash(),
                VSrCanBan('id'),
                thing=VByName('id', thing_cls=Link))
    @api_doc(api_section.links_and_comments)
    def POST_unlock(self, thing):
        """Unlock a link.

        Allow a post to receive new comments.

        See also: [/api/lock](#POST_api_lock).

        """
        if thing.archived_slow:
            return abort(400, "Bad Request")
        VNotInTimeout().run(action_name="unlock", target=thing)
        thing.locked = False
        thing._commit()

        ModAction.create(thing.subreddit_slow, c.user, target=thing,
                         action='unlock')

    @require_oauth2_scope("modposts")
    @noresponse(VUser(),
                VModhash(),
                VSrCanAlter('id'),
                thing = VByName('id'))
    @api_doc(api_section.links_and_comments)
    def POST_marknsfw(self, thing):
        """Mark a link NSFW.

        See also: [/api/unmarknsfw](#POST_api_unmarknsfw).

        """
        thing.over_18 = True
        thing._commit()

        if c.user._id != thing.author_id:
            ModAction.create(thing.subreddit_slow, c.user, target=thing,
                             action='marknsfw')

        thing.update_search_index()

    @require_oauth2_scope("modposts")
    @noresponse(VUser(),
                VModhash(),
                VSrCanAlter('id'),
                thing = VByName('id'))
    @api_doc(api_section.links_and_comments)
    def POST_unmarknsfw(self, thing):
        """Remove the NSFW marking from a link.

        See also: [/api/marknsfw](#POST_api_marknsfw).

        """

        if promote.is_promo(thing):
            if c.user_is_sponsor:
                # set the override attribute so this link won't be automatically
                # reset as nsfw by promote.make_daily_promotions
                thing.over_18_override = True
            else:
                abort(403,'forbidden')

        thing.over_18 = False
        thing._commit()

        if c.user._id != thing.author_id:
            ModAction.create(thing.subreddit_slow, c.user, target=thing,
                             action='marknsfw', details='remove')

        thing.update_search_index()

    @require_oauth2_scope("edit")
    @noresponse(VUser(),
                VModhash(),
                thing=VByNameIfAuthor('id'),
                state=VBoolean('state'))
    @api_doc(api_section.links_and_comments)
    def POST_sendreplies(self, thing, state):
        """Enable or disable inbox replies for a link or comment.

        `state` is a boolean that indicates whether you are enabling or
        disabling inbox replies - true to enable, false to disable.

        """
        if not isinstance(thing, (Link, Comment)):
            return

        thing.sendreplies = state
        thing._commit()

    @noresponse(VUser(),
                VModhash(),
                VSrCanAlter('id'),
                thing=VByName('id'))
    def POST_rescrape(self, thing):
        """Re-queues the link in the media scraper."""
        if not isinstance(thing, Link):
            return

        # KLUDGE: changing the cache entry to a placeholder for this URL will
        # cause the media scraper to force a rescrape.  This will be fixed
        # when parameters can be passed to the scraper queue.
        media_cache.MediaByURL.add_placeholder(thing.url, autoplay=False)

        amqp.add_item("scraper_q", thing._fullname)

    @require_oauth2_scope("modposts")
    @validatedForm(
        VUser(),
        VModhash(),
        VSrCanBan("id"),
        thing=VByName("id", thing_cls=Link),
        sort=VOneOf("sort", CommentSortMenu.suggested_sort_options),
        timeout=VNotInTimeout("id"),
    )
    @api_doc(api_section.links_and_comments)
    def POST_set_suggested_sort(self, form, jquery, thing, sort, timeout):
        """Set a suggested sort for a link.

        Suggested sorts are useful to display comments in a certain preferred way
        for posts. For example, casual conversation may be better sorted by new
        by default, or AMAs may be sorted by Q&A. A sort of an empty string
        clears the default sort.
        """
        if c.user._id != thing.author_id:
            ModAction.create(thing.subreddit_slow, c.user, target=thing,
                             action='setsuggestedsort')

        thing.suggested_sort = sort
        thing._commit()
        jquery.refresh()

    @require_oauth2_scope("modposts")
    @validatedForm(
        VUser(),
        VModhash(),
        VSrCanBan("id"),
        thing=VByName("id"),
        state=VBoolean("state"),
        timeout=VNotInTimeout("id"),
    )
    @api_doc(api_section.links_and_comments)
    def POST_set_contest_mode(self, form, jquery, thing, state, timeout):
        """Set or unset "contest mode" for a link's comments.
        
        `state` is a boolean that indicates whether you are enabling or
        disabling contest mode - true to enable, false to disable.

        """
        thing.contest_mode = state
        thing._commit()
        if state:
            action = 'setcontestmode'
        else:
            action = 'unsetcontestmode'
        ModAction.create(thing.subreddit_slow, c.user, action, target=thing)
        jquery.refresh()

    @require_oauth2_scope("modposts")
    @validatedForm(
        VUser(),
        VModhash(),
        VSrCanBan('id'),
        thing=VByName('id'),
        state=VBoolean('state'),
        num=VInt("num", min=1, max=Subreddit.MAX_STICKIES, coerce=True),
        timeout=VNotInTimeout("id"),
    )
    @api_doc(api_section.links_and_comments)
    def POST_set_subreddit_sticky(self, form, jquery, thing, state, num,
            timeout):
        """Set or unset a Link as the sticky in its subreddit.
        
        `state` is a boolean that indicates whether to sticky or unsticky
        this post - true to sticky, false to unsticky.

        The `num` argument is optional, and only used when stickying a post.
        It allows specifying a particular "slot" to sticky the post into, and
        if there is already a post stickied in that slot it will be replaced.
        If there is no post in the specified slot to replace, or `num` is None,
        the bottom-most slot will be used.
        
        """
        if not isinstance(thing, Link):
            return

        sr = thing.subreddit_slow
        stickied = thing.is_stickied(sr)

        if not stickied and (thing._deleted or thing._spam):
            abort(400, "Can't sticky a removed or deleted post")

        if state:
            if not thing.is_stickyable():
                abort(400, "Post not stickyable")

            if stickied:
                abort(409, "Already stickied")
            sr.set_sticky(thing, c.user, num=num)
        else:
            sr.remove_sticky(thing, c.user)

        jquery.refresh()

    @require_oauth2_scope("report")
    @validatedForm(
        VUser(),
        VModhash(),
        thing=VByName('thing_id'),
        reason=VLength('reason', max_length=100, empty_error=None),
        site_reason=VLength('site_reason', max_length=100, empty_error=None),
        other_reason=VLength('other_reason', max_length=100, empty_error=None),
    )
    @api_doc(api_section.links_and_comments)
    def POST_report(self, form, jquery, thing, reason, site_reason, other_reason):
        """Report a link, comment or message.

        Reporting a thing brings it to the attention of the subreddit's
        moderators. Reporting a message sends it to a system for admin review.

        For links and comments, the thing is implicitly hidden as well (see
        [/api/hide](#POST_api_hide) for details).

        """
        if not thing:
            # preserve old behavior: we used to send the thing's fullname as the
            # "id" parameter, but we can't use that because that name is used to
            # send the form's id
            thing_id = request.POST.get('id')
            if thing_id:
                thing = VByName('id').run(thing_id)

        if not thing or thing._deleted:
            return

        if (form.has_errors("reason", errors.TOO_LONG) or
            form.has_errors("site_reason", errors.TOO_LONG) or
            form.has_errors("other_reason", errors.TOO_LONG)):
            return

        sr = getattr(thing, 'subreddit_slow', None)

        if reason == "site_reason_selected":
            reason = site_reason
        elif reason == "other":
            reason = other_reason

        # if it is a message that is being reported, ban it.
        # every user is admin over their own personal inbox
        if isinstance(thing, Message):
            # Ensure the message is either to them directly or indirectly
            # (through modmail), to prevent unauthorized banning through
            # spoofing.
            if (c.user._id != thing.to_id and
                    not (sr and c.user._id in sr.moderator_ids())):
                abort(403)
            admintools.spam(thing, False, True, c.user.name)
        # auto-hide links that are reported
        elif isinstance(thing, Link):
            # don't hide items from admins/moderators when reporting
            if not (c.user_is_admin or sr.is_moderator(c.user)):
                thing._hide(c.user)
        # TODO: be nice to be able to remove comments that are reported
        # from a user's inbox so they don't have to look at them.
        elif isinstance(thing, Comment):
            pass

        # Don't allow a user in timeout to report things, but continue
        # to hide the links of the reported items
        VNotInTimeout().run(action_name="report", target=thing)

        hooks.get_hook("thing.report").call(thing=thing)

        if not (c.user._spam or
                c.user.ignorereports or
                (sr and sr.is_banned(c.user))):
            Report.new(c.user, thing, reason)
            admintools.report(thing)

        g.events.report_event(
            reason=reason,
            details_text=reason,
            subreddit=sr,
            target=thing,
            request=request,
            context=c,
        )

        if isinstance(thing, (Link, Message)):
            button = jquery(".id-%s .report-button" % thing._fullname)
        elif isinstance(thing, Comment):
            button = jquery(".id-%s .entry:first .report-button" % thing._fullname)
        else:
            return

        button.text(_("reported"))
        parent_div = jquery(".report-%s.reportform" % thing._fullname)
        parent_div.removeClass("active")
        parent_div.html("")

    @require_oauth2_scope("privatemessages")
    @noresponse(
        VUser(),
        VModhash(),
        thing=VByName('id'),
    )
    @api_doc(api_section.messages)
    def POST_del_msg(self, thing):
        """Delete messages from the recipient's view of their inbox."""
        if not thing:
            return

        if not isinstance(thing, Message):
            return

        if thing.to_id != c.user._id:
            return

        thing.del_on_recipient = True
        thing._commit()

        # report the message deletion to data pipeline
        g.events.message_event(thing, event_type="ss.delete_message",
                               request=request, context=c)

    @require_oauth2_scope("privatemessages")
    @noresponse(
        VUser(),
        VModhash(),
        thing=VByName('id'),
    )
    @api_doc(api_section.messages)
    def POST_block(self, thing):
        '''For blocking via inbox.'''
        if not thing:
            return

        # don't allow blocking yourself
        if thing.author_id == c.user._id:
            return

        try:
            sr = Subreddit._byID(thing.sr_id) if thing.sr_id else None
        except NotFound:
            sr = None

        if getattr(thing, "from_sr", False) and sr:
            # Users may only block a subreddit they don't mod
            if not (sr.is_moderator(c.user) or c.user_is_admin):
                BlockedSubredditsByAccount.block(c.user, sr)
            return

        # Users may only block someone who has actively harassed them
        # directly (i.e. comment/link reply or PM). Make sure that 'thing'
        # is in the user's inbox somewhere, unless it's modmail to a
        # subreddit that the user moderates (since then it's not
        # necessarily in their personal inbox)
        is_modmail = (isinstance(thing, Message)
            and sr
            and sr.is_moderator_with_perms(c.user, 'mail'))

        if not is_modmail:
            inbox_cls = Inbox.rel(Account, thing.__class__)
            rels = inbox_cls._fast_query(c.user, thing,
                                        ("inbox", "selfreply", "mention"))
            if not any(rels.values()):
                return

        block_acct = Account._byID(thing.author_id)
        display_author = getattr(thing, "display_author", None)
        if block_acct.name in g.admins or display_author:
            return
        c.user.add_enemy(block_acct)

        # report the user blocking to data pipeline
        g.events.report_event(
            subreddit=sr,
            target=thing,
            request=request,
            context=c,
            event_type="ss.block_user"
        )


    @require_oauth2_scope("privatemessages")
    @noresponse(
        VUser(),
        VModhash(),
        thing=VByName('id'),
    )
    @api_doc(api_section.messages)
    def POST_unblock_subreddit(self, thing):
        if not thing:
            return

        try:
            sr = Subreddit._byID(thing.sr_id) if thing.sr_id else None
        except NotFound:
            sr = None

        if getattr(thing, "from_sr", False) and sr:
            BlockedSubredditsByAccount.unblock(c.user, sr)
            return

    @require_oauth2_scope("modcontributors")
    @noresponse(
        VUser(),
        VModhash(),
        message=VByName('id'),
    )
    @api_doc(api_section.moderation)
    def POST_mute_message_author(self, message):
        '''For muting user via modmail.'''
        if not message:
            return
        subreddit = message.subreddit_slow

        if not subreddit:
            abort(403, 'Not modmail')

        user = message.author_slow
        if not subreddit.can_mute(c.user, user):
            abort(403)

        if not c.user_is_admin:
            if not subreddit.is_moderator_with_perms(c.user, 'access', 'mail'):
                abort(403, 'Invalid mod permissions')

            if subreddit.use_quotas:
                sr_ratelimit = SimpleRateLimit(
                    name="sr_muted_%s" % subreddit._id36,
                    seconds=g.sr_quota_time,
                    limit=g.sr_muted_quota,
                )
                if not sr_ratelimit.record_and_check():
                    abort(403, errors.SUBREDDIT_RATELIMIT)

        # Don't allow a user in timeout to mute users
        VNotInTimeout().run(action_name="muteuser", details_text="modmail",
            target=user, subreddit=subreddit)

        added = subreddit.add_muted(user)
        # Don't mute the user and create another modaction if already muted
        if added:
            MutedAccountsBySubreddit.mute(subreddit, user, c.user, message)
            permalink = message.make_permalink(force_domain=True)
            ModAction.create(subreddit, c.user, 'muteuser',
                target=user, description=permalink)
            subreddit.add_rel_note('muted', user, permalink)

    @require_oauth2_scope("modcontributors")
    @noresponse(
        VUser(),
        VModhash(),
        message=VByName('id'),
    )
    @api_doc(api_section.moderation)
    def POST_unmute_message_author(self, message):
        '''For unmuting user via modmail.'''
        if not message:
            return
        subreddit = message.subreddit_slow

        if not subreddit:
            abort(403, 'Not modmail')

        user = message.author_slow
        if not c.user_is_admin:
            if not subreddit.is_moderator_with_perms(c.user, 'access', 'mail'):
                abort(403, 'Invalid mod permissions')

        # Don't allow a user in timeout to unmute users
        VNotInTimeout().run(action_name="unmuteuser", details_text="modmail",
            target=user, subreddit=subreddit)

        removed = subreddit.remove_muted(user)
        if removed:
            MutedAccountsBySubreddit.unmute(subreddit, user)
            ModAction.create(subreddit, c.user, 'unmuteuser', target=user)

    @require_oauth2_scope("edit")
    @validatedForm(
        VUser(),
        VModhash(),
        item=VByNameIfAuthor('thing_id'),
        text=VMarkdown('text'),
    )
    @api_doc(api_section.links_and_comments)
    def POST_editusertext(self, form, jquery, item, text):
        """Edit the body text of a comment or self-post."""
        if (form.has_errors('text', errors.NO_TEXT) or
                form.has_errors("thing_id", errors.NOT_AUTHOR)):
            return

        if isinstance(item, Link) and not item.is_self:
            return abort(403, "forbidden")
            
        if getattr(item, 'admin_takedown', False):
            # this item has been takendown by the admins,
            # and not not be edited
            # would love to use a 451 (legal) here, but pylons throws an error
            return abort(403, "this content is locked and can not be edited")

        if isinstance(item, Comment):
            max_length = 10000
            admin_override = False
        else:
            max_length = Link.SELFTEXT_MAX_LENGTH
            admin_override = c.user_is_admin

        if not admin_override and len(text) > max_length:
            c.errors.add(errors.TOO_LONG, field='text',
                         msg_params={'max_length': max_length})
            form.set_error(errors.TOO_LONG, 'text')
            return

        removed_mentions = None
        original_text = item.body
        if isinstance(item, Comment):
            kind = 'comment'
            prev_mentions = extract_user_mentions(original_text)
            new_mentions = extract_user_mentions(text)
            removed_mentions = prev_mentions - new_mentions
            item.body = text
        elif isinstance(item, Link):
            kind = 'link'
            if not getattr(item, "is_self", False):
                return abort(403, "forbidden")
            item.selftext = text
        else:
            g.log.warning("%s tried to edit usertext on %r", c.user, item)
            return

        if item._deleted:
            return abort(403, "forbidden")

        if item._age > timedelta(minutes=3) or item.num_votes > 2:
            item.editted = c.start_time

        item.ignore_reports = False

        item._commit()

        # only add to the edited page if this is marked as edited
        if hasattr(item, "editted"):
            queries.edit(item)

        item.update_search_index()

        amqp.add_item('%s_text_edited' % kind, item._fullname)

        hooks.get_hook("thing.edit").call(
            thing=item, original_text=original_text)

        # new mentions are subject to more constraints, handled in butler_q
        if removed_mentions:
            queries.unnotify(item, list(Account._names_to_ids(
                removed_mentions,
                ignore_missing=True,
            )))

        wrapper = default_thing_wrapper(expand_children = True)
        jquery("body>div.content").replace_things(item, True, True, wrap = wrapper)
        jquery("body>div.content .link .rank").hide()

    @allow_oauth2_access
    @validatedForm(
        VUser(),
        VModhash(),
        VRatelimit(rate_user=True, rate_ip=True, prefix="rate_comment_"),
        parent=VSubmitParent(['thing_id', 'parent']),
        comment=VMarkdownLength(['text', 'comment'], max_length=10000),
    )
    @api_doc(api_section.links_and_comments)
    def POST_comment(self, commentform, jquery, parent, comment):
        """Submit a new comment or reply to a message.

        `parent` is the fullname of the thing being replied to. Its value
        changes the kind of object created by this request:

        * the fullname of a Link: a top-level comment in that Link's thread. (requires `submit` scope)
        * the fullname of a Comment: a comment reply to that comment. (requires `submit` scope)
        * the fullname of a Message: a message reply to that message. (requires `privatemessages` scope)

        `text` should be the raw markdown body of the comment or message.

        To start a new message thread, use [/api/compose](#POST_api_compose).

        """
        should_ratelimit = True
        #check the parent type here cause we need that for the
        #ratelimit checks
        if isinstance(parent, Message):
            if (c.oauth_user and not
                    c.oauth_scope.has_any_scope({'privatemessages', 'submit'})):
                abort(403, 'forbidden')
            if not getattr(parent, "repliable", True):
                abort(403, 'forbidden')
            if not parent.can_view_slow():
                abort(403, 'forbidden')

            if parent.sr_id and not c.user_is_admin:
                sr = parent.subreddit_slow

                if sr.is_moderator(c.user) and not c.user_is_admin:
                    # don't let a moderator message a muted user
                    muted_user = parent.get_muted_user_in_conversation()
                    if muted_user:
                        c.errors.add(
                            errors.MUTED_FROM_SUBREDDIT, field="parent")
                        g.events.muted_forbidden_event("muted mod",
                            sr, parent_message=parent, target=muted_user,
                            request=request, context=c,
                        )
                elif sr.is_muted(c.user):
                    # don't let a muted user message the subreddit
                    c.errors.add(errors.USER_MUTED, field="parent")
                    g.events.muted_forbidden_event("muted",
                        parent_message=parent, target=sr,
                        request=request, context=c,
                    )

            is_message = True
            should_ratelimit = False
        else:
            if (c.oauth_user and not
                    c.oauth_scope.has_access(c.site.name, {'submit'})):
                abort(403, 'forbidden')

            is_message = False
            if isinstance(parent, Link):
                link = parent
                parent_comment = None
            else:
                link = Link._byID(parent.link_id)
                parent_comment = parent

            sr = Subreddit._byID(parent.sr_id, stale=True)
            is_author = link.author_id == c.user._id
            if (is_author and (link.is_self or promote.is_promo(link)) or
                    not sr.should_ratelimit(c.user, 'comment')):
                should_ratelimit = False

            hooks.get_hook("comment.validate").call(sr=sr, link=link,
                           parent_comment=parent_comment)

        #remove the ratelimit error if the user's karma is high
        if not should_ratelimit:
            c.errors.remove((errors.RATELIMIT, 'ratelimit'))

        if (commentform.has_errors("text", errors.NO_TEXT, errors.TOO_LONG) or
                commentform.has_errors("comment", errors.TOO_LONG) or
                commentform.has_errors("ratelimit", errors.RATELIMIT) or
                commentform.has_errors("parent", errors.DELETED_COMMENT,
                    errors.TOO_OLD, errors.USER_BLOCKED,
                    errors.USER_MUTED, errors.MUTED_FROM_SUBREDDIT,
                    errors.THREAD_LOCKED)
        ):
            return

        if is_message:
            if parent.from_sr:
                to = Subreddit._byID(parent.sr_id)
            else:
                to = Account._byID(parent.author_id)

            # Restrict messaging for users in timeout
            if to:
                sr_name = None
                if isinstance(to, Subreddit):
                    sr_name = to.name
                # Replies in modmail have an Account as their target, but act
                # like they're sent to everyone involved in the conversation.
                elif isinstance(to, Account) and parent and parent.sr_id:
                    sr = Subreddit._byID(parent.sr_id, data=True)
                    if sr:
                        sr_name = sr.name
                is_messaging_admins = ('/r/%s' % sr_name) == g.admin_message_acct

                # Users in timeout can only message the admins.
                if not (sr_name and is_messaging_admins):
                    VNotInTimeout().run(action_name='messagereply', target=parent)

            subject = parent.subject
            re = "re: "
            if not subject.startswith(re):
                subject = re + subject

            item, inbox_rel = Message._new(c.user, to, subject, comment,
                                           request.ip, parent=parent)
        else:
            # Don't let users in timeout comment
            VNotInTimeout().run(action_name='comment', target=parent)

            item, inbox_rel = Comment._new(c.user, link, parent_comment,
                                           comment, request.ip)

        if is_message:
            queries.new_message(item, inbox_rel)
        else:
            queries.new_comment(item, inbox_rel)

        if should_ratelimit:
            VRatelimit.ratelimit(rate_user=True, rate_ip = True,
                                 prefix = "rate_comment_")

        # clean up the submission form and remove it from the DOM (if reply)
        t = commentform.find("textarea")
        t.attr('rows', 3).html("").val("")
        if isinstance(parent, (Comment, Message)):
            commentform.remove()
            jquery.things(parent._fullname).set_text(".reply-button:first",
                                                     _("replied"))

        # insert the new comment
        jquery.insert_things(item)

        # remove any null listings that may be present
        jquery("#noresults").hide()

    @validatedForm(
        VUser(),
        VModhash(),
        VShareRatelimit(),
        share_to=ValidEmailsOrExistingUnames("share_to"),
        message=VLength("message", max_length=1000),
        link=VByName('parent', thing_cls=Link),
    )
    def POST_share(self, shareform, jquery, share_to, message, link):
        if not link:
            abort(404, 'not found')

        # remove the ratelimit error if the user's karma is high
        sr = link.subreddit_slow
        should_ratelimit = sr.should_ratelimit(c.user, 'link')
        if not should_ratelimit:
            c.errors.remove((errors.RATELIMIT, 'ratelimit'))

        if shareform.has_errors("message", errors.TOO_LONG):
            return
        elif shareform.has_errors("share_to", errors.BAD_EMAILS,
                                  errors.NO_EMAILS,
                                  errors.TOO_MANY_EMAILS):
            return
        elif shareform.has_errors("ratelimit", errors.RATELIMIT):
            return

        subreddit = link.subreddit_slow

        if subreddit.quarantine or not subreddit.can_view(c.user):
            return abort(403, 'forbidden')

        VNotInTimeout().run(target=link, subreddit=subreddit)

        emails, users = share_to

        # disallow email share for accounts without a verified email address
        if emails and (not c.user.email or not c.user.email_verified):
            return abort(403, 'forbidden')

        link_title = _force_unicode(link.title)

        if getattr(link, "promoted", None) and link.disable_comments:
            message = blockquote_text(message) + "\n\n" if message else ""
            message += '\n%s\n\n%s\n\n' % (link_title, link.url)
            email_message = pm_message = message
        else:
            message = blockquote_text(message) + "\n\n" if message else ""
            message += '\n%s\n' % link_title

            message_body = '\n'

            # Deliberately not translating this, as it'd be in the
            # sender's language
            if link.num_comments:
                count = ("There are currently %(num_comments)s comments " +
                         "on this link.  You can view them here:")
                if link.num_comments == 1:
                    count = ("There is currently %(num_comments)s " +
                             "comment on this link.  You can view it here:")
                numcom = count % {'num_comments': link.num_comments}
                message_body = message_body + "%s\n\n" % numcom
            else:
                message_body = message_body + "You can leave a comment here:\n\n"

            url = add_sr(link.make_permalink_slow(), force_hostname=True)
            url_parser = UrlParser(url)
            url_parser.update_query(ref="share", ref_source="email")
            email_comments_url = url_parser.unparse()
            url_parser.update_query(ref_source="pm")
            pm_comments_url = url_parser.unparse()

            message_body += '%(comments_url)s'
            email_message = message + message_body % {
                    "comments_url": email_comments_url,
                }
            pm_message = message + message_body % {
                    "comments_url": pm_comments_url,
                }
        
        # E-mail everyone
        emailer.share(link, emails, body=email_message or "")

        # Send the PMs
        subject = "%s has shared a link with you!" % c.user.name
        # Prepend this subject to the message - we're repeating ourselves
        # because it looks very abrupt without it.
        pm_message = "%s\n\n%s" % (subject, pm_message)
        
        for target in users:
            m, inbox_rel = Message._new(c.user, target, subject,
                                        pm_message, request.ip)
            # Queue up this PM
            amqp.add_item('new_message', m._fullname)

            queries.new_message(m, inbox_rel)

        g.stats.simple_event('share.email_sent', len(emails))
        g.stats.simple_event('share.pm_sent', len(users))

        # Set the ratelimiter.
        VShareRatelimit.ratelimit()

    @require_oauth2_scope("vote")
    @noresponse(VUser(),
                VModhash(),
                direction=VInt("dir", min=-1, max=1,
                    docs={"dir": "vote direction. one of (1, 0, -1)"}
                ),
                thing=VByName('id'),
                rank=VInt("rank", min=1))
    @api_doc(api_section.links_and_comments)
    def POST_vote(self, direction, thing, rank):
        """Cast a vote on a thing.

        `id` should be the fullname of the Link or Comment to vote on.

        `dir` indicates the direction of the vote. Voting `1` is an upvote,
        `-1` is a downvote, and `0` is equivalent to "un-voting" by clicking
        again on a highlighted arrow.

        **Note: votes must be cast by humans.** That is, API clients proxying a
        human's action one-for-one are OK, but bots deciding how to vote on
        content or amplifying a human's vote are not. See [the reddit
        rules](/rules) for more details on what constitutes vote cheating.

        """

        # a persistent A/A to provide a consistent event stream and confidence
        # in bucketing to the data team
        feature.is_enabled('persistent_vote_a_a')

        if not thing or thing._deleted:
            return self.abort404()

        if not thing.is_votable:
            abort(400, "That type of thing can't be voted on.")

        hooks.get_hook("vote.validate").call(thing=thing)

        if isinstance(thing, Link) and promote.is_promo(thing):
            if not promote.is_promoted(thing):
                return abort(400, "Bad Request")

        if thing.archived_slow:
            return abort(400,
                "This thing is archived and may no longer be voted on")

        # Don't allow users in timeout to vote
        VNotInTimeout().run(target=thing)

        # convert vote direction to enum value
        if direction == 1:
            direction = Vote.DIRECTIONS.up
        elif direction == -1:
            direction = Vote.DIRECTIONS.down
        elif direction == 0:
            direction = Vote.DIRECTIONS.unvote

        cast_vote(c.user, thing, direction, rank=rank)

    @require_oauth2_scope("modconfig")
    @validatedForm(VSrModerator(perms='config'),
                   VModhash(),
                   # nop is safe: handled after auth checks below
                   stylesheet_contents=nop('stylesheet_contents',
                       docs={"stylesheet_contents":
                             "the new stylesheet content"}),
                   reason=VPrintable('reason', 256, empty_error=None),
                   op = VOneOf('op',['save','preview']))
    @api_doc(api_section.subreddits, uses_site=True)
    def POST_subreddit_stylesheet(self, form, jquery,
                                  stylesheet_contents = '', prevstyle='',
                                  op='save', reason=None):
        """Update a subreddit's stylesheet.

        `op` should be `save` to update the contents of the stylesheet.

        """

        if g.css_killswitch:
            return abort(403, 'forbidden')

        css_errors, parsed = c.site.parse_css(stylesheet_contents)

        # The hook passes errors back by setting them on the form.
        hooks.get_hook('subreddit.css.validate').call(
            request=request, form=form, op=op,
            stylesheet_contents=stylesheet_contents,
            parsed_stylesheet=parsed,
            css_errors=css_errors,
            subreddit=c.site,
            user=c.user
        )

        if css_errors:
            error_items = [CssError(x).render(style='html') for x in css_errors]
            form.set_text(".status", _('validation errors'))
            form.set_html(".errors ul", ''.join(error_items))
            form.find('.errors').show()
            c.errors.add(errors.BAD_CSS, field="stylesheet_contents")
            form.has_errors("stylesheet_contents", errors.BAD_CSS)
            return
        else:
            form.find('.errors').hide()
            form.set_html(".errors ul", '')

        # Don't allow users in timeout to modify the stylesheet
        VNotInTimeout().run(action_name="editsettings",
            details_text="%s_stylesheet" % op, target=c.site)

        if op == 'save' and not form.has_error():
            wr = c.site.change_css(stylesheet_contents, parsed, reason=reason)
            form.find('.errors').hide()
            form.set_text(".status", _('saved'))
            form.set_html(".errors ul", "")

        jquery.apply_stylesheet(parsed)

        if op == 'preview':
            # try to find a link to use, otherwise give up and
            # return
            links = SubredditStylesheet.find_preview_links(c.site)
            if links:

                jquery('#preview-table').show()
    
                # do a regular link
                jquery('#preview_link_normal').html(
                    SubredditStylesheet.rendered_link(
                        links, media='off', compress=False))
                # now do one with media
                jquery('#preview_link_media').html(
                    SubredditStylesheet.rendered_link(
                        links, media='on', compress=False))
                # do a compressed link
                jquery('#preview_link_compressed').html(
                    SubredditStylesheet.rendered_link(
                        links, media='off', compress=True))
                # do a stickied link
                jquery('#preview_link_stickied').html(
                    SubredditStylesheet.rendered_link(
                        links, media='off', compress=False, stickied=True))
    
            # and do a comment
            comments = SubredditStylesheet.find_preview_comments(c.site)
            if comments:
                jquery('#preview_comment').html(
                    SubredditStylesheet.rendered_comment(comments))

                jquery('#preview_comment_gilded').html(
                    SubredditStylesheet.rendered_comment(
                        comments, gilded=True))

    @require_oauth2_scope("modconfig")
    @validatedForm(VSrModerator(perms='config'),
                   VModhash(),
                   name = VCssName('img_name'))
    @api_doc(api_section.subreddits, uses_site=True)
    def POST_delete_sr_img(self, form, jquery, name):
        """Remove an image from the subreddit's custom image set.

        The image will no longer count against the subreddit's image limit.
        However, the actual image data may still be accessible for an
        unspecified amount of time. If the image is currently referenced by the
        subreddit's stylesheet, that stylesheet will no longer validate and
        won't be editable until the image reference is removed.

        See also: [/api/upload_sr_img](#POST_api_upload_sr_img).

        """
        # just in case we need to kill this feature from XSS
        if g.css_killswitch:
            return abort(403, 'forbidden')

        if form.has_errors("img_name", errors.BAD_CSS_NAME):
            return

        # Don't allow users in timeout to modify the stylesheet
        VNotInTimeout().run(action_name="editsettings",
            details_text="del_image", target=c.site)

        wiki.ImagesByWikiPage.delete_image(c.site, "config/stylesheet", name)
        ModAction.create(c.site, c.user, action='editsettings', 
                         details='del_image', description=name)

    @require_oauth2_scope("modconfig")
    @validatedForm(
        VSrModerator(perms='config'),
        VModhash(),
        VNotInTimeout(),
    )
    @api_doc(api_section.subreddits, uses_site=True)
    def POST_delete_sr_header(self, form, jquery):
        """Remove the subreddit's custom header image.

        The sitewide-default header image will be shown again after this call.

        See also: [/api/upload_sr_img](#POST_api_upload_sr_img).

        """
        # just in case we need to kill this feature from XSS
        if g.css_killswitch:
            return abort(403, 'forbidden')

        if c.site.header:
            c.site.header = None
            c.site.header_size = None
            c.site._commit()
            ModAction.create(c.site, c.user, action='editsettings', 
                             details='del_header')

        # hide the button which started this
        form.find('.delete-img').hide()
        # hide the preview box
        form.find('.img-preview-container').hide()
        # reset the status boxes
        form.set_text('.img-status', _("deleted"))
        
    @require_oauth2_scope("modconfig")
    @validatedForm(
        VSrModerator(perms='config'),
        VModhash(),
        VNotInTimeout(),
    )
    @api_doc(api_section.subreddits, uses_site=True)
    def POST_delete_sr_icon(self, form, jquery):
        """Remove the subreddit's custom mobile icon.

        See also: [/api/upload_sr_img](#POST_api_upload_sr_img).

        """
        if c.site.icon_img:
            c.site.icon_img = None
            c.site.icon_size = None
            c.site._commit()
            ModAction.create(c.site, c.user, action='editsettings',
                             details='del_icon')

        # hide the button which started this
        form.find('.delete-img').hide()
        # hide the preview box
        form.find('.img-preview-container').hide()
        # reset the status boxes
        form.set_text('.img-status', _("deleted"))

    @require_oauth2_scope("modconfig")
    @validatedForm(
        VSrModerator(perms='config'),
        VModhash(),
        VNotInTimeout(),
    )
    @api_doc(api_section.subreddits, uses_site=True)
    def POST_delete_sr_banner(self, form, jquery):
        """Remove the subreddit's custom mobile banner.

        See also: [/api/upload_sr_img](#POST_api_upload_sr_img).

        """
        if c.site.banner_img:
            c.site.banner_img = None
            c.site.banner_size = None
            c.site._commit()
            ModAction.create(c.site, c.user, action='editsettings',
                             details='del_banner')

        # hide the button which started this
        form.find('.delete-img').hide()
        # hide the preview box
        form.find('.img-preview-container').hide()
        # reset the status boxes
        form.set_text('.img-status', _("deleted"))

    def GET_upload_sr_img(self, *a, **kw):
        """
        Completely unnecessary method which exists because safari can
        be dumb too.  On page reload after an image has been posted in
        safari, the iframe to which the request posted preserves the
        URL of the POST, and safari attempts to execute a GET against
        it.  The iframe is hidden, so what it returns is completely
        irrelevant.
        """
        return "nothing to see here."

    @require_oauth2_scope("modconfig")
    @validate(VSrModerator(perms='config'),
              VModhash(),
              file = VUploadLength('file', max_length=1024*500),
              name = VCssName("name"),
              img_type = VImageType('img_type'),
              form_id = VLength('formid', max_length = 100,
                                docs={"formid": "(optional) can be ignored"}),
              upload_type = VOneOf('upload_type',
                                   ('img', 'header', 'icon', 'banner')),
              header = VInt('header', max=1, min=0))
    @api_doc(api_section.subreddits, uses_site=True)
    def POST_upload_sr_img(self, file, header, name, form_id, img_type,
                           upload_type=None):
        """Add or replace a subreddit image, custom header logo, custom mobile
        icon, or custom mobile banner.

        * If the `upload_type` value is `img`, an image for use in the
        subreddit stylesheet is uploaded with the name specified in `name`.
        * If the `upload_type` value is `header` then the image uploaded will
        be the subreddit's new logo and `name` will be ignored.
        * If the `upload_type` value is `icon` then the image uploaded will be
        the subreddit's new mobile icon and `name` will be ignored.
        * If the `upload_type` value is `banner` then the image uploaded will
        be the subreddit's new mobile banner and `name` will be ignored.

        For backwards compatibility, if `upload_type` is not specified, the
        `header` field will be used instead:

        * If the `header` field has value `0`, then `upload_type` is `img`.
        * If the `header` field has value `1`, then `upload_type` is `header`.

        The `img_type` field specifies whether to store the uploaded image as a
        PNG or JPEG.

        Subreddits have a limited number of images that can be in use at any
        given time. If no image with the specified name already exists, one of
        the slots will be consumed.

        If an image with the specified name already exists, it will be
        replaced.  This does not affect the stylesheet immediately, but will
        take effect the next time the stylesheet is saved.

        See also: [/api/delete_sr_img](#POST_api_delete_sr_img),
        [/api/delete_sr_header](#POST_api_delete_sr_header),
        [/api/delete_sr_icon](#POST_api_delete_sr_icon), and
        [/api/delete_sr_banner](#POST_api_delete_sr_banner).

        """

        if c.site.quarantine:
            abort(403)

        # default error list (default values will reset the errors in
        # the response if no error is raised)
        errors = dict(BAD_CSS_NAME = "", IMAGE_ERROR = "")

        # for backwards compatibility, map header to upload_type
        if upload_type is None:
            upload_type = 'header' if header else 'img'
        
        if upload_type == 'img' and not name:
            # error if the name wasn't specified and the image was not for a sponsored link or header
            # this may also fail if a sponsored image was added and the user is not an admin
            errors['BAD_CSS_NAME'] = _("bad image name")
        
        if upload_type == 'img' and not c.user_is_admin:
            image_count = wiki.ImagesByWikiPage.get_image_count(
                c.site, "config/stylesheet")
            if image_count >= g.max_sr_images:
                errors['IMAGE_ERROR'] = _("too many images (you only get %d)") % g.max_sr_images

        try:
            size = str_to_image(file).size
        except (IOError, TypeError):
            errors['IMAGE_ERROR'] = _('Invalid image or general image error')
        else:
            if upload_type == 'icon':
                if size != Subreddit.ICON_EXACT_SIZE:
                    errors['IMAGE_ERROR'] = (
                        _('must be %dx%d pixels') % Subreddit.ICON_EXACT_SIZE)
            elif upload_type == 'banner':
                aspect_ratio = float(size[0]) / size[1]
                if abs(Subreddit.BANNER_ASPECT_RATIO - aspect_ratio) > 0.01:
                    errors['IMAGE_ERROR'] = _('10:3 aspect ratio required')
                elif size > Subreddit.BANNER_MAX_SIZE:
                    errors['IMAGE_ERROR'] = (
                        _('max %dx%d pixels') % Subreddit.BANNER_MAX_SIZE)
                elif size < Subreddit.BANNER_MIN_SIZE:
                    errors['IMAGE_ERROR'] = (
                        _('min %dx%d pixels') % Subreddit.BANNER_MIN_SIZE)

        if any(errors.values()):
            return UploadedImage("", "", "", errors=errors, form_id=form_id).render()
        else:
            try:
                new_url = media.upload_media(file, file_type="." + img_type)
            except Exception as e:
                g.log.warning("error uploading subreddit image: %s", e)
                errors['IMAGE_ERROR'] = _("Invalid image or general image error")
                return UploadedImage("", "", "", errors=errors, form_id=form_id).render()

            details_text = "upload_image"
            if not upload_type == "img":
                details_text = "upload_image_%s" % upload_type
            VNotInTimeout().run(action_name="editsettings",
                details_text=details_text, target=c.site)

            if upload_type == 'img':
                wiki.ImagesByWikiPage.add_image(c.site, "config/stylesheet",
                                                name, new_url)
                kw = dict(details='upload_image', description=name)
            elif upload_type == 'header':
                c.site.header = new_url
                c.site.header_size = size
                c.site._commit()
                kw = dict(details='upload_image_header')
            elif upload_type == 'icon':
                c.site.icon_img = new_url
                c.site.icon_size = size
                c.site._commit()
                kw = dict(details='upload_image_icon')
            elif upload_type == 'banner':
                c.site.banner_img = new_url
                c.site.banner_size = size
                c.site._commit()
                kw = dict(details='upload_image_banner')

            ModAction.create(c.site, c.user, action='editsettings', **kw)

            return UploadedImage(_('saved'), new_url, name, 
                                 errors=errors, form_id=form_id).render()

    @require_oauth2_scope("modconfig")
    @validatedForm(VUser(),
                   VCaptcha(),
                   VModhash(),
                   VRatelimit(rate_user = True,
                              rate_ip = True,
                              prefix = 'create_reddit_'),
                   sr = VByName('sr'),
                   name = VAvailableSubredditName("name"),
                   title = VLength("title", max_length = 100),
                   header_title = VLength("header-title", max_length = 500),
                   domain = VCnameDomain("domain"),
                   submit_text = VMarkdownLength("submit_text", max_length=1024),
                   public_description = VMarkdownLength("public_description", max_length = 500),
                   description = VMarkdownLength("description", max_length = 5120),
                   lang = VLang("lang"),
                   over_18 = VBoolean('over_18'),
                   allow_top = VBoolean('allow_top'),
                   show_media = VBoolean('show_media'),
                   # show_media_preview = VBoolean('show_media_preview'),
                   public_traffic = VBoolean('public_traffic'),
                   collapse_deleted_comments = VBoolean('collapse_deleted_comments'),
                   exclude_banned_modqueue = VBoolean('exclude_banned_modqueue'),
                   spam_links = VOneOf('spam_links', ('low', 'high', 'all')),
                   spam_selfposts = VOneOf('spam_selfposts', ('low', 'high', 'all')),
                   spam_comments = VOneOf('spam_comments', ('low', 'high', 'all')),
                   type = VOneOf('type', Subreddit.valid_types),
                   link_type = VOneOf('link_type', ('any', 'link', 'self')),
                   submit_link_label=VLength('submit_link_label', max_length=60),
                   submit_text_label=VLength('submit_text_label', max_length=60),
                   comment_score_hide_mins=VInt('comment_score_hide_mins',
                       coerce=False, num_default=0, min=0, max=1440),
                   wikimode = VOneOf('wikimode', ('disabled', 'modonly', 'anyone')),
                   wiki_edit_karma = VInt("wiki_edit_karma", coerce=False, num_default=0, min=0),
                   wiki_edit_age = VInt("wiki_edit_age", coerce=False, num_default=0, min=0),
                   hide_ads = VBoolean("hide_ads"),
                   suggested_comment_sort=VOneOf('suggested_comment_sort',
                                                 CommentSortMenu._options,
                                                 default=None),
                   # related_subreddits = VSubredditList('related_subreddits', limit=20),
                   # key_color = VColor('key_color'),
                   )
    @api_doc(api_section.subreddits)
    def POST_site_admin(self, form, jquery, name, sr, **kw):
        """Create or configure a subreddit.

        If `sr` is specified, the request will attempt to modify the specified
        subreddit. If not, a subreddit with name `name` will be created.

        This endpoint expects *all* values to be supplied on every request.  If
        modifying a subset of options, it may be useful to get the current
        settings from [/about/edit.json](#GET_r_{subreddit}_about_edit.json)
        first.

        For backwards compatibility, `description` is the sidebar text and
        `public_description` is the publicly visible subreddit description.

        Most of the parameters for this endpoint are identical to options
        visible in the user interface and their meanings are best explained
        there.

        See also: [/about/edit.json](#GET_r_{subreddit}_about_edit.json).

        """
        def apply_wikid_field(sr, form, pagename, value, field):
            try:
                wikipage = wiki.WikiPage.get(sr, pagename)
            except tdb_cassandra.NotFound:
                wikipage = wiki.WikiPage.create(sr, pagename)
            wr = wikipage.revise(value, author=c.user._id36)
            setattr(sr, field, value)
            if wr:
                ModAction.create(sr, c.user, 'wikirevise',
                                 details=wiki.modactions.get(pagename))

        # This should be moved to @validatedForm above when we remove
        # the feature flag. Down here to avoid processing when flagged off
        # and to hide from API docs.
        if feature.is_enabled('mobile_settings'):
            validator = VColor('key_color')
            value = request.params.get('key_color')
            kw['key_color'] = validator.run(value)
        if feature.is_enabled('related_subreddits'):
            validator = VSubredditList('related_subreddits', limit=20)
            value = request.params.get('related_subreddits')
            kw['related_subreddits'] = validator.run(value)

        if feature.is_enabled('autoexpand_media_previews'):
            validator = VBoolean('show_media_preview')
            value = request.params.get('show_media_preview')
            kw["show_media_preview"] = validator.run(value)

        # the status button is outside the form -- have to reset by hand
        form.parent().set_html('.status', "")

        redir = False
        keyword_fields = [
            'allow_top',
            'collapse_deleted_comments',
            'comment_score_hide_mins',
            'description',
            'domain',
            'exclude_banned_modqueue',
            'header_title',
            'hide_ads',
            'lang',
            'link_type',
            'name',
            'over_18',
            'public_description',
            'public_traffic',
            'show_media',
            'show_media_preview',
            'spam_comments',
            'spam_links',
            'spam_selfposts',
            'submit_link_label',
            'submit_text',
            'submit_text_label',
            'suggested_comment_sort',
            'title',
            'type',
            'wiki_edit_age',
            'wiki_edit_karma',
            'wikimode',
        ]

        if feature.is_enabled('mobile_settings'):
            keyword_fields.append('key_color')
        if sr and feature.is_enabled('related_subreddits'):
            keyword_fields.append('related_subreddits')

        kw = {k: v for k, v in kw.iteritems() if k in keyword_fields}

        public_description = kw.pop('public_description')
        description = kw.pop('description')
        submit_text = kw.pop('submit_text')

        def update_wiki_text(sr):
            error = False
            apply_wikid_field(
                sr,
                form,
                'config/sidebar',
                description,
                'description',
            )

            apply_wikid_field(
                sr,
                form,
                'config/submit_text',
                submit_text,
                'submit_text',
            )

            apply_wikid_field(
                sr,
                form,
                'config/description',
                public_description,
                'public_description',
            )
        
        if not sr and not c.user.can_create_subreddit:
            form.set_error(errors.CANT_CREATE_SR, "")
            c.errors.add(errors.CANT_CREATE_SR, field="")

        # only care about captcha if this is creating a subreddit
        if not sr and form.has_errors("captcha", errors.BAD_CAPTCHA):
            return

        domain = kw['domain']
        cname_sr = domain and Subreddit._by_domain(domain)
        if cname_sr and (not sr or sr != cname_sr):
            c.errors.add(errors.USED_CNAME)

        can_set_archived = c.user_is_admin or (sr and sr.type == 'archived')
        if kw['type'] == 'archived' and not can_set_archived:
            c.errors.add(errors.INVALID_OPTION, field='type')

        can_set_gold_restricted = c.user_is_admin or (sr and sr.type == 'gold_restricted')
        if kw['type'] == 'gold_restricted' and not can_set_gold_restricted:
            c.errors.add(errors.INVALID_OPTION, field='type')

        # can't create a gold only subreddit without having gold
        can_set_gold_only = (c.user.gold or c.user.gold_charter or
                (sr and sr.type == 'gold_only'))
        if kw['type'] == 'gold_only' and not can_set_gold_only:
            form.set_error(errors.GOLD_REQUIRED, 'type')
            c.errors.add(errors.GOLD_REQUIRED, field='type')

        can_set_hide_ads = can_set_gold_only and kw['type'] == 'gold_only'
        if kw['hide_ads'] and not can_set_hide_ads:
            form.set_error(errors.GOLD_ONLY_SR_REQUIRED, 'hide_ads')
            c.errors.add(errors.GOLD_ONLY_SR_REQUIRED, field='hide_ads')
        elif not can_set_hide_ads and sr:
            kw['hide_ads'] = sr.hide_ads

        can_set_employees_only = c.user.employee
        if kw['type'] == 'employees_only' and not can_set_employees_only:
            c.errors.add(errors.INVALID_OPTION, field='type')

        if not sr and form.has_errors("ratelimit", errors.RATELIMIT):
            pass
        elif not sr and form.has_errors("", errors.CANT_CREATE_SR):
            pass
        # if existing subreddit is employees_only and trying to change type,
        # require that admin mode is on
        elif (sr and sr.type == 'employees_only' and kw['type'] != sr.type and
                not c.user_is_admin):
            form.set_error(errors.ADMIN_REQUIRED, 'type')
            c.errors.add(errors.ADMIN_REQUIRED, field='type')
        # if the user wants to convert an existing subreddit to gold_only,
        # let them know that they'll need to contact an admin to convert it.
        elif (sr and sr.type != 'gold_only' and kw['type'] == 'gold_only' and
                not c.user_is_admin):
            form.set_error(errors.CANT_CONVERT_TO_GOLD_ONLY, 'type')
            c.errors.add(errors.CANT_CONVERT_TO_GOLD_ONLY, field='type')
        elif form.has_errors('type', errors.GOLD_REQUIRED):
            pass
        elif not sr and form.has_errors("name", errors.SUBREDDIT_EXISTS,
                                        errors.BAD_SR_NAME):
            form.find('#example_name').hide()
        elif form.has_errors('title', errors.NO_TEXT, errors.TOO_LONG):
            form.find('#example_title').hide()
        elif form.has_errors('domain', errors.BAD_CNAME, errors.USED_CNAME):
            form.find('#example_domain').hide()
        elif (form.has_errors(('type', 'link_type', 'wikimode'),
                              errors.INVALID_OPTION) or
              form.has_errors(('public_description',
                               'submit_text',
                               'description'), errors.TOO_LONG)):
            pass
        elif (form.has_errors(('wiki_edit_karma', 'wiki_edit_age'), 
                              errors.BAD_NUMBER)):
            pass
        elif form.has_errors('comment_score_hide_mins', errors.BAD_NUMBER):
            pass
        elif form.has_errors('related_subreddits', errors.SUBREDDIT_NOEXIST,
                             errors.BAD_SR_NAME, errors.TOO_MANY_SUBREDDITS):
            pass
        elif form.has_errors('hide_ads', errors.GOLD_ONLY_SR_REQUIRED):
            pass
        #creating a new reddit
        elif not sr:
            # Don't allow user in timeout to create a new subreddit
            VNotInTimeout().run(action_name="createsubreddit", target=None)

            #sending kw is ok because it was sanitized above
            sr = Subreddit._new(name = name, author_id = c.user._id,
                                ip=request.ip, **kw)

            update_wiki_text(sr)
            sr._commit()

            hooks.get_hook("subreddit.new").call(subreddit=sr)

            Subreddit.subscribe_defaults(c.user)
            sr.add_subscriber(c.user)
            sr.add_moderator(c.user)

            if not sr.hide_contributors:
                sr.add_contributor(c.user)
            redir = sr.path + "about/edit/?created=true"
            if not c.user_is_admin:
                VRatelimit.ratelimit(rate_user=True,
                                     rate_ip = True,
                                     prefix = "create_reddit_")

            queries.new_subreddit(sr)
            sr.update_search_index()

        #editting an existing reddit
        elif sr.is_moderator_with_perms(c.user, 'config') or c.user_is_admin:
            # Don't allow user in timeout to edit subreddit settings
            VNotInTimeout().run(action_name="editsettings", target=sr)

            #assume sr existed, or was just built
            old_domain = sr.domain

            update_wiki_text(sr)

            if sr.quarantine:
                del kw['allow_top']
                del kw['show_media']
                del kw['show_media_preview']

            #notify ads if sr in a collection changes over_18 to true
            if kw.get('over_18', False) and not sr.over_18:
                collections = []
                for collection in Collection.get_all():
                    if (sr.name in collection.sr_names
                            and not collection.over_18):
                        collections.append(collection.name)

                if collections:
                    msg = "%s now NSFW, in collection(s) %s"
                    msg %= (sr.name, ', '.join(collections))
                    emailer.sales_email(msg)

            for k, v in kw.iteritems():
                if getattr(sr, k, None) != v:
                    ModAction.create(sr, c.user, action='editsettings',
                                     details=k)

                setattr(sr, k, v)
            sr._commit()

            #update the domain cache if the domain changed
            if sr.domain != old_domain:
                Subreddit._by_domain(old_domain, _update = True)
                Subreddit._by_domain(sr.domain, _update = True)

            sr.update_search_index()
            form.parent().set_text('.status', _("saved"))

        if form.has_error():
            return

        if redir:
            form.redirect(redir)
        else:
            jquery.refresh()

    @require_oauth2_scope("modposts")
    @noresponse(VUser(), VModhash(),
                VSrCanBan('id'),
                thing = VByName('id'),
                spam = VBoolean('spam', default=True))
    @api_doc(api_section.moderation)
    def POST_remove(self, thing, spam):
        """Remove a link, comment, or modmail message.

        If the thing is a link, it will be removed from all subreddit listings.
        If the thing is a comment, it will be redacted and removed from all
        subreddit comment listings.

        See also: [/api/approve](#POST_api_approve).

        """

        # Don't remove a promoted link
        if getattr(thing, "promoted", None):
            return
        action_name = "remove"
        if spam:
            action_name = "spam"
        VNotInTimeout().run(action_name=action_name, target=thing)

        if thing._deleted:
            return

        filtered = thing._spam
        kw = {'target': thing}

        if filtered and spam:
            kw['details'] = 'confirm_spam'
            train_spam = False
        elif filtered and not spam:
            kw['details'] = 'remove'
            admintools.unspam(thing, unbanner=c.user.name, insert=False)
            train_spam = False
        elif not filtered and spam:
            kw['details'] = 'spam'
            train_spam = True
        elif not filtered and not spam:
            kw['details'] = 'remove'
            train_spam = False

        admintools.spam(thing, auto=False,
                        moderator_banned=not c.user_is_admin,
                        banner=c.user.name,
                        train_spam=train_spam)

        if isinstance(thing, (Link, Comment)):
            sr = thing.subreddit_slow
            action = 'remove' + thing.__class__.__name__.lower()
            ModAction.create(sr, c.user, action, **kw)

        if isinstance(thing, Link):
            sr.remove_sticky(thing)
        elif isinstance(thing, Comment):
            thing.link_slow.remove_sticky_comment(comment=thing, set_by=c.user)
            queries.unnotify(thing)


    @require_oauth2_scope("modposts")
    @noresponse(VUser(), VModhash(),
                VSrCanBan('id'),
                thing = VByName('id'))
    @api_doc(api_section.moderation)
    def POST_approve(self, thing):
        """Approve a link or comment.

        If the thing was removed, it will be re-inserted into appropriate
        listings. Any reports on the approved thing will be discarded.

        See also: [/api/remove](#POST_api_remove).

        """
        if not thing: return
        if thing._deleted: return
        if c.user._spam:
           self.abort403()

        # Don't allow user in timeout to approve link or comment
        VNotInTimeout().run(target=thing)

        kw = {'target': thing}
        if thing._spam:
            kw['details'] = 'unspam'
            train_spam = True
            insert = True
        else:
            kw['details'] = 'confirm_ham'
            train_spam = False
            insert = False

        admintools.unspam(thing, moderator_unbanned=not c.user_is_admin,
                          unbanner=c.user.name, train_spam=train_spam,
                          insert=insert)

        if isinstance(thing, (Link, Comment)):
            sr = thing.subreddit_slow
            action = 'approve' + thing.__class__.__name__.lower()
            ModAction.create(sr, c.user, action, **kw)

        if isinstance(thing, Comment) and insert:
            queries.renotify(thing)

    @require_oauth2_scope("modposts")
    @noresponse(VUser(), VModhash(),
                VSrCanBan('id'),
                thing=VByName('id'))
    @api_doc(api_section.moderation)
    def POST_ignore_reports(self, thing):
        """Prevent future reports on a thing from causing notifications.

        Any reports made about a thing after this flag is set on it will not
        cause notifications or make the thing show up in the various moderation
        listings.

        See also: [/api/unignore_reports](#POST_api_unignore_reports).

        """
        if not thing: return
        if thing._deleted: return
        if thing.ignore_reports: return

        # Don't allow user in timeout to ignore reports
        VNotInTimeout().run(action_name="ignorereports", target=thing)

        thing.ignore_reports = True
        thing._commit()

        sr = thing.subreddit_slow
        ModAction.create(sr, c.user, 'ignorereports', target=thing)

    @require_oauth2_scope("modposts")
    @noresponse(VUser(), VModhash(),
                VSrCanBan('id'),
                thing=VByName('id'))
    @api_doc(api_section.moderation)
    def POST_unignore_reports(self, thing):
        """Allow future reports on a thing to cause notifications.

        See also: [/api/ignore_reports](#POST_api_ignore_reports).

        """
        if not thing: return
        if thing._deleted: return
        if not thing.ignore_reports: return

        # Don't allow user in timeout to unignore reports
        VNotInTimeout().run(action_name="unignorereports", target=thing)

        thing.ignore_reports = False
        thing._commit()

        sr = thing.subreddit_slow
        ModAction.create(sr, c.user, 'unignorereports', target=thing)

    @require_oauth2_scope("modposts")
    @validatedForm(VUser(), VModhash(),
                   VCanDistinguish(('id', 'how')),
                   thing = VByName('id'),
                   how = VOneOf('how', ('yes','no','admin','special')),
                   # sticky=VBoolean('sticky', default=False),
                   )
    @api_doc(api_section.moderation)
    def POST_distinguish(self, form, jquery, thing, how):
        """Distinguish a thing's author with a sigil.

        This can be useful to draw attention to and confirm the identity of the
        user in the context of a link or comment of theirs. The options for
        distinguish are as follows:

        * `yes` - add a moderator distinguish (`[M]`). only if the user is a
                  moderator of the subreddit the thing is in.
        * `no` - remove any distinguishes.
        * `admin` - add an admin distinguish (`[A]`). admin accounts only.
        * `special` - add a user-specific distinguish. depends on user.

        The first time a top-level comment is moderator distinguished, the
        author of the link the comment is in reply to will get a notification
        in their inbox.

        """

        # To be added to API docs when fully enabled:
        #
        # `sticky` is a boolean flag for comments, which will stick the
        #  distingushed comment to the top of all comments threads. If a comment
        #  is marked sticky, it will override any other stickied comment for that
        #  link (as only one comment may be stickied at a time.) Only top-level
        #  comments may be stickied.

        if not thing:return

        # XXX: Temporary retrieval of sticky param down here to avoid it
        # showing up in API docs while in development. Move this to the
        # validatedForm above when live.
        sticky = False
        if feature.is_enabled('sticky_comments'):
            sticky_validator = VBoolean('sticky', default=False)
            sticky = sticky_validator.run(request.params.get('sticky'))

        if (feature.is_enabled('sticky_comments') and
                sticky and
                not isinstance(thing, Comment)):
            abort(400, "Only comments may be stickied from distinguish. To "
                       "sticky a link in a subreddit use set_subreddit_sticky."
                  )

        c.profilepage = request.params.get('profilepage') == 'True'
        log_modaction = True
        log_kw = {}
        send_message = False
        original = getattr(thing, 'distinguished', 'no')
        if how == original: # Distinguish unchanged
            log_modaction = False
        elif how in ('admin', 'special'): # Add admin/special
            log_modaction = False
            send_message = True
        elif (original in ('admin', 'special') and
                how == 'no'): # Remove admin/special
            log_modaction = False
        elif how == 'no': # From yes to no
            log_kw['details'] = 'remove'
        else: # From no to yes
            send_message = True

        if isinstance(thing, Comment):
            link = thing.link_slow

            # Send a message if this is a top-level comment on a submission or
            # comment that has disabled receiving inbox notifications of
            # replies, if it's the first distinguish for this comment, and if
            # the user isn't banned or blocked by the author (replying didn't
            # generate an inbox notification, send one now upon distinguishing
            # it)
            if not thing.parent_id:
                to = Account._byID(link.author_id, data=True)
                replies_enabled = link.sendreplies
            else:
                parent = Comment._byID(thing.parent_id, data=True)
                to = Account._byID(parent.author_id, data=True)
                replies_enabled = parent.sendreplies

            previously_distinguished = hasattr(thing, 'distinguished')
            user_can_notify = (not c.user._spam and
                               c.user._id not in to.enemies and
                               to.name != c.user.name)

            if (send_message and
                    not replies_enabled and
                    not previously_distinguished and
                    user_can_notify):
                inbox_rel = Inbox._add(to, thing, 'selfreply')
                queries.update_comment_notifications(thing, inbox_rel)

            # Sticky handling - done before commit so that if there is an error
            # setting sticky we don't distinguish. This ordering does leave the
            # potential for an erroneous sticky if there's a commit error on
            # distinguish, but a stickied comment that's not distinguished is
            # not the end of the world, and handling rollback would probably be
            # more error prone if we're hitting commit errors anyhow.
            if feature.is_enabled('sticky_comments'):
                try:
                    if not sticky or how == 'no':
                        # Un-distinguished a comment or sticky was False? Check
                        # to see if it was previously stickied and unsticky if
                        # so.
                        if link.sticky_comment_id == thing._id:
                            link.remove_sticky_comment(set_by=c.user)
                    elif sticky and how != 'no':
                        link.set_sticky_comment(thing, set_by=c.user)
                except RedditError as error:
                    abort_with_error(error, error.code or 400)

        thing.distinguished = how
        thing._commit()

        hooks.get_hook("thing.distinguish").call(thing=thing)

        wrapper = default_thing_wrapper(expand_children = True)
        w = wrap_links(thing, wrapper)
        jquery("body>div.content").replace_things(w, True, True)
        jquery("body>div.content .link .rank").hide()
        if log_modaction:
            sr = thing.subreddit_slow
            ModAction.create(sr, c.user, 'distinguish', target=thing, **log_kw)

    @require_oauth2_scope("save")
    @json_validate(VUser())
    @api_doc(api_section.links_and_comments)
    def GET_saved_categories(self, responder):
        """Get a list of categories in which things are currently saved.

        See also: [/api/save](#POST_api_save).

        """
        if not c.user.gold:
            abort(403)
        categories = LinkSavesByCategory.get_saved_categories(c.user)
        categories += CommentSavesByCategory.get_saved_categories(c.user)
        categories = sorted(set(categories), key=lambda name: name.lower())
        categories = [dict(category=category) for category in categories]
        return {'categories': categories}

    @require_oauth2_scope("save")
    @noresponse(
        VUser(),
        VModhash(),
        category=VSavedCategory('category'),
        thing=VByName('id'),
    )
    @api_doc(api_section.links_and_comments)
    def POST_save(self, thing, category):
        """Save a link or comment.

        Saved things are kept in the user's saved listing for later perusal.

        See also: [/api/unsave](#POST_api_unsave).

        """
        if not thing or not isinstance(thing, (Link, Comment)):
            abort(400)

        if category and not c.user.gold:
            category = None

        if ('BAD_SAVE_CATEGORY', 'category') in c.errors:
            abort(403)

        thing._save(c.user, category=category)

    @require_oauth2_scope("save")
    @noresponse(
        VUser(),
        VModhash(),
        thing=VByName('id'),
    )
    @api_doc(api_section.links_and_comments)
    def POST_unsave(self, thing):
        """Unsave a link or comment.

        This removes the thing from the user's saved listings as well.

        See also: [/api/save](#POST_api_save).

        """
        if not thing or not isinstance(thing, (Link, Comment)):
            abort(400)

        thing._unsave(c.user)

    def collapse_handler(self, things, collapse):
        if not things:
            return
        things = tup(things)
        srs = Subreddit._byID([t.sr_id for t in things if t.sr_id],
                              return_dict = True)
        for t in things:
            if hasattr(t, "to_id") and c.user._id == t.to_id:
                t.to_collapse = collapse
            elif hasattr(t, "author_id") and c.user._id == t.author_id:
                t.author_collapse = collapse
            elif isinstance(t, Message) and t.sr_id:
                if srs[t.sr_id].is_moderator(c.user):
                    t.to_collapse = collapse
            t._commit()

    @noresponse(VUser(),
                VModhash(),
                things = VByName('id', multiple = True))
    @api_doc(api_section.messages)
    def POST_collapse_message(self, things):
        """Collapse a message

        See also: [/api/uncollapse_message](#POST_uncollapse_message)

        """
        self.collapse_handler(things, True)

    @noresponse(VUser(),
                VModhash(),
                things = VByName('id', multiple = True))
    @api_doc(api_section.messages)
    def POST_uncollapse_message(self, things):
        """Uncollapse a message

        See also: [/api/collapse_message](#POST_collapse_message)

        """
        self.collapse_handler(things, False)

    @require_oauth2_scope("privatemessages")
    @noresponse(VUser(),
                VModhash(),
                things = VByName('id', multiple=True, limit=25))
    @api_doc(api_section.messages)
    def POST_unread_message(self, things):
        if not things:
            if (errors.TOO_MANY_THING_IDS, 'id') in c.errors:
                return abort(413)
            else:
                return abort(400)

        queries.unread_handler(things, c.user, unread=True)

    @require_oauth2_scope("privatemessages")
    @noresponse(VUser(),
                VModhash(),
                things = VByName('id', multiple=True, limit=25))
    @api_doc(api_section.messages)
    def POST_read_message(self, things):
        if not things:
            if (errors.TOO_MANY_THING_IDS, 'id') in c.errors:
                return abort(413)
            else:
                return abort(400)

        queries.unread_handler(things, c.user, unread=False)

    @require_oauth2_scope("privatemessages")
    @noresponse(VUser(),
                VModhash(),
                VRatelimit(rate_user=True, prefix="rate_read_all_", fatal=True))
    @api_doc(api_section.messages)
    def POST_read_all_messages(self):
        """Queue up marking all messages for a user as read.

        This may take some time, and returns 202 to acknowledge acceptance of
        the request.
        """
        amqp.add_item('mark_all_read', c.user._fullname)
        # Mark usage in the ratelimiter.
        VRatelimit.ratelimit(rate_user=True, prefix='rate_read_all_')

        return abort(202)

    @require_oauth2_scope("report")
    @noresponse(VUser(),
                VModhash(),
                links=VByName('id', thing_cls=Link, multiple=True, limit=50))
    @api_doc(api_section.links_and_comments)
    def POST_hide(self, links):
        """Hide a link.

        This removes it from the user's default view of subreddit listings.

        See also: [/api/unhide](#POST_api_unhide).

        """
        if not links:
            return abort(400)

        LinkHidesByAccount._hide(c.user, links)

    @require_oauth2_scope("report")
    @noresponse(VUser(),
                VModhash(),
                links=VByName('id', thing_cls=Link, multiple=True, limit=50))
    @api_doc(api_section.links_and_comments)
    def POST_unhide(self, links):
        """Unhide a link.

        See also: [/api/hide](#POST_api_hide).

        """
        if not links:
            return abort(400)

        LinkHidesByAccount._unhide(c.user, links)


    @csrf_exempt
    @validatedForm(VUser(),
                   parent = VByName('parent_id'))
    def POST_moremessages(self, form, jquery, parent):
        if not parent.can_view_slow():
            return abort(403, 'forbidden')

        if parent.sr_id:
            builder = SrMessageBuilder(parent.subreddit_slow,
                                       parent = parent, skip = False)
        else:
            builder = UserMessageBuilder(c.user, parent = parent, skip = False)

        listing = Listing(builder).listing()

        a = []
        for item in listing.things:
            a.append(item)
            if hasattr(item, "child"):
                for x in item.child.things:
                    a.append(x)

        for item in a:
            if hasattr(item, "child"):
                item.child = None

        jquery.things(parent._fullname).parent().replace_things(a, False, True)

    @require_oauth2_scope("read")
    @validatedForm(
        link=VByName('link_id', thing_cls=Link),
        sort=VMenu('morechildren', CommentSortMenu, remember=False),
        children=VCommentIDs('children'),
        mc_id=nop(
            "id",
            docs={"id": "(optional) id of the associated MoreChildren object"}),
    )
    @api_doc(api_section.links_and_comments)
    def GET_morechildren(self, form, jquery, link, sort, children, mc_id):
        """Retrieve additional comments omitted from a base comment tree.

        When a comment tree is rendered, the most relevant comments are
        selected for display first. Remaining comments are stubbed out with
        "MoreComments" links. This API call is used to retrieve the additional
        comments represented by those stubs, up to 20 at a time.

        The two core parameters required are `link` and `children`.  `link` is
        the fullname of the link whose comments are being fetched. `children`
        is a comma-delimited list of comment ID36s that need to be fetched.

        If `id` is passed, it should be the ID of the MoreComments object this
        call is replacing. This is needed only for the HTML UI's purposes and
        is optional otherwise.

        **NOTE:** you may only make one request at a time to this API endpoint.
        Higher concurrency will result in an error being returned.

        """

        CHILD_FETCH_COUNT = 20

        lock = None
        if c.user_is_loggedin:
            lock = g.make_lock("morechildren", "morechildren-" + c.user.name,
                               timeout=0)
            try:
                lock.acquire()
            except TimeoutExpired:
                abort(429)

        try:
            if not link or not link.subreddit_slow.can_view(c.user):
                return abort(403,'forbidden')

            if children:
                children = list(set(children))
                builder = CommentBuilder(link, CommentSortMenu.operator(sort),
                                         children=children,
                                         num=CHILD_FETCH_COUNT)
                listing = Listing(builder, nextprev = False)
                items = listing.get_items()
                def _children(cur_items):
                    items = []
                    for cm in cur_items:
                        items.append(cm)
                        if hasattr(cm, 'child'):
                            if hasattr(cm.child, 'things'):
                                items.extend(_children(cm.child.things))
                                cm.child = None
                            else:
                                items.append(cm.child)

                    return items
                # assumes there is at least one child
                # a = _children(items[0].child.things)
                a = []
                for item in items:
                    a.append(item)
                    if hasattr(item, 'child'):
                        a.extend(_children(item.child.things))
                        item.child = None

                # the result is not always sufficient to replace the
                # morechildren link
                jquery.things(str(mc_id)).remove()
                jquery.insert_things(a, append = True)
        finally:
            if lock:
                lock.release()

    @csrf_exempt
    @require_oauth2_scope("read")
    def POST_morechildren(self):
        """Wrapper around `GET_morechildren` for backwards-compatibility"""
        return self.GET_morechildren()

    @validatedForm(VUser(),
                   VModhash(),
                   code=VPrintable("code", 30))
    def POST_claimgold(self, form, jquery, code):
        status = ''
        if not code:
            c.errors.add(errors.NO_TEXT, field = "code")
            form.has_errors("code", errors.NO_TEXT)
            return

        rv = claim_gold(code, c.user._id)

        if rv is None:
            c.errors.add(errors.INVALID_CODE, field = "code")
        elif rv == "already claimed":
            c.errors.add(errors.CLAIMED_CODE, field = "code")
        else:
            days, subscr_id = rv
            if days <= 0:
                raise ValueError("days = %r?" % days)

            if subscr_id:
                c.user.gold_subscr_id = subscr_id

            if code.startswith("cr_"):
                c.user.gold_creddits += int(days / 31)
                c.user._commit()
                status = 'claimed-creddits'
            else:
                # send the user a message if they don't already have gold
                if not c.user.gold:
                    subject = "You claimed a reddit gold code!"
                    message = strings.gold_claimed_code
                    message += "\n\n" + strings.gold_benefits_msg

                    if g.lounge_reddit:
                        message += "\n\n" + strings.lounge_msg
                    message = append_random_bottlecap_phrase(message)

                    try:
                        send_system_message(c.user, subject, message,
                                            distinguished='gold-auto')
                    except MessageError:
                        g.log.error('claimgold: could not send system message')

                admintools.adjust_gold_expiration(c.user, days=days)

                status = 'claimed-gold'
                jquery(".lounge").show()

        # Activate any errors we just manually set
        if not form.has_errors("code", errors.INVALID_CODE, errors.CLAIMED_CODE,
                               errors.NO_TEXT):
            form.redirect("/gold/thanks?v=%s" % status)

    @csrf_exempt
    @validatedForm(
        VRatelimit(rate_ip=True, prefix="rate_password_"),
        user=VUserWithEmail('name'),
    )
    def POST_password(self, form, jquery, user):
        """Send password reset email."""
        def _event(error):
            email = user.email if user else None
            email_verified = bool(user.email_verified) if email else None
            g.events.login_event(
                'password_reset',
                error_msg=error,
                user_name=request.POST.get('name'),
                email=email,
                email_verified=email_verified,
                request=request,
                context=c)

        if form.has_errors('name', errors.USER_DOESNT_EXIST):
            _event(error='USER_DOESNT_EXIST')

        elif form.has_errors('name', errors.NO_EMAIL_FOR_USER):
            _event(error='NO_EMAIL_FOR_USER')

        elif form.has_errors('ratelimit', errors.RATELIMIT):
            _event(error='RATELIMIT')

        else:
            VRatelimit.ratelimit(rate_ip=True, prefix="rate_password_")
            if emailer.password_email(user):
                _event(error=None)
                form.set_text(".status",
                      _("an email will be sent to that account's address shortly"))
            else:
                _event(error='RESET_LIMIT')
                form.set_text(".status", _("try again tomorrow"))

    @csrf_exempt
    @validatedForm(token=VOneTimeToken(PasswordResetToken, "key"),
                   password=VPasswordChange(["passwd", "passwd2"]))
    def POST_resetpassword(self, form, jquery, token, password):
        # was the token invalid or has it expired?
        if not token:
            form.redirect("/password?expired=true")
            return

        # did they fill out the password form correctly?
        form.has_errors("passwd",  errors.BAD_PASSWORD)
        form.has_errors("passwd2", errors.BAD_PASSWORD_MATCH)
        if form.has_error():
            return

        # at this point, we should mark the token used since it's either
        # valid now or will never be valid again.
        token.consume()

        # load up the user and check that things haven't changed
        user = Account._by_fullname(token.user_id)
        if not token.valid_for_user(user):
            form.redirect('/password?expired=true')
            return

        # Prevent banned users from resetting, and thereby logging in
        if user._banned:
            return

        # successfully entered user name and valid new password
        change_password(user, password)
        if user.email:
            emailer.password_change_email(user)
        g.log.warning("%s did a password reset for %s via %s",
                      request.ip, user.name, token._id)

        # add this ip to the user's account so they can sign in even if
        # their account is being brute forced by a third party.
        set_account_ip(user._id, request.ip, c.start_time)

        # if the token is for the current user, their cookies will be
        # invalidated and they'll have to log in again.
        if not c.user_is_loggedin or c.user._fullname == token.user_id:
            jquery.redirect('/login')

        form.set_text(".status", _("password updated"))

    @require_oauth2_scope("subscribe")
    @noresponse(VUser(),
                VModhash(),
                action = VOneOf('action', ('sub', 'unsub')),
                sr = VSubscribeSR('sr', 'sr_name'))
    @api_doc(api_section.subreddits)
    def POST_subscribe(self, action, sr):
        """Subscribe to or unsubscribe from a subreddit.

        To subscribe, `action` should be `sub`. To unsubscribe, `action` should
        be `unsub`. The user must have access to the subreddit to be able to
        subscribe to it.

        See also: [/subreddits/mine/](#GET_subreddits_mine_{where}).

        """

        if not sr:
            return abort(404, 'not found')
        elif action == "sub" and not sr.can_view(c.user):
            return abort(403, 'permission denied')
        elif isinstance(sr, FakeSubreddit):
            return abort(403, 'permission denied')

        Subreddit.subscribe_defaults(c.user)

        if action == "sub":
            SubredditParticipationByAccount.mark_participated(c.user, sr)

            if not sr.is_subscriber(c.user):
                sr.add_subscriber(c.user)
        else:
            if sr.is_subscriber(c.user):
                sr.remove_subscriber(c.user)
            else:
                # tried to unsubscribe but user was not subscribed
                return abort(404, 'not found')
        sr.update_search_index(boost_only=True)

    @validatedForm(
        VAdmin(),
        VModhash(),
        subreddit=VByName('subreddit'),
        quarantine=VBoolean('quarantine'),
        subject=VLength('subject', 1000),
        body=VMarkdownLength('body', max_length=10000),
    )
    def POST_quarantine(self, form, jquery, subreddit, quarantine, subject, body):
        if subreddit.quarantine == quarantine:
            return

        subreddit.quarantine = quarantine
        subreddit._commit()
        system_user = Account.system_user()
        kw = dict(
            sr_id36=subreddit._id36,
            mod_id36=system_user._id36,
            action="editsettings",
            details="quarantine",
        )
        ma = ModAction(**kw)
        ma._commit()

        if config['r2.import_private']:
            from r2admin.lib.admin_utils import record_admin_event
            if quarantine:
                record_admin_event('quarantine', page="subreddit_page",
                    target_thing=subreddit)
            else:
                record_admin_event('unquarantine', page="subreddit_page",
                    target_thing=subreddit)

        if body.strip():
            send_system_message(subreddit, subject, body,
                distinguished='admin', repliable=False)

        # Refresh the CSS since images aren't allowed
        stylesheet_contents = subreddit.fetch_stylesheet_source()
        css_errors, parsed = subreddit.parse_css(stylesheet_contents)
        subreddit.change_css(stylesheet_contents, parsed, author=system_user)
        jquery.refresh()

    @require_oauth2_scope("subscribe")
    @noresponse(
        VUser(),
        VModhash(),
        sr=VSRByName('sr_name'),
    )
    def POST_quarantine_optout(self, sr):
        """Opt out from a quarantined subreddit"""
        if not sr:
            return abort(404, 'not found')
        else:
            g.events.quarantine_event('quarantine_opt_out', sr,
                request=request, context=c)
            QuarantinedSubredditOptInsByAccount.opt_out(c.user, sr)
        return self.redirect('/')

    @require_oauth2_scope("subscribe")
    @noresponse(
        VUser(),
        VModhash(),
        sr=VSRByName('sr_name'),
    )
    def POST_quarantine_optin(self, sr):
        """Opt in to a quarantined subreddit"""
        if not sr:
            return abort(404, 'not found')
        elif not c.user.email_verified:
            return abort(403, 'email not verified')
        else:
            g.events.quarantine_event('quarantine_opt_in', sr,
                request=request, context=c)
            QuarantinedSubredditOptInsByAccount.opt_in(c.user, sr)
        return self.redirect('/r/%s' % sr.name)

    @validatedForm(VAdmin(),
                   VModhash(),
                   hexkey=VLength("hexkey", max_length=32),
                   nickname=VLength("nickname", max_length = 1000),
                   status = VOneOf("status",
                      ("new", "severe", "interesting", "normal", "fixed")))
    def POST_edit_error(self, form, jquery, hexkey, nickname, status):
        if form.has_errors(("hexkey", "nickname", "status"),
                           errors.NO_TEXT, errors.INVALID_OPTION):
            pass

        if form.has_error():
            return

        key = "error_nickname-%s" % str(hexkey)
        g.hardcache.set(key, nickname, 86400 * 365)

        key = "error_status-%s" % str(hexkey)
        g.hardcache.set(key, status, 86400 * 365)

        form.set_text(".status", _('saved'))

    @validatedForm(VAdmin(),
                   VModhash(),
                   award=VByName("fullname"),
                   colliding_award=VAwardByCodename(("codename", "fullname")),
                   codename=VLength("codename", max_length = 100),
                   title=VLength("title", max_length = 100),
                   awardtype=VOneOf("awardtype",
                                    ("regular", "manual", "invisible")),
                   api_ok=VBoolean("api_ok"),
                   imgurl=VLength("imgurl", max_length = 1000))
    def POST_editaward(self, form, jquery, award, colliding_award, codename,
                       title, awardtype, api_ok, imgurl):
        if form.has_errors(("codename", "title", "awardtype", "imgurl"),
                           errors.NO_TEXT):
            pass

        if awardtype is None:
            form.set_text(".status", "bad awardtype")
            return

        if form.has_errors(("codename"), errors.INVALID_OPTION):
            form.set_text(".status", "some other award has that codename")
            pass

        url_ok = True

        if not imgurl.startswith("//"):
            url_ok = False
            form.set_text(".status", "the url must be protocol-relative")

        try:
            imgurl % 1
        except TypeError:
            url_ok = False
            form.set_text(".status", "the url must have a %d for size")

        if not url_ok:
            c.errors.add(errors.BAD_URL, field="imgurl")
            form.has_errors("imgurl", errors.BAD_URL)

        if form.has_error():
            return

        if award is None:
            Award._new(codename, title, awardtype, imgurl, api_ok)
            form.set_text(".status", "saved. reload to see it.")
            return

        award.codename = codename
        award.title = title
        award.awardtype = awardtype
        award.imgurl = imgurl
        award.api_ok = api_ok
        award._commit()
        form.set_text(".status", _('saved'))

    @require_oauth2_scope("modflair")
    @validatedForm(VSrModerator(perms='flair'),
                   VModhash(),
                   user = VFlairAccount("name"),
                   link = VFlairLink('link'),
                   text = VFlairText("text"),
                   css_class = VFlairCss("css_class"))
    @api_doc(api_section.flair, uses_site=True)
    def POST_flair(self, form, jquery, user, link, text, css_class):
        if form.has_errors('css_class', errors.BAD_CSS_NAME):
            form.set_text(".status:first", _('invalid css class'))
            return
        if form.has_errors('css_class', errors.TOO_MUCH_FLAIR_CSS):
            form.set_text(".status:first", _('too many css classes'))
            return

        if link:
            if form.has_errors("link", errors.BAD_FLAIR_TARGET):
                return

            if not c.user_is_admin and not link.can_flair_slow(c.user):
                abort(403)

            # If the user is in timeout, don't let them set flair
            VNotInTimeout().run(action_name="editflair", details_text="set",
                target=link)

            link.set_flair(text, css_class, set_by=c.user)
        else:
            if form.has_errors("name", errors.BAD_FLAIR_TARGET):
                return

            # If the user is in timeout, don't let them set flair
            VNotInTimeout().run(action_name="editflair", details_text="set",
                target=user)

            user.set_flair(c.site, text, css_class, set_by=c.user)

            # XXX: this is still gross with all the UI code in here
            if not text and not css_class:
                jquery('#flairrow_%s' % user._id36).hide()
            elif not c.site.is_flair(user):
                jquery.redirect('?name=%s' % user.name)
                return

            wrapped_user = WrappedUser(
                user, force_show_flair=True, include_flair_selector=True)
            rendered = wrapped_user.render(style='html')
            jquery('.tagline .flairselectable.id-%s'
                % user._fullname).parent().html(rendered)
            jquery('input[name="text"]').data('saved', text)
            jquery('input[name="css_class"]').data('saved', css_class)
            form.set_text('.status', _('saved'))

    @require_oauth2_scope("modflair")
    @validatedForm(
        VSrModerator(perms='flair'),
        VModhash(),
        user=VFlairAccount("name"),
    )
    @api_doc(api_section.flair, uses_site=True)
    def POST_deleteflair(self, form, jquery, user):
        if form.has_errors('name', errors.USER_DOESNT_EXIST, errors.NO_USER):
            return

        VNotInTimeout().run(action_name="editflair", details_text="delete",
            target=user)
        user.set_flair(c.site, None, None, set_by=c.user)

        jquery('#flairrow_%s' % user._id36).remove()
        unflair = WrappedUser(
            user, include_flair_selector=True).render(style='html')
        jquery('.tagline .id-%s' % user._fullname).parent().html(unflair)

    @require_oauth2_scope("modflair")
    @validate(
        VSrModerator(perms='flair'),
        VModhash(),
        VNotInTimeout(),
        flair_csv=nop("flair_csv",
            docs={"flair_csv": "comma-seperated flair information"}),
    )
    @api_doc(api_section.flair, uses_site=True)
    def POST_flaircsv(self, flair_csv):
        """Change the flair of multiple users in the same subreddit with a
        single API call.

        Requires a string 'flair_csv' which has up to 100 lines of the form
        '`user`,`flairtext`,`cssclass`' (Lines beyond the 100th are ignored).

        If both `cssclass` and `flairtext` are the empty string for a given
        `user`, instead clears that user's flair.

        Returns an array of objects indicating if each flair setting was 
        applied, or a reason for the failure.

        """

        if not flair_csv:
            return
        
        limit = 100  # max of 100 flair settings per call
        results = FlairCsv()
        # encode to UTF-8, since csv module doesn't fully support unicode
        infile = csv.reader(flair_csv.strip().encode('utf-8').split('\n'))
        for i, row in enumerate(infile):
            line_result = results.add_line()
            line_no = i + 1
            if line_no > limit:
                line_result.error('row',
                                  'limit of %d rows per call reached' % limit)
                break

            try:
                name, text, css_class = row
            except ValueError:
                line_result.error('row', 'improperly formatted row, ignoring')
                continue

            user = VFlairAccount('name').run(name)
            if not user:
                line_result.error('user',
                                  "unable to resolve user `%s', ignoring"
                                  % name)
                continue

            orig_text = text
            text = VFlairText('text').run(orig_text)
            if text and orig_text and len(text) < len(orig_text):
                line_result.warn('text',
                                 'truncating flair text to %d chars'
                                 % len(text))

            if css_class and not VFlairCss('css_class').run(css_class):
                line_result.error('css',
                                  "invalid css class `%s', ignoring"
                                  % css_class)
                continue

            # all validation passed, enflair the user
            user.set_flair(
                c.site, text, css_class, set_by=c.user, log_details="csv")

            if text or css_class:
                mode = 'added'
            else:
                mode = 'removed'
            line_result.status = '%s flair for user %s' % (mode, user.name)
            line_result.ok = True

        return BoringPage(_("API"), content = results).render()

    @require_oauth2_scope("flair")
    @validatedForm(VUser(),
                   VModhash(),
                   flair_enabled = VBoolean("flair_enabled"))
    @api_doc(api_section.flair, uses_site=True)
    def POST_setflairenabled(self, form, jquery, flair_enabled):
        setattr(c.user, 'flair_%s_enabled' % c.site._id, flair_enabled)
        c.user._commit()
        jquery.refresh()

    @require_oauth2_scope("modflair")
    @validatedForm(
        VSrModerator(perms='flair'),
        VModhash(),
        flair_enabled=VBoolean("flair_enabled"),
        flair_position=VOneOf("flair_position", ("left", "right")),
        link_flair_position=VOneOf("link_flair_position",
            ("", "left", "right")),
        flair_self_assign_enabled=VBoolean("flair_self_assign_enabled"),
        link_flair_self_assign_enabled =
            VBoolean("link_flair_self_assign_enabled"),
        timeout=VNotInTimeout(),
    )
    @api_doc(api_section.flair, uses_site=True)
    def POST_flairconfig(self, form, jquery, flair_enabled, flair_position,
            link_flair_position, flair_self_assign_enabled,
            link_flair_self_assign_enabled, timeout):
        if c.site.flair_enabled != flair_enabled:
            c.site.flair_enabled = flair_enabled
            ModAction.create(c.site, c.user, action='editflair',
                             details='flair_enabled')
        if c.site.flair_position != flair_position:
            c.site.flair_position = flair_position
            ModAction.create(c.site, c.user, action='editflair',
                             details='flair_position')
        if c.site.link_flair_position != link_flair_position:
            c.site.link_flair_position = link_flair_position
            ModAction.create(c.site, c.user, action='editflair',
                             details='link_flair_position')
        if c.site.flair_self_assign_enabled != flair_self_assign_enabled:
            c.site.flair_self_assign_enabled = flair_self_assign_enabled
            ModAction.create(c.site, c.user, action='editflair',
                             details='flair_self_enabled')
        if (c.site.link_flair_self_assign_enabled
            != link_flair_self_assign_enabled):
            c.site.link_flair_self_assign_enabled = (
                link_flair_self_assign_enabled)
            ModAction.create(c.site, c.user, action='editflair',
                             details='link_flair_self_enabled')
        c.site._commit()
        jquery.refresh()

    @require_oauth2_scope("modflair")
    @paginated_listing(max_page_size=1000)
    @validate(
        VSrModerator(perms='flair'),
        user=VFlairAccount('name'),
    )
    @api_doc(api_section.flair, uses_site=True)
    def GET_flairlist(self, num, after, reverse, count, user):
        if user and user._deleted:
            return self.abort403()

        # Don't allow users in timeout to modify flairs
        VNotInTimeout().run(action_name="editflair", details_text="flair_list",
            target=user)

        flair = FlairList(num, after, reverse, '', user)
        return BoringPage(_("API"), content = flair).render()

    @require_oauth2_scope("modflair")
    @validatedForm(VSrModerator(perms='flair'),
                   VModhash(),
                   flair_template = VFlairTemplateByID('flair_template_id'),
                   text = VFlairText('text'),
                   css_class = VFlairCss('css_class'),
                   text_editable = VBoolean('text_editable'),
                   flair_type = VOneOf('flair_type', (USER_FLAIR, LINK_FLAIR),
                                       default=USER_FLAIR))
    @api_doc(api_section.flair, uses_site=True)
    def POST_flairtemplate(self, form, jquery, flair_template, text,
                           css_class, text_editable, flair_type):
        if text is None:
            text = ''
        if css_class is None:
            css_class = ''

        # Check validation.
        if form.has_errors('css_class', errors.BAD_CSS_NAME):
            form.set_text(".status:first", _('invalid css class'))
            return
        if form.has_errors('css_class', errors.TOO_MUCH_FLAIR_CSS):
            form.set_text(".status:first", _('too many css classes'))
            return

        # Don't allow users in timeout to modify the flair templates
        VNotInTimeout().run(action_name="editflair",
            details_text="flair_template", target=c.site)

        # Load flair template thing.
        if flair_template:
            flair_template.text = text
            flair_template.css_class = css_class
            flair_template.text_editable = text_editable
            flair_template._commit()
            new = False
        else:
            try:
                flair_template = FlairTemplateBySubredditIndex.create_template(
                    c.site._id, text=text, css_class=css_class,
                    text_editable=text_editable,
                    flair_type=flair_type)
            except OverflowError:
                form.set_text(".status:first", _('max flair templates reached'))
                return

            new = True

        # Push changes back to client.
        if new:
            empty_ids = {
                USER_FLAIR: '#empty-user-flair-template',
                LINK_FLAIR: '#empty-link-flair-template',
            }
            empty_id = empty_ids[flair_type]
            jquery(empty_id).before(
                FlairTemplateEditor(flair_template, flair_type)
                .render(style='html'))
            empty_template = FlairTemplate()
            empty_template._committed = True  # to disable unnecessary warning
            jquery(empty_id).html(
                FlairTemplateEditor(empty_template, flair_type)
                .render(style='html'))
            form.set_text('.status', _('saved'))
        else:
            jquery('#%s' % flair_template._id).html(
                FlairTemplateEditor(flair_template, flair_type)
                .render(style='html'))
            form.set_text('.status', _('saved'))
            jquery('input[name="text"]').data('saved', text)
            jquery('input[name="css_class"]').data('saved', css_class)
        ModAction.create(c.site, c.user, action='editflair',
                             details='flair_template')

    @require_oauth2_scope("modflair")
    @validatedForm(
        VSrModerator(perms='flair'),
        VModhash(),
        flair_template=VFlairTemplateByID('flair_template_id'),
    )
    @api_doc(api_section.flair, uses_site=True)
    def POST_deleteflairtemplate(self, form, jquery, flair_template):
        if not flair_template:
            return self.abort404()

        VNotInTimeout().run(action_name="editflair",
            details_text="flair_delete_template", target=c.site)
        idx = FlairTemplateBySubredditIndex.by_sr(c.site._id)
        if idx.delete_by_id(flair_template._id):
            jquery('#%s' % flair_template._id).parent().remove()
            ModAction.create(c.site, c.user, action='editflair',
                             details='flair_delete_template')

    @require_oauth2_scope("modflair")
    @validatedForm(
        VSrModerator(perms='flair'),
        VModhash(),
        VNotInTimeout(),
        flair_type=VOneOf('flair_type', (USER_FLAIR, LINK_FLAIR),
            default=USER_FLAIR),
    )
    @api_doc(api_section.flair, uses_site=True)
    def POST_clearflairtemplates(self, form, jquery, flair_type):
        FlairTemplateBySubredditIndex.clear(c.site._id, flair_type=flair_type)
        jquery.refresh()
        ModAction.create(c.site, c.user, action='editflair',
                         details='flair_clear_template')

    @csrf_exempt
    @require_oauth2_scope("flair")
    @validate(
        VUser(),
        user=VFlairAccount('name'),
        link=VFlairLink('link'),
    )
    @api_doc(api_section.flair, uses_site=True)
    def POST_flairselector(self, user, link):
        """Return information about a users's flair options.

        If `link` is given, return link flair options.
        Otherwise, return user flair options for this subreddit.

        The logged in user's flair is also returned.
        Subreddit moderators may give a user by `name` to instead
        retrieve that user's flair.

        """

        if link:
            if not (c.user_is_admin or link.can_flair_slow(c.user)):
                abort(403)

            site = link.subreddit_slow
            user = c.user
            return FlairSelector(user, site, link).render()
        else:
            if isinstance(c.site, FakeSubreddit):
                abort(403)
            else:
                site = c.site

            if user:
                if (user != c.user and
                        not c.user_is_admin and
                        not site.is_moderator_with_perms(c.user, 'flair')):
                    abort(403)
            else:
                user = c.user

            if user._deleted:
                abort(403)

            return FlairSelector(user, site).render()

    @require_oauth2_scope("flair")
    @validatedForm(VUser(),
                   VModhash(),
                   user = VFlairAccount('name'),
                   link = VFlairLink('link'),
                   flair_template_id = nop('flair_template_id'),
                   text = VFlairText('text'))
    @api_doc(api_section.flair, uses_site=True)
    def POST_selectflair(self, form, jquery, user, link, flair_template_id,
                         text):
        if link:
            flair_type = LINK_FLAIR
            subreddit = link.subreddit_slow
            if not (c.user_is_admin or link.can_flair_slow(c.user)):
                abort(403)
        elif user:
            flair_type = USER_FLAIR
            subreddit = c.site
            if not (c.user_is_admin or user.can_flair_in_sr(c.user, subreddit)):
                abort(403)
        else:
            return self.abort404()

        if flair_template_id:
            try:
                flair_template = FlairTemplateBySubredditIndex.get_template(
                    subreddit._id, flair_template_id, flair_type=flair_type)
            except NotFound:
                # TODO: serve error to client
                g.log.debug('invalid flair template for subreddit %s', subreddit._id)
                return

            text_editable = flair_template.text_editable

            # Ignore given text if user doesn't have permission to customize it.
            if not (text_editable or
                        c.user_is_admin or
                        subreddit.is_moderator_with_perms(c.user, "flair")):
                text = None

            if not text:
                text = flair_template.text

            css_class = flair_template.css_class
        else:
            flair_template = None
            text_editable = False
            text = None
            css_class = None

        if flair_type == LINK_FLAIR:
            VNotInTimeout().run(action_name="editflair", details_text="select",
                target=link)
            link.set_flair(text, css_class, set_by=c.user)

            # XXX: gross UI code
            # Push some client-side updates back to the browser.

            jquery('.id-%s' % link._fullname).removeLinkFlairClass()
            jquery('.id-%s .entry .linkflairlabel' % link._fullname).remove()
            title_path = '.id-%s .entry > .title > .title' % link._fullname

            # TODO: move this to a template
            if flair_template:
                classes = ' '.join('linkflair-' + c for c in css_class.split())
                jquery('.id-%s' % link._fullname).addClass('linkflair').addClass(classes)
                flair = format_html('<span class="linkflairlabel">%s</span>', text)

                if subreddit.link_flair_position == 'left':
                    jquery(title_path).before(flair)
                elif subreddit.link_flair_position == 'right':
                    jquery(title_path).after(flair)

            # TODO: close the selector popup more gracefully
            jquery('body').click()
        else:
            VNotInTimeout().run(action_name="editflair", details_text="select",
                target=user)
            user.set_flair(subreddit, text, css_class, set_by=c.user)

            # XXX: gross UI code
            # Push some client-side updates back to the browser.
            u = WrappedUser(user, force_show_flair=True,
                            flair_text_editable=text_editable,
                            include_flair_selector=True)
            flair = u.render(style='html')
            jquery('.tagline .flairselectable.id-%s'
                % user._fullname).parent().html(flair)
            jquery('#flairrow_%s input[name="text"]' % user._id36).data(
                'saved', text).val(text)
            jquery('#flairrow_%s input[name="css_class"]' % user._id36).data(
                'saved', css_class).val(css_class)

    @validatedForm(
        VUser(),
        VModhash(),
        sr_style_enabled=VBoolean("sr_style_enabled")
    )
    def POST_set_sr_style_enabled(self, form, jquery, sr_style_enabled):
        """Update enabling of individual sr themes; refresh the page style"""
        if feature.is_enabled('stylesheets_everywhere'):
            c.user.set_subreddit_style(c.site, sr_style_enabled)
            c.can_apply_styles = True
            sr = DefaultSR()

            if sr_style_enabled:
                sr = c.site
            elif (c.user.pref_default_theme_sr and
                    feature.is_enabled('stylesheets_everywhere')):
                sr = Subreddit._by_name(c.user.pref_default_theme_sr)
                if (not sr.can_view(c.user) or
                        not c.user.pref_enable_default_themes):
                    sr = DefaultSR()
            sr_stylesheet_url = Reddit.get_subreddit_stylesheet_url(sr)
            if not sr_stylesheet_url:
                sr_stylesheet_url = ""
                c.can_apply_styles = False

            jquery.apply_stylesheet_url(sr_stylesheet_url, sr_style_enabled)

            if not sr.header or header_url(sr.header) == g.default_header_url:
                jquery.remove_header_image();
            else:
                jquery.apply_header_image(header_url(sr.header),
                    sr.header_size, sr.header_title)

    @validatedForm(secret_used=VAdminOrAdminSecret("secret"),
                   award=VByName("fullname"),
                   description=VLength("description", max_length=1000),
                   url=VLength("url", max_length=1000),
                   recipient=VExistingUname("recipient"))
    def POST_givetrophy(self, form, jquery, secret_used, award, description,
                        url, recipient):
        if form.has_errors("recipient", errors.USER_DOESNT_EXIST,
                                        errors.NO_USER):
            pass

        if form.has_errors("fullname", errors.NO_TEXT, errors.NO_THING_ID):
            pass

        if secret_used and not award.api_ok:
            c.errors.add(errors.NO_API, field='secret')
            form.has_errors('secret', errors.NO_API)

        if form.has_error():
            return

        t = Trophy._new(recipient, award, description=description, url=url)

        form.set_text(".status", _('saved'))
        form._send_data(trophy_fn=t._id36)

    @validatedForm(secret_used=VAdminOrAdminSecret("secret"),
                   trophy = VTrophy("trophy_fn"))
    def POST_removetrophy(self, form, jquery, secret_used, trophy):
        if not trophy:
            return self.abort404()
        recipient = trophy._thing1
        award = trophy._thing2
        if secret_used and not award.api_ok:
            c.errors.add(errors.NO_API, field='secret')
            form.has_errors('secret', errors.NO_API)
        
        if form.has_error():
            return

        trophy._delete()
        Trophy.by_account(recipient, _update=True)
        Trophy.by_award(award, _update=True)

    @validatedForm(
        VAdmin(),
        VModhash(),
        recipient=VExistingUname("recipient"),
        num_creddits=VInt('num_creddits', num_default=0),
    )
    def POST_givecreddits(self, form, jquery, recipient, num_creddits):
        if form.has_errors("recipient",
                           errors.USER_DOESNT_EXIST, errors.NO_USER):
            return

        with creddits_lock(recipient):
            recipient.gold_creddits += num_creddits
            # make sure it doesn't go into the negative
            recipient.gold_creddits = max(0, recipient.gold_creddits)
            recipient._commit()

        form.set_text(".status", _('saved'))

    @validatedForm(
        VAdmin(),
        VModhash(),
        recipient=VExistingUname("recipient"),
        num_months=VInt('num_months', num_default=0),
    )
    def POST_givegold(self, form, jquery, recipient, num_months):
        if form.has_errors("recipient",
                           errors.USER_DOESNT_EXIST, errors.NO_USER):
            return
        
        if not recipient.gold and num_months < 0:
            form.set_text(".status", _('no gold to take'))
            return

        admintools.adjust_gold_expiration(recipient, months=num_months)
        form.set_text(".status", _('saved'))

    @noresponse(VUser(),
                VModhash(),
                ui_elem=VOneOf('id', ('organic',)))
    def POST_disable_ui(self, ui_elem):
        if ui_elem:
            pref = "pref_%s" % ui_elem
            if getattr(c.user, pref):
                setattr(c.user, "pref_" + ui_elem, False)
                c.user._commit()

    @noresponse(VUser(),
                VModhash(),
                show_nsfw_media=VBoolean("show_nsfw_media"))
    def POST_set_nsfw_media_pref(self, show_nsfw_media):
        changed = False

        if show_nsfw_media is not None:
            no_profanity = not show_nsfw_media
            if c.user.pref_no_profanity != no_profanity:
                c.user.pref_no_profanity = no_profanity
                changed = True

        if not c.user.nsfw_media_acknowledged:
            c.user.nsfw_media_acknowledged = True
            changed = True

        if changed:
            c.user._commit()

    @validatedForm(type = VOneOf('type', ('click'), default = 'click'),
                   links = VByName('ids', thing_cls = Link, multiple = True))
    def GET_gadget(self, form, jquery, type, links):
        if not links and type == 'click':
            # malformed cookie, clear it out
            set_user_cookie('recentclicks2', '')

        if not links:
            return

        content = ClickGadget(links).make_content()

        jquery('.gadget').show().find('.click-gadget').html(
            spaceCompress(content))

    @csrf_exempt
    @require_oauth2_scope("read")
    @json_validate(query=VPrintable('query', max_length=50),
                   include_over_18=VBoolean('include_over_18', default=True),
                   exact=VBoolean('exact', default=False))
    @api_doc(api_section.subreddits)
    def POST_search_reddit_names(self, responder, query, include_over_18, exact):
        """List subreddit names that begin with a query string.

        Subreddits whose names begin with `query` will be returned. If
        `include_over_18` is false, subreddits with over-18 content
        restrictions will be filtered from the results.

        If `exact` is true, only an exact match will be returned.
        """
        if query:
            query = sr_path_rx.sub('\g<name>', query.strip())

        names = []
        if query and exact:
            try:
                sr = Subreddit._by_name(query.strip())
            except NotFound:
                self.abort404()
            else:
                # not respecting include_over_18 for exact match
                names = [sr.name]
        elif query:
            names = search_reddits(query, include_over_18)

        return {'names': names}

    @validate(link=VByName('link_id', thing_cls=Link))
    def GET_expando(self, link):
        if not link:
            abort(404, 'not found')

        # pass through wrap_links/IDBuilder to ensure the user can view the link
        listing = wrap_links(link)
        try:
            wrapped_link = listing.things[0]
        except IndexError:
            wrapped_link = None

        if wrapped_link and wrapped_link.link_child:
            content = wrapped_link.link_child.content()
            return websafe(spaceCompress(content))
        else:
            abort(404, 'not found')

    @csrf_exempt
    def POST_expando(self):
        return self.GET_expando()

    @validatedForm(
        VUser(),
        VModhash(),
        VVerifyPassword("password", fatal=False),
        VOneTimePassword("otp",
                         required=not g.disable_require_admin_otp),
        remember=VBoolean("remember"),
        dest=VDestination(),
    )
    def POST_adminon(self, form, jquery, remember, dest):
        if c.user.name not in g.admins:
            self.abort403()

        if form.has_errors('password', errors.WRONG_PASSWORD):
            return

        if form.has_errors("otp", errors.WRONG_PASSWORD,
                                  errors.NO_OTP_SECRET,
                                  errors.RATELIMIT):
            return

        if remember:
            self.remember_otp(c.user)

        self.enable_admin_mode(c.user)
        form.redirect(dest)

    @validatedForm(
        VUser(),
        VModhash(),
        VVerifyPassword("password", fatal=False),
    )
    def POST_generate_otp_secret(self, form, jquery):
        if form.has_errors("password", errors.WRONG_PASSWORD):
            return

        if c.user.otp_secret:
            c.errors.add(errors.OTP_ALREADY_ENABLED, field="password")
            form.has_errors("password", errors.OTP_ALREADY_ENABLED)
            return

        secret = totp.generate_secret()
        g.gencache.set("otp:secret_" + c.user._id36, secret, time=300)
        jquery("body").make_totp_qrcode(secret)

    @validatedForm(VUser(),
                   VModhash(),
                   otp=nop("otp"))
    def POST_enable_otp(self, form, jquery, otp):
        if form.has_errors("password", errors.WRONG_PASSWORD):
            return

        if c.user.otp_secret:
            c.errors.add(errors.OTP_ALREADY_ENABLED, field="otp")
            form.has_errors("otp", errors.OTP_ALREADY_ENABLED)
            return

        secret = g.gencache.get("otp:secret_" + c.user._id36)
        if not secret:
            c.errors.add(errors.EXPIRED, field="otp")
            form.has_errors("otp", errors.EXPIRED)
            return

        if not VOneTimePassword.validate_otp(secret, otp):
            c.errors.add(errors.WRONG_PASSWORD, field="otp")
            form.has_errors("otp", errors.WRONG_PASSWORD)
            return

        c.user.otp_secret = secret
        c.user._commit()

        form.redirect("/prefs/security")

    @validatedForm(
        VUser(),
        VModhash(),
        VVerifyPassword("password", fatal=False),
        VOneTimePassword("otp", required=True),
    )
    def POST_disable_otp(self, form, jquery):
        if form.has_errors("password", errors.WRONG_PASSWORD):
            return

        if form.has_errors("otp", errors.WRONG_PASSWORD,
                                  errors.NO_OTP_SECRET,
                                  errors.RATELIMIT):
            return

        c.user.otp_secret = ""
        c.user._commit()
        form.redirect("/prefs/security")

    @require_oauth2_scope("read")
    @json_validate(query=VLength("query", max_length=50))
    @api_doc(api_section.subreddits)
    def GET_subreddits_by_topic(self, responder, query):
        """Return a list of subreddits that are relevant to a search query."""
        if not g.CLOUDSEARCH_SEARCH_API:
            return []

        query = query and query.strip()
        if not query or len(query) < 2:
            return []

        # http://en.wikipedia.org/wiki/Most_common_words_in_English
        common_english_words = {
            'the', 'be', 'to', 'of', 'and', 'in', 'that', 'have', 'it', 'for',
            'not', 'on', 'with', 'he', 'as', 'you', 'do', 'at', 'this', 'but',
            'his', 'by', 'from', 'they', 'we', 'say', 'her', 'she', 'or', 'an',
            'will', 'my', 'one', 'all', 'would', 'there', 'their', 'what', 'so',
            'up', 'out', 'if', 'about', 'who', 'get', 'which', 'go', 'me',
            'when', 'make', 'can', 'like', 'time', 'no', 'just', 'him', 'know',
            'take', 'people', 'into', 'year', 'your', 'good', 'some', 'could',
            'them', 'see', 'other', 'than', 'then', 'now', 'look', 'only',
            'come', 'its', 'over', 'think', 'also', 'back', 'after', 'use',
            'two', 'how', 'our', 'work', 'first', 'well', 'way', 'even', 'new',
            'want', 'because', 'any', 'these', 'give', 'day', 'most', 'us',
        }

        if query.lower() in common_english_words:
            return []

        exclude = Subreddit.default_subreddits()

        faceting = {"reddit":{"sort":"-sum(text_relevance)", "count":20}}
        try:
            results = g.search.SearchQuery(query, sort="relevance",
                                           faceting=faceting, num=0,
                                           syntax="plain").run()
        except g.search.SearchException:
            abort(500)

        sr_results = []
        for sr, count in results.subreddit_facets:
            if (sr._id in exclude or (sr.over_18 and not c.over18)
                  or sr.type == "archived"):
                continue

            sr_results.append({
                "name": sr.name,
            })

        return sr_results

    @noresponse(VUser(),
                VModhash(),
                client=VOAuth2ClientID())
    def POST_revokeapp(self, client):
        if client:
            client.revoke(c.user)

    @validatedForm(VUser(),
                   VModhash(),
                   name=VRequired('name', errors.NO_TEXT,
                                  docs=dict(name="a name for the app")),
                   about_url=VSanitizedUrl('about_url'),
                   icon_url=VSanitizedUrl('icon_url'),
                   redirect_uri=VRedirectUri('redirect_uri'),
                   app_type=VOneOf('app_type', OAuth2Client.APP_TYPES))
    def POST_updateapp(self, form, jquery, name, about_url, icon_url,
                       redirect_uri, app_type):
        if (form.has_errors('name', errors.NO_TEXT) |
            form.has_errors('redirect_uri', errors.BAD_URL) |
            form.has_errors('redirect_uri', errors.NO_URL) |
            form.has_errors('app_type', errors.INVALID_OPTION)):
            return

        # Web apps should be redirecting to web
        if app_type == 'web':
            parsed = urlparse(redirect_uri)
            if parsed.scheme not in ('http', 'https'):
                c.errors.add(errors.INVALID_SCHEME, field='redirect_uri',
                        msg_params={"schemes": "http, https"})
                form.has_errors('redirect_uri', errors.INVALID_SCHEME)
                return

        description = request.POST.get('description', '')

        client_id = request.POST.get('client_id')
        if client_id:
            # client_id was specified, updating existing OAuth2Client
            client = OAuth2Client.get_token(client_id)
            if client.is_first_party() and not c.user_is_admin:
                form.set_text('.status', _('this app can not be modified from this interface'))
                return
            if app_type != client.app_type:
                # App type cannot be changed after creation
                abort(400, "invalid request")
                return
            if not client:
                form.set_text('.status', _('invalid client id'))
                return
            if client.deleted:
                form.set_text('.status', _('cannot update deleted app'))
                return
            if not client.has_developer(c.user):
                form.set_text('.status', _('app does not belong to you'))
                return

            client.name = name
            client.description = description
            client.about_url = about_url or ''
            client.redirect_uri = redirect_uri
            client._commit()
            form.set_text('.status', _('application updated'))
            apps = PrefApps([], [client])
            jquery('#developed-app-%s' % client._id).replaceWith(
                apps.render_developed_app(client, collapsed=False))
        else:
            # client_id was omitted or empty, creating new OAuth2Client
            client = OAuth2Client._new(name=name,
                                       description=description,
                                       about_url=about_url or '',
                                       redirect_uri=redirect_uri,
                                       app_type=app_type)
            client._commit()
            client.add_developer(c.user)
            form.set_text('.status', _('application created'))
            apps = PrefApps([], [client])
            jquery('#developed-apps > h1').show()
            jquery('#developed-apps > ul').append(
                apps.render_developed_app(client, collapsed=False))

    @validatedForm(VUser(),
                   VModhash(),
                   client=VOAuth2ClientDeveloper(),
                   account=VExistingUname('name'))
    def POST_adddeveloper(self, form, jquery, client, account):
        if not client:
            return
        if form.has_errors('name', errors.USER_DOESNT_EXIST, errors.NO_USER):
            return
        if client.is_first_party() and not c.user_is_admin:
            c.errors.add(errors.DEVELOPER_FIRST_PARTY_APP, field='name')
            form.set_error(errors.DEVELOPER_FIRST_PARTY_APP, 'name')
            return
        if ((account.employee or account == Account.system_user()) and
           not c.user_is_admin):
            c.errors.add(errors.DEVELOPER_PRIVILEGED_ACCOUNT, field='name')
            form.set_error(errors.DEVELOPER_PRIVILEGED_ACCOUNT, 'name')
            return
        if client.has_developer(account):
            c.errors.add(errors.DEVELOPER_ALREADY_ADDED, field='name')
            form.set_error(errors.DEVELOPER_ALREADY_ADDED, 'name')
            return
        try:
            client.add_developer(account)
        except OverflowError:
            c.errors.add(errors.TOO_MANY_DEVELOPERS, field='')
            form.set_error(errors.TOO_MANY_DEVELOPERS, '')
            return

        form.set_text('.status', _('developer added'))
        apps = PrefApps([], [client])
        (jquery('#app-developer-%s input[name="name"]' % client._id).val('')
            .closest('.prefright').find('ul').append(
                apps.render_editable_developer(client, account)))

    @validatedForm(VUser(),
                   VModhash(),
                   client=VOAuth2ClientDeveloper(),
                   account=VExistingUname('name'))
    def POST_removedeveloper(self, form, jquery, client, account):
        if client.is_first_party() and not c.user_is_admin:
            c.errors.add(errors.DEVELOPER_FIRST_PARTY_APP, field='name')
            form.set_error(errors.DEVELOPER_FIRST_PARTY_APP, 'name')
            return
        if client and account and not form.has_errors('name'):
            client.remove_developer(account)
            if account._id == c.user._id:
                jquery('#developed-app-%s' % client._id).fadeOut()
            else:
                jquery('li#app-dev-%s-%s' % (client._id, account._id)).fadeOut()

    @noresponse(VUser(),
                VModhash(),
                client=VOAuth2ClientDeveloper())
    def POST_deleteapp(self, client):
        if client:
            client.deleted = True
            client._commit()

    @validatedMultipartForm(VUser(),
                            VModhash(),
                            client=VOAuth2ClientDeveloper(),
                            icon_file=VUploadLength(
                                'file', max_length=1024*128,
                                docs=dict(file="an icon (72x72)")))
    def POST_setappicon(self, form, jquery, client, icon_file):
        if not icon_file:
            form.set_error(errors.TOO_LONG, 'file')
        if not form.has_error():
            try:
                client.icon_url = media.upload_icon(icon_file, (72, 72))
            except IOError, ex:
                c.errors.add(errors.BAD_IMAGE,
                             msg_params=dict(message=ex.message),
                             field='file')
                form.set_error(errors.BAD_IMAGE, 'file')
            else:
                client._commit()
                form.set_text('.status', 'uploaded')
                jquery('#developed-app-%s .app-icon img'
                       % client._id).attr('src', make_url_protocol_relative(client.icon_url))
                jquery('#developed-app-%s .ajax-upload-form'
                       % client._id).hide()
                jquery('#developed-app-%s .edit-app-icon-button'
                       % client._id).toggleClass('collapsed')

    @json_validate(
        VUser(),
        VModhash(),
        thing=VByName("thing"),
        signed=VBoolean("signed")
    )
    def POST_generate_payment_blob(self, responder, thing, signed):
        if not thing:
            abort(400, "Bad Request")

        if thing._deleted:
            abort(403, "Forbidden")

        thing_sr = Subreddit._byID(thing.sr_id, data=True)
        if (not thing_sr.can_view(c.user) or
            not thing_sr.allow_gilding):
            abort(403, "Forbidden")

        try:
            recipient = Account._byID(thing.author_id, data=True)
        except NotFound:
            self.abort404()

        if recipient._deleted:
            self.abort404()

        VNotInTimeout().run(action_name="gild", target=thing)

        return generate_blob(dict(
            goldtype="gift",
            account_id=c.user._id,
            account_name=c.user.name,
            status="initialized",
            signed=signed,
            recipient=recipient.name,
            giftmessage=None,
            thing=thing._fullname,
        ))

    @json_validate(
        VUser(),
        VModhash(),
        code=nop("code"),
        signed=VBoolean("signed", default=False),
        message=nop("message")
    )
    def POST_modify_payment_blob(self, responder, code, signed, message):
        if c.user.gild_reveal_username != signed:
            c.user.gild_reveal_username = signed
            c.user._commit()

        updates = {}
        updates["signed"] = signed
        if message and message.strip() != "":
            updates["giftmessage"] = _force_utf8(message)

        update_blob(str(code), updates)

    def OPTIONS_request_promo(self):
        """Send CORS headers for request_promo requests."""
        if "Origin" in request.headers:
            origin = request.headers["Origin"]
            response.headers["Access-Control-Allow-Origin"] = "*"
            response.headers["Access-Control-Allow-Methods"] = "POST"
            response.headers["Access-Control-Allow-Headers"] = "Authorization, "
            response.headers["Access-Control-Allow-Credentials"] = "false"
            response.headers['Access-Control-Expose-Headers'] = \
                self.COMMON_REDDIT_HEADERS

    @csrf_exempt
    @validate(srnames=VPrintable("srnames", max_length=2100))
    def POST_request_promo(self, srnames):
        self.OPTIONS_request_promo()

        if not srnames:
            return

        srnames = srnames.split('+')
        try:
            srnames.remove(Frontpage.name)
            srnames.append('')
        except ValueError:
            pass

        promo_tuples = promote.lottery_promoted_links(srnames, n=10)
        builder = CampaignBuilder(promo_tuples,
                                  wrap=default_thing_wrapper(),
                                  keep_fn=promote.promo_keep_fn,
                                  num=1,
                                  skip=True)
        listing = LinkListing(builder, nextprev=False).listing()
        promote.add_trackers(listing.things, c.site)
        promote.update_served(listing.things)
        if listing.things:
            w = listing.things[0]
            w.num = ""
            return responsive(w.render(), space_compress=True)

    @json_validate(
        VUser(),
        VModhash(),
        collapsed=VBoolean('collapsed'),
    )
    def POST_set_left_bar_collapsed(self, responder, collapsed):
        c.user.pref_collapse_left_bar = collapsed
        c.user._commit()

    @require_oauth2_scope("read")
    @validate(srs=VSRByNames("srnames"),
              to_omit=VSRByNames("omit", required=False))
    @api_doc(api_section.subreddits, uri='/api/recommend/sr/{srnames}')
    def GET_subreddit_recommendations(self, srs, to_omit):
        """Return subreddits recommended for the given subreddit(s).

        Gets a list of subreddits recommended for `srnames`, filtering out any
        that appear in the optional `omit` param.

        """

        srs = [sr for sr in srs.values() if not isinstance(sr, FakeSubreddit)]
        to_omit = [sr for sr in to_omit.values() if not isinstance(sr, FakeSubreddit)]

        omit_id36s = [sr._id36 for sr in to_omit]
        rec_srs = recommender.get_recommendations(srs, to_omit=omit_id36s)
        sr_data = [{'sr_name': sr.name} for sr in rec_srs]
        return json.dumps(sr_data)


    @validatedForm(VUser(),
                   VModhash(),
                   action=VOneOf("type", FEEDBACK_ACTIONS),
                   srs=VSRByNames("srnames"))
    def POST_rec_feedback(self, form, jquery, action, srs):
        if form.has_errors("type", errors.INVALID_OPTION):
            return self.abort404()
        AccountSRFeedback.record_feedback(c.user, srs.values(), action)


    @validatedForm(
        VUser(),
        VModhash(),
        seconds_visibility=VOneOf(
            "seconds_visibility",
            ("public", "private"),
            default="private",
        ),
    )
    def POST_server_seconds_visibility(self, form, jquery, seconds_visibility):
        c.user.pref_public_server_seconds = seconds_visibility == "public"
        c.user._commit()

        hook = hooks.get_hook("server_seconds_visibility.change")
        hook.call(user=c.user, value=c.user.pref_public_server_seconds)

    @require_oauth2_scope("save")
    @noresponse(VGold(),
                VModhash(),
                links = VByName('links', thing_cls=Link, multiple=True,
                                limit=100))
    @api_doc(api_section.links_and_comments)
    def POST_store_visits(self, links):
        if not c.user.pref_store_visits or not links:
            return

        LinkVisitsByAccount._visit(c.user, links)

    @validatedForm(
        VAdmin(),
        VModhash(),
        system=VLength('system', 1024),
        subject=VLength('subject', 1024),
        note=VLength('note', 10000),
        author=VLength('author', 1024),
    )
    def POST_add_admin_note(self, form, jquery, system, subject, note, author):
        if form.has_errors(('system', 'subject', 'note', 'author'),
                           errors.TOO_LONG):
            return

        if note:
            from r2.models.admin_notes import AdminNotesBySystem
            AdminNotesBySystem.add(system, subject, note, author)
        form.refresh()

    @require_oauth2_scope("modconfig")
    @validatedForm(
        VSrModerator(perms="config"),
        VModhash(),
        short_name=VAvailableSubredditRuleName("short_name"),
        description=VMarkdownLength("description", max_length=500),
        kind=VOneOf('kind', ['link', 'comment', 'all']),
    )
    def POST_add_subreddit_rule(self, form, jquery, short_name, description,
            kind):
        if not feature.is_enabled("subreddit_rules", subreddit=c.site.name):
            abort(404)
        if form.has_errors("short_name", errors.TOO_SHORT, errors.NO_TEXT,
                errors.TOO_LONG, errors.SR_RULE_EXISTS, errors.SR_RULE_TOO_MANY):
            return
        if form.has_errors("description", errors.TOO_LONG):
            return
        if form.has_errors("kind", errors.INVALID_OPTION):
            return

        SubredditRules.create(c.site, short_name, description, kind)
        ModAction.create(c.site, c.user, 'createrule', details=short_name)

        if description:
            description_html = safemarkdown(description, wrap=False)
            form._send_data(description_html=description_html)

        form.refresh()

    @require_oauth2_scope("modconfig")
    @validatedForm(
        VSrModerator(perms="config"),
        VModhash(),
        rule=VSubredditRule("old_short_name"),
        short_name=VAvailableSubredditRuleName("short_name", updating=True),
        description=VMarkdownLength('description', max_length=500),
        kind=VOneOf('kind', ['link', 'comment', 'all']),
    )
    def POST_update_subreddit_rule(self, form, jquery, rule,
            short_name, description, kind):
        if not feature.is_enabled("subreddit_rules", subreddit=c.site.name):
            abort(404)
        if form.has_errors("old_short_name", errors.SR_RULE_DOESNT_EXIST):
            return
        if form.has_errors("short_name", errors.TOO_SHORT, errors.NO_TEXT,
                errors.TOO_LONG, errors.SR_RULE_TOO_MANY):
            return
        # if the short_name is changing and the new short_name already exists
        if rule["short_name"] != short_name:
            if form.has_errors("short_name", errors.SR_RULE_EXISTS):
                return
        if form.has_errors("description", errors.TOO_LONG):
            return
        if form.has_errors("kind", errors.INVALID_OPTION):
            return

        SubredditRules.update(c.site, rule["short_name"], short_name,
            description, kind)
        ModAction.create(c.site, c.user, 'editrule', details=short_name)

        if description:
            description_html = safemarkdown(description, wrap=False)
            form._send_data(description_html=description_html)

        form.refresh()

    @require_oauth2_scope("modconfig")
    @validatedForm(
        VSrModerator(perms="config"),
        VModhash(),
        rule=VSubredditRule("short_name"),
    )
    def POST_remove_subreddit_rule(self, form, jquery, rule):
        if not feature.is_enabled("subreddit_rules", subreddit=c.site.name):
            abort(404)
        if form.has_errors("rule", errors.SR_RULE_DOESNT_EXIST):
            return
        short_name = rule["short_name"]
        SubredditRules.remove_rule(c.site, short_name)
        ModAction.create(c.site, c.user, 'deleterule', details=short_name)
        form.refresh()

    @validatedForm(
        VUser(),
        VModhash(),
        thing=VByName('thing'),
    )
    def GET_report_form(self, form, jquery, thing):
        """Return information about report reasons for the thing."""
        if feature.is_enabled("new_report_dialog"):
            if request.params.get("api_type") == "json":
                style = "json"
                filter_by_kind = False
            else:
                style = "html"
                filter_by_kind = True

            report_form = SubredditReportForm(thing, filter_by_kind=filter_by_kind)

            if style == "json":
                form._send_data(
                    rules=report_form.rules,
                    sr_name=report_form.sr_name,
                )
            return report_form.render(style=style)
        else:
            return ReportForm(thing).render(style="html")
        abort(404, 'not found')


    @validatedForm(VModhashIfLoggedIn())
    def POST_hide_locationbar(self, form, jquery):
        c.user.pref_hide_locationbar = True
        c.user._commit()
        jquery(".locationbar").hide()

    @validatedForm(VModhashIfLoggedIn())
    def POST_use_global_defaults(self, form, jquery):
        c.user.pref_use_global_defaults = True
        c.user._commit()
        jquery.refresh()
