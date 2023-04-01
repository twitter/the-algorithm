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

import hmac
import hashlib
import urllib

from r2.config import feature
from r2.models import *
from filters import (
    _force_unicode,
    _force_utf8,
    conditional_websafe,
    keep_space,
    unsafe,
    double_websafe,
    websafe,
)
from r2.lib.cache_poisoning import make_poisoning_report_mac
from r2.lib.utils import UrlParser, timeago, timesince, is_subdomain

from r2.lib import hooks
from r2.lib.static import static_mtime
from r2.lib import js, tracking

import babel.numbers
import simplejson
import os.path
from copy import copy
import random
import urlparse
import calendar
import math
import time
import pytz

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _, ungettext

static_text_extensions = {
    '.js': 'js',
    '.css': 'css',
    '.less': 'css'
}
def static(path, absolute=False, mangle_name=True):
    """
    Simple static file maintainer which automatically paths and
    versions files being served out of static.

    In the case of JS and CSS where g.uncompressedJS is set, the
    version of the file is set to be random to prevent caching and it
    mangles the path to point to the uncompressed versions.
    """
    dirname, filename = os.path.split(path)
    extension = os.path.splitext(filename)[1]
    is_text = extension in static_text_extensions
    should_cache_bust = False

    path_components = []
    actual_filename = None if mangle_name else filename

    # If building an absolute url, default to https because we like it and the
    # static server should support it.
    scheme = 'https' if absolute else None

    if g.static_domain:
        domain = g.static_domain
    else:
        path_components.append(c.site.static_path)

        if g.uncompressedJS:
            # unminified static files are in type-specific subdirectories
            if not dirname and is_text:
                path_components.append(static_text_extensions[extension])

            should_cache_bust = True
            actual_filename = filename

        domain = g.domain if absolute else None

    path_components.append(dirname)
    if not actual_filename:
        actual_filename = g.static_names.get(filename, filename)
    path_components.append(actual_filename)

    actual_path = os.path.join(*path_components)

    query = None
    if path and should_cache_bust:
        file_id = static_mtime(actual_path) or random.randint(0, 1000000)
        query = 'v=' + str(file_id)

    return urlparse.urlunsplit((
        scheme,
        domain,
        actual_path,
        query,
        None
    ))


def make_url_protocol_relative(url):
    if not url or url.startswith("//"):
        return url

    scheme, netloc, path, query, fragment = urlparse.urlsplit(url)
    return urlparse.urlunsplit((None, netloc, path, query, fragment))


def make_url_https(url):
    if not url or url.startswith("https://"):
        return url

    scheme, netloc, path, query, fragment = urlparse.urlsplit(url)
    return urlparse.urlunsplit(("https", netloc, path, query, fragment))


def header_url(url, absolute=False):
    if url == g.default_header_url:
        return static(url, absolute=absolute)
    elif absolute:
        return make_url_https(url)
    else:
        return make_url_protocol_relative(url)


def js_config(extra_config=None):
    logged = c.user_is_loggedin and c.user.name
    user_id = c.user_is_loggedin and c.user._id
    user_in_timeout = c.user_is_loggedin and c.user.in_timeout
    gold = bool(logged and c.user.gold)
    controller_name = request.environ['pylons.routes_dict']['controller']
    action_name = request.environ['pylons.routes_dict']['action']
    route_name = controller_name + '.' + action_name

    cache_policy = "loggedout_www"
    if c.user_is_loggedin:
        cache_policy = "loggedin_www_new"

    # Canary for detecting cache poisoning
    poisoning_canary = None
    poisoning_report_mac = None
    if logged:
        if "pc" in c.cookies and len(c.cookies["pc"].value) == 2:
            poisoning_canary = c.cookies["pc"].value
            poisoning_report_mac = make_poisoning_report_mac(
                poisoner_canary=poisoning_canary,
                poisoner_name=logged,
                poisoner_id=user_id,
                cache_policy=cache_policy,
                source="web",
                route_name=route_name,
            )

    mac = hmac.new(g.secrets["action_name"], route_name, hashlib.sha1)
    verification = mac.hexdigest()
    cur_subreddit = ""
    cur_sr_fullname = ""
    cur_listing = ""
    listing_over_18 = False
    pref_no_profanity = not logged or c.user.pref_no_profanity
    pref_media_preview = c.user.pref_media_preview

    if not feature.is_enabled("autoexpand_media_previews"):
        expando_preference = None
    elif pref_media_preview == "subreddit":
        expando_preference = "subreddit_default"
    elif pref_media_preview == "on":
        expando_preference = "auto_expand"
    else:
        expando_preference = "do_not_expand"

    pref_beta = c.user.pref_beta
    nsfw_media_acknowledged = logged and c.user.nsfw_media_acknowledged

    if isinstance(c.site, Subreddit) and not c.default_sr:
        cur_subreddit = c.site.name
        cur_sr_fullname = c.site._fullname
        cur_listing = cur_subreddit
        listing_over_18 = c.site.over_18
    elif isinstance(c.site, DefaultSR):
        cur_listing = "frontpage"
    elif isinstance(c.site, FakeSubreddit):
        cur_listing = c.site.name

    if g.debug:
        events_collector_url = g.events_collector_test_url
        events_collector_key = g.secrets['events_collector_test_js_key']
        events_collector_secret = g.secrets['events_collector_test_js_secret']
    else:
        events_collector_url = g.events_collector_url
        events_collector_key = g.secrets['events_collector_js_key']
        events_collector_secret = g.secrets['events_collector_js_secret']

    config = {
        # is the user logged in?
        "logged": logged,
        # logged in user's id
        "user_id": user_id,
        # is user in timeout?
        "user_in_timeout": user_in_timeout,
        # the subreddit's name (for posts)
        "post_site": cur_subreddit,
        "cur_site": cur_sr_fullname,
        "cur_listing": cur_listing,
        # the user's voting hash
        "modhash": c.modhash or False,
        # the current rendering style
        "renderstyle": c.render_style,

        # they're welcome to try to override this in the DOM because we just
        # disable the features server-side if applicable
        'store_visits': gold and c.user.pref_store_visits,

        # current domain
        "cur_domain": get_domain(subreddit=False, no_www=True),
        # where do ajax requests go?
        "ajax_domain": get_domain(subreddit=False),
        "stats_domain": g.stats_domain or '',
        "stats_sample_rate": g.stats_sample_rate or 0,
        "extension": c.extension,
        "https_endpoint": is_subdomain(request.host, g.domain) and g.https_endpoint,
        "media_domain": g.media_domain,
        # does the client only want to communicate over HTTPS?
        "https_forced": feature.is_enabled("force_https"),
        # debugging?
        "debug": g.debug,
        "poisoning_canary": poisoning_canary,
        "poisoning_report_mac": poisoning_report_mac,
        "cache_policy": cache_policy,
        "send_logs": g.live_config["frontend_logging"],
        "server_time": math.floor(time.time()),
        "status_msg": {
          "fetching": _("fetching title..."),
          "submitting": _("submitting..."),
          "loading": _("loading...")
        },
        "is_fake": isinstance(c.site, FakeSubreddit),
        "tracker_url": "",  # overridden below if configured
        "adtracker_url": g.adtracker_url,
        "clicktracker_url": g.clicktracker_url,
        "uitracker_url": g.uitracker_url,
        "eventtracker_url": g.eventtracker_url,
        "anon_eventtracker_url": g.anon_eventtracker_url,
        "events_collector_url": events_collector_url,
        "events_collector_key": events_collector_key,
        "events_collector_secret": events_collector_secret,
        "feature_screenview_events": feature.is_enabled('screenview_events'),
        "static_root": static(''),
        "over_18": bool(c.over18),
        "listing_over_18": listing_over_18,
        "expando_preference": expando_preference,
        "pref_no_profanity": pref_no_profanity,
        "pref_beta": pref_beta,
        "nsfw_media_acknowledged": nsfw_media_acknowledged,
        "new_window": logged and bool(c.user.pref_newwindow),
        "mweb_blacklist_expressions": g.live_config['mweb_blacklist_expressions'],
        "gold": gold,
        "has_subscribed": logged and c.user.has_subscribed,
        "is_sponsor": logged and c.user_is_sponsor,
        "pageInfo": {
          "verification": verification,
          "actionName": route_name,
        },
        "facebook_app_id": g.live_config["facebook_app_id"],
        "feature_new_report_dialog": feature.is_enabled('new_report_dialog'),
        "email_verified": logged and c.user.email and c.user.email_verified,
    }

    if g.tracker_url:
        config["tracker_url"] = tracking.get_pageview_pixel_url()

    if g.uncompressedJS:
        config["uncompressedJS"] = True

    if extra_config:
        config.update(extra_config)

    hooks.get_hook("js_config").call(config=config)

    return config


class JSPreload(js.DataSource):
    def __init__(self, data=None):
        if data is None:
            data = {}
        js.DataSource.__init__(self, "r.preload.set({content})", data)

    def set(self, url, data):
        self.data[url] = data

    def set_wrapped(self, url, wrapped):
        from r2.lib.pages.things import wrap_things
        if not isinstance(wrapped, Wrapped):
            wrapped = wrap_things(wrapped)[0]
        self.data[url] = wrapped.render_nocache('api').finalize()

    def use(self):
        hooks.get_hook("js_preload.use").call(js_preload=self)

        if self.data:
            return js.DataSource.use(self)
        else:
            return ''


def class_dict():
    t_cls = [Link, Comment, Message, Subreddit]
    l_cls = [Listing, OrganicListing]

    classes  = [('%s: %s') % ('t'+ str(cl._type_id), cl.__name__ ) for cl in t_cls] \
             + [('%s: %s') % (cl.__name__, cl._js_cls) for cl in l_cls]

    res = ', '.join(classes)
    return unsafe('{ %s }' % res)


def comment_label(num_comments=None):
    if not num_comments:
        # generates "comment" the imperative verb
        com_label = _("comment {verb}")
        com_cls = 'comments empty may-blank'
    else:
        # generates "XX comments" as a noun
        com_label = ungettext("comment", "comments", num_comments)
        com_label = strings.number_label % dict(num=num_comments,
                                                thing=com_label)
        com_cls = 'comments may-blank'
    return com_label, com_cls

def replace_render(listing, item, render_func):
    def _replace_render(style = None, display = True):
        """
        A helper function for listings to set uncachable attributes on a
        rendered thing (item) to its proper display values for the current
        context.
        """
        style = style or c.render_style or 'html'
        replacements = {}

        if hasattr(item, 'child'):
            if item.child:
                replacements['childlisting'] = item.child.render(style=style)
            else:
                # Special case for when the comment tree wasn't built which
                # occurs both in the inbox and spam page view of comments.
                replacements['childlisting'] = None
        else:
            replacements['childlisting'] = ''

        #only LinkListing has a show_nums attribute
        if listing and hasattr(listing, "show_nums"):
            if listing.show_nums and item.num > 0:
                num = str(item.num)
            else:
                num = ""
            replacements["num"] = num

        if getattr(item, "rowstyle_cls", None):
            replacements["rowstyle"] = item.rowstyle_cls

        if hasattr(item, "num_comments"):
            com_label, com_cls = comment_label(item.num_comments)
            if style == "compact":
                com_label = unicode(item.num_comments)
            replacements['numcomments'] = com_label
            replacements['commentcls'] = com_cls

        if hasattr(item, "num_children"):
            label = ungettext("child", "children", item.num_children)
            numchildren_text = strings.number_label % {'num': item.num_children,
                                                       'thing': label}
            replacements['numchildren_text'] = numchildren_text

        replacements['display'] =  "" if display else "style='display:none'"

        if hasattr(item, "render_score"):
            # replace the score stub
            (replacements['scoredislikes'],
             replacements['scoreunvoted'],
             replacements['scorelikes'])  = item.render_score

        # compute the timesince here so we don't end up caching it
        if hasattr(item, "_date"):
            if hasattr(item, "promoted") and item.promoted is not None:
                from r2.lib import promote
                # promoted links are special in their date handling
                replacements['timesince'] = \
                    simplified_timesince(item._date - promote.timezone_offset)
            else:
                replacements['timesince'] = simplified_timesince(item._date)

        # compute the last edited time here so we don't end up caching it
        if hasattr(item, "editted") and not isinstance(item.editted, bool):
            replacements['lastedited'] = simplified_timesince(item.editted)

        renderer = render_func or item.render
        res = renderer(style = style, **replacements)

        if isinstance(res, (str, unicode)):
            rv = unsafe(res)
            if g.debug:
                for leftover in re.findall('<\$>(.+?)(?:<|$)', rv):
                    print "replace_render didn't replace %s" % leftover

            return rv

        return res

    return _replace_render

def get_domain(cname=False, subreddit=True, no_www=False):
    """
    returns the domain on the current subreddit, possibly including
    the subreddit part of the path, suitable for insertion after an
    "http://" and before a fullpath (i.e., something including the
    first '/') in a template.  The domain is updated to include the
    current port (request.port).  The effect of the arguments is:

     * no_www: if the domain ends up being g.domain, the default
       behavior is to prepend "www." to the front of it (for akamai).
       This flag will optionally disable it.

     * cname: deprecated.

     * subreddit: flags whether or not to append to the domain the
       subreddit path (without the trailing path).

    """
    # locally cache these lookups as this gets run in a loop in add_props
    domain = g.domain

    # c.domain_prefix is only set to non '' values, so we're safe to
    # override if it is falsy. we need to check this here because this method
    # might be getting called out of request, but c.domain_prefix is set in
    # request in MinimalController.pre.
    domain_prefix = c.domain_prefix or g.domain_prefix

    site = c.site

    if not no_www and domain_prefix:
        domain = domain_prefix + "." + domain

    if hasattr(request, "port") and request.port:
        domain += ":" + str(request.port)

    if subreddit:
        domain += site.path.rstrip('/')

    return domain


def add_sr(
        path, sr_path=True, nocname=False, force_hostname=False,
        retain_extension=True, force_https=False,
        force_extension=None):
    """
    Given a path (which may be a full-fledged url or a relative path),
    parses the path and updates it to include the subreddit path
    according to the rules set by its arguments:

     * sr_path: if a cname is not used for the domain, updates the
       path to include c.site.path.

     * nocname: deprecated.

     * force_hostname: if True, force the url's hostname to be updated
       even if it is already set in the path. If false, the path will still
       have its domain updated if no hostname is specified in the url.

     * retain_extension: if True, sets the extention according to
       c.render_style.

     * force_https: force the URL scheme to https

    For caching purposes: note that this function uses:
      c.render_style, c.site.name

    """
    # don't do anything if it is just an anchor
    if path.startswith(('#', 'javascript:')):
        return path

    u = UrlParser(path)
    if sr_path:
        u.path_add_subreddit(c.site)

    if not u.hostname or force_hostname:
        u.hostname = get_domain(subreddit=False)

    if (c.secure and u.is_reddit_url()) or force_https:
        u.scheme = "https"

    if force_extension is not None:
        u.set_extension(force_extension)
    elif retain_extension:
        if c.render_style == 'mobile':
            u.set_extension('mobile')

        elif c.render_style == 'compact':
            u.set_extension('compact')

    return u.unparse()

def join_urls(*urls):
    """joins a series of urls together without doubles slashes"""
    if not urls:
        return

    url = urls[0]
    for u in urls[1:]:
        if not url.endswith('/'):
            url += '/'
        while u.startswith('/'):
            u = utils.lstrips(u, '/')
        url += u
    return url

def style_line(button_width = None, bgcolor = "", bordercolor = ""):
    style_line = ''
    bordercolor = c.bordercolor or bordercolor
    bgcolor     = c.bgcolor or bgcolor
    if bgcolor:
        style_line += "background-color: #%s;" % bgcolor
    if bordercolor:
        style_line += "border: 1px solid #%s;" % bordercolor
    if button_width:
        style_line += "width: %spx;" % button_width
    return style_line

def choose_width(link, width):
    if width:
        return width - 5
    else:
        if hasattr(link, "_ups"):
            return 100 + (10 * (len(str(link._ups - link._downs))))
        else:
            return 110

# Appends to the list "attrs" a tuple of:
# <priority (higher trumps lower), letter,
#  css class, i18n'ed mouseover label, hyperlink (opt)>
def add_attr(attrs, kind, label=None, link=None, cssclass=None, symbol=None):
    from r2.lib.template_helpers import static

    symbol = symbol or kind

    if kind == 'F':
        priority = 1
        cssclass = 'friend'
        if not label:
            label = _('friend')
        if not link:
            link = '/prefs/friends'
    elif kind == 'S':
        priority = 2
        cssclass = 'submitter'
        if not label:
            label = _('submitter')
        if not link:
            raise ValueError ("Need a link")
    elif kind == 'M':
        priority = 3
        cssclass = 'moderator'
        if not label:
            raise ValueError ("Need a label")
        if not link:
            raise ValueError ("Need a link")
    elif kind == 'A':
        priority = 4
        cssclass = 'admin'
        if not label:
            label = _('reddit admin, speaking officially')
    elif kind in ('X', '@'):
        priority = 5
        cssclass = 'gray'
        if not label:
            raise ValueError ("Need a label")
    elif kind == 'V':
        priority = 6
        cssclass = 'green'
        if not label:
            raise ValueError ("Need a label")
    elif kind == 'B':
        priority = 7
        cssclass = 'wrong'
        if not label:
            raise ValueError ("Need a label")
    elif kind == 'special':
        priority = 98
    elif kind == "cake":
        priority = 99
        cssclass = "cakeday"
        symbol = "&#x1F370;"
        if not label:
            raise ValueError ("Need a label")
        if not link:
            raise ValueError ("Need a link")
    else:
        raise ValueError ("Got weird kind [%s]" % kind)

    attrs.append( (priority, symbol, cssclass, label, link) )


def add_admin_distinguish(distinguish_attribs_list):
    add_attr(distinguish_attribs_list, 'A')


def add_moderator_distinguish(distinguish_attribs_list, subreddit):
    link = '/r/%s/about/moderators' % subreddit.name
    label = _('moderator of /r/%(reddit)s, speaking officially')
    label %= {'reddit': subreddit.name}
    add_attr(distinguish_attribs_list, 'M', label=label, link=link)


def add_friend_distinguish(distinguish_attribs_list, note=None):
    if note:
        label = u"%s (%s)" % (_("friend"), _force_unicode(note))
    else:
        label = None
    add_attr(distinguish_attribs_list, 'F', label)


def add_cakeday_distinguish(distinguish_attribs_list, user):
    label = _("%(user)s just celebrated a reddit birthday!")
    label %= {"user": user.name}
    link = "/user/%s" % user.name
    add_attr(distinguish_attribs_list, kind="cake", label=label, link=link)


def add_special_distinguish(distinguish_attribs_list, user):
    args = user.special_distinguish()
    args.pop('name')
    if not args.get('kind'):
        args['kind'] = 'special'
    add_attr(distinguish_attribs_list, **args)


def add_submitter_distinguish(distinguish_attribs_list, link, subreddit):
    permalink = link.make_permalink(subreddit)
    add_attr(distinguish_attribs_list, 'S', link=permalink)


def search_url(query, subreddit, restrict_sr="off", sort=None, recent=None, ref=None):
    import urllib
    query = _force_utf8(query)
    url_query = {"q": query}
    if ref:
        url_query["ref"] = ref
    if restrict_sr:
        url_query["restrict_sr"] = restrict_sr
    if sort:
        url_query["sort"] = sort
    if recent:
        url_query["t"] = recent
    path = "/r/%s/search?" % subreddit if subreddit else "/search?"
    path += urllib.urlencode(url_query)
    return path


def format_number(number, locale=None):
    if not locale:
        locale = c.locale or g.locale

    return babel.numbers.format_number(number, locale=locale)


def format_percent(ratio, locale=None):
    if not locale:
        locale = c.locale or g.locale

    return babel.numbers.format_percent(ratio, locale=locale)


def html_datetime(date):
    # Strip off the microsecond to appease the HTML5 gods, since
    # datetime.isoformat() returns too long of a microsecond value.
    # http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#times
    return date.astimezone(pytz.UTC).replace(microsecond=0).isoformat()


def js_timestamp(date):
    return '%d' % (calendar.timegm(date.timetuple()) * 1000)


def simplified_timesince(date, include_tense=True):
    if date > timeago("1 minute"):
        return _("just now")

    since = timesince(date)
    if include_tense:
        return _("%s ago") % since
    else:
        return since


def display_link_karma(karma):
    if not c.user_is_admin:
        return max(karma, g.link_karma_display_floor)
    return karma


def display_comment_karma(karma):
    if not c.user_is_admin:
        return max(karma, g.comment_karma_display_floor)
    return karma


def format_html(format_string, *args, **kwargs):
    """
    Similar to str % foo, but passes all arguments through conditional_websafe,
    and calls 'unsafe' on the result. This function should be used instead
    of str.format or % interpolation to build up small HTML fragments.

    Example:

      format_html("Are you %s? %s", name, unsafe(checkbox_html))
    """
    if args and kwargs:
        raise ValueError("Can't specify both positional and keyword args")
    args_safe = tuple(map(conditional_websafe, args))
    kwargs_gen = ((k, conditional_websafe(v)) for (k, v) in kwargs.iteritems())
    kwargs_safe = dict(kwargs_gen)

    format_args = args_safe or kwargs_safe
    return unsafe(format_string % format_args)


def _ws(text, keep_spaces=False):
    """Helper function to get HTML escaped output from gettext"""
    if keep_spaces:
        return keep_space(_(text))
    else:
        return websafe(_(text))


def _wsf(format, keep_spaces=True, *args, **kwargs):
    """
    format_html, but with an escaped, translated string as the format str

    Sometimes trusted HTML needs to be included in a translatable string,
    but we don't trust translators to write HTML themselves.

    Example:

      _wsf("Are you %(name)s? %(box)s", name=name, box=unsafe(checkbox_html))
    """
    format_trans = _ws(format, keep_spaces)
    return format_html(format_trans, *args, **kwargs)


def get_linkflair_css_classes(thing, prefix="linkflair-", on_class="has-linkflair", off_class="no-linkflair"):
    has_linkflair =  thing.flair_text or thing.flair_css_class
    show_linkflair = c.user.pref_show_link_flair
    if has_linkflair and show_linkflair:
        if thing.flair_css_class:
            flair_css_classes = thing.flair_css_class.split()
            prefixed_css_classes = ["%s%s" % (prefix, css_class) for css_class in flair_css_classes]
            on_class = "%s %s" % (on_class, ' '.join(prefixed_css_classes))
        return on_class
    else:
        return off_class


def update_query(base_url, **kw):
    parsed = UrlParser(base_url)
    parsed.update_query(**kw)
    return parsed.unparse()
