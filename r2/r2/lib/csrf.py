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


class CSRFPreventionException(Exception):
    pass


def csrf_exempt(fn):
    """Mark an endpoint as exempt from CSRF prevention checks"""
    fn.handles_csrf = True
    return fn


def check_controller_csrf_prevention(controller):
    """Check that the a controller and its handlers are properly protected
       from CSRF attacks"""
    if getattr(controller, 'handles_csrf', False):
        return

    # We're only interested in handlers that might mutate data
    mutating_methods = {"POST", "PUT", "PATCH", "DELETE"}

    for name, func in controller.__dict__.iteritems():
        method, sep, action = name.partition('_')
        if not action:
            continue
        if method not in mutating_methods:
            continue

        # Check if the handler has specified how it deals with CSRF
        if not getattr(func, 'handles_csrf', False):
            endpoint_name = ':'.join((controller.__name__, name))
            msg = ("Handlers that might mutate data must be "
                   "explicit about CSRF prevention: %s" % endpoint_name)
            raise CSRFPreventionException(msg)
