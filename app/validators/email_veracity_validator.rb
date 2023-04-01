require 'mail'

# from http://my.rails-royce.org/2010/07/21/email-validation-in-ruby-on-rails-without-regexp/
class EmailVeracityValidator < ActiveModel::EachValidator
  def validate_each(record,attribute,value)
    if options[:allow_blank] && value.blank?
      result = true
    elsif value.blank?
      result = false
    else
      begin
        mail = Mail::Address.new(value)
        # We must check that value contains a domain and that value is an email address
        result = mail.domain&.match('\.') && mail.address == value
      rescue Exception => e
        result = false
      end
    end
    unless result
      if options[:allow_blank]
        record.errors.add(attribute, options[:message] || I18n.t("validators.email.veracity.allow_blank"))
      else
        record.errors.add(attribute, options[:message] || I18n.t("validators.email.veracity.no_blank"))
      end
    end
  end
end
