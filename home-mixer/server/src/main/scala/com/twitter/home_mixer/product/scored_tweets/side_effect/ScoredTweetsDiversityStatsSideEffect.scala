package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.SimClustersLogFavBasedTweetFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.simclusters_features.SimclustersFeaturesAdapter.SimclustersSparseTweetEmbeddingsFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.CategoryDiversityRescoringParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.CategoryDiversityKParam
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.ml.api.DataRecord
import scala.jdk.CollectionConverters._

@Singleton
case class ScoredTweetsDiversityStatsSideEffect @Inject() (
  statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[ScoredTweetsQuery, ScoredTweetsResponse] {

  import ScoredTweetsDiversityStatsSideEffect._

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("ScoredTweetsDiversityStats")
  private val baseStatsReceiver = statsReceiver.scope(identifier.toString)
  private val diversityStatsReceiverMap = diversityTopK.map { k =>
    k.toString -> baseStatsReceiver.scope("numUniqCategories@k=" + k.toString)
  }.toMap

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[ScoredTweetsQuery, ScoredTweetsResponse]
  ): Stitch[Unit] = {
    val query = inputs.query
    val selectedCandidates = inputs.selectedCandidates

    if (query.params(CategoryDiversityRescoringParam)) {
      diversityTopK.foreach { topK =>
        getNumUniqueCategoriesStats(
          query,
          selectedCandidates,
          topK
        )
      }
    }

    Stitch.Unit
  }

  private def getNumUniqueCategoriesStats(
    query: ScoredTweetsQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    topK: Int
  ): Unit = {
    val topKSelectedCandidates = selectedCandidates
      .sortBy(_.features.getOrElse(ScoreFeature, None).getOrElse(0.0))
      .take(topK)

    val categories = topKSelectedCandidates.map { candidate =>
      val topKClustersMap =
        Option(
          candidate.features
            .getOrElse(SimClustersLogFavBasedTweetFeature, new DataRecord())
            .getSparseContinuousFeatures
        ).flatMap(mapOpt =>
          Option(mapOpt.get(SimclustersSparseTweetEmbeddingsFeature.getFeatureId)))
      topKClustersMap
        .map(_.asScala.toSeq.sortBy(-_._2).take(query.params(CategoryDiversityKParam)).map(_._1))
        .getOrElse(Seq.empty)
    }
    // assign the num of unique categories to the stats receiver
    val uniqueCategories = categories.flatten.toSet
    diversityStatsReceiverMap(topK.toString).stat("numUniqCategories").add(uniqueCategories.size)
  }
}

object ScoredTweetsDiversityStatsSideEffect {
  private val diversityTopK = Array(5, 15, 30)
}
