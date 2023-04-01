class Admin::AdminInvitationsController < Admin::BaseController

  def index
  end

  def create
    @invitation = current_admin.invitations.new(invitation_params)

    if @invitation.invitee_email.blank?
      flash[:error] = t('no_email', default: "Please enter an email address.")
      render action: 'index'
    elsif @invitation.save
      flash[:notice] = t('sent', default: "An invitation was sent to %{email_address}", email_address: @invitation.invitee_email)
      redirect_to admin_invitations_path
    else
      render action: 'index'
    end
  end

  def invite_from_queue
    count = invitation_params[:invite_from_queue].to_i
    InviteFromQueueJob.perform_later(count: count, creator: current_admin)

    flash[:notice] = t(".success", count: count)
    redirect_to admin_invitations_path
  end

  def grant_invites_to_users
    if invitation_params[:user_group] == "All"
      Invitation.grant_all(invitation_params[:number_of_invites].to_i)
    else
      Invitation.grant_empty(invitation_params[:number_of_invites].to_i)
    end
    flash[:notice] = t('invites_created', default: 'Invitations successfully created.')
    redirect_to admin_invitations_path
  end

  def find
    unless invitation_params[:user_name].blank?
      @user = User.find_by(login: invitation_params[:user_name])
      @hide_dashboard = true
      @invitations = @user.invitations if @user
    end
    if !invitation_params[:token].blank?
      @invitation = Invitation.find_by(token: invitation_params[:token])
    elsif !invitation_params[:invitee_email].blank?
      @invitations = Invitation.where('invitee_email LIKE ?', "%#{invitation_params[:invitee_email]}%")
      @invitation = @invitations.first if @invitations.length == 1
    end
    unless @user || @invitation || @invitations
      flash.now[:error] = t('user_not_found', default: "No results were found. Try another search.")
    end
  end

  private

  def invitation_params
    params.require(:invitation).permit(:invitee_email, :invite_from_queue,
      :user_group, :number_of_invites, :user_name, :token)
  end
end
