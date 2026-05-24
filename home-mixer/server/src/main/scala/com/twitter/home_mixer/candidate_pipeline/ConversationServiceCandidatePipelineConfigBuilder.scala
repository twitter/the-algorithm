package com.twitter.home_mixer.candidate_pipeline

import com.twitter.home_mixer.functional_component.decorator.HomeConversationServiceCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeTweetContextBuilder
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.PostContextFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.twitter.product_mixer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSource
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.communities.CommunityNamesFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedBulkCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.timelines.configapi.Param
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationServiceCandidatePipelineConfigBuilder[Query <: PipelineQuery] @Inject() (
  conversationServiceCandidateSource: ConversationServiceCandidateSource,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  communityNamesFeatureHydrator: CommunityNamesFeatureHydrator,
  postContextFeatureHydrator: PostContextFeatureHydrator,
  invalidSubscriptionTweetFilter: InvalidSubscriptionTweetFilter,
  namesFeatureHydrator: NamesFeatureHydrator,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
  homeTweetContextBuilder: HomeTweetContextBuilder) {

  def build(
    nonEmptyCandidateScope: CandidateScope,
    servedType: hmt.ServedType,
    enablePostContextFeatureHydratorParam: Param[Boolean]
  ): ConversationServiceCandidatePipelineConfig[Query] = {
    val paramGatedPostContextFeatureHydrator = ParamGatedBulkCandidateFeatureHydrator(
      enablePostContextFeatureHydratorParam,
      postContextFeatureHydrator
    )
    
    new ConversationServiceCandidatePipelineConfig(
      conversationServiceCandidateSource,
      tweetypieFeatureHydrator,
      namesFeatureHydrator,
      communityNamesFeatureHydrator,
      invalidSubscriptionTweetFilter,
      Seq(NonEmptyCandidatesGate(nonEmptyCandidateScope)),
      HomeConversationServiceCandidateDecorator(
        homeFeedbackActionInfoBuilder,
        homeTweetContextBuilder,
        servedType
      ),
      servedType,
      paramGatedPostContextFeatureHydrator
    )
  }
}
