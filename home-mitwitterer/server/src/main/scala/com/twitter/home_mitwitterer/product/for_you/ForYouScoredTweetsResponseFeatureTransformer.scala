package com.twitter.home_mitwitterer.product.for_you

import com.twitter.home_mitwitterer.model.HomeFeatures._
import com.twitter.home_mitwitterer.product.for_you.candidate_source.ScoredTweetWithConversationMetadata
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.BasicTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecommendationTopicContetwittertFunctionalityType
import com.twitter.timelines.render.{thriftscala => tl}
import com.twitter.timelineservice.suggests.{thriftscala => tls}

object ForYouScoredTweetsResponseFeatureTransformer
    etwittertends CandidateFeatureTransformer[ScoredTweetWithConversationMetadata] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ForYouScoredTweetsResponse")

  override val features: Set[Feature[_, _]] = Set(
    AncestorsFeature,
    AuthorIdFeature,
    AuthorIsBlueVerifiedFeature,
    AuthorIsCreatorFeature,
    AuthorIsGoldVerifiedFeature,
    AuthorIsGrayVerifiedFeature,
    AuthorIsLegacyVerifiedFeature,
    ConversationModuleFocalTweetIdFeature,
    ConversationModuleIdFeature,
    DirectedAtUserIdFeature,
    EtwitterclusiveConversationAuthorIdFeature,
    FullScoringSucceededFeature,
    FavoritedByUserIdsFeature,
    FollowedByUserIdsFeature,
    InNetworkFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsAncestorCandidateFeature,
    IsReadFromCacheFeature,
    IsRetweetFeature,
    PerspectiveFilteredLikedByUserIdsFeature,
    QuotedTweetIdFeature,
    QuotedUserIdFeature,
    SGSValidFollowedByUserIdsFeature,
    SGSValidLikedByUserIdsFeature,
    ScoreFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    StreamToKafkaFeature,
    SuggestTypeFeature,
    TopicContetwittertFunctionalityTypeFeature,
    TopicIdSocialContetwittertFeature
  )

  override def transform(input: ScoredTweetWithConversationMetadata): FeatureMap =
    FeatureMapBuilder()
      .add(AncestorsFeature, input.ancestors.getOrElse(Seq.empty))
      .add(AuthorIdFeature, Some(input.authorId))
      .add(AuthorIsBlueVerifiedFeature, input.authorIsBlueVerified.getOrElse(false))
      .add(AuthorIsGoldVerifiedFeature, input.authorIsGoldVerified.getOrElse(false))
      .add(AuthorIsGrayVerifiedFeature, input.authorIsGrayVerified.getOrElse(false))
      .add(AuthorIsLegacyVerifiedFeature, input.authorIsLegacyVerified.getOrElse(false))
      .add(AuthorIsCreatorFeature, input.authorIsCreator.getOrElse(false))
      .add(ConversationModuleIdFeature, input.conversationId)
      .add(ConversationModuleFocalTweetIdFeature, input.conversationFocalTweetId)
      .add(DirectedAtUserIdFeature, input.directedAtUserId)
      .add(EtwitterclusiveConversationAuthorIdFeature, input.etwitterclusiveConversationAuthorId)
      .add(SGSValidLikedByUserIdsFeature, input.sgsValidLikedByUserIds.getOrElse(Seq.empty))
      .add(SGSValidFollowedByUserIdsFeature, input.sgsValidFollowedByUserIds.getOrElse(Seq.empty))
      .add(FavoritedByUserIdsFeature, input.sgsValidLikedByUserIds.getOrElse(Seq.empty))
      .add(FollowedByUserIdsFeature, input.sgsValidFollowedByUserIds.getOrElse(Seq.empty))
      .add(FullScoringSucceededFeature, true)
      .add(InNetworkFeature, input.inNetwork.getOrElse(true))
      .add(InReplyToTweetIdFeature, input.inReplyToTweetId)
      .add(InReplyToUserIdFeature, input.inReplyToUserId)
      .add(IsAncestorCandidateFeature, input.conversationFocalTweetId.etwitterists(_ != input.tweetId))
      .add(IsReadFromCacheFeature, input.isReadFromCache.getOrElse(false))
      .add(IsRetweetFeature, input.sourceTweetId.isDefined)
      .add(
        PerspectiveFilteredLikedByUserIdsFeature,
        input.perspectiveFilteredLikedByUserIds.getOrElse(Seq.empty))
      .add(QuotedTweetIdFeature, input.quotedTweetId)
      .add(QuotedUserIdFeature, input.quotedUserId)
      .add(ScoreFeature, input.score)
      .add(SourceTweetIdFeature, input.sourceTweetId)
      .add(SourceUserIdFeature, input.sourceUserId)
      .add(StreamToKafkaFeature, input.streamToKafka.getOrElse(false))
      .add(SuggestTypeFeature, input.suggestType.orElse(Some(tls.SuggestType.Undefined)))
      .add(
        TopicContetwittertFunctionalityTypeFeature,
        input.topicFunctionalityType.collect {
          case tl.TopicContetwittertFunctionalityType.Basic => BasicTopicContetwittertFunctionalityType
          case tl.TopicContetwittertFunctionalityType.Recommendation =>
            RecommendationTopicContetwittertFunctionalityType
          case tl.TopicContetwittertFunctionalityType.RecWithEducation =>
            RecWithEducationTopicContetwittertFunctionalityType
        }
      )
      .add(TopicIdSocialContetwittertFeature, input.topicId)
      .build()
}
