package com.X.product_mixer.core.service.candidate_feature_transformer_executor

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier

case class CandidateFeatureTransformerExecutorResult(
  featureMaps: Seq[FeatureMap],
  individualFeatureMaps: Seq[Map[TransformerIdentifier, FeatureMap]])
