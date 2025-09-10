package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap

trait WithDefaultFeatureMap {
  // Make sure that default Feature Map has same features as defined in the feature hydrator
  val defaultFeatureMap: FeatureMap
}
