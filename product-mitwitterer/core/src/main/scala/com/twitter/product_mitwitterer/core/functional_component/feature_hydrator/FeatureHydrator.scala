package com.twitter.product_mitwitterer.core.functional_component.feature_hydrator

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.model.common.Component

/** Hydrates a [[com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap]] for a given input */
trait FeatureHydrator[FeatureType <: Feature[_, _]] etwittertends Component {
  def features: Set[FeatureType]
}
