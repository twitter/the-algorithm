package com.X.follow_recommendations.common.models

trait HasByfSeedUserIds {
  def byfSeedUserIds: Option[Seq[Long]]
}
