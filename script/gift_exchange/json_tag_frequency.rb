# frozen_string_literal: true

require 'json'

unless ARGV.size == 1
  puts "
    Usage: ruby json_tag_frequency.rb <JSON_SIGNUPS>

    Given a JSON file specifying the signups for an exchange, calculate the
    frequency of each of the tags. Useful for examining the fall-off in the
    popularity of tags.

    Does not use Rails, only Ruby.
  "
  exit
end

tag_types = %w(
  fandoms characters relationships freeforms ratings warnings categories
)

# Makes the type singular.
def singular(type)
  type.gsub(/ies$/, 'y').gsub(/s$/, '')
end

signups = JSON.load(File.open(ARGV[0], 'r'))

requests = signups.flat_map { |signup| signup["requests"] }
offers = signups.flat_map { |signup| signup["offers"] }
prompts = requests + offers

tag_types.each do |type|
  tags = prompts.flat_map { |prompt| prompt[type] || [] }
  counts = Hash.new 0

  tags.each do |tag|
    counts[tag] += 1
  end

  next if tags.empty?

  puts type.upcase

  average = tags.size.to_f / prompts.size
  any = prompts.count do |prompt|
    prompt['any'] && prompt['any'].include?(singular(type))
  end

  puts "average number of #{type} per prompt: #{average}"
  puts "number of prompts with any #{type}: #{any}"

  puts

  tags = counts.keys.sort { |x, y| counts[x] <=> counts[y] }
  tags.each do |tag|
    puts "#{tag}: #{counts[tag]}"
  end

  puts
end
