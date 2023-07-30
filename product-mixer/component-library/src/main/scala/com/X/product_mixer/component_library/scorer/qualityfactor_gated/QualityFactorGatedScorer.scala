package com.X.product_mixer.component_library.scorer.qualityfactor_gated

import com.X.product_mixer.component_library.scorer.qualityfactor_gated.QualityFactorGatedScorer.IdentifierPrefix
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.common.alert.Alert
import com.X.product_mixer.core.functional_component.scorer.Scorer
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.Conditionally
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.X.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.X.stitch.Stitch
import com.X.timelines.configapi.Param

/**
 * A [[scorer]] with [[Conditionally]] based on quality factor value and threshold
 *
 * @param qualityFactorThreshold quliaty factor threshold that turn off the scorer
 * @param pipelineIdentifier identifier of the pipeline that quality factor is based on
 * @param scorer the underlying [[scorer]] to run when `enabledParam` is true
 * @tparam Query The domain model for the query or request
 * @tparam Result The type of the candidates
 */
case class QualityFactorGatedScorer[
  -Query <: PipelineQuery with HasQualityFactorStatus,
  Result <: UniversalNoun[Any]
](
  pipelineIdentifier: ComponentIdentifier,
  qualityFactorThresholdParam: Param[Double],
  scorer: Scorer[Query, Result])
    extends Scorer[Query, Result]
    with Conditionally[Query] {

  override val identifier: ScorerIdentifier = ScorerIdentifier(
    IdentifierPrefix + scorer.identifier.name)

  override val alerts: Seq[Alert] = scorer.alerts

  override val features: Set[Feature[_, _]] = scorer.features

  override def onlyIf(query: Query): Boolean =
    Conditionally.and(
      query,
      scorer,
      query.getQualityFactorCurrentValue(pipelineIdentifier) >= query.params(
        qualityFactorThresholdParam))

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Result]]
  ): Stitch[Seq[FeatureMap]] = scorer(query, candidates)
}

object QualityFactorGatedScorer {
  val IdentifierPrefix = "QualityFactorGated"
}
