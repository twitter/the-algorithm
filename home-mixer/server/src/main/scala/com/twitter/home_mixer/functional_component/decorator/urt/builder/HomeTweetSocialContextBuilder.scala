package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.model.HomeFeatures.ConversationModuleFocalTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ConversationModuleIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableCommunitiesContextParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnableSocialContextParam
import com.twitter.product_mixer.component_library.decorator.urt.builder.social_context.CommunitiesSocialContextBuilder
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class HomeTweetSocialContextBuilder @Inject() (
  likedBySocialContextBuilder: LikedBySocialContextBuilder,
  servedTypeSocialContextBuilder: ServedTypeSocialContextBuilder,
  followedBySocialContextBuilder: FollowedBySocialContextBuilder,
  extendedReplySocialContextBuilder: ExtendedReplySocialContextBuilder,
  receivedReplySocialContextBuilder: ReceivedReplySocialContextBuilder)
    extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    features: FeatureMap
  ): Option[SocialContext] = {
    val communitiesSocialContextBuilder =
      if (query.params(EnableCommunitiesContextParam))
        CommunitiesSocialContextBuilder(query, candidate, features)
      else None

    if (query.params(EnableSocialContextParam)) {
      features.getOrElse(ConversationModuleFocalTweetIdFeature, None) match {
        case None =>
          DebugSocialContextBuilder(query, candidate, features)
            .orElse(communitiesSocialContextBuilder)
            .orElse(servedTypeSocialContextBuilder(query, candidate, features))
            .orElse(followedBySocialContextBuilder(query, candidate, features))
        case Some(_) =>
          val conversationId = features.getOrElse(ConversationModuleIdFeature, None)
          // Only hydrate the social context into the root tweet in a conversation module
          if (conversationId.contains(candidate.id)) {
            communitiesSocialContextBuilder
              .orElse(extendedReplySocialContextBuilder(query, candidate, features))
              .orElse(receivedReplySocialContextBuilder(query, candidate, features))
          } else None
      }
    } else None
  }
}
