class Profile < ApplicationRecord
  include Justifiable

  PROFILE_TITLE_MAX = 255
  LOCATION_MAX = 255
  ABOUT_ME_MAX = 2000

  belongs_to :user

  validates_length_of :location, allow_blank: true, maximum: LOCATION_MAX,
    too_long: ts("must be less than %{max} characters long.", max: LOCATION_MAX)
  validates_length_of :title, allow_blank: true, maximum: PROFILE_TITLE_MAX,
    too_long: ts("must be less than %{max} characters long.", max: PROFILE_TITLE_MAX)
  validates_length_of :about_me, allow_blank: true, maximum: ABOUT_ME_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ABOUT_ME_MAX)

  before_update :validate_date_of_birth
  # Checks date of birth when user updates profile
  # blank is okay as they already checked that they were over 13 when registering
  def validate_date_of_birth
    return unless self.date_of_birth
    if self.date_of_birth > 13.years.ago.to_date
      errors.add(:base, "You must be over 13.")
      throw :abort
    end
  end
end
