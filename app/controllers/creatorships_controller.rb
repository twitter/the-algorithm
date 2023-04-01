# frozen_string_literal: true

# A controller for viewing co-creator requests -- that is, creatorships where
# the creator hasn't yet approved it.
class CreatorshipsController < ApplicationController
  before_action :load_user
  before_action :check_ownership

  # Show all of the creatorships associated with the current user. Displays a
  # form where the user can select multiple creatorships and perform actions
  # (accept, reject) in bulk.
  def show
    @page_subtitle = ts("Co-Creator Requests")
    @creatorships = @creatorships.unapproved.order(id: :desc).
                    paginate(page: params[:page])
  end

  # Update the selected creatorships.
  def update
    @creatorships = @creatorships.where(id: params[:selected])

    if params[:accept]
      accept_update
    elsif params[:reject]
      reject_update
    end

    redirect_to user_creatorships_path(@user, page: params[:page])
  end

  private

  # When the user presses "Accept" on the co-creator request listing, this is
  # the code that runs.
  def accept_update
    flash[:notice] = []

    @creatorships.each do |creatorship|
      creatorship.accept!
      link = view_context.link_to(title_for_creation(creatorship.creation),
                                  creatorship.creation)
      flash[:notice] << ts("You are now listed as a co-creator on %{link}.",
                           link: link).html_safe
    end
  end

  # When the user presses "Reject" on the co-creator request listing, this is
  # the code that runs. Note that rejection is equivalent to destroying the
  # request.
  def reject_update
    @creatorships.each(&:destroy)
    flash[:notice] = ts("Requests rejected.")
  end

  # A helper method used to display a nicely formatted title for a creation.
  helper_method :title_for_creation
  def title_for_creation(creation)
    if creation.is_a?(Chapter)
      "Chapter #{creation.position} of #{creation.work.title}"
    else
      creation.title
    end
  end

  # Load the user, and set @creatorships equal to all co-creator requests for
  # that user.
  def load_user
    @user = User.find_by!(login: params[:user_id])
    @check_ownership_of = @user
    @creatorships = Creatorship.unapproved.for_user(@user)
  end
end
