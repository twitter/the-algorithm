# frozen_string_literal: true

unless ARGV.size == 1
  puts "
    Usage: rails runner export_matches.rb <EXCHANGE>

    Runs matching for the specified exchange, reads all of the resulting
    matches from the database, and prints them all on STDOUT. The output
    is sorted so that if you pipe it to a text file, it's easy to take
    the diff between the results of two different algorithms and see which
    PotentialMatches are missing.
  "
  exit
end

challenge_name = ARGV[0]

c = Collection.find_by(name: challenge_name)
PotentialMatch.generate_in_background c.id
all = PotentialMatch.where(collection_id: c.id)

matches = all.map do |pm|
  "#{pm.offer_signup_id} writes for #{pm.request_signup_id}"
end

matches.sort!

puts matches.join("\n")
