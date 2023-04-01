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

import json
import requests

from pylons import app_globals as g

def post_takedown_notice_to_external_site(title, 
                          request_type, 
                          date_sent,
                          date_received,
                          source,
                          action_taken,
                          public_description,
                          kind,
                          original_url,
                          infringing_urls,
                          submitter_attributes,
                          sender_name,
                          sender_kind,
                          sender_country,
                          ):
    """This method publicly posts a copy of the takedown notice to 
    https://lumendatabase.org. Posting notices to Lumen is free, and needs to
    be arranged by contacting their team. Read more about Lumen at
    https://www.lumendatabase.org/pages/about
    """
    # API documentation for lumendatabase.org found here:
    # https://github.com/berkmancenter/lumendatabase/blob/master/doc/api_documentation.mkd
    notice_json = {
        'authentication_token': g.secrets['lumendatabase_org_api_key'],
        'notice': {
            'title': title,
            'type': request_type,
            'date_sent': date_sent.strftime('%Y-%m-%d'),
            'date_received': date_received.strftime('%Y-%m-%d'),
            'source': source,
            'jurisdiction_list': 'US, CA',
            'action_taken': action_taken,
            'works_attributes': [
                {
                    'description': public_description,
                    'kind': kind,
                    'copyrighted_urls_attributes': [
                        { 'url': original_url },
                    ],
                    'infringing_urls_attributes': infringing_urls
                }
            ],
            'entity_notice_roles_attributes': [
                {
                    'name': 'recipient',
                    'entity_attributes': submitter_attributes,
                },
                {
                    'name': 'sender',
                    'entity_attributes': {
                        'name': sender_name,
                        'kind': sender_kind,
                        'address_line_1': '',
                        'city': '',
                        'state': '', 
                        'zip': '',
                        'country_code': sender_country,
                    }
                }
            ]
        }
    }
        
    timer = g.stats.get_timer('lumendatabase.takedown_create')
    timer.start()
    response = requests.post(
        '%snotices' % g.live_config['lumendatabase_org_api_base_url'],
        headers={
            'Content-type': 'application/json',
            'Accept': 'application/json',
        },
        data=json.dumps(notice_json)
    )
    timer.stop()
    return response.headers['location']
