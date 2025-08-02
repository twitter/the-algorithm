package com.twitter.cr_mitwitterer.param

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param
import com.twitter.usersignalservice.thriftscala.SignalType

object GoodTweetClickParams {

  object ClickMinDwellTimeParam etwittertends Enumeration {
    protected case class SignalTypeValue(signalType: SignalType) etwittertends super.Val
    import scala.language.implicitConversions
    implicit def valueToSignalTypeValue(twitter: Value): SignalTypeValue =
      twitter.asInstanceOf[SignalTypeValue]

    val TotalDwellTime2s = SignalTypeValue(SignalType.GoodTweetClick)
    val TotalDwellTime5s = SignalTypeValue(SignalType.GoodTweetClick5s)
    val TotalDwellTime10s = SignalTypeValue(SignalType.GoodTweetClick10s)
    val TotalDwellTime30s = SignalTypeValue(SignalType.GoodTweetClick30s)

  }

  object EnableSourceParam
      etwittertends FSParam[Boolean](
        name = "signal_good_tweet_clicks_enable_source",
        default = false
      )

  object ClickMinDwellTimeType
      etwittertends FSEnumParam[ClickMinDwellTimeParam.type](
        name = "signal_good_tweet_clicks_min_dwelltime_type_id",
        default = ClickMinDwellTimeParam.TotalDwellTime2s,
        enum = ClickMinDwellTimeParam
      )

  object MatwitterSignalNumParam
      etwittertends FSBoundedParam[Int](
        name = "signal_good_tweet_clicks_matwitter_signal_num",
        default = 15,
        min = 0,
        matwitter = 15
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(EnableSourceParam, ClickMinDwellTimeType, MatwitterSignalNumParam)

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam
    )

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      ClickMinDwellTimeType
    )

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MatwitterSignalNumParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(enumOverrides: _*)
      .set(intOverrides: _*)
      .build()
  }
}
