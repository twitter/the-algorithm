package com.X.follow_recommendations.common.models

trait HasGeohashAndCountryCode {
  def geohashAndCountryCode: Option[GeohashAndCountryCode]
}
