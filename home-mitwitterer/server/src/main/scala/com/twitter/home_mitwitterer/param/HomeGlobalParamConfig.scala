package com.twitter.home_mitwitterer.param

import com.twitter.home_mitwitterer.param.HomeGlobalParams._
import com.twitter.product_mitwitterer.core.functional_component.configapi.registry.GlobalParamConfig
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Register Params that do not relate to a specific product. See GlobalParamConfig -> ParamConfig
 * for hooks to register Params based on type.
 */
@Singleton
class HomeGlobalParamConfig @Inject() () etwittertends GlobalParamConfig {

  override val booleanFSOverrides = Seq(
    AdsDisableInjectionBasedOnUserRoleParam,
    EnableAdvertiserBrandSafetySettingsFeatureHydratorParam,
    EnableImpressionBloomFilter,
    EnableNahFeedbackInfoParam,
    EnableNewTweetsPillAvatarsParam,
    EnableScribeServedCandidatesParam,
    EnableSendScoresToClient,
    EnableSocialContetwittertParam,
  )

  override val boundedIntFSOverrides = Seq(
    MatwitterNumberReplaceInstructionsParam,
    TimelinesPersistenceStoreMatwitterEntriesPerClient,
  )

  override val boundedDoubleFSOverrides = Seq(
    ImpressionBloomFilterFalsePositiveRateParam
  )
}
