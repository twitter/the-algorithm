namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala

include "com/twitter/context/feature_context.thrift"
include "com/twitter/expandodo/cards.thrift"
include "com/twitter/gizmoduck/user.thrift"
include "com/twitter/mediaservices/commons/MediaCommon.thrift"
include "com/twitter/mediaservices/commons/MediaInformation.thrift"
include "com/twitter/mediaservices/commons/TweetMedia.thrift"
include "com/twitter/servo/exceptions.thrift"
include "com/twitter/servo/cache/servo_repo.thrift"
include "com/twitter/tseng/withholding/withholding.thrift"
include "com/twitter/tweetypie/delete_location_data.thrift"
include "com/twitter/tweetypie/transient_context.thrift"
include "com/twitter/tweetypie/media_entity.thrift"
include "com/twitter/tweetypie/tweet.thrift"
include "com/twitter/tweetypie/tweet_audit.thrift"
include "com/twitter/tweetypie/stored_tweet_info.thrift"
include "com/twitter/tweetypie/tweet_service.thrift"

typedef i16 FieldId

struct UserIdentity {
  1: required i64 id
  2: required string screen_name
  3: required string real_name
# obsolete 4: bool deactivated = 0
# obsolete 5: bool suspended = 0
}

enum HydrationType {
  MENTIONS          = 1,
  URLS              = 2,
  CACHEABLE_MEDIA   = 3,
  QUOTED_TWEET_REF  = 4,
  REPLY_SCREEN_NAME = 5,
  DIRECTED_AT       = 6,
  CONTRIBUTOR       = 7,
  SELF_THREAD_INFO  = 8
}

struct CachedTweet {
  1: required tweet.Tweet tweet
  // @obsolete 2: optional set<i16> included_additional_fields
  3: set<HydrationType> completed_hydrations = []

  // Indicates that a tweet was deleted after being bounced for violating
  // the Twitter Rules.
  // When set to true, all other fields in CachedTweet are ignored.
  4: optional bool is_bounce_deleted

  // Indicates whether this tweet has safety labels stored in Strato.
  // See com.twitter.tweetypie.core.TweetData.hasSafetyLabels for more details.
  // @obsolete 5: optional bool has_safety_labels
} (persisted='true', hasPersonalData='true')

struct MediaFaces {
  1: required map<TweetMedia.MediaSizeType, list<MediaInformation.Face>> faces
}

enum AsyncWriteEventType {
  INSERT                          = 1,
  DELETE                          = 2,
  UNDELETE                        = 3,
  SET_ADDITIONAL_FIELDS           = 4,
  DELETE_ADDITIONAL_FIELDS        = 5,
  UPDATE_POSSIBLY_SENSITIVE_TWEET = 6,
  UPDATE_TWEET_MEDIA              = 7,
  TAKEDOWN                        = 8,
  SET_RETWEET_VISIBILITY          = 9
}

// an enum of actions that could happen in an async-write (insert or delete)
enum AsyncWriteAction {
  HOSEBIRD_ENQUEUE        = 1
  SEARCH_ENQUEUE          = 2
  // obsolete MAIL_ENQUEUE            = 3
  FANOUT_DELIVERY         = 4
  // obsolete FACEBOOK_ENQUEUE        = 5
  TWEET_INDEX             = 6
  TIMELINE_UPDATE         = 7
  CACHE_UPDATE            = 8
  REPLICATION             = 9
  // obsolete MONORAIL_EXPIRY_ENQUEUE = 10
  USER_GEOTAG_UPDATE      = 11
  // obsolete IBIS_ENQUEUE            = 12
  EVENT_BUS_ENQUEUE       = 13
  // obsolete HOSEBIRD_BINARY_ENQUEUE = 14
  TBIRD_UPDATE            = 15
  RETWEETS_DELETION       = 16
  GUANO_SCRIBE            = 17
  MEDIA_DELETION          = 18
  GEO_SEARCH_REQUEST_ID   = 19
  SEARCH_THRIFT_ENQUEUE    = 20
  RETWEET_ARCHIVAL_ENQUEUE = 21
}

# This struct is scribed to test_tweetypie_failed_async_write after
# an async-write action has failed multiple retries
struct FailedAsyncWrite {
  1: required AsyncWriteEventType event_type
  2: required AsyncWriteAction action
  3: optional tweet.Tweet tweet
} (persisted='true', hasPersonalData='true')

# This struct is scribed to test_tweetypie_detached_retweets after
# attempting to read a retweet for which the source tweet has been deleted.
struct DetachedRetweet {
  1: required i64 tweet_id (personalDataType='TweetId')
  2: required i64 user_id (personalDataType='UserId')
  3: required i64 source_tweet_id (personalDataType='TweetId')
} (persisted='true', hasPersonalData='true')

struct TweetCacheWrite {
  1: required i64 tweet_id (personalDataType = 'TweetId')
  // If the tweet id is a snowflake id, this is an offset since tweet creation. 
  // If it is not a snowflake id, then this is a Unix epoch time in
  // milliseconds. (The idea is that for most tweets, this encoding will make
  // it easier to see the interval between events and whether it occured soon
  // acter tweet creation.)
  2: required i64 timestamp (personalDataType = 'TransactionTimestamp')
  3: required string action // One of "set", "add", "replace", "cas", "delete"
  4: required servo_repo.CachedValue cached_value // Contains metadata about the cached value
  5: optional CachedTweet cached_tweet
} (persisted='true', hasPersonalData='true')

struct AsyncInsertRequest {
  12: required tweet.Tweet tweet
  18: required user.User user
  21: required i64 timestamp
  // the cacheable version of tweet from field 12
  29: required CachedTweet cached_tweet
  # 13: obsolete tweet.Tweet internal_tweet
  19: optional tweet.Tweet source_tweet
  20: optional user.User source_user
  // Used for quote tweet feature
  22: optional tweet.Tweet quoted_tweet
  23: optional user.User quoted_user
  28: optional i64 parent_user_id
  // Used for delivering the requestId of a geotagged tweet
  24: optional string geo_search_request_id
  # 7: obsolete
  # if not specified, all async insert actions are performed. if specified, only
  # the specified action is performed; this is used for retrying specific actions
  # that failed on a previous attempt.
  10: optional AsyncWriteAction retry_action
  # 11: obsolete: bool from_monorail = 0
  # 14: obsolete
  15: optional feature_context.FeatureContext feature_context
  # 16: obsolete
  # 17: obsolete
  # 26: obsolete: optional tweet.Tweet debug_tweet_copy
  27: optional map<tweet.TweetCreateContextKey, string> additional_context
  30: optional transient_context.TransientCreateContext transient_context
  // Used to check whether the same tweet has been quoted multiple
  // times by a given user.
  31: optional bool quoter_has_already_quoted_tweet
  32: optional InitialTweetUpdateRequest initialTweetUpdateRequest
  // User ids of users mentioned in note tweet. Used for tls events
  33: optional list<i64> note_tweet_mentioned_user_ids
}

struct AsyncUpdatePossiblySensitiveTweetRequest {
  1: required tweet.Tweet tweet
  2: required user.User user
  3: required i64 by_user_id
  4: required i64 timestamp
  5: optional bool nsfw_admin_change
  6: optional bool nsfw_user_change
  7: optional string note
  8: optional string host
  9: optional AsyncWriteAction action
}

struct AsyncUpdateTweetMediaRequest {
  1: required i64 tweet_id
  2: required list<media_entity.MediaEntity> orphaned_media
  3: optional AsyncWriteAction retry_action
  4: optional list<MediaCommon.MediaKey> media_keys
}

struct AsyncSetAdditionalFieldsRequest {
  1: required tweet.Tweet additional_fields
  3: required i64 timestamp
  4: required i64 user_id
  2: optional AsyncWriteAction retry_action
}

struct AsyncSetRetweetVisibilityRequest {
  1: required i64 retweet_id
  // Whether to archive or unarchive(visible=true) the retweet_id edge in the RetweetsGraph.
  2: required bool visible
  3: required i64 src_id
  5: required i64 retweet_user_id
  6: required i64 source_tweet_user_id
  7: required i64 timestamp
  4: optional AsyncWriteAction retry_action
}

struct SetRetweetVisibilityRequest {
  1: required i64 retweet_id
  // Whether to archive or unarchive(visible=true) the retweet_id edge in the RetweetsGraph.
  2: required bool visible
}

struct AsyncEraseUserTweetsRequest {
  1: required i64 user_id
  3: required i64 flock_cursor
  4: required i64 start_timestamp
  5: required i64 tweet_count
}

struct AsyncDeleteRequest {
  4: required tweet.Tweet tweet
  11: required i64 timestamp
  2: optional user.User user
  9: optional i64 by_user_id
  12: optional tweet_audit.AuditDeleteTweet audit_passthrough
  13: optional i64 cascaded_from_tweet_id
  # if not specified, all async-delete actions are performed. if specified, only
  # the specified action is performed; this is used for retrying specific actions
  # that failed on a previous attempt.
  3: optional AsyncWriteAction retry_action
  5: bool delete_media = 1
  6: bool delete_retweets = 1
  8: bool scribe_for_audit = 1
  15: bool is_user_erasure = 0
  17: bool is_bounce_delete = 0
  18: optional bool is_last_quote_of_quoter
  19: optional bool is_admin_delete
}

struct AsyncUndeleteTweetRequest {
  1: required tweet.Tweet tweet
  3: required user.User user
  4: required i64 timestamp
  // the cacheable version of tweet from field 1
  12: required CachedTweet cached_tweet
  # 2: obsolete tweet.Tweet internal_tweet
  5: optional AsyncWriteAction retry_action
  6: optional i64 deleted_at
  7: optional tweet.Tweet source_tweet
  8: optional user.User source_user
  9: optional tweet.Tweet quoted_tweet
  10: optional user.User quoted_user
  11: optional i64 parent_user_id
  13: optional bool quoter_has_already_quoted_tweet
}

struct AsyncIncrFavCountRequest {
  1: required i64 tweet_id
  2: required i32 delta
}

struct AsyncIncrBookmarkCountRequest {
  1: required i64 tweet_id
  2: required i32 delta
}

struct AsyncDeleteAdditionalFieldsRequest {
  6: required i64 tweet_id
  7: required list<i16> field_ids
  4: required i64 timestamp
  5: required i64 user_id
  3: optional AsyncWriteAction retry_action
}

// Used for both tweet and user takedowns.
// user will be None for user takedowns because user is only used when scribe_for_audit or
// eventbus_enqueue are true, which is never the case for user takedown.
struct AsyncTakedownRequest {
  1: required tweet.Tweet tweet

  // Author of the tweet.  Used when scribe_for_audit or eventbus_enqueue are true which is the case
  // for tweet takedown but not user takedown.
  2: optional user.User user

  // This field is the resulting list of takedown country codes on the tweet after the
  // countries_to_add and countries_to_remove changes have been applied.
  13: list<withholding.TakedownReason> takedown_reasons = []

  // This field is the list of takedown reaons to add to the tweet.
  14: list<withholding.TakedownReason> reasons_to_add = []

  // This field is the list of takedown reasons to remove from the tweet.
  15: list<withholding.TakedownReason> reasons_to_remove = []

  // This field determines whether or not Tweetypie should write takedown audits
  // for this request to Guano.
  6: required bool scribe_for_audit

  // This field determines whether or not Tweetypie should enqueue a
  // TweetTakedownEvent to EventBus and Hosebird for this request.
  7: required bool eventbus_enqueue

  // This field is sent as part of the takedown audit that's written to Guano,
  // and is not persisted with the takedown itself.
  8: optional string audit_note

  // This field is the ID of the user who initiated the takedown.  It is used
  // when auditing the takedown in Guano.  If unset, it will be logged as -1.
  9: optional i64 by_user_id

  // This field is the host where the request originated or the remote IP that
  // is associated with the request.  It is used when auditing the takedown in
  // Guano.  If unset, it will be logged as "<unknown>".
  10: optional string host

  11: optional AsyncWriteAction retry_action
  12: required i64 timestamp
}

struct SetTweetUserTakedownRequest {
  1: required i64 tweet_id
  2: required bool has_takedown
  3: optional i64 user_id
}

enum DataErrorCause {
  UNKNOWN = 0
  // Returned on set_tweet_user_takedown when
  // the SetTweetUserTakedownRequest.user_id does not match the author
  // of the tweet identified by SetTweetUserTakedownRequest.tweet_id.
  USER_TWEET_RELATIONSHIP = 1
}

/**
 * DataError is returned for operations that perform data changes,
 * but encountered an inconsistency, and the operation cannot
 * be meaninfully performed.
 */
exception DataError {
  1: required string message
  2: optional DataErrorCause errorCause
}

struct ReplicatedDeleteAdditionalFieldsRequest {
  /** is a map for backwards compatibility, but will only contain a single tweet id */
  1: required map<i64, list<i16>> fields_map
}

struct CascadedDeleteTweetRequest {
  1: required i64 tweet_id
  2: required i64 cascaded_from_tweet_id
  3: optional tweet_audit.AuditDeleteTweet audit_passthrough
}

struct QuotedTweetDeleteRequest {
  1: i64 quoting_tweet_id
  2: i64 quoted_tweet_id
  3: i64 quoted_user_id
}

struct QuotedTweetTakedownRequest {
  1: i64 quoting_tweet_id
  2: i64 quoted_tweet_id
  3: i64 quoted_user_id
  4: list<string> takedown_country_codes = []
  5: list<withholding.TakedownReason> takedown_reasons = []
}

struct ReplicatedInsertTweet2Request {
  1: required CachedTweet cached_tweet
  // Used to check whether the same tweet has been quoted by a user.
  2: optional bool quoter_has_already_quoted_tweet
  3: optional InitialTweetUpdateRequest initialTweetUpdateRequest
}

struct ReplicatedDeleteTweet2Request {
  1: required tweet.Tweet tweet
  2: required bool is_erasure
  3: required bool is_bounce_delete
  4: optional bool is_last_quote_of_quoter
}

struct ReplicatedSetRetweetVisibilityRequest {
  1: required i64 src_id
  // Whether to archive or unarchive(visible=true) the retweet_id edge in the RetweetsGraph.
  2: required bool visible
}

struct ReplicatedUndeleteTweet2Request {
  1: required CachedTweet cached_tweet
  2: optional bool quoter_has_already_quoted_tweet
}

struct GetStoredTweetsOptions {
  1: bool bypass_visibility_filtering = 0
  2: optional i64 for_user_id
  3: list<FieldId> additional_field_ids = []
}

struct GetStoredTweetsRequest {
  1: required list<i64> tweet_ids
  2: optional GetStoredTweetsOptions options
}

struct GetStoredTweetsResult {
  1: required stored_tweet_info.StoredTweetInfo stored_tweet
}

struct GetStoredTweetsByUserOptions {
  1: bool bypass_visibility_filtering = 0
  2: bool set_for_user_id = 0
  3: optional i64 start_time_msec
  4: optional i64 end_time_msec
  5: optional i64 cursor
  6: bool start_from_oldest = 0
  7: list<FieldId> additional_field_ids = []
}

struct GetStoredTweetsByUserRequest {
  1: required i64 user_id
  2: optional GetStoredTweetsByUserOptions options
}

struct GetStoredTweetsByUserResult {
  1: required list<stored_tweet_info.StoredTweetInfo> stored_tweets
  2: optional i64 cursor
}

/* This is a request to update an initial tweet based on the creation of a edit tweet
 * initialTweetId: The tweet to be updated
 * editTweetId: The tweet being created, which is an edit of initialTweetId
 * selfPermalink: A self permalink for initialTweetId
 */
struct InitialTweetUpdateRequest {
  1: required i64 initialTweetId
  2: required i64 editTweetId
  3: optional tweet.ShortenedUrl selfPermalink
}

service TweetServiceInternal extends tweet_service.TweetService {

  /**
   * Performs the async portion of TweetService.erase_user_tweets.
   * Only tweetypie itself can call this.
   */
  void async_erase_user_tweets(1: AsyncEraseUserTweetsRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.post_tweet.
   * Only tweetypie itself can call this.
   */
  void async_insert(1: AsyncInsertRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.delete_tweets.
   * Only tweetypie itself can call this.
   */
  void async_delete(1: AsyncDeleteRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.undelete_tweet.
   * Only tweetypie itself can call this.
   */
  void async_undelete_tweet(1: AsyncUndeleteTweetRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.update_possibly_sensitive_tweet.
   * Only tweetypie itself can call this.
   */
  void async_update_possibly_sensitive_tweet(1: AsyncUpdatePossiblySensitiveTweetRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.incr_tweet_fav_count.
   * Only tweetypie itself can call this.
   */
  void async_incr_fav_count(1: AsyncIncrFavCountRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.incr_tweet_bookmark_count.
   * Only tweetypie itself can call this.
   */
  void async_incr_bookmark_count(1: AsyncIncrBookmarkCountRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.set_additional_fields.
   * Only tweetypie itself can call this.
   */
  void async_set_additional_fields(1: AsyncSetAdditionalFieldsRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetServiceInternal.set_retweet_visibility.
   * Only tweetypie itself can call this.
   */
  void async_set_retweet_visibility(1: AsyncSetRetweetVisibilityRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Set whether the specified retweet ID should be included in its source tweet's retweet count.
   * This endpoint is invoked from a tweetypie-daemon to adjust retweet counts for all tweets a
   * suspended or fraudulent (e.g. ROPO-'d) user has retweeted to disincentivize their false engagement.
   */
  void set_retweet_visibility(1: SetRetweetVisibilityRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.delete_additional_fields.
   * Only tweetypie itself can call this.
   */
  void async_delete_additional_fields(1: AsyncDeleteAdditionalFieldsRequest field_delete) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Performs the async portion of TweetService.takedown.
   * Only tweetypie itself can call this.
   */
  void async_takedown(1: AsyncTakedownRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Update the tweet's takedown fields when a user is taken down.
   * Only tweetypie's UserTakedownChange daemon can call this.
   */
  void set_tweet_user_takedown(1: SetTweetUserTakedownRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error,
    3: DataError data_error)

  /**
   * Cascade delete tweet is the logic for removing tweets that are detached
   * from their dependency which has been deleted. They are already filtered
   * out from serving, so this operation reconciles storage with the view
   * presented by Tweetypie.
   * This RPC call is delegated from daemons or batch jobs. Currently there
   * are two use-cases when this call is issued:
   * *   Deleting detached retweets after the source tweet was deleted.
   *     This is done through RetweetsDeletion daemon and the
   *     CleanupDetachedRetweets job.
   * *   Deleting edits of an initial tweet that has been deleted.
   *     This is done by CascadedEditedTweetDelete daemon.
   *     Note that, when serving the original delete request for an edit,
   *     the initial tweet is only deleted, which makes all edits hidden.
   */
  void cascaded_delete_tweet(1: CascadedDeleteTweetRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Update the timestamp of the user's most recent request to delete
   * location data on their tweets. This does not actually remove the
   * geo information from the user's tweets, but it will prevent the geo
   * information for this user's tweets from being returned by
   * Tweetypie.
   */
  void scrub_geo_update_user_timestamp(1: delete_location_data.DeleteLocationData request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Look up tweets quoting a tweet that has been deleted and enqueue a compliance event.
   * Only tweetypie's QuotedTweetDelete daemon can call this.
  **/
  void quoted_tweet_delete(1: QuotedTweetDeleteRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Look up tweets quoting a tweet that has been taken down and enqueue a compliance event.
   * Only tweetypie's QuotedTweetTakedown daemon can call this.
  **/
  void quoted_tweet_takedown(1: QuotedTweetTakedownRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates TweetService.get_tweet_counts from another cluster.
   */
  void replicated_get_tweet_counts(1: tweet_service.GetTweetCountsRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates TweetService.get_tweet_fields from another cluster.
   */
  void replicated_get_tweet_fields(1: tweet_service.GetTweetFieldsRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates TweetService.get_tweets from another cluster.
   */
  void replicated_get_tweets(1: tweet_service.GetTweetsRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.post_tweet InsertTweet event from another cluster.
   * Note: v1 version of this endpoint previously just took a Tweet which is why it was replaced
   */
  void replicated_insert_tweet2(1: ReplicatedInsertTweet2Request request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.delete_tweets DeleteTweet event from another cluster.
   */
  void replicated_delete_tweet2(1: ReplicatedDeleteTweet2Request request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.incr_tweet_fav_count event from another cluster.
   */
  void replicated_incr_fav_count(1: i64 tweet_id, 2: i32 delta) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.incr_tweet_bookmark_count event from another cluster.
   */
  void replicated_incr_bookmark_count(1: i64 tweet_id, 2: i32 delta) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetServiceInternal.set_retweet_visibility event from another cluster.
   */
  void replicated_set_retweet_visibility(1: ReplicatedSetRetweetVisibilityRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.scrub_geo from another cluster.
   */
  void replicated_scrub_geo(1: list<i64> tweet_ids) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.set_additional_fields event from another cluster.
   */
  void replicated_set_additional_fields(
    1: tweet_service.SetAdditionalFieldsRequest request
  ) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.delete_additional_fields event from another cluster.
   */
  void replicated_delete_additional_fields(
    1: ReplicatedDeleteAdditionalFieldsRequest request
  ) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.undelete_tweet event from another cluster.
   * Note: v1 version of this endpoint previously just took a Tweet which is why it was replaced
   */
  void replicated_undelete_tweet2(1: ReplicatedUndeleteTweet2Request request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.takedown event from another cluster.
   */
  void replicated_takedown(1: tweet.Tweet tweet) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Replicates a TweetService.update_possibly_sensitive_tweet event from another cluster.
   */
  void replicated_update_possibly_sensitive_tweet(1: tweet.Tweet tweet) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
   * Fetches hydrated Tweets and some metadata irrespective of the Tweets' state.
   */
  list<GetStoredTweetsResult> get_stored_tweets(1: GetStoredTweetsRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)

  /**
  * Fetches hydrated Tweets and some metadata for a particular user, irrespective of the Tweets'
  * state.
  */
  GetStoredTweetsByUserResult get_stored_tweets_by_user(1: GetStoredTweetsByUserRequest request) throws (
    1: exceptions.ClientError client_error,
    2: exceptions.ServerError server_error)
}
