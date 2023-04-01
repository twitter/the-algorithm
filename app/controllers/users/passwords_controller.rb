# frozen_string_literal: true

# Use for resetting lost passwords
class Users::PasswordsController < Devise::PasswordsController
  before_action :admin_logout_required
  skip_before_action :store_location
  layout "session"

  def create
    if User.find_for_authentication(resource_params.permit(:login))&.prevent_password_resets?
      flash[:error] = t(".reset_blocked", contact_abuse_link: view_context.link_to(t(".contact_abuse"), new_abuse_report_path)).html_safe
      redirect_to root_path and return
    end
    super do |user|
      flash.now[:notice] = ts("We couldn't find an account with that email address or username. Please try again?") if user.nil? || user.new_record?
    end
  end
end
