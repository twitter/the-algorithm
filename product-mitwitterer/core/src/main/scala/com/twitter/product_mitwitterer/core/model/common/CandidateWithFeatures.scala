package com.twitter.product_mitwitterer.core.model.common

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap

/** [[Candidate]] and it's FeatureMap */
trait CandidateWithFeatures[+Candidate <: UniversalNoun[Any]] {
  val candidate: Candidate
  val features: FeatureMap
}

object CandidateWithFeatures {
  def unapply[Candidate <: UniversalNoun[Any]](
    candidateWithFeatures: CandidateWithFeatures[Candidate]
  ): Option[(Candidate, FeatureMap)] =
    Some(
      (candidateWithFeatures.candidate, candidateWithFeatures.features)
    )
}
