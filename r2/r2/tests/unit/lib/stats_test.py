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

import unittest

from r2.lib import stats

class TimingStatBufferTest(unittest.TestCase):
    def test_tsb(self):
        tsb = stats.TimingStatBuffer()
        self.assertEquals([], list(tsb.flush()))

        for i in xrange(1, 4):
            for j in xrange(i):
                tsb.record(str(i), 0, 0.1 * (j + 1))
        self.assertEquals(
            set([('1', '100.0|ms'),
                 ('2', '150.0|ms'),  # (0.1 + 0.2) / 2
                 ('3', '200.0|ms'),  # (0.1 + 0.2 + 0.3) / 3
                ]), set(tsb.flush()))

class CountingStatBufferTest(unittest.TestCase):
    def test_csb(self):
        csb = stats.CountingStatBuffer()
        self.assertEquals([], list(csb.flush()))

        for i in xrange(1, 4):
            for j in xrange(i):
                csb.record(str(i), j + 1)
        self.assertEquals(
            set([('1', '1|c'),
                 ('2', '3|c'),
                 ('3', '6|c')]),
            set(csb.flush()))

class StringCountBufferTest(unittest.TestCase):
    def test_encode_string(self):
        enc = stats.StringCountBuffer._encode_string
        self.assertEquals('test', enc('test'))
        self.assertEquals('\\n\\&\\\\&', enc('\n|\\&'))

    def test_scb(self):
        scb = stats.StringCountBuffer()
        self.assertEquals([], list(scb.flush()))

        for i in xrange(1, 4):
            for j in xrange(i):
                for k in xrange(j + 1):
                    scb.record(str(i), str(j))
        self.assertEquals(
            set([('1', '1|s|0'),
                 ('2', '1|s|0'),
                 ('2', '2|s|1'),
                 ('3', '1|s|0'),
                 ('3', '2|s|1'),
                 ('3', '3|s|2')]),
            set(scb.flush()))

class FakeUdpSocket:
    def __init__(self, *ignored_args):
        self.host = None
        self.port = None
        self.datagrams = []

    def sendto(self, datagram, host_port):
        self.datagrams.append(datagram)

class StatsdConnectionUnderTest(stats.StatsdConnection):
    _make_socket = FakeUdpSocket

class StatsdConnectionTest(unittest.TestCase):
    @staticmethod
    def connect(compress=False):
         return StatsdConnectionUnderTest('host:1000', compress=compress)

    def test_parse_addr(self):
        self.assertEquals(
            ('1:2', 3), stats.StatsdConnection._parse_addr('1:2:3'))

    def test_send(self):
        conn = self.connect()
        conn.send((i, i) for i in xrange(1, 6))
        self.assertEquals(
            ['1:1\n2:2\n3:3\n4:4\n5:5'],
            conn.sock.datagrams)

        # verify compression
        data = [('a.b.c.w', 1), ('a.b.c.x', 2), ('a.b.c.y', 3), ('a.b.z', 4),
                ('bbb', 5), ('bbc', 6)]
        conn = self.connect(compress=True)
        conn.send(reversed(data))
        self.assertEquals(
            ['a.b.c.w:1\n^06x:2\n^06y:3\n^04z:4\nbbb:5\nbbc:6'],
            conn.sock.datagrams)
        conn = self.connect(compress=False)
        conn.send(reversed(data))
        self.assertEquals(
            ['bbc:6\nbbb:5\na.b.z:4\na.b.c.y:3\na.b.c.x:2\na.b.c.w:1'],
            conn.sock.datagrams)

        # ensure send is a no-op when not connected
        conn.sock = None
        conn.send((i, i) for i in xrange(1, 6))

class StatsdClientUnderTest(stats.StatsdClient):
    @classmethod
    def _data_iterator(cls, x):
       return sorted(iter(x))

    @classmethod
    def _make_conn(cls, addr):
        return StatsdConnectionUnderTest(addr, compress=False)

class StatsdClientTest(unittest.TestCase):
    def test_flush(self):
        client = StatsdClientUnderTest('host:1000')
        client.timing_stats.record('t', 0, 1)
        client.counting_stats.record('c', 1)
        client.flush()
        self.assertEquals(
            ['c:1|c\nt:1000.0|ms'],
            client.conn.sock.datagrams)

class CounterAndTimerTest(unittest.TestCase):
    @staticmethod
    def client():
        return StatsdClientUnderTest('host:1000')

    def test_get_stat_name(self):
        self.assertEquals(
            'a.b.c',
            stats._get_stat_name('a', '', u'b', None, 'c', 0))

    def test_counter(self):
        c = stats.Counter(self.client(), 'c')
        c.increment('a')
        c.increment('b', 2)
        c.decrement('c')
        c.decrement('d', 2)
        c += 1
        c -= 2
        self.assertEquals(
            set([('c.a', '1|c'),
                 ('c.b', '2|c'),
                 ('c.c', '-1|c'),
                 ('c.d', '-2|c'),
                 ('c', '-1|c')]),
            set(c.client.counting_stats.flush()))
        self.assertEquals(set(), set(c.client.counting_stats.flush()))

    def test_timer(self):
        t = stats.Timer(self.client(), 't')
        t._time = iter(i / 10.0 for i in xrange(10)).next
        self.assertRaises(AssertionError, t.intermediate, 'fail')
        self.assertRaises(AssertionError, t.stop)

        t.start()
        t.intermediate('a')
        t.intermediate('b')
        t.intermediate('c')
        t.stop(subname='t')

        self.assertRaises(AssertionError, t.intermediate, 'fail')
        self.assertRaises(AssertionError, t.stop)
        t.send('x', 0, 0.5)

        self.assertEquals(
            set([('t.a', '100.0|ms'),
                 ('t.b', '100.0|ms'),
                 ('t.c', '100.0|ms'),
                 ('t.t', '400.0|ms'),
                 ('t.x', '500.0|ms')]),
            set(t.client.timing_stats.flush()))
        self.assertEquals(set(), set(t.client.timing_stats.flush()))
