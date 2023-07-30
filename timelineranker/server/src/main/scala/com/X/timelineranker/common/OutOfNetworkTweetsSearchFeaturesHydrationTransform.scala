package com.X.timelineranker.common

import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.HydratedCandidatesAndFeaturesEnvelope
import com.X.timelines.earlybird.common.utils.EarlybirdFeaturesHydrator
import com.X.util.Future

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
