######################################################################
# HOW TO TEST THE SPEED OF THE MATCHING CODE
######################################################################

If you want to test the speed of the matching code, you'll need an exchange
with signups. (See below for how to generate one.) Once you have the exchange,
you can run three different benchmarking commands.

The first command is the one that you'll use most often. It's for benchmarking
the entire PotentialMatch generation process, from processing invalid signups
all the way through to generating ChallengeAssignments. You can run it with the
following command:

    rails runner script/gift_exchange/benchmark_match.rb \
	CollectionName "Test for the new matching code."

The string argument is optional. If you just want to see how fast the code
goes, you can omit it. But if you're running multiple trials, and experimenting
with a number of different algorithms, it can sometimes be difficult
after-the-fact to figure out which number is associated with which version of
the code. It may also be annoying to have to copy all of the numbers into a
separate document. That's what the additional argument is for: it signals to
the benchmarking code that you want to save the test. As a result, it will run
the test, and save the results (as well as the information message you entered,
the name of the exchange, and a few other pieces of information) to the file
log/benchmark.log.

The next command is used to benchmark just the challenge assignment process.
(So it will return incorrect results if you haven't already generated the set
of all potential matches.)

    rails runner script/gift_exchange/benchmark_assignments.rb \
	CollectionName "Generating assignments for full PotentialMatches."

Just as with the benchmark_match.rb code, the second argument is optional --
it's only used to signal that the results should be recorded to the file
log/benchmark.log.

Since this code was developed for the purposes of optimizing the PotentialMatch
process, not the ChallengeAssignment process, this was mostly used to figure
out roughly how long the PotentialMatch process alone was taking (since
variable numbers of PotentialMatch objects can cause variations in the length
of the ChallengeAssignment process). But if someone wants to work on optimizing
the ChallengeAssignment process in the future, this may be useful for that, as
well.

The final command is used to benchmark the regeneration of matches for a single
signup:

    rails runner script/gift_exchange/benchmark_regeneration.rb \
	CollectionName "Checking the speed of the old match code."

Just as with the others, the second argument is optional, and if included, it
will cause the script to write out the results to log/benchmark.log. (Each
benchmark is clearly labelled with the type of benchmark at the top, so that
it's easier to sort out which results are from which test.)

It regenerates all matches for the first signup in the collection. I considered
making the choice of signup random, but that might cause inconsistencies in the
test results (e.g. Code #1 might look a lot better than Code #2 based on a
single trial, but if Code #1 lucked into getting a really simple signup, and
Code #2 ended up with the most difficult signup, that makes the results
somewhat incomparable.)

######################################################################
# HOW TO GENERATE A RANDOM EXCHANGE
######################################################################

First, log into an account on your dev machine and create a new collection. Add
a gift exchange challenge, and set up the offer/request restrictions. Once you
have everything set up the way you want, run

    rails runner script/gift_exchange/export_settings_json.rb \
        CollectionName collection-signup-settings.json

This will read in the prompt restrictions from CollectionName and export them
to JSON. You can view the JSON file, and tweak some of the values. You might
want an exchange where the average number of characters requested is very
small, or an exchange where a lot of people said they were willing to write any
character -- there are parameters for that, and the format should be pretty
easy to understand. Try not to change the min or the max, though, since you
don't want the signups that you've generated to violate the rules of the
exchange.

You will probably also want to tweak a few other values: the number of
participants, and the "tag limits." The tag limits control how many different
tags you're going to be randomly sampling from. For a "sparse" exchange (where
there are very few matching options), you want the tag limits to be much
higher than the number of participants. For a "dense" exchange, you want the
tag limits to be much lower than the number of participants. Be careful, though
-- if your tag limits are too small (for instance, if they're smaller than the
total number of tags of that type that can be used in a single signup, and the
challenge requires that tag to be unique), you may run into an error.

The relationship and character tag limits tend to be fairly small, since the
tag limits control how many relationships or characters there are PER FANDOM,
not as a whole. You may want to increase the default limit of 20 for a
single-fandom exchange, though.

Once you're happy with the settings, you can generate an exchange with your
settings by running:

    ruby script/gift_exchange/generate_from_spec.rb \
        collection-signup-settings.json collection-signup-list.json

This will generate all signups and save them under collection-signup-list.json.
At this point, you may want to examine the distribution of tags in your
generated signups, using json_tag_frequency.rb:

    ruby script/gift_exchange/json_tag_frequency.rb \
        collection-signup-list.json

This will show a list of all tags in the generated exchange, as well as their
frequencies. If the most popular tag is too popular, you'll probably want to go
back into the settings JSON file and increase the tag limits, since they
control how popular the most popular tags are.

You may also want to try tweaking some of the tag generation settings in the
generate_from_spec.rb file -- in particular, to switch between uniform,
geometric, and harmonic sampling. Uniform will sample uniformly at random, and
probably isn't a good model for most exchanges (since it's very rare to have
each tag be equally popular). Both geometric and harmonic sampling are biased,
so that some tags are incredibly popular, and there's a long tail of less
popular tags (which as far as I can tell is closer to the distribution of tags
in a real exchange). In harmonic sampling, the most popular item tends to be
VERY popular, with a quick drop-off; the most popular item in geometric tends
to be somewhat less popular, but the drop-off is a lot slower. You can switch
between the three by commenting and uncommenting the lines defining
TAG_SAMPLE_TYPE.

Also, if you find never-ending lists of "character 1 (fandom 5)" mind-numbing,
you can actually change that by editing the "tag seeds":

   script/gift_exchange/tag_seed.json

If you add fandom names as keys in the "fandoms" hash, and lists of character
names as the values, it will try to use those tag names when generating
signups. It's not particularly useful, unless you're trying to double-check the
matching algorithm's work, in which case you might find it easier to remember
the difference between Dean Winchester and Tony Stark than it is to remember
the difference between character 1 (fandom 2) and character 2 (fandom 1).

######################################################################
# HOW TO LOAD THE JSON SIGNUPS INTO THE DATABASE
######################################################################

Once you have a JSON file containing a list of signups, you can create all of
the signups in the database by running this command:

    rails runner script/gift_exchange/load_json_challenge.rb \
        CollectionName collection-signup-list.json

It will probably take a long time, since it has to create a lot of user
accounts, canonical tags, and the signups themselves. At the end of it, you
should have an exchange populated with the signups from your JSON list. (And
only those signups. It WILL delete all existing signups, so don't use this
unless it's for an exchange that you don't care about.)
