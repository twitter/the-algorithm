package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.home_mixer.model.HomeFeatures.InNetworkFeature
import com.X.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.X.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.TopicContext
import com.X.product_mixer.core.pipeline.PipelineQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class TopicSocialContextBuilder @Inject() ()
    extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext] = {
    val inNetwork = candidateFeatures.getOrElse(InNetworkFeature, true)
    if (!inNetwork) {
      val topicIdSocialContextOpt = candidateFeatures.getOrElse(TopicIdSocialContextFeature, None)
      val topicContextFunctionalityTypeOpt =
        candidateFeatures.getOrElse(TopicContextFunctionalityTypeFeature, None)
      (topicIdSocialContextOpt, topicContextFunctionalityTypeOpt) match {
        case (Some(topicId), Some(topicContextFunctionalityType)) =>
          Some(
            TopicContext(
              topicId = topicId.toString,
              functionalityType = Some(topicContextFunctionalityType)
            ))
        case _ => None
      }
    } else {
      None
    }
  }
}
