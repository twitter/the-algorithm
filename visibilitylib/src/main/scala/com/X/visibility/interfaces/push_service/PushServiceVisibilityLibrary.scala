package com.X.visibility.interfaces.push_service

import com.X.gizmoduck.thriftscala.User
import com.X.servo.util.Gate
import com.X.finagle.stats.StatsReceiver
import com.X.stitch.Stitch
import com.X.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.tweets.TweetFeatures
import com.X.visibility.builder.tweets.StratoTweetLabelMaps
import com.X.visibility.builder.users.AuthorFeatures
import com.X.visibility.builder.users.RelationshipFeatures
import com.X.visibility.builder.users.ViewerFeatures
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.common._
import com.X.visibility.common.UserRelationshipSource
import com.X.visibility.common.UserSource
import com.X.visibility.features.FeatureMap
import com.X.visibility.features.TweetIsInnerQuotedTweet
import com.X.visibility.features.TweetIsRetweet
import com.X.visibility.features.TweetIsSourceTweet
import com.X.visibility.interfaces.push_service.PushServiceVisibilityLibraryUtil._
import com.X.visibility.models.ContentId
import com.X.visibility.models.ViewerContext

object TweetType extends Enumeration {
  type TweetType = Value
  val ORIGINAL, SOURCE, QUOTED = Value
}
import com.X.visibility.interfaces.push_service.TweetType._

object PushServiceVisibilityLibrary {
  type Type = PushServiceVisibilityRequest => Stitch[PushServiceVisibilityResponse]

  def apply(
    visibilityLibrary: VisibilityLibrary,
    userSource: UserSource,
    userRelationshipSource: UserRelationshipSource,
    stratoClient: StratoClient,
    enableParityTest: Gate[Unit] = Gate.False,
    cachedTweetyPieStoreV2: ReadableStore[Long, TweetyPieResult] = ReadableStore.empty,
    safeCachedTweetyPieStoreV2: ReadableStore[Long, TweetyPieResult] = ReadableStore.empty,
  )(
    implicit statsReceiver: StatsReceiver
  ): Type = {
    val stats = statsReceiver.scope("push_service_vf")
    val candidateTweetCounter = stats.counter("request_cnt")
    val allowedTweetCounter = stats.counter("allow_cnt")
    val droppedTweetCounter = stats.counter("drop_cnt")
    val failedTweetCounter = stats.counter("fail_cnt")
    val authorLabelsEmptyCount = stats.counter("author_labels_empty_cnt")
    val authorLabelsCount = stats.counter("author_labels_cnt")

    val tweetLabelMaps = new StratoTweetLabelMaps(
      SafetyLabelMapSource.fromSafetyLabelMapFetcher(
        PushServiceSafetyLabelMapFetcher(stratoClient, stats)))

    val viewerFeatures = new ViewerFeatures(UserSource.empty, stats)
    val tweetFeatures = new TweetFeatures(tweetLabelMaps, stats)
    val authorFeatures = new AuthorFeatures(userSource, stats)
    val relationshipFeatures = new RelationshipFeatures(UserRelationshipSource.empty, stats)

    val parityTester = new PushServiceVisibilityLibraryParity(
      cachedTweetyPieStoreV2,
      safeCachedTweetyPieStoreV2
    )(statsReceiver)

    def buildFeatureMap(
      request: PushServiceVisibilityRequest,
      tweet: Tweet,
      tweetType: TweetType,
      author: Option[User] = None,
    ): FeatureMap = {
      val authorId = author.map(_.id) orElse getAuthorId(tweet)
      (author.map(authorFeatures.forAuthor(_)) orElse
        getAuthorId(tweet).map(authorFeatures.forAuthorId(_))) match {
        case Some(authorVisibilityFeatures) =>
          visibilityLibrary.featureMapBuilder(
            Seq(
              viewerFeatures.forViewerContext(ViewerContext.fromContextWithViewerIdFallback(None)),
              tweetFeatures.forTweet(tweet),
              authorVisibilityFeatures,
              relationshipFeatures.forAuthorId(authorId.get, None),
              _.withConstantFeature(TweetIsInnerQuotedTweet, tweetType == QUOTED),
              _.withConstantFeature(TweetIsRetweet, request.isRetweet),
              _.withConstantFeature(TweetIsSourceTweet, tweetType == SOURCE)
            )
          )
        case _ =>
          visibilityLibrary.featureMapBuilder(
            Seq(
              viewerFeatures.forViewerContext(ViewerContext.fromContextWithViewerIdFallback(None)),
              tweetFeatures.forTweet(tweet),
              _.withConstantFeature(TweetIsInnerQuotedTweet, tweetType == QUOTED),
              _.withConstantFeature(TweetIsRetweet, request.isRetweet),
              _.withConstantFeature(TweetIsSourceTweet, tweetType == SOURCE)
            )
          )
      }
    }

    def runRuleEngineForTweet(
      request: PushServiceVisibilityRequest,
      tweet: Tweet,
      tweetType: TweetType,
      author: Option[User] = None,
    ): Stitch[VisibilityResult] = {
      val featureMap = buildFeatureMap(request, tweet, tweetType, author)
      val contentId = ContentId.TweetId(tweet.id)
      visibilityLibrary.runRuleEngine(
        contentId,
        featureMap,
        request.viewerContext,
        request.safetyLevel)
    }

    def runRuleEngineForAuthor(
      request: PushServiceVisibilityRequest,
      tweet: Tweet,
      tweetType: TweetType,
      author: Option[User] = None,
    ): Stitch[VisibilityResult] = {
      val featureMap = buildFeatureMap(request, tweet, tweetType, author)
      val authorId = author.map(_.id).getOrElse(getAuthorId(tweet).get)
      val contentId = ContentId.UserId(authorId)
      visibilityLibrary.runRuleEngine(
        contentId,
        featureMap,
        request.viewerContext,
        request.safetyLevel)
    }

    def getAllVisibilityFilters(
      request: PushServiceVisibilityRequest
    ): Stitch[PushServiceVisibilityResponse] = {
      val tweetResult =
        runRuleEngineForTweet(request, request.tweet, ORIGINAL, Some(request.author))
      val authorResult =
        runRuleEngineForAuthor(request, request.tweet, ORIGINAL, Some(request.author))
      val sourceTweetResult = request.sourceTweet
        .map(runRuleEngineForTweet(request, _, SOURCE).map(Some(_))).getOrElse(Stitch.None)
      val quotedTweetResult = request.quotedTweet
        .map(runRuleEngineForTweet(request, _, QUOTED).map(Some(_))).getOrElse(Stitch.None)

      Stitch.join(tweetResult, authorResult, sourceTweetResult, quotedTweetResult).map {
        case (tweetResult, authorResult, sourceTweetResult, quotedTweetResult) =>
          PushServiceVisibilityResponse(
            tweetResult,
            authorResult,
            sourceTweetResult,
            quotedTweetResult)
      }
    }

    { request: PushServiceVisibilityRequest =>
      candidateTweetCounter.incr()

      request.author.labels match {
        case Some(labels) if (!labels._1.isEmpty) => authorLabelsCount.incr()
        case _ => authorLabelsEmptyCount.incr()
      }

      val response = getAllVisibilityFilters(request)
        .onSuccess { response =>
          if (response.shouldAllow) allowedTweetCounter.incr() else droppedTweetCounter.incr()
        }.onFailure { _ => failedTweetCounter.incr() }

      if (enableParityTest()) {
        response.applyEffect { resp => Stitch.async(parityTester.runParityTest(request, resp)) }
      } else {
        response
      }

    }
  }
}
