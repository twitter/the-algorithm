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

from hashlib import md5

from r2.lib.filters import _force_utf8
from r2.lib.cache import NoneResult, make_key_id
from r2.lib.lock import make_lock_factory
from pylons import app_globals as g


def memoize(iden, time = 0, stale=False, timeout=30):
    def memoize_fn(fn):
        from r2.lib.memoize import NoneResult
        def new_fn(*a, **kw):

            #if the keyword param _update == True, the cache will be
            #overwritten no matter what
            update = kw.pop('_update', False)

            key = "memo:%s:%s" % (iden, make_key_id(*a, **kw))

            res = None if update else g.memoizecache.get(key, stale=stale)

            if res is None:
                # not cached, we should calculate it.
                with g.make_lock("memoize", 'memoize_lock(%s)' % key,
                                 time=timeout, timeout=timeout):

                    # see if it was completed while we were waiting
                    # for the lock
                    stored = None if update else g.memoizecache.get(key)
                    if stored is not None:
                        # it was calculated while we were waiting
                        res = stored
                    else:
                        # okay now go and actually calculate it
                        res = fn(*a, **kw)
                        if res is None:
                            res = NoneResult
                        g.memoizecache.set(key, res, time=time)

            if res == NoneResult:
                res = None

            return res

        new_fn.memoized_fn = fn
        return new_fn
    return memoize_fn

@memoize('test')
def test(x, y):
    import time
    time.sleep(1)
    print 'calculating %d + %d' % (x, y)
    if x + y == 10:
        return None
    else:
        return x + y
