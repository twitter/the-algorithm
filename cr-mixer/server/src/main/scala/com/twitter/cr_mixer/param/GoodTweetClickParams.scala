package com.twitter.cr_mixer.param

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

  object ClickMinDwellTimeParam extends Enumeration {
    protected case class SignalTypeValue(signalType: SignalType) extends super.Val
    import scala.language.implicitConversions
    implicit def valueToSignalTypeValue(x: Value): SignalTypeValue =
      x.asInstanceOf[SignalTypeValue]

    val TotalDwellTime2s = SignalTypeValue(SignalType.GoodTweetClick)
    val TotalDwellTime5s = SignalTypeValue(SignalType.GoodTweetClick5s)
    val TotalDwellTime10s = SignalTypeValue(SignalType.GoodTweetClick10s)
    val TotalDwellTime30s = SignalTypeValue(SignalType.GoodTweetClick30s)

  }

  object EnableSourceParam
      extends FSParam[Boolean](
        name = "signal_good_tweet_clicks_enable_source",
        default = false
      )

  object ClickMinDwellTimeType
      extends FSEnumParam[ClickMinDwellTimeParam.type](
        name = "signal_good_tweet_clicks_min_dwelltime_type_id",
        default = ClickMinDwellTimeParam.TotalDwellTime2s,
        enum = ClickMinDwellTimeParam
      )

  object MaxSignalNumParam
      extends FSBoundedParam[Int](
        name = "signal_good_tweet_clicks_max_signal_num",
        default = 15,
        min = 0,
        max = 15
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(EnableSourceParam, ClickMinDwellTimeType, MaxSignalNumParam)

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
      MaxSignalNumParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(enumOverrides: _*)
      .set(intOverrides: _*)
      .build()
  }
}
