package com.ExTwitter.home_mixer.functional_component.decorator.urt.builder

import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.home_mixer.model.request.FollowingProduct
import com.ExTwitter.home_mixer.model.request.ForYouProduct
import com.ExTwitter.home_mixer.param.HomeGlobalParams.EnableNahFeedbackInfoParam
import com.ExTwitter.home_mixer.util.CandidatesUtil
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.timelines.service.{thriftscala => t}
import com.ExTwitter.timelines.util.FeedbackMetadataSerializer
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
