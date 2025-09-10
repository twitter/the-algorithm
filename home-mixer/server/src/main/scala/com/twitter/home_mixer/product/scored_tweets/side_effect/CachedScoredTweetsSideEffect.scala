package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.transport.Transport
import com.twitter.home_mixer.model.HomeFeatures._
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
import com.twitter.home_mixer.param.HomeMixerInjectionNames.ScoredTweetsCache
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityNameFeature
import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationIdFeature
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.TopicContextFunctionalityTypeMarshaller
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.TtlCache
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CachedScoredTweetsSideEffect @Inject() (
  @Named(ScoredTweetsCache)
  scoredTweetsCache: TtlCache[Long, hmt.ScoredTweetsResponse])
    extends PipelineResultSideEffect[PipelineQuery, ScoredTweetsResponse]
    with Conditionally[PipelineQuery, ScoredTweetsResponse] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("CachedScoredTweets")

  private val MaxTweetsToCache = 1000

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: ScoredTweetsResponse
  ): Boolean = {
    val serviceIdentifier = ServiceIdentifier.fromCertificate(Transport.peerCertificate)
    serviceIdentifier.role != "explore-mixer" && serviceIdentifier.role != "video-mixer"
  }

  def buildCachedScoredTweets(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails]
  ): hmt.ScoredTweetsResponse = {
    val tweets = candidates.map { candidate =>
      val sgsValidLikedByUserIds =
        candidate.features.getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty)
      val validLikedByUserIds = candidate.features.getOrElse(ValidLikedByUserIdsFeature, Seq.empty)
      val sgsValidFollowedByUserIds =
        candidate.features.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty)
      val ancestors = candidate.features.getOrElse(AncestorsFeature, Seq.empty)
      val mediaIds = candidate.features.getOrElse(TweetMediaIdsFeature, Seq.empty)
      val sourceSignalOpt = candidate.features.getOrElse(SourceSignalFeature, None)
      val sourceSignal = sourceSignalOpt.map { signal =>
        hmt.SourceSignal(
          id = signal.id,
          signalType = signal.signalType,
          signalEntity = signal.signalEntity,
          authorId = signal.authorId,
        )
      }

      val predictedScores = hmt.PredictedScores(
        favoriteScore = candidate.features.getOrElse(PredictedFavoriteScoreFeature, None),
        replyScore = candidate.features.getOrElse(PredictedReplyScoreFeature, None),
        retweetScore = candidate.features.getOrElse(PredictedRetweetScoreFeature, None),
        replyEngagedByAuthorScore =
          candidate.features.getOrElse(PredictedReplyEngagedByAuthorScoreFeature, None),
        goodClickConvoDescFavoritedOrRepliedScore = candidate.features
          .getOrElse(PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature, None),
        goodClickConvoDescUamGt2Score =
          candidate.features.getOrElse(PredictedGoodClickConvoDescUamGt2ScoreFeature, None),
        goodProfileClickScore =
          candidate.features.getOrElse(PredictedGoodProfileClickScoreFeature, None),
        videoQualityViewScore =
          candidate.features.getOrElse(PredictedVideoQualityViewScoreFeature, None),
        shareScore = candidate.features.getOrElse(PredictedShareScoreFeature, None),
        dwellScore = candidate.features.getOrElse(PredictedDwellScoreFeature, None),
        negativeFeedbackV2Score =
          candidate.features.getOrElse(PredictedNegativeFeedbackV2ScoreFeature, None)
      )

      hmt.ScoredTweet(
        tweetId = candidate.candidateIdLong,
        authorId = candidate.features.get(AuthorIdFeature).get,
        // Cache the model score instead of the final score because rescoring is per-request
        score = candidate.features.getOrElse(WeightedModelScoreFeature, None),
        servedType = candidate.features.get(ServedTypeFeature),
        sourceTweetId = candidate.features.getOrElse(SourceTweetIdFeature, None),
        sourceUserId = candidate.features.getOrElse(SourceUserIdFeature, None),
        quotedTweetId = candidate.features.getOrElse(QuotedTweetIdFeature, None),
        quotedUserId = candidate.features.getOrElse(QuotedUserIdFeature, None),
        inReplyToTweetId = candidate.features.getOrElse(InReplyToTweetIdFeature, None),
        inReplyToUserId = candidate.features.getOrElse(InReplyToUserIdFeature, None),
        directedAtUserId = candidate.features.getOrElse(DirectedAtUserIdFeature, None),
        inNetwork = Some(candidate.features.getOrElse(InNetworkFeature, true)),
        sgsValidLikedByUserIds = Some(sgsValidLikedByUserIds),
        validLikedByUserIds = Some(validLikedByUserIds),
        sgsValidFollowedByUserIds = Some(sgsValidFollowedByUserIds),
        topicId = candidate.features.getOrElse(TopicIdSocialContextFeature, None),
        topicFunctionalityType = candidate.features
          .getOrElse(TopicContextFunctionalityTypeFeature, None).map(
            TopicContextFunctionalityTypeMarshaller(_)),
        ancestors = if (ancestors.nonEmpty) Some(ancestors) else None,
        isReadFromCache = Some(true),
        exclusiveConversationAuthorId = candidate.features
          .getOrElse(ExclusiveConversationAuthorIdFeature, None),
        authorMetadata = Some(
          hmt.AuthorMetadata(
            blueVerified = candidate.features.getOrElse(AuthorIsBlueVerifiedFeature, false),
            goldVerified = candidate.features.getOrElse(AuthorIsGoldVerifiedFeature, false),
            grayVerified = candidate.features.getOrElse(AuthorIsGrayVerifiedFeature, false),
            legacyVerified = candidate.features.getOrElse(AuthorIsLegacyVerifiedFeature, false),
            creator = candidate.features.getOrElse(AuthorIsCreatorFeature, false),
            followers = candidate.features.getOrElse(AuthorFollowersFeature, None)
          )),
        lastScoredTimestampMs = candidate.features
          .getOrElse(LastScoredTimestampMsFeature, Some(query.queryTime.inMilliseconds)),
        candidatePipelineIdentifier = candidate.features
          .getOrElse(CachedCandidatePipelineIdentifierFeature, Some(candidate.source.name)),
        tweetUrls = Some(candidate.features.getOrElse(TweetUrlsFeature, Seq.empty)),
        perspectiveFilteredLikedByUserIds = None,
        predictionRequestId = candidate.features.getOrElse(PredictionRequestIdFeature, None),
        communityId = candidate.features.getOrElse(CommunityIdFeature, None),
        communityName = candidate.features.getOrElse(CommunityNameFeature, None),
        listId = candidate.features.getOrElse(ListIdFeature, None),
        listName = candidate.features.getOrElse(ListNameFeature, None),
        tweetTypeMetrics = candidate.features.getOrElse(TweetTypeMetricsFeature, None),
        debugString = candidate.features.getOrElse(DebugStringFeature, None),
        viralContentCreator = Some(candidate.features.getOrElse(ViralContentCreatorFeature, false)),
        locationId = candidate.features.getOrElse(LocationIdFeature, None),
        isArticle = Some(candidate.features.getOrElse(IsArticleFeature, false)),
        hasVideo = Some(candidate.features.getOrElse(HasVideoFeature, false)),
        videoDurationMs = candidate.features.getOrElse(VideoDurationMsFeature, None),
        mediaIds = if (mediaIds.nonEmpty) Some(mediaIds) else None,
        grokAnnotations = candidate.features.getOrElse(GrokAnnotationsFeature, None),
        predictedScores = Some(predictedScores),
        tweetMixerScore = candidate.features.getOrElse(TweetMixerScoreFeature, None),
        clipClusterIdsFeature = Some(
          hmt.ClipClusterIdsFeature(
            tweetMediaClusterIdsFeature = Some(
              candidate.features.getOrElse(TweetMediaClusterIdsFeature, Map.empty[Long, Long])),
            clipImageClusterIdsFeature =
              Some(candidate.features.getOrElse(ClipImageClusterIdsFeature, Map.empty[Long, Long]))
          )),
        grokSlopScoreFeature = candidate.features.getOrElse(GrokSlopScoreFeature, None),
        mediaCompletionRate = candidate.features.getOrElse(TweetMediaCompletionRateFeature, None),
        tweetText = candidate.features.getOrElse(TweetTextFeature, None),
        sourceSignal = sourceSignal,
        grokContentCreator = Some(candidate.features.getOrElse(GrokContentCreatorFeature, false)),
        gorkContentCreator = Some(candidate.features.getOrElse(GorkContentCreatorFeature, false)),
        // MultiModalEmbeddingsFeature are not cached because the value size is too large for memcache
        multiModalEmbedding = None
      )
    }

    hmt.ScoredTweetsResponse(tweets)
  }

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, ScoredTweetsResponse]
  ): Stitch[Unit] = {
    val candidates =
      (inputs.selectedCandidates ++ inputs.remainingCandidates ++ inputs.droppedCandidates)
        .filter(_.features.getOrElse(ScoreFeature, None).exists(_ > 0.0))

    val truncatedCandidates =
      if (candidates.size > MaxTweetsToCache)
        candidates
          .sortBy(-_.features.getOrElse(ScoreFeature, None).getOrElse(0.0)).take(MaxTweetsToCache)
      else candidates

    if (truncatedCandidates.nonEmpty) {
      val ttl = inputs.query.params(CachedScoredTweets.TTLParam)
      val scoredTweets = buildCachedScoredTweets(inputs.query, truncatedCandidates)
      Stitch.callFuture(scoredTweetsCache.set(inputs.query.getRequiredUserId, scoredTweets, ttl))
    } else Stitch.Unit
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.4)
  )
}
