namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
namespace py gen.twitter.tweetypie
namespace rb TweetyPie
namespace go tweetypie

/**
 * Event that triggers deletion of the geo information on tweets created
 * at timestamp_ms or earlier.
 */
struct DeleteLocationData {
  /**
   * The id of the user whose tweets should have their geo information
   * removed.
   */
  1: required i64 user_id (personalDataType='UserId')

  /**
   * The time at which this request was initiated. Tweets by this user
   * whose snowflake ids contain timestamps less than or equal to this
   * value will no longer be returned with geo information.
   */
  2: required i64 timestamp_ms

  /**
   * The last time this user requested deletion of location data prior
   * to this request. This value may be omitted, but should be included
   * if available for implementation efficiency, since it eliminates the
   * need to scan tweets older than this value for geo information.
   */
  3: optional i64 last_timestamp_ms
}(persisted='true', hasPersonalData='true')
