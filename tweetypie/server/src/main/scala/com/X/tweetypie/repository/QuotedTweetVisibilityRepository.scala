package com.X.tweetypie
package repository

import com.X.spam.rtf.thriftscala.{SafetyLevel => ThriftSafetyLevel}
import com.X.stitch.Stitch
import com.X.tweetypie.core._
import com.X.tweetypie.repository.VisibilityResultToFilteredState.toFilteredState
import com.X.visibility.configapi.configs.VisibilityDeciderGates
import com.X.visibility.interfaces.tweets.QuotedTweetVisibilityLibrary
import com.X.visibility.interfaces.tweets.QuotedTweetVisibilityRequest
import com.X.visibility.interfaces.tweets.TweetAndAuthor
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

/**
 * This repository handles visibility filtering of inner quoted tweets
 * based on relationships between the inner and outer tweets. This is
 * additive to independent visibility filtering of the inner tweet.
 */
object QuotedTweetVisibilityRepository {
  type Type = Request => Stitch[Option[FilteredState]]

  case class Request(
    outerTweetId: TweetId,
    outerAuthorId: UserId,
    innerTweetId: TweetId,
    innerAuthorId: UserId,
    viewerId: Option[UserId],
    safetyLevel: ThriftSafetyLevel)

  def apply(
    quotedTweetVisibilityLibrary: QuotedTweetVisibilityLibrary.Type,
    visibilityDeciderGates: VisibilityDeciderGates,
  ): QuotedTweetVisibilityRepository.Type = { request: Request =>
    quotedTweetVisibilityLibrary(
      QuotedTweetVisibilityRequest(
        quotedTweet = TweetAndAuthor(request.innerTweetId, request.innerAuthorId),
        outerTweet = TweetAndAuthor(request.outerTweetId, request.outerAuthorId),
        ViewerContext.fromContextWithViewerIdFallback(request.viewerId),
        safetyLevel = SafetyLevel.fromThrift(request.safetyLevel)
      )
    ).map(visibilityResult =>
      toFilteredState(
        visibilityResult = visibilityResult,
        disableLegacyInterstitialFilteredReason =
          visibilityDeciderGates.disableLegacyInterstitialFilteredReason()))
  }
}
