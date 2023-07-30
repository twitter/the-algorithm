package com.X.cr_mixer.param

import com.X.finagle.stats.NullStatsReceiver
import com.X.logging.Logger
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSEnumParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param
import com.X.usersignalservice.thriftscala.SignalType

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
