package com.twitter.tweet_mixer.functional_component.selector

import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.tweet_mixer.functional_component.hydrator.SignalInfoFeature
import com.twitter.usersignalservice.thriftscala.SignalType

case class FeedbackRelevantSelector(
  override val pipelineScope: CandidateScope)
    extends Selector[PipelineQuery] {

  private def hasFeedbackRelevantSignal(candidateWithDetails: CandidateWithDetails): Boolean = {
    candidateWithDetails.features
      .getOrElse(SignalInfoFeature, Seq.empty)
      .exists(_.signalType == SignalType.FeedbackRelevant)
  }

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val (feedbackRelevantCandidates, otherCandidates) = result.partition(hasFeedbackRelevantSignal)

    SelectorResult(
      remainingCandidates = remainingCandidates ++ otherCandidates,
      result = feedbackRelevantCandidates
    )
  }
}
