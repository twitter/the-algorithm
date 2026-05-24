package com.twitter.product_mixer.core.functional_component.feature_hydrator.featurestorev1

import com.twitter.ml.featurestore.lib.dynamic.BaseDynamicHydrationConfig
import com.twitter.ml.featurestore.lib.dynamic.BaseGatedFeatures
import com.twitter.ml.featurestore.lib.dynamic.DynamicFeatureStoreClient
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait FeatureStoreV1DynamicClientBuilder {
  def build[Query <: PipelineQuery](
    dynamicHydrationConfig: BaseDynamicHydrationConfig[Query, _ <: BaseGatedFeatures[Query]]
  ): DynamicFeatureStoreClient[Query]
}
