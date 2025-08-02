package com.twitter.follow_recommendations.common.models

trait HasCustomInterests {
  def customInterests: Option[Seq[String]]
}

trait HasUttInterests {
  def uttInterestIds: Option[Seq[Long]]
}

trait HasInterestIds etwittertends HasCustomInterests with HasUttInterests {}
