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
import sys

from pylons import request
from pylons import tmpl_context as c
from pylons import app_globals as g
from pylons.util import PylonsContext, AttribSafeContextObj, ContextObj
import raven
from raven.processors import Processor
from weberror.reporter import Reporter

from r2.lib.app_globals import Globals


def get_operational_exceptions():
    import _pylibmc
    import sqlalchemy.exc
    import pycassa.pool
    import r2.lib.db.thing
    import r2.lib.lock
    import r2.lib.cache

    return (
        SystemExit,  # gunicorn is shutting us down
        _pylibmc.MemcachedError,
        r2.lib.db.thing.NotFound,
        r2.lib.lock.TimeoutExpired,
        sqlalchemy.exc.OperationalError,
        sqlalchemy.exc.IntegrityError,
        pycassa.pool.AllServersUnavailable,
        pycassa.pool.NoConnectionAvailable,
        pycassa.pool.MaximumRetryException,
    )


class SanitizeStackLocalsProcessor(Processor):
    keys_to_remove = (
        "self",
        "__traceback_supplement__",
    )

    classes_to_remove = (
        Globals,
        PylonsContext,
        AttribSafeContextObj,
        ContextObj,
    )

    def filter_stacktrace(self, data, **kwargs):
        def remove_keys(obj):
            if isinstance(obj, dict):
                for k in obj.keys():
                    if k in self.keys_to_remove:
                        obj.pop(k)
                    elif isinstance(obj[k], self.classes_to_remove):
                        obj.pop(k)
                    elif isinstance(obj[k], basestring):
                        contains_forbidden_repr = any(
                            _cls.__name__ in obj[k]
                            for _cls in self.classes_to_remove
                        )
                        if contains_forbidden_repr:
                            obj.pop(k)
                    elif isinstance(obj[k], (list, dict)):
                        remove_keys(obj[k])
            elif isinstance(obj, list):
                for v in obj:
                    if isinstance(v, (list, dict)):
                        remove_keys(v)

        for frame in data.get('frames', []):
            if 'vars' in frame:
                remove_keys(frame['vars'])


class RavenErrorReporter(Reporter):
    @classmethod
    def get_module_versions(cls):
        return {
            repo: commit_hash[:6]
            for repo, commit_hash in g.versions.iteritems()
        }

    @classmethod
    def add_http_context(cls, client):
        """Add request details to the 'request' context

        These fields will be filtered by SanitizePasswordsProcessor
        as long as they are one of 'data', 'cookies', 'headers', 'env', and
        'query_string'.

        """

        HEADER_WHITELIST = (
            "user-agent",
            "host",
            "accept",
            "accept-encoding",
            "accept-language",
            "referer",
        )
        headers = {
            k: v for k, v in request.headers.iteritems()
            if k.lower() in HEADER_WHITELIST
        }

        client.http_context({
            "url": request.path,
            "method": request.method,
            "query_string": request.query_string,
            "data": request.body,
            "headers": headers,
        })

        if "app" in request.GET:
            client.tags_context({"app": request.GET["app"]})

    @classmethod
    def add_reddit_context(cls, client):
        reddit_context = {
            "language": c.lang,
            "render_style": c.render_style,
        }

        if c.site:
            reddit_context["subreddit"] = c.site.name

        client.extra_context(reddit_context)

    @classmethod
    def add_user_context(cls, client):
        user_context = {}

        if c.user_is_loggedin:
            user_context["user"] = c.user._id

        if c.oauth2_client:
            user_context["oauth_client_id"] = c.oauth2_client._id
            user_context["oauth_client_name"] = c.oauth2_client.name

        client.user_context(user_context)

    @classmethod
    def get_raven_client(cls):
        app_path_prefixes = [
            "r2",
            "reddit_",  # plugins such as 'reddit_liveupdate'
            "/opt/",    # scripts may be run from /opt/REPO/scripts
        ]
        release_str = '|'.join(
           "%s:%s" % (repo, commit_hash)
           for repo, commit_hash in sorted(g.versions.items())
        )
        release_hash = md5(release_str).hexdigest()

        RAVEN_CLIENT = raven.Client(
            dsn=g.sentry_dsn,
            # use the default transport to send errors from another thread:
            transport=raven.transport.threaded.ThreadedHTTPTransport,
            include_paths=app_path_prefixes,
            processors=[
                'raven.processors.SanitizePasswordsProcessor',
                'r2.lib.log.SanitizeStackLocalsProcessor',
            ],
            release=release_hash,
            environment=g.pool_name,
            include_versions=False,     # handled by get_module_versions
            install_sys_hook=False,
        )
        return RAVEN_CLIENT

    @classmethod
    def capture_exception(cls, exc_info=None):
        if exc_info is None:
            # if possible exc_info should be captured as close to the exception
            # as possible and passed in because sys.exc_info() can give
            # unexpected behavior
            exc_info = sys.exc_info()

        if issubclass(exc_info[0], get_operational_exceptions()):
            return

        client = cls.get_raven_client()

        if g.running_as_script:
            # scripts are run like:
            # paster run INIFILE -c "python code to execute"
            # OR
            # paster run INIFILE script.py
            # either way sys.argv[-1] will tell us the entry point to the error
            culprit = 'script: "%s"' % sys.argv[-1]
        else:
            cls.add_http_context(client)
            cls.add_reddit_context(client)
            cls.add_user_context(client)

            routes_dict = request.environ["pylons.routes_dict"]
            controller = routes_dict.get("controller", "unknown")
            action = routes_dict.get("action", "unknown")
            culprit = "%s.%s" % (controller, action)

        try:
            client.captureException(
                exc_info=exc_info,
                data={
                    "modules": cls.get_module_versions(),
                    "culprit": culprit,
                },
            )
        finally:
            client.context.clear()

    def report(self, exc_data):
        self.capture_exception()


def write_error_summary(error):
    """Log a single-line summary of the error for easy log grepping."""
    fullpath = request.environ.get('FULLPATH', request.path)
    uid = c.user._id if c.user_is_loggedin else '-'
    g.log.error("E: %s U: %s FP: %s", error, uid, fullpath)


class LoggingErrorReporter(Reporter):
    """ErrorMiddleware-compatible reporter that writes exceptions to g.log."""

    def report(self, exc_data):
        # exception_formatted is the output of traceback.format_exception_only
        exception = exc_data.exception_formatted[-1].strip()

        # First emit a single-line summary.  This is great for grepping the
        # streaming log for errors.
        write_error_summary(exception)

        text, extra = self.format_text(exc_data)
        # TODO: send this all in one burst so that error reports aren't
        # interleaved / individual lines aren't dropped. doing so will take
        # configuration on the syslog side and potentially in apptail as well
        for line in text.splitlines():
            g.log.warning(line)
