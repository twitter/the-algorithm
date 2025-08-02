package com.twitter.follow_recommendations.common.models

trait HasEtwittercludedUserIds {
  // user ids that are going to be etwittercluded from recommendations
  def etwittercludedUserIds: Seq[Long]
}
