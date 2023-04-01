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
import re
import requests

from pylons import app_globals as g

from r2.lib.configparse import ConfigValue
from r2.lib.providers.support import TicketProvider

class ZenDeskProvider(TicketProvider):
    """A provider that interfaces with ZenDesk for managing tickets."""

    def build_ticket_url_from_id(self, ticket_id):
        return '%sagent/tickets/%s' % (
            g.live_config['ticket_base_url'], 
            ticket_id,
        )

    def create(self, 
               subject, 
               group_id,
               comment_body, 
               comment_is_public=False,
               requester_id=None, 
               custom_fields=[],
               ):
        """Creates a new support ticket on ZenDesk. 

        `subject` (self explanatory)
        `group_id` The group ID to assign to
        `comment_body` (self explanatory)
        `comment_is_public` Whether the comment is public or internal
        `requester_id` The external user ID for the user submitting
        `custom_fields` If custom feilds are defined and need to be set

        Returns a JSON object of the newly created ticket.
        """
        zd_ticket_create_params = {
            'ticket': {
                "requester_id": requester_id,
                "subject": subject,
                "comment": { 
                    "body": comment_body,
                    "public": comment_is_public,
                },
                "group_id": group_id,
                "custom_fields": custom_fields,
            }
        }
        
        timer = g.stats.get_timer("providers.zendesk.ticket_create")
        timer.start()
        response = requests.post(
            '%s/api/v2/tickets.json' % g.live_config['ticket_base_url'], 
            auth=(
                '%s/token' % g.secrets['zendesk_user'], 
                g.secrets["zendesk_api_key"]
            ),
            headers={'content-type': 'application/json'},
            data=json.dumps(zd_ticket_create_params))
        timer.stop
        
        if response.status_code != 201:
            g.log.error(
                'ZENDESK_CREATE_ERROR: code: %s msg: %s' % 
                (response.status_code, response.text)
            )
            return None

        return json.loads(response.content)['ticket']
    
    def get_ticket_id_from_url(self, ticket_url):
        """Extracts the ticket ID from a URL."""
        r = re.compile('(\d).*')
        numbers_in_url = r.findall(ticket_url)
        if not numbers_in_url:
            raise Exception('No digits in request_url')
        else:
            return numbers_in_url[0]

    def get(self, ticket_id):        
        """Gets a ticket from ZenDesk as a JSON object"""
        timer = g.stats.get_timer("providers.zendesk.ticket_get")
        timer.start()
        response = requests.get(
            '%s/api/v2/tickets/%s.json' % (
                g.live_config['ticket_base_url'], 
                ticket_id
            ),
            auth=(
                '%s/token' % g.secrets['zendesk_user'], 
                g.secrets["zendesk_api_key"]
            ),
            headers={'content-type': 'application/json'},
        )
        timer.stop()
        
        if response.status_code != 200:
            g.log.error(
                'ZENDESK_GET_ERROR: code: %s msg: %s' % 
                (response.status_code, response.text)
            )
            return None
            
        return json.loads(response.content)['ticket']

    def update(self, ticket, status=None,
               comment_body='', comment_is_public=False, 
               tag_list=None):
        """Updates the ticket on ZenDesk."""
        
        if comment_body:
            comment_json = {
                    "public": comment_is_public, 
                    "body": comment_body,
                }
        else:
            comment_json = {}
        
        ticket_updated_json = {
            'ticket': {
                "comment": comment_json,
                "status": status or ticket['status'],
                "tags": tag_list or ticket['tags'],
            }
        }
        
        timer = g.stats.get_timer("providers.zendesk.ticket_update")
        timer.start()
        response = requests.put(
            '%s/api/v2/tickets/%s.json' % (
                g.live_config['ticket_base_url'], 
                ticket['id']
            ),
            auth=(
                '%s/token' % g.secrets['zendesk_user'], 
                g.secrets["zendesk_api_key"]
            ),
            headers={'content-type': 'application/json'},
            data=json.dumps(ticket_updated_json)
        )
        timer.stop()
        
        if response.status_code != 200:
            g.log.error(
                'ZENDESK_UPDATE_ERROR: code: %s msg: %s' % 
                (response.status_code, response.text)
            )
            return None
            
        return json.loads(response.content)['ticket']
