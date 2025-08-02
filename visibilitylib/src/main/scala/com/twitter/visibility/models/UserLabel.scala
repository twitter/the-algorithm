package com.twitter.visibility.models

import com.twitter.gizmoduck.{thriftscala => t}
import com.twitter.util.Time
import com.twitter.visibility.util.NamingUtils

sealed trait UserLabelValue etwittertends SafetyLabelType {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

case class UserLabel(
  id: Long,
  createdAt: Time,
  createdBy: String,
  labelValue: UserLabelValue,
  source: Option[LabelSource] = None)

object UserLabelValue etwittertends SafetyLabelType {

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
    t.LabelValue.EtwitterperimentalPfmUser1 -> EtwitterperimentalPfmUser1,
    t.LabelValue.EtwitterperimentalPfmUser2 -> EtwitterperimentalPfmUser2,
    t.LabelValue.EtwitterperimentalPfmUser3 -> EtwitterperimentalPfmUser3,
    t.LabelValue.EtwitterperimentalPfmUser4 -> EtwitterperimentalPfmUser4,
    t.LabelValue.EtwitterperimentalSeh1 -> EtwitterperimentalSeh1,
    t.LabelValue.EtwitterperimentalSeh2 -> EtwitterperimentalSeh2,
    t.LabelValue.EtwitterperimentalSeh3 -> EtwitterperimentalSeh3,
    t.LabelValue.EtwitterperimentalSehUser4 -> EtwitterperimentalSehUser4,
    t.LabelValue.EtwitterperimentalSehUser5 -> EtwitterperimentalSehUser5,
    t.LabelValue.EtwitterperimentalSensitiveIllegal1 -> EtwitterperimentalSensitiveIllegal1,
    t.LabelValue.EtwitterperimentalSensitiveIllegal2 -> EtwitterperimentalSensitiveIllegal2,
    t.LabelValue.FakeSignupDeferredRemediation -> FakeSignupDeferredRemediation,
    t.LabelValue.FakeSignupHoldback -> FakeSignupHoldback,
    t.LabelValue.GoreAndViolenceHighPrecision -> GoreAndViolenceHighPrecision,
    t.LabelValue.GoreAndViolenceReportedHeuristics -> GoreAndViolenceReportedHeuristics,
    t.LabelValue.HealthEtwitterperimentation1 -> HealthEtwitterperimentation1,
    t.LabelValue.HealthEtwitterperimentation2 -> HealthEtwitterperimentation2,
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
    t.LabelValue.NsfwTetwittert -> NsfwTetwittert,
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
    t.LabelValue.FakeAccountEtwitterperiment -> Deprecated,
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
    t.LabelValue.SpammerEtwitterperiment -> Deprecated,
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

  case object Abusive etwittertends UserLabelValue
  case object AbusiveHighRecall etwittertends UserLabelValue
  case object AgathaSpamTopUser etwittertends UserLabelValue
  case object BirdwatchDisabled etwittertends UserLabelValue
  case object BlinkBad etwittertends UserLabelValue
  case object BlinkQuestionable etwittertends UserLabelValue
  case object BlinkWorst etwittertends UserLabelValue
  case object Compromised etwittertends UserLabelValue
  case object DelayedRemediation etwittertends UserLabelValue
  case object DoNotAmplify etwittertends UserLabelValue
  case object DoNotCharge etwittertends UserLabelValue
  case object DownrankSpamReply etwittertends UserLabelValue
  case object DuplicateContent etwittertends UserLabelValue
  case object EngagementSpammer etwittertends UserLabelValue
  case object EngagementSpammerHighRecall etwittertends UserLabelValue
  case object EtwitterperimentalPfmUser1 etwittertends UserLabelValue
  case object EtwitterperimentalPfmUser2 etwittertends UserLabelValue
  case object EtwitterperimentalPfmUser3 etwittertends UserLabelValue
  case object EtwitterperimentalPfmUser4 etwittertends UserLabelValue
  case object EtwitterperimentalSeh1 etwittertends UserLabelValue
  case object EtwitterperimentalSeh2 etwittertends UserLabelValue
  case object EtwitterperimentalSeh3 etwittertends UserLabelValue
  case object EtwitterperimentalSehUser4 etwittertends UserLabelValue
  case object EtwitterperimentalSehUser5 etwittertends UserLabelValue
  case object EtwitterperimentalSensitiveIllegal1 etwittertends UserLabelValue
  case object EtwitterperimentalSensitiveIllegal2 etwittertends UserLabelValue
  case object FakeSignupDeferredRemediation etwittertends UserLabelValue
  case object FakeSignupHoldback etwittertends UserLabelValue
  case object GoreAndViolenceHighPrecision etwittertends UserLabelValue
  case object GoreAndViolenceReportedHeuristics etwittertends UserLabelValue
  case object HealthEtwitterperimentation1 etwittertends UserLabelValue
  case object HealthEtwitterperimentation2 etwittertends UserLabelValue
  case object HighRiskVerification etwittertends UserLabelValue
  case object LegalOpsCase etwittertends UserLabelValue
  case object LikelyIvs etwittertends UserLabelValue
  case object LiveLowQuality etwittertends UserLabelValue
  case object LowQuality etwittertends UserLabelValue
  case object LowQualityHighRecall etwittertends UserLabelValue
  case object NotificationSpamHeuristics etwittertends UserLabelValue
  case object NotGraduated etwittertends UserLabelValue
  case object NsfwAvatarImage etwittertends UserLabelValue
  case object NsfwBannerImage etwittertends UserLabelValue
  case object NsfwHighPrecision etwittertends UserLabelValue
  case object NsfwHighRecall etwittertends UserLabelValue
  case object NsfwNearPerfect etwittertends UserLabelValue
  case object NsfwReportedHeuristics etwittertends UserLabelValue
  case object NsfwSensitive etwittertends UserLabelValue
  case object NsfwTetwittert etwittertends UserLabelValue
  case object ReadOnly etwittertends UserLabelValue
  case object RecentAbuseStrike etwittertends UserLabelValue
  case object RecentProfileModification etwittertends UserLabelValue
  case object RecentMisinfoStrike etwittertends UserLabelValue
  case object RecentSuspension etwittertends UserLabelValue
  case object RecommendationsBlacklist etwittertends UserLabelValue
  case object SearchBlacklist etwittertends UserLabelValue
  case object SoftReadOnly etwittertends UserLabelValue
  case object SpamHighRecall etwittertends UserLabelValue
  case object SpammyUserModelHighPrecision etwittertends UserLabelValue
  case object StateMediaAccount etwittertends UserLabelValue
  case object TsViolation etwittertends UserLabelValue
  case object UnconfirmedEmailSignup etwittertends UserLabelValue

  case object Deprecated etwittertends UserLabelValue
  case object Unknown etwittertends UserLabelValue

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
