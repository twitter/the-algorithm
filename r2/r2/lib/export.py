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

import sys

__all__ = ["export", "ExportError"]


class ExportError(Exception):
    def __init__(self, module):
        msg = "Missing __all__ declaration in module %s.  " \
              "@export cannot be used without declaring __all__ " \
              "in that module." % (module)
        Exception.__init__(self, msg)


def export(exported_entity):
    """Use a decorator to avoid retyping function/class names.
  
    * Based on an idea by Duncan Booth:
    http://groups.google.com/group/comp.lang.python/msg/11cbb03e09611b8a
    * Improved via a suggestion by Dave Angel:
    http://groups.google.com/group/comp.lang.python/msg/3d400fb22d8a42e1
    * Copied from Stack Overflow
    http://stackoverflow.com/questions/6206089/is-it-a-good-practice-to-add-names-to-all-using-a-decorator
    """
    all_var = sys.modules[exported_entity.__module__].__dict__.get('__all__')
    if all_var is None:
        raise ExportError(exported_entity.__module__)
    if exported_entity.__name__ not in all_var:  # Prevent duplicates if run from an IDE.
        all_var.append(exported_entity.__name__)
    return exported_entity

export(export)  # Emulate decorating ourself

