require "factory_bot"

FactoryBot.find_definitions

class ApplicationMailerPreview < ActionMailer::Preview
  include FactoryBot::Syntax::Methods

  # Avoid saving data created for mailer previews.
  def self.call(...)
    message = nil
    ActiveRecord::Base.transaction do
      message = super(...)
      raise ActiveRecord::Rollback
    end
    message
  end
end
