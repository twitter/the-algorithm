package com.twitter.product_mixer.component_library.decorator.urt.builder.metadata

import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.Frown
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.SeeFewer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stringcenter.client.ExternalStringRegistry
import com.twitter.stringcenter.client.StringCenter

case class WhoToFollowFeedbackActionInfoBuilder[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any]
](
  externalStringRegistry: ExternalStringRegistry,
  stringCenter: StringCenter,
  encodedFeedbackRequest: Option[String])
    extends BaseFeedbackActionInfoBuilder[Query, Candidate] {

  private val seeLessOftenFeedback =
    externalStringRegistry.createProdString("Feedback.seeLessOften")
  private val seeLessOftenConfirmationFeedback =
    externalStringRegistry.createProdString("Feedback.seeLessOftenConfirmation")

  override def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo] = Some(
    FeedbackActionInfo(
      feedbackActions = Seq(
        FeedbackAction(
          feedbackType = SeeFewer,
          prompt = Some(
            Str(seeLessOftenFeedback, stringCenter, None)
              .apply(query, candidate, candidateFeatures)),
          confirmation = Some(
            Str(seeLessOftenConfirmationFeedback, stringCenter, None)
              .apply(query, candidate, candidateFeatures)),
          childFeedbackActions = None,
          feedbackUrl = None,
          confirmationDisplayType = None,
          clientEventInfo = None,
          richBehavior = None,
          subprompt = None,
          icon = Some(Frown), // ignored by unsupported clients
          hasUndoAction = Some(true),
          encodedFeedbackRequest = encodedFeedbackRequest
        )
      ),
      feedbackMetadata = None,
      displayContext = None,
      clientEventInfo = None
    )
  )
}
