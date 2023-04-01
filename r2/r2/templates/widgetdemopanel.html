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
   from r2.lib.template_helpers import get_domain, _wsf
%>

<% 
   domain = get_domain(subreddit=False)
   sr_domain = get_domain(subreddit=True)
 %>

<script type="text/javascript">
function escapeHTML(text) {
  var div = document.createElement('div');
  var text = document.createTextNode(text);
  div.appendChild(text);
  return div.innerHTML;
}

function getrval(r) {
    for (var i=0; i < r.length; i++) {
        if (r[i].checked) return r[i].value;
    } 
}

function showPreview(val) {
  $("#previewbox").html(val)
}

function update() {
    f = document.forms.widget;
    which = getrval(f.which);
    if (which == "all") {
        url = "${g.default_scheme}://${sr_domain}/" + f.what.value + "/.embed?limit=" +
                      f.num.value + "&t=" + f.when.value;
        if(f.what.value == "new") {
           url += "&sort=new";
        }
    } else if (which == "one") {
        if (!f.who.value) return;
        url = "${g.default_scheme}://${domain}/user/"+f.who.value+"/"+
                      f.where2.value+".embed?limit=" + f.num.value + 
                      "&sort="+f.what.value;

    } else if (which == "two") {
        if(!f.domain.value) return;
         url = "${g.default_scheme}://${domain}/domain/" + f.domain.value + "/" +
                      f.what.value + "/.embed?limit=" 
                      + f.num.value  + "&t=" + f.when.value;
        if(f.what.value == "new") {
           url += "&sort=new";
        }
    } else {
        alert(which);
    }
    $("#preview").css("width", "");
    if(f.expanded.checked) {
      url += "&expanded=1";
    }
    if(f.nostyle.checked) {
      url += "&style=off";
      $("#css-options").hide();
    }
    else {
      $("#css-options").show();
      if(f.border.checked && f.bord_color.value) {
        url += "&bordercolor=" + f.bord_color.value;
      }
      if(f.background.checked && f.bg_color.value) {
        url += "&bgcolor=" + f.bg_color.value;
      }
      if(f.twocol.checked) {
        url += "&twocolumn=true";
        $("#preview").css("width", "550px");
      }
    }

    script = '<script src="' + 
                      escapeHTML(url).replace(/&amp;/g, '&') + 
                      '" type="text/javascript"><'+'/script>';
    $("#codebox").html(escapeHTML(script));
    $.getScript(url+'&callback=showPreview');
                      
    }
</script>

<div class="instructions">
  
  <div id="preview">
    <span>preview</span>
    <div id="previewbox">
      <script src="${g.default_scheme}://${sr_domain}/.embed?limit=5" type="text/javascript"></script>
    </div>
  </div>

  <h1>${_("get live %(site)s headlines on your site") % dict(site=c.site.name)}</h1>
  
  <p>${_("just cut and paste the generated code into your site and your specified %(site)s feed will be displayed and updated as new stories bubble up") % dict(site=c.site.name)}</p>

  <h2>${_("which links do you want to display?")}</h2>
  
  <form name="widget" action="" onsubmit="update(); return false" id="widget"
        class="pretty-form">
    <%def name="when()" buffered="True">
      <select name="when" onchange="update()" onfocus="update()">
        <option value="hour">${_("this hour")}</option>
        <option value="day">${_("today")}</option>
        <option value="week">${_("this week")}</option>
        <option value="month">${_("this month")}</option>
        <option value="all" selected="selected">${_("all-time")}</option>
      </select>
    </%def>
    <%def name="where2()" buffered="True">
      <select name="where2" onchange="update()"
              onfocus="this.parentNode.firstChild.checked='checked'">
        <option value="submitted">${_("submitted by")}</option>
        <option value="saved">${_("saved by")}</option>
        <option value="liked">${_("upvoted by")}</option>
        <option value="disliked">${_("downvoted by")}</option>
      </select> 
    </%def>
    <%def name="text_input(name)" buffered="True">
      <input type="text" name="${name}" value="" 
             onfocus="this.parentNode.firstChild.checked='checked'"
             onchange="update()" onblur="update()" />
    </%def>
   <table class="widget-preview preftable">
     <tr>
       <th>
         ${_("listing options")}
       </th>
       <td class="prefright">
         <p>
           <input type="radio" name="which" value="all" checked="checked" 
                  onclick="update()" /> 
           ${_("links from %(domain)s") % dict(domain = get_domain())}
         </p>
         <p>
          <input type="radio" name="which" value="one" onclick="update()" /> 
           ${_wsf("links %(submitted_by)s the user %(who)s", submitted_by=unsafe(where2()), who=unsafe(text_input("who")))}
         </p>
         <p>
           <input type="radio" name="which" value="two" onclick="update()" /> 
          ${_wsf("links from the domain %(domain)s", domain=unsafe(text_input("domain")))}
         </p>
       </td>
     </tr>
     <tr>
       <th>
         ${_("sorting options")}
       </th>
       <td class="prefright">
         <p>
           <%def name="what()" buffered="True">
           <select name="what" onchange="update()">
             <option value="hot" selected="selected">${_("hottest")}</option>
             <option value="new">${_("newest")}</option>
             <option value="top">${_("top")}</option>
           </select>
           </%def>
           ${_wsf("sort links by %(what)s", what=unsafe(what()))}
         </p>
         <p>
           ${_wsf("date range includes %(when)s", when=unsafe(when()))}
         </p>
         <p>
           ${_("number of links to show")}:
           <select name="num" onchange="update()">
             <option value="5" selected="selected">5</option>
             <option value="10">10</option>
             <option value="20">20</option>
           </select>
         </p>
       </td>
     </tr>
     <tr>
       <th>
         ${_("display options")}
       </th>
       <td class="prefright">
         <p>
           <input name="nostyle" id="nostyle" type="checkbox"
                  onchange="update()"/>
           <label for="nostyle">
             ${_("remove css styling")}  
             &#32;<span class="little gray">
               ${_("(the widget will inherit all styles from the page)")}
             </span>
           </label>
         </p>
         <p>
           <input name="expanded" id="expanded" type="checkbox"
                  onchange="update()"/>
           <label for="expanded">
             ${_("enable in-widget voting")}  
             &#32;<span class="little gray">
               ${_("(will slow down the rendering)")}
             </span>
           </label>
         </p>
         <div id="css-options">
         <p>
           <input name="twocol" id="twocol" type="checkbox"
                  onchange="update()"/>
           <label for="twocol">
             ${_("two columns")}
           </label>
         </p>
         <p>
           <input name="background" id="background" type="checkbox"
                  onchange="update()"/>
           <label for="background">
             ${_("background color")}
           </label>
           &nbsp;#${unsafe(text_input("bg_color"))}
           &#32;<span class="little gray">
             ${_("(e.g., FF0000 = red)")}
           </span>
         </p>
         <p>
           <input name="border" id="border" type="checkbox" 
                  onchange="update()"/>
           <label for="border"> 
             ${_("border color")}
           </label>
           &nbsp;#${unsafe(text_input("bord_color"))}
         </p>
         </div>
       </td>
     </tr>
  </table>
  </form>

  <h2>${_("the code")}</h2>

  <p>${_("add this into your HTML where you want the %(site)s links displayed") %dict(site=c.site.name)}</p>

  <p>
    <textarea rows="5" cols="50" id="codebox">
      &lt;script src="${g.default_scheme}://${domain}/.embed?limit=5" type="text/javascript">&lt;/script>
    </textarea>
  </p>
</div>
