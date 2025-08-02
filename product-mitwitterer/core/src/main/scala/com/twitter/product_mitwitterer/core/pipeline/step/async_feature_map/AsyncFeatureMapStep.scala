package com.twitter.product_mitwitterer.core.pipeline.step.async_feature_map

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasAsyncFeatureMap
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutorResults
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * Async Feature Hydrator Step, it takes an etwitteristing asyn feature map and etwitterecutes any hydration
 * needed before the netwittert step. The state object is responsible for keeping the updated query
 * with the updated feature map.
 *
 * @param asyncFeatureMapEtwitterecutor Async feature map etwitterecutor
 *
 * @tparam Query Type of PipelineQuery domain model
 * @tparam State The pipeline state domain model.
 */
case class AsyncFeatureMapStep[
  Query <: PipelineQuery,
  State <: HasQuery[Query, State] with HasAsyncFeatureMap[State]] @Inject() (
  asyncFeatureMapEtwitterecutor: AsyncFeatureMapEtwitterecutor)
    etwittertends Step[
      State,
      AsyncFeatureMapStepConfig,
      AsyncFeatureMap,
      AsyncFeatureMapEtwitterecutorResults
    ] {
  override def isEmpty(config: AsyncFeatureMapStepConfig): Boolean = false

  override def adaptInput(
    state: State,
    config: AsyncFeatureMapStepConfig
  ): AsyncFeatureMap = state.asyncFeatureMap

  override def arrow(
    config: AsyncFeatureMapStepConfig,
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] =
    asyncFeatureMapEtwitterecutor.arrow(config.stepToHydrateFor, config.currentStep, contetwittert)

  override def updateState(
    state: State,
    etwitterecutorResult: AsyncFeatureMapEtwitterecutorResults,
    config: AsyncFeatureMapStepConfig
  ): State = {
    val hydratedFeatureMap =
      etwitterecutorResult.featureMapsByStep.getOrElse(config.stepToHydrateFor, FeatureMap.empty)
    if (hydratedFeatureMap.isEmpty) {
      state
    } else {
      val updatedFeatureMap = state.query.features
        .getOrElse(FeatureMap.empty) ++ hydratedFeatureMap
      state.updateQuery(
        state.query
          .withFeatureMap(updatedFeatureMap).asInstanceOf[Query])
    }
  }
}

case class AsyncFeatureMapStepConfig(
  stepToHydrateFor: PipelineStepIdentifier,
  currentStep: PipelineStepIdentifier)
