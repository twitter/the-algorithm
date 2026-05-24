package com.twitter.timelineranker.common

import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.HydratedCandidatesAndFeaturesEnvelope
import com.twitter.timelines.earlybird.common.utils.EarlybirdFeaturesHydrator
import com.twitter.util.Future

object OutOfNetworkTweetsSearchFeaturesHydrationTransform
    extends FutureArrow[
      HydratedCandidatesAndFeaturesEnvelope,
      HydratedCandidatesAndFeaturesEnvelope
    ] {
  override def apply(
    request: HydratedCandidatesAndFeaturesEnvelope
  ): Future[HydratedCandidatesAndFeaturesEnvelope] = {
    val featuresByTweetId = EarlybirdFeaturesHydrator.hydrate(
      searcherUserId = request.candidateEnvelope.query.userId,
      searcherProfileInfo = request.userProfileInfo,
      followedUserIds = Seq.empty,
      mutuallyFollowingUserIds = Set.empty,
      userLanguages = request.userLanguages,
      uiLanguageCode = request.candidateEnvelope.query.deviceContext.flatMap(_.languageCode),
      searchResults = request.candidateEnvelope.searchResults,
      sourceTweetSearchResults = Seq.empty,
      tweets = request.candidateEnvelope.hydratedTweets.outerTweets,
      sourceTweets = Seq.empty
    )

    Future.value(request.copy(features = featuresByTweetId))
  }
}
