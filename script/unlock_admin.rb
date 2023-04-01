# Run this script and follow the onscreen instructions:
#   bundle exec rails r script/unlock_admin.rb

puts <<~PROMPT
  Enter a comma-separated list of admin logins to unlock or type ALL to unlock
  all admin accounts:

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
puts "Unlocking..."
puts

failures = []
admins.each do |admin|
  if admin.unlock_access!
    puts "#{admin.login} has been unlocked"
  else
    failures << admin.login
  end
end

puts
puts "Unlocking complete!"
puts "Could not find: #{invalid_logins.join(', ')}" unless invalid_logins.empty?
puts "Could not unlock: #{failures.join(', ')}" unless failures.empty?
