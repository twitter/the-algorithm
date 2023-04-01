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
from r2.lib.strings import Score
%>

<%def name="gold_dropdown(what, chosen_months, somethings=None, append_or_somethings=None)">
  <%
    if not somethings:
      somethings = what
  %>
  <%!
    month_options = (1, 3)
    year_options = (1, 2, 3)
  %>
  <select id=${what} name=${what} class="gold-dropdown">
    %for i in month_options:
       <option value="${i}" ${"selected" if chosen_months == i else ""}>
         ${Score.somethings(i, somethings)}: ${g.gold_month_price * i}
         ${" or %s" % Score.somethings(i, append_or_somethings) if append_or_somethings else ""}
       </option>
    %endfor
    %for i in year_options:
       <option value="${i * 12}" ${"selected" if (chosen_months == (i * 12) or (not chosen_months and i == 1)) else ""}>
         ${Score.somethings(i * 12, somethings)} &#32; (special price!): ${g.gold_year_price * i}
         ${" or %s" % Score.somethings(i * 12, append_or_somethings) if append_or_somethings else ""}
       </option>
    %endfor
  </select>
</%def>
