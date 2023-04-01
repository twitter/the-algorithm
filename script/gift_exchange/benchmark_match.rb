# frozen_string_literal: true

require 'benchmark'

if ARGV.size.zero? || ARGV.size > 2
  puts "
    Usage: rails runner benchmark_match.rb <EXCHANGE> [<TEST_MESSAGE>]

    Runs matching for the specified exchange, recording the amount of time
    it takes. Prints out the number of signups and the number of matches at
    the end. If <TEST_MESSAGE> is specified, it will also log the results of
    the test to the file log/benchmark.log (for easier recording of results).
  "
  exit
end

exchange_name = ARGV[0]
test_name = ARGV[1]

collection = Collection.find_by(name: exchange_name)
count = collection.signups.count

bench = Benchmark.measure do
  PotentialMatch.generate_in_background collection.id
end

matches = collection.potential_matches.count

puts "#{count} signups, #{matches} matches"
puts "Time Required: " + bench.to_s

unless test_name.nil?
  File.open('log/benchmark.log', 'a') do |f|
    f << "Generated All Matches\n"
    f << "#{test_name}\n"
    f << "#{exchange_name} (#{count} signups, #{matches} matches)\n"
    f << bench << "\n"
  end
end
