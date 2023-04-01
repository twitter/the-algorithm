# From Authlogic, to mimic old behavior
#
# https://github.com/binarylogic/authlogic/blob/v3.6.0/lib/authlogic/regex.rb#L13
#
# https://github.com/binarylogic/authlogic/blob/v3.6.0/lib/authlogic/acts_as_authentic/email.rb#L90
#
class EmailFormatValidator < ActiveModel::EachValidator
  def validate_each(record, attribute, value)
    email_regex ||= begin
      email_name_regex = '[A-Z0-9_\.&%\+\-\']+'
      domain_head_regex = '(?:[A-Z0-9\-]+\.)+'
      domain_tld_regex = '(?:[A-Z]{2,25})'
      /\A#{email_name_regex}@#{domain_head_regex}#{domain_tld_regex}\z/i
    end

    if (options[:allow_blank] && value.blank?) || (value.present? && value.match(email_regex))
      result = true
    else
      result = false
    end

    unless result
      if options[:allow_blank]
        record.errors.add(attribute, options[:message] || I18n.t("validators.email.format.allow_blank"))
      else
        record.errors.add(attribute, options[:message] || I18n.t("validators.email.format.no_blank"))
      end
    end
  end
end
