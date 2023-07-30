package com.X.home_mixer.candidate_pipeline

import com.X.home_mixer.model.HomeFeatures._
import com.X.product_mixer.component_library.candidate_source.tweetconvosvc.TweetWithConversationMetadata
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.timelineservice.suggests.thriftscala.SuggestType

object ConversationServiceResponseFeatureTransformer
    extends CandidateFeatureTransformer[TweetWithConversationMetadata] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ConversationServiceResponse")

  override val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    InReplyToTweetIdFeature,
    IsRetweetFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    ConversationModuleFocalTweetIdFeature,
    AncestorsFeature,
    SuggestTypeFeature
  )

  override def transform(candidate: TweetWithConversationMetadata): FeatureMap = FeatureMapBuilder()
    .add(AuthorIdFeature, candidate.userId)
    .add(InReplyToTweetIdFeature, candidate.inReplyToTweetId)
    .add(IsRetweetFeature, candidate.sourceTweetId.isDefined)
    .add(SourceTweetIdFeature, candidate.sourceTweetId)
    .add(SourceUserIdFeature, candidate.sourceUserId)
    .add(ConversationModuleFocalTweetIdFeature, candidate.conversationId)
    .add(AncestorsFeature, candidate.ancestors)
    .add(SuggestTypeFeature, Some(SuggestType.RankedOrganicTweet))
    .build()
}
