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
# All portions of the code written by reddit are Copyright (c) 2006-2016 reddit
# Inc. All Rights Reserved.
###############################################################################
"""A rate limit implementation with state in a key-value cache with item expiry.

The standalone functions (get_usage, get_timeslice, record_usage and the _multi
variants) adapt from the building blocks needed to compute a rate limit to the
key-value operations.

This protocol is best-effort, *not* atomic and transactional. Multiple
simultaneous accesses may push the actual rate limit above the intended limit.

The RateLimit class implements the "check a rate limit" and the "record a usage"
operations and a policy of how the rate limit configuration is loaded from the
application configuration.
"""

import collections
import time

import pylibmc

from pylons import app_globals as g

# AKA, a half open interval.
class TimeSlice(collections.namedtuple("TimeSlice", ["beginning", "end"])):

  @property
  def remaining(self):
      return self.end - int(time.time())


class RatelimitError(Exception):
    def __init__(self, e):
        self.wrapped = e

    def __str__(self):
        return str(self.wrapped)


def _make_ratelimit_cache_key(key_prefix, time_slice):
    # Short term rate limits: use a timestamp that's only valid for a day.
    fmt = '-%H%M%S'
    # Long term rate limits: use an unambigious timestamp.
    if time_slice.end - time_slice.beginning >= 86400:
        fmt = '-@%s'

    # enforce the "rl:" prefix for mcrouter
    prefix = "rl:" + key_prefix
    return prefix + time.strftime(fmt, time.gmtime(time_slice.beginning))


def get_timeslice(slice_seconds):
    """Return tuple describing the current slice given slice width.

    The elements of the tuple are:

    - `beginning`: seconds since epoch to beginning of time period
    - `end`: seconds since epoch to end of time period
    """

    now = int(time.time())
    slice_count = now // slice_seconds
    slice_start = int(slice_count * slice_seconds)
    slice_end = slice_start + slice_seconds
    return TimeSlice(slice_start, slice_end)


def record_usage(key_prefix, time_slice):
    """Record usage of a ratelimit for the specified time slice.

    The total usage (including this one) of the ratelimit is returned or
    RatelimitError is raised if something went wrong during the process.

    """

    key = _make_ratelimit_cache_key(key_prefix, time_slice)

    try:
        g.ratelimitcache.add(key, 0, time=time_slice.remaining)

        try:
            return g.ratelimitcache.incr(key)
        except pylibmc.NotFound:
            # Previous round of ratelimiting fell out in the
            # time between calling `add` and calling `incr`.
            now = int(time.time())
            if now < time_slice.end:
                g.ratelimitcache.add(key, 1, time=time_slice.end - now + 1)
                g.stats.simple_event("ratelimit.eviction")
            return 1
    except pylibmc.Error as e:
        raise RatelimitError(e)


def record_usage_multi(prefix_slices):
    """Record usage of multiple rate limits.

    If any of the of the rate limits expire during the processing of the
    function, the usage counts may be inaccurate and it is not defined
    which, if any, of the keys have been updated in the underlying cache.

    Arguments:
        prefix_slices: A list of (prefix, timeslice)

    Returns:
        A list of the usage counts in the same order as prefix_slices.

    Raises:
        RateLimitError if anything goes wrong.
        It is not defined which, if any, of the keys have been updated if this
        happens.

    """

    keys = [_make_ratelimit_cache_key(k, t) for k, t in prefix_slices]

    try:
        # Can't use add_multi because the various timeslices may be different.
        now = int(time.time())
        for key, (_, time_slice) in zip(keys, prefix_slices):
            g.ratelimitcache.add(key, 0, time=time_slice.end - now + 1)

        try:
            recent_usage = g.ratelimitcache.incr_multi(keys)
        except pylibmc.NotFound:
            # Some part of the previous round of ratelimiting fell out in the
            # time between calling `add` and calling `incr`.
            now = int(time.time())
            if now < time_slice.end:
                recent_usage = []
                for key, (_, time_slice) in zip(keys, prefix_slices):
                    if g.ratelimitcache.add(key, 1,
                                            time=time_slice.end - now + 1):
                        recent_usage.append(1)
                        g.stats.simple_event("ratelimit.eviction")
                    else:
                        recent_usage.append(g.ratelimitcache.get(key))
        return recent_usage
    except pylibmc.Error as e:
        raise RatelimitError(e)


def get_usage(key_prefix, time_slice):
    """Return the current usage of a ratelimit for the specified time slice."""

    key = _make_ratelimit_cache_key(key_prefix, time_slice)

    try:
        return g.ratelimitcache.get(key)
    except pylibmc.NotFound:
        return 0
    except pylibmc.Error as e:
        raise RatelimitError(e)


def get_usage_multi(prefix_slices):
    """Return the current usage of several rate limits.

    Arguments:
        prefix_slices: A list of (prefix, timeslice)

    Returns:
        A list of usages in the same order as prefix_slices
    """
    keys = [_make_ratelimit_cache_key(k, t) for k, t in prefix_slices]

    try:
        values = g.ratelimitcache.get_multi(keys)
        return [values.get(k, 0) for k in keys]
    except pylibmc.Error as e:
        raise RatelimitError(e)


class RateLimit(object):
    """A general purpose whole system rate limit.

    This class takes several basically-static properties for configuring the
    rate limit and logging and provides the basic operations of checking the
    limit and using the limit.

    The limit data is stored as a cluster of keys with a common prefix in the
    g.ratelimitcache memcached cluster.

    Subclasses should set the the parameter properties somehow. @property and in
    __init__ work fine if necessary.  This class is designed for ease of static
    declaration in the subclass definition while still allowing subclasses to
    use dynamic values when necessary.

    Properties:
        sample_rate: How frequently to log to g.stats, probabalistically.
            Defaults to 0.1.
        event_name: The g.stats event prefix.
        event_type: The g.stats event suffix.
        key: The ratelimitcache key prefix.
        limit: The count of events per self.seconds to allow.
        seconds: The length of the interval over which to limit rates.

    Example:
        class MyRateLimit(RateLimit):
            event_name = 'my_system'
            event_type = 'thing'
            key = 'my-rate-limit'
            # Limit to 10 times every 5 minutes
            limit = 10
            seconds = 300

        LIMIT = MyRateLimit()

        if LIMIT.check():
            do_something()
            LIMIT.record_usage()

        This code logs (at self.sample_rate)
            my_system.check_my_thing
            (if limited) my_system.thing_limit_hit
            (if not limited) my_system.set_thing_limit
    """
    sample_rate = 0.1

    def _record_event(self, event_type_template):
        g.stats.event_count(
            self.event_name,
            event_type_template.format(event_type=self.event_type),
            sample_rate=self.sample_rate)

    @property
    def timeslice(self):
        return get_timeslice(self.seconds)

    def _check(self, usage):
        self._record_event('check_{event_type}')
        below_limit = usage is None or usage < self.limit
        if not below_limit:
            self._record_event('{event_type}_limit_hit')
        return below_limit

    def check(self):
        """Check that the usage in the current timeslice is below the limit."""
        return self._check(get_usage(self.key, self.timeslice))

    @staticmethod
    def check_multi(ratelimits):
        """Check that all of the ratelimits can allow more usage."""
        usage = get_usage_multi([(r.key, r.timeslice) for r in ratelimits])
        return all(r._check(u) for u, r in zip(usage, ratelimits))

    def get_usage(self):
        """Get the usage of this limit. You should probably be using check()."""
        return get_usage(self.key, self.timeslice)

    def record_usage(self):
        """Record a new usage within the current timeslice."""
        self._record_event('set_{event_type}_limit')
        return record_usage(self.key, self.timeslice)

    @staticmethod
    def record_multi(ratelimits):
        """Record a new usage of all of the ratelimits.
        See record_usage_multi for everything that could go wrong.
        """
        for r in ratelimits:
            r._record_event('set_{event_type}_limit')
        return record_usage_multi([(r.key, r.timeslice) for r in ratelimits])


class LiveConfigRateLimit(RateLimit):
    """A RateLimit that derives its parameters from values in g.live_config.

    Properties:
        limit_live_key: The g.live_config key for the number per timespan
        seconds_live_key: The g.live_config key for the timespan over which the
            rate is limited, in seconds.
    """

    @property
    def seconds(self):
        return g.live_config[self.seconds_live_key]

    @property
    def limit(self):
        return g.live_config[self.limit_live_key]


class SimpleRateLimit(RateLimit):
    """Simple ratelimiting class.

    Useful for cases where we just want to be able to call record_usage() and
    check(). Does not record events to g.stats.

    """

    def __init__(self, name, seconds, limit):
        self.key = name
        self.seconds = seconds
        self.limit = limit

    def _record_event(self, event_type_template):
        # make this a no-op
        pass

    def record_and_check(self):
        self.record_usage()
        return self.check()
