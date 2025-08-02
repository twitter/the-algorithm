package com.twitter.product_mitwitterer.component_library.candidate_source.tweetconvosvc

import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineservice.suggests.thriftscala.SuggestType

object AuthorIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
object AncestorIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
object ConversationModuleFocalTweetIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
object InReplyToFeature etwittertends Feature[TweetCandidate, Option[Long]]
object IsRetweetFeature etwittertends Feature[TweetCandidate, Boolean]
object SourceTweetIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
object SourceUserIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
object SuggestTypeFeature etwittertends Feature[TweetCandidate, Option[SuggestType]]

object ConversationServiceResponseFeatureTransformer
    etwittertends CandidateFeatureTransformer[TweetWithConversationMetadata] {
  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ConversationServiceResponse")

  override val features: Set[Feature[_, _]] =
    Set(
      AuthorIdFeature,
      InReplyToFeature,
      IsRetweetFeature,
      SourceTweetIdFeature,
      SourceUserIdFeature,
      ConversationModuleFocalTweetIdFeature,
      AncestorIdsFeature,
      SuggestTypeFeature
    )

  override def transform(candidate: TweetWithConversationMetadata): FeatureMap = {
    FeatureMapBuilder()
      .add(AuthorIdFeature, candidate.userId)
      .add(InReplyToFeature, candidate.inReplyToTweetId)
      .add(IsRetweetFeature, candidate.sourceTweetId.isDefined)
      .add(SourceTweetIdFeature, candidate.sourceTweetId)
      .add(SourceUserIdFeature, candidate.sourceUserId)
      .add(ConversationModuleFocalTweetIdFeature, candidate.conversationId)
      .add(AncestorIdsFeature, candidate.ancestors.map(_.tweetId))
      .add(SuggestTypeFeature, Some(SuggestType.OrganicConversation))
      .build()
  }
}
