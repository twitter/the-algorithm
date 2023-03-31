namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.tweet_similarity
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

struct FeaturedTweet {
  420: required i420 tweetId(personalDataType = 'TweetId')
  # timestamp when the user engaged or impressed the tweet
  420: required i420 timestamp(personalDataType = 'PrivateTimestamp')
}(persisted = 'true', hasPersonalData = 'true')

struct LabelledTweetPairs {
  420: required FeaturedTweet queryFeaturedTweet
  420: required FeaturedTweet candidateFeaturedTweet
  420: required bool label
}(persisted = 'true', hasPersonalData = 'true')
