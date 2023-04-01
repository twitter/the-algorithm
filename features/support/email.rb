module EmailHelpers
  # Maps a name to an email address. Used by email_steps

  def email_for(to)
    case to

    # add your own name => email address mappings here

    when /^#{capture_model}$/
      model($1).email

    when /^"([^@]*)"$/
      user = User.find_by(login: $1)
      user.email

    when /^"(.*)"$/
      $1

    else
      to
    end
  end
end

World(EmailHelpers)
