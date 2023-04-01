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

import hashlib
import json
import requests

from pylons import app_globals as g

from r2.lib.providers.cdn import CdnProvider
from r2.lib.utils import constant_time_compare

class CloudFlareCdnProvider(CdnProvider):
    """A provider for reddit's configuration of CloudFlare.

    """
 
    def _do_content_purge(self, url):  
        """Does the purge of the content from CloudFlare."""      
        data = {
            'files': [
                url,
            ]
        }

        timer = g.stats.get_timer("providers.cloudflare.content_purge")
        timer.start()
        response = requests.delete(
            g.secrets['cloudflare_purge_key_url'],
            headers={
                'X-Auth-Email': g.secrets['cloudflare_email_address'],
                'X-Auth-Key': g.secrets['cloudflare_api_key'],
                'content-type': 'application/json',
            },
            data=json.dumps(data),
        )
        timer.stop()

    def get_client_ip(self, environ):
        try:
            client_ip = environ["HTTP_CF_CONNECTING_IP"]
            provided_hash = environ["HTTP_CF_CIP_TAG"].lower()
        except KeyError:
            return None

        secret = g.secrets["cdn_ip_verification"]
        expected_hash = hashlib.sha1(client_ip + secret).hexdigest()

        if not constant_time_compare(expected_hash, provided_hash):
            return None

        return client_ip

    def get_client_location(self, environ):
        return environ.get("HTTP_CF_IPCOUNTRY", None)

    def purge_content(self, url):
        """Purges the content specified by url from the cache."""

        # per the CloudFlare docs:
        #    https://www.cloudflare.com/docs/client-api.html#s4.5
        #    The full URL of the file that needs to be purged from 
        #    CloudFlare's  cache. Keep in mind, that if an HTTP and 
        #    an HTTPS version of the file exists, then both versions 
        #    will need to be purged independently
        # create the "alternate" URL for http or https
        if 'https://' in url:
            url_altered = url.replace('https://', 'http://')
        else:
            url_altered = url.replace('http://', 'https://')

        self._do_content_purge(url)
        self._do_content_purge(url_altered)

        return True
