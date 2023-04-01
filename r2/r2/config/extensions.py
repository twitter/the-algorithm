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

from pylons import tmpl_context as c

def api_type(subtype = ''):
    return 'api-' + subtype if subtype else 'api'

def is_api(subtype = ''):
    return c.render_style and c.render_style.startswith(api_type(subtype))

def get_api_subtype():
    if is_api() and c.render_style.startswith('api-'):
        return c.render_style[4:]

extension_mapping = {
    "rss": ("xml", "application/atom+xml; charset=UTF-8"),
    "xml": ("xml", "application/atom+xml; charset=UTF-8"),
    "js": ("js", "text/javascript; charset=UTF-8"),
    "embed": ("htmllite", "text/javascript; charset=UTF-8"),
    "mobile": ("mobile", "text/html; charset=UTF-8"),
    "png": ("png", "image/png"),
    "css": ("css", "text/css"),
    "csv": ("csv", "text/csv; charset=UTF-8"),
    "api": (api_type(), "application/json; charset=UTF-8"),
    "json-html": (api_type("html"), "application/json; charset=UTF-8"),
    "json-compact": (api_type("compact"), "application/json; charset=UTF-8"),
    "compact": ("compact", "text/html; charset=UTF-8"),
    "json": (api_type(), "application/json; charset=UTF-8"),
    "i": ("compact", "text/html; charset=UTF-8"),
}

API_TYPES = ('api', 'json')
RSS_TYPES = ('rss', 'xml')

def set_extension(environ, ext):
    environ["extension"] = ext
    environ["render_style"], environ["content_type"] = extension_mapping[ext]
