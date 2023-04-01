class EmailBlacklistValidator < ActiveModel::EachValidator
  def validate_each(record,attribute,value)
    if AdminBlacklistedEmail.is_blacklisted?(value)
      record.errors.add(attribute, options[:message] || I18n.t("validators.email.blacklist"))
      return false
    else
      return true
    end
  end
end
