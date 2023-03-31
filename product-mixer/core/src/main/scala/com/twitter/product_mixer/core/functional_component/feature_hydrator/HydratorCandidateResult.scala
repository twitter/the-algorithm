package com.twitter.product_mixer.core.functional_component.feature_hydrator

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun

case class HydratorCandidateResult[+Candidate <: UniversalNoun[Any]](
  override val candidate: Candidate,
  override val features: FeatureMap)
    extends CandidateWithFeatures[Candidate]
