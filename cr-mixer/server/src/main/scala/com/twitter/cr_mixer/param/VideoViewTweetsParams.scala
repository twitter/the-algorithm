package com.twitter.cr_mixer.param

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param
import com.twitter.usersignalservice.thriftscala.SignalType

object VideoViewTweetsParams {
  object EnableSourceParam
      extends FSParam[Boolean](
        name = "signal_videoviewtweets_enable_source",
        default = false
      )

  object EnableSourceImpressionParam
      extends FSParam[Boolean](
        name = "signal_videoviewtweets_enableimpression_source",
        default = false
      )

  object VideoViewTweetType extends Enumeration {
    protected case class SignalTypeValue(signalType: SignalType) extends super.Val
    import scala.language.implicitConversions
    implicit def valueToSignalTypeValue(x: Value): SignalTypeValue =
      x.asInstanceOf[SignalTypeValue]

    val VideoTweetQualityView: SignalTypeValue = SignalTypeValue(SignalType.VideoView90dQualityV1)
    val VideoTweetPlayback50: SignalTypeValue = SignalTypeValue(SignalType.VideoView90dPlayback50V1)
  }

  object VideoViewTweetTypeParam
      extends FSEnumParam[VideoViewTweetType.type](
        name = "signal_videoviewtweets_videoviewtype_id",
        default = VideoViewTweetType.VideoTweetQualityView,
        enum = VideoViewTweetType
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(EnableSourceParam, EnableSourceImpressionParam, VideoViewTweetTypeParam)

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam,
      EnableSourceImpressionParam,
    )
    val enumOverrides =
      FeatureSwitchOverrideUtil.getEnumFSOverrides(
        NullStatsReceiver,
        Logger(getClass),
        VideoViewTweetTypeParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(enumOverrides: _*)
      .build()
  }

}
