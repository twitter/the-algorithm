try {
package com.twitter.follow_recommendations.common.models

trait HasGeohashAndCountryCode {
  def geohashAndCountryCode: Option[GeohashAndCountryCode]
}

} catch {
  case e: Exception =>
}
