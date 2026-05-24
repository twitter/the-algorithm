package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TuneFeedFeedbackActionInfoBuilder @Inject() (
  postFollowupFeedbackActionBuilder: PostFollowupFeedbackActionBuilder,
  dontLikeFeedbackActionBuilder: DontLikeFeedbackActionBuilder)
    extends BaseFeedbackActionInfoBuilder[PipelineQuery, TweetCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo] = {
    Some(
      FeedbackActionInfo(
        feedbackActions = Seq(
          postFollowupFeedbackActionBuilder(query, candidate, candidateFeatures),
          dontLikeFeedbackActionBuilder(query, candidate, candidateFeatures)).flatten,
        feedbackMetadata = None,
        displayContext = None,
        clientEventInfo = None
      ))
  }
}
