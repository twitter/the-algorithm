package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.home_mixer.model.request.FollowingProduct
import com.X.home_mixer.model.request.ForYouProduct
import com.X.home_mixer.param.HomeGlobalParams.EnableNahFeedbackInfoParam
import com.X.home_mixer.util.CandidatesUtil
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.service.{thriftscala => t}
import com.X.timelines.util.FeedbackMetadataSerializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeFeedbackActionInfoBuilder @Inject() (
  notInterestedTopicFeedbackActionBuilder: NotInterestedTopicFeedbackActionBuilder,
  dontLikeFeedbackActionBuilder: DontLikeFeedbackActionBuilder)
    extends BaseFeedbackActionInfoBuilder[PipelineQuery, TweetCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo] = {
    val supportedProduct = query.product match {
      case FollowingProduct => query.params(EnableNahFeedbackInfoParam)
      case ForYouProduct => true
      case _ => false
    }
    val isAuthoredByViewer = CandidatesUtil.isAuthoredByViewer(query, candidateFeatures)

    if (supportedProduct && !isAuthoredByViewer) {
      val feedbackActions = Seq(
        notInterestedTopicFeedbackActionBuilder(candidateFeatures),
        dontLikeFeedbackActionBuilder(query, candidate, candidateFeatures)
      ).flatten
      val feedbackMetadata = FeedbackMetadataSerializer.serialize(
        t.FeedbackMetadata(injectionType = candidateFeatures.getOrElse(SuggestTypeFeature, None)))

      Some(
        FeedbackActionInfo(
          feedbackActions = feedbackActions,
          feedbackMetadata = Some(feedbackMetadata),
          displayContext = None,
          clientEventInfo = None
        ))
    } else None
  }
}
