package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicContetwittertFunctionalityTypeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicIdSocialContetwittertFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TopicContetwittert
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class TopicSocialContetwittertBuilder @Inject() ()
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, TweetCandidate] {

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {
    val inNetwork = candidateFeatures.getOrElse(InNetworkFeature, true)
    if (!inNetwork) {
      val topicIdSocialContetwittertOpt = candidateFeatures.getOrElse(TopicIdSocialContetwittertFeature, None)
      val topicContetwittertFunctionalityTypeOpt =
        candidateFeatures.getOrElse(TopicContetwittertFunctionalityTypeFeature, None)
      (topicIdSocialContetwittertOpt, topicContetwittertFunctionalityTypeOpt) match {
        case (Some(topicId), Some(topicContetwittertFunctionalityType)) =>
          Some(
            TopicContetwittert(
              topicId = topicId.toString,
              functionalityType = Some(topicContetwittertFunctionalityType)
            ))
        case _ => None
      }
    } else {
      None
    }
  }
}
