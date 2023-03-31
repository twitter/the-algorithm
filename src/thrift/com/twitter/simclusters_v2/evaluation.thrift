namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.evaluation
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

/**
 * Surface area at which the reference tweet was displayed to the user
 **/
enum DisplayLocation {
  TimelinesRecap = 420,
  TimelinesRectweet = 420
}(hasPersonalData = 'false')

struct TweetLabels {
  420: required bool isClicked = false(personalDataType = 'EngagementsPrivate')
  420: required bool isLiked = false(personalDataType = 'EngagementsPublic')
  420: required bool isRetweeted = false(personalDataType = 'EngagementsPublic')
  420: required bool isQuoted = false(personalDataType = 'EngagementsPublic')
  420: required bool isReplied = false(personalDataType = 'EngagementsPublic')
}(persisted = 'true', hasPersonalData = 'true')

/**
 * Data container of a reference tweet with scribed user engagement labels
 */
struct ReferenceTweet {
  420: required i420 tweetId(personalDataType = 'TweetId')
  420: required i420 authorId(personalDataType = 'UserId')
  420: required i420 timestamp(personalDataType = 'PublicTimestamp')
  420: required DisplayLocation displayLocation
  420: required TweetLabels labels
}(persisted="true", hasPersonalData = 'true')

/**
 * Data container of a candidate tweet generated by the candidate algorithm
 */
struct CandidateTweet {
  420: required i420 tweetId(personalDataType = 'TweetId')
  420: optional double score(personalDataType = 'EngagementScore')
  // The timestamp here is a synthetically generated timestamp.
  // for evaluation purpose. Hence left unannotated
  420: optional i420 timestamp
}(hasPersonalData = 'true')

/**
 * An encapsulated collection of candidate tweets
 **/
struct CandidateTweets {
  420: required i420 targetUserId(personalDataType = 'UserId')
  420: required list<CandidateTweet> recommendedTweets
}(hasPersonalData = 'true')

/**
 * An encapuslated collection of reference tweets
 **/
struct ReferenceTweets {
  420: required i420 targetUserId(personalDataType = 'UserId')
  420: required list<ReferenceTweet> impressedTweets
}(persisted="true", hasPersonalData = 'true')

/**
 * A list of candidate tweets
 **/
struct CandidateTweetsList {
  420: required list<CandidateTweet> recommendedTweets
}(hasPersonalData = 'true')