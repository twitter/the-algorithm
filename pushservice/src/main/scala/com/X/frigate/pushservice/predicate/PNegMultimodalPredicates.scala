package com.X.frigate.pushservice.predicate

import com.X.abuse.detection.scoring.thriftscala.Model
import com.X.abuse.detection.scoring.thriftscala.TweetScoringRequest
import com.X.abuse.detection.scoring.thriftscala.TweetScoringResponse
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.TweetCandidate
import com.X.frigate.common.rec_types.RecTypes
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.params.PushParams
import com.X.frigate.pushservice.util.CandidateUtil
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.storehaus.ReadableStore
import com.X.util.Future

object PNegMultimodalPredicates {

  def healthSignalScorePNegMultimodalPredicate(
    tweetHealthScoreStore: ReadableStore[TweetScoringRequest, TweetScoringResponse]
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    val name = "pneg_multimodal_predicate"
    val statsScope = stats.scope(name)
    val oonCandidatesCounter = statsScope.counter("oon_candidates")
    val nonEmptyModelScoreCounter = statsScope.counter("non_empty_model_score")
    val bucketedCounter = statsScope.counter("bucketed_oon_candidates")
    val filteredCounter = statsScope.counter("filtered_oon_candidates")

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate =>
        val target = candidate.target
        val crt = candidate.commonRecType
        val isOonCandidate = RecTypes.isOutOfNetworkTweetRecType(crt) ||
          RecTypes.outOfNetworkTopicTweetTypes.contains(crt)

        lazy val enablePNegMultimodalPredicateParam =
          target.params(PushFeatureSwitchParams.EnablePNegMultimodalPredicateParam)
        lazy val pNegMultimodalPredicateModelThresholdParam =
          target.params(PushFeatureSwitchParams.PNegMultimodalPredicateModelThresholdParam)
        lazy val pNegMultimodalPredicateBucketThresholdParam =
          target.params(PushFeatureSwitchParams.PNegMultimodalPredicateBucketThresholdParam)
        val pNegMultimodalEnabledForF1Tweets =
          target.params(PushParams.EnablePnegMultimodalPredictionForF1Tweets)

        if (CandidateUtil.shouldApplyHealthQualityFilters(
            candidate) && (isOonCandidate || pNegMultimodalEnabledForF1Tweets) && enablePNegMultimodalPredicateParam) {

          val pNegMultimodalRequest = TweetScoringRequest(candidate.tweetId, Model.PNegMultimodal)
          tweetHealthScoreStore.get(pNegMultimodalRequest).map {
            case Some(tweetScoringResponse) =>
              nonEmptyModelScoreCounter.incr()

              val pNegMultimodalScore = 1.0 - tweetScoringResponse.score

              candidate
                .cacheExternalScore("PNegMultimodalScore", Future.value(Some(pNegMultimodalScore)))

              if (isOonCandidate) {
                oonCandidatesCounter.incr()

                if (pNegMultimodalScore > pNegMultimodalPredicateBucketThresholdParam) {
                  bucketedCounter.incr()
                  if (pNegMultimodalScore > pNegMultimodalPredicateModelThresholdParam) {
                    filteredCounter.incr()
                    false
                  } else true
                } else true
              } else {
                true
              }
            case _ => true
          }
        } else {
          Future.True
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }
}
