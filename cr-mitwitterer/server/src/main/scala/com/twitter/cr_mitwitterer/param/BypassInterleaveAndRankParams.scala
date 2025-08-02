package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object BypassInterleaveAndRankParams {
  object EnableTwhinCollabFilterBypassParam
      etwittertends FSParam[Boolean](
        name = "bypass_interleave_and_rank_twhin_collab_filter",
        default = false
      )

  object EnableTwoTowerBypassParam
      etwittertends FSParam[Boolean](
        name = "bypass_interleave_and_rank_two_tower",
        default = false
      )

  object EnableConsumerBasedTwhinBypassParam
      etwittertends FSParam[Boolean](
        name = "bypass_interleave_and_rank_consumer_based_twhin",
        default = false
      )

  object EnableConsumerBasedWalsBypassParam
      etwittertends FSParam[Boolean](
        name = "bypass_interleave_and_rank_consumer_based_wals",
        default = false
      )

  object TwhinCollabFilterBypassPercentageParam
      etwittertends FSBoundedParam[Double](
        name = "bypass_interleave_and_rank_twhin_collab_filter_percentage",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  object TwoTowerBypassPercentageParam
      etwittertends FSBoundedParam[Double](
        name = "bypass_interleave_and_rank_two_tower_percentage",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  object ConsumerBasedTwhinBypassPercentageParam
      etwittertends FSBoundedParam[Double](
        name = "bypass_interleave_and_rank_consumer_based_twhin_percentage",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  object ConsumerBasedWalsBypassPercentageParam
      etwittertends FSBoundedParam[Double](
        name = "bypass_interleave_and_rank_consumer_based_wals_percentage",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableTwhinCollabFilterBypassParam,
    EnableTwoTowerBypassParam,
    EnableConsumerBasedTwhinBypassParam,
    EnableConsumerBasedWalsBypassParam,
    TwhinCollabFilterBypassPercentageParam,
    TwoTowerBypassPercentageParam,
    ConsumerBasedTwhinBypassPercentageParam,
    ConsumerBasedWalsBypassPercentageParam,
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableTwhinCollabFilterBypassParam,
      EnableTwoTowerBypassParam,
      EnableConsumerBasedTwhinBypassParam,
      EnableConsumerBasedWalsBypassParam,
    )

    val doubleOverrides = FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(
      TwhinCollabFilterBypassPercentageParam,
      TwoTowerBypassPercentageParam,
      ConsumerBasedTwhinBypassPercentageParam,
      ConsumerBasedWalsBypassPercentageParam,
    )
    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}
