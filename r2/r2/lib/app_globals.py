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
from urlparse import urlparse

import base64
import ConfigParser
import locale
import json
import logging
import os
import re
import signal
import site
import socket
import subprocess
import sys

from sqlalchemy import engine, event
from baseplate import Baseplate, config as baseplate_config
from baseplate.thrift_pool import ThriftConnectionPool
from baseplate.context.thrift import ThriftContextFactory
from baseplate.server import einhorn

import pkg_resources
import pytz

from r2.config import queues
import r2.lib.amqp
from r2.lib.baseplate_integration import R2BaseplateObserver
from r2.lib.cache import (
    CacheChain,
    CL_ONE,
    CL_QUORUM,
    CMemcache,
    HardCache,
    HardcacheChain,
    LocalCache,
    Mcrouter,
    MemcacheChain,
    Permacache,
    SelfEmptyingCache,
    StaleCacheChain,
    TransitionalCache,
)
from r2.lib.configparse import ConfigValue, ConfigValueParser
from r2.lib.contrib import ipaddress
from r2.lib.contrib.activity_thrift import ActivityService
from r2.lib.contrib.activity_thrift.ttypes import ActivityInfo
from r2.lib.eventcollector import EventQueue
from r2.lib.lock import make_lock_factory
from r2.lib.manager import db_manager
from r2.lib.plugin import PluginLoader
from r2.lib.providers import select_provider
from r2.lib.stats import (
    CacheStats,
    StaleCacheStats,
    Stats,
    StatsCollectingConnectionPool,
)
from r2.lib.translation import get_active_langs, I18N_PATH
from r2.lib.utils import config_gold_price, thread_dump
from r2.lib.zookeeper import (
    connect_to_zookeeper,
    LiveConfig,
    IPNetworkLiveList,
)


LIVE_CONFIG_NODE = "/config/live"

SEARCH_SYNTAXES = {
        'cloudsearch': ('cloudsearch', 'lucene', 'plain'),
        'solr': ('solr', 'plain'),
        }

SECRETS_NODE = "/config/secrets"


def extract_live_config(config, plugins):
    """Gets live config out of INI file and validates it according to spec."""

    # ConfigParser will include every value in DEFAULT (which paste abuses)
    # if we do this the way we're supposed to. sorry for the horribleness.
    live_config = config._sections["live_config"].copy()
    del live_config["__name__"]  # magic value used by ConfigParser

    # parse the config data including specs from plugins
    parsed = ConfigValueParser(live_config)
    parsed.add_spec(Globals.live_config_spec)
    for plugin in plugins:
        parsed.add_spec(plugin.live_config)

    return parsed


def _decode_secrets(secrets):
    return {key: base64.b64decode(value) for key, value in secrets.iteritems()}


def extract_secrets(config):
    # similarly to the live_config one above, if we just did
    # .options("secrets") we'd get back all the junk from DEFAULT too. bleh.
    secrets = config._sections["secrets"].copy()
    del secrets["__name__"]  # magic value used by ConfigParser
    return _decode_secrets(secrets)


def fetch_secrets(zk_client):
    node_data = zk_client.get(SECRETS_NODE)[0]
    secrets = json.loads(node_data)
    return _decode_secrets(secrets)


PERMISSIONS = {
    "admin": "admin",
    "sponsor": "sponsor",
    "employee": "employee",
}


class PermissionFilteredEmployeeList(object):
    def __init__(self, config, type):
        self.config = config
        self.type = type

    def __iter__(self):
        return (username
                for username, permission in self.config["employees"].iteritems()
                if permission == self.type)

    def __getitem__(self, key):
        return list(self)[key]

    def __contains__(self, item):
        # since we do these permission checks off usernames, make it case
        # insensitive to relax config file editing pains (safe because
        # Account._by_name is case-insensitive)
        return any(item.lower() == username.lower() for username in self)

    def __repr__(self):
        return "<PermissionFilteredEmployeeList %r>" % (list(self),)


SHUTDOWN_CALLBACKS = []
def on_app_shutdown(arbiter, worker):
    for callback in SHUTDOWN_CALLBACKS:
        callback()


class Globals(object):
    spec = {

        ConfigValue.int: [
            'db_pool_size',
            'db_pool_overflow_size',
            'commentpane_cache_time',
            'num_mc_clients',
            'MAX_CAMPAIGNS_PER_LINK',
            'MIN_DOWN_LINK',
            'MIN_UP_KARMA',
            'MIN_DOWN_KARMA',
            'MIN_RATE_LIMIT_KARMA',
            'MIN_RATE_LIMIT_COMMENT_KARMA',
            'HOT_PAGE_AGE',
            'ADMIN_COOKIE_TTL',
            'ADMIN_COOKIE_MAX_IDLE',
            'OTP_COOKIE_TTL',
            'hsts_max_age',
            'num_comments',
            'max_comments',
            'max_comments_gold',
            'max_comment_parent_walk',
            'max_sr_images',
            'num_serendipity',
            'comment_visits_period',
            'butler_max_mentions',
            'min_membership_create_community',
            'bcrypt_work_factor',
            'cassandra_pool_size',
            'sr_banned_quota',
            'sr_muted_quota',
            'sr_wikibanned_quota',
            'sr_wikicontributor_quota',
            'sr_moderator_invite_quota',
            'sr_contributor_quota',
            'sr_quota_time',
            'sr_invite_limit',
            'thumbnail_hidpi_scaling',
            'wiki_keep_recent_days',
            'wiki_max_page_length_bytes',
            'wiki_max_page_name_length',
            'wiki_max_page_separators',
            'RL_RESET_MINUTES',
            'RL_OAUTH_RESET_MINUTES',
            'comment_karma_display_floor',
            'link_karma_display_floor',
            'mobile_auth_gild_time',
            'default_total_budget_pennies',
            'min_total_budget_pennies',
            'max_total_budget_pennies',
            'default_bid_pennies',
            'min_bid_pennies',
            'max_bid_pennies',
            'frequency_cap_min',
            'frequency_cap_default',
            'eu_cookie_max_attempts',
        ],

        ConfigValue.float: [
            'statsd_sample_rate',
            'querycache_prune_chance',
            'RL_AVG_REQ_PER_SEC',
            'RL_OAUTH_AVG_REQ_PER_SEC',
            'RL_LOGIN_AVG_PER_SEC',
            'RL_LOGIN_IP_AVG_PER_SEC',
            'RL_SHARE_AVG_PER_SEC',
            'tracing_sample_rate',
        ],

        ConfigValue.bool: [
            'debug',
            'log_start',
            'sqlprinting',
            'template_debug',
            'reload_templates',
            'uncompressedJS',
            'css_killswitch',
            'db_create_tables',
            'disallow_db_writes',
            'disable_ratelimit',
            'amqp_logging',
            'read_only_mode',
            'disable_wiki',
            'heavy_load_mode',
            'disable_captcha',
            'disable_ads',
            'disable_require_admin_otp',
            'trust_local_proxies',
            'shard_commentstree_queues',
            'shard_author_query_queues',
            'shard_subreddit_query_queues',
            'shard_domain_query_queues',
            'authnet_validate',
            'ENFORCE_RATELIMIT',
            'RL_SITEWIDE_ENABLED',
            'RL_OAUTH_SITEWIDE_ENABLED',
            'enable_loggedout_experiments',
        ],

        ConfigValue.tuple: [
            'plugins',
            'stalecaches',
            'lockcaches',
            'permacache_memcaches',
            'cassandra_seeds',
            'automatic_reddits',
            'hardcache_categories',
            'case_sensitive_domains',
            'known_image_domains',
            'reserved_subdomains',
            'offsite_subdomains',
            'TRAFFIC_LOG_HOSTS',
            'exempt_login_user_agents',
            'autoexpand_media_types',
            'media_preview_domain_whitelist',
            'multi_icons',
            'hide_subscribers_srs',
            'mcrouter_addr',
        ],

        ConfigValue.tuple_of(ConfigValue.int): [
            'thumbnail_size',
            'preview_image_max_size',
            'preview_image_min_size',
            'mobile_ad_image_size',
        ],

        ConfigValue.tuple_of(ConfigValue.float): [
            'ios_versions',
            'android_versions',
        ],

        ConfigValue.dict(ConfigValue.str, ConfigValue.int): [
            'user_agent_ratelimit_regexes',
        ],

        ConfigValue.str: [
            'wiki_page_registration_info',
            'wiki_page_privacy_policy',
            'wiki_page_user_agreement',
            'wiki_page_gold_bottlecaps',
            'fraud_email',
            'feedback_email',
            'share_reply',
            'community_email',
            'smtp_server',
            'events_collector_url',
            'events_collector_test_url',
            'search_provider',
        ],

        ConfigValue.choice(ONE=CL_ONE, QUORUM=CL_QUORUM): [
             'cassandra_rcl',
             'cassandra_wcl',
        ],

        ConfigValue.choice(zookeeper="zookeeper", config="config"): [
            "liveconfig_source",
            "secrets_source",
        ],

        ConfigValue.timeinterval: [
            'ARCHIVE_AGE',
            "vote_queue_grace_period",
        ],

        config_gold_price: [
            'gold_month_price',
            'gold_year_price',
            'cpm_selfserve',
            'cpm_selfserve_geotarget_metro',
            'cpm_selfserve_geotarget_country',
            'cpm_selfserve_collection',
        ],

        ConfigValue.baseplate(baseplate_config.Optional(baseplate_config.Endpoint)): [
            "activity_endpoint",
            "tracing_endpoint",
        ],

        ConfigValue.dict(ConfigValue.str, ConfigValue.str): [
            'emr_traffic_tags',
        ],
    }

    live_config_spec = {
        ConfigValue.bool: [
            'frontend_logging',
            'mobile_gild_first_login',
            'precomputed_comment_suggested_sort',
        ],
        ConfigValue.int: [
            'captcha_exempt_comment_karma',
            'captcha_exempt_link_karma',
            'create_sr_account_age_days',
            'create_sr_comment_karma',
            'create_sr_link_karma',
            'cflag_min_votes',
            'ads_popularity_threshold',
            'precomputed_comment_sort_min_comments',
            'comment_vote_update_threshold',
            'comment_vote_update_period',
        ],
        ConfigValue.float: [
            'cflag_lower_bound',
            'cflag_upper_bound',
            'spotlight_interest_sub_p',
            'spotlight_interest_nosub_p',
            'gold_revenue_goal',
            'invalid_key_sample_rate',
            'events_collector_vote_sample_rate',
            'events_collector_poison_sample_rate',
            'events_collector_mod_sample_rate',
            'events_collector_quarantine_sample_rate',
            'events_collector_modmail_sample_rate',
            'events_collector_report_sample_rate',
            'events_collector_submit_sample_rate',
            'events_collector_comment_sample_rate',
            'events_collector_use_gzip_chance',
            'https_cert_testing_probability',
        ],
        ConfigValue.tuple: [
            'fastlane_links',
            'listing_chooser_sample_multis',
            'discovery_srs',
            'proxy_gilding_accounts',
            'mweb_blacklist_expressions',
            'global_loid_experiments',
            'precomputed_comment_sorts',
            'mailgun_domains',
        ],
        ConfigValue.str: [
            'listing_chooser_gold_multi',
            'listing_chooser_explore_sr',
        ],
        ConfigValue.messages: [
            'welcomebar_messages',
            'sidebar_message',
            'gold_sidebar_message',
        ],
        ConfigValue.dict(ConfigValue.str, ConfigValue.int): [
            'ticket_groups',
            'ticket_user_fields', 
        ],
        ConfigValue.dict(ConfigValue.str, ConfigValue.float): [
            'pennies_per_server_second',
        ],
        ConfigValue.dict(ConfigValue.str, ConfigValue.str): [
            'employee_approved_clients',
            'modmail_forwarding_email',
            'modmail_account_map',
        ],
        ConfigValue.dict(ConfigValue.str, ConfigValue.choice(**PERMISSIONS)): [
            'employees',
        ],
    }

    def __init__(self, config, global_conf, app_conf, paths, **extra):
        """
        Globals acts as a container for objects available throughout
        the life of the application.

        One instance of Globals is created by Pylons during
        application initialization and is available during requests
        via the 'g' variable.

        ``config``
            The PylonsConfig object passed in from ``config/environment.py``

        ``global_conf``
            The same variable used throughout ``config/middleware.py``
            namely, the variables from the ``[DEFAULT]`` section of the
            configuration file.

        ``app_conf``
            The same ``kw`` dictionary used throughout
            ``config/middleware.py`` namely, the variables from the
            section in the config file for your application.

        ``extra``
            The configuration returned from ``load_config`` in 
            ``config/middleware.py`` which may be of use in the setup of
            your global variables.

        """

        global_conf.setdefault("debug", False)

        # reloading site ensures that we have a fresh sys.path to build our
        # working set off of. this means that forked worker processes won't get
        # the sys.path that was current when the master process was spawned
        # meaning that new plugins will be picked up on regular app reload
        # rather than having to restart the master process as well.
        reload(site)
        self.pkg_resources_working_set = pkg_resources.WorkingSet()

        self.config = ConfigValueParser(global_conf)
        self.config.add_spec(self.spec)
        self.plugins = PluginLoader(self.pkg_resources_working_set,
                                    self.config.get("plugins", []))

        self.stats = Stats(self.config.get('statsd_addr'),
                           self.config.get('statsd_sample_rate'))
        self.startup_timer = self.stats.get_timer("app_startup")
        self.startup_timer.start()

        self.baseplate = Baseplate()
        self.baseplate.configure_logging()
        self.baseplate.register(R2BaseplateObserver())
        self.baseplate.configure_tracing(
            "r2",
            tracing_endpoint=self.config.get("tracing_endpoint"),
            sample_rate=self.config.get("tracing_sample_rate"),
        )

        self.paths = paths

        self.running_as_script = global_conf.get('running_as_script', False)
        
        # turn on for language support
        self.lang = getattr(self, 'site_lang', 'en')
        self.languages, self.lang_name = get_active_langs(
            config, default_lang=self.lang)

        all_languages = self.lang_name.keys()
        all_languages.sort()
        self.all_languages = all_languages
        
        # set default time zone if one is not set
        tz = global_conf.get('timezone', 'UTC')
        self.tz = pytz.timezone(tz)
        
        dtz = global_conf.get('display_timezone', tz)
        self.display_tz = pytz.timezone(dtz)

        self.startup_timer.intermediate("init")

    def __getattr__(self, name):
        if not name.startswith('_') and name in self.config:
            return self.config[name]
        else:
            raise AttributeError("g has no attr %r" % name)

    def setup(self):
        self.env = ''
        if (
            # handle direct invocation of "nosetests"
            "test" in sys.argv[0] or
            # handle "setup.py test" and all permutations thereof.
            "setup.py" in sys.argv[0] and "test" in sys.argv[1:]
        ):
            self.env = "unit_test"

        self.queues = queues.declare_queues(self)

        self.extension_subdomains = dict(
            simple="mobile",
            i="compact",
            api="api",
            rss="rss",
            xml="xml",
            json="json",
        )

        ################# PROVIDERS
        self.auth_provider = select_provider(
            self.config,
            self.pkg_resources_working_set,
            "r2.provider.auth",
            self.authentication_provider,
        )
        self.media_provider = select_provider(
            self.config,
            self.pkg_resources_working_set,
            "r2.provider.media",
            self.media_provider,
        )
        self.cdn_provider = select_provider(
            self.config,
            self.pkg_resources_working_set,
            "r2.provider.cdn",
            self.cdn_provider,
        )
        self.ticket_provider = select_provider(
            self.config,
            self.pkg_resources_working_set,
            "r2.provider.support",
            # TODO: fix this later, it refuses to pick up 
            # g.config['ticket_provider'] value, so hardcoding for now.
            # really, the next uncommented line should be:
            #self.ticket_provider,
            # instead of:
            "zendesk",
        )
        self.image_resizing_provider = select_provider(
            self.config,
            self.pkg_resources_working_set,
            "r2.provider.image_resizing",
            self.image_resizing_provider,
        )
        self.email_provider = select_provider(
            self.config,
            self.pkg_resources_working_set,
            "r2.provider.email",
            self.email_provider,
        )
        self.startup_timer.intermediate("providers")

        ################# CONFIGURATION
        # AMQP is required
        if not self.amqp_host:
            raise ValueError("amqp_host not set in the .ini")

        if not self.cassandra_seeds:
            raise ValueError("cassandra_seeds not set in the .ini")

        # heavy load mode is read only mode with a different infobar
        if self.heavy_load_mode:
            self.read_only_mode = True

        origin_prefix = self.domain_prefix + "." if self.domain_prefix else ""
        self.origin = self.default_scheme + "://" + origin_prefix + self.domain

        self.trusted_domains = set([self.domain])
        if self.https_endpoint:
            https_url = urlparse(self.https_endpoint)
            self.trusted_domains.add(https_url.hostname)

        # load the unique hashed names of files under static
        static_files = os.path.join(self.paths.get('static_files'), 'static')
        names_file_path = os.path.join(static_files, 'names.json')
        if os.path.exists(names_file_path):
            with open(names_file_path) as handle:
                self.static_names = json.load(handle)
        else:
            self.static_names = {}

        # make python warnings go through the logging system
        logging.captureWarnings(capture=True)

        log = logging.getLogger('reddit')

        # when we're a script (paster run) just set up super simple logging
        if self.running_as_script:
            log.setLevel(logging.INFO)
            log.addHandler(logging.StreamHandler())

        # if in debug mode, override the logging level to DEBUG
        if self.debug:
            log.setLevel(logging.DEBUG)

        # attempt to figure out which pool we're in and add that to the
        # LogRecords.
        try:
            with open("/etc/ec2_asg", "r") as f:
                pool = f.read().strip()
            # clean up the pool name since we're putting stuff after "-"
            pool = pool.partition("-")[0]
        except IOError:
            pool = "reddit-app"
        self.log = logging.LoggerAdapter(log, {"pool": pool})

        # set locations
        locations = pkg_resources.resource_stream(__name__,
                                                  "../data/locations.json")
        self.locations = json.loads(locations.read())

        if not self.media_domain:
            self.media_domain = self.domain
        if self.media_domain == self.domain:
            print >> sys.stderr, ("Warning: g.media_domain == g.domain. " +
                   "This may give untrusted content access to user cookies")
        if self.oauth_domain == self.domain:
            print >> sys.stderr, ("Warning: g.oauth_domain == g.domain. "
                    "CORS requests to g.domain will be allowed")

        for arg in sys.argv:
            tokens = arg.split("=")
            if len(tokens) == 2:
                k, v = tokens
                self.log.debug("Overriding g.%s to %s" % (k, v))
                setattr(self, k, v)

        self.reddit_host = socket.gethostname()
        self.reddit_pid  = os.getpid()

        if hasattr(signal, 'SIGUSR1'):
            # not all platforms have user signals
            signal.signal(signal.SIGUSR1, thread_dump)

        locale.setlocale(locale.LC_ALL, self.locale)

        # Pre-calculate ratelimit values
        self.RL_RESET_SECONDS = self.config["RL_RESET_MINUTES"] * 60
        self.RL_MAX_REQS = int(self.config["RL_AVG_REQ_PER_SEC"] *
                                      self.RL_RESET_SECONDS)

        self.RL_OAUTH_RESET_SECONDS = self.config["RL_OAUTH_RESET_MINUTES"] * 60
        self.RL_OAUTH_MAX_REQS = int(self.config["RL_OAUTH_AVG_REQ_PER_SEC"] *
                                     self.RL_OAUTH_RESET_SECONDS)

        self.RL_LOGIN_MAX_REQS = int(self.config["RL_LOGIN_AVG_PER_SEC"] *
                                     self.RL_RESET_SECONDS)
        self.RL_LOGIN_IP_MAX_REQS = int(self.config["RL_LOGIN_IP_AVG_PER_SEC"] *
                                        self.RL_RESET_SECONDS)
        self.RL_SHARE_MAX_REQS = int(self.config["RL_SHARE_AVG_PER_SEC"] *
                                     self.RL_RESET_SECONDS)

        # Compile ratelimit regexs
        user_agent_ratelimit_regexes = {}
        for agent_re, limit in self.user_agent_ratelimit_regexes.iteritems():
            user_agent_ratelimit_regexes[re.compile(agent_re)] = limit
        self.user_agent_ratelimit_regexes = user_agent_ratelimit_regexes

        self.startup_timer.intermediate("configuration")

        ################# ZOOKEEPER
        zk_hosts = self.config["zookeeper_connection_string"]
        zk_username = self.config["zookeeper_username"]
        zk_password = self.config["zookeeper_password"]
        self.zookeeper = connect_to_zookeeper(zk_hosts, (zk_username,
                                                         zk_password))

        self.throttles = IPNetworkLiveList(
            self.zookeeper,
            root="/throttles",
            reduced_data_node="/throttles_reduced",
        )

        parser = ConfigParser.RawConfigParser()
        parser.optionxform = str
        parser.read([self.config["__file__"]])

        if self.config["liveconfig_source"] == "zookeeper":
            self.live_config = LiveConfig(self.zookeeper, LIVE_CONFIG_NODE)
        else:
            self.live_config = extract_live_config(parser, self.plugins)

        if self.config["secrets_source"] == "zookeeper":
            self.secrets = fetch_secrets(self.zookeeper)
        else:
            self.secrets = extract_secrets(parser)

        ################# PRIVILEGED USERS
        self.admins = PermissionFilteredEmployeeList(
            self.live_config, type="admin")
        self.sponsors = PermissionFilteredEmployeeList(
            self.live_config, type="sponsor")
        self.employees = PermissionFilteredEmployeeList(
            self.live_config, type="employee")

        # Store which OAuth clients employees may use, the keys are just for
        # readability.
        self.employee_approved_clients = \
            self.live_config["employee_approved_clients"].values()

        self.startup_timer.intermediate("zookeeper")

        ################# MEMCACHE
        num_mc_clients = self.num_mc_clients

        # a smaller pool of caches used only for distributed locks.
        self.lock_cache = CMemcache(
            "lock",
            self.lockcaches,
            num_clients=num_mc_clients,
        )
        self.make_lock = make_lock_factory(self.lock_cache, self.stats)

        # memcaches used in front of the permacache CF in cassandra.
        # XXX: this is a legacy thing; permacache was made when C* didn't have
        # a row cache.
        permacache_memcaches = CMemcache(
            "perma",
            self.permacache_memcaches,
            min_compress_len=1400,
            num_clients=num_mc_clients,
        )

        # the stalecache is a memcached local to the current app server used
        # for data that's frequently fetched but doesn't need to be fresh.
        if self.stalecaches:
            stalecaches = CMemcache(
                "stale",
                self.stalecaches,
                num_clients=num_mc_clients,
            )
        else:
            stalecaches = None

        self.startup_timer.intermediate("memcache")

        ################# MCROUTER
        self.mcrouter = Mcrouter(
            "mcrouter",
            self.mcrouter_addr,
            min_compress_len=1400,
            num_clients=num_mc_clients,
        )

        ################# THRIFT-BASED SERVICES
        activity_endpoint = self.config.get("activity_endpoint")
        if activity_endpoint:
            # make ActivityInfo objects rendercache-key friendly
            # TODO: figure out a more general solution for this if
            # we need to do this for other thrift-generated objects
            ActivityInfo.cache_key = lambda self, style: repr(self)

            activity_pool = ThriftConnectionPool(activity_endpoint, timeout=0.1)
            self.baseplate.add_to_context("activity_service",
                ThriftContextFactory(activity_pool, ActivityService.Client))

        self.startup_timer.intermediate("thrift")

        ################# CASSANDRA
        keyspace = "reddit"
        self.cassandra_pools = {
            "main":
                StatsCollectingConnectionPool(
                    keyspace,
                    stats=self.stats,
                    logging_name="main",
                    server_list=self.cassandra_seeds,
                    pool_size=self.cassandra_pool_size,
                    timeout=4,
                    max_retries=3,
                    prefill=False
                ),
        }

        permacache_cf = Permacache._setup_column_family(
            'permacache',
            self.cassandra_pools[self.cassandra_default_pool],
        )

        self.startup_timer.intermediate("cassandra")

        ################# POSTGRES
        self.dbm = self.load_db_params()
        self.startup_timer.intermediate("postgres")

        ################# CHAINS
        # initialize caches. Any cache-chains built here must be added
        # to cache_chains (closed around by reset_caches) so that they
        # can properly reset their local components
        cache_chains = {}
        localcache_cls = (SelfEmptyingCache if self.running_as_script
                          else LocalCache)

        if stalecaches:
            self.gencache = StaleCacheChain(
                localcache_cls(),
                stalecaches,
                self.mcrouter,
            )
        else:
            self.gencache = CacheChain((localcache_cls(), self.mcrouter))
        cache_chains.update(gencache=self.gencache)

        if stalecaches:
            self.thingcache = StaleCacheChain(
                localcache_cls(),
                stalecaches,
                self.mcrouter,
            )
        else:
            self.thingcache = CacheChain((localcache_cls(), self.mcrouter))
        cache_chains.update(thingcache=self.thingcache)

        if stalecaches:
            self.memoizecache = StaleCacheChain(
                localcache_cls(),
                stalecaches,
                self.mcrouter,
            )
        else:
            self.memoizecache = MemcacheChain(
                (localcache_cls(), self.mcrouter))
        cache_chains.update(memoizecache=self.memoizecache)

        if stalecaches:
            self.srmembercache = StaleCacheChain(
                localcache_cls(),
                stalecaches,
                self.mcrouter,
            )
        else:
            self.srmembercache = MemcacheChain(
                (localcache_cls(), self.mcrouter))
        cache_chains.update(srmembercache=self.srmembercache)

        if stalecaches:
            self.relcache = StaleCacheChain(
                localcache_cls(),
                stalecaches,
                self.mcrouter,
            )
        else:
            self.relcache = MemcacheChain(
                (localcache_cls(), self.mcrouter))
        cache_chains.update(relcache=self.relcache)

        self.ratelimitcache = MemcacheChain(
                (localcache_cls(), self.mcrouter))
        cache_chains.update(ratelimitcache=self.ratelimitcache)

        # rendercache holds rendered partial templates.
        self.rendercache = MemcacheChain((
            localcache_cls(),
            self.mcrouter,
        ))
        cache_chains.update(rendercache=self.rendercache)

        # commentpanecaches hold fully rendered comment panes
        self.commentpanecache = MemcacheChain((
            localcache_cls(),
            self.mcrouter,
        ))
        cache_chains.update(commentpanecache=self.commentpanecache)

        # cassandra_local_cache is used for request-local caching in tdb_cassandra
        self.cassandra_local_cache = localcache_cls()
        cache_chains.update(cassandra_local_cache=self.cassandra_local_cache)

        if stalecaches:
            permacache_cache = StaleCacheChain(
                localcache_cls(),
                stalecaches,
                permacache_memcaches,
            )
        else:
            permacache_cache = CacheChain(
                (localcache_cls(), permacache_memcaches),
            )
        cache_chains.update(permacache=permacache_cache)

        self.permacache = Permacache(
            permacache_cache,
            permacache_cf,
            lock_factory=self.make_lock,
        )

        # hardcache is used for various things that tend to expire
        # TODO: replace hardcache w/ cassandra stuff
        self.hardcache = HardcacheChain(
            (localcache_cls(), HardCache(self)),
            cache_negative_results=True,
        )
        cache_chains.update(hardcache=self.hardcache)

        # I know this sucks, but we need non-request-threads to be
        # able to reset the caches, so we need them be able to close
        # around 'cache_chains' without being able to call getattr on
        # 'g'
        def reset_caches():
            for name, chain in cache_chains.iteritems():
                if isinstance(chain, TransitionalCache):
                    chain = chain.read_chain

                chain.reset()
                if isinstance(chain, LocalCache):
                    continue
                elif isinstance(chain, StaleCacheChain):
                    chain.stats = StaleCacheStats(self.stats, name)
                else:
                    chain.stats = CacheStats(self.stats, name)
        self.cache_chains = cache_chains

        self.reset_caches = reset_caches
        self.reset_caches()

        self.startup_timer.intermediate("cache_chains")

        # try to set the source control revision numbers
        self.versions = {}
        r2_root = os.path.dirname(os.path.dirname(self.paths["root"]))
        r2_gitdir = os.path.join(r2_root, ".git")
        self.short_version = self.record_repo_version("r2", r2_gitdir)

        if I18N_PATH:
            i18n_git_path = os.path.join(os.path.dirname(I18N_PATH), ".git")
            self.record_repo_version("i18n", i18n_git_path)

        # Initialize the amqp module globals, start the worker, etc.
        r2.lib.amqp.initialize(self)

        self.events = EventQueue()

        self.startup_timer.intermediate("revisions")

    def setup_complete(self):
        self.startup_timer.stop()
        self.stats.flush()

        if self.log_start:
            self.log.error(
                "%s:%s started %s at %s (took %.02fs)",
                self.reddit_host,
                self.reddit_pid,
                self.short_version,
                datetime.now().strftime("%H:%M:%S"),
                self.startup_timer.elapsed_seconds()
            )

        if einhorn.is_worker():
            einhorn.ack_startup()

    def record_repo_version(self, repo_name, git_dir):
        """Get the currently checked out git revision for a given repository,
        record it in g.versions, and return the short version of the hash."""
        try:
            subprocess.check_output
        except AttributeError:
            # python 2.6 compat
            pass
        else:
            try:
                revision = subprocess.check_output(["git",
                                                    "--git-dir", git_dir,
                                                    "rev-parse", "HEAD"])
            except subprocess.CalledProcessError, e:
                self.log.warning("Unable to fetch git revision: %r", e)
            else:
                self.versions[repo_name] = revision.rstrip()
                return revision[:7]

        return "(unknown)"

    def load_db_params(self):
        self.databases = tuple(ConfigValue.to_iter(self.config.raw_data['databases']))
        self.db_params = {}
        self.predefined_type_ids = {}
        if not self.databases:
            return

        if self.env == 'unit_test':
            from mock import MagicMock
            return MagicMock()

        dbm = db_manager.db_manager()
        db_param_names = ('name', 'db_host', 'db_user', 'db_pass', 'db_port',
                          'pool_size', 'max_overflow')
        for db_name in self.databases:
            conf_params = ConfigValue.to_iter(self.config.raw_data[db_name + '_db'])
            params = dict(zip(db_param_names, conf_params))
            if params['db_user'] == "*":
                params['db_user'] = self.db_user
            if params['db_pass'] == "*":
                params['db_pass'] = self.db_pass
            if params['db_port'] == "*":
                params['db_port'] = self.db_port

            if params['pool_size'] == "*":
                params['pool_size'] = self.db_pool_size
            if params['max_overflow'] == "*":
                params['max_overflow'] = self.db_pool_overflow_size

            dbm.setup_db(db_name, g_override=self, **params)
            self.db_params[db_name] = params

        dbm.type_db = dbm.get_engine(self.config.raw_data['type_db'])
        dbm.relation_type_db = dbm.get_engine(self.config.raw_data['rel_type_db'])

        def split_flags(raw_params):
            params = []
            flags = {}

            for param in raw_params:
                if not param.startswith("!"):
                    params.append(param)
                else:
                    key, sep, value = param[1:].partition("=")
                    if sep:
                        flags[key] = value
                    else:
                        flags[key] = True

            return params, flags

        prefix = 'db_table_'
        for k, v in self.config.raw_data.iteritems():
            if not k.startswith(prefix):
                continue

            params, table_flags = split_flags(ConfigValue.to_iter(v))
            name = k[len(prefix):]
            kind = params[0]
            server_list = self.config.raw_data["db_servers_" + name]
            engines, flags = split_flags(ConfigValue.to_iter(server_list))

            typeid = table_flags.get("typeid")
            if typeid:
                self.predefined_type_ids[name] = int(typeid)

            if kind == 'thing':
                dbm.add_thing(name, dbm.get_engines(engines),
                              **flags)
            elif kind == 'relation':
                dbm.add_relation(name, params[1], params[2],
                                 dbm.get_engines(engines),
                                 **flags)
        return dbm

    def __del__(self):
        """
        Put any cleanup code to be run when the application finally exits 
        here.
        """
        pass

    @property
    def search(self):
        if getattr(self, 'search_provider', None):
            if type(self.search_provider) == str:
                self.search_provider = select_provider(self.config,
                                       self.pkg_resources_working_set,
                                       "r2.provider.search",
                                       self.search_provider,
                                       )
            return  self.search_provider
        return None

    @property
    def search_syntaxes(self):
        return SEARCH_SYNTAXES[self.config.get('search_provider')]
