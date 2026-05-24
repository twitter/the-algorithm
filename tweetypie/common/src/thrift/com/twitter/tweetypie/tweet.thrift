namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace py gen.twitter.tweetypie.tweet
namespace rb TweetyPie
// Specific namespace to avoid golang circular import
namespace go tweetypie.tweet

include "com/twitter/escherbird/tweet_annotation.thrift"
include "com/twitter/expandodo/cards.thrift"
include "com/twitter/content-health/toxicreplyfilter/filtered_reply_details.thrift"
include "com/twitter/dataproducts/enrichments_profilegeo.thrift"
include "com/twitter/geoduck/public/thriftv1/geoduck_common.thrift"
include "com/twitter/mediaservices/commons/MediaCommon.thrift"
include "com/twitter/mediaservices/commons/MediaInformation.thrift"
include "com/twitter/tweetypie/api_fields.thrift"
include "com/twitter/tweetypie/edit_control.thrift"
include "com/twitter/tweetypie/media_entity.thrift"
include "com/twitter/tweetypie/note_tweet.thrift"
include "com/twitter/service/scarecrow/gen/tiered_actions.thrift"
include "com/twitter/spam/rtf/safety_label.thrift"
include "com/twitter/timelines/self_thread/self_thread.thrift"
include "com/twitter/tseng/withholding/withholding.thrift"
include "com/twitter/tweet_pivots/tweet_pivots.thrift"
include "com/twitter/tweetypie/geo/tweet_location_info.thrift"
include "com/twitter/tweetypie/media/media_ref.thrift"
include "unified_cards_contract.thrift"
include "com/twitter/tweetypie/creative-entity-enrichments/creative_entity_enrichments.thrift"
include "com/twitter/tweetypie/unmentions/unmentions.thrift"

/**
 * IDs are annotated with their corresponding space for Strato.
 */

/**
 * A Reply is data about a tweet in response to another tweet or a
 * user.
 *
 * This struct will be present if:
 * 1. This tweet is a reply to another tweet, or
 * 2. This tweet is directed at a user (the tweet's text begins with
 *    an @mention).
 */
struct Reply {
  /**
   * The id of the tweet that this tweet is replying to.
   *
   * This field will be missing for directed-at tweets (tweets whose
   * text begins with an @mention) that are not replying to another
   * tweet.
   */
  1: optional i64 in_reply_to_status_id (strato.space = "Tweet", strato.name = "inReplyToStatus", personalDataType = 'TweetId', tweetEditAllowed='false')

  /**
   * The user to whom this tweet is directed.
   *
   * If in_reply_to_status_id is set, this field is the author of that tweet.
   * If in_reply_to_status_id is not set, this field is the user mentioned at
   * the beginning of the tweet.
   */
  2: i64 in_reply_to_user_id (strato.space = "User", strato.name = "inReplyToUser", personalDataType = 'UserId')

  /**
   * The current username of in_reply_to_user_id.
   *
   * This field is not set when Gizmoduck returns a failure to Tweetypie.
   */
  3: optional string in_reply_to_screen_name (personalDataType = 'Username')
}(persisted='true', hasPersonalData = 'true')

/**
 * Includes information about the user a tweet is directed at (when a tweet
 * begins with @mention).
 *
 * Tweets with a DirectedAtUser are delivered to users who follow both the
 * author and the DirectedAtUser. Normally the DirectedAtUser will be the same
 * as Reply.in_reply_to_user_id, but will be different if the tweet's author
 * rearranges the @mentions in a reply.
 */
struct DirectedAtUser {
  1: i64 user_id (strato.space = "User", strato.name = "user", personalDataType = 'UserId')
  2: string screen_name (personalDataType = 'Username')
}(persisted='true', hasPersonalData = 'true')

/**
 * A Share is data about the source tweet of a retweet.
 *
 * Share was the internal name for the retweet feature.
 */
struct Share {
  /**
   * The id of the original tweet that was retweeted.
   *
   * This is always a tweet and never a retweet (unlike parent_status_id).
   */
  1: i64 source_status_id (strato.space = "Tweet", strato.name = "sourceStatus", personalDataType = 'TweetId')

  /*
   * The user id of the original tweet's author.
   */
  2: i64 source_user_id (strato.space = "User", strato.name = "sourceUser", personalDataType = 'UserId')

  /**
   * The id of the tweet that the user retweeted.
   *
   * Often this is the same as source_status_id, but it is different when a
   * user retweets via another retweet. For example, user A posts tweet id 1,
   * user B retweets it, creating tweet 2. If user user C sees B's retweet and
   * retweets it, the result is another retweet of tweet id 1, with the parent
   * status id of tweet 2.
   */
  3: i64 parent_status_id (strato.space = "Tweet", strato.name = "parentStatus", personalDataType = 'TweetId')
}(persisted='true', hasPersonalData = 'true')

/**
 * A record mapping a shortened URL (usually t.co) to a long url, and a prettified
 * display text. This is similar to data found in UrlEntity, and may replace that
 * data in the future.
 */
struct ShortenedUrl {
  /**
   * Shortened t.co URL.
   */
  1: string short_url (personalDataType = 'ShortUrl')

  /**
   * Original, full-length URL.
   */
  2: string long_url (personalDataType = 'LongUrl')

  /**
   * Truncated version of expanded URL that does not include protocol and is
   * limited to 27 characters.
   */
  3: string display_text (personalDataType = 'LongUrl')
}(persisted='true', hasPersonalData = 'true')

/**
 * A QuotedTweet is data about a tweet referenced within another tweet.
 *
 * QuotedTweet is included if Tweet.QuotedTweetField is requested, and the
 * linked-to tweet is public and visible at the time that the linking tweet
 * is hydrated, which can be during write-time or later after a cache-miss
 * read. Since linked-to tweets can be deleted, and users can become
 * suspended, deactivated, or protected, the presence of this value is not a
 * guarantee that the quoted tweet is still public and visible.
 *
 * Because a tweet quoting another tweet may not require a permalink URL in
 * the tweet's text, the URLs in ShortenedUrl may be useful to clients that
 * require maintaining a legacy-rendering of the tweet's text with the permalink.
 * See ShortenedUrl for details. Clients should avoid reading permalink whenever
 * possible and prefer the QuotedTweet's tweet_id and user_id instead.
 *
 * we always populate the permalink on tweet hydration unless there are partial
 * hydration errors or inner quoted tweet is filtered due to visibility rules.
 *
 */
struct QuotedTweet {
  1: i64 tweet_id (strato.space = "Tweet", strato.name = "tweet", personalDataType = 'TweetId')
  2: i64 user_id (strato.space = "User", strato.name = "user", personalDataType = 'UserId')
  3: optional ShortenedUrl permalink // URLs to access the quoted-tweet
}(persisted='true', hasPersonalData = 'true')

/**
 * A Contributor is a user who has access to another user's account.
 */
struct Contributor {
  1: i64 user_id (strato.space = "User", strato.name = "user", personalDataType = 'UserId')
  2: optional string screen_name (personalDataType = 'Username')// not set on Gizmoduck failure
}(persisted='true', hasPersonalData = 'true')

struct GeoCoordinates {
  1: double latitude (personalDataType = 'GpsCoordinates')
  2: double longitude (personalDataType = 'GpsCoordinates')
  3: i32 geo_precision = 0 (personalDataType = 'GpsCoordinates')

  /**
   * Whether or not make the coordinates public.
   *
   * This parameter is needed because coordinates are not typically published
   * by the author. If false: A tweet has geo coordinates shared but not make
   * it public.
   */
  4: bool display = 1
}(persisted='true', hasPersonalData = 'true')

enum PlaceType {
  UNKNOWN = 0
  COUNTRY = 1
  ADMIN = 2
  CITY = 3
  NEIGHBORHOOD = 4
  POI = 5
}

enum PlaceNameType {
  NORMAL = 0
  ABBREVIATION = 1
  SYNONYM = 2
}

struct PlaceName {
  1: string name
  2: string language = ""
  3: PlaceNameType type
  4: bool preferred
}(persisted='true', hasPersonalData='false')

/**
 * A Place is the physical and political properties of a location on Earth.
 */
struct Place {
  /**
   * Geo service identifier.
   */
  1: string id (personalDataType = 'PublishedPreciseLocationTweet, PublishedCoarseLocationTweet')

  /**
   * Granularity of place.
   */
  2: PlaceType type

  /**
   * The name of this place composed with its parent locations.
   *
   * For example, the full name for "Brooklyn" would be "Brooklyn, NY". This
   * name is returned in the language specified by
   * GetTweetOptions.language_tag.
   */
  3: string full_name (personalDataType = 'InferredLocation')

  /**
   * The best name for this place as determined by geoduck heuristics.
   *
   * This name is returned in the language specified by
   * GetTweetOptions.language_tag.
   *
   * @see com.twitter.geoduck.util.primitives.bestPlaceNameMatchingFilter
   */
  4: string name (personalDataType = 'PublishedPreciseLocationTweet, PublishedCoarseLocationTweet')

  /**
   * Arbitrary key/value data from the geoduck PlaceAttributes for this place.
   */
  5: map<string, string> attributes (personalDataTypeKey = 'PostalCode')

  7: set<PlaceName> names

  /**
   * The ISO 3166-1 alpha-2 code for the country containing this place.
   */
  9: optional string country_code (personalDataType = 'PublishedCoarseLocationTweet')

  /**
   * The best name for the country containing this place as determined by
   * geoduck heuristics.
   *
   * This name is returned in the language specified by
   * GetTweetOptions.language_tag.
   */
  10: optional string country_name (personalDataType = 'PublishedCoarseLocationTweet')

  /**
   * A simplified polygon that encompasses the place's geometry.
   */
  11: optional list<GeoCoordinates> bounding_box

  /**
   * An unordered list of geo service identifiers for places that contain this
   * one from the most immediate parent up to the country.
   */
  12: optional set<string> containers (personalDataType = 'PublishedCoarseLocationTweet')

  /**
   * A centroid-like coordinate that is within the geometry of the place.
   */
  13: optional GeoCoordinates centroid

  /**
   * Reason this place is being suppressed from display.
   *
   * This field is present when we previously had a place for this ID, but are
   * now choosing not to hydrate it and instead providing fake place metadata
   * along with a reason for not including place information.
   */
  14: optional geoduck_common.WithheldReason withheldReason
}(persisted='true', hasPersonalData='true')

/**
 * A UrlEntity is the position and content of a t.co shortened URL in the
 * tweet's text.
 *
 * If Talon returns an error to Tweetypie during tweet hydration, the
 * UrlEntity will be omitted from the response. UrlEntities are not included
 * for non-t.co-wrapped URLs found in older tweets, for spam and user safety
 * reasons.
*/
struct UrlEntity {
  /**
   * The position of this entity's first character, in zero-indexed Unicode
   * code points.
   */
  1: i16 from_index

  /**
   * The position after this entity's last character, in zero-indexed Unicode
   * code points.
   */
  2: i16 to_index

  /**
   * Shortened t.co URL.
   */
  3: string url (personalDataType = 'ShortUrl')

  /**
   * Original, full-length URL.
   *
   * This field will always be present on URL entities returned by
   * Tweetypie; it is optional as an implementation artifact.
   */
  4: optional string expanded (personalDataType = 'LongUrl')

  /**
   * Truncated version of expanded URL that does not include protocol and is
   * limited to 27 characters.
   *
   * This field will always be present on URL entities returned by
   * Tweetypie; it is optional as an implementation artifact.
   */
  5: optional string display (personalDataType = 'LongUrl')

  6: optional i64 click_count (personalDataType = 'CountOfTweetEntitiesClicked')
}(persisted = 'true', hasPersonalData = 'true')

/**
 * A MentionEntity is the position and content of a mention, (the "@"
 * character followed by the name of another valid user) in a tweet's text.
 *
 * If Gizmoduck returns an error to Tweetypie during tweet hydration that
 * MentionEntity will be omitted from the response.
 */
struct MentionEntity {
  /**
   * The position of this entity's first character ("@"), in zero-indexed
   * Unicode code points.
   */
  1: i16 from_index

  /**
   * The position after this entity's last character, in zero-indexed Unicode
   * code points.
   */
  2: i16 to_index

  /**
   * Contents of the mention without the leading "@".
   */
  3: string screen_name (personalDataType = 'Username')

  /**
   * User id of the current user with the mentioned screen name.
   *
   * In the current implementation user id does not necessarily identify the
   * user who was originally mentioned when the tweet was created, only the
   * user who owns the mentioned screen name at the time of hydration. If a
   * mentioned user changes their screen name and a second user takes the old
   * name, this field identifies the second user.
   *
   * This field will always be present on mention entities returned by
   * Tweetypie; it is optional as an implementation artifact.
   */
  4: optional i64 user_id (strato.space = "User", strato.name = "user", personalDataType = 'UserId')

  /**
   * Display name of the current user with the mentioned screen name.
   *
   * See user_id for caveats about which user's name is used here. This field
   * will always be present on mention entities returned by Tweetypie; it is
   * optional as an implementation artifact.
   */
  5: optional string name (personalDataType = 'DisplayName')

  /**
   * Indicates if the user referred to by this MentionEntity has been unmentioned
   * from the conversation.  If this field is set to true, the fromIndex and toIndex
   * fields will have a value of 0.
   *
   * @deprecated isUnmentioned is no longer being populated
   */
  6: optional bool isUnmentioned (personalDataType = 'ContentPrivacySettings')
}(persisted = 'true', hasPersonalData = 'true')

/**
  * A list of users that are mentioned in the tweet and have a blocking
  * relationship with the tweet author. Mentions for these users will be unlinked
  * in the tweet.
  */
struct BlockingUnmentions {
  1: optional list<i64> unmentioned_user_ids (strato.space = 'User', strato.name = 'users', personalDataType = 'UserId')
}(persisted = 'true', hasPersonalData = 'true', strato.graphql.typename = 'BlockingUnmentions')

/**
  * A list of users that are mentioned in the tweet and have indicated they do not want
  * to be mentioned via their mention settings. Mentions for these users will be unlinked
  * in the tweet by Twitter owned and operated clients.
  */
struct SettingsUnmentions {
  1: optional list<i64> unmentioned_user_ids (strato.space = 'User', strato.name = 'users', personalDataType = 'UserId')
}(persisted = 'true', hasPersonalData = 'true', strato.graphql.typename = 'SettingsUnmentions')

/**
 * A HashtagEntity is the position and content of a hashtag (a term starting
 * with "#") in a tweet's text.
 */
struct HashtagEntity {
  /**
   * The position of this entity's first character ("#"), in zero-indexed
   * Unicode code points.
   */
  1: i16 from_index

  /**
   * The position after this entity's last character, in zero-indexed Unicode
   * code points.
   */
  2: i16 to_index

  /**
   * Contents of the hashtag without the leading "#".
   */
  3: string text (personalDataType = 'PrivateTweetEntitiesAndMetadata, PublicTweetEntitiesAndMetadata')
}(persisted = 'true', hasPersonalData = 'true')

/**
 * A CashtagEntity is the position and content of a cashtag (a term starting
 * with "$") in a tweet's text.
 */
struct CashtagEntity {
  /**
   * The position of this entity's first character, in zero-indexed Unicode
   * code points.
   */
  1: i16 from_index

  /**
   * The position after this entity's last character, in zero-indexed Unicode
   * code points.
   */
  2: i16 to_index

  /**
   * Contents of the cashtag without the leading "$"
   */
  3: string text (personalDataType = 'PrivateTweetEntitiesAndMetadata, PublicTweetEntitiesAndMetadata')
}(persisted = 'true', hasPersonalData = 'true')

enum MediaTagType {
  USER = 0
  RESERVED_1 = 1
  RESERVED_2 = 2
  RESERVED_3 = 3
  RESERVED_4 = 4
}

struct MediaTag {
  1: MediaTagType tag_type
  2: optional i64 user_id (strato.space = "User", strato.name = "user",  personalDataType = 'UserId')
  3: optional string screen_name (personalDataType = 'Username')
  4: optional string name (personalDataType = 'DisplayName')
}(persisted='true', hasPersonalData = 'true')

struct TweetMediaTags {
  1: map<MediaCommon.MediaId, list<MediaTag>> tag_map
}(persisted='true', hasPersonalData = 'true')

/**
 * A UserMention is a user reference not stored in the tweet text.
 *
 * @deprecated Was used only in ReplyAddresses
 */
struct UserMention {
  1: i64 user_id (strato.space = "User", strato.name = "user", personalDataType = 'UserId')
  2: optional string screen_name (personalDataType = 'Username')
  3: optional string name (personalDataType = 'DisplayName')
}(persisted='true', hasPersonalData = 'true')

/**
 * ReplyAddresses is a list of reply entities which are stored outside of the
 * text.
 *
 * @deprecated
 */
struct ReplyAddresses {
  1: list<UserMention> users = []
}(persisted='true', hasPersonalData = 'true')

/**
 * SchedulingInfo is metadata about tweets created by the tweet scheduling
 * service.
 */
//
struct SchedulingInfo {
  /**
   * Id of the corresponding scheduled tweet before it was created as a real
   * tweet.
   */
  1: i64 scheduled_tweet_id (personalDataType = 'TweetId')
}(persisted='true', hasPersonalData = 'true')

/**
 * @deprecated
 */
enum SuggestType {
  WTF_CARD    =  0
  WORLD_CUP   =  1
  WTD_CARD    =  2
  NEWS_CARD   =  3
  RESERVED_4  =  4
  RESERVED_5  =  5
  RESERVED_6  =  6
  RESERVED_7  =  7
  RESERVED_8  =  8
  RESERVED_9  =  9
  RESERVED_10 = 10
  RESERVED_11 = 11
}

/**
 * @deprecated
 */
enum TwitterSuggestsVisibilityType {
  /**
   * Always public to everyone
   */
  PUBLIC = 1

  /**
   * Inherits visibility rules of personalized_for_user_id.
   */
  RESTRICTED = 2

  /**
   * Only visible to personalized_for_user_id (and author).
   */
  PRIVATE = 3
}

/**
 * TwitterSuggestInfo is details about a synthetic tweet generated by an early
 * version of Twitter Suggests.
 *
 * @deprecated
 */
struct TwitterSuggestInfo {
  1: SuggestType suggest_type
  2: TwitterSuggestsVisibilityType visibility_type
  3: optional i64 personalized_for_user_id (strato.space = "User", strato.name = "personalizedForUser", personalDataType = 'UserId')
  4: optional i64 display_timestamp_secs (personalDataType = 'PublicTimestamp')
}(persisted='true', hasPersonalData = 'true')

/**
 * A DeviceSource contains information about the client application from which
 * a tweet was sent.
 *
 * This information is stored in Passbird. The developer that owns a client
 * application provides this information on https://apps.twitter.com.
 */
struct DeviceSource {

  /**
   * The id of the client in the now deprecated device_sources MySQL table.
   *
   * Today this value will always be 0.
   *
   * @deprecated Use client_app_id
   */
  1: required i64 id (personalDataType = 'AppId')

  /**
   * Identifier for the client in the format "oauth:<client_app_id>"
   */
  2: string parameter

  /**
   * Identifier for the client in the format "oauth:<client_app_id>"
   */
  3: string internal_name

  /**
   * Developer-provided name of the client application.
   */
  4: string name

  /**
   * Developer-provided publicly accessible home page for the client
   * application.
   */
  5: string url

  /**
   * HTML fragment with a link to the client-provided URL
   */
  6: string display

  /**
   * This field is marked optional for backwards compatibility but will always
   * be populated by Tweetypie.
   */
  7: optional i64 client_app_id (personalDataType = 'AppId')
}(persisted='true', hasPersonalData = 'true')

/**
 * A Narrowcast restricts delivery of a tweet geographically.
 *
 * Narrowcasts allow multi-national advertisers to create geo-relevant content
 * from a central handle that is only delivered to to followers in a
 * particular country or set of countries.
 */
struct Narrowcast {
  2: list<string> location = [] (personalDataType = 'PublishedCoarseLocationTweet')
}(persisted='true', hasPersonalData = 'true')

/**
 * StatusCounts is a summary of engagement metrics for a tweet.
 *
 * These metrics are loaded from TFlock.
 */
struct StatusCounts {

  /**
   * Number of times this tweet has been retweeted.
   *
   * This number may not match the list of users who have retweeted because it
   * includes retweets from protected and suspended users who are not listed.
   */
  1: optional i64 retweet_count (personalDataType = 'CountOfPrivateRetweets, CountOfPublicRetweets', strato.json.numbers.type = 'int53')

  /**
   * Number of direct replies to this tweet.
   *
   * This number does not include replies to replies.
   */
  2: optional i64 reply_count (personalDataType = 'CountOfPrivateReplies, CountOfPublicReplies', strato.json.numbers.type = 'int53')

  /**
   * Number of favorites this tweet has received.
   *
   * This number may not match the list of users who have favorited a tweet
   * because it includes favorites from protected and suspended users who are
   * not listed.
   */
  3: optional i64 favorite_count (personalDataType = 'CountOfPrivateLikes, CountOfPublicLikes', strato.json.numbers.type = 'int53')

  /**
   * @deprecated
   */
  4: optional i64 unique_users_impressed_count (strato.json.numbers.type = 'int53')

  /**
   * Number of replies to this tweet including replies to replies.
   *
   * @deprecated
   */
  5: optional i64 descendent_reply_count (personalDataType = 'CountOfPrivateReplies, CountOfPublicReplies', strato.json.numbers.type = 'int53')

  /**
   * Number of times this tweet has been quote tweeted.
   *
   * This number may not match the list of users who have quote tweeted because it
   * includes quote tweets from protected and suspended users who are not listed.
   */
  6: optional i64 quote_count (personalDataType = 'CountOfPrivateRetweets, CountOfPublicRetweets', strato.json.numbers.type = 'int53')

  /**
   * Number of bookmarks this tweet has received.
   */
  7: optional i64 bookmark_count (personalDataType = 'CountOfPrivateLikes', strato.json.numbers.type = 'int53')

}(persisted='true', hasPersonalData = 'true', strato.graphql.typename='StatusCounts')

/**
 * A is a tweet's properties from one user's point of view.
 */
struct StatusPerspective {
  1: i64 user_id (strato.space = "User", strato.name = "user", personalDataType = 'UserId')

  /**
   * Whether user_id has favorited this tweet.
   */
  2: bool favorited

  /**
   * Whether user_id has retweeted this tweet.
   */
  3: bool retweeted

  /**
   * If user_id has retweeted this tweet, retweet_id identifies that tweet.
   */
  4: optional i64 retweet_id (strato.space = "Tweet", strato.name = "retweet", personalDataType = 'TweetId')

  /**
   * Whether user_id has reported this tweet as spam, offensive, or otherwise
   * objectionable.
   */
  5: bool reported

  /**
   * Whether user_id has bookmarked this tweet.
   */
  6: optional bool bookmarked
}(persisted='true', hasPersonalData = 'true')

/**
 * A Language is a guess about the human language of a tweet's text.
 *
 * Language is determined by TwitterLanguageIdentifier from the
 * com.twitter.common.text package (commonly called "Penguin").
 */
struct Language {
  /**
   * Language code in BCP-47 format.
   */
  1: required string language (personalDataType = 'InferredLanguage')

  /**
   * Language direction.
   */
  2: bool right_to_left

  /**
   * Confidence level of the detected language.
   */
  3: double confidence = 1.0

  /**
   * Other possible languages and their confidence levels.
   */
  4: optional map<string, double> other_candidates
}(persisted='true', hasPersonalData = 'true')

/**
 * A SupplementalLanguage is a guess about the human language of a tweet's
 * text.
 *
 * SupplementalLanguage is typically determined by a third-party translation
 * service. It is only stored when the service detects a different language
 * than TwitterLanguageIdentifier.
 *
 * @deprecated 2020-07-08 no longer populated.
 */
struct SupplementalLanguage {
  /**
   * Language code in BCP-47 format.
   */
  1: required string language (personalDataType = 'InferredLanguage')
}(persisted='true', hasPersonalData = 'true')

/**
 * A SpamLabel is a collection of spam actions for a tweet.
 *
 * Absence of a SpamLabel indicates that no action needs to be taken
 */
struct SpamLabel {
  /**
   * Filter this content at render-time
   *
   * @deprecated 2014-05-19 Use filter_renders
   */
  1: bool spam = 0

  2: optional set<tiered_actions.TieredActionResult> actions;
}(persisted='true')


/**
 * The available types of spam signal
 *
 * @deprecated
 */
enum SpamSignalType {
  MENTION           = 1
  SEARCH            = 2
  STREAMING         = 4
  # OBSOLETE HOME_TIMELINE = 3
  # OBSOLETE NOTIFICATION  = 5
  # OBSOLETE CONVERSATION  = 6
  # OBSOLETE CREATION      = 7
  RESERVED_VALUE_8  = 8
  RESERVED_VALUE_9  = 9
  RESERVED_VALUE_10 = 10
}

/**
 * @deprecated
 * CardBindingValues is a collection of key-value pairs used to render a card.
 */
struct CardBindingValues {
  1: list<cards.Card2ImmediateValue> pairs = []
}(persisted='true')

/**
 * A CardReference is a mechanism for explicitly associating a card with a
 * tweet.
 */
struct CardReference {
  /**
   * Link to the card to associate with a tweet.
   *
   * This URI may reference either a card stored in the card service, or
   * another resource, such as a crawled web page URL. This value supercedes
   * any URL present in tweet text.
   */
  1: string card_uri
}(persisted='true')

/**
 * A TweetPivot is a semantic entity related to a tweet.
 *
 * TweetPivots are used to direct to the user to another related location. For
 * example, a "See more about <name>" UI element that takes the user to <url>
 * when clicked.
 */
struct TweetPivot {
  1: required tweet_annotation.TweetEntityAnnotation annotation
  2: required tweet_pivots.TweetPivotData data
}(persisted='true')

struct TweetPivots {
  1: required list<TweetPivot> tweet_pivots
}(persisted='true')

struct EscherbirdEntityAnnotations {
  1: list<tweet_annotation.TweetEntityAnnotation> entity_annotations
}(persisted='true')

struct TextRange {
  /**
   * The inclusive index of the start of the range, in zero-indexed Unicode
   * code points.
   */
  1: required i32 from_index

  /**
   * The exclusive index of the end of the range, in zero-indexed Unicode
   * code points.
   */
  2: required i32 to_index
}(persisted='true')

struct TweetCoreData {
  1: i64 user_id (strato.space = "User", strato.name = "user", personalDataType = 'UserId', tweetEditAllowed='false')

  /**
   * The body of the tweet consisting of the user-supplied displayable message
   * and:
   * - an optional prefix list of @mentions
   * - an optional suffix attachment url.
   *
   * The indices from visible_text_range specify the substring of text indended
   * to be displayed, whose length is limited to 140 display characters.  Note
   * that the visible substring may be longer than 140 characters due to HTML
   * entity encoding of &, <, and > .

   * For retweets the text is that of the original tweet, prepended with "RT
   * @username: " and truncated to 140 characters.
   */
  2: string text (personalDataType = 'PrivateTweets, PublicTweets')

  /**
   * The client from which this tweet was created
   *
   * The format of this value is oauth:<client id>.
   */
  3: string created_via (personalDataType = 'ClientType')

  /**
   * Time this tweet was created.
   *
   * This value is seconds since the Unix epoch. For tweets with Snowflake IDs
   * this value is redundant, since a millisecond-precision timestamp is part
   * of the id.
   */
  4: i64 created_at_secs

  /**
   * Present when this tweet is a reply to another tweet or another user.
   */
  5: optional Reply reply

  /**
   * Present when a tweet begins with an @mention or has metadata indicating the directed-at user.
   */
  6: optional DirectedAtUser directed_at_user

  /**
   * Present when this tweet is a retweet.
   */
  7: optional Share share

  /**
   * Whether there is a takedown country code or takedown reason set for this specific tweet.
   *
   * See takedown_country_codes for the countries where the takedown is active.  (deprecated)
   * See takedown_reasons for a list of reasons why the tweet is taken down.
   *
   * has_takedown will be set to true if either this specific tweet or the author has a
   * takedown active.
   */
  8: bool has_takedown = 0

  /**
   * Whether this tweet might be not-safe-for-work, judged by the tweet author.
   *
   * Users can flag their own accounts as not-safe-for-work in account
   * preferences by selecting "Mark media I tweet as containing material that
   * may be sensitive" and each tweet created after that point will have
   * this flag set.
   *
   * The value can also be updated after tweet create time via the
   * update_possibly_sensitive_tweet method.
   */
  9: bool nsfw_user = 0

  /**
   * Whether this tweet might be not-safe-for-work, judged by an internal Twitter
   * support agent.
   *
   * This tweet value originates from the user's nsfw_admin flag at
   * tweet create time but can be updated afterwards using the
   * update_possibly_sensitive_tweet method.
   */
  10: bool nsfw_admin = 0

  /**
   * When nullcast is true a tweet is not delivered to a user's followers, not
   * shown in the user's timeline, and does not appear in search results.
   *
   * This is primarily used to create tweets that can be used as ads without
   * broadcasting them to an advertiser's followers.
   */
  11: bool nullcast = 0 (tweetEditAllowed='false')

  /**
   * Narrowcast limits delivery of a tweet to followers in specific geographic
   * regions.
   */
  12: optional Narrowcast narrowcast (tweetEditAllowed='false')

  /**
   * The impression id of the ad from which this tweet was created.
   *
   * This is set when a user retweets or replies to a promoted tweet. It is
   * used to attribute the "earned" exposure of an advertisement.
   */
  13: optional i64 tracking_id (personalDataType = 'ImpressionId', tweetEditAllowed='false')

  /**
   * A shared identifier among all the tweets in the reply chain for a single
   * tweet.
   *
   * The conversation id is the id of the tweet that started the conversation.
   */
  14: optional i64 conversation_id (strato.space = "Tweet", strato.name = "conversation", personalDataType = 'TweetId')

  /**
   * Whether this tweet has media of any type.
   *
   * Media can be in the form of media entities, media cards, or URLs in the
   * tweet text that link to media partners.
   *
   * @see MediaIndexHelper
   */
  15: optional bool has_media

  /**
   * Supported for legacy clients to associate a location with a Tweet.
   *
   * Twitter owned clients must use place_id REST API param for geo-tagging.
   *
   * @deprecated Use place_id REST API param
   */
  16: optional GeoCoordinates coordinates (personalDataType = 'GpsCoordinates', tweetEditAllowed='false')

  /**
   * The location where a tweet was sent from.
   *
   * Place is either published in API request explicitly or implicitly reverse
   * geocoded from API lat/lon coordinates params.
   *
   * Tweetypie implementation notes:
   *  - Currently, if both place_id and coordinates are specified, coordinates
   *    takes precedence in geo-tagging. I.e.: Place returned rgc(coordinates)
   *    sets the place_id field.
   *  - place_id is reverse geocoded on write-path.
   */
  17: optional string place_id (personalDataType = 'PublishedPreciseLocationTweet, PublishedCoarseLocationTweet')
}(persisted='true', hasPersonalData = 'true', tweetEditAllowed='false')

/**
 * List of community ID's the tweet belongs to.
 */
struct Communities {
  1: required list<i64> community_ids (personalDataType = 'EngagementId')
}(persisted='true')

/**
 * Tweet metadata that is present on extended tweets, a tweet whose total text length is greater
 * than the classic limit of 140 characters.
 */
struct ExtendedTweetMetadata {
  /**
   * @deprecated was display_count
   */
  1: i32 unused1 = 0

  /**
   * The index, in unicode code points, at which the tweet text should be truncated
   * for rendering in a public API backwards-compatible mode.  Once truncated to this
   * point, the text should be appended with an ellipsis, a space, and the short_url
   * from self_permalink.  The resulting text must conform to the 140 display glyph
   * limit.
   */
  2: required i32 api_compatible_truncation_index

  /**
   * @deprecated was default_display_truncation_index
   */
  3: i32 unused3 = 0

  /**
   * @deprecated was is_long_form
   */
  4: bool unused4 = 0

  /**
   * @deprecated was preview_range
   */
  5: optional TextRange unused5

  /**
   * @deprecated was extended_preview_range
   */
  6: optional TextRange unused6
}(persisted='true')

/**
 * @deprecated use TransientCreateContext instead
 */
enum TweetCreateContextKey {
  PERISCOPE_IS_LIVE    = 0,
  PERISCOPE_CREATOR_ID = 1
}

/**
 * DirectedAtUserMetadata is a tweetypie-internal structure that can be used to store metadata about
 * a directed-at user on the tweet.
 *
 * Note: absence of this field does not imply the tweet does not have a DirectedAtUser, see
 * tweet.directedAtUserMetadata for more information.
 */
struct DirectedAtUserMetadata {
  /**
   * ID of the user a tweet is directed-at.
   */
  1: optional i64 user_id (personalDataType = 'UserId')
}(persisted='true', hasPersonalData = 'true')

/**
 * Tweet metadata that may be present on tweets in a self-thread (tweetstorm).
 *
 * A self-thread is a tree of self-replies that may either:
 * 1. begin as a reply to another user's tweet (called a non-root self-thread) or
 * 2. stand alone (called root self-thread).
 *
 * Note that not all self-threads have SelfThreadMetadata.
 */
struct SelfThreadMetadata {
  /**
   * A shared identifier among all the tweets in the self-thread (tweetstorm).
   *
   * The tweetstorm id is the id of the tweet that started the self thread.
   *
   * If the id matches the tweet's conversation_id then it is a root self-thread, otherwise it is
   * a non-root self-thread.
   */
  1: required i64 id (personalDataType = 'TweetId')

  /**
   * Indicates if the tweet with this SelfThreadMetadata is a leaf in the self-thread tree.
   * This flag might be used to encourage the author to extend their tweetstorm at the end.
   */
  2: bool isLeaf = 0
}(persisted='true', hasPersonalData = 'true')

/**
 * Composer flow used to create this tweet. Unless using the News Camera (go/newscamera)
 * flow, this should be `STANDARD`.
 *
 * When set to `CAMERA`, clients are expected to display the tweet with a different UI
 * to emphasize attached media.
 */
enum ComposerSource {
  STANDARD = 1
  CAMERA = 2
}


/**
 * The conversation owner and users in invited_user_ids can reply
 **/
struct ConversationControlByInvitation {
  1: required list<i64> invited_user_ids (personalDataType = 'UserId')
  2: required i64 conversation_tweet_author_id (personalDataType = 'UserId')
  3: optional bool invite_via_mention
}(persisted='true', hasPersonalData = 'true')

/**
 * The conversation owner, users in invited_user_ids, and users who the conversation owner follows can reply
 **/
struct ConversationControlCommunity {
  1: required list<i64> invited_user_ids (personalDataType = 'UserId')
  2: required i64 conversation_tweet_author_id (personalDataType = 'UserId')
  3: optional bool invite_via_mention
}(persisted='true', hasPersonalData = 'true')

/**
 * The conversation owner, users in invited_user_ids, and users who follows the conversation owner can reply
 **/
struct ConversationControlFollowers {
 1: required list<i64> invited_user_ids (personalDataType = 'UserId')
 2: required i64 conversation_tweet_author_id (personalDataType = 'UserId')
 3: optional bool invite_via_mention
}(persisted='true', hasPersonalData = 'true')

/**
* This tweet metadata captures restrictions on who is allowed to reply in a conversation.
*/
union ConversationControl {

  1: ConversationControlCommunity community

  2: ConversationControlByInvitation byInvitation

  3: ConversationControlFollowers followers
}(persisted='true', hasPersonalData = 'true')

// This tweet metadata shows the exclusivity of a tweet and is used to determine
// whether replies / visibility of a tweet is limited
struct ExclusiveTweetControl {
  1: required i64 conversation_author_id (personalDataType = 'UserId')
}(persisted='true', hasPersonalData = 'true')

/**
 * Tweet metadata for a Trusted Friends tweet.
 *
 * A Trusted Friends tweet is a tweet whose visibility is restricted to members
 * of an author-specified list.
 *
 * Replies to a Trusted Friends tweet will inherit a copy of this metadata from
 * the root tweet.
 */
struct TrustedFriendsControl {
  /**
   * The ID of the Trusted Friends List whose members can view this tweet.
   */
  1: required i64 trusted_friends_list_id (personalDataType = 'TrustedFriendsListMetadata')
}(persisted='true', hasPersonalData = 'true')

enum CollabInvitationStatus {
  PENDING = 0
  ACCEPTED = 1
  REJECTED = 2
}

/**
 * Represents a user who has been invited to collaborate on a CollabTweet, associated with whether
 * they have accepted or rejected collaboration
 */
struct InvitedCollaborator {
  1: required i64 collaborator_user_id (personalDataType = 'UserId')
  2: required CollabInvitationStatus collab_invitation_status
}(persisted='true', hasPersonalData='true')

/**
 * Present if Tweet is a CollabInvitation awaiting publishing, stores list of invited Collaborators
 */
struct CollabInvitation {
  1: required list<InvitedCollaborator> invited_collaborators
}(persisted='true', hasPersonalData='true')

/**
 * Present if Tweet is a published CollabTweet, stores list of Collaborators
 */
struct CollabTweet {
  1: required list<i64> collaborator_user_ids (personalDataType = 'UserId')
}(persisted='true', hasPersonalData='true')

/**
 * CollabTweets treat multiple users as co-authors or "Collaborators" of a single "Collab Tweet".
 *
 * When creating a Collab Tweet, the original author will begin by creating a CollabInvitation which
 * is sent to another Collaborator to accept or reject collaboration. If and when other
 * Collaborators have accepted, the CollabInvitation is replaced by a CollabTweet which is published
 * publicly and fanned out to followers of all Collaborators. A CollabInvitation will be hidden from
 * anyone except the list of Collaborators using VF. The CollabTweet will then be fanned out like
 * a regular Tweet to the profiles and combined audiences of all Collaborators.
 *
 * A Tweet representing a CollabTweet or CollabInvitation is denoted by the presence of a
 * CollabControl field on a Tweet.
 */
union CollabControl {
  1: CollabInvitation collab_invitation
  2: CollabTweet collab_tweet
}(persisted='true', hasPersonalData='true')

/**
 * A Tweet is a message that belongs to a Twitter user.
 *
 * The Tweet struct replaces the deprecated Status struct. All fields except
 * id are optional.
 *
 * This struct supports the additional fields flexible schema. Additional fields are
 * defined starting from field 101.
 *
 * The guidelines for adding a new Additional field:
 * 1. It's required to define the additional field as an optional struct.
 *    Inside the struct, define optional or non-optional field(s) according
 *    to your needs.
 * 2. If you have several immutable piece of data that are always accessed
 *    together, you should define them in the same struct for better storage
 *    locality.
 * 3. If your data model has several mutable pieces, and different piece can
 *    be updated in a close succession, you should group them into
 *    separate structs and each struct contains one mutable piece.
 */
struct Tweet {
  /**
  * The primary key for a tweet.
  *
  * A tweet's id is assigned by the tweet service at creation time. Since
  * 2010-11-04 tweet ids have been generated using Snowflake. Prior to this
  * ids were assigned sequentially by MySQL AUTOINCREMENT.
  */
  1: i64 id (personalDataType = 'TweetId')

  /**
   * The essential properties of a tweet.
   *
   * This field will always be present on tweets returned by Tweetypie. It is
   * marked optional so an empty tweet can be provided to write additional
   * fields.
   */
  2: optional TweetCoreData core_data

  /**
   * URLs extracted from the tweet's text.
   */
  3: optional list<UrlEntity> urls

  /**
   * Mentions extracted from the tweet's text.
   */
  4: optional list<MentionEntity> mentions

  /**
   * Hashtags extracted from the tweet's text.
   */
  5: optional list<HashtagEntity> hashtags

  /**
   * Cashtags extracted from the tweet's text
   */
  6: optional list<CashtagEntity> cashtags

  7: optional list<media_entity.MediaEntity> media

  /**
   * Place identified by Tweet.core_data.place_id.
   */
  10: optional Place place

  11: optional QuotedTweet quoted_tweet

  /**
   * The list of countries where this tweet will not be shown.
   *
   * This field contains countries for both the tweet and the user, so it may
   * contain values even if has_takedown is false.
   *
   * @deprecated, use field 30 takedown_reasons which includes the same information and more
   */
  12: optional list<string> takedown_country_codes (personalDataType = 'ContentRestrictionStatus')

  /**
   * Interaction metrics for this tweet.
   *
   * Included when one of GetTweetOptions.load_retweet_count,
   * GetTweetOptions.load_reply_count, or GetTweetOptions.load_favorite_count
   * is set. This can be missing in a PARTIAL response if the TFlock request
   * fails.
   */
  13: optional StatusCounts counts

  /**
   * Properties of the client from which the tweet was sent.
   *
   * This can be missing in a PARTIAL response if the Passbird request fails.
   */
  14: optional DeviceSource device_source

  /**
   * Properties of this tweet from the point of view of
   * GetTweetOptions.for_user_id.
   *
   * This field is included only when for_user_id is provided and
   * include_perspective == true This can be missing in a PARTIAL response if
   * the timeline service request fails.
   */
  15: optional StatusPerspective perspective

  /**
   * Version 1 cards.
   *
   * This field is included only when GetTweetOptions.include_cards == true.
   */
  16: optional list<cards.Card> cards

  /**
   * Version 2 cards.
   *
   * This field is included only included when GetTweetOptions.include_cards
   * == true and GetTweetOptions.cards_platform_key is set to valid value.
   */
  17: optional cards.Card2 card2

  /**
   * Human language of tweet text as determined by TwitterLanguageIdentifier.
   */
  18: optional Language language

  /**
   * @deprecated
   */
  19: optional map<SpamSignalType, SpamLabel> spam_labels

  /**
   * User responsible for creating this tweet when it is not the same as the
   * core_data.user_id.
   *
   * This is sensitive information and must not be shared externally (via UI,
   * API, or streaming) except to the the owner of the tweet
   * (core_data.user_id) or a contributor to the owner's account.
   */
  20: optional Contributor contributor

  // obsolete 21: optional list<TopicLabel> topic_labels

  22: optional enrichments_profilegeo.ProfileGeoEnrichment profile_geo_enrichment

  // Maps extension name to value; only populated if the request contained an extension on tweets.
  // obsolete 24: optional map<string, binary> extensions

  /**
   * Deprecated.
   * Semantic entities that are related to this tweet.
   */
  25: optional TweetPivots tweet_pivots

  /**
   * @deprecated
   * Strato Tweet Extensions support has moved to birdherd.
   *
   * Internal thrift clients should query strato columns directly and
   * not rely upon ext/*.Tweet columns which are designed to serve
   * client APIs.
   */
  26: optional binary extensions_reply

  /**
   * Has the requesting user muted the conversation referred to by
   * `conversation_id`? When this field is absent, the conversation may
   * or may not be muted. Use the `include_conversation_muted` field in
   * GetTweetOptions to request this field.
   *
   * If this field has a value, the value applies to the user in the
   * `for_user_id` field of the requesting `GetTweetOptions`.
   */
  27: optional bool conversation_muted

  /**
   * The user id of the tweet referenced by conversation_id
   *
   * @deprecated Was conversation_owner_id. This was never implemented.
   */
  28: optional i64 unused28

  /**
   * Has this tweet been removed from its conversation by the conversation owner?
   *
   * @deprecated Was is_removed_from_conversation. This was never implemented.
   */
  29: optional bool unused29

  /**
   * A list of takedown reasons indicating which country and reason this tweet was taken down.
   */
  30: optional list<withholding.TakedownReason> takedown_reasons

  /**
   * @obsolete, self-thread metadata is now stored in field 151, self_thread_metadata
   */
  31: optional self_thread.SelfThreadInfo self_thread_info

  // field 32 to 99 are reserved
  // field 100 is used for flexible schema proof of concept
  // additional fields
  // these fields are stored in Manhattan flexible schema
  101: optional TweetMediaTags media_tags
  102: optional SchedulingInfo scheduling_info

  /**
   * @deprecated
   */
  103: optional CardBindingValues binding_values

  /**
   * @deprecated
   */
  104: optional ReplyAddresses reply_addresses

  /**
   * OBSOLETE, but originally contained information about synthetic tweets created by the first
   * version of Twitter Suggests.
   *
   * @deprecated
   */
  105: optional TwitterSuggestInfo obsolete_twitter_suggest_info

  106: optional EscherbirdEntityAnnotations escherbird_entity_annotations (personalDataType = 'AnnotationValue')

  // @deprecated 2021-07-19
  107: optional safety_label.SafetyLabel spam_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  108: optional safety_label.SafetyLabel abusive_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  109: optional safety_label.SafetyLabel low_quality_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  110: optional safety_label.SafetyLabel nsfw_high_precision_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  111: optional safety_label.SafetyLabel nsfw_high_recall_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  112: optional safety_label.SafetyLabel abusive_high_recall_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  113: optional safety_label.SafetyLabel low_quality_high_recall_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  114: optional safety_label.SafetyLabel persona_non_grata_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  115: optional safety_label.SafetyLabel recommendations_low_quality_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  116: optional safety_label.SafetyLabel experimentation_label (personalDataType = 'TweetSafetyLabels')

  117: optional tweet_location_info.TweetLocationInfo tweet_location_info
  118: optional CardReference card_reference

  /**
   * @deprecated 2020-07-08 no longer populated.
   */
  119: optional SupplementalLanguage supplemental_language

  // field 120, additional_media_metadata, is deprecated.
  // field 121, media_metadatas, is deprecated

  // under certain circumstances, including long form tweets, we create and store a self-permalink
  // to this tweet. in the case of a long-form tweet, this will be used in a truncated version
  // of the tweet text.
  122: optional ShortenedUrl self_permalink

  // metadata that is present on extended tweets.
  123: optional ExtendedTweetMetadata extended_tweet_metadata

  // obsolete 124: crosspost_destinations.CrosspostDestinations crosspost_destinations

  // Communities associated with a tweet
  125: optional Communities communities (personalDataType = 'PrivateTweetEntitiesAndMetadata', tweetEditAllowed='false')

  // If some text at the beginning or end of the tweet should be hidden, then this
  // field indicates the range of text that should be shown in clients.
  126: optional TextRange visible_text_range

  // @deprecated 2021-07-19
  127: optional safety_label.SafetyLabel spam_high_recall_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  128: optional safety_label.SafetyLabel duplicate_content_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  129: optional safety_label.SafetyLabel live_low_quality_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  130: optional safety_label.SafetyLabel nsfa_high_recall_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  131: optional safety_label.SafetyLabel pdna_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  132: optional safety_label.SafetyLabel search_blacklist_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  133: optional safety_label.SafetyLabel low_quality_mention_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  134: optional safety_label.SafetyLabel bystander_abusive_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  135: optional safety_label.SafetyLabel automation_high_recall_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  136: optional safety_label.SafetyLabel gore_and_violence_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  137: optional safety_label.SafetyLabel untrusted_url_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  138: optional safety_label.SafetyLabel gore_and_violence_high_recall_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  139: optional safety_label.SafetyLabel nsfw_video_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  140: optional safety_label.SafetyLabel nsfw_near_perfect_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  141: optional safety_label.SafetyLabel automation_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  142: optional safety_label.SafetyLabel nsfw_card_image_label (personalDataType = 'TweetSafetyLabels')
  // @deprecated 2021-07-19
  143: optional safety_label.SafetyLabel duplicate_mention_label (personalDataType = 'TweetSafetyLabels')

  // @deprecated 2021-07-19
  144: optional safety_label.SafetyLabel bounce_label (personalDataType = 'TweetSafetyLabels')
  // field 145 to 150 is reserved for safety labels

  /**
   * If this tweet is part of a self_thread (tweetstorm) then this value may be set.
   * See SelfThreadMetadata for details.
   */
  151: optional SelfThreadMetadata self_thread_metadata
  // field 152 has been deprecated

  // The composer used to create this tweet. Either via the standard tweet creator or the
  // Camera flow (go/newscamera).
  //
  // NOTE: this field is only set if a client passed an explicit ComposerSource in the PostTweetRequest.
  // News Camera is deprecated and we no longer set ComposerSource in the PostTweetRequest so no new Tweets will
  // have this field.
  153: optional ComposerSource composer_source

  // Present if replies are restricted, see ConversationControl for more details
  154: optional ConversationControl conversation_control

  // Determines the super follows requirements for being able to view a tweet.
  155: optional ExclusiveTweetControl exclusive_tweet_control (tweetEditAllowed='false')

  // Present for a Trusted Friends tweet, see TrustedFriendsControl for more details.
  156: optional TrustedFriendsControl trusted_friends_control (tweetEditAllowed='false')

  // Data about edits and editability. See EditControl for more details.
  157: optional edit_control.EditControl edit_control

  // Present for a CollabTweet or CollabInvitation, see CollabControl for more details.
  158: optional CollabControl collab_control (tweetEditAllowed='false')

  // Present for a 3rd-party developer-built card. See http://go/developer-built-cards-prd
  159: optional i64 developer_built_card_id (personalDataType = 'CardId')

  // Data about enrichments attached to a tweet.
  160: optional creative_entity_enrichments.CreativeEntityEnrichments creative_entity_enrichments_for_tweet

  // This field includes summed engagements from the previous tweets in the edit chain.
  161: optional StatusCounts previous_counts

  // A list of media references, including information about the source Tweet for pasted media.
  // Prefer this field to media_keys, as media_keys is not present for old Tweets or pasted media Tweets.
  162: optional list<media_ref.MediaRef> media_refs

  // Whether this tweet is a 'backend tweet' to be referenced only by the creatives containers service
  // go/cea-cc-integration for more details
  163: optional bool is_creatives_container_backend_tweet

  /**
  * Aggregated perspective of this tweet and all other versions from the point of view of the
  * user specified in for_user_id.
  *
  * This field is included only when for_user_id is provided and can be missing in a PARTIAL response
  * if the timeline service request fails.
  */
  164: optional api_fields.TweetPerspective edit_perspective

  // Visibility controls related to Toxic Reply Filtering
  // go/toxrf for more details
  165: optional filtered_reply_details.FilteredReplyDetails filtered_reply_details

  // The list of mentions that have unmentioned from the tweet's associated conversation
  166: optional unmentions.UnmentionData unmention_data

  /**
    * A list of users that were mentioned in the tweet and have a blocking
    * relationship with the author.
    */
  167: optional BlockingUnmentions blocking_unmentions

  /**
      * A list of users that were mentioned in the tweet and should be unmentioned
      * based on their mention setttings
      */
  168: optional SettingsUnmentions settings_unmentions

  /**
    * A Note associated with this Tweet.
    */
  169: optional note_tweet.NoteTweet note_tweet

  // For additional fields, the next available field id is 169.
  // NOTE: when adding a new additional field, please also update UnrequestedFieldScrubber.scrubKnownFields

  /**
   * INTERNAL FIELDS
   *
   * These fields are used by tweetypie only and should not be accessed externally.
   * The field ids are in descending order, starting with `32767`.
   */

  /**
   * Present if tweet data is provided creatives container service instead of tweetypie storage,
   * with encapsulated tweets or customized data.
   */
  32763: optional i64 underlying_creatives_container_id

  /**
   * Stores tweetypie-internal metadata about a DirectedAtUser.
   *
   * A tweet's DirectedAtUser is hydrated as follows:
   * 1. if this field is present, then DirectedAtUserMetadata.userId is the directed-at user
   * 2. if this field is absent, then if the tweet has a reply and has a mention starting at text
   *    index 0 then that user is the directed-at user.
   *
   * Note: External clients should use CoreData.directed_at_user.
   */
  32764: optional DirectedAtUserMetadata directed_at_user_metadata

  // list of takedowns that are applied directly to the tweet
  32765: optional list<withholding.TakedownReason> tweetypie_only_takedown_reasons

  // Stores the media keys used to interact with the media platform systems.
  // Prefer `media_refs` which will always have media data, unlike this field which is empty for
  // older Tweets and Tweets with pasted media.
  32766: optional list<MediaCommon.MediaKey> media_keys

  // field 32767 is the list of takedowns that are applied directly to the tweet
  32767: optional list<string> tweetypie_only_takedown_country_codes (personalDataType = 'ContentRestrictionStatus')


  // for internal fields, the next available field id is 32765 (counting down)
}(persisted='true', hasPersonalData = 'true')
