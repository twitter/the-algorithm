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
    import time
    import babel.dates
    from r2.lib.template_helpers import js_timestamp, format_number
%>

<%
    month_names = babel.dates.get_month_names(locale=c.locale)
%>

<table id="${thing.id}" class="timeseries ${thing.classes}" data-interval="${thing.interval}">
<caption>${thing.title}</caption>
<thead>
<tr>
  <th scope="col">${_("date")}</th>
  % for col in thing.columns:
  % if "color" in col:
  <th scope="col" title="${col['title']}" data-color="${col['color']}">${col['shortname']}</th>
  % else:
  <th>${col['shortname']}</th>
  % endif
  % endfor
</tr>
</thead>
<tbody>
% for date, data in thing.rows:
<tr
  % if thing.interval == "day" and date.weekday() in (5, 6):
  class="dow-${date.weekday()}"
  % endif
  >
  <th data-value="${js_timestamp(date)}" scope="row">
      % if thing.make_period_link:
      <a href="${thing.make_period_link(thing.interval, date)}">
      % endif
      % if thing.interval == "hour":
      ${babel.dates.format_datetime(date, format="short", locale=c.locale)}
      % elif thing.interval == "day":
      ${babel.dates.format_date(date, format="short", locale=c.locale)}
      % else:
      ${month_names[date.month]}
      % endif
      % if thing.make_period_link:
      </a>
      % endif
  </th>
  % for datum in data:
  % if date < thing.latest_available_data:
  <td data-value="${datum}">${format_number(datum)}</td>
  % else:
  <td data-value="-1">${_("unavailable")}</td>
  % endif
  % endfor
</tr>
% endfor
</tbody>
</table>
