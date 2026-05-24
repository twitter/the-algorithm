package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableOnPremRealGraphQueryFeatures
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.recommendations.interaction_graph.on_prem.RealGraphUserFeaturesOnUserClientColumn
import com.twitter.timelines.real_graph.{thriftscala => rg}

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnPremRealGraphQueryFeatureHydrator @Inject() (
  realGraphUserFeaturesClientColumn: RealGraphUserFeaturesOnUserClientColumn)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("OnPremRealGraphFeatures")

  override val features: Set[Feature[_, _]] = Set(RealGraphFeatures)

  override def onlyIf(
    query: PipelineQuery
  ): Boolean = {
    query.params(EnableOnPremRealGraphQueryFeatures)
  }
  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    realGraphUserFeaturesClientColumn.fetcher
      .fetch(query.getRequiredUserId).map { userSession =>
        val realGraphFeaturesMap = userSession.v.flatMap { userSession =>
          userSession.realGraphFeatures.collect {
            case rg.RealGraphFeatures.V1(realGraphFeatures) =>
              val edgeFeatures =
                realGraphFeatures.edgeFeatures ++ realGraphFeatures.oonEdgeFeatures
              edgeFeatures.map { edge => edge.destId -> edge }.toMap
          }
        }

        FeatureMapBuilder().add(RealGraphFeatures, realGraphFeaturesMap).build()
      }
  }
}
