package com.twitter.home_mixer.product.for_you

import com.twitter.timelines.render.{thriftscala => tl}
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.product.for_you.candidate_source.ScoredTweetWithConversationMetadata
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType

object ForYouScoredTweetsResponseFeatureTransformer
    extends CandidateFeatureTransformer[ScoredTweetWithConversationMetadata] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ForYouScoredTweetsResponse")

  override val features: Set[Feature[_, _]] = Set(
    AncestorsFeature,
    AuthorIdFeature,
    ConversationModuleIdFeature,
    ConversationModuleFocalTweetIdFeature,
    DirectedAtUserIdFeature,
    FavoritedByUserIdsFeature,
    FollowedByUserIdsFeature,
    InNetworkFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsReadFromCacheFeature,
    IsRetweetFeature,
    QuotedTweetIdFeature,
    QuotedUserIdFeature,
    ScoreFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    StreamToKafkaFeature,
    SuggestTypeFeature,
    TopicContextFunctionalityTypeFeature,
    TopicIdSocialContextFeature
  )

  override def transform(input: ScoredTweetWithConversationMetadata): FeatureMap =
    FeatureMapBuilder()
      .add(AncestorsFeature, input.ancestors.getOrElse(Seq.empty))
      .add(AuthorIdFeature, Some(input.authorId))
      .add(ConversationModuleIdFeature, input.conversationId)
      .add(ConversationModuleFocalTweetIdFeature, input.conversationFocalTweetId)
      .add(DirectedAtUserIdFeature, input.directedAtUserId)
      .add(FavoritedByUserIdsFeature, input.favoritedByUserIds.getOrElse(Seq.empty))
      .add(FollowedByUserIdsFeature, input.followedByUserIds.getOrElse(Seq.empty))
      .add(InNetworkFeature, input.inNetwork.getOrElse(false))
      .add(InReplyToTweetIdFeature, input.inReplyToTweetId)
      .add(InReplyToUserIdFeature, input.inReplyToUserId)
      .add(IsReadFromCacheFeature, input.isReadFromCache.getOrElse(false))
      .add(IsRetweetFeature, input.sourceTweetId.isDefined)
      .add(QuotedTweetIdFeature, input.quotedTweetId)
      .add(QuotedUserIdFeature, input.quotedUserId)
      .add(ScoreFeature, input.score)
      .add(SourceTweetIdFeature, input.sourceTweetId)
      .add(SourceUserIdFeature, input.sourceUserId)
      .add(StreamToKafkaFeature, input.streamToKafka.getOrElse(false))
      .add(SuggestTypeFeature, input.suggestType)
      .add(
        TopicContextFunctionalityTypeFeature,
        input.topicFunctionalityType.collect {
          case tl.TopicContextFunctionalityType.Basic => BasicTopicContextFunctionalityType
          case tl.TopicContextFunctionalityType.Recommendation =>
            RecommendationTopicContextFunctionalityType
          case tl.TopicContextFunctionalityType.RecWithEducation =>
            RecWithEducationTopicContextFunctionalityType
        }
      )
      .add(TopicIdSocialContextFeature, input.topicId)
      .build()
}
