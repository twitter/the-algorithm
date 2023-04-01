module ChallengeCore

  # Used to ensure allowed is no less than required
  def update_allowed_values
    self.class::PROMPT_TYPES.each do |prompt_type|
      required = required(prompt_type)
      eval("#{prompt_type}_num_allowed = required") if required > allowed(prompt_type)
    end
  end

  # make sure that challenge sign-up dates / open dates aren't contradictory
  def validate_signup_dates
    # some variables for clarity
    error_message = []
    open_date  = self.signups_open_at
    close_date = self.signups_close_at
    signups_open = self.signup_open
    if signups_open
      if close_date && close_date.past?
        error_message << ts("If sign-ups are open, sign-up close date cannot be in the past.")
      end
      if open_date && open_date.future?
        error_message << ts("If sign-ups are open, sign-up open date cannot be in the future.")
      end
      if close_date && open_date && close_date.to_s(:number) < open_date.to_s(:number)
        error_message << ts("Close date cannot be before open date.")
      end
    end
    unless error_message.empty?
      error_message.each do |errors|
        self.errors.add(:base, errors)
      end
    end
  end

  # When Challenges are deleted, there are two references left behind that need to be reset to nil
  def clear_challenge_references
    collection.challenge_id = nil
    collection.challenge_type = nil
    collection.save!
  end
  
  # a couple of handy shorthand methods
  def required(type)
    self.send("#{type}_num_required")
  end
  
  def allowed(type)
    self.send("#{type}_num_allowed")
  end

  def allowed_range_string(type)
    "#{required(type)}" + (allowed(type) != required(type) ? " - #{allowed(type)}" : '')
  end

  #### Management 
  
  def user_allowed_to_see_signups?(user)
    self.collection.user_is_maintainer?(user)
  end
  
  def user_allowed_to_see_assignments?(user)
    self.collection.user_is_maintainer?(user)
  end

  def user_allowed_to_sign_up?(user)
    self.collection.user_is_maintainer?(user) || self.signup_open
  end
  
  def user_allowed_to_see_prompt?(user, prompt)
    true
  end

  # whether users can change the name on their signup or not -- override in challenge class as appropriate
  def allow_name_change?
    true
  end

  module ClassMethods
    # override datetime setters so we can take strings
    def override_datetime_setters
      %w(signups_open_at signups_close_at assignments_due_at works_reveal_at authors_reveal_at).each do |datetime_attr|
        define_method("#{datetime_attr}_string") do
          return unless (datetime = self[datetime_attr])

          datetime.in_time_zone(time_zone.presence || Time.zone).strftime(ArchiveConfig.DEFAULT_DATETIME_FORMAT)
        end

        define_method("#{datetime_attr}_string=") do |datetimestring|
          self[datetime_attr] = Timeliness.parse(datetimestring, zone: (time_zone.presence || Time.zone))
        end
      end
    end
  end
  
  def self.included(base)
    base.extend(ClassMethods)
  end
  
end
