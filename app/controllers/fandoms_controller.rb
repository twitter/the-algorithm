class FandomsController < ApplicationController
  before_action :load_collection

  def index
    if @collection
      @media = Media.canonical.by_name - [Media.find_by(name: ArchiveConfig.MEDIA_NO_TAG_NAME)] - [Media.find_by(name: ArchiveConfig.MEDIA_UNCATEGORIZED_NAME)]
      @page_subtitle = @collection.title
      @medium = Media.find_by_name(params[:medium_id]) if params[:medium_id]
      @counts = SearchCounts.fandom_ids_for_collection(@collection)
      @fandoms = (@medium ? @medium.fandoms : Fandom.all).where(id: @counts.keys).by_name
    elsif params[:medium_id]
      if @medium = Media.find_by_name(params[:medium_id])
         @page_subtitle = @medium.name
        if @medium == Media.uncategorized
          @fandoms = @medium.fandoms.in_use.by_name
        else
          @fandoms = @medium.fandoms.canonical.by_name.with_count
        end
      else
        raise ActiveRecord::RecordNotFound, "Couldn't find media category named '#{params[:medium_id]}'"
      end
    else
      redirect_to media_path(notice: "Please choose a media category to start browsing fandoms.")
      return
    end
    @fandoms_by_letter = @fandoms.group_by {|f| f.sortable_name[0].upcase}
  end

  def show
    @fandom = Fandom.find_by_name(params[:id])
    if @fandom.nil?
      flash[:error] = ts("Could not find fandom named %{fandom_name}", fandom_name: params[:id])
      redirect_to media_path and return
    end
    @characters = @fandom.characters.canonical.by_name
  end

  def unassigned
    join_string = "LEFT JOIN wrangling_assignments
                  ON (wrangling_assignments.fandom_id = tags.id)
                  LEFT JOIN users
                  ON (users.id = wrangling_assignments.user_id)"
    conditions = "canonical = 1 AND users.id IS NULL"
    unless params[:media_id].blank?
      @media = Media.find_by_name(params[:media_id])
      if @media
        join_string <<  " INNER JOIN common_taggings
                        ON (tags.id = common_taggings.common_tag_id)"
        conditions  << " AND common_taggings.filterable_id = #{@media.id}
                        AND common_taggings.filterable_type = 'Tag'"
      end
    end
    @fandoms = Fandom.joins(join_string).
                      where(conditions).
                      order(params[:sort] == 'count' ? "count DESC" : "sortable_name ASC").
                      with_count.
                      paginate(page: params[:page], per_page: 250)
  end
end
