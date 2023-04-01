# frozen_string_literal: true

# Mark the error messages as html_safe (to allow links), and define a few
# variables that can be used in all messages.
class DeviseFailureMessageOptions < Devise::FailureApp
  def default_i18n_variables
    @default_i18n_variables ||= {
      reset_path: new_user_password_path,
      problems_path: admin_post_path(12035),
      app_name: ArchiveConfig.APP_SHORT_NAME
    }
  end

  def i18n_options(options)
    options.merge(default_i18n_variables)
  end

  def i18n_message(*args)
    super(*args).html_safe
  end
end
