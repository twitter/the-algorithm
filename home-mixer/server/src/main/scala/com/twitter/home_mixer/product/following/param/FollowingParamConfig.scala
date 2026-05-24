package com.twitter.home_mixer.product.following.param

import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.home_mixer.product.following.param.FollowingParam._
import com.twitter.product_mixer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingParamConfig @Inject() () extends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableFollowingProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val booleanFSOverrides = Seq(
    ClearCache.PtrEnableParam,
    ClearCache.ColdStartEnableParam,
    ClearCache.WarmStartEnableParam,
    ClearCache.ManualRefreshEnableParam,
    ClearCache.NavigateEnableParam,
    EnableFlipInjectionModuleCandidatePipelineParam,
    EnableWhoToFollowCandidatePipelineParam,
    EnablePostContextFeatureHydratorParam,
    EnableFastAds,
    EnableDependentAdsParam,
    EnableNavigationInstructionParam,
    Navigation.PtrEnableParam,
    Navigation.ColdStartEnableParam,
    Navigation.WarmStartEnableParam,
    Navigation.ManualRefreshEnableParam,
    Navigation.NavigateEnableParam,
  )

  override val boundedIntFSOverrides = Seq(
    ClearCache.MinEntriesParam,
    ServerMaxResultsParam
  )

  override val stringFSOverrides = Seq(WhoToFollowDisplayLocationParam)

  override val boundedDurationFSOverrides = Seq(WhoToFollowMinInjectionIntervalParam)

  override val enumFSOverrides = Seq(
    WhoToFollowDisplayTypeIdParam,
    WhoToFollowUserDisplayTypeIdParam
  )
}
