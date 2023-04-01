class Api::V2::BookmarksController < Api::V2::BaseController
  respond_to :json

  # POST - search for bookmarks for this archivist
  def search
    archivist = User.find_by(login: params[:archivist])
    bookmarks = params[:bookmarks]
    # check for top-level errors (not an archivist, no bookmarks...)
    status, messages = batch_errors(archivist, bookmarks)
    results = []

    if status == :ok
      archivist_bookmarks = Bookmark.where(pseud_id: archivist.default_pseud.id)
      results = bookmarks.map do |bookmark|
        found_result = {}
        found_result = check_archivist_bookmark(archivist, bookmark[:url], archivist_bookmarks) unless archivist_bookmarks.empty?

        bookmark_response(
          status: found_result[:bookmark_status] || :not_found,
          bookmark_url: found_result[:bookmark_url] || "",
          bookmark_id: bookmark[:id],
          original_url: bookmark[:url],
          messages: found_result[:bookmark_messages] || ["No bookmark found for archivist \"#{archivist.login}\" and URL \"#{bookmark[:url]}\""]
        )
      end
      messages = ["Successfully searched bookmarks for archivist '#{archivist.login}'"]
    end

    render_api_response(status, messages, bookmarks: results)
  end

  # POST - create a bookmark for this archivist
  def create
    archivist = User.find_by(login: params[:archivist])
    bookmarks = params[:bookmarks]
    bookmarks_responses = []
    @bookmarks = []

    # check for top-level errors (not an archivist, no bookmarks...)
    status, messages = batch_errors(archivist, bookmarks)

    if status == :ok
      # Flag error and successes
      @some_errors = @some_success = false

      # Process the bookmarks
      archivist_bookmarks = Bookmark.where(pseud_id: archivist.default_pseud_id, bookmarkable_type: "ExternalWork")
      bookmarks.each do |bookmark|
        bookmarks_responses << create_bookmark(archivist, bookmark, archivist_bookmarks)
      end

      # set final response code and message depending on the flags
      status = :bad_request if bookmarks_responses.any? { |r| [:ok, :created, :found].exclude?(r[:status]) }
      messages = response_message(messages)
    end

    render_api_response(status, messages, bookmarks: bookmarks_responses)
  end

  private

  # Find bookmarks for this archivist
  def check_archivist_bookmark(archivist, current_bookmark_url, archivist_bookmarks)
    archivist_bookmarks = archivist_bookmarks
                            .select { |b| b&.bookmarkable.is_a?(ExternalWork) ? b&.bookmarkable&.url == current_bookmark_url : false }
                            .map    { |b| [b, b.bookmarkable] }

    if archivist_bookmarks.present?
      archivist_bookmark, archivist_bookmarkable = archivist_bookmarks.first
      find_bookmark_response(
        bookmarkable: archivist_bookmarkable,
        bookmark_status: :found,
        bookmark_message: "There is already a bookmark for #{archivist.login} and the URL #{current_bookmark_url}",
        bookmark_url: bookmark_url(archivist_bookmark)
      )
    else
      find_bookmark_response(
        bookmarkable: nil,
        bookmark_status: :not_found,
        bookmark_message: "There is no bookmark for #{archivist.login} and the URL #{current_bookmark_url}",
        bookmark_url: ""
      )
    end
  end

  # Create a bookmark for this archivist using the Bookmark model
  def create_bookmark(archivist, params, archivist_bookmarks)
    found_result = {}
    bookmark_attributes = bookmark_attributes(archivist, params)
    external_work_attributes = external_work_attributes(params)
    bookmark_status, bookmark_messages = external_work_errors(external_work_attributes)
    bookmark_url = nil
    original_url = nil
    bookmarkable = nil
    @some_errors = true
    if bookmark_status == :ok
      begin

        # Check if this bookmark is already imported by filtering the archivist's bookmarks
        unless archivist_bookmarks.empty?
          found_result = check_archivist_bookmark(archivist, external_work_attributes[:url], archivist_bookmarks)
          bookmarkable = found_result[:bookmarkable]
        end

        if found_result[:bookmark_status] == :found
          found_result[:bookmark_status] = :already_imported
        else
          bookmarkable = ExternalWork.new(external_work_attributes)
          bookmark = bookmarkable.bookmarks.build(bookmark_attributes)
          if bookmarkable.save && bookmark.save
            @bookmarks << bookmark
            @some_success = true
            @some_errors = false
            bookmark_status = :created
            bookmark_url = bookmark_url(bookmark)
            bookmark_messages << "Successfully created bookmark for \"" + bookmarkable.title + "\"."
          else
            bookmark_status = :unprocessable_entity
            bookmark_messages << bookmarkable.errors.full_messages + bookmark.errors.full_messages
          end
        end
      rescue StandardError => exception
        bookmark_status = :unprocessable_entity
        bookmark_messages << exception.message
      end
      original_url = bookmarkable.url if bookmarkable
    end

    bookmark_response(
      status: bookmark_status || found_result[:bookmark_status],
      bookmark_url: bookmark_url || found_result[:bookmark_url],
      bookmark_id: params[:id],
      original_url: original_url,
      messages: bookmark_messages.flatten || found_result[:bookmark_messages]
    )
  end

  # Error handling
  
  # Set messages based on success and error flags
  def response_message(messages)
    messages << if @some_success && @some_errors
                  "At least one bookmark was not created. Please check the individual bookmark results for further information."
                elsif !@some_success && @some_errors
                  "None of the bookmarks were created. Please check the individual bookmark results for further information."
                else
                  "All bookmarks were successfully created."
                end
    messages
  end

  # Handling for incomplete requests
  def external_work_errors(external_work_attributes)
    status = :bad_request
    errors = []

    # Perform basic validation which the ExternalWork model doesn't do or returns strange messages for
    # (title is validated correctly in the model and so isn't checked here)
    url = external_work_attributes[:url]
    author = external_work_attributes[:author]
    fandom = external_work_attributes[:fandom_string]

    if url.nil?
      # Unreachable and AO3 URLs are handled in the ExternalWork model
      errors << "This bookmark does not contain a URL to an external site. Please specify a valid, non-AO3 URL."
    end

    if author.nil? || author == ""
      errors << "This bookmark does not contain an external author name. Please specify an author."
    end

    if fandom.nil? || fandom == ""
      errors << "This bookmark does not contain a fandom. Please specify a fandom."
    end

    status = :ok if errors.empty?
    [status, errors]
  end

  # Request and response hashes

  # Map JSON request to attributes for bookmark
  def bookmark_attributes(archivist, params)
    {
      pseud_id: archivist.default_pseud_id,
      bookmarker_notes: params[:bookmarker_notes],
      tag_string: params[:tag_string] || "",
      collection_names: params[:collection_names],
      private: params[:private].blank? ? false : params[:private],
      rec: params[:recommendation].blank? ? false : params[:recommendation]
    }
  end

  # Map JSON request to attributes for external work
  def external_work_attributes(params)
    {
      url: params[:url],
      author: params[:author],
      title: params[:title],
      summary: params[:summary],
      fandom_string: params[:fandom_string] || "",
      rating_string: params[:rating_string] || "",
      category_string: params[:category_string] ? params[:category_string].to_s.split(",") : [], # category is actually an array on bookmarks
      relationship_string: params[:relationship_string] || "",
      character_string: params[:character_string] || ""
    }
  end

  def bookmark_response(status:, bookmark_url:, bookmark_id:, original_url:, messages:)
    messages = [messages] unless messages.respond_to?('each')
    {
      status: status,
      archive_url: bookmark_url,
      original_id: bookmark_id,
      original_url: original_url,
      messages: messages
    }
  end
  
  def find_bookmark_response(bookmarkable:, bookmark_status:, bookmark_message:, bookmark_url:)
    bookmark_status = :not_found unless [:found, :not_found].include?(bookmark_status)
    {
      bookmarkable: bookmarkable,
      bookmark_status: bookmark_status,
      bookmark_messages: bookmark_message,
      bookmark_url: bookmark_url
    }
  end
end
