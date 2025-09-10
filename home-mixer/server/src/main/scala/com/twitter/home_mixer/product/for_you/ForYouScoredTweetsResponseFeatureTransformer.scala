package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.model.GrokTopics.GrokCategoryIdToNameMap
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.model.PhoenixPredictedBookmarkScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedDwellScoreFeature
import com.twitter.home_mixer.model.candidate_source.SourceSignal
import com.twitter.home_mixer.model.PhoenixPredictedFavoriteScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedGoodClickConvoDescUamGt2ScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedGoodProfileClickScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedNegativeFeedbackV2ScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedOpenLinkScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedReplyScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedRetweetScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedScreenshotScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedShareScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedVideoQualityViewScoreFeature
import com.twitter.home_mixer.model.PredictedDwellScoreFeature
import com.twitter.home_mixer.model.PredictedFavoriteScoreFeature
import com.twitter.home_mixer.model.PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature
import com.twitter.home_mixer.model.PredictedGoodClickConvoDescUamGt2ScoreFeature
import com.twitter.home_mixer.model.PredictedGoodProfileClickScoreFeature
import com.twitter.home_mixer.model.PredictedNegativeFeedbackV2ScoreFeature
import com.twitter.home_mixer.model.PredictedReplyEngagedByAuthorScoreFeature
import com.twitter.home_mixer.model.PredictedReplyScoreFeature
import com.twitter.home_mixer.model.PredictedRetweetScoreFeature
import com.twitter.home_mixer.model.PredictedShareScoreFeature
import com.twitter.home_mixer.model.PredictedVideoQualityViewScoreFeature
import com.twitter.home_mixer.product.for_you.candidate_source.ScoredTweetWithConversationMetadata
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityNameFeature
import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationIdFeature
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.tweet_is_nsfw.IsNsfw
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.tweet_visibility_reason.VisibilityReason
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType
import com.twitter.timelines.render.{thriftscala => tl}

object ForYouScoredTweetsResponseFeatureTransformer
    extends CandidateFeatureTransformer[ScoredTweetWithConversationMetadata] {

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
    AuthorFollowersFeature,
    ConversationModuleFocalTweetIdFeature,
    ConversationModuleIdFeature,
    DirectedAtUserIdFeature,
    DebugStringFeature,
    SourceSignalFeature,
    ExclusiveConversationAuthorIdFeature,
    FullScoringSucceededFeature,
    FavoritedByUserIdsFeature,
    FollowedByUserIdsFeature,
    InNetworkFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsAncestorCandidateFeature,
    IsNsfw,
    IsReadFromCacheFeature,
    IsRetweetFeature,
    CommunityIdFeature,
    CommunityNameFeature,
    ListIdFeature,
    ListNameFeature,
    LocationIdFeature,
    PredictionRequestIdFeature,
    QuotedTweetIdFeature,
    QuotedUserIdFeature,
    SGSValidFollowedByUserIdsFeature,
    SGSValidLikedByUserIdsFeature,
    ValidLikedByUserIdsFeature,
    ScoreFeature,
    ServedTypeFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    TopicContextFunctionalityTypeFeature,
    TopicIdSocialContextFeature,
    TweetLanguageFeature,
    TweetTextFeature,
    TweetTypeMetricsFeature,
    UserActionsSizeFeature,
    UserActionsContainsExplicitSignalsFeature,
    VisibilityReason,
    ViralContentCreatorFeature,
    GrokContentCreatorFeature,
    GorkContentCreatorFeature,
    HasVideoFeature,
    VideoDurationMsFeature,
    TweetMediaIdsFeature,
    GrokAnnotationsFeature,
    GrokTopCategoryFeature,
    GrokIsGoreFeature,
    GrokIsNsfwFeature,
    GrokIsSpamFeature,
    GrokIsViolentFeature,
    GrokIsLowQualityFeature,
    GrokIsOcrFeature,
    PredictedDwellScoreFeature,
    PredictedFavoriteScoreFeature,
    PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
    PredictedGoodClickConvoDescUamGt2ScoreFeature,
    PredictedGoodProfileClickScoreFeature,
    PredictedNegativeFeedbackV2ScoreFeature,
    PredictedReplyEngagedByAuthorScoreFeature,
    PredictedReplyScoreFeature,
    PredictedRetweetScoreFeature,
    PredictedShareScoreFeature,
    PredictedVideoQualityViewScoreFeature,
    PhoenixPredictedDwellScoreFeature,
    PhoenixPredictedFavoriteScoreFeature,
    PhoenixPredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
    PhoenixPredictedGoodClickConvoDescUamGt2ScoreFeature,
    PhoenixPredictedGoodProfileClickScoreFeature,
    PhoenixPredictedNegativeFeedbackV2ScoreFeature,
    PhoenixPredictedReplyScoreFeature,
    PhoenixPredictedRetweetScoreFeature,
    PhoenixPredictedShareScoreFeature,
    PhoenixPredictedVideoQualityViewScoreFeature,
    PhoenixPredictedOpenLinkScoreFeature,
    PhoenixPredictedScreenshotScoreFeature,
    PhoenixPredictedBookmarkScoreFeature
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
      .add(AuthorFollowersFeature, input.authorFollowers)
      .add(CommunityIdFeature, input.communityId)
      .add(CommunityNameFeature, input.communityName)
      .add(ConversationModuleIdFeature, input.conversationId)
      .add(ConversationModuleFocalTweetIdFeature, input.conversationFocalTweetId)
      .add(DirectedAtUserIdFeature, input.directedAtUserId)
      .add(DebugStringFeature, input.debugString)
      .add(
        SourceSignalFeature,
        input.sourceSignal.map { ss =>
          SourceSignal(ss.id, ss.signalType, ss.signalEntity, ss.authorId)
        }
      )
      .add(ExclusiveConversationAuthorIdFeature, input.exclusiveConversationAuthorId)
      .add(SGSValidLikedByUserIdsFeature, input.sgsValidLikedByUserIds.getOrElse(Seq.empty))
      .add(SGSValidFollowedByUserIdsFeature, input.sgsValidFollowedByUserIds.getOrElse(Seq.empty))
      .add(ValidLikedByUserIdsFeature, input.validLikedByUserIds.getOrElse(Seq.empty))
      .add(FavoritedByUserIdsFeature, input.sgsValidLikedByUserIds.getOrElse(Seq.empty))
      .add(FollowedByUserIdsFeature, input.sgsValidFollowedByUserIds.getOrElse(Seq.empty))
      .add(FullScoringSucceededFeature, true)
      .add(InNetworkFeature, input.inNetwork.getOrElse(true))
      .add(InReplyToTweetIdFeature, input.inReplyToTweetId)
      .add(InReplyToUserIdFeature, input.inReplyToUserId)
      .add(IsAncestorCandidateFeature, input.conversationFocalTweetId.exists(_ != input.tweetId))
      .add(IsReadFromCacheFeature, input.isReadFromCache.getOrElse(false))
      .add(IsRetweetFeature, input.sourceTweetId.isDefined)
      .add(IsNsfw, input.isNsfw)
      .add(ListIdFeature, input.listId)
      .add(ListNameFeature, input.listName)
      .add(LocationIdFeature, input.locationId)
      .add(PredictionRequestIdFeature, input.predictionRequestId)
      .add(QuotedTweetIdFeature, input.quotedTweetId)
      .add(QuotedUserIdFeature, input.quotedUserId)
      .add(ScoreFeature, input.score)
      .add(SourceTweetIdFeature, input.sourceTweetId)
      .add(SourceUserIdFeature, input.sourceUserId)
      .add(ServedTypeFeature, input.servedType)
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
      .add(TweetLanguageFeature, input.tweetLanguage)
      .add(TweetTextFeature, input.tweetText)
      .add(TweetTypeMetricsFeature, input.tweetTypeMetrics)
      .add(UserActionsSizeFeature, input.userActionsSize)
      .add(
        UserActionsContainsExplicitSignalsFeature,
        input.userActionsContainsExplicitSignals.getOrElse(false))
      .add(VisibilityReason, input.visibilityReason)
      .add(ViralContentCreatorFeature, input.viralContentCreatorFeature.getOrElse(false))
      .add(GrokContentCreatorFeature, input.grokContentCreatorFeature.getOrElse(false))
      .add(GorkContentCreatorFeature, input.gorkContentCreatorFeature.getOrElse(false))
      .add(HasVideoFeature, input.hasVideoFeature.getOrElse(false))
      .add(VideoDurationMsFeature, input.videoDurationMsFeature)
      .add(TweetMediaIdsFeature, input.mediaIds.getOrElse(Seq.empty))
      .add(GrokAnnotationsFeature, input.grokAnnotations)
      .add(GrokIsGoreFeature, input.grokAnnotations.flatMap(_.metadata.map(_.isGore)))
      .add(GrokIsNsfwFeature, input.grokAnnotations.flatMap(_.metadata.map(_.isNsfw)))
      .add(GrokIsSpamFeature, input.grokAnnotations.flatMap(_.metadata.map(_.isSpam)))
      .add(GrokIsViolentFeature, input.grokAnnotations.flatMap(_.metadata.map(_.isViolent)))
      .add(GrokIsLowQualityFeature, input.grokAnnotations.flatMap(_.metadata.map(_.isLowQuality)))
      .add(GrokIsOcrFeature, input.grokAnnotations.flatMap(_.metadata.map(_.isOcr)))
      .add(
        GrokTopCategoryFeature,
        input.grokAnnotations.flatMap { annotations =>
          annotations.categoryScores.flatMap { scores =>
            val validCategories = scores.collect {
              case (category, score)
                  if category.forall(_.isDigit) &&
                    GrokCategoryIdToNameMap.contains(category.toLong) =>
                (category.toLong, score)
            }
            if (validCategories.nonEmpty) Some(validCategories.maxBy(_._2)._1) else None
          }
        }
      )
      .add(PredictedDwellScoreFeature, input.predictedScores.flatMap(_.dwellScore))
      .add(PredictedFavoriteScoreFeature, input.predictedScores.flatMap(_.favoriteScore))
      .add(
        PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
        input.predictedScores.flatMap(_.goodClickConvoDescFavoritedOrRepliedScore))
      .add(
        PredictedGoodClickConvoDescUamGt2ScoreFeature,
        input.predictedScores.flatMap(_.goodClickConvoDescUamGt2Score))
      .add(
        PredictedGoodProfileClickScoreFeature,
        input.predictedScores.flatMap(_.goodProfileClickScore))
      .add(
        PredictedNegativeFeedbackV2ScoreFeature,
        input.predictedScores.flatMap(_.negativeFeedbackV2Score))
      .add(
        PredictedReplyEngagedByAuthorScoreFeature,
        input.predictedScores.flatMap(_.replyEngagedByAuthorScore))
      .add(PredictedReplyScoreFeature, input.predictedScores.flatMap(_.replyScore))
      .add(PredictedRetweetScoreFeature, input.predictedScores.flatMap(_.retweetScore))
      .add(PredictedShareScoreFeature, input.predictedScores.flatMap(_.shareScore))
      .add(
        PredictedVideoQualityViewScoreFeature,
        input.predictedScores.flatMap(_.videoQualityViewScore))
      .add(PhoenixPredictedDwellScoreFeature, input.phoenixPredictedScores.flatMap(_.dwellScore))
      .add(
        PhoenixPredictedFavoriteScoreFeature,
        input.phoenixPredictedScores.flatMap(_.favoriteScore))
      .add(
        PhoenixPredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
        input.phoenixPredictedScores.flatMap(_.goodClickConvoDescFavoritedOrRepliedScore))
      .add(
        PhoenixPredictedGoodClickConvoDescUamGt2ScoreFeature,
        input.phoenixPredictedScores.flatMap(_.goodClickConvoDescUamGt2Score))
      .add(
        PhoenixPredictedGoodProfileClickScoreFeature,
        input.phoenixPredictedScores.flatMap(_.goodProfileClickScore))
      .add(
        PhoenixPredictedNegativeFeedbackV2ScoreFeature,
        input.phoenixPredictedScores.flatMap(_.negativeFeedbackV2Score))
      .add(PhoenixPredictedReplyScoreFeature, input.phoenixPredictedScores.flatMap(_.replyScore))
      .add(
        PhoenixPredictedRetweetScoreFeature,
        input.phoenixPredictedScores.flatMap(_.retweetScore))
      .add(PhoenixPredictedShareScoreFeature, input.phoenixPredictedScores.flatMap(_.shareScore))
      .add(
        PhoenixPredictedVideoQualityViewScoreFeature,
        input.phoenixPredictedScores.flatMap(_.videoQualityViewScore))
      .add(
        PhoenixPredictedOpenLinkScoreFeature,
        input.phoenixPredictedScores.flatMap(_.openLinkScore))
      .add(
        PhoenixPredictedScreenshotScoreFeature,
        input.phoenixPredictedScores.flatMap(_.screenshotScore))
      .add(
        PhoenixPredictedBookmarkScoreFeature,
        input.phoenixPredictedScores.flatMap(_.bookmarkScore))
      .build()
}
