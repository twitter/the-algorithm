package com.X.cr_mixer.param

import com.X.finagle.stats.NullStatsReceiver
import com.X.logging.Logger
import com.X.usersignalservice.thriftscala.SignalType
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSEnumParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object RepeatedProfileVisitsParams {
  object ProfileMinVisitParam extends Enumeration {
    protected case class SignalTypeValue(signalType: SignalType) extends super.Val
    import scala.language.implicitConversions
    implicit def valueToSignalTypeValue(x: Value): SignalTypeValue =
      x.asInstanceOf[SignalTypeValue]

    val TotalVisitsInPast180Days = SignalTypeValue(SignalType.RepeatedProfileVisit180dMinVisit6V1)
    val TotalVisitsInPast90Days = SignalTypeValue(SignalType.RepeatedProfileVisit90dMinVisit6V1)
    val TotalVisitsInPast14Days = SignalTypeValue(SignalType.RepeatedProfileVisit14dMinVisit2V1)
    val TotalVisitsInPast180DaysNoNegative = SignalTypeValue(
      SignalType.RepeatedProfileVisit180dMinVisit6V1NoNegative)
    val TotalVisitsInPast90DaysNoNegative = SignalTypeValue(
      SignalType.RepeatedProfileVisit90dMinVisit6V1NoNegative)
    val TotalVisitsInPast14DaysNoNegative = SignalTypeValue(
      SignalType.RepeatedProfileVisit14dMinVisit2V1NoNegative)
  }

  object EnableSourceParam
      extends FSParam[Boolean](
        name = "twistly_repeatedprofilevisits_enable_source",
        default = true
      )

  object MinScoreParam
      extends FSBoundedParam[Double](
        name = "twistly_repeatedprofilevisits_min_score",
        default = 0.5,
        min = 0.0,
        max = 1.0
      )

  object ProfileMinVisitType
      extends FSEnumParam[ProfileMinVisitParam.type](
        name = "twistly_repeatedprofilevisits_min_visit_type_id",
        default = ProfileMinVisitParam.TotalVisitsInPast14Days,
        enum = ProfileMinVisitParam
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(EnableSourceParam, ProfileMinVisitType)

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam
    )

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      ProfileMinVisitType
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(enumOverrides: _*)
      .build()
  }
}
