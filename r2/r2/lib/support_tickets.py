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

class SupportTicketError(Exception):
    pass

class SupportTickerNotFoundError(SupportTicketError):
    pass

def create_support_ticket(subject,
                          comment_body,
                          comment_is_public=False,
                          group=None,
                          requester_email=None, 
                          product=None,
                          ):
    requester_id = None
    if requester_email == 'contact@reddit.com':
        requester_id = g.live_config['ticket_contact_user_id']
        
    custom_fields = []
    if product:
        custom_fields.append({
            'id': g.live_config['ticket_user_fields']['Product'],
            'value': product,
        })
        
    return g.ticket_provider.create(
        requester_id=requester_id,
        subject=subject,
        comment_body=comment_body,
        comment_is_public=comment_is_public,
        group_id=g.live_config['ticket_groups'][group],
        custom_fields=custom_fields,
    )

def get_support_ticket(ticket_id):
    return g.ticket_provider.get(ticket_id)

def get_support_ticket_url(ticket_id):
    return g.ticket_provider.build_ticket_url_from_id(ticket_id)

def update_support_ticket(ticket=None, ticket_id=None,
                          status=None,
                          comment_body=None,
                          comment_is_public=False,
                          tag_list=None,
                          ):
    if not ticket and not ticket_id:
        raise SupportTickerNotFoundError(
            'No ticket provided to update.'
        )
        
    if not ticket:
        ticket = get_support_ticket(ticket_id)
    
    return g.ticket_provider.update(
            ticket=ticket,
            status=status,
            comment_body=comment_body,
            comment_is_public=comment_is_public,
            tag_list=tag_list,
        )
