package com.X.visibility.rules

import com.X.escherbird.thriftscala.TweetEntityAnnotation
import com.X.gizmoduck.thriftscala.Label
import com.X.spam.rtf.thriftscala.BotMakerAction
import com.X.spam.rtf.thriftscala.SafetyLabelSource
import com.X.spam.rtf.thriftscala.SemanticCoreAction
import com.X.visibility.common.actions.EscherbirdAnnotation
import com.X.visibility.common.actions.SoftInterventionReason
import com.X.visibility.configapi.configs.DeciderKey
import com.X.visibility.features.AuthorUserLabels
import com.X.visibility.features.Feature
import com.X.visibility.features.TweetSafetyLabels
import com.X.visibility.logging.thriftscala.ActionSource
import com.X.visibility.models.LabelSource._
import com.X.visibility.models.TweetSafetyLabel
import com.X.visibility.models.TweetSafetyLabelType
import com.X.visibility.models.UserLabel
import com.X.visibility.models.UserLabelValue

sealed trait RuleActionSourceBuilder {
  def build(resolvedFeatureMap: Map[Feature[_], Any], verdict: Action): Option[ActionSource]

}

object RuleActionSourceBuilder {

  case class TweetSafetyLabelSourceBuilder(tweetSafetyLabelType: TweetSafetyLabelType)
      extends RuleActionSourceBuilder {
    override def build(
      resolvedFeatureMap: Map[Feature[_], Any],
      verdict: Action
    ): Option[ActionSource] = {
      resolvedFeatureMap
        .getOrElse(TweetSafetyLabels, Seq.empty[TweetSafetyLabel])
        .asInstanceOf[Seq[TweetSafetyLabel]]
        .find(_.labelType == tweetSafetyLabelType)
        .flatMap(_.safetyLabelSource)
        .map(ActionSource.SafetyLabelSource(_))
    }
  }

  case class UserSafetyLabelSourceBuilder(userLabel: UserLabelValue)
      extends RuleActionSourceBuilder {
    override def build(
      resolvedFeatureMap: Map[Feature[_], Any],
      verdict: Action
    ): Option[ActionSource] = {
      resolvedFeatureMap
        .getOrElse(AuthorUserLabels, Seq.empty[Label])
        .asInstanceOf[Seq[Label]]
        .map(UserLabel.fromThrift)
        .find(_.labelValue == userLabel)
        .flatMap(_.source)
        .collect {
          case BotMakerRule(ruleId) =>
            ActionSource.SafetyLabelSource(SafetyLabelSource.BotMakerAction(BotMakerAction(ruleId)))
        }
    }
  }

  case class SemanticCoreActionSourceBuilder() extends RuleActionSourceBuilder {
    override def build(
      resolvedFeatureMap: Map[Feature[_], Any],
      verdict: Action
    ): Option[ActionSource] = {
      verdict match {
        case softIntervention: SoftIntervention =>
          getSemanticCoreActionSourceOption(softIntervention)
        case tweetInterstitial: TweetInterstitial =>
          tweetInterstitial.softIntervention.flatMap(getSemanticCoreActionSourceOption)
        case _ => None
      }
    }

    def getSemanticCoreActionSourceOption(
      softIntervention: SoftIntervention
    ): Option[ActionSource] = {
      val siReason = softIntervention.reason
        .asInstanceOf[SoftInterventionReason.EscherbirdAnnotations]
      val firstAnnotation: Option[EscherbirdAnnotation] =
        siReason.escherbirdAnnotations.headOption

      firstAnnotation.map { annotation =>
        ActionSource.SafetyLabelSource(
          SafetyLabelSource.SemanticCoreAction(SemanticCoreAction(
            TweetEntityAnnotation(annotation.groupId, annotation.domainId, annotation.entityId))))
      }
    }
  }
}

trait DoesLogVerdict {}

trait DoesLogVerdictDecidered extends DoesLogVerdict {
  def verdictLogDeciderKey: DeciderKey.Value
}
