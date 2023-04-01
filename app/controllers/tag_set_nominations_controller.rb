class TagSetNominationsController < ApplicationController
  cache_sweeper :tag_set_sweeper

  before_action :users_only
  before_action :load_tag_set, except: [ :index ]
  before_action :check_pseud_ownership, only: [:create, :update]
  before_action :load_nomination, only: [:show, :edit, :update, :destroy, :confirm_delete]
  before_action :set_limit, only: [:new, :edit, :show, :create, :update, :review]

  def check_pseud_ownership
    if !tag_set_nomination_params[:pseud_id].blank?
      pseud = Pseud.find(tag_set_nomination_params[:pseud_id])
      unless pseud && current_user && current_user.pseuds.include?(pseud)
        flash[:error] = ts("You can't nominate tags with that pseud.")
        redirect_to root_path and return
      end
    end
  end

  def load_tag_set
    @tag_set = OwnedTagSet.find_by_id(params[:tag_set_id])
    unless @tag_set
      flash[:error] = ts("What tag set did you want to nominate for?")
      redirect_to tag_sets_path and return
    end
  end

  def load_nomination
    @tag_set_nomination = @tag_set.tag_set_nominations.find_by(id: params[:id])
    unless @tag_set_nomination
      flash[:error] = ts("Which nominations did you want to work with?")
      redirect_to tag_set_path(@tag_set) and return
    end
    unless current_user.is_author_of?(@tag_set_nomination) || @tag_set.user_is_moderator?(current_user)
      flash[:error] = ts("You can only see your own nominations or nominations for a set you moderate.")
      redirect_to tag_set_path(@tag_set) and return
    end
  end

  def set_limit
    @limit = @tag_set.limits
  end

  # used in new/edit to build any nominations that don't already exist before we open the form
  def build_nominations
    @limit[:fandom].times do |i|
      fandom_nom = @tag_set_nomination.fandom_nominations[i] || @tag_set_nomination.fandom_nominations.build
      @limit[:character].times {|j| fandom_nom.character_nominations[j] || fandom_nom.character_nominations.build }
      @limit[:relationship].times {|j| fandom_nom.relationship_nominations[j] || fandom_nom.relationship_nominations.build }
    end

    if @limit[:fandom] == 0
      @limit[:character].times {|j| @tag_set_nomination.character_nominations[j] || @tag_set_nomination.character_nominations.build }
      @limit[:relationship].times {|j| @tag_set_nomination.relationship_nominations[j] || @tag_set_nomination.relationship_nominations.build }
    end

    @limit[:freeform].times {|i| @tag_set_nomination.freeform_nominations[i] || @tag_set_nomination.freeform_nominations.build }
  end


  def index
    if params[:user_id]
      @user = User.find_by(login: params[:user_id])
      if @user != current_user
        flash[:error] = ts("You can only view your own nominations, sorry.")
        redirect_to tag_sets_path and return
      else
        @tag_set_nominations = TagSetNomination.owned_by(@user)
      end
    elsif (@tag_set = OwnedTagSet.find_by_id(params[:tag_set_id]))
      if @tag_set.user_is_moderator?(current_user)
        # reviewing nominations
        setup_for_review
      else
        flash[:error] = ts("You can't see those nominations, sorry.")
        redirect_to tag_sets_path and return
      end
    else
      flash[:error] = ts("What nominations did you want to work with?")
      redirect_to tag_sets_path and return
    end
  end

  def show
  end

  def new
    if @tag_set_nomination = TagSetNomination.for_tag_set(@tag_set).owned_by(current_user).first
      redirect_to edit_tag_set_nomination_path(@tag_set, @tag_set_nomination)
    else
      @tag_set_nomination = TagSetNomination.new(pseud: current_user.default_pseud, owned_tag_set: @tag_set)
      build_nominations
    end
  end

  def edit
    # build up extra nominations if not all were used
    build_nominations
  end

  def create
    @tag_set_nomination = @tag_set.tag_set_nominations.build(tag_set_nomination_params)
    if @tag_set_nomination.save
      flash[:notice] = ts('Your nominations were successfully submitted.')
      redirect_to tag_set_nomination_path(@tag_set, @tag_set_nomination)
    else
      build_nominations
      render action: "new"
    end
  end


  def update
    if @tag_set_nomination.update(tag_set_nomination_params)
      flash[:notice] = ts("Your nominations were successfully updated.")
      redirect_to tag_set_nomination_path(@tag_set, @tag_set_nomination)
    else
      build_nominations
      render action: "edit"
    end
  end

  def destroy
    unless @tag_set_nomination.unreviewed? || @tag_set.user_is_moderator?(current_user)
      flash[:error] = ts("You cannot delete nominations after some of them have been reviewed, sorry!")
      redirect_to tag_set_nomination_path(@tag_set, @tag_set_nomination)
    else
      @tag_set_nomination.destroy
      flash[:notice] = ts("Your nominations were deleted.")
      redirect_to tag_set_path(@tag_set)
    end
  end

  def base_nom_query(tag_type)
    TagNomination.where(type: (tag_type.is_a?(Array) ? tag_type.map {|t| "#{t.classify}Nomination"} : "#{tag_type.classify}Nomination")).
      for_tag_set(@tag_set).unreviewed.limit(@nom_limit)
  end

  # set up various variables for reviewing nominations
  def setup_for_review
    set_limit
    @nom_limit = 30
    @nominations = HashWithIndifferentAccess.new
    @nominations_count = HashWithIndifferentAccess.new
    more_noms = false

    if @tag_set.includes_fandoms?
      # all char and rel tags happen under fandom noms
      @nominations_count[:fandom] = @tag_set.fandom_nominations.unreviewed.count
      more_noms = true if  @nominations_count[:fandom] > @nom_limit
      @nominations[:fandom] = more_noms ? base_nom_query("fandom").random_order : base_nom_query("fandom").order(:tagname)
      if (@limit[:character] > 0 || @limit[:relationship] > 0)
        @nominations[:cast] = base_nom_query(%w(character relationship)).
          join_fandom_nomination.
          where('fandom_nominations_tag_nominations.approved = 1').
          order(:parent_tagname, :type, :tagname)
      end
    else
      # if there are no fandoms we're going to assume this is a one or few fandom tagset
      @nominations_count[:character] = @tag_set.character_nominations.unreviewed.count
      @nominations_count[:relationship] = @tag_set.relationship_nominations.unreviewed.count
      more_noms = true if (@tag_set.character_nominations.unreviewed.count > @nom_limit || @tag_set.relationship_nominations.unreviewed.count > @nom_limit)
      @nominations[:character] = base_nom_query("character") if @limit[:character] > 0
      @nominations[:relationship] = base_nom_query("relationship") if @limit[:relationship] > 0
      if more_noms
        parent_tagnames = TagNomination.for_tag_set(@tag_set).unreviewed.random_order.limit(100).pluck(:parent_tagname).uniq.first(30)
        @nominations[:character] = @nominations[:character].where(parent_tagname: parent_tagnames) if @limit[:character] > 0
        @nominations[:relationship] = @nominations[:relationship].where(parent_tagname: parent_tagnames) if @limit[:relationship] > 0
      end
      @nominations[:character] = @nominations[:character].order(:parent_tagname, :tagname) if @limit[:character] > 0
      @nominations[:relationship] = @nominations[:relationship].order(:parent_tagname, :tagname) if @limit[:relationship] > 0
    end
    @nominations_count[:freeform] =  @tag_set.freeform_nominations.unreviewed.count
    more_noms = true if @nominations_count[:freeform] > @nom_limit
    @nominations[:freeform] = (more_noms ? base_nom_query("freeform").random_order : base_nom_query("freeform").order(:tagname)) unless @limit[:freeform].zero?

    if more_noms
      flash[:notice] = ts("There are too many nominations to show at once, so here's a randomized selection! Additional nominations will appear after you approve or reject some.")
    end

    if @tag_set.tag_nominations.unreviewed.empty?
      flash[:notice] = ts("No nominations to review!")
    end
  end

  def confirm_delete
  end

  def confirm_destroy_multiple
  end

  def destroy_multiple
    unless @tag_set.user_is_owner?(current_user)
      flash[:error] = ts("You don't have permission to do that.")
      redirect_to tag_set_path(@tag_set) and return
    end

    @tag_set.clear_nominations!
    flash[:notice] = ts("All nominations for this Tag Set have been cleared.")
    redirect_to tag_set_path(@tag_set)
  end

  # update_multiple gets called from the index/review form.
  # we expect params like "character_approve_My Awesome Tag" and "fandom_reject_My Lousy Tag"
  def update_multiple
    unless @tag_set.user_is_moderator?(current_user)
      flash[:error] = ts("You don't have permission to do that.")
      redirect_to tag_set_path(@tag_set) and return
    end

    # Collate the input into @approve, @reject, @synonym, @change, checking for:
    # - invalid tag name changes
    # - approve & reject both selected
    # put errors in @errors, mark types to force to be expanded with @force_expand
    @approve = HashWithIndifferentAccess.new; @synonym = HashWithIndifferentAccess.new
    @reject = HashWithIndifferentAccess.new; @change = HashWithIndifferentAccess.new
    @errors = []; @force_expand = {}
    collect_update_multiple_results

    # If we have errors don't move ahead
    unless @errors.empty?
      render_index_on_error and return
    end

    # OK, now we're going ahead and making piles of db changes! eep! D:
    TagSet::TAG_TYPES_INITIALIZABLE.each do |tag_type|
      # we're adding the approved tags and synonyms
      @tagnames_to_add = @approve[tag_type] + @synonym[tag_type]
      @tagnames_to_remove = @reject[tag_type]

      # If we've approved a tag, change any other nominations that have this tag as a synonym to the synonym
      if @tagnames_to_add.present?
        tagnames_to_change = TagNomination.for_tag_set(@tag_set).where(type: "#{tag_type.classify}Nomination").where("synonym IN (?)", @tagnames_to_add).pluck(:tagname).uniq
        tagnames_to_change.each do |oldname|
          synonym = TagNomination.for_tag_set(@tag_set).where(type: "#{tag_type.classify}Nomination", tagname: oldname).pluck(:synonym).first
          unless TagNomination.change_tagname!(@tag_set, oldname, synonym)
            flash[:error] = ts("Oh no! We ran into a problem partway through saving your updates, changing %{oldname} to %{newname} -- please check over your tag set closely!",
              oldname: oldname, newname: synonym)
            render_index_on_error and return
          end
        end
      end

      # do the name changes
      @change[tag_type].each do |oldname, newname|
        if TagNomination.change_tagname!(@tag_set, oldname, newname)
          @tagnames_to_add << newname
        else
          # ughhhh
          flash[:error] = ts("Oh no! We ran into a problem partway through saving your updates, changing %{oldname} to %{newname} -- please check over your tag set closely!",
            oldname: oldname, newname: newname)
          render_index_on_error and return
        end
      end

      # update the tag set
      unless @tag_set.add_tagnames(tag_type, @tagnames_to_add) && @tag_set.remove_tagnames(tag_type, @tagnames_to_remove)
        @errors = @tag_set.errors.full_messages
        flash[:error] = ts("Oh no! We ran into a problem partway through saving your updates -- please check over your tag set closely!")
        render_index_on_error and return
      end

      @notice ||= []
      @notice << ts("Successfully added to set: %{approved}", approved: @tagnames_to_add.join(', ')) unless @tagnames_to_add.empty?
      @notice << ts("Successfully rejected: %{rejected}", rejected: @tagnames_to_remove.join(', ')) unless @tagnames_to_remove.empty?
    end

    # If we got here we made it through, YAY
    flash[:notice] = @notice
    if @tag_set.tag_nominations.unreviewed.empty?
      flash[:notice] << ts("All nominations reviewed, yay!")
      redirect_to tag_set_path(@tag_set)
    else
      flash[:notice] << ts("Still some nominations left to review!")
      redirect_to tag_set_nominations_path(@tag_set) and return
    end
  end

  protected

  def render_index_on_error
    setup_for_review
    render action: "index"
  end

  # gathers up the data for all the tag types
  def collect_update_multiple_results
    TagSet::TAG_TYPES_INITIALIZABLE.each do |tag_type|
      @approve[tag_type] = []
      @synonym[tag_type] = []
      @reject[tag_type] = []
      @change[tag_type] = []
    end

    params.each_pair do |key, val|
      next unless val.present?
      if key.match(/^([a-z]+)_(approve|reject|synonym|change)_(.*)$/)
        type = $1
        action = $2
        name = $3
        # fix back the tagname if it has [] brackets -- see _review_individual_nom for details
        name = name.gsub('#LBRACKET', '[').gsub('#RBRACKET', ']')
        if TagSet::TAG_TYPES_INITIALIZABLE.include?(type)
          # we're safe
          case action
          when "reject"
            @reject[type] << name
          when "approve"
            @approve[type] << name unless params["#{type}_change_#{name}"].present? && (params["#{type}_change_#{name}"] != name)
          when "synonym", "change"
            next if val == name
            # this is the tricky one: make sure we can do this name change
            tagnom = TagNomination.for_tag_set(@tag_set).where(type: "#{type.classify}Nomination", tagname: name).first
            if !tagnom
              @errors << ts("Couldn't find a #{type} nomination for #{name}")
              @force_expand[type] = true
            elsif !tagnom.change_tagname?(val)
              @errors << ts("Invalid name change for #{name} to #{val}: %{msg}", msg: tagnom.errors.full_messages.join(', '))
              @force_expand[type] = true
            elsif action == "synonym"
              @synonym[type] << val
            else
              @change[type] << [name, val]
            end
          end
        end
      end
    end

    TagSet::TAG_TYPES_INITIALIZABLE.each do |tag_type|
      unless (intersect = @approve[tag_type] & @reject[tag_type]).empty?
        @errors << ts("You have both approved and rejected the following %{type} tags: %{intersect}", type: tag_type, intersect: intersect.join(", "))
        @force_expand[tag_type] = true
      end
    end
  end

  private

  def tag_set_nomination_params
    params.require(:tag_set_nomination).permit(
      :pseud_id,
      fandom_nominations_attributes: [
        :id,
        :tagname,
        character_nominations_attributes: [
          :id, :tagname, :from_fandom_nomination
        ],
        relationship_nominations_attributes: [
          :id, :tagname, :from_fandom_nomination
        ],
      ],
      freeform_nominations_attributes: [
        :id, :tagname
      ],
      character_nominations_attributes: [
        :id, :tagname, :parent_tagname
      ],
      relationship_nominations_attributes: [
        :id, :tagname, :parent_tagname
      ]
    )
  end

end
