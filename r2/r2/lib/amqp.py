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
"""Methods and classes for inserting/removing from reddit's queues

There are three main ways of interacting with this module:

add_item: Adds a single item to a queue*
handle_items: For processing multiple items from a queue
consume_items: For processing a queue one item at a time


* _add_item (the internal function for adding items to amqp that are
  added using add_item) might block for an arbitrary amount of time
  while trying to get a connection to amqp.

"""
from Queue import Queue
from threading import local, Thread
from datetime import datetime
import os
import sys
import time
import errno
import socket
import itertools
import cPickle as pickle

from amqplib import client_0_8 as amqp

cfg = None
worker = None
connection_manager = None


def initialize(app_globals):
    global cfg
    cfg = Config(app_globals)
    global worker
    worker = Worker()
    global connection_manager
    connection_manager = ConnectionManager()


class Config(object):
    def __init__(self, g):
        self.amqp_host = g.amqp_host
        self.amqp_user = g.amqp_user
        self.amqp_pass = g.amqp_pass
        self.amqp_exchange = 'reddit_exchange'
        self.log = g.log
        self.amqp_virtual_host = g.amqp_virtual_host
        self.amqp_logging = g.amqp_logging
        self.stats = g.stats
        self.queues = g.queues
        self.reset_caches = g.reset_caches


class Worker:
    def __init__(self):
        self.q = Queue()
        self.t = Thread(target=self._handle)
        self.t.setDaemon(True)
        self.t.start()

    def _handle(self):
        while True:
            cfg.reset_caches()

            fn = self.q.get()
            try:
                fn()
                self.q.task_done()
            except:
                import traceback
                print traceback.format_exc()

    def do(self, fn, *a, **kw):
        fn1 = lambda: fn(*a, **kw)
        self.q.put(fn1)

    def join(self):
        self.q.join()


class ConnectionManager(local):
    # There should be only two threads that ever talk to AMQP: the
    # worker thread and the foreground thread (whether consuming queue
    # items or a shell). This class is just a wrapper to make sure
    # that they get separate connections
    def __init__(self):
        self.connection = None
        self.channel = None
        self.have_init = False

    def get_connection(self):
        while not self.connection:
            try:
                self.connection = amqp.Connection(
                    host=cfg.amqp_host,
                    userid=cfg.amqp_user,
                    password=cfg.amqp_pass,
                    virtual_host=cfg.amqp_virtual_host,
                    insist=False,
                )
            except (socket.error, IOError), e:
                print ('error connecting to amqp %s @ %s (%r)' %
                       (cfg.amqp_user, cfg.amqp_host, e))
                time.sleep(1)

        # don't run init_queue until someone actually needs it. this
        # allows the app server to start and serve most pages if amqp
        # isn't running
        if not self.have_init:
            self.init_queue()
            self.have_init = True

        return self.connection

    def get_channel(self, reconnect = False):
        # Periodic (and increasing with uptime) errors appearing when
        # connection object is still present, but appears to have been
        # closed.  This checks that the the connection is still open.
        if self.connection and self.connection.channels is None:
            cfg.log.error(
                "Error: amqp.py, connection object with no available channels."
                "  Reconnecting...")
            self.connection = None

        if not self.connection or reconnect:
            self.connection = None
            self.channel = None
            self.get_connection()

        if not self.channel:
            self.channel = self.connection.channel()

        return self.channel

    def init_queue(self):
        chan = self.get_channel()
        chan.exchange_declare(exchange=cfg.amqp_exchange,
                              type="direct",
                              durable=True,
                              auto_delete=False)

        for queue in cfg.queues:
            chan.queue_declare(queue=queue.name,
                               durable=queue.durable,
                               exclusive=queue.exclusive,
                               auto_delete=queue.auto_delete)

        for queue, key in cfg.queues.bindings:
            chan.queue_bind(routing_key=key,
                            queue=queue,
                            exchange=cfg.amqp_exchange)



DELIVERY_TRANSIENT = 1
DELIVERY_DURABLE = 2

def _add_item(routing_key, body, message_id = None,
              delivery_mode=DELIVERY_DURABLE, headers=None,
              exchange=None, send_stats=True):
    """adds an item onto a queue. If the connection to amqp is lost it
    will try to reconnect and then call itself again."""
    if not cfg.amqp_host:
        cfg.log.error("Ignoring amqp message %r to %r" % (body, routing_key))
        return
    if not exchange:
        exchange = cfg.amqp_exchange

    chan = connection_manager.get_channel()
    msg = amqp.Message(body,
                       timestamp = datetime.now(),
                       delivery_mode = delivery_mode)
    if message_id:
        msg.properties['message_id'] = message_id

    if headers:
        msg.properties["application_headers"] = headers

    event_name = 'amqp.%s' % routing_key
    try:
        chan.basic_publish(msg,
                           exchange=exchange,
                           routing_key = routing_key)
    except Exception as e:
        if send_stats:
            cfg.stats.event_count(event_name, 'enqueue_failed')

        if e.errno == errno.EPIPE:
            connection_manager.get_channel(True)
            add_item(routing_key, body, message_id)
        else:
            raise
    else:
        if send_stats:
            cfg.stats.event_count(event_name, 'enqueue')

def add_item(routing_key, body, message_id=None,
             delivery_mode=DELIVERY_DURABLE, headers=None,
             exchange=None, send_stats=True):
    if cfg.amqp_host and cfg.amqp_logging:
        cfg.log.debug("amqp: adding item %r to %r", body, routing_key)
    if exchange is None:
        exchange = cfg.amqp_exchange

    worker.do(_add_item, routing_key, body, message_id = message_id,
              delivery_mode=delivery_mode, headers=headers, exchange=exchange,
              send_stats=send_stats)

def add_kw(routing_key, **kw):
    add_item(routing_key, pickle.dumps(kw))

def consume_items(queue, callback, verbose=True):
    """A lighter-weight version of handle_items that uses AMQP's
       basic.consume instead of basic.get. Callback is only passed a
       single items at a time. This is more efficient than
       handle_items when the queue is likely to be occasionally empty
       or if batching the received messages is not necessary."""
    from pylons import tmpl_context as c

    chan = connection_manager.get_channel()

    # configure the amount of data rabbit will send down to our buffer before
    # we're ready for it (to reduce network latency). by default, it will send
    # as much as our buffers will allow.
    chan.basic_qos(
        # size in bytes of prefetch window. zero indicates no preference.
        prefetch_size=0,
        # maximum number of prefetched messages.
        prefetch_count=10,
        # if global, applies to the whole connection, else just this channel.
        a_global=False
    )

    def _callback(msg):
        if verbose:
            count_str = ''
            if 'message_count' in msg.delivery_info:
                # the count from the last message, if the count is
                # available
                count_str = '(%d remaining)' % msg.delivery_info['message_count']

            print "%s: 1 item %s" % (queue, count_str)

        cfg.reset_caches()
        c.use_write_db = {}

        ret = callback(msg)
        msg.channel.basic_ack(msg.delivery_tag)
        sys.stdout.flush()
        return ret

    chan.basic_consume(queue=queue, callback=_callback)

    try:
        while chan.callbacks:
            try:
                chan.wait()
            except KeyboardInterrupt:
                break
    finally:
        worker.join()
        if chan.is_open:
            chan.close()

def handle_items(queue, callback, ack=True, limit=1, min_size=0,
                 drain=False, verbose=True, sleep_time=1):
    """Call callback() on every item in a particular queue. If the
    connection to the queue is lost, it will die. Intended to be
    used as a long-running process."""
    if limit < min_size:
        raise ValueError("min_size must be less than limit")
    from pylons import tmpl_context as c

    chan = connection_manager.get_channel()
    countdown = None

    while True:
        # NB: None != 0, so we don't need an "is not None" check here
        if countdown == 0:
            break

        msg = chan.basic_get(queue)
        if not msg and drain:
            return
        elif not msg:
            time.sleep(sleep_time)
            continue

        if countdown is None and drain and 'message_count' in msg.delivery_info:
            countdown = 1 + msg.delivery_info['message_count']

        cfg.reset_caches()
        c.use_write_db = {}

        items = [msg]

        while countdown != 0:
            if countdown is not None:
                countdown -= 1
            if len(items) >= limit:
                break # the innermost loop only
            msg = chan.basic_get(queue)
            if msg is None:
                if len(items) < min_size:
                    time.sleep(sleep_time)
                else:
                    break
            else:
                items.append(msg)

        try:
            count_str = ''
            if 'message_count' in items[-1].delivery_info:
                # the count from the last message, if the count is
                # available
                count_str = '(%d remaining)' % items[-1].delivery_info['message_count']
            if verbose:
                print "%s: %d items %s" % (queue, len(items), count_str)
            callback(items, chan)

            if ack:
                # ack *all* outstanding messages
                chan.basic_ack(0, multiple=True)

            # flush any log messages printed by the callback
            sys.stdout.flush()
        except:
            for item in items:
                # explicitly reject the items that we've not processed
                chan.basic_reject(item.delivery_tag, requeue = True)
            raise


def empty_queue(queue):
    """debug function to completely erase the contents of a queue"""
    chan = connection_manager.get_channel()
    chan.queue_purge(queue)


def black_hole(queue):
    """continually empty out a queue as new items are created"""
    def _ignore(msg):
        print 'Ignoring msg: %r' % msg.body

    consume_items(queue, _ignore)

def dedup_queue(queue, rk = None, limit=None,
                delivery_mode = DELIVERY_DURABLE):
    """Hackily try to reduce the size of a queue by removing duplicate
       messages. The consumers of the target queue must consider
       identical messages to be idempotent. Preserves only message
       bodies"""
    chan = connection_manager.get_channel()

    if rk is None:
        rk = queue

    bodies = set()

    while True:
        msg = chan.basic_get(queue)

        if msg is None:
            break

        if msg.body not in bodies:
            bodies.add(msg.body)

        if limit is None:
            limit = msg.delivery_info.get('message_count')
            if limit is None:
                default_max = 100*1000
                print ("Message count was unavailable, defaulting to %d"
                       % (default_max,))
                limit = default_max
            else:
                print "Grabbing %d messages" % (limit,)
        else:
            limit -= 1
            if limit <= 0:
                break
            elif limit % 1000 == 0:
                print limit

    print "Grabbed %d unique bodies" % (len(bodies),)

    if bodies:
        for body in bodies:
            _add_item(rk, body, delivery_mode = delivery_mode)

        worker.join()

        chan.basic_ack(0, multiple=True)
