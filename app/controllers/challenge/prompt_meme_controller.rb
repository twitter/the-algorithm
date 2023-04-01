class Challenge::PromptMemeController < ChallengesController

  before_action :users_only
  before_action :load_collection
  before_action :load_challenge, except: [:new, :create]
  before_action :collection_owners_only, only: [:new, :create, :edit, :update, :destroy]

  # ACTIONS

  # is actually a blank page - should it be redirected to collection profile?
  def show
  end

  # The new form for prompt memes is actually the challenge settings page because challenges are always created in the context of a collection.
  def new
    if (@collection.challenge)
      flash[:notice] = ts("There is already a challenge set up for this collection.")
      redirect_to edit_collection_prompt_meme_path(@collection)
    else
      @challenge = PromptMeme.new
    end
  end

  def edit
  end

  def create
    @challenge = PromptMeme.new(prompt_meme_params)
    if @challenge.save
      @collection.challenge = @challenge
      @collection.save
      flash[:notice] = ts('Challenge was successfully created.')
      redirect_to collection_profile_path(@collection)
    else
      render action: :new
    end
  end

  def update
    if @challenge.update(prompt_meme_params)
      flash[:notice] = 'Challenge was successfully updated.'
      # expire the cache on the signup form
      ActionController::Base.new.expire_fragment('challenge_signups/new')
      redirect_to @collection
    else
      render action: :edit
    end
  end

  def destroy
    @challenge.destroy
    flash[:notice] = 'Challenge settings were deleted.'
    redirect_to @collection
  end

  private

  def prompt_meme_params
    params.require(:prompt_meme).permit(
      :signup_open, :time_zone, :signups_open_at_string, :signups_close_at_string,
      :assignments_due_at_string, :anonymous, :requests_num_required, :requests_num_allowed,
      :signup_instructions_general, :signup_instructions_requests, :request_url_label,
      :request_description_label, :works_reveal_at_string, :authors_reveal_at_string,
      request_restriction_attributes: [ :id, :optional_tags_allowed, :title_required,
        :title_allowed, :description_required, :description_allowed, :url_required,
        :url_allowed, :fandom_num_required, :fandom_num_allowed, :require_unique_fandom,
        :character_num_required, :character_num_allowed, :category_num_required,
        :category_num_allowed, :require_unique_category, :require_unique_character,
        :relationship_num_required, :relationship_num_allowed, :require_unique_relationship,
        :rating_num_required, :rating_num_allowed, :require_unique_rating,
        :freeform_num_required, :freeform_num_allowed, :require_unique_freeform,
        :archive_warning_num_required, :archive_warning_num_allowed, :require_unique_archive_warning,
        :tag_sets_to_remove, :tag_sets_to_add, :character_restrict_to_fandom,
        :character_restrict_to_tag_set, :relationship_restrict_to_fandom,
        :relationship_restrict_to_tag_set, tag_sets_to_remove: []
      ]
    )
  end

end
