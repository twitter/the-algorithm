package com.twitter.product_mitwitterer.component_library.scorer.cr_ml_ranker

import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.cr_ml_ranker.CrMlRankerCommonFeatures
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.cr_ml_ranker.CrMlRankerRankingConfig
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.scorer.Scorer
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object CrMlRankerScore etwittertends Feature[TweetCandidate, Double]

/**
 * Scorer that scores tweets using the Content Recommender ML Light Ranker: http://go/cr-ml-ranker
 */
@Singleton
class CrMlRankerScorer @Inject() (crMlRanker: CrMlRankerScoreStitchClient)
    etwittertends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("CrMlRanker")

  override val features: Set[Feature[_, _]] = Set(CrMlRankerScore)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val queryFeatureMap = query.features.getOrElse(FeatureMap.empty)
    val rankingConfig = queryFeatureMap.get(CrMlRankerRankingConfig)
    val commonFeatures = queryFeatureMap.get(CrMlRankerCommonFeatures)
    val userId = query.getRequiredUserId

    val scoresStitch = Stitch.collect(candidates.map { candidateWithFeatures =>
      crMlRanker
        .getScore(userId, candidateWithFeatures.candidate, rankingConfig, commonFeatures).map(
          _.score)
    })
    scoresStitch.map { scores =>
      scores.map { score =>
        FeatureMapBuilder()
          .add(CrMlRankerScore, score)
          .build()
      }
    }
  }
}
