package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.common.Component

/** Hydrates a [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap]] for a g ven  nput */
tra  FeatureHydrator[FeatureType <: Feature[_, _]] extends Component {
  def features: Set[FeatureType]
}
