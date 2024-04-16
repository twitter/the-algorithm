package com.tw ter.product_m xer.core.p pel ne.state

 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap

tra  HasAsyncFeatureMap[State] {
  def asyncFeatureMap: AsyncFeatureMap

  pr vate[core] def addAsyncFeatureMap(newFeatureMap: AsyncFeatureMap): State
}
