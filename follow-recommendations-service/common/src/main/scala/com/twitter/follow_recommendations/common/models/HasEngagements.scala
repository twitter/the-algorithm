try {
package com.twitter.follow_recommendations.common.models

trait HasEngagements {

  def engagements: Seq[EngagementType]

}

} catch {
  case e: Exception =>
}
