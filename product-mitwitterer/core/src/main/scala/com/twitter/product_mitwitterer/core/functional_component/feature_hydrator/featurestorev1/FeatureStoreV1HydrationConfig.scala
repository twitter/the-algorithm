package com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.featurestorev1

import com.twitter.ml.featurestore.lib.EntityId
import com.twitter.ml.featurestore.lib.dynamic.BaseDynamicHydrationConfig
import com.twitter.product_mitwitterer.core.feature.featurestorev1.BaseFeatureStoreV1CandidateFeature
import com.twitter.product_mitwitterer.core.feature.featurestorev1.BaseFeatureStoreV1QueryFeature
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class FeatureStoreV1QueryFeatureHydrationConfig[Query <: PipelineQuery](
  features: Set[BaseFeatureStoreV1QueryFeature[Query, _ <: EntityId, _]])
    etwittertends BaseDynamicHydrationConfig[
      Query,
      BaseFeatureStoreV1QueryFeature[Query, _ <: EntityId, _]
    ](features)

case class FeatureStoreV1CandidateFeatureHydrationConfig[
  Query <: PipelineQuery,
  Input <: UniversalNoun[Any]
](
  features: Set[BaseFeatureStoreV1CandidateFeature[Query, Input, _ <: EntityId, _]])
    etwittertends BaseDynamicHydrationConfig[
      Query,
      BaseFeatureStoreV1CandidateFeature[Query, Input, _ <: EntityId, _]
    ](features)
