package com.twitter.home_mitwitterer.product.following.param

import com.twitter.home_mitwitterer.param.decider.DeciderKey
import com.twitter.home_mitwitterer.product.following.param.FollowingParam._
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class FollowingParamConfig @Inject() () etwittertends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableFollowingProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val booleanFSOverrides =
    Seq(
      EnableFlipInjectionModuleCandidatePipelineParam,
      EnableWhoToFollowCandidatePipelineParam,
      EnableAdsCandidatePipelineParam,
      EnableFastAds,
    )

  override val boundedIntFSOverrides = Seq(
    FlipInlineInjectionModulePosition,
    WhoToFollowPositionParam,
    ServerMatwitterResultsParam
  )

  override val stringFSOverrides = Seq(WhoToFollowDisplayLocationParam)

  override val boundedDurationFSOverrides = Seq(WhoToFollowMinInjectionIntervalParam)

  override val enumFSOverrides = Seq(WhoToFollowDisplayTypeIdParam)
}
