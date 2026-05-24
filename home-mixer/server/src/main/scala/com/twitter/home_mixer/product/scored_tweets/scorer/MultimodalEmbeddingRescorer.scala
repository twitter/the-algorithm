package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.MultiModalEmbeddingsFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.MultiModalEmbeddingRescorerGammaParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.MultiModalEmbeddingRescorerMinScoreParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.MultiModalEmbeddingRescorerNumCandidatesParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import scala.collection.mutable.ArrayBuffer

object MultimodalEmbeddingRescorer {

  private val statsReceiver = DefaultStatsReceiver.scope("MultimodalEmbeddingRescorer")
  private val rescoredCandidatesStat = statsReceiver.stat("rescored_candidates")
  private val rescoreFactorx100Stat = statsReceiver.stat("rescore_factor_x100")

  private def onlyIf(query: PipelineQuery): Boolean = {
    (query.params(MultiModalEmbeddingRescorerGammaParam) > 0.0) &&
    (query.params(MultiModalEmbeddingRescorerMinScoreParam) < 1.0)
  }

  private def dot(a: Array[Double], b: Array[Double]): Double = {
    a.zip(b).map { case (x, y) => x * y }.sum
  }

  private def getSimilarityScoreFactors(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    gamma: Double,
    minScore: Double
  ): Map[Long, Double] = {
    val embsBuilder = ArrayBuffer.empty[Array[Double]]
    val factorsBuilder = Map.newBuilder[Long, Double]

    candidates.foreach { candidate =>
      val id = candidate.candidate.id
      candidate.features.getOrElse(MultiModalEmbeddingsFeature, None) match {
        case Some(embSeq: Seq[Double]) =>
          val emb = embSeq.toArray
          var similarCount = 0
          var i = 0
          while (i < embsBuilder.length) {
            if (dot(embsBuilder(i), emb) > minScore)
              similarCount += 1
            i += 1
          }
          val factor = 1.0 / (1.0 + gamma * similarCount)
          factorsBuilder += (id -> factor)
          embsBuilder += emb
        case _ =>
      }
    }

    factorsBuilder.result()
  }

  def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Map[Long, Double] = {
    if (onlyIf(query)) {
      // Get the top 100 candidates by score
      val sortedCandidates =
        candidates.sortBy(_.features.getOrElse(ScoreFeature, None).getOrElse(0.0))
      // Get top 100 and rest of candidates
      val numCandidatesToScore = query.params(MultiModalEmbeddingRescorerNumCandidatesParam)
      val topCandidates = sortedCandidates.take(numCandidatesToScore)
      val restCandidates = sortedCandidates.drop(numCandidatesToScore)

      val candidateToFactorMap = getSimilarityScoreFactors(
        topCandidates,
        query.params(MultiModalEmbeddingRescorerGammaParam),
        query.params(MultiModalEmbeddingRescorerMinScoreParam)
      )
      val rescoredCandidates = candidateToFactorMap.filter {
        case (_, factor) => factor != 1.0
      }

      rescoredCandidatesStat.add(rescoredCandidates.size.toFloat)
      rescoreFactorx100Stat.add(rescoredCandidates.values.map(_.toFloat).sum * 100)

      // For the rest of the candidates, set factor to 1.0
      restCandidates.map(candidate => candidate.candidate.id -> 1.0).toMap ++ candidateToFactorMap
    } else candidates.map(candidate => candidate.candidate.id -> 1.0).toMap
  }
}
