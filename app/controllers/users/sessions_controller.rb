class Users::SessionsController < Devise::SessionsController

  layout "session"
  before_action :admin_logout_required
  skip_before_action :store_location

  # POST /users/login
  def create
    super do |resource|
      unless resource.remember_me
        message = ts(" <strong>You'll stay logged in for %{number} weeks even if you close your browser, so make sure to log out if you're using a public or shared computer.</strong>", number: ArchiveConfig.DEFAULT_SESSION_LENGTH_IN_WEEKS)
      end
      flash[:notice] += message unless message.nil?
      flash[:notice] = flash[:notice].html_safe
    end
  end

  # GET /users/logout
  def confirm_logout
    # If the user is already logged out, we just redirect to the front page.
    redirect_to root_path unless user_signed_in?
  end

  # DELETE /users/logout
  def destroy
    # signed_out clears the session
    return_to = session[:return_to]

    signed_out = (Devise.sign_out_all_scopes ? sign_out : sign_out(resource_name))
    set_flash_message! :notice, :signed_out if signed_out

    session[:return_to] = return_to

    redirect_back_or_default root_path
  end
end
