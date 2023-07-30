package com.X.product_mixer.component_library.feature_hydrator.query.cr_ml_ranker

import com.X.cr_ml_ranker.{thriftscala => t}
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

object CrMlRankerCommonFeatures extends Feature[PipelineQuery, t.CommonFeatures]
object CrMlRankerRankingConfig extends Feature[PipelineQuery, t.RankingConfig]

private[cr_ml_ranker] class CrMlRankerCommonQueryFeatureHydrator(
  crMlRanker: t.CrMLRanker.MethodPerEndpoint,
  rankingConfigSelector: RankingConfigBuilder)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("CrMlRanker")

  override val features: Set[Feature[_, _]] =
    Set(CrMlRankerCommonFeatures, CrMlRankerRankingConfig)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val rankingConfig = rankingConfigSelector.apply(query)
    Stitch
      .callFuture(
        crMlRanker.getCommonFeatures(
          t.RankingRequestContext(query.getRequiredUserId, rankingConfig))).map { commonFeatures =>
        FeatureMapBuilder()
          .add(CrMlRankerRankingConfig, rankingConfig)
          .add(CrMlRankerCommonFeatures, commonFeatures)
          .build()
      }
  }
}
