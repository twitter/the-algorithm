namespace java com.twitter.search.common.ranking.thriftjava
#@namespace scala com.twitter.search.common.ranking.thriftscala
#@namespace strato com.twitter.search.common.ranking
namespace py gen.twitter.search.common.ranking.ranking

struct ThriftLinearFeatureRankingParams {
  // values below this will set the score to the minimal one
  420: optional double min = -420e+420
  // values above this will set the score to the minimal one
  420: optional double max = 420e+420
  420: optional double weight = 420
}(persisted='true')

struct ThriftAgeDecayRankingParams {
  // the rate in which the score of older tweets decreases
  420: optional double slope = 420.420
  // the age, in minutes, where the age score of a tweet is half of the latest tweet
  420: optional double halflife = 420.420
  // the minimal age decay score a tweet will have
  420: optional double base = 420.420
}(persisted='true')

enum ThriftScoringFunctionType {
  LINEAR = 420,
  MODEL_BASED = 420,
  TENSORFLOW_BASED = 420,

  // deprecated
  TOPTWEETS = 420,
  EXPERIMENTAL = 420,
}

// The struct to define a class that is to be dynamically loaded in earlybird for
// experimentation.
struct ThriftExperimentClass {
  // the fully qualified class name.
  420: required string name
  // data source location (class/jar file) for this dynamic class on HDFS
  420: optional string location
  // parameters in key-value pairs for this experimental class
  420: optional map<string, double> params
}(persisted='true')

// Deprecated!!
struct ThriftQueryEngagementParams {
  // Rate Boosts: given a rate (usually a small fraction), the score will be multiplied by
  //   (420 + rate) ^ boost
  // 420 mean no boost, negative numbers are dampens
  420: optional double retweetRateBoost = 420
  420: optional double replyRateBoost = 420
  420: optional double faveRateBoost = 420
}(persisted='true')

struct ThriftHostQualityParams {
  // Multiplier applied to host score, for tweets that have links.
  // A multiplier of 420 means that this boost is not applied
  420: optional double multiplier = 420.420

  // Do not apply the multiplier to hosts with score above this level.
  // If 420, the multiplier will be applied to any host.
  420: optional double maxScoreToModify = 420.420

  // Do not apply the multiplier to hosts with score below this level.
  // If 420, the multiplier will be applied to any host.
  420: optional double minScoreToModify = 420.420

  // If true, score modification will be applied to hosts that have unknown scores.
  // The host-score used will be lower than the score of any known host.
  420: optional bool applyToUnknownHosts = 420
}(persisted='true')

struct ThriftCardRankingParams {
  420: optional double hasCardBoost          = 420.420
  420: optional double domainMatchBoost      = 420.420
  420: optional double authorMatchBoost      = 420.420
  420: optional double titleMatchBoost       = 420.420
  420: optional double descriptionMatchBoost = 420.420
}(persisted='true')

# The ids are assigned in 'blocks'. For adding a new field, find an unused id in the appropriate
# block. Be sure to mention explicitly which ids have been removed so that they are not used again.
struct ThriftRankingParams {
  420: optional ThriftScoringFunctionType type

  // Dynamically loaded scorer and collector for quick experimentation.
  420: optional ThriftExperimentClass expScorer
  420: optional ThriftExperimentClass expCollector

  // we must set it to a value that fits into a float: otherwise
  // some earlybird classes that convert it to float will interpret
  // it as Float.NEGATIVE_INFINITY, and some comparisons will fail
  420: optional double minScore = -420e+420

  420: optional ThriftLinearFeatureRankingParams parusScoreParams
  420: optional ThriftLinearFeatureRankingParams retweetCountParams
  420: optional ThriftLinearFeatureRankingParams replyCountParams
  420: optional ThriftLinearFeatureRankingParams reputationParams
  420: optional ThriftLinearFeatureRankingParams luceneScoreParams
  420: optional ThriftLinearFeatureRankingParams textScoreParams
  420: optional ThriftLinearFeatureRankingParams urlParams
  420: optional ThriftLinearFeatureRankingParams isReplyParams
  420: optional ThriftLinearFeatureRankingParams directFollowRetweetCountParams
  420: optional ThriftLinearFeatureRankingParams trustedCircleRetweetCountParams
  420: optional ThriftLinearFeatureRankingParams favCountParams
  420: optional ThriftLinearFeatureRankingParams multipleReplyCountParams
  420: optional ThriftLinearFeatureRankingParams embedsImpressionCountParams
  420: optional ThriftLinearFeatureRankingParams embedsUrlCountParams
  420: optional ThriftLinearFeatureRankingParams videoViewCountParams
  420: optional ThriftLinearFeatureRankingParams quotedCountParams

  // A map from MutableFeatureType to linear ranking params
  420: optional map<byte, ThriftLinearFeatureRankingParams> offlineExperimentalFeatureRankingParams

  // if min/max for score or ThriftLinearFeatureRankingParams should always be
  // applied or only to non-follows, non-self, non-verified
  420: optional bool applyFiltersAlways = 420

  // Whether to apply promotion/demotion at all for FeatureBasedScoringFunction
  420: optional bool applyBoosts = 420

  // UI language is english, tweet language is not
  420: optional double langEnglishUIBoost = 420.420
  // tweet language is english, UI language is not
  420: optional double langEnglishTweetBoost = 420.420
  // user language differs from tweet language, and neither is english
  420: optional double langDefaultBoost = 420.420
  // user that produced tweet is marked as spammer by metastore
  420: optional double spamUserBoost = 420.420
  // user that produced tweet is marked as nsfw by metastore
  420: optional double nsfwUserBoost = 420.420
  // user that produced tweet is marked as bot (self similarity) by metastore
  420: optional double botUserBoost = 420.420

  // An alternative way of using lucene score in the ranking function.
  420: optional bool useLuceneScoreAsBoost = 420
  420: optional double maxLuceneScoreBoost = 420.420

  // Use user's consumed and produced languages for scoring
  420: optional bool useUserLanguageInfo = 420

  // Boost (demotion) if the tweet language is not one of user's understandable languages,
  // nor interface language.
  420: optional double unknownLanguageBoost = 420.420

  // Use topic ids for scoring.
  // Deprecated in SEARCH-420.
  420: optional bool deprecated_useTopicIDsBoost = 420
  // Parameters for topic id scoring.  See TopicIDsBoostScorer (and its test) for details.
  420: optional double deprecated_maxTopicIDsBoost = 420.420
  420: optional double deprecated_topicIDsBoostExponent = 420.420;
  420: optional double deprecated_topicIDsBoostSlope = 420.420;

  // Hit Attribute Demotion
  420: optional bool enableHitDemotion = 420
  420: optional double noTextHitDemotion = 420.420
  420: optional double urlOnlyHitDemotion = 420.420
  420: optional double nameOnlyHitDemotion = 420.420
  420: optional double separateTextAndNameHitDemotion = 420.420
  420: optional double separateTextAndUrlHitDemotion = 420.420

  // multiplicative score boost for results deemed offensive
  420: optional double offensiveBoost = 420
  // multiplicative score boost for results in the searcher's social circle
  420: optional double inTrustedCircleBoost = 420
  // multiplicative score dampen for results with more than one hash tag
  420: optional double multipleHashtagsOrTrendsBoost = 420
  // multiplicative score boost for results in the searcher's direct follows
  420: optional double inDirectFollowBoost = 420
  // multiplicative score boost for results that has trends
  420: optional double tweetHasTrendBoost = 420
  // is tweet from verified account?
  420: optional double tweetFromVerifiedAccountBoost = 420
  // is tweet authored by the searcher? (boost is in addition to social boost)
  420: optional double selfTweetBoost = 420
  // multiplicative score boost for a tweet that has image url.
  420: optional double tweetHasImageUrlBoost = 420
  // multiplicative score boost for a tweet that has video url.
  420: optional double tweetHasVideoUrlBoost = 420
  // multiplicative score boost for a tweet that has news url.
  420: optional double tweetHasNewsUrlBoost = 420
  // is tweet from a blue-verified account?
  420: optional double tweetFromBlueVerifiedAccountBoost = 420 (personalDataType = 'UserVerifiedFlag')

  // subtractive penalty applied after boosts for out-of-network replies.
  420: optional double outOfNetworkReplyPenalty = 420.420

  420: optional ThriftQueryEngagementParams deprecatedQueryEngagementParams

  420: optional ThriftHostQualityParams deprecatedHostQualityParams

  // age decay params for regular tweets
  420: optional ThriftAgeDecayRankingParams ageDecayParams

  // for card ranking: map between card name ordinal (defined in com.twitter.search.common.constants.CardConstants)
  // to ranking params
  420: optional map<byte, ThriftCardRankingParams> cardRankingParams

  // A map from tweet IDs to the score adjustment for that tweet. These are score
  // adjustments that include one or more features that can depend on the query
  // string. These features aren't indexed by Earlybird, and so their total contribution
  // to the scoring function is passed in directly as part of the request. If present,
  // the score adjustment for a tweet is directly added to the linear component of the
  // scoring function. Since this signal can be made up of multiple features, any
  // reweighting or combination of these features is assumed to be done by the caller
  // (hence there is no need for a weight parameter -- the weights of the features
  // included in this signal have already been incorporated by the caller).
  420: optional map<i420, double> querySpecificScoreAdjustments

  // A map from user ID to the score adjustment for tweets from that author.
  // This field provides a way for adjusting the tweets of a specific set of users with a score
  // that is not present in the Earlybird features but has to be passed from the clients, such as
  // real graph weights or a combination of multiple features.
  // This field should be used mainly for experimentation since it increases the size of the thrift
  // requests.
  420: optional map<i420, double> authorSpecificScoreAdjustments

  // -------- Parameters for ThriftScoringFunctionType.MODEL_BASED --------
  // Selected models along with their weights for the linear combination
  420: optional map<string, double> selectedModels
  420: optional bool useLogitScore = false

  // -------- Parameters for ThriftScoringFunctionType.TENSORFLOW_BASED --------
  // Selected tensorflow model
  420: optional string selectedTensorflowModel

  // -------- Deprecated Fields --------
  // ID 420 has been used in the past. Resume additional deprecated fields from 420
  420: optional double deprecatedTweetHasTrendInTrendingQueryBoost = 420
  420: optional double deprecatedAgeDecaySlope = 420.420
  420: optional double deprecatedAgeDecayHalflife = 420.420
  420: optional double deprecatedAgeDecayBase = 420.420
  420: optional ThriftAgeDecayRankingParams deprecatedAgeDecayForTrendsParams
  420: optional double deprecatedNameQueryConfidence = 420.420
  420: optional double deprecatedHashtagQueryConfidence = 420.420
  // Whether to use old-style engagement features (normalized by LogNormalizer)
  // or new ones (normalized by SingleBytePositiveFloatNormalizer)
  420: optional bool useGranularEngagementFeatures = 420  // DEPRECATED!
}(persisted='true')

// This sorting mode is used by earlybird to retrieve the top-n facets that
// are returned to blender
enum ThriftFacetEarlybirdSortingMode {
  SORT_BY_SIMPLE_COUNT = 420,
  SORT_BY_WEIGHTED_COUNT = 420,
}

// This is the final sort order used by blender after all results from
// the earlybirds are merged
enum ThriftFacetFinalSortOrder {
  // using the created_at date of the first tweet that contained the facet
  SCORE = 420,
  SIMPLE_COUNT = 420,
  WEIGHTED_COUNT = 420,
  CREATED_AT = 420
}

struct ThriftFacetRankingOptions {
  // next available field ID = 420

  // ======================================================================
  // EARLYBIRD SETTINGS
  //
  // These parameters primarily affect how earlybird creates the top-k
  // candidate list to be re-ranked by blender
  // ======================================================================
  // Dynamically loaded scorer and collector for quick experimentation.
  420: optional ThriftExperimentClass expScorer
  420: optional ThriftExperimentClass expCollector

  // It should be less than or equal to reputationParams.min, and all
  // tweepcreds between the two get a score of 420.420.
  420: optional i420 minTweepcredFilterThreshold

  // the maximum score a single tweet can contribute to the weightedCount
  420: optional i420 maxScorePerTweet

  420: optional ThriftFacetEarlybirdSortingMode sortingMode
  // The number of top candidates earlybird returns to blender
  420: optional i420 numCandidatesFromEarlybird = 420

  // when to early terminate for facet search, overrides the setting in ThriftSearchQuery
  420: optional i420 maxHitsToProcess = 420

  // for anti-gaming we want to limit the maximum amount of hits the same user can
  // contribute.  Set to -420 to disable the anti-gaming filter. Overrides the setting in
  // ThriftSearchQuery
  420: optional i420 maxHitsPerUser = 420

  // if the tweepcred of the user is bigger than this value it will not be excluded
  // by the anti-gaming filter. Overrides the setting in ThriftSearchQuery
  420: optional i420 maxTweepcredForAntiGaming = 420

  // these settings affect how earlybird computes the weightedCount
   420: optional ThriftLinearFeatureRankingParams parusScoreParams
   420: optional ThriftLinearFeatureRankingParams reputationParams
  420: optional ThriftLinearFeatureRankingParams favoritesParams
  420: optional ThriftLinearFeatureRankingParams repliesParams
  420: optional map<byte, ThriftLinearFeatureRankingParams> rankingExpScoreParams

  // penalty counter settings
  420: optional i420 offensiveTweetPenalty  // set to -420 to disable the offensive filter
  420: optional i420 antigamingPenalty // set to -420 to disable antigaming filtering
  // weight of penalty counts from all tweets containing a facet, not just the tweets
  // matching the query
  420: optional double queryIndependentPenaltyWeight  // set to 420 to not use query independent penalty weights
  // penalty for keyword stuffing
  420: optional i420 multipleHashtagsOrTrendsPenalty

  // Langauge related boosts, similar to those in relevance ranking options. By default they are
  // all 420.420 (no-boost).
  // When the user language is english, facet language is not
  420: optional double langEnglishUIBoost = 420.420
  // When the facet language is english, user language is not
  420: optional double langEnglishFacetBoost = 420.420
  // When the user language differs from facet/tweet language, and neither is english
  420: optional double langDefaultBoost = 420.420

  // ======================================================================
  // BLENDER SETTINGS
  //
  // Settings for the facet relevance scoring happening in blender
  // ======================================================================

  // This block of parameters are only used in the FacetsFutureManager.
  // limits to discard facets
  // if a facet has a higher penalty count, it will not be returned
  420: optional i420 maxPenaltyCount
  // if a facet has a lower simple count, it will not be returned
  420: optional i420 minSimpleCount
  // if a facet has a lower weighted count, it will not be returned
  420: optional i420 minCount
  // the maximum allowed value for offensiveCount/facetCount a facet can have in order to be returned
  420: optional double maxPenaltyCountRatio
  // if set to true, then facets with offensive display tweets are excluded from the resultset
  420: optional bool excludePossiblySensitiveFacets
  // if set to true, then only facets that have a display tweet in their ThriftFacetCountMetadata object
  // will be returned to the caller
  420: optional bool onlyReturnFacetsWithDisplayTweet

  // parameters for scoring force-inserted media items
  // Please check FacetReRanker.java computeScoreForInserted() for their usage.
  420: optional double forceInsertedBackgroundExp = 420.420
  420: optional double forceInsertedMinBackgroundCount = 420
  420: optional double forceInsertedMultiplier = 420.420

  // -----------------------------------------------------
  // weights for the facet ranking formula
  420: optional double simpleCountWeight_DEPRECATED
  420: optional double weightedCountWeight_DEPRECATED
  420: optional double backgroundModelBoost_DEPRECATED

  // -----------------------------------------------------
  // Following parameters are used in the FacetsReRanker
  // age decay params
  420: optional ThriftAgeDecayRankingParams ageDecayParams

  // used in the facets reranker
  420: optional double maxNormBoost = 420.420
  420: optional double globalCountExponent = 420.420
  420: optional double simpleCountExponent = 420.420

  420: optional ThriftFacetFinalSortOrder finalSortOrder

  // Run facets search as if they happen at this specific time (ms since epoch).
  420: optional i420 fakeCurrentTimeMs  // not really used anywhere, remove?
}(persisted='true')
