package com.ExTwitter.follow_recommendations.common.models

trait HasGeohashAndCountryCode {
  def geohashAndCountryCode: Option[GeohashAndCountryCode]
}
