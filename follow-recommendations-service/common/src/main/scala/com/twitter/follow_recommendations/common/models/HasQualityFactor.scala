package com.twitter.follow_recommendations.common.models

trait HasQualityFactor {
  def qualityFactor: Option[Double]
}
