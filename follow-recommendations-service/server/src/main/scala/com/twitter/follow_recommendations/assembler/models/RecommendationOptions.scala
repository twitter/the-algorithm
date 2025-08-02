package com.twitter.follow_recommendations.assembler.models

sealed trait RecommendationOptions

case class UserListOptions(
  userBioEnabled: Boolean,
  userBioTruncated: Boolean,
  userBioMatwitterLines: Option[Long],
) etwittertends RecommendationOptions

case class CarouselOptions() etwittertends RecommendationOptions
