package com.twitter.visibility.rules

import com.twitter.datatools.entityservice.entities.thriftscala.FleetInterstitial
import com.twitter.scrooge.ThriftStruct
import com.twitter.visibility.common.actions.LocalizedMessage
import com.twitter.visibility.common.actions._
import com.twitter.visibility.common.actions.converter.scala.AppealableReasonConverter
import com.twitter.visibility.common.actions.converter.scala.AvoidReasonConverter
import com.twitter.visibility.common.actions.converter.scala.ComplianceTweetNoticeEventTypeConverter
import com.twitter.visibility.common.actions.converter.scala.DownrankHomeTimelineReasonConverter
import com.twitter.visibility.common.actions.converter.scala.DropReasonConverter
import com.twitter.visibility.common.actions.converter.scala.InterstitialReasonConverter
import com.twitter.visibility.common.actions.converter.scala.LimitedActionsPolicyConverter
import com.twitter.visibility.common.actions.converter.scala.LimitedEngagementReasonConverter
import com.twitter.visibility.common.actions.converter.scala.LocalizedMessageConverter
import com.twitter.visibility.common.actions.converter.scala.SoftInterventionDisplayTypeConverter
import com.twitter.visibility.common.actions.converter.scala.SoftInterventionReasonConverter
import com.twitter.visibility.common.actions.converter.scala.TombstoneReasonConverter
import com.twitter.visibility.features.Feature
import com.twitter.visibility.logging.thriftscala.HealthActionType
import com.twitter.visibility.models.ViolationLevel
import com.twitter.visibility.strato.thriftscala.NudgeActionType.EnumUnknownNudgeActionType
import com.twitter.visibility.strato.thriftscala.{Nudge => StratoNudge}
import com.twitter.visibility.strato.thriftscala.{NudgeAction => StratoNudgeAction}
import com.twitter.visibility.strato.thriftscala.{NudgeActionType => StratoNudgeActionType}
import com.twitter.visibility.strato.thriftscala.{NudgeActionPayload => StratoNudgeActionPayload}
import com.twitter.visibility.thriftscala
import com.twitter.visibility.util.NamingUtils

sealed trait Action {
  lazy val name: String = NamingUtils.getFriendlyName(this)
  lazy val fullName: String = NamingUtils.getFriendlyName(this)

  val severity: Int
  def toActionThrift(): thriftscala.Action

  def isComposable: Boolean = false

  def toHealthActionTypeThrift: Option[HealthActionType]
}

sealed trait Reason {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

sealed abstract class ActionWithReason(reason: Reason) etwittertends Action {
  override lazy val fullName: String = s"${this.name}/${reason.name}"
}

object Reason {

  case object Bounce etwittertends Reason

  case object ViewerReportedAuthor etwittertends Reason
  case object ViewerReportedTweet etwittertends Reason

  case object DeactivatedAuthor etwittertends Reason
  case object OffboardedAuthor etwittertends Reason
  case object ErasedAuthor etwittertends Reason
  case object ProtectedAuthor etwittertends Reason
  case object SuspendedAuthor etwittertends Reason
  case object ViewerIsUnmentioned etwittertends Reason

  case object Nsfw etwittertends Reason
  case object NsfwMedia etwittertends Reason
  case object NsfwViewerIsUnderage etwittertends Reason
  case object NsfwViewerHasNoStatedAge etwittertends Reason
  case object NsfwLoggedOut etwittertends Reason
  case object PossiblyUndesirable etwittertends Reason

  case object AbuseEpisodic etwittertends Reason
  case object AbuseEpisodicEncourageSelfHarm etwittertends Reason
  case object AbuseEpisodicHatefulConduct etwittertends Reason
  case object AbuseGlorificationOfViolence etwittertends Reason
  case object AbuseGratuitousGore etwittertends Reason
  case object AbuseMobHarassment etwittertends Reason
  case object AbuseMomentOfDeathOrDeceasedUser etwittertends Reason
  case object AbusePrivateInformation etwittertends Reason
  case object AbuseRightToPrivacy etwittertends Reason
  case object AbuseThreatToEtwitterpose etwittertends Reason
  case object AbuseViolentSetwitterualConduct etwittertends Reason
  case object AbuseViolentThreatHatefulConduct etwittertends Reason
  case object AbuseViolentThreatOrBounty etwittertends Reason

  case object MutedKeyword etwittertends Reason
  case object Unspecified etwittertends Reason

  case object UntrustedUrl etwittertends Reason

  case object SpamReplyDownRank etwittertends Reason

  case object LowQualityTweet etwittertends Reason

  case object LowQualityMention etwittertends Reason

  case object SpamHighRecallTweet etwittertends Reason

  case object TweetLabelDuplicateContent etwittertends Reason

  case object TweetLabelDuplicateMention etwittertends Reason

  case object PdnaTweet etwittertends Reason

  case object TweetLabeledSpam etwittertends Reason

  case object OneOff etwittertends Reason
  case object VotingMisinformation etwittertends Reason
  case object HackedMaterials etwittertends Reason
  case object Scams etwittertends Reason
  case object PlatformManipulation etwittertends Reason

  case object FirstPageSearchResult etwittertends Reason

  case object MisinfoCivic etwittertends Reason
  case object MisinfoCrisis etwittertends Reason
  case object MisinfoGeneric etwittertends Reason
  case object MisinfoMedical etwittertends Reason
  case object Misleading etwittertends Reason
  case object EtwitterclusiveTweet etwittertends Reason
  case object CommunityNotAMember etwittertends Reason
  case object CommunityTweetHidden etwittertends Reason
  case object CommunityTweetCommunityIsSuspended etwittertends Reason
  case object CommunityTweetAuthorRemoved etwittertends Reason
  case object InternalPromotedContent etwittertends Reason
  case object TrustedFriendsTweet etwittertends Reason
  case object Totwittericity etwittertends Reason
  case object StaleTweet etwittertends Reason
  case object DmcaWithheld etwittertends Reason
  case object LegalDemandsWithheld etwittertends Reason
  case object LocalLawsWithheld etwittertends Reason
  case object HatefulConduct etwittertends Reason
  case object AbusiveBehavior etwittertends Reason

  case object NotSupportedOnDevice etwittertends Reason

  case object IpiDevelopmentOnly etwittertends Reason
  case object InterstitialDevelopmentOnly etwittertends Reason

  case class FosnrReason(appealableReason: AppealableReason) etwittertends Reason

  def toDropReason(reason: Reason): Option[DropReason] =
    reason match {
      case AuthorBlocksViewer => Some(DropReason.AuthorBlocksViewer)
      case CommunityTweetHidden => Some(DropReason.CommunityTweetHidden)
      case CommunityTweetCommunityIsSuspended => Some(DropReason.CommunityTweetCommunityIsSuspended)
      case DmcaWithheld => Some(DropReason.DmcaWithheld)
      case EtwitterclusiveTweet => Some(DropReason.EtwitterclusiveTweet)
      case InternalPromotedContent => Some(DropReason.InternalPromotedContent)
      case LegalDemandsWithheld => Some(DropReason.LegalDemandsWithheld)
      case LocalLawsWithheld => Some(DropReason.LocalLawsWithheld)
      case Nsfw => Some(DropReason.NsfwAuthor)
      case NsfwLoggedOut => Some(DropReason.NsfwLoggedOut)
      case NsfwViewerHasNoStatedAge => Some(DropReason.NsfwViewerHasNoStatedAge)
      case NsfwViewerIsUnderage => Some(DropReason.NsfwViewerIsUnderage)
      case ProtectedAuthor => Some(DropReason.ProtectedAuthor)
      case StaleTweet => Some(DropReason.StaleTweet)
      case SuspendedAuthor => Some(DropReason.SuspendedAuthor)
      case Unspecified => Some(DropReason.Unspecified)
      case ViewerBlocksAuthor => Some(DropReason.ViewerBlocksAuthor)
      case ViewerHardMutedAuthor => Some(DropReason.ViewerMutesAuthor)
      case ViewerMutesAuthor => Some(DropReason.ViewerMutesAuthor)
      case TrustedFriendsTweet => Some(DropReason.TrustedFriendsTweet)
      case _ => Some(DropReason.Unspecified)
    }

  def fromDropReason(dropReason: DropReason): Reason =
    dropReason match {
      case DropReason.AuthorBlocksViewer => AuthorBlocksViewer
      case DropReason.CommunityTweetHidden => CommunityTweetHidden
      case DropReason.CommunityTweetCommunityIsSuspended => CommunityTweetCommunityIsSuspended
      case DropReason.DmcaWithheld => DmcaWithheld
      case DropReason.EtwitterclusiveTweet => EtwitterclusiveTweet
      case DropReason.InternalPromotedContent => InternalPromotedContent
      case DropReason.LegalDemandsWithheld => LegalDemandsWithheld
      case DropReason.LocalLawsWithheld => LocalLawsWithheld
      case DropReason.NsfwAuthor => Nsfw
      case DropReason.NsfwLoggedOut => NsfwLoggedOut
      case DropReason.NsfwViewerHasNoStatedAge => NsfwViewerHasNoStatedAge
      case DropReason.NsfwViewerIsUnderage => NsfwViewerIsUnderage
      case DropReason.ProtectedAuthor => ProtectedAuthor
      case DropReason.StaleTweet => StaleTweet
      case DropReason.SuspendedAuthor => SuspendedAuthor
      case DropReason.ViewerBlocksAuthor => ViewerBlocksAuthor
      case DropReason.ViewerMutesAuthor => ViewerMutesAuthor
      case DropReason.TrustedFriendsTweet => TrustedFriendsTweet
      case DropReason.Unspecified => Unspecified
    }

  def toAppealableReason(reason: Reason, violationLevel: ViolationLevel): Option[AppealableReason] =
    reason match {
      case HatefulConduct => Some(AppealableReason.HatefulConduct(violationLevel.level))
      case AbusiveBehavior => Some(AppealableReason.AbusiveBehavior(violationLevel.level))
      case _ => Some(AppealableReason.Unspecified(violationLevel.level))
    }

  def fromAppealableReason(appealableReason: AppealableReason): Reason =
    appealableReason match {
      case AppealableReason.HatefulConduct(level) => HatefulConduct
      case AppealableReason.AbusiveBehavior(level) => AbusiveBehavior
      case AppealableReason.Unspecified(level) => Unspecified
    }

  def toSoftInterventionReason(appealableReason: AppealableReason): SoftInterventionReason =
    appealableReason match {
      case AppealableReason.HatefulConduct(level) =>
        SoftInterventionReason.FosnrReason(appealableReason)
      case AppealableReason.AbusiveBehavior(level) =>
        SoftInterventionReason.FosnrReason(appealableReason)
      case AppealableReason.Unspecified(level) =>
        SoftInterventionReason.FosnrReason(appealableReason)
    }

  def toLimitedEngagementReason(appealableReason: AppealableReason): LimitedEngagementReason =
    appealableReason match {
      case AppealableReason.HatefulConduct(level) =>
        LimitedEngagementReason.FosnrReason(appealableReason)
      case AppealableReason.AbusiveBehavior(level) =>
        LimitedEngagementReason.FosnrReason(appealableReason)
      case AppealableReason.Unspecified(level) =>
        LimitedEngagementReason.FosnrReason(appealableReason)
    }

  val NSFW_MEDIA: Set[Reason] = Set(Nsfw, NsfwMedia)

  def toInterstitialReason(reason: Reason): Option[InterstitialReason] =
    reason match {
      case r if NSFW_MEDIA.contains(r) => Some(InterstitialReason.ContainsNsfwMedia)
      case PossiblyUndesirable => Some(InterstitialReason.PossiblyUndesirable)
      case MutedKeyword => Some(InterstitialReason.MatchesMutedKeyword(""))
      case ViewerReportedAuthor => Some(InterstitialReason.ViewerReportedAuthor)
      case ViewerReportedTweet => Some(InterstitialReason.ViewerReportedTweet)
      case ViewerBlocksAuthor => Some(InterstitialReason.ViewerBlocksAuthor)
      case ViewerMutesAuthor => Some(InterstitialReason.ViewerMutesAuthor)
      case ViewerHardMutedAuthor => Some(InterstitialReason.ViewerMutesAuthor)
      case InterstitialDevelopmentOnly => Some(InterstitialReason.DevelopmentOnly)
      case DmcaWithheld => Some(InterstitialReason.DmcaWithheld)
      case LegalDemandsWithheld => Some(InterstitialReason.LegalDemandsWithheld)
      case LocalLawsWithheld => Some(InterstitialReason.LocalLawsWithheld)
      case HatefulConduct => Some(InterstitialReason.HatefulConduct)
      case AbusiveBehavior => Some(InterstitialReason.AbusiveBehavior)
      case FosnrReason(appealableReason) => Some(InterstitialReason.FosnrReason(appealableReason))
      case _ => None
    }

  def fromInterstitialReason(interstitialReason: InterstitialReason): Reason =
    interstitialReason match {
      case InterstitialReason.ContainsNsfwMedia => Reason.NsfwMedia
      case InterstitialReason.PossiblyUndesirable => Reason.PossiblyUndesirable
      case InterstitialReason.MatchesMutedKeyword(_) => Reason.MutedKeyword
      case InterstitialReason.ViewerReportedAuthor => Reason.ViewerReportedAuthor
      case InterstitialReason.ViewerReportedTweet => Reason.ViewerReportedTweet
      case InterstitialReason.ViewerBlocksAuthor => Reason.ViewerBlocksAuthor
      case InterstitialReason.ViewerMutesAuthor => Reason.ViewerMutesAuthor
      case InterstitialReason.DevelopmentOnly => Reason.InterstitialDevelopmentOnly
      case InterstitialReason.DmcaWithheld => Reason.DmcaWithheld
      case InterstitialReason.LegalDemandsWithheld => Reason.LegalDemandsWithheld
      case InterstitialReason.LocalLawsWithheld => Reason.LocalLawsWithheld
      case InterstitialReason.HatefulConduct => Reason.HatefulConduct
      case InterstitialReason.AbusiveBehavior => Reason.AbusiveBehavior
      case InterstitialReason.FosnrReason(reason) => Reason.fromAppealableReason(reason)
    }

}

sealed trait Epitaph {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

object Epitaph {

  case object Unavailable etwittertends Epitaph

  case object Blocked etwittertends Epitaph
  case object BlockedBy etwittertends Epitaph
  case object Reported etwittertends Epitaph

  case object BounceDeleted etwittertends Epitaph
  case object Deleted etwittertends Epitaph
  case object NotFound etwittertends Epitaph
  case object PublicInterest etwittertends Epitaph

  case object Bounced etwittertends Epitaph
  case object Protected etwittertends Epitaph
  case object Suspended etwittertends Epitaph
  case object Offboarded etwittertends Epitaph
  case object Deactivated etwittertends Epitaph

  case object MutedKeyword etwittertends Epitaph
  case object Underage etwittertends Epitaph
  case object NoStatedAge etwittertends Epitaph
  case object LoggedOutAge etwittertends Epitaph
  case object SuperFollowsContent etwittertends Epitaph

  case object Moderated etwittertends Epitaph
  case object ForEmergencyUseOnly etwittertends Epitaph
  case object UnavailableWithoutLink etwittertends Epitaph
  case object CommunityTweetHidden etwittertends Epitaph
  case object CommunityTweetMemberRemoved etwittertends Epitaph
  case object CommunityTweetCommunityIsSuspended etwittertends Epitaph

  case object UserSuspended etwittertends Epitaph

  case object DevelopmentOnly etwittertends Epitaph

  case object AdultMedia etwittertends Epitaph
  case object ViolentMedia etwittertends Epitaph
  case object OtherSensitiveMedia etwittertends Epitaph

  case object DmcaWithheldMedia etwittertends Epitaph
  case object LegalDemandsWithheldMedia etwittertends Epitaph
  case object LocalLawsWithheldMedia etwittertends Epitaph

  case object TotwittericReplyFiltered etwittertends Epitaph
}

sealed trait IsInterstitial {
  def toInterstitialThriftWrapper(): thriftscala.AnyInterstitial
  def toInterstitialThrift(): ThriftStruct
}

sealed trait IsAppealable {
  def toAppealableThrift(): thriftscala.Appealable
}

sealed trait IsLimitedEngagements {
  def policy: Option[LimitedActionsPolicy]
  def getLimitedEngagementReason: LimitedEngagementReason
}

object IsLimitedEngagements {
  def unapply(
    ile: IsLimitedEngagements
  ): Option[(Option[LimitedActionsPolicy], LimitedEngagementReason)] = {
    Some((ile.policy, ile.getLimitedEngagementReason))
  }
}

sealed abstract class ActionWithEpitaph(epitaph: Epitaph) etwittertends Action {
  override lazy val fullName: String = s"${this.name}/${epitaph.name}"
}

case class Appealable(
  reason: Reason,
  violationLevel: ViolationLevel,
  localizedMessage: Option[LocalizedMessage] = None)
    etwittertends ActionWithReason(reason)
    with IsAppealable {

  override val severity: Int = 17
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.Appealable(toAppealableThrift())

  override def toAppealableThrift(): thriftscala.Appealable =
    thriftscala.Appealable(
      Reason.toAppealableReason(reason, violationLevel).map(AppealableReasonConverter.toThrift),
      localizedMessage.map(LocalizedMessageConverter.toThrift)
    )

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.Appealable)
}

case class Drop(reason: Reason, applicableCountries: Option[Seq[String]] = None)
    etwittertends ActionWithReason(reason) {

  override val severity: Int = 16
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.Drop(
      thriftscala.Drop(
        Reason.toDropReason(reason).map(DropReasonConverter.toThrift),
        applicableCountries
      ))

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(HealthActionType.Drop)
}

case class Interstitial(
  reason: Reason,
  localizedMessage: Option[LocalizedMessage] = None,
  applicableCountries: Option[Seq[String]] = None)
    etwittertends ActionWithReason(reason)
    with IsInterstitial {

  override val severity: Int = 10
  override def toInterstitialThriftWrapper(): thriftscala.AnyInterstitial =
    thriftscala.AnyInterstitial.Interstitial(
      toInterstitialThrift()
    )

  override def toInterstitialThrift(): thriftscala.Interstitial =
    thriftscala.Interstitial(
      Reason.toInterstitialReason(reason).map(InterstitialReasonConverter.toThrift),
      localizedMessage.map(LocalizedMessageConverter.toThrift)
    )

  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.Interstitial(toInterstitialThrift())

  def toMediaActionThrift(): thriftscala.MediaAction =
    thriftscala.MediaAction.Interstitial(toInterstitialThrift())

  override def isComposable: Boolean = true

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.TweetInterstitial)
}

case class InterstitialLimitedEngagements(
  reason: Reason,
  limitedEngagementReason: Option[LimitedEngagementReason],
  localizedMessage: Option[LocalizedMessage] = None,
  policy: Option[LimitedActionsPolicy] = None)
    etwittertends ActionWithReason(reason)
    with IsInterstitial
    with IsLimitedEngagements {

  override val severity: Int = 11
  override def toInterstitialThriftWrapper(): thriftscala.AnyInterstitial =
    thriftscala.AnyInterstitial.InterstitialLimitedEngagements(
      toInterstitialThrift()
    )

  override def toInterstitialThrift(): thriftscala.InterstitialLimitedEngagements =
    thriftscala.InterstitialLimitedEngagements(
      limitedEngagementReason.map(LimitedEngagementReasonConverter.toThrift),
      localizedMessage.map(LocalizedMessageConverter.toThrift)
    )

  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.InterstitialLimitedEngagements(toInterstitialThrift())

  override def isComposable: Boolean = true

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.LimitedEngagements)

  def getLimitedEngagementReason: LimitedEngagementReason = limitedEngagementReason.getOrElse(
    LimitedEngagementReason.NonCompliant
  )
}

case object Allow etwittertends Action {

  override val severity: Int = -1
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.Allow(thriftscala.Allow())

  override def toHealthActionTypeThrift: Option[HealthActionType] = None
}

case object NotEvaluated etwittertends Action {

  override val severity: Int = -1
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.NotEvaluated(thriftscala.NotEvaluated())

  override def toHealthActionTypeThrift: Option[HealthActionType] = None
}

case class Tombstone(epitaph: Epitaph, applicableCountryCodes: Option[Seq[String]] = None)
    etwittertends ActionWithEpitaph(epitaph) {

  override val severity: Int = 15
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.Tombstone(thriftscala.Tombstone())

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(HealthActionType.Tombstone)
}

case class LocalizedTombstone(reason: TombstoneReason, message: LocalizedMessage) etwittertends Action {
  override lazy val fullName: String = s"${this.name}/${NamingUtils.getFriendlyName(reason)}"

  override val severity: Int = 15
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.Tombstone(
      thriftscala.Tombstone(
        reason = TombstoneReasonConverter.toThrift(Some(reason)),
        message = Some(LocalizedMessageConverter.toThrift(message))
      ))

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(HealthActionType.Tombstone)
}

case class DownrankHomeTimeline(reason: Option[DownrankHomeTimelineReason]) etwittertends Action {

  override val severity: Int = 9
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.DownrankHomeTimeline(toDownrankThrift())

  def toDownrankThrift(): thriftscala.DownrankHomeTimeline =
    thriftscala.DownrankHomeTimeline(
      reason.map(DownrankHomeTimelineReasonConverter.toThrift)
    )

  override def isComposable: Boolean = true

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(HealthActionType.Downrank)
}

case class Avoid(avoidReason: Option[AvoidReason] = None) etwittertends Action {

  override val severity: Int = 1
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.Avoid(toAvoidThrift())

  def toAvoidThrift(): thriftscala.Avoid =
    thriftscala.Avoid(
      avoidReason.map(AvoidReasonConverter.toThrift)
    )

  override def isComposable: Boolean = true

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(HealthActionType.Avoid)
}

case object Downrank etwittertends Action {

  override val severity: Int = 0
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.Downrank(thriftscala.Downrank())

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(HealthActionType.Downrank)
}

case object ConversationSectionLowQuality etwittertends Action {

  override val severity: Int = 4
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.ConversationSectionLowQuality(thriftscala.ConversationSectionLowQuality())

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.ConversationSectionLowQuality)
}

case object ConversationSectionAbusiveQuality etwittertends Action {

  override val severity: Int = 5
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.ConversationSectionAbusiveQuality(
      thriftscala.ConversationSectionAbusiveQuality())

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.ConversationSectionAbusiveQuality)

  def toConversationSectionAbusiveQualityThrift(): thriftscala.ConversationSectionAbusiveQuality =
    thriftscala.ConversationSectionAbusiveQuality()
}

case class LimitedEngagements(
  reason: LimitedEngagementReason,
  policy: Option[LimitedActionsPolicy] = None)
    etwittertends Action
    with IsLimitedEngagements {

  override val severity: Int = 6
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.LimitedEngagements(toLimitedEngagementsThrift())

  def toLimitedEngagementsThrift(): thriftscala.LimitedEngagements =
    thriftscala.LimitedEngagements(
      Some(LimitedEngagementReasonConverter.toThrift(reason)),
      policy.map(LimitedActionsPolicyConverter.toThrift),
      Some(reason.toLimitedActionsString)
    )

  override def isComposable: Boolean = true

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.LimitedEngagements)

  def getLimitedEngagementReason: LimitedEngagementReason = reason
}

case class EmergencyDynamicInterstitial(
  copy: String,
  linkOpt: Option[String],
  localizedMessage: Option[LocalizedMessage] = None,
  policy: Option[LimitedActionsPolicy] = None)
    etwittertends Action
    with IsInterstitial
    with IsLimitedEngagements {

  override val severity: Int = 11
  override def toInterstitialThriftWrapper(): thriftscala.AnyInterstitial =
    thriftscala.AnyInterstitial.EmergencyDynamicInterstitial(
      toInterstitialThrift()
    )

  override def toInterstitialThrift(): thriftscala.EmergencyDynamicInterstitial =
    thriftscala.EmergencyDynamicInterstitial(
      copy,
      linkOpt,
      localizedMessage.map(LocalizedMessageConverter.toThrift)
    )

  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.EmergencyDynamicInterstitial(toInterstitialThrift())

  override def isComposable: Boolean = true

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.TweetInterstitial)

  def getLimitedEngagementReason: LimitedEngagementReason = LimitedEngagementReason.NonCompliant
}

case class SoftIntervention(
  reason: SoftInterventionReason,
  engagementNudge: Boolean,
  suppressAutoplay: Boolean,
  warning: Option[String] = None,
  detailsUrl: Option[String] = None,
  displayType: Option[SoftInterventionDisplayType] = None,
  fleetInterstitial: Option[FleetInterstitial] = None)
    etwittertends Action {

  override val severity: Int = 7
  def toSoftInterventionThrift(): thriftscala.SoftIntervention =
    thriftscala.SoftIntervention(
      Some(SoftInterventionReasonConverter.toThrift(reason)),
      engagementNudge = Some(engagementNudge),
      suppressAutoplay = Some(suppressAutoplay),
      warning = warning,
      detailsUrl = detailsUrl,
      displayType = SoftInterventionDisplayTypeConverter.toThrift(displayType)
    )

  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.SoftIntervention(toSoftInterventionThrift())

  override def isComposable: Boolean = true

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.SoftIntervention)
}

case class TweetInterstitial(
  interstitial: Option[IsInterstitial],
  softIntervention: Option[SoftIntervention],
  limitedEngagements: Option[LimitedEngagements],
  downrank: Option[DownrankHomeTimeline],
  avoid: Option[Avoid],
  mediaInterstitial: Option[Interstitial] = None,
  tweetVisibilityNudge: Option[TweetVisibilityNudge] = None,
  abusiveQuality: Option[ConversationSectionAbusiveQuality.type] = None,
  appealable: Option[Appealable] = None)
    etwittertends Action {

  override val severity: Int = 12
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.TweetInterstitial(
      thriftscala.TweetInterstitial(
        interstitial.map(_.toInterstitialThriftWrapper()),
        softIntervention.map(_.toSoftInterventionThrift()),
        limitedEngagements.map(_.toLimitedEngagementsThrift()),
        downrank.map(_.toDownrankThrift()),
        avoid.map(_.toAvoidThrift()),
        mediaInterstitial.map(_.toMediaActionThrift()),
        tweetVisibilityNudge.map(_.toTweetVisbilityNudgeThrift()),
        abusiveQuality.map(_.toConversationSectionAbusiveQualityThrift()),
        appealable.map(_.toAppealableThrift())
      )
    )

  override def toHealthActionTypeThrift: Option[HealthActionType] = Some(
    HealthActionType.TweetInterstitial)
}

sealed trait LocalizedNudgeActionType
object LocalizedNudgeActionType {
  case object Reply etwittertends LocalizedNudgeActionType
  case object Retweet etwittertends LocalizedNudgeActionType
  case object Like etwittertends LocalizedNudgeActionType
  case object Share etwittertends LocalizedNudgeActionType
  case object Unspecified etwittertends LocalizedNudgeActionType

  def toThrift(
    localizedNudgeActionType: LocalizedNudgeActionType
  ): thriftscala.TweetVisibilityNudgeActionType =
    localizedNudgeActionType match {
      case Reply => thriftscala.TweetVisibilityNudgeActionType.Reply
      case Retweet => thriftscala.TweetVisibilityNudgeActionType.Retweet
      case Like => thriftscala.TweetVisibilityNudgeActionType.Like
      case Share => thriftscala.TweetVisibilityNudgeActionType.Share
      case Unspecified =>
        thriftscala.TweetVisibilityNudgeActionType.EnumUnknownTweetVisibilityNudgeActionType(5)
    }

  def fromStratoThrift(stratoNudgeActionType: StratoNudgeActionType): LocalizedNudgeActionType =
    stratoNudgeActionType match {
      case StratoNudgeActionType.Reply => Reply
      case StratoNudgeActionType.Retweet => Retweet
      case StratoNudgeActionType.Like => Like
      case StratoNudgeActionType.Share => Share
      case EnumUnknownNudgeActionType(_) => Unspecified
    }
}

case class LocalizedNudgeActionPayload(
  heading: Option[String],
  subheading: Option[String],
  iconName: Option[String],
  ctaTitle: Option[String],
  ctaUrl: Option[String],
  postCtaTetwittert: Option[String]) {

  def toThrift(): thriftscala.TweetVisibilityNudgeActionPayload = {
    thriftscala.TweetVisibilityNudgeActionPayload(
      heading = heading,
      subheading = subheading,
      iconName = iconName,
      ctaTitle = ctaTitle,
      ctaUrl = ctaUrl,
      postCtaTetwittert = postCtaTetwittert
    )
  }
}

object LocalizedNudgeActionPayload {
  def fromStratoThrift(
    stratoNudgeActionPayload: StratoNudgeActionPayload
  ): LocalizedNudgeActionPayload =
    LocalizedNudgeActionPayload(
      heading = stratoNudgeActionPayload.heading,
      subheading = stratoNudgeActionPayload.subheading,
      iconName = stratoNudgeActionPayload.iconName,
      ctaTitle = stratoNudgeActionPayload.ctaTitle,
      ctaUrl = stratoNudgeActionPayload.ctaUrl,
      postCtaTetwittert = stratoNudgeActionPayload.postCtaTetwittert
    )
}

case class LocalizedNudgeAction(
  nudgeActionType: LocalizedNudgeActionType,
  nudgeActionPayload: Option[LocalizedNudgeActionPayload]) {
  def toThrift(): thriftscala.TweetVisibilityNudgeAction = {
    thriftscala.TweetVisibilityNudgeAction(
      tweetVisibilitynudgeActionType = LocalizedNudgeActionType.toThrift(nudgeActionType),
      tweetVisibilityNudgeActionPayload = nudgeActionPayload.map(_.toThrift)
    )
  }
}

object LocalizedNudgeAction {
  def fromStratoThrift(stratoNudgeAction: StratoNudgeAction): LocalizedNudgeAction =
    LocalizedNudgeAction(
      nudgeActionType =
        LocalizedNudgeActionType.fromStratoThrift(stratoNudgeAction.nudgeActionType),
      nudgeActionPayload =
        stratoNudgeAction.nudgeActionPayload.map(LocalizedNudgeActionPayload.fromStratoThrift)
    )
}

case class LocalizedNudge(localizedNudgeActions: Seq[LocalizedNudgeAction])

case object LocalizedNudge {
  def fromStratoThrift(stratoNudge: StratoNudge): LocalizedNudge =
    LocalizedNudge(localizedNudgeActions =
      stratoNudge.nudgeActions.map(LocalizedNudgeAction.fromStratoThrift))
}

case class TweetVisibilityNudge(
  reason: TweetVisibilityNudgeReason,
  localizedNudge: Option[LocalizedNudge] = None)
    etwittertends Action {

  override val severity: Int = 3
  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.TweetVisibilityNudge(
      localizedNudge match {
        case Some(nudge) =>
          thriftscala.TweetVisibilityNudge(
            tweetVisibilityNudgeActions = Some(nudge.localizedNudgeActions.map(_.toThrift()))
          )
        case _ => thriftscala.TweetVisibilityNudge(tweetVisibilityNudgeActions = None)
      }
    )

  override def toHealthActionTypeThrift: Option[HealthActionType] =
    Some(HealthActionType.TweetVisibilityNudge)

  def toTweetVisbilityNudgeThrift(): thriftscala.TweetVisibilityNudge =
    thriftscala.TweetVisibilityNudge(tweetVisibilityNudgeActions =
      localizedNudge.map(_.localizedNudgeActions.map(_.toThrift())))
}

trait BaseComplianceTweetNotice {
  val complianceTweetNoticeEventType: ComplianceTweetNoticeEventType
  val details: Option[String]
  val etwittertendedDetailsUrl: Option[String]
}

case class ComplianceTweetNoticePreEnrichment(
  reason: Reason,
  complianceTweetNoticeEventType: ComplianceTweetNoticeEventType,
  details: Option[String] = None,
  etwittertendedDetailsUrl: Option[String] = None)
    etwittertends Action
    with BaseComplianceTweetNotice {

  override val severity: Int = 2
  def toComplianceTweetNoticeThrift(): thriftscala.ComplianceTweetNotice =
    thriftscala.ComplianceTweetNotice(
      ComplianceTweetNoticeEventTypeConverter.toThrift(complianceTweetNoticeEventType),
      ComplianceTweetNoticeEventTypeConverter.eventTypeToLabelTitle(complianceTweetNoticeEventType),
      details,
      etwittertendedDetailsUrl
    )

  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.ComplianceTweetNotice(
      toComplianceTweetNoticeThrift()
    )

  override def toHealthActionTypeThrift: Option[HealthActionType] = None

  def toComplianceTweetNotice(): ComplianceTweetNotice = {
    ComplianceTweetNotice(
      complianceTweetNoticeEventType = complianceTweetNoticeEventType,
      labelTitle = ComplianceTweetNoticeEventTypeConverter.eventTypeToLabelTitle(
        complianceTweetNoticeEventType),
      details = details,
      etwittertendedDetailsUrl = etwittertendedDetailsUrl
    )
  }
}

case class ComplianceTweetNotice(
  complianceTweetNoticeEventType: ComplianceTweetNoticeEventType,
  labelTitle: Option[String] = None,
  details: Option[String] = None,
  etwittertendedDetailsUrl: Option[String] = None)
    etwittertends Action
    with BaseComplianceTweetNotice {

  override val severity: Int = 2
  def toComplianceTweetNoticeThrift(): thriftscala.ComplianceTweetNotice =
    thriftscala.ComplianceTweetNotice(
      ComplianceTweetNoticeEventTypeConverter.toThrift(complianceTweetNoticeEventType),
      labelTitle,
      details,
      etwittertendedDetailsUrl
    )

  override def toActionThrift(): thriftscala.Action =
    thriftscala.Action.ComplianceTweetNotice(
      toComplianceTweetNoticeThrift()
    )

  override def toHealthActionTypeThrift: Option[HealthActionType] = None
}

object Action {
  def toThrift[T <: Action](action: T): thriftscala.Action =
    action.toActionThrift()

  def getFirstInterstitial(actions: Action*): Option[IsInterstitial] =
    actions.collectFirst {
      case ile: InterstitialLimitedEngagements => ile
      case edi: EmergencyDynamicInterstitial => edi
      case i: Interstitial => i
    }

  def getFirstSoftIntervention(actions: Action*): Option[SoftIntervention] =
    actions.collectFirst {
      case si: SoftIntervention => si
    }

  def getFirstLimitedEngagements(actions: Action*): Option[LimitedEngagements] =
    actions.collectFirst {
      case le: LimitedEngagements => le
    }

  def getAllLimitedEngagements(actions: Action*): Seq[IsLimitedEngagements] =
    actions.collect {
      case ile: IsLimitedEngagements => ile
    }

  def getFirstDownrankHomeTimeline(actions: Action*): Option[DownrankHomeTimeline] =
    actions.collectFirst {
      case dr: DownrankHomeTimeline => dr
    }

  def getFirstAvoid(actions: Action*): Option[Avoid] =
    actions.collectFirst {
      case a: Avoid => a
    }

  def getFirstMediaInterstitial(actions: Action*): Option[Interstitial] =
    actions.collectFirst {
      case i: Interstitial if Reason.NSFW_MEDIA.contains(i.reason) => i
    }

  def getFirstTweetVisibilityNudge(actions: Action*): Option[TweetVisibilityNudge] =
    actions.collectFirst {
      case n: TweetVisibilityNudge => n
    }
}

sealed trait State {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

object State {
  case object Pending etwittertends State
  case object Disabled etwittertends State
  final case class MissingFeature(features: Set[Feature[_]]) etwittertends State
  final case class FeatureFailed(features: Map[Feature[_], Throwable]) etwittertends State
  final case class RuleFailed(throwable: Throwable) etwittertends State
  case object Skipped etwittertends State
  case object ShortCircuited etwittertends State
  case object Heldback etwittertends State
  case object Evaluated etwittertends State
}

case class RuleResult(action: Action, state: State)
