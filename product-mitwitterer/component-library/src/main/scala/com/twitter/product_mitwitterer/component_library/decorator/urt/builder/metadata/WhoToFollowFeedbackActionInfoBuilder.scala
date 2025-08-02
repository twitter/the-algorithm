package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon.Frown
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SeeFewer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stringcenter.client.EtwitterternalStringRegistry
import com.twitter.stringcenter.client.StringCenter

case class WhoToFollowFeedbackActionInfoBuilder[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any]
](
  etwitterternalStringRegistry: EtwitterternalStringRegistry,
  stringCenter: StringCenter,
  encodedFeedbackRequest: Option[String])
    etwittertends BaseFeedbackActionInfoBuilder[Query, Candidate] {

  private val seeLessOftenFeedback =
    etwitterternalStringRegistry.createProdString("Feedback.seeLessOften")
  private val seeLessOftenConfirmationFeedback =
    etwitterternalStringRegistry.createProdString("Feedback.seeLessOftenConfirmation")

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
      displayContetwittert = None,
      clientEventInfo = None
    )
  )
}
