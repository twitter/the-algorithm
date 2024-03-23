package com.ExTwitter.follow_recommendations.common.models

trait HasByfSeedUserIds {
  def byfSeedUserIds: Option[Seq[Long]]
}
