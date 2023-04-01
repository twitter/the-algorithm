module TagWrangling
  extend ActiveSupport::Concern

  # When used an around_action, record_wrangling_activity will
  # mark that some wrangling activity has occurred. If a Wrangleable
  # is saved during the action, a LastWranglingActivity will be
  # recorded. Otherwise no additional steps are taken.
  def record_wrangling_activity
    User.should_update_wrangling_activity = true
    yield
  ensure
    User.should_update_wrangling_activity = false
  end
end
