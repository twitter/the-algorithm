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

"""r2

This file loads the finished app from r2.config.middleware.
"""

# _strptime is imported with PyImport_ImportModuleNoBlock which can fail
# miserably when multiple threads try to import it simultaneously.
# import this here to get it over with
# see "Non Blocking Module Imports" in:
# http://code.google.com/p/modwsgi/wiki/ApplicationIssues
import _strptime

# defer the (hefty) import until it's actually needed. this allows
# modules below r2 to be imported before cython files are built, also
# provides a hefty speed boost to said imports when they don't need
# the app initialization.
def make_app(*args, **kwargs):
    from r2.config.middleware import make_app as real_make_app
    return real_make_app(*args, **kwargs)
