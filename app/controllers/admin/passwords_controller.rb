# frozen_string_literal: true

class Admin::PasswordsController < Devise::PasswordsController
  before_action :user_logout_required
  skip_before_action :store_location
  layout "session"
end
