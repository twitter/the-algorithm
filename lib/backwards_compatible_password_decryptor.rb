module BackwardsCompatiblePasswordDecryptor
  # http://stackoverflow.com/questions/6113375/converting-existing-password-hash-to-devise
  # https://github.com/binarylogic/authlogic/blob/master/lib/authlogic/acts_as_authentic/password.rb#L361
  # https://www.ruby-forum.com/topic/217465
  # Not useful but https://github.com/plataformatec/devise/issues/511
  def self.included(base)
    base.class_eval do
      alias :devise_valid_password? :valid_password?
    end
  end

  def valid_password?(password)
    begin
      result = super(password)
      # Now the common form is that we are using an authlogic method so let's
      # test that on failure
      return true if result
      # This is the backwards compatibility with what we used to authenticate
      # with bcrypt and authlogic.
      # https://github.com/binarylogic/authlogic/blob/master/lib/authlogic/acts_as_authentic/password.rb#L361

      # if Authlogic::CryptoProviders::BCrypt.matches?(encrypted_password, [password, password_salt].compact)
      # same as
      if BCrypt::Password.new(encrypted_password) == [password, password_salt].flatten.join
        # I am commenting the following line so that if we need to roll back the
        # migration because of reasons the authentication would still work.
        # self.password = password
        return true
      end
      return false
    rescue BCrypt::Errors::InvalidHash
      # Now a really old password hash
      # This is the backwards compatibility for the old she512 passwords, all 1
      # of them
      # http://stackoverflow.com/questions/6113375/converting-existing-password-hash-to-devise/9079088
      digest = "#{password}#{password_salt}"
      20.times { digest = Digest::SHA512.hexdigest(digest) }
      return false unless digest == encrypted_password
      # I am commenting the following line so that if we needed to roll back the
      # migration because of reasons the authentication would still work.
      # self.password = password
      true
    end
  end
end
