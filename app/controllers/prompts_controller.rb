class PromptsController < ApplicationController

  before_action :users_only, except: [:show]
  before_action :load_collection, except: [:index]
  before_action :load_challenge, except: [:index]
  before_action :load_prompt_from_id, only: [:show, :edit, :update, :destroy]
  before_action :load_signup, except: [:index, :destroy, :show]
  # before_action :promptmeme_only, except: [:index, :new]
  before_action :allowed_to_destroy, only: [:destroy]
  before_action :allowed_to_view, only: [:show]
  before_action :signup_owner_only, only: [:edit, :update]
  before_action :check_signup_open, only: [:new, :create, :edit, :update]
  before_action :check_prompt_in_collection, only: [:show, :edit, :update, :destroy]

  # def promptmeme_only
  #   unless @collection.challenge_type == "PromptMeme"
  #     flash[:error] = ts("Only available for prompt meme challenges, not gift exchanges")
  #     redirect_to collection_path(@collection) rescue redirect_to '/'
  #   end
  # end

  def load_challenge
    @challenge = @collection.challenge
    no_challenge and return unless @challenge
  end

  def no_challenge
    flash[:error] = ts("What challenge did you want to sign up for?")
    redirect_to collection_path(@collection) rescue redirect_to '/'
    false
  end

  def load_signup
    unless @challenge_signup
      @challenge_signup = ChallengeSignup.in_collection(@collection).by_user(current_user).first
    end
    no_signup and return unless @challenge_signup
  end

  def no_signup
    flash[:error] = ts("Please submit a basic sign-up with the required fields first.")
    redirect_to new_collection_signup_path(@collection) rescue redirect_to '/'
    false
  end

  def check_signup_open
    signup_closed and return unless (@challenge.signup_open || @collection.user_is_owner?(current_user) || @collection.user_is_moderator?(current_user))
  end

  def signup_closed
    flash[:error] = ts("Signup is currently closed: please contact a moderator for help.")
    redirect_to @collection rescue redirect_to '/'
    false
  end

  def signup_owner_only
    not_signup_owner and return unless (@challenge_signup.pseud.user == current_user || (@collection.challenge_type == "GiftExchange" && !@challenge.signup_open && @collection.user_is_owner?(current_user)))
  end

  def maintainer_or_signup_owner_only
    not_allowed(@collection) and return unless (@challenge_signup.pseud.user == current_user || @collection.user_is_maintainer?(current_user))
  end

  def not_signup_owner
    flash[:error] = ts("You can't edit someone else's sign-up!")
    redirect_to @collection
    false
  end

  def allowed_to_destroy
    @challenge_signup.user_allowed_to_destroy?(current_user) || not_allowed(@collection)
  end

  def load_prompt_from_id
    @prompt = Prompt.find_by(id: params[:id])
    if @prompt.nil?
      no_prompt
      return
    end
    @challenge_signup = @prompt.challenge_signup
  end

  def no_prompt
    flash[:error] = ts("What prompt did you want to work on?")
    redirect_to collection_path(@collection) rescue redirect_to '/'
    false
  end

  def check_prompt_in_collection
    unless @prompt.collection_id == @collection.id
      flash[:error] = ts("Sorry, that prompt isn't associated with that collection.")
      redirect_to @collection
    end
  end

  def allowed_to_view
    unless @challenge.user_allowed_to_see_prompt?(current_user, @prompt)
      access_denied(redirect: @collection)
    end
  end

  #### ACTIONS

  def index
    # this currently doesn't get called anywhere
    # should probably list all the prompts in a given collection (instead of using challenge signup for that)
  end

  def show
  end

  def new
    if params[:prompt_type] == "offer"
      @index = @challenge_signup.offers.count
      @prompt = @challenge_signup.offers.build
    else
      @index = @challenge_signup.requests.count
      @prompt = @challenge_signup.requests.build
    end
  end

  def edit
    @index = @challenge_signup.send(@prompt.class.name.downcase.pluralize).index(@prompt)
  end

  def create
    if params[:prompt_type] == "offer"
      @prompt = @challenge_signup.offers.build(prompt_params)
    else
      @prompt = @challenge_signup.requests.build(prompt_params)
    end

    if !@challenge_signup.valid?
      flash[:error] = ts("That prompt would make your overall sign-up invalid, sorry.")
      redirect_to edit_collection_signup_path(@collection, @challenge_signup)
    elsif @prompt.save
      flash[:notice] = ts("Prompt was successfully added.")
      redirect_to collection_signup_path(@collection, @challenge_signup)
    else
      render action: :new
    end
  end

  def update
    if @prompt.update(prompt_params)
      flash[:notice] = ts("Prompt was successfully updated.")
      redirect_to collection_signup_path(@collection, @challenge_signup)
    else
      render action: :edit
    end
  end

  def destroy
    if !(@challenge.signup_open || @collection.user_is_maintainer?(current_user))
      flash[:error] = ts("You cannot delete a prompt after sign-ups are closed. Please contact a moderator for help.")
    else
      if !@prompt.can_delete?
        flash[:error] = ts("That would make your sign-up invalid, sorry! Please edit instead.")
      else
        @prompt.destroy
        flash[:notice] = ts("Prompt was deleted.")
      end
    end
    if @collection.user_is_maintainer?(current_user) && @collection.challenge_type == "PromptMeme"
      redirect_to collection_requests_path(@collection)
    elsif @prompt.challenge_signup
      redirect_to collection_signup_path(@collection, @prompt.challenge_signup)
    elsif @collection.user_is_maintainer?(current_user)
      redirect_to collection_signups_path(@collection)
    else
      redirect_to @collection
    end
  end

  private

  def prompt_params
    params.require(:prompt).permit(
      :title,
      :url,
      :anonymous,
      :description,
      :any_fandom,
      :any_character,
      :any_relationship,
      :any_freeform,
      :any_category,
      :any_rating,
      :any_archive_warning,
      tag_set_attributes: [
        :fandom_tagnames,
        :id,
        :updated_at,
        :character_tagnames,
        :relationship_tagnames,
        :freeform_tagnames,
        :category_tagnames,
        :rating_tagnames,
        :archive_warning_tagnames,
        fandom_tagnames: [],
        character_tagnames: [],
        relationship_tagnames: [],
        freeform_tagnames: [],
        category_tagnames: [],
        rating_tagnames: [],
        archive_warning_tagnames: []
      ],
      optional_tag_set_attributes: [
        :tagnames
      ]
    )
  end
end
