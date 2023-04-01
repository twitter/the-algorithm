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

from r2.lib.cache import MemcachedError


# smart get multi:
# For any keys not found in the cache, miss_fn() is run and the result is
# stored in the cache. Then it returns everything, both the hits and misses.
def sgm(cache, keys, miss_fn, str prefix='', int time=0, stale=False,
        found_fn=None, _update=False, stat_subname=None,
        ignore_set_errors=False):
    cdef dict ret
    cdef dict s_keys
    cdef dict cached
    cdef dict calculated
    cdef dict calculated_to_cache
    cdef set  still_need

    ret = {}

    # map the string versions of the keys to the real version. we only
    # need this to interprate the cache's response and turn it back
    # into the version they asked for
    s_keys = {}
    for key in keys:
        s_keys[str(key)] = key

    if _update:
        cached = {}
    else:
        kw = {}
        if stale:
            kw['stale'] = stale
        if stat_subname:
            kw['stat_subname'] = stat_subname

        cached = cache.get_multi(s_keys.keys(), prefix=prefix, **kw)

        for k, v in cached.iteritems():
            ret[s_keys[k]] = v

    still_need = set(s_keys.values()) - set(ret.keys())

    if found_fn is not None:
        # give the caller an opportunity to reject some of the cache
        # hits if they aren't good enough. it's expected to use the
        # mutability of the cached dict and still_need set to modify
        # them as appropriate
        found_fn(ret, still_need)

    if miss_fn and still_need:
        # if we didn't get all of the keys from the cache, go to the
        # miss_fn with the keys they asked for minus the ones that we
        # found
        calculated = miss_fn(still_need)
        ret.update(calculated)

        calculated_to_cache = {}
        for k, v in calculated.iteritems():
            calculated_to_cache[str(k)] = v

        try:
            cache.set_multi(calculated_to_cache, prefix=prefix, time=time)
        except MemcachedError:
            if not ignore_set_errors:
                raise

    return ret
