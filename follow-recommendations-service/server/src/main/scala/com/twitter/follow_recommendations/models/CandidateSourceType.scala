package com.twitter.follow_recommendations.models

object CandidateSourceType extends Enumeration {
  type CandidateSourceType = Value
  val Social = Value("social")
  val GeoAndInterests = Value("geo_and_interests")
  val ActivityContextual = Value("activity_contextual")
  val None = Value("none")
}
