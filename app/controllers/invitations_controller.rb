class InvitationsController < ApplicationController
  before_action :check_permission
  before_action :admin_only, only: [:create, :destroy]
  before_action :check_user_status, only: [:index, :manage, :invite_friend, :update]
  before_action :load_invitation, only: [:show, :invite_friend, :update, :destroy]
  before_action :check_ownership_or_admin, only: [:show, :invite_friend, :update]

  def load_invitation
    @invitation = Invitation.find(params[:id] || invitation_params[:id])
    @check_ownership_of = @invitation
  end

  def check_permission
    @user = User.find_by(login: params[:user_id])
    access_denied unless policy(User).can_manage_users? || @user.present? && @user == current_user
  end

  def index
    @unsent_invitations = @user.invitations.unsent.limit(5)
  end

  def manage
    status = params[:status]
    @invitations = @user.invitations
    if %w(unsent unredeemed redeemed).include?(status)
      @invitations = @invitations.send(status)
    end
  end

  def show
  end

  def invite_friend
    if !invitation_params[:invitee_email].blank?
      @invitation.invitee_email = invitation_params[:invitee_email]
      if @invitation.save
        flash[:notice] = 'Invitation was successfully sent.'
        redirect_to([@user, @invitation])
      else
        render action: "show"
      end
    else
      flash[:error] = "Please enter an email address."
      render action: "show"
    end
  end

  def create
    if invitation_params[:number_of_invites].to_i > 0
      invitation_params[:number_of_invites].to_i.times do
        @user.invitations.create
      end
    end
    flash[:notice] = "Invitations were successfully created."
    redirect_to user_invitations_path(@user)
  end

  def update
    @invitation.attributes = invitation_params

    if @invitation.invitee_email_changed? && @invitation.update(invitation_params)
      flash[:notice] = 'Invitation was successfully sent.'
      if logged_in_as_admin?
        redirect_to find_admin_invitations_path("invitation[token]" => @invitation.token)
      else
        redirect_to([@user, @invitation])
      end
    else
      flash[:error] = "Please enter an email address." if @invitation.invitee_email.blank?
      render action: "show"
    end
  end

  def destroy
    @user = @invitation.creator
    if @invitation.destroy
      flash[:notice] = "Invitation successfully destroyed"
    else
      flash[:error] = "Invitation was not destroyed."
    end
    if @user.is_a?(User)
      redirect_to user_invitations_path(@user)
    else
      redirect_to admin_invitations_path
    end
  end

  private

  def invitation_params
    params.require(:invitation).permit(:id, :invitee_email, :number_of_invites)
  end
end
