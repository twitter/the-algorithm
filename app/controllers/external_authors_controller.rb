class ExternalAuthorsController < ApplicationController
  before_action :load_user
  before_action :check_ownership, only: [:create, :edit, :destroy, :new]
  before_action :check_user_status, only: [:new, :create, :edit]
  before_action :get_external_author_from_invitation, only: [:claim, :complete_claim]
  before_action :users_only, only: [:complete_claim]

  def load_user
    @user = User.find_by(login: params[:user_id])
    @check_ownership_of = @user
  end

  def index
    if @user && current_user == @user
      @external_authors = @user.external_authors
    elsif logged_in? && current_user.archivist
      @external_authors = ExternalCreatorship.where(archivist_id: current_user).collect(&:external_author).uniq
    elsif logged_in?
      redirect_to user_external_authors_path(current_user)
    else
      flash[:notice] = "You can't see that information."
      redirect_to root_path
    end
  end

  def edit
    @external_author = ExternalAuthor.find(params[:id])
  end

  def get_external_author_from_invitation
    token = params[:invitation_token] || (params[:user] && params[:user][:invitation_token])
    @invitation = Invitation.find_by(token: token)
    unless @invitation
      flash[:error] = ts("You need an invitation to do that.")
      redirect_to root_path
      return
    end

    @external_author = @invitation.external_author
    return if @external_author
    flash[:error] = ts("There are no stories to claim on this invitation. Did you want to sign up instead?")
    redirect_to signup_path(@invitation.token)
  end

  def claim
  end

  def complete_claim
    # go ahead and give the user the works
    @external_author.claim!(current_user)
    @invitation.mark_as_redeemed(current_user) if @invitation
    flash[:notice] = t('external_author_claimed', default: "We have added the stories imported under %{email} to your account.", email: @external_author.email)
    redirect_to user_external_authors_path(current_user)
  end

  def update
    @invitation = Invitation.find_by(token: params[:invitation_token])
    @external_author = ExternalAuthor.find(params[:id])
    unless (@invitation && @invitation.external_author == @external_author) || @external_author.user == current_user
      flash[:error] = "You don't have permission to do that."
      redirect_to root_path
      return
    end

    flash[:notice] = ""
    if params[:imported_stories] == "nothing"
      flash[:notice] += "Okay, we'll leave things the way they are! You can use the email link any time if you change your mind. "
    elsif params[:imported_stories] == "orphan"
      # orphan the works
      @external_author.orphan(params[:remove_pseud])
      flash[:notice] += "Your imported stories have been orphaned. Thank you for leaving them in the archive! "
    elsif params[:imported_stories] == "delete"
      # delete the works
      @external_author.delete_works
      flash[:notice] += "Your imported stories have been deleted. "
    end

    if @invitation &&
       params[:imported_stories].present? &&
       params[:imported_stories] != "nothing"
      @invitation.mark_as_redeemed
    end

    if @external_author.update(external_author_params[:external_author] || {})
      flash[:notice] += "Your preferences have been saved."
      redirect_to @user ? user_external_authors_path(@user) : root_path
    else
      flash[:error] = "There were problems saving your preferences."
      render action: "edit"
    end
  end

  private

  def external_author_params
    params.permit(
      :id, :user_id, :utf8, :_method, :authenticity_token, :invitation_token,
      :imported_stories, :commit, :remove_pseud,
      external_author: [
        :email, :do_not_email, :do_not_import
      ]
    )
  end
end
