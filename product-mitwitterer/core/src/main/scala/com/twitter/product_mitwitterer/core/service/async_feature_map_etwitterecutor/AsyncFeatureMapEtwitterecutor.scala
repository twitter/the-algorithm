package com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.Etwitterecutor._
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.stitch.Arrow
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class AsyncFeatureMapEtwitterecutor @Inject() (
  override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {

  /**
   * Forces an [[AsyncFeatureMap]] to hydrate and resolve into a [[FeatureMap]]
   * containing all [[com.twitter.product_mitwitterer.core.feature.Feature]]s that are
   * supposed to be hydrated before `stepToHydrateBefore`.
   */
  def arrow(
    stepToHydrateFor: PipelineStepIdentifier,
    currentStep: PipelineStepIdentifier,
    contetwittert: Contetwittert
  ): Arrow[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] = {
    Arrow
      .map[AsyncFeatureMap, Option[Stitch[FeatureMap]]](_.hydrate(stepToHydrateFor))
      .andThen(
        Arrow.choose(
          Arrow.Choice.ifDefinedAt(
            { case Some(stitchOfFeatureMap) => stitchOfFeatureMap },
            // only stat if there's something to hydrate
            wrapComponentWithEtwitterecutorBookkeeping(contetwittert, currentStep)(
              Arrow
                .flatMap[Stitch[FeatureMap], FeatureMap](identity)
                .map(featureMap =>
                  AsyncFeatureMapEtwitterecutorResults(Map(stepToHydrateFor -> featureMap)))
            )
          ),
          Arrow.Choice.otherwise(Arrow.value(AsyncFeatureMapEtwitterecutorResults(Map.empty)))
        )
      )
  }
}

case class AsyncFeatureMapEtwitterecutorResults(
  featureMapsByStep: Map[PipelineStepIdentifier, FeatureMap])
    etwittertends EtwitterecutorResult {
  def ++(
    asyncFeatureMapEtwitterecutorResults: AsyncFeatureMapEtwitterecutorResults
  ): AsyncFeatureMapEtwitterecutorResults =
    AsyncFeatureMapEtwitterecutorResults(
      featureMapsByStep ++ asyncFeatureMapEtwitterecutorResults.featureMapsByStep)
}
