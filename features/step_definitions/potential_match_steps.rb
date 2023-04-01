# frozen_string_literal: true

Given /^I create the gift exchange "([^\"]*)" with the following options$/ do |name, table|
  # Set up the gift exchange with the correct owner.
  step %{the user "moderator" exists and is activated}
  user = User.find_by(login: "moderator")
  collection = Collection.new(
    name: name,
    title: name,
    challenge_type: "GiftExchange"
  )

  collection.collection_participants.build(
    pseud_id: user.default_pseud.id,
    participant_role: "Owner"
  )

  # Create the new collection.
  collection.challenge = GiftExchange.new
  collection.challenge.request_restriction = PromptRestriction.new
  collection.challenge.offer_restriction = PromptRestriction.new
  collection.challenge.potential_match_settings = PotentialMatchSettings.new

  potential_match_settings = collection.challenge.potential_match_settings

  # Go through each of the types, and set up the request/offer/matching
  # requirements accordingly.
  table.hashes.each do |hash|
    # Get the type (prompt/fandom/character/etc.) that we're restricting.
    type = hash["value"].downcase.singularize

    # Constraints on the prompts.
    required = (hash["minimum"] || "0").to_i
    allowed = (hash["maximum"] || "0").to_i
    any = %w(y t yes true).include?(hash["any"] || "yes")
    unique = %w(y t yes true).include?(hash["unique"] || "yes")
    optional = %w(y t yes true).include?(hash["optional"] || "no")

    # Constraints on the matching.
    match = (hash["match"] || hash["minimum"] || "0").to_i

    if type == "prompt"
      # Prompts aren't a type of tag, so we have to set them up differently.
      collection.challenge.requests_num_required = required
      collection.challenge.requests_num_allowed = allowed
      collection.challenge.offers_num_required = required
      collection.challenge.offers_num_allowed = allowed
      potential_match_settings.num_required_prompts = match
    else
      attributes = {
        "#{type}_num_required" => required,
        "#{type}_num_allowed" => allowed,
        "allow_any_#{type}" => any,
        "require_unique_#{type}" => unique
      }

      attributes["optional_tags_allowed"] = true if optional

      # We treat requests and offers identically, in order to make the
      # constraints simpler to express. (The tests are designed to verify
      # potential match generation, not challenge signups, so we don't need
      # that kind of fine-grained control.)
      collection.challenge.request_restriction.update(attributes)
      collection.challenge.offer_restriction.update(attributes)

      potential_match_settings.update_attribute(
        "num_required_#{type.pluralize}", match
      )

      potential_match_settings.update_attribute(
        "include_optional_#{type.pluralize}", optional
      )
    end
  end

  # Save all the things!
  collection.save!
  collection.challenge.save!
  collection.challenge.request_restriction.save!
  collection.challenge.offer_restriction.save!
  collection.challenge.potential_match_settings.save!
end

Given /^the user "([^\"]*)" signs up for "([^\"]*)" with the following prompts$/ do |user_name, collection_name, table|
  # Set up the username.
  step %{the user "#{user_name}" exists and is activated}
  user = User.find_by(login: user_name).default_pseud

  # Set up the basics of the signup.
  collection = Collection.find_by(name: collection_name)
  signup = ChallengeSignup.new(pseud: user, collection: collection)
  offers = []
  requests = []

  table.hashes.each do |hash|
    # Each row of the table is a prompt -- either a request, or an offer.
    prompt_type = hash["type"].downcase
    prompt = prompt_type.classify.constantize.new(
      collection: collection,
      pseud: user
    )

    tagset = TagSet.new
    optional_tag_set = nil
    TagSet::TAG_TYPES.each do |type|
      optional = hash["optional #{type}"] || hash["optional #{type.pluralize}"]
      unless optional.nil?
        optional_tag_set ||= TagSet.new

        tag_names = optional.split(/ *, */)
        tag_names.each do |tag_name|
          tag = type.classify.constantize.create_canonical(tag_name)
          optional_tag_set.tags << tag
        end
      end

      value = hash[type] || hash[type.pluralize]
      next if value.nil?
      value = value.downcase

      # Allow the test to specify "any" for the type.
      if value == "any"
        prompt.update_attribute("any_#{type}", true)
      else
        tag_names = value.split(/ *, */)
        tag_names.each do |tag_name|
          tag = type.classify.constantize.create_canonical(tag_name)
          tagset.tags << tag
        end
      end
    end

    tagset.save!
    prompt.tag_set = tagset

    optional_tag_set.save! if optional_tag_set
    prompt.optional_tag_set = optional_tag_set

    requests << prompt if prompt_type == "request"
    offers << prompt if prompt_type == "offer"
  end

  # Save the final signup.
  signup.requests = requests
  signup.offers = offers
  signup.save!
end

When /^potential matches are generated for "([^\"]*)"$/ do |name|
  collection = Collection.find_by(name: name)
  PotentialMatch.generate_in_background collection.id
end

Then /^there should be no potential matches for "([^\"]*)"$/ do |name|
  collection = Collection.find_by(name: name)
  collection.potential_matches.count.should == 0
end

Then /^the potential matches for "([^\"]*)" should be$/ do |name, table|
  # First extract the set of potential matches for the given challenge.
  collection = Collection.find_by(name: name)
  matches = collection.potential_matches.includes(
    request_signup: :pseud,
    offer_signup: :pseud
  )

  match_names = matches.map do |pm|
    [pm.request_signup.pseud.name, pm.offer_signup.pseud.name]
  end

  match_names.sort!

  # Next extract the set of potential matches from the table.
  desired_names = []
  table.hashes.each do |hash|
    desired_names << [hash["request"], hash["offer"]]
  end

  desired_names.sort!

  match_names.should == desired_names
end
