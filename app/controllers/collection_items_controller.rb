class CollectionItemsController < ApplicationController
  before_action :load_collection
  before_action :load_user, only: [:update_multiple]
  before_action :load_collectible_item, only: [:new, :create]

  cache_sweeper :collection_sweeper

  def index

    if @collection && @collection.user_is_maintainer?(current_user)
      @collection_items = @collection.collection_items.include_for_works
      @collection_items = case
                          when params[:approved]
                            @collection_items.approved_by_both
                          when params[:rejected]
                            @collection_items.rejected_by_collection
                          when params[:invited]
                            @collection_items.invited_by_collection
                          else
                            @collection_items.unreviewed_by_collection
                          end
    elsif params[:user_id] && (@user = User.find_by(login: params[:user_id])) && @user == current_user
      @collection_items = CollectionItem.for_user(@user).includes(:collection)
      @collection_items = case
                          when params[:approved]
                            @collection_items.approved_by_both
                          when params[:rejected]
                            @collection_items.rejected_by_user
                          else
                            @collection_items.unreviewed_by_user
                          end
    else
      flash[:error] = ts("You don't have permission to see that, sorry!")
      redirect_to collections_path and return
    end

    sort = "created_at DESC"
    @collection_items = @collection_items.order(sort).paginate page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE
  end

  def load_collectible_item
    if params[:work_id]
      @item = Work.find(params[:work_id])
    elsif params[:bookmark_id]
      @item = Bookmark.find(params[:bookmark_id])
    end
  end

  def load_user
    unless @collection
      @user = User.find_by(login: params[:user_id])
    end
  end

  def new
  end

  def create
    unless params[:collection_names]
      flash[:error] = ts("What collections did you want to add?")
      redirect_to(request.env["HTTP_REFERER"] || root_path) and return
    end
    unless @item
      flash[:error] = ts("What did you want to add to a collection?")
      redirect_to(request.env["HTTP_REFERER"] || root_path) and return
    end
    if @item.respond_to?(:allow_collection_invitation?) && !@item.allow_collection_invitation?
      flash[:error] = t(".invitation_not_sent", default: "This item could not be invited.")
      redirect_to(@item) and return
    end
    # for each collection name
    # see if it exists, is open, and isn't already one of this item's collections
    # add the collection and save
    # if there are errors, add them to errors
    new_collections = []
    invited_collections = []
    unapproved_collections = []
    errors = []
    params[:collection_names].split(',').map {|name| name.strip}.uniq.each do |collection_name|
      collection = Collection.find_by(name: collection_name)
      if !collection
        errors << ts("%{name}, because we couldn't find a collection with that name. Make sure you are using the one-word name, and not the title.", name: collection_name)
      elsif @item.collections.include?(collection)
        if @item.rejected_collections.include?(collection)
          errors << ts("%{collection_title}, because the %{object_type}'s owner has rejected the invitation.", collection_title: collection.title, object_type: @item.class.name.humanize.downcase)
        else
          errors << ts("%{collection_title}, because this item has already been submitted.", collection_title: collection.title)
        end
      elsif collection.closed? && !collection.user_is_maintainer?(User.current_user)
        errors << ts("%{collection_title} is closed to new submissions.", collection_title: collection.title)
      elsif (collection.anonymous? || collection.unrevealed?) && !current_user.is_author_of?(@item)
        errors << ts("%{collection_title}, because you don't own this item and the collection is anonymous or unrevealed.", collection_title: collection.title)
      elsif !current_user.is_author_of?(@item) && !collection.user_is_maintainer?(current_user)
        errors << ts("%{collection_title}, either you don't own this item or are not a moderator of the collection.", collection_title: collection.title)
      elsif @item.is_a?(Work) && @item.anonymous? && !current_user.is_author_of?(@item)
        errors << ts("%{collection_title}, because you don't own this item and the item is anonymous.", collection_title: collection.title)
      # add the work to a collection, and try to save it
      elsif @item.add_to_collection(collection) && @item.save(validate: false)
        # approved_by_user? and approved_by_collection? are both true
        if @item.approved_collections.include?(collection)
          new_collections << collection
        # if the current_user is a maintainer of the collection then approved_by_user must have been false (which means
        # the current_user isn't the owner of the item), then the maintainer is attempting to invite this work to
        # their collection
        elsif collection.user_is_maintainer?(current_user)
          invited_collections << collection
        # otherwise the current_user is the owner of the item and approved_by_COLLECTION was false (which means the
        # current_user isn't a collection_maintainer), so the item owner is attempting to add their work to a moderated
        # collection
        else
          unapproved_collections << collection
        end
      else
        errors << ts("Something went wrong trying to add collection %{name}, sorry!", name: collection_name)
      end
    end

    # messages to the user
    unless errors.empty?
      flash[:error] = ts("We couldn't add your submission to the following collection(s): ") + "<br><ul><li />" + errors.join("<li />") + "</ul>"
    end
    flash[:notice] = "" unless new_collections.empty? && unapproved_collections.empty?
    unless new_collections.empty?
      flash[:notice] = ts("Added to collection(s): %{collections}.",
                            collections: new_collections.collect(&:title).join(", "))
    end
    unless invited_collections.empty?
      invited_collections.each do |needs_user_approval|
        flash[:notice] ||= ""
        flash[:notice] = ts("This work has been <a href=\"#{collection_items_path(needs_user_approval)}?invited=true\">invited</a> to your collection (#{needs_user_approval.title}).").html_safe
      end
    end
    unless unapproved_collections.empty?
      flash[:notice] ||= ""
      flash[:notice] += ts(" You have submitted your work to #{unapproved_collections.size > 1 ? "moderated collections (%{all_collections}). It will not become a part of those collections" : "the moderated collection '%{all_collections}'. It will not become a part of the collection"} until it has been approved by a moderator.", all_collections: unapproved_collections.map { |f| f.title }.join(', '))
    end

    flash[:notice] = (flash[:notice]).html_safe unless flash[:notice].blank?
    flash[:error] = (flash[:error]).html_safe unless flash[:error].blank?

    redirect_to(@item)
  end

  def update_multiple
    if @collection&.user_is_maintainer?(current_user)
      update_multiple_with_params(
        allowed_items: @collection.collection_items,
        update_params: collection_update_multiple_params,
        success_path: collection_items_path(@collection)
      )
    elsif @user && @user == current_user
      update_multiple_with_params(
        allowed_items: CollectionItem.for_user(@user),
        update_params: user_update_multiple_params,
        success_path: user_collection_items_path(@user)
      )
    else
      flash[:error] = ts("You don't have permission to do that, sorry!")
      redirect_to(@collection || @user)
    end
  end

  # The main work performed by update_multiple. Uses the passed-in parameters
  # to update, and only updates items that can be found in allowed_items (which
  # should be a relation on CollectionItems). When all items are successfully
  # updated, redirects to success_path.
  def update_multiple_with_params(allowed_items:, update_params:, success_path:)
    # Collect any failures so that we can display errors:
    @collection_items = []

    # Make sure that the keys are integers so that we can look up the
    # parameters by ID.
    update_params.transform_keys!(&:to_i)

    # By using where() here and updating each item individually, instead of
    # using allowed_items.update(update_params.keys, update_params.values) --
    # which uses find() under the hood -- we ensure that we'll fail silently if
    # the user tries to update an item they're not allowed to.
    allowed_items.where(id: update_params.keys).each do |item|
      item_data = update_params[item.id]
      if item_data[:remove] == "1"
        @collection_items << item unless item.destroy
      else
        @collection_items << item unless item.update(item_data)
      end
    end

    if @collection_items.empty?
      flash[:notice] = ts("Collection status updated!")
      redirect_to success_path
    else
      render action: "index"
    end
  end

  private

  def user_update_multiple_params
    allowed = %i[user_approval_status remove]
    params.slice(:collection_items).permit(collection_items: allowed).
      require(:collection_items)
  end

  def collection_update_multiple_params
    allowed = %i[collection_approval_status unrevealed anonymous remove]
    params.slice(:collection_items).permit(collection_items: allowed).
      require(:collection_items)
  end
end
