package com.twitter.home_mixer.product.scored_tweets.marshaller

import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.model._
import com.twitter.home_mixer.product.scored_tweets.model.QueryMetadata
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityNameFeature
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.tweet_is_nsfw.IsNsfw
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.tweet_visibility_reason.VisibilityReason
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.TopicContextFunctionalityTypeMarshaller
import com.twitter.product_mixer.core.model.common.identifier.TransportMarshallerIdentifier

/**
 * Marshall the domain model into our transport (Thrift) model.
 */
object ScoredTweetsResponseTransportMarshaller
    extends TransportMarshaller[ScoredTweetsResponse, hmt.ScoredTweetsResponse] {

  override val identifier: TransportMarshallerIdentifier =
    TransportMarshallerIdentifier("ScoredTweetsResponse")

  override def apply(input: ScoredTweetsResponse): hmt.ScoredTweetsResponse = {
    val scoredTweets = input.scoredTweets.map { tweet =>
      mkScoredTweet(tweet.candidateIdLong, tweet.features, input.queryMetadata)
    }
    hmt.ScoredTweetsResponse(scoredTweets)
  }

  private def mkScoredTweet(
    tweetId: Long,
    features: FeatureMap,
    queryMetadata: Option[QueryMetadata]
  ): hmt.ScoredTweet = {
    val topicFunctionalityType = features
      .getOrElse(TopicContextFunctionalityTypeFeature, None)
      .map(TopicContextFunctionalityTypeMarshaller(_))

    val predictedScores = hmt.PredictedScores(
      favoriteScore = features.getOrElse(PredictedFavoriteScoreFeature, None),
      replyScore = features.getOrElse(PredictedReplyScoreFeature, None),
      retweetScore = features.getOrElse(PredictedRetweetScoreFeature, None),
      replyEngagedByAuthorScore =
        features.getOrElse(PredictedReplyEngagedByAuthorScoreFeature, None),
      goodClickConvoDescFavoritedOrRepliedScore = features
        .getOrElse(PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature, None),
      goodClickConvoDescUamGt2Score =
        features.getOrElse(PredictedGoodClickConvoDescUamGt2ScoreFeature, None),
      goodProfileClickScore = features.getOrElse(PredictedGoodProfileClickScoreFeature, None),
      videoQualityViewScore = features.getOrElse(PredictedVideoQualityViewScoreFeature, None),
      shareScore = features.getOrElse(PredictedShareScoreFeature, None),
      dwellScore = features.getOrElse(PredictedDwellScoreFeature, None),
      negativeFeedbackV2Score = features.getOrElse(PredictedNegativeFeedbackV2ScoreFeature, None)
    )

    val phoenixPredictedScores = hmt.PredictedScores(
      favoriteScore = features.getOrElse(PhoenixPredictedFavoriteScoreFeature, None),
      replyScore = features.getOrElse(PhoenixPredictedReplyScoreFeature, None),
      retweetScore = features.getOrElse(PhoenixPredictedRetweetScoreFeature, None),
      replyEngagedByAuthorScore = None,
      goodClickConvoDescFavoritedOrRepliedScore = features
        .getOrElse(PhoenixPredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature, None),
      goodClickConvoDescUamGt2Score =
        features.getOrElse(PhoenixPredictedGoodClickConvoDescUamGt2ScoreFeature, None),
      goodProfileClickScore =
        features.getOrElse(PhoenixPredictedGoodProfileClickScoreFeature, None),
      videoQualityViewScore =
        features.getOrElse(PhoenixPredictedVideoQualityViewScoreFeature, None),
      shareScore = features.getOrElse(PhoenixPredictedShareScoreFeature, None),
      dwellScore = features.getOrElse(PhoenixPredictedDwellScoreFeature, None),
      negativeFeedbackV2Score =
        features.getOrElse(PhoenixPredictedNegativeFeedbackV2ScoreFeature, None),
      openLinkScore = features.getOrElse(PhoenixPredictedOpenLinkScoreFeature, None),
      screenshotScore = features.getOrElse(PhoenixPredictedScreenshotScoreFeature, None),
      bookmarkScore = features.getOrElse(PhoenixPredictedBookmarkScoreFeature, None)
    )

    val sourceSignal: Option[hmt.SourceSignal] = features.getOrElse(SourceSignalFeature, None).map {
      modelSignal =>
        hmt.SourceSignal(
          id = modelSignal.id,
          signalType = modelSignal.signalType,
          signalEntity = modelSignal.signalEntity,
          authorId = modelSignal.authorId,
        )
    }
    hmt.ScoredTweet(
      tweetId = tweetId,
      authorId = features.get(AuthorIdFeature).get,
      score = features.get(ScoreFeature),
      servedType = features.get(ServedTypeFeature),
      sourceTweetId = features.getOrElse(SourceTweetIdFeature, None),
      sourceUserId = features.getOrElse(SourceUserIdFeature, None),
      quotedTweetId = features.getOrElse(QuotedTweetIdFeature, None),
      quotedUserId = features.getOrElse(QuotedUserIdFeature, None),
      inReplyToTweetId = features.getOrElse(InReplyToTweetIdFeature, None),
      inReplyToUserId = features.getOrElse(InReplyToUserIdFeature, None),
      directedAtUserId = features.getOrElse(DirectedAtUserIdFeature, None),
      inNetwork = Some(features.getOrElse(InNetworkFeature, true)),
      sgsValidLikedByUserIds = Some(features.getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty)),
      sgsValidFollowedByUserIds =
        Some(features.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty)),
      validLikedByUserIds = Some(features.getOrElse(ValidLikedByUserIdsFeature, Seq.empty)),
      topicId = features.getOrElse(TopicIdSocialContextFeature, None),
      topicFunctionalityType = topicFunctionalityType,
      ancestors = Some(features.getOrElse(AncestorsFeature, Seq.empty)),
      isReadFromCache = Some(features.getOrElse(IsReadFromCacheFeature, false)),
      exclusiveConversationAuthorId =
        features.getOrElse(ExclusiveConversationAuthorIdFeature, None),
      authorMetadata = Some(
        hmt.AuthorMetadata(
          blueVerified = features.getOrElse(AuthorIsBlueVerifiedFeature, false),
          goldVerified = features.getOrElse(AuthorIsGoldVerifiedFeature, false),
          grayVerified = features.getOrElse(AuthorIsGrayVerifiedFeature, false),
          legacyVerified = features.getOrElse(AuthorIsLegacyVerifiedFeature, false),
          creator = features.getOrElse(AuthorIsCreatorFeature, false),
          followers = features.getOrElse(AuthorFollowersFeature, None)
        )),
      lastScoredTimestampMs = None,
      candidatePipelineIdentifier = None,
      tweetUrls = None,
      perspectiveFilteredLikedByUserIds = None,
      predictionRequestId = features.getOrElse(PredictionRequestIdFeature, None),
      communityId = features.getOrElse(CommunityIdFeature, None),
      communityName = features.getOrElse(CommunityNameFeature, None),
      listId = features.getOrElse(ListIdFeature, None),
      listName = features.getOrElse(ListNameFeature, None),
      isNsfw = features.getOrElse(IsNsfw, None),
      visibilityReason = features.getOrElse(VisibilityReason, None),
      tweetLanguage = features.getOrElse(TweetLanguageFeature, None),
      tweetText = features.getOrElse(TweetTextFeature, None),
      tweetTypeMetrics = features.getOrElse(TweetTypeMetricsFeature, None),
      debugString = features.getOrElse(DebugStringFeature, None),
      hasVideo = Some(features.getOrElse(HasVideoFeature, false)),
      videoDurationMs = features.getOrElse(VideoDurationMsFeature, None),
      mediaIds = Some(features.getOrElse(TweetMediaIdsFeature, Seq.empty)),
      grokAnnotations = features.getOrElse(GrokAnnotationsFeature, None),
      predictedScores = Some(predictedScores),
      tweetMixerScore = features.getOrElse(TweetMixerScoreFeature, None),
      phoenixPredictedScores = Some(phoenixPredictedScores),
      sourceSignal = sourceSignal,
      userActionsSize = queryMetadata.flatMap(_.userActionsSize),
      userActionsContainsExplicitSignals =
        queryMetadata.flatMap(_.userActionsContainsExplicitSignals)
    )
  }
}
