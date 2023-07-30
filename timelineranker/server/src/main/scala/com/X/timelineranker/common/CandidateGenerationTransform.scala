package com.X.timelineranker.common

import com.X.finagle.stats.StatsReceiver
import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.HydratedCandidatesAndFeaturesEnvelope
import com.X.timelineranker.model.CandidateTweet
import com.X.timelineranker.model.CandidateTweetsResult
import com.X.util.Future

class CandidateGenerationTransform(statsReceiver: StatsReceiver)
    extends FutureArrow[HydratedCandidatesAndFeaturesEnvelope, CandidateTweetsResult] {
  private[this] val numCandidateTweetsStat = statsReceiver.stat("numCandidateTweets")
  private[this] val numSourceTweetsStat = statsReceiver.stat("numSourceTweets")

  override def apply(
    candidatesAndFeaturesEnvelope: HydratedCandidatesAndFeaturesEnvelope
  ): Future[CandidateTweetsResult] = {
    val hydratedTweets = candidatesAndFeaturesEnvelope.candidateEnvelope.hydratedTweets.outerTweets

    if (hydratedTweets.nonEmpty) {
      val candidates = hydratedTweets.map { hydratedTweet =>
        CandidateTweet(hydratedTweet, candidatesAndFeaturesEnvelope.features(hydratedTweet.tweetId))
      }
      numCandidateTweetsStat.add(candidates.size)

      val sourceTweets =
        candidatesAndFeaturesEnvelope.candidateEnvelope.sourceHydratedTweets.outerTweets.map {
          hydratedTweet =>
            CandidateTweet(
              hydratedTweet,
              candidatesAndFeaturesEnvelope.features(hydratedTweet.tweetId))
        }
      numSourceTweetsStat.add(sourceTweets.size)

      Future.value(CandidateTweetsResult(candidates, sourceTweets))
    } else {
      Future.value(CandidateTweetsResult.Empty)
    }
  }
}
