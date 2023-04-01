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

from pylons import app_globals as g

import json
import requests

BASE_URL = "https://api.createsend.com/api/v3.1/"
API_KEY = g.secrets['newsletter_api_key']
LIST_ID = g.newsletter_list_id

# under providers even though this is not yet a provider because this will be
# moved to become one, so we want the right stats namespace.
STATS_NAMESPACE = "providers.campaignmonitor"


class NewsletterError(Exception):
    pass


class EmailUnacceptableError(NewsletterError):
    pass


def add_subscriber(email, source=""):
    """Given an email, add this user to our upvoted newsletter.

    Optionally, also provide a source parameter describing where the subscribe
    came from - like "register".

    If the email was unable to be added, throws `NewsletterError`. Returns
    `True` on success.

    This should be used sparingly and outside of high traffic areas, as it
    requires a network call.
    """
    if not API_KEY or not LIST_ID:
        raise NewsletterError("Unable to subscribe user %s to newsletter. "
                              "API key or list ID not set." % email)

    params = {
        "EmailAddress": email
    }
    if source:
        params["CustomFields"] = [{"Key": "source", "Value": source}]

    timer = g.stats.get_timer('%s.add_subscriber' % STATS_NAMESPACE)
    timer.start()
    try:
        r = requests.post(
            "%s/subscribers/%s.json" % (BASE_URL, LIST_ID),
            json.dumps(params),
            timeout=5,
            auth=(API_KEY, 'x'),
        )
    except requests.exceptions.Timeout:
        g.stats.simple_event('%s.request.timeout' % STATS_NAMESPACE)
        raise NewsletterError("Unable to subscribe user %s to newsletter. "
                              "Request timed out." % email)
    except requests.exceptions.SSLError:
        g.stats.simple_event('%s.request.ssl_error' % STATS_NAMESPACE)
        raise NewsletterError("Unable to subscribe user %s to newsletter. "
                              "SSL Error." % email)
    else:
        if r.status_code == 201:
            return True
        elif r.status_code == 400:
            g.stats.simple_event('%s.request.email_unacceptable' %
                                 STATS_NAMESPACE)
            raise EmailUnacceptableError("Could not subscribe user %s to"
                                         "newsletter. Email was unacceptable, "
                                         "likely due to subscription status." %
                                         email)
        else:
            raise NewsletterError("Could not subscribe user %s to "
                                  "newsletter. Status code: %s" %
                                  (email, r.status_code))
    finally:
        timer.stop()
