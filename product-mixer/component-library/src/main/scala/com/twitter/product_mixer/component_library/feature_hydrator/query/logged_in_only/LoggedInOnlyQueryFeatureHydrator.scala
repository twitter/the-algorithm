package com.twitter.product_mixer.component_library.feature_hydrator.query.logged_in_only

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * A [[QueryFeatureHydrator]] with [[Conditionally]] to run only for logged in users
 *
 * @param queryFeatureHydrator the underlying [[QueryFeatureHydrator]] to run when query.isLoggedOut is false
 * @tparam Query The domain model for the query or request
 * @tparam Result The type of the candidates
 */
case class LoggedInOnlyQueryFeatureHydrator[-Query <: PipelineQuery, Result <: UniversalNoun[Any]](
  queryFeatureHydrator: QueryFeatureHydrator[Query])
    extends QueryFeatureHydrator[Query]
    with Conditionally[Query] {
  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "LoggedInOnly" + queryFeatureHydrator.identifier.name)
  override val alerts: Seq[Alert] = queryFeatureHydrator.alerts
  override val features: Set[Feature[_, _]] = queryFeatureHydrator.features
  override def onlyIf(query: Query): Boolean =
    Conditionally.and(query, queryFeatureHydrator, !query.isLoggedOut)
  override def hydrate(query: Query): Stitch[FeatureMap] = queryFeatureHydrator.hydrate(query)
}
