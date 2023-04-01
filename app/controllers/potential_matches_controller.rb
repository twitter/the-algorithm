class PotentialMatchesController < ApplicationController

  before_action :users_only
  before_action :load_collection
  before_action :collection_maintainers_only
  before_action :load_challenge
  before_action :check_assignments_not_sent
  before_action :check_signup_closed, only: [:generate]
  before_action :load_potential_match_from_id, only: [:show]


  def load_challenge
    @challenge = @collection.challenge
    no_challenge and return unless @challenge
  end

  def no_challenge
    flash[:error] = ts("What challenge did you want to sign up for?")
    redirect_to collection_path(@collection) rescue redirect_to '/'
    false
  end

  def load_potential_match_from_id
    @potential_match = PotentialMatch.find(params[:id])
    no_potential_match and return unless @potential_match
  end

  def no_assignment
    flash[:error] = ts("What potential match did you want to work on?")
    redirect_to collection_path(@collection) rescue redirect_to '/'
    false
  end

  def check_signup_closed
    signup_open and return unless !@challenge.signup_open
  end

  def signup_open
    flash[:error] = ts("Sign-up is still open, you cannot determine potential matches now.")
    redirect_to @collection rescue redirect_to '/'
    false
  end

  def check_assignments_not_sent
    assignments_sent and return unless @challenge.assignments_sent_at.nil?
  end

  def assignments_sent
    flash[:error] = ts("Assignments have already been sent! If necessary, you can purge them.")
    redirect_to collection_assignments_path(@collection) rescue redirect_to '/'
    false
  end

  def index
    @settings = @collection.challenge.potential_match_settings

    if (invalid_ids = PotentialMatch.invalid_signups_for(@collection)).present?
      # there are invalid signups
      @invalid_signups = ChallengeSignup.where(id: invalid_ids)
    elsif PotentialMatch.in_progress?(@collection)
      # we're generating
      @in_progress = true
      @progress = PotentialMatch.progress(@collection)
    elsif ChallengeAssignment.in_progress?(@collection)
      @assignment_in_progress = true
    elsif @collection.potential_matches.count > 0 && @collection.assignments.count == 0
      flash[:error] = ts("There has been an error in the potential matching. Please first try regenerating assignments, and if that doesn't work, all potential matches. If the problem persists, please contact Support.")
    elsif @collection.potential_matches.count > 0
      # we have potential_matches and assignments

      ### find assignments with no potential recipients
      # first get signups with no offer potential matches
      no_opms = ChallengeSignup.in_collection(@collection).no_potential_offers.pluck(:id)
      @assignments_with_no_potential_recipients = @collection.assignments.where(offer_signup_id: no_opms)

      ### find assignments with no potential giver
      # first get signups with no request potential matches
      no_rpms = ChallengeSignup.in_collection(@collection).no_potential_requests.pluck(:id)
      @assignments_with_no_potential_givers = @collection.assignments.where(request_signup_id: no_rpms)

      # list the assignments by requester
      if params[:no_giver]
        @assignments = @collection.assignments.with_request.with_no_offer.order_by_requesting_pseud
      elsif params[:no_recipient]
        # ordering causes this to hang on large challenge due to
        # left join required to get offering pseuds
        @assignments = @collection.assignments.with_offer.with_no_request # .order_by_offering_pseud
      elsif params[:dup_giver]
        @assignments = ChallengeAssignment.duplicate_givers(@collection).order_by_offering_pseud
      elsif params[:dup_recipient]
        @assignments = ChallengeAssignment.duplicate_recipients(@collection).order_by_requesting_pseud
      else
        @assignments = @collection.assignments.with_request.with_offer.order_by_requesting_pseud
      end
      @assignments = @assignments.paginate page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE
    end
  end

  # Generate potential matches
  def generate
    if PotentialMatch.in_progress?(@collection)
      flash[:error] = ts("Potential matches are already being generated for this collection!")
    else
      # delete all existing assignments and potential matches for this collection
      ChallengeAssignment.clear!(@collection)
      PotentialMatch.clear!(@collection)

      flash[:notice] = ts("Beginning generation of potential matches. This may take some time, especially if your challenge is large.")
      PotentialMatch.set_up_generating(@collection)
      PotentialMatch.generate(@collection)
    end

    # redirect to index
    redirect_to collection_potential_matches_path(@collection)
  end

  # Regenerate matches for one signup
  def regenerate_for_signup
    if params[:signup_id].blank? || (@signup = ChallengeSignup.where(id: params[:signup_id]).first).nil?
      flash[:error] = ts("What sign-up did you want to regenerate matches for?")
    else
      PotentialMatch.regenerate_for_signup(@signup)
      flash[:notice] = ts("Matches are being regenerated for ") + @signup.pseud.byline +
        ts(". Please allow at least 5 minutes for this process to complete before refreshing the page.")
    end
    # redirect to index
    redirect_to collection_potential_matches_path(@collection)
  end

  def cancel_generate
    if !PotentialMatch.in_progress?(@collection)
      flash[:error] = ts("Potential matches are not currently being generated for this challenge.")
    elsif PotentialMatch.canceled?(@collection)
      flash[:error] = ts("Potential match generation has already been canceled, please refresh again shortly.")
    else
      PotentialMatch.cancel_generation(@collection)
      flash[:notice] = ts("Potential match generation cancellation requested. This may take a while, please refresh shortly.")
    end

    redirect_to collection_potential_matches_path(@collection)
  end

  def show
  end

end
