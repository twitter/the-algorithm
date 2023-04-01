class TagSetAssociationsController < ApplicationController
  cache_sweeper :tag_set_sweeper

  before_action :load_tag_set
  before_action :users_only
  before_action :moderators_only

  def load_tag_set
    @tag_set = OwnedTagSet.find_by(id: params[:tag_set_id])
    unless @tag_set
      flash[:error] = ts("What tag set did you want to look at?")
      redirect_to tag_sets_path and return
    end
  end

  def moderators_only
    @tag_set.user_is_moderator?(current_user) || access_denied
  end

  def index
    get_tags_to_associate
  end

  def update_multiple
    # we get params like "create_association_[tag_id]_[parent_tagname]"
    @errors = []
    params.each_pair do |key, val|
      next unless val.present?
      if key.match(/^create_association_(\d+)_(.*?)$/)
        tag_id = $1
        parent_tagname = $2

        # fix back the tagnames if they have [] brackets -- see _review_individual_nom for details
        parent_tagname = parent_tagname.gsub('#LBRACKET', '[').gsub('#RBRACKET', ']')

        assoc = @tag_set.tag_set_associations.build(tag_id: tag_id, parent_tagname: parent_tagname, create_association: true)
        if assoc.valid?
          assoc.save
        else
          @errors += assoc.errors.full_messages
        end
      end
    end

    if @errors.empty?
      flash[:notice] = ts("Nominated associations were added.")
      redirect_to tag_set_path(@tag_set)
    else
      flash[:error] = ts("We couldn't add all of your specified associations. See more detailed errors below!")
      get_tags_to_associate
      render action: :index
    end
  end



  protected
  def get_tags_to_associate
    # get the tags for which we have a parent nomination which doesn't already exist in the database
    @tags_to_associate = Tag.joins(:set_taggings).where("set_taggings.tag_set_id = ?", @tag_set.tag_set_id).
      joins("INNER JOIN tag_nominations ON tag_nominations.tagname = tags.name").
      joins("INNER JOIN tag_set_nominations ON tag_nominations.tag_set_nomination_id = tag_set_nominations.id").
      where("tag_set_nominations.owned_tag_set_id = ?", @tag_set.id).
      where("tag_nominations.parented = 0 AND tag_nominations.rejected != 1 AND EXISTS
        (SELECT * from tags WHERE tags.name = tag_nominations.parent_tagname)")

    # skip already associated tags
    associated_tag_ids = TagSetAssociation.where(owned_tag_set_id: @tag_set.id).pluck :tag_id
    @tags_to_associate = @tags_to_associate.where("tags.id NOT IN (?)", associated_tag_ids) unless associated_tag_ids.empty?

    # now get out just the tags and nominated parent tagnames
    @tags_to_associate = @tags_to_associate.select("DISTINCT tags.id, tags.name, tag_nominations.parent_tagname").
      order("tag_nominations.parent_tagname ASC, tags.name ASC")

  end

end
