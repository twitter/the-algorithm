package com.twitter.home_mixer.param

import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableClipEmbeddingFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableClipEmbeddingMediaUnderstandingFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableDedupClusterId88FeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableDedupClusterIdFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableGeoduckAuthorLocationHydatorParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableGrokVideoMetadataFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableImmersiveClientActionsClipEmbeddingQueryFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableImmersiveClientActionsQueryFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableLargeEmbeddingsFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableOnPremRealGraphQueryFeatures
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableRealGraphQueryFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableRealGraphViewerRelatedUsersFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableSimclustersSparseTweetFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTransformerPostEmbeddingJointBlueFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetLanguageFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetTextTokensEmbeddingFeatureScribingParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetVideoAggregatedWatchTimeFeatureScribingParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetypieContentFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetypieContentMediaEntityFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinRebuildTweetFeaturesOnlineParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinRebuildUserEngagementFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinRebuildUserPositiveFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinTweetFeaturesOnlineParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinUserNegativeFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinUserPositiveFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinVideoFeaturesOnlineParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinVideoFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableUserFavAvgTextEmbeddingsQueryFeatureParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableUserHistoryTransformerJointBlueEmbeddingFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableViewCountFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring
import com.twitter.home_mixer.param.HomeGlobalParams._
import com.twitter.product_mixer.core.functional_component.configapi.registry.GlobalParamConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Register Params that do not relate to a specific product. See GlobalParamConfig -> ParamConfig
 * for hooks to register Params based on type.
 */
@Singleton
class HomeGlobalParamConfig @Inject() () extends GlobalParamConfig {

  override val booleanDeciderOverrides = Seq(
    EnableServedCandidateFeatureKeysKafkaPublishingParam,
    FeatureHydration.EnableSimClustersSimilarityFeaturesDeciderParam,
    FeatureHydration.EnableTweetypieContentFeaturesDeciderParam,
    FeatureHydration.EnableVideoSummaryEmbeddingFeatureDeciderParam,
    FeatureHydration.EnableVideoClipEmbeddingFeatureHydrationDeciderParam,
    FeatureHydration.EnableScoredVideoTweetsUserHistoryEventsQueryFeatureHydrationDeciderParam,
    FeatureHydration.EnableVideoClipEmbeddingMediaUnderstandingFeatureHydrationDeciderParam,
    EnableCommonFeaturesDataRecordCopyDuringPldrConversionParam,
    Scoring.UseSecondaryNaviClusterParam,
    Scoring.UseGPUNaviClusterTestUsersParam
  )

  override val boundedDoubleDeciderOverrides = Seq(Scoring.NaviGPUBatchSizeParam)

  override val stringFSOverrides =
    Seq(
      Scoring.ModelNameParam,
      Scoring.ModelIdParam,
      Scoring.ProdModelIdParam,
      PostFeedbackPromptTitleParam,
      PostFeedbackPromptPositiveParam,
      PostFeedbackPromptNegativeParam,
      PostFeedbackPromptNeutralParam,
    )

  override val booleanFSOverrides = Seq(
    AdsDisableInjectionBasedOnUserRoleParam,
    EnableTweetEntityServiceMigrationParam,
    EnableTweetEntityServiceVisibilityMigrationParam,
    EnableAdvertiserBrandSafetySettingsFeatureHydratorParam,
    EnableSSPAdsBrandSafetySettingsFeatureHydratorParam,
    EnableDebugString,
    EnablePersistenceDebug,
    EnableLargeEmbeddingsFeatureHydrationParam,
    EnableNewTweetsPillAvatarsParam,
    EnableOnPremRealGraphQueryFeatures,
    EnableRealGraphQueryFeaturesParam,
    EnableRealGraphViewerRelatedUsersFeaturesParam,
    EnableScribeServedCandidatesParam,
    EnableSendScoresToClient,
    EnableSimclustersSparseTweetFeaturesParam,
    EnableCommunitiesContextParam,
    EnableSocialContextParam,
    EnableTwhinRebuildUserEngagementFeaturesParam,
    EnableTwhinRebuildUserPositiveFeaturesParam,
    EnableTwhinVideoFeaturesParam,
    EnableTwhinUserNegativeFeaturesParam,
    EnableTwhinUserPositiveFeaturesParam,
    EnableTwhinVideoFeaturesOnlineParam,
    EnableTwhinTweetFeaturesOnlineParam,
    EnableTwhinRebuildTweetFeaturesOnlineParam,
    EnableBasketballContextFeatureHydratorParam,
    EnablePostContextFeatureHydratorParam,
    EnableTransformerPostEmbeddingJointBlueFeaturesParam,
    EnableClipEmbeddingFeaturesParam,
    EnableClipEmbeddingMediaUnderstandingFeaturesParam,
    EnableUserHistoryTransformerJointBlueEmbeddingFeaturesParam,
    EnableLandingPage,
    EnableTenSecondsLogicForVQV,
    EnableImmersiveVQV,
    EnableExploreSimclustersLandingPage,
    EnableTweetLanguageFeaturesParam,
    EnableTweetypieContentFeaturesParam,
    EnableTweetypieContentMediaEntityFeaturesParam,
    EnableViewCountFeaturesParam,
    ListMandarinTweetsParams.ListMandarinTweetsEnable,
    Scoring.EnableNoNegHeuristicParam,
    Scoring.EnableNegSectionRankingParam,
    Scoring.EnableBinarySchemeForVQVParam,
    Scoring.EnableBinarySchemeForDwellParam,
    Scoring.EnableDwellOrVQVParam,
    Scoring.UseRealtimeNaviClusterParam,
    Scoring.UseGPUNaviClusterParam,
    Scoring.RequestNormalizedScoresParam,
    Scoring.AddNoiseInWeightsPerLabel,
    Scoring.EnableDailyFrozenNoisyWeights,
    Scoring.TwhinDiversityRescoringParam,
    Scoring.CategoryDiversityRescoringParam,
    Scoring.UseVideoNaviClusterParam,
    Scoring.NormalizedNegativeHead,
    Scoring.UseWeightForNegHeadParam,
    Scoring.ConstantNegativeHead,
    Scoring.UseProdInPhoenixParams.EnableProdDwellForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdFavForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdGoodClickV1ForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdGoodClickV2ForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdNegForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdProfileClickForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdReplyForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdRetweetForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdShareForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdVQVForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdOpenLinkForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdScreenshotForPhoenixParam,
    Scoring.UseProdInPhoenixParams.EnableProdBookmarkForPhoenixParam,
    EnableUserFavAvgTextEmbeddingsQueryFeatureParam,
    EnableTweetTextTokensEmbeddingFeatureScribingParam,
    EnableTweetVideoAggregatedWatchTimeFeatureScribingParam,
    EnableImmersiveClientActionsQueryFeatureHydrationParam,
    EnableImmersiveClientActionsClipEmbeddingQueryFeatureHydrationParam,
    EnableGrokVideoMetadataFeatureHydrationParam,
    EnableDedupClusterIdFeatureHydrationParam,
    EnableDedupClusterId88FeatureHydrationParam,
    EnableServedFilterAllRequests,
    EnablePinnedTweetsCarouselParam,
    EnablePostDetailsNegativeFeedbackParam,
    EnablePostFeedbackParam,
    EnablePostFollowupParam,
    EnableSlopFilter,
    EnableNsfwFilter,
    EnableSoftNsfwFilter,
    EnableGrokGoreFilter,
    EnableMinVideoDurationFilter,
    EnableMaxVideoDurationFilter,
    EnableGrokSpamFilter,
    EnableGrokViolentFilter,
    EnableClusterBasedDedupFilter,
    EnableCountryFilter,
    EnableRegionFilter,
    EnableHasMultipleMediaFilter,
    EnableClusterBased88DedupFilter,
    EnableNoClusterFilter,
    EnableSlopFilterLowSignalUsers,
    EnableSlopFilterEligibleUserStateParam,
    EnableGrokAnnotations,
    EnableTopicBasedRealTimeAggregateFeatureHydratorParam,
    EnableTopicCountryBasedRealTimeAggregateFeatureHydratorParam,
    EnableTopicEdgeAggregateFeatureHydratorParam,
    EnableAdditionalChildFeedbackParam,
    EnableGeoduckAuthorLocationHydatorParam,
    EnableTweetRTAMhOnlyParam,
    EnableTweetRTAMhFallbackParam,
    EnableTweetCountryRTAMhOnlyParam,
    EnableTweetCountryRTAMhFallbackParam,
    EnableUserRTAMhOnlyParam,
    EnableUserRTAMhFallbackParam,
    EnableUserAuthorRTAMhOnlyParam,
    EnableUserAuthorRTAMhFallbackParam,
    EnableBlockMuteReportChildFeedbackParam,
    EnablePhoenixScorerParam,
    EnableUserActionsShadowScribeParam,
  )

  override val boundedIntFSOverrides = Seq(
    MaxNumberReplaceInstructionsParam,
    TimelinesPersistenceStoreMaxEntriesPerClient,
    ExcludeServedTweetIdsNumberParam,
    IsSelectedByHeavyRankerCountParam,
    SlopMinFollowers,
    UserActionsMaxCount,
    PhoenixTimeoutInMsParam,
  )

  override val boundedLongFSOverrides =
    Seq(
      DedupHistoricalEventsTimeWindowParam,
      MinVideoDurationThresholdParam,
      MaxVideoDurationThresholdParam)
  override val boundedDoubleFSOverrides = Seq(
    SlopMaxScore,
    PostFeedbackThresholdParam,
    PostFollowupThresholdParam,
    // Model Weights
    Scoring.ModelWeights.BookmarkParam,
    Scoring.ModelWeights.Dwell0Param,
    Scoring.ModelWeights.Dwell1Param,
    Scoring.ModelWeights.Dwell2Param,
    Scoring.ModelWeights.Dwell3Param,
    Scoring.ModelWeights.Dwell4Param,
    Scoring.ModelWeights.DwellParam,
    Scoring.ModelWeights.FavParam,
    Scoring.ModelWeights.GoodClickParam,
    Scoring.ModelWeights.GoodClickV1Param,
    Scoring.ModelWeights.GoodClickV2Param,
    Scoring.ModelWeights.GoodProfileClickParam,
    Scoring.ModelWeights.NegativeFeedbackV2Param,
    Scoring.ModelWeights.OpenLinkParam,
    Scoring.ModelWeights.ProfileDwelledParam,
    Scoring.ModelWeights.ReplyEngagedByAuthorParam,
    Scoring.ModelWeights.ReplyParam,
    Scoring.ModelWeights.ReportParam,
    Scoring.ModelWeights.RetweetParam,
    Scoring.ModelWeights.ScreenshotParam,
    Scoring.ModelWeights.ShareMenuClickParam,
    Scoring.ModelWeights.ShareParam,
    Scoring.ModelWeights.StrongNegativeFeedbackParam,
    Scoring.ModelWeights.TweetDetailDwellParam,
    Scoring.ModelWeights.VideoPlayback50Param,
    Scoring.ModelWeights.VideoQualityViewImmersiveParam,
    Scoring.ModelWeights.VideoQualityViewParam,
    Scoring.ModelWeights.VideoQualityWatchParam,
    Scoring.ModelWeights.VideoWatchTimeMsParam,
    Scoring.ModelWeights.WeakNegativeFeedbackParam,
    // Model Biases
    Scoring.ModelBiases.VideoQualityViewParam,
    Scoring.ModelBiases.VideoQualityViewImmersiveParam,
    Scoring.ModelBiases.VideoQualityWatchParam,
    Scoring.NoisyWeightAlphaParam,
    Scoring.NoisyWeightBetaParam,
    Scoring.NegativeScoreConstantFilterThresholdParam,
    Scoring.NegativeScoreNormFilterThresholdParam,
    Scoring.RequestRankDecayFactorParam,
    Scoring.ScoreThresholdForVQVParam,
    Scoring.ScoreThresholdForDwellParam,
    Scoring.BinarySchemeConstantForVQVParam,
    Scoring.ImpressedMediaClusterBasedRescoringParam,
    // ModelDebiases
    Scoring.ModelDebiases.FavParam,
    Scoring.ModelDebiases.ReplyParam,
    Scoring.ModelDebiases.RetweetParam,
    Scoring.ModelDebiases.GoodClickV1Param,
    Scoring.ModelDebiases.GoodClickV2Param,
    Scoring.ModelDebiases.GoodProfileClickParam,
    Scoring.ModelDebiases.ReplyEngagedByAuthorParam,
    Scoring.ModelDebiases.VideoQualityViewParam,
    Scoring.ModelDebiases.VideoQualityViewImmersiveParam,
    Scoring.ModelDebiases.NegativeFeedbackV2Param,
    Scoring.ModelDebiases.BookmarkParam,
    Scoring.ModelDebiases.ShareParam,
    Scoring.ModelDebiases.DwellParam,
    Scoring.ModelDebiases.VideoQualityWatchParam,
    Scoring.ModelDebiases.VideoWatchTimeMsParam
  )

  override val longSetFSOverrides = Seq(
    RateLimitTestIdsParam,
    BasketballTeamAccountIdsParam,
    Scoring.AuthorListForDataCollectionParam
  )

  override val boundedDurationFSOverrides = Seq(
    FeedbackFatigueFilteringDurationParam,
    ExcludeServedTweetIdsDurationParam,
    ExcludeServedAuthorIdsDurationParam
  )

  override val enumFSOverrides = Seq(
    PhoenixInferenceClusterParam
  )

  override val longSeqFSOverrides = Seq(
    ListMandarinTweetsParams.ListMandarinTweetsLists
  )
}
