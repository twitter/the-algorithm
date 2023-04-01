class Users::RegistrationsController < Devise::RegistrationsController
  before_action :check_account_creation_status
  before_action :configure_permitted_parameters

  def new
    super do |resource|
      if params[:invitation_token]
        @invitation = Invitation.find_by(token: params[:invitation_token])
        resource.invitation_token = @invitation.token
        resource.email = @invitation.invitee_email
      end

      @hide_dashboard = true
    end
  end

  def create
    @hide_dashboard = true
    if params[:cancel_create_account]
      redirect_to root_path
    else
      build_resource(sign_up_params)

      resource.transaction do
        # skip sending the Devise confirmation notification
        resource.skip_confirmation_notification!
        resource.invitation_token = params[:invitation_token]
        if resource.save
          notify_and_show_confirmation_screen
        else
          render action: 'new'
        end
      end
    end
  end

  private

  def notify_and_show_confirmation_screen
    # deliver synchronously to avoid getting caught in backed-up mail queue
    UserMailer.signup_notification(resource.id).deliver_now

    flash[:notice] = ts("During testing you can activate via <a href='%{activation_url}'>your activation url</a>.",
                        activation_url: activate_path(resource.confirmation_token)).html_safe if Rails.env.development?

    render 'users/confirmation'
  end

  def configure_permitted_parameters
    params[:user] = params[:user_registration]&.merge(
      accepted_tos_version: @current_tos_version
    )
    devise_parameter_sanitizer.permit(
      :sign_up,
      keys: [
        :password_confirmation, :email, :age_over_13, :terms_of_service, :accepted_tos_version
      ]
    )
  end

  def check_account_creation_status
    if is_registered_user?
      flash[:error] = ts('You are already logged in!')
      redirect_to root_path and return
    end

    token = params[:invitation_token]

    if !@admin_settings.account_creation_enabled?
      flash[:error] = ts('Account creation is suspended at the moment. Please check back with us later.')
      redirect_to root_path and return
    else
      check_account_creation_invite(token) if @admin_settings.creation_requires_invite?
    end
  end

  def check_account_creation_invite(token)
    unless token.blank?
      invitation = Invitation.find_by(token: token)

      if !invitation
        flash[:error] = ts('There was an error with your invitation token, please contact support')
        redirect_to new_feedback_report_path
      elsif invitation.redeemed_at
        flash[:error] = ts('This invitation has already been used to create an account, sorry!')
        redirect_to root_path
      end

      return
    end

    if !@admin_settings.invite_from_queue_enabled?
      flash[:error] = ts('Account creation currently requires an invitation. We are unable to give out additional invitations at present, but existing invitations can still be used to create an account.')
      redirect_to root_path
    else
      flash[:error] = ts("To create an account, you'll need an invitation. One option is to add your name to the automatic queue below.")
      redirect_to invite_requests_path
    end
  end
end
