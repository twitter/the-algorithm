namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace py gen.twitter.tweetypie.deletedtweet
namespace rb TweetyPie
namespace go tweetypie

// Structs used for response from getDeletedTweets

struct DeletedTweetMediaEntity {
  1: required i64 id
  2: required i8 mediaType
  3: required i16 width
  4: required i16 height
} (persisted = 'true')

struct DeletedTweetShare {
  1: required i64 sourceStatusId
  2: required i64 sourceUserId
  3: required i64 parentStatusId
} (persisted = 'true')

/**
 * A tweet that has been soft- or hard-deleted.
 *
 * Originally DeletedTweet used the same field ids as tbird.Status.
 * This is no longer the case.
 */
struct DeletedTweet {
  // Uses the same field ids as tbird.thrift so we can easily map and add fields later
  1: required i64 id

  /**
   * User who created the tweet. Only available for soft-deleted tweets.
   */
  2: optional i64 userId

  /**
   * Content of the tweet. Only available for soft-deleted tweets.
   */
  3: optional string text

  /**
   * When the tweet was created. Only available for soft-deleted tweets.
   */
  5: optional i64 createdAtSecs

  /**
   * Retweet information if the deleted tweet was a retweet. Only available
   * for soft-deleted tweets.
   */
  7: optional DeletedTweetShare share

  /**
   * Media metadata if the deleted tweet included media. Only available for
   * soft-deleted tweets.
   */
  14: optional list<DeletedTweetMediaEntity> media

  /**
   * The time when this tweet was deleted by a user, in epoch milliseconds, either normally (aka
   * "softDelete") or via a bouncer flow (aka "bounceDelete").
   *
   * This data is not available for all deleted tweets.
   */
  18: optional i64 deletedAtMsec

  /**
   * The time when this tweet was permanently deleted, in epoch milliseconds.
   *
   * This data is not available for all deleted tweets.
   */
  19: optional i64 hardDeletedAtMsec

  /**
  * The ID of the NoteTweet associated with this Tweet if one exists. This is used by safety tools
  * to fetch the NoteTweet content when viewing soft deleted Tweets.
  */
  20: optional i64 noteTweetId

  /**
  * Specifies if the Tweet can be expanded into the NoteTweet, or if they have the same text. Can
  * be used to distinguish between Longer Tweets and RichText Tweets.
  */
  21: optional bool isExpandable
} (persisted = 'true')
