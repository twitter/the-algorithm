namespace java com.twitter.tweetypie.storage_internal.thriftjava
#@namespace scala com.twitter.tweetypie.storage_internal.thriftscala

struct StoredReply {
  1: i64 in_reply_to_status_id (personalDataType = 'TweetId')
  2: i64 in_reply_to_user_id (personalDataType = 'UserId')
  3: optional i64 conversation_id (personalDataType = 'TweetId')
} (hasPersonalData = 'true', persisted='true')

struct StoredShare {
  1: i64 source_status_id (personalDataType = 'TweetId')
  2: i64 source_user_id (personalDataType = 'UserId')
  3: i64 parent_status_id (personalDataType = 'TweetId')
} (hasPersonalData = 'true', persisted='true')

struct StoredGeo {
  1: double latitude (personalDataType = 'GpsCoordinates')
  2: double longitude (personalDataType = 'GpsCoordinates')
  3: i32 geo_precision (personalDataType = 'GpsCoordinates')
  4: i64 entity_id (personalDataType = 'PublishedPreciseLocationTweet, PublishedCoarseLocationTweet')
  5: optional string name (personalDataType = 'PublishedPreciseLocationTweet, PublishedCoarseLocationTweet')
} (hasPersonalData = 'true', persisted='true')

struct StoredMediaEntity {
  1: i64 id (personalDataType = 'MediaId')
  2: i8 media_type (personalDataType = 'ContentTypeTweetMedia')
  3: i16 width
  4: i16 height
} (hasPersonalData = 'true', persisted='true')

struct StoredNarrowcast {
  1: optional list<string> language (personalDataType = 'InferredLanguage')
  2: optional list<string> location (personalDataType = 'PublishedCoarseLocationTweet')
  3: optional list<i64> ids (personalDataType = 'TweetId')
} (hasPersonalData = 'true', persisted='true')

struct StoredQuotedTweet {
  1: i64 tweet_id (personalDataType = 'TweetId')        // the tweet id being quoted
  2: i64 user_id (personalDataType = 'UserId')          // the user id being quoted
  3: string short_url (personalDataType = 'ShortUrl')   // tco url - used when rendering in backwards-compat mode
} (hasPersonalData = 'true', persisted='true')

struct StoredTweet {
  1: i64 id (personalDataType = 'TweetId')
  2: optional i64 user_id (personalDataType = 'UserId')
  3: optional string text (personalDataType = 'PrivateTweets, PublicTweets')
  4: optional string created_via (personalDataType = 'ClientType')
  5: optional i64 created_at_sec (personalDataType = 'PrivateTimestamp, PublicTimestamp')    // in seconds

  6: optional StoredReply reply
  7: optional StoredShare share
  8: optional i64 contributor_id (personalDataType = 'Contributor')
  9: optional StoredGeo geo
  11: optional bool has_takedown
  12: optional bool nsfw_user (personalDataType = 'TweetSafetyLabels')
  13: optional bool nsfw_admin (personalDataType = 'TweetSafetyLabels')
  14: optional list<StoredMediaEntity> media
  15: optional StoredNarrowcast narrowcast
  16: optional bool nullcast
  17: optional i64 tracking_id (personalDataType = 'ImpressionId')
  18: optional i64 updated_at (personalDataType = 'PrivateTimestamp, PublicTimestamp')
  19: optional StoredQuotedTweet quoted_tweet
} (hasPersonalData = 'true', persisted='true')

struct CoreFields {
  2: optional i64 user_id (personalDataType = 'UserId')
  3: optional string text (personalDataType = 'PrivateTweets, PublicTweets')
  4: optional string created_via (personalDataType = 'ClientType')
  5: optional i64 created_at_sec (personalDataType = 'PrivateTimestamp, PublicTimestamp')

  6: optional StoredReply reply
  7: optional StoredShare share
  8: optional i64 contributor_id (personalDataType = 'Contributor')
  19: optional StoredQuotedTweet quoted_tweet
} (hasPersonalData = 'true', persisted='true')

struct InternalTweet {
 1: optional CoreFields core_fields
} (hasPersonalData = 'true', persisted='true')
