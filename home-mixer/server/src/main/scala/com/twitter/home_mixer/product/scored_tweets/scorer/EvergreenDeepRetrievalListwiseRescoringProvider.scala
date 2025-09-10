package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EvergreenDeepRetrievalMaxCountParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableEvergreenDeepRetrievalMaxCountParam
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object EvergreenDeepRetrievalListwiseRescoringProvider
    extends ListwiseRescoringProvider[CandidateWithFeatures[TweetCandidate], hmt.ServedType] {

  override def groupByKey(
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Option[hmt.ServedType] =
    Some(candidate.features.get(ServedTypeFeature))

  override def candidateRescoringFactor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate],
    index: Int
  ): Double = {
    if (query.params(EnableEvergreenDeepRetrievalMaxCountParam)) {
      val servedType = candidate.features.get(ServedTypeFeature)
      val maxCount = query.params(EvergreenDeepRetrievalMaxCountParam)
      if (servedType == hmt.ServedType.ForYouEvergreenDeepRetrievalHome && index >= maxCount)
        0.0001
      else 1.0
    } else 1.0
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Map[Long, Double] = {
    candidates
      .groupBy(groupByKey)
      .flatMap {
        case (_, groupedCandidates) =>
          groupedCandidates.zipWithIndex.map {
            case (candidate, index) =>
              candidate.candidate.id -> candidateRescoringFactor(query, candidate, index)
          }
        case _ => Map.empty
      }
  }
}
