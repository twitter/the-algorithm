# from https://github.com/rails/rails/issues/44698#issuecomment-1069675285

module MailDisableStarttls
  def build_smtp_session
    super.tap do |smtp|
      unless settings[:enable_starttls_auto]
        if smtp.respond_to?(:disable_starttls)
          smtp.disable_starttls
        end
      end
    end
  end
end

if Mail::VERSION::STRING == "2.7.1"
  Mail::SMTP.prepend MailDisableStarttls
else
  puts "WARNING: The monkeypatch #{__FILE__} was written for version 2.7.1 of the mail gem, but you are running #{Mail::VERSION::STRING}. Please update or remove the monkeypatch."
end
