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

import os.path


def get_r2_path():
    # we know this file is at r2/r2/config/paths.py
    this_path = os.path.abspath(__file__)
    # walk up 3 directories to r2
    r2_path = os.path.dirname(os.path.dirname(os.path.dirname(this_path)))
    return r2_path


def get_built_statics_path():
    """Return the path for built (compiled/compressed) statics."""
    r2_path = get_r2_path()
    return os.path.join(r2_path, 'build', 'public')


def get_raw_statics_path():
    """Return the path for the raw (under version control) statics"""
    r2_path = get_r2_path()
    return os.path.join(r2_path, 'r2', 'public')
