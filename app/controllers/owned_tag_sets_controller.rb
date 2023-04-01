class OwnedTagSetsController < ApplicationController
  cache_sweeper :tag_set_sweeper

  before_action :load_tag_set, except: [ :index, :new, :create, :show_options ]
  before_action :users_only, only: [ :new, :create, :nominate ]
  before_action :moderators_only, except: [ :index, :new, :create, :show, :show_options ]
  before_action :owners_only, only: [ :destroy ]

  def load_tag_set
    @tag_set = OwnedTagSet.find_by(id: params[:id])
    unless @tag_set
      flash[:error] = ts("What Tag Set did you want to look at?")
      redirect_to tag_sets_path and return
    end
  end

  def moderators_only
    @tag_set.user_is_moderator?(current_user) || access_denied
  end

  def owners_only
    @tag_set.user_is_owner?(current_user) || access_denied
  end

  def nominated_only
    @tag_set.nominated || access_denied
  end

  ### ACTIONS

  def index
    if params[:user_id]
      @user = User.find_by login: params[:user_id]
      @tag_sets = OwnedTagSet.owned_by(@user)
    elsif params[:restriction]
      @restriction = PromptRestriction.find(params[:restriction])
      @tag_sets = OwnedTagSet.in_prompt_restriction(@restriction)
      if @tag_sets.count == 1
        redirect_to tag_set_path(@tag_sets.first, tag_type: (params[:tag_type] || "fandom")) and return
      end
    else
      @tag_sets = OwnedTagSet
      if params[:query]
        @query = params[:query]
        @tag_sets = @tag_sets.where("title LIKE ?", '%' + params[:query] + '%')
      else
        # show a random selection
        @tag_sets = @tag_sets.order("created_at DESC")
      end
    end
    @tag_sets = @tag_sets.paginate(per_page: (params[:per_page] || ArchiveConfig.ITEMS_PER_PAGE), page: (params[:page] || 1))
  end

  def show_options
    @restriction = PromptRestriction.find_by(id: params[:restriction])
    unless @restriction
      flash[:error] = ts("Which Tag Set did you want to look at?")
      redirect_to tag_sets_path and return
    end
    @tag_sets = OwnedTagSet.in_prompt_restriction(@restriction)
    @tag_set_ids = @tag_sets.pluck(:tag_set_id)
    @tag_type = params[:tag_type] && TagSet::TAG_TYPES.include?(params[:tag_type]) ? params[:tag_type] : "fandom"
    # @tag_type is restricted by in_prompt_restriction and therefore safe to pass to constantize
    @tags = @tag_type.classify.constantize.joins(:set_taggings).where("set_taggings.tag_set_id IN (?)", @tag_set_ids).by_name_without_articles
  end

  def show
    # don't bother collecting tags unless the user gets to see them
    if @tag_set.visible || @tag_set.user_is_moderator?(current_user)

      # we use this to collect up fandom parents for characters and relationships
      @fandom_keys_from_other_tags = []

      # we use this to store the tag name results
      @tag_hash = HashWithIndifferentAccess.new

      %w(character relationship).each do |tag_type|
        if @tag_set.has_type?(tag_type)
          ## names_by_parent returns a hash of arrays like so:
          ## hash[parent_name] => [child name, child name, child name]

          # get the manually associated fandoms
          assoc_hash = TagSetAssociation.names_by_parent(TagSetAssociation.for_tag_set(@tag_set), tag_type)

          # get canonically associated fandoms
          # Safe for constantize as tag_type restricted to character relationship
          canonical_hash = Tag.names_by_parent(tag_type.classify.constantize.in_tag_set(@tag_set), "fandom")

          # merge the values of the two hashes (each value is an array) as a set (ie remove duplicates)
          @tag_hash[tag_type] = assoc_hash.merge(canonical_hash) {|key, oldval, newval| (oldval | newval) }

          # get any tags without a fandom
          remaining = @tag_set.with_type(tag_type).with_no_parents
          if remaining.count > 0
            @tag_hash[tag_type]["(No linked fandom - might need association)"] ||= []
            @tag_hash[tag_type]["(No linked fandom - might need association)"] += remaining.pluck(:name)
          end

          # store the parents
          @fandom_keys_from_other_tags += @tag_hash[tag_type].keys
        end
      end

      # get rid of duplicates and sort
      @fandom_keys_from_other_tags = @fandom_keys_from_other_tags.compact.uniq.sort {|a,b| a.gsub(/^(the |an |a )/, '') <=> b.gsub(/^(the |an |a )/, '')}

      # now handle fandoms
      if @tag_set.has_type?("fandom")
        # Get fandoms hashed by media -- just the canonical associations
        @tag_hash[:fandom] = Tag.names_by_parent(Fandom.in_tag_set(@tag_set), "media")

        # get any fandoms without a media
        if @tag_set.with_type("fandom").with_no_parents.count > 0
          @tag_hash[:fandom]["(No Media)"] ||= []
          @tag_hash[:fandom]["(No Media)"] += @tag_set.with_type("fandom").with_no_parents.pluck(:name)
        end

        # we want to collect and warn about any chars or relationships not in the set's fandoms
        @character_seen = {}
        @relationship_seen = {}

        # clear out the fandoms we're showing from the list of other tags
        if @fandom_keys_from_other_tags && !@fandom_keys_from_other_tags.empty?
          @fandom_keys_from_other_tags -= @tag_hash[:fandom].values.flatten
        end

        @unassociated_chars = []
        @unassociated_rels = []
        unless @fandom_keys_from_other_tags.empty?
          if @tag_hash[:character]
            @unassociated_chars = @tag_hash[:character].values_at(*@fandom_keys_from_other_tags).flatten.compact.uniq
          end
          if @tag_hash[:relationship]
            @unassociated_rels = @tag_hash[:relationship].values_at(*@fandom_keys_from_other_tags).flatten.compact.uniq
          end
        end
      end
    end
  end

  def new
    @tag_set = OwnedTagSet.new
  end

  def create
    @tag_set = OwnedTagSet.new(owned_tag_set_params)
    @tag_set.add_owner(current_user.default_pseud)
    if @tag_set.save
      flash[:notice] = ts('Tag Set was successfully created.')
      redirect_to tag_set_path(@tag_set)
    else
      render action: "new"
    end
  end

  def edit
    get_parent_child_tags
  end

  def update
    if @tag_set.update(owned_tag_set_params) && @tag_set.tag_set.save!
      flash[:notice] = ts("Tag Set was successfully updated.")
      redirect_to tag_set_path(@tag_set)
    else
      get_parent_child_tags
      render action: :edit
    end
  end

  def confirm_delete
  end

  def destroy
    @tag_set = OwnedTagSet.find(params[:id])
    begin
      name = @tag_set.title
      @tag_set.destroy
      flash[:notice] = ts("Your Tag Set %{name} was deleted.", name: name)
    rescue
      flash[:error] = ts("We couldn't delete that right now, sorry! Please try again later.")
    end
    redirect_to tag_sets_path
  end

  def batch_load
  end

  def do_batch_load
    if params[:batch_associations]
      failed = @tag_set.load_batch_associations!(params[:batch_associations], do_relationships: (params[:batch_do_relationships] ? true : false))
      if failed.empty?
        flash[:notice] = ts("Tags and associations loaded!")
        redirect_to tag_set_path(@tag_set) and return
      else
        flash.now[:notice] = ts("We couldn't add all the tags and associations you wanted -- the ones left below didn't work. See the help for suggestions!")
        @failed_batch_associations = failed.join("\n")
        render action: :batch_load and return
      end
    else
      flash[:error] = ts("What did you want to load?")
      redirect_to action: :batch_load and return
    end
  end




  protected

  # for manual associations
  def get_parent_child_tags
    @tags_in_set = Tag.joins(:set_taggings).where("set_taggings.tag_set_id = ?", @tag_set.tag_set_id).order("tags.name ASC")
    @parent_tags_in_set = @tags_in_set.where(type: 'Fandom').pluck :name, :id
    @child_tags_in_set = @tags_in_set.where("type IN ('Relationship', 'Character')").pluck :name, :id
  end

  private

  def owned_tag_set_params
    params.require(:owned_tag_set).permit(
      :owner_changes, :moderator_changes, :title, :description, :visible,
      :usable, :nominated, :fandom_nomination_limit, :character_nomination_limit,
      :relationship_nomination_limit, :freeform_nomination_limit,
      associations_to_remove: [],
      tag_set_associations_attributes: [
        :id, :create_association, :tag_id, :parent_tag_id, :_destroy
      ],
      tag_set_attributes: [
        :id,
        :from_owned_tag_set, :fandom_tagnames_to_add, :character_tagnames_to_add,
        :relationship_tagnames_to_add, :freeform_tagnames_to_add,
        character_tagnames: [], rating_tagnames: [], archive_warning_tagnames: [],
        category_tagnames: [], fandom_tags_to_remove: [], character_tags_to_remove: [],
        relationship_tags_to_remove: [], freeform_tags_to_remove: []
      ]
    )
  end

end
