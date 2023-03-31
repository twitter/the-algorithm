package com.twitter.visibility.models

import com.twitter.gizmoduck.{thriftscala => t}
import com.twitter.util.Time
import com.twitter.visibility.util.NamingUtils

sealed trait UserLabelValue extends SafetyLabelType {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

case class UserLabel(
  id: Long,
  createdAt: Time,
  createdBy: String,
  labelValue: UserLabelValue,
  source: Option[LabelSource] = None)

object UserLabelValue extends SafetyLabelType {

  private lazy val nameToValueMap: Map[String, UserLabelValue] =
    List.map(l => l.name.toLowerCase -> l).toMap
  def fromName(name: String): Option[UserLabelValue] = nameToValueMap.get(name.toLowerCase)

  private val UnknownThriftUserLabelValue =
    t.LabelValue.EnumUnknownLabelValue(UnknownEnumValue)

  private lazy val thriftToModelMap: Map[t.LabelValue, UserLabelValue] = Map(
    t.LabelValue.Abusive -> Abusive,
    t.LabelValue.AbusiveHighRecall -> AbusiveHighRecall,
    t.LabelValue.AgathaSpamTopUser -> AgathaSpamTopUser,
    t.LabelValue.BirdwatchDisabled -> BirdwatchDisabled,
    t.LabelValue.BlinkBad -> BlinkBad,
    t.LabelValue.BlinkQuestionable -> BlinkQuestionable,
    t.LabelValue.BlinkWorst -> BlinkWorst,
    t.LabelValue.Compromised -> Compromised,
    t.LabelValue.DelayedRemediation -> DelayedRemediation,
    t.LabelValue.DoNotCharge -> DoNotCharge,
    t.LabelValue.DoNotAmplify -> DoNotAmplify,
    t.LabelValue.DownrankSpamReply -> DownrankSpamReply,
    t.LabelValue.DuplicateContent -> DuplicateContent,
    t.LabelValue.EngagementSpammer -> EngagementSpammer,
    t.LabelValue.EngagementSpammerHighRecall -> EngagementSpammerHighRecall,
    t.LabelValue.ExperimentalPfmUser1 -> ExperimentalPfmUser1,
    t.LabelValue.ExperimentalPfmUser2 -> ExperimentalPfmUser2,
    t.LabelValue.ExperimentalPfmUser3 -> ExperimentalPfmUser3,
    t.LabelValue.ExperimentalPfmUser4 -> ExperimentalPfmUser4,
    t.LabelValue.ExperimentalSeh1 -> ExperimentalSeh1,
    t.LabelValue.ExperimentalSeh2 -> ExperimentalSeh2,
    t.LabelValue.ExperimentalSeh3 -> ExperimentalSeh3,
    t.LabelValue.ExperimentalSehUser4 -> ExperimentalSehUser4,
    t.LabelValue.ExperimentalSehUser5 -> ExperimentalSehUser5,
    t.LabelValue.ExperimentalSensitiveIllegal1 -> ExperimentalSensitiveIllegal1,
    t.LabelValue.ExperimentalSensitiveIllegal2 -> ExperimentalSensitiveIllegal2,
    t.LabelValue.FakeSignupDeferredRemediation -> FakeSignupDeferredRemediation,
    t.LabelValue.FakeSignupHoldback -> FakeSignupHoldback,
    t.LabelValue.GoreAndViolenceHighPrecision -> GoreAndViolenceHighPrecision,
    t.LabelValue.GoreAndViolenceReportedHeuristics -> GoreAndViolenceReportedHeuristics,
    t.LabelValue.HealthExperimentation1 -> HealthExperimentation1,
    t.LabelValue.HealthExperimentation2 -> HealthExperimentation2,
    t.LabelValue.HighRiskVerification -> HighRiskVerification,
    t.LabelValue.LikelyIvs -> LikelyIvs,
    t.LabelValue.LiveLowQuality -> LiveLowQuality,
    t.LabelValue.LowQuality -> LowQuality,
    t.LabelValue.LowQualityHighRecall -> LowQualityHighRecall,
    t.LabelValue.NotGraduated -> NotGraduated,
    t.LabelValue.NotificationSpamHeuristics -> NotificationSpamHeuristics,
    t.LabelValue.NsfwAvatarImage -> NsfwAvatarImage,
    t.LabelValue.NsfwBannerImage -> NsfwBannerImage,
    t.LabelValue.NsfwHighPrecision -> NsfwHighPrecision,
    t.LabelValue.NsfwHighRecall -> NsfwHighRecall,
    t.LabelValue.NsfwNearPerfect -> NsfwNearPerfect,
    t.LabelValue.NsfwReportedHeuristics -> NsfwReportedHeuristics,
    t.LabelValue.NsfwSensitive -> NsfwSensitive,
    t.LabelValue.NsfwText -> NsfwText,
    t.LabelValue.ReadOnly -> ReadOnly,
    t.LabelValue.RecentAbuseStrike -> RecentAbuseStrike,
    t.LabelValue.RecentMisinfoStrike -> RecentMisinfoStrike,
    t.LabelValue.RecentProfileModification -> RecentProfileModification,
    t.LabelValue.RecentSuspension -> RecentSuspension,
    t.LabelValue.RecommendationsBlacklist -> RecommendationsBlacklist,
    t.LabelValue.SearchBlacklist -> SearchBlacklist,
    t.LabelValue.SoftReadOnly -> SoftReadOnly,
    t.LabelValue.SpamHighRecall -> SpamHighRecall,
    t.LabelValue.SpammyUserModelHighPrecision -> SpammyUserModelHighPrecision,
    t.LabelValue.StateMediaAccount -> StateMediaAccount,
    t.LabelValue.TsViolation -> TsViolation,
    t.LabelValue.UnconfirmedEmailSignup -> UnconfirmedEmailSignup,
    t.LabelValue.LegalOpsCase -> LegalOpsCase,
    t.LabelValue.AutomationHighRecall -> Deprecated,
    t.LabelValue.AutomationHighRecallHoldback -> Deprecated,
    t.LabelValue.BouncerUserFiltered -> Deprecated,
    t.LabelValue.DeprecatedListBannerPdna -> Deprecated,
    t.LabelValue.DeprecatedMigration50 -> Deprecated,
    t.LabelValue.DmSpammer -> Deprecated,
    t.LabelValue.DuplicateContentHoldback -> Deprecated,
    t.LabelValue.FakeAccountExperiment -> Deprecated,
    t.LabelValue.FakeAccountReadonly -> Deprecated,
    t.LabelValue.FakeAccountRecaptcha -> Deprecated,
    t.LabelValue.FakeAccountSspc -> Deprecated,
    t.LabelValue.FakeAccountVoiceReadonly -> Deprecated,
    t.LabelValue.FakeEngagement -> Deprecated,
    t.LabelValue.HasBeenSuspended -> Deprecated,
    t.LabelValue.HighProfile -> Deprecated,
    t.LabelValue.NotificationsSpike -> Deprecated,
    t.LabelValue.NsfaProfileHighRecall -> Deprecated,
    t.LabelValue.NsfwUserName -> Deprecated,
    t.LabelValue.PotentiallyCompromised -> Deprecated,
    t.LabelValue.ProfileAdsBlacklist -> Deprecated,
    t.LabelValue.RatelimitDms -> Deprecated,
    t.LabelValue.RatelimitFavorites -> Deprecated,
    t.LabelValue.RatelimitFollows -> Deprecated,
    t.LabelValue.RatelimitRetweets -> Deprecated,
    t.LabelValue.RatelimitTweets -> Deprecated,
    t.LabelValue.RecentCompromised -> Deprecated,
    t.LabelValue.RevenueOnlyHsSignal -> Deprecated,
    t.LabelValue.SearchBlacklistHoldback -> Deprecated,
    t.LabelValue.SpamHighRecallHoldback -> Deprecated,
    t.LabelValue.SpamRepeatOffender -> Deprecated,
    t.LabelValue.SpammerExperiment -> Deprecated,
    t.LabelValue.TrendBlacklist -> Deprecated,
    t.LabelValue.VerifiedDeceptiveIdentity -> Deprecated,
    t.LabelValue.BrandSafetyNsfaAggregate -> Deprecated,
    t.LabelValue.Pcf -> Deprecated,
    t.LabelValue.Reserved97 -> Deprecated,
    t.LabelValue.Reserved98 -> Deprecated,
    t.LabelValue.Reserved99 -> Deprecated,
    t.LabelValue.Reserved100 -> Deprecated,
    t.LabelValue.Reserved101 -> Deprecated,
    t.LabelValue.Reserved102 -> Deprecated,
    t.LabelValue.Reserved103 -> Deprecated,
    t.LabelValue.Reserved104 -> Deprecated,
    t.LabelValue.Reserved105 -> Deprecated,
    t.LabelValue.Reserved106 -> Deprecated
  )

  private lazy val modelToThriftMap: Map[UserLabelValue, t.LabelValue] =
    (for ((k, v) <- thriftToModelMap) yield (v, k)) ++ Map(
      Deprecated -> t.LabelValue.EnumUnknownLabelValue(DeprecatedEnumValue),
    )

  case object Abusive extends UserLabelValue
  case object AbusiveHighRecall extends UserLabelValue
  case object AgathaSpamTopUser extends UserLabelValue
  case object BirdwatchDisabled extends UserLabelValue
  case object BlinkBad extends UserLabelValue
  case object BlinkQuestionable extends UserLabelValue
  case object BlinkWorst extends UserLabelValue
  case object Compromised extends UserLabelValue
  case object DelayedRemediation extends UserLabelValue
  case object DoNotAmplify extends UserLabelValue
  case object DoNotCharge extends UserLabelValue
  case object DownrankSpamReply extends UserLabelValue
  case object DuplicateContent extends UserLabelValue
  case object EngagementSpammer extends UserLabelValue
  case object EngagementSpammerHighRecall extends UserLabelValue
  case object ExperimentalPfmUser1 extends UserLabelValue
  case object ExperimentalPfmUser2 extends UserLabelValue
  case object ExperimentalPfmUser3 extends UserLabelValue
  case object ExperimentalPfmUser4 extends UserLabelValue
  case object ExperimentalSeh1 extends UserLabelValue
  case object ExperimentalSeh2 extends UserLabelValue
  case object ExperimentalSeh3 extends UserLabelValue
  case object ExperimentalSehUser4 extends UserLabelValue
  case object ExperimentalSehUser5 extends UserLabelValue
  case object ExperimentalSensitiveIllegal1 extends UserLabelValue
  case object ExperimentalSensitiveIllegal2 extends UserLabelValue
  case object FakeSignupDeferredRemediation extends UserLabelValue
  case object FakeSignupHoldback extends UserLabelValue
  case object GoreAndViolenceHighPrecision extends UserLabelValue
  case object GoreAndViolenceReportedHeuristics extends UserLabelValue
  case object HealthExperimentation1 extends UserLabelValue
  case object HealthExperimentation2 extends UserLabelValue
  case object HighRiskVerification extends UserLabelValue
  case object LegalOpsCase extends UserLabelValue
  case object LikelyIvs extends UserLabelValue
  case object LiveLowQuality extends UserLabelValue
  case object LowQuality extends UserLabelValue
  case object LowQualityHighRecall extends UserLabelValue
  case object NotificationSpamHeuristics extends UserLabelValue
  case object NotGraduated extends UserLabelValue
  case object NsfwAvatarImage extends UserLabelValue
  case object NsfwBannerImage extends UserLabelValue
  case object NsfwHighPrecision extends UserLabelValue
  case object NsfwHighRecall extends UserLabelValue
  case object NsfwNearPerfect extends UserLabelValue
  case object NsfwReportedHeuristics extends UserLabelValue
  case object NsfwSensitive extends UserLabelValue
  case object NsfwText extends UserLabelValue
  case object ReadOnly extends UserLabelValue
  case object RecentAbuseStrike extends UserLabelValue
  case object RecentProfileModification extends UserLabelValue
  case object RecentMisinfoStrike extends UserLabelValue
  case object RecentSuspension extends UserLabelValue
  case object RecommendationsBlacklist extends UserLabelValue
  case object SearchBlacklist extends UserLabelValue
  case object SoftReadOnly extends UserLabelValue
  case object SpamHighRecall extends UserLabelValue
  case object SpammyUserModelHighPrecision extends UserLabelValue
  case object StateMediaAccount extends UserLabelValue
  case object TsViolation extends UserLabelValue
  case object UnconfirmedEmailSignup extends UserLabelValue

  case object Deprecated extends UserLabelValue
  case object Unknown extends UserLabelValue

  def fromThrift(userLabelValue: t.LabelValue): UserLabelValue = {
    thriftToModelMap.get(userLabelValue) match {
      case Some(safetyLabelType) => safetyLabelType
      case _ =>
        userLabelValue match {
          case t.LabelValue.EnumUnknownLabelValue(DeprecatedEnumValue) => Deprecated
          case _ =>
            Unknown
        }
    }
  }

  def toThrift(userLabelValue: UserLabelValue): t.LabelValue =
    modelToThriftMap.get((userLabelValue)).getOrElse(UnknownThriftUserLabelValue)

  val List: List[UserLabelValue] = t.LabelValue.list.map(fromThrift)
}

object UserLabel {
  def fromThrift(userLabel: t.Label): UserLabel = {
    UserLabel(
      userLabel.id,
      Time.fromMilliseconds(userLabel.createdAtMsec),
      userLabel.byUser,
      UserLabelValue.fromThrift(userLabel.labelValue),
      userLabel.source.flatMap(LabelSource.fromString)
    )
  }

  def toThrift(userLabel: UserLabel): t.Label = {
    t.Label(
      userLabel.id,
      UserLabelValue.toThrift(userLabel.labelValue),
      userLabel.createdAt.inMillis,
      byUser = userLabel.createdBy,
      source = userLabel.source.map(_.name)
    )
  }
}
