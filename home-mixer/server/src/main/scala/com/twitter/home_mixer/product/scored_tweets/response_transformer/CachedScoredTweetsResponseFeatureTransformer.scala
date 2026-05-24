package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.marshaller.timelines.TopicContextFunctionalityTypeUnmarshaller
import com.twitter.home_mixer.model.candidate_source.SourceSignal
import com.twitter.home_mixer.model.HomeFeatures.AncestorsFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorFollowersFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsCreatorFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsGoldVerifiedFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsGrayVerifiedFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsLegacyVerifiedFeature
import com.twitter.home_mixer.model.HomeFeatures.CachedCandidatePipelineIdentifierFeature
import com.twitter.home_mixer.model.HomeFeatures.ClipImageClusterIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.model.HomeFeatures.DirectedAtUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.GorkContentCreatorFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokAnnotationsFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokContentCreatorFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokSlopScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokTagsFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokTopCategoryFeature
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsArticleFeature
import com.twitter.home_mixer.model.HomeFeatures.IsReadFromCacheFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.LastScoredTimestampMsFeature
import com.twitter.home_mixer.model.HomeFeatures.ListIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ListNameFeature
import com.twitter.home_mixer.model.HomeFeatures.MultiModalEmbeddingsFeature
import com.twitter.home_mixer.model.HomeFeatures.PredictionRequestIdFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceSignalFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaClusterIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaCompletionRateFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMixerScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTextFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTypeMetricsFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDurationMsFeature
import com.twitter.home_mixer.model.HomeFeatures.ViralContentCreatorFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.model.PredictedFavoriteScoreFeature
import com.twitter.home_mixer.model.PredictedReplyScoreFeature
import com.twitter.home_mixer.model.PredictedRetweetScoreFeature
import com.twitter.home_mixer.model.PredictedShareScoreFeature
import com.twitter.home_mixer.model.PredictedVideoQualityViewScoreFeature
import com.twitter.home_mixer.model.PredictedDwellScoreFeature
import com.twitter.home_mixer.model.PredictedNegativeFeedbackV2ScoreFeature
import com.twitter.home_mixer.model.PredictedGoodClickConvoDescUamGt2ScoreFeature
import com.twitter.home_mixer.model.PredictedGoodProfileClickScoreFeature
import com.twitter.home_mixer.model.PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature
import com.twitter.home_mixer.model.PredictedReplyEngagedByAuthorScoreFeature
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityNameFeature
import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationIdFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

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
    AuthorFollowersFeature,
    CachedCandidatePipelineIdentifierFeature,
    DirectedAtUserIdFeature,
    DebugStringFeature,
    ExclusiveConversationAuthorIdFeature,
    InNetworkFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsReadFromCacheFeature,
    IsRetweetFeature,
    LastScoredTimestampMsFeature,
    PredictionRequestIdFeature,
    QuotedTweetIdFeature,
    QuotedUserIdFeature,
    SGSValidFollowedByUserIdsFeature,
    SGSValidLikedByUserIdsFeature,
    ScoreFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    ServedTypeFeature,
    TopicContextFunctionalityTypeFeature,
    TopicIdSocialContextFeature,
    TweetTypeMetricsFeature,
    TweetUrlsFeature,
    ViralContentCreatorFeature,
    GrokContentCreatorFeature,
    GorkContentCreatorFeature,
    WeightedModelScoreFeature,
    CommunityIdFeature,
    CommunityNameFeature,
    ListIdFeature,
    ListNameFeature,
    LocationIdFeature,
    IsArticleFeature,
    HasVideoFeature,
    VideoDurationMsFeature,
    TweetMediaIdsFeature,
    GrokAnnotationsFeature,
    GrokTopCategoryFeature,
    GrokTagsFeature,
    PredictedFavoriteScoreFeature,
    PredictedReplyScoreFeature,
    PredictedRetweetScoreFeature,
    PredictedReplyEngagedByAuthorScoreFeature,
    PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
    PredictedGoodClickConvoDescUamGt2ScoreFeature,
    PredictedGoodProfileClickScoreFeature,
    PredictedVideoQualityViewScoreFeature,
    PredictedShareScoreFeature,
    PredictedDwellScoreFeature,
    PredictedNegativeFeedbackV2ScoreFeature,
    TweetMixerScoreFeature,
    SourceSignalFeature,
    TweetMediaClusterIdsFeature,
    ClipImageClusterIdsFeature,
    GrokSlopScoreFeature,
    TweetMediaCompletionRateFeature,
    TweetTextFeature,
    MultiModalEmbeddingsFeature
  )

  override def transform(candidate: hmt.ScoredTweet): FeatureMap = {
    val grokTopCategory = candidate.grokAnnotations
      .flatMap(_.categoryScores)
      .flatMap { scores =>
        val validCategories = scores.collect {
          case (category, score) if category.forall(_.isDigit) && category.toLong % 10000 == 0 =>
            (category.toLong, score)
        }
        if (validCategories.nonEmpty) Some(validCategories.maxBy(_._2)._1) else None
      }

    FeatureMapBuilder()
      .add(AncestorsFeature, candidate.ancestors.getOrElse(Seq.empty))
      .add(AuthorIdFeature, Some(candidate.authorId))
      .add(AuthorIsBlueVerifiedFeature, candidate.authorMetadata.exists(_.blueVerified))
      .add(AuthorIsGoldVerifiedFeature, candidate.authorMetadata.exists(_.goldVerified))
      .add(AuthorIsGrayVerifiedFeature, candidate.authorMetadata.exists(_.grayVerified))
      .add(AuthorIsLegacyVerifiedFeature, candidate.authorMetadata.exists(_.legacyVerified))
      .add(AuthorIsCreatorFeature, candidate.authorMetadata.exists(_.creator))
      .add(AuthorFollowersFeature, candidate.authorMetadata.flatMap(_.followers))
      .add(CachedCandidatePipelineIdentifierFeature, candidate.candidatePipelineIdentifier)
      .add(DirectedAtUserIdFeature, candidate.directedAtUserId)
      .add(DebugStringFeature, candidate.debugString)
      .add(ExclusiveConversationAuthorIdFeature, candidate.exclusiveConversationAuthorId)
      .add(InNetworkFeature, candidate.inNetwork.getOrElse(true))
      .add(InReplyToTweetIdFeature, candidate.inReplyToTweetId)
      .add(InReplyToUserIdFeature, candidate.inReplyToUserId)
      .add(IsReadFromCacheFeature, true)
      .add(IsRetweetFeature, candidate.sourceTweetId.isDefined)
      .add(LastScoredTimestampMsFeature, candidate.lastScoredTimestampMs)
      .add(PredictionRequestIdFeature, candidate.predictionRequestId)
      .add(QuotedTweetIdFeature, candidate.quotedTweetId)
      .add(QuotedUserIdFeature, candidate.quotedUserId)
      .add(ScoreFeature, candidate.score)
      .add(SGSValidLikedByUserIdsFeature, candidate.sgsValidLikedByUserIds.getOrElse(Seq.empty))
      .add(
        SGSValidFollowedByUserIdsFeature,
        candidate.sgsValidFollowedByUserIds.getOrElse(Seq.empty))
      .add(SourceTweetIdFeature, candidate.sourceTweetId)
      .add(SourceUserIdFeature, candidate.sourceUserId)
      .add(ServedTypeFeature, candidate.servedType)
      .add(
        TopicContextFunctionalityTypeFeature,
        candidate.topicFunctionalityType.map(TopicContextFunctionalityTypeUnmarshaller(_)))
      .add(TopicIdSocialContextFeature, candidate.topicId)
      .add(TweetTypeMetricsFeature, candidate.tweetTypeMetrics)
      .add(TweetUrlsFeature, candidate.tweetUrls.getOrElse(Seq.empty))
      .add(ViralContentCreatorFeature, candidate.viralContentCreator.contains(true))
      .add(WeightedModelScoreFeature, candidate.score)
      .add(CommunityIdFeature, candidate.communityId)
      .add(CommunityNameFeature, candidate.communityName)
      .add(ListIdFeature, candidate.listId)
      .add(ListNameFeature, candidate.listName)
      .add(LocationIdFeature, candidate.locationId)
      .add(IsArticleFeature, candidate.isArticle.contains(true))
      .add(HasVideoFeature, candidate.hasVideo.contains(true))
      .add(VideoDurationMsFeature, candidate.videoDurationMs)
      .add(TweetMediaIdsFeature, candidate.mediaIds.getOrElse(Seq.empty))
      .add(GrokAnnotationsFeature, candidate.grokAnnotations)
      .add(GrokTopCategoryFeature, grokTopCategory)
      .add(
        GrokTagsFeature,
        candidate.grokAnnotations.map(_.tags.map(_.toLowerCase)).getOrElse(Seq.empty).toSet
      )
      .add(PredictedFavoriteScoreFeature, candidate.predictedScores.flatMap(_.favoriteScore))
      .add(PredictedReplyScoreFeature, candidate.predictedScores.flatMap(_.replyScore))
      .add(PredictedRetweetScoreFeature, candidate.predictedScores.flatMap(_.retweetScore))
      .add(
        PredictedReplyEngagedByAuthorScoreFeature,
        candidate.predictedScores.flatMap(_.replyEngagedByAuthorScore))
      .add(
        PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
        candidate.predictedScores.flatMap(_.goodClickConvoDescFavoritedOrRepliedScore))
      .add(
        PredictedGoodClickConvoDescUamGt2ScoreFeature,
        candidate.predictedScores.flatMap(_.goodClickConvoDescUamGt2Score))
      .add(
        PredictedGoodProfileClickScoreFeature,
        candidate.predictedScores.flatMap(_.goodProfileClickScore))
      .add(
        PredictedVideoQualityViewScoreFeature,
        candidate.predictedScores.flatMap(_.videoQualityViewScore))
      .add(PredictedShareScoreFeature, candidate.predictedScores.flatMap(_.shareScore))
      .add(PredictedDwellScoreFeature, candidate.predictedScores.flatMap(_.dwellScore))
      .add(
        PredictedNegativeFeedbackV2ScoreFeature,
        candidate.predictedScores.flatMap(_.negativeFeedbackV2Score))
      .add(TweetMixerScoreFeature, candidate.tweetMixerScore)
      .add(
        SourceSignalFeature,
        candidate.sourceSignal.map { signal =>
          SourceSignal(
            id = signal.id,
            signalType = signal.signalType,
            signalEntity = signal.signalEntity,
            authorId = signal.authorId,
          )
        }
      )
      .add(
        ClipImageClusterIdsFeature,
        candidate.clipClusterIdsFeature
          .flatMap(_.clipImageClusterIdsFeature).getOrElse(Map.empty[Long, Long]).toMap)
      .add(
        TweetMediaClusterIdsFeature,
        candidate.clipClusterIdsFeature
          .flatMap(_.tweetMediaClusterIdsFeature).getOrElse(Map.empty[Long, Long]).toMap)
      .add(GrokSlopScoreFeature, candidate.grokSlopScoreFeature)
      .add(TweetMediaCompletionRateFeature, candidate.mediaCompletionRate)
      .add(TweetTextFeature, candidate.tweetText)
      .add(GrokContentCreatorFeature, candidate.grokContentCreator.contains(true))
      .add(GorkContentCreatorFeature, candidate.gorkContentCreator.contains(true))
      .add(MultiModalEmbeddingsFeature, candidate.multiModalEmbedding)
      .build()
  }
}
