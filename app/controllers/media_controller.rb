class MediaController < ApplicationController
  before_action :load_collection
  skip_before_action :store_location, only: [:show]

  def index
    uncategorized = Media.uncategorized
    @media = Media.by_name - [Media.find_by_name(ArchiveConfig.MEDIA_NO_TAG_NAME), uncategorized] + [uncategorized]
    @fandom_listing = {}
    @media.each do |medium|
      if medium == uncategorized
        @fandom_listing[medium] = medium.children.in_use.by_type('Fandom').order('created_at DESC').limit(5)
      else
        @fandom_listing[medium] = (logged_in? || logged_in_as_admin?) ?
          # was losing the select trying to do this through the parents association
          Fandom.unhidden_top(5).joins(:common_taggings).where(canonical: true, common_taggings: {filterable_id: medium.id, filterable_type: 'Tag'}) :
          Fandom.public_top(5).joins(:common_taggings).where(canonical: true, common_taggings: {filterable_id: medium.id, filterable_type: 'Tag'})
      end
    end
    @page_subtitle = ts("Fandoms")
  end

  def show
    redirect_to medium_fandoms_path(medium_id: params[:id])
  end
end
