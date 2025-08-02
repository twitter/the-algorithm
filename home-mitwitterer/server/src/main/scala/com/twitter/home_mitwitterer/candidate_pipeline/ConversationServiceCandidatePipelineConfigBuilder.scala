package com.twitter.home_mitwitterer.candidate_pipeline

import com.twitter.home_mitwitterer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.twitter.home_mitwitterer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mitwitterer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.twitter.product_mitwitterer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSource
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.gate.BaseGate
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

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
