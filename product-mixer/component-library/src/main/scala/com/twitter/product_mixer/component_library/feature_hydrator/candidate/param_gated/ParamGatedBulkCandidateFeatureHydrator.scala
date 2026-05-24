package com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated

import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedBulkCandidateFeatureHydrator.IdentifierPrefix
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

/**
 * A [[BulkCandidateFeatureHydrator]] with [[Conditionally]] based on a [[Param]]
 *
 * @param enabledParam the param to turn this [[BulkCandidateFeatureHydrator]] on and off
 * @param bulkCandidateFeatureHydrator the underlying [[BulkCandidateFeatureHydrator]] to run when `enabledParam` is true
 * @tparam Query The domain model for the query or request
 * @tparam Result The type of the candidates
 */
case class ParamGatedBulkCandidateFeatureHydrator[
  -Query <: PipelineQuery,
  Result <: UniversalNoun[Any]
](
  enabledParam: Param[Boolean],
  bulkCandidateFeatureHydrator: BulkCandidateFeatureHydrator[Query, Result])
    extends BulkCandidateFeatureHydrator[Query, Result]
    with Conditionally[Query] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    IdentifierPrefix + bulkCandidateFeatureHydrator.identifier.name)

  override val alerts: Seq[Alert] = bulkCandidateFeatureHydrator.alerts

  override val features: Set[Feature[_, _]] = bulkCandidateFeatureHydrator.features

  override def onlyIf(query: Query): Boolean =
    Conditionally.and(query, bulkCandidateFeatureHydrator, query.params(enabledParam))

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Result]]
  ): Stitch[Seq[FeatureMap]] = bulkCandidateFeatureHydrator(query, candidates)
}

object ParamGatedBulkCandidateFeatureHydrator {
  val IdentifierPrefix = "ParamGated"
}
