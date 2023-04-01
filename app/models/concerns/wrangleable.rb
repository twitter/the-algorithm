module Wrangleable
  extend ActiveSupport::Concern

  included do
    after_save :update_last_wrangling_activity, if: :update_wrangling_activity?
    after_destroy :update_last_wrangling_activity, if: :update_wrangling_activity?
  end

  private

  def update_last_wrangling_activity
    current_user = User.current_user
    current_user.update_last_wrangling_activity if current_user.respond_to?(:update_last_wrangling_activity)
  end

  def update_wrangling_activity?
    User.should_update_wrangling_activity
  end
end
