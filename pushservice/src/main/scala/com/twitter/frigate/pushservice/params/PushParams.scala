package com.twitter.frigate.pushservice.params

import com.twitter.rux.common.context.thriftscala.ExperimentKey
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.configapi.decider.BooleanDeciderParam

object PushParams {

  /**
   * Disable ML models in filtering
   */
  object DisableMlInFilteringParam extends BooleanDeciderParam(DeciderKey.disableMLInFiltering)

  /**
   * Disable ML models in ranking, use random ranking instead
   * This param is used for ML holdback and training data collection
   */
  object UseRandomRankingParam extends Param(false)

  /**
   * Disable feature hydration, ML ranking, and ML filtering
   * Use default order from candidate source
   * This param is for service continuity
   */
  object DisableAllRelevanceParam extends BooleanDeciderParam(DeciderKey.disableAllRelevance)

  /**
   * Disable ML heavy ranking
   * Use default order from candidate source
   * This param is for service continuity
   */
  object DisableHeavyRankingParam extends BooleanDeciderParam(DeciderKey.disableHeavyRanking)

  /**
   * Restrict ML light ranking by selecting top3 candidates
   * Use default order from candidate source
   * This param is for service continuity
   */
  object RestrictLightRankingParam extends BooleanDeciderParam(DeciderKey.restrictLightRanking)

  /**
   * Downsample ML light ranking scribed candidates
   */
  object DownSampleLightRankingScribeCandidatesParam
      extends BooleanDeciderParam(DeciderKey.downSampleLightRankingScribeCandidates)

  /**
   * Set it to true only for Android only ranking experiments
   */
  object AndroidOnlyRankingExperimentParam extends Param(false)

  /**
   * Enable the user_tweet_entity_graph tweet candidate source.
   */
  object UTEGTweetCandidateSourceParam
      extends BooleanDeciderParam(DeciderKey.entityGraphTweetRecsDeciderKey)

  /**
   * Enable writes to Notification Service
   */
  object EnableWritesToNotificationServiceParam
      extends BooleanDeciderParam(DeciderKey.enablePushserviceWritesToNotificationServiceDeciderKey)

  /**
   * Enable writes to Notification Service for all employees
   */
  object EnableWritesToNotificationServiceForAllEmployeesParam
      extends BooleanDeciderParam(
        DeciderKey.enablePushserviceWritesToNotificationServiceForAllEmployeesDeciderKey)

  /**
   * Enable writes to Notification Service for everyone
   */
  object EnableWritesToNotificationServiceForEveryoneParam
      extends BooleanDeciderParam(
        DeciderKey.enablePushserviceWritesToNotificationServiceForEveryoneDeciderKey)

  /**
   * Enable fatiguing MR for Ntab caret click
   */
  object EnableFatigueNtabCaretClickingParam extends Param(true)

  /**
   * Param for disabling in-network Tweet candidates
   */
  object DisableInNetworkTweetCandidatesParam extends Param(false)

  /**
   * Decider controlled param to enable prompt feedback response NO predicate
   */
  object EnablePromptFeedbackFatigueResponseNoPredicate
      extends BooleanDeciderParam(
        DeciderKey.enablePromptFeedbackFatigueResponseNoPredicateDeciderKey)

  /**
   * Enable hydration and generation of Social context (TF, TR) based candidates for Earlybird Tweets
   */
  object EarlyBirdSCBasedCandidatesParam
      extends BooleanDeciderParam(DeciderKey.enableUTEGSCForEarlybirdTweetsDecider)

  /**
   * Param to allow reduce to one social proof for tweet param in UTEG
   */
  object AllowOneSocialProofForTweetInUTEGParam extends Param(true)

  /**
   * Param to query UTEG for out network tweets only
   */
  object OutNetworkTweetsOnlyForUTEGParam extends Param(false)

  object EnablePushSendEventBus extends BooleanDeciderParam(DeciderKey.enablePushSendEventBus)

  /**
   * Enable RUX Tweet landing page for push open on iOS
   */
  object EnableRuxLandingPageIOSParam extends Param[Boolean](true)

  /**
   * Enable RUX Tweet landing page for push open on Android
   */
  object EnableRuxLandingPageAndroidParam extends Param[Boolean](true)

  /**
   * Param to decide which ExperimentKey to be encoded into Rux landing page context object.
   * The context object is sent to rux-api and rux-api applies logic (e.g. show reply module on
   * rux landing page or not) accordingly based on the experiment key.
   */
  object RuxLandingPageExperimentKeyIOSParam extends Param[Option[ExperimentKey]](None)
  object RuxLandingPageExperimentKeyAndroidParam extends Param[Option[ExperimentKey]](None)

  /**
   * Param to enable MR Tweet Fav Recs
   */
  object MRTweetFavRecsParam extends BooleanDeciderParam(DeciderKey.enableTweetFavRecs)

  /**
   * Param to enable MR Tweet Retweet Recs
   */
  object MRTweetRetweetRecsParam extends BooleanDeciderParam(DeciderKey.enableTweetRetweetRecs)

  /**
   * Param to disable writing to NTAB
   * */
  object DisableWritingToNTAB extends Param[Boolean](default = false)

  /**
   * Param to show RUX landing page as a modal on iOS
   */
  object ShowRuxLandingPageAsModalOnIOS extends Param[Boolean](default = false)

  /**
   * Param to enable mr end to end scribing
   */
  object EnableMrRequestScribing extends BooleanDeciderParam(DeciderKey.enableMrRequestScribing)

  /**
   * Param to enable scribing of high quality candidate scores
   */
  object EnableHighQualityCandidateScoresScribing
      extends BooleanDeciderParam(DeciderKey.enableHighQualityCandidateScoresScribing)

  /**
   * Decider controlled param to pNeg multimodal predictions for F1 tweets
   */
  object EnablePnegMultimodalPredictionForF1Tweets
      extends BooleanDeciderParam(DeciderKey.enablePnegMultimodalPredictionForF1Tweets)

  /**
   * Decider controlled param to scribe oonFav score for F1 tweets
   */
  object EnableScribeOonFavScoreForF1Tweets
      extends BooleanDeciderParam(DeciderKey.enableScribingOonFavScoreForF1Tweets)

  /**
   * Param to enable htl user aggregates extended hydration
   */
  object EnableHtlOfflineUserAggregatesExtendedHydration
      extends BooleanDeciderParam(DeciderKey.enableHtlOfflineUserAggregateExtendedFeaturesHydration)

  /**
   * Param to enable predicate detailed info scribing
   */
  object EnablePredicateDetailedInfoScribing
      extends BooleanDeciderParam(DeciderKey.enablePredicateDetailedInfoScribing)

  /**
   * Param to enable predicate detailed info scribing
   */
  object EnablePushCapInfoScribing
      extends BooleanDeciderParam(DeciderKey.enablePredicateDetailedInfoScribing)

  /**
   * Param to enable user signal language feature hydration
   */
  object EnableUserSignalLanguageFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableUserSignalLanguageFeatureHydration)

  /**
   * Param to enable user preferred language feature hydration
   */
  object EnableUserPreferredLanguageFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableUserPreferredLanguageFeatureHydration)

  /**
   * Param to enable ner erg feature hydration
   */
  object EnableNerErgFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableNerErgFeaturesHydration)

  /**
   * Param to enable inline action on push copy for Android
   */
  object MRAndroidInlineActionOnPushCopyParam extends Param[Boolean](default = true)

  /**
   * Param to enable hydrating mr user semantic core embedding features
   * */
  object EnableMrUserSemanticCoreFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserSemanticCoreFeaturesHydration)

  /**
   * Param to enable hydrating mr user semantic core embedding features filtered by 0.0000001
   * */
  object EnableMrUserSemanticCoreNoZeroFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserSemanticCoreNoZeroFeaturesHydration)

  /*
   * Param to enable days since user's recent resurrection features hydration
   */
  object EnableDaysSinceRecentResurrectionFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableDaysSinceRecentResurrectionFeatureHydration)

  /*
   * Param to enable days since user past aggregates features hydration
   */
  object EnableUserPastAggregatesFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableUserPastAggregatesFeatureHydration)

  /*
   * Param to enable mr user simcluster features (v2020) hydration
   * */
  object EnableMrUserSimclusterV2020FeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserSimclusterV2020FeaturesHydration)

  /*
   * Param to enable mr user simcluster features (v2020) hydration
   * */
  object EnableMrUserSimclusterV2020NoZeroFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserSimclusterV2020NoZeroFeaturesHydration)

  /*
   * Param to enable HTL topic engagement realtime aggregate features
   * */
  object EnableTopicEngagementRealTimeAggregatesFeatureHydration
      extends BooleanDeciderParam(
        DeciderKey.enableTopicEngagementRealTimeAggregatesFeatureHydration)

  object EnableUserTopicAggregatesFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableUserTopicAggregatesFeatureHydration)

  /**
   * Param to enable user author RTA feature hydration
   */
  object EnableHtlUserAuthorRTAFeaturesFromFeatureStoreHydration
      extends BooleanDeciderParam(DeciderKey.enableHtlUserAuthorRealTimeAggregateFeatureHydration)

  /**
   * Param to enable duration since last visit features
   */
  object EnableDurationSinceLastVisitFeatures
      extends BooleanDeciderParam(DeciderKey.enableDurationSinceLastVisitFeatureHydration)

  object EnableTweetAnnotationFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableTweetAnnotationFeatureHydration)

  /**
   * Param to Enable visibility filtering through SpaceVisibilityLibrary from SpacePredicate
   */
  object EnableSpaceVisibilityLibraryFiltering
      extends BooleanDeciderParam(DeciderKey.enableSpaceVisibilityLibraryFiltering)

  /*
   * Param to enable user topic follow feature set hydration
   * */
  object EnableUserTopicFollowFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.enableUserTopicFollowFeatureSet)

  /*
   * Param to enable onboarding new user feature set hydration
   * */
  object EnableOnboardingNewUserFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.enableOnboardingNewUserFeatureSet)

  /*
   * Param to enable mr user author sparse continuous feature set hydration
   * */
  object EnableMrUserAuthorSparseContFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserAuthorSparseContFeatureSet)

  /*
   * Param to enable mr user topic sparse continuous feature set hydration
   * */
  object EnableMrUserTopicSparseContFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserTopicSparseContFeatureSet)

  /*
   * Param to enable penguin language feature set hydration
   * */
  object EnableUserPenguinLanguageFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.enableUserPenguinLanguageFeatureSet)

  /*
   * Param to enable user engaged tweet tokens feature hydration
   * */
  object EnableMrUserEngagedTweetTokensFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserEngagedTweetTokensFeaturesHydration)

  /*
   * Param to enable candidate tweet tokens feature hydration
   * */
  object EnableMrCandidateTweetTokensFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableMrCandidateTweetTokensFeaturesHydration)

  /*
   * Param to enable mr user hashspace embedding feature set hydration
   * */
  object EnableMrUserHashspaceEmbeddingFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserHashspaceEmbeddingFeatureSet)

  /*
   * Param to enable mr tweet sentiment feature set hydration
   * */
  object EnableMrTweetSentimentFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableMrTweetSentimentFeatureSet)

  /*
   * Param to enable mr tweet_author aggregates feature set hydration
   * */
  object EnableMrTweetAuthorAggregatesFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableMrTweetAuthorAggregatesFeatureSet)

  /**
   * Param to enable twistly aggregated features
   */
  object EnableTwistlyAggregatesFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableTwistlyAggregatesFeatureHydration)

  /**
   * Param to enable tweet twhin favoriate features
   */
  object EnableTweetTwHINFavFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableTweetTwHINFavFeaturesHydration)

  /*
   * Param to enable mr user geo feature set hydration
   * */
  object EnableUserGeoFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.enableUserGeoFeatureSet)

  /*
   * Param to enable mr author geo feature set hydration
   * */
  object EnableAuthorGeoFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.enableAuthorGeoFeatureSet)

  /*
   * Param to ramp up mr user geo feature set hydration
   * */
  object RampupUserGeoFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.rampupUserGeoFeatureSet)

  /*
   * Param to ramp up mr author geo feature set hydration
   * */
  object RampupAuthorGeoFeatureSetHydration
      extends BooleanDeciderParam(DeciderKey.rampupAuthorGeoFeatureSet)

  /*
   *  Decider controlled param to enable Pop Geo Tweets
   * */
  object PopGeoCandidatesDecider extends BooleanDeciderParam(DeciderKey.enablePopGeoTweets)

  /**
   * Decider controlled param to enable Trip Geo Tweets
   */
  object TripGeoTweetCandidatesDecider
      extends BooleanDeciderParam(DeciderKey.enableTripGeoTweetCandidates)

  /**
   * Decider controlled param to enable ContentRecommenderMixerAdaptor
   */
  object ContentRecommenderMixerAdaptorDecider
      extends BooleanDeciderParam(DeciderKey.enableContentRecommenderMixerAdaptor)

  /**
   * Decider controlled param to enable GenericCandidateAdaptor
   */
  object GenericCandidateAdaptorDecider
      extends BooleanDeciderParam(DeciderKey.enableGenericCandidateAdaptor)

  /**
   * Decider controlled param to enable dark traffic to ContentMixer for Trip Geo Tweets
   */
  object TripGeoTweetContentMixerDarkTrafficDecider
      extends BooleanDeciderParam(DeciderKey.enableTripGeoTweetContentMixerDarkTraffic)

  /*
   *  Decider controlled param to enable Pop Geo Tweets
   * */
  object TrendsCandidateDecider extends BooleanDeciderParam(DeciderKey.enableTrendsTweets)

  /*
   *  Decider controlled param to enable INS Traffic
   **/
  object EnableInsTrafficDecider extends BooleanDeciderParam(DeciderKey.enableInsTraffic)

  /**
   * Param to enable assigning pushcap with ML predictions (read from MH table).
   * Disabling will fallback to only use heuristics and default values.
   */
  object EnableModelBasedPushcapAssignments
      extends BooleanDeciderParam(DeciderKey.enableModelBasedPushcapAssignments)

  /**
   * Param to enable twhin user engagement feature hydration
   */
  object EnableTwHINUserEngagementFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableTwHINUserEngagementFeaturesHydration)

  /**
   * Param to enable twhin user follow feature hydration
   */
  object EnableTwHINUserFollowFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableTwHINUserFollowFeaturesHydration)

  /**
   * Param to enable twhin author follow feature hydration
   */
  object EnableTwHINAuthorFollowFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableTwHINAuthorFollowFeaturesHydration)

  /**
   * Param to enable calls to the IsTweetTranslatable strato column
   */
  object EnableIsTweetTranslatableCheck
      extends BooleanDeciderParam(DeciderKey.enableIsTweetTranslatable)

  /**
   * Decider controlled param to enable mr tweet simcluster feature set hydration
   */
  object EnableMrTweetSimClusterFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableMrTweetSimClusterFeatureSet)

  /**
   * Decider controlled param to enable real graph v2 feature set hydration
   */
  object EnableRealGraphV2FeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableRealGraphV2FeatureHydration)

  /**
   * Decider controlled param to enable Tweet BeT feature set hydration
   */
  object EnableTweetBeTFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableTweetBeTFeatureHydration)

  /**
   * Decider controlled param to enable mr user tweet topic feature set hydration
   */
  object EnableMrOfflineUserTweetTopicAggregateHydration
      extends BooleanDeciderParam(DeciderKey.enableMrOfflineUserTweetTopicAggregate)

  /**
   * Decider controlled param to enable mr tweet simcluster feature set hydration
   */
  object EnableMrOfflineUserTweetSimClusterAggregateHydration
      extends BooleanDeciderParam(DeciderKey.enableMrOfflineUserTweetSimClusterAggregate)

  /**
   * Decider controlled param to enable user send time features
   */
  object EnableUserSendTimeFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableUserSendTimeFeatureHydration)

  /**
   * Decider controlled param to enable mr user utc send time aggregate features
   */
  object EnableMrUserUtcSendTimeAggregateFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserUtcSendTimeAggregateFeaturesHydration)

  /**
   * Decider controlled param to enable mr user local send time aggregate features
   */
  object EnableMrUserLocalSendTimeAggregateFeaturesHydration
      extends BooleanDeciderParam(DeciderKey.enableMrUserLocalSendTimeAggregateFeaturesHydration)

  /**
   * Decider controlled param to enable BQML report model predictions for F1 tweets
   */
  object EnableBqmlReportModelPredictionForF1Tweets
      extends BooleanDeciderParam(DeciderKey.enableBqmlReportModelPredictionForF1Tweets)

  /**
   * Decider controlled param to enable user Twhin embedding feature hydration
   */
  object EnableUserTwhinEmbeddingFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableUserTwhinEmbeddingFeatureHydration)

  /**
   * Decider controlled param to enable author follow Twhin embedding feature hydration
   */
  object EnableAuthorFollowTwhinEmbeddingFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableAuthorFollowTwhinEmbeddingFeatureHydration)

  object EnableScribingMLFeaturesAsDataRecord
      extends BooleanDeciderParam(DeciderKey.enableScribingMLFeaturesAsDataRecord)

  /**
   * Decider controlled param to enable feature hydration for Verified related feature
   */
  object EnableAuthorVerifiedFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableAuthorVerifiedFeatureHydration)

  /**
   * Decider controlled param to enable feature hydration for creator subscription related feature
   */
  object EnableAuthorCreatorSubscriptionFeatureHydration
      extends BooleanDeciderParam(DeciderKey.enableAuthorCreatorSubscriptionFeatureHydration)

  /**
   * Decider controlled param to direct MH+Memcache hydration for the UserFeaturesDataset
   */
  object EnableDirectHydrationForUserFeatures
      extends BooleanDeciderParam(DeciderKey.enableDirectHydrationForUserFeatures)
}
