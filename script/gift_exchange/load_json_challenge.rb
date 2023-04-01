# frozen_string_literal: true

require 'json'

unless ARGV.size == 2
  puts %q|
    Usage: rails runner load_json_challenge.rb <EXCHANGE> <JSON_SIGNUPS>

    Clears all existing signups for the specified exchange, and then
    creates signups using the information in the specified JSON file. The
    JSON file must contain a list of signups, each of which should be in
    (roughly) the following format:

    {

      "pseud": "testuser1",

      "requests": [
        {"fandoms": ["Jessica Jones"], "any": ["relationship"]}
        {"fandoms": ["Wynonna Earp"], "freeforms": ["Fanfiction"]}
      ],

      "offers": [
        {"fandoms": ["Supernatural", "Harry Potter"], "any": ["character"]},
        {"any": ["fandom", "character"], "freeforms": ["Fanart"]}
      ]

    }

    Note that the tag types should be singular when used in the "any"
    field, and plural when used in the hash.

    All users will be created with a password of "password," if they
    don't already exist. All tags will be made canonical (and no
    associations will be added). Basically, don't use this script unless
    you really don't care about the current state of your database.

    This may take a VERY long time to run, depending on the size of your
    exchange.
  |
  exit
end

challenge_name = ARGV[0]
json_input = ARGV[1]

data = JSON.load(File.open(json_input, 'r'))

puts "Getting collection ..."

collection = Collection.find_by(name: challenge_name)

puts "Creating all signed-up users ..."

users = data.map { |signup| signup['pseud'] }

old_progress = 0
index = 0
users.each do |username|
  index += 1
  new_progress = (index * 100) / users.size
  if new_progress != old_progress
    puts new_progress.to_s + "%"
    old_progress = new_progress
  end

  next if User.where(login: username).count > 0
  u = User.new
  u.login = username
  u.password = "password"
  u.email = username.downcase + "@foo.com"
  u.activate
end

puts "Making all tags canonical ..."

prompts = data.flat_map { |signup| signup['requests'] + signup['offers'] }

TagSet::TAG_TYPES.each do |type|
  puts "Processing #{type.pluralize} ..."

  names = prompts.flat_map { |prompt| prompt[type.pluralize] }.compact.uniq

  old_progress = 0
  index = 0
  names.each do |name|
    index += 1
    new_progress = (index * 100) / names.size
    if new_progress != old_progress
      puts new_progress.to_s + "%"
      old_progress = new_progress
    end

    type.classify.constantize.create_canonical(name)
  end
end

puts "Clearing all previous signups ..."

collection.signups.destroy_all

puts "Creating all signups ..."

def make_prompt(challenge_signup, info, prompt_type)
  # Create the tag set for the prompt.
  tag_set = TagSet.new
  tag_names = TagSet::TAG_TYPES.flat_map do |type|
    info[type.pluralize]
  end.compact.uniq

  tag_set.tags = Tag.where(name: tag_names)

  # Create the attributes for the prompt.
  attributes = {
    collection: challenge_signup.collection,
    challenge_signup: challenge_signup,
    pseud: challenge_signup.pseud,
    tag_set: tag_set
  }

  if info.key? 'any'
    info['any'].each do |type|
      attributes["any_#{type}"] = true
    end
  end

  if prompt_type == 'request'
    challenge_signup.requests << Request.create(attributes)
  else
    challenge_signup.offers << Offer.create(attributes)
  end
end

old_progress = 0
index = 0
data.each do |signup|
  index += 1
  new_progress = (index * 100) / data.size
  if new_progress != old_progress
    puts new_progress.to_s + "%"
    old_progress = new_progress
  end

  pseud = User.where(login: signup['pseud']).first.default_pseud
  next if pseud.nil?

  challenge_signup = ChallengeSignup.new(
    collection: collection,
    pseud: pseud
  )

  signup['requests'].each do |info|
    make_prompt(challenge_signup, info, 'request')
  end

  signup['offers'].each do |info|
    make_prompt(challenge_signup, info, 'offer')
  end

  challenge_signup.save!
end
