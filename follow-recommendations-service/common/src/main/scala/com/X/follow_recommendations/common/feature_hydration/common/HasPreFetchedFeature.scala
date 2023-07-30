package com.X.follow_recommendations.common.feature_hydration.common

import com.X.follow_recommendations.common.models.HasMutualFollowedUserIds
import com.X.follow_recommendations.common.models.HasWtfImpressions
import com.X.follow_recommendations.common.models.WtfImpression
import com.X.util.Time

trait HasPreFetchedFeature extends HasMutualFollowedUserIds with HasWtfImpressions {

  lazy val followedImpressions: Seq[WtfImpression] = {
    for {
      wtfImprList <- wtfImpressions.toSeq
      wtfImpr <- wtfImprList
      if recentFollowedUserIds.exists(_.contains(wtfImpr.candidateId))
    } yield wtfImpr
  }

  lazy val numFollowedImpressions: Int = followedImpressions.size

  lazy val lastFollowedImpressionDurationMs: Option[Long] = {
    if (followedImpressions.nonEmpty) {
      Some((Time.now - followedImpressions.map(_.latestTime).max).inMillis)
    } else None
  }
}
