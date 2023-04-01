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

import cgi
import json
from collections import OrderedDict
from decimal import Decimal

from pylons import request, response
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _
from pylons.controllers.util import abort

from r2.config import feature
from r2.config.extensions import api_type, is_api
from r2.lib import utils, captcha, promote, totp, ratelimit
from r2.lib.filters import unkeep_space, websafe, _force_unicode, _force_utf8
from r2.lib.filters import markdown_souptest
from r2.lib.db import tdb_cassandra
from r2.lib.db.operators import asc, desc
from r2.lib.souptest import (
    SoupError,
    SoupDetectedCrasherError,
    SoupUnsupportedEntityError,
)
from r2.lib.template_helpers import add_sr
from r2.lib.jsonresponse import JQueryResponse, JsonResponse
from r2.lib.permissions import ModeratorPermissionSet
from r2.models import *
from r2.models.rules import MAX_RULES_PER_SUBREDDIT
from r2.models.promo import Location
from r2.lib.authorize import Address, CreditCard
from r2.lib.utils import constant_time_compare
from r2.lib.require import require, require_split, RequirementException
from r2.lib import signing

from r2.lib.errors import errors, RedditError, UserRequiredException
from r2.lib.errors import VerifiedUserRequiredException

from copy import copy
from datetime import datetime, timedelta
from curses.ascii import isprint
import re, inspect
from itertools import chain
from functools import wraps


def can_view_link_comments(article):
    return (article.subreddit_slow.can_view(c.user) and
            article.can_view_promo(c.user))


class Validator(object):
    notes = None
    default_param = None
    def __init__(self, param=None, default=None, post=True, get=True, url=True,
                 get_multiple=False, body=False, docs=None):
        if param:
            self.param = param
        else:
            self.param = self.default_param

        self.default = default
        self.post, self.get, self.url, self.docs = post, get, url, docs
        self.get_multiple = get and get_multiple
        self.body = body
        self.has_errors = False

    def set_error(self, error, msg_params={}, field=False, code=None):
        """
        Adds the provided error to c.errors and flags that it is come
        from the validator's param
        """
        if field is False:
            field = self.param

        c.errors.add(error, msg_params=msg_params, field=field, code=code)
        self.has_errors = True

    def param_docs(self):
        param_info = {}
        for param in filter(None, tup(self.param)):
            param_info[param] = None
        return param_info

    def __call__(self, url):
        self.has_errors = False
        a = []
        if self.param:
            for p in utils.tup(self.param):
                # cgi.FieldStorage is falsy even if it has a filled value
                # property. :(
                post_val = request.POST.get(p)
                if self.post and (post_val or
                                  isinstance(post_val, cgi.FieldStorage)):
                    val = request.POST[p]
                elif ((self.get_multiple and
                      (self.get_multiple == True or
                       p in self.get_multiple)) and
                      request.GET.getall(p)):
                    val = request.GET.getall(p)
                elif self.get and request.GET.get(p):
                    val = request.GET[p]
                elif self.url and url.get(p):
                    val = url[p]
                elif self.body:
                    val = request.body
                else:
                    val = self.default
                a.append(val)
        try:
            return self.run(*a)
        except TypeError, e:
            if str(e).startswith('run() takes'):
                # Prepend our class name so we know *which* run()
                raise TypeError('%s.%s' % (type(self).__name__, str(e)))
            else:
                raise


def build_arg_list(fn, env):
    """given a fn and and environment the builds a keyword argument list
    for fn"""
    kw = {}
    argspec = inspect.getargspec(fn)

    # if there is a **kw argument in the fn definition,
    # just pass along the environment
    if argspec[2]:
        kw = env
    #else for each entry in the arglist set the value from the environment
    else:
        #skip self
        argnames = argspec[0][1:]
        for name in argnames:
            if name in env:
                kw[name] = env[name]
    return kw

def _make_validated_kw(fn, simple_vals, param_vals, env):
    for validator in simple_vals:
        validator(env)
    kw = build_arg_list(fn, env)
    for var, validator in param_vals.iteritems():
        kw[var] = validator(env)
    return kw

def set_api_docs(fn, simple_vals, param_vals, extra_vals=None):
    doc = fn._api_doc = getattr(fn, '_api_doc', {})
    param_info = doc.get('parameters', {})
    notes = doc.get('notes', [])
    for validator in chain(simple_vals, param_vals.itervalues()):
        param_docs = validator.param_docs()
        if validator.docs:
            param_docs.update(validator.docs)
        param_info.update(param_docs)
        if validator.notes:
            notes.append(validator.notes)
    if extra_vals:
        param_info.update(extra_vals)
    doc['parameters'] = param_info
    doc['notes'] = notes

def _validators_handle_csrf(simple_vals, param_vals):
    for validator in chain(simple_vals, param_vals.itervalues()):
        if getattr(validator, 'handles_csrf', False):
            return True
    return False

def validate(*simple_vals, **param_vals):
    """Validation decorator that delegates error handling to the controller.

    Runs the validators specified and calls self.on_validation_error to
    process each error. This allows controllers to define their own fatal
    error processing logic.
    """
    def val(fn):
        @wraps(fn)
        def newfn(self, *a, **env):
            try:
                kw = _make_validated_kw(fn, simple_vals, param_vals, env)
            except RedditError as err:
                self.on_validation_error(err)

            for err in c.errors:
                self.on_validation_error(c.errors[err])

            try:
                return fn(self, *a, **kw)
            except RedditError as err:
                self.on_validation_error(err)

        set_api_docs(newfn, simple_vals, param_vals)
        newfn.handles_csrf = _validators_handle_csrf(simple_vals, param_vals)
        return newfn
    return val


def api_validate(response_type=None, add_api_type_doc=False):
    """
    Factory for making validators for API calls, since API calls come
    in two flavors: responsive and unresponsive.  The machinary
    associated with both is similar, and the error handling identical,
    so this function abstracts away the kw validation and creation of
    a Json-y responder object.
    """
    def wrap(response_function):
        def _api_validate(*simple_vals, **param_vals):
            def val(fn):
                @wraps(fn)
                def newfn(self, *a, **env):
                    renderstyle = request.params.get("renderstyle")
                    if renderstyle:
                        c.render_style = api_type(renderstyle)
                    elif not c.extension:
                        # if the request URL included an extension, don't
                        # touch the render_style, since it was already set by
                        # set_extension. if no extension was provided, default
                        # to response_type.
                        c.render_style = api_type(response_type)

                    # generate a response object
                    if response_type == "html" and not request.params.get('api_type') == "json":
                        responder = JQueryResponse()
                    else:
                        responder = JsonResponse()

                    response.content_type = responder.content_type

                    try:
                        kw = _make_validated_kw(fn, simple_vals, param_vals, env)
                        return response_function(self, fn, responder,
                                                 simple_vals, param_vals, *a, **kw)
                    except UserRequiredException:
                        responder.send_failure(errors.USER_REQUIRED)
                        return self.api_wrapper(responder.make_response())
                    except VerifiedUserRequiredException:
                        responder.send_failure(errors.VERIFIED_USER_REQUIRED)
                        return self.api_wrapper(responder.make_response())

                extra_param_vals = {}
                if add_api_type_doc:
                    extra_param_vals = {
                        "api_type": "the string `json`",
                    }

                set_api_docs(newfn, simple_vals, param_vals, extra_param_vals)
                newfn.handles_csrf = _validators_handle_csrf(simple_vals,
                                                             param_vals)
                return newfn
            return val
        return _api_validate
    return wrap


@api_validate("html")
def noresponse(self, self_method, responder, simple_vals, param_vals, *a, **kw):
    self_method(self, *a, **kw)
    return self.api_wrapper({})

@api_validate("html")
def textresponse(self, self_method, responder, simple_vals, param_vals, *a, **kw):
    return self_method(self, *a, **kw)

@api_validate()
def json_validate(self, self_method, responder, simple_vals, param_vals, *a, **kw):
    if c.extension != 'json':
        abort(404)

    val = self_method(self, responder, *a, **kw)
    if val is None:
        val = responder.make_response()
    return self.api_wrapper(val)

def _validatedForm(self, self_method, responder, simple_vals, param_vals,
                  *a, **kw):
    # generate a form object
    form = responder(request.POST.get('id', "body"))

    # clear out the status line as a courtesy
    form.set_text(".status", "")

    # do the actual work
    val = self_method(self, form, responder, *a, **kw)

    # add data to the output on some errors
    for validator in chain(simple_vals, param_vals.values()):
        if (isinstance(validator, VCaptcha) and
            (form.has_errors('captcha', errors.BAD_CAPTCHA) or
             (form.has_error() and c.user.needs_captcha()))):
            form.new_captcha()
        elif (isinstance(validator, (VRatelimit, VThrottledLogin)) and
              form.has_errors('ratelimit', errors.RATELIMIT)):
            form.ratelimit(validator.seconds)
    if val:
        return val
    else:
        return self.api_wrapper(responder.make_response())

@api_validate("html", add_api_type_doc=True)
def validatedForm(self, self_method, responder, simple_vals, param_vals,
                  *a, **kw):
    return _validatedForm(self, self_method, responder, simple_vals, param_vals,
                          *a, **kw)

@api_validate("html", add_api_type_doc=True)
def validatedMultipartForm(self, self_method, responder, simple_vals,
                           param_vals, *a, **kw):
    def wrapped_self_method(*a, **kw):
        val = self_method(*a, **kw)
        if val:
            return val
        else:
            data = json.dumps(responder.make_response())
            response.content_type = "text/html"
            return ('<html><head><script type="text/javascript">\n'
                    'parent.$.handleResponse()(%s)\n'
                    '</script></head></html>') % filters.websafe_json(data)
    return _validatedForm(self, wrapped_self_method, responder, simple_vals,
                          param_vals, *a, **kw)


jsonp_callback_rx = re.compile("\\A[\\w$\\.\"'[\\]]+\\Z")
def valid_jsonp_callback(callback):
    return jsonp_callback_rx.match(callback)


#### validators ####
class nop(Validator):
    def run(self, x):
        return x

class VLang(Validator):
    @staticmethod
    def validate_lang(lang, strict=False):
        if lang in g.all_languages:
            return lang
        else:
            if not strict:
                return g.lang
            else:
                raise ValueError("invalid language %r" % lang)
    def run(self, lang):
        return VLang.validate_lang(lang)

    def param_docs(self):
        return {
            self.param: "a valid IETF language tag (underscore separated)",
        }


class VRequired(Validator):
    def __init__(self, param, error, *a, **kw):
        Validator.__init__(self, param, *a, **kw)
        self._error = error

    def error(self, e = None):
        if not e: e = self._error
        if e:
            self.set_error(e)

    def run(self, item):
        if not item:
            self.error()
        else:
            return item

class VThing(Validator):
    def __init__(self, param, thingclass, redirect = True, *a, **kw):
        Validator.__init__(self, param, *a, **kw)
        self.thingclass = thingclass
        self.redirect = redirect

    def run(self, thing_id):
        if thing_id:
            try:
                tid = int(thing_id, 36)
                thing = self.thingclass._byID(tid, True)
                if thing.__class__ != self.thingclass:
                    raise TypeError("Expected %s, got %s" %
                                    (self.thingclass, thing.__class__))
                return thing
            except (NotFound, ValueError):
                if self.redirect:
                    abort(404, 'page not found')
                else:
                    return None

    def param_docs(self):
        return {
            self.param: "The base 36 ID of a " + self.thingclass.__name__
        }

class VLink(VThing):
    def __init__(self, param, redirect = True, *a, **kw):
        VThing.__init__(self, param, Link, redirect=redirect, *a, **kw)

class VPromoCampaign(VThing):
    def __init__(self, param, redirect = True, *a, **kw):
        VThing.__init__(self, param, PromoCampaign, *a, **kw)

class VCommentByID(VThing):
    def __init__(self, param, redirect = True, *a, **kw):
        VThing.__init__(self, param, Comment, redirect=redirect, *a, **kw)


class VAward(VThing):
    def __init__(self, param, redirect = True, *a, **kw):
        VThing.__init__(self, param, Award, redirect=redirect, *a, **kw)

class VAwardByCodename(Validator):
    def run(self, codename, required_fullname=None):
        if not codename:
            return self.set_error(errors.NO_TEXT)

        try:
            a = Award._by_codename(codename)
        except NotFound:
            a = None

        if a and required_fullname and a._fullname != required_fullname:
            return self.set_error(errors.INVALID_OPTION)
        else:
            return a

class VTrophy(VThing):
    def __init__(self, param, redirect = True, *a, **kw):
        VThing.__init__(self, param, Trophy, redirect=redirect, *a, **kw)

class VMessage(Validator):
    def run(self, message_id):
        if message_id:
            try:
                aid = int(message_id, 36)
                return Message._byID(aid, True)
            except (NotFound, ValueError):
                abort(404, 'page not found')


class VCommentID(Validator):
    def run(self, cid):
        if cid:
            try:
                cid = int(cid, 36)
                return Comment._byID(cid, True)
            except (NotFound, ValueError):
                pass

class VMessageID(Validator):
    def run(self, cid):
        if cid:
            try:
                cid = int(cid, 36)
                m = Message._byID(cid, True)
                if not m.can_view_slow():
                    abort(403, 'forbidden')
                return m
            except (NotFound, ValueError):
                pass

class VCount(Validator):
    def run(self, count):
        if count is None:
            count = 0
        try:
            return max(int(count), 0)
        except ValueError:
            return 0

    def param_docs(self):
        return {
            self.param: "a positive integer (default: 0)",
        }


class VLimit(Validator):
    def __init__(self, param, default=25, max_limit=100, **kw):
        self.default_limit = default
        self.max_limit = max_limit
        Validator.__init__(self, param, **kw)

    def run(self, limit):
        default = c.user.pref_numsites
        if not default or c.render_style in ("compact", api_type("compact")):
            default = self.default_limit  # TODO: ini param?

        if limit is None:
            return default

        try:
            i = int(limit)
        except ValueError:
            return default

        return min(max(i, 1), self.max_limit)

    def param_docs(self):
        return {
            self.param: "the maximum number of items desired "
                        "(default: %d, maximum: %d)" % (self.default_limit,
                                                        self.max_limit),
        }

class VCssMeasure(Validator):
    measure = re.compile(r"\A\s*[\d\.]+\w{0,3}\s*\Z")
    def run(self, value):
        return value if value and self.measure.match(value) else ''


class VLength(Validator):
    only_whitespace = re.compile(r"\A\s*\Z", re.UNICODE)

    def __init__(self, param, max_length,
                 min_length=0,
                 empty_error = errors.NO_TEXT,
                 length_error = errors.TOO_LONG,
                 short_error=errors.TOO_SHORT,
                 **kw):
        Validator.__init__(self, param, **kw)
        self.max_length = max_length
        self.min_length = min_length
        self.length_error = length_error
        self.short_error = short_error
        self.empty_error = empty_error

    def run(self, text, text2 = ''):
        text = text or text2
        if self.empty_error and (not text or self.only_whitespace.match(text)):
            self.set_error(self.empty_error, code=400)
        elif len(text) > self.max_length:
            self.set_error(self.length_error, {'max_length': self.max_length}, code=400)
        elif len(text) < self.min_length:
            self.set_error(self.short_error, {'min_length': self.min_length},
                           code=400)
        else:
            return text

    def param_docs(self):
        return {
            self.param:
                "a string no longer than %d characters" % self.max_length,
        }

class VUploadLength(VLength):
    def run(self, upload, text2=''):
        # upload is expected to be a FieldStorage object
        if isinstance(upload, cgi.FieldStorage):
            return VLength.run(self, upload.value, text2)
        else:
            self.set_error(self.empty_error, code=400)

    def param_docs(self):
        kibibytes = self.max_length / 1024
        return {
            self.param:
                "file upload with maximum size of %d KiB" % kibibytes,
        }

class VPrintable(VLength):
    def run(self, text, text2 = ''):
        text = VLength.run(self, text, text2)

        if text is None:
            return None

        try:
            if all(isprint(str(x)) for x in text):
                return str(text)
        except UnicodeEncodeError:
            pass

        self.set_error(errors.BAD_STRING, code=400)
        return None

    def param_docs(self):
        return {
            self.param: "a string up to %d characters long,"
                        " consisting of printable characters."
                            % self.max_length,
        }

class VTitle(VLength):
    def __init__(self, param, max_length = 300, **kw):
        VLength.__init__(self, param, max_length, **kw)

    def param_docs(self):
        return {
            self.param: "title of the submission. "
                        "up to %d characters long" % self.max_length,
        }

class VMarkdown(Validator):
    def __init__(self, param, renderer='reddit'):
        Validator.__init__(self, param)
        self.renderer = renderer

    def run(self, text, text2=''):
        text = text or text2
        try:
            markdown_souptest(text, renderer=self.renderer)
            return text
        except SoupError as e:
            # Could happen if someone does `&#00;`. It's not a security issue,
            # it's just unacceptable.
            # TODO: give a better indication to the user of what happened
            if isinstance(e, SoupUnsupportedEntityError):
                abort(400)
                return

            import sys
            user = "???"
            if c.user_is_loggedin:
                user = c.user.name

            # work around CRBUG-464270
            if isinstance(e, SoupDetectedCrasherError):
                # We want a general idea of how often this is triggered, and
                # by what
                g.log.warning("CHROME HAX by %s: %s" % (user, text))
                abort(400)
                return

            g.log.error("HAX by %s: %s" % (user, text))
            s = sys.exc_info()
            # reraise the original error with the original stack trace
            raise s[1], None, s[2]

    def param_docs(self):
        return {
            tup(self.param)[0]: "raw markdown text",
        }


class VMarkdownLength(VMarkdown):
    def __init__(self, param, renderer='reddit', max_length=10000,
                 empty_error=errors.NO_TEXT, length_error=errors.TOO_LONG):
        VMarkdown.__init__(self, param, renderer)
        self.max_length = max_length
        self.empty_error = empty_error
        self.length_error = length_error

    def run(self, text, text2=''):
        text = text or text2
        text = VLength(self.param, self.max_length,
                       empty_error=self.empty_error,
                       length_error=self.length_error).run(text)
        if text:
            return VMarkdown.run(self, text)
        else:
            return ''


class VSavedCategory(Validator):
    savedcategory_rx = re.compile(r"\A[a-z0-9 _]{1,20}\Z")

    def run(self, name):
        if not name:
            return
        name = name.lower()
        valid = self.savedcategory_rx.match(name)
        if not valid:
            self.set_error('BAD_SAVE_CATEGORY')
            return
        return name

    def param_docs(self):
        return {
            self.param: "a category name",
        }


class VSubredditName(VRequired):
    def __init__(self, item, allow_language_srs=False, *a, **kw):
        VRequired.__init__(self, item, errors.BAD_SR_NAME, *a, **kw)
        self.allow_language_srs = allow_language_srs

    def run(self, name):
        if name:
            name = sr_path_rx.sub('\g<name>', name.strip())

        valid_name = Subreddit.is_valid_name(
            name, allow_language_srs=self.allow_language_srs)

        if not valid_name:
            self.set_error(self._error, code=400)
            return

        return str(name)

    def param_docs(self):
        return {
            self.param: "subreddit name",
        }


class VAvailableSubredditName(VSubredditName):
    def run(self, name):
        name = VSubredditName.run(self, name)
        if name:
            try:
                a = Subreddit._by_name(name)
                return self.error(errors.SUBREDDIT_EXISTS)
            except NotFound:
                return name


class VSRByName(Validator):
    def __init__(self, sr_name, required=True, return_srname=False):
        self.required = required
        self.return_srname = return_srname
        Validator.__init__(self, sr_name)

    def run(self, sr_name):
        if not sr_name:
            if self.required:
                self.set_error(errors.BAD_SR_NAME, code=400)
        else:
            sr_name = sr_path_rx.sub('\g<name>', sr_name.strip())
            try:
                sr = Subreddit._by_name(sr_name)
                if self.return_srname:
                    return sr.name
                else:
                    return sr
            except NotFound:
                self.set_error(errors.SUBREDDIT_NOEXIST, code=400)

    def param_docs(self):
        return {
            self.param: "subreddit name",
        }


class VSRByNames(Validator):
    """Returns a dict mapping subreddit names to subreddit objects.

    sr_names_csv - a comma delimited string of subreddit names
    required - if true (default) an empty subreddit name list is an error

    """
    def __init__(self, sr_names_csv, required=True):
        self.required = required
        Validator.__init__(self, sr_names_csv)

    def run(self, sr_names_csv):
        if sr_names_csv:
            sr_names = [sr_path_rx.sub('\g<name>', s.strip())
                        for s in sr_names_csv.split(',')]
            return Subreddit._by_name(sr_names)
        elif self.required:
            self.set_error(errors.BAD_SR_NAME, code=400)
        return {}

    def param_docs(self):
        return {
            self.param: "comma-delimited list of subreddit names",
        }


class VSubredditTitle(Validator):
    def run(self, title):
        if not title:
            self.set_error(errors.NO_TITLE)
        elif len(title) > 100:
            self.set_error(errors.TITLE_TOO_LONG)
        else:
            return title

class VSubredditDesc(Validator):
    def run(self, description):
        if description and len(description) > 500:
            self.set_error(errors.DESC_TOO_LONG)
        return unkeep_space(description or '')


class VAvailableSubredditRuleName(Validator):
    def __init__(self, short_name, updating=False):
        Validator.__init__(self, short_name)
        self.updating = updating

    def run(self, short_name):
        short_name = VLength(
            self.param,
            max_length=50,
            min_length=1,
        ).run(short_name.strip())
        if not short_name:
            return None

        if SubredditRules.get_rule(c.site, short_name):
            self.set_error(errors.SR_RULE_EXISTS)
        elif not self.updating:
            number_rules = len(SubredditRules.get_rules(c.site))
            if number_rules >= MAX_RULES_PER_SUBREDDIT:
                self.set_error(errors.SR_RULE_TOO_MANY)
                return None
        return short_name


class VSubredditRule(Validator):
    def run(self, short_name):
        short_name = VLength(
            self.param,
            max_length=50,
            min_length=1,
        ).run(short_name.strip())
        if not short_name:
            self.set_error(errors.SR_RULE_DOESNT_EXIST)
            return None

        rule = SubredditRules.get_rule(c.site, short_name)
        if not rule:
            self.set_error(errors.SR_RULE_DOESNT_EXIST)
        else:
            return rule


class VAccountByName(VRequired):
    def __init__(self, param, error = errors.USER_DOESNT_EXIST, *a, **kw):
        VRequired.__init__(self, param, error, *a, **kw)

    def run(self, name):
        if name:
            try:
                return Account._by_name(name)
            except NotFound: pass
        return self.error()

    def param_docs(self):
        return {self.param: "A valid, existing reddit username"}


class VFriendOfMine(VAccountByName):
    def run(self, name):
        # Must be logged in
        VUser().run()
        maybe_friend = VAccountByName.run(self, name)
        if maybe_friend:
            friend_rel = Account.get_friend(c.user, maybe_friend)
            if friend_rel:
                return friend_rel
            else:
                self.error(errors.NOT_FRIEND)
        return None


def fullname_regex(thing_cls = None, multiple = False):
    pattern = "[%s%s]" % (Relation._type_prefix, Thing._type_prefix)
    if thing_cls:
        pattern += utils.to36(thing_cls._type_id)
    else:
        pattern += r"[0-9a-z]+"
    pattern += r"_[0-9a-z]+"
    if multiple:
        pattern = r"(%s *,? *)+" % pattern
    return re.compile(r"\A" + pattern + r"\Z")

class VByName(Validator):
    # Lookup tdb_sql.Thing or tdb_cassandra.Thing objects by fullname.
    splitter = re.compile('[ ,]+')
    def __init__(self, param, thing_cls=None, multiple=False, limit=None,
                 error=errors.NO_THING_ID, ignore_missing=False,
                 backend='sql', **kw):
        # Limit param only applies when multiple is True
        if not multiple and limit is not None:
            raise TypeError('multiple must be True when limit is set')
        self.thing_cls = thing_cls
        self.re = fullname_regex(thing_cls)
        self.multiple = multiple
        self.limit = limit
        self._error = error
        self.ignore_missing = ignore_missing
        self.backend = backend

        Validator.__init__(self, param, **kw)

    def run(self, items):
        if self.backend == 'cassandra':
            # tdb_cassandra.Thing objects can't use the regex
            if items and self.multiple:
                items = [item for item in self.splitter.split(items)]
                if self.limit and len(items) > self.limit:
                    return self.set_error(errors.TOO_MANY_THING_IDS)
            if items:
                try:
                    return tdb_cassandra.Thing._by_fullname(
                        items,
                        ignore_missing=self.ignore_missing,
                        return_dict=False,
                    )
                except tdb_cassandra.NotFound:
                    pass
        else:
            if items and self.multiple:
                items = [item for item in self.splitter.split(items)
                         if item and self.re.match(item)]
                if self.limit and len(items) > self.limit:
                    return self.set_error(errors.TOO_MANY_THING_IDS)
            if items and (self.multiple or self.re.match(items)):
                try:
                    return Thing._by_fullname(
                        items,
                        return_dict=False,
                        ignore_missing=self.ignore_missing,
                        data=True,
                    )
                except NotFound:
                    pass

        return self.set_error(self._error)

    def param_docs(self):
        thingtype = (self.thing_cls or Thing).__name__.lower()
        if self.multiple:
            return {
                self.param: ("A comma-separated list of %s [fullnames]"
                             "(#fullnames)" % thingtype)
            }
        else:
            return {
                self.param: "[fullname](#fullnames) of a %s" % thingtype,
            }

class VByNameIfAuthor(VByName):
    def run(self, fullname):
        thing = VByName.run(self, fullname)
        if thing:
            if c.user_is_loggedin and thing.author_id == c.user._id:
                return thing
        return self.set_error(errors.NOT_AUTHOR)

    def param_docs(self):
        return {
            self.param: "[fullname](#fullnames) of a thing created by the user",
        }

class VCaptcha(Validator):
    default_param = ('iden', 'captcha')

    def run(self, iden, solution):
        if c.user.needs_captcha():
            valid_captcha = captcha.valid_solution(iden, solution)
            if not valid_captcha:
                self.set_error(errors.BAD_CAPTCHA)
            g.stats.action_event_count("captcha", valid_captcha)

    def param_docs(self):
        return {
            self.param[0]: "the identifier of the CAPTCHA challenge",
            self.param[1]: "the user's response to the CAPTCHA challenge",
        }


class VUser(Validator):
    def run(self):
        if not c.user_is_loggedin:
            raise UserRequiredException


class VNotInTimeout(Validator):
    def run(self, target_fullname=None, fatal=True, action_name=None,
            details_text="", target=None, subreddit=None):
        if c.user_is_loggedin and c.user.in_timeout:
            g.events.timeout_forbidden_event(
                action_name,
                details_text=details_text,
                target=target,
                target_fullname=target_fullname,
                subreddit=subreddit,
                request=request,
                context=c,
            )
            if fatal:
                request.environ['REDDIT_ERROR_NAME'] = 'IN_TIMEOUT'
                abort(403, errors.IN_TIMEOUT)
            return False


class VVerifyPassword(Validator):
    def __init__(self, param, fatal=True, *a, **kw):
        Validator.__init__(self, param, *a, **kw)
        self.fatal = fatal

    def run(self, password):
        VUser().run()
        if not valid_password(c.user, password):
            if self.fatal:
                abort(403)
            self.set_error(errors.WRONG_PASSWORD)
            return None
        # bcrypt wants a bytestring
        return _force_utf8(password)

    def param_docs(self):
        return {
            self.param: "the current user's password",
        }


class VModhash(Validator):
    handles_csrf = True
    default_param = 'uh'

    def __init__(self, param=None, fatal=True, *a, **kw):
        Validator.__init__(self, param, *a, **kw)
        self.fatal = fatal

    def run(self, modhash):
        # OAuth authenticated requests do not require CSRF protection.
        if c.oauth_user:
            return

        VUser().run()

        if modhash is None:
            modhash = request.headers.get('X-Modhash')

        hook = hooks.get_hook("modhash.validate")
        result = hook.call_until_return(modhash=modhash)

        # if no plugins validate the hash, just check if it's the user name
        if result is None:
            result = (modhash == c.user.name)

        if not result:
            g.stats.simple_event("event.modhash.invalid")
            if self.fatal:
                abort(403)
            self.set_error('INVALID_MODHASH')

    def param_docs(self):
        return {
            '%s / X-Modhash header' % self.param: 'a [modhash](#modhashes)',
        }


class VModhashIfLoggedIn(Validator):
    handles_csrf = True
    default_param = 'uh'

    def __init__(self, param=None, fatal=True, *a, **kw):
        Validator.__init__(self, param, *a, **kw)
        self.fatal = fatal

    def run(self, modhash):
        if c.user_is_loggedin:
            VModhash(fatal=self.fatal).run(modhash)

    def param_docs(self):
        return {
            '%s / X-Modhash header' % self.param: 'a [modhash](#modhashes)',
        }


class VAdmin(Validator):
    def run(self):
        if not c.user_is_admin:
            abort(404, "page not found")

def make_or_admin_secret_cls(base_cls):
    class VOrAdminSecret(base_cls):
        handles_csrf = True

        def run(self, secret=None):
            '''If validation succeeds, return True if the secret was used,
            False otherwise'''
            if secret and constant_time_compare(secret,
                                                g.secrets["ADMINSECRET"]):
                return True
            super(VOrAdminSecret, self).run()

            if request.method.upper() != "GET":
                VModhash(fatal=True).run(request.POST.get("uh"))

            return False
    return VOrAdminSecret

VAdminOrAdminSecret = make_or_admin_secret_cls(VAdmin)

class VVerifiedUser(VUser):
    def run(self):
        VUser.run(self)
        if not c.user.email_verified:
            raise VerifiedUserRequiredException

class VGold(VUser):
    notes = "*Requires a subscription to [reddit gold](/gold/about)*"
    def run(self):
        VUser.run(self)
        if not c.user.gold:
            abort(403, 'forbidden')

class VSponsorAdmin(VVerifiedUser):
    """
    Validator which checks c.user_is_sponsor
    """
    def user_test(self, thing):
        return (thing.author_id == c.user._id)

    def run(self, link_id = None):
        VVerifiedUser.run(self)
        if c.user_is_sponsor:
            return
        abort(403, 'forbidden')

VSponsorAdminOrAdminSecret = make_or_admin_secret_cls(VSponsorAdmin)

class VSponsor(VUser):
    """
    Not intended to be used as a check for c.user_is_sponsor, but
    rather is the user allowed to use the sponsored link system.
    If a link or campaign is passed in, it also checks whether the user is
    allowed to edit that particular sponsored link.
    """
    def user_test(self, thing):
        return (thing.author_id == c.user._id)

    def run(self, link_id=None, campaign_id=None):
        assert not (link_id and campaign_id), 'Pass link or campaign, not both'

        VUser.run(self)
        if c.user_is_sponsor:
            return
        elif campaign_id:
            pc = None
            try:
                if '_' in campaign_id:
                    pc = PromoCampaign._by_fullname(campaign_id, data=True)
                else:
                    pc = PromoCampaign._byID36(campaign_id, data=True)
            except (NotFound, ValueError):
                pass
            if pc:
                link_id = pc.link_id
        if link_id:
            try:
                if '_' in link_id:
                    t = Link._by_fullname(link_id, True)
                else:
                    aid = int(link_id, 36)
                    t = Link._byID(aid, True)
                if self.user_test(t):
                    return
            except (NotFound, ValueError):
                pass
            abort(403, 'forbidden')


class VVerifiedSponsor(VSponsor):
    def run(self, *args, **kwargs):
        VVerifiedUser().run()

        return super(VVerifiedSponsor, self).run(*args, **kwargs)


class VEmployee(VVerifiedUser):
    """Validate that user is an employee."""
    def run(self):
        if not c.user.employee:
            abort(403, 'forbidden')
        VVerifiedUser.run(self)


class VSrModerator(Validator):
    def __init__(self, fatal=True, perms=(), *a, **kw):
        # If True, abort rather than setting an error
        self.fatal = fatal
        self.perms = utils.tup(perms)
        super(VSrModerator, self).__init__(*a, **kw)

    def run(self):
        if not (c.user_is_loggedin
                and c.site.is_moderator_with_perms(c.user, *self.perms)
                or c.user_is_admin):
            if self.fatal:
                abort(403, "forbidden")
            return self.set_error('MOD_REQUIRED', code=403)


class VCanDistinguish(VByName):
    def run(self, thing_name, how):
        if c.user_is_loggedin:
            can_distinguish = False
            item = VByName.run(self, thing_name)

            if not item:
                abort(404)

            if item.author_id == c.user._id:
                if isinstance(item, Message) and c.user.employee:
                    return True
                subreddit = item.subreddit_slow

                if (how in ("yes", "no") and
                        subreddit.can_distinguish(c.user)):
                    can_distinguish = True
                elif (how in ("special", "no") and
                        c.user_special_distinguish):
                    can_distinguish = True
                elif (how in ("admin", "no") and
                        c.user.employee):
                    can_distinguish = True

                if can_distinguish:
                    # Don't allow distinguishing for users in timeout
                    VNotInTimeout().run(target=item, subreddit=subreddit)
                    return can_distinguish

        abort(403,'forbidden')

    def param_docs(self):
        return {}

class VSrCanAlter(VByName):
    def run(self, thing_name):
        if c.user_is_admin:
            return True
        elif c.user_is_loggedin:
            can_alter = False
            subreddit = None
            item = VByName.run(self, thing_name)

            if item.author_id == c.user._id:
                can_alter = True
            elif item.promoted and c.user_is_sponsor:
                can_alter = True
            else:
                # will throw a legitimate 500 if this isn't a link or
                # comment, because this should only be used on links and
                # comments
                subreddit = item.subreddit_slow
                if subreddit.can_distinguish(c.user):
                    can_alter = True

            if can_alter:
                # Don't allow mod actions for users who are in timeout
                VNotInTimeout().run(target=item, subreddit=subreddit)
                return can_alter

        abort(403,'forbidden')

class VSrCanBan(VByName):
    def run(self, thing_name):
        if c.user_is_admin:
            return True
        elif c.user_is_loggedin:
            item = VByName.run(self, thing_name)

            if isinstance(item, (Link, Comment)):
                sr = item.subreddit_slow
                if sr.is_moderator_with_perms(c.user, 'posts'):
                    return True
            elif isinstance(item, Message):
                sr = item.subreddit_slow
                if sr and sr.is_moderator_with_perms(c.user, 'mail'):
                    return True
        abort(403,'forbidden')

class VSrSpecial(VByName):
    def run(self, thing_name):
        if c.user_is_admin:
            return True
        elif c.user_is_loggedin:
            item = VByName.run(self, thing_name)
            # will throw a legitimate 500 if this isn't a link or
            # comment, because this should only be used on links and
            # comments
            subreddit = item.subreddit_slow
            if subreddit.is_special(c.user):
                return True
        abort(403,'forbidden')


class VSubmitParent(VByName):
    def run(self, fullname, fullname2):
        # for backwards compatibility (with iphone app)
        fullname = fullname or fullname2
        parent = VByName.run(self, fullname) if fullname else None

        if not parent:
            # for backwards compatibility (normally 404)
            abort(403, "forbidden")

        if not isinstance(parent, (Comment, Link, Message)):
            # for backwards compatibility (normally 400)
            abort(403, "forbidden")

        if not c.user_is_loggedin:
            # in practice this is handled by VUser
            abort(403, "forbidden")

        if parent.author_id in c.user.enemies:
            self.set_error(errors.USER_BLOCKED)

        if isinstance(parent, Message):
            return parent

        elif isinstance(parent, Link):
            sr = parent.subreddit_slow

            if parent.is_archived(sr):
                self.set_error(errors.TOO_OLD)
            elif parent.locked and not sr.can_distinguish(c.user):
                self.set_error(errors.THREAD_LOCKED)

            if self.has_errors or parent.can_comment_slow(c.user):
                return parent

        elif isinstance(parent, Comment):
            sr = parent.subreddit_slow

            if parent._deleted:
                self.set_error(errors.DELETED_COMMENT)

            elif parent._spam:
                # Only author, mod or admin can reply to removed comments
                can_reply = (c.user_is_loggedin and
                             (parent.author_id == c.user._id or
                              c.user_is_admin or
                              sr.is_moderator(c.user)))
                if not can_reply:
                    self.set_error(errors.DELETED_COMMENT)

            link = Link._byID(parent.link_id, data=True)

            if link.is_archived(sr):
                self.set_error(errors.TOO_OLD)
            elif link.locked and not sr.can_distinguish(c.user):
                self.set_error(errors.THREAD_LOCKED)

            if self.has_errors or link.can_comment_slow(c.user):
                return parent

        abort(403, "forbidden")

    def param_docs(self):
        return {
            self.param[0]: "[fullname](#fullnames) of parent thing",
        }

class VSubmitSR(Validator):
    def __init__(self, srname_param, linktype_param=None, promotion=False):
        self.require_linktype = False
        self.promotion = promotion

        if linktype_param:
            self.require_linktype = True
            Validator.__init__(self, (srname_param, linktype_param))
        else:
            Validator.__init__(self, srname_param)

    def run(self, sr_name, link_type = None):
        if not sr_name:
            self.set_error(errors.SUBREDDIT_REQUIRED)
            return None

        try:
            sr_name = sr_path_rx.sub('\g<name>', str(sr_name).strip())
            sr = Subreddit._by_name(sr_name)
        except (NotFound, AttributeError, UnicodeEncodeError):
            self.set_error(errors.SUBREDDIT_NOEXIST)
            return

        if not c.user_is_loggedin or not sr.can_submit(c.user, self.promotion):
            self.set_error(errors.SUBREDDIT_NOTALLOWED)
            return

        if not sr.allow_ads and self.promotion:
            self.set_error(errors.SUBREDDIT_DISABLED_ADS)
            return

        if self.require_linktype:
            if link_type not in ('link', 'self'):
                self.set_error(errors.INVALID_OPTION)
                return
            elif link_type == "link" and not sr.can_submit_link(c.user):
                self.set_error(errors.NO_LINKS)
                return
            elif link_type == "self" and not sr.can_submit_text(c.user):
                self.set_error(errors.NO_SELFS)
                return

        return sr

    def param_docs(self):
        return {
            self.param[0]: "name of a subreddit",
        }

class VSubscribeSR(VByName):
    def __init__(self, srid_param, srname_param):
        VByName.__init__(self, (srid_param, srname_param))

    def run(self, sr_id, sr_name):
        if sr_id:
            return VByName.run(self, sr_id)
        elif not sr_name:
            return

        try:
            sr = Subreddit._by_name(str(sr_name).strip())
        except (NotFound, AttributeError, UnicodeEncodeError):
            self.set_error(errors.SUBREDDIT_NOEXIST)
            return

        return sr

    def param_docs(self):
        return {
            self.param[0]: "the name of a subreddit",
        }


RE_GTM_ID = re.compile(r"^GTM-[A-Z0-9]+$")

class VGTMContainerId(Validator):
    def run(self, value):
        if not value:
            return g.googletagmanager

        if RE_GTM_ID.match(value):
            return value
        else:
            abort(404)


class VCollection(Validator):
    def run(self, name):
        collection = Collection.by_name(name)
        if collection:
            return collection
        self.set_error(errors.COLLECTION_NOEXIST)


class VPromoTarget(Validator):
    default_param = ("targeting", "sr", "collection")

    def run(self, targeting, sr_name, collection_name):
        if targeting == "collection" and collection_name == "none":
            return Target(Frontpage.name)
        elif targeting == "none":
            return Target(Frontpage.name)
        elif targeting == "collection":
            collection = VCollection("collection").run(collection_name)
            if collection:
                return Target(collection)
            else:
                # VCollection added errors so no need to do anything
                return
        elif targeting == "one":
            sr = VSubmitSR("sr", promotion=True).run(sr_name)
            if sr:
                return Target(sr.name)
            else:
                # VSubmitSR added errors so no need to do anything
                return
        else:
            self.set_error(errors.INVALID_TARGET, field="targeting")


class VOSVersion(Validator):
    def __init__(self, param, os, *a, **kw):
        Validator.__init__(self, param, *a, **kw)
        self.os = os

    def assign_error(self):
        self.set_error(errors.INVALID_OS_VERSION, field="os_version")

    def run(self, version_range):
        if not version_range:
            return

        # check that string conforms to `min,max` format
        try:
            min, max = version_range.split(',')
        except ValueError:
            self.assign_error()
            return

        # check for type errors
        # (max can be empty string, otherwise both float)
        type_errors = False
        if max == '':
            # check that min is a float
            try:
                min = float(min)
            except ValueError:
                type_errors = True
        else:
            # check that min and max are both floats
            try:
                min, max = float(min), float(max)
                # ensure than min is less-or-equal-to max
                if min > max:
                    type_errors = True
            except ValueError:
                type_errors = True

        if type_errors == True:
            self.assign_error()
            return

        for endpoint in (min, max):
            if endpoint != '':
                # check that the version is in the global config
                if endpoint not in getattr(g, '%s_versions' % self.os):
                    self.assign_error()
                    return

        return [str(min), str(max)]


MIN_PASSWORD_LENGTH = 6

class VPassword(Validator):
    def run(self, password):
        if not (password and len(password) >= MIN_PASSWORD_LENGTH):
            self.set_error(errors.SHORT_PASSWORD, {"chars": MIN_PASSWORD_LENGTH})
            self.set_error(errors.BAD_PASSWORD)
        else:
            return password.encode('utf8')

    def param_docs(self):
        return {
            self.param[0]: "the password"
        }


class VPasswordChange(VPassword):
    def run(self, password, verify):
        base = super(VPasswordChange, self).run(password)

        if self.has_errors:
            return base

        if (verify != password):
            self.set_error(errors.BAD_PASSWORD_MATCH)
        else:
            return base

    def param_docs(self):
        return {
            self.param[0]: "the new password",
            self.param[1]: "the password again (for verification)",
        }

MIN_USERNAME_LENGTH = 3
MAX_USERNAME_LENGTH = 20

user_rx = re.compile(r"\A[\w-]+\Z", re.UNICODE)

def chkuser(x):
    if x is None:
        return None
    try:
        if any(ch.isspace() for ch in x):
            return None
        return str(x) if user_rx.match(x) else None
    except TypeError:
        return None
    except UnicodeEncodeError:
        return None

class VUname(VRequired):
    def __init__(self, item, *a, **kw):
        VRequired.__init__(self, item, errors.BAD_USERNAME, *a, **kw)
    def run(self, user_name):
        length = 0 if not user_name else len(user_name)
        if (length < MIN_USERNAME_LENGTH or length > MAX_USERNAME_LENGTH):
            msg_params = {
                'min': MIN_USERNAME_LENGTH,
                'max': MAX_USERNAME_LENGTH,
            }
            self.set_error(errors.USERNAME_TOO_SHORT, msg_params=msg_params)
            self.set_error(errors.BAD_USERNAME)
            return
        user_name = chkuser(user_name)
        if not user_name:
            self.set_error(errors.USERNAME_INVALID_CHARACTERS)
            self.set_error(errors.BAD_USERNAME)
            return
        else:
            try:
                a = Account._by_name(user_name, True)
                if a._deleted:
                   return self.set_error(errors.USERNAME_TAKEN_DEL)
                else:
                   return self.set_error(errors.USERNAME_TAKEN)
            except NotFound:
                return user_name

    def param_docs(self):
        return {
            self.param[0]: "a valid, unused, username",
        }

class VLoggedOut(Validator):
    def run(self):
        if c.user_is_loggedin:
            self.set_error(errors.LOGGED_IN)


class AuthenticationFailed(Exception):
    pass


class LoginRatelimit(object):
    def __init__(self, category, key):
        self.category = category
        self.key = key

    def __str__(self):
        return "login-%s-%s" % (self.category, self.key)

    def __hash__(self):
        return hash(str(self))


class VThrottledLogin(VRequired):
    def __init__(self, params):
        VRequired.__init__(self, params, error=errors.WRONG_PASSWORD)
        self.vlength = VLength("user", max_length=100)
        self.seconds = None

    def get_ratelimits(self, account):
        is_previously_seen_ip = request.ip in [
            j for i in IPsByAccount.get(account._id, column_count=1000)
            for j in i.itervalues()
        ]

        # We want to maintain different rate-limit buckets depending on whether
        # we have seen the IP logging in before.  If someone is trying to brute
        # force an account from an unfamiliar location, we will rate limit
        # *all* requests from unfamiliar locations that try to access the
        # account, while still maintaining a separate rate-limit for IP
        # addresses we have seen use the account before.
        #
        # Finally, we also rate limit IPs themselves that appear to be trying
        # to log into accounts they have never logged into before.  This goes
        # into a separately maintained bucket.
        if is_previously_seen_ip:
            ratelimits = {
                LoginRatelimit("familiar", account._id): g.RL_LOGIN_MAX_REQS,
            }
        else:
            ratelimits = {
                LoginRatelimit("unfamiliar", account._id): g.RL_LOGIN_MAX_REQS,
                LoginRatelimit("ip", request.ip): g.RL_LOGIN_IP_MAX_REQS,
            }

        hooks.get_hook("login.ratelimits").call(
            ratelimits=ratelimits,
            familiar=is_previously_seen_ip,
        )

        return ratelimits

    def run(self, username, password):
        ratelimits = {}

        try:
            if username:
                username = username.strip()
                username = self.vlength.run(username)
                username = chkuser(username)

            if not username:
                raise AuthenticationFailed

            try:
                account = Account._by_name(username)
            except NotFound:
                raise AuthenticationFailed

            hooks.get_hook("account.spotcheck").call(account=account)
            if account._banned:
                raise AuthenticationFailed

            # if already logged in, you're exempt from your own ratelimit
            # (e.g. to allow account deletion regardless of DoS)
            ratelimit_exempt = (account == c.user)
            if not ratelimit_exempt:
                time_slice = ratelimit.get_timeslice(g.RL_RESET_SECONDS)
                ratelimits = self.get_ratelimits(account)
                now = int(time.time())

                for rl, max_requests in ratelimits.iteritems():
                    try:
                        failed_logins = ratelimit.get_usage(str(rl), time_slice)

                        if failed_logins >= max_requests:
                            self.seconds = time_slice.end - now
                            period_end = datetime.utcfromtimestamp(
                                time_slice.end).replace(tzinfo=pytz.UTC)
                            remaining_text = utils.timeuntil(period_end)
                            self.set_error(
                                errors.RATELIMIT, {'time': remaining_text},
                                field='ratelimit', code=429)
                            g.stats.event_count('login.throttle', rl.category)
                            return False
                    except ratelimit.RatelimitError as e:
                        g.log.info("ratelimitcache error (login): %s", e)

            try:
                str(password)
            except UnicodeEncodeError:
                password = password.encode("utf8")

            if not valid_password(account, password):
                raise AuthenticationFailed
            g.stats.event_count('login', 'success')
            return account
        except AuthenticationFailed:
            g.stats.event_count('login', 'failure')
            if ratelimits:
                for rl in ratelimits:
                    try:
                        ratelimit.record_usage(str(rl), time_slice)
                    except ratelimit.RatelimitError as e:
                        g.log.info("ratelimitcache error (login): %s", e)
            self.error()
            return False

    def param_docs(self):
        return {
            self.param[0]: "a username",
            self.param[1]: "the user's password",
        }


class VSanitizedUrl(Validator):
    def run(self, url):
        return utils.sanitize_url(url)

    def param_docs(self):
        return {self.param: "a valid URL"}


class VUrl(VRequired):
    def __init__(self, item, allow_self=True, require_scheme=False,
                 valid_schemes=utils.VALID_SCHEMES, *a, **kw):
        self.allow_self = allow_self
        self.require_scheme = require_scheme
        self.valid_schemes = valid_schemes
        VRequired.__init__(self, item, errors.NO_URL, *a, **kw)

    def run(self, url):
        if not url:
            return self.error(errors.NO_URL)

        url = utils.sanitize_url(url, require_scheme=self.require_scheme,
                                 valid_schemes=self.valid_schemes)
        if not url:
            return self.error(errors.BAD_URL)

        try:
            url.encode('utf-8')
        except UnicodeDecodeError:
            return self.error(errors.BAD_URL)

        if url == 'self':
            if self.allow_self:
                return url
            else:
                self.error(errors.BAD_URL)
        else:
            return url

    def param_docs(self):
        return {self.param: "a valid URL"}


class VRedirectUri(VUrl):
    def __init__(self, item, valid_schemes=None, *a, **kw):
        VUrl.__init__(self, item, allow_self=False, require_scheme=True,
                      valid_schemes=valid_schemes, *a, **kw)

    def param_docs(self):
        doc = "a valid URI"
        if self.valid_schemes:
            doc += " with one of the following schemes: "
            doc += ", ".join(self.valid_schemes)
        return {self.param: doc}


class VShamedDomain(Validator):
    def run(self, url):
        if not url:
            return

        is_shamed, domain, reason = is_shamed_domain(url)

        if is_shamed:
            self.set_error(errors.DOMAIN_BANNED, dict(domain=domain,
                                                      reason=reason))

class VExistingUname(VRequired):
    def __init__(self, item, allow_deleted=False, *a, **kw):
        self.allow_deleted = allow_deleted
        VRequired.__init__(self, item, errors.NO_USER, *a, **kw)

    def run(self, name):
        if name:
            name = name.strip()
        if name and name.startswith('~') and c.user_is_admin:
            try:
                user_id = int(name[1:])
                return Account._byID(user_id, True)
            except (NotFound, ValueError):
                self.error(errors.USER_DOESNT_EXIST)

        # make sure the name satisfies our user name regexp before
        # bothering to look it up.
        name = chkuser(name)
        if name:
            try:
                return Account._by_name(name)
            except NotFound:
                if self.allow_deleted:
                    try:
                        return Account._by_name(name, allow_deleted=True)
                    except NotFound:
                        pass

                self.error(errors.USER_DOESNT_EXIST)
        else:
            self.error()

    def param_docs(self):
        return {
            self.param: 'the name of an existing user'
        }

class VMessageRecipient(VExistingUname):
    def run(self, name):
        if not name:
            return self.error()
        is_subreddit = False
        if name.startswith('/r/'):
            name = name[3:]
            is_subreddit = True
        elif name.startswith('#'):
            name = name[1:]
            is_subreddit = True

        # A user in timeout should only be able to message us, the admins.
        if (c.user.in_timeout and
                not (is_subreddit and
                     '/r/%s' % name == g.admin_message_acct)):
            abort(403, 'forbidden')

        if is_subreddit:
            try:
                s = Subreddit._by_name(name)
                if isinstance(s, FakeSubreddit):
                    raise NotFound, "fake subreddit"
                if s._spam:
                    raise NotFound, "banned subreddit"
                if s.is_muted(c.user) and not c.user_is_admin:
                    self.set_error(errors.USER_MUTED)
                return s
            except NotFound:
                self.set_error(errors.SUBREDDIT_NOEXIST)
        else:
            account = VExistingUname.run(self, name)
            if account and account._id in c.user.enemies:
                self.set_error(errors.USER_BLOCKED)
            else:
                return account

class VUserWithEmail(VExistingUname):
    def run(self, name):
        user = VExistingUname.run(self, name)
        if not user or not hasattr(user, 'email') or not user.email:
            return self.error(errors.NO_EMAIL_FOR_USER)
        return user


class VBoolean(Validator):
    def run(self, val):
        if val is True or val is False:
            # val is already a bool object, no processing needed
            return val
        lv = str(val).lower()
        if lv == 'off' or lv == '' or lv[0] in ("f", "n"):
            return False
        return bool(val)

    def param_docs(self):
        return {
            self.param: 'boolean value',
        }

class VNumber(Validator):
    def __init__(self, param, min=None, max=None, coerce = True,
                 error=errors.BAD_NUMBER, num_default=None,
                 *a, **kw):
        self.min = self.cast(min) if min is not None else None
        self.max = self.cast(max) if max is not None else None
        self.coerce = coerce
        self.error = error
        self.num_default = num_default
        Validator.__init__(self, param, *a, **kw)

    def cast(self, val):
        raise NotImplementedError

    def _set_error(self):
        if self.max is None and self.min is None:
            range = ""
        elif self.max is None:
            range = _("%(min)d to any") % dict(min=self.min)
        elif self.min is None:
            range = _("any to %(max)d") % dict(max=self.max)
        else:
            range = _("%(min)d to %(max)d") % dict(min=self.min, max=self.max)
        self.set_error(self.error, msg_params=dict(range=range))

    def run(self, val):
        if not val:
            return self.num_default
        try:
            val = self.cast(val)
            if self.min is not None and val < self.min:
                if self.coerce:
                    val = self.min
                else:
                    raise ValueError, ""
            elif self.max is not None and val > self.max:
                if self.coerce:
                    val = self.max
                else:
                    raise ValueError, ""
            return val
        except ValueError:
            self._set_error()

class VInt(VNumber):
    def cast(self, val):
        return int(val)

    def param_docs(self):
        if self.min is not None and self.max is not None:
            description = "an integer between %d and %d" % (self.min, self.max)
        elif self.min is not None:
            description = "an integer greater than %d" % self.min
        elif self.max is not None:
            description = "an integer less than %d" % self.max
        else:
            description = "an integer"

        if self.num_default is not None:
            description += " (default: %d)" % self.num_default

        return {self.param: description}


class VFloat(VNumber):
    def cast(self, val):
        return float(val)


class VDecimal(VNumber):
    def cast(self, val):
        return Decimal(val)


class VCssName(Validator):
    """
    returns a name iff it consists of alphanumeric characters and
    possibly "-", and is below the length limit.
    """

    r_css_name = re.compile(r"\A[a-zA-Z0-9\-]{1,100}\Z")

    def run(self, name):
        if name:
            if self.r_css_name.match(name):
                return name
            else:
                self.set_error(errors.BAD_CSS_NAME)
        return ''

    def param_docs(self):
        return {
            self.param: "a valid subreddit image name",
        }

class VColor(Validator):
    """Validate a string as being a 6 digit hex color starting with #"""
    color = re.compile(r"\A#[a-f0-9]{6}\Z", re.IGNORECASE)

    def run(self, color):
        if color:
            if self.color.match(color):
                return color.lower()
            else:
                self.set_error(errors.BAD_COLOR)
        return ''

    def param_docs(self):
        return {
            self.param: "a 6-digit rgb hex color, e.g. `#AABBCC`",
        }


class VMenu(Validator):
    def __init__(self, param, menu_cls, remember = True, **kw):
        self.nav = menu_cls
        self.remember = remember
        param = (menu_cls.name, param)
        Validator.__init__(self, param, **kw)

    def run(self, sort, where):
        if self.remember:
            pref = "%s_%s" % (where, self.nav.name)
            user_prefs = copy(c.user.sort_options) if c.user else {}
            user_pref = user_prefs.get(pref)

            # check to see if a default param has been set
            if not sort:
                sort = user_pref

        # validate the sort
        if sort not in self.nav._options:
            sort = self.nav._default

        # commit the sort if changed and if this is a POST request
        if (self.remember and c.user_is_loggedin and sort != user_pref
            and request.method.upper() == 'POST'):
            user_prefs[pref] = sort
            c.user.sort_options = user_prefs
            user = c.user
            user._commit()

        return sort

    def param_docs(self):
        return {
            self.param[0]: 'one of (%s)' % ', '.join("`%s`" % s
                                                  for s in self.nav._options),
        }


class VRatelimit(Validator):
    def __init__(self, rate_user=False, rate_ip=False, prefix='rate_',
                 error=errors.RATELIMIT, fatal=False, *a, **kw):
        self.rate_user = rate_user
        self.rate_ip = rate_ip
        self.name = prefix
        self.cache_prefix = "rl:%s" % self.name
        self.error = error
        self.fatal = fatal
        self.seconds = None
        Validator.__init__(self, *a, **kw)

    def run(self):
        if g.disable_ratelimit:
            return

        if c.user_is_loggedin:
            hook = hooks.get_hook("account.is_ratelimit_exempt")
            ratelimit_exempt = hook.call_until_return(account=c.user)
            if ratelimit_exempt:
                self._record_event(self.name, 'exempted')
                return

        to_check = []
        if self.rate_user and c.user_is_loggedin:
            to_check.append('user' + str(c.user._id36))
            self._record_event(self.name, 'check_user')
        if self.rate_ip:
            to_check.append('ip' + str(request.ip))
            self._record_event(self.name, 'check_ip')

        r = g.ratelimitcache.get_multi(to_check, prefix=self.cache_prefix)
        if r:
            expire_time = max(r.values())
            time = utils.timeuntil(expire_time)

            g.log.debug("rate-limiting %s from %s" % (self.name, r.keys()))
            for key in r.keys():
                if key.startswith('user'):
                    self._record_event(self.name, 'user_limit_hit')
                elif key.startswith('ip'):
                    self._record_event(self.name, 'ip_limit_hit')

            # when errors have associated field parameters, we'll need
            # to add that here
            if self.error == errors.RATELIMIT:
                from datetime import datetime
                delta = expire_time - datetime.now(g.tz)
                self.seconds = delta.total_seconds()
                if self.seconds < 3:  # Don't ratelimit within three seconds
                    return
                if self.fatal:
                    abort(429)
                self.set_error(errors.RATELIMIT, {'time': time},
                               field='ratelimit', code=429)
            else:
                if self.fatal:
                    abort(429)
                self.set_error(self.error)

    @classmethod
    def ratelimit(cls, rate_user=False, rate_ip=False, prefix="rate_",
                  seconds=None):
        name = prefix
        cache_prefix = "rl:%s" % name

        if seconds is None:
            seconds = g.RL_RESET_SECONDS

        expire_time = datetime.now(g.tz) + timedelta(seconds=seconds)

        to_set = {}
        if rate_user and c.user_is_loggedin:
            to_set['user' + str(c.user._id36)] = expire_time
            cls._record_event(name, 'set_user_limit')

        if rate_ip:
            to_set['ip' + str(request.ip)] = expire_time
            cls._record_event(name, 'set_ip_limit')

        g.ratelimitcache.set_multi(to_set, prefix=cache_prefix, time=seconds)

    @classmethod
    def _record_event(cls, name, event):
        g.stats.event_count('VRatelimit.%s' % name, event, sample_rate=0.1)


class VRatelimitImproved(Validator):
    """Enforce ratelimits on a function.

    This is a newer version of VRatelimit that uses the ratelimit lib.
    """

    class RateLimit(ratelimit.RateLimit):
        """A RateLimit with defaults specialized for VRatelimitImproved.

        Arguments:
            event_action: The type of the action the user took, for logging.
            event_type: Part of the key in the rate limit cache.
            limit: The RateLimit.limit value. Allowed hits per batch of seconds.
            seconds: The RateLimit.seconds value. How may seconds per batch.
            event_id_fn: Nullary function that derives an id from the current
                context.
        """
        sample_rate = 0.1

        def __init__(self,
                     event_action, event_type, limit, seconds, event_id_fn):
            ratelimit.RateLimit.__init__(self)
            self.event_name = 'VRatelimitImproved.' + event_action
            self.event_type = event_type
            self.event_id_fn = event_id_fn
            self.limit = limit
            self.seconds = seconds

        @property
        def key(self):
            return 'ratelimit-%s-%s' % (self.event_type, self.event_id_fn())

    def __init__(self, user_limit=None, ip_limit=None, error=errors.RATELIMIT,
                 *a, **kw):
        """
        At least one of user_limit and ip_limit should be set for this function
        to have any effect.

        Arguments:
            user_limit: RateLimit -- The per-user rate limit.
            ip_limit: RateLimit -- The per-IP rate limit.
            error -- the error message to use when the limit is exceeded.
        """
        self.user_limit = user_limit
        self.ip_limit = ip_limit
        self.error = error

        # _validatedForm passes self.seconds to the current form's javascript.
        self.seconds = None
        Validator.__init__(self, *a, **kw)

    def run(self):
        if g.disable_ratelimit:
            return

        if c.user_is_loggedin:
            hook = hooks.get_hook("account.is_ratelimit_exempt")
            ratelimit_exempt = hook.call_until_return(account=c.user)
            if ratelimit_exempt:
                return

        if self.user_limit and c.user_is_loggedin:
            self._check_usage(self.user_limit)

        if self.ip_limit:
            self._check_usage(self.ip_limit)

    def _check_usage(self, rate_limit):
        """Check ratelimit usage and set an error if necessary."""
        if rate_limit.check():
            # Not rate limited.
            return

        g.log.debug('rate-limiting %s with %s used',
                    rate_limit.key, rate_limit.get_usage())
        # When errors have associated field parameters, we'll need
        # to add that here.
        if self.error == errors.RATELIMIT:
            period_end = datetime.utcfromtimestamp(
                rate_limit.timeslice.end).replace(tzinfo=pytz.UTC)
            time = utils.timeuntil(period_end)
            self.set_error(errors.RATELIMIT, {'time': time},
                            field='ratelimit', code=429)
        else:
            self.set_error(self.error)

    @classmethod
    def ratelimit(cls, user_limit=None, ip_limit=None):
        """Record usage of a resource."""
        if user_limit and c.user_is_loggedin:
            user_limit.record_usage()

        if ip_limit:
            ip_limit.record_usage()


class VShareRatelimit(VRatelimitImproved):
    USER_LIMIT = VRatelimitImproved.RateLimit(
        'share', 'user',
        limit=g.RL_SHARE_MAX_REQS,
        seconds=g.RL_RESET_SECONDS,
        event_id_fn=lambda: c.user._id36)

    IP_LIMIT = VRatelimitImproved.RateLimit(
        'share', 'ip',
        limit=g.RL_SHARE_MAX_REQS,
        seconds=g.RL_RESET_SECONDS,
        event_id_fn=lambda: request.ip)

    def __init__(self):
        super(VShareRatelimit, self).__init__(
            user_limit=self.USER_LIMIT, ip_limit=self.IP_LIMIT)

    @classmethod
    def ratelimit(cls):
        super(VShareRatelimit, cls).ratelimit(
            user_limit=cls.USER_LIMIT, ip_limit=cls.IP_LIMIT)


class VCommentIDs(Validator):
    def run(self, id_str):
        if id_str:
            try:
                cids = [int(i, 36) for i in id_str.split(',')]
                return cids
            except ValueError:
                abort(400)
        return []

    def param_docs(self):
        return {
            self.param: "a comma-delimited list of comment ID36s",
        }


class VOneTimeToken(Validator):
    def __init__(self, model, param, *args, **kwargs):
        self.model = model
        Validator.__init__(self, param, *args, **kwargs)

    def run(self, key):
        token = self.model.get_token(key)

        if token:
            return token
        else:
            self.set_error(errors.EXPIRED)
            return None

class VOneOf(Validator):
    def __init__(self, param, options = (), *a, **kw):
        Validator.__init__(self, param, *a, **kw)
        self.options = options

    def run(self, val):
        if self.options and val not in self.options:
            self.set_error(errors.INVALID_OPTION, code=400)
            return self.default
        else:
            return val

    def param_docs(self):
        return {
            self.param: 'one of (%s)' % ', '.join("`%s`" % s
                                                  for s in self.options),
        }


class VList(Validator):
    def __init__(self, param, separator=",", choices=None,
                 error=errors.INVALID_OPTION, *a, **kw):
        Validator.__init__(self, param, *a, **kw)
        self.separator = separator
        self.choices = choices
        self.error = error

    def run(self, items):
        if not items:
            return None
        all_values = items.split(self.separator)
        if self.choices is None:
            return all_values

        values = []
        for val in all_values:
            if val in self.choices:
                values.append(val)
            else:
                msg_params = {"choice": val}
                self.set_error(self.error, msg_params=msg_params,
                               code=400)
        return values

    # Not i18n'able, but param_docs are not currently i18n'ed
    NICE_SEP = {",": "comma"}
    def param_docs(self):
        if self.choices:
            msg = ("A %(separator)s-separated list of items from "
                   "this set:\n\n%(choices)s")
            choices = "`" + "`  \n`".join(self.choices) + "`"
        else:
            msg = "A %(separator)s-separated list of items"
            choices = None

        sep = self.NICE_SEP.get(self.separator, self.separator)
        docs = msg % {"separator": sep, "choices": choices}
        return {self.param: docs}


class VFrequencyCap(Validator):
    def run(self, frequency_capped='false', frequency_cap=None):

        if frequency_capped == 'true':
            if frequency_cap and int(frequency_cap) >= g.frequency_cap_min:
                try:
                    return frequency_cap
                except (ValueError, TypeError):
                    self.set_error(errors.INVALID_FREQUENCY_CAP, code=400)
            else:
                self.set_error(
                    errors.FREQUENCY_CAP_TOO_LOW,
                    {'min': g.frequency_cap_min},
                    code=400
                )
        else:
            return None


class VPriority(Validator):
    def run(self, val):
        if c.user_is_sponsor:
            return (PROMOTE_PRIORITIES.get(val,
                PROMOTE_DEFAULT_PRIORITY(context=c)))
        elif feature.is_enabled('ads_auction'):
            return PROMOTE_DEFAULT_PRIORITY(context=c)
        else:
            return PROMOTE_PRIORITIES['standard']


class VLocation(Validator):
    default_param = ("country", "region", "metro")

    def run(self, country, region, metro):
        # some browsers are sending "null" rather than omitting the input when
        # the select is disabled
        country, region, metro = map(lambda val: None if val == "null" else val,
                                     [country, region, metro])

        if not (country or region or metro):
            return None

        # Sponsors should only be creating fixed-CPM campaigns, which we
        # cannot calculate region specific inventory for
        if c.user_is_sponsor and region and not (region and metro):
            invalid_region = True
        else:
            invalid_region = False

        # Non-sponsors can only create auctions (non-inventory), so they
        # can target country, country/region, and country/region/metro
        if not (country and not (region or metro) or
                (country and region and not metro) or
                (country and region and metro)):
            invalid_geotargets = True
        else:
            invalid_geotargets = False

        if (country not in g.locations or
                region and region not in g.locations[country]['regions'] or
                metro and metro not in g.locations[country]['regions'][region]['metros']):
            nonexistent_geotarget = True
        else:
            nonexistent_geotarget = False

        if invalid_region or invalid_geotargets or nonexistent_geotarget:
            self.set_error(errors.INVALID_LOCATION, code=400, field='location')
        else:
            return Location(country, region, metro)


class VImageType(Validator):
    def run(self, img_type):
        if not img_type in ('png', 'jpg'):
            return 'png'
        return img_type

    def param_docs(self):
        return {
            self.param: "one of `png` or `jpg` (default: `png`)",
        }


class ValidEmail(Validator):
    """Validates a single email. Returns the email on success."""

    def run(self, email):
        # Strip out leading/trailing whitespace, since the inclusion of that is
        # a common and easily-fixable user error.
        if email is not None:
            email = email.strip()

        if not email:
            self.set_error(errors.NO_EMAIL)
        elif not ValidEmails.email_re.match(email):
            self.set_error(errors.BAD_EMAIL)
        else:
            return email


class ValidEmails(Validator):
    """Validates a list of email addresses passed in as a string and
    delineated by whitespace, ',' or ';'.  Also validates quantity of
    provided emails.  Returns a list of valid email addresses on
    success"""

    separator = re.compile(r'[^\s,;]+')
    email_re  = re.compile(r'\A[^\s@]+@[^\s@]+\.[^\s@]+\Z')

    def __init__(self, param, num = 20, **kw):
        self.num = num
        Validator.__init__(self, param = param, **kw)

    def run(self, emails0):
        emails = set(self.separator.findall(emails0) if emails0 else [])
        failures = set(e for e in emails if not self.email_re.match(e))
        emails = emails - failures

        # make sure the number of addresses does not exceed the max
        if self.num > 0 and len(emails) + len(failures) > self.num:
            # special case for 1: there should be no delineators at all, so
            # send back original string to the user
            if self.num == 1:
                self.set_error(errors.BAD_EMAILS,
                             {'emails': '"%s"' % emails0})
            # else report the number expected
            else:
                self.set_error(errors.TOO_MANY_EMAILS,
                             {'num': self.num})
        # correct number, but invalid formatting
        elif failures:
            self.set_error(errors.BAD_EMAILS,
                         {'emails': ', '.join(failures)})
        # no emails
        elif not emails:
            self.set_error(errors.NO_EMAILS)
        else:
            # return single email if one is expected, list otherwise
            return list(emails)[0] if self.num == 1 else emails

class ValidEmailsOrExistingUnames(Validator):
    """Validates a list of mixed email addresses and usernames passed in
    as a string, delineated by whitespace, ',' or ';'.  Validates total
    quantity too while we're at it.  Returns a tuple of the form
    (e-mail addresses, user account objects)"""

    def __init__(self, param, num=20, **kw):
        self.num = num
        Validator.__init__(self, param=param, **kw)

    def run(self, items):
        # Use ValidEmails separator to break the list up
        everything = set(ValidEmails.separator.findall(items) if items else [])

        # Use ValidEmails regex to divide the list into e-mail and other
        emails = set(e for e in everything if ValidEmails.email_re.match(e))
        failures = everything - emails

        # Run the rest of the validator against the e-mails list
        ve = ValidEmails(self.param, self.num)
        if len(emails) > 0:
            ve.run(", ".join(emails))

        # ValidEmails will add to c.errors for us, so do nothing if that fails
        # Elsewise, on with the users
        if not ve.has_errors:
            users = set()  # set of accounts
            validusers = set()  # set of usernames to subtract from failures

            # Now steal from VExistingUname:
            for uname in failures:
                check = uname
                if re.match('/u/', uname):
                    check = check[3:]
                veu = VExistingUname(check)
                account = veu.run(check)
                if account:
                    validusers.add(uname)
                    users.add(account)

            # We're fine if all our failures turned out to be valid users
            if len(users) == len(failures):
                # ValidEmails checked to see if there were too many addresses,
                # check to see if there's enough left-over space for users
                remaining = self.num - len(emails)
                if len(users) > remaining:
                    if self.num == 1:
                        # We only wanted one, and we got it as an e-mail,
                        # so complain.
                        self.set_error(errors.BAD_EMAILS,
                                       {"emails": '"%s"' % items})
                    else:
                        # Too many total
                        self.set_error(errors.TOO_MANY_EMAILS,
                                       {"num": self.num})
                elif len(users) + len(emails) == 0:
                    self.set_error(errors.NO_EMAILS)
                else:
                    # It's all good!
                    return (emails, users)
            else:
                failures = failures - validusers
                self.set_error(errors.BAD_EMAILS,
                               {'emails': ', '.join(failures)})

class VCnameDomain(Validator):
    domain_re  = re.compile(r'\A([\w\-_]+\.)+[\w]+\Z')

    def run(self, domain):
        if (domain
            and (not self.domain_re.match(domain)
                 or domain.endswith('.' + g.domain)
                 or domain.endswith('.' + g.media_domain)
                 or len(domain) > 300)):
            self.set_error(errors.BAD_CNAME)
        elif domain:
            try:
                return str(domain).lower()
            except UnicodeEncodeError:
                self.set_error(errors.BAD_CNAME)

    def param_docs(self):
        # cnames are dead; don't advertise this.
        return {}


class VDate(Validator):
    """
    Date checker that accepts string inputs.

    Error conditions:
       * BAD_DATE on mal-formed date strings (strptime parse failure)

    """

    def __init__(self, param, format="%m/%d/%Y", required=True):
        self.format = format
        self.required = required
        Validator.__init__(self, param)

    def run(self, datestr):
        if not datestr and not self.required:
            return None

        try:
            dt = datetime.strptime(datestr, self.format)
            return dt.replace(tzinfo=g.tz)
        except (ValueError, TypeError):
            self.set_error(errors.BAD_DATE)


class VDestination(Validator):
    def __init__(self, param = 'dest', default = "", **kw):
        Validator.__init__(self, param, default, **kw)

    def run(self, dest):
        if not dest:
            dest = self.default or add_sr("/")

        ld = dest.lower()
        if ld.startswith(('/', 'http://', 'https://')):
            u = UrlParser(dest)

            if u.is_reddit_url(c.site) and u.is_web_safe_url():
                return dest

        return "/"

    def param_docs(self):
        return {
            self.param: 'destination url (must be same-domain)',
        }

class ValidAddress(Validator):
    def set_error(self, msg, field):
        Validator.set_error(self, errors.BAD_ADDRESS,
                            dict(message=msg), field = field)

    def run(self, firstName, lastName, company, address,
            city, state, zipCode, country, phoneNumber):
        if not firstName:
            self.set_error(_("please provide a first name"), "firstName")
        elif not lastName:
            self.set_error(_("please provide a last name"), "lastName")
        elif not address:
            self.set_error(_("please provide an address"), "address")
        elif not city:
            self.set_error(_("please provide your city"), "city")
        elif not state:
            self.set_error(_("please provide your state"), "state")
        elif not zipCode:
            self.set_error(_("please provide your zip or post code"), "zip")
        elif not country:
            self.set_error(_("please provide your country"), "country")

        # Make sure values don't exceed max length defined in the authorize.net
        # xml schema: https://api.authorize.net/xml/v1/schema/AnetApiSchema.xsd
        max_lengths = [
            (firstName, 50, 'firstName'), # (argument, max len, form field name)
            (lastName, 50, 'lastName'),
            (company, 50, 'company'),
            (address, 60, 'address'),
            (city, 40, 'city'),
            (state, 40, 'state'),
            (zipCode, 20, 'zip'),
            (country, 60, 'country'),
            (phoneNumber, 255, 'phoneNumber')
        ]
        for (arg, max_length, form_field_name) in max_lengths:
            if arg and len(arg) > max_length:
                self.set_error(_("max length %d characters" % max_length), form_field_name)

        if not self.has_errors:
            return Address(firstName = firstName,
                           lastName = lastName,
                           company = company or "",
                           address = address,
                           city = city, state = state,
                           zip = zipCode, country = country,
                           phoneNumber = phoneNumber or "")

class ValidCard(Validator):
    valid_date = re.compile(r"\d\d\d\d-\d\d")
    def set_error(self, msg, field):
        Validator.set_error(self, errors.BAD_CARD,
                            dict(message=msg), field = field)

    def run(self, cardNumber, expirationDate, cardCode):
        has_errors = False

        cardNumber = cardNumber or ""
        if not (cardNumber.isdigit() and 13 <= len(cardNumber) <= 16):
            self.set_error(_("credit card numbers should be 13 to 16 digits"),
                           "cardNumber")
            has_errors = True

        if not self.valid_date.match(expirationDate or ""):
            self.set_error(_("dates should be YYYY-MM"), "expirationDate")
            has_errors = True
        else:
            now = datetime.now(g.tz)
            yyyy, mm = expirationDate.split("-")
            year = int(yyyy)
            month = int(mm)
            if month < 1 or month > 12:
                self.set_error(_("month must be in the range 01..12"), "expirationDate")
                has_errors = True
            elif datetime(year, month, 1) < datetime(now.year, now.month, 1):
                self.set_error(_("expiration date must be in the future"), "expirationDate")
                has_errors = True

        cardCode = cardCode or ""
        if not (cardCode.isdigit() and 3 <= len(cardCode) <= 4):
            self.set_error(_("card verification codes should be 3 or 4 digits"),
                           "cardCode")
            has_errors = True

        if not has_errors:
            return CreditCard(cardNumber = cardNumber,
                              expirationDate = expirationDate,
                              cardCode = cardCode)

class VTarget(Validator):
    target_re = re.compile("\A[\w_-]{3,20}\Z")
    def run(self, name):
        if name and self.target_re.match(name):
            return name

    def param_docs(self):
        # this is just for htmllite and of no interest to api consumers
        return {}

class VFlairAccount(VRequired):
    def __init__(self, item, *a, **kw):
        VRequired.__init__(self, item, errors.BAD_FLAIR_TARGET, *a, **kw)

    def _lookup(self, name, allow_deleted):
        try:
            return Account._by_name(name, allow_deleted=allow_deleted)
        except NotFound:
            return None

    def run(self, name):
        if not name:
            return self.error()
        return (
            self._lookup(name, False)
            or self._lookup(name, True)
            or self.error())

    def param_docs(self):
        return {self.param: _("a user by name")}

class VFlairLink(VRequired):
    def __init__(self, item, *a, **kw):
        VRequired.__init__(self, item, errors.BAD_FLAIR_TARGET, *a, **kw)

    def run(self, name):
        if not name:
            return self.error()
        try:
            return Link._by_fullname(name, data=True)
        except NotFound:
            return self.error()

    def param_docs(self):
        return {self.param: _("a [fullname](#fullname) of a link")}

class VFlairCss(VCssName):
    def __init__(self, param, max_css_classes=10, **kw):
        self.max_css_classes = max_css_classes
        VCssName.__init__(self, param, **kw)

    def run(self, css):
        if not css:
            return css

        names = css.split()
        if len(names) > self.max_css_classes:
            self.set_error(errors.TOO_MUCH_FLAIR_CSS)
            return ''

        for name in names:
            if not self.r_css_name.match(name):
                self.set_error(errors.BAD_CSS_NAME)
                return ''

        return css

class VFlairText(VLength):
    def __init__(self, param, max_length=64, **kw):
        VLength.__init__(self, param, max_length, **kw)

class VFlairTemplateByID(VRequired):
    def __init__(self, param, **kw):
        VRequired.__init__(self, param, None, **kw)

    def run(self, flair_template_id):
        try:
            return FlairTemplateBySubredditIndex.get_template(
                c.site._id, flair_template_id)
        except tdb_cassandra.NotFound:
            return None

class VOneTimePassword(Validator):
    allowed_skew = [-1, 0, 1]  # allow a period of skew on either side of now
    ratelimit = 3  # maximum number of tries per period

    def __init__(self, param, required):
        self.required = required
        Validator.__init__(self, param)

    @classmethod
    def validate_otp(cls, secret, password):
        # is the password a valid format and has it been used?
        try:
            key = "otp:used_%s_%d" % (c.user._id36, int(password))
        except (TypeError, ValueError):
            valid_and_unused = False
        else:
            # leave this key around for one more time period than the maximum
            # number of time periods we'll check for valid passwords
            key_ttl = totp.PERIOD * (len(cls.allowed_skew) + 1)
            valid_and_unused = g.gencache.add(key, True, time=key_ttl)

        # check the password (allowing for some clock-skew as 2FA-users
        # frequently travel at relativistic velocities)
        if valid_and_unused:
            for skew in cls.allowed_skew:
                expected_otp = totp.make_totp(secret, skew=skew)
                if constant_time_compare(password, expected_otp):
                    return True

        return False

    def run(self, password):
        # does the user have 2FA configured?
        secret = c.user.otp_secret
        if not secret:
            if self.required:
                self.set_error(errors.NO_OTP_SECRET)
            return

        # do they have the otp cookie instead?
        if c.otp_cached:
            return

        # make sure they're not trying this too much
        if not g.disable_ratelimit:
            current_password = totp.make_totp(secret)
            otp_ratelimit = ratelimit.SimpleRateLimit(
                name="otp_tries_%s_%s" % (c.user._id36, current_password),
                seconds=600,
                limit=self.ratelimit,
            )
            if not otp_ratelimit.record_and_check():
                self.set_error(errors.RATELIMIT, dict(time="30 seconds"))
                return

        # check the password
        if self.validate_otp(secret, password):
            return

        # if we got this far, their password was wrong, invalid or already used
        self.set_error(errors.WRONG_PASSWORD)

class VOAuth2ClientID(VRequired):
    default_param = "client_id"
    default_param_doc = _("an app")
    def __init__(self, param=None, *a, **kw):
        VRequired.__init__(self, param, errors.OAUTH2_INVALID_CLIENT, *a, **kw)

    def run(self, client_id):
        client_id = VRequired.run(self, client_id)
        if client_id:
            client = OAuth2Client.get_token(client_id)
            if client and not client.deleted:
                return client
            else:
                self.error()

    def param_docs(self):
        return {self.default_param: self.default_param_doc}

class VOAuth2ClientDeveloper(VOAuth2ClientID):
    default_param_doc = _("an app developed by the user")

    def run(self, client_id):
        client = super(VOAuth2ClientDeveloper, self).run(client_id)
        if not client or not client.has_developer(c.user):
            return self.error()
        return client

class VOAuth2Scope(VRequired):
    default_param = "scope"
    def __init__(self, param=None, *a, **kw):
        VRequired.__init__(self, param, errors.OAUTH2_INVALID_SCOPE, *a, **kw)

    def run(self, scope):
        scope = VRequired.run(self, scope)
        if scope:
            parsed_scope = OAuth2Scope(scope)
            if parsed_scope.is_valid():
                return parsed_scope
            else:
                self.error()

class VOAuth2RefreshToken(Validator):
    def __init__(self, param, *a, **kw):
        Validator.__init__(self, param, None, *a, **kw)

    def run(self, refresh_token_id):
        if refresh_token_id:
            try:
                token = OAuth2RefreshToken._byID(refresh_token_id)
            except tdb_cassandra.NotFound:
                self.set_error(errors.OAUTH2_INVALID_REFRESH_TOKEN)
                return None
            if not token.check_valid():
                self.set_error(errors.OAUTH2_INVALID_REFRESH_TOKEN)
                return None
            return token
        else:
            return None

class VPermissions(Validator):
    types = dict(
        moderator=ModeratorPermissionSet,
        moderator_invite=ModeratorPermissionSet,
    )

    def __init__(self, type_param, permissions_param, *a, **kw):
        Validator.__init__(self, (type_param, permissions_param), *a, **kw)

    def run(self, type, permissions):
        permission_class = self.types.get(type)
        if not permission_class:
            self.set_error(errors.INVALID_PERMISSION_TYPE, field=self.param[0])
            return (None, None)
        try:
            perm_set = permission_class.loads(permissions, validate=True)
        except ValueError:
            self.set_error(errors.INVALID_PERMISSIONS, field=self.param[1])
            return (None, None)
        return type, perm_set


class VJSON(Validator):
    def run(self, json_str):
        if not json_str:
            return self.set_error('JSON_PARSE_ERROR', code=400)
        else:
            try:
                return json.loads(json_str)
            except ValueError:
                return self.set_error('JSON_PARSE_ERROR', code=400)

    def param_docs(self):
        return {
            self.param: "JSON data",
        }


class VValidatedJSON(VJSON):
    """Apply validators to the values of JSON formatted data."""
    class ArrayOf(object):
        """A JSON array of objects with the specified schema."""
        def __init__(self, spec):
            self.spec = spec

        def run(self, data):
            if not isinstance(data, list):
                raise RedditError('JSON_INVALID', code=400)

            validated_data = []
            for item in data:
                validated_data.append(self.spec.run(item))
            return validated_data

        def spec_docs(self):
            spec_lines = []
            spec_lines.append('[')
            if hasattr(self.spec, 'spec_docs'):
                array_docs = self.spec.spec_docs()
            else:
                array_docs = self.spec.param_docs()[self.spec.param]
            for line in array_docs.split('\n'):
                spec_lines.append('  ' + line)
            spec_lines[-1] += ','
            spec_lines.append('  ...')
            spec_lines.append(']')
            return '\n'.join(spec_lines)


    class Object(object):
        """A JSON object with validators for specified fields."""
        def __init__(self, spec):
            self.spec = spec

        def run(self, data, ignore_missing=False):
            if not isinstance(data, dict):
                raise RedditError('JSON_INVALID', code=400)

            validated_data = {}
            for key, validator in self.spec.iteritems():
                try:
                    validated_data[key] = validator.run(data[key])
                except KeyError:
                    if ignore_missing:
                        continue
                    raise RedditError('JSON_MISSING_KEY', code=400,
                                      msg_params={'key': key})
            return validated_data

        def spec_docs(self):
            spec_docs = {}
            for key, validator in self.spec.iteritems():
                if hasattr(validator, 'spec_docs'):
                    spec_docs[key] = validator.spec_docs()
                elif hasattr(validator, 'param_docs'):
                    spec_docs.update(validator.param_docs())
                    if validator.docs:
                        spec_docs.update(validator.docs)

            # generate markdown json schema docs
            spec_lines = []
            spec_lines.append('{')
            for key in sorted(spec_docs.keys()):
                key_docs = spec_docs[key]
                # indent any new lines
                key_docs = key_docs.replace('\n', '\n  ')
                spec_lines.append('  "%s": %s,' % (key, key_docs))
            spec_lines.append('}')
            return '\n'.join(spec_lines)

    class PartialObject(Object):
        def run(self, data):
            super_ = super(VValidatedJSON.PartialObject, self)
            return super_.run(data, ignore_missing=True)

    def __init__(self, param, spec, **kw):
        VJSON.__init__(self, param, **kw)
        self.spec = spec

    def run(self, json_str):
        data = VJSON.run(self, json_str)
        if self.has_errors:
            return

        # Note: this relies on the fact that all validator errors are dumped
        # into a global (c.errors) and then checked by @validate.
        return self.spec.run(data)

    def docs_model(self):
        spec_md = self.spec.spec_docs()

        # indent for code formatting
        spec_md = '\n'.join(
            '    ' + line for line in spec_md.split('\n')
        )
        return spec_md

    def param_docs(self):
        return {
            self.param: 'json data:\n\n' + self.docs_model(),
        }


multi_name_rx = re.compile(r"\A[A-Za-z0-9][A-Za-z0-9_]{1,20}\Z")
multi_name_chars_rx = re.compile(r"[^A-Za-z0-9_]")

class VMultiPath(Validator):
    """Validates a multireddit path. Returns a path info dictionary.
    """
    def __init__(self, param, kinds=None, required=True, **kw):
        Validator.__init__(self, param, **kw)
        self.required = required
        self.kinds = tup(kinds or ('f', 'm'))

    @classmethod
    def normalize(self, path):
        if path[0] != '/':
            path = '/' + path
        path = path.lower().rstrip('/')
        return path

    def run(self, path):
        if not path and not self.required:
            return None
        try:
            require(path)
            path = self.normalize(path)
            require(path.startswith('/user/'))
            prefix, owner, kind, name = require_split(path, 5, sep='/')[1:]
            require(kind in self.kinds)
            owner = chkuser(owner)
            require(owner)
        except RequirementException:
            self.set_error('BAD_MULTI_PATH', code=400)
            return

        try:
            require(multi_name_rx.match(name))
        except RequirementException:
            invalid_char = multi_name_chars_rx.search(name)
            if invalid_char:
                char = invalid_char.group()
                if char == ' ':
                    reason = _('no spaces allowed')
                else:
                    reason = _("invalid character: '%s'") % char
            elif name[0] == '_':
                reason = _("can't start with a '_'")
            elif len(name) < 2:
                reason = _('that name is too short')
            elif len(name) > 21:
                reason = _('that name is too long')
            else:
                reason = _("that name isn't going to work")

            self.set_error('BAD_MULTI_NAME', {'reason': reason}, code=400)
            return

        return {'path': path, 'prefix': prefix, 'owner': owner, 'name': name}

    def param_docs(self):
        return {
            self.param: "multireddit url path",
        }


class VMultiByPath(Validator):
    """Validates a multireddit path.  Returns a LabeledMulti.
    """
    def __init__(self, param, require_view=True, require_edit=False, kinds=None):
        Validator.__init__(self, param)
        self.require_view = require_view
        self.require_edit = require_edit
        self.kinds = tup(kinds or ('f', 'm'))

    def run(self, path):
        path = VMultiPath.normalize(path)
        if not path.startswith('/user/'):
            return self.set_error('MULTI_NOT_FOUND', code=404)

        name = path.split('/')[-1]
        if not multi_name_rx.match(name):
            return self.set_error('MULTI_NOT_FOUND', code=404)

        try:
            multi = LabeledMulti._byID(path)
        except tdb_cassandra.NotFound:
            return self.set_error('MULTI_NOT_FOUND', code=404)

        if not multi or multi.kind not in self.kinds:
            return self.set_error('MULTI_NOT_FOUND', code=404)
        if not multi or (self.require_view and not multi.can_view(c.user)):
            return self.set_error('MULTI_NOT_FOUND', code=404)
        if self.require_edit and not multi.can_edit(c.user):
            return self.set_error('MULTI_CANNOT_EDIT', code=403)

        return multi

    def param_docs(self):
        return {
            self.param: "multireddit url path",
        }


sr_path_rx = re.compile(r"\A(/?r/)?(?P<name>.*?)/?\Z")
class VSubredditList(Validator):

    def __init__(self, param, limit=20, allow_language_srs=True):
        Validator.__init__(self, param)
        self.limit = limit
        self.allow_language_srs = allow_language_srs

    def run(self, subreddits):
        if not subreddits:
            return []

        # extract subreddit name if path provided
        subreddits = [sr_path_rx.sub('\g<name>', sr.strip())
                      for sr in subreddits.lower().strip().splitlines() if sr]

        for name in subreddits:
            valid_name = Subreddit.is_valid_name(
                name, allow_language_srs=self.allow_language_srs)
            if not valid_name:
                return self.set_error(errors.BAD_SR_NAME, code=400)

        unique_srs = set(subreddits)

        if subreddits:
            valid_srs = set(Subreddit._by_name(subreddits).keys())
            if unique_srs - valid_srs:
                return self.set_error(errors.SUBREDDIT_NOEXIST, code=400)

        if len(unique_srs) > self.limit:
            return self.set_error(
                errors.TOO_MANY_SUBREDDITS, {'max': self.limit}, code=400)

        # return list of subreddit names as entered
        return subreddits

    def param_docs(self):
        return {
            self.param: 'a list of subreddit names, line break delimited',
        }


class VResultTypes(Validator):
    """
    Validates a list of search result types, provided either as multiple
    GET parameters or as a comma separated list.  Returns a set.
    """
    def __init__(self, param):
        Validator.__init__(self, param, get_multiple=True)
        self.default = []
        self.options = {'link', 'sr'}

    def run(self, result_types):
        if result_types and ',' in result_types[0]:
            result_types = result_types[0].strip(',').split(',')

        # invalid values are ignored
        result_types = set(result_types) & self.options

        # for backwards compatibility, api and legacy default to link results
        if is_api():
            result_types = result_types or {'link'}
        elif feature.is_enabled('legacy_search') or c.user.pref_legacy_search:
            result_types = {'link'}
        else:
            result_types = result_types or {'link', 'sr'}

        return result_types

    def param_docs(self):
        return {
            self.param: (
                '(optional) comma-delimited list of result types '
                '(`%s`)' % '`, `'.join(self.options)
            ),
        }


class VSigned(Validator):
    """Validate if the request is properly signed.

    Checks the headers (mostly the User-Agent) are signed with
    :py:function:`~r2.lib.signing.valid_ua_signature` and in the case
    of POST and PUT ensure that any request.body included is also signed
    via :py:function:`~r2.lib.signing.valid_body_signature`.

    In :py:method:`run`, the signatures are combined as needed to generate a
    final signature that is generally the combination of the two.
    """

    def run(self):
        signature = signing.valid_ua_signature(request)

        # only check the request body when there should be one
        if request.method.upper() in ("POST", "PUT"):
            signature.update(signing.valid_post_signature(request))

        # add a simple event for each error as it appears (independent of
        # whether we're going to ignore them).
        for code, field in signature.errors:
            g.stats.simple_event(
                "signing.%s.invalid.%s" % (field, code.lower())
            )

        # persistent skew problems on android suggest something deeper is
        # wrong in v1.  Disable the expiration check for now!
        if signature.platform == "android" and signature.version == 1:
            signature.add_ignore(signing.ERRORS.EXPIRED_TOKEN)

        return signature


def need_provider_captcha():
    return False
