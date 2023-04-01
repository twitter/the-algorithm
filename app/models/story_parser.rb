# Parse stories from other websites and uploaded files, looking for metadata to harvest
# and put into the archive.
#
class StoryParser
  require 'timeout'
  require 'nokogiri'
  require 'mechanize'
  require 'open-uri'
  include HtmlCleaner

  OPTIONAL_META = {notes: 'Note',
                   freeform_string: 'Tag',
                   fandom_string: 'Fandom',
                   rating_string: 'Rating',
                   archive_warning_string: 'Warning',
                   relationship_string: 'Relationship|Pairing',
                   character_string: 'Character' }.freeze
  REQUIRED_META = { title: 'Title',
                    summary: 'Summary',
                    revised_at: 'Date|Posted|Posted on|Posted at',
                    chapter_title: 'Chapter Title' }.freeze

  # Use this for raising custom error messages
  # (so that we can distinguish them from unexpected exceptions due to
  # faulty code)
  class Error < StandardError
  end

  # These attributes need to be moved from the work to the chapter
  # format: {work_attribute_name: :chapter_attribute_name} (can be the same)
  CHAPTER_ATTRIBUTES_ONLY = {}

  # These attributes need to be copied from the work to the chapter
  CHAPTER_ATTRIBUTES_ALSO = { revised_at: :published_at }.freeze

  ### NOTE ON KNOWN SOURCES
  # These lists will stop with the first one it matches, so put more-specific matches
  # towards the front of the list.

  # places for which we have a custom parse_story_from_[source] method
  # for getting information out of the downloaded text
  KNOWN_STORY_PARSERS = %w[deviantart dw lj].freeze

  # places for which we have a custom parse_author_from_[source] method
  # which returns an external_author object including an email address
  KNOWN_AUTHOR_PARSERS = %w[lj].freeze

  # places for which we have a download_story_from_[source]
  # used to customize the downloading process
  KNOWN_STORY_LOCATIONS = %w[lj].freeze

  # places for which we have a download_chaptered_from
  # to get a set of chapters all together
  CHAPTERED_STORY_LOCATIONS = %w[ffnet thearchive_net efiction quotev].freeze

  # regular expressions to match against the URLS
  SOURCE_LJ = '((live|dead|insane)journal\.com)|journalfen(\.net|\.com)|dreamwidth\.org'.freeze
  SOURCE_DW = 'dreamwidth\.org'.freeze
  SOURCE_FFNET = '(^|[^A-Za-z0-9-])fanfiction\.net'.freeze
  SOURCE_DEVIANTART = 'deviantart\.com'.freeze
  SOURCE_THEARCHIVE_NET = 'the\-archive\.net'.freeze
  SOURCE_EFICTION = 'viewstory\.php'.freeze
  SOURCE_QUOTEV = 'quotev\.com'.freeze

  # time out if we can't download fast enough
  STORY_DOWNLOAD_TIMEOUT = 60
  MAX_CHAPTER_COUNT = 200

  # To check for duplicate chapters, take a slice this long out of the story
  # (in characters)
  DUPLICATE_CHAPTER_LENGTH = 10_000


  # Import many stories
  def import_many(urls, options = {})
    # Try to get the works
    works = []
    failed_urls = []
    errors = []
    @options = options
    urls.each do |url|
      begin
        response = download_and_parse_work(url, options)
        work = response[:work]
        if response[:status] == :created
          if work && work.save
            work.chapters.each(&:save)
            works << work
          else
            failed_urls << url
            errors << work.errors.values.join(", ")
            work.delete if work
          end
        elsif response[:status] == :already_imported
          raise StoryParser::Error, response[:message]
        end
      rescue Timeout::Error
        failed_urls << url
        errors << "Import has timed out. This may be due to connectivity problems with the source site. Please try again in a few minutes, or check Known Issues to see if there are import problems with this site."
        work.delete if work
      rescue Error => exception
        failed_urls << url
        errors << "We couldn't successfully import that work, sorry: #{exception.message}"
        work.delete if work
      end
    end
    [works, failed_urls, errors]
  end

  # Downloads a story and passes it on to the parser.
  # If the URL of the story is from a site for which we have special rules
  # (eg, downloading from a livejournal clone, you want to use ?format=light
  # to get a nice and consistent post format), it will pre-process the url
  # according to the rules for that site.
  def download_and_parse_work(location, options = {})
    status = :created
    message = ""
    work = Work.find_by_url(location)
    if work.nil?
      @options = options
      source = get_source_if_known(CHAPTERED_STORY_LOCATIONS, location)
      if source.nil?
        story = download_text(location)
        work = parse_story(story, location, options)
      else
        work = download_and_parse_chaptered_story(source, location, options)
      end
    else
      status = :already_imported
      message = "A work has already been imported from #{location}."
    end
    {
      status: status,
      message: message,
      work: work
    }
  end

  # Given an array of urls for chapters of a single story,
  # download them all and combine into a single work
  def import_chapters_into_story(locations, options = {})
    status = :created
    work = Work.find_by_url(locations.first)
    if work.nil?
      chapter_contents = []
      @options = options
      locations.each do |location|
        chapter_contents << download_text(location)
      end
      work = parse_chapters_into_story(locations.first, chapter_contents, options)
      message = "Successfully created work \"" + work.title + "\"."
    else
      status = :already_imported
      message = "A work has already been imported from #{locations.first}."
    end
    {
      status: status,
      message: message,
      work: work
    }
  end


  ### OLD PARSING METHODS

  # Import many stories
  def import_from_urls(urls, options = {})
    # Try to get the works
    works = []
    failed_urls = []
    errors = []
    @options = options
    urls.each do |url|
      begin
        work = download_and_parse_story(url, options)
        if work && work.save
          work.chapters.each(&:save)
          works << work
        else
          failed_urls << url
          errors << work.errors.values.join(", ")
          work.delete if work
        end
      rescue Timeout::Error
        failed_urls << url
        errors << "Import has timed out. This may be due to connectivity problems with the source site. Please try again in a few minutes, or check Known Issues to see if there are import problems with this site."
        work.delete if work
      rescue Error => exception
        failed_urls << url
        errors << "We couldn't successfully import that work, sorry: #{exception.message}"
        work.delete if work
      end
    end
    [works, failed_urls, errors]
  end

  # Downloads a story and passes it on to the parser.
  # If the URL of the story is from a site for which we have special rules
  # (eg, downloading from a livejournal clone, you want to use ?format=light
  # to get a nice and consistent post format), it will pre-process the url
  # according to the rules for that site.
  def download_and_parse_story(location, options = {})
    check_for_previous_import(location)
    @options = options
    source = get_source_if_known(CHAPTERED_STORY_LOCATIONS, location)
    if source.nil?
      story = download_text(location)
      work = parse_story(story, location, options)
    else
      work = download_and_parse_chaptered_story(source, location, options)
    end
    work
  end

  # Given an array of urls for chapters of a single story,
  # download them all and combine into a single work
  def download_and_parse_chapters_into_story(locations, options = {})
    check_for_previous_import(locations.first)
    chapter_contents = []
    @options = options
    locations.each do |location|
      chapter_contents << download_text(location)
    end
    parse_chapters_into_story(locations.first, chapter_contents, options)
  end

  ### PARSING METHODS

  # Parses the text of a story, optionally from a given location.
  def parse_story(story, location, options = {})
    work_params = parse_common(story, location, options[:encoding], options[:detect_tags])

    # move any attributes from work to chapter if necessary
    set_work_attributes(Work.new(work_params), location, options)
  end

  # parses and adds a new chapter to the end of the work
  def parse_chapter_of_work(work, chapter_content, location, options = {})
    tmp_work_params = parse_common(chapter_content, location, options[:encoding], options[:detect_tags])
    chapter = get_chapter_from_work_params(tmp_work_params)
    work.chapters << set_chapter_attributes(work, chapter)
    work
  end

  def parse_chapters_into_story(location, chapter_contents, options = {})
    work = nil
    chapter_contents.each do |content|
      work_params = parse_common(content, location, options[:encoding], options[:detect_tags])
      if work.nil?
        # create the new work
        work = Work.new(work_params)
      else
        new_chapter = get_chapter_from_work_params(work_params)
        work.chapters << set_chapter_attributes(work, new_chapter)
      end
    end
    set_work_attributes(work, location, options)
  end

  # Everything below here is protected and should not be touched by outside
  # code -- please use the above functions to parse external works.

  protected

  # tries to create an external author for a given url
  def parse_author(location, ext_author_name, ext_author_email)
    if location.present? && ext_author_name.blank? && ext_author_email.blank?
      source = get_source_if_known(KNOWN_AUTHOR_PARSERS, location)
      if source.nil?
        raise Error, "No external author name or email specified"
      else
        send("parse_author_from_#{source.downcase}", location)
      end
    else
      parse_author_common(ext_author_email, ext_author_name)
    end
  end

  # download an entire story from an archive type where we know how to parse multi-chaptered works
  # this should only be called from download_and_parse_story
  def download_and_parse_chaptered_story(source, location, options = {})
    chapter_contents = send("download_chaptered_from_#{source.downcase}", location)
    parse_chapters_into_story(location, chapter_contents, options)
  end

  # our custom url finder checks for previously imported URL in almost any format it may have been presented
  def check_for_previous_import(location)
    if Work.find_by_url(location).present?
      raise Error, "A work has already been imported from #{location}."
    end
  end

  def set_chapter_attributes(work, chapter)
    chapter.position = work.chapters.length + 1
    chapter.posted = true
    chapter
  end

  def set_work_attributes(work, location = "", options = {})
    raise Error, "Work could not be downloaded" if work.nil?

    @options = options
    work.imported_from_url = location
    work.ip_address = options[:ip_address]
    work.expected_number_of_chapters = work.chapters.length
    work.revised_at = work.chapters.last.published_at
    if work.revised_at && work.revised_at.to_date < Date.current
      work.backdate = true
    end

    # set authors for the works
    pseuds = []
    pseuds << User.current_user.default_pseud unless options[:do_not_set_current_author] || User.current_user.nil?
    pseuds << options[:archivist].default_pseud if options[:archivist]
    pseuds << options[:pseuds] if options[:pseuds]
    pseuds = pseuds.flatten.compact.uniq
    raise Error, "A work must have at least one author specified" if pseuds.empty?
    pseuds.each do |pseud|
      work.creatorships.build(pseud: pseud, enable_notifications: true)
      work.chapters.each do |chapter|
        chapter.creatorships.build(pseud: pseud)
      end
    end

    # handle importing works for others
    # build an external creatorship for each author
    if options[:importing_for_others]
      external_author_names = options[:external_author_names] || parse_author(location, options[:external_author_name], options[:external_author_email])
      # convert to an array if not already one
      external_author_names = [external_author_names] if external_author_names.is_a?(ExternalAuthorName)
      if options[:external_coauthor_name].present?
        external_author_names << parse_author(location, options[:external_coauthor_name], options[:external_coauthor_email])
      end
      external_author_names.each do |external_author_name|
        next if !external_author_name || external_author_name.external_author.blank?
        if external_author_name.external_author.do_not_import
          # we're not allowed to import works from this address
          raise Error, "Author #{external_author_name.name} at #{external_author_name.external_author.email} does not allow importing their work to this archive."
        end
        work.external_creatorships.build(external_author_name: external_author_name, archivist: (options[:archivist] || User.current_user))
      end
    end

    # lock to registered users if specified or importing for others
    work.restricted = options[:restricted] || options[:importing_for_others] || false

    # set default values for required tags
    work.fandom_string = meta_or_default(work.fandom_string, options[:fandom], ArchiveConfig.FANDOM_NO_TAG_NAME)
    work.rating_string = meta_or_default(work.rating_string, options[:rating], ArchiveConfig.RATING_DEFAULT_TAG_NAME)
    work.archive_warning_strings = meta_or_default(work.archive_warning_strings, options[:archive_warning], ArchiveConfig.WARNING_DEFAULT_TAG_NAME)
    work.category_string = meta_or_default(work.category_string, options[:category], [])
    work.character_string = meta_or_default(work.character_string, options[:character], [])
    work.relationship_string = meta_or_default(work.relationship_string, options[:relationship], [])
    work.freeform_string = meta_or_default(work.freeform_string, options[:freeform], [])

    # set default value for title
    work.title = meta_or_default(work.title, options[:title], "Untitled Imported Work")
    work.summary = meta_or_default(work.summary, options[:summary], '')
    work.notes = meta_or_default(work.notes, options[:notes], '')

    # set collection name if present
    work.collection_names = get_collection_names(options[:collection_names]) if options[:collection_names].present?

    # set default language (English)
    work.language_id = options[:language_id] || Language.default.id

    work.posted = true if options[:post_without_preview]
    work.chapters.each do |chapter|
      if chapter.content.length > ArchiveConfig.CONTENT_MAX
        # TODO: eventually: insert a new chapter
        chapter.content.truncate(ArchiveConfig.CONTENT_MAX, omission: "<strong>WARNING: import truncated automatically because chapter was too long! Please add a new chapter for remaining content.</strong>", separator: "</p>")
      elsif chapter.content.empty?
        raise Error, "Chapter #{chapter.position} of \"#{work.title}\" is blank."
      end

      chapter.posted = true # do not save - causes the chapters to exist even if work doesn't get created!
    end
    work
  end

  def parse_author_from_lj(location)
    return if location !~ %r{^(?:http:\/\/)?(?<lj_name>[^.]*).(?<site_name>livejournal\.com|dreamwidth\.org|insanejournal\.com|journalfen.net)}
    email = ""
    lj_name = Regexp.last_match[:lj_name]
    site_name = Regexp.last_match[:site_name]
    if lj_name == "community"
      # whups
      post_text = download_text(location)
      doc = Nokogiri.parse(post_text)
      lj_name = doc.xpath("/html/body/div[2]/div/div/div/table/tbody/tr/td[2]/span/a[2]/b").content
    end
    profile_url = "http://#{lj_name}.#{site_name}/profile"
    lj_profile = download_text(profile_url)
    doc = Nokogiri.parse(lj_profile)
    contact = doc.css('div.contact').inner_html
    if contact.present?
      contact.gsub! '<p class="section_body_title">Contact:</p>', ""
      contact.gsub! /<\/?(span|i)>/, ""
      contact.delete! "\n"
      contact.gsub! "<br/>", ""
      if contact =~ /(.*@.*\..*)/
        email = Regexp.last_match[1]
      end
    end
    email = "#{lj_name}@#{site_name}" if email.blank?
    parse_author_common(email, lj_name)
  end

  def parse_author_from_unknown(_location)
    # for now, nothing
    nil
  end

  def parse_author_common(email, name)
    if name.present? && email.present?
      # convert to ASCII and strip out invalid characters (everything except alphanumeric characters, _, @ and -)
      name = name.to_ascii.gsub(/[^\w[ \-@.]]/u, "")
      external_author = ExternalAuthor.find_or_create_by(email: email)
      external_author_name = external_author.default_name
      unless name.blank?
        external_author_name = ExternalAuthorName.where(name: name, external_author_id: external_author.id).first ||
                               ExternalAuthorName.new(name: name)
        external_author.external_author_names << external_author_name
        external_author.save
      end
      external_author_name
    else
      messages = []
      messages << "No author name specified" if name.blank?
      messages << "No author email specified" if email.blank?
      raise Error, messages.join("\n")
    end
  end

  def get_chapter_from_work_params(work_params)
    @chapter = Chapter.new(work_params[:chapter_attributes])
    # don't override specific chapter params (eg title) with work params
    chapter_params = work_params.delete_if do |name, _param|
      !@chapter.attribute_names.include?(name.to_s) || !@chapter.send(name.to_s).blank?
    end
    @chapter.update(chapter_params)
    @chapter
  end

  def download_text(location)
    source = get_source_if_known(KNOWN_STORY_LOCATIONS, location)
    if source.nil?
      download_with_timeout(location)
    else
      send("download_from_#{source.downcase}", location)
    end
  end

  # canonicalize the url for downloading from lj or clones
  def download_from_lj(location)
    url = location
    url.gsub!(/\#(.*)$/, "") # strip off any anchor information
    url.gsub!(/\?(.*)$/, "") # strip off any existing params at the end
    url.gsub!('_', '-') # convert underscores in usernames to hyphens
    url += "?format=light" # go to light format
    text = download_with_timeout(url)

    if text.match(/adult_check/)
      Timeout::timeout(STORY_DOWNLOAD_TIMEOUT) {
        begin
          agent = Mechanize.new
          url.include?("dreamwidth") ? form = agent.get(url).forms.first : form = agent.get(url).forms.third
          page = agent.submit(form, form.buttons.first) # submits the adult concepts form
          text = page.body.force_encoding(agent.page.encoding)
        rescue
          text = ""
        end
      }
    end
    text
  end

  # grab all the chapters of the story from ff.net
  def download_chaptered_from_ffnet(_location)
    raise Error, "Sorry, Fanfiction.net does not allow imports from their site."
  end

  def download_chaptered_from_quotev(_location)
    raise Error, "Sorry, Quotev.com does not allow imports from their site."
  end

  # this is an efiction archive but it doesn't handle chapters normally
  # best way to handle is to get the full story printable version
  # We have to make it a download-chaptered because otherwise it gets sent to the
  #  generic efiction version since chaptered sources are checked first
  def download_chaptered_from_thearchive_net(location)
    if location.match(/^(.*)\/.*viewstory\.php.*[^p]sid=(\d+)($|&)/i)
      location = "#{$1}/viewstory.php?action=printable&psid=#{$2}"
    end
    text = download_with_timeout(location)
    text.sub!('</style>', '</style></head>') unless text.match('</head>')
    [text]
  end

  # grab all the chapters of a story from an efiction-based site
  def download_chaptered_from_efiction(location)
    chapter_contents = []
    if location.match(/^(?<site>.*)\/.*viewstory\.php.*sid=(?<storyid>\d+)($|&)/i)
      site = Regexp.last_match[:site]
      storyid = Regexp.last_match[:storyid]
      chapnum = 1
      last_body = ""
      Timeout::timeout(STORY_DOWNLOAD_TIMEOUT) do
        loop do
          url = "#{site}/viewstory.php?action=printable&sid=#{storyid}&chapter=#{chapnum}"
          body = download_with_timeout(url)
          # get a section to check that this isn't a duplicate of previous chapter
          body_to_check = body.slice(10, DUPLICATE_CHAPTER_LENGTH)
          if body.nil? || body_to_check == last_body || chapnum > MAX_CHAPTER_COUNT || body.match(/<div class='chaptertitle'> by <\/div>/) || body.match(/Access denied./) || body.match(/Chapter : /)
            break
          end
          # save the value to check for duplicate chapter
          last_body = body_to_check

          # clean up the broken head in many efiction printable sites
          body.sub!('</style>', '</style></head>') unless body.match('</head>')
          chapter_contents << body
          chapnum += 1
        end
      end
    end
    chapter_contents
  end


  # This is the heavy lifter, invoked by all the story and chapter parsers.
  # It takes a single string containing the raw contents of a story, parses it with
  # Nokogiri into the @doc object, and then and calls a subparser.
  #
  # If the story source can be identified as one of the sources we know how to parse in some custom/
  # special way, parse_common calls the customized parse_story_from_[source] method.
  # Otherwise, it falls back to parse_story_from_unknown.
  #
  # This produces a hash equivalent to the params hash that is normally created by the standard work
  # upload form.
  #
  # parse_common then calls sanitize_params (which would also be called on the standard work upload
  # form results) and returns the final sanitized hash.
  #
  def parse_common(story, location = nil, encoding = nil, detect_tags = true)
    work_params = { title: "Untitled Imported Work", chapter_attributes: { content: "" } }

    # Encode as HTML - the dummy "foo" tag will be stripped out by the sanitizer but forces Nokogiri to
    # preserve line breaks in plain text documents
    # Rescue all errors as Nokogiri complains about things the sanitizer will fix later
    @doc = Nokogiri::HTML.parse(story.prepend("<foo/>"), nil, encoding) rescue ""

    # Try to convert all relative links to absolute
    base = @doc.at_css("base") ? @doc.css("base")[0]["href"] : location.split("?").first
    if base.present?
      @doc.css("a").each do |link|
        next if link["href"].blank? || link["href"].start_with?("#")
        begin
          query = link["href"].match(/(\?.*)$/) ? $1 : ""
          link["href"] = URI.join(base, link["href"].gsub(/(\?.*)$/, "")).to_s + query
        rescue
# ignored
        end
      end
    end

    # Extract metadata (unless detect_tags is false)
    if location && (source = get_source_if_known(KNOWN_STORY_PARSERS, location))
      params = send("parse_story_from_#{source.downcase}", story, detect_tags)
      work_params.merge!(params)
    else
      work_params.merge!(parse_story_from_unknown(story, detect_tags))
    end

    shift_chapter_attributes(sanitize_params(work_params))
  end

  # our fallback: parse a story from an unknown source, so we have no special
  # rules.
  def parse_story_from_unknown(story, detect_tags = true)
    work_params = { chapter_attributes: {} }
    story_head = ""
    story_head = @doc.css("head").inner_html if @doc.css("head")

    # Story content - Look for progressively less specific containers or grab everything
    element = @doc.at_css('.chapter-content') || @doc.at_css('body') || @doc.at_css('html') || @doc
    storytext = element ? element.inner_html : story

    meta = {}
    meta.merge!(scan_text_for_meta(story_head, detect_tags)) unless story_head.blank?
    meta.merge!(scan_text_for_meta(story, detect_tags))
    meta[:title] ||= @doc.css('title').inner_html
    work_params[:chapter_attributes][:title] = meta.delete(:chapter_title)
    work_params[:chapter_attributes][:content] = clean_storytext(storytext)
    work_params.merge!(meta)
  end

  # Parses a story from livejournal or a livejournal equivalent (eg, dreamwidth, insanejournal)
  # Assumes that we have downloaded the story from one of those equivalents (ie, we've downloaded
  # it in format=light which is a stripped-down plaintext version.)
  #
  def parse_story_from_lj(_story, detect_tags = true)
    work_params = { chapter_attributes: {} }

    # in LJ "light" format, the story contents are in the second div
    # inside the body.
    body = @doc.css("body")
    storytext = body.css("article.b-singlepost-body").inner_html
    storytext = body.inner_html if storytext.empty?

    # cleanup the text
    # storytext.gsub!(/<br\s*\/?>/i, "\n") # replace the breaks with newlines
    storytext = clean_storytext(storytext)

    work_params[:chapter_attributes][:content] = storytext
    work_params[:title] = @doc.css("title").inner_html
    work_params[:title].gsub! /^[^:]+: /, ""
    work_params.merge!(scan_text_for_meta(storytext, detect_tags))

    date = @doc.css("time.b-singlepost-author-date")
    unless date.empty?
      work_params[:revised_at] = convert_revised_at(date.first.inner_text)
    end

    work_params
  end

  def parse_story_from_dw(_story, detect_tags = true)
    work_params = { chapter_attributes: {} }

    body = @doc.css("body")
    content_divs = body.css("div.contents")

    if content_divs[0].present?
      # Get rid of the DW metadata table
      content_divs[0].css("div.currents, ul.entry-management-links, div.header.inner, span.restrictions, h3.entry-title").each(&:remove)
      storytext = content_divs[0].inner_html
    else
      storytext = body.inner_html
    end

    # cleanup the text
    storytext = clean_storytext(storytext)

    work_params[:chapter_attributes][:content] = storytext
    work_params[:title] = @doc.css("title").inner_html
    work_params[:title].gsub! /^[^:]+: /, ""
    work_params.merge!(scan_text_for_meta(storytext, detect_tags))

    font_blocks = @doc.xpath('//font')
    unless font_blocks.empty?
      date = font_blocks.first.inner_text
      work_params[:revised_at] = convert_revised_at(date)
    end

    # get the date
    date = @doc.css("span.date").inner_text
    work_params[:revised_at] = convert_revised_at(date)

    work_params
  end

  def parse_story_from_deviantart(_story, detect_tags = true)
    work_params = { chapter_attributes: {} }
    storytext = ""
    notes = ""

    body = @doc.css("body")
    title = @doc.css("title").inner_html.gsub /\s*on deviantart$/i, ""

    # Find the image (original size) if it's art
    image_full = body.css("div.dev-view-deviation img.dev-content-full")
    unless image_full[0].nil?
      storytext = "<center><img src=\"#{image_full[0]["src"]}\"></center>"
    end

    # Find the fic text if it's fic (needs the id for disambiguation, the "deviantART loves you" bit in the footer has the same class path)
    text_table = body.css(".grf-indent > div:nth-child(1)")[0]
    unless text_table.nil?
      # Try to remove some metadata (title and author) from the work's text, if possible
      # Try to remove the title: if it exists, and if it's the same as the browser title
      if text_table.css("h1")[0].present? && title && title.match(text_table.css("h1")[0].text)
        text_table.css("h1")[0].remove
      end

      # Try to remove the author: if it exists, and if it follows a certain pattern
      if text_table.css("small")[0].present? && text_table.css("small")[0].inner_html.match(/by ~.*?<a class="u" href=/m)
        text_table.css("small")[0].remove
      end
      storytext = text_table.inner_html
    end

    # cleanup the text
    storytext.gsub!(%r{<br\s*\/?>}i, "\n") # replace the breaks with newlines
    storytext = clean_storytext(storytext)
    work_params[:chapter_attributes][:content] = storytext

    # Find the notes
    content_divs = body.css("div.text-ctrl div.text")
    notes = content_divs[0].inner_html unless content_divs[0].nil?

    # cleanup the notes
    notes.gsub!(%r{<br\s*\/?>}i, "\n") # replace the breaks with newlines
    notes = clean_storytext(notes)
    work_params[:notes] = notes

    work_params.merge!(scan_text_for_meta(notes, detect_tags))
    work_params[:title] = title

    body.css("div.dev-title-container h1 a").each do |node|
      if node["class"] != "u"
        work_params[:title] = node.inner_html
      end
    end

    tags = []
    @doc.css("div.dev-about-cat-cc a.h").each { |node| tags << node.inner_html }
    work_params[:freeform_string] = clean_tags(tags.join(ArchiveConfig.DELIMITER_FOR_OUTPUT))

    details = @doc.css("div.dev-right-bar-content span[title]")
    unless details[0].nil?
      work_params[:revised_at] = convert_revised_at(details[0].inner_text)
    end

    work_params
  end

  # Move and/or copy any meta attributes that need to be on the chapter rather
  # than on the work itself
  def shift_chapter_attributes(work_params)
    CHAPTER_ATTRIBUTES_ONLY.each_pair do |work_attrib, chapter_attrib|
      if work_params[work_attrib] && !work_params[:chapter_attributes][chapter_attrib]
        work_params[:chapter_attributes][chapter_attrib] = work_params[work_attrib]
        work_params.delete(work_attrib)
      end
    end

    # copy any attributes from work to chapter as necessary
    CHAPTER_ATTRIBUTES_ALSO.each_pair do |work_attrib, chapter_attrib|
      if work_params[work_attrib] && !work_params[:chapter_attributes][chapter_attrib]
        work_params[:chapter_attributes][chapter_attrib] = work_params[work_attrib]
      end
    end

    work_params
  end

  # Find any cases of the given pieces of meta in the given text
  # and return a hash of meta values
  def scan_text_for_meta(text, detect_tags = true)
    # break up the text with some extra newlines to make matching more likely
    # and strip out some tags
    text = text.gsub(/<br/, "\n<br")
    text.gsub!(/<p/, "\n<p")
    text.gsub!(/<\/?(label|span|div|b)(.*?)?>/, '')

    meta = {}
    metapatterns = detect_tags ? REQUIRED_META.merge(OPTIONAL_META) : REQUIRED_META
    is_tag = {}.tap do |h|
      %w[fandom_string relationship_string freeform_string rating_string archive_warning_string].each do |c|
        h[c.to_sym] = true
      end
    end
    handler = {}.tap do |h|
      %w[rating_string revised_at].each do |c|
        h[c.to_sym] = "convert_#{c.to_s.downcase}"
      end
    end

    # 1. Look for Pattern: (whatever), optionally followed by a closing p or div tag
    # 2. Set meta[:metaname] = whatever
    # eg, if it finds Fandom: Stargate SG-1 it will set meta[:fandom] = Stargate SG-1
    # 3. convert_<metaname> for cleanup if such a function is defined (eg convert_rating_string)
    metapatterns.each do |metaname, pattern|
      metapattern = Regexp.new("(?:#{pattern}|#{pattern.pluralize})\s*:\s*(.*?)(?:</(?:p|div)>)?$", Regexp::IGNORECASE)
      if text.match(metapattern)
        value = Regexp.last_match[1]
        value = clean_tags(value) if is_tag[metaname]
        value = clean_close_html_tags(value)
        value.strip! # lose leading/trailing whitespace
        value = send(handler[metaname], value) if handler[metaname]

        meta[metaname] = value
      end
    end
    post_process_meta meta
  end

  def download_with_timeout(location, limit = 10)
    story = ""
    Timeout.timeout(STORY_DOWNLOAD_TIMEOUT) do
      begin
        # we do a little cleanup here in case the user hasn't included the 'http://'
        # or if they've used capital letters or an underscore in the hostname
        uri = URI.parse(location)
        uri = URI.parse('http://' + location) if uri.class.name == "URI::Generic"
        uri.host.downcase!
        uri.host.tr!(" ", "-")
        response = Net::HTTP.get_response(uri)
        case response
        when Net::HTTPSuccess
          story = response.body
        when Net::HTTPRedirection
          if limit.positive?
            story = download_with_timeout(response['location'], limit - 1)
          end
        else
          Rails.logger.error("------- STORY PARSER: download_with_timeout: response is not success or redirection ------")
          nil
        end
      rescue Errno::ECONNREFUSED, SocketError, EOFError => e
        Rails.logger.error("------- STORY PARSER: download_with_timeout: error rescue: \n#{e.inspect} ------")
        nil
      end
    end
    if story.blank?
      raise Error, "We couldn't download anything from #{location}. Please make sure that the URL is correct and complete, and try again."
    end

    # clean up any erroneously included string terminator (AO3-2251)
    story.delete("\000")
  end

  def get_last_modified(location)
    Timeout.timeout(STORY_DOWNLOAD_TIMEOUT) do
      resp = open(location)
      resp.last_modified
    end
  end

  def get_source_if_known(known_sources, location)
    known_sources.each do |source|
      pattern = Regexp.new(eval("SOURCE_#{source.upcase}"), Regexp::IGNORECASE)
      return source if location.match(pattern)
    end
    nil
  end

  def clean_close_html_tags(value)
    # if there are any closing html tags at the start of the value let's ditch them
    value.gsub(/^(\s*<\/[^>]+>)+/, '')
  end

  # We clean the text as if it had been submitted as the content of a chapter
  def clean_storytext(storytext)
    storytext = storytext.encode("UTF-8", invalid: :replace, undef: :replace, replace: "") unless storytext.encoding.name == "UTF-8"
    sanitize_value("content", storytext)
  end

  # works conservatively -- doesn't split on
  # spaces and truncates instead.
  def clean_tags(tags)
    tags = Sanitize.clean(tags.force_encoding("UTF-8")) # no html allowed in tags
    tags_list = tags =~ /,/ ? tags.split(/,/) : [tags]
    new_list = []
    tags_list.each do |tag|
      tag.gsub!(/[*<>]/, '')
      tag = truncate_on_word_boundary(tag, ArchiveConfig.TAG_MAX)
      new_list << tag unless tag.blank?
    end
    new_list.join(ArchiveConfig.DELIMITER_FOR_OUTPUT)
  end

  def truncate_on_word_boundary(text, max_length)
    return if text.blank?
    words = text.split
    truncated = words.first
    if words.length > 1
      words[1..words.length].each do |word|
        truncated += " " + word if truncated.length + word.length + 1 <= max_length
      end
    end
    truncated[0..max_length - 1]
  end

  # convert space-separated tags to comma-separated
  def clean_and_split_tags(tags)
    tags = tags.split(/\s+/).join(',') if !tags.match(/,/) && tags.match(/\s/)
    clean_tags(tags)
  end

  # Convert the common ratings into whatever ratings we're
  # using on this archive.
  def convert_rating_string(rating)
    rating = rating.downcase
    if rating =~ /^(nc-?1[78]|x|ma|explicit)/
      ArchiveConfig.RATING_EXPLICIT_TAG_NAME
    elsif rating =~ /^(r|m|mature)/
      ArchiveConfig.RATING_MATURE_TAG_NAME
    elsif rating =~ /^(pg-?1[35]|t|teen)/
      ArchiveConfig.RATING_TEEN_TAG_NAME
    elsif rating =~ /^(pg|g|k+|k|general audiences)/
      ArchiveConfig.RATING_GENERAL_TAG_NAME
    else
      ArchiveConfig.RATING_DEFAULT_TAG_NAME
    end
  end

  def convert_revised_at(date_string)
    begin
      date = nil
      if date_string =~ /^(\d+)$/
        # probably seconds since the epoch
        date = Time.at(Regex.last_match[1].to_i)
      end
      date ||= Date.parse(date_string)
      return '' if date > Date.current
      return date
    rescue ArgumentError, TypeError
      return ''
    end
  end

  # Additional processing for meta - currently to make sure warnings
  # that aren't Archive warnings become additional tags instead
  def post_process_meta(meta)
    if meta[:archive_warning_string]
      result = process_warnings(meta[:archive_warning_string], meta[:freeform_string])
      meta[:archive_warning_string] = result[:archive_warning_string]
      meta[:freeform_string] = result[:freeform_string]
    end
    meta
  end

  def process_warnings(warning_string, freeform_string)
    result = {
        archive_warning_string: warning_string,
        freeform_string: freeform_string
    }
    new_warning = ''
    result[:archive_warning_string].split(/\s?,\s?/).each do |warning|
      if ArchiveWarning.warning? warning
        new_warning += ', ' unless new_warning.blank?
        new_warning += warning
      else
        result[:freeform_string] = (result[:freeform_string] || '') + ", #{warning}"
      end
    end
    result[:archive_warning_string] = new_warning
    result
  end

  # tries to find appropriate existing collections and converts them to comma-separated collection names only
  def get_collection_names(collection_string)
    collections = ""
    collection_string.split(',').map(&:squish).each do |collection_name|
      collection = Collection.find_by(name: collection_name) || Collection.find_by(title: collection_name)
      if collection
        collections += ", " unless collections.blank?
        collections += collection.name
      end
    end
    collections
  end

  # determine which value to use for a metadata field
  def meta_or_default(detected_field, provided_field, default = nil)
    if @options[:override_tags] || detected_field.blank?
      if provided_field.blank?
        detected_field.blank? ? default : detected_field
      else
        provided_field
      end
    else
      detected_field
    end
  end
end
