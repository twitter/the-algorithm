package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.simclusters_features.SimclustersFeaturesAdapter.SimclustersSparseTweetEmbeddingsFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.CategoryDiversityRescoringWeightParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.CategoryDiversityKParam
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import scala.jdk.CollectionConverters._

object CategoryDiversityRescoringFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("CategoryDiversityRescoring")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  final val EmptyDataRecord = new DataRecord()

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {

    diversityPenalty(query, candidates).map(score => FeatureMap(ScoreFeature, Some(score)))
  }

  private def diversityPenalty(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Seq[Double] = {
    val n = candidates.length
    val selected = scala.collection.mutable.Set[Int]()
    val newScores = Array.fill(n)(0.0)
    val weight = query.params(CategoryDiversityRescoringWeightParam)
    val k = query.params(CategoryDiversityKParam)

    val topKCategories = getCandidateCategory(k, candidates)
    val clusterCntMap = scala.collection.mutable.Map[String, Int]()

    val candidatesWithIndexWithCategories: Seq[
      (CandidateWithFeatures[TweetCandidate], Int, Seq[String])
    ] = candidates.zipWithIndex.zip(topKCategories).map {
      case ((candidate, index), categories) =>
        (candidate, index, categories)
    }
    for (i <- 0 until n) {
      var maxScore = Double.NegativeInfinity
      var bestCandidateIndex = -1
      var candidateCategories: Seq[String] = Seq.empty

      for ((candidate, index, categories) <- candidatesWithIndexWithCategories) {
        if (!selected.contains(index)) {
          val relevance = candidate.features.getOrElse(ScoreFeature, None).getOrElse(0.0)
          var penalty = 0.0
          var totalCategoryCnt = 0
          
          categories.foreach { category =>
            val cnt = clusterCntMap.getOrElse(category, 0)
            penalty += math.log(cnt + 1) / math.log(2)
          }

          val score = math.max(relevance - weight * penalty, 0.00001)

          if (score > maxScore) {
            maxScore = score
            bestCandidateIndex = index
            candidateCategories = categories
          }
        }
      }
      if (bestCandidateIndex != -1) {
        selected += bestCandidateIndex
        newScores(bestCandidateIndex) = maxScore
        candidateCategories.foreach { category =>
          clusterCntMap.put(category, clusterCntMap.getOrElse(category, 0) + 1)
        }
      }
    }

    newScores.toSeq
  }

  private def getCandidateCategory(
    k: Int,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Seq[String]] = {
    val topKClusters: Seq[Seq[String]] = candidates.map { candidate =>
      val topKClustersMap =
        Option(
          candidate.features
            .getOrElse(SimClustersLogFavBasedTweetFeature, new DataRecord())
            .getSparseContinuousFeatures
        ).flatMap(mapOpt =>
          Option(mapOpt.get(SimclustersSparseTweetEmbeddingsFeature.getFeatureId)))
      topKClustersMap
        .map(_.asScala.toSeq.sortBy(-_._2).take(k).map(_._1))
        .getOrElse(Seq.empty)
    }
    topKClusters
  }

}
