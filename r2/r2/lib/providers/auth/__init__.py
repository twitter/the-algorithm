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


class AuthenticationProvider(object):
    """Provider for authenticating web requests.

    Authentication providers should look at the request environment and
    determine if a particular user is logged in to the site.  This may take the
    form of cookies like on reddit.com or perhaps a web server provided HTTP
    header in intranet environments.

    Authentication systems may allow users to select their login, or may force
    them to a particular one and disallow logout.

    Note: this is NOT intended for API authentication, see instead: OAuth.

    """

    def is_logout_allowed(self):
        """Return if the user allowed to log out.

        Some authentication systems, such as single sign-on on an intranet,
        pick up the authenticated user from an external system and logging out
        of reddit would be meaningless.  If disallowed, some UI elements can be
        disabled to reduce confusion.

        """
        raise NotImplementedError

    def get_authenticated_account(self):
        """Return the authenticated user, or None if logged out.

        """
        raise NotImplementedError
