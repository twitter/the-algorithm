/* The reddit extension for jquery.  This file is intended to store
 * "utils" type function declarations and to add functionality to "$"
 * or "jquery" lookups. See 
 *   http://docs.jquery.com/Plugins/Authoring 
 * for the plug-in spec.
*/

(function($) {

/* utility functions */

$.log = function(message) {
    if (window.console) {
        if (window.console.debug)
            window.console.debug(message);
        else if (window.console.log)
            window.console.log(message);
    }
    else
        alert(message);
};

$.debug = function(message) {
    if ($.with_default(r.config.debug, false)) {
        return $.log(message);
    }
}
$.fn.debug = function() { 
    $.debug($(this));
    return $(this);
}

$.redirect = function(dest) {
    window.location = dest;
};

$.fn.redirect = function(dest) {
    /* for forms which are "posting" by ajax leading to a redirect */
    $(this).filter("form").find(".status").show().html("redirecting...");
    var target = $(this).attr('target');
    if(target == "_top") {
      var w = window;
      while(w != w.parent) {
        w = w.parent;
      }
      w.location = dest;
    } else {
      $.redirect(dest);
    }
    /* this should never happen, but for the sake of internal consistency */
    return $(this)
}

$.refresh = function() {
    window.location.reload(true);
};

$.defined = function(value) {
    return (typeof(value) != "undefined");
};

$.with_default = function(value, alt) {
    return $.defined(value) ? value : alt;
};

$.websafe = function(text) {
    if(typeof(text) == "string") {
        text = text.replace(/&/g, "&amp;")
            .replace(/"/g, '&quot;') /* " */
            .replace(/>/g, "&gt;").replace(/</g, "&lt;")
    }
    return (text || "");
};

$.unsafe = function(text) {
    /* inverts websafe filtering of reddit app. */
    if(typeof(text) == "string") {
        text = text.replace(/&quot;/g, '"')
            .replace(/&gt;/g, ">").replace(/&lt;/g, "<")
            .replace(/&amp;/g, "&");
    }
    return (text || "");
};

$.uniq = function(list, max) {
    /* $.unique only works on arrays of DOM elements */
    var ret = [];
    var seen = {};
    var num = max ? max : list.length;
    for(var i = 0; i < list.length && ret.length < num; i++) {
        if(!seen[list[i]]) {
            seen[list[i]] = true;
            ret.push(list[i]);
        }
    }
    return ret;
};

/* upgrade show and hide to trigger onshow/onhide events when fired. */
(function(show, hide) {
    $.fn.show = function(speed, callback) {
        $(this).trigger("onshow");
        return show.call(this, speed, callback);
    }
    $.fn.hide = function(speed, callback) {
        $(this).trigger("onhide");
        return hide.call(this, speed, callback);
    }
})($.fn.show, $.fn.hide);

/* customized requests (formerly redditRequest) */

var _ajax_locks = {};
function acquire_ajax_lock(op) {
    if(_ajax_locks[op]) {
        return false;
    }
    _ajax_locks[op] = true;
    return true;
};

function release_ajax_lock(op) {
    delete _ajax_locks[op];
};

function handleResponse(action) {
    return function(res) {
        if(res.jquery) {
            var objs = {};
            objs[0] = jQuery;
            $.map(res.jquery, function(q) {
                    var old_i = q[0], new_i = q[1], op = q[2], args = q[3];
                    if (typeof(args) == "string") {
                      args = $.unsafe(args);
                    } else { // assume array
                      for(var i = 0; args.length && i < args.length; i++)
                        args[i] = $.unsafe(args[i]);
                    }
                    if (op == "call") 
                        objs[new_i] = objs[old_i].apply(objs[old_i]._obj, args);
                    else if (op == "attr") {
                        // remove beforeunload event handler if exists for redirects
                        if(args == 'redirect') {
                          $(window).off('beforeunload');
                        }
                        objs[new_i] = objs[old_i][args];
                        if(objs[new_i])
                            objs[new_i]._obj = objs[old_i];
                        else {
                            $.debug("unrecognized");
                        }
                    } else if (op == "refresh") {
                        $.refresh();
                    } else {
                        $.debug("unrecognized");
                    }
                });
        }
    };
};
$.handleResponse = handleResponse;

var api_loc = '/api/';
$.request = function(op, parameters, worker_in, block, type, 
                     get_only, errorhandler) {
    /* 
       Uniquitous reddit AJAX poster.  Automatically addes
       handleResponse(action) worker to deal with the API result.  The
       current subreddit (r.config.post_site) and the user's modhash
       (r.config.modhash) are also automatically sent across.
     */
    var action = op;
    var worker = worker_in;

    if (rate_limit(op)) {
        if (errorhandler) {
            errorhandler('ratelimit')
        }
        return
    }

    if (window != window.top) {
        return
    }

    /* we have a lock if we are not blocking or if we have gotten a lock */
    var have_lock = !$.with_default(block, false) || acquire_ajax_lock(action);

    parameters = $.with_default(parameters, {});
    worker_in  = $.with_default(worker_in, handleResponse(action));
    type  = $.with_default(type, "json");

    var form = $('form.warn-on-unload');

    if (typeof(worker_in) != 'function')
        worker_in  = handleResponse(action);
    var worker = function(res) {
        release_ajax_lock(action);

        /*
         * check if there exists a form that has the
         * warn-on-unload class. Remove the beforeunload event
         * listener if the form submission was successful
         * and we dont warn the user on succesful form submissions
         */
        if($(form).length && res.success) {
          $(window).off('beforeunload');
        }
        return worker_in(res);
    };
    /* do the same for the error handler, and make sure to release the lock*/
    errorhandler_in = $.with_default(errorhandler, function() { });
    errorhandler = function(r) {
        release_ajax_lock(action);
        return errorhandler_in(r);
    };


    get_only = $.with_default(get_only, false);

    /* set the subreddit name if there is one */
    if (r.config.post_site) 
        parameters.r = r.config.post_site;

    /* add the modhash if the user is logged in */
    if (r.config.logged) 
        parameters.uh = r.config.modhash;

    parameters.renderstyle = r.config.renderstyle;

    if(have_lock) {
        op = api_loc + op;
        /*if( document.location.host == r.config.ajax_domain ) 
            /* normal AJAX post */

        $.ajax({ type: (get_only) ? "GET" : "POST",
                    url: op, 
                    data: parameters, 
                    success: worker,
                    error: errorhandler,
                    dataType: type});
        /*else { /* cross domain it is... * /
            op = "http://" + r.config.ajax_domain + op + "?callback=?";
            $.getJSON(op, parameters, worker);
            } */
    }
};

var up_cls = "up";
var upmod_cls = "upmod";
var down_cls = "down";
var downmod_cls = "downmod";

rate_limit = (function() {
    var default_rate_limit = 333,  // default rate-limit duration (in ms)
        rate_limits = {  // rate limit per-action (in ms, 0 = don't rate limit)
            "vote": 333,
            "comment": 333,
            "ignore": 0,
            "ban": 0,
            "unban": 0,
            "assignad": 0
        },
        last_dates = {}

    // paranoia: copy global functions used to avoid tampering.
    var _Date = Date

    return function rate_limit(action) {
        var now = new _Date(),
            allowed_interval = action in rate_limits ?
                               rate_limits[action] : default_rate_limit,
            last_date = last_dates[action],
            rate_limited = last_date && (now - last_date) < allowed_interval

        last_dates[action] = now
        return rate_limited
    };
})()

$.fn.removeLinkFlairClass = function () {
  $(this)
    .removeClass("linkflair")
    .attr('class', function(i, c) {
      return (c.replace(/(^|\s)linkflair\S+/g, ''));
    });
};

$.fn.updateThing = function(update) {
    var $thing = $(this);
    var $entry = $thing.children('.entry');

    if ('enemy' in update) {
        // TODO: this will hide comments of enemies along with all of their
        // children.  The better alternative would be to make it render as
        // deleted.
        $thing.remove();
        return;
    }

    if ('friend' in update) {
        var label = '<a class="friend" title="friend" href="/prefs/friends">F</a>';
        
        $entry.find('.author')
              .addClass('friend')
              .next('.userattrs')
              .each(function() {
                    var $this = $(this);

                    if (!$this.html()) {
                        $this.html(' [' + label + ']');
                    } else if (!$this.find('.friend').length) {
                        $this.find('a:first').before(label + ',');
                    }
              });
    }

    if ('voted' in update) {
        var $midcol = $thing.children('.midcol');
        var $up = $midcol.find('.arrow.'+up_cls+', .arrow.'+upmod_cls);
        var $down = $midcol.find('.arrow.'+down_cls+', .arrow.'+downmod_cls);
        var $elems = $($midcol).add($entry);

        switch (update.voted) {
            case 1:
                $elems.addClass('likes').removeClass('dislikes unvoted');
                $up.removeClass(up_cls).addClass(upmod_cls);
                $down.removeClass(downmod_cls).addClass(down_cls);
            break;
            case -1:
                $elems.addClass('dislikes').removeClass('likes unvoted');
                $up.removeClass(upmod_cls).addClass(up_cls);
                $down.removeClass(down_cls).addClass(downmod_cls);
            break;
            default:
                $elems.addClass('unvoted').removeClass('likes dislikes');
                $up.removeClass(upmod_cls).addClass(up_cls);
                $down.removeClass(downmod_cls).addClass(down_cls);
        }
    }

    if ('saved' in update) {
        $thing.addClass('saved');
        $entry.find('.save-button a')
              .text(r._('unsave'));
    }
}

$.fn.resetInput = function() {
  var $el = $(this);
  $el.wrap('<form>').closest('form').get(0).reset();
  $el.unwrap();

  return this;
};

$.fn.show_unvotable_message = function() {
  // deprecated
};

$.fn.thing = function() {
    /* Returns the first thing that is a parent of the current element */
    return this.parents(".thing:first");
};

$.fn.all_things_by_id = function() {
    /* Returns the set of things that have the same ID as the current
     * element's thing (we make no guarantee about uniqueness of
     * things across multiple listings on the same page) */
    return this.thing().add( $.things(this.thing_id()) );
};

$.fn.thing_id = function() {
    /* Returns the (reddit) ID of the current element's thing */
    var t = this.hasClass('thing') ? this : this.thing();

    if (!t.length) {
        return '';
    }

    var id = t.data('fullname');

    if (id) {
        return id;
    }

    // fallback to old, clunky way of getting id from class
    id = $.grep(t.get(0).className.match(/\S+/g),
                function(i) { return i.match(/^id-/); }); 
    return (id.length) ? id[0].slice(3, id[0].length) : '';
};

$.things = function() {
    /* 
     * accepts a list of thing_ids as the first argument and returns a
     * jquery object consisting of the union of all things on the page
     * that represent those things.
     */
    var sel = $.map(arguments, function(x) { return ".thing.id-" + x; })
       .join(", ");
    return $(sel);
};

$.fn.things = function() {
    /* 
     * try to find all things that occur below a given selector, like:
     * $('.organic-listing').things('t3_12345')
     */
    var sel = $.map(arguments, function(x) { return ".thing.id-" + x; })
       .join(", ");
    return this.find(sel);
};

$.listing = function(name) {
    /* 
     * Given an element name (a sitetable ID or a thing ID, with
     * optional siteTable_ at the front), return or generate a listing
     * with the proper id for that name. 
     *
     * In the case of a thing ID, this siteTable will be the listing
     * in the child div of that thing's container.
     * 
     * In the case of a general ID, it will be the listing of that
     * name already present in the DOM.
     *
     * On failure, will return a JQuery object of zero length.
     */
    name = name || "";
    var sitetable = "siteTable";
    /* we'll add the hash specifier in later */
    if (name.slice(0, 1) == "#" || name.slice(0, 1) == ".")
        name = name.slice(1, name.length);

    /* lname should be the name of the actual listing (will always
     * start with sitetable) while name should be the element it is
     * named for (strip off sitetable if present) */
    var lname = name;
    if(name.slice(0, sitetable.length) != sitetable) 
        lname = sitetable + ( (name) ? ("_" + name): "");
    else 
        name = name.slice(sitetable.length + 1, name.length);

    var listing = $("#" + lname).filter(":first");
    /* did the $ lookup match anything? */
    if (listing.length == 0) {
        listing = $.things(name).find(".child")
            .append(document.createElement('div'))
            .children(":last")
            .addClass("sitetable")
            .attr("id", lname);
    }
    return listing;
};


var thing_init_func = function() { };
$.fn.set_thing_init = function(func) {
    thing_init_func = func;
    $(this).find(".thing:not(.stub)").each(function() { func(this) });
};


$.fn.new_thing_child = function(what, use_listing) {
    var id = this.thing_id();
    var where = (use_listing) ? $.listing(id) :
        this.thing().find(".child:first");
    
    var new_form;
    if (typeof(what) == "string") 
        new_form = where.prepend(what).children(":first");
    else 
        new_form = what.hide()
            .prependTo(where)
            .show()
            .find('input[name="parent"]').val(id).end();
    
    return (new_form).randomize_ids();
};

$.fn.randomize_ids = function() {
    var new_id = (Math.random() + "").split('.')[1]
    $(this).find("*[id]").each(function() {
            $(this).attr('id', $(this).attr("id") + new_id);
        }).end()
    .find("label").each(function() {
            $(this).attr('for', $(this).attr("for") + new_id);
        });
    return $(this);
}

$.fn.replace_things = function(things, keep_children, reveal, stubs) {
    /* Given the api-html structured things, insert them into the DOM
     * in such a way as to remove any elements with the same thing_id.
     * "keep_children" is a boolean to determine whether or not any
     * existing child divs should be retained on the new thing (in the
     * case of a comment tree, flags whether or not the new thing has
     * the thread present) while "reveal" determines whether or not to
     * animate the transition from old to new. */
    var self = this,
        map = $.map(things, function(thing) {
            var data = thing.data;
            var existing = $(self).things(data.id);
            if(stubs) 
                existing = existing.filter(".stub");
            if(existing.length == 0) {
                var parent = $.things(data.parent);
                if (parent.length) {
                    existing = $("<div></div>");
                    parent.find(".child:first").append(existing);
                }
            }
            existing.after($.unsafe(data.content));
            var new_thing = existing.next();
            if(keep_children) {
                /* show the new thing */
                new_thing.show()
                    /* but hide its new content */
                    .children(".midcol, .entry").hide().end()
                    .children(".child:first")
                    /* slop over the children */ 
                    .html(existing.children(".child:first")
                          .remove().html())
                    .end();
                /* hide the old entry and show the new one */
                if(reveal) {
                    existing.hide();
                    new_thing.children(".midcol, .entry").show();
                }
                new_thing.find(".rank:first")
                    .html(existing.find(".rank:first").html());
            }

            /* hide and remove old. add in new */
            if(reveal) {
                existing.hide();
                if(keep_children) 
                    new_thing.children(".midcol, .entry")
                        .show();
                else 
                    new_thing.show();
                existing.remove();
            }
            else { 
                new_thing.hide();
                existing.remove();
             }

            /* lastly, set the event handlers for these new things */
            thing_init_func(new_thing);
            $(document).trigger('new_thing', new_thing)
            return new_thing;
        });

    $(document).trigger('new_things_inserted')
    return map
};


$.insert_things = function(things, append) {
    /* Insert new things into a listing.*/
    var map = $.map(things, function(thing) {
            var data = thing.data;
            var s = $.listing(data.parent);
            if(append)
                s = s.append($.unsafe(data.content)).children(".thing:last");
            else
                s = s.prepend($.unsafe(data.content)).children(".thing:first");

            thing_init_func(s.hide().show());
            $(document).trigger('new_thing', s)
            return s;
        })
    $(document).trigger('new_things_inserted')
    return map
};

$.fn.delete_table_row = function(callback) {
    var tr = this.parents("tr:first").get(0);
    var table = this.parents("table").get(0);
    if(tr) {
        $(tr).fadeOut(function() {
                table.deleteRow(tr.rowIndex);
                if(callback) {
                    callback();
                }
            });
    } else if (callback) {
        callback();
    }
};

$.fn.insert_table_rows = function(rows, index) {
    /* find the subset of the current selection that is a table, or
     * the first parent of the current selection that is a table.*/
    var tables = ((this.is("table")) ? this.filter("table") : 
                  this.parents("table:first"));
    $.map(tables.get(),
          function(table) {
              $.map(rows, function(row) {
                      var i = index;
                      if(i < 0)
                          i = Math.max(table.rows.length + i + 1, 0);
                      i = Math.min(i, table.rows.length);

                      var $newRow = $(table.insertRow(i)),
                          $toInsert = $($.parseHTML($.unsafe(row)))

                      $toInsert.hide()
                      $newRow.replaceWith($toInsert)
                      $toInsert.trigger("insert-row")
                      $toInsert.css('display', 'table-row')
                      $toInsert.fadeIn()
                  });
          });
    return this;
};


$.fn.captcha = function(iden) {
    /*  */
    var c = this.find(".capimage");
    if(iden) {
        c.attr("src", "/captcha/" + iden + ".png")
            .siblings('input[name="iden"]').val(iden);
    }
    return c;
};
   

/* Textarea handlers */
$.fn.insertAtCursor = function(value) {
    /* "this" refers to current jquery selection and may contain many
     * non-textarea elements, so filter out and apply to each */
    return $(this).filter("textarea").each(function() {
            /* this should be rebound to one of the elements in the orig list.*/
            var textbox = $(this).get(0);
            var orig_pos = textbox.scrollTop;
        
            if (document.selection) { /* IE */
                textbox.focus();
                var sel = document.selection.createRange();
                sel.text = value;
            }
            else if (textbox.selectionStart) {
                var prev_start = textbox.selectionStart;
                textbox.value = 
                    textbox.value.substring(0, textbox.selectionStart) + 
                    value + 
                    textbox.value.substring(textbox.selectionEnd, 
                                            textbox.value.length);
                prev_start += value.length;
                textbox.setSelectionRange(prev_start, prev_start);
            } else {
                textbox.value += value;
            }
        
            if(textbox.scrollHeight) {
                textbox.scrollTop = orig_pos;
            }
        
            $(this).focus();
        })
    .end();
};

$.fn.select_line = function(lineNo) {
    return $(this).filter("textarea").each(function() {
            var newline = '\n', newline_length = 1, caret_pos = 0;
            var isIE = !!/msie [\w.]+/.exec( navigator.userAgent.toLowerCase() );
            if ( isIE ) { /* IE hack */
                newline = '\r';
                newline_length = 0;
                caret_pos = 1;
            }
            
            var lines = $(this).val().split(newline);
            
            for(var x=0; x<lineNo-1; x++) 
                caret_pos += lines[x].length + newline_length;

            var end_pos = caret_pos;
            if (lineNo <= lines.length) 
                end_pos += lines[lineNo-1].length + newline_length;
            
            $(this).focus();
            if(this.createTextRange) {   /* IE */
                var start = this.createTextRange();
                start.move('character', caret_pos);
                var end = this.createTextRange();
                end.move('character', end_pos);
                start.setEndPoint("StartToEnd", end);
                start.select();
            } else if (this.selectionStart) {
                this.setSelectionRange(caret_pos, end_pos);
            }
            if(this.scrollHeight) {
                var avgLineHight = this.scrollHeight / lines.length;
                this.scrollTop = (lineNo-2) * avgLineHight;
            }
        });
};


$.apply_stylesheet = function(cssText) {
    
    var sheet_title = $("head").children("link[title], style[title]")
        .filter(":first").attr("title") || "preferred stylesheet";

    if(document.styleSheets[0].cssText) {
        /* of course IE has to do this differently from everyone else. */
        var sheets = document.styleSheets;
        for(var x=0; x < sheets.length; x++) 
            if(sheets[x].title == sheet_title) {
                sheets[x].cssText = cssText;
                break;
            }
    } else {
        /* for everyone else, we walk <head> for the <link> or <style>
         * that has the old stylesheet, and delete it. Then we add a
         * <style> with the new one */
        $("head").children('*[title="' + sheet_title + '"]').remove();

        /* Hack to trigger a reflow so webkit browsers reset animations */
        document.body.offsetHeight;

        var stylesheet = $('<style type="text/css" media="screen"></style>')
            .attr('title', sheet_title)
            .text(cssText)
            .appendTo('head')
  }
    
};

$.apply_stylesheet_url = function(cssUrl, srStyleEnabled) {
  var sheetTitle = 'applied_subreddit_stylesheet';
  var $stylesheet = $('link[title="' + sheetTitle + '"]');
  if ($stylesheet.length == 0) {
    $('head').append('<link type="text/css" title="' + sheetTitle + '" rel="stylesheet">');
    $stylesheet = $('link[title="' + sheetTitle + '"]');
  }

  $stylesheet.attr("href", cssUrl);
  $("#sr_style_enabled").prop("checked", srStyleEnabled);
  $("#sr_style_throbber")
    .html("")
    .css("display", "none");
};

$.apply_header_image = function(src, size, title) {
  var $headerImage = $("#header-img");
  if ($headerImage.is("a")) {
    $headerImage
      .attr("id", "header-img-a")
      .text("")
      .append('<img id="header-img"/>');
    $headerImage = $("#header-img");
  }
  $headerImage.removeClass("default-header");
  $headerImage.attr("src", src);
  $headerImage.attr("title", title);
  if (size) {
    $headerImage.attr("width", size[0]);
    $headerImage.attr("height", size[1]);
  } else {
    $headerImage.removeAttr("width");
    $headerImage.removeAttr("height");
  }
}

$.remove_header_image = function() {
  var $headerLink = $("#header-img-a");

  if ($headerLink) {
    $headerLink
      .addClass("default-header")
      .attr("id", "header-img")
      .empty();
    $("#header-img").empty();
    $headerLink.attr("id", "header-img");
  }
}

/* namespace globals for cookies -- default prefix, security and domain */
var default_cookie_domain
$.default_cookie_domain = function(domain) {
    if (domain) {
        default_cookie_domain = domain
    }
}

var default_cookie_security
$.default_cookie_security = function(security) {
    default_cookie_security = security
}

var cookie_name_prefix = "_"
$.cookie_name_prefix = function(name) {
    if (name) {
        cookie_name_prefix = name + "_"
    }
}

/* old reddit-specific cookie functions */
$.cookie_write = function(c) {
    if (c.name) {
        var options = {}
        options.expires = c.expires
        options.domain = c.domain || default_cookie_domain
        options.path = c.path || '/'
        options.secure = c.secure || default_cookie_security

        var key = cookie_name_prefix + c.name,
            value = c.data

        if (value === null || value == '') {
            value = null
        } else if (typeof(value) != 'string') {
            value = JSON.stringify(value)
        }

        $.cookie(key, value, options)
    }
}

$.cookie_read = function(name, prefix) {
    var prefixedName = (prefix || cookie_name_prefix) + name,
        data = $.cookie(prefixedName)

    try {
        data = JSON.parse(data)
    } catch(e) {}

    return {name: name, data: data}
}

$.fn.highlight = function(text) {
  if (!text) { return this; }

  var escaped = $.websafe(text.trim()).replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
  var regex = new RegExp("\\b" + escaped + "\\b", "gi");

  return this.each(function() {
    if (this.children.length) { return; }

    this.innerHTML = this.innerHTML.replace(regex, function(matched) {
      return "<mark>" + matched + "</mark>";
    });
  });
};

})(jQuery);
