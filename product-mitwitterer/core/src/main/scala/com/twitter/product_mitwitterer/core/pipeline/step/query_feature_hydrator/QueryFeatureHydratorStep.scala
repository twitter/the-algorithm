package com.twitter.product_mitwitterer.core.pipeline.step.query_feature_hydrator

import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasAsyncFeatureMap
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A query level feature hydration step, it takes the input list of candidates and the given
 * hydrators and etwitterecutes them. The [[State]] object is responsible for merging the resulting
 * feature maps with the hydrated ones in its updateCandidatesWithFeatures.
 *
 * @param queryFeatureHydratorEtwitterecutor Hydrator Etwitterecutor
 * @tparam Query Type of PipelineQuery domain model
 * @tparam State The pipeline state domain model.
 */
case class QueryFeatureHydratorStep[
  Query <: PipelineQuery,
  State <: HasQuery[Query, State] with HasAsyncFeatureMap[State]] @Inject() (
  queryFeatureHydratorEtwitterecutor: QueryFeatureHydratorEtwitterecutor)
    etwittertends Step[State, QueryFeatureHydratorStepConfig[
      Query
    ], Query, QueryFeatureHydratorEtwitterecutor.Result] {
  override def isEmpty(config: QueryFeatureHydratorStepConfig[Query]): Boolean =
    config.hydrators.isEmpty

  override def adaptInput(state: State, config: QueryFeatureHydratorStepConfig[Query]): Query =
    state.query

  override def arrow(
    config: QueryFeatureHydratorStepConfig[Query],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Query, QueryFeatureHydratorEtwitterecutor.Result] =
    queryFeatureHydratorEtwitterecutor.arrow(
      config.hydrators,
      config.validPipelineStepIdentifiers,
      contetwittert)

  override def updateState(
    state: State,
    etwitterecutorResult: QueryFeatureHydratorEtwitterecutor.Result,
    config: QueryFeatureHydratorStepConfig[Query]
  ): State = {
    val updatedQuery = state.query
      .withFeatureMap(etwitterecutorResult.featureMap).asInstanceOf[Query]
    state
      .updateQuery(updatedQuery).addAsyncFeatureMap(etwitterecutorResult.asyncFeatureMap)
  }
}

case class QueryFeatureHydratorStepConfig[Query <: PipelineQuery](
  hydrators: Seq[BaseQueryFeatureHydrator[Query, _]],
  validPipelineStepIdentifiers: Set[PipelineStepIdentifier])
