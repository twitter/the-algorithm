package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePostFeedbackParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePostFollowupParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePostDetailsNegativeFeedbackParam
import com.twitter.home_mixer.param.HomeGlobalParams.PostFeedbackThresholdParam
import com.twitter.home_mixer.param.HomeGlobalParams.PostFollowupThresholdParam
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

@Singleton
class HomeFeedbackActionInfoBuilder @Inject() (
  postFeedbackActionBuilder: PostFeedbackActionBuilder,
  postFollowupFeedbackActionBuilder: PostFollowupFeedbackActionBuilder,
  dontLikeFeedbackActionBuilder: DontLikeFeedbackActionBuilder,
  postDetailsNegativeFeedbackActionBuilder: PostDetailsNegativeFeedbackActionBuilder)
    extends BaseFeedbackActionInfoBuilder[PipelineQuery, TweetCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo] = {
    val supportedProduct = query.product match {
      case ForYouProduct => true
      case _ => false
    }
    val isAuthoredByViewer = CandidatesUtil.isAuthoredByViewer(query, candidateFeatures)

    if (supportedProduct && !isAuthoredByViewer) {
      // avoid showing post feedback for every candidate
      val shouldShowPostFeedback =
        query.params(EnablePostFeedbackParam) &&
          Random.nextDouble() < query.params(PostFeedbackThresholdParam)

      val shouldShowPostFollowup =
        query.params(EnablePostFollowupParam) &&
          Random.nextDouble() < query.params(PostFollowupThresholdParam)

      val shouldShowPostDetailsNegative =
        query.params(EnablePostDetailsNegativeFeedbackParam)

      val feedbackActions =
        Seq(
          if (shouldShowPostFeedback)
            postFeedbackActionBuilder(query, candidate, candidateFeatures)
          else None,
          if (shouldShowPostFollowup)
            postFollowupFeedbackActionBuilder(query, candidate, candidateFeatures)
          else None,
          dontLikeFeedbackActionBuilder(
            query,
            candidate,
            candidateFeatures
          ),
          if (shouldShowPostDetailsNegative)
            postDetailsNegativeFeedbackActionBuilder(
              query,
              candidate,
              candidateFeatures
            )
          else None
        ).flatten

      Some(
        FeedbackActionInfo(
          feedbackActions = feedbackActions,
          feedbackMetadata = None,
          displayContext = None,
          clientEventInfo = None
        ))
    } else None
  }
}
