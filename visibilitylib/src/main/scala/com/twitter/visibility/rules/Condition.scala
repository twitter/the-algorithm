package com.twitter.visibility.rules

import com.twitter.contenthealth.sensitivemediasettings.thriftscala.SensitiveMediaSettingsLevel
import com.twitter.contenthealth.totwittericreplyfilter.thriftscala.FilterState
import com.twitter.conversions.DurationOps._
import com.twitter.gizmoduck.thriftscala.Label
import com.twitter.gizmoduck.thriftscala.MuteSurface
import com.twitter.health.platform_manipulation.stcm_tweet_holdback.StcmTweetHoldback
import com.twitter.search.common.constants.thriftscala.ThriftQuerySource
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.takedown.util.TakedownReasons
import com.twitter.takedown.util.{TakedownReasons => TakedownReasonsUtil}
import com.twitter.timelines.configapi.EnumParam
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.configapi.Params
import com.twitter.tseng.withholding.thriftscala.TakedownReason
import com.twitter.util.Duration
import com.twitter.util.Time
import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams
import com.twitter.visibility.features.AuthorIsSuspended
import com.twitter.visibility.features.CardIsPoll
import com.twitter.visibility.features.CardUriHost
import com.twitter.visibility.features.SearchQuerySource
import com.twitter.visibility.features._
import com.twitter.visibility.features.{AuthorBlocksOuterAuthor => AuthorBlocksOuterAuthorFeature}
import com.twitter.visibility.features.{AuthorBlocksViewer => AuthorBlocksViewerFeature}
import com.twitter.visibility.features.{
  CommunityTweetAuthorIsRemoved => CommunityTweetAuthorIsRemovedFeature
}
import com.twitter.visibility.features.{
  CommunityTweetCommunityNotFound => CommunityTweetCommunityNotFoundFeature
}
import com.twitter.visibility.features.{
  CommunityTweetCommunityDeleted => CommunityTweetCommunityDeletedFeature
}
import com.twitter.visibility.features.{
  CommunityTweetCommunitySuspended => CommunityTweetCommunitySuspendedFeature
}
import com.twitter.visibility.features.{
  CommunityTweetCommunityVisible => CommunityTweetCommunityVisibleFeature
}
import com.twitter.visibility.features.{CommunityTweetIsHidden => CommunityTweetIsHiddenFeature}
import com.twitter.visibility.features.{
  NotificationIsOnCommunityTweet => NotificationIsOnCommunityTweetFeature
}
import com.twitter.visibility.features.{OuterAuthorFollowsAuthor => OuterAuthorFollowsAuthorFeature}
import com.twitter.visibility.features.{OuterAuthorIsInnerAuthor => OuterAuthorIsInnerAuthorFeature}
import com.twitter.visibility.features.{TweetHasCard => TweetHasCardFeature}
import com.twitter.visibility.features.{TweetHasMedia => TweetHasMediaFeature}
import com.twitter.visibility.features.{TweetIsCommunityTweet => TweetIsCommunityTweetFeature}
import com.twitter.visibility.features.{TweetIsEditTweet => TweetIsEditTweetFeature}
import com.twitter.visibility.features.{TweetIsStaleTweet => TweetIsStaleTweetFeature}
import com.twitter.visibility.features.{ViewerBlocksAuthor => ViewerBlocksAuthorFeature}
import com.twitter.visibility.features.{ViewerIsCommunityAdmin => ViewerIsCommunityAdminFeature}
import com.twitter.visibility.features.{ViewerIsCommunityMember => ViewerIsCommunityMemberFeature}
import com.twitter.visibility.features.{
  ViewerIsCommunityModerator => ViewerIsCommunityModeratorFeature
}
import com.twitter.visibility.features.{
  ViewerIsInternalCommunitiesAdmin => ViewerIsInternalCommunitiesAdminFeature
}
import com.twitter.visibility.features.{ViewerMutesAuthor => ViewerMutesAuthorFeature}
import com.twitter.visibility.features.{
  ViewerMutesRetweetsFromAuthor => ViewerMutesRetweetsFromAuthorFeature
}
import com.twitter.visibility.models.ViolationLevel
import com.twitter.visibility.models._
import com.twitter.visibility.rules.Result.FoundCardUriRootDomain
import com.twitter.visibility.rules.Result.FoundMediaLabel
import com.twitter.visibility.rules.Result.FoundSpaceLabel
import com.twitter.visibility.rules.Result.FoundSpaceLabelWithScoreAboveThreshold
import com.twitter.visibility.rules.Result.FoundTweetLabel
import com.twitter.visibility.rules.Result.FoundTweetLabelForPerspectivalUser
import com.twitter.visibility.rules.Result.FoundTweetLabelWithLanguageIn
import com.twitter.visibility.rules.Result.FoundTweetLabelWithLanguageScoreAboveThreshold
import com.twitter.visibility.rules.Result.FoundTweetLabelWithScoreAboveThreshold
import com.twitter.visibility.rules.Result.FoundTweetViolationOfLevel
import com.twitter.visibility.rules.Result.FoundTweetViolationOfSomeLevel
import com.twitter.visibility.rules.Result.FoundUserLabel
import com.twitter.visibility.rules.Result.FoundUserRole
import com.twitter.visibility.rules.Result.HasQuerySource
import com.twitter.visibility.rules.Result.HasTweetTimestampAfterCutoff
import com.twitter.visibility.rules.Result.HasTweetTimestampAfterOffset
import com.twitter.visibility.rules.Result.HasTweetTimestampBeforeCutoff
import com.twitter.visibility.rules.Result.ParamWasTrue
import com.twitter.visibility.rules.Result.Result
import com.twitter.visibility.rules.Result.Satisfied
import com.twitter.visibility.rules.Result.Unsatisfied
import com.twitter.visibility.util.NamingUtils
import com.twitter.visibility.{features => feats}

sealed trait PreFilterResult
case object Filtered etwittertends PreFilterResult
case object NeedsFullEvaluation etwittertends PreFilterResult
case object NotFiltered etwittertends PreFilterResult

sealed trait Condition {
  lazy val name: String = NamingUtils.getFriendlyName(this)
  def features: Set[Feature[_]]
  def optionalFeatures: Set[Feature[_]]

  def preFilter(
    evaluationContetwittert: EvaluationContetwittert,
    featureMap: Map[Feature[_], _]
  ): PreFilterResult = {
    if (features.forall(featureMap.contains)) {
      if (apply(evaluationContetwittert, featureMap).asBoolean) {
        NotFiltered
      } else {
        Filtered
      }
    } else {
      NeedsFullEvaluation
    }
  }

  def apply(evaluationContetwittert: EvaluationContetwittert, featureMap: Map[Feature[_], _]): Result
}

trait PreFilterOnOptionalFeatures etwittertends Condition {
  override def preFilter(
    evaluationContetwittert: EvaluationContetwittert,
    featureMap: Map[Feature[_], _]
  ): PreFilterResult =
    if ((features ++ optionalFeatures).forall(featureMap.contains)) {
      if (apply(evaluationContetwittert, featureMap).asBoolean) {
        NotFiltered
      } else {
        Filtered
      }
    } else {
      NeedsFullEvaluation
    }
}

trait HasSafetyLabelType {
  val labelTypes: Set[SafetyLabelType]
  def hasLabelType(labelType: SafetyLabelType): Boolean = labelTypes.contains(labelType)
}

sealed trait HasNestedConditions etwittertends HasSafetyLabelType {
  val conditions: Seq[Condition]
  override lazy val labelTypes: Set[SafetyLabelType] = conditions
    .collect {
      case lt: HasSafetyLabelType => lt.labelTypes
    }.flatten.toSet
}

object Result {
  sealed trait ConditionReason
  case object FoundInnerQuotedTweet etwittertends ConditionReason
  case object FoundTweetViolationOfSomeLevel etwittertends ConditionReason
  case class FoundTweetViolationOfLevel(level: ViolationLevel) etwittertends ConditionReason
  case class FoundTweetLabel(label: TweetSafetyLabelType) etwittertends ConditionReason
  case class FoundSpaceLabel(label: SpaceSafetyLabelType) etwittertends ConditionReason
  case class FoundMediaLabel(label: MediaSafetyLabelType) etwittertends ConditionReason
  case class FoundTweetLabelForPerspectivalUser(label: TweetSafetyLabelType) etwittertends ConditionReason
  case class FoundTweetLabelWithLanguageScoreAboveThreshold(
    label: TweetSafetyLabelType,
    languagesToScoreThresholds: Map[String, Double])
      etwittertends ConditionReason
  case class FoundTweetLabelWithScoreAboveThreshold(label: TweetSafetyLabelType, threshold: Double)
      etwittertends ConditionReason
  case class FoundTweetLabelWithLanguageIn(
    safetyLabelType: TweetSafetyLabelType,
    languages: Set[String])
      etwittertends ConditionReason
  case class FoundTweetSafetyLabelWithPredicate(safetyLabelType: TweetSafetyLabelType, name: String)
      etwittertends ConditionReason
  case class FoundUserLabel(label: UserLabelValue) etwittertends ConditionReason
  case class FoundMutedKeyword(keyword: String) etwittertends ConditionReason
  case object HasTweetTimestampAfterCutoff etwittertends ConditionReason
  case object HasTweetTimestampAfterOffset etwittertends ConditionReason
  case object HasTweetTimestampBeforeCutoff etwittertends ConditionReason
  case class IsTweetReplyToParentTweetBeforeDuration(duration: Duration) etwittertends ConditionReason
  case class IsTweetReplyToRootTweetBeforeDuration(duration: Duration) etwittertends ConditionReason
  case class HasQuerySource(querySource: ThriftQuerySource) etwittertends ConditionReason
  case class FoundUserRole(role: String) etwittertends ConditionReason
  case class ViewerInHrcj(jurisdiction: String) etwittertends ConditionReason
  case class ViewerOrRequestInCountry(country: String) etwittertends ConditionReason
  case class ViewerAgeInYears(ageInYears: Int) etwittertends ConditionReason
  case object NoViewerAge etwittertends ConditionReason
  case class ParamWasTrue(param: Param[Boolean]) etwittertends ConditionReason
  case class FoundCardUriRootDomain(domain: String) etwittertends ConditionReason
  case object Unknown etwittertends ConditionReason

  sealed trait Result {
    def asBoolean: Boolean
  }

  val SatisfiedResult: Result = Satisfied()

  case class Satisfied(reason: ConditionReason = Unknown) etwittertends Result {
    override val asBoolean: Boolean = true
  }

  case class Unsatisfied(condition: Condition) etwittertends Result {
    override val asBoolean: Boolean = false
  }

  def fromMutedKeyword(mutedKeyword: MutedKeyword, unsatisfied: Unsatisfied): Result = {
    mutedKeyword match {
      case MutedKeyword(Some(keyword)) => Satisfied(FoundMutedKeyword(keyword))
      case _ => unsatisfied
    }
  }

  case class FoundSpaceLabelWithScoreAboveThreshold(label: SpaceSafetyLabelType, threshold: Double)
      etwittertends ConditionReason
}

object Condition {

  abstract class BooleanFeatureCondition(feature: Feature[Boolean]) etwittertends Condition {
    override val features: Set[Feature[_]] = Set(feature)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      if (featureMap(feature).asInstanceOf[Boolean]) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
  }

  case class ParamIsTrue(param: Param[Boolean]) etwittertends Condition with HasParams {
    override lazy val name: String = s"ParamIsTrue(${NamingUtils.getFriendlyName(param)})"
    override val features: Set[Feature[_]] = Set.empty
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)
    private val SatisfiedResult = Satisfied(ParamWasTrue(param))

    override val params: Set[Param[_]] = Set(param)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      if (evaluationContetwittert.params(param)) {
        SatisfiedResult
      } else {
        UnsatisfiedResult
      }
  }

  case object Never etwittertends Condition {
    override lazy val name: String = s"""Never"""
    override val features: Set[Feature[_]] = Set.empty
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult = {
      NeedsFullEvaluation
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      UnsatisfiedResult
  }

  class BooleanCondition(value: Boolean) etwittertends Condition {
    override lazy val name: String = s"""${if (value) "True" else "False"}"""
    override val features: Set[Feature[_]] = Set.empty
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      value match {
        case true => Result.SatisfiedResult
        case false => UnsatisfiedResult
      }
  }

  case object True etwittertends BooleanCondition(true)
  case object False etwittertends BooleanCondition(false)

  abstract class ContentTakendownInViewerCountry(takedownFeature: Feature[Seq[TakedownReason]])
      etwittertends Condition {
    override val features: Set[Feature[_]] = Set(takedownFeature)
    override val optionalFeatures: Set[Feature[_]] = Set(RequestCountryCode)

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val requestCountryCode = featureMap.get(RequestCountryCode).asInstanceOf[Option[String]]
      val takedownReasons = featureMap(takedownFeature).asInstanceOf[Seq[TakedownReason]]
      if (TakedownReasonsUtil.isTakenDownIn(requestCountryCode, takedownReasons)) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object TweetTakendownInViewerCountry
      etwittertends ContentTakendownInViewerCountry(TweetTakedownReasons)

  case object AuthorTakendownInViewerCountry
      etwittertends ContentTakendownInViewerCountry(AuthorTakedownReasons)

  case object SuspendedAuthor etwittertends BooleanFeatureCondition(AuthorIsSuspended)

  case object SuspendedViewer etwittertends BooleanFeatureCondition(ViewerIsSuspended)

  case object DeactivatedViewer etwittertends BooleanFeatureCondition(ViewerIsDeactivated)

  case object UnavailableAuthor etwittertends BooleanFeatureCondition(AuthorIsUnavailable)

  case object IsVerifiedCrawlerViewer etwittertends BooleanFeatureCondition(RequestIsVerifiedCrawler)

  case object LoggedOutViewer etwittertends Condition {
    override val features: Set[Feature[_]] = Set.empty
    override val optionalFeatures: Set[Feature[_]] = Set(ViewerId)

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      if (featureMap.contains(ViewerId)) UnsatisfiedResult else Result.SatisfiedResult
  }

  case object IsSelfQuote etwittertends Condition {
    override val features: Set[Feature[_]] = Set(AuthorId, OuterAuthorId)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val authorIds = featureMap(AuthorId).asInstanceOf[Set[Long]]
      val outerAuthorId = featureMap(OuterAuthorId).asInstanceOf[Long]
      if (authorIds.contains(outerAuthorId)) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object ViewerIsAuthor etwittertends Condition {
    override val features: Set[Feature[_]] = Set(AuthorId)
    override val optionalFeatures: Set[Feature[_]] = Set(ViewerId)

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      if (featureMap.contains(ViewerId)) {
        val authorIds = featureMap(AuthorId).asInstanceOf[Set[Long]]
        val viewerId = featureMap(ViewerId).asInstanceOf[Long]
        if (authorIds.contains(viewerId)) {
          Result.SatisfiedResult
        } else {
          UnsatisfiedResult
        }
      } else {
        UnsatisfiedResult
      }
  }

  case object NonAuthorViewer etwittertends Condition {
    override val features: Set[Feature[_]] = Set(AuthorId)
    override val optionalFeatures: Set[Feature[_]] = Set(ViewerId)

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      if (featureMap.contains(ViewerId)) {
        val authorIds = featureMap(AuthorId).asInstanceOf[Set[Long]]
        val viewerId = featureMap(ViewerId).asInstanceOf[Long]
        if (authorIds.contains(viewerId)) {
          UnsatisfiedResult
        } else {
          Result.SatisfiedResult
        }
      } else {
        Result.SatisfiedResult
      }
  }

  case object ViewerFollowsAuthorOfFosnrViolatingTweet
      etwittertends BooleanFeatureCondition(ViewerFollowsAuthorOfViolatingTweet)

  case object ViewerDoesNotFollowAuthorOfFosnrViolatingTweet
      etwittertends BooleanFeatureCondition(ViewerDoesNotFollowAuthorOfViolatingTweet)

  case object ViewerDoesFollowAuthor etwittertends BooleanFeatureCondition(ViewerFollowsAuthor)

  case object AuthorDoesFollowViewer etwittertends BooleanFeatureCondition(AuthorFollowsViewer)

  case object AuthorBlocksViewer etwittertends BooleanFeatureCondition(AuthorBlocksViewerFeature)

  case object ViewerBlocksAuthor etwittertends BooleanFeatureCondition(ViewerBlocksAuthorFeature)

  case object ViewerIsUnmentioned etwittertends BooleanFeatureCondition(NotificationIsOnUnmentionedViewer)

  case object AuthorBlocksOuterAuthor
      etwittertends BooleanFeatureCondition(AuthorBlocksOuterAuthorFeature)

  case object OuterAuthorFollowsAuthor
      etwittertends BooleanFeatureCondition(OuterAuthorFollowsAuthorFeature)

  case object OuterAuthorIsInnerAuthor
      etwittertends BooleanFeatureCondition(OuterAuthorIsInnerAuthorFeature)

  case object ViewerMutesAuthor etwittertends BooleanFeatureCondition(ViewerMutesAuthorFeature)

  case object ViewerReportsAuthor etwittertends BooleanFeatureCondition(ViewerReportsAuthorAsSpam)
  case object ViewerReportsTweet etwittertends BooleanFeatureCondition(ViewerReportedTweet)

  case object IsQuotedInnerTweet etwittertends BooleanFeatureCondition(TweetIsInnerQuotedTweet)

  case object IsSourceTweet etwittertends BooleanFeatureCondition(TweetIsSourceTweet)

  case object ViewerMutesRetweetsFromAuthor
      etwittertends BooleanFeatureCondition(ViewerMutesRetweetsFromAuthorFeature)

  case object ConversationRootAuthorDoesFollowViewer
      etwittertends BooleanFeatureCondition(ConversationRootAuthorFollowsViewer)

  case object ViewerDoesFollowConversationRootAuthor
      etwittertends BooleanFeatureCondition(ViewerFollowsConversationRootAuthor)

  case object TweetIsCommunityTweet etwittertends BooleanFeatureCondition(TweetIsCommunityTweetFeature)

  case object NotificationIsOnCommunityTweet
      etwittertends BooleanFeatureCondition(NotificationIsOnCommunityTweetFeature)

  sealed trait CommunityTweetCommunityUnavailable etwittertends Condition

  case object CommunityTweetCommunityNotFound
      etwittertends BooleanFeatureCondition(CommunityTweetCommunityNotFoundFeature)
      with CommunityTweetCommunityUnavailable

  case object CommunityTweetCommunityDeleted
      etwittertends BooleanFeatureCondition(CommunityTweetCommunityDeletedFeature)
      with CommunityTweetCommunityUnavailable

  case object CommunityTweetCommunitySuspended
      etwittertends BooleanFeatureCondition(CommunityTweetCommunitySuspendedFeature)
      with CommunityTweetCommunityUnavailable

  case object CommunityTweetCommunityVisible
      etwittertends BooleanFeatureCondition(CommunityTweetCommunityVisibleFeature)

  case object ViewerIsInternalCommunitiesAdmin
      etwittertends BooleanFeatureCondition(ViewerIsInternalCommunitiesAdminFeature)

  case object ViewerIsCommunityAdmin etwittertends BooleanFeatureCondition(ViewerIsCommunityAdminFeature)

  case object ViewerIsCommunityModerator
      etwittertends BooleanFeatureCondition(ViewerIsCommunityModeratorFeature)

  case object ViewerIsCommunityMember
      etwittertends BooleanFeatureCondition(ViewerIsCommunityMemberFeature)

  sealed trait CommunityTweetIsModerated etwittertends Condition

  case object CommunityTweetIsHidden
      etwittertends BooleanFeatureCondition(CommunityTweetIsHiddenFeature)
      with CommunityTweetIsModerated

  case object CommunityTweetAuthorIsRemoved
      etwittertends BooleanFeatureCondition(CommunityTweetAuthorIsRemovedFeature)
      with CommunityTweetIsModerated

  case object DoesHaveInnerCircleOfFriendsRelationship
      etwittertends BooleanFeatureCondition(HasInnerCircleOfFriendsRelationship)

  case object TweetIsCommunityConversation
      etwittertends BooleanFeatureCondition(TweetHasCommunityConversationControl)

  case object TweetIsByInvitationConversation
      etwittertends BooleanFeatureCondition(TweetHasByInvitationConversationControl)

  case object TweetIsFollowersConversation
      etwittertends BooleanFeatureCondition(TweetHasFollowersConversationControl)

  case object ViewerIsTweetConversationRootAuthor
      etwittertends BooleanFeatureCondition(TweetConversationViewerIsRootAuthor)

  private case object ViewerIsInvitedToTweetConversationByMention
      etwittertends BooleanFeatureCondition(TweetConversationViewerIsInvited)

  private case object ViewerIsInvitedToTweetConversationByReplyMention
      etwittertends BooleanFeatureCondition(TweetConversationViewerIsInvitedViaReplyMention)

  object ViewerIsInvitedToTweetConversation
      etwittertends Or(
        ViewerIsInvitedToTweetConversationByMention,
        ViewerIsInvitedToTweetConversationByReplyMention)

  object TweetIsEtwitterclusiveContent etwittertends BooleanFeatureCondition(TweetIsEtwitterclusiveTweet)
  object ViewerIsEtwitterclusiveTweetAuthor
      etwittertends BooleanFeatureCondition(ViewerIsEtwitterclusiveTweetRootAuthor)
  object ViewerSuperFollowsEtwitterclusiveTweetAuthor
      etwittertends BooleanFeatureCondition(ViewerSuperFollowsEtwitterclusiveTweetRootAuthor)

  object TweetIsTrustedFriendsContent etwittertends BooleanFeatureCondition(TweetIsTrustedFriendTweet)
  object ViewerIsTrustedFriendsTweetAuthor
      etwittertends BooleanFeatureCondition(ViewerIsTrustedFriendTweetAuthor)
  object ViewerIsTrustedFriend etwittertends BooleanFeatureCondition(ViewerIsTrustedFriendOfTweetAuthor)

  object TweetIsCollabInvitationContent
      etwittertends BooleanFeatureCondition(TweetIsCollabInvitationTweet)

  case class TweetHasLabelForPerspectivalUser(safetyLabel: TweetSafetyLabelType)
      etwittertends Condition
      with HasSafetyLabelType {
    override lazy val name: String = s"TweetHasLabelForPerspectivalUser(${safetyLabel.name})"
    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set(ViewerId)
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(
      FoundTweetLabelForPerspectivalUser(safetyLabel)
    )

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      if (!featureMap.contains(ViewerId)) {
        UnsatisfiedResult
      } else {
        val viewerId = featureMap(ViewerId).asInstanceOf[Long]
        val labels = featureMap(TweetSafetyLabels).asInstanceOf[Seq[TweetSafetyLabel]]
        labels
          .collectFirst {
            case label
                if label.labelType == safetyLabel && label.applicableUsers.contains(viewerId)
                  && EtwitterperimentBase.shouldFilterForSource(evaluationContetwittert.params, label.source) =>
              SatisfiedResult
          }.getOrElse(UnsatisfiedResult)
      }
    }
  }

  case class TweetHasLabel(
    safetyLabel: TweetSafetyLabelType,
    labelSourceEtwitterperimentPredicate: Option[(Params, Option[LabelSource]) => Boolean] = None)
      etwittertends Condition
      with HasSafetyLabelType {
    override lazy val name: String = s"TweetHasLabel(${safetyLabel.name})"
    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(FoundTweetLabel(safetyLabel))

    private val labelSourcePredicate: (Params, Option[LabelSource]) => Boolean =
      labelSourceEtwitterperimentPredicate match {
        case Some(predicate) => predicate
        case _ => EtwitterperimentBase.shouldFilterForSource
      }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(TweetSafetyLabels).asInstanceOf[Seq[TweetSafetyLabel]]
      labels
        .collectFirst {
          case label
              if label.labelType == safetyLabel
                && labelSourcePredicate(evaluationContetwittert.params, label.source) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class SpaceHasLabel(
    safetyLabelType: SpaceSafetyLabelType)
      etwittertends Condition
      with HasSafetyLabelType {
    override lazy val name: String = s"SpaceHasLabel(${safetyLabelType.name})"
    override val features: Set[Feature[_]] = Set(SpaceSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(FoundSpaceLabel(safetyLabelType))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(SpaceSafetyLabels).asInstanceOf[Seq[SpaceSafetyLabel]]
      labels
        .collectFirst {
          case label if label.safetyLabelType == safetyLabelType =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class MediaHasLabel(
    safetyLabelType: MediaSafetyLabelType)
      etwittertends Condition
      with HasSafetyLabelType {
    override lazy val name: String = s"MediaHasLabel(${safetyLabelType.name})"
    override val features: Set[Feature[_]] = Set(MediaSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(FoundMediaLabel(safetyLabelType))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(MediaSafetyLabels).asInstanceOf[Seq[MediaSafetyLabel]]
      labels
        .collectFirst {
          case label if label.safetyLabelType == safetyLabelType =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class TweetHasLabelWithLanguageScoreAboveThreshold(
    safetyLabel: TweetSafetyLabelType,
    languagesToScoreThresholds: Map[String, Double])
      etwittertends Condition
      with HasSafetyLabelType {

    override lazy val name: String =
      s"TweetHasLabelWithLanguageScoreAboveThreshold(${safetyLabel.name}, ${languagesToScoreThresholds.toString})"
    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied =
      Satisfied(
        FoundTweetLabelWithLanguageScoreAboveThreshold(safetyLabel, languagesToScoreThresholds))

    private[this] def isAboveThreshold(label: TweetSafetyLabel) = {
      val isAboveThresholdOpt = for {
        modelMetadata <- label.modelMetadata
        calibratedLanguage <- modelMetadata.calibratedLanguage
        threshold <- languagesToScoreThresholds.get(calibratedLanguage)
        score <- label.score
      } yield score >= threshold

      isAboveThresholdOpt.getOrElse(false)
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(TweetSafetyLabels).asInstanceOf[Seq[TweetSafetyLabel]]
      labels
        .collectFirst {
          case label
              if label.labelType == safetyLabel
                && isAboveThreshold(label) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class TweetHasLabelWithScoreAboveThreshold(
    safetyLabel: TweetSafetyLabelType,
    threshold: Double)
      etwittertends Condition
      with HasSafetyLabelType {

    override lazy val name: String =
      s"TweetHasLabelWithScoreAboveThreshold(${safetyLabel.name}, $threshold)"
    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)

    private val UnsatisfiedResult = Unsatisfied(this)
    private val SatisfiedResult =
      Satisfied(FoundTweetLabelWithScoreAboveThreshold(safetyLabel, threshold))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(TweetSafetyLabels).asInstanceOf[Seq[TweetSafetyLabel]]
      labels
        .collectFirst {
          case label
              if label.labelType == safetyLabel
                && label.score.etwitterists(_ >= threshold) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class TweetHasLabelWithScoreAboveThresholdWithParam(
    safetyLabel: TweetSafetyLabelType,
    thresholdParam: Param[Double])
      etwittertends Condition
      with HasSafetyLabelType
      with HasParams {
    override lazy val name: String =
      s"TweetHasLabelWithScoreAboveThreshold(${safetyLabel.name}, ${NamingUtils.getFriendlyName(thresholdParam)})"
    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)
    private val UnsatisfiedResult = Unsatisfied(this)
    override val params: Set[Param[_]] = Set(thresholdParam)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(TweetSafetyLabels).asInstanceOf[Seq[TweetSafetyLabel]]
      val threshold = evaluationContetwittert.params(thresholdParam)
      val SatisfiedResult =
        Satisfied(FoundTweetLabelWithScoreAboveThreshold(safetyLabel, threshold))
      labels
        .collectFirst {
          case label
              if label.labelType == safetyLabel
                && label.score.etwitterists(_ >= threshold) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class TweetHasLabelWithLanguageIn(
    safetyLabelType: TweetSafetyLabelType,
    languages: Set[String])
      etwittertends Condition
      with HasSafetyLabelType {

    override lazy val name: String =
      s"TweetHasLabelWithLanguageIn($safetyLabelType, $languages)"
    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied =
      Satisfied(FoundTweetLabelWithLanguageIn(safetyLabelType, languages))

    private[this] def hasLanguageMatch(label: TweetSafetyLabel): Boolean = {
      val isMatchingLanguageOpt = for {
        metadata <- label.modelMetadata
        language <- metadata.calibratedLanguage
      } yield languages.contains(language)
      isMatchingLanguageOpt.getOrElse(false)
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(TweetSafetyLabels)
        .asInstanceOf[Seq[TweetSafetyLabel]]
        .collectFirst {
          case label if label.labelType == safetyLabelType && hasLanguageMatch(label) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class TweetHasLabelWithLanguagesWithParam(
    safetyLabelType: TweetSafetyLabelType,
    languageParam: Param[Seq[String]])
      etwittertends Condition
      with HasSafetyLabelType
      with HasParams {
    override lazy val name: String =
      s"TweetHasLabelWithLanguageIn($safetyLabelType, ${NamingUtils.getFriendlyName(languageParam)})"
    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)
    override val params: Set[Param[_]] = Set(languageParam)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)

    private[this] def hasLanguageMatch(label: TweetSafetyLabel, languages: Set[String]): Boolean = {
      val isMatchingLanguageOpt = for {
        metadata <- label.modelMetadata
        language <- metadata.calibratedLanguage
      } yield languages.contains(language)
      isMatchingLanguageOpt.getOrElse(false)
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val languages = evaluationContetwittert.params(languageParam).toSet
      val SatisfiedResult: Satisfied =
        Satisfied(FoundTweetLabelWithLanguageIn(safetyLabelType, languages))
      featureMap(TweetSafetyLabels)
        .asInstanceOf[Seq[TweetSafetyLabel]]
        .collectFirst {
          case label if label.labelType == safetyLabelType && hasLanguageMatch(label, languages) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  type TweetSafetyLabelPredicateFn = (TweetSafetyLabel) => Boolean
  abstract class NamedTweetSafetyLabelPredicate(
    private[rules] val fn: TweetSafetyLabelPredicateFn,
    private[rules] val name: String)

  abstract class TweetHasSafetyLabelWithPredicate(
    private[rules] val safetyLabelType: TweetSafetyLabelType,
    private[rules] val predicate: NamedTweetSafetyLabelPredicate)
      etwittertends Condition
      with HasSafetyLabelType {

    override lazy val name: String =
      s"TweetHasSafetyLabelWithPredicate(${predicate.name}($safetyLabelType))"
    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied =
      Satisfied(Result.FoundTweetSafetyLabelWithPredicate(safetyLabelType, predicate.name))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(TweetSafetyLabels)
        .asInstanceOf[Seq[TweetSafetyLabel]]
        .collectFirst {
          case label if label.labelType == safetyLabelType && predicate.fn(label) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  object TweetHasSafetyLabelWithPredicate {
    def unapply(
      condition: TweetHasSafetyLabelWithPredicate
    ): Option[(TweetSafetyLabelType, NamedTweetSafetyLabelPredicate)] =
      Some((condition.safetyLabelType, condition.predicate))
  }

  case class WithScoreEqInt(score: Int)
      etwittertends NamedTweetSafetyLabelPredicate(
        fn = tweetSafetyLabel => tweetSafetyLabel.score.etwitterists(s => s.intValue() == score),
        name = "WithScoreEqInt"
      )
  case class TweetHasSafetyLabelWithScoreEqInt(
    override val safetyLabelType: TweetSafetyLabelType,
    score: Int)
      etwittertends TweetHasSafetyLabelWithPredicate(
        safetyLabelType,
        predicate = WithScoreEqInt(score)
      )

  case class TweetReplyToParentTweetBeforeDuration(duration: Duration) etwittertends Condition {
    override val features: Set[Feature[_]] = Set(TweetParentId, TweetTimestamp)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(
      Result.IsTweetReplyToParentTweetBeforeDuration(duration))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap
        .get(TweetParentId).collect {
          case tweetParentId: Long =>
            featureMap
              .get(TweetTimestamp).collect {
                case tweetTimestamp: Time
                    if tweetTimestamp.diff(SnowflakeId.timeFromId(tweetParentId)) < duration =>
                  SatisfiedResult
              }.getOrElse(UnsatisfiedResult)
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class TweetReplyToRootTweetBeforeDuration(duration: Duration) etwittertends Condition {
    override val features: Set[Feature[_]] = Set(TweetConversationId, TweetTimestamp)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(
      Result.IsTweetReplyToRootTweetBeforeDuration(duration))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap
        .get(TweetConversationId).collect {
          case tweetConversationId: Long =>
            featureMap
              .get(TweetTimestamp).collect {
                case tweetTimestamp: Time
                    if tweetTimestamp.diff(
                      SnowflakeId.timeFromId(tweetConversationId)) < duration =>
                  SatisfiedResult
              }.getOrElse(UnsatisfiedResult)
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class TweetComposedBefore(cutoffTimestamp: Time) etwittertends Condition {
    assert(cutoffTimestamp.inMilliseconds > SnowflakeId.FirstSnowflakeIdUnitwitterTime)

    override val features: Set[Feature[_]] = Set(TweetTimestamp)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(HasTweetTimestampBeforeCutoff)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(TweetTimestamp) match {
        case timestamp: Time if timestamp > cutoffTimestamp => UnsatisfiedResult
        case _ => SatisfiedResult
      }
    }
  }

  case class TweetComposedAfter(cutoffTimestamp: Time) etwittertends Condition {
    assert(cutoffTimestamp.inMilliseconds > SnowflakeId.FirstSnowflakeIdUnitwitterTime)

    override val features: Set[Feature[_]] = Set(TweetTimestamp)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(HasTweetTimestampAfterCutoff)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(TweetTimestamp) match {
        case timestamp: Time if timestamp > cutoffTimestamp => SatisfiedResult
        case _ => UnsatisfiedResult
      }
    }
  }

  case class TweetComposedAfterOffset(offset: Duration) etwittertends Condition {
    override val features: Set[Feature[_]] = Set(TweetTimestamp)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(HasTweetTimestampAfterOffset)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(TweetTimestamp) match {
        case timestamp: Time if timestamp > Time.now.minus(offset) => SatisfiedResult
        case _ => UnsatisfiedResult
      }
    }
  }

  case class TweetComposedAfterWithParam(cutoffTimeParam: Param[Time])
      etwittertends Condition
      with HasParams {
    override val features: Set[Feature[_]] = Set(TweetTimestamp)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val params: Set[Param[_]] = Set(cutoffTimeParam)
    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(HasTweetTimestampAfterCutoff)

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult = {
      val cutoffTimestamp = evaluationContetwittert.params(cutoffTimeParam)
      if (cutoffTimestamp.inMilliseconds < SnowflakeId.FirstSnowflakeIdUnitwitterTime) {
        Filtered
      } else {
        super.preFilter(evaluationContetwittert, featureMap)
      }
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val cutoffTimestamp = evaluationContetwittert.params(cutoffTimeParam)
      featureMap(TweetTimestamp) match {
        case _: Time if cutoffTimestamp.inMilliseconds < SnowflakeId.FirstSnowflakeIdUnitwitterTime =>
          UnsatisfiedResult
        case timestamp: Time if timestamp > cutoffTimestamp => SatisfiedResult
        case _ => UnsatisfiedResult
      }
    }
  }

  case class AuthorHasLabel(labelValue: UserLabelValue, shortCircuitable: Boolean = true)
      etwittertends Condition
      with HasSafetyLabelType {
    override lazy val name: String = s"AuthorHasLabel(${labelValue.name})"
    override val features: Set[Feature[_]] = Set(AuthorUserLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(labelValue)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(FoundUserLabel(labelValue))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(AuthorUserLabels).asInstanceOf[Seq[Label]].map(UserLabel.fromThrift)
      labels
        .collectFirst {
          case label
              if label.labelValue == labelValue
                && EtwitterperimentBase.shouldFilterForSource(evaluationContetwittert.params, label.source) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  abstract class ViewerHasRole(role: String) etwittertends Condition {
    override lazy val name: String = s"ViewerHasRole(${role})"
    override val features: Set[Feature[_]] = Set(ViewerRoles)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(FoundUserRole(role))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val roles = featureMap(ViewerRoles).asInstanceOf[Seq[String]]
      if (roles.contains(role)) {
        SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object ViewerIsEmployee etwittertends ViewerHasRole(ViewerRoles.EmployeeRole)

  case class ViewerHasLabel(labelValue: UserLabelValue) etwittertends Condition with HasSafetyLabelType {
    override lazy val name: String = s"ViewerHasLabel(${labelValue.name})"
    override val features: Set[Feature[_]] = Set(ViewerUserLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    override val labelTypes: Set[SafetyLabelType] = Set(labelValue)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(FoundUserLabel(labelValue))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(ViewerUserLabels).asInstanceOf[Seq[Label]].map(UserLabel.fromThrift)
      labels
        .collectFirst {
          case label
              if label.labelValue == labelValue
                && EtwitterperimentBase.shouldFilterForSource(evaluationContetwittert.params, label.source) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case object DeactivatedAuthor etwittertends BooleanFeatureCondition(AuthorIsDeactivated)
  case object ErasedAuthor etwittertends BooleanFeatureCondition(AuthorIsErased)
  case object OffboardedAuthor etwittertends BooleanFeatureCondition(AuthorIsOffboarded)
  case object ProtectedAuthor etwittertends BooleanFeatureCondition(AuthorIsProtected)
  case object VerifiedAuthor etwittertends BooleanFeatureCondition(AuthorIsVerified)
  case object NsfwUserAuthor etwittertends BooleanFeatureCondition(AuthorIsNsfwUser)
  case object NsfwAdminAuthor etwittertends BooleanFeatureCondition(AuthorIsNsfwAdmin)
  case object TweetHasNsfwUserAuthor etwittertends BooleanFeatureCondition(TweetHasNsfwUser)
  case object TweetHasNsfwAdminAuthor etwittertends BooleanFeatureCondition(TweetHasNsfwAdmin)
  case object TweetHasMedia etwittertends BooleanFeatureCondition(TweetHasMediaFeature)
  case object TweetHasDmcaMedia etwittertends BooleanFeatureCondition(HasDmcaMediaFeature)
  case object TweetHasCard etwittertends BooleanFeatureCondition(TweetHasCardFeature)
  case object IsPollCard etwittertends BooleanFeatureCondition(CardIsPoll)

  case object ProtectedViewer etwittertends BooleanFeatureCondition(ViewerIsProtected)
  case object SoftViewer etwittertends BooleanFeatureCondition(ViewerIsSoftUser)

  case object ViewerHasUqfEnabled
      etwittertends BooleanFeatureCondition(ViewerHasUniversalQualityFilterEnabled)

  abstract class ViewerHasMatchingKeywordFor(muteSurface: MuteSurface) etwittertends Condition {
    override def features: Set[Feature[_]] = Set(feature)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    private val feature: Feature[MutedKeyword] = muteSurface match {
      case MuteSurface.HomeTimeline => ViewerMutesKeywordInTweetForHomeTimeline
      case MuteSurface.Notifications => ViewerMutesKeywordInTweetForNotifications
      case MuteSurface.TweetReplies => ViewerMutesKeywordInTweetForTweetReplies

      case _ => throw new NoSuchElementEtwitterception(muteSurface.toString)
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val mutedKeyword = featureMap(feature)
        .asInstanceOf[MutedKeyword]
      Result.fromMutedKeyword(mutedKeyword, UnsatisfiedResult)
    }
  }

  case object ViewerHasMatchingKeywordForHomeTimeline
      etwittertends ViewerHasMatchingKeywordFor(MuteSurface.HomeTimeline)

  case object ViewerHasMatchingKeywordForNotifications
      etwittertends ViewerHasMatchingKeywordFor(MuteSurface.Notifications)

  case object ViewerHasMatchingKeywordForTweetReplies
      etwittertends ViewerHasMatchingKeywordFor(MuteSurface.TweetReplies)

  case object ViewerHasMatchingKeywordForAllSurfaces etwittertends Condition {
    override def features: Set[Feature[_]] = Set(ViewerMutesKeywordInTweetForAllSurfaces)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val mutedKeyword = featureMap(ViewerMutesKeywordInTweetForAllSurfaces)
        .asInstanceOf[MutedKeyword]
      Result.fromMutedKeyword(mutedKeyword, UnsatisfiedResult)
    }
  }

  abstract class ViewerHasMatchingKeywordInSpaceTitleFor(muteSurface: MuteSurface)
      etwittertends Condition {
    override def features: Set[Feature[_]] = Set(feature)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    private val feature: Feature[MutedKeyword] = muteSurface match {
      case MuteSurface.Notifications => ViewerMutesKeywordInSpaceTitleForNotifications
      case _ => throw new NoSuchElementEtwitterception(muteSurface.toString)
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val mutedKeyword = featureMap(feature)
        .asInstanceOf[MutedKeyword]
      Result.fromMutedKeyword(mutedKeyword, UnsatisfiedResult)
    }
  }

  case object ViewerHasMatchingKeywordInSpaceTitleForNotifications
      etwittertends ViewerHasMatchingKeywordInSpaceTitleFor(MuteSurface.Notifications)

  case object ViewerFiltersNoConfirmedEmail
      etwittertends BooleanFeatureCondition(
        com.twitter.visibility.features.ViewerFiltersNoConfirmedEmail
      )

  case object ViewerFiltersNoConfirmedPhone
      etwittertends BooleanFeatureCondition(
        com.twitter.visibility.features.ViewerFiltersNoConfirmedPhone
      )

  case object ViewerFiltersDefaultProfileImage
      etwittertends BooleanFeatureCondition(
        com.twitter.visibility.features.ViewerFiltersDefaultProfileImage
      )

  case object ViewerFiltersNewUsers
      etwittertends BooleanFeatureCondition(
        com.twitter.visibility.features.ViewerFiltersNewUsers
      )

  case object ViewerFiltersNotFollowedBy
      etwittertends BooleanFeatureCondition(
        com.twitter.visibility.features.ViewerFiltersNotFollowedBy
      )

  case object AuthorHasConfirmedEmail
      etwittertends BooleanFeatureCondition(
        com.twitter.visibility.features.AuthorHasConfirmedEmail
      )

  case object AuthorHasVerifiedPhone
      etwittertends BooleanFeatureCondition(
        com.twitter.visibility.features.AuthorHasVerifiedPhone
      )

  case object AuthorHasDefaultProfileImage
      etwittertends BooleanFeatureCondition(
        com.twitter.visibility.features.AuthorHasDefaultProfileImage
      )

  case object AuthorIsNewAccount etwittertends Condition {
    override val features: Set[Feature[_]] = Set(AuthorAccountAge)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val age = featureMap(AuthorAccountAge).asInstanceOf[Duration]

      if (age < 72.hours) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  abstract class ViewerInJurisdiction etwittertends Condition {
    override def features: Set[Feature[_]] = Set.empty
    override val optionalFeatures: Set[Feature[_]] = Set(RequestCountryCode, ViewerCountryCode)

    protected val unsatisfiedResult = Unsatisfied(this)

    protected case class CountryFeatures(
      requestCountryCode: Option[String],
      viewerCountryCode: Option[String])

    def getCountryFeatures(featureMap: Map[Feature[_], _]): CountryFeatures = {
      val requestCountryCodeOpt = featureMap
        .get(RequestCountryCode)
        .map(_.asInstanceOf[String])
      val viewerCountryCodeOpt = featureMap
        .get(ViewerCountryCode)
        .map(_.asInstanceOf[String])

      CountryFeatures(requestCountryCodeOpt, viewerCountryCodeOpt)
    }
  }

  case class ViewerInHrcj(jurisdictions: Set[String]) etwittertends ViewerInJurisdiction {

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult =
      featureMap
        .get(RequestCountryCode)
        .map(_.asInstanceOf[String])
        .collectFirst {
          case rcc if jurisdictions.contains(rcc) => NeedsFullEvaluation
        }
        .getOrElse(Filtered)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val countryFeatures = getCountryFeatures(featureMap)

      countryFeatures match {
        case CountryFeatures(Some(rcc), Some(vcc))
            if jurisdictions.contains(rcc) && vcc.equals(rcc) =>
          Satisfied(Result.ViewerInHrcj(rcc))
        case _ => unsatisfiedResult
      }
    }
  }

  case class ViewerOrRequestInJurisdiction(enabledCountriesParam: Param[Seq[String]])
      etwittertends ViewerInJurisdiction
      with HasParams
      with PreFilterOnOptionalFeatures {

    override val params: Set[Param[_]] = Set(enabledCountriesParam)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val countries: Seq[String] =
        evaluationContetwittert.params(enabledCountriesParam).map(c => c.toLowerCase)
      val countryFeatures = getCountryFeatures(featureMap)

      val countryCodeOpt =
        countryFeatures.viewerCountryCode.orElse(countryFeatures.requestCountryCode)

      countryCodeOpt match {
        case Some(countryCode) if countries.contains(countryCode) =>
          Satisfied(Result.ViewerOrRequestInCountry(countryCode))
        case _ => unsatisfiedResult
      }
    }
  }

  case class ViewerAgeInYearsGte(ageToCompare: Int, ignoreEmptyAge: Boolean = false)
      etwittertends Condition
      with PreFilterOnOptionalFeatures {
    override def features: Set[Feature[_]] = Set.empty
    override def optionalFeatures: Set[Feature[_]] = Set(ViewerAge)

    private val unsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      featureMap
        .get(ViewerAge)
        .map(_.asInstanceOf[UserAge])
        .collectFirst {
          case UserAge(Some(age)) if age >= ageToCompare =>
            Satisfied(Result.ViewerAgeInYears(age))
          case UserAge(None) if ignoreEmptyAge =>
            Satisfied(Result.NoViewerAge)
        }
        .getOrElse(unsatisfiedResult)
  }

  case class UnderageViewer(ageToCompare: Int) etwittertends Condition with PreFilterOnOptionalFeatures {
    override def features: Set[Feature[_]] = Set.empty
    override def optionalFeatures: Set[Feature[_]] = Set(ViewerAge)

    private val unsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      featureMap
        .get(ViewerAge)
        .map(_.asInstanceOf[UserAge])
        .collectFirst {
          case UserAge(Some(age)) if age < ageToCompare => Satisfied(Result.ViewerAgeInYears(age))
        }
        .getOrElse(unsatisfiedResult)
  }

  case object ViewerMissingAge etwittertends Condition with PreFilterOnOptionalFeatures {
    override def features: Set[Feature[_]] = Set.empty
    override def optionalFeatures: Set[Feature[_]] = Set(ViewerAge)

    private val unsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      featureMap
        .get(ViewerAge)
        .map(_.asInstanceOf[UserAge])
        .collectFirst {
          case UserAge(None) => Satisfied(Result.NoViewerAge)
        }
        .getOrElse(unsatisfiedResult)
  }

  case object ViewerOptInBlockingOnSearch etwittertends BooleanFeatureCondition(ViewerOptInBlocking)
  case object ViewerOptInFilteringOnSearch etwittertends BooleanFeatureCondition(ViewerOptInFiltering)
  case object SelfReply etwittertends BooleanFeatureCondition(TweetIsSelfReply)
  case object Nullcast etwittertends BooleanFeatureCondition(TweetIsNullcast)
  case object Moderated etwittertends BooleanFeatureCondition(TweetIsModerated)
  case object Retweet etwittertends BooleanFeatureCondition(TweetIsRetweet)

  case object IsFirstPageSearchResult etwittertends Condition {
    override val features: Set[Feature[_]] = Set(SearchResultsPageNumber)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val searchResultsPageNumber = featureMap(SearchResultsPageNumber).asInstanceOf[Int]
      if (searchResultsPageNumber == 1) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object HasSearchCandidateCountGreaterThan45 etwittertends Condition {
    override val features: Set[Feature[_]] = Set(SearchCandidateCount)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val searchCandidateCount = featureMap(SearchCandidateCount).asInstanceOf[Int]
      if (searchCandidateCount > 45) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  abstract class HasSearchQuerySource(querySourceToMatch: ThriftQuerySource) etwittertends Condition {
    override lazy val name: String = s"HasSearchQuerySource(${querySourceToMatch})"
    override val features: Set[Feature[_]] = Set(SearchQuerySource)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)
    private val SatisfiedResult: Satisfied = Satisfied(HasQuerySource(querySourceToMatch))

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val querySource = featureMap(SearchQuerySource).asInstanceOf[ThriftQuerySource]
      if (querySourceToMatch.equals(querySource)) {
        SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object IsTrendClickSourceSearchResult etwittertends Condition {
    override val features: Set[Feature[_]] = Set(SearchQuerySource)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)

    private def checkQuerySource[T](
      featureMap: Map[Feature[_], _],
      nonTrendSourceResult: T,
      trendSourceResult: T
    ): T = {
      val searchResultsPageNumber = featureMap(SearchQuerySource).asInstanceOf[ThriftQuerySource]
      if (searchResultsPageNumber == ThriftQuerySource.TrendClick) {
        trendSourceResult
      } else {
        nonTrendSourceResult
      }
    }

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult =
      checkQuerySource(featureMap, Filtered, NotFiltered)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      checkQuerySource(featureMap, UnsatisfiedResult, Result.SatisfiedResult)
  }
  case object IsSearchHashtagClick etwittertends HasSearchQuerySource(ThriftQuerySource.HashtagClick)
  case object IsSearchTrendClick etwittertends HasSearchQuerySource(ThriftQuerySource.TrendClick)

  case object SearchQueryHasUser
      etwittertends BooleanFeatureCondition(com.twitter.visibility.features.SearchQueryHasUser)

  case class Equals[T](feature: Feature[T], value: T) etwittertends Condition {

    override def features: Set[Feature[_]] = Set(feature)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val SatisfiedResult: Result = Satisfied()
    private val UnsatisfiedResult: Result = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val featureValue = featureMap(feature).asInstanceOf[T]
      if (featureValue.equals(value)) SatisfiedResult else UnsatisfiedResult
    }
  }

  case class FeatureEquals[T](left: Feature[T], right: Feature[T]) etwittertends Condition {

    override def features: Set[Feature[_]] = Set.empty
    override val optionalFeatures: Set[Feature[_]] = Set(left, right)

    private val SatisfiedResult: Result = Satisfied()
    private val UnsatisfiedResult: Result = Unsatisfied(this)

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult = {
      if (featureMap.contains(left) && featureMap.contains(right)) {
        if (apply(evaluationContetwittert, featureMap).asBoolean) {
          NotFiltered
        } else {
          Filtered
        }
      } else {
        NeedsFullEvaluation
      }
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      if (featureMap.contains(left) && featureMap.contains(right)) {
        val leftValue = featureMap(left).asInstanceOf[T]
        val rightValue = featureMap(right).asInstanceOf[T]
        if (leftValue.equals(rightValue)) SatisfiedResult else UnsatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case class And(override val conditions: Condition*)
      etwittertends Condition
      with HasNestedConditions
      with HasParams {
    override lazy val name: String = s"(${conditions.map(_.name).mkString(" And ")})"
    override val features: Set[Feature[_]] = conditions.flatMap(_.features).toSet
    override val optionalFeatures: Set[Feature[_]] = conditions.flatMap(_.optionalFeatures).toSet
    override val params: Set[Param[_]] =
      conditions.collect { case p: HasParams => p.params }.flatten.toSet

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult = {
      conditions.foldLeft(NotFiltered: PreFilterResult) {
        case (NotFiltered, condition) => condition.preFilter(evaluationContetwittert, featureMap)
        case (Filtered, _) => Filtered
        case (NeedsFullEvaluation, condition) => {
          condition.preFilter(evaluationContetwittert, featureMap) match {
            case Filtered => Filtered
            case _ => NeedsFullEvaluation
          }
        }
      }
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      conditions.foldLeft(Result.SatisfiedResult) {
        case (result @ Unsatisfied(_), _) => result
        case (Result.SatisfiedResult, condition) => condition.apply(evaluationContetwittert, featureMap)
        case (result @ Satisfied(_), condition) => {
          condition.apply(evaluationContetwittert, featureMap) match {
            case r @ Unsatisfied(_) => r
            case _ => result
          }
        }
      }
    }
  }

  case class Or(override val conditions: Condition*)
      etwittertends Condition
      with HasNestedConditions
      with HasParams {
    override lazy val name: String = s"(${conditions.map(_.name).mkString(" Or ")})"
    override val features: Set[Feature[_]] = conditions.flatMap(_.features).toSet
    override val optionalFeatures: Set[Feature[_]] = conditions.flatMap(_.optionalFeatures).toSet
    override val params: Set[Param[_]] =
      conditions.collect { case p: HasParams => p.params }.flatten.toSet

    private val UnsatisfiedResult = Unsatisfied(this)

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult = {
      conditions.foldLeft(Filtered: PreFilterResult) {
        case (Filtered, c) => c.preFilter(evaluationContetwittert, featureMap)
        case (NotFiltered, _) => NotFiltered
        case (NeedsFullEvaluation, c) => {
          c.preFilter(evaluationContetwittert, featureMap) match {
            case NotFiltered => NotFiltered
            case _ => NeedsFullEvaluation
          }
        }
      }
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val foundSatisfiedCondition =
        conditions.find(_.apply(evaluationContetwittert, featureMap).asBoolean)
      if (foundSatisfiedCondition.isDefined) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case class Not(condition: Condition) etwittertends Condition with HasNestedConditions with HasParams {
    override lazy val name: String = s"Not(${condition.name})"
    override val features: Set[Feature[_]] = condition.features
    override val optionalFeatures: Set[Feature[_]] = condition.optionalFeatures
    override val conditions: Seq[Condition] = Seq(condition)
    override val params: Set[Param[_]] =
      conditions.collect { case p: HasParams => p.params }.flatten.toSet

    private val UnsatisfiedResult = Unsatisfied(this)

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult =
      condition.preFilter(evaluationContetwittert, featureMap) match {
        case Filtered => NotFiltered
        case NotFiltered => Filtered
        case _ => NeedsFullEvaluation
      }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      if (condition(evaluationContetwittert, featureMap).asBoolean) {
        UnsatisfiedResult
      } else {
        Result.SatisfiedResult
      }
  }

  val LoggedOutOrViewerNotFollowingAuthor: And =
    And(NonAuthorViewer, Or(LoggedOutViewer, Not(ViewerDoesFollowAuthor)))

  val LoggedOutOrViewerOptInFiltering: Or =
    Or(LoggedOutViewer, ViewerOptInFilteringOnSearch)

  val LoggedInViewer: Not = Not(LoggedOutViewer)

  val OuterAuthorNotFollowingAuthor: And =
    And(Not(OuterAuthorIsInnerAuthor), Not(OuterAuthorFollowsAuthor))

  val IsFocalTweet: FeatureEquals[Long] = FeatureEquals(TweetId, FocalTweetId)

  val NonHydratingConditions: Set[Class[_]] = Set(
    LoggedOutViewer,
    NonAuthorViewer,
    True,
    TweetComposedAfter(Time.now),
    TweetComposedBefore(Time.now)
  ).map(_.getClass)

  trait HasParams {
    val params: Set[Param[_]]
  }

  def hasLabelCondition(condition: Condition, tweetSafetyLabelType: TweetSafetyLabelType): Boolean =
    condition match {
      case lt: HasSafetyLabelType =>
        lt.hasLabelType(tweetSafetyLabelType)
      case _ => false
    }

  def hasLabelCondition(condition: Condition, userLabelValue: UserLabelValue): Boolean =
    condition match {
      case lt: HasSafetyLabelType =>
        lt.hasLabelType(userLabelValue)
      case _ => false
    }

  def hasLabelCondition(condition: Condition, spaceSafetyLabelType: SpaceSafetyLabelType): Boolean =
    condition match {
      case lt: HasSafetyLabelType =>
        lt.hasLabelType(spaceSafetyLabelType)
      case _ => false
    }

  def hasLabelCondition(condition: Condition, mediaSafetyLabelType: MediaSafetyLabelType): Boolean =
    condition match {
      case lt: HasSafetyLabelType =>
        lt.hasLabelType(mediaSafetyLabelType)
      case _ => false
    }

  case class Choose[T](
    conditionMap: Map[T, Condition],
    defaultCondition: Condition,
    choiceParam: Param[T])
      etwittertends Condition
      with HasNestedConditions
      with HasParams {
    override lazy val name: String =
      s"(Either ${conditionMap.values.map(_.name).mkString(", ")} or ${defaultCondition.name})"
    override val features: Set[Feature[_]] =
      conditionMap.values.flatMap(_.features).toSet ++ defaultCondition.features
    override val optionalFeatures: Set[Feature[_]] =
      conditionMap.values.flatMap(_.optionalFeatures).toSet ++ defaultCondition.optionalFeatures
    override val conditions: Seq[Condition] = conditionMap.values.toSeq :+ defaultCondition
    override val params: Set[Param[_]] =
      conditions.collect { case p: HasParams => p.params }.flatten.toSet

    private[this] def getCondition(evaluationContetwittert: EvaluationContetwittert): Condition =
      conditionMap.getOrElse(evaluationContetwittert.params(choiceParam), defaultCondition)

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult =
      getCondition(evaluationContetwittert).preFilter(evaluationContetwittert, featureMap)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      getCondition(evaluationContetwittert)(evaluationContetwittert, featureMap)
  }

  case class IfElse(
    branchingCondition: Condition,
    ifTrueCondition: Condition,
    ifFalseCondition: Condition)
      etwittertends Condition
      with HasNestedConditions
      with HasParams {
    override lazy val name: String =
      s"(If ${branchingCondition.name} Then ${ifTrueCondition.name} Else ${ifFalseCondition.name})"
    override val features: Set[Feature[_]] =
      branchingCondition.features ++ ifTrueCondition.features ++ ifFalseCondition.features
    override val optionalFeatures: Set[Feature[_]] =
      branchingCondition.optionalFeatures ++ ifTrueCondition.optionalFeatures ++ ifFalseCondition.optionalFeatures
    override val conditions: Seq[Condition] =
      Seq(branchingCondition, ifTrueCondition, ifFalseCondition)
    override val params: Set[Param[_]] =
      conditions.collect { case p: HasParams => p.params }.flatten.toSet

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult =
      branchingCondition.preFilter(evaluationContetwittert, featureMap) match {
        case Filtered =>
          ifFalseCondition.preFilter(evaluationContetwittert, featureMap)
        case NotFiltered =>
          ifTrueCondition.preFilter(evaluationContetwittert, featureMap)
        case _ =>
          NeedsFullEvaluation
      }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result =
      if (branchingCondition(evaluationContetwittert, featureMap).asBoolean) {
        ifTrueCondition(evaluationContetwittert, featureMap)
      } else {
        ifFalseCondition(evaluationContetwittert, featureMap)
      }
  }

  case class GatedAlternate[T](
    defaultCondition: Condition,
    alternateConditions: Map[T, Condition],
    bucketIdentifierToUseOnDisagreementParam: Param[Option[T]])
      etwittertends Condition
      with HasNestedConditions
      with HasParams {

    override lazy val name: String =
      s"(${defaultCondition.name} or sometimes ${alternateConditions.values.map(_.name).mkString(" or ")})"

    override val features: Set[Feature[_]] =
      defaultCondition.features ++ alternateConditions.values.flatMap(_.features)

    override val optionalFeatures: Set[Feature[_]] =
      defaultCondition.optionalFeatures ++ alternateConditions.values.flatMap(_.optionalFeatures)

    override val conditions: Seq[Condition] = Seq(defaultCondition) ++ alternateConditions.values

    override val params: Set[Param[_]] =
      conditions.collect { case p: HasParams => p.params }.flatten.toSet

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult =
      if (defaultCondition.preFilter(evaluationContetwittert, featureMap) == Filtered &&
        alternateConditions.values.forall(_.preFilter(evaluationContetwittert, featureMap) == Filtered)) {
        Filtered
      } else if (defaultCondition.preFilter(evaluationContetwittert, featureMap) == NotFiltered &&
        alternateConditions.values.forall(
          _.preFilter(evaluationContetwittert, featureMap) == NotFiltered)) {
        NotFiltered
      } else {
        NeedsFullEvaluation
      }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val defaultConditionResult: Result = defaultCondition(evaluationContetwittert, featureMap)
      val alternateConditionResult: Map[T, Result] =
        alternateConditions.mapValues(_(evaluationContetwittert, featureMap))

      if (alternateConditionResult.values.etwitterists(_.asBoolean != defaultConditionResult.asBoolean)) {
        evaluationContetwittert.params(bucketIdentifierToUseOnDisagreementParam) match {
          case Some(bucket) if alternateConditionResult.contains(bucket) =>
            alternateConditionResult(bucket)
          case _ =>
            defaultConditionResult
        }
      } else {
        defaultConditionResult
      }
    }
  }

  case class EnumGatedAlternate[E <: Enumeration](
    defaultCondition: Condition,
    alternateConditions: Map[E#Value, Condition],
    bucketIdentifierToUseOnDisagreementParam: EnumParam[E])
      etwittertends Condition
      with HasNestedConditions
      with HasParams {

    override lazy val name: String =
      s"(${defaultCondition.name} or sometimes ${alternateConditions.values.map(_.name).mkString(" or ")})"

    override val features: Set[Feature[_]] =
      defaultCondition.features ++ alternateConditions.values.flatMap(_.features)

    override val optionalFeatures: Set[Feature[_]] =
      defaultCondition.optionalFeatures ++ alternateConditions.values.flatMap(_.optionalFeatures)

    override val conditions: Seq[Condition] = Seq(defaultCondition) ++ alternateConditions.values

    override val params: Set[Param[_]] =
      conditions
        .collect {
          case p: HasParams => p.params
        }.flatten.toSet + bucketIdentifierToUseOnDisagreementParam

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult =
      if (defaultCondition.preFilter(evaluationContetwittert, featureMap) == Filtered &&
        alternateConditions.values.forall(_.preFilter(evaluationContetwittert, featureMap) == Filtered)) {
        Filtered
      } else if (defaultCondition.preFilter(evaluationContetwittert, featureMap) == NotFiltered &&
        alternateConditions.values.forall(
          _.preFilter(evaluationContetwittert, featureMap) == NotFiltered)) {
        NotFiltered
      } else {
        NeedsFullEvaluation
      }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val defaultConditionResult: Result = defaultCondition(evaluationContetwittert, featureMap)
      val alternateConditionResult: Map[E#Value, Result] =
        alternateConditions.mapValues(_(evaluationContetwittert, featureMap))

      if (alternateConditionResult.values.etwitterists(_.asBoolean != defaultConditionResult.asBoolean)) {
        evaluationContetwittert.params(bucketIdentifierToUseOnDisagreementParam) match {
          case bucket if alternateConditionResult.contains(bucket) =>
            alternateConditionResult(bucket)
          case _ =>
            defaultConditionResult
        }
      } else {
        defaultConditionResult
      }
    }
  }

  case object IsTestTweet etwittertends Condition {
    override val features: Set[Feature[_]] = Set(TweetId)
    override val optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      if (!featureMap.contains(TweetId)) {
        UnsatisfiedResult
      } else {
        Result.SatisfiedResult
      }
    }
  }

  case object IsTweetInTweetLevelStcmHoldback etwittertends Condition {
    override val features: Set[Feature[_]] = Set(TweetId)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val tweetId: Long = featureMap(TweetId).asInstanceOf[Long]
      if (StcmTweetHoldback.isTweetInHoldback(tweetId)) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object MediaRestrictedInViewerCountry etwittertends Condition {
    override val features: Set[Feature[_]] =
      Set(MediaGeoRestrictionsAllowList, MediaGeoRestrictionsDenyList)
    override val optionalFeatures: Set[Feature[_]] = Set(RequestCountryCode)

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val requestCountryCode = TakedownReasons.normalizeCountryCodeOption(
        featureMap.get(RequestCountryCode).asInstanceOf[Option[String]])
      val allowlistCountryCodes =
        featureMap(MediaGeoRestrictionsAllowList).asInstanceOf[Seq[String]]
      val denylistCountryCodes =
        featureMap(MediaGeoRestrictionsDenyList).asInstanceOf[Seq[String]]
      if ((allowlistCountryCodes.nonEmpty && !allowlistCountryCodes.contains(requestCountryCode))
        || denylistCountryCodes.contains(requestCountryCode)) {
        Result.SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object OneToOneDmConversation
      etwittertends BooleanFeatureCondition(DmConversationIsOneToOneConversation)

  case object DmConversationTimelineIsEmpty
      etwittertends BooleanFeatureCondition(DmConversationHasEmptyTimeline)

  case object DmConversationLastReadableEventIdIsValid
      etwittertends BooleanFeatureCondition(DmConversationHasValidLastReadableEventId)

  case object ViewerIsDmConversationParticipant
      etwittertends BooleanFeatureCondition(feats.ViewerIsDmConversationParticipant)

  case object DmConversationInfoEtwitterists
      etwittertends BooleanFeatureCondition(feats.DmConversationInfoEtwitterists)

  case object DmConversationTimelineEtwitterists
      etwittertends BooleanFeatureCondition(feats.DmConversationTimelineEtwitterists)

  case object DmEventIsBeforeLastClearedEvent
      etwittertends BooleanFeatureCondition(DmEventOccurredBeforeLastClearedEvent)

  case object DmEventIsBeforeJoinConversationEvent
      etwittertends BooleanFeatureCondition(DmEventOccurredBeforeJoinConversationEvent)

  case object DmEventIsDeleted etwittertends BooleanFeatureCondition(feats.DmEventIsDeleted)

  case object DmEventIsHidden etwittertends BooleanFeatureCondition(feats.DmEventIsHidden)

  case object ViewerIsDmEventInitiatingUser
      etwittertends BooleanFeatureCondition(feats.ViewerIsDmEventInitiatingUser)

  case object DmEventInOneToOneConversationWithUnavailableUser
      etwittertends BooleanFeatureCondition(feats.DmEventInOneToOneConversationWithUnavailableUser)

  case object DmEventInOneToOneConversation
      etwittertends BooleanFeatureCondition(feats.DmEventInOneToOneConversation)

  case object MessageCreateDmEvent etwittertends BooleanFeatureCondition(DmEventIsMessageCreateEvent)

  case object WelcomeMessageCreateDmEvent
      etwittertends BooleanFeatureCondition(DmEventIsWelcomeMessageCreateEvent)

  case object LastMessageReadUpdateDmEvent
      etwittertends BooleanFeatureCondition(DmEventIsLastMessageReadUpdateEvent)

  case object JoinConversationDmEvent
      etwittertends BooleanFeatureCondition(DmEventIsJoinConversationEvent)

  case object ConversationCreateDmEvent
      etwittertends BooleanFeatureCondition(DmEventIsConversationCreateEvent)

  case object TrustConversationDmEvent
      etwittertends BooleanFeatureCondition(DmEventIsTrustConversationEvent)

  case object CsFeedbackSubmittedDmEvent
      etwittertends BooleanFeatureCondition(DmEventIsCsFeedbackSubmitted)

  case object CsFeedbackDismissedDmEvent
      etwittertends BooleanFeatureCondition(DmEventIsCsFeedbackDismissed)

  case object PerspectivalJoinConversationDmEvent
      etwittertends BooleanFeatureCondition(feats.DmEventIsPerspectivalJoinConversationEvent)


  case class SpaceHasLabelWithScoreAboveThresholdWithParam(
    spaceSafetyLabelType: SpaceSafetyLabelType,
    thresholdParam: Param[Double])
      etwittertends Condition
      with HasParams {
    override lazy val name: String =
      s"SpaceHasLabelWithScoreAboveThreshold(${spaceSafetyLabelType.name}, ${NamingUtils.getFriendlyName(thresholdParam)})"
    override val features: Set[Feature[_]] = Set(SpaceSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)
    override val params: Set[Param[_]] = Set(thresholdParam)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(SpaceSafetyLabels).asInstanceOf[Seq[SpaceSafetyLabel]]
      val threshold = evaluationContetwittert.params(thresholdParam)
      val SatisfiedResult =
        Satisfied(FoundSpaceLabelWithScoreAboveThreshold(spaceSafetyLabelType, threshold))
      labels
        .collectFirst {
          case label
              if label.safetyLabelType == spaceSafetyLabelType
                && label.safetyLabel.score.etwitterists(_ >= threshold) =>
            SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class CardUriHasRootDomain(rootDomainParam: Param[Seq[String]])
      etwittertends Condition
      with HasParams {
    override lazy val name: String =
      s"CardUriHasRootDomain(${NamingUtils.getFriendlyName(rootDomainParam)})"
    override val features: Set[Feature[_]] = Set(CardUriHost)
    override val optionalFeatures: Set[Feature[_]] = Set.empty
    private val UnsatisfiedResult = Unsatisfied(this)
    override val params: Set[Param[_]] = Set(rootDomainParam)

    private[this] def isHostDomainOrSubdomain(domain: String, host: String): Boolean =
      host == domain || host.endsWith("." + domain)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val cardUriHost = featureMap(CardUriHost).asInstanceOf[String]
      val rootDomains = evaluationContetwittert.params(rootDomainParam)

      if (rootDomains.etwitterists(isHostDomainOrSubdomain(_, cardUriHost))) {
        Satisfied(FoundCardUriRootDomain(cardUriHost))
      } else {
        UnsatisfiedResult
      }
    }
  }

  case class TweetHasViolationOfLevel(level: ViolationLevel)
      etwittertends Condition
      with HasSafetyLabelType {

    override lazy val name: String = s"tweetHasViolationOfLevel(${level})"

    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)
    override def optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)

    private val SatisfiedResult: Satisfied = Satisfied(FoundTweetViolationOfLevel(level))

    override val labelTypes: Set[SafetyLabelType] =
      ViolationLevel.violationLevelToSafetyLabels
        .getOrElse(level, Set.empty)
        .map(_.asInstanceOf[SafetyLabelType])

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(TweetSafetyLabels).asInstanceOf[Seq[TweetSafetyLabel]]
      if (labels.map(ViolationLevel.fromTweetSafetyLabel).contains(level)) {
        SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object TweetHasViolationOfAnyLevel etwittertends Condition with HasSafetyLabelType {

    override lazy val name: String = s"tweetHasViolationOfAnyLevel"

    override val features: Set[Feature[_]] = Set(TweetSafetyLabels)

    override def optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)

    private val SatisfiedResult: Satisfied = Satisfied(FoundTweetViolationOfSomeLevel)

    override val labelTypes: Set[SafetyLabelType] =
      ViolationLevel.violationLevelToSafetyLabels.values
        .reduceLeft(_ ++ _)
        .map(_.asInstanceOf[SafetyLabelType])

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(TweetSafetyLabels).asInstanceOf[Seq[TweetSafetyLabel]]
      if (labels
          .map(ViolationLevel.fromTweetSafetyLabelOpt).collect {
            case Some(level) => level
          }.nonEmpty) {
        SatisfiedResult
      } else {
        UnsatisfiedResult
      }
    }
  }

  case object TweetIsEditTweet etwittertends BooleanFeatureCondition(TweetIsEditTweetFeature)
  case object TweetIsStaleTweet etwittertends BooleanFeatureCondition(TweetIsStaleTweetFeature)


  case class ViewerHasAdultMediaSettingLevel(settingLevelToCompare: SensitiveMediaSettingsLevel)
      etwittertends Condition {
    override def features: Set[Feature[_]] = Set(ViewerSensitiveMediaSettings)

    override def optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap
        .get(ViewerSensitiveMediaSettings)
        .map(_.asInstanceOf[UserSensitiveMediaSettings])
        .collectFirst {
          case UserSensitiveMediaSettings(Some(setting))
              if (setting.viewAdultContent == settingLevelToCompare) =>
            Result.SatisfiedResult
          case UserSensitiveMediaSettings(None) => UnsatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class ViewerHasViolentMediaSettingLevel(settingLevelToCompare: SensitiveMediaSettingsLevel)
      etwittertends Condition {
    override def features: Set[Feature[_]] = Set(ViewerSensitiveMediaSettings)

    override def optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {

      featureMap
        .get(ViewerSensitiveMediaSettings)
        .map(_.asInstanceOf[UserSensitiveMediaSettings])
        .collectFirst {
          case UserSensitiveMediaSettings(Some(setting))
              if (setting.viewViolentContent == settingLevelToCompare) =>
            Result.SatisfiedResult
          case UserSensitiveMediaSettings(None) => UnsatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class ViewerHasOtherSensitiveMediaSettingLevel(
    settingLevelToCompare: SensitiveMediaSettingsLevel)
      etwittertends Condition {
    override def features: Set[Feature[_]] = Set(ViewerSensitiveMediaSettings)

    override def optionalFeatures: Set[Feature[_]] = Set.empty

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {

      featureMap
        .get(ViewerSensitiveMediaSettings)
        .map(_.asInstanceOf[UserSensitiveMediaSettings])
        .collectFirst {
          case UserSensitiveMediaSettings(Some(setting))
              if (setting.viewOtherContent == settingLevelToCompare) =>
            Result.SatisfiedResult
          case UserSensitiveMediaSettings(None) => UnsatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  private[rules] val TotwitterrfTweetFilteredForAuthor =
    Equals(TotwittericReplyFilterState, FilterState.FilteredFromAuthor)

  private[rules] case object TotwitterrfViewerIsConversationAuthor
      etwittertends BooleanFeatureCondition(TotwittericReplyFilterConversationAuthorIsViewer)

  val TotwitterrfFilteredFromAuthorViewer =
    And(LoggedInViewer, TotwitterrfTweetFilteredForAuthor, TotwitterrfViewerIsConversationAuthor)

  case object SearchQueryMatchesScreenName etwittertends Condition {
    override def features: Set[Feature[_]] = Set.empty

    override def optionalFeatures: Set[Feature[_]] = Set(RawQuery, AuthorScreenName)

    private val UnsatisfiedResult = Unsatisfied(this)

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      if (featureMap.contains(RawQuery) && featureMap.contains(AuthorScreenName)) {
        val rawQuery = featureMap(RawQuery).asInstanceOf[String]
        val authorScreenName = featureMap(AuthorScreenName).asInstanceOf[String]
        if (rawQuery.equalsIgnoreCase(authorScreenName)) {
          Result.SatisfiedResult
        } else {
          UnsatisfiedResult
        }
      } else {
        UnsatisfiedResult
      }
    }
  }

  object SearchQueryDoesNotMatchScreenNameConditionBuilder {
    def apply(condition: Condition, ruleParam: RuleParam[Boolean]): Choose[Boolean] = {
      Choose(
        conditionMap =
          Map(true -> And(condition, Not(SearchQueryMatchesScreenName)), false -> condition),
        defaultCondition = condition,
        choiceParam = ruleParam
      )
    }
  }

  val SearchQueryDoesNotMatchScreenNameDefaultTrueCondition: Choose[Boolean] =
    SearchQueryDoesNotMatchScreenNameConditionBuilder(Condition.True, RuleParams.False)

  case object OptionalNonAuthorViewer etwittertends Condition {
    override val features: Set[Feature[_]] = Set()
    override val optionalFeatures: Set[Feature[_]] = Set(AuthorId, ViewerId)

    private val UnsatisfiedResult = Unsatisfied(this)

    override def preFilter(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): PreFilterResult = {
      NeedsFullEvaluation
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val authorIdsOpt = featureMap.get(AuthorId).asInstanceOf[Option[Set[Long]]]
      val viewerIdOpt = featureMap.get(ViewerId).asInstanceOf[Option[Long]]

      (for {
        authorIds <- authorIdsOpt
        viewerId <- viewerIdOpt
      } yield {
        if (authorIds.contains(viewerId)) UnsatisfiedResult
        else Result.SatisfiedResult
      }) getOrElse {
        Result.SatisfiedResult
      }
    }
  }

  case class ViewerLocatedInApplicableCountriesOfMediaWithholdingLabel(
    safetyLabelType: MediaSafetyLabelType)
      etwittertends ViewerInJurisdiction
      with HasSafetyLabelType {

    override lazy val name: String =
      s"ViewerLocatedInApplicableCountriesOfMediaLabel(${safetyLabelType.name})"
    override val features: Set[Feature[_]] = Set(MediaSafetyLabels)
    override val optionalFeatures: Set[Feature[_]] = Set(ViewerCountryCode, RequestCountryCode)
    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)

    private[this] def isInApplicableCountries(
      countryCodeOpt: Option[String],
      label: SafetyLabel
    ): Boolean = {
      val inApplicableCountry = (for {
        applicableCountries <- label.applicableCountries
        countryCode <- countryCodeOpt
      } yield {
        applicableCountries.contains(countryCode)
      }) getOrElse (false)
      inApplicableCountry
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(MediaSafetyLabels).asInstanceOf[Seq[MediaSafetyLabel]]

      val countryFeatures = getCountryFeatures(featureMap)
      val countryCodeOpt = countryFeatures.requestCountryCode
        .orElse(countryFeatures.viewerCountryCode)

      labels
        .collectFirst {
          case label
              if label.safetyLabelType == safetyLabelType
                &&
                  isInApplicableCountries(countryCodeOpt, label.safetyLabel) =>
            Result.SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }
  }

  case class MediaHasLabelWithWorldwideWithholding(safetyLabelType: MediaSafetyLabelType)
      etwittertends Condition
      with HasSafetyLabelType {

    override lazy val name: String =
      s"MediaHasLabelWithWorldwideWithholding(${safetyLabelType.name})"

    override val features: Set[Feature[_]] = Set(MediaSafetyLabels)

    override val optionalFeatures: Set[Feature[_]] = Set.empty

    override val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    private val UnsatisfiedResult: Unsatisfied = Unsatisfied(this)

    private[this] def isWithheldWorldwide(label: SafetyLabel): Boolean = {
      label.applicableCountries.map(_.contains("twittertwitter")).getOrElse(false)
    }

    override def apply(
      evaluationContetwittert: EvaluationContetwittert,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(MediaSafetyLabels).asInstanceOf[Seq[MediaSafetyLabel]]

      labels
        .collectFirst {
          case label
              if label.safetyLabelType == safetyLabelType
                && isWithheldWorldwide(label.safetyLabel) =>
            Result.SatisfiedResult
        }.getOrElse(UnsatisfiedResult)
    }

  }
}
