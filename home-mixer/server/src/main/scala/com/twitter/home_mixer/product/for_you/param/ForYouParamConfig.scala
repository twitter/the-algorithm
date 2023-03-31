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

  override val booleanDeciderOverrides = Seq(
    EnableScoredTweetsCandidatePipelineParam
  )

  override val booleanFSOverrides = Seq(
    EnableFlipInjectionModuleCandidatePipelineParam,
    EnableWhoToFollowCandidatePipelineParam,
    EnableScoredTweetsMixerPipelineParam,
    ClearCacheOnPtr.EnableParam,
    EnableTimelineScorerCandidatePipelineParam
  )

  override val boundedIntFSOverrides = Seq(
    ServerMaxResultsParam,
    WhoToFollowPositionParam,
    FlipInlineInjectionModulePosition,
    TimelineServiceMaxResultsParam,
    AdsNumOrganicItemsParam,
    ClearCacheOnPtr.MinEntriesParam
  )

  override val boundedDurationFSOverrides = Seq(
    WhoToFollowMinInjectionIntervalParam
  )

  override val enumFSOverrides = Seq(
    WhoToFollowDisplayTypeIdParam
  )
}
