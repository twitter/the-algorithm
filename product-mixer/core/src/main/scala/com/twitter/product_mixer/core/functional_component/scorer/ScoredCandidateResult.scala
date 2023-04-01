package com.twitter.product_mixer.core.functional_component.scorer

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun

/** A [[Candidate]] and it's [[FeatureMap]] after being processed by a [[Scorer]] */
case class ScoredCandidateResult[Candidate <: UniversalNoun[Any]](
  candidate: Candidate,
  scorerResult: FeatureMap)
    extends CandidateWithFeatures[Candidate] {
  override val features: FeatureMap = scorerResult
}
