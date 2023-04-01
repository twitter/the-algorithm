# Methods added to this helper will be available to all templates in the application.
module ApplicationHelper
  include HtmlCleaner

  # TODO: Official recommendation from Rails indicates we should switch to
  # unobtrusive JavaScript instead of using anything like `link_to_function`
  def link_to_function(name, *args, &block)
    html_options = args.extract_options!.symbolize_keys

    function = block_given? ? update_page(&block) : args[0] || ''

    onclick = "#{"#{html_options[:onclick]}; " if html_options[:onclick]}#{function}; return false;"
    href = html_options[:href] || 'javascript:void(0)'

    content_tag(:a, name, html_options.merge(href: href, onclick: onclick))
  end

  # Generates class names for the main div in the application layout
  def classes_for_main
    class_names = controller.controller_name + '-' + controller.action_name

    show_sidebar = ((@user || @admin_posts || @collection || show_wrangling_dashboard) && !@hide_dashboard)
    class_names += " dashboard" if show_sidebar

    if page_has_filters?
      class_names += " filtered"
    end

    if %w(abuse_reports feedbacks known_issues).include?(controller.controller_name)
      class_names = "system support " + controller.controller_name + ' ' + controller.action_name
    end
    if controller.controller_name == "archive_faqs"
      class_names = "system docs support faq " + controller.action_name
    end
    if controller.controller_name == "wrangling_guidelines"
      class_names = "system docs guideline " + controller.action_name
    end
    if controller.controller_name == "home"
      class_names = "system docs " + controller.action_name
    end
    if controller.controller_name == "errors"
      class_names = "system " + controller.controller_name + " error-" + controller.action_name
    end

    class_names
  end

  def page_has_filters?
    @facets.present? || (controller.action_name == 'index' && controller.controller_name == 'collections') || (controller.action_name == 'unassigned' && controller.controller_name == 'fandoms')
  end

  # This is used to make the current page we're on (determined by the path or by the specified condition) a span with class "current" and it allows us to add a title attribute to the link or the span
  def span_if_current(link_to_default_text, path, condition=nil, title_attribute_default_text=nil)
    is_current = condition.nil? ? current_page?(path) : condition
    span_tag = title_attribute_default_text.nil? ? "<span class=\"current\">#{link_to_default_text}</span>" : "<span class=\"current\" title=\"#{title_attribute_default_text}\">#{link_to_default_text}</span>"
    link_code = title_attribute_default_text.nil? ? link_to(link_to_default_text, path) : link_to(link_to_default_text, path, title: "#{title_attribute_default_text}")
    is_current ? span_tag.html_safe : link_code
  end

  def link_to_rss(link_to_feed)
    link_to content_tag(:span, ts("RSS Feed")), link_to_feed, title: ts("RSS Feed"), class: "rss"
  end

  # 1: default shows just the link to help
  # 2: show_text = true: shows "plain text with limited html" and link to help
  def allowed_html_instructions(show_text = true)
    (show_text ? h(ts("Plain text with limited HTML")) : "".html_safe) +
      link_to_help("html-help")
  end

  # Byline helpers
  def byline(creation, options={})
    if creation.respond_to?(:anonymous?) && creation.anonymous?
      anon_byline = ts("Anonymous").html_safe
      if options[:visibility] != "public" && (logged_in_as_admin? || is_author_of?(creation))
        anon_byline += " [#{non_anonymous_byline(creation, options[:only_path])}]".html_safe
      end
      return anon_byline
    end
    non_anonymous_byline(creation, options[:only_path])
  end

  def non_anonymous_byline(creation, url_path = nil)
    only_path = url_path.nil? ? true : url_path

    if @preview_mode
      # Skip cache in preview mode
      return byline_text(creation, only_path)
    end

    Rails.cache.fetch("#{creation.cache_key}/byline-nonanon/#{only_path.to_s}") do
      byline_text(creation, only_path)
    end
  end

  def byline_text(creation, only_path, text_only = false)
    if creation.respond_to?(:author)
      creation.author
    else
      pseuds = @preview_mode ? creation.pseuds_after_saving : creation.pseuds.to_a
      pseuds = pseuds.flatten.uniq.sort

      archivists = Hash.new []
      if creation.is_a?(Work)
        external_creatorships = creation.external_creatorships.select { |ec| !ec.claimed? }
        external_creatorships.each do |ec|
          archivist_pseud = pseuds.select { |p| ec.archivist.pseuds.include?(p) }.first
          archivists[archivist_pseud] += [ec.author_name]
        end
      end

      pseuds.map { |pseud|
        pseud_byline = text_only ? pseud.byline : pseud_link(pseud, only_path)
        if archivists[pseud].empty?
          pseud_byline
        else
          archivists[pseud].map { |ext_author|
            ts("%{ext_author} [archived by %{name}]", ext_author: ext_author, name: pseud_byline)
          }.join(', ')
        end
      }.join(', ').html_safe
    end
  end

  def pseud_link(pseud, only_path = true)
    if only_path
      link_to(pseud.byline, user_pseud_path(pseud.user, pseud), rel: "author")
    else
      link_to(pseud.byline, user_pseud_url(pseud.user, pseud), rel: "author")
    end
  end

  # A plain text version of the byline, for when we don't want to deliver a linkified version.
  def text_byline(creation, options={})
    if creation.respond_to?(:anonymous?) && creation.anonymous?
      anon_byline = ts("Anonymous")
      if (logged_in_as_admin? || is_author_of?(creation)) && options[:visibility] != 'public'
        anon_byline += " [#{non_anonymous_byline(creation)}]".html_safe
      end
      anon_byline
    else
      only_path = false
      text_only = true
      byline_text(creation, only_path, text_only)
    end
  end

  def link_to_modal(content = "", options = {})
    options[:class] ||= ""
    options[:for] ||= ""
    options[:title] ||= options[:for]

    html_options = { "class" => options[:class] + " modal", "title" => options[:title], "aria-controls" => "#modal" }
    link_to content, options[:for], html_options
  end

  # Currently, help files are static. We may eventually want to make these dynamic?
  def link_to_help(help_entry, link = '<span class="symbol question"><span>?</span></span>'.html_safe)
    help_file = ""
    #if Locale.active && Locale.active.language
    #  help_file = "#{ArchiveConfig.HELP_DIRECTORY}/#{Locale.active.language.code}/#{help_entry}.html"
    #end

    unless !help_file.blank? && File.exists?("#{Rails.root}/public/#{help_file}")
      help_file = "#{ArchiveConfig.HELP_DIRECTORY}/#{help_entry}.html"
    end

    " ".html_safe + link_to_modal(link, for: help_file, title: help_entry.split('-').join(' ').capitalize, class: "help symbol question").html_safe
  end

  # Inserts the flash alert messages for flash[:key] wherever
  #       <%= flash_div :key %>
  # is placed in the views. That is, if a controller or model sets
  #       flash[:error] = "OMG ERRORZ AIE"
  # or
  #       flash.now[:error] = "OMG ERRORZ AIE"
  #
  # then that error will appear in the view where you have
  #       <%= flash_div :error %>
  #
  # The resulting HTML will look like this:
  #       <div class="flash error">OMG ERRORZ AIE</div>
  #
  # The CSS classes are specified in system-messages.css.
  #
  # You can also have multiple possible flash alerts in a single location with:
  #       <%= flash_div :error, :caution, :notice %>
  # (These are the three varieties currently defined.)
  #
  def flash_div *keys
    keys.collect { |key|
      if flash[key]
        if flash[key].is_a?(Array)
          content_tag(:div, content_tag(:ul, flash[key].map { |flash_item| content_tag(:li, h(flash_item)) }.join("\n").html_safe), class: "flash #{key}")
        else
          content_tag(:div, h(flash[key]), class: "flash #{key}")
        end
      end
    }.join.html_safe
  end

  # Generates sorting links for index pages, with column names and directions
  def sort_link(title, column=nil, options = {})
    condition = options[:unless] if options.has_key?(:unless)

    unless column.nil?
      current_column = (params[:sort_column] == column.to_s) || params[:sort_column].blank? && options[:sort_default]
      css_class = current_column ? "current" : nil
      if current_column # explicitly or implicitly doing the existing sorting, so we need to toggle
        if params[:sort_direction]
          direction = params[:sort_direction].to_s.upcase == 'ASC' ? 'DESC' : 'ASC'
        else
          direction = options[:desc_default] ? 'ASC' : 'DESC'
        end
      else
        direction = options[:desc_default] ? 'DESC' : 'ASC'
      end
      link_to_unless condition, ((direction == 'ASC' ? '&#8593;&#160;' : '&#8595;&#160;') + title).html_safe,
          current_path_with(sort_column: column, sort_direction: direction), {class: css_class, title: (direction == 'ASC' ? ts('sort up') : ts('sort down'))}
    else
      link_to_unless params[:sort_column].nil?, title, current_path_with(sort_column: nil, sort_direction: nil)
    end
  end

  ## Allow use of tiny_mce WYSIWYG editor
  def use_tinymce
    @content_for_tinymce = ""
    content_for :tinymce do
      javascript_include_tag "tinymce/tinymce.min.js", skip_pipeline: true
    end
    @content_for_tinymce_init = ""
    content_for :tinymce_init do
      javascript_include_tag "mce_editor.min.js", skip_pipeline: true
    end
  end

  # check for pages that allow tiny_mce before loading the massive javascript
  def allow_tinymce?(controller)
    %w(admin_posts archive_faqs known_issues chapters works wrangling_guidelines).include?(controller.controller_name) &&
      %w(new create edit update).include?(controller.action_name)
  end

  # see: http://www.w3.org/TR/wai-aria/states_and_properties#aria-valuenow
  def generate_countdown_html(field_id, max)
    max = max.to_s
    span = content_tag(:span, max, id: "#{field_id}_counter", class: "value", "data-maxlength" => max, "aria-live" => "polite", "aria-valuemax" => max, "aria-valuenow" => field_id)
    content_tag(:p, span + ts(' characters left'), class: "character_counter")
  end

  # expand/contracts all expand/contract targets inside its nearest parent with the target class (usually index or listbox etc)
  def expand_contract_all(target="index")
    expand_all = content_tag(:a, ts("Expand All"), href: "#", class: "expand_all", "target_class" => target, role: "button")
    contract_all = content_tag(:a, ts("Contract All"), href: "#", class: "contract_all", "target_class" => target, role: "button")
    content_tag(:span, expand_all + "\n".html_safe + contract_all, class: "actions hidden showme", role: "menu")
  end

  # Sets up expand/contract/shuffle buttons for any list whose id is passed in
  # See the jquery code in application.js
  # Note that these start hidden because if javascript is not available, we
  # don't want to show the user the buttons at all.
  def expand_contract_shuffle(list_id, shuffle=true)
    ('<span class="action expand hidden" title="expand" action_target="#' + list_id + '"><a href="#" role="button">&#8595;</a></span>
    <span class="action contract hidden" title="contract" action_target="#' + list_id + '"><a href="#" role="button">&#8593;</a></span>').html_safe +
    (shuffle ? ('<span class="action shuffle hidden" title="shuffle" action_target="#' + list_id + '"><a href="#" role="button">&#8646;</a></span>') : '').html_safe
  end

  # returns the default autocomplete attributes, all of which can be overridden
  # note: we do this and put the message defaults here so we can use translation on them
  def autocomplete_options(method, options={})
    {
      class: "autocomplete",
      data: {
        autocomplete_method: (method.is_a?(Array) ? method.to_json : "/autocomplete/#{method}"),
        autocomplete_hint_text: ts("Start typing for suggestions!"),
        autocomplete_no_results_text: ts("(No suggestions found)"),
        autocomplete_min_chars: 1,
        autocomplete_searching_text: ts("Searching...")
      }
    }.deep_merge(options)
  end

  # see http://asciicasts.com/episodes/197-nested-model-form-part-2
  def link_to_add_section(linktext, form, nested_model_name, partial_to_render, locals = {})
    new_nested_model = form.object.class.reflect_on_association(nested_model_name).klass.new
    child_index = "new_#{nested_model_name}"
    rendered_partial_to_add =
      form.fields_for(nested_model_name, new_nested_model, child_index: child_index) {|child_form|
        render(partial: partial_to_render, locals: {form: child_form, index: child_index}.merge(locals))
      }
    link_to_function(linktext, "add_section(this, \"#{nested_model_name}\", \"#{escape_javascript(rendered_partial_to_add)}\")", class: "hidden showme")
  end

  # see above
  def link_to_remove_section(linktext, form, class_of_section_to_remove="removeme")
    form.hidden_field(:_destroy) + "\n" +
    link_to_function(linktext, "remove_section(this, \"#{class_of_section_to_remove}\")", class: "hidden showme")
  end

  def time_in_zone(time, zone = nil, user = User.current_user)
    return ts("(no time specified)") if time.blank?

    zone ||= (user&.is_a?(User) && user.preference.time_zone) ? user.preference.time_zone : Time.zone.name
    time_in_zone = time.in_time_zone(zone)
    time_in_zone_string = time_in_zone.strftime('<abbr class="day" title="%A">%a</abbr> <span class="date">%d</span>
                                                 <abbr class="month" title="%B">%b</abbr> <span class="year">%Y</span>
                                                 <span class="time">%I:%M%p</span>') +
                          " <abbr class=\"timezone\" title=\"#{zone}\">#{time_in_zone.zone}</abbr> "

    user_time_string = ""
    if user.is_a?(User) && user.preference.time_zone
      if user.preference.time_zone != zone
        user_time = time.in_time_zone(user.preference.time_zone)
        user_time_string = "(" + user_time.strftime('<span class="time">%I:%M%p</span>') +
                           " <abbr class=\"timezone\" title=\"#{user.preference.time_zone}\">#{user_time.zone}</abbr>)"
      elsif !user.preference.time_zone
        user_time_string = link_to ts("(set timezone)"), user_preferences_path(user)
      end
    end

    (time_in_zone_string + user_time_string).strip.html_safe
  end

  def mailto_link(user, options={})
    "<a href=\"mailto:#{h(user.email)}?subject=[#{ArchiveConfig.APP_SHORT_NAME}]#{options[:subject]}\" class=\"mailto\">
      <img src=\"/images/envelope_icon.gif\" alt=\"email #{h(user.login)}\">
    </a>".html_safe
  end

  # these two handy methods will take a form object (eg from form_for) and an attribute (eg :title or '_destroy')
  # and generate the id or name that Rails will output for that object
  def field_attribute(attribute)
    attribute.to_s.sub(/\?$/,"")
  end

  def name_to_id(name)
    name.to_s.gsub(/\]\[|[^-a-zA-Z0-9:.]/, "_").sub(/_$/, "")
  end

  def field_id(form, attribute)
    name_to_id(field_name(form, attribute))
  end

  def field_name(form, attribute)
    "#{form.object_name}[#{field_attribute(attribute)}]"
  end

  # toggle an checkboxes (scrollable checkboxes) section of a form to show all of the checkboxes
  def checkbox_section_toggle(checkboxes_id, checkboxes_size, options = {})
    toggle_show = content_tag(:a, ts("Expand %{checkboxes_size} Checkboxes", checkboxes_size: checkboxes_size),
                              class: "toggle #{checkboxes_id}_show") + "\n".html_safe

    toggle_hide = content_tag(:a, ts("Collapse Checkboxes"),
                                  style: "display: none;",
                                  class: "toggle #{checkboxes_id}_hide",
                                  href: "##{checkboxes_id}") + "\n".html_safe

    css_class = checkbox_section_css_class(checkboxes_size)

    javascript_bits = content_for(:footer_js) {
      javascript_tag("$j(document).ready(function(){\n" +
        "$j('##{checkboxes_id}').find('.actions').show();\n" +
        "$j('.#{checkboxes_id}_show').click(function() {\n" +
          "$j('##{checkboxes_id}').find('.index').attr('class', 'options index all');\n" +
          "$j('.#{checkboxes_id}_hide').show();\n" +
          "$j('.#{checkboxes_id}_show').hide();\n" +
        "});" + "\n" +
        "$j('.#{checkboxes_id}_hide').click(function() {\n" +
          "$j('##{checkboxes_id}').find('.index').attr('class', '#{css_class}');\n" +
          "$j('.#{checkboxes_id}_show').show();\n" +
          "$j('.#{checkboxes_id}_hide').hide();\n" +
        "});\n" +
      "})")
    }

    toggle = content_tag(:p,
      (options[:no_show] ? "".html_safe : toggle_show) +
      toggle_hide +
      (options[:no_js] ? "".html_safe : javascript_bits), class: "actions", style: "display: none;")
  end

  # create a scrollable checkboxes section for a form that can be toggled open/closed
  # form: the form this is being created in
  # attribute: the attribute being set
  # choices: the array of options (which should be objects of some sort)
  # checked_method: a method that can be run on the object of the form to get back a list
  #         of currently-set options
  # name_method: a method that can be run on each individual option to get its pretty name for labelling (typically just "name")
  # value_method: a value that can be run to get the value of each individual option
  #
  #
  # See the prompt_form in challenge signups for example of usage
  def checkbox_section(form, attribute, choices, options = {})
    options = {
      checked_method: nil,
      name_method: "name",
      name_helper_method: nil, # alternative: pass a helper method that gets passed the choice
      extra_info_method: nil, # helper method that gets passed the choice, for any extra information that gets attached to the label
      value_method: "id",
      disabled: false,
      include_toggle: true,
      checkbox_side: "left",
      include_blank: true,
      concise: false # specify concise to invoke alternate formatting for skimmable lists (two-column in default layout)
    }.merge(options)

    field_name = options[:field_name] || field_name(form, attribute)
    field_name += '[]'
    base_id = options[:field_id] || field_id(form, attribute)
    checkboxes_id = "#{base_id}_checkboxes"
    opts = options[:disabled] ? {disabled: "true"} : {}
    already_checked = case
      when options[:checked_method].is_a?(Array)
        options[:checked_method]
      when options[:checked_method].nil?
        []
      else
        form.object.send(options[:checked_method]) || []
      end

    checkboxes = choices.map do |choice|
      is_checked = !options[:checked_method] || already_checked.empty? ? false : already_checked.include?(choice)
      display_name = case
        when options[:name_helper_method]
          eval("#{options[:name_helper_method]}(choice)")
        else
          choice.send(options[:name_method]).html_safe
        end
      value = choice.send(options[:value_method])
      checkbox_id = "#{base_id}_#{name_to_id(value)}"
      checkbox = check_box_tag(field_name, value, is_checked, opts.merge({id: checkbox_id}))
      checkbox_and_label = label_tag checkbox_id, class: "action" do
        options[:checkbox_side] == "left" ? checkbox + display_name : display_name + checkbox
      end
      if options[:extra_info_method]
        checkbox_and_label = options[:checkbox_side] == "left" ? checkbox_and_label + eval("#{options[:extra_info_method]}(choice)") : eval("#{options[:extra_info_method]}(choice)") + checkbox_and_label
      end
      content_tag(:li, checkbox_and_label)
    end.join("\n").html_safe

    # if there are only a few choices, don't show the scrolling and the toggle
    size = choices.size
    css_class = checkbox_section_css_class(size, options[:concise])
    checkboxes_ul = content_tag(:ul, checkboxes, class: css_class)

    toggle = "".html_safe
    if options[:include_toggle] && !options[:concise] && size > (ArchiveConfig.OPTIONS_TO_SHOW * 6)
      toggle = checkbox_section_toggle(checkboxes_id, size)
    end

    # We wrap the whole thing in a div
    return content_tag(:div, checkboxes_ul + toggle + (options[:include_blank] ? hidden_field_tag(field_name, " ") : ''.html_safe), id: checkboxes_id)
  end

  def checkbox_section_css_class(size, concise=false)
    css_class = "options index group"

    if concise
      css_class += " concise lots" if size > ArchiveConfig.OPTIONS_TO_SHOW
    else
      css_class += " many" if size > ArchiveConfig.OPTIONS_TO_SHOW
      css_class += " lots" if size > (ArchiveConfig.OPTIONS_TO_SHOW * 6)
    end

    css_class
  end

  def check_all_none(all_text="All", none_text="None", id_filter=nil)
    filter_attrib = (id_filter ? " data-checkbox-id-filter=\"#{id_filter}\"" : '')
    ('<ul class="actions">
      <li><a href="#" class="check_all"' +
      "#{filter_attrib}>#{all_text}</a></li>" +
      '<li><a href="#" class="check_none"' +
      "#{filter_attrib}>#{none_text}</a></li></ul>").html_safe
  end

  def submit_button(form=nil, button_text=nil)
    button_text ||= (form.nil? || form.object.nil? || form.object.new_record?) ? ts("Submit") : ts("Update")
    content_tag(:p, (form.nil? ? submit_tag(button_text) : form.submit(button_text)), class: "submit")
  end

  def submit_fieldset(form=nil, button_text=nil)
    content_tag(:fieldset, content_tag(:legend, ts("Actions")) + submit_button(form, button_text))
  end

  def first_paragraph(full_text, placeholder_text = 'No preview available.')
    # is there a paragraph that does not have a child image?
    paragraph = Nokogiri::HTML.parse(full_text).at_xpath('//p[not(img)]')
    if paragraph.present?
      # if so, get its text and put it in a fresh p tag
      paragraph_text = paragraph.text
      return content_tag(:p, paragraph_text)
    else
      # if not, put the placeholder text in a p tag with the placeholder class
      return content_tag(:p, ts(placeholder_text), class: 'placeholder')
    end
  end

  # change the default link renderer for will_paginate
  def will_paginate(collection_or_options = nil, options = {})
    if collection_or_options.is_a? Hash
      options = collection_or_options
      collection_or_options = nil
    end
    unless options[:renderer]
      options = options.merge renderer: PaginationListLinkRenderer
    end
    super(*[collection_or_options, options].compact)
  end

  # spans for nesting a checkbox or radio button inside its label to make custom
  # checkbox or radio designs
  def label_indicator_and_text(text)
    content_tag(:span, "", class: "indicator", "aria-hidden": "true") + content_tag(:span, text)
  end

  # Display a collection of radio buttons, wrapped in an unordered list.
  #
  # The parameter option_array should be a list of pairs, where the first
  # element in each pair is the radio button's value, and the second element in
  # each pair is the radio button's label.
  def radio_button_list(form, field_name, option_array)
    content_tag(:ul) do
      form.collection_radio_buttons(field_name, option_array, :first, :second,
                                    include_hidden: false) do |builder|
        content_tag(:li, builder.label { builder.radio_button + builder.text })
      end
    end
  end

  # Identifier for creation, formatted external-work-12, series-12, work-12.
  def creation_id_for_css_classes(creation)
    return unless %w[ExternalWork Series Work].include?(creation.class.name)

    "#{creation.class.name.underscore.dasherize}-#{creation.id}"
  end

  # Array of creator ids, formatted user-123, user-126.
  # External works are not created by users, so we can skip this.
  def creator_ids_for_css_classes(creation)
    return [] unless %w[Series Work].include?(creation.class.name)
    return [] if creation.anonymous?
    # Although series.unrevealed? can be true, the creators are not concealed
    # in the blurb. Therefore, we do not need special handling for unrevealed
    # series.
    return [] if creation.is_a?(Work) && creation.unrevealed?

    creation.users.pluck(:id).uniq.map { |id| "user-#{id}" }
  end

  def css_classes_for_creation_blurb(creation)
    return if creation.nil?

    Rails.cache.fetch("#{creation.cache_key_with_version}/blurb_css_classes-v2") do
      creation_id = creation_id_for_css_classes(creation)
      creator_ids = creator_ids_for_css_classes(creation).join(" ")
      "blurb group #{creation_id} #{creator_ids}".strip
    end
  end

  # Returns the current path, with some modified parameters. Modeled after
  # WillPaginate::ActionView::LinkRenderer to try to prevent any additional
  # security risks.
  def current_path_with(**kwargs)
    # Only throw in the query params if this is a GET request, because POST and
    # such don't pass their params in the URL.
    path_params = if request.get? || request.head?
                    permit_all_except(params, [:script_name, :original_script_name])
                  else
                    {}
                  end

    path_params.deep_merge!(kwargs)
    path_params[:only_path] = true # prevent shenanigans

    url_for(path_params)
  end

  # Creates a new hash with all keys except those marked as blocked.
  #
  # This is a bit of a hack, but without this we'd have to either (a) make a
  # list of all permitted params each time current_path_with is called, or (b)
  # call params.permit! and effectively disable strong parameters for any code
  # called after current_path_with.
  def permit_all_except(params, blocked_keys)
    if params.respond_to?(:each_pair)
      {}.tap do |result|
        params.each_pair do |key, value|
          key = key.to_sym
          next if blocked_keys.include?(key)

          result[key] = permit_all_except(value, blocked_keys)
        end
      end
    elsif params.respond_to?(:map)
      params.map do |entry|
        permit_all_except(entry, blocked_keys)
      end
    else # not a hash or an array, just a flat value
      params
    end
  end

  def disallow_robots?(item)
    return unless item

    if item.is_a?(User)
      item.preference&.minimize_search_engines?
    elsif item.respond_to?(:users)
      item.users.all? { |u| u&.preference&.minimize_search_engines? }
    end
  end
end # end of ApplicationHelper
