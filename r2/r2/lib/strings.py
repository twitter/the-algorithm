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

"""
Module for maintaining long or commonly used translatable strings,
removing the need to pollute the code with lots of extra _ and
ungettext calls.
"""

from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.i18n import _, ungettext, get_lang
import random
import babel.numbers

from r2.lib.filters import websafe
from r2.lib.generate_strings import funny_translatable_strings
from r2.lib.translation import set_lang


__all__ = ['StringHandler', 'strings', 'PluralManager', 'plurals',
           'Score', 'get_funny_translated_string']

# here's where all of the really long site strings (that need to be
# translated) live so as not to clutter up the rest of the code.  This
# dictionary is not used directly but rather is managed by the single
# StringHandler instance strings
string_dict = dict(

    banned_by = "removed by %s",
    banned    = "removed",
    times_banned="removed %d times",
    time_banned="removed at %s",
    time_approved="approved at %s",
    reports   = "reports: %d",

    submitting = _("submitting..."),

    # this accomodates asian languages which don't use spaces
    number_label = _("%(num)d %(thing)s"),

    # this accomodates asian languages which don't use spaces
    points_label = _("%(num)d %(point)s"),

    # this accomodates asian languages which don't use spaces
    time_label = _("%(num)d %(time)s"),

    # this accomodates asian languages which don't use spaces
    float_label = _("%(num)5.3f %(thing)s"),

    already_submitted = _("that link has already been submitted, but you can try to [submit it again](%s)."),

    multiple_submitted = _("that link has been submitted to multiple subreddits. you can try to [submit it again](%s)."),

    user_deactivated = _("your account has been deactivated, but we won't judge you for it."),

    oauth_login_msg = _(
        "Log in or sign up to connect your reddit account with %(app)s."),

    legal = _("I understand and agree that registration on or use of this site constitutes agreement to its %(user_agreement)s and %(privacy_policy)s."),

    friends = _('to view reddit with only submissions from your friends, use [reddit.com/r/friends](%s)'),

    sr_created = _('your subreddit has been created'),

    more_info_link = _("visit [%(link)s](%(link)s) for more information"),

    sr_messages = dict(
        empty =  _('you have not subscribed to any subreddits.'),
        subscriber =  _('below are the subreddits you have subscribed to.'),
        contributor =  _('below are the subreddits that you are an approved submitter on.'),
        moderator = _('below are the subreddits that you have moderator access to.')
        ),

    sr_subscribe =  _('click the `subscribe` or `unsubscribe` buttons to choose which subreddits appear on your front page.'),

    searching_a_reddit = _('you\'re searching within the [%(reddit_name)s](%(reddit_link)s) subreddit. '+
                           'you can also search within [all subreddits](%(all_reddits_link)s)'),

    permalink_title = _("%(author)s comments on %(title)s"),
    link_info_title = _("%(title)s : %(site)s"),
    link_info_og_description = _("%(score)s points and %(num_comments)s comments so far on reddit"),

    submit_link = _("""You are submitting a link. The key to a successful submission is interesting content and a descriptive title."""),
    submit_text = _("""You are submitting a text-based post. Speak your mind. A title is required, but expanding further in the text field is not. Beginning your title with "vote up if" is violation of intergalactic law."""),
    submit_link_label = _("Submit a new link"),
    submit_text_label = _("Submit a new text post"),
    verify_email = _("we're going to need to verify your email address for you to proceed."),
    verify_email_submit = _("you'll be able to submit more frequently once you verify your email address"),
    email_verified =  _("your email address has been verified"),
    email_verify_failed = _("Verification failed.  Please try that again"),
    email_verify_wrong_user = _("The email verification link you've followed is for a different user. Please log out and switch to that user or try again below."),
    search_failed = _("Our search machines are under too much load to handle your request right now. :( Sorry for the inconvenience. Try again in a little bit -- but please don't mash reload; that only makes the problem worse."),
    invalid_search_query = _("I couldn't understand your query, so I simplified it and searched for \"%(clean_query)s\" instead."),
    completely_invalid_search_query = _("I couldn't understand your search query. Please try again."),
    search_help = _("You may also want to check the [search help page](%(search_help)s) for more information."),
    formatting_help_info = _('reddit uses a slightly-customized version of [Markdown](http://daringfireball.net/projects/markdown/syntax) for formatting. See below for some basics, or check [the commenting wiki page](/wiki/commenting) for more detailed help and solutions to common issues.'),
    read_only_msg = _("Reddit is in \"emergency read-only mode\" right now. :( You won't be able to log in. We're sorry and are working frantically to fix the problem."),
    heavy_load_msg = _("this page is temporarily in read-only mode due to heavy traffic."),
    in_perma_timeout_msg = _("Your account has been permanently [suspended](https://reddit.zendesk.com/hc/en-us/articles/205687686) from Reddit."),
    in_temp_timeout_msg = _("Your account has been [suspended](https://reddit.zendesk.com/hc/en-us/articles/205687686) from Reddit for %(days)s."),
    gold_benefits_msg = "reddit gold is our premium membership program. It grants you access to [extra features](https://www.reddit.com/gold/about) to improve your reddit experience. It also makes you really quite dapper. If you have questions about your gold, please visit /r/goldbenefits.",
    lounge_msg = "Grab a drink and join us in /r/lounge, the super-secret members-only community that may or may not exist.",
    postcard_msg = _("You sent us a postcard! (Or something similar.) When we run out of room on our refrigerator, we might one day auction off the stuff that people sent in. Is it okay if we include your thing?"),
    over_comment_limit = _("Sorry, the maximum number of comments is %(max)d. (However, if you subscribe to reddit gold, it goes up to %(goldmax)d.)"),
    over_comment_limit_gold = _("Sorry, the maximum number of comments is %d."),
    youve_got_gold = "%(sender)s just gifted you %(amount)s of reddit gold!",
    giftgold_note = "Here's a note that was included:\n\n----\n\n",
    youve_been_gilded_comment = "%(sender)s liked [your comment](%(url)s) so much that they gilded it, giving you reddit gold.\n\n",
    youve_been_gilded_link = "%(sender)s liked [your submission](%(url)s) so much that they gilded it, giving you reddit gold.\n\n",
    respond_to_anonymous_gilder = "Want to say thanks to your mysterious benefactor? Reply to this message. You will find out their username if they choose to reply back.",
    unsupported_respond_to_gilder = "Sorry, replying directly to your mysterious benefactor is not yet supported for this gilding.",
    anonymous_gilder_warning = _("***WARNING: Responding to this message will reveal your username to the gildee.***\n\n"),
    gold_claimed_code = _("Thanks for claiming a reddit gold code.\n\n"),
    gold_summary_autorenew_monthly = _("You're about to set up an ongoing, autorenewing subscription to reddit gold for yourself (%(user)s). \n\nYou'll pay **%(price)s** for this, **monthly**. \n\n>This subscription will renew automatically each month until you cancel. You may cancel at any time. If you cancel, you will not be billed for any additional months of service, and service will continue until the end of the billing period. If you cancel, you will not receive a refund for any service already paid for. Receipts will be delivered via private message in your account."),
    gold_summary_autorenew_yearly = _("You're about to set up an ongoing, autorenewing subscription to reddit gold for yourself (%(user)s). \n\nYou'll pay **%(price)s** for this, **yearly**. \n\n>This subscription will renew automatically each year until you cancel. You may cancel at any time. If you cancel, you will not be billed for any additional years of service, and service will continue until the end of the billing period. If you cancel, you will not receive a refund for any service already paid for. Receipts will be delivered via private message in your account."),
    gold_summary_onetime = _("You're about to make a one-time purchase of %(amount)s of reddit gold for yourself (%(user)s). You'll pay a total of %(price)s for this."),
    gold_summary_creddits = _("You're about to purchase %(amount)s. They work like gift certificates: each creddit you have will allow you to give one month of reddit gold to someone else. You'll pay a total of %(price)s for this."),
    gold_summary_gift_code = _("You're about to purchase %(amount)s of reddit gold in the form of a gift code. The recipient (or you) will be able to claim the code to redeem that gold to their account. You'll pay a total of %(price)s for this."),
    gold_summary_signed_gift = _("You're about to give %(amount)s of reddit gold to %(recipient)s, who will be told that it came from you. You'll pay a total of %(price)s for this."),
    gold_summary_anonymous_gift = _("You're about to give %(amount)s of reddit gold to %(recipient)s. It will be an anonymous gift. You'll pay a total of %(price)s for this."),
    gold_summary_gilding_comment = _("Want to say thanks to *%(recipient)s* for this comment? Give them a month of [reddit gold](/gold/about)."),
    gold_summary_gilding_link = _("Want to say thanks to *%(recipient)s* for this submission? Give them a month of [reddit gold](/gold/about)."),
    gold_summary_gilding_page_comment = _("You're about to give *%(recipient)s* a month of [reddit gold](/gold/about) for this comment:"),
    gold_summary_gilding_page_link = _("You're about to give *%(recipient)s* a month of [reddit gold](/gold/about) for this submission:"),
    gold_summary_gilding_page_footer = _("You'll pay a total of %(price)s for this."),
    archived_post_message = _("This is an archived post. You won't be able to vote or comment."),
    locked_post_message = _("This post is locked. You won't be able to comment."),
    account_activity_blurb = _("This page shows a history of recent activity on your account. If you notice unusual activity, you should change your password immediately. Location information is guessed from your computer's IP address and may be wildly wrong, especially for visits from mobile devices."),
    your_current_ip_is = _("You are currently accessing reddit from this IP address: %(address)s."),
    account_activity_apps_blurb = _("""
These apps are authorized to access your account. Signing out of all sessions
will revoke access from all apps. You may also revoke access from individual
apps below.
"""),

    traffic_promoted_link_explanation = _("Below you will see your promotion's impression and click traffic per hour of promotion.  Please note that these traffic totals will lag behind by two to three hours, and that daily totals will be preliminary until 24 hours after the link has finished its run."),
    traffic_processing_slow = _("Traffic processing is currently running slow. The latest data available is from %(date)s. This page will be updated as new data becomes available."),
    traffic_processing_normal = _("Traffic processing occurs on an hourly basis. The latest data available is from %(date)s. This page will be updated as new data becomes available."),
    traffic_help_email = _("Questions? Email self serve support: %(email)s"),

    traffic_subreddit_explanation = _("""
Below are the traffic statistics for your subreddit. Each graph represents one of the following over the interval specified.

* **pageviews** are all hits to %(subreddit)s, including both listing pages and comment pages.
* **uniques** are the total number of unique visitors (determined by a combination of their IP address and User Agent string) that generate the above pageviews. This is independent of whether or not they are signed in.
* **subscriptions** is the number of new subscriptions that have been generated in a given day. This number is less accurate than the first two metrics, as, though we can track new subscriptions, we have no way to track unsubscriptions.

Note: there are a couple of places outside of your subreddit where someone can click "subscribe", so it is possible (though unlikely) that the subscription count can exceed the unique count on a given day.
"""),

    subscribed_multi = _("multireddit of your subscriptions"),
    mod_multi = _("multireddit of subreddits you moderate"),

    r_all_description = _("/r/all displays content from all of reddit, including subreddits you aren't subscribed to. Some subreddits have chosen to exclude themselves from /r/all."),
    r_all_minus_description = _("Displaying content from /r/all of reddit, except the following subreddits:"),
    all_minus_gold_only = _('Filtering /r/all is a feature only available to [reddit gold](/gold/about) subscribers. Displaying unfiltered results from /r/all.'),
)

class StringHandler(object):
    """Class for managing long translatable strings.  Allows accessing
    of strings via both getitem and getattr.  In both cases, the
    string is passed through the gettext _ function before being
    returned."""
    def __init__(self, **sdict):
        self.string_dict = sdict

    def get(self, attr, default=None):
        try:
            return self[attr]
        except KeyError:
            return default

    def __getitem__(self, attr):
        try:
            return self.__getattr__(attr)
        except AttributeError:
            raise KeyError

    def __getattr__(self, attr):
        rval = self.string_dict[attr]
        if isinstance(rval, (str, unicode)):
            return _(rval)
        elif isinstance(rval, dict):
            return StringHandler(**rval)
        else:
            raise AttributeError

    def __iter__(self):
        return iter(self.string_dict)

    def keys(self):
        return self.string_dict.keys()

strings = StringHandler(**string_dict)


def P_(x, y):
    """Convenience method for handling pluralizations.  This identity
    function has been added to the list of keyword functions for babel
    in setup.cfg so that the arguments are translated without having
    to resort to ungettext and _ trickery."""
    return (x, y)

class PluralManager(object):
    """String handler for dealing with pluralizable forms.  plurals
    are passed in in pairs (sing, pl) and can be accessed via
    self.sing and self.pl.

    Additionally, calling self.N_sing(n) (or self.N_pl(n)) (where
    'sing' and 'pl' are placeholders for a (sing, pl) pairing) is
    equivalent to ungettext(sing, pl, n)
    """
    def __init__(self, plurals):
        self.string_dict = {}
        for s, p in plurals:
            self.string_dict[s] = self.string_dict[p] = (s, p)

    def __getattr__(self, attr):
        to_func = False
        if attr.startswith("N_"):
            attr = attr[2:]
            to_func = True

        attr = attr.replace("_", " ")
        if to_func:
            rval = self.string_dict[attr]
            return lambda x: ungettext(rval[0], rval[1], x)
        else:
            rval = self.string_dict[attr]
            n = 1 if attr == rval[0] else 5
            return ungettext(rval[0], rval[1], n)

plurals = PluralManager([P_("comment",     "comments"),
                         P_("point",       "points"),

                         # things
                         P_("link",        "links"),
                         P_("comment",     "comments"),
                         P_("message",     "messages"),
                         P_("subreddit",   "subreddits"),
                         P_("creddit",     "creddits"),

                         # people
                         P_("reader",  "readers"),
                         P_("subscriber",  "subscribers"),
                         P_("approved submitter", "approved submitters"),
                         P_("moderator",   "moderators"),
                         P_("user here now",   "users here now"),

                         # time words
                         P_("milliseconds","milliseconds"),
                         P_("second",      "seconds"),
                         P_("minute",      "minutes"),
                         P_("hour",        "hours"),
                         P_("day",         "days"),
                         P_("month",       "months"),
                         P_("year",        "years"),
])


class Score(object):
    """Convienience class for populating '10 points' in a traslatible
    fasion, used primarily by the score() method in printable.html"""

    # This used to pass through _() because allegedly Japanese needed different
    # markup, but that doesn't appear to be the case anymore
    PERSON_LABEL = ('<span class="number">%(num)s</span>&#32;'
                    '<span class="word">%(persons)s</span>')

    @staticmethod
    def number_only(x):
        return str(max(x, 0))

    @staticmethod
    def points(x):
        return strings.points_label % dict(num=x,
                                           point=plurals.N_points(x))

    @staticmethod
    def safepoints(x):
        return Score.points(max(x, 0))

    @staticmethod
    def _people(x, label, prepend=''):
        num = prepend + babel.numbers.format_number(x, c.locale)
        return Score.PERSON_LABEL % \
            dict(num=num, persons=websafe(label(x)))

    @staticmethod
    def subscribers(x):
        return Score._people(x, plurals.N_subscribers)

    @staticmethod
    def readers(x):
        return Score._people(x, plurals.N_readers)

    @staticmethod
    def somethings(x, word):
        p = plurals.string_dict[word]
        f = lambda x: ungettext(p[0], p[1], x)
        return strings.number_label % dict(num=x, thing=f(x))

    @staticmethod
    def users_here_now(x, prepend=''):
        return Score._people(x, plurals.N_users_here_now, prepend=prepend)

    @staticmethod
    def none(x):
        return ""


def fallback_trans(x):
    """For translating placeholder strings the user should never see
    in raw form, such as 'funny 500 message'.  If the string does not
    translate in the current language, falls back on the g.lang
    translation that we've hopefully already provided"""
    t = _(x)
    if t == x:
        l = get_lang()
        set_lang(g.lang, graceful_fail = True)
        t = _(x)
        if l and l[0] != g.lang:
            set_lang(l[0])
    return t


def get_funny_translated_string(category, num=1):
    strings = random.sample(funny_translatable_strings[category], num)
    ret = [fallback_trans(string) for string in strings]
    if len(ret) == 1:
        return ret[0]
    else:
        return ret
