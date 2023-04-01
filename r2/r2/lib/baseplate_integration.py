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
"""Transitional integration with Baseplate.

This module provides basic transitional integration with Baseplate. Its intent
is to integrate baseplate-provided functionality (like thrift clients) into
r2's existing diagnostics infrastructure. It is not meant to be the last word
on r2+baseplate; ideally r2 will move towards using more of baseplate rather
than its own implementations.

"""

import functools
import sys

from baseplate.core import BaseplateObserver, ServerSpanObserver, SpanObserver
from pylons import app_globals as g, tmpl_context as c


def make_server_span(span_name):
    c.trace = g.baseplate.make_server_span(context=c, name=span_name)
    return c.trace


def finish_server_span():
    c.trace.finish()


def with_server_span(name):
    """A decorator for functions that run outside request context.

    This will add a server span which starts just before invocation of the
    function and ends immediately after. The context (`c`) will have all
    appropriate baseplate stuff added to it, and metrics will be flushed when
    the function returns.

    This is useful for functions run in cron jobs or from the shell. Note that
    you cannot call a function wrapped with this decorator from within an
    existing server span.

    """
    def with_server_span_decorator(fn):
        @functools.wraps(fn)
        def with_server_span_wrapper(*args, **kwargs):
            assert not c.trace, "called while already in a server span"

            try:
                with make_server_span(name):
                    return fn(*args, **kwargs)
            finally:
                g.stats.flush()
        return with_server_span_wrapper
    return with_server_span_decorator


# this is just for backwards compatibility
with_root_span = with_server_span


class R2BaseplateObserver(BaseplateObserver):
    def on_server_span_created(self, context, server_span):
        observer = R2ServerSpanObserver()
        server_span.register(observer)


class R2ServerSpanObserver(ServerSpanObserver):
    def on_child_span_created(self, span):
        observer = R2SpanObserver(span.name)
        span.register(observer)


class R2SpanObserver(SpanObserver):
    def __init__(self, span_name):
        self.metric_name = "providers.{}".format(span_name)
        self.timer = g.stats.get_timer(self.metric_name)

    def on_start(self):
        self.timer.start()

    def on_finish(self, exc_info):
        self.timer.stop()

        if exc_info:
            error = exc_info[1]
            g.log.warning("%s: error: %s", self.metric_name, error)
            g.stats.simple_event("{}.error".format(self.metric_name))
