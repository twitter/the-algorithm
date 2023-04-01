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

import cStringIO
import gzip
import wsgiref.headers

from paste.util.mimeparse import parse_mime_type, desired_matches


ENCODABLE_CONTENT_TYPES = {
    "application/json",
    "application/javascript",
    "application/xml",
    "text/css",
    "text/csv",
    "text/html",
    "text/javascript",
    "text/plain",
    "text/xml",
}


class GzipMiddleware(object):
    """A middleware that transparently compresses content with gzip.

    Note: this middleware deliberately violates PEP-333 in three ways:

        - it disables the use of the "write()" callable.
        - it does content encoding which is a "hop-by-hop" feature.
        - it does not "yield at least one value each time its underlying
          application yields a value".

    None of these are an issue for the reddit application, but use at your
    own risk.

    """

    def __init__(self, app, compression_level, min_size):
        self.app = app
        self.compression_level = compression_level
        self.min_size = min_size

    def _start_response(self, status, response_headers, exc_info=None):
        self.status = status
        self.headers = response_headers
        self.exc_info = exc_info
        return self._write_not_implemented

    @staticmethod
    def _write_not_implemented(*args, **kwargs):
        """Raise an exception.

        This middleware doesn't work with the write callable.

        """
        raise NotImplementedError

    @staticmethod
    def content_length(headers, app_iter):
        """Return the content-length of this response as best as we can tell.

        If the application returned a Content-Length header we will trust it.
        If not, we are allowed by PEP-333 to attempt to determine the length of
        the app's iterable and if it's 1, use the length of the only chunk as
        the content-length.

        """
        content_length_header = headers["Content-Length"]

        if content_length_header:
            return int(content_length_header)

        try:
            app_iter_len = len(app_iter)
        except ValueError:
            return None  # streaming response; we're done here.

        if app_iter_len == 1:
            return len(app_iter[0])
        return None

    def should_gzip_response(self, headers, app_iter):
        # this middleware isn't smart enough to deal with stuff like ETags or
        # content ranges at the moment. let's just bail out. (this prevents
        # issues with pylons/paste's static file middleware)
        if "ETag" in headers:
            return False

        # here we are, violating pep-333 by looking at a hop-by-hop header
        # within the middleware chain. but this will prevent us from overriding
        # encoding done lower down in the app, if present. so it goes.
        if "Content-Encoding" in headers:
            return False

        # bail if we can't figure out how big it is or it's too small
        content_length = self.content_length(headers, app_iter)
        if not content_length or content_length < self.min_size:
            return False

        # make sure this is one of the content-types we're allowed to encode
        content_type = headers["Content-Type"]
        type, subtype, params = parse_mime_type(content_type)
        if "%s/%s" % (type, subtype) not in ENCODABLE_CONTENT_TYPES:
            return False

        return True

    @staticmethod
    def update_vary_header(headers):
        vary_headers = headers.get_all("Vary")
        del headers["Vary"]

        varies = []
        for vary_header in vary_headers:
            varies.extend(field.strip().lower()
                          for field in vary_header.split(","))

        if "*" in varies:
            varies = ["*"]
        elif "accept-encoding" not in varies:
            varies.append("accept-encoding")

        headers["Vary"] = ", ".join(varies)

    @staticmethod
    def request_accepts_gzip(environ):
        accept_encoding = environ.get("HTTP_ACCEPT_ENCODING", "identity")
        return "gzip" in desired_matches(["gzip"], accept_encoding)

    def __call__(self, environ, start_response):
        app_iter = self.app(environ, self._start_response)
        headers = wsgiref.headers.Headers(self.headers)

        response_compressible = self.should_gzip_response(headers, app_iter)
        if response_compressible:
            # this means that the sole factor left in determining whether or
            # not to gzip is the Accept-Encoding header; we should let
            # downstream caches know that this is the case with the Vary header
            self.update_vary_header(headers)

        if response_compressible and self.request_accepts_gzip(environ):
            headers["Content-Encoding"] = "gzip"

            response_buffer = cStringIO.StringIO()
            gzipper = gzip.GzipFile(fileobj=response_buffer, mode="wb",
                                    compresslevel=self.compression_level)
            try:
                for chunk in app_iter:
                    gzipper.write(chunk)
            finally:
                if hasattr(app_iter, "close"):
                    app_iter.close()

            gzipper.close()
            new_response = response_buffer.getvalue()
            encoded_app_iter = [new_response]
            response_buffer.close()

            headers["Content-Length"] = str(len(new_response))
        else:
            encoded_app_iter = app_iter

        # send the response
        start_response(self.status, self.headers, self.exc_info)
        return encoded_app_iter


def make_gzip_middleware(app, global_conf=None, compress_level=9, min_size=0):
    """Return a gzip-compressing middleware."""
    return GzipMiddleware(app, int(compress_level), int(min_size))
