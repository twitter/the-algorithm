package com.twitter.product_mitwitterer.core.functional_component.scorer

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun

/** A [[Candidate]] and it's [[FeatureMap]] after being processed by a [[Scorer]] */
case class ScoredCandidateResult[Candidate <: UniversalNoun[Any]](
  candidate: Candidate,
  scorerResult: FeatureMap)
    etwittertends CandidateWithFeatures[Candidate] {
  override val features: FeatureMap = scorerResult
}
