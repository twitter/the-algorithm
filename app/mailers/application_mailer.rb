class ApplicationMailer < ActionMailer::Base
  self.delivery_job = ApplicationMailerJob

  layout "mailer"
  helper :mailer
  default from: "Archive of Our Own " + "<#{ArchiveConfig.RETURN_ADDRESS}>"
end
