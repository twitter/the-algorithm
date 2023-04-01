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

from webob.exc import HTTPBadRequest, HTTPForbidden, status_map
from r2.lib.utils import Storage, tup
from pylons import request
from pylons import app_globals as g
from pylons.i18n import _
from copy import copy


error_list = dict((
        ('USER_REQUIRED', _("Please log in to do that.")),
        ('MOD_REQUIRED', _("You must be a moderator to do that.")),
        ('HTTPS_REQUIRED', _("this page must be accessed using https")),
        ('WRONG_DOMAIN', _("you can't do that on this domain")),
        ('VERIFIED_USER_REQUIRED', _("you need to set a valid email address to do that.")),
        ('NO_URL', _('a url is required')),
        ('BAD_URL', _('you should check that url')),
        ('INVALID_SCHEME', _('URI scheme must be one of: %(schemes)s')),
        ('BAD_CAPTCHA', _('care to try these again?')),
        ('BAD_USERNAME', _('invalid user name')),
        ('USERNAME_TOO_SHORT', _('username must be between %(min)d and %(max)d characters')),
        ('USERNAME_INVALID_CHARACTERS', _('username must contain only letters, numbers, "-", and "_"')),
        ('USERNAME_TAKEN', _('that username is already taken')),
        ('USERNAME_TAKEN_DEL', _('that username is taken by a deleted account')),
        ('USER_BLOCKED', _("you can't send to a user that you have blocked")),
        ('NO_THING_ID', _('id not specified')),
        ('TOO_MANY_THING_IDS', _('you provided too many ids')),
        ('NOT_AUTHOR', _("you can't do that")),
        ('NOT_USER', _("You are not logged in as that user.")),
        ('NOT_FRIEND', _("you are not friends with that user")),
        ('LOGGED_IN', _("You are already logged in.")),
        ('DELETED_COMMENT', _('that comment has been deleted')),
        ('DELETED_THING', _('that element has been deleted')),
        ('SHORT_PASSWORD', _('the password must be at least %(chars)d characters')),
        ('BAD_PASSWORD', _('that password is unacceptable')),
        ('WRONG_PASSWORD', _('wrong password')),
        ('BAD_PASSWORD_MATCH', _('passwords do not match')),
        ('NO_NAME', _('please enter a name')),
        ('NO_EMAIL', _('please enter an email address')),
        ('NO_EMAIL_FOR_USER', _('no email address for that user')),
        ('NO_VERIFIED_EMAIL', _('no verified email address for that user')),
        ('NO_TO_ADDRESS', _('send it to whom?')),
        ('NO_SUBJECT', _('please enter a subject')),
        ('USER_DOESNT_EXIST', _("that user doesn't exist")),
        ('NO_USER', _('please enter a username')),
        ('INVALID_PREF', _("that preference isn't valid")),
        ('NON_PREFERENCE', _("'%(choice)s' is not a user preference field")),
        ('INVALID_LANG', _("that language is not available")),
        ('BAD_NUMBER', _("that number isn't in the right range (%(range)s)")),
        ('BAD_STRING', _("you used a character here that we can't handle")),
        ('BAD_BUDGET', _("your budget must be at least $%(min)d and no more than $%(max)d.")),
        ('BAD_BID', _('your bid must be at least $%(min)s and no more than $%(max)s.')),
        ('ALREADY_SUB', _("that link has already been submitted")),
        ('SUBREDDIT_EXISTS', _('that subreddit already exists')),
        ('SUBREDDIT_NOEXIST', _('that subreddit doesn\'t exist')),
        ('SUBREDDIT_NOTALLOWED', _("you aren't allowed to post there.")),
        ('SUBREDDIT_NO_ACCESS', _("you aren't allowed access to this subreddit")),
        ('SUBREDDIT_REQUIRED', _('you must specify a subreddit')),
        ('SUBREDDIT_DISABLED_ADS', _('this subreddit has chosen to disable their ads at this time')),
        ('BAD_SR_NAME', _('that name isn\'t going to work')),
        ('COLLECTION_NOEXIST', _('that collection doesn\'t exist')),
        ('INVALID_TARGET', _('that target type is not valid')),
        ('INVALID_NSFW_TARGET', _('nsfw ads must target nsfw content')),
        ('INVALID_OS_VERSION', _('that version range is not valid')),
        ('RATELIMIT', _('you are doing that too much. try again in %(time)s.')),
        ('SUBREDDIT_RATELIMIT', _("you are doing that too much. try again later.")),
        ('EXPIRED', _('your session has expired')),
        ('DRACONIAN', _('you must accept the terms first')),
        ('BANNED_IP', "IP banned"),
        ('BAD_CNAME', "that domain isn't going to work"),
        ('USED_CNAME', "that domain is already in use"),
        ('INVALID_OPTION', _('that option is not valid')),
        ('BAD_EMAIL', _('that email is invalid')),
        ('BAD_EMAILS', _('the following emails are invalid: %(emails)s')),
        ('NO_EMAILS', _('please enter at least one email address')),
        ('TOO_MANY_EMAILS', _('please only share to %(num)s emails at a time.')),
        ('NEWSLETTER_NO_EMAIL', _('where should we send that weekly newsletter?')),
        ('SPONSOR_NO_EMAIL', _('advertisers are required to supply an email')),
        ('NEWSLETTER_EMAIL_UNACCEPTABLE', _('That email could not be added. Check your email for an existing confirmation email.')),
        ('OVERSOLD', _('that subreddit has already been oversold on %(start)s to %(end)s. Please pick another subreddit or date.')),
        ('OVERSOLD_DETAIL', _("We have insufficient inventory to fulfill your requested budget, target, and dates. Only %(available)s impressions available on %(target)s from %(start)s to %(end)s.")),
        ('BAD_DATE', _('please provide a date of the form mm/dd/yyyy')),
        ('BAD_DATE_RANGE', _('the dates need to be in order and not identical')),
        ('DATE_TOO_LATE', _('please enter a date %(day)s or earlier')),
        ('DATE_TOO_EARLY', _('please enter a date %(day)s or later')),
        ('START_DATE_CANNOT_CHANGE', _('start date cannot be changed')),
        ('COST_BASIS_CANNOT_CHANGE', _('this campaign was created prior to auction and cannot be edited')),
        ('BAD_ADDRESS', _('address problem: %(message)s')),
        ('BAD_CARD', _('card problem: %(message)s')),
        ('TOO_LONG', _("this is too long (max: %(max_length)s)")),
        ('NO_TEXT', _('we need something here')),
        ('TOO_SHORT', _("this is too short (min: %(min_length)s)")),
        ('INVALID_CODE', _("we've never seen that code before")),
        ('CLAIMED_CODE', _("that code has already been claimed -- perhaps by you?")),
        ('NO_SELFS', _("that subreddit doesn't allow text posts")),
        ('NO_LINKS', _("that subreddit only allows text posts")),
        ('TOO_OLD', _("that's a piece of history now; it's too late to reply to it")),
        ('THREAD_LOCKED', _("Comments are locked.")),
        ('BAD_CSS_NAME', _('invalid css name')),
        ('BAD_CSS', _('invalid css')),
        ('BAD_COLOR', _('invalid color')),
        ('BAD_REVISION', _('invalid revision ID')),
        ('TOO_MUCH_FLAIR_CSS', _('too many flair css classes')),
        ('BAD_FLAIR_TARGET', _('not a valid flair target')),
        ('OAUTH2_INVALID_CLIENT', _('invalid client id')),
        ('OAUTH2_INVALID_REDIRECT_URI', _('invalid redirect_uri parameter')),
        ('OAUTH2_INVALID_RESPONSE_TYPE', _('invalid response type')),
        ('OAUTH2_INVALID_SCOPE', _('invalid scope requested')),
        ('OAUTH2_INVALID_REFRESH_TOKEN', _('invalid refresh token')),
        ('OAUTH2_ACCESS_DENIED', _('access denied by the user')),
        ('OAUTH2_NO_REFRESH_TOKENS_ALLOWED', _('refresh tokens are not allowed for this response_type')),
        ('OAUTH2_CONFIDENTIAL_TOKEN', _('confidential clients can not request tokens directly')),
        ('CONFIRM', _("please confirm the form")),
        ('CONFLICT', _("conflict error while saving")),
        ('NO_API', _('cannot perform this action via the API')),
        ('DOMAIN_BANNED', _('%(domain)s is not allowed on reddit: %(reason)s')),
        ('NO_OTP_SECRET', _('you must enable two-factor authentication')),
        ('OTP_ALREADY_ENABLED', _('two-factor authentication is already enabled')),
        ('BAD_IMAGE', _('image problem')),
        ('DEVELOPER_ALREADY_ADDED', _('already added')),
        ('TOO_MANY_DEVELOPERS', _('too many developers')),
        ('DEVELOPER_FIRST_PARTY_APP', _('this app can not be modified from this interface')),
        ('DEVELOPER_PRIVILEGED_ACCOUNT', _('you cannot add this account from this interface')),
        ('INVALID_MODHASH', _("invalid modhash")),
        ('ALREADY_MODERATOR', _('that user is already a moderator')),
        ('CANT_RESTRICT_MODERATOR', _("You can't perform that action because that user is a moderator.")),
        ('NO_INVITE_FOUND', _('there is no pending invite for that subreddit')),
        ('BUDGET_LIVE', _('you cannot edit the budget of a live ad')),
        ('TOO_MANY_CAMPAIGNS', _('you have too many campaigns for that promotion')),
        ('BAD_JSONP_CALLBACK', _('that jsonp callback contains invalid characters')),
        ('INVALID_PERMISSION_TYPE', _("permissions don't apply to that type of user")),
        ('INVALID_PERMISSIONS', _('invalid permissions string')),
        ('BAD_MULTI_PATH', _('invalid multi path')),
        ('BAD_MULTI_NAME', _('%(reason)s')),
        ('MULTI_NOT_FOUND', _('that multireddit doesn\'t exist')),
        ('MULTI_EXISTS', _('that multireddit already exists')),
        ('MULTI_CANNOT_EDIT', _('you can\'t change that multireddit')),
        ('MULTI_TOO_MANY_SUBREDDITS', _('no more space for subreddits in that multireddit')),
        ('MULTI_SPECIAL_SUBREDDIT', _("can't add special subreddit %(path)s")),
        ('TOO_MANY_SUBREDDITS', _('maximum %(max)s subreddits')),
        ('JSON_PARSE_ERROR', _('unable to parse JSON data')),
        ('JSON_INVALID', _('unexpected JSON structure')),
        ('JSON_MISSING_KEY', _('JSON missing key: "%(key)s"')),
        ('NO_CHANGE_KIND', _("can't change post type")),
        ('INVALID_LOCATION', _("invalid location")),
        ('INVALID_FREQUENCY_CAP', _("invalid values for frequency cap")),
        ('FREQUENCY_CAP_TOO_LOW', _('frequency cap must be at least %(min)d')),
        ('BANNED_FROM_SUBREDDIT', _('that user is banned from the subreddit')),
        ('IN_TIMEOUT', _("You can't do that while suspended.")),
        ('GOLD_REQUIRED', _('you must have an active reddit gold subscription to do that')),
        ('INSUFFICIENT_CREDDITS', _("insufficient creddits")),
        ('GILDING_NOT_ALLOWED', _("gilding is not allowed in this subreddit")),
        ('SCRAPER_ERROR', _("unable to scrape provided url")),
        ('NO_SR_TO_SR_MESSAGE', _("can't send a message from a subreddit to another subreddit")),
        ('USER_BLOCKED_MESSAGE', _("can't send message to that user")),
        ('ADMIN_REQUIRED', _("you must be in admin mode for this")),
        ('CANT_CONVERT_TO_GOLD_ONLY', _("to convert an existing subreddit to gold only, send a message to %(admin_modmail)s") 
            % dict(admin_modmail=g.admin_message_acct)),
        ('GOLD_ONLY_SR_REQUIRED', _("this subreddit must be 'gold only' to select this")),
        ('CANT_CREATE_SR', _("your account is too new or you do not have enough karma to create a subreddit. please contact the admins to request an exemption.")),
        ('BAD_PROMO_MOBILE_OS', _("you must select at least one mobile OS to target")),
        ('BAD_PROMO_MOBILE_DEVICE', _("you must select at least one device per OS to target")),
        ('USER_MUTED', _("You have been muted from this subreddit.")),
        ('MUTED_FROM_SUBREDDIT', _("This user has been muted from the subreddit.")),
        ('COMMENT_NOT_STICKYABLE', _("This comment is not stickyable. Ensure that it is a top level comment.")),
        ('SR_RULE_EXISTS', _("A subreddit rule by that name already exists.")),
        ('SR_RULE_DOESNT_EXIST', _("No subreddit rule by that name exists.")),
        ('SR_RULE_TOO_MANY', _("This subreddit already has the maximum number of rules.")),
        ('COMMENT_NOT_ACCESSIBLE', _("Cannot access this comment.")),
        ('POST_NOT_ACCESSIBLE', _("Cannot access this post.")),
    ))

errors = Storage([(e, e) for e in error_list.keys()])


def add_error_codes(new_codes):
    """Add error codes to the error enumeration.

    It is assumed that the incoming messages are marked for translation but not
    yet translated, so they can be declared before pylons.i18n is ready.

    """
    for code, message in new_codes.iteritems():
        error_list[code] = _(message)
        errors[code] = code


class RedditError(Exception):
    name = None
    fields = None
    code = None

    def __init__(self, name=None, msg_params=None, fields=None, code=None):
        Exception.__init__(self)

        if name is not None:
            self.name = name

        self.i18n_message = error_list.get(self.name)
        self.msg_params = msg_params or {}

        if fields is not None:
            # list of fields in the original form that caused the error
            self.fields = tup(fields)

        if code is not None:
            self.code = code

    @property
    def message(self):
        return _(self.i18n_message) % self.msg_params

    def __iter__(self):
        yield ('name', self.name)
        yield ('message', _(self.message))

    def __repr__(self):
        return '<RedditError: %s>' % self.name

    def __str__(self):
        return repr(self)


class ErrorSet(object):
    def __init__(self):
        self.errors = {}

    def __contains__(self, pair):
        """Expects an (error_name, field_name) tuple and checks to
        see if it's in the errors list."""
        return self.errors.has_key(pair)

    def get(self, name, default=None):
        return self.errors.get(name, default)

    def get_first(self, field_name, *error_names):
        error = None

        for error_name in error_names:
            error = self.get((error_name, field_name))
            if error:
                return error

    def __getitem__(self, name):
        return self.errors[name]

    def __repr__(self):
        return "<ErrorSet %s>" % list(self)

    def __iter__(self):
        for x in self.errors:
            yield x

    def __len__(self):
        return len(self.errors)

    def add(self, error_name, msg_params=None, field=None, code=None):
        for field_name in tup(field):
            e = RedditError(error_name, msg_params, fields=field_name,
                            code=code)
            self.add_error(e)

    def add_error(self, error):
        for field_name in tup(error.fields):
            self.errors[(error.name, field_name)] = error

    def remove(self, pair):
        """Expects an (error_name, field_name) tuple and removes it
        from the errors list."""
        if self.errors.has_key(pair):
            del self.errors[pair]


class ForbiddenError(HTTPForbidden):
    def __init__(self, error_name):
        HTTPForbidden.__init__(self)
        self.explanation = error_list[error_name]


class BadRequestError(HTTPBadRequest):
    def __init__(self, error_name):
        HTTPBadRequest.__init__(self)
        self.error_data = {
            'reason': error_name,
            'explanation': error_list[error_name],
        }
        self.explanation = error_list[error_name]


def reddit_http_error(code=400, error_name='UNKNOWN_ERROR', **data):
    exc = status_map[code]()

    data['reason'] = exc.explanation = error_name
    if 'explanation' not in data and error_name in error_list:
        data['explanation'] = exc.explanation = error_list[error_name]

    # omit 'fields' json attribute if it is empty
    if 'fields' in data and not data['fields']:
        del data['fields']

    exc.error_data = data
    return exc


class UserRequiredException(RedditError):
    name = errors.USER_REQUIRED
    code = 403


class VerifiedUserRequiredException(RedditError):
    name = errors.VERIFIED_USER_REQUIRED
    code = 403


class MessageError(Exception): pass
