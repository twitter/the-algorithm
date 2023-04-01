# frozen_string_literal: true

require 'benchmark'

if ARGV.size.zero? || ARGV.size > 2
  puts "
    Usage: rails runner benchmark_regenerate.rb <EXCHANGE> [<TEST_MESSAGE>]

    Regenerates matches for the first signup in the specified exchange,
    recording the time that it takes. If <TEST_MESSAGE> is specified, it will
    also log the results of the test to the file log/benchmark.log.
  "
  exit
end

exchange_name = ARGV[0]
test_name = ARGV[1]

collection = Collection.find_by(name: exchange_name)
count = collection.signups.count
signup = collection.signups.first

bench = Benchmark.measure do
  PotentialMatch.regenerate_for_signup_in_background signup.id
end

signup.reload
offer_matches = signup.offer_potential_matches.size
request_matches = signup.request_potential_matches.size

puts "Time Required: " + bench.to_s
puts "#{offer_matches} matches as an offer"
puts "#{request_matches} matches as a request"

unless test_name.nil?
  File.open('log/benchmark.log', 'a') do |f|
    f << "Regenerate Matches for First Signup\n"
    f << "#{test_name}\n"
    f << "#{exchange_name} (#{count} signups)\n"
    f << "#{offer_matches} matches as an offer\n"
    f << "#{request_matches} matches as a request\n"
    f << bench << "\n"
  end
end
