package com.twitter.product_mixer.core.pipeline.state

import com.twitter.product_mixer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap

trait HasAsyncFeatureMap[State] {
  def asyncFeatureMap: AsyncFeatureMap

  private[core] def addAsyncFeatureMap(newFeatureMap: AsyncFeatureMap): State
}
