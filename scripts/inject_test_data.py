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

from __future__ import division

import collections
import HTMLParser
import itertools
import random
import string
import time

import requests

from pylons import app_globals as g

from r2.lib.db import queries
from r2.lib import amqp
from r2.lib.utils import weighted_lottery, get_requests_resp_json
from r2.lib.voting import cast_vote
from r2.models import (
    Account,
    Comment,
    Link,
    LocalizedDefaultSubreddits,
    LocalizedFeaturedSubreddits,
    NotFound,
    register,
    Subreddit,
    Vote,
)


unescape_htmlentities = HTMLParser.HTMLParser().unescape


class TextGenerator(object):
    """A Markov Chain based text mimicker."""

    def __init__(self, order=8):
        self.order = order
        self.starts = collections.Counter()
        self.start_lengths = collections.defaultdict(collections.Counter)
        self.models = [
            collections.defaultdict(collections.Counter)
            for i in xrange(self.order)]

    @staticmethod
    def _in_groups(input_iterable, n):
        iterables = itertools.tee(input_iterable, n)
        for offset, iterable in enumerate(iterables):
            for _ in xrange(offset):
                next(iterable, None)
        return itertools.izip(*iterables)

    def add_sample(self, sample):
        """Add a sample to the model of text for this generator."""

        if len(sample) <= self.order:
            return

        start = sample[:self.order]
        self.starts[start] += 1
        self.start_lengths[start][len(sample)] += 1
        for order, model in enumerate(self.models, 1):
            for chars in self._in_groups(sample, order+1):
                prefix = "".join(chars[:-1])
                next_char = chars[-1]
                model[prefix][next_char] += 1

    def generate(self):
        """Generate a string similar to samples previously fed in."""

        start = weighted_lottery(self.starts)
        desired_length = weighted_lottery(self.start_lengths[start])
        desired_length = max(desired_length, self.order)

        generated = []
        generated.extend(start)
        while len(generated) < desired_length:
            # try each model, from highest order down, til we find
            # something
            for order, model in reversed(list(enumerate(self.models, 1))):
                current_prefix = "".join(generated[-order:])
                frequencies = model[current_prefix]
                if frequencies:
                    generated.append(weighted_lottery(frequencies))
                    break
            else:
                generated.append(random.choice(string.lowercase))

        return "".join(generated)


def fetch_listing(path, limit=1000, batch_size=100):
    """Fetch a reddit listing from reddit.com."""

    session = requests.Session()
    session.headers.update({
        "User-Agent": "reddit-test-data-generator/1.0",
    })

    base_url = "https://api.reddit.com" + path

    after = None
    count = 0
    while count < limit:
        params = {"limit": batch_size, "count": count}
        if after:
            params["after"] = after

        print "> {}-{}".format(count, count+batch_size)
        response = session.get(base_url, params=params)
        response.raise_for_status()

        listing = get_requests_resp_json(response)["data"]
        for child in listing["children"]:
            yield child["data"]
            count += 1

        after = listing["after"]
        if not after:
            break

        # obey reddit.com's ratelimits
        # see: https://github.com/reddit/reddit/wiki/API#rules
        time.sleep(2)


class Modeler(object):
    def __init__(self):
        self.usernames = TextGenerator(order=2)

    def model_subreddit(self, subreddit_name):
        """Return a model of links and comments in a given subreddit."""

        subreddit_path = "/r/{}".format(subreddit_name)
        print ">>>", subreddit_path

        print ">> Links"
        titles = TextGenerator(order=5)
        selfposts = TextGenerator(order=8)
        link_count = self_count = 0
        urls = set()
        for link in fetch_listing(subreddit_path, limit=500):
            self.usernames.add_sample(link["author"])
            titles.add_sample(unescape_htmlentities(link["title"]))
            if link["is_self"]:
                self_count += 1
                selfposts.add_sample(unescape_htmlentities(link["selftext"]))
            else:
                urls.add(link["url"])
            link_count += 1
        self_frequency = self_count / link_count

        print ">> Comments"
        comments = TextGenerator(order=8)
        for comment in fetch_listing(subreddit_path + "/comments"):
            self.usernames.add_sample(comment["author"])
            comments.add_sample(unescape_htmlentities(comment["body"]))

        return SubredditModel(
            subreddit_name, titles, selfposts, urls, comments, self_frequency)

    def generate_username(self):
        """Generate and return a username like those seen on reddit.com."""
        return self.usernames.generate()


class SubredditModel(object):
    """A snapshot of a subreddit's links and comments."""

    def __init__(self, name, titles, selfposts, urls, comments, self_frequency):
        self.name = name
        self.titles = titles
        self.selfposts = selfposts
        self.urls = list(urls)
        self.comments = comments
        self.selfpost_frequency = self_frequency

    def generate_link_title(self):
        """Generate and return a title like those seen in the subreddit."""
        return self.titles.generate()

    def generate_link_url(self):
        """Generate and return a URL from one seen in the subreddit.

        The URL returned may be "self" indicating a self post. This should
        happen with the same frequency it is seen in the modeled subreddit.

        """
        if random.random() < self.selfpost_frequency:
            return "self"
        else:
            return random.choice(self.urls)

    def generate_selfpost_body(self):
        """Generate and return a self-post body like seen in the subreddit."""
        return self.selfposts.generate()

    def generate_comment_body(self):
        """Generate and return a comment body like seen in the subreddit."""
        return self.comments.generate()


def fuzz_number(number):
    return int(random.betavariate(2, 8) * 5 * number)


def ensure_account(name):
    """Look up or register an account and return it."""
    try:
        account = Account._by_name(name)
        print ">> found /u/{}".format(name)
        return account
    except NotFound:
        print ">> registering /u/{}".format(name)
        return register(name, "password", "127.0.0.1")


def ensure_subreddit(name, author):
    """Look up or create a subreddit and return it."""
    try:
        sr = Subreddit._by_name(name)
        print ">> found /r/{}".format(name)
        return sr
    except NotFound:
        print ">> creating /r/{}".format(name)
        sr = Subreddit._new(
            name=name,
            title="/r/{}".format(name),
            author_id=author._id,
            lang="en",
            ip="127.0.0.1",
        )
        sr._commit()
        return sr


def inject_test_data(num_links=25, num_comments=25, num_votes=5):
    """Flood your reddit install with test data based on reddit.com."""

    print ">>>> Ensuring configured objects exist"
    system_user = ensure_account(g.system_user)
    ensure_account(g.automoderator_account)
    ensure_subreddit(g.default_sr, system_user)
    ensure_subreddit(g.takedown_sr, system_user)
    ensure_subreddit(g.beta_sr, system_user)
    ensure_subreddit(g.promo_sr_name, system_user)

    print
    print

    print ">>>> Fetching real data from reddit.com"
    modeler = Modeler()
    subreddits = [
        modeler.model_subreddit("pics"),
        modeler.model_subreddit("videos"),
        modeler.model_subreddit("askhistorians"),
    ]
    extra_settings = {
        "pics": {
            "show_media": True,
        },
        "videos": {
            "show_media": True,
        },
    }

    print
    print

    print ">>>> Generating test data"
    print ">>> Accounts"
    account_query = Account._query(sort="_date", limit=500, data=True)
    accounts = [a for a in account_query if a.name != g.system_user]
    accounts.extend(
        ensure_account(modeler.generate_username())
        for i in xrange(50 - len(accounts)))

    print ">>> Content"
    things = []
    for sr_model in subreddits:
        sr_author = random.choice(accounts)
        sr = ensure_subreddit(sr_model.name, sr_author)

        # make the system user subscribed for easier testing
        if sr.add_subscriber(system_user):
            sr._incr("_ups", 1)

        # apply any custom config we need for this sr
        for setting, value in extra_settings.get(sr.name, {}).iteritems():
            setattr(sr, setting, value)
        sr._commit()

        for i in xrange(num_links):
            link_author = random.choice(accounts)
            url = sr_model.generate_link_url()
            is_self = (url == "self")
            content = sr_model.generate_selfpost_body() if is_self else url
            link = Link._submit(
                is_self=is_self,
                title=sr_model.generate_link_title(),
                content=content,
                author=link_author,
                sr=sr,
                ip="127.0.0.1",
            )
            queries.new_link(link)
            things.append(link)

            comments = [None]
            for i in xrange(fuzz_number(num_comments)):
                comment_author = random.choice(accounts)
                comment, inbox_rel = Comment._new(
                    comment_author,
                    link,
                    parent=random.choice(comments),
                    body=sr_model.generate_comment_body(),
                    ip="127.0.0.1",
                )
                queries.new_comment(comment, inbox_rel)
                comments.append(comment)
                things.append(comment)

    for thing in things:
        for i in xrange(fuzz_number(num_votes)):
            direction = random.choice([
                Vote.DIRECTIONS.up,
                Vote.DIRECTIONS.unvote,
                Vote.DIRECTIONS.down,
            ])
            voter = random.choice(accounts)

            cast_vote(voter, thing, direction)

    amqp.worker.join()

    srs = [Subreddit._by_name(n) for n in ("pics", "videos", "askhistorians")]
    LocalizedDefaultSubreddits.set_global_srs(srs)
    LocalizedFeaturedSubreddits.set_global_srs([Subreddit._by_name('pics')])
