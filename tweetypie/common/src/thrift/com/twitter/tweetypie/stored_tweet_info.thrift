namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie

include "com/twitter/tweetypie/tweet.thrift"

struct HardDeleted {
  1: i64 soft_deleted_timestamp_msec
  2: i64 timestamp_msec
}

struct SoftDeleted {
  1: i64 timestamp_msec
}

struct BounceDeleted {
  1: i64 timestamp_msec
}

struct Undeleted {
  1: i64 timestamp_msec
}

struct ForceAdded {
  1: i64 timestamp_msec
}

struct NotFound {}

union StoredTweetState {
  1: HardDeleted hard_deleted
  2: SoftDeleted soft_deleted
  3: BounceDeleted bounce_deleted
  4: Undeleted undeleted
  5: ForceAdded force_added
  6: NotFound not_found
}

enum StoredTweetError {
  CORRUPT                     = 1,
  SCRUBBED_FIELDS_PRESENT     = 2,
  FIELDS_MISSING_OR_INVALID   = 3,
  SHOULD_BE_HARD_DELETED      = 4,
  FAILED_FETCH                = 5
}

struct StoredTweetInfo {
  1: required i64 tweet_id
  2: optional tweet.Tweet tweet
  3: optional StoredTweetState stored_tweet_state
  4: required list<StoredTweetError> errors = []
}
