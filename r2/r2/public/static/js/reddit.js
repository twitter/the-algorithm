function open_menu(menu) {
    $(menu).siblings(".drop-choices").not(".inuse")
        .css("top", menu.offsetHeight + 'px')
                .each(function(){
                        $(this).css("left", $(menu).position().left + "px")
                            .css("top", ($(menu).height()+
                                         $(menu).position().top) + "px");
                    })
        .addClass("active inuse");
};

function close_menu(item) {
    $(item).closest('.drop-choices')
        .removeClass('active inuse');
}

function close_menus(event) {
    $(".drop-choices.inuse").not(".active")
        .removeClass("inuse");
    $(".drop-choices.active").removeClass("active").trigger("close_menu")

    // Clear any flairselectors that may have been opened.
    $(".flairselector").empty();

    /* hide the search expando if the user clicks elsewhere on the page */ 
    if ($(event.target).closest("#search").length == 0) {
        $("#moresearchinfo").slideUp();

        if ($("#searchexpando").length == 1) {
            $("#searchexpando").slideUp(function() {
                $("#search_showmore").parent().show();
            });
        } else {
            $("#search_showmore").parent().show();
        }
    }
};

function select_tab_menu(tab_link, tab_name) {
    var target = "tabbedpane-" + tab_name;
    var menu = $(tab_link).parent().parent().parent();
    menu.find(".tabmenu li").removeClass("selected");
    $(tab_link).parent().addClass("selected");
    menu.find(".tabbedpane").each(function() {
        this.style.display = (this.id == target) ? "block" : "none";
      });
}

function post_user(form, where) {
  var user = $(form).find('input[name="user"]').val();

  if (user == null) {
    return post_form (form, where);
  } else {
    return post_form (form, where + '/' + user);
  }
}

function post_form(form, where, statusfunc, nametransformfunc, block) {
    try {
        if (form.disabled) {
            return false;
        }
        if(statusfunc == null)
            statusfunc = function(x) { 
                return r.config.status_msg.submitting; 
            };
        /* set the submitted state */
        $(form).find(".error").not(".status").hide();
        $(form).find(".status").html(statusfunc(form)).show();
        return simple_post_form(form, where, {}, block);
    } catch(e) {
        return false;
    }
};

function get_form_fields(form, fields, filter_func) {
    fields = fields || {};
    if (!filter_func)
        filter_func = function(x) { return true; };
    /* consolidate the form's inputs for submission */
    $(form).find("select, input, textarea").not(".gray, :disabled").each(function() {
            var $el = $(this),
                type = $el.attr("type");
            if (!filter_func(this)) {
                return;
            }
            if ($el.data('send-checked')) {
                fields[$el.attr("name")] = $el.is(':checked');
            } else if ((type != "radio" && type != "checkbox") || $el.is(":checked")) {
                fields[$el.attr("name")] = $el.val();
            }
        });
    if (fields.id == null) {
        fields.id = $(form).attr("id") ? ("#" + $(form).attr("id")) : "";
    }
    return fields;
};

function form_error(form) {
    return function(req) {
        var msg
        if (req == 'ratelimit') {
            msg = r._('please wait a few seconds and try again.')
        } else {
            msg = r._('an error occurred (status: %(status)s)').format({status: req.status})
        }
        $(form).find('.status').text(msg)
    }
}

function simple_post_form(form, where, fields, block, callback) {
    $.request(where, get_form_fields(form, fields), callback, block, 
              "json", false, form_error(form));
    return false;
};

function post_pseudo_form(form, where, block) {
    var filter_func = function(x) {
        var parent = $(x).parents("form:first");
        return (parent.length == 0 || parent.get(0) == $(form).get(0))
    };
    $(form).find(".error").not(".status").hide();
    $(form).find(".status").html(r.config.status_msg.submitting).show();
    $.request(where, get_form_fields(form, {}, filter_func), null, block,
              "json", false, form_error(form));
    return false;
}

function post_multipart_form(form, where) {
    $(form).find(".error").not(".status").hide();
    $(form).find(".status").html(r.config.status_msg.submitting).show();
    return true;
}

function showlang() {
    var content = $('#lang-popup').prop('innerHTML');
    var popup = new r.ui.Popup({
        className: 'lang-modal',
        content: content,
    });

    popup.show();

    return false;
};

/* table handling */

function deleteRow(elem) {
    $(elem).delete_table_row();
};



/* general things */

function change_state(elem, op, callback, keep, post_callback) {
    var form = $(elem).parents("form").first();
    /* look to see if the form has an id specified */
    var id = form.find('input[name="id"]');
    if (id.length) 
        id = id.val();
    else /* fallback on the parent thing */
        id = $(elem).thing_id();

    simple_post_form(form, op, {id: id}, undefined, post_callback);
    /* call the callback first before we mangle anything */
    if (callback) {
        callback(form.length ? form : elem, op);
    }
    if(!$.defined(keep)) {
        form.html(form.find('[name="executed"]').val());
    }
    return false;
};

function unread_thing(elem) {
    var t = $(elem);
    if (!t.hasClass("thing")) {
        t = t.thing();
    }

    $(t).addClass("new unread");
}

function read_thing(elem) {
    var t = $(elem);
    if (!t.hasClass("thing")) {
        t = t.thing();
    }
    if($(t).hasClass("new")) {
        $(t).removeClass("new");
    } else {
        $(t).removeClass("unread");
    }

    $.request("read_message", {"id": $(t).thing_id()});
}

function click_thing(elem) {
    var t = $(elem);
    if (!t.hasClass("thing")) {
        t = t.thing();
    }
    if (t.hasClass("message") && t.hasClass("recipient")) {
        if (t.hasClass("unread")) {
            t.removeClass("unread");
        } else if ( t.hasClass("new")) {
            read_thing(elem);
        }
    }
}

function hide_thing(elem) {
    if ($('body').hasClass('comments-page')) {
        return;
    }

    var $thing = $(elem).thing();

    if ($thing.is('.comment') && $thing.has('.child:not(:empty)').length) {
        var deleted = '[' + _.escape(r._('deleted')) + ']';
        var $entry = $thing.addClass('deleted').find('.entry:first');

        $entry.find('.usertext')
            .addClass('grayed')
            .find('.md')
                .html('<p>' + deleted + '</p>');

        $entry.find('.author')
            .replaceWith('<em>' + deleted + '</em>')  ;  
        
        $entry.find('.userattrs, .score, .buttons')
            .remove();
    } else {
        $thing.fadeOut(function() {
            $(this).toggleClass('hidden');
            var thing_id = $(this).thing_id();
            $(document).trigger('hide_thing_' + thing_id);
        });
    }
}

function toggle(elem, callback, cancelback) {
    if (r.access.isLinkRestricted(elem)) {
        return false;
    }

    r.analytics.breadcrumbs.storeLastClick(elem)

    var self = $(elem).parent().addBack().filter(".option");
    var sibling = self.removeClass("active")
        .siblings().addClass("active").get(0); 

    /*
    var self = $(elem).siblings().addBack();
    var sibling = self.filter(":hidden").debug();
    self = self.filter(":visible").removeClass("active");
    sibling = sibling.addClass("active").get(0);
    */

    if(cancelback && !sibling.onclick) {
        sibling.onclick = function() {
            return toggle(sibling, cancelback, callback);
        }
    }
    if(callback) callback(elem);
    return false;
};

function cancelToggleForm(elem, form_class, button_class, on_hide) {
    /* if there is a toggle button that triggered this, toggle it if
     * it is not already active.*/
    if(button_class && $(elem).filter("button").length) {
        var sel = $(elem).thing().find(button_class)
            .children(":visible").filter(":first");
        toggle(sel);
    }
    $(elem).thing().find(form_class)
        .each(function() {
                if(on_hide) on_hide($(this));
                $(this).hide().remove();
            });
    return false;
};


/* links */

function linkstatus(form) {
    return r.config.status_msg.submitting;
};


function subscribe(reddit_name) {
    return function() { 
        if (r.config.logged) {
            if (r.config.cur_site == reddit_name) {
                $('body').addClass('subscriber');
            }
            $.things(reddit_name).find(".entry").addClass("likes");
            $.request("subscribe", {sr: reddit_name, action: "sub"});
            r.analytics.fireUITrackingPixel("sub", reddit_name, {"has_subd": r.config.has_subscribed})
        }
    };
};

function unsubscribe(reddit_name) {
    return function() { 
        if (r.config.logged) {
            if (r.config.cur_site == reddit_name) {
                $('body').removeClass('subscriber');
            }
            $.things(reddit_name).find(".entry").removeClass("likes");
            $.request("subscribe", {sr: reddit_name, action: "unsub"});
            r.analytics.fireUITrackingPixel("unsub", reddit_name)
        }
    };
};

function quarantine_optout(subreddit_name) {
    return function() {
        if (r.config.logged) {
            $.request("quarantine_optout", {sr: subreddit_name});
            $.redirect("/");
        }
    };
};

function friend(user_name, container_name, type) {
    return function() {
        if (r.config.logged) {
            encoded = encodeURIComponent(document.referrer);
            $.request("friend?note=" + encoded,
                      {name: user_name, container: container_name, type: type});
        }
    }
};

function unfriend(user_name, container_name, type) {
    return function() {
        $.request("unfriend",
                  {name: user_name, container: container_name, type: type});
    }
};

function reject_promo(elem) {
    $(elem).thing().find(".rejection-form").show().find("textare").focus();
}

function cancel_reject_promo(elem) {  
    $(elem).thing().find(".rejection-form").hide();
}

function complete_reject_promo(elem) {
    var $el = $(elem);

    $el.thing().removeClass("accepted").addClass("rejected")
        .find(".reject_promo").remove();

    if ($el.data('hide-after-seen')) {
        hide_thing(elem);
    }
}

/* Comment generation */
function helpon(elem) {
    $(elem).parents(".usertext-edit:first").children(".markhelp:first").show();
};
function helpoff(elem) {
    $(elem).parents(".usertext-edit:first").children(".markhelp:first").hide();
};

function show_all_messages(elem) {
    var $rootMessage = $(elem).parents(".message");
    var $childMessages = $rootMessage.find(".message");
    var $messages = $rootMessage.add($childMessages);
    var ids = [];

    _.each($messages, function(message) {
      var $message = $(message);
      var $expander = $message.find(".expand:first");
      var isCollapsed = $message.hasClass("collapsed");

      if (isCollapsed) {
        $message.toggleClass("collapsed noncollapsed");
        $expander.text("[-]");
        ids.push($message.thing_id());
      }
    });

    if (ids.length) {
        $.request("uncollapse_message", {"id": ids.join(',')});
    }
    return false;
}

function hide_all_messages(elem) {
    var $rootMessage = $(elem).parents(".message");
    var $childMessages = $rootMessage.find(".message");
    var $messages = $rootMessage.add($childMessages);
    var ids = [];

    _.each($messages, function(message) {
      var $message = $(message);
      var $expander = $message.find(".expand:first");
      var isCollapsed = $message.hasClass("collapsed");

      if (!isCollapsed) {
        $message.toggleClass("collapsed noncollapsed");
        $expander.text("[+]");
        ids.push($message.thing_id());
      }
    });

    if (ids.length) {
        $.request("collapse_message", {"id": ids.join(',')});
    }
    return false;
}

function togglecomment(elem) {
  var comment = $(elem).thing()
  var expander = comment.find(".expand:first")
  var isCollapsed = comment.hasClass("collapsed")
  comment.toggleClass("collapsed noncollapsed")

  if (!isCollapsed) {
    expander.text("[+]")
  } else {
    expander.text("[–]")
  }
}

function toggleSrQuarantine(elem) {
  var $toolbox = $(".quarantine-tool");
  var $expander = $toolbox.find(".expand:first");
  var isCollapsed = $toolbox.hasClass("collapsed");
  $toolbox.toggleClass("collapsed noncollapsed");

  if (!isCollapsed) {
    $expander.text('[+]');
  } else {
    $expander.text('[–]');
  }
}

function togglemessage(elem) {
  var message = $(elem).thing()
  var expander = message.find(".expand:first")
  var isCollapsed = message.hasClass("collapsed")
  message.toggleClass("collapsed noncollapsed")

  if (!isCollapsed) {
    expander.text("[+]")
    $.request("collapse_message", { "id": $(message).thing_id() })
  } else {
    expander.text("[–]")
    $.request("uncollapse_message", { "id": $(message).thing_id() })
  }
}

function morechildren(form, link_id, sort, children, depth) {
    $(form).html(r.config.status_msg.loading)
        .css("color", "red");
    var id = $(form).parents(".thing.morechildren:first").thing_id();
    var child_params = {
        link_id: link_id,
        sort: sort,
        children: children,
        depth: depth,
        id: id,
    };
    $.request('morechildren', child_params, undefined, undefined,
              undefined, true);
    return false;
}

function moremessages(elem) {
    $(elem).html(r.config.status_msg.loading).css("color", "red");
    $.request("moremessages", {parent_id: $(elem).thing_id()});
    return false;
}

/* stylesheet and CSS stuff */

function add_thing_to_cookie(thing, cookie_name) {
    var id = $(thing).thing_id();

    if(id && id.length) {
        return add_thing_id_to_cookie(id, cookie_name);
    }
}

function add_thing_id_to_cookie(id, cookie_name) {
    var cookie = $.cookie_read(cookie_name);
    if(!cookie.data) {
        cookie.data = "";
    }

    /* avoid adding consecutive duplicates */
    if(cookie.data.substring(0, id.length) == id) {
        return;
    }

    cookie.data = id + ',' + cookie.data;

    var fullnames = cookie.data.split(',');
    if(fullnames.length > 5) {
        fullnames = $.uniq(fullnames, 5);
        cookie.data = fullnames.join(',');
    }

    $.cookie_write(cookie);
};

function clicked_items() {
    var cookie = $.cookie_read('recentclicks2');
    if(cookie && cookie.data) {
        var fullnames = cookie.data.split(",");
        /* don't return empty ones */
        for(var i=fullnames.length-1; i >= 0; i--) {
            if(!fullnames[i] || !fullnames[i].length) {
                fullnames.splice(i,1);
            }
        }
        return fullnames;
    } else {
        return [];
    }
}

function clear_clicked_items() {
    var cookie = $.cookie_read('recentclicks2');
    cookie.data = '';
    $.cookie_write(cookie);
    $('.gadget').remove();
}

function updateEventHandlers(thing) {
    /* this function serves as a default callback every time a new
     * Thing is inserted into the DOM.  It serves to rewrite a Thing's
     * event handlers depending on context (as in the case of an
     * organic listing) and to set the click behavior on links. */
    thing = $(thing);
    var listing = thing.parent();

    /* click on a title.. */
    $(thing).filter(".link")
        .find("a.title, a.comments").mousedown(function() {
            /* set the click cookie. */
            add_thing_to_cookie(this, "recentclicks2");
        });

    if (listing.filter(".organic-listing").length) {
        thing.find(".hide-button a, .del-button a.yes, .report-button a.yes")
            .each(function() { $(this).get(0).onclick = null });
        thing.find(".hide-button a")
           .click(function() {
                   var a = $(this).get(0);
                   change_state(a, 'hide', 
                                function() { r.spotlight.next() });
                });
        thing.find(".del-button a.yes")
            .click(function() {
                    var a = $(this).get(0);
                    change_state(a, 'del',
                                 function() { r.spotlight.next() });
                });
        thing.find(".report-button a.yes")
            .click(function() {
                    var a = $(this).get(0);
                    change_state(a, 'report', 
                                 function() { r.spotlight.next() });
                    }); 
    }
};

function last_click() {
    var fullname = r.analytics.breadcrumbs.lastClickFullname()
    if (fullname && $('body').hasClass('listing-page')) {
        $('.last-clicked').removeClass('last-clicked')
        $('.id-' + fullname).last().addClass('last-clicked')
    }
}

function login(elem) {
    return post_user(this, "login");
};

function register(elem) {
    return post_user(this, "register");
};

/***submit stuff***/
function fetch_title() {
    var url_field = $("#url-field");
    var error = url_field.find(".NO_URL");
    var status = url_field.find(".title-status");
    var url = $("#url").val();
    if (url) {
        if ($('form#newlink textarea[name="title"]').val() &&
            !confirm("This will replace your existing title, proceed?")) {
                return
        }
        status.show().text(r.config.status_msg.loading);
        error.hide();
        $.request("fetch_title", {url: url});
    }
    else {
        status.hide();
        error.show().text("a url is required");
    }
}



function highlight_reddit(item) {
    $("#sr-drop-down").children('.sr-selected').removeClass('sr-selected');
    if (item) {
        $(item).addClass('sr-selected');
    }
}

function update_dropdown(sr_names) {
    var drop_down = $("#sr-drop-down");
    if (!sr_names.length) {
        drop_down.hide();
        return;
    }

    var first_row = drop_down.children(":first");
    first_row.removeClass('sr-selected');
    drop_down.children().remove();

    $.each(sr_names, function(i) {
            if (i > 10) return;
            var name = sr_names[i];
            var new_row = first_row.clone();
            new_row.text(name);
            drop_down.append(new_row);
        });


    var height = $("#sr-autocomplete").outerHeight();
    drop_down.css('top', height);
    drop_down.show();
}

/*** tabbed pane stuff ***/
function select_form_tab(elem, to_show, to_hide) {
    //change the menu    
    var link_parent = $(elem).parent();
    link_parent
        .addClass('selected')
        .siblings().removeClass('selected');
    
    //swap content and enable/disable form elements
    var content = link_parent.parent('ul').next('.formtabs-content');
    content.find(to_show)
        .show()
        .find(":input").removeAttr("disabled").end();
    content.find(to_hide)
        .hide()
        .find(":input").attr("disabled", true);
}

/******* editting comments *********/
function show_edit_usertext(form) {
    var edit = form.find(".usertext-edit");
    var body = form.find(".usertext-body");
    var textarea = edit.find('div > textarea');

    //max of the height of the content or the min values from the css.
    var body_width = Math.max(body.children(".md").width(), 500);
    var body_height = Math.max(body.children(".md").height(), 100);

    //we need to show the textbox first so it has dimensions
    body.hide();
    edit.show();

    //restore original (?) css width/height. I can't explain why, but
    //this is important.
    textarea.css('width', '');
    textarea.css('height', '');

    //if there would be scroll bars, expand the textarea to the size
    //of the rendered body text
    if (textarea.get(0).scrollHeight > textarea.height()) {
        var new_width = Math.max(body_width - 5, textarea.width());
        textarea.width(new_width);
        edit.width(new_width);

        var new_height = Math.max(body_height, textarea.height());
        textarea.height(new_height);
    }

    form
        .find(".cancel, .save").show().end()
        .find(".help-toggle").show().end();

    textarea.focus();
}

function hide_edit_usertext(form) {
    form
        .find(".usertext-edit").hide().end()
        .find(".usertext-body").show().end()
        .find(".cancel, .save").hide().end()
        .find(".help-toggle").hide().end()
        .find(".markhelp").hide().end()
}

function comment_reply_for_elem(elem) {
    elem = $(elem);
    var thing = elem.thing();
    var thing_id = elem.thing_id();
    //try to find a previous form
    var form = thing.find(".child .usertext:first");
    if (!form.length || form.parent().thing_id() != thing.thing_id()) {
        form = $(".usertext.cloneable:first").clone(true);
        elem.new_thing_child(form);
        form.prop("thing_id").value = thing_id;
        form.attr("id", "commentreply_" + thing_id);
        form.find(".error").hide();
    }
    return form;
}

function edit_usertext(elem) {
    var t = $(elem).thing();
    t.find(".edit-usertext:first").parent("li").addBack().hide();
    show_edit_usertext(t.find(".usertext:first"));
}

function cancel_usertext(elem) {
    $(window).off('beforeunload');
    var t = $(elem);
    t.thing().find(".edit-usertext:first").parent("li").addBack().show(); 
    hide_edit_usertext(t.closest(".usertext"));
}

function reply(elem) {
    if (r.access.isLinkRestricted(elem)) {
        return;
    }

    var form = comment_reply_for_elem(elem);

    // quote any selected text and put it in the textarea if it's empty
    // not compatible with IE < 9
    var textarea = form.find("textarea")
    if (window.getSelection && textarea.val().length == 0) {
        // check if the selection is all inside one markdown element
        var sel = window.getSelection()
        var focusParentDiv = $(sel.focusNode).parents(".md").first()
        var anchorParentDiv = $(sel.anchorNode).parents(".md").first()
        if (focusParentDiv.length && focusParentDiv.is(anchorParentDiv)) {
            var selectedText = sel.toString()
            if (selectedText.length > 0) {
                selectedText = selectedText.replace(/^/gm, "> ")
                textarea.val(selectedText+"\n\n")
                textarea.scrollTop(textarea.scrollHeight)
            }
        }
    }

    //show the right buttons
    show_edit_usertext(form);
    //re-show the whole form if required
    form.show();
    //update the cancel button to call the toggle button's click
    form.find(".cancel").get(0).onclick = function() {
      $(window).off('beforeunload');
      form.hide();
    };
    $(e.target).thing().find(".showreplies:visible").click();
    return false;
}

function toggle_distinguish_span(elem) {
  var form = $(elem).parents("form")[0];
  $(form).children().toggle();
}

function set_distinguish(elem, value) {
  if (value === "yes_sticky") {
    $(elem).parents('form').first().find('input[name="sticky"]').val('true');
    value = 'yes';
  }

  change_state(elem, "distinguish/" + value);
  $(elem).children().toggle();
}

function toggle_clear_suggested_sort(elem) {
  var form = $(elem).parents("form")[0];
  $(form).children().toggle();
}

function set_suggested_sort(elem, value) {
  $(elem).parents('form').first().find('input[name="sort"]').val(value);
  change_state(elem, "set_suggested_sort");
  $(elem).children().toggle();
}


function populate_click_gadget() {
    /* if we can find the click-gadget, populate it */
    if($('.click-gadget').length) {
        var clicked = clicked_items();

        if(clicked && clicked.length) {
            clicked = $.uniq(clicked, 5);
            clicked.sort();

            $.request('gadget/click/' + clicked.join(','), undefined, undefined,
                      undefined, "json", true);
        }
    }
}

function fetch_parent(elem, parent_permalink, parent_id) {
    var thing = $(elem).thing();
    var parent = '';

    $(elem).css("color", "red").html(r.config.status_msg.loading);

    $.getJSON(parent_permalink, function(response) {
      $.each(response, function() {
        if (this && this.data.children) {
          $.each(this.data.children, function() {
            if (this.data.name == parent_id) {
              parent = this.data.body_html;
            }
          });
        }
      });

      if (parent) {
        /* make a parent div for the contents of the fetch */
        thing.find(".md").first()
          .before('<div class="parent rounded">' + $.unsafe(parent) + '</div>');
      }

      /* remove the button */
      $(elem).parent("li").addBack().remove();
    });
    return false;
}

function big_mod_action(elem, dir) {
   if ( ! elem.hasClass("pressed")) {
      elem.addClass("pressed");

      var thing_id = elem.thing_id();

      d = {
         id: thing_id
      };

      elem.siblings(".status-msg").hide();
      if (dir == -1) {
        d.spam = false;
        $.request("remove", d, null, true);
        elem.siblings(".removed").show();
      } else if (dir == -2) {
        $.request("remove", d, null, true);
        elem.siblings(".spammed").show();
      } else if (dir == 1) {
        $.request("approve", d, null, true);
        elem.siblings(".approved").show();
      }
   }
   elem.siblings(".pretty-button").removeClass("pressed");
   return false;
}

function big_mod_toggle(el, press_action, unpress_action) {
    el.toggleClass('pressed')
    $.request(el.is('.pressed') ? press_action : unpress_action, {
        id: el.thing_id()
    }, null, true)
    return false
}

/* The ready method */
$(function() {
        $("body").click(close_menus);

        /* set function to be called on thing creation/replacement,
         * and call it on all things currently rendered in the
         * page. */
        $("body").set_thing_init(updateEventHandlers);

        /* Fall back to the old ".gray" system if placeholder isn't supported
         * by this browser */
        if (!('placeholder' in document.createElement('input'))) {
            $("textarea[placeholder], input[placeholder]")
                .addClass("gray")
                .each(function() {
                    var element = $(this);
                    var placeholder_text = element.attr('placeholder');
                    if (element.val() == "") {
                        element.val(placeholder_text);
                    }
                });
        }

        /* Set up gray inputs and textareas to clear on focus */
        $("textarea.gray, input.gray")
            .focus( function() {
                    $(this).attr("rows", 7)
                        .filter(".gray").removeClass("gray").val("")
                        });
        /* set cookies to be from this user if there is one */
        if (r.config.logged) {
            $.cookie_name_prefix(r.config.logged);
        }
        else {
            //populate_click_gadget();
        }
        /* set up the cookie domain */
        $.default_cookie_domain(r.config.cur_domain.split(':')[0]);

        // When forcing HTTPS, all cookies need the secure flag
        $.default_cookie_security(r.config.https_forced)
        
        /* visually mark the last-clicked entry */
        last_click();
        $(window).on('pageshow', function() {
            last_click()
        })

        /* search form help expando */
        /* TODO: use focusin and focusout in jQuery 1.4 */
        $('#search input[name="q"]').focus(function () {
            $("#searchexpando").slideDown();
        });

        // Store the user's choice for restrict_sr
        $('#search input[name="restrict_sr"]')
          .change(function() {
            store.safeSet('search.restrict_sr.checked', this.checked)
          });
        $('#searchexpando input[name="restrict_sr"]')
          .prop("checked", !!store.safeGet('search.restrict_sr.checked'));

        $("#search_showmore").click(function(event) {
            $("#search_showmore").parent().hide();
            $("#moresearchinfo").slideDown();
            event.preventDefault();
        });

        $("#moresearchinfo")
            .prepend('<a href="#" id="search_hidemore">[-]</a>')

        $("#search_hidemore").click(function(event) {
            $("#search_showmore").parent().show();
            $("#moresearchinfo").slideUp();
            event.preventDefault();
        });

        var query = $('#search input[name="q"]').val();
        $('.search-result-listing')
          .find('.search-title, .search-link, .search-subreddit-link, .search-result-body')
          .highlight(query);
        
        // add new search page links to the 'recently viewed' links...
        $(".search-result-link").find("a.search-title, a.thumbnail").mousedown(function() {
            var fullname = $(this).closest('[data-fullname]').data('fullname');
            if (fullname) {
                add_thing_id_to_cookie(fullname, "recentclicks2");
            }
        });

        /* Select shortlink text on click */
        $("#shortlink-text").click(function() {
            $(this).select();
        });

        $(".sr_style_toggle").change(function() {
          $('#sr_style_throbber')
            .html('<img src="' + r.utils.staticURL('throbber.gif') + '" />')
            .css("display", "inline-block");
          return post_form($(this), "set_sr_style_enabled");
        });

        $(".reddit-themes .theme").click(function() {
          $("div.theme.selected").removeClass("selected");
          $("input[name='enable_default_themes']").prop("checked", true);
          // if other is selected
          if ($(this).hasClass("select-custom-theme")) {
            $("#other_theme_selector").prop("checked", true);
          } else {
            $("input[name='theme_selector'][value='" + $(this).attr("id") + "']")
              .prop("checked", true);
          }
          $(this).addClass("selected");
        });

        /* ajax ynbutton */
        function toggleThis() { return toggle(this); }
        $("body")
            .delegate(".ajax-yn-button", "submit",
                      function() {
                          var op = $(this).find('input[name="_op"]').val();
                          post_form(this, op);
                          return false;
                      })
            .delegate(".ajax-yn-button .togglebutton", "click", toggleThis)
            .delegate(".ajax-yn-button .no", "click", toggleThis)
            .delegate(".ajax-yn-button .yes", "click",
                      function() { $(this).closest("form").submit(); })
            ;
    });
