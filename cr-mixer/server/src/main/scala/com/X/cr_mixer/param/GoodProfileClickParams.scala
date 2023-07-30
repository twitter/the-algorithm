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

object GoodProfileClickParams {

  object ClickMinDwellTimeParam extends Enumeration {
    protected case class SignalTypeValue(signalType: SignalType) extends super.Val
    import scala.language.implicitConversions
    implicit def valueToSignalTypeValue(x: Value): SignalTypeValue =
      x.asInstanceOf[SignalTypeValue]

    val TotalDwellTime10s = SignalTypeValue(SignalType.GoodProfileClick)
    val TotalDwellTime20s = SignalTypeValue(SignalType.GoodProfileClick20s)
    val TotalDwellTime30s = SignalTypeValue(SignalType.GoodProfileClick30s)

  }

  object EnableSourceParam
      extends FSParam[Boolean](
        name = "signal_good_profile_clicks_enable_source",
        default = false
      )

  object ClickMinDwellTimeType
      extends FSEnumParam[ClickMinDwellTimeParam.type](
        name = "signal_good_profile_clicks_min_dwelltime_type_id",
        default = ClickMinDwellTimeParam.TotalDwellTime10s,
        enum = ClickMinDwellTimeParam
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(EnableSourceParam, ClickMinDwellTimeType)

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam
    )

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      ClickMinDwellTimeType
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(enumOverrides: _*)
      .build()
  }
}
