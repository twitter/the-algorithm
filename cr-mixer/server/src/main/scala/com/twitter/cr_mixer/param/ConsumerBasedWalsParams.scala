package com.twitter.cr_mixer.param

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

object ConsumerBasedWalsParams {

  object EnableSourceParam
      extends FSParam[Boolean](
        name = "consumer_based_wals_enable_source",
        default = false
      )

  object ModelNameParam
      extends FSParam[String](
        name = "consumer_based_wals_model_name",
        default = "model_0"
      )

  object WilyNsNameParam
      extends FSParam[String](
        name = "consumer_based_wals_wily_ns_name",
        default = ""
      )

  object ModelInputNameParam
      extends FSParam[String](
        name = "consumer_based_wals_model_input_name",
        default = "examples"
      )

  object ModelOutputNameParam
      extends FSParam[String](
        name = "consumer_based_wals_model_output_name",
        default = "all_tweet_ids"
      )

  object ModelSignatureNameParam
      extends FSParam[String](
        name = "consumer_based_wals_model_signature_name",
        default = "serving_default"
      )

  object MaxTweetSignalAgeHoursParam
      extends FSBoundedParam[Duration](
        name = "consumer_based_wals_max_tweet_signal_age_hours",
        default = 72.hours,
        min = 1.hours,
        max = 720.hours
      )
      with HasDurationConversion {

    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    ModelNameParam,
    ModelInputNameParam,
    ModelOutputNameParam,
    ModelSignatureNameParam,
    MaxTweetSignalAgeHoursParam,
    WilyNsNameParam,
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam,
    )
    val stringOverrides = FeatureSwitchOverrideUtil.getStringFSOverrides(
      ModelNameParam,
      ModelInputNameParam,
      ModelOutputNameParam,
      ModelSignatureNameParam,
      WilyNsNameParam
    )

    val boundedDurationFSOverrides =
      FeatureSwitchOverrideUtil.getBoundedDurationFSOverrides(MaxTweetSignalAgeHoursParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(stringOverrides: _*)
      .set(boundedDurationFSOverrides: _*)
      .build()
  }
}
