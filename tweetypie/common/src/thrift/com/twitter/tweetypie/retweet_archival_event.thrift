namespace java com.twitter.tweetypie.thriftjava
namespace py gen.twitter.tweetypie.retweet_archival_event
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace rb TweetyPie
namespace go tweetypie

/**
 * This event is published to "retweet_archival_events" when Tweetypie processes an
 * AsyncSetRetweetVisibilityRequest.
 *
 * This is useful for services (Interaction Counter, Insights Track) that need to
 * know when the retweet engagement count of a tweet has been modified due to the
 * retweeting user being put in to or out of suspension or read-only mode.
 */
struct RetweetArchivalEvent {
  // The retweet id affected by this archival event.
  1: required i64 retweet_id (personalDataType = 'TweetId')
  // The source tweet id for the retweet. This tweet had its retweet count modified.
  2: required i64 src_tweet_id (personalDataType = 'TweetId')
  3: required i64 retweet_user_id (personalDataType = 'UserId')
  4: required i64 src_tweet_user_id (personalDataType = 'UserId')
  // Approximate time in milliseconds for when the count modification occurred, based on
  // Unix Epoch (1 January 1970 00:00:00 UTC). Tweetypie will use the time when it is
  // about to send the asynchronous write request to tflock for this timestamp.
  5: required i64 timestamp_ms
  // Marks if this event is for archiving(True) or unarchiving(False) action.
  // Archiving indicates an engagement count decrement occurred and unarchiving indicates an incremental.
  6: optional bool is_archiving_action
}(persisted='true', hasPersonalData = 'true')
