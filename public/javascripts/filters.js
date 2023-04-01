$j(document).ready(function() {
  setupFilterToggles();
  showFilters();
  setupNarrowScreenFilters();
});

// Make tag filter section names into buttons for toggling the sections
// e.g. Fandoms dt should have a button to toggle dd with Fandom tags
// (actual toggling done with setupAccordion in application.js)
function setupFilterToggles() {
  var filter_option = $j('.filters').find('dt.filter-toggle');

  filter_option.each(function() {
    var option_list_id = $j(this).next().attr("id");

    $j(this).wrapInner('<button type="button" class="expander" aria-expanded="false" aria-controls="' + option_list_id + '"></button>');
  });

  // change toggle button's aria-expanded value on click
  $j('dt.tags button').on( "click", function() {
    if ($j(this).attr('aria-expanded') == 'false') {
      $j(this).attr('aria-expanded', 'true');
    } else {
      $j(this).attr('aria-expanded', 'false');
    };
  });
}

// Expand a filter section if it's in use, e.g. if I filtered for F/F, Include
// Categories section is expanded; if I filter by Word Count, Word Count section
// is expanded
function showFilters() {
  var filters = $j('.filters').find('dd.expandable');

  filters.each(function(index, filter) {
    // This should only apply to filter sections where the selected item is not
    // the default (i.e. don't expand Crossover if Include crossovers is 
    // selected), so we only want to look at inputs with an existing value 
    // attribute that is not blank
    // https://stackoverflow.com/questions/17248915/
    var inputs = $j(filter).find('input').filter('[value]:not([value=""])');
    var option_list_id = $j(filter).attr('id');
    var toggle_container = $j('#toggle_' + option_list_id);
    var toggle_button = $j('[aria-controls="' + option_list_id + '"]');

    inputs.each(function(index, input) {
      // We've already excluded inputs with blank values, so any 
      // text fields in this array will be non-blank and therefore need to be 
      //expanded
      if ($j(input).is(':checked, [type="text"]')) {
        $j(filter).removeClass('hidden');
        $j(toggle_container).removeClass('collapsed').addClass('expanded');
        $j(toggle_button).attr('aria-expanded', 'true');
      }
    });
  });
}

function setupNarrowScreenFilters() {
  var filters = $j('form.filters');
  var outer = $j('#outer');
  var show_link = $j('#go_to_filters');
  var hide_link = $j('#leave_filters');

  show_link.click(function(e) {
    e.preventDefault();
    filters.removeClass('narrow-hidden');
    outer.addClass('filtering');
    filters.find(':focusable').first().focus();
    filters.trap();
  });

  hide_link.click(function(e) {
    e.preventDefault();
    outer.removeClass('filtering');
    filters.addClass('narrow-hidden');
    show_link.focus();
  });
}
