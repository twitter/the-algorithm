package com.twitter.product_mitwitterer.core.service.candidate_feature_transformer_etwitterecutor

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier

case class CandidateFeatureTransformerEtwitterecutorResult(
  featureMaps: Seq[FeatureMap],
  individualFeatureMaps: Seq[Map[TransformerIdentifier, FeatureMap]])
