package com.X.product_mixer.core.functional_component.scorer

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun

/** A [[Candidate]] and it's [[FeatureMap]] after being processed by a [[Scorer]] */
case class ScoredCandidateResult[Candidate <: UniversalNoun[Any]](
  candidate: Candidate,
  scorerResult: FeatureMap)
    extends CandidateWithFeatures[Candidate] {
  override val features: FeatureMap = scorerResult
}
