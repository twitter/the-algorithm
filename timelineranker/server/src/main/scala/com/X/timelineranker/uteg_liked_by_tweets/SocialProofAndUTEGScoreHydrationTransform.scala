package com.X.timelineranker.uteg_liked_by_tweets

import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.HydratedCandidatesAndFeaturesEnvelope
import com.X.util.Future

object SocialProofAndUTEGScoreHydrationTransform
    extends FutureArrow[
      HydratedCandidatesAndFeaturesEnvelope,
      HydratedCandidatesAndFeaturesEnvelope
    ] {
  override def apply(
    request: HydratedCandidatesAndFeaturesEnvelope
  ): Future[HydratedCandidatesAndFeaturesEnvelope] = {

    val updatedFeatures = request.features.map {
      case (tweetId, features) =>
        tweetId ->
          features.copy(
            utegSocialProofByType =
              request.candidateEnvelope.utegResults.get(tweetId).map(_.socialProofByType),
            utegScore = request.candidateEnvelope.utegResults.get(tweetId).map(_.score)
          )
    }

    Future.value(request.copy(features = updatedFeatures))
  }
}
