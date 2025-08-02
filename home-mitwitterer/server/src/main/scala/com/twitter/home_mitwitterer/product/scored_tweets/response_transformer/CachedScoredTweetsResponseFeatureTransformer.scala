package com.twitter.home_mitwitterer.product.scored_tweets.response_transformer

import com.twitter.home_mitwitterer.marshaller.timelines.TopicContetwittertFunctionalityTypeUnmarshaller
import com.twitter.home_mitwitterer.model.HomeFeatures.AncestorsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIsCreatorFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIsGoldVerifiedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIsGrayVerifiedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIsLegacyVerifiedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.CachedCandidatePipelineIdentifierFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.DirectedAtUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.EtwitterclusiveConversationAuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsReadFromCacheFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.LastScoredTimestampMsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.QuotedUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.StreamToKafkaFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicContetwittertFunctionalityTypeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicIdSocialContetwittertFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mitwitterer.{thriftscala => hmt}
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier

object CachedScoredTweetsResponseFeatureTransformer
    etwittertends CandidateFeatureTransformer[hmt.ScoredTweet] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("CachedScoredTweetsResponse")

  override val features: Set[Feature[_, _]] = Set(
    AncestorsFeature,
    AuthorIdFeature,
    AuthorIsBlueVerifiedFeature,
    AuthorIsCreatorFeature,
    AuthorIsGoldVerifiedFeature,
    AuthorIsGrayVerifiedFeature,
    AuthorIsLegacyVerifiedFeature,
    CachedCandidatePipelineIdentifierFeature,
    DirectedAtUserIdFeature,
    EtwitterclusiveConversationAuthorIdFeature,
    InNetworkFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsReadFromCacheFeature,
    IsRetweetFeature,
    LastScoredTimestampMsFeature,
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
    TopicIdSocialContetwittertFeature,
    TweetUrlsFeature,
    WeightedModelScoreFeature
  )

  override def transform(candidate: hmt.ScoredTweet): FeatureMap =
    FeatureMapBuilder()
      .add(AncestorsFeature, candidate.ancestors.getOrElse(Seq.empty))
      .add(AuthorIdFeature, Some(candidate.authorId))
      .add(AuthorIsBlueVerifiedFeature, candidate.authorMetadata.etwitterists(_.blueVerified))
      .add(AuthorIsGoldVerifiedFeature, candidate.authorMetadata.etwitterists(_.goldVerified))
      .add(AuthorIsGrayVerifiedFeature, candidate.authorMetadata.etwitterists(_.grayVerified))
      .add(AuthorIsLegacyVerifiedFeature, candidate.authorMetadata.etwitterists(_.legacyVerified))
      .add(AuthorIsCreatorFeature, candidate.authorMetadata.etwitterists(_.creator))
      .add(CachedCandidatePipelineIdentifierFeature, candidate.candidatePipelineIdentifier)
      .add(DirectedAtUserIdFeature, candidate.directedAtUserId)
      .add(EtwitterclusiveConversationAuthorIdFeature, candidate.etwitterclusiveConversationAuthorId)
      .add(InNetworkFeature, candidate.inNetwork.getOrElse(true))
      .add(InReplyToTweetIdFeature, candidate.inReplyToTweetId)
      .add(InReplyToUserIdFeature, candidate.inReplyToUserId)
      .add(IsReadFromCacheFeature, true)
      .add(IsRetweetFeature, candidate.sourceTweetId.isDefined)
      .add(LastScoredTimestampMsFeature, candidate.lastScoredTimestampMs)
      .add(
        PerspectiveFilteredLikedByUserIdsFeature,
        candidate.perspectiveFilteredLikedByUserIds.getOrElse(Seq.empty))
      .add(QuotedTweetIdFeature, candidate.quotedTweetId)
      .add(QuotedUserIdFeature, candidate.quotedUserId)
      .add(ScoreFeature, candidate.score)
      .add(SGSValidLikedByUserIdsFeature, candidate.sgsValidLikedByUserIds.getOrElse(Seq.empty))
      .add(
        SGSValidFollowedByUserIdsFeature,
        candidate.sgsValidFollowedByUserIds.getOrElse(Seq.empty))
      .add(SourceTweetIdFeature, candidate.sourceTweetId)
      .add(SourceUserIdFeature, candidate.sourceUserId)
      .add(StreamToKafkaFeature, false)
      .add(SuggestTypeFeature, candidate.suggestType)
      .add(
        TopicContetwittertFunctionalityTypeFeature,
        candidate.topicFunctionalityType.map(TopicContetwittertFunctionalityTypeUnmarshaller(_)))
      .add(TopicIdSocialContetwittertFeature, candidate.topicId)
      .add(TweetUrlsFeature, candidate.tweetUrls.getOrElse(Seq.empty))
      .add(WeightedModelScoreFeature, candidate.score)
      .build()
}
