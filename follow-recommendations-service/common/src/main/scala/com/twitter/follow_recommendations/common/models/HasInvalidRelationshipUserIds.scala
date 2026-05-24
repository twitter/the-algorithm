package com.twitter.follow_recommendations.common.models

trait HasInvalidRelationshipUserIds {
  // user ids that have invalid relationship with the target user
  def invalidRelationshipUserIds: Option[Set[Long]]
}
