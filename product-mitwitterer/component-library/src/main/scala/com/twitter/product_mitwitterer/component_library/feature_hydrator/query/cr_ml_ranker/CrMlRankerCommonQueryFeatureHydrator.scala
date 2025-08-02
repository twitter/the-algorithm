package com.twitter.product_mitwitterer.component_library.feature_hydrator.query.cr_ml_ranker

import com.twitter.cr_ml_ranker.{thriftscala => t}
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object CrMlRankerCommonFeatures etwittertends Feature[PipelineQuery, t.CommonFeatures]
object CrMlRankerRankingConfig etwittertends Feature[PipelineQuery, t.RankingConfig]

private[cr_ml_ranker] class CrMlRankerCommonQueryFeatureHydrator(
  crMlRanker: t.CrMLRanker.MethodPerEndpoint,
  rankingConfigSelector: RankingConfigBuilder)
    etwittertends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("CrMlRanker")

  override val features: Set[Feature[_, _]] =
    Set(CrMlRankerCommonFeatures, CrMlRankerRankingConfig)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val rankingConfig = rankingConfigSelector.apply(query)
    Stitch
      .callFuture(
        crMlRanker.getCommonFeatures(
          t.RankingRequestContetwittert(query.getRequiredUserId, rankingConfig))).map { commonFeatures =>
        FeatureMapBuilder()
          .add(CrMlRankerRankingConfig, rankingConfig)
          .add(CrMlRankerCommonFeatures, commonFeatures)
          .build()
      }
  }
}
