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
  from r2.lib.filters import SC_OFF, SC_ON
  from r2.lib.template_helpers import js_timestamp, format_number
%>

<div class="raisedbox traffic-form">
<p>${_("Enter one subreddit per line. Traffic numbers shown are for the last full month.")}</p>

<form method="GET" action="/traffic/subreddits/report">
  ${unsafe(SC_OFF)}<textarea name="subreddits" cols="30" rows="20">${thing.textarea}</textarea>${unsafe(SC_ON)}
  <input type="submit" value="${_('make report')}">
</form>
</div>

% if thing.report or thing.invalid_srs:
<table class="traffic-table">
  <caption>${_("subreddit traffic")} <span class="normal"><a href="${thing.csv_url}">(download as .csv)</a></span></caption>
  <thead>
    <tr>
      <th scope="col">${_("subreddit")}</th>
      <th scope="col">${_("uniques")}</th>
      <th scope="col">${_("pageviews")}</th>
    </tr>
  </thead>
  <tbody>
    % for (name, url), data in thing.report:
    <tr>
    % if url:
      <th scope="row"><a href="${url}">${name}</a></th>
    % else:
      <th scope="row">${name}</th>
    % endif
      % for datum in data:
      <td>${format_number(datum)}</td>
      % endfor
    </tr>
    % endfor
    % for name in thing.invalid_srs:
    <tr>
      <th scope="row" class="error">${name}</th>
      <td colspan="2">${_("not found")}</td>
    </tr>
    % endfor
  </tbody>
</table>
% endif
