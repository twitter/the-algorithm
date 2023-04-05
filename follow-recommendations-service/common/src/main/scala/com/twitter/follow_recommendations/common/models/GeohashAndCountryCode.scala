try {
package com.twitter.follow_recommendations.common.models

case class GeohashAndCountryCode(geohash: Option[String], countryCode: Option[String])

} catch {
  case e: Exception =>
}
