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

import logging
import json
import hashlib

from pylons import tmpl_context as c
from pylons import app_globals as g


class FeatureState(object):
    """A FeatureState is the state of a feature and its condition in the world.

    It determines if this feature is enabled given the world provided.
    """

    # Special values for globally enabled properties - no need to interrogate
    # the world for these values.
    GLOBALLY_ON = "on"
    GLOBALLY_OFF = "off"

    # constant config blocks
    DISABLED_CFG = {"enabled": GLOBALLY_OFF}
    ENABLED_CFG = {"enabled": GLOBALLY_ON}

    # The number of buckets to use for any bucketing operations.  Should always
    # be evenly divisible by 100.  Each factor of 10 over 100 gives us an
    # additional digit of precision.
    NUM_BUCKETS = 1000

    # The variant definition for control groups that are added by default.
    DEFAULT_CONTROL_GROUPS = {'control_1': 10, 'control_2': 10}

    def __init__(self, name, world, config_name=None, config_str=None):
        self.name = name
        self.world = world
        self.config = self._parse_config(name, config_name, config_str)

    def _parse_config(self, name, config_name=None, config_str=None):
        """Find and parse a config from our live config with this given name.

        :param name string - a given feature name
        :return dict - a dictionary with at least "enabled". May include more
                       depending on the enabled type.
        """
        if not config_name:
            config_name = "feature_%s" % name

        if not config_str:
            config_str = self.world.live_config(config_name)

        if not config_str or config_str == FeatureState.GLOBALLY_OFF:
            return self.DISABLED_CFG

        if config_str == FeatureState.GLOBALLY_ON:
            return self.ENABLED_CFG

        try:
            config = json.loads(config_str)
        except (ValueError, TypeError) as e:
            g.log.warning("Could not load config for name %r - %r",
                          config_name, e)
            return self.DISABLED_CFG

        if not isinstance(config, dict):
            g.log.warning("Config not dict, on or off: %r", config_name)
            return self.DISABLED_CFG

        return config

    @staticmethod
    def get_all(world):
        """Return FeatureState objects for all features in live_config.

        Creates a FeatureState object for every config entry prefixed with
        "feature_".

        :param world - World proxy object to the app/request state.
        """
        features = []
        for (key, config_str) in world.live_config_iteritems():
            if key.startswith('feature_'):
                feature_state = FeatureState(key[8:], world, key, config_str)
                features.append(feature_state)
        return features

    @staticmethod
    def is_user_experiment(experiment):
        return not FeatureState.is_page_experiment(experiment)

    @staticmethod
    def is_page_experiment(experiment):
        return experiment.get('page')

    def _calculate_bucket(self, seed, experiment_seed=None):
        """Sort something into one of self.NUM_BUCKETS buckets.

        :param seed -- a string used for shifting the deterministic bucketing
                       algorithm.  In most cases, this will be an Account's
                       _fullname.
        :return int -- a bucket, 0 <= bucket < self.NUM_BUCKETS
        """
        # Mix the feature name in with the seed so the same users don't get
        # selected for ramp-ups for every feature.
        hashed = hashlib.sha1(self.name + seed)
        bucket = long(hashed.hexdigest(), 16) % self.NUM_BUCKETS
        return bucket

    @classmethod
    def _choose_variant(cls, bucket, variants):
        """Deterministically choose a percentage-based variant.

        The algorithm satisfies two conditions:

        1. It's deterministic (that is, every call with the same bucket and
           variants will result in the same answer).
        2. An increase in any of the variant percentages will keep the same
           buckets in the same variants as at the smaller percentage (that is,
           all buckets previously put in variant A will still be in variant A,
           all buckets previously put in variant B will still be in variant B,
           etc. and the increased percentages will be made of up buckets
           previously not assigned to a bucket).

        These attributes make it suitable for use in A/B experiments that may
        see an increase in their variant percentages post-enabling.

        :param bucket -- an integer bucket representation
        :param variants -- a dictionary of
                           <string:variant name>:<float:percentage> pairs.  If
                           any percentage exceeds 1/n percent, where n is the
                           number of variants, the percentage will be capped to
                           1/n.  These variants will be added to
                           DEFAULT_CONTROL_GROUPS to create the effective
                           variant set.
        :return string -- the variant name, or None if bucket doesn't fall into
                          any of the variants
        """
        # We want to always include two control groups, but allow overriding of
        # their percentages.
        all_variants = dict(cls.DEFAULT_CONTROL_GROUPS)
        all_variants.update(variants)

        # Say we have an experiment with two new things we're trying out for 2%
        # of users (A and B), a control group with 5% (C), and a pool of
        # excluded users (x).  The buckets will be assigned like so:
        #
        #     A B C A B C x x C x x C x x C x x x x x x x x x...
        #
        # This scheme allows us to later increase the size of A and B to 7%
        # while keeping the experience consistent for users in any group other
        # than excluded users:
        #
        #     A B C A B C A B C A B C A B C A B x A B x x x x...
        #
        # Rather than building this entire structure out in memory, we can use
        # a little bit of math to figure out just the one bucket's value.
        num_variants = len(all_variants)
        variant_names = sorted(all_variants.keys())
        # If the variants took up the entire set of buckets, which bucket would
        # we be in?
        candidate_variant = variant_names[bucket % num_variants]
        # Log a warning if this variant is capped, to help us prevent user (us)
        # error.  It's not the most correct to only check the one, but it's
        # easy and quick, and anything with that high a percentage should be
        # selected quite often.
        variant_fraction = all_variants[candidate_variant] / 100.0
        variant_cap = 1.0 / num_variants
        if variant_fraction > variant_cap:
            g.log.warning(
                'Variant %s exceeds allowable percentage (%.2f > %.2f)',
                candidate_variant,
                variant_fraction,
                variant_cap,
            )
        # Variant percentages are expressed as numeric percentages rather than
        # a fraction of 1 (that is, 1.5 means 1.5%, not 150%); thus, at 100
        # buckets, buckets and percents map 1:1 with each other.  Since we may
        # have more than 100 buckets (causing each bucket to represent less
        # than 1% each), we need to scale up how far "right" we move for each
        # variant percent.
        bucket_multiplier = cls.NUM_BUCKETS / 100
        # Now check to see if we're far enough left to be included in the
        # variant percentage.
        if bucket < (all_variants[candidate_variant] * num_variants *
                     bucket_multiplier):
            return candidate_variant
        else:
            return None

    @classmethod
    def _is_variant_enabled(cls, variant):
        """Determine if a variant is "enabled", as returned by is_enabled."""
        # The excluded experimental group will have a `None` variant and
        # this feature should be disabled.
        # For users in control groups, the feature is considered "not
        # enabled" because they should get the same behavior as ineligible
        # users.
        return (
            variant is not None and
            variant not in cls.DEFAULT_CONTROL_GROUPS
        )

    def is_enabled(self, user=None, subreddit=None, subdomain=None,
                   oauth_client=None):
        """Determine if a feature is enabled.

        For experiments, this induces a bucketing event by calling
        self._is_experiment_enabled.
        """
        cfg = self.config
        kw = dict(
            user=user,
            subreddit=subreddit,
            subdomain=subdomain,
            oauth_client=oauth_client
        )
        # first, test if the config would be enabled without an experiment
        if self._is_config_enabled(cfg, **kw):
            return True

        # next, test if the config is enabled fractionally
        if self._is_percent_enabled(cfg, user=user):
            return True

        # lastly, check experiment
        experiment = self.config.get('experiment')
        if self._is_config_enabled(experiment, **kw):
            return self._is_experiment_enabled(experiment, user=user)

        # Unknown value, default to off.
        return False

    def _is_config_enabled(
        self, cfg, user=None, subreddit=None, subdomain=None,
        oauth_client=None
    ):
        world = self.world

        if not cfg:
            return False

        if cfg.get('enabled') == self.GLOBALLY_ON:
            return True

        if cfg.get('enabled') == self.GLOBALLY_OFF:
            return False

        url_flag = cfg.get('url')
        if url_flag:
            if isinstance(url_flag, dict):
                for feature in world.url_features():
                    if feature in url_flag:
                        return self._is_variant_enabled(url_flag[feature])
            elif url_flag in world.url_features():
                return True

        if cfg.get('admin') and world.is_admin(user):
            return True

        if cfg.get('employee') and world.is_employee(user):
            return True

        if cfg.get('beta') and world.user_has_beta_enabled(user):
            return True

        if cfg.get('gold') and world.has_gold(user):
            return True

        loggedin = world.is_user_loggedin(user)
        if cfg.get('loggedin') and loggedin:
            return True

        if cfg.get('loggedout') and not loggedin:
            return True

        users = [u.lower() for u in cfg.get('users', [])]
        if users and user and user.name.lower() in users:
            return True

        subreddits = [s.lower() for s in cfg.get('subreddits', [])]
        if subreddits and subreddit and subreddit.lower() in subreddits:
            return True

        subdomains = [s.lower() for s in cfg.get('subdomains', [])]
        if subdomains and subdomain and subdomain.lower() in subdomains:
            return True

        clients = set(cfg.get('oauth_clients', []))
        if clients and oauth_client and oauth_client in clients:
            return True

    def _is_percent_enabled(self, cfg, user=None):
        loggedin = self.world.is_user_loggedin(user)
        percent_loggedin = cfg.get('percent_loggedin', 0)
        if percent_loggedin and loggedin:
            bucket = self._calculate_bucket(user._fullname)
            scaled_percent = bucket / (self.NUM_BUCKETS / 100)
            if scaled_percent < percent_loggedin:
                return True

        percent_loggedout = cfg.get('percent_loggedout', 0)
        if percent_loggedout and not loggedin:
            # We want this to match the JS function for bucketing loggedout
            # users, and JS doesn't make it easy to mix the feature name in
            # with the LOID. Just look at the last 4 chars of the LOID.
            loid = self.world.current_loid()
            if loid:
                try:
                    bucket = int(loid[-4:], 36) % 100
                    if bucket < percent_loggedout:
                        return True
                except ValueError:
                    pass

    def _is_experiment_enabled(self, experiment, user=None):
        """ Determine if there's an active variant of the specified experiment
        for the current user.

        Sends a bucketing event.
        """
        if not experiment.get('enabled', True):
            return False

        variant = None
        if FeatureState.is_user_experiment(experiment):
            variant = self._get_user_experiment_variant(experiment, user)
        elif FeatureState.is_page_experiment(experiment):
            content_id, _ = FeatureState.get_content_id()
            variant = self._get_page_experiment_variant(experiment)

        # We only want to send this event once per request, because that's
        # an easy way to get rid of extraneous events.
        if not c.have_sent_bucketing_event:
            c.have_sent_bucketing_event = set()

        if variant is not None and self.world.valid_experiment_request():
            if FeatureState.is_user_experiment(experiment):
                loid = self.world.current_loid()
                if self.world.is_user_loggedin(user):
                    bucketing_id = user._id
                else:
                    bucketing_id = loid

                key = ('user', self.name, bucketing_id)

                if (
                    g.running_as_script or
                    key not in c.have_sent_bucketing_event
                ):
                    g.events.bucketing_event(
                        experiment_id=experiment.get('experiment_id'),
                        experiment_name=self.name,
                        variant=variant,
                        user=user,
                        loid=self.world.current_loid_obj(),
                    )
                    c.have_sent_bucketing_event.add(key)
            else:
                # This is a page experiment, so we know we have a content_id
                key = ('page', self.name, content_id)
                if (
                    g.running_as_script or
                    key not in c.have_sent_bucketing_event
                ):
                    g.events.page_bucketing_event(
                        experiment_id=experiment.get('experiment_id'),
                        experiment_name=self.name,
                        variant=variant,
                        content_id=content_id,
                        request=request,
                        context=c,
                    )
                    c.have_sent_bucketing_event.add(key)

        return self._is_variant_enabled(variant)

    def variant(self, user):
        """ Determine which variant of this experiment, if any, is active.

        Does not send a bucketing event.
        """
        url_flag = self.config.get('url')
        # We only care about the dict-type 'url_flag's, since those are the
        # only ones that can specify a variant.
        if url_flag and isinstance(url_flag, dict):
            for feature in self.world.url_features():
                try:
                    return url_flag[feature]
                except KeyError:
                    pass

        experiment = self.config.get('experiment')
        if not experiment:
            return None

        if FeatureState.is_user_experiment(experiment):
            return self._get_user_experiment_variant(experiment, user)
        return self._get_page_experiment_variant(experiment)

    def _get_user_experiment_variant(self, experiment, user):
        # for logged in users, bucket based on the User's fullname
        if self.world.is_user_loggedin(user):
            bucket = self._calculate_bucket(user._fullname)
        # for logged out users, bucket based on the loid if we have one
        elif g.enable_loggedout_experiments:
            loid = self.world.current_loid()
            # we can't run an experiment if we have no id to vary on.
            if not loid:
                return None
            bucket = self._calculate_bucket(loid)
        # if logged out experiments are disabled, bail.
        else:
            return None

        variant = self._choose_variant(bucket, experiment.get('variants', {}))
        return variant

    @staticmethod
    def get_content_id():
        from r2.lib import utils
        thing = utils.url_to_thing(request.fullurl)

        if not thing:
            return None, None

        content_id = None
        type_name = getattr(thing, '_type_name', None)
        if type_name == 'comment':
            # We use the parent link for comment permalink pages, since
            # they share a canonical URL
            link = getattr(thing, 'link', thing.link_slow)
            content_id = link._fullname
        elif type_name == 'link':
            content_id = thing._fullname
        elif type_name == 'subreddit':
            content_id = thing._fullname
        return content_id, type_name

    def _get_page_experiment_variant(self, experiment):
        content_id, type_name = FeatureState.get_content_id()

        if content_id is None:
            return None

        # If we've restricted the experiment to certain page types, make sure
        # the request is for one of those
        if (experiment.get('subreddit_only', False) and
                type_name != 'subreddit'):
            return None

        if (experiment.get('link_only', False) and
                (type_name != 'link' and type_name != 'comment')):
            # We treat comment permalink pages like general comments pages
            return None

        experiment_seed = experiment.get('experiment_seed', None)
        bucket = self._calculate_bucket(content_id, experiment_seed)
        variant = self._choose_variant(bucket, experiment.get('variants', {}))
        return variant
