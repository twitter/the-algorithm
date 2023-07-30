package com.X.product_mixer.core.functional_component.scorer

import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.feature_hydrator.BaseBulkCandidateFeatureHydrator
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.SupportsConditionally
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

/** Scores the provided `candidates` */
trait Scorer[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]]
    extends BaseBulkCandidateFeatureHydrator[Query, Candidate, Feature[_, _]]
    with SupportsConditionally[Query] {

  /** @see [[ScorerIdentifier]] */
  override val identifier: ScorerIdentifier

  /**
   * Features returned by the Scorer
   */
  def features: Set[Feature[_, _]]

  /**
   * Scores the provided `candidates`
   *
   * @note the returned Seq of [[FeatureMap]] must contain all the input 'candidates'
   * and be in the same order as the input 'candidates'
   **/
  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[Seq[FeatureMap]]
}
