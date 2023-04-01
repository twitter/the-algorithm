#!/usr/bin/env python
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

import collections
import unittest
import contextlib
import math
import functools
import traceback
import sys

from mock import MagicMock, patch
from pylons import request
from pylons import app_globals as g

from r2.lib import utils


class CrappyQuery(object):
    """Helper class for testing.
    It satisfies the methods fetch_things2 will call on a query
    and also generator interface.

    It is expected that fetch_things2 will set _after on
    instance of this class."""

    def __init__(self,
                 start,
                 end,
                 failures_between_chunks=0,
                 chunk_num_to_fail_on=None
                 ):
        """
        :param int start: integer to stat yielding from.
        :param int end: integer to end yielding at (exclusive).
        :param int failure_between_chunks: number of times to
            fail before starting to yield numbers from next chunk.
        :param int chunk_num_to_fail_on:  If not None, fail only
            after this chunk.  Do not fail between other chunks.
        """
        self.start = start
        self.end = end
        self.failures_between_chunks = failures_between_chunks
        self.chunk_num_to_fail_on = chunk_num_to_fail_on

        self._sort = "ascending"
        self._rules = []

        self.current_chunk = 0
        self.num_after_was_called = 0
        self.total_num_failed = 0
        self.num_failed = 0
        self.should_fail = False
        self._reset_state()

    def _reset_state(self):
        self.i = self.start

    def __iter__(self):
        return self

    def _after(self, num):
        self.num_after_was_called += 1
        self.start = num + 1

    def __next__(self):
        if (self.i >= self.start + self._limit or
                self.i >= self.end):
            # quit iterating if we reach end or end of chunk
            self.current_chunk += 1
            raise StopIteration()
        if self.i == self.start:
            # if we are at a start of a chunk, do some failing
            if (self.num_failed < self.failures_between_chunks and
                (self.chunk_num_to_fail_on is None or
                    self.chunk_num_to_fail_on == self.current_chunk)):
                self.should_fail = True
            else:
                self.should_fail = False
                self.num_failed = 0
        if self.should_fail:
            self.num_failed += 1
            self.total_num_failed += 1
            raise ValueError("FOO %d %d %d" %
                             (self.i,
                              self.current_chunk,
                              self.num_failed))
        ret = self.i
        self.i += 1
        return ret

    def next(self):
        return self.__next__()

    def __call__(self):
        self._reset_state()
        return self


class UtilsTest(unittest.TestCase):
    def test_weighted_lottery_errors(self):
        self.assertRaises(ValueError, utils.weighted_lottery, {})
        self.assertRaises(ValueError, utils.weighted_lottery, {'x': 0})
        self.assertRaises(
            ValueError, utils.weighted_lottery,
            collections.OrderedDict([('x', -1), ('y', 1)]))

    @contextlib.contextmanager
    def check_exponential_backoff_sleep_times(self,
                                              start,
                                              num):
        sleepy_times = []

        def record_sleep_times(sec):
            sleepy_times.append(sec)

        try:
            with patch('time.sleep', new=record_sleep_times):
                yield
        finally:
            self.assertEquals(len(sleepy_times), num)
            walker = start / 1000.0
            for i in sleepy_times:
                self.assertEquals(i, walker)
                walker *= 2

    def test_exponential_retrier(self):

        num_retries = 5

        def make_crappy_function(fail_start=1, fail_end=5):
            """Make a function that iterates from zero to infinity.
            However when it is iterating in the range [fail_start,fail_end]
            it will throw a ValueError exception but still increment
            """
            side_effects = [0]

            def ret():
                ret = side_effects[0]
                side_effects[0] += 1
                if ret >= fail_start and ret <= fail_end:
                    raise ValueError("foo %d" % ret)
                return ret

            return ret

        crappy_function = make_crappy_function()

        with self.check_exponential_backoff_sleep_times(500, 0):
            # first call to crappy_function should return zero without
            # any retrying
            self.assertEquals(0,
                              utils.exponential_retrier(
                                  crappy_function,
                                  max_retries=num_retries))

        with self.check_exponential_backoff_sleep_times(500, num_retries):
            # this should return 6 as we will retry 5 times
            self.assertEquals(6,
                              utils.exponential_retrier(
                                  crappy_function,
                                  max_retries=num_retries))

        with self.check_exponential_backoff_sleep_times(500, 0):
            self.assertEqual(7,
                             utils.exponential_retrier(
                                 crappy_function,
                                 max_retries=num_retries))

        with self.check_exponential_backoff_sleep_times(1, num_retries):
            # make sure this will fail in exponential_retrier and
            # test that last exception is re_thrown
            crappy_function = make_crappy_function(fail_start=0,
                                                   fail_end=1000)

            error = None
            try:
                utils.exponential_retrier(crappy_function,
                                          retry_min_wait_ms=1,
                                          max_retries=num_retries)
            except ValueError as e:
                # test that exception caught here has proper stack trace
                self.assertTrue(any(map(
                    lambda x: x[3] == "raise ValueError(\"foo %d\" % ret)",
                    traceback.extract_tb(sys.exc_traceback))))
                error = e

            self.assertEquals(error.message, "foo %d" % num_retries)

        with patch('time.sleep'):
            # check that exception is rethrown if we pass
            # exception filter that returns False if exception
            # is ValueError
            crappy_function = make_crappy_function(fail_start=0,
                                                   fail_end=0)

            def exception_filter(exception):
                return type(exception) is not ValueError

            error = None
            try:
                utils.exponential_retrier(crappy_function,
                                          retry_min_wait_ms=1,
                                          max_retries=100000,
                                          exception_filter=exception_filter)
            except ValueError as e:
                error = e

            self.assertEquals(error.message, "foo 0")

    def test_retriable_fetch_things_passthrough(self):
        # test simple pass through case

        num_retries = 5
        chunk_size = 5
        end = 20
        num_chunks = int(math.ceil(end / float(chunk_size)))

        fetch_things_with_retry = functools.partial(
            utils.fetch_things_with_retry,
            chunk_size=chunk_size,
            max_retries=num_retries,
            retry_min_wait_ms=1)

        crappy_query = CrappyQuery(0, end, 0)
        generated = list(fetch_things_with_retry(crappy_query))

        self.assertEquals(generated, range(0, end))
        self.assertEquals(crappy_query.total_num_failed, 0)
        self.assertEquals(crappy_query.num_after_was_called, num_chunks)

    def test_retriable_fetch_things_exception_rethrow(self):
        # test that exception is rethrown if we run out of retries

        num_retries = 5
        chunk_size = 5
        end = 20
        num_chunks = int(math.ceil(end / float(chunk_size)))

        fetch_things_with_retry = functools.partial(
            utils.fetch_things_with_retry,
            chunk_size=chunk_size,
            max_retries=num_retries,
            retry_min_wait_ms=1)

        with self.check_exponential_backoff_sleep_times(1, num_retries):
            error = None
            crappy_query = CrappyQuery(0, end, num_retries + 1)
            try:
                list(fetch_things_with_retry(crappy_query))
            except ValueError as e:
                error = e

            # after should not have ever been called because
            # getting the first chunk should have failed
            self.assertEquals(0, crappy_query.num_after_was_called)
            self.assertEquals("FOO %d %d %d" % (0, 0, num_retries + 1),
                              error.message)

        # test same thing but failing in subsequent chunk
        with self.check_exponential_backoff_sleep_times(1, num_retries):
            crappy_query = CrappyQuery(0, end, num_retries + 1, 2)
            generated = []
            try:
                # cant use list here as it wont get cbunks that succeeded
                for i in fetch_things_with_retry(crappy_query):
                    generated.append(i)
            except ValueError as e:
                error = e

            # we should have generated some partial results
            self.assertEquals(generated, range(0, chunk_size * 2))
            self.assertEquals("FOO %d %d %d" % (10, 2, num_retries + 1),
                              error.message)
            self.assertEquals(2, crappy_query.num_after_was_called)

    def test_retriable_fetch_things_recover_from_fail(self):
        # test that we get all of the numbers in the range
        # if we the number of failures is less than number of retries

        num_retries = 5
        chunk_size = 5
        end = 20
        num_chunks = int(math.ceil(end / float(chunk_size)))

        fetch_things_with_retry = functools.partial(
            utils.fetch_things_with_retry,
            chunk_size=chunk_size,
            max_retries=num_retries,
            retry_min_wait_ms=1)

        with patch('time.sleep'):
            crappy_query = CrappyQuery(0, end, num_retries - 1)
            generated = list(fetch_things_with_retry(crappy_query))

            self.assertEquals(generated, range(0, end))
            self.assertEqual(num_chunks,
                             crappy_query.num_after_was_called)

            # same thing but fail in the subsequent chunk
            crappy_query = CrappyQuery(0, end, num_retries - 1, 2)
            generated = list(fetch_things_with_retry(crappy_query))

            self.assertEquals(generated, range(0, end))
            self.assertEquals(num_chunks,
                              crappy_query.num_after_was_called)

        # test same thing as above but with chunks=True
        with patch('time.sleep'):
            expected = []
            for i in range(0, num_chunks):
                expected.append(range(i * chunk_size,
                                      i * chunk_size + chunk_size))

            crappy_query = CrappyQuery(0, end, num_retries - 1)
            generated = list(fetch_things_with_retry(crappy_query,
                                                     chunks=True))

            self.assertEquals(generated, expected)
            self.assertEqual(num_chunks,
                             crappy_query.num_after_was_called)

            # same thing but fail in the subsequent chunk
            crappy_query = CrappyQuery(0, end, num_retries - 1, 2)
            generated = list(fetch_things_with_retry(crappy_query,
                                                     chunks=True))

            self.assertEquals(generated, expected)
            self.assertEquals(num_chunks,
                              crappy_query.num_after_was_called)

    def test_weighted_lottery(self):
        weights = collections.OrderedDict(
            [('x', 2), (None, 0), (None, 0), ('y', 3), ('z', 1)])

        def expect(result, random_value):
            scaled_r = float(random_value) / sum(weights.itervalues())
            self.assertEquals(
                result,
                utils.weighted_lottery(weights, _random=lambda: scaled_r))

        expect('x', 0)
        expect('x', 1)
        expect('y', 2)
        expect('y', 3)
        expect('y', 4)
        expect('z', 5)
        self.assertRaises(ValueError, expect, None, 6)

    def test_extract_subdomain(self):
        self.assertEquals(
            utils.extract_subdomain('beta.reddit.com', 'reddit.com'),
            'beta')

        self.assertEquals(
            utils.extract_subdomain('beta.reddit.local:8000', 'reddit.local'),
            'beta')

        self.assertEquals(
            utils.extract_subdomain('reddit.com', 'reddit.com'),
            '')

        self.assertEquals(
            utils.extract_subdomain('internet-frontpage.com', 'reddit.com'),
            '')

    def test_coerce_url_to_protocol(self):
        self.assertEquals(
            utils.coerce_url_to_protocol('http://example.com/foo'),
            'http://example.com/foo')

        self.assertEquals(
            utils.coerce_url_to_protocol('https://example.com/foo'),
            'http://example.com/foo')

        self.assertEquals(
            utils.coerce_url_to_protocol('//example.com/foo'),
            'http://example.com/foo')

        self.assertEquals(
            utils.coerce_url_to_protocol('http://example.com/foo', 'https'),
            'https://example.com/foo')

        self.assertEquals(
            utils.coerce_url_to_protocol('https://example.com/foo', 'https'),
            'https://example.com/foo')

        self.assertEquals(
            utils.coerce_url_to_protocol('//example.com/foo', 'https'),
            'https://example.com/foo')

    def test_sanitize_url(self):
        self.assertEquals(
            utils.sanitize_url('http://dk./'),
            'http://dk/'
        )

        self.assertEquals(
            utils.sanitize_url('http://google.com./'),
            'http://google.com/'
        )

        self.assertEquals(
            utils.sanitize_url('http://google.com/'),
            'http://google.com/'
        )

        self.assertEquals(
            utils.sanitize_url('https://github.com/reddit/reddit/pull/1302'),
            'https://github.com/reddit/reddit/pull/1302'
        )

        self.assertEquals(
            utils.sanitize_url('http://dk../'),
            None
        )


class TestCanonicalizeEmail(unittest.TestCase):
    def test_empty_string(self):
        canonical = utils.canonicalize_email("")
        self.assertEquals(canonical, "")

    def test_unicode(self):
        canonical = utils.canonicalize_email(u"\u2713@example.com")
        self.assertEquals(canonical, "\xe2\x9c\x93@example.com")

    def test_localonly(self):
        canonical = utils.canonicalize_email("invalid")
        self.assertEquals(canonical, "")

    def test_multiple_ats(self):
        canonical = utils.canonicalize_email("invalid@invalid@invalid")
        self.assertEquals(canonical, "")

    def test_remove_dots(self):
        canonical = utils.canonicalize_email("d.o.t.s@example.com")
        self.assertEquals(canonical, "dots@example.com")

    def test_remove_plus_address(self):
        canonical = utils.canonicalize_email("fork+nork@example.com")
        self.assertEquals(canonical, "fork@example.com")

    def test_unicode_in_byte_str(self):
        # this shouldn't ever happen, but some entries in postgres appear
        # to be byte strings with non-ascii in 'em.
        canonical = utils.canonicalize_email("\xe2\x9c\x93@example.com")
        self.assertEquals(canonical, "\xe2\x9c\x93@example.com")


class TestTruncString(unittest.TestCase):
    def test_empty_string(self):
        truncated = utils.trunc_string('', 80)
        self.assertEqual(truncated, '')

    def test_short_enough(self):
        truncated = utils.trunc_string('short string', 80)
        self.assertEqual(truncated, 'short string')

    def test_word_breaks(self):
        truncated = utils.trunc_string('two words', 6)
        self.assertEqual(truncated, 'two...')

    def test_suffix(self):
        truncated = utils.trunc_string('two words', 6, '')
        self.assertEqual(truncated, 'two')

    def test_really_long_words(self):
        truncated = utils.trunc_string('ThisIsALongWord', 10)
        self.assertEqual(truncated, 'ThisIsA...')


class TestUrlToThing(unittest.TestCase):

    def test_subreddit_noslash(self):
        with patch('r2.models.Subreddit') as MockSubreddit:
            MockSubreddit._by_name.return_value = s.Subreddit
            self.assertEqual(
                utils.url_to_thing('http://reddit.local/r/pics'),
                s.Subreddit,
            )

    def test_subreddit(self):
        with patch('r2.models.Subreddit') as MockSubreddit:
            MockSubreddit._by_name.return_value = s.Subreddit
            self.assertEqual(
                utils.url_to_thing('http://reddit.local/r/pics/'),
                s.Subreddit,
            )

    def test_frontpage(self):
        self.assertEqual(
            utils.url_to_thing('http://reddit.local/'),
            None,
        )
