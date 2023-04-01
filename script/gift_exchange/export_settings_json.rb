# frozen_string_literal: true

require 'json'

unless ARGV.size == 2
  puts "
    Usage: rails runner export_settings_json.rb <EXCHANGE> <OUTPUT>

    Reads in the request/offer restrictions for the given exchange, and
    outputs them in a format suitable for use with generate_from_spec.rb.
  "
  exit
end

challenge_name = ARGV[0]
output_json = ARGV[1]

collection = Collection.find_by(name: challenge_name)
challenge = collection.challenge

result = {}

result["participants"] = 100
result["tag_limits"] = {}

included_types = []

%w(offer request).each do |prompt_type|
  info = {}

  required = challenge.send("#{prompt_type.pluralize}_num_required")
  allowed = challenge.send("#{prompt_type.pluralize}_num_allowed")
  info["prompts"] = {
    min: required,
    max: allowed,
    average: (required + allowed) / 2.0
  }

  restriction = challenge.send("#{prompt_type}_restriction")

  TagSet::TAG_TYPES.each do |tag_type|
    required = restriction.send("#{tag_type}_num_required")
    allowed = restriction.send("#{tag_type}_num_allowed")

    next unless allowed > 0

    included_types << tag_type

    info[tag_type.pluralize] = {
      min: required,
      max: allowed,
      average: (required + allowed) / 2.0
    }

    if restriction.send("require_unique_#{tag_type}")
      info[tag_type.pluralize]["unique"] = true
    end

    if restriction.send("allow_any_#{tag_type}")
      # default probability of picking "any"
      info[tag_type.pluralize]["any"] = 0.1
    end
  end

  result[prompt_type.pluralize] = info
end

included_types.uniq!
included_types.each do |type|
  limit = if TagSet::TAG_TYPES_RESTRICTED_TO_FANDOM.include?(type)
            20
          elsif TagSet::TAG_TYPES_INITIALIZABLE.include?(type)
            100
          else
            Tag.where(type: type).count
          end

  result["tag_limits"][type.pluralize] = limit
end

File.open(output_json, 'w') do |f|
  f << JSON.pretty_generate(result)
end
