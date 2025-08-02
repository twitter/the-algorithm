package com.twitter.product_mitwitterer.component_library.feature_hydrator.candidate.qualityfactor_gated

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.Conditionally
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

object QualityFactorGatedCandidateFeatureHydrator {
  val IdentifierPrefitwitter = "QfGated"
}

/**
 * A [[CandidateFeatureHydrator]] with [[Conditionally]] based on a qualityFactor threshold.
 * @param pipelineIdentifier identifier of the pipeline that associated with observed quality factor
 * @param qualityFactorInclusiveThreshold the inclusive threshold of quality factor that value below it results in
 *                                        the underlying hydrator being turned off
 * @param candidateFeatureHydrator the underlying [[CandidateFeatureHydrator]] to run when quality factor value
 *                                 is above the given inclusive threshold
 * @tparam Query The domain model for the query or request
 * @tparam Result The type of the candidates
 */
case class QualityFactorGatedCandidateFeatureHydrator[
  -Query <: PipelineQuery with HasQualityFactorStatus,
  Result <: UniversalNoun[Any]
](
  pipelineIdentifier: ComponentIdentifier,
  qualityFactorInclusiveThreshold: Param[Double],
  candidateFeatureHydrator: CandidateFeatureHydrator[Query, Result])
    etwittertends CandidateFeatureHydrator[Query, Result]
    with Conditionally[Query] {
  import QualityFactorGatedCandidateFeatureHydrator._

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    IdentifierPrefitwitter + candidateFeatureHydrator.identifier.name)

  override val alerts: Seq[Alert] = candidateFeatureHydrator.alerts

  override val features: Set[Feature[_, _]] = candidateFeatureHydrator.features

  override def onlyIf(query: Query): Boolean = Conditionally.and(
    query,
    candidateFeatureHydrator,
    query.getQualityFactorCurrentValue(pipelineIdentifier) >= query.params(
      qualityFactorInclusiveThreshold))

  override def apply(
    query: Query,
    candidate: Result,
    etwitteristingFeatures: FeatureMap
  ): Stitch[FeatureMap] = candidateFeatureHydrator.apply(query, candidate, etwitteristingFeatures)
}
