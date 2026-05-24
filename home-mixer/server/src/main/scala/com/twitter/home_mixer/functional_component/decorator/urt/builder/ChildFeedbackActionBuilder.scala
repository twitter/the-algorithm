package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.param.HomeGlobalParams.PostFeedbackPromptNegativeParam
import com.twitter.home_mixer.param.HomeGlobalParams.PostFeedbackPromptNeutralParam
import com.twitter.home_mixer.param.HomeGlobalParams.PostFeedbackPromptPositiveParam
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.stringcenter.client.core.ExternalString
import com.twitter.timelines.common.{thriftscala => tlc}
import com.twitter.timelineservice.model.FeedbackInfo
import com.twitter.timelineservice.model.FeedbackMetadata
import com.twitter.timelineservice.{thriftscala => tlst}
import javax.inject.Inject
import javax.inject.Singleton

trait ChildFeedbackActionBuilder {
  val stringCenter: StringCenter
  def feedbackType: tlst.FeedbackType
  def internalFeedbackType: FeedbackType
  def promptString: ExternalString
  def confirmationString: ExternalString
  def clientEventElement: String
  def clientEventAction: String
  def clientEventComponent: Option[String] = None
  def hasUndoAction: Boolean = true
  def confirmationDisplayType: Option[ConfirmationDisplayType] = None
  def getPrompt(query: PipelineQuery): String = {
    stringCenter.prepare(promptString)
  }

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap,
  ): Option[ChildFeedbackAction] = {
    val prompt = getPrompt(query)
    val confirmation = stringCenter.prepare(confirmationString)
    val feedbackMetadata = FeedbackMetadata(
      engagementType = None,
      entityIds = Seq(tlc.FeedbackEntity.TweetId(candidate.id)),
      ttl = Some(FeedbackUtil.FeedbackTtl)
    )
    val feedbackUrl = FeedbackInfo.feedbackUrl(
      feedbackType = feedbackType,
      feedbackMetadata = feedbackMetadata,
      injectionType = None
    )

    Some(
      ChildFeedbackAction(
        feedbackType = internalFeedbackType,
        prompt = Some(prompt),
        confirmation = Some(confirmation),
        feedbackUrl = Some(feedbackUrl),
        hasUndoAction = Some(hasUndoAction),
        confirmationDisplayType = confirmationDisplayType,
        clientEventInfo = Some(
          ClientEventInfo(
            component = clientEventComponent,
            element = Some(clientEventElement),
            details = None,
            action = Some(clientEventAction),
            entityToken = None
          )
        ),
        icon = None,
        richBehavior = None,
        subprompt = None
      )
    )
  }
}

@Singleton
case class SeeMoreChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Relevant
  override def promptString: ExternalString = externalStrings.seeMoreString
  override def confirmationString: ExternalString =
    externalStrings.genericConfirmationString
  override def clientEventComponent: Option[String] = Some("for_you_post_followup")
  override def clientEventElement: String = "feedback_relevant"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Relevant
  override def hasUndoAction: Boolean = false
  override def confirmationDisplayType: Option[ConfirmationDisplayType] = Some(BottomSheet)
}

@Singleton
case class SeeLessChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.NotRelevant
  override def promptString: ExternalString = externalStrings.seeLessString
  override def confirmationString: ExternalString =
    externalStrings.genericConfirmationString
  override def clientEventComponent: Option[String] = Some("for_you_post_followup")
  override def clientEventElement: String = "feedback_notrelevant"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = NotRelevant
  override def hasUndoAction: Boolean = false
  override def confirmationDisplayType: Option[ConfirmationDisplayType] = Some(BottomSheet)
}

@Singleton
case class RelevantChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Relevant
  override def promptString: ExternalString = externalStrings.relevantString
  override def confirmationString: ExternalString =
    externalStrings.relevantConfirmationString
  override def clientEventComponent: Option[String] = Some("for_you_post_relevance_prompt")
  override def clientEventElement: String = "feedback_relevant"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Relevant
  override def hasUndoAction: Boolean = false
  override def confirmationDisplayType: Option[ConfirmationDisplayType] = Some(BottomSheet)
  override def getPrompt(query: PipelineQuery): String = {
    query.params(PostFeedbackPromptPositiveParam)
  }
}

@Singleton
case class NotRelevantChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.NotRelevant
  override def promptString: ExternalString = externalStrings.notRelevantString
  override def confirmationString: ExternalString =
    externalStrings.notRelevantConfirmationString
  override def clientEventComponent: Option[String] = Some("for_you_post_relevance_prompt")
  override def clientEventElement: String = "feedback_notrelevant"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = NotRelevant
  override def hasUndoAction: Boolean = false
  override def confirmationDisplayType: Option[ConfirmationDisplayType] = Some(BottomSheet)
  override def getPrompt(query: PipelineQuery): String = {
    query.params(PostFeedbackPromptNegativeParam)
  }

}

@Singleton
case class NeutralChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Neutral
  override def promptString: ExternalString = externalStrings.neutralString
  override def confirmationString: ExternalString =
    externalStrings.neutralConfirmationString
  override def clientEventComponent: Option[String] = Some("for_you_post_relevance_prompt")
  override def clientEventElement: String = "feedback_neutral"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Neutral
  override def hasUndoAction: Boolean = false
  override def confirmationDisplayType: Option[ConfirmationDisplayType] = Some(BottomSheet)
  override def getPrompt(query: PipelineQuery): String = {
    query.params(PostFeedbackPromptNeutralParam)
  }
}

@Singleton
case class DontlikeNotRelevantChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.NotRelevant
  override def promptString: ExternalString = externalStrings.notRelevantString
  override def confirmationString: ExternalString =
    externalStrings.notRelevantConfirmationString
  override def clientEventElement: String = "feedback_notrelevant"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = NotRelevant
}

@Singleton
case class HatefulChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Hateful
  override def promptString: ExternalString = externalStrings.hatefulString
  override def confirmationString: ExternalString =
    externalStrings.hatefulConfirmationString
  override def clientEventElement: String = "feedback_hateful"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Hateful
}

@Singleton
case class BoringChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Boring
  override def promptString: ExternalString = externalStrings.boringString
  override def confirmationString: ExternalString =
    externalStrings.boringConfirmationString
  override def clientEventElement: String = "feedback_boring"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Boring
}

@Singleton
case class ConfusingChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Confusing
  override def promptString: ExternalString = externalStrings.confusingString
  override def confirmationString: ExternalString =
    externalStrings.confusingConfirmationString
  override def clientEventElement: String = "feedback_confusing"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Confusing
}

@Singleton
case class ClickbaitChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Clickbait
  override def promptString: ExternalString = externalStrings.clickbaitString
  override def confirmationString: ExternalString =
    externalStrings.clickbaitConfirmationString
  override def clientEventElement: String = "feedback_clickbait"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Clickbait
}

@Singleton
case class RagebaitChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Ragebait
  override def promptString: ExternalString = externalStrings.ragebaitString
  override def confirmationString: ExternalString =
    externalStrings.ragebaitConfirmationString
  override def clientEventElement: String = "feedback_ragebait"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Ragebait
}

@Singleton
case class RegretChildFeedbackActionBuilder @Inject() (
  @ProductScoped override val stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings)
    extends ChildFeedbackActionBuilder {
  override def feedbackType: tlst.FeedbackType = tlst.FeedbackType.Regret
  override def promptString: ExternalString = externalStrings.regretString
  override def confirmationString: ExternalString =
    externalStrings.regretConfirmationString
  override def clientEventElement: String = "feedback_regret"
  override def clientEventAction: String = "click"
  override def internalFeedbackType: FeedbackType = Regret
}
