package com.twitter.follow_recommendations.common.models

trait HasEngagements {

  def engagements: Seq[EngagementType]

}
