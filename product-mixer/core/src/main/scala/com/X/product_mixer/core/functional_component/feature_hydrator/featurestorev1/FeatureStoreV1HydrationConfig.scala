package com.X.product_mixer.core.functional_component.feature_hydrator.featurestorev1

import com.X.ml.featurestore.lib.EntityId
import com.X.ml.featurestore.lib.dynamic.BaseDynamicHydrationConfig
import com.X.product_mixer.core.feature.featurestorev1.BaseFeatureStoreV1CandidateFeature
import com.X.product_mixer.core.feature.featurestorev1.BaseFeatureStoreV1QueryFeature
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.pipeline.PipelineQuery

case class FeatureStoreV1QueryFeatureHydrationConfig[Query <: PipelineQuery](
  features: Set[BaseFeatureStoreV1QueryFeature[Query, _ <: EntityId, _]])
    extends BaseDynamicHydrationConfig[
      Query,
      BaseFeatureStoreV1QueryFeature[Query, _ <: EntityId, _]
    ](features)

case class FeatureStoreV1CandidateFeatureHydrationConfig[
  Query <: PipelineQuery,
  Input <: UniversalNoun[Any]
](
  features: Set[BaseFeatureStoreV1CandidateFeature[Query, Input, _ <: EntityId, _]])
    extends BaseDynamicHydrationConfig[
      Query,
      BaseFeatureStoreV1CandidateFeature[Query, Input, _ <: EntityId, _]
    ](features)
