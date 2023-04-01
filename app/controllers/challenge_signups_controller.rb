# For exporting to Excel CSV format
require 'csv'

class ChallengeSignupsController < ApplicationController
  include ExportsHelper

  before_action :users_only, except: [:summary, :display_summary, :requests_summary]
  before_action :load_collection, except: [:index]
  before_action :load_challenge, except: [:index]
  before_action :load_signup_from_id, only: [:show, :edit, :update, :destroy, :confirm_delete]
  before_action :allowed_to_destroy, only: [:destroy, :confirm_delete]
  before_action :signup_owner_only, only: [:edit, :update]
  before_action :maintainer_or_signup_owner_only, only: [:show]
  before_action :check_signup_open, only: [:new, :create, :edit, :update]
  before_action :check_pseud_ownership, only: [:create, :update]
  before_action :check_signup_in_collection, only: [:show, :edit, :update, :destroy, :confirm_delete]

  def load_challenge
    @challenge = @collection.challenge
    no_challenge and return unless @challenge
  end

  def no_challenge
    flash[:error] = ts("What challenge did you want to sign up for?")
    redirect_to collection_path(@collection) rescue redirect_to '/'
    false
  end

  def check_signup_open
    signup_closed and return unless (@challenge.signup_open || @collection.user_is_maintainer?(current_user))
  end

  def signup_closed
    flash[:error] = ts("Sign-up is currently closed: please contact a moderator for help.")
    redirect_to @collection rescue redirect_to '/'
    false
  end

  def signup_closed_owner?
    @collection.challenge_type == "GiftExchange" && !@challenge.signup_open && @collection.user_is_owner?(current_user)
  end

  def signup_owner_only
    not_signup_owner and return unless @challenge_signup.pseud.user == current_user || signup_closed_owner?
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

  def load_signup_from_id
    @challenge_signup = ChallengeSignup.find(params[:id])
    no_signup and return unless @challenge_signup
  end

  def no_signup
    flash[:error] = ts("What sign-up did you want to work on?")
    redirect_to collection_path(@collection) rescue redirect_to '/'
    false
  end

  def check_pseud_ownership
    if params[:challenge_signup][:pseud_id] && (pseud = Pseud.find(params[:challenge_signup][:pseud_id]))
      # either you have to own the pseud, OR you have to be a mod editing after signups are closed and NOT changing the pseud
      unless current_user.pseuds.include?(pseud) || (@challenge_signup && @challenge_signup.pseud == pseud && signup_closed_owner?)
        flash[:error] = ts("You can't sign up with that pseud.")
        redirect_to root_path and return
      end
    end
  end

  def check_signup_in_collection
    unless @challenge_signup.collection_id == @collection.id
      flash[:error] = ts("Sorry, that sign-up isn't associated with that collection.")
      redirect_to @collection
    end
  end

  #### ACTIONS

  def index
    if params[:user_id] && (@user = User.find_by(login: params[:user_id]))
      if current_user == @user
        @challenge_signups = @user.challenge_signups.order_by_date
        render action: :index and return
      else
        flash[:error] = ts("You aren't allowed to see that user's sign-ups.")
        redirect_to '/' and return
      end
    else
      load_collection
      load_challenge if @collection
      return false unless @challenge
    end

    # using respond_to in order to provide Excel output
    # see ExportsHelper for export_csv method
    respond_to do |format|
      format.html {
          if @challenge.user_allowed_to_see_signups?(current_user)
            @challenge_signups = @collection.signups.joins(:pseud)
            if params[:query]
              @query = params[:query]
              @challenge_signups = @challenge_signups.where("pseuds.name LIKE ?", '%' + params[:query] + '%')
            end
            @challenge_signups = @challenge_signups.order("pseuds.name").paginate(page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE)
          elsif params[:user_id] && (@user = User.find_by(login: params[:user_id]))
            @challenge_signups = @collection.signups.by_user(current_user)
          else
            not_allowed(@collection)
          end
      }
      format.csv {
        if (@collection.gift_exchange? && @challenge.user_allowed_to_see_signups?(current_user)) ||
        (@collection.prompt_meme? && @collection.user_is_maintainer?(current_user))
          csv_data = self.send("#{@challenge.class.name.underscore}_to_csv")
          filename = "#{@collection.name}_signups_#{Time.now.strftime('%Y-%m-%d-%H%M')}.csv"
          send_csv_data(csv_data, filename)
        else
          flash[:error] = ts("You aren't allowed to see the CSV summary.")
          redirect_to collection_path(@collection) rescue redirect_to '/' and return
        end
      }
    end
  end

  def summary
    @summary = ChallengeSignupSummary.new(@collection)

    if @collection.signups.count < (ArchiveConfig.ANONYMOUS_THRESHOLD_COUNT/2)
      flash.now[:notice] = ts("Summary does not appear until at least %{count} sign-ups have been made!", count: ((ArchiveConfig.ANONYMOUS_THRESHOLD_COUNT/2)))
    elsif @collection.signups.count > ArchiveConfig.MAX_SIGNUPS_FOR_LIVE_SUMMARY
      # too many signups in this collection to show the summary page "live"
      modification_time = @summary.cached_time

      # The time is always written alongside the cache, so if the time is
      # missing, then the cache must be missing as well -- and we want to
      # generate it. We also want to generate it if signups are open and it was
      # last generated more than an hour ago.
      if modification_time.nil? ||
         (@collection.challenge.signup_open? && modification_time < 1.hour.ago)

        # Touch the cache so that we don't try to generate the summary a second
        # time on subsequent page loads.
        @summary.touch_cache

        # Generate the cache of the summary in the background.
        @summary.enqueue_for_generation
      end
    else
      # generate it on the fly
      @tag_type = @summary.tag_type
      @summary_tags = @summary.summary
      @generated_live = true
    end
  end

  def show
    unless @challenge_signup.valid?
      flash[:error] = ts("This sign-up is invalid. Please check your sign-ups for a duplicate or edit to fix any other problems.")
    end
  end

  protected
  def build_prompts
    notice = ""
    @challenge.class::PROMPT_TYPES.each do |prompt_type|
      num_to_build = params["num_#{prompt_type}"] ? params["num_#{prompt_type}"].to_i : @challenge.required(prompt_type)
      if num_to_build < @challenge.required(prompt_type)
        notice += ts("You must submit at least %{required} #{prompt_type}. ", required: @challenge.required(prompt_type))
        num_to_build = @challenge.required(prompt_type)
      elsif num_to_build > @challenge.allowed(prompt_type)
        notice += ts("You can only submit up to %{allowed} #{prompt_type}. ", allowed: @challenge.allowed(prompt_type))
        num_to_build = @challenge.allowed(prompt_type)
      elsif params["num_#{prompt_type}"]
        notice += ts("Set up %{num} #{prompt_type.pluralize}. ", num: num_to_build)
      end
      num_existing = @challenge_signup.send(prompt_type).count
      num_existing.upto(num_to_build-1) do
        @challenge_signup.send(prompt_type).build
      end
    end
    unless notice.blank?
      flash[:notice] = notice
    end
  end

  public
  def new
    if (@challenge_signup = ChallengeSignup.in_collection(@collection).by_user(current_user).first)
      flash[:notice] = ts("You are already signed up for this challenge. You can edit your sign-up below.")
      redirect_to edit_collection_signup_path(@collection, @challenge_signup)
    else
      @challenge_signup = ChallengeSignup.new
      build_prompts
    end
  end

  def edit
    build_prompts
  end

  def create
    @challenge_signup = ChallengeSignup.new(challenge_signup_params)

    @challenge_signup.pseud = current_user.default_pseud unless @challenge_signup.pseud
    @challenge_signup.collection = @collection
    # we check validity first to prevent saving tag sets if invalid
    if @challenge_signup.valid? && @challenge_signup.save
      flash[:notice] = ts('Sign-up was successfully created.')
      redirect_to collection_signup_path(@collection, @challenge_signup)
    else
      render action: :new
    end
  end

  def update
    if @challenge_signup.update(challenge_signup_params)
      flash[:notice] = ts('Sign-up was successfully updated.')
      redirect_to collection_signup_path(@collection, @challenge_signup)
    else
      render action: :edit
    end
  end

  def confirm_delete
  end

  def destroy
    unless @challenge.signup_open || @collection.user_is_maintainer?(current_user)
      flash[:error] = ts("You cannot delete your sign-up after sign-ups are closed. Please contact a moderator for help.")
    else
      @challenge_signup.destroy
      flash[:notice] = ts("Challenge sign-up was deleted.")
    end
    if @collection.user_is_maintainer?(current_user) && !@collection.prompt_meme?
      redirect_to collection_signups_path(@collection)
    elsif @collection.prompt_meme?
      redirect_to collection_requests_path(@collection)
    else
      redirect_to @collection
    end
  end


protected

  def request_to_array(type, request)
    any_types = TagSet::TAG_TYPES.select {|type| request && request.send("any_#{type}")}
    any_types.map! { |type| ts("Any %{type}", type: type.capitalize) }
    tags = request.nil? ? [] : request.tag_set.tags.map {|tag| tag.name}
    rarray = [(tags + any_types).join(", ")]

    if @challenge.send("#{type}_restriction").optional_tags_allowed
      rarray << (request.nil? ? "" : request.optional_tag_set.tags.map {|tag| tag.name}.join(", "))
    end

    if @challenge.send("#{type}_restriction").title_allowed
      rarray << (request.nil? ? "" : sanitize_field(request, :title))
    end

    if @challenge.send("#{type}_restriction").description_allowed
      description = (request.nil? ? "" : sanitize_field(request, :description))
      # Didn't find a way to get Excel 2007 to accept line breaks
      # withing a field; not even when the row delimiter is set to
      # \r\n and linebreaks within the field are only \n. :-(
      #
      # Thus stripping linebreaks.
      rarray << description.gsub(/[\n\r]/, " ")
    end

    rarray << (request.nil? ? "" : request.url) if
      @challenge.send("#{type}_restriction").url_allowed

    return rarray
  end


  def gift_exchange_to_csv
    header = ["Pseud", "Email", "Sign-up URL"]

    %w(request offer).each do |type|
      @challenge.send("#{type.pluralize}_num_allowed").times do |i|
        header << "#{type.capitalize} #{i+1} Tags"
        header << "#{type.capitalize} #{i+1} Optional Tags" if
          @challenge.send("#{type}_restriction").optional_tags_allowed
        header << "#{type.capitalize} #{i+1} Title" if
          @challenge.send("#{type}_restriction").title_allowed
        header << "#{type.capitalize} #{i+1} Description" if
          @challenge.send("#{type}_restriction").description_allowed
        header << "#{type.capitalize} #{i+1} URL" if
          @challenge.send("#{type}_restriction").url_allowed
      end
    end

    csv_array = []
    csv_array << header

    @collection.signups.each do |signup|
      row = [signup.pseud.name, signup.pseud.user.email,
             collection_signup_url(@collection, signup)]

      %w(request offer).each do |type|
        @challenge.send("#{type.pluralize}_num_allowed").times do |i|
          row += request_to_array(type, signup.send(type.pluralize)[i])
        end
      end
      csv_array << row
    end

    csv_array
  end


  def prompt_meme_to_csv
    header = ["Pseud", "Sign-up URL", "Tags"]
    header << "Optional Tags" if @challenge.request_restriction.optional_tags_allowed
    header << "Title" if @challenge.request_restriction.title_allowed
    header << "Description" if @challenge.request_restriction.description_allowed
    header << "URL" if @challenge.request_restriction.url_allowed

    csv_array = []
    csv_array << header
    @collection.prompts.where(type: "Request").each do |request|
      row =
        if request.anonymous?
          ["(Anonymous)", ""]
        else
          [request.challenge_signup.pseud.name,
           collection_signup_url(@collection, request.challenge_signup)]
        end
      csv_array << (row + request_to_array("request", request))
    end

    csv_array
  end

  private

  def challenge_signup_params
    params.require(:challenge_signup).permit(
      :pseud_id,
      requests_attributes: nested_prompt_params,
      offers_attributes: nested_prompt_params
    )
  end

  def nested_prompt_params
    [
      :id,
      :title,
      :url,
      :any_fandom,
      :any_character,
      :any_relationship,
      :any_freeform,
      :any_category,
      :any_rating,
      :any_archive_warning,
      :anonymous,
      :description,
      :_destroy,
      tag_set_attributes: [
        :id,
        :updated_at,
        :character_tagnames,
        :relationship_tagnames,
        :freeform_tagnames,
        :category_tagnames,
        :rating_tagnames,
        :archive_warning_tagnames,
        :fandom_tagnames,
        character_tagnames: [],
        relationship_tagnames: [],
        freeform_tagnames: [],
        category_tagnames: [],
        rating_tagnames: [],
        archive_warning_tagnames: [],
        fandom_tagnames: [],
      ],
      optional_tag_set_attributes: [
        :tagnames
      ]
    ]
  end
end
