package com.twitter.home_mixer.product.for_you.param

import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.home_mixer.product.for_you.param.ForYouParam._
import com.twitter.product_mixer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouParamConfig @Inject() () extends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableForYouProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val booleanFSOverrides = Seq(
    ClearCache.PtrEnableParam,
    ClearCache.ColdStartEnableParam,
    ClearCache.WarmStartEnableParam,
    ClearCache.ManualRefreshEnableParam,
    ClearCache.NavigateEnableParam,
    ClearCache.ColdStartRetainViewportParam,
    EnableCommunitiesToJoinCandidatePipelineParam,
    EnableBookmarksCandidatePipelineParam,
    EnableExplorationTweetsCandidatePipelineParam,
    EnableJetfuelFramePipelineParam,
    EnableBookmarksModuleWeekendGate,
    EnablePinnedTweetsCandidatePipelineParam,
    EnableEntryPointPivotParam,
    EnableFlipInjectionModuleCandidatePipelineParam,
    EnableGrokEntryPointPivotParam,
    EnableRecommendedJobsParam,
    EnableRecommendedRecruitingOrganizationsParam,
    EnableTweetPreviewsCandidatePipelineParam,
    EnableViewerHasJobRecommendationsFeatureParam,
    EnableWhoToFollowCandidatePipelineParam,
    EnableWhoToSubscribeCandidatePipelineParam,
    EnableTrendsParam,
    EnableKeywordTrendsParam,
    EnableArticlePreviewTextHydrationParam,
    EnableForYouTimelineAdsSurface,
    EnableScoredVideoTweetsCandidatePipelineParam,
    VideoCarouselEnableFooterParam,
    VideoCarouselAllowVerticalVideos,
    VideoCarouselAllowHorizontalVideos,
    RelevancePromptEnableParam,
    Navigation.PtrEnableParam,
    Navigation.ColdStartEnableParam,
    Navigation.WarmStartEnableParam,
    Navigation.ManualRefreshEnableParam,
    Navigation.NavigateEnableParam,
    EnableAdsDebugParam,
    EnableForYouAppUpsellParam,
    EnableForYouTopicSelectorParam,
    EnableTuneFeedCandidatePipelineParam,
    EnableFollowedGrokTopicsHydrationParam,
    EnableTLSHydrationParam
  )

  override val boundedLongFSOverrides = Seq(
    ExplorationTweetsMaxFollowerCountParam
  )

  override val boundedIntFSOverrides = Seq(
    AdsNumOrganicItemsParam,
    ClearCache.MinEntriesParam,
    ExplorationTweetsTimelinePosition,
    GrokPivotModuleTimelinePosition,
    SuperbowlModuleTimelinePosition,
    VideoCarouselNumTweetCandidatesToDedupeAgainstParam,
    VideoCarouselTimelinePosition,
    VideoCarouselNumCandidates,
    MaxCommunitiesToJoinCandidatesParam,
    MaxNumberExplorationTweetsParam,
    MaxNumberKeywordTrendsParam,
    MaxRecommendedJobCandidatesParam,
    MaxRecommendedRecruitingOrganizationCandidatesParam,
    ServerMaxResultsParam,
    TweetPreviewsMaxCandidatesParam,
    RelevancePromptTweetPositionParam,
    ForYouAppUpsellPosition,
    ForYouTopicSelectorPosition,
    TuneFeedTimelinePosition,
    MinFollowingCountParam,
    MaxFollowingCountParam
  )

  override val stringFSOverrides = Seq(
    WhoToFollowDisplayLocationParam,
    ExperimentStatsParam,
    RelevancePromptTitleParam,
    RelevancePromptPositiveParam,
    RelevancePromptNegativeParam,
    RelevancePromptNeutralParam,
    ForYouAppUpsellJetfuelRouteParam,
    ForYouTopicSelectorJetfuelRouteParam,
  )

  override val boundedDurationFSOverrides = Seq(
    CommunitiesToJoinMinInjectionIntervalParam,
    RecommendedJobMinInjectionIntervalParam,
    RecommendedRecruitingOrganizationMinInjectionIntervalParam,
    WhoToFollowMinInjectionIntervalParam,
    WhoToSubscribeMinInjectionIntervalParam,
    TweetPreviewsMinInjectionIntervalParam,
    BookmarksModuleMinInjectionIntervalParam,
    InNetworkExplorationTweetsMinInjectionIntervalParam,
    PinnedTweetsModuleMinInjectionIntervalParam,
    TrendsModuleMinInjectionIntervalParam,
    KeywordTrendsModuleMinInjectionIntervalParam,
    EntryPointPivotMinInjectionIntervalParam,
    GrokEntryPointPivotMinInjectionIntervalParam,
    VideoTweetsModuleMinInjectionIntervalParam,
    RelevancePromptMinInjectionIntervalParam
  )

  override val enumFSOverrides = Seq(
    WhoToFollowDisplayTypeIdParam,
    WhoToSubscribeDisplayTypeIdParam,
    WhoToFollowUserDisplayTypeIdParam,
    CommunitiesToJoinDisplayTypeIdParam
  )

  override val booleanDeciderOverrides =
    Seq(EnableGetTweetsFromArchiveIndex)

  override val longSetFSOverrides = Seq(
    AuthorListForStatsParam,
    FollowingSportsGateUsersParam
  )
}
