package com.twitter.follow_recommendations.common.feature_hydration.adapters

import com.twitter.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.ml.api.Feature.Continuous
import com.twitter.ml.api.util.FDsl._
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.IRecordOneToOneAdapter
import com.twitter.util.Time

/**
 * This adapter mimics UserRecentWTFImpressionsAndFollowsAdapter (for user) and
 * RecentWTFImpressionsFeatureAdapter (for candidate) for extracting recent impression
 * and follow features. This adapter extracts user, candidate, and pair-wise features.
 */
object PreFetchedFeatureAdapter
    extends IRecordOneToOneAdapter[
      (HasPreFetchedFeature, CandidateUser)
    ] {

  // impression features
  val USER_NUM_RECENT_IMPRESSIONS: Continuous = new Continuous(
    "user.prefetch.num_recent_impressions"
  )
  val USER_LAST_IMPRESSION_DURATION: Continuous = new Continuous(
    "user.prefetch.last_impression_duration"
  )
  val CANDIDATE_NUM_RECENT_IMPRESSIONS: Continuous = new Continuous(
    "user-candidate.prefetch.num_recent_impressions"
  )
  val CANDIDATE_LAST_IMPRESSION_DURATION: Continuous = new Continuous(
    "user-candidate.prefetch.last_impression_duration"
  )
  // follow features
  val USER_NUM_RECENT_FOLLOWERS: Continuous = new Continuous(
    "user.prefetch.num_recent_followers"
  )
  val USER_NUM_RECENT_FOLLOWED_BY: Continuous = new Continuous(
    "user.prefetch.num_recent_followed_by"
  )
  val USER_NUM_RECENT_MUTUAL_FOLLOWS: Continuous = new Continuous(
    "user.prefetch.num_recent_mutual_follows"
  )
  // impression + follow features
  val USER_NUM_RECENT_FOLLOWED_IMPRESSIONS: Continuous = new Continuous(
    "user.prefetch.num_recent_followed_impression"
  )
  val USER_LAST_FOLLOWED_IMPRESSION_DURATION: Continuous = new Continuous(
    "user.prefetch.last_followed_impression_duration"
  )

  override def adaptToDataRecord(
    record: (HasPreFetchedFeature, CandidateUser)
  ): DataRecord = {
    val (target, candidate) = record
    val dr = new DataRecord()
    val t = Time.now
    // set impression features for user, optionally for candidate
    dr.setFeatureValue(USER_NUM_RECENT_IMPRESSIONS, target.numWtfImpressions.toDouble)
    dr.setFeatureValue(
      USER_LAST_IMPRESSION_DURATION,
      (t - target.latestImpressionTime).inMillis.toDouble)
    target.getCandidateImpressionCounts(candidate.id).foreach { counts =>
      dr.setFeatureValue(CANDIDATE_NUM_RECENT_IMPRESSIONS, counts.toDouble)
    }
    target.getCandidateLatestTime(candidate.id).foreach { latestTime: Time =>
      dr.setFeatureValue(CANDIDATE_LAST_IMPRESSION_DURATION, (t - latestTime).inMillis.toDouble)
    }
    // set recent follow features for user
    dr.setFeatureValue(USER_NUM_RECENT_FOLLOWERS, target.numRecentFollowedUserIds.toDouble)
    dr.setFeatureValue(USER_NUM_RECENT_FOLLOWED_BY, target.numRecentFollowedByUserIds.toDouble)
    dr.setFeatureValue(USER_NUM_RECENT_MUTUAL_FOLLOWS, target.numRecentMutualFollows.toDouble)
    dr.setFeatureValue(USER_NUM_RECENT_FOLLOWED_IMPRESSIONS, target.numFollowedImpressions.toDouble)
    dr.setFeatureValue(
      USER_LAST_FOLLOWED_IMPRESSION_DURATION,
      target.lastFollowedImpressionDurationMs.getOrElse(Long.MaxValue).toDouble)
    dr
  }
  override def getFeatureContext: FeatureContext = new FeatureContext(
    USER_NUM_RECENT_IMPRESSIONS,
    USER_LAST_IMPRESSION_DURATION,
    CANDIDATE_NUM_RECENT_IMPRESSIONS,
    CANDIDATE_LAST_IMPRESSION_DURATION,
    USER_NUM_RECENT_FOLLOWERS,
    USER_NUM_RECENT_FOLLOWED_BY,
    USER_NUM_RECENT_MUTUAL_FOLLOWS,
    USER_NUM_RECENT_FOLLOWED_IMPRESSIONS,
    USER_LAST_FOLLOWED_IMPRESSION_DURATION,
  )
}
