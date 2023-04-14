try {
package com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.featurestorev1

import com.twitter.ml.featurestore.lib.EntityId
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featurestorev1.BaseFeatureStoreV1QueryFeature
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.feature_hydrator.featurestorev1.FeatureStoreV1DynamicClientBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.featurestorev1.FeatureStoreV1QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

/**
 * A [[QueryFeatureHydrator]] with [[Conditionally]] based on a [[Param]]
 *
 * @param enabledParam the param to turn this [[QueryFeatureHydrator]] on and off
 * @param queryFeatureHydrator the underlying [[QueryFeatureHydrator]] to run when `enabledParam` is true
 * @tparam Query The domain model for the query or request
 * @tparam Result The type of the candidates
 */
case class ParamGatedFeatureStoreV1QueryFeatureHydrator[
  Query <: PipelineQuery,
  Result <: UniversalNoun[Any]
](
  enabledParam: Param[Boolean],
  queryFeatureHydrator: FeatureStoreV1QueryFeatureHydrator[Query])
    extends FeatureStoreV1QueryFeatureHydrator[Query]
    with Conditionally[Query] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "ParamGated" + queryFeatureHydrator.identifier.name)

  override val alerts: Seq[Alert] = queryFeatureHydrator.alerts

  override val features: Set[BaseFeatureStoreV1QueryFeature[Query, _ <: EntityId, _]] =
    queryFeatureHydrator.features

  override val clientBuilder: FeatureStoreV1DynamicClientBuilder =
    queryFeatureHydrator.clientBuilder
  override def onlyIf(query: Query): Boolean =
    Conditionally.and(query, queryFeatureHydrator, query.params(enabledParam))

  override def hydrate(query: Query): Stitch[FeatureMap] = queryFeatureHydrator.hydrate(query)
}

} catch {
  case e: Exception =>
}
