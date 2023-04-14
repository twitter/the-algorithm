try {
package com.twitter.follow_recommendations.assembler.models

sealed trait RecommendationOptions

case class UserListOptions(
  userBioEnabled: Boolean,
  userBioTruncated: Boolean,
  userBioMaxLines: Option[Long],
) extends RecommendationOptions

case class CarouselOptions() extends RecommendationOptions

} catch {
  case e: Exception =>
}
