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

<%def name="other_awards(heading, awards)">
  <br class="clear" />
  <h1>${heading}</h1>
  %for award in awards:
    <div class="award-square mini">
      <a href="/wiki/awards/#${award.codename}">
        <img src="${award.imgurl % 70}" />
        <br/>
        <span class="award-name mini">${award.title}</span>
      </a>
    </div>
  %endfor
</%def>

<div class="award-square-container">
  %if thing.manuals:
    <h1>${_("Ongoing awards, and their most recent winners:")}</h1>
  %else:
    <h1>&nbsp;</h1>
  %endif

  %for award, winner, trophy in thing.regular_winners:

    <div class="award-square">
      <a href="/wiki/awards/#${award.codename}">
        <img src="${award.imgurl % 70}" />
        <span class="award-name">${award.title}</span>
      </a>

      <div class="winner-info">
        %if hasattr(trophy, "description"):
          ${_("won by")}
        %else:
          <br/>
          ${_("recently won by")}
        %endif
        <span class="winner-name">
          &#32;
          <a href="/user/${winner}">${winner}</a>
        </span>

        <br/>

        %if hasattr(trophy, "description") and not trophy.description.startswith("20"):
          (
          %if hasattr(trophy, "url"):
           <a href="${trophy.url}">
             ${trophy.description}
           </a>
          %else:
             ${trophy.description}
          %endif
          )
        %else:
          %if hasattr(trophy, "description"):
            on
            ${trophy.description}
          %endif

          %if hasattr(trophy, "url"):
           &#32;
           <a href="${trophy.url}">
             ${_("for this")}
           </a>
          %endif
        %endif
      </div>

    </div>
  %endfor

  %if thing.manuals:
    ${other_awards(_("Special awards:"), thing.manuals)}
  %endif

  %if c.user_is_admin and thing.invisibles:
    ${other_awards("Invisible awards:", thing.invisibles)}
  %endif

</div>
