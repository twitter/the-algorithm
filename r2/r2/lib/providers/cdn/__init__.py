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

class CdnProvider(object):
    """Provider for handling Content Delivery Network (CDN) interactions.

    """

    def get_client_ip(self, environ):
        """Verify and return the CDN-provided remote IP address.

        For requests coming through the CDN, the value of REMOTE_ADDR will be
        the IP address of one of the CDN's edge nodes rather than that of the
        client itself.  The CDN can provide the remote client's true IP as well
        as verification that the provided IP is indeed accurate via extra
        headers, the details of which vary with each CDN.

        This function should analyze the request environment and return the
        remote client's true IP address if it's present and validates. If it is
        not present or does not validate, it should return None.

        """
        raise NotImplementedError

    def get_client_location(self, environ):
        """Return CDN-provided geo location data for the requester.

        The return value is an ISO 3166-1 Alpha 2 format country code or None.

        This function is only defined when get_client_ip returns a non-None
        value, i.e. when the request has been validated as being from the CDN.

        """
        raise NotImplementedError

    def purge_content(self, url):
        """Purge content from the CDN by URL"""
        raise NotImplementedError
