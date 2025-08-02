package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.ConversationModuleFocalTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.ConversationModuleIdFeature
import com.twitter.home_mitwitterer.param.HomeGlobalParams.EnableSocialContetwittertParam
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class HomeTweetSocialContetwittertBuilder @Inject() (
  likedBySocialContetwittertBuilder: LikedBySocialContetwittertBuilder,
  listsSocialContetwittertBuilder: ListsSocialContetwittertBuilder,
  followedBySocialContetwittertBuilder: FollowedBySocialContetwittertBuilder,
  topicSocialContetwittertBuilder: TopicSocialContetwittertBuilder,
  etwittertendedReplySocialContetwittertBuilder: EtwittertendedReplySocialContetwittertBuilder,
  receivedReplySocialContetwittertBuilder: ReceivedReplySocialContetwittertBuilder,
  popularVideoSocialContetwittertBuilder: PopularVideoSocialContetwittertBuilder,
  popularInYourAreaSocialContetwittertBuilder: PopularInYourAreaSocialContetwittertBuilder)
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, TweetCandidate] {

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    features: FeatureMap
  ): Option[SocialContetwittert] = {
    if (query.params(EnableSocialContetwittertParam)) {
      features.getOrElse(ConversationModuleFocalTweetIdFeature, None) match {
        case None =>
          likedBySocialContetwittertBuilder(query, candidate, features)
            .orElse(followedBySocialContetwittertBuilder(query, candidate, features))
            .orElse(topicSocialContetwittertBuilder(query, candidate, features))
            .orElse(popularVideoSocialContetwittertBuilder(query, candidate, features))
            .orElse(listsSocialContetwittertBuilder(query, candidate, features))
            .orElse(popularInYourAreaSocialContetwittertBuilder(query, candidate, features))
        case Some(_) =>
          val conversationId = features.getOrElse(ConversationModuleIdFeature, None)
          // Only hydrate the social contetwittert into the root tweet in a conversation module
          if (conversationId.contains(candidate.id)) {
            etwittertendedReplySocialContetwittertBuilder(query, candidate, features)
              .orElse(receivedReplySocialContetwittertBuilder(query, candidate, features))
          } else None
      }
    } else None
  }
}
