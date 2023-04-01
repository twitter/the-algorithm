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

<h2>${_("select flair")}</h2>
%if thing.choices:
  <div class="flairoptionpane">
    <ul>
      %for choice in thing.choices:
        <%
          li_class = 'flairsample-%s' % thing.position
          if choice.flair_text_editable:
              li_class += ' texteditable'
          if choice.flair_template_id == thing.matching_template:
              li_class += ' selected'
        %>
        <li class="${li_class}" id="${choice.flair_template_id}">
          ${choice}
        </li>
      %endfor
    </ul>
  </div>
  <form action="/post/selectflair" method="post">
    <div class="flairselection">
      <div class="flairremove">
        (<a href="javascript://void(0)">${_('remove flair')}</a>)
      </div>
    </div>
    <input type="hidden" name="flair_template_id">
    <div class="customizer">
      <input type="text" size="16" maxlength="64" name="text">
    </div>
    <button type="submit">${_('save')}</button>
    <span class="status"></span>
  </form>
%else:
  <div class="error">${_("flair selection unavailable")}</div>
%endif
