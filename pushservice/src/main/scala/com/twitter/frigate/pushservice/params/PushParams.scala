package com.twitter.frigate.pushservice.params

import com.twitter.rutwitter.common.contetwittert.thriftscala.EtwitterperimentKey
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.configapi.decider.BooleanDeciderParam

object PushParams {

  /**
   * Disable ML models in filtering
   */
  object DisableMlInFilteringParam etwittertends BooleanDeciderParam(DeciderKey.disableMLInFiltering)

  /**
   * Disable ML models in ranking, use random ranking instead
   * This param is used for ML holdback and training data collection
   */
  object UseRandomRankingParam etwittertends Param(false)

  /**
   * Disable feature hydration, ML ranking, and ML filtering
   * Use default order from candidate source
   * This param is for service continuity
   */
  object DisableAllRelevanceParam etwittertends BooleanDeciderParam(DeciderKey.disableAllRelevance)

  /**
   * Disable ML heavy ranking
   * Use default order from candidate source
   * This param is for service continuity
   */
  object DisableHeavyRankingParam etwittertends BooleanDeciderParam(DeciderKey.disableHeavyRanking)

  /**
   * Restrict ML light ranking by selecting top3 candidates
   * Use default order from candidate source
   * This param is for service continuity
   */
  object RestrictLightRankingParam etwittertends BooleanDeciderParam(DeciderKey.restrictLightRanking)

  /**
   * Downsample ML light ranking scribed candidates
   */
  object DownSampleLightRankingScribeCandidatesParam
      etwittertends BooleanDeciderParam(DeciderKey.downSampleLightRankingScribeCandidates)

  /**
   * Set it to true only for Android only ranking etwitterperiments
   */
  object AndroidOnlyRankingEtwitterperimentParam etwittertends Param(false)

  /**
   * Enable the user_tweet_entity_graph tweet candidate source.
   */
  object UTEGTweetCandidateSourceParam
      etwittertends BooleanDeciderParam(DeciderKey.entityGraphTweetRecsDeciderKey)

  /**
   * Enable writes to Notification Service
   */
  object EnableWritesToNotificationServiceParam
      etwittertends BooleanDeciderParam(DeciderKey.enablePushserviceWritesToNotificationServiceDeciderKey)

  /**
   * Enable writes to Notification Service for all employees
   */
  object EnableWritesToNotificationServiceForAllEmployeesParam
      etwittertends BooleanDeciderParam(
        DeciderKey.enablePushserviceWritesToNotificationServiceForAllEmployeesDeciderKey)

  /**
   * Enable writes to Notification Service for everyone
   */
  object EnableWritesToNotificationServiceForEveryoneParam
      etwittertends BooleanDeciderParam(
        DeciderKey.enablePushserviceWritesToNotificationServiceForEveryoneDeciderKey)

  /**
   * Enable fatiguing MR for Ntab caret click
   */
  object EnableFatigueNtabCaretClickingParam etwittertends Param(true)

  /**
   * Param for disabling in-network Tweet candidates
   */
  object DisableInNetworkTweetCandidatesParam etwittertends Param(false)

  /**
   * Decider controlled param to enable prompt feedback response NO predicate
   */
  object EnablePromptFeedbackFatigueResponseNoPredicate
      etwittertends BooleanDeciderParam(
        DeciderKey.enablePromptFeedbackFatigueResponseNoPredicateDeciderKey)

  /**
   * Enable hydration and generation of Social contetwittert (TF, TR) based candidates for Earlybird Tweets
   */
  object EarlyBirdSCBasedCandidatesParam
      etwittertends BooleanDeciderParam(DeciderKey.enableUTEGSCForEarlybirdTweetsDecider)

  /**
   * Param to allow reduce to one social proof for tweet param in UTEG
   */
  object AllowOneSocialProofForTweetInUTEGParam etwittertends Param(true)

  /**
   * Param to query UTEG for out network tweets only
   */
  object OutNetworkTweetsOnlyForUTEGParam etwittertends Param(false)

  object EnablePushSendEventBus etwittertends BooleanDeciderParam(DeciderKey.enablePushSendEventBus)

  /**
   * Enable RUtwitter Tweet landing page for push open on iOS
   */
  object EnableRutwitterLandingPageIOSParam etwittertends Param[Boolean](true)

  /**
   * Enable RUtwitter Tweet landing page for push open on Android
   */
  object EnableRutwitterLandingPageAndroidParam etwittertends Param[Boolean](true)

  /**
   * Param to decide which EtwitterperimentKey to be encoded into Rutwitter landing page contetwittert object.
   * The contetwittert object is sent to rutwitter-api and rutwitter-api applies logic (e.g. show reply module on
   * rutwitter landing page or not) accordingly based on the etwitterperiment key.
   */
  object RutwitterLandingPageEtwitterperimentKeyIOSParam etwittertends Param[Option[EtwitterperimentKey]](None)
  object RutwitterLandingPageEtwitterperimentKeyAndroidParam etwittertends Param[Option[EtwitterperimentKey]](None)

  /**
   * Param to enable MR Tweet Fav Recs
   */
  object MRTweetFavRecsParam etwittertends BooleanDeciderParam(DeciderKey.enableTweetFavRecs)

  /**
   * Param to enable MR Tweet Retweet Recs
   */
  object MRTweetRetweetRecsParam etwittertends BooleanDeciderParam(DeciderKey.enableTweetRetweetRecs)

  /**
   * Param to disable writing to NTAB
   * */
  object DisableWritingToNTAB etwittertends Param[Boolean](default = false)

  /**
   * Param to show RUtwitter landing page as a modal on iOS
   */
  object ShowRutwitterLandingPageAsModalOnIOS etwittertends Param[Boolean](default = false)

  /**
   * Param to enable mr end to end scribing
   */
  object EnableMrRequestScribing etwittertends BooleanDeciderParam(DeciderKey.enableMrRequestScribing)

  /**
   * Param to enable scribing of high quality candidate scores
   */
  object EnableHighQualityCandidateScoresScribing
      etwittertends BooleanDeciderParam(DeciderKey.enableHighQualityCandidateScoresScribing)

  /**
   * Decider controlled param to pNeg multimodal predictions for F1 tweets
   */
  object EnablePnegMultimodalPredictionForF1Tweets
      etwittertends BooleanDeciderParam(DeciderKey.enablePnegMultimodalPredictionForF1Tweets)

  /**
   * Decider controlled param to scribe oonFav score for F1 tweets
   */
  object EnableScribeOonFavScoreForF1Tweets
      etwittertends BooleanDeciderParam(DeciderKey.enableScribingOonFavScoreForF1Tweets)

  /**
   * Param to enable htl user aggregates etwittertended hydration
   */
  object EnableHtlOfflineUserAggregatesEtwittertendedHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableHtlOfflineUserAggregateEtwittertendedFeaturesHydration)

  /**
   * Param to enable predicate detailed info scribing
   */
  object EnablePredicateDetailedInfoScribing
      etwittertends BooleanDeciderParam(DeciderKey.enablePredicateDetailedInfoScribing)

  /**
   * Param to enable predicate detailed info scribing
   */
  object EnablePushCapInfoScribing
      etwittertends BooleanDeciderParam(DeciderKey.enablePredicateDetailedInfoScribing)

  /**
   * Param to enable user signal language feature hydration
   */
  object EnableUserSignalLanguageFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserSignalLanguageFeatureHydration)

  /**
   * Param to enable user preferred language feature hydration
   */
  object EnableUserPreferredLanguageFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserPreferredLanguageFeatureHydration)

  /**
   * Param to enable ner erg feature hydration
   */
  object EnableNerErgFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableNerErgFeaturesHydration)

  /**
   * Param to enable inline action on push copy for Android
   */
  object MRAndroidInlineActionOnPushCopyParam etwittertends Param[Boolean](default = true)

  /**
   * Param to enable hydrating mr user semantic core embedding features
   * */
  object EnableMrUserSemanticCoreFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserSemanticCoreFeaturesHydration)

  /**
   * Param to enable hydrating mr user semantic core embedding features filtered by 0.0000001
   * */
  object EnableMrUserSemanticCoreNoZeroFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserSemanticCoreNoZeroFeaturesHydration)

  /*
   * Param to enable days since user's recent resurrection features hydration
   */
  object EnableDaysSinceRecentResurrectionFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableDaysSinceRecentResurrectionFeatureHydration)

  /*
   * Param to enable days since user past aggregates features hydration
   */
  object EnableUserPastAggregatesFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserPastAggregatesFeatureHydration)

  /*
   * Param to enable mr user simcluster features (v2020) hydration
   * */
  object EnableMrUserSimclusterV2020FeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserSimclusterV2020FeaturesHydration)

  /*
   * Param to enable mr user simcluster features (v2020) hydration
   * */
  object EnableMrUserSimclusterV2020NoZeroFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserSimclusterV2020NoZeroFeaturesHydration)

  /*
   * Param to enable HTL topic engagement realtime aggregate features
   * */
  object EnableTopicEngagementRealTimeAggregatesFeatureHydration
      etwittertends BooleanDeciderParam(
        DeciderKey.enableTopicEngagementRealTimeAggregatesFeatureHydration)

  object EnableUserTopicAggregatesFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserTopicAggregatesFeatureHydration)

  /**
   * Param to enable user author RTA feature hydration
   */
  object EnableHtlUserAuthorRTAFeaturesFromFeatureStoreHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableHtlUserAuthorRealTimeAggregateFeatureHydration)

  /**
   * Param to enable duration since last visit features
   */
  object EnableDurationSinceLastVisitFeatures
      etwittertends BooleanDeciderParam(DeciderKey.enableDurationSinceLastVisitFeatureHydration)

  object EnableTweetAnnotationFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableTweetAnnotationFeatureHydration)

  /**
   * Param to Enable visibility filtering through SpaceVisibilityLibrary from SpacePredicate
   */
  object EnableSpaceVisibilityLibraryFiltering
      etwittertends BooleanDeciderParam(DeciderKey.enableSpaceVisibilityLibraryFiltering)

  /*
   * Param to enable user topic follow feature set hydration
   * */
  object EnableUserTopicFollowFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserTopicFollowFeatureSet)

  /*
   * Param to enable onboarding new user feature set hydration
   * */
  object EnableOnboardingNewUserFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableOnboardingNewUserFeatureSet)

  /*
   * Param to enable mr user author sparse continuous feature set hydration
   * */
  object EnableMrUserAuthorSparseContFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserAuthorSparseContFeatureSet)

  /*
   * Param to enable mr user topic sparse continuous feature set hydration
   * */
  object EnableMrUserTopicSparseContFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserTopicSparseContFeatureSet)

  /*
   * Param to enable penguin language feature set hydration
   * */
  object EnableUserPenguinLanguageFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserPenguinLanguageFeatureSet)

  /*
   * Param to enable user engaged tweet tokens feature hydration
   * */
  object EnableMrUserEngagedTweetTokensFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserEngagedTweetTokensFeaturesHydration)

  /*
   * Param to enable candidate tweet tokens feature hydration
   * */
  object EnableMrCandidateTweetTokensFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrCandidateTweetTokensFeaturesHydration)

  /*
   * Param to enable mr user hashspace embedding feature set hydration
   * */
  object EnableMrUserHashspaceEmbeddingFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserHashspaceEmbeddingFeatureSet)

  /*
   * Param to enable mr tweet sentiment feature set hydration
   * */
  object EnableMrTweetSentimentFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrTweetSentimentFeatureSet)

  /*
   * Param to enable mr tweet_author aggregates feature set hydration
   * */
  object EnableMrTweetAuthorAggregatesFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrTweetAuthorAggregatesFeatureSet)

  /**
   * Param to enable twistly aggregated features
   */
  object EnableTwistlyAggregatesFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableTwistlyAggregatesFeatureHydration)

  /**
   * Param to enable tweet twhin favoriate features
   */
  object EnableTweetTwHINFavFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableTweetTwHINFavFeaturesHydration)

  /*
   * Param to enable mr user geo feature set hydration
   * */
  object EnableUserGeoFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserGeoFeatureSet)

  /*
   * Param to enable mr author geo feature set hydration
   * */
  object EnableAuthorGeoFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableAuthorGeoFeatureSet)

  /*
   * Param to ramp up mr user geo feature set hydration
   * */
  object RampupUserGeoFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.rampupUserGeoFeatureSet)

  /*
   * Param to ramp up mr author geo feature set hydration
   * */
  object RampupAuthorGeoFeatureSetHydration
      etwittertends BooleanDeciderParam(DeciderKey.rampupAuthorGeoFeatureSet)

  /*
   *  Decider controlled param to enable Pop Geo Tweets
   * */
  object PopGeoCandidatesDecider etwittertends BooleanDeciderParam(DeciderKey.enablePopGeoTweets)

  /**
   * Decider controlled param to enable Trip Geo Tweets
   */
  object TripGeoTweetCandidatesDecider
      etwittertends BooleanDeciderParam(DeciderKey.enableTripGeoTweetCandidates)

  /**
   * Decider controlled param to enable ContentRecommenderMitwittererAdaptor
   */
  object ContentRecommenderMitwittererAdaptorDecider
      etwittertends BooleanDeciderParam(DeciderKey.enableContentRecommenderMitwittererAdaptor)

  /**
   * Decider controlled param to enable GenericCandidateAdaptor
   */
  object GenericCandidateAdaptorDecider
      etwittertends BooleanDeciderParam(DeciderKey.enableGenericCandidateAdaptor)

  /**
   * Decider controlled param to enable dark traffic to ContentMitwitterer for Trip Geo Tweets
   */
  object TripGeoTweetContentMitwittererDarkTrafficDecider
      etwittertends BooleanDeciderParam(DeciderKey.enableTripGeoTweetContentMitwittererDarkTraffic)

  /*
   *  Decider controlled param to enable Pop Geo Tweets
   * */
  object TrendsCandidateDecider etwittertends BooleanDeciderParam(DeciderKey.enableTrendsTweets)

  /*
   *  Decider controlled param to enable INS Traffic
   **/
  object EnableInsTrafficDecider etwittertends BooleanDeciderParam(DeciderKey.enableInsTraffic)

  /**
   * Param to enable assigning pushcap with ML predictions (read from MH table).
   * Disabling will fallback to only use heuristics and default values.
   */
  object EnableModelBasedPushcapAssignments
      etwittertends BooleanDeciderParam(DeciderKey.enableModelBasedPushcapAssignments)

  /**
   * Param to enable twhin user engagement feature hydration
   */
  object EnableTwHINUserEngagementFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableTwHINUserEngagementFeaturesHydration)

  /**
   * Param to enable twhin user follow feature hydration
   */
  object EnableTwHINUserFollowFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableTwHINUserFollowFeaturesHydration)

  /**
   * Param to enable twhin author follow feature hydration
   */
  object EnableTwHINAuthorFollowFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableTwHINAuthorFollowFeaturesHydration)

  /**
   * Param to enable calls to the IsTweetTranslatable strato column
   */
  object EnableIsTweetTranslatableCheck
      etwittertends BooleanDeciderParam(DeciderKey.enableIsTweetTranslatable)

  /**
   * Decider controlled param to enable mr tweet simcluster feature set hydration
   */
  object EnableMrTweetSimClusterFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrTweetSimClusterFeatureSet)

  /**
   * Decider controlled param to enable real graph v2 feature set hydration
   */
  object EnableRealGraphV2FeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableRealGraphV2FeatureHydration)

  /**
   * Decider controlled param to enable Tweet BeT feature set hydration
   */
  object EnableTweetBeTFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableTweetBeTFeatureHydration)

  /**
   * Decider controlled param to enable mr user tweet topic feature set hydration
   */
  object EnableMrOfflineUserTweetTopicAggregateHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrOfflineUserTweetTopicAggregate)

  /**
   * Decider controlled param to enable mr tweet simcluster feature set hydration
   */
  object EnableMrOfflineUserTweetSimClusterAggregateHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrOfflineUserTweetSimClusterAggregate)

  /**
   * Decider controlled param to enable user send time features
   */
  object EnableUserSendTimeFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserSendTimeFeatureHydration)

  /**
   * Decider controlled param to enable mr user utc send time aggregate features
   */
  object EnableMrUserUtcSendTimeAggregateFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserUtcSendTimeAggregateFeaturesHydration)

  /**
   * Decider controlled param to enable mr user local send time aggregate features
   */
  object EnableMrUserLocalSendTimeAggregateFeaturesHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableMrUserLocalSendTimeAggregateFeaturesHydration)

  /**
   * Decider controlled param to enable BQML report model predictions for F1 tweets
   */
  object EnableBqmlReportModelPredictionForF1Tweets
      etwittertends BooleanDeciderParam(DeciderKey.enableBqmlReportModelPredictionForF1Tweets)

  /**
   * Decider controlled param to enable user Twhin embedding feature hydration
   */
  object EnableUserTwhinEmbeddingFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableUserTwhinEmbeddingFeatureHydration)

  /**
   * Decider controlled param to enable author follow Twhin embedding feature hydration
   */
  object EnableAuthorFollowTwhinEmbeddingFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableAuthorFollowTwhinEmbeddingFeatureHydration)

  object EnableScribingMLFeaturesAsDataRecord
      etwittertends BooleanDeciderParam(DeciderKey.enableScribingMLFeaturesAsDataRecord)

  /**
   * Decider controlled param to enable feature hydration for Verified related feature
   */
  object EnableAuthorVerifiedFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableAuthorVerifiedFeatureHydration)

  /**
   * Decider controlled param to enable feature hydration for creator subscription related feature
   */
  object EnableAuthorCreatorSubscriptionFeatureHydration
      etwittertends BooleanDeciderParam(DeciderKey.enableAuthorCreatorSubscriptionFeatureHydration)

  /**
   * Decider controlled param to direct MH+Memcache hydration for the UserFeaturesDataset
   */
  object EnableDirectHydrationForUserFeatures
      etwittertends BooleanDeciderParam(DeciderKey.enableDirectHydrationForUserFeatures)
}
