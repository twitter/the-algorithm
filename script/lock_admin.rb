# Run this script and follow the onscreen instructions:
#   bundle exec rails r script/lock_admin.rb

puts <<~PROMPT
  Enter a comma-separated list of admin logins to lock or type ALL to lock all
  admin accounts:

PROMPT

input = gets.chomp

admins = []
invalid_logins = []

if input == "ALL"
  admins = Admin.all.to_a
else
  logins = input.split(/,/)
  logins.each do |login|
    login = login.strip
    admin = Admin.find_by(login: login)
    if admin
      admins << admin
    else
      invalid_logins << login
    end
  end
end

puts
puts "Locking..."
puts

failures = []
admins.each do |admin|
  # We do not have an unlock instruction email for admins, but let's be
  # cautious. If we ever add one, we do not want to send it when we do this.
  if admin.lock_access!({ send_instructions: false })
    puts "#{admin.login} has been locked"
  else
    failures << admin.login
  end
end

puts
puts "Locking complete!"
puts "Could not find: #{invalid_logins.join(', ')}" unless invalid_logins.empty?
puts "Could not lock: #{failures.join(', ')}" unless failures.empty?
