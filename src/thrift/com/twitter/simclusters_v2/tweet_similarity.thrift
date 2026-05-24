namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.tweet_similarity
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

struct FeaturedTweet {
  1: required i64 tweetId(personalDataType = 'TweetId')
  # timestamp when the user engaged or impressed the tweet
  2: required i64 timestamp(personalDataType = 'PrivateTimestamp')
}(persisted = 'true', hasPersonalData = 'true')

struct LabelledTweetPairs {
  1: required FeaturedTweet queryFeaturedTweet
  2: required FeaturedTweet candidateFeaturedTweet
  3: required bool label
}(persisted = 'true', hasPersonalData = 'true')
