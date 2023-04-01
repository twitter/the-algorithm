class ChallengeRequestsController < ApplicationController

  before_action :load_collection
  before_action :check_visibility

  def check_visibility
    unless @collection
      flash.now[:notice] = ts("Collection could not be found")
      redirect_to '/' and return
    end
    unless @collection.challenge_type == "PromptMeme" || (@collection.challenge_type == "GiftExchange" && @collection.challenge.user_allowed_to_see_requests_summary?(current_user))
      flash.now[:notice] = ts("You are not allowed to view the requests summary!")
      redirect_to collection_path(@collection) and return
    end
  end

  def index
    @show_request_fandom_tags = @collection.challenge.request_restriction.allowed("fandom").positive?

    # sorting
    set_sort_order
    direction = (@sort_direction.casecmp("ASC").zero? ? "ASC" : "DESC")

    # actual content, do the efficient method unless we need the full query

    if @sort_column == "fandom"
      query = "SELECT prompts.*, GROUP_CONCAT(tags.name) AS tagnames FROM prompts INNER JOIN set_taggings ON prompts.tag_set_id = set_taggings.tag_set_id 
      INNER JOIN tags ON tags.id = set_taggings.tag_id 
      WHERE prompts.type = 'Request' AND tags.type = 'Fandom' AND prompts.collection_id = " + @collection.id.to_s + " GROUP BY prompts.id ORDER BY tagnames " + @sort_direction
      @requests = Prompt.paginate_by_sql(query, page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE)
    elsif @sort_column == "prompter" && !@collection.prompts.where(anonymous: true).exists?
      @requests = @collection.prompts.where("type = 'Request'").
        joins(challenge_signup: :pseud).
        order("pseuds.name #{direction}").
        paginate(page: params[:page])
    else
      @requests = @collection.prompts.where("type = 'Request'").order(@sort_order).paginate(page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE)
    end
  end

end
