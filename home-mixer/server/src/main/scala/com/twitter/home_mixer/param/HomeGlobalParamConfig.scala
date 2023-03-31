package com.twitter.home_mixer.param

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

  override val booleanFSOverrides = Seq(
    AdsDisableInjectionBasedOnUserRoleParam,
    EnableSendScoresToClient,
    EnableNahFeedbackInfoParam,
    EnableNewTweetsPillAvatarsParam,
    EnableServedCandidateKafkaPublishingParam,
    EnableSocialContextParam,
    EnableGizmoduckAuthorSafetyFeatureHydratorParam,
    EnableAdvertiserBrandSafetySettingsFeatureHydratorParam,
    EnableFeedbackFatigueParam
  )

  override val boundedIntFSOverrides = Seq(
    MaxNumberReplaceInstructionsParam,
    TimelinesPersistenceStoreMaxEntriesPerClient,
  )

  override val boundedDoubleFSOverrides = Seq(
    BlueVerifiedAuthorInNetworkMultiplierParam,
    BlueVerifiedAuthorOutOfNetworkMultiplierParam
  )

  override val longSetFSOverrides = Seq(
    AuthorListForStatsParam
  )
}
