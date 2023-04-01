## The contents of this file are subject to the Common Public Attribution
## License Version 1.0. (the "License"); you may not use this file except in
## compliance with the License. You may obtain a copy of the License at
## http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
## License Version 1.1, but Sections 14 and 15 have been added to cover use of
## software over a computer network and provide for limited attribution for the
## Original Developer. In addition, Exhibit A has been modified to be
## consistent with Exhibit B.
##
## Software distributed under the License is distributed on an "AS IS" basis,
## WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
## the specific language governing rights and limitations under the License.
##
## The Original Code is reddit.
##
## The Original Developer is the Initial Developer.  The Initial Developer of
## the Original Code is reddit Inc.
##
## All portions of the code written by reddit are Copyright (c) 2006-2015
## reddit Inc. All Rights Reserved.
###############################################################################

<%!
  import json
  from r2.lib.strings import strings
  from babel.numbers import format_currency
%>

<% jquery_row = "$('." + thing.campaign._fullname + "')" %>

<tr class="campaign-row ${thing.campaign._fullname} ${'refund' if (not thing.free and thing.needs_refund) else ''}"
    data-startdate="${thing.campaign.start_date.strftime('%m/%d/%Y')}"
    data-enddate="${thing.campaign.end_date.strftime('%m/%d/%Y')}"
    data-targeting-collection="${thing.campaign.target.is_collection}"
    data-targeting="${thing.targeting_data}"
    data-country="${thing.country}"
    data-region="${thing.region}"
    data-metro="${thing.metro}"
    data-campaign_id36="${thing.campaign._id36}"
    data-campaign_name="${thing.campaign._fullname}"
    data-frequency_cap="${thing.campaign.frequency_cap}"
    data-priority="${thing.campaign.priority.name}"
    data-override="${json.dumps(thing.campaign.priority.inventory_override)}"
    data-is_auction="${thing.is_auction}"
    data-platform="${thing.platform}"
    data-mobile_os="${json.dumps(thing.mobile_os)}"
    data-ios_devices="${json.dumps(thing.ios_devices) if thing.ios_devices else ''}"
    data-ios_versions="${json.dumps(thing.ios_versions) if thing.ios_versions else ''}"
    data-android_devices="${json.dumps(thing.android_devices) if thing.android_devices else ''}"
    data-android_versions="${json.dumps(thing.android_versions) if thing.android_versions else ''}"
    data-paid="${json.dumps(thing.paid)}"
    data-has-served="${json.dumps(thing.campaign.has_served or thing.is_live or thing.is_complete)}"
    data-total_budget_dollars="${'%.2f' % thing.total_budget_dollars}"
    data-cost_basis="${thing.cost_basis}"
    data-bid_dollars="${'%.2f' % (thing.bid_pennies / 100.)}"
    data-is_live="${thing.is_live}">
  <td class="campaign-start-date">
    ${thing.campaign.start_date.strftime("%m/%d/%Y")}
  </td>

  <td class="campaign-end-date">
    ${thing.campaign.end_date.strftime("%m/%d/%Y")}
  </td>

  <td class="campaign-priority"
      style="${'display:none' if not c.user_is_sponsor else ''}">
    ${thing.campaign.priority.text}
  </td>

  <td class="campaign-total-budget ${'paid' if thing.paid else ''}">
    <span class="total-budget">
      %if thing.campaign.is_house:
        ${_("N/A")}
      %else:
        ${format_currency(thing.total_budget_dollars, 'USD', locale=c.locale)}
      %endif
    </span>
    %if not thing.campaign.is_house:
      %if not thing.paid:
        %if c.user_is_sponsor:
          <button class="free" onclick="free_campaign(${jquery_row})">
            ${_("free")}
          </button>
        %else:
          <button class="pay" onclick="$.redirect('${thing.pay_url}')">
            ${_("pay")}
          </button>
        %endif
      %elif not thing.free and not (thing.is_complete or thing.is_live):
        <button class="pay" onclick="$.redirect('${thing.pay_url}')">
          ${_("change")}
        </button>
      %endif

      %if thing.free:
        <span class='info'>${_("freebie")}</span>
      %endif

      %if not thing.free and c.user_is_sponsor and thing.needs_refund:
        <button class="refund" onclick="$.redirect('${thing.refund_url}')">
          ${_("refund")}
        </button>
      %endif
    %endif
  </td>

  <td class="campaign-spent">
      ${format_currency(thing.spent, 'USD', locale=c.locale)}
  </td>

  %if thing.ads_auction_enabled:
    <td class="campaign-bid">
      %if getattr(thing.campaign, 'cost_basis') is not 0:
        ${thing.printable_bid}
        ${thing.cost_basis.upper()}
      %else:
        ${_("N/A")}
      %endif
    </td>
  %endif

  <td class="campaign-target" title="${thing.campaign.target.pretty_name}">
    ${thing.campaign.target.pretty_name}
  </td>

  <td class="campaign-location" title="${thing.location_str}">
    ${thing.location_str}
  </td>

  %if c.user_is_sponsor:
    <td>
      %if thing.campaign.trans_country_match is None:
        N/A
      %elif thing.campaign.trans_country_match:
        no
      %else:
        <span title="${thing.campaign.trans_billing_country} vs. ${thing.campaign.trans_ip_country}">
          ${thing.campaign.trans_billing_country}/${thing.campaign.trans_ip_country}
        </span>
      %endif
    </td>
  %endif

  <td class="campaign-buttons">
    %if thing.is_complete:
      <span class='info'>${_("complete")}</span>
    %elif thing.is_edited_live:
      <span class='info'>${_("edited live")}</span>
    %else:
      %if thing.editable:
        <button class="edit" onclick="edit_campaign(${jquery_row})">
          ${_("edit")}
        </button>
      %endif

      %if not thing.is_live:
        <button class="delete" onclick="del_campaign(${jquery_row})">
          ${_("delete")}
        </button>
      %endif

      %if thing.is_live:
        <button class="view" onclick="$.redirect('${thing.view_live_url}')">
          ${_("view")}
        </button>
      %endif

      %if thing.pause_ads_enabled and thing.is_live:
        %if thing.campaign.paused:
          <button class="resume" onclick="toggle_pause_campaign(${jquery_row}, false)">
            ${_("resume")}
          </button>
        %elif thing.needs_approval:
          <span class='info'>${_("awaiting approval")}</span>
        %else:
          <button class="pause" onclick="toggle_pause_campaign(${jquery_row}, true)">
            ${_("pause")}
          </button>
        %endif
      %endif

      %if thing.is_live and c.user_is_sponsor:
        <button class="terminate" onclick="terminate_campaign(${jquery_row})">
          ${_("terminate")}
        </button>
      %endif
    %endif
  </td>
</tr>
