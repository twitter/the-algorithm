module MailersController
  extend ActiveSupport::Concern

  included do
    # Hide the dev mark in mailer previews.
    skip_rack_dev_mark
  end
end

::Rails::MailersController.include MailersController
