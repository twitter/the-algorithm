package com.X.visibility.interfaces.tweets

import com.X.decider.Decider
import com.X.stitch.Stitch
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.features.TweetDeleteReason
import com.X.visibility.features.TweetIsInnerQuotedTweet
import com.X.visibility.features.TweetIsRetweet
import com.X.visibility.generators.TombstoneGenerator
import com.X.visibility.models.ContentId.DeleteTweetId
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.TweetDeleteReason.TweetDeleteReason
import com.X.visibility.models.ViewerContext

object DeletedTweetVisibilityLibrary {
  type Type = DeletedTweetVisibilityLibrary.Request => Stitch[VisibilityResult]

  case class Request(
    tweetId: Long,
    safetyLevel: SafetyLevel,
    viewerContext: ViewerContext,
    tweetDeleteReason: TweetDeleteReason,
    isRetweet: Boolean,
    isInnerQuotedTweet: Boolean,
  )

  def apply(
    visibilityLibrary: VisibilityLibrary,
    decider: Decider,
    tombstoneGenerator: TombstoneGenerator,
  ): Type = {
    val vfEngineCounter = visibilityLibrary.statsReceiver.counter("vf_engine_requests")

    (request: Request) => {
      vfEngineCounter.incr()
      val contentId = DeleteTweetId(request.tweetId)
      val language = request.viewerContext.requestLanguageCode.getOrElse("en")

      val featureMap =
        visibilityLibrary.featureMapBuilder(
          Seq(
            _.withConstantFeature(TweetIsInnerQuotedTweet, request.isInnerQuotedTweet),
            _.withConstantFeature(TweetIsRetweet, request.isRetweet),
            _.withConstantFeature(TweetDeleteReason, request.tweetDeleteReason)
          )
        )

      visibilityLibrary
        .runRuleEngine(
          contentId,
          featureMap,
          request.viewerContext,
          request.safetyLevel
        )
        .map(tombstoneGenerator(_, language))
    }
  }
}
