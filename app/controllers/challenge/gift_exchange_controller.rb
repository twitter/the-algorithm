class Challenge::GiftExchangeController < ChallengesController

  before_action :users_only
  before_action :load_collection
  before_action :load_challenge, except: [:new, :create]
  before_action :collection_owners_only, only: [:new, :create, :edit, :update, :destroy]

  # ACTIONS

  def show
  end

  def new
    if (@collection.challenge)
      flash[:notice] = ts("There is already a challenge set up for this collection.")
      # TODO this will break if the challenge isn't a gift exchange
      redirect_to edit_collection_gift_exchange_path(@collection)
    else
      @challenge = GiftExchange.new
    end
  end

  def edit
  end

  def create
    @challenge = GiftExchange.new(gift_exchange_params)
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
    if @challenge.update(gift_exchange_params)
      flash[:notice] = ts('Challenge was successfully updated.')

      # expire the cache on the signup form
      ActionController::Base.new.expire_fragment('challenge_signups/new')

      # see if we initialized the tag set
      redirect_to collection_profile_path(@collection)
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

  def gift_exchange_params
    params.require(:gift_exchange).permit(
      :signup_open, :time_zone, :signups_open_at_string, :signups_close_at_string,
      :assignments_due_at_string, :requests_summary_visible, :requests_num_required,
      :requests_num_allowed, :offers_num_required, :offers_num_allowed,
      :signup_instructions_general, :signup_instructions_requests,
      :signup_instructions_offers, :request_url_label, :offer_url_label,
      :offer_description_label, :request_description_label, :works_reveal_at_string,
      :authors_reveal_at_string,
      request_restriction_attributes: [
        :id, :optional_tags_allowed, :title_required, :title_allowed, :description_required,
        :description_allowed, :url_required, :url_allowed, :fandom_num_required,
        :fandom_num_allowed, :allow_any_fandom, :require_unique_fandom,
        :character_num_required, :character_num_allowed, :allow_any_character,
        :require_unique_character, :relationship_num_required, :relationship_num_allowed,
        :allow_any_relationship, :require_unique_relationship, :rating_num_required,
        :rating_num_allowed, :allow_any_rating, :require_unique_rating,
        :category_num_required, :category_num_allowed, :allow_any_category,
        :require_unique_category, :freeform_num_required, :freeform_num_allowed,
        :allow_any_freeform, :require_unique_freeform, :archive_warning_num_required,
        :archive_warning_num_allowed, :allow_any_archive_warning, :require_unique_archive_warning
      ],
      offer_restriction_attributes: [
        :id, :optional_tags_allowed, :title_required, :title_allowed,
        :description_required, :description_allowed, :url_required, :url_allowed,
        :fandom_num_required, :fandom_num_allowed, :allow_any_fandom,
        :require_unique_fandom, :character_num_required, :character_num_allowed,
        :allow_any_character, :require_unique_character, :relationship_num_required,
        :relationship_num_allowed, :allow_any_relationship, :require_unique_relationship,
        :rating_num_required, :rating_num_allowed, :rating_num_required, :allow_any_rating, :require_unique_rating,
        :category_num_required, :category_num_allowed, :allow_any_category,
        :require_unique_category, :freeform_num_required, :freeform_num_allowed,
        :allow_any_freeform, :require_unique_freeform, :archive_warning_num_required,
        :archive_warning_num_allowed, :allow_any_archive_warning, :require_unique_archive_warning,
        :tag_sets_to_add, :character_restrict_to_fandom,
        :character_restrict_to_tag_set, :relationship_restrict_to_fandom,
        :relationship_restrict_to_tag_set,
        tag_sets_to_remove: []
      ],
      potential_match_settings_attributes: [
        :id, :num_required_prompts, :num_required_fandoms, :num_required_characters,
        :num_required_relationships, :num_required_freeforms, :num_required_categories,
        :num_required_ratings, :num_required_archive_warnings, :include_optional_fandoms,
        :include_optional_characters, :include_optional_relationships,
        :include_optional_freeforms, :include_optional_categories, :include_optional_ratings,
        :include_optional_archive_warnings
      ]
    )
  end
end
