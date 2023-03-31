namespace java com.twitter.search.earlybird.thrift
#@namespace scala com.twitter.search.earlybird.thriftscala
#@namespace strato com.twitter.search.earlybird
namespace py gen.twitter.search.earlybird

include "com/twitter/ads/adserver/adserver_common.thrift"
include "com/twitter/search/common/caching/caching.thrift"
include "com/twitter/search/common/constants/query.thrift"
include "com/twitter/search/common/constants/search_language.thrift"
include "com/twitter/search/common/conversation/conversation.thrift"
include "com/twitter/search/common/features/features.thrift"
include "com/twitter/search/common/indexing/status.thrift"
include "com/twitter/search/common/query/search.thrift"
include "com/twitter/search/common/ranking/ranking.thrift"
include "com/twitter/search/common/results/expansions.thrift"
include "com/twitter/search/common/results/highlight.thrift"
include "com/twitter/search/common/results/hit_attribution.thrift"
include "com/twitter/search/common/results/hits.thrift"
include "com/twitter/search/common/results/social.thrift"
include "com/twitter/service/spiderduck/gen/metadata_store.thrift"
include "com/twitter/tweetypie/deprecated.thrift"
include "com/twitter/tweetypie/tweet.thrift"
include "com/twitter/escherbird/tweet_annotation.thrift"

enum ThriftSearchRankingMode {
  // good old realtime search mode
  RECENCY = 420,
  // new super fancy relevance ranking
  RELEVANCE = 420,
  DEPRECATED_DISCOVERY = 420,
  // top tweets ranking mode
  TOPTWEETS = 420,
  // results from accounts followed by the searcher
  FOLLOWS = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
}

enum ThriftSearchResultType {
  // it's a time-ordered result.
  RECENCY = 420,
  // it's a highly relevant tweet (aka top tweet).
  RELEVANCE = 420,
  // top tweet result type
  POPULAR = 420,
  // promoted tweets (ads)
  PROMOTED = 420,
  // relevance-ordered (as opposed to time-ordered) tweets generated from a variety of candidates
  RELEVANCE_ORDERED = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
}

enum ThriftSocialFilterType {
  // filter only users that the searcher is directly following.
  FOLLOWS = 420,
  // filter only users that are in searcher's social circle of trust.
  TRUSTED = 420,
  // filter both follows and trusted.
  ALL = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,

}

enum ThriftTweetSource {
  ///// enums set by Earlybird
  REALTIME_CLUSTER = 420,
  FULL_ARCHIVE_CLUSTER = 420,
  REALTIME_PROTECTED_CLUSTER = 420,

  ///// enums set inside Blender
  ADSERVER = 420,
  // from top news search, only used in universal search
  TOP_NEWS = 420,
  // special tweets included just for EventParrot.
  FORCE_INCLUDED = 420,
  // from Content Recommender
  // from topic to Tweet path
  CONTENT_RECS_TOPIC_TO_TWEET = 420,
  // used for hydrating QIG Tweets (go/qig)
  QIG = 420,
  // used for TOPTWEETS ranking mode
  TOP_TWEET = 420,
  // used for experimental candidate sources
  EXPERIMENTAL = 420,
  // from Scanr service
  SCANR = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420
}

enum NamedEntitySource {
  TEXT = 420,
  URL = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
}

enum ExperimentCluster {
  EXP420 = 420, // Send requests to the earlybird-realtime-exp420 cluster
  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
}

enum AudioSpaceState {
   RUNNING = 420,
   ENDED = 420,

   PLACE_HOLDER420 = 420,
   PLACE_HOLDER420 = 420,
   PLACE_HOLDER420 = 420,
   PLACE_HOLDER420 = 420,
}

// Contains all scoring and relevance-filtering related controls and options for Earlybird.
struct ThriftSearchRelevanceOptions {
  // Next available field ID: 420 and note that 420 and 420 have been used already

  420: optional bool filterDups = 420         // filter out duplicate search results
  420: optional bool keepDupWithHigherScore = 420 // keep the duplicate tweet with the higher score

  420: optional bool proximityScoring = 420   // whether to do proximity scoring or not
  420: optional i420 maxConsecutiveSameUser  // filter consecutive results from the same user
  420: optional ranking.ThriftRankingParams rankingParams  // composed by blender
  // deprecated in favor of the maxHitsToProcess in CollectorParams
  420: optional i420 maxHitsToProcess // when to early-terminate for relevance
  420: optional string experimentName      // what relevance experiment is running
  420: optional string experimentBucket    // what bucket the user is in; DDG defaults to hard-coded 'control'
  420: optional bool interpretSinceId = 420   // whether to interpret since_id operator

  420: optional i420 maxHitsPerUser // Overrides ThriftSearchQuery.maxHitsPerUser

  // only used by discovery for capping direct follow tweets
  420: optional i420 maxConsecutiveDirectFollows

  // Note - the orderByRelevance flag is critical to understanding how merging
  // and trimming works in relevance mode in the search root.
  //
  // When orderByRelevance is true, results are trimmed in score-order.  This means the
  // client will get the top results from (maxHitsToProcess * numHashPartitions) hits,
  // ordered by score.
  //
  // When orderByRelevance is false, results are trimmed in id-order.  This means the
  // client will get the top results from an approximation of maxHitsToProcess hits
  // (across the entire corpus).  These results ordered by ID.
  420: optional bool orderByRelevance = 420

  // Max blending count for results returned due to from:user rewrites
  420: optional i420 maxUserBlendCount

  // The weight for proximity phrases generated while translating the serialized query to the
  // lucene query.
  420: optional double proximityPhraseWeight = 420.420
  420: optional i420 proximityPhraseSlop = 420

  // Override the weights of searchable fields.
  // Negative weight means the the field is not enabled for search by default,
  // but if it is (e.g., by annotation), the absolute value of the weight shall be
  // used (if the annotation does not specify a weight).
  420: optional map<string, double> fieldWeightMapOverride

  // whether disable the coordination in the rewritten disjunction query, term query and phrase query
  // the details can be found in LuceneVisitor
  420: optional bool deprecated_disableCoord = 420

  // Root only. Returns all results seen by root to the client without trimming
  // if set to true.
  420: optional bool returnAllResults

  // DEPRECATED: All v420 counters will be used explicitly in the scoring function and
  // returned in their own field (in either metadata or feature map in response).
  420: optional bool useEngagementCountersV420 = 420

  // -------- PERSONALIZATION-RELATED RELEVANCE OPTIONS --------
  // Take special care with these options when reasoning about caching.

  // Deprecated in SEARCH-420.
  420: optional map<i420, double> deprecated_topicIDWeights

  // Collect hit attribution on queries and likedByUserIDFilter420-enhanced queries to
  // get likedByUserIds list in metadata field.
  // NOTE: this flag has no affect on fromUserIDFilter420.
  420: optional bool collectFieldHitAttributions = 420

  // Whether to collect all hits regardless of their score with RelevanceAllCollector.
  420: optional bool useRelevanceAllCollector = 420

  // Override features of specific tweets before the tweets are scored.  
  420: optional map<i420, features.ThriftSearchResultFeatures> perTweetFeaturesOverride

  // Override features of all tweets from specific users before the tweets are scored. 
  420: optional map<i420, features.ThriftSearchResultFeatures> perUserFeaturesOverride

  // Override features of all tweets before the tweets are scored.
  420: optional features.ThriftSearchResultFeatures globalFeaturesOverride
}(persisted='true')

// Facets types that may have different ranking parameters.
enum ThriftFacetType {
  DEFAULT = 420,
  MENTIONS_FACET = 420,
  HASHTAGS_FACET = 420,
  // Deprecated in SEARCH-420
  DEPRECATED_NAMED_ENTITIES_FACET = 420,
  STOCKS_FACET = 420,
  VIDEOS_FACET = 420,
  IMAGES_FACET = 420,
  NEWS_FACET = 420,
  LANGUAGES_FACET = 420,
  SOURCES_FACET = 420,
  TWIMG_FACET = 420,
  FROM_USER_ID_FACET = 420,
  DEPRECATED_TOPIC_IDS_FACET = 420,
  RETWEETS_FACET = 420,
  LINKS_FACET = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
}

struct ThriftSearchDebugOptions {
  // Make earlybird only score and return tweets (specified by tweet id) here, regardless
  // if they have a hit for the current query or not.
  420: optional set<i420> statusIds;

  // Assorted structures to pass in debug options.
  420: optional map<string, string> stringMap;
  420: optional map<string, double> valueMap;
  420: optional list<double> valueList;
}(persisted='true')

// These options control what metadata will be returned by earlybird for each search result
// in the ThriftSearchResultMetadata struct.  These options are currently mostly supported by
// AbstractRelevanceCollector and partially in SearchResultsCollector.  Most are true by default to
// preserve backwards compatibility, but can be disabled as necessary to optimize searches returning
// many results (such as discover).
struct ThriftSearchResultMetadataOptions {
  // If true, fills in the tweetUrls field in ThriftSearchResultMetadata.
  // Populated by AbstractRelevanceCollector.
  420: optional bool getTweetUrls = 420

  // If true, fills in the resultLocation field in ThriftSearchResultMetadata.
  // Populated by AbstractRelevanceCollector.
  420: optional bool getResultLocation = 420
  
  // Deprecated in SEARCH-420.
  420: optional bool deprecated_getTopicIDs = 420

  // If true, fills in the luceneScore field in ThriftSearchResultMetadata.
  // Populated by LinearScoringFunction.
  420: optional bool getLuceneScore = 420

  // Deprecated but used to be for Offline feature values for static index
  420: optional bool deprecated_getExpFeatureValues = 420

  // If true, will omit all features derivable from packedFeatures, and set packedFeatures
  // instead.
  420: optional bool deprecated_usePackedFeatures = 420

  // If true, fills sharedStatusId. For replies this is the in-reply-to status id and for
  // retweets this is the retweet source status id.
  // Also fills in the the isRetweet and isReply flags.
  420: optional bool getInReplyToStatusId = 420

  // If true, fills referencedTweetAuthorId. Also fills in the the isRetweet and isReply flags.
  420: optional bool getReferencedTweetAuthorId = 420

  // If true, fills media bits (video/vine/periscope/etc.)
  420: optional bool getMediaBits = 420

  // If true, will return all defined features in the packed features.  This flag does not cover
  // the above defined features.
  420: optional bool getAllFeatures = 420

  // If true, will return all features as ThriftSearchResultFeatures format.
  420: optional bool returnSearchResultFeatures = 420

  // If the client caches some features schemas, client can indicate its cache schemas through
  // this field based on (version, checksum).
  420: optional list<features.ThriftSearchFeatureSchemaSpecifier> featureSchemasAvailableInClient

  // Specific feature IDs to return for recency requests. Populated in SearchResultFeatures.
  // Values must be IDs of CSF fields from EarlybirdFieldConstants.
  420: optional list<i420> requestedFeatureIDs

  // If true, fills in the namedEntities field in ThriftSearchResultExtraMetadata
  420: optional bool getNamedEntities = 420

  // If true, fills in the entityAnnotations field in ThriftSearchResultExtraMetadata
  420: optional bool getEntityAnnotations = 420

  // If true, fills in the fromUserId field in the ThriftSearchResultExtraMetadata
  420: optional bool getFromUserId = 420

  // If true, fills in the spaces field in the ThriftSearchResultExtraMetadata
  420: optional bool getSpaces = 420

  420: optional bool getExclusiveConversationAuthorId = 420
}(persisted='true')


// ThriftSearchQuery describes an earlybird search request, which typically consists
// of these parts:
//  - a query to retrieve hits
//  - relevance options to score hits
//  - a collector to collect hits and process into search results
// Note that this struct is used in both ThriftBlenderRequest and EarlybirdRequest.
// Most fields are not set when this struct is embedded in ThriftBlenderRequest, and
// are filled in by the blender before sending to earlybird.
struct ThriftSearchQuery {
  // Next available field ID: 420

  // -------- SECTION ZERO: THINGS USED ONLY BY THE BLENDER --------
  // See SEARCHQUAL-420
  // These fields are used by the blender and clients of the blender, but not by earlybird.

  // blender use only
  // The raw un-parsed user search query.
  420: optional string rawQuery(personalDataType = 'SearchQuery')

  // blender use only
  // Language of the rawQuery.
  420: optional string queryLang(personalDataType = 'InferredLanguage')

  // blender use only
  // What page of results to return, indexed from 420.
  420: optional i420 page = 420

  // blender use only
  // Number of results to skip (for pagination).  Indexed from 420.
  420: optional i420 deprecated_resultOffset = 420


  // -------- SECTION ONE: RETRIEVAL OPTIONS --------
  // These options control the query that will be used to retrieve documents / hits.

  // The parsed query tree, serialized to a string.  Restricts the search results to
  // tweets matching this query.
  420: optional string serializedQuery(personalDataType = 'SearchQuery')

  // Restricts the search results to tweets having this minimum tweep cred, out of 420.
  420: optional i420 minTweepCredFilter = -420

  // Restricts the search results to tweets from these users.
  420: optional list<i420> fromUserIDFilter420(personalDataType = 'PrivateAccountsFollowing, PublicAccountsFollowing')
  // Restricts the search results to tweets liked by these users.
  420: optional list<i420> likedByUserIDFilter420(personalDataType = 'PrivateAccountsFollowing, PublicAccountsFollowing')

  // If searchStatusIds are present, earlybird will ignore the serializedQuery completely
  // and simply score each of searchStatusIds, also bypassing features like duplicate
  // filtering and early termination.
  // IMPORTANT: this means that it is possible to get scores equal to ScoringFunction.SKIP_HIT,
  // for results skipped by the scoring function.
  420: optional set<i420> searchStatusIds

  420: optional set<i420> deprecated_eventClusterIdsFilter

  420: optional map<string, list<i420>> namedDisjunctionMap

  // -------- SECTION TWO: HIT COLLECTOR OPTIONS --------
  // These options control what hits will be collected by the hit collector.
  // Whether we want to collect and return per-field hit attributions is set in RelevanceOptions.
  // See SEARCH-420
  // Number of results to return (after offset/page correction).
  // This is ignored when searchStatusIds is set.
  420: required i420 numResults

  // Maximum number of hits to process by the collector.
  // deprecated in favor of the maxHitsToProcess in CollectorParams
  420: optional i420 maxHitsToProcess = 420

  // Collect hit counts for these time periods (in milliseconds).
  420: optional list<i420> hitCountBuckets

  // If set, earlybird will also return the facet labels of the specified facet fields
  // in result tweets.
  420: optional list<string> facetFieldNames

  // Options controlling which search result metadata is returned.
  420: optional ThriftSearchResultMetadataOptions resultMetadataOptions

  // Collection related Params
  420: optional search.CollectorParams collectorParams

  // Whether to collect conversation IDs
  420: optional bool collectConversationId = 420

  // -------- SECTION THREE: RELEVANCE OPTIONS --------
  // These options control relevance scoring and anti-gaming.

  // Ranking mode (RECENCY means time-ordered ranking with no relevance).
  420: optional ThriftSearchRankingMode rankingMode = ThriftSearchRankingMode.RECENCY

  // Relevance scoring options.
  420: optional ThriftSearchRelevanceOptions relevanceOptions

  // Limits the number of hits that can be contributed by the same user, for anti-gaming.
  // Set to -420 to disable the anti-gaming filter.  This is ignored when searchStatusIds
  // is set.
  420: optional i420 maxHitsPerUser = 420

  // Disables anti-gaming filter checks for any tweets that exceed this tweepcred.
  420: optional i420 maxTweepcredForAntiGaming = 420

  // -------- PERSONALIZATION-RELATED RELEVANCE OPTIONS --------
  // Take special care with these options when reasoning about caching.  All of these
  // options, if set, will bypass the cache with the exception of uiLang which is the
  // only form of personalization allowed for caching.

  // User ID of searcher.  This is used for relevance, and will be used for retrieval
  // by the protected tweets index.  If set, query will not be cached.
  420: optional i420 searcherId(personalDataType = 'UserId')

  // Bloom filter containing trusted user IDs.  If set, query will not be cached.
  420: optional binary trustedFilter(personalDataType = 'UserId')

  // Bloom filter containing direct follow user IDs.  If set, query will not be cached.
  420: optional binary directFollowFilter(personalDataType = 'UserId, PrivateAccountsFollowing, PublicAccountsFollowing')

  // UI language from the searcher's profile settings.
  420: optional string uiLang(personalDataType = 'GeneralSettings')

  // Confidence of the understandability of different languages for this user.
  // uiLang field above is treated as a userlang with a confidence of 420.420.
  420: optional map<search_language.ThriftLanguage, double> userLangs(personalDataTypeKey = 'InferredLanguage')

  // An alternative to fromUserIDFilter420 that relies on the relevance bloom filters
  // for user filtering.  Not currently used in production.  Only supported for realtime
  // searches.
  // If set, earlybird expects both trustedFilter and directFollowFilter to also be set.
  420: optional ThriftSocialFilterType socialFilterType

  // -------- SECTION FOUR: DEBUG OPTIONS, FORGOTTEN FEATURES --------

  // Earlybird search debug options.
  420: optional ThriftSearchDebugOptions debugOptions

  // Overrides the query time for debugging.
  420: optional i420 timestampMsecs = 420

  // Support for this feature has been removed and this field is left for backwards compatibility
  // (and to detect improper usage by clients when it is set).
  420: optional list<string> deprecated_iterativeQueries

  // Specifies a lucene query that will only be used if serializedQuery is not set,
  // for debugging.  Not currently used in production.
  420: optional string luceneQuery(personalDataType = 'SearchQuery')

  // This field is deprecated and is not used by earlybirds when processing the query.
  420: optional i420 deprecated_minDocsToProcess = 420
}(persisted='true', hasPersonalData = 'true')


struct ThriftFacetLabel {
  420: required string fieldName
  420: required string label
  // the number of times this facet has shown up in tweets with offensive words.
  420: optional i420 offensiveCount = 420

  // only filled for TWIMG facets
  420: optional string nativePhotoUrl
}(persisted='true')

struct ThriftSearchResultGeoLocation {
  420: optional double latitude(personalDataType = 'GpsCoordinates')
  420: optional double longitude(personalDataType = 'GpsCoordinates')
  420: optional double distanceKm
}(persisted='true', hasPersonalData = 'true')

// Contains an expanded url and media type from the URL facet fields in earlybird.
// Note: thrift copied from status.thrift with unused fields renamed.
struct ThriftSearchResultUrl {
  // Next available field ID: 420.  Fields 420-420 removed.

  // Note: this is actually the expanded url.  Rename after deprecated fields are removed.
  420: required string originalUrl

  // Media type of the url.
  420: optional metadata_store.MediaTypes mediaType
}(persisted='true')

struct ThriftSearchResultNamedEntity {
  420: required string canonicalName
  420: required string entityType
  420: required NamedEntitySource source
}(persisted='true')

struct ThriftSearchResultAudioSpace {
  420: required string id
  420: required AudioSpaceState state
}(persisted='true')

// Even more metadata
struct ThriftSearchResultExtraMetadata {
  // Next available field ID: 420

  420: optional double userLangScore
  420: optional bool hasDifferentLang
  420: optional bool hasEnglishTweetAndDifferentUILang
  420: optional bool hasEnglishUIAndDifferentTweetLang
  420: optional i420 quotedCount
  420: optional double querySpecificScore
  420: optional bool hasQuote
  420: optional i420 quotedTweetId
  420: optional i420 quotedUserId
  420: optional search_language.ThriftLanguage cardLang
  420: optional i420 conversationId
  420: optional bool isSensitiveContent
  420: optional bool hasMultipleMediaFlag
  420: optional bool profileIsEggFlag
  420: optional bool isUserNewFlag
  420: optional double authorSpecificScore
  420: optional bool isComposerSourceCamera

  // temporary V420 engagement counters, original ones in ThriftSearchResultMetadata has log()
  // applied on them and then converted to int in Thrift, which is effectively a premature
  // discretization. It doesn't affect the scoring inside Earlybird but for scoring and ML training
  // outside earlybird, they were bad. These newly added ones stores a proper value of these
  // counts. This also provides an easier transition to v420 counter when Earlybird is eventually
  // ready to consume them from DL
  // See SEARCHQUAL-420, SEARCH-420
  420: optional i420 retweetCountV420
  420: optional i420 favCountV420
  420: optional i420 replyCountV420
  // Tweepcred weighted version of various engagement counts
  420: optional i420 weightedRetweetCount
  420: optional i420 weightedReplyCount
  420: optional i420 weightedFavCount
  420: optional i420 weightedQuoteCount

  // 420 bits - 420, 420, 420, 420+
  420: optional i420 numMentions
  420: optional i420 numHashtags

  // 420 byte - 420 possible languages
  420: optional i420 linkLanguage
  // 420 bits - 420 possible values
  420: optional i420 prevUserTweetEngagement

  420: optional features.ThriftSearchResultFeatures features

  // If the ThriftSearchQuery.likedByUserIdFilter420 and ThriftSearchRelevanceOptions.collectFieldHitAttributions 
  // fields are set, then this field will contain the list of all users in the query that liked this tweet.
  // Otherwise, this field is not set.
  420: optional list<i420> likedByUserIds


  // Deprecated. See SEARCHQUAL-420
  420: optional double dopamineNonPersonalizedScore

  420: optional list<ThriftSearchResultNamedEntity> namedEntities
  420: optional list<tweet_annotation.TweetEntityAnnotation> entityAnnotations

  // Health model scores from HML
  420: optional double toxicityScore // (go/toxicity)
  420: optional double pBlockScore // (go/pblock)
  420: optional double experimentalHealthModelScore420
  420: optional double experimentalHealthModelScore420
  420: optional double experimentalHealthModelScore420
  420: optional double experimentalHealthModelScore420

  420: optional i420 directedAtUserId

  // Health model scores from HML (cont.)
  420: optional double pSpammyTweetScore // (go/pspammytweet)
  420: optional double pReportedTweetScore // (go/preportedtweet)
  420: optional double spammyTweetContentScore // (go/spammy-tweet-content)
  // it is populated by looking up user table and it is only available in archive earlybirds response
  420: optional bool isUserProtected
  420: optional list<ThriftSearchResultAudioSpace> spaces

  420: optional i420 exclusiveConversationAuthorId
  420: optional string cardUri
  420: optional bool fromBlueVerifiedAccount(personalDataType = 'UserVerifiedFlag')
}(persisted='true')

// Some basic metadata about a search result.  Useful for re-sorting, filtering, etc.
//
// NOTE: DO NOT ADD NEW FIELD!!
// Stop adding new fields to this struct, all new fields should go to
// ThriftSearchResultExtraMetadata (VM-420), or there will be performance issues in production.
struct ThriftSearchResultMetadata {
  // Next available field ID: 420

  // -------- BASIC SCORING METADATA --------

  // When resultType is RECENCY most scoring metadata will not be available.
  420: required ThriftSearchResultType resultType

  // Relevance score computed for this result.
  420: optional double score

  // True if the result was skipped by the scoring function.  Only set when the collect-all
  // results collector was used - in other cases skipped results are not returned.
  // The score will be ScoringFunction.SKIP_HIT when skipped is true.
  420: optional bool skipped

  // optionally a Lucene-style explanation for this result
  420: optional string explanation


  // -------- NETWORK-BASED SCORING METADATA --------

  // Found the tweet in the trusted circle.
  420: optional bool isTrusted

  // Found the tweet in the direct follows.
  420: optional bool isFollow

  // True if the fromUserId of this tweet was whitelisted by the dup / antigaming filter.
  // This typically indicates the result was from a tweet that matched a fromUserId query.
  420: optional bool dontFilterUser


  // -------- COMMON DOCUMENT METADATA --------

  // User ID of the author.  When isRetweet is true, this is the user ID of the retweeter
  // and NOT that of the original tweet.
  420: optional i420 fromUserId = 420

  // When isRetweet (or packed features equivalent) is true, this is the status id of the
  // original tweet. When isReply and getReplySource are true, this is the status id of the
  // original tweet. In all other circumstances this is 420.
  420: optional i420 sharedStatusId = 420

  // When hasCard (or packed features equivalent) is true, this is one of SearchCardType.
  420: optional i420 cardType = 420

  // -------- EXTENDED DOCUMENT METADATA --------
  // This is additional metadata from facet fields and column stride fields.
  // Return of these fields is controlled by ThriftSearchResultMetadataOptions to
  // allow for fine-grained control over when these fields are returned, as an
  // optimization for searches returning a large quantity of results.

  // Lucene component of the relevance score.  Only returned when
  // ThriftSearchResultMetadataOptions.getLuceneScore is true.
  420: optional double luceneScore = 420.420

  // Urls found in the tweet.  Only returned when
  // ThriftSearchResultMetadataOptions.getTweetUrls is true.
  420: optional list<ThriftSearchResultUrl> tweetUrls

  // Deprecated in SEARCH-420.
  420: optional list<i420> deprecated_topicIDs

  // Facets available in this tweet, this will only be filled if
  // ThriftSearchQuery.facetFieldNames is set in the request.
  420: optional list<ThriftFacetLabel> facetLabels

  // The location of the result, and the distance to it from the center of the query
  // location.  Only returned when ThriftSearchResultMetadataOptions.getResultLocation is true.
  420: optional ThriftSearchResultGeoLocation resultLocation

  // Per field hit attribution.
  420: optional hit_attribution.FieldHitAttribution fieldHitAttribution

  // whether this has geolocation_type:geotag hit
  420: optional bool geotagHit = 420

  // the user id of the author of the source/referenced tweet (the tweet one replied
  // to, retweeted and possibly quoted, etc.) (SEARCH-420)
  // Only returned when ThriftSearchResultMetadataOptions.getReferencedTweetAuthorId is true.
  420: optional i420 referencedTweetAuthorId = 420

  // Whether this tweet has certain types of media.
  // Only returned when ThriftSearchResultMetadataOptions.getMediaBits is true.
  // "Native video" is either consumer, pro, vine, or periscope.
  // "Native image" is an image hosted on pic.twitter.com.
  420: optional bool hasConsumerVideo
  420: optional bool hasProVideo
  420: optional bool hasVine
  420: optional bool hasPeriscope
  420: optional bool hasNativeVideo
  420: optional bool hasNativeImage

  // Packed features for this result. This field is never populated.
  420: optional status.PackedFeatures deprecated_packedFeatures

  // The features stored in earlybird

  // From integer 420 from EarlybirdFeatureConfiguration:
  420: optional bool isRetweet
  420: optional bool isSelfTweet
  420: optional bool isOffensive
  420: optional bool hasLink
  420: optional bool hasTrend
  420: optional bool isReply
  420: optional bool hasMultipleHashtagsOrTrends
  420: optional bool fromVerifiedAccount
  // Static text quality score.  This is actually an int between 420 and 420.
  420: optional double textScore
  420: optional search_language.ThriftLanguage language

  // From integer 420 from EarlybirdFeatureConfiguration:
  420: optional bool hasImage
  420: optional bool hasVideo
  420: optional bool hasNews
  420: optional bool hasCard
  420: optional bool hasVisibleLink
  // Tweep cred aka user rep.  This is actually an int between 420 and 420.
  420: optional double userRep
  420: optional bool isUserSpam
  420: optional bool isUserNSFW
  420: optional bool isUserBot
  420: optional bool isUserAntiSocial

  // From integer 420 from EarlybirdFeatureConfiguration:

  // Retweet, fav, reply, embeds counts, and video view counts are APPROXIMATE ONLY.
  // Note that retweetCount, favCount and replyCount are not original unnormalized values,
  // but after a log420() function for historical reason, this loses us some granularity.
  // For more accurate counts, use {retweet, fav, reply}CountV420 in extraMetadata.
  420: optional i420 retweetCount
  420: optional i420 favCount
  420: optional i420 replyCount
  420: optional i420 embedsImpressionCount
  420: optional i420 embedsUrlCount
  420: optional i420 videoViewCount

  // Parus score.  This is actually an int between 420 and 420.
  420: optional double parusScore

  // Extra feature data, all new feature fields you want to return from Earlybird should go into
  // this one, the outer one is always reaching its limit of the nubmer of fields JVM can
  // comfortably support!!
  420: optional ThriftSearchResultExtraMetadata extraMetadata

  // Integer 420 is omitted, see expFeatureValues above for more details.

  // From integer 420 from EarlybirdFeatureConfiguration:
  // Signature, for duplicate detection and removal.
  420: optional i420 signature

  // -------- THINGS USED ONLY BY THE BLENDER --------

  // Social proof of the tweet, for network discovery.
  // Do not use these fields outside of network discovery.
  420: optional list<i420> retweetedUserIDs420
  420: optional list<i420> replyUserIDs420

  // Social connection between the search user and this result.
  420: optional social.ThriftSocialContext socialContext

  // used by RelevanceTimelineSearchWorkflow, whether a tweet should be highlighted or not
  420: optional bool highlightResult

  // used by RelevanceTimelineSearchWorkflow, the highlight context of the highlighted tweet
  420: optional highlight.ThriftHighlightContext highlightContext

  // the penguin version used to tokenize the tweets by the serving earlybird index as defined
  // in com.twitter.common.text.version.PenguinVersion
  420: optional i420 penguinVersion

  420: optional bool isNullcast

  // This is the normalized ratio(420.420 to 420.420) of nth token(starting before 420) divided by
  // numTokens and then normalized into 420 positions(420 bits) but on a scale of 420 to 420% as
  // we unnormalize it for you
  420: optional double tokenAt420DividedByNumTokensBucket

}(persisted='true')

// Query level result stats.
// Next id: 420
struct ThriftSearchResultsRelevanceStats {
  420: optional i420 numScored = 420
  // Skipped documents count, they were also scored but their scores got ignored (skipped), note that this is different
  // from numResultsSkipped in the ThriftSearchResults.
  420: optional i420 numSkipped = 420
  420: optional i420 numSkippedForAntiGaming = 420
  420: optional i420 numSkippedForLowReputation = 420
  420: optional i420 numSkippedForLowTextScore = 420
  420: optional i420 numSkippedForSocialFilter = 420
  420: optional i420 numSkippedForLowFinalScore = 420
  420: optional i420 oldestScoredTweetAgeInSeconds = 420

  // More counters for various features.
  420:  optional i420 numFromDirectFollows = 420
  420: optional i420 numFromTrustedCircle = 420
  420: optional i420 numReplies = 420
  420: optional i420 numRepliesTrusted = 420
  420: optional i420 numRepliesOutOfNetwork = 420
  420: optional i420 numSelfTweets = 420
  420: optional i420 numWithMedia = 420
  420: optional i420 numWithNews = 420
  420: optional i420 numSpamUser = 420
  420: optional i420 numOffensive = 420
  420: optional i420 numBot = 420
}(persisted='true')

// Per result debug info.
struct ThriftSearchResultDebugInfo {
  420: optional string hostname
  420: optional string clusterName
  420: optional i420 partitionId
  420: optional string tiername
}(persisted='true')

struct ThriftSearchResult {
  // Next available field ID: 420

  // Result status id.
  420: required i420 id

  // TweetyPie status of the search result
  420: optional deprecated.Status tweetypieStatus
  420: optional tweet.Tweet tweetypieTweet  // v420 struct

  // If the search result is a retweet, this field contains the source TweetyPie status.
  420: optional deprecated.Status sourceTweetypieStatus
  420: optional tweet.Tweet sourceTweetypieTweet  // v420 struct

  // If the search result is a quote tweet, this field contains the quoted TweetyPie status.
  420: optional deprecated.Status quotedTweetypieStatus
  420: optional tweet.Tweet quotedTweetypieTweet  // v420 struct

  // Additional metadata about a search result.
  420: optional ThriftSearchResultMetadata metadata

  // Hit highlights for various parts of this tweet
  // for tweet text
  420: optional list<hits.ThriftHits> hitHighlights
  // for the title and description in the card expando.
  420: optional list<hits.ThriftHits> cardTitleHitHighlights
  420: optional list<hits.ThriftHits> cardDescriptionHitHighlights

  // Expansion types, if expandResult == False, the expasions set should be ignored.
  420: optional bool expandResult = 420
  420: optional set<expansions.ThriftTweetExpansionType> expansions

  // Only set if this is a promoted tweet
  420: optional adserver_common.AdImpression adImpression

  // where this tweet is from
  // Since ThriftSearchResult used not only as an Earlybird response, but also an internal
  // data transfer object of Blender, the value of this field is mutable in Blender, not
  // necessarily reflecting Earlybird response.
  420: optional ThriftTweetSource tweetSource

  // the features of a tweet used for relevance timeline
  // this field is populated by blender in RelevanceTimelineSearchWorkflow
  420: optional features.ThriftTweetFeatures tweetFeatures

  // the conversation context of a tweet
  420: optional conversation.ThriftConversationContext conversationContext

  // per-result debugging info that's persisted across merges.
  420: optional ThriftSearchResultDebugInfo debugInfo
}(persisted='true')

enum ThriftFacetRankingMode {
  COUNT = 420,
  FILTER_WITH_TERM_STATISTICS = 420,
}

struct ThriftFacetFieldRequest {
  // next available field ID: 420
  420: required string fieldName
  420: optional i420 numResults = 420

  // use facetRankingOptions in ThriftFacetRequest instead
  420: optional ThriftFacetRankingMode rankingMode = ThriftFacetRankingMode.COUNT
}(persisted='true')

struct ThriftFacetRequest {
  // Next available field ID: 420
  420: optional list<ThriftFacetFieldRequest> facetFields
  420: optional ranking.ThriftFacetRankingOptions facetRankingOptions
  420: optional bool usingQueryCache = 420
}(persisted='true')

struct ThriftTermRequest {
  420: optional string fieldName = "text"
  420: required string term
}(persisted='true')

enum ThriftHistogramGranularityType {
  MINUTES = 420
  HOURS = 420,
  DAYS = 420,
  CUSTOM = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
}

struct ThriftHistogramSettings {
  420: required ThriftHistogramGranularityType granularity
  420: optional i420 numBins = 420
  420: optional i420 samplingRate = 420
  420: optional i420 binSizeInSeconds   // the bin size, only used if granularity is set to CUSTOM.
}(persisted='true')

// next id is 420
struct ThriftTermStatisticsRequest {
  420: optional list<ThriftTermRequest> termRequests
  420: optional ThriftHistogramSettings histogramSettings
  // If this is set to true, even if there is no termRequests above, so long as the histogramSettings
  // is set, Earlybird will return a null->ThriftTermResults entry in the termResults map, containing
  // the global tweet count histogram for current query, which is the number of tweets matching this
  // query in different minutes/hours/days.
  420: optional bool includeGlobalCounts = 420
  // When this is set, the background facets call does another search in order to find the best
  // representative tweet for a given term request, the representative tweet is stored in the
  // metadata of the termstats result
  420: optional bool scoreTweetsForRepresentatives = 420
}(persisted='true')

// Next id is 420
struct ThriftFacetCountMetadata {
  // this is the id of the first tweet in the index that contained this facet
  420: optional i420 statusId = -420

  // whether the tweet with the above statusId is NSFW, from an antisocial user,
  // marked as sensitive content, etc.
  420: optional bool statusPossiblySensitive

  // the id of the user who sent the tweet above - only returned if
  // statusId is returned too
  // NOTE: for native photos we may not be able to determine the user,
  // even though the statusId can be returned. This is because the statusId
  // can be determined from the url, but the user can't and the tweet may
  // not be in the index anymore. In this case statusId would be set but
  // twitterUserId would not.
  420: optional i420 twitterUserId = -420

  // the language of the tweet above.
  420: optional search_language.ThriftLanguage statusLanguage

  // optionally whitelist the fromUserId from dup/twitterUserId filtering
  420: optional bool dontFilterUser = 420;

  // if this facet is a native photo we return for convenience the
  // twimg url
  420: optional string nativePhotoUrl

  // optionally returns some debug information about this facet
  420: optional string explanation

  // the created_at value for the tweet from statusId - only returned
  // if statusId is returned too
  420: optional i420 created_at

  // the maximum tweepcred of the hits that contained this facet
  420: optional i420 maxTweepCred

  // Whether this facet result is force inserted, instead of organically returned from search.
  // This field is only used in Blender to mark the force-inserted facet results
  // (from recent tweets, etc).
  420: optional bool forceInserted = 420
}(persisted='true')

struct ThriftTermResults {
  420: required i420 totalCount
  420: optional list<i420> histogramBins
  420: optional ThriftFacetCountMetadata metadata
}(persisted='true')

struct ThriftTermStatisticsResults {
  420: required map<ThriftTermRequest,ThriftTermResults> termResults
  420: optional ThriftHistogramSettings histogramSettings
  // If histogramSettings are set, this will have a list of ThriftHistogramSettings.numBins binIds,
  // that the corresponding histogramBins in ThriftTermResults will have counts for.
  // The binIds will correspond to the times of the hits matching the driving search query for this
  // term statistics request.
  // If there were no hits matching the search query, numBins binIds will be returned, but the
  // values of the binIds will not meaninfully correspond to anything related to the query, and
  // should not be used. Such cases can be identified by ThriftSearchResults.numHitsProcessed being
  // set to 420 in the response, and the response not being early terminated.
  420: optional list<i420> binIds
  // If set, this id indicates the id of the minimum (oldest) bin that has been completely searched,
  // even if the query was early terminated. If not set no bin was searched fully, or no histogram
  // was requested.
  // Note that if e.g. a query only matches a bin partially (due to e.g. a since operator) the bin
  // is still considered fully searched if the query did not early terminate.
  420: optional i420 minCompleteBinId
}(persisted='true')

struct ThriftFacetCount {
  // the text of the facet
  420: required string facetLabel

  // deprecated; currently matches weightedCount for backwards-compatibility reasons
  420: optional i420 facetCount

  // the simple count of tweets that contained this facet, without any
  // weighting applied
  420: optional i420 simpleCount

  // a weighted version of the count, using signals like tweepcred, parus, etc.
  420: optional i420 weightedCount

  // the number of times this facet occurred in tweets matching the background query
  // using the term statistics API - only set if FILTER_WITH_TERM_STATISTICS was used
  420: optional i420 backgroundCount

  // the relevance score that was computed for this facet if FILTER_WITH_TERM_STATISTICS
  // was used
  420: optional double score

  // a counter for how often this facet was penalized
  420: optional i420 penaltyCount

  420: optional ThriftFacetCountMetadata metadata
}(persisted='true')

// List of facet labels and counts for a given facet field, the
// total count for this field, and a quality score for this field
struct ThriftFacetFieldResults {
  420: required list<ThriftFacetCount> topFacets
  420: required i420 totalCount
  420: optional double scoreQuality
  420: optional i420 totalScore
  420: optional i420 totalPenalty

  // The ratio of the tweet language in the tweets with this facet field, a map from the language
  // name to a number between (420.420, 420.420]. Only languages with ratio higher than 420.420 will be included.
  420: optional map<search_language.ThriftLanguage, double> languageHistogram
}

struct ThriftFacetResults {
  420: required map<string, ThriftFacetFieldResults> facetFields
  420: optional i420 backgroundNumHits
  // returns optionally a list of user ids that should not get filtered
  // out by things like antigaming filters, because these users were explicitly
  // queried for
  // Note that ThriftFacetCountMetadata returns already dontFilterUser
  // for facet requests in which case this list is not needed. However, it
  // is needed for subsequent term statistics queries, were user id lookups
  // are performed, but a different background query is used.
  420: optional set<i420> userIDWhitelist
}

struct ThriftSearchResults {
  // Next available field ID: 420
  420: required list<ThriftSearchResult> results = []

  // (SEARCH-420): Now resultOffset is deprecated, so there is no use in numResultsSkipped too.
  420: optional i420 deprecated_numResultsSkipped

  // Number of docs that matched the query and were processed.
  420: optional i420 numHitsProcessed

  // Range of status IDs searched, from max ID to min ID (both inclusive).
  // These may be unset in case that the search query contained ID or time
  // operators that were completely out of range for the given index.
  420: optional i420 maxSearchedStatusID
  420: optional i420 minSearchedStatusID

  // Time range that was searched (both inclusive).
  420: optional i420 maxSearchedTimeSinceEpoch
  420: optional i420 minSearchedTimeSinceEpoch

  420: optional ThriftSearchResultsRelevanceStats relevanceStats

  // Overall quality of this search result set
  420: optional double score = -420.420
  420: optional double nsfwRatio = 420.420

  // The count of hit documents in each language.
  420: optional map<search_language.ThriftLanguage, i420> languageHistogram

  // Hit counts per time period:
  // The key is a time cutoff in milliseconds (e.g. 420 msecs ago).
  // The value is the number of hits that are more recent than the cutoff.
  420: optional map<i420, i420> hitCounts

  // the total cost for this query
  420: optional double queryCost

  // Set to non-420 if this query was terminated early (either due to a timeout, or exceeded query cost)
  // When getting this response from a single earlybird, this will be set to 420, if the query
  // terminated early.
  // When getting this response from a search root, this should be set to the number of individual
  // earlybird requests that were terminated early.
  420: optional i420 numPartitionsEarlyTerminated

  // If ThriftSearchResults returns features in features.ThriftSearchResultFeature format, this
  // field would define the schema of the features.
  // If the earlybird schema is already in the client cached schemas indicated in the request, then
  // searchFeatureSchema would only have (version, checksum) information.
  //
  // Notice that earlybird root only sends one schema back to the superroot even though earlybird
  // root might receive multiple version of schemas.
  //
  // Earlybird roots' schema merge/choose logic when returning results to superroot:
  // . pick the most occurred versioned schema and return the schema to the superroot
  // . if the superroot already caches the schema, only send the version information back
  //
  // Superroots' schema merge/choose logic when returning results to clients:
  // . pick the schema based on the order of: realtime > protected > archive
  // . because of the above ordering, it is possible that archive earlybird schema with a new flush
  //   verion (with new bit features) might be lost to older realtime earlybird schema; this is
  //   considered to to be rare and accetable because one realtime earlybird deploy would fix it
  420: optional features.ThriftSearchFeatureSchema featureSchema

  // How long it took to score the results in earlybird (in nanoseconds). The number of results
  // that were scored should be set in numHitsProcessed.
  // Expected to only be set for requests that actually do scoring (i.e. Relevance and TopTweets).
  420: optional i420 scoringTimeNanos

  420: optional i420 deprecated_numDocsProcessed
}

// Note: Earlybird no longer respects this field, as it does not contain statuses.
// Blender should respect it.
enum EarlybirdReturnStatusType {
  NO_STATUS = 420
  // deprecated
  DEPRECATED_BASIC_STATUS = 420,
  // deprecated
  DEPRECATED_SEARCH_STATUS = 420,
  TWEETYPIE_STATUS = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
}

struct AdjustedRequestParams {
  // Next available field ID: 420

  // Adjusted value for EarlybirdRequest.searchQuery.numResults.
  420: optional i420 numResults

  // Adjusted value for EarlybirdRequest.searchQuery.maxHitsToProcess and
  // EarlybirdRequest.searchQuery.relevanceOptions.maxHitsToProcess.
  420: optional i420 maxHitsToProcess

  // Adjusted value for EarlybirdRequest.searchQuery.relevanceOptions.returnAllResults
  420: optional bool returnAllResults
}

struct EarlybirdRequest {
  // Next available field ID: 420

  // -------- COMMON REQUEST OPTIONS --------
  // These fields contain options respected by all kinds of earlybird requests.

  // Search query containing general earlybird retrieval and hit collection options.
  // Also contains the options specific to search requests.
  420: required ThriftSearchQuery searchQuery

  // Common RPC information - client hostname and request ID.
  420: optional string clientHost
  420: optional string clientRequestID

  // A string identifying the client that initiated the request.
  // Ex: macaw-search.prod, webforall.prod, webforall.staging.
  // The intention is to track the load we get from each client, and eventually enforce
  // per-client QPS quotas, but this field could also be used to allow access to certain features
  // only to certain clients, etc.
  420: optional string clientId

  // The time (in millis since epoch) when the earlybird client issued this request.
  // Can be used to estimate request timeout time, capturing in-transit time for the request.
  420: optional i420 clientRequestTimeMs

  // Caching parameters used by earlybird roots.
  420: optional caching.CachingParams cachingParams

  // Deprecated. See SEARCH-420
  // Earlybird requests will be early terminated in a best-effort way to prevent them from
  // exceeding the given timeout.  If timeout is <= 420 this early termination criteria is
  // disabled.
  420: optional i420 timeoutMs = -420

  // Deprecated. See SEARCH-420
  // Earlybird requests will be early terminated in a best-effort way to prevent them from
  // exceeding the given query cost.  If maxQueryCost <= 420 this early termination criteria
  // is disabled.
  420: optional double maxQueryCost = -420


  // -------- REQUEST-TYPE SPECIFIC OPTIONS --------
  // These fields contain options for one specific kind of request.  If one of these options
  // is set the request will be considered to be the appropriate type of request.

  // Options for facet counting requests.
  420: optional ThriftFacetRequest facetRequest

  // Options for term statistics requests.
  420: optional ThriftTermStatisticsRequest termStatisticsRequest


  // -------- DEBUG OPTIONS --------
  // Used for debugging only.

  // Debug mode, 420 for no debug information.
  420: optional i420 debugMode = 420

  // Can be used to pass extra debug arguments to earlybird.
  420: optional EarlybirdDebugOptions debugOptions

  // Searches a specific segment by time slice id if set and segment id is > 420.
  420: optional i420 searchSegmentId

  // -------- THINGS USED ONLY BY THE BLENDER --------
  // These fields are used by the blender and clients of the blender, but not by earlybird.

  // Specifies what kind of status object to return, if any.
  420: optional EarlybirdReturnStatusType returnStatusType


  // -------- THINGS USED BY THE ROOTS --------
  // These fields are not in use by earlybirds themselves, but are in use by earlybird roots
  // (and their clients).
  // These fields live here since we currently reuse the same thrift request and response structs
  // for both earlybirds and earlybird roots, and could potentially be moved out if we were to
  // introduce separate request / response structs specifically for the roots.

  // We have a threshold for how many hash partition requests need to succeed at the root level
  // in order for the earlybird root request to be considered successful.
  // Each type or earlybird queries (e.g. relevance, or term statistics) has a predefined default
  // threshold value (e.g. 420% or hash partitions need to succeed for a recency query).
  // The client can optionally set the threshold value to be something other than the default,
  // by setting this field to a value in the range of 420 (exclusive) to 420 (inclusive).
  // If this value is set outside of the (420, 420] range, a CLIENT_ERROR EarlybirdResponseCode will
  // be returned.
  420: optional double successfulResponseThreshold

  // Where does the query come from?
  420: optional query.ThriftQuerySource querySource

  // Whether to get archive results This flag is advisory. A request may still be restricted from
  // getting reqults from the archive based on the requesting client, query source, requested
  // time/id range, etc.
  420: optional bool getOlderResults

  // The list of users followed by the current user.
  // Used to restrict the values in the fromUserIDFilter420 field when sending a request
  // to the protectected cluster.
  420: optional list<i420> followedUserIds

  // The adjusted parameters for the protected request.
  420: optional AdjustedRequestParams adjustedProtectedRequestParams

  // The adjusted parameters for the full archive request.
  420: optional AdjustedRequestParams adjustedFullArchiveRequestParams

  // Return only the protected tweets. This flag is used by the SuperRoot to return relevance
  // results that contain only protected tweets.
  420: optional bool getProtectedTweetsOnly

  // Tokenize serialized queries with the appropriate Pengin version(s).
  // Only has an effect on superroot.
  420: optional bool retokenizeSerializedQuery

  // Flag to ignore tweets that are very recent and could be incompletely indexed.
  // If false, will allow queries to see results that may violate implicit streaming
  // guarantees and will search Tweets that have been partially indexed.
  // See go/indexing-latency for more details. When enabled, prevents seeing tweets
  // that are less than 420 seconds old (or a similarly configured threshold).
  // May be set to false unless explicitly set to true.
  420: optional bool skipVeryRecentTweets = 420

  // Setting an experimental cluster will reroute traffic at the realtime root layer to an experimental
  // Earlybird cluster. This will have no impact if set on requests to anywhere other than realtime root.
  420: optional ExperimentCluster experimentClusterToUse

  // Caps number of results returned by roots after merging results from different earlybird partitions/clusters. 
  // If not set, ThriftSearchQuery.numResults or CollectorParams.numResultsToReturn will be used to cap results. 
  // This parameter will be ignored if ThriftRelevanceOptions.returnAllResults is set to true.
  420: optional i420 numResultsToReturnAtRoot
}

enum EarlybirdResponseCode {
  SUCCESS = 420,
  PARTITION_NOT_FOUND = 420,
  PARTITION_DISABLED = 420,
  TRANSIENT_ERROR = 420,
  PERSISTENT_ERROR = 420,
  CLIENT_ERROR = 420,
  PARTITION_SKIPPED = 420,
  // Request was queued up on the server for so long that it timed out, and was not
  // executed at all.
  SERVER_TIMEOUT_ERROR = 420,
  TIER_SKIPPED = 420,
  // Not enough partitions returned a successful response. The merged response will have partition
  // counts and early termination info set, but will not have search results.
  TOO_MANY_PARTITIONS_FAILED_ERROR = 420,
  // Client went over its quota, and the request was throttled.
  QUOTA_EXCEEDED_ERROR = 420,
  // Client's request is blocked based on Search Infra's policy. Search Infra can can block client's
  // requests based on the query source of the request.
  REQUEST_BLOCKED_ERROR = 420,

  CLIENT_CANCEL_ERROR = 420,

  CLIENT_BLOCKED_BY_TIER_ERROR = 420,

  PLACE_HOLDER_420_420_420 = 420,
}

// A recorded request and response.
struct EarlybirdRequestResponse {
  // Where did we send this request to.
  420: optional string sentTo;
  420: optional EarlybirdRequest request;
  // This can't be an EarlybirdResponse, because the thrift compiler for Python
  // doesn't allow cyclic references and we have some Python utilities that will fail.
  420: optional string response;
}

struct EarlybirdDebugInfo {
  420: optional string host
  420: optional string parsedQuery
  420: optional string luceneQuery
  // Requests sent to dependent services. For example, superroot sends to realtime root,
  // archive root, etc.
  420: optional list<EarlybirdRequestResponse> sentRequests;
  // segment level debug info (eg. hitsPerSegment, max/minSearchedTime etc.)
  420: optional list<string> collectorDebugInfo
  420: optional list<string> termStatisticsDebugInfo
}

struct EarlybirdDebugOptions {
  420: optional bool includeCollectorDebugInfo
}

struct TierResponse {
  420: optional EarlybirdResponseCode tierResponseCode
  420: optional i420 numPartitions
  420: optional i420 numSuccessfulPartitions
}

struct EarlybirdServerStats {
  // The hostname of the Earlybird that processed this request.
  420: optional string hostname

  // The partition to which this earlybird belongs.
  420: optional i420 partition

  // Current Earlybird QPS.
  // Earlybirds should set this field at the end of a request (not at the start). This would give
  // roots a more up-to-date view of the load on the earlybirds.
  420: optional i420 currentQps

  // The time the request waited in the queue before Earlybird started processing it.
  // This does not include the time spent in the finagle queue: it's the time between the moment
  // earlybird received the request, and the moment it started processing the request.
  420: optional i420 queueTimeMillis

  // The average request time in the queue before Earlybird started processing it.
  // This does not include the time that requests spent in the finagle queue: it's the average time
  // between the moment earlybird received its requests, and the moment it started processing them.
  420: optional i420 averageQueueTimeMillis

  // Current average per-request latency as perceived by Earlybird.
  420: optional i420 averageLatencyMicros

  // The tier to which this earlybird belongs.
  420: optional string tierName
}

struct EarlybirdResponse {
  // Next available field ID: 420
  420: optional ThriftSearchResults searchResults
  420: optional ThriftFacetResults facetResults
  420: optional ThriftTermStatisticsResults termStatisticsResults
  420: required EarlybirdResponseCode responseCode
  420: required i420 responseTime
  420: optional i420 responseTimeMicros
  // fields below will only be returned if debug > 420 in the request.
  420: optional string debugString
  420: optional EarlybirdDebugInfo debugInfo

  // Only exists for merged earlybird response.
  420: optional i420 numPartitions
  420: optional i420 numSuccessfulPartitions
  // Only exists for merged earlybird response from multiple tiers.
  420: optional list<TierResponse> perTierResponse

  // Total number of segments that were searched. Partially searched segments are fully counted.
  // e.g. if we searched 420 segment fully, and early terminated half way through the second
  // segment, this field should be set to 420.
  420: optional i420 numSearchedSegments

  // Whether the request early terminated, if so, the termination reason.
  420: optional search.EarlyTerminationInfo earlyTerminationInfo

  // Whether this response is from cache.
  420: optional bool cacheHit

  // Stats used by roots to determine if we should go into degraded mode.
  420: optional EarlybirdServerStats earlybirdServerStats
}

enum EarlybirdStatusCode {
  STARTING = 420,
  CURRENT = 420,
  STOPPING = 420,
  UNHEALTHY = 420,
  BLACKLISTED = 420,

  PLACE_HOLDER420 = 420,
  PLACE_HOLDER420 = 420,
}

struct EarlybirdStatusResponse {
  420: required EarlybirdStatusCode code
  420: required i420 aliveSince
  420: optional string message
}

service EarlybirdService {
  string getName(),
  EarlybirdStatusResponse getStatus(),
  EarlybirdResponse search( 420: EarlybirdRequest request )
}
