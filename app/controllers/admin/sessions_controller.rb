# Namespaced Admin class
class Admin
  # Handle admin session authentication
  class SessionsController < Devise::SessionsController
    before_action :user_logout_required, except: :destroy
    skip_before_action :store_location, raise: false

    # GET /admin/logout
    def confirm_logout
      # If the user is already logged out, we just redirect to the front page.
      redirect_to root_path unless admin_signed_in?
    end
  end
end
