package com.ExTwitter.home_mixer.product.scored_tweets.response_transformer

import com.ExTwitter.home_mixer.marshaller.timelines.TopicContextFunctionalityTypeUnmarshaller
import com.ExTwitter.home_mixer.model.HomeFeatures.AncestorsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIsCreatorFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIsGoldVerifiedFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIsGrayVerifiedFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIsLegacyVerifiedFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.CachedCandidatePipelineIdentifierFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.DirectedAtUserIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.IsReadFromCacheFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.LastScoredTimestampMsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.QuotedUserIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.StreamToKafkaFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.ExTwitter.home_mixer.{thriftscala => hmt}
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object CachedScoredTweetsResponseFeatureTransformer
    extends CandidateFeatureTransformer[hmt.ScoredTweet] {

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
    ExclusiveConversationAuthorIdFeature,
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
    TopicContextFunctionalityTypeFeature,
    TopicIdSocialContextFeature,
    TweetUrlsFeature,
    WeightedModelScoreFeature
  )

  override def transform(candidate: hmt.ScoredTweet): FeatureMap =
    FeatureMapBuilder()
      .add(AncestorsFeature, candidate.ancestors.getOrElse(Seq.empty))
      .add(AuthorIdFeature, Some(candidate.authorId))
      .add(AuthorIsBlueVerifiedFeature, candidate.authorMetadata.exists(_.blueVerified))
      .add(AuthorIsGoldVerifiedFeature, candidate.authorMetadata.exists(_.goldVerified))
      .add(AuthorIsGrayVerifiedFeature, candidate.authorMetadata.exists(_.grayVerified))
      .add(AuthorIsLegacyVerifiedFeature, candidate.authorMetadata.exists(_.legacyVerified))
      .add(AuthorIsCreatorFeature, candidate.authorMetadata.exists(_.creator))
      .add(CachedCandidatePipelineIdentifierFeature, candidate.candidatePipelineIdentifier)
      .add(DirectedAtUserIdFeature, candidate.directedAtUserId)
      .add(ExclusiveConversationAuthorIdFeature, candidate.exclusiveConversationAuthorId)
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
        TopicContextFunctionalityTypeFeature,
        candidate.topicFunctionalityType.map(TopicContextFunctionalityTypeUnmarshaller(_)))
      .add(TopicIdSocialContextFeature, candidate.topicId)
      .add(TweetUrlsFeature, candidate.tweetUrls.getOrElse(Seq.empty))
      .add(WeightedModelScoreFeature, candidate.score)
      .build()
}
