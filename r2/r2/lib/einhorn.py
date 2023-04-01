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
"""Run a Gunicorn WSGI container under Einhorn.

[Einhorn] is a language/protocol-agnostic socket and worker manager. We're
using it elsewhere for Baseplate services (where something WSGI-specific
wouldn't work on Thrift-based services) and its graceful reload logic is more
friendly than Gunicorn's*. However, for non-gevent WSGI, we still need
something to parse HTTP and provide a WSGI container. Gunicorn is excellent at
this. This module adapts Gunicorn to work under Einhorn as a single worker
process.

To run a paste-based application (like r2) under Einhorn using Gunicorn as the
WSGI container, run this module. All of gunicorn's command line arguments are
supported, though some may be meaningless (like worker count) because Gunicorn
isn't managing workers.

    einhorn -n 4 -b 0.0.0.0:8080 python -m r2.lib.einhorn example.ini

[Einhorn]: https://github.com/stripe/einhorn

*: In particular, when told to gracefully reload, Gunicorn will gracefully
terminate all workers immediately and then replace them. Einhorn starts up a
new worker, waits for it to acknowledge it is up and running, and then reaps an
old worker.

"""
import os
import signal
import sys

from baseplate.server import einhorn
from gunicorn import util
from gunicorn.app.pasterapp import PasterApplication
from gunicorn.workers.sync import SyncWorker


class EinhornSyncWorker(SyncWorker):
    def __init__(self, cfg, app):
        listener = einhorn.get_socket()
        super(EinhornSyncWorker, self).__init__(
            age=0,
            ppid=os.getppid(),
            sockets=[listener],
            app=app,
            timeout=None,
            cfg=cfg,
            log=cfg.logger_class(cfg),
        )

    def init_signals(self):
        # reset signal handlers to defaults
        [signal.signal(s, signal.SIG_DFL) for s in self.SIGNALS]

        # einhorn will send SIGUSR2 to request a graceful shutdown
        signal.signal(signal.SIGUSR2, self.start_graceful_shutdown)
        signal.siginterrupt(signal.SIGUSR2, False)

    def start_graceful_shutdown(self, signal_number, frame):
        # gunicorn changed the meaning of its signals in 8124190. by being
        # explicit what we mean here, we avoid woes when upgrading later.
        self.alive = False


def run_gunicorn_worker():
    if not einhorn.is_worker():
        print >> sys.stderr, "This process does not appear to be running under Einhorn."
        sys.exit(1)

    app = PasterApplication()
    util._setproctitle("worker [%s]" % app.cfg.proc_name)
    worker = EinhornSyncWorker(app.cfg, app)
    worker.init_process()


if __name__ == "__main__":
    run_gunicorn_worker()
