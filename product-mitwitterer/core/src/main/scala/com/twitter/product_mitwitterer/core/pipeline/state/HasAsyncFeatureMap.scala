package com.twitter.product_mitwitterer.core.pipeline.state

import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap

trait HasAsyncFeatureMap[State] {
  def asyncFeatureMap: AsyncFeatureMap

  private[core] def addAsyncFeatureMap(newFeatureMap: AsyncFeatureMap): State
}
