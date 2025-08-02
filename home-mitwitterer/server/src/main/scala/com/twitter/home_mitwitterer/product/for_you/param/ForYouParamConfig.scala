package com.twitter.home_mitwitterer.product.for_you.param

import com.twitter.home_mitwitterer.param.decider.DeciderKey
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam._
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ForYouParamConfig @Inject() () etwittertends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableForYouProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val booleanDeciderOverrides = Seq(
    EnableScoredTweetsCandidatePipelineParam
  )

  override val booleanFSOverrides = Seq(
    ClearCacheOnPtr.EnableParam,
    EnableFlipInjectionModuleCandidatePipelineParam,
    EnablePushToHomeMitwittererPipelineParam,
    EnableScoredTweetsMitwittererPipelineParam,
    EnableServedCandidateKafkaPublishingParam,
    EnableTimelineScorerCandidatePipelineParam,
    EnableTopicSocialContetwittertFilterParam,
    EnableVerifiedAuthorSocialContetwittertBypassParam,
    EnableWhoToFollowCandidatePipelineParam,
    EnableWhoToSubscribeCandidatePipelineParam,
    EnableTweetPreviewsCandidatePipelineParam,
    EnableClearCacheOnPushToHome
  )

  override val boundedIntFSOverrides = Seq(
    AdsNumOrganicItemsParam,
    ClearCacheOnPtr.MinEntriesParam,
    FlipInlineInjectionModulePosition,
    ServerMatwitterResultsParam,
    WhoToFollowPositionParam,
    WhoToSubscribePositionParam,
    TweetPreviewsPositionParam,
    TweetPreviewsMatwitterCandidatesParam
  )

  override val stringFSOverrides = Seq(
    WhoToFollowDisplayLocationParam,
    EtwitterperimentStatsParam
  )

  override val boundedDurationFSOverrides = Seq(
    WhoToFollowMinInjectionIntervalParam,
    WhoToSubscribeMinInjectionIntervalParam,
    TweetPreviewsMinInjectionIntervalParam
  )

  override val enumFSOverrides = Seq(
    WhoToFollowDisplayTypeIdParam,
    WhoToSubscribeDisplayTypeIdParam
  )
}
