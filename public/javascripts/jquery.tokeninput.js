/*
 * jQuery Plugin: Tokenizing Autocomplete Text Entry
 * Version 1.4.2
 *
 * Copyright (c) 2009 James Smith (http://loopj.com)
 * Licensed jointly under the GPL and MIT licenses,
 * choose which one suits your project best!
 *
 */

(function ($) {
// Default settings
var DEFAULT_SETTINGS = {
    hintText: "Type in a search term",
    noResultsText: "No results",
    searchingText: "Searching...",
    deleteText: "&times;",
    searchDelay: 500,
    minChars: 1,
    tokenLimit: null,
    jsonContainer: null,
    method: "GET",
    contentType: "json",
    queryParam: "q",
    tokenDelimiter: ",",
    preventDuplicates: false,
    prePopulate: null,
    processPrePopulate: false,
    makeSortable: false,
    escapeHTML: true,
    animateDropdown: true,
    onResult: null,
    onAdd: null,
    onDelete: null,
    noCache: false
};

// Default classes to use when theming
var DEFAULT_CLASSES = {
    tokenList: "autocomplete",
    sortable: "sortable",
    token: "added tag",
    tokenDelete: "delete",
    selectedToken: "selected",
    highlightedToken: "highlighted",
    dropdown: "autocomplete dropdown",
    dropdownItem: "even",
    dropdownItem2: "odd",
    selectedDropdownItem: "selected",
    inputToken: "input",
    insertBefore: "selected",
    insertAfter: "selected"
};

// Input box position "enum"
var POSITION = {
    BEFORE: 0,
    AFTER: 1,
    END: 2
};

// Keys "enum"
var KEY;
if (!(KeyboardEvent.prototype.hasOwnProperty("key"))) {
    // Older browsers past IE8
    KEY = {
        BACKSPACE: 8,
        TAB: 9,
        ENTER: 13,
        ESCAPE: 27,
        SPACE: 32,
        PAGE_UP: 33,
        PAGE_DOWN: 34,
        END: 35,
        HOME: 36,
        LEFT: 37,
        UP: 38,
        RIGHT: 39,
        DOWN: 40,
        DELETE: 46,
        NUMPAD_ENTER: 108,
        COMMA: 188
    };
    Object.defineProperty(
        KeyboardEvent.prototype,
        "key",
        {
            configurable: true,
            enumerable: true,
            get: function () {
            return this.keyCode;
            }
        });
} else {
    var browserHasKeyProp = true;
    KEY = {
        BACKSPACE: "Backspace",
        TAB: "Tab",
        ENTER: "Enter",
        ESCAPE: "Escape",
        SPACE: " ",
        PAGE_UP: "PageUp",
        PAGE_DOWN: "PageDown",
        END: "End",
        HOME: "Home",
        LEFT: "ArrowLeft",
        UP: "ArrowUp",
        RIGHT: "ArrowRight",
        DOWN: "ArrowDown",
        DELETE: "Delete",
        NUMPAD_ENTER: "Enter",
        COMMA: ","
    };
}


// IE/Edge compatibility for event.key
if (browserHasKeyProp) {
    (function() {
        var eventProto = KeyboardEvent.prototype;
        var keyProp = Object.getOwnPropertyDescriptor(eventProto, "key");
        var keys = {
            Spacebar: " ",
            Esc: "Escape",
            Left: "ArrowLeft",
            Up: "ArrowUp",
            Right: "ArrowRight",
            Down: "ArrowDown",
            Del: "Delete",
        };

        Object.defineProperty(eventProto, "key", {
            configurable: true,
            enumerable: true,
            get: function() {
                var key = keyProp.get.call(this);
                return keys.hasOwnProperty(key) ? keys[key] : key;
            }
        });
    })();
}


// Expose the .tokenInput function to jQuery as a plugin
$.fn.tokenInput = function (url_or_data, options) {
    var settings = $.extend({}, DEFAULT_SETTINGS, options || {});

    return this.each(function () {
        new $.TokenList(this, url_or_data, settings);
    });
};


// TokenList class for each input
$.TokenList = function (input, url_or_data, settings) {
    //
    // Initialization
    //

    // Configure the data source
    if(typeof(url_or_data) === "string") {
        // Set the url to query against
        settings.url = url_or_data;

        // Make a smart guess about cross-domain if it wasn't explicitly specified
        if(settings.crossDomain === undefined) {
            if(settings.url.indexOf("://") === -1) {
                settings.crossDomain = false;
            } else {
                settings.crossDomain = (location.href.split(/\/+/g)[1] !== settings.url.split(/\/+/g)[1]);
            }
        }
    } else if(typeof(url_or_data) === "object") {
        // Set the local data to search through
        settings.local_data = url_or_data;
    }

    // Build class names
    if(settings.classes) {
        // Use custom class names
        settings.classes = $.extend({}, DEFAULT_CLASSES, settings.classes);
    } else if(settings.theme) {
        // Use theme-suffixed default class names
        settings.classes = {};
        $.each(DEFAULT_CLASSES, function(key, value) {
            settings.classes[key] = value + "-" + settings.theme;
        });
    } else {
        settings.classes = DEFAULT_CLASSES;
    }


    // Save the tokens
    var saved_tokens = [];

    // Keep track of the number of tokens in the list
    var token_count = 0;

    // Basic cache to save on db hits
    var cache = new $.TokenList.Cache();

    // Keep track of the timeout, old vals
    var timeout;
    var input_val;

    // Keep a reference to the original input box's id so we can use it for our new input and its label
    var hidden_input_id = $(input)
                              .attr('id');

    // Keep a reference to the label whose for attribute that matches the original input box's id
    var hidden_input_label = $('label[for="' + hidden_input_id + '"]');

    // Change the original label's for attribute so it will match the id attribue we give the new input box
    hidden_input_label.attr({
      'for': hidden_input_id + '_autocomplete'
    });

    // Give the new input box an id attribute based on the original input box's id
    // Originally included .css({outline: "none" }), but we actually want to see an outline for accessibility reasons
        var input_box = $("<input type=\"text\" class=\"text\" autocomplete=\"off\" role=\"combobox\" aria-expanded=\"true\" aria-autocomplete=\"list\">")
        .attr({
          'id': hidden_input_id + '_autocomplete'
        })
        .focus(function () {
            if (settings.tokenLimit === null || token_count < settings.tokenLimit) {
                if ($(this).val().length >= settings.minChars) {
                    // run the search
                    setTimeout(function(){do_search();}, 5);
                } else {
                    show_dropdown_hint();
                }
            }
        })
        .blur(function () {
            hide_dropdown();
        })
        .keydown(function (event) {
            var previous_token;
            var next_token;

            switch(event.key) {
                case KEY.LEFT:
                case KEY.RIGHT:
                case KEY.UP:
                case KEY.DOWN:
                    if(!first_dropdown_item || first_dropdown_item.is(":hidden")) {
                        // There's no dropdown of search results available, we're aiming for the existing tokens
                        if (selected_token) {
                            // save prev and next tokens
                            previous_token = $(selected_token).prev();
                            next_token = $(selected_token).next();

                            // no matter what, deselect the currently selected token
                            if(event.key === KEY.LEFT || event.key === KEY.UP) {
                                deselect_token($(selected_token), POSITION.BEFORE);
                            } else {
                                deselect_token($(selected_token), POSITION.AFTER);
                            }
                        } else {
                            previous_token = input_token.prev();
                            next_token = input_token.next();
                        }

                        if((event.key === KEY.LEFT || event.key === KEY.UP) && previous_token.length) {
                            // We are moving left, select the previous token if it exists
                            select_token($(previous_token.get(0)));
                        } else if((event.key === KEY.RIGHT || event.key === KEY.DOWN) && next_token.length) {
                            // We are moving right, select the next token if it exists
                            select_token($(next_token.get(0)));
                        }
                    } else if (event.key === KEY.LEFT || event.key === KEY.RIGHT) {
                        // ignore to allow users to move around in the string they're typing in the input field with the arrow keys
                        return true;
                    } else {
                        // move up and down in the dropdown list
                        var dropdown_item = null;

                        if (!selected_dropdown_item) {
                            dropdown_item = first_dropdown_item;
                        } else if(event.key === KEY.DOWN) {
                            dropdown_item = $(selected_dropdown_item).next();
                        } else {
                            dropdown_item = $(selected_dropdown_item).prev();
                        }

                        if(dropdown_item.length) {
                            select_dropdown_item(dropdown_item);
                            // scroll to newly selected item if necessary
                            $(dropdown).scrollTo($(dropdown_item));
                        } else if (event.key === KEY.UP) {
                            // deselect the dropdown item
                            if(selected_dropdown_item) {
                                deselect_dropdown_item($(selected_dropdown_item));
                            }
                        }
                        return false;
                    }
                    break;

                case KEY.BACKSPACE:
                case KEY.DELETE:
                    previous_token = input_token.prev();

                    if(!$(this).val().length) {
                        if(selected_token) {
                            delete_token($(selected_token));
                        } else if(previous_token.length) {
                            select_token($(previous_token.get(0)));
                        }

                        return false;
                    } else if($(this).val().length === 1) {
                        hide_dropdown();
                    } else {
                        // set a timeout just long enough to let this function finish.
                        // was 5.
                        setTimeout(function(){do_search();}, 25);
                    }
                    break;

                case KEY.COMMA:
                case KEY.TAB:
                case KEY.ENTER:
                case KEY.NUMPAD_ENTER:
                  if(selected_dropdown_item) {
                    add_token($(selected_dropdown_item));
                    deselect_dropdown_item($(selected_dropdown_item));
                    hide_dropdown();
                    if (event.keyCode === KEY.TAB && settings.tokenLimit && settings.tokenLimit === token_count) {
                        break;
                    } else {
                        return false;
                    }
                  } else if(input_box.val()) {
                    // split contents and add them
                    $.each(input_box.val().split(settings.tokenDelimiter), function(index, item) {
                      add_token($.trim(item));
                    });
                    hide_dropdown();
                    if (event.keyCode === KEY.TAB && settings.tokenLimit && settings.tokenLimit === token_count) {
                        break;
                    } else {
                        return false;
                    }
                  }
                  break;

                case KEY.ESCAPE:
                  hide_dropdown();
                  return true;

                default:
                    if(String.fromCharCode(event.which)) {
                        // set a timeout just long enough to let this function finish.
                        // was 5.
                        setTimeout(function(){do_search();}, 25);
                    }
                    break;
            }
        });

    // If the parent form is submitted and there is data in the input box, submit it
    $(input).closest("form").submit(function (){
        if(input_box.val()) {
            // split contents and add them
            $.each(input_box.val().split(settings.tokenDelimiter), function(index, item) {
              add_token($.trim(item));
            });
        }
    });

    // Keep a reference to the original input box
    // Remove its id because we don't want it duplicated after adding it to the new input
    var hidden_input = $(input)
                           .hide()
                           .focus(function () {
                               input_box.focus();
                           })
                           .blur(function () {
                               input_box.blur();
                           });

    // Keep a reference to the selected token and dropdown item
    var selected_token = null;
    var selected_token_index = 0;
    var selected_dropdown_item = null;
    var first_dropdown_item = null;

    // The list to store the token items in
    var token_list = $("<ul />") // was role=\"listbox\" aria-activedescendant=\"ui-active-menuitem\"
        .addClass(settings.classes.tokenList)
        .click(function (event) {
            var li = $(event.target).closest("li");
            if(li && li.get(0) && $.data(li.get(0), "tokeninput")) {
                toggle_select_token(li);
            } else {
                // Deselect selected token
                if(selected_token) {
                    deselect_token($(selected_token), POSITION.END);
                }

                // Focus input box
                input_box.focus();
            }
        })
        .mouseover(function (event) {
            var li = $(event.target).closest("li");
            if(li && selected_token !== this) {
                li.addClass(settings.classes.highlightedToken);
            }
        })
        .mouseout(function (event) {
            var li = $(event.target).closest("li");
            if(li && selected_token !== this) {
                li.removeClass(settings.classes.highlightedToken);
            }
        })
        .insertBefore(hidden_input);

    if(settings.makeSortable) {
        token_list.addClass(settings.classes.sortable);
    }

    // The token holding the input box
    var input_token = $("<li />") // was role=\"menuitem\"
        .addClass(settings.classes.inputToken)
        .appendTo(token_list)
        .append(input_box);

    // The list to store the dropdown items in
    var dropdown = $("<div>")
        .addClass(settings.classes.dropdown)
        //.appendTo("body")
        .insertAfter(input_box)
        .hide();

    // Save data to pre-populate list with
    var li_data = $(input).val() || settings.prePopulate;
    if(settings.processPrePopulate && $.isFunction(settings.onResult)) {
        li_data = settings.onResult.call(hidden_input, li_data);
    }

    // clear the input
    $(input).val("");
    hidden_input.val("");
    input_box.val("");

    // pre-populate the list
    if(li_data && li_data.length) {
        if(typeof(li_data) === "string") {
            insert_token(li_data, li_data);
        } else {
            $.each(li_data, function (index, value) {
                insert_token(value.id, value.name);
            });
        }
    }

    // Check the token limit
    if(settings.tokenLimit !== null && token_count >= settings.tokenLimit) {
        input_box.hide();
    }


    // Set up the live params to expire our cache if they change
    if(settings.liveParams) {
        var live_param_fields = settings.liveParams.split("&");
        $.each(live_param_fields, function (index, value) {
            var kv = value.split("=");
            var id_to_get = '#' + kv[1] + ' input';
            if ($(id_to_get).size() === 0) {id_to_get = '#' + kv[1];}
            // this will work on both checkboxes and on text fields
            $(id_to_get).each(function(index, node){
                if ($(node).hasClass("autocomplete")) {
                    // we need to expire when the autocomplete field changes
                    node = $(node).prev().find("input").first();
                }
                $(node).change(function(event){
                    cache.clear_data();
                });
            });
        });
    }


    //
    // Private functions
    //

    // function resize_input() {
    //     if(input_val === (input_val = input_box.val())) {return;}
    //
    //     // Enter new content into resizer and resize input accordingly
    //     var escaped = input_val.replace(/&/g, '&amp;').replace(/\s/g,' ').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    //     input_resizer.html(escaped);
    //     input_box.width(input_resizer.width() + 30);
    // }

    function is_printable_character(keycode) {
        return ((keycode >= 48 && keycode <= 90) ||     // 0-1a-z
                (keycode >= 96 && keycode <= 111) ||    // numpad 0-9 + - / * .
                (keycode >= 186 && keycode <= 192) ||   // ; = , - . / ^
                (keycode >= 219 && keycode <= 222));    // ( \ ) '
    }

    // Inner function to a token to the list
    function insert_token(id, value) {
        if(value.indexOf(settings.tokenDelimiter) >= 0){
            $.each(value.split(settings.tokenDelimiter), function (index, subvalue) {
                insert_token(subvalue, subvalue);
            });
            return;
        }

        var this_token = $("<li>"+ escapeHTML(value) +" </li>")
          .addClass(settings.classes.token)
          .insertBefore(input_token);

          if(settings.makeSortable) {
            // use the jqueryUI Sortable method
            // this_token.sortable();
            // this_token.addClass("ui-state-default");
          }

        // The 'delete token' button
        var delete_span_token = $("<span></span>")
            .addClass(settings.classes.tokenDelete)
            .appendTo(this_token)
            .click(function () {
                delete_token($(this).parent());
                return false;
            });
				// Link with a title attribute for better accessibility
        $("<a href=\"#\">" + settings.deleteText + "</a>")
						.attr("title", "remove " + value)
						.appendTo(delete_span_token);

        // Store data on the token
        var token_data = {"id": id, "name": value};
        $.data(this_token.get(0), "tokeninput", token_data);

        // Save this token for duplicate checking
        saved_tokens = saved_tokens.slice(0,selected_token_index).concat([token_data]).concat(saved_tokens.slice(selected_token_index));
        selected_token_index++;

        // Update the hidden input
        var token_ids = $.map(saved_tokens, function (el) {
            return el.id;
        });


        hidden_input.val(token_ids.join(settings.tokenDelimiter));

        token_count += 1;

        return this_token;
    }



    // Add a token to the token list based on user input
    function add_token (item) {
        var li_data;
        if ($.type(item) === "string"){
            li_data = {id: item, name: item};
        } else {
            li_data = $.data(item.get(0), "tokeninput");
        }

        // Clear input box
        input_box.val("");

        var callback = settings.onAdd;

        // See if the token already exists and select it if we don't want duplicates
        if(token_count > 0 && settings.preventDuplicates) {
            var found_existing_token = null;
            token_list.children().each(function () {
                var existing_token = $(this);
                var existing_data = $.data(existing_token.get(0), "tokeninput");
                if(existing_data && existing_data.id === li_data.id) {
                    found_existing_token = existing_token;
                    return false;
                }
            });

            if(found_existing_token) {
                select_token(found_existing_token);
                // input_token.insertAfter(found_existing_token);
                input_box.focus();
                return;
            }
        }

        // Insert the new tokens
        insert_token(li_data.id, li_data.name);

        // Check the token limit
        if(settings.tokenLimit !== null && token_count >= settings.tokenLimit) {
            // put focus on the last token's remove option, then hide the input
            input_box.parent().prev("li").find("a").focus();
            input_box.hide();
        } else {
            input_box.focus();
        }

        // Don't show the help dropdown, they've got the idea
        hide_dropdown();

        // Execute the onAdd callback if defined
        if($.isFunction(callback)) {
            callback.call(hidden_input,li_data);
        }

        // trigger our onchange event
        input_box.change();
    }


    // Edit a token in the token list
    function edit_token(token) {
        hide_dropdown();
        var token_data = $.data(token.get(0), "tokeninput");
        delete_token(token);
        input_box.val(token_data.id);
    }

    // Select a token in the token list
    function select_token(token) {
        token.addClass(settings.classes.selectedToken);
        selected_token = token.get(0);

        // Hide input box
        input_box.val("");

        // Hide dropdown if it is visible (eg if we clicked to select token)
        hide_dropdown();
    }

    // Deselect a token in the token list
    function deselect_token (token, position) {
        token.removeClass(settings.classes.selectedToken);
        selected_token = null;

        if(position === POSITION.BEFORE) {
            //input_token.insertBefore(token);
            selected_token_index--;
        } else if(position === POSITION.AFTER) {
            //input_token.insertAfter(token);
            selected_token_index++;
        } else {
            //input_token.appendTo(token_list);
            selected_token_index = token_count;
        }

        // Show the input box and give it focus again
        input_box.focus();
    }

    // Toggle selection of a token in the token list
    function toggle_select_token(token) {
        var previous_selected_token = selected_token;

        if(selected_token) {
            if(selected_token === token.get(0)) {
                // second click -- edit
                edit_token(token);
                return;
            }
            deselect_token($(selected_token), POSITION.END);
        }

        if(previous_selected_token === token.get(0)) {
            deselect_token(token, POSITION.END);
        } else {
            select_token(token);
        }
    }

    // Delete a token from the token list
    function delete_token (token) {
        // Remove the id from the saved list
        var token_data = $.data(token.get(0), "tokeninput");
        var callback = settings.onDelete;

        var index = token.prevAll().length;
        if(index > selected_token_index) index--;

        // Delete the token
        token.remove();
        selected_token = null;

        // Show the input box and give it focus again
        input_box.focus();

        // Remove this token from the saved list
        saved_tokens = saved_tokens.slice(0,index).concat(saved_tokens.slice(index+1));
        if(index < selected_token_index) selected_token_index--;

        // Update the hidden input
        var token_ids = $.map(saved_tokens, function (el) {
            return el.id;
        });
        hidden_input.val(token_ids.join(settings.tokenDelimiter));

        token_count -= 1;

        if(settings.tokenLimit !== null) {
            input_box
                .show()
                .val("")
                .focus();
        }

        // Execute the onDelete callback if defined
        if($.isFunction(callback)) {
            callback.call(hidden_input,token_data);
        }

        // trigger our onchange event
        input_box.change();
    }

    // Hide and clear the results dropdown
    function hide_dropdown () {
        // dropdown.hide().empty();
        // first_dropdown_item = null;
        // selected_dropdown_item = null;

        dropdown.hide();
        if (selected_dropdown_item) {
            $(selected_dropdown_item).removeClass(settings.classes.selectedToken);
        }
        selected_dropdown_item = null;
    }

    function show_dropdown() {
        dropdown
            .css({
                position: "absolute"
                // ,
                // top: $(token_list).offset().top + $(token_list).outerHeight(),
                // left: $(token_list).offset().left
            })
            .css('z-index', 999)
            .show();
    }

    function show_dropdown_searching () {
        if(settings.searchingText) {
            dropdown.html("<p class='notice'>"+settings.searchingText+"</p>");
            show_dropdown();
        }
    }

    function show_dropdown_hint () {
        if(settings.hintText) {
            dropdown.html("<p class='notice'>"+settings.hintText+"</p>");
            show_dropdown();
        }
    }

    // Highlight the query part of the search term
    function highlight_term(value, term) {
        var newvalue = value;
        $.each(term.split(' '), function(index, termbit) {
            if (!termbit) {
                // AO3-4976 skip empty strings
                return;
            }
            termbit = termbit.replace(/([.?*+^$[\]\\(){}-])/g, "\\$1");
            newvalue = newvalue.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + termbit + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<b>$1</b>");
        });
        return newvalue;
    }

    // Populate the results dropdown with some results
    function populate_dropdown (query, results) {
        if(results && results.length) {
            dropdown.empty();
            var dropdown_ul = $("<ul role=\"listbox\" aria-activedescendant=\"ui-active-menuitem\">")
                .appendTo(dropdown)
                .mouseover(function (event) {
                    select_dropdown_item($(event.target).closest("li"));
                })
                .mousedown(function (event) {
                    add_token($(event.target).closest("li"));
                    return false;
                })
                .hide();

            $.each(results, function(index, value) {
                var this_li = $("<li role=\"option\">" + highlight_term(escapeHTML(value.name), query) + "</li>") // was role=\"menuitem\"
                                  .appendTo(dropdown_ul);

                if(index % 2) {
                    this_li.addClass(settings.classes.dropdownItem);
                } else {
                    this_li.addClass(settings.classes.dropdownItem2);
                }

                if(index === 0) {
                     first_dropdown_item = this_li;
                }

                $.data(this_li.get(0), "tokeninput", {"id": value.id, "name": value.name});
            });

            show_dropdown();

            if(settings.animateDropdown) {
                dropdown_ul.slideDown("fast");
            } else {
                dropdown_ul.show();
            }
        } else {
            if(settings.noResultsText) {
                dropdown.html("<p class='notice'>"+settings.noResultsText+"</p>");
                show_dropdown();
            }
        }
    }

    // Highlight an item in the results dropdown
    function select_dropdown_item (item) {
        if(item) {
            if(selected_dropdown_item) {
                deselect_dropdown_item($(selected_dropdown_item));
            }

            item.addClass(settings.classes.selectedDropdownItem);
            selected_dropdown_item = item.get(0);
        }
    }

    // Remove highlighting from an item in the results dropdown
    function deselect_dropdown_item (item) {
        item.removeClass(settings.classes.selectedDropdownItem);
        selected_dropdown_item = null;
    }


    function escapeHTML(text) {
      if(!settings.escapeHTML) return text;
      return $("<p></p>").text(text).html();
    }


    // Do a search and show the "searching" dropdown if the input is longer
    // than settings.minChars
    function do_search() {
        var query = input_box.val().toLowerCase();

        if( (query && query.length) || settings.minChars == 0) {
            if (selected_dropdown_item) {
                deselect_dropdown_item($(selected_dropdown_item));
            }

            if(selected_token) {
                deselect_token($(selected_token), POSITION.AFTER);
            }

            if(settings.minChars == 0 || query.length >= settings.minChars) {
                show_dropdown_searching();
                clearTimeout(timeout);

                timeout = setTimeout(function(){
                    run_search(query);
                }, settings.searchDelay);
            } else {
                hide_dropdown();
            }
        }
    }

    // Do the actual search
    function run_search(query) {
        if(!settings.noCache) {
            var cached_results = cache.get(query);
        }
        if(!settings.noCache && cached_results) {
            populate_dropdown(query, cached_results);
        } else {
            search_and_cache(query);
        }
    }

    // Populate the cache with results and return the results
    function search_and_cache(query) {
        // Are we doing an ajax search or local data search?
        if(settings.url) { // ajax search
            ajax_search(query);
        } else if(settings.local_data) {
            // Do the search through local data
            var results = $.grep(settings.local_data, function (row) {
                return row.name.toLowerCase().indexOf(query.toLowerCase()) > -1;
            });

            if($.isFunction(settings.onResult)) {
                results = settings.onResult.call(hidden_input, results);
            }
            cache.add(query, results);
            populate_dropdown(query, results);
        }
    }

    // Run ajax query
    function ajax_search(query) {
        // Extract exisiting get params
        var ajax_params = {};
        ajax_params.data = {};
        if(settings.url.indexOf("?") > -1) {
            var parts = settings.url.split("?");
            ajax_params.url = parts[0];

            var param_array = parts[1].split("&");
            $.each(param_array, function (index, value) {
                var kv = value.split("=");
                ajax_params.data[kv[0]] = kv[1];
            });
        } else {
            ajax_params.url = settings.url;
        }

        // Get live params
        if(settings.liveParams) {
            var live_param_fields = settings.liveParams.split("&");
            $.each(live_param_fields, function (index, value) {
                var kv = value.split("=");
                // test for checkboxes or text input field
                var id_to_get = '#' + kv[1] + ' input';
                if ($(id_to_get).size() === 0) {
                    id_to_get = '#' + kv[1];
                } else {
                    id_to_get += ':checked';
                }
                var id_contents = $(id_to_get).map(function(i,n){return $(n).val();}).get();
                if(id_contents) {
                    ajax_params.data[kv[0]] = id_contents;
                }
            });
        }

        // Prepare the request
        ajax_params.data[settings.queryParam] = query;
        ajax_params.type = settings.method;
        ajax_params.dataType = settings.contentType;
        if(settings.crossDomain) {
            ajax_params.dataType = "jsonp";
        }

        // Attach the success callback
        ajax_params.success = function(results) {
            if($.isFunction(settings.onResult)) {
                results = settings.onResult.call(hidden_input, results);
            }

            if(!settings.noCache) {
                cache.add(query, settings.jsonContainer ? results[settings.jsonContainer] : results);
            }

            // only populate the dropdown if the results are associated with the active search query
            if(input_box.val().toLowerCase() === query && input_box.is(":focus")) {
                populate_dropdown(query, settings.jsonContainer ? results[settings.jsonContainer] : results);
            }
        };

        // Make the request
        $.ajax(ajax_params);
    }


};

// Really basic cache for the results
$.TokenList.Cache = function (options) {
    var settings = $.extend({
        max_size: 500
    }, options);

    var data = {};
    var size = 0;

    var flush = function () {
        data = {};
        size = 0;
    };

    this.clear_data = function () {
        flush();
    };

    this.add = function (query, results) {
        if(size > settings.max_size) {
            flush();
        }

        if(!data[query]) {
            size += 1;
        }

        data[query] = results;
    };

    this.get = function (query) {
        return data[query];
    };
};
}(jQuery));
