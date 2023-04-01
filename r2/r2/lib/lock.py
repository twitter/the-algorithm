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

from __future__ import with_statement
from time import sleep
from datetime import datetime
from threading import local
from pylons import app_globals as g
import os
import socket
import random

from _pylibmc import MemcachedError

from r2.lib.utils import simple_traceback

# thread-local storage for detection of recursive locks
locks = local()

reddit_host = socket.gethostname()
reddit_pid  = os.getpid()

class TimeoutExpired(Exception): pass

class MemcacheLock(object):
    """A simple global lock based on the memcache 'add' command. We
    attempt to grab a lock by 'adding' the lock name. If the response
    is True, we have the lock. If it's False, someone else has it."""

    def __init__(self, stats, group, key, cache,
                 time=30, timeout=30, verbose=True):
        # get a thread-local set of locks that we own
        self.locks = locks.locks = getattr(locks, 'locks', set())

        self.stats = stats
        self.group = group
        self.key = key
        self.cache = cache
        self.time = time
        self.timeout = timeout
        self.have_lock = False
        self.owns_lock = False
        self.verbose = verbose

    def __enter__(self):
        self.acquire()
        return self

    def __exit__(self, type, value, tb):
        self.release()

    def acquire(self):
        start = datetime.now()

        self.nonce = (reddit_host, reddit_pid, simple_traceback(limit=7))

        # if this thread already has this lock, move on
        if self.key in self.locks:
            self.have_lock = True
            return

        timer = self.stats.get_timer("lock_wait")
        timer.start()

        # try and fetch the lock, looping until it's available
        lock = None
        while not lock:
            # catch all exceptions here because we can't trust the memcached
            # protocol. The add for the lock may have actually succeeded.
            try:
                lock = self.cache.add(self.key, self.nonce, time = self.time)
            except MemcachedError as e:
                if self.cache.get(self.key) == self.nonce:
                    g.log.error(
                        'Memcached add succeeded, but threw an exception for key %r %s',
                        self.key, e)
                    break

            if not lock:
                if (datetime.now() - start).seconds > self.timeout:
                    if self.verbose:
                        info = self.cache.get(self.key)
                        if info:
                            info = "%s %s\n%s" % info
                        else:
                            info = "(nonexistent)"
                        msg = ("\nSome jerk is hogging %s:\n%s" %
                                         (self.key, info))
                        msg += "^^^ that was the stack trace of the lock hog, not me."
                    else:
                        msg = "Timed out waiting for %s" % self.key
                    raise TimeoutExpired(msg)
                else:
                    # this should prevent unnecessary spam on highly contended locks.
                    sleep(random.uniform(0.1, 1))

        timer.stop(subname=self.group)

        self.owns_lock = True
        self.have_lock = True

        # tell this thread we have this lock so we can avoid deadlocks
        # of requests for the same lock in the same thread
        self.locks.add(self.key)

    def release(self):
        # only release the lock if we acquired it in the first place (are owner)
        if self.owns_lock:
            # verify that our lock did not expire before we could release it
            if self.cache.get(self.key) == self.nonce:
                self.cache.delete(self.key)
            else:
                g.log.error("Lock expired before completion at key %r: %s",
                            self.key, self.nonce)
            self.locks.remove(self.key)
            self.have_lock = False
            self.owns_lock = False
            self.nonce = None


def make_lock_factory(cache, stats):
    def factory(group, key, **kw):
        return MemcacheLock(stats, group, key, cache, **kw)
    return factory
