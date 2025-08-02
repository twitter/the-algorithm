package com.twitter.product_mitwitterer.core.functional_component.feature_hydrator

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun

case class HydratorCandidateResult[+Candidate <: UniversalNoun[Any]](
  override val candidate: Candidate,
  override val features: FeatureMap)
    etwittertends CandidateWithFeatures[Candidate]
