try {
package com.twitter.follow_recommendations.common.models

trait HasByfSeedUserIds {
  def byfSeedUserIds: Option[Seq[Long]]
}

} catch {
  case e: Exception =>
}
