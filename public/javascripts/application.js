// Place your application-specific JavaScript functions and classes here
// This file is automatically included by javascript_include_tag :defaults

//things to do when the page loads
$j(document).ready(function() {
    setupToggled();
    if ($j('#work-form')) { hideFormFields(); };
    hideHideMe();
    showShowMe();
    handlePopUps();
    attachCharacterCounters();
    setupAccordion();
    setupDropdown();
    updateCachedTokens();

    // remove final comma from comma lists in older browsers
    $j('.commas li:last-child').addClass('last');

    // add clear to items on the splash page in older browsers
    $j('.splash').children('div:nth-of-type(odd)').addClass('odd');

    // make Share buttons on works and own bookmarks visible
    $j('.actions').children('.share').removeClass('hidden');

    // make Approve buttons on inbox items visible
    $j('#inbox-form, .messages').find('.unreviewed').find('.review').find('a').removeClass('hidden');

    prepareDeleteLinks();
    thermometer();
    $j('body').addClass('javascript');
});

///////////////////////////////////////////////////////////////////
// Autocomplete
///////////////////////////////////////////////////////////////////

function get_token_input_options(self) {
  return {
    searchingText: self.data('autocomplete-searching-text'),
    hintText: self.data('autocomplete-hint-text'),
    noResultsText: self.data('autocomplete-no-results-text'),
    minChars: self.data('autocomplete-min-chars'),
    queryParam: "term",
    preventDuplicates: true,
    tokenLimit: self.data('autocomplete-token-limit'),
    liveParams: self.data('autocomplete-live-params'),
    makeSortable: self.data('autocomplete-sortable')
  };
}

// Look for autocomplete_options in application helper and throughout the views to
// see how to use this!
var input = $j('input.autocomplete');
if (input.livequery) {
  jQuery(function($) {
    $('input.autocomplete').livequery(function(){
      var self = $(this);
      var token_input_options = get_token_input_options(self);
      var method;
      try {
          method = $.parseJSON(self.data('autocomplete-method'));
      } catch (err) {
          method = self.data('autocomplete-method');
      }
      self.tokenInput(method, token_input_options);
    });
  });
}

///////////////////////////////////////////////////////////////////

// expand, contract, shuffle
jQuery(function($){
  $('.expand').each(function(){
    // start by hiding the list in the page
    list = $($(this).attr('action_target'));
    if (!list.attr('force_expand') || list.children().size() > 25 || list.attr('force_contract')) {
      list.hide();
      $(this).show();
    } else {
      // show the shuffle and contract button only
      $(this).nextAll(".shuffle").show();
      $(this).next(".contract").show();
    }

    // set up click event to expand the list
    $(this).click(function(event){
      list = $($(this).attr('action_target'));
      list.show();

      // show the contract & shuffle buttons and hide us
      $(this).next(".contract").show();
      $(this).nextAll(".shuffle").show();
      $(this).hide();

      event.preventDefault(); // don't want to actually click the link
    });
  });

  $('.contract').each(function(){
    $(this).click(function(event){
      // hide the list when clicked
      list = $($(this).attr('action_target'));
      list.hide();

      // show the expand and shuffle buttons and hide us
      $(this).prev(".expand").show();
      $(this).nextAll(".shuffle").hide();
      $(this).hide();

      event.preventDefault(); // don't want to actually click the link
    });
  });

  $('.shuffle').each(function(){
    // shuffle the list's children when clicked
    $(this).click(function(event){
      list = $($(this).attr('action_target'));
      list.children().shuffle();
      event.preventDefault(); // don't want to actually click the link
    });
  });

  $('.expand_all').each(function(){
      target = "." + $(this).attr('target_class');
     $(this).click(function(event) {
        $(this).closest(target).find(".expand").click();
        event.preventDefault();
     });
  });

  $('.contract_all').each(function(){
     target = "." + $(this).attr('target_class');
     $(this).click(function(event) {
        $(this).closest(target).find(".contract").click();
        event.preventDefault();
     });
  });

});

// check all or none within the parent fieldset, optionally with a string to match on the id attribute of the checkboxes
// stored in the "data-checkbox-id-filter" attribute on the all/none links.
// allow for some flexibility by checking the next and previous fieldset if the checkboxes aren't in this one
jQuery(function($){
  $('.check_all').each(function(){
    $(this).click(function(event){
      var filter = $(this).data('checkbox-id-filter');
      var checkboxes;
      if (filter) {
        checkboxes = $(this).closest('fieldset').find('input[id*="' + filter + '"][type="checkbox"]');
      } else {
        checkboxes = $(this).closest("fieldset").find(':checkbox');
        if (checkboxes.length == 0) {
          checkboxes = $(this).closest("fieldset").next().find(':checkbox');
          if (checkboxes.length == 0) {
            checkboxes = $(this).closest("fieldset").prev().find(':checkbox');
          }
        }
      }
      checkboxes.prop('checked', true);
      event.preventDefault();
    });
  });

  $('.check_none').each(function(){
    $(this).click(function(event){
      var filter = $(this).data('checkbox-id-filter');
      var checkboxes;
      if (filter) {
        checkboxes = $(this).closest('fieldset').find('input[id*="' + filter + '"][type="checkbox"]');
      } else {
        checkboxes = $(this).closest("fieldset").find(':checkbox');
        if (checkboxes.length == 0) {
          checkboxes = $(this).closest("fieldset").next().find(':checkbox');
          if (checkboxes.length == 0) {
            checkboxes = $(this).closest("fieldset").prev().find(':checkbox');
          }
        }
      }
      checkboxes.prop('checked', false);
      event.preventDefault();
    });
  });
});

// Set up open and close toggles for a given object
// Typical setup (this will leave the toggled item open for users without javascript but hide the controls from them):
// <a class="foo_open hidden">Open Foo</a>
// <div id="foo" class="toggled">
//   foo!
//   <a class="foo_close hidden">Close</a>
// </div>
//
// Notes:
// - The open button CANNOT be inside the toggled div, the close button can be (but doesn't have to be)
// - You can have multiple open and close buttons for the same div since those are labeled with classes
// - You don't have to use div and a, those are just examples. Anything you put the toggled and _open/_close classes on will work.
// - If you want the toggled item not to be visible to users without JavaScript by default, add the class "hidden" to the toggled item as well.
//   (and you can then add an alternative link for them using <noscript>)
// - Generally reserved for toggling complex elements like bookmark forms and challenge sign-ups; for simple elements like lists use setupAccordion.
function setupToggled(){
  $j('.toggled').each(function(){
    var node = $j(this);
    var open_toggles = $j('.' + node.attr('id') + "_open");
    var close_toggles = $j('.' + node.attr('id') + "_close");

    if (node.hasClass('open')) {
      close_toggles.each(function(){$j(this).show();});
      open_toggles.each(function(){$j(this).hide();});
    } else {
      node.hide();
      close_toggles.each(function(){$j(this).hide();});
      open_toggles.each(function(){$j(this).show();});
    }

    open_toggles.each(function(){
      $j(this).click(function(e){
        if ($j(this).attr('href') == '#') {e.preventDefault();}
        node.show();
        open_toggles.each(function(){$j(this).hide();});
        close_toggles.each(function(){$j(this).show();});
      });
    });

    close_toggles.each(function(){
      $j(this).click(function(e){
        if ($j(this).attr('href') == '#') {e.preventDefault();}
        node.hide();
        close_toggles.each(function(){$j(this).hide();});
        open_toggles.each(function(){$j(this).show();});
      });
    });
  });
}

function hideHideMe() {
    $j('.hideme').each(function() { $j(this).hide(); });
}

function showShowMe() {
    $j('.showme').each(function() { $j(this).show(); });
}

function handlePopUps() {
    $j("a[data_popup]").click(function(event, element) {
      if (event.stopped) return;
      window.open($j(element).attr('href'));
      event.stop();
    });
}

// used in nested form fields for deleting a nested resource
// see prompt form for example
function remove_section(link, class_of_section_to_remove) {
  $j(link).siblings(":input[type=hidden]").val("1"); // relies on the "_destroy" field being the nearest hidden field
  var section = $j(link).closest("." + class_of_section_to_remove);
  section.find(".required input, .required textarea").each(function(index) {
    var element = eval('validation_for_' + $j(this).attr('id'));
    element.disable();
  });
  section.hide();
}

// used with nested form fields for dynamically stuffing in an extra partial
// see challenge signup form and prompt form for an example
function add_section(link, nested_model_name, content) {
    // get the right new_id which should be in a div with class "last_id" at the bottom of
    // the nearest section
    var last_id = parseInt($j(link).parent().siblings('.last_id').last().html());
    var new_id = last_id + 1;
    var regexp = new RegExp("new_" + nested_model_name, "g");
    content = content.replace(regexp, new_id);
    // kludgy: show the hidden remove_section link (we don't want it showing for non-js users)
    content = content.replace('class="hidden showme"', '');
    $j(link).parent().before(content);
}

// An attempt to replace the various work form toggle methods with a more generic one
function toggleFormField(element_id) {
    var ticky = $j('#' + element_id + '-show');
    if (ticky.is(':checked')) {
      $j('#' + element_id).removeClass('hidden');
    }
    else {
        $j('#' + element_id).addClass('hidden');
        if (element_id != 'chapters-options' && element_id != 'backdate-options') {
            $j('#' + element_id).find(':input[type!="hidden"]').each(function(index, d) {
                if ($j(d).attr('type') == "checkbox") {$j(d).attr('checked', false);}
                else {$j(d).val('');}
            });
        }
    }
    // We want to check this whether the ticky is checked or not
    if (element_id == 'chapters-options') {
        var item = document.getElementById('work_wip_length');
        if (item.value == 1 || item.value == '1') {item.value = '?';}
        else {item.value = 1;}
    }
}

// Hides expandable form field options if Javascript is enabled
function hideFormFields() {
    if ($j('#work-form') != null) {
        var toHide = ['#co-authors-options', '#front-notes-options', '#end-notes-options', '#chapters-options',
          '#parent-options', '#series-options', '#backdate-options', '#override_tags-options'];
        $j.each(toHide, function(index, name) {
            if ($j(name)) {
                if (!($j(name + '-show').is(':checked'))) { $j(name).addClass('hidden'); }
            }
        });
        $j('#work-form').className = $j('#work-form').className;
    }
}

// Hides the extra checkbox fields in prompt form
function hideField(id) {
  $j('#' + id).toggle();
}

function attachCharacterCounters() {
    var countFn = function() {
        var counter = (function(input) {
                /* Character-counted inputs do not always have the same hierarchical relationship
                to their associated counter elements in the DOM, and some cc-inputs have
                duplicate ids. So search for the input's associated counter element first by id,
                then by checking the input's siblings, then by checking its cousins. */
                var cc = $j('.character_counter [id='+input.attr('id')+'_counter]');
                if (cc.length === 1) { return cc; } // id search, use attribute selector rather
                // than # to check for duplicate ids

                cc = input.nextAll('.character_counter').first().find('.value'); // sibling search
                if (cc.length) { return cc; }

                var parent = input.parent(); // 2 level cousin search
                for (var i = 0; i < 2; i++) {
                    cc = parent.nextAll('.character_counter').find('.value');
                    if (cc.length) { return cc; }
                    parent = parent.parent();
                }

                return $j(); // return empty jquery element if search found nothing
            })($j(this)),
            max = parseInt(counter.attr('data-maxlength'), 10),
            val = $j(this).val().replace(/\r\n/g,'\n').replace(/\r|\n/g,'\r\n'),
            remaining = max - val.length;

        counter.html(remaining).attr("aria-valuenow", remaining);
    };

    $j(document).on('keyup keydown mouseup mousedown change', '.observe_textlength', countFn);
    $j('.observe_textlength').each(countFn);
}

// prevent double submission for JS enabled
jQuery.fn.preventDoubleSubmit = function() {
  jQuery(this).submit(function() {
    if (this.beenSubmitted)
      return false;
    else
      this.beenSubmitted = true;
  });
};

// add attributes that are only needed in the primary menus and when JavaScript is enabled
function setupDropdown(){
  $j('#header').find('.dropdown').attr("aria-haspopup", true);
  $j('#header').find('.dropdown, .dropdown .actions').children('a').attr({
    'class': 'dropdown-toggle',
    'data-toggle': 'dropdown',
    'data-target': '#'
  });
  $j('.dropdown').find('.menu').addClass("dropdown-menu");
  $j('.dropdown').find('.menu').children('li').attr("role", "menu-item");
}

// Accordion-style collapsible widgets
// The pane element can be shown or hidden using the expander (link)
// Apply hidden to the pane element if it shouldn't be visible when JavaScript is disabled
// Typical set up:
// <li aria-haspopup="true">
//  <a href="#">Expander</a>
//  <div class="expandable">
//    foo!
//  </div>
// </li>
function setupAccordion() {
  $j(".expandable").each(function() {
    var pane = $j(this);
    // hide the pane element if it's not hidden by default
    if ( !pane.hasClass("hidden") ) {
      pane.addClass("hidden");
    };

    // make the expander visible
    // add the default collapsed state
    // make it do the expanding and collapsing
    pane.prev().removeClass("hidden").addClass("collapsed").click(function(e) {
      var expander = $j(this);
      if (expander.attr('href') == '#') {
        e.preventDefault();
      };

      // change the classes upon clicking the expander
      expander.toggleClass("collapsed").toggleClass("expanded").next().toggleClass("hidden");
    });
  });
}

// Remove the /confirm_delete portion of delete links so user who have JS enabled will
// be able to delete items via hyperlink (per rails/jquery-ujs) rather than a dedicated
// form page. Also add a confirmation message if one was not set in the back end using
// :confirm => "message"
function prepareDeleteLinks() {
  $j('a[href$="/confirm_delete"]').each(function(){
    this.href = this.href.replace(/\/confirm_delete$/, "");
    $j(this).attr("data-method", "delete");
    if ($j(this).is("[data-confirm]")) {
      return;
    } else {
      $j(this).attr("data-confirm", "Are you sure? This CANNOT BE UNDONE!");
    };
  });

  // For purging assignments in gift exchanges. This is only on one page and easy to
  // check, so don't worry about adding a fallback data-confirm message.
  $j('a[href$="/confirm_purge"]').each(function() {
    this.href = this.href.replace(/\/confirm_purge$/, "/purge");
    $j(this).attr("data-method", "post");
  });
}

/// Kudos
$j(document).ready(function() {
  $j('#kudo_submit').on("click", function(event) {
    event.preventDefault();

    $j.ajax({
      type: 'POST',
      url: '/kudos.js',
      data: jQuery('#new_kudo').serialize(),
      error: function(jqXHR, textStatus, errorThrown) {
        var msg = 'Sorry, we were unable to save your kudos';

        // When we hit the rate limit, the response from Rack::Attack is a plain text 429.
        if (jqXHR.status == "429") {
          msg = "Sorry, you can't leave more kudos right now. Please try again in a few minutes.";
        } else {
          var data = $j.parseJSON(jqXHR.responseText);
          if (data.error_message) {
            msg = data.error_message;
          }
        }

        $j('#kudos_message').addClass('kudos_error').text(msg);
      },
      success: function(data) {
        $j('#kudos_message').addClass('notice').text('Thank you for leaving kudos!');
      }
    });
  });

  // Scroll to the top of the comments section when loading additional pages via Ajax in comment pagination.
  $j('#comments_placeholder').on('click.rails', '.pagination a[data-remote]', function(e){
    $j.scrollTo('#comments_placeholder');
  });

  // Scroll to the top of the comments section when loading comments via AJAX
  $j("#show_comments_link_top").on('click.rails', 'a[href*="show_comments"]', function(e){
    $j.scrollTo('#comments');
  });
});

// For simple forms that appear to toggle between creating and destroying records
// e.g. favorite tags, subscriptions
// <form> needs ajax-create-destroy class, data-create-value, data-destroy-value
// data-create-value: text of the button for creating, e.g. Favorite, Subscribe
// data-destroy-value: text of button for destroying, e.g. Unfavorite, Unsubscribe
// controller needs item_id and item_success_message for save success and
// item_success_message for destroy success
$j(document).ready(function() {
  $j('.ajax-create-destroy').on("click", function(event) {
    event.preventDefault();

    var form = $j(this);
    var formAction = form.attr('action');
    var formSubmit = form.find('[type="submit"]');
    var createValue = form.data('create-value');
    var destroyValue = form.data('destroy-value');
    var flashContainer = $j('.flash');

    $j.ajax({
      type: 'POST',
      url: formAction,
      data: form.serialize(),
      dataType: 'json',
      success: function(data) {
        flashContainer.removeClass('error').empty();
        if (data.item_id) {
          flashContainer.addClass('notice').html(data.item_success_message);
          formSubmit.val(destroyValue);
          form.append('<input name="_method" type="hidden" value="delete">');
          form.attr('action', formAction + '/' + data.item_id);
        } else {
          flashContainer.addClass('notice').html(data.item_success_message);
          formSubmit.val(createValue);
          form.find('input[name="_method"]').remove();
          form.attr('action', formAction.replace(/\/\d+/, ''));
        }
      },
      error: function(xhr, textStatus, errorThrown) {
        flashContainer.empty();
        flashContainer.addClass('error notice');
        try {
          jQuery.parseJSON(xhr.responseText);
        } catch (e) {
          flashContainer.append("We're sorry! Something went wrong.");
          return;
        }
        $j.each(jQuery.parseJSON(xhr.responseText).errors, function(index, error) {
          flashContainer.append(error + " ");
        });
      }
    });
  });
});

// For simple forms that update or destroy records and remove them from a listing
// e.g. delete from history, mark as read, delete invitation request
// <form> needs ajax-remove class
// controller needs item_success_message
$j(document).ready(function() {
  $j('.ajax-remove').on("click", function(event) {
    event.preventDefault();

    var form = $j(this);
    var formAction = form.attr('action');
    // The record we're removing is probably in a list, but might be in a table
    if (form.closest('li.group').length !== 0) {
      formParent = form.closest('li.group');
    } else { formParent = form.closest('tr'); };
    // The admin div does not hold a flash container
    var parentContainer = formParent.closest('div:not(.admin)');
    var flashContainer = parentContainer.find('.flash');

    $j.ajax({
      type: 'POST',
      url: formAction,
      data: form.serialize(),
      dataType: 'json',
      success: function(data) {
        flashContainer.removeClass('error').empty();
        flashContainer.addClass('notice').html(data.item_success_message);
      },
      error: function(xhr, textStatus, errorThrown) {
        flashContainer.empty();
        flashContainer.addClass('error notice');
        try {
          jQuery.parseJSON(xhr.responseText);
        } catch (e) {
          flashContainer.append("We're sorry! Something went wrong.");
          return;
        }
        $j.each(jQuery.parseJSON(xhr.responseText).errors, function(index, error) {
          flashContainer.append(error + " ");
        });
      }
    });

    $j(document).ajaxSuccess(function() {
      formParent.slideUp(function() {
        $j(this).remove();
      });
    });
  });
});

// FUNDRAISING THERMOMETER adapted from http://jsfiddle.net/GeekyJohn/vQ4Xn/
function thermometer() {
  $j('.announcement').has('.goal').each(function(){
    var banner_content = $j(this).find('blockquote')
        banner_goal_text = banner_content.find('span.goal').text()
        banner_progress_text = banner_content.find('span.progress').text()
        if ($j(this).find('span.goal').hasClass('stretch')){
          stretch = true
        } else { stretch = false }

        goal_amount = parseFloat(banner_goal_text.replace(/,/g, ''))
        progress_amount = parseFloat(banner_progress_text.replace(/,/g, ''))
        percentage_amount = Math.min( Math.round(progress_amount / goal_amount * 1000) / 10, 100);

    // add thermometer markup (with amounts)
    banner_content.append('<div class="thermometer-content"><div class="thermometer"><div class="track"><div class="goal"><span class="amount">US$' + banner_goal_text +'</span></div><div class="progress"><span class="amount">US$' + banner_progress_text + '</span></div></div></div></div>');

    // set the progress indicator
    // darker green for over 100% stretch goals
    // green for 100%
    // yellow-green for 85-99%
    // yellow for 30-84%
    // orange for 0-29%
    if ( stretch == true ) {
      banner_content.find('div.track').css({
        'background': '#8eb92a',
        'background-image': 'linear-gradient(to bottom, #bfd255 0%, #8eb92a 50%, #72aa00 51%, #9ecb2d 100%)'
      });
      banner_content.find('div.progress').css({
        'width': percentage_amount + '%',
        'background': '#4d7c10',
        'background-image': 'linear-gradient(to bottom, #6e992f 0%, #4d7c10 50%, #3b7000 51%, #5d8e13 100%)'
      });
    } else if (percentage_amount >= 100) {
      banner_content.find('div.progress').css({
        'width': '100%',
        'background': '#8eb92a',
        'background-image': 'linear-gradient(to bottom, #bfd255 0%, #8eb92a 50%, #72aa00 51%, #9ecb2d 100%)'
      });
    } else if (percentage_amount >= 85) {
      banner_content.find('div.progress').css({
        'width': percentage_amount + '%',
        'background': '#d2e638',
        'background-image': 'linear-gradient(to bottom, #e6f0a3 0%, #d2e638 50%, #c3d825 51%, #dbf043 100%)'
      });
    } else if (percentage_amount >= 30) {
      banner_content.find('div.progress').css({
        'width': percentage_amount + '%',
        'background': '#fccd4d',
        'background-image': 'linear-gradient(to bottom, #fceabb 0%, #fccd4d 50%, #f8b500 51%, #fbdf93 100%)'
      });
    } else {
      banner_content.find('div.progress').css({
        'width': percentage_amount + '%',
        'background': '#f17432',
        'background-image': 'linear-gradient(to bottom, #feccb1 0%, #f17432 50%, #ea5507 51%, #fb955e 100%)'
      });
    }
  });
}

function updateCachedTokens() {
  // we only do full page caching when users are logged out
  if ($j('#small_login').length > 0) {
    $j.getJSON("/token_dispenser.json", function( data ) {
      var token = data.token;
      // set token on fields
      $j('input[name=authenticity_token]').each(function(){
        $j(this).attr('value', token);
      });
      $j('meta[name=csrf-token]').attr('content', token);
      $j.event.trigger({ type: "loadedCSRF" });
    });
  } else {
    $j.event.trigger({ type: "loadedCSRF" });
  }
}
