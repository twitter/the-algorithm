Given /^(.*) first read "([^"]*)" on "([^"]*)"$/ do |login, title, date|
  user = User.find_by(login: login)
  work = Work.find_by(title: title)
  time = date.to_time.in_time_zone("UTC")
  # create the reading
  reading_json = [user.id, time, work.id, work.major_version, work.minor_version, false].to_json
  REDIS_GENERAL.sadd("Reading:new", reading_json)
  step "the readings are saved to the database"
end

When "the readings are saved to the database" do
  RedisSetJobSpawner.perform_now("ReadingsJob")
end
