package com.X.home_mixer.candidate_pipeline

import com.X.home_mixer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.X.home_mixer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.X.product_mixer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSource
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.gate.BaseGate
import com.X.product_mixer.core.pipeline.PipelineQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationServiceCandidatePipelineConfigBuilder[Query <: PipelineQuery] @Inject() (
  conversationServiceCandidateSource: ConversationServiceCandidateSource,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  invalidSubscriptionTweetFilter: InvalidSubscriptionTweetFilter,
  namesFeatureHydrator: NamesFeatureHydrator) {

  def build(
    gates: Seq[BaseGate[Query]] = Seq.empty,
    decorator: Option[CandidateDecorator[Query, TweetCandidate]] = None
  ): ConversationServiceCandidatePipelineConfig[Query] = {
    new ConversationServiceCandidatePipelineConfig(
      conversationServiceCandidateSource,
      tweetypieFeatureHydrator,
      namesFeatureHydrator,
      invalidSubscriptionTweetFilter,
      gates,
      decorator
    )
  }
}
