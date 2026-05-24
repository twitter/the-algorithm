package com.twitter.home_mixer.functional_component.feature_hydrator

import breeze.linalg._
import breeze.numerics.sqrt
//import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinEmbeddingsFeatures.TwhinTweetEmbeddingsFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.TransformerEmbeddingsFeatures.PostTransformerEmbeddingsJointBlueFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.TwhinDiversityRescoringWeightParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.TwhinDiversityRescoringRatioParam
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import scala.jdk.CollectionConverters.collectionAsScalaIterableConverter

object DiversityRescoringFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("DiversityRescoring")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  final val EmptyDataRecord = new DataRecord()

  private val embeddingsSize = 128

  private val defaultEmbeddings = Seq.fill(embeddingsSize)(0.0)

  private val defaultDenseVector =
    DenseVector(Array.fill(embeddingsSize)(1.0)) / sqrt(embeddingsSize)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val embeddings = candidates.map { candidate =>
      val embeddingsTensor =
        Option(
          candidate.features
            .getOrElse(TransformerPostEmbeddingJointBlueFeature, EmptyDataRecord)
            .getTensors)
          .flatMap(tensorsOpt =>
            Option(tensorsOpt.get(PostTransformerEmbeddingsJointBlueFeature.getFeatureId)))
      embeddingsTensor
        .map(_.getFloatTensor.floats.asScala.map(_.doubleValue).toSeq)
        .getOrElse(defaultEmbeddings)
    }

    val denseEmbeddingsNormalized = embeddings.map { seq =>
      val denseVector = DenseVector(seq.toArray)
      val normVal = norm(denseVector)
      if (normVal != 0)
        denseVector / normVal
      else defaultDenseVector
    }

    val distanceMatrix =
      DenseMatrix.zeros[Double](denseEmbeddingsNormalized.length, denseEmbeddingsNormalized.length)

    for (i <- denseEmbeddingsNormalized.indices; j <- denseEmbeddingsNormalized.indices) {
      distanceMatrix(i, j) = norm(denseEmbeddingsNormalized(i) - denseEmbeddingsNormalized(j))
    }
    mmr(query, candidates, distanceMatrix).map(score => FeatureMap(ScoreFeature, Some(score)))
  }

  def mmr(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    distanceMatrix: DenseMatrix[Double]
  ): Seq[Double] = {
    val n = candidates.length
    val diversityRatio = query.params(TwhinDiversityRescoringRatioParam)
    val diversityWeight = query.params(TwhinDiversityRescoringWeightParam)
    val selected = scala.collection.mutable.Set[Int]()
    val newScores = Array.fill(n)(0.0)

    val candidatesWithIndex = candidates.zipWithIndex
    for (i <- 0 until n) {
      var maxScore = Double.NegativeInfinity
      var bestCandidateIndex = -1

      for ((candidate, index) <- candidatesWithIndex) {
        if (!selected.contains(index)) {
          val relevance = candidate.features.getOrElse(ScoreFeature, None).getOrElse(0.0)
          val minDistance = {
            if (selected.isEmpty || selected.size < (1 - diversityRatio) * n) None
            else {
              selected
                .map(j => distanceMatrix(index, j))
                .reduceOption { (a, b) =>
                  if (a < b) a else b
                }
            }
          }
          val score = relevance + diversityWeight * minDistance.getOrElse(2.0)
          if (score > maxScore) {
            maxScore = score
            bestCandidateIndex = index
          }
        }
      }

      if (bestCandidateIndex != -1) {
        selected += bestCandidateIndex
        newScores(bestCandidateIndex) = maxScore
      }
    }

    newScores.toSeq
  }

}
