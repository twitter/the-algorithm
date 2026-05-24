namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace py gen.twitter.tweetypie.deprecated
namespace rb TweetyPie

include "com/twitter/expandodo/cards.thrift"
include "com/twitter/gizmoduck/user.thrift"
include "com/twitter/tweetypie/media_entity.thrift"
include "com/twitter/tweetypie/tweet.thrift"
include "com/twitter/tweetypie/tweet_service.thrift"

/**
 * @deprecated Use Place
 */
struct Geo {
  /**
   * @deprecated Use coordinates.latitude
   */
  1: double latitude = 0.0 (personalDataType = 'GpsCoordinates')

  /**
   * @deprecated Use coordinates.longitude
   */
  2: double longitude = 0.0 (personalDataType = 'GpsCoordinates')

  /**
   * @deprecated Use coordinates.geo_precision
   */
  3: i32 geo_precision = 0

  /**
   * 0: don't show lat/long
   * 2: show
   *
   * @deprecated
   */
  4: i64 entity_id = 0

  /**
   * @deprecated Use place_id
   */
  5: optional string name (personalDataType = 'PublishedCoarseLocationTweet')

  6: optional tweet.Place place // provided if StatusRequestOptions.load_places is set
  7: optional string place_id // ex: ad2f50942562790b
  8: optional tweet.GeoCoordinates coordinates
}(persisted = 'true', hasPersonalData = 'true')

/**
 * @deprecated Use Tweet and APIs that accept or return Tweet.
 */
struct Status {
  1: i64 id (personalDataType = 'TweetId')
  2: i64 user_id (personalDataType = 'UserId')
  3: string text (personalDataType = 'PrivateTweets, PublicTweets')
  4: string created_via (personalDataType = 'ClientType')
  5: i64 created_at // in seconds
  6: list<tweet.UrlEntity> urls = []
  7: list<tweet.MentionEntity> mentions = []
  8: list<tweet.HashtagEntity> hashtags = []
  29: list<tweet.CashtagEntity> cashtags = []
  9: list<media_entity.MediaEntity> media = []
  10: optional tweet.Reply reply
  31: optional tweet.DirectedAtUser directed_at_user
  11: optional tweet.Share share
  32: optional tweet.QuotedTweet quoted_tweet
  12: optional tweet.Contributor contributor
  13: optional Geo geo
  // has_takedown indicates if there is a takedown specifically on this tweet.
  // takedown_country_codes contains takedown countries for both the tweet and the user,
  // so has_takedown might be false while takedown_country_codes is non-empty.
  14: bool has_takedown = 0
  15: bool nsfw_user = 0
  16: bool nsfw_admin = 0
  17: optional tweet.StatusCounts counts
  // 18: obsoleted
  19: optional tweet.DeviceSource device_source // not set on DB failure
  20: optional tweet.Narrowcast narrowcast
  21: optional list<string> takedown_country_codes (personalDataType = 'ContentRestrictionStatus')
  22: optional tweet.StatusPerspective perspective // not set if no user ID or on TLS failure
  23: optional list<cards.Card> cards // only included if StatusRequestOptions.include_cards == true
  // only included when StatusRequestOptions.include_cards == true
  // and StatusRequestOptions.cards_platform_key is set to valid value
  30: optional cards.Card2 card2
  24: bool nullcast = 0
  25: optional i64 conversation_id (personalDataType = 'TweetId')
  26: optional tweet.Language language
  27: optional i64 tracking_id (personalDataType = 'ImpressionId')
  28: optional map<tweet.SpamSignalType, tweet.SpamLabel> spam_labels
  33: optional bool has_media
  // obsolete 34: optional list<tweet.TopicLabel> topic_labels
  // Additional fields for flexible schema
  101: optional tweet.TweetMediaTags media_tags
  103: optional tweet.CardBindingValues binding_values
  104: optional tweet.ReplyAddresses reply_addresses
  105: optional tweet.TwitterSuggestInfo twitter_suggest_info
}(persisted = 'true', hasPersonalData = 'true')

