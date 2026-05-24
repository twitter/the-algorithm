package com.twitter.product_mixer.core.functional_component.feature_hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.model.common.Component

/** Hydrates a [[com.twitter.product_mixer.core.feature.featuremap.FeatureMap]] for a given input */
trait FeatureHydrator[FeatureType <: Feature[_, _]] extends Component {
  def features: Set[FeatureType]
}
