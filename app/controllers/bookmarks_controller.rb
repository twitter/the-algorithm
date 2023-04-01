class BookmarksController < ApplicationController
  before_action :load_collection
  before_action :load_owner, only: [:index]
  before_action :load_bookmarkable, only: [:index, :new, :create, :fetch_recent, :hide_recent]
  before_action :users_only, only: [:new, :create, :edit, :update]
  before_action :check_user_status, only: [:new, :create, :edit, :update]
  before_action :load_bookmark, only: [:show, :edit, :update, :destroy, :fetch_recent, :hide_recent, :confirm_delete, :share]
  before_action :check_visibility, only: [:show, :share]
  before_action :check_ownership, only: [:edit, :update, :destroy, :confirm_delete, :share]

  before_action :check_pseud_ownership, only: [:create, :update]

  skip_before_action :store_location, only: [:share]

  def check_pseud_ownership
    if params[:bookmark][:pseud_id]
      pseud = Pseud.find(bookmark_params[:pseud_id])
      unless pseud && current_user && current_user.pseuds.include?(pseud)
        flash[:error] = ts("You can't bookmark with that pseud.")
        redirect_to root_path and return
      end
    end
  end

  # get the parent
  def load_bookmarkable
    if params[:work_id]
      @bookmarkable = Work.find(params[:work_id])
    elsif params[:external_work_id]
      @bookmarkable = ExternalWork.find(params[:external_work_id])
    elsif params[:series_id]
      @bookmarkable = Series.find(params[:series_id])
    end
  end

  def load_bookmark
    @bookmark = Bookmark.find(params[:id])
    @check_ownership_of = @bookmark
    @check_visibility_of = @bookmark
  end

  def search
    @languages = Language.default_order
    options = params[:bookmark_search].present? ? bookmark_search_params : {}
    options.merge!(page: params[:page]) if params[:page].present?
    options[:show_private] = false
    options[:show_restricted] = logged_in? || logged_in_as_admin?
    @search = BookmarkSearchForm.new(options)
    @page_subtitle = ts("Search Bookmarks")
    if params[:bookmark_search].present? && params[:edit_search].blank?
      if @search.query.present?
        @page_subtitle = ts("Bookmarks Matching '%{query}'", query: @search.query)
      end
      @bookmarks = @search.search_results.scope(:for_blurb)
      flash_search_warnings(@bookmarks)
      set_own_bookmarks
      render 'search_results'
    end
  end

  def index
    if @bookmarkable
      access_denied unless is_admin? || @bookmarkable.visible?
      @bookmarks = @bookmarkable.bookmarks.is_public.paginate(page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE)
    else
      base_options = {
        show_private: (@user.present? && @user == current_user),
        show_restricted: logged_in? || logged_in_as_admin?,
        page: params[:page]
      }

      options = params[:bookmark_search].present? ? bookmark_search_params : {}

      if params[:include_bookmark_search].present?
        params[:include_bookmark_search].keys.each do |key|
          options[key] ||= []
          options[key] << params[:include_bookmark_search][key]
          options[key].flatten!
        end
      end

      if params[:exclude_bookmark_search].present?
        params[:exclude_bookmark_search].keys.each do |key|
          # Keep bookmarker tags separate, so we can search for them on bookmarks
          # and search for the rest on bookmarkables
          options_key = key == "tag_ids" ? :excluded_bookmark_tag_ids : :excluded_tag_ids
          options[options_key] ||= []
          options[options_key] << params[:exclude_bookmark_search][key]
          options[options_key].flatten!
        end
      end

      options.merge!(base_options)
      @page_subtitle = index_page_title

      if @owner.present?
        @search = BookmarkSearchForm.new(options.merge(faceted: true, parent: @owner))

        if @user.blank?
          # When it's not a particular user's bookmarks, we want
          # to list *bookmarkable* items to avoid duplication
          @bookmarkable_items = @search.bookmarkable_search_results.scope(:for_blurb)
          flash_search_warnings(@bookmarkable_items)
          @facets = @bookmarkable_items.facets
        else
          # We're looking at a particular user's bookmarks, so
          # just retrieve the standard search results and their facets.
          @bookmarks = @search.search_results.scope(:for_blurb)
          flash_search_warnings(@bookmarks)
          @facets = @bookmarks.facets
        end

        if @search.options[:excluded_tag_ids].present? || @search.options[:excluded_bookmark_tag_ids].present?
          # Excluded tags do not appear in search results, so we need to generate empty facets
          # to keep them as checkboxes on the filters.
          excluded_tag_ids = @search.options[:excluded_tag_ids] || []
          excluded_bookmark_tag_ids = @search.options[:excluded_bookmark_tag_ids] || []

          # It's possible to determine the tag types by looking at
          # the original parameters params[:exclude_bookmark_search],
          # but we need the tag names too, so a database query is unavoidable.
          tags = Tag.where(id: excluded_tag_ids + excluded_bookmark_tag_ids)
          tags.each do |tag|
            if excluded_tag_ids.include?(tag.id.to_s)
              key = tag.class.to_s.underscore
              @facets[key] ||= []
              @facets[key] << QueryFacet.new(tag.id, tag.name, 0)
            end
            if excluded_bookmark_tag_ids.include?(tag.id.to_s)
              key = 'tag'
              @facets[key] ||= []
              @facets[key] << QueryFacet.new(tag.id, tag.name, 0)
            end
          end
        end
      elsif use_caching?
        @bookmarks = Rails.cache.fetch("bookmarks/index/latest/v2_true", expires_in: ArchiveConfig.SECONDS_UNTIL_BOOKMARK_INDEX_EXPIRE.seconds) do
          search = BookmarkSearchForm.new(show_private: false, show_restricted: false, sort_column: 'created_at')
          results = search.search_results.scope(:for_blurb)
          flash_search_warnings(results)
          results.to_a
        end
      else
        @bookmarks = Bookmark.latest.for_blurb.to_a
      end
    end
    set_own_bookmarks
  end

  # GET    /:locale/bookmark/:id
  # GET    /:locale/users/:user_id/bookmarks/:id
  # GET    /:locale/works/:work_id/bookmark/:id
  # GET    /:locale/external_works/:external_work_id/bookmark/:id
  def show
  end

  # GET /bookmarks/new
  # GET /bookmarks/new.xml
  def new
    @bookmark = Bookmark.new
    respond_to do |format|
      format.html
      format.js {
        @button_name = ts("Create")
        @action = :create
        render action: "bookmark_form_dynamic"
      }
    end
  end

  # GET /bookmarks/1/edit
  def edit
    @bookmarkable = @bookmark.bookmarkable
    respond_to do |format|
      format.html
      format.js {
        @button_name = ts("Update")
        @action = :update
        render action: "bookmark_form_dynamic"
      }
    end
  end

  # POST /bookmarks
  # POST /bookmarks.xml
  def create
    @bookmarkable ||= ExternalWork.new(external_work_params)
    @bookmark = Bookmark.new(bookmark_params.merge(bookmarkable: @bookmarkable))
    if @bookmark.errors.empty? && @bookmark.save
      flash[:notice] = ts("Bookmark was successfully created. It should appear in bookmark listings within the next few minutes.")
      redirect_to(bookmark_path(@bookmark))
    else
      render :new
    end
  end

  # PUT /bookmarks/1
  # PUT /bookmarks/1.xml
  def update
    new_collections = []
    unapproved_collections = []
    errors = []
    bookmark_params[:collection_names]&.split(",")&.map(&:strip)&.uniq&.each do |collection_name|
      collection = Collection.find_by(name: collection_name)
      if collection.nil?
        errors << ts("#{collection_name} does not exist.")
      else
        if @bookmark.collections.include?(collection)
          next
        elsif collection.closed? && !collection.user_is_maintainer?(User.current_user)
          errors << ts("#{collection.title} is closed to new submissions.")
        elsif @bookmark.add_to_collection(collection) && @bookmark.save
          if @bookmark.approved_collections.include?(collection)
            new_collections << collection
          else
            unapproved_collections << collection
          end
        else
          errors << ts("Something went wrong trying to add collection #{collection.title}, sorry!")
        end
      end
    end

    # messages to the user
    unless errors.empty?
      flash[:error] = ts("We couldn't add your submission to the following collections: ") + errors.join("<br />")
    end

    unless new_collections.empty?
      flash[:notice] = ts("Added to collection(s): %{collections}.",
                          collections: new_collections.collect(&:title).join(", "))
    end
    unless unapproved_collections.empty?
      flash[:notice] = flash[:notice] ? flash[:notice] + " " : ""
      flash[:notice] += if unapproved_collections.size > 1
                          ts("You have submitted your bookmark to moderated collections (%{all_collections}). It will not become a part of those collections until it has been approved by a moderator.", all_collections: unapproved_collections.map(&:title).join(", "))
                        else
                          ts("You have submitted your bookmark to the moderated collection '%{collection}'. It will not become a part of the collection until it has been approved by a moderator.", collection: unapproved_collections.first.title)
                        end
    end

    flash[:notice] = (flash[:notice]).html_safe unless flash[:notice].blank?
    flash[:error] = (flash[:error]).html_safe unless flash[:error].blank?

    if @bookmark.update(bookmark_params) && errors.empty?
      flash[:notice] = flash[:notice] ? " " + flash[:notice] : ""
      flash[:notice] = ts("Bookmark was successfully updated.").html_safe + flash[:notice]
      flash[:notice] = flash[:notice].html_safe
      redirect_to(@bookmark)
    else
      @bookmarkable = @bookmark.bookmarkable
      render :edit and return
    end
  end

  # GET /bookmarks/1/share
  def share
    if request.xhr?
      if @bookmark.bookmarkable.is_a?(Work) && @bookmark.bookmarkable.unrevealed?
        render template: "errors/404", status: :not_found
      else
        render layout: false
      end
    else
      # Avoid getting an unstyled page if JavaScript is disabled
      flash[:error] = ts("Sorry, you need to have JavaScript enabled for this.")
      redirect_back(fallback_location: root_path)
    end
  end

  def confirm_delete
  end

  # DELETE /bookmarks/1
  # DELETE /bookmarks/1.xml
  def destroy
    @bookmark.destroy
    flash[:notice] = ts("Bookmark was successfully deleted.")
    redirect_to user_bookmarks_path(current_user)
  end

  # Used on index page to show 4 most recent bookmarks (after bookmark being currently viewed) via RJS
  # Only main bookmarks page or tag bookmarks page
  # non-JS fallback should be to the 'view all bookmarks' link which serves the same function
  def fetch_recent
    @bookmarkable = @bookmark.bookmarkable
    respond_to do |format|
      format.js {
        @bookmarks = @bookmarkable.bookmarks.visible.order("created_at DESC").offset(1).limit(4)
        set_own_bookmarks
      }
      format.html do
        id_symbol = (@bookmarkable.class.to_s.underscore + '_id').to_sym
        redirect_to url_for({action: :index, id_symbol => @bookmarkable})
      end
    end
  end
  def hide_recent
    @bookmarkable = @bookmark.bookmarkable
  end

  protected

  def load_owner
    if params[:user_id].present?
      @user = User.find_by(login: params[:user_id])
      unless @user
        raise ActiveRecord::RecordNotFound, "Couldn't find user named '#{params[:user_id]}'"
      end
      if params[:pseud_id].present?
        @pseud = @user.pseuds.find_by(name: params[:pseud_id])
        unless @pseud
          raise ActiveRecord::RecordNotFound, "Couldn't find pseud named '#{params[:pseud_id]}'"
        end
      end
    end
    if params[:tag_id]
      @tag = Tag.find_by_name(params[:tag_id])
      unless @tag
        raise ActiveRecord::RecordNotFound, "Couldn't find tag named '#{params[:tag_id]}'"
      end
      unless @tag.canonical?
        if @tag.merger.present?
          redirect_to tag_bookmarks_path(@tag.merger) and return
        else
          redirect_to tag_path(@tag) and return
        end
      end
    end
    @owner = @bookmarkable || @pseud || @user || @collection || @tag
  end

  def index_page_title
    if @owner.present?
      owner_name = case @owner.class.to_s
                   when 'Pseud'
                     @owner.name
                   when 'User'
                     @owner.login
                   when 'Collection'
                     @owner.title
                   else
                     @owner.try(:name)
                   end
      "#{owner_name} - Bookmarks".html_safe
    else
      "Latest Bookmarks"
    end
  end

  def set_own_bookmarks
    return unless @bookmarks
    @own_bookmarks = []
    if current_user.is_a?(User)
      pseud_ids = current_user.pseuds.pluck(:id)
      @own_bookmarks = @bookmarks.select do |b|
        pseud_ids.include?(b.pseud_id)
      end
    end
  end

  private

  def bookmark_params
    params.require(:bookmark).permit(
      :pseud_id, :bookmarker_notes, :tag_string, :collection_names, :private, :rec
    )
  end

  def external_work_params
    params.require(:external_work).permit(
      :url, :author, :title, :fandom_string, :rating_string, :relationship_string,
      :character_string, :summary, category_strings: []
    )
  end

  def bookmark_search_params
    params.require(:bookmark_search).permit(
      :bookmark_query,
      :bookmarkable_query,
      :bookmarker,
      :bookmark_notes,
      :rec,
      :with_notes,
      :bookmarkable_type,
      :language_id,
      :date,
      :bookmarkable_date,
      :sort_column,
      :other_tag_names,
      :excluded_tag_names,
      :other_bookmark_tag_names,
      :excluded_bookmark_tag_names,
      rating_ids: [],
      warning_ids: [], # backwards compatibility
      archive_warning_ids: [],
      category_ids: [],
      fandom_ids: [],
      character_ids: [],
      relationship_ids: [],
      freeform_ids: [],
      tag_ids: [],
    )
  end
end
