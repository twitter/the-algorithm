package com.twitter.follow_recommendations.flows.content_recommender_flow

import com.twitter.timelines.configapi.FSBoundedParam

object ContentRecommenderFlowCandidateSourceWeightsParams {
  // Social based
  case object ForwardPhoneBookSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.ForwardPhoneBookSourceWeight,
        1d,
        0d,
        1000d)
  case object ForwardEmailBookSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.ForwardEmailBookSourceWeight,
        1d,
        0d,
        1000d)
  case object ReversePhoneBookSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.ReversePhoneBookSourceWeight,
        1d,
        0d,
        1000d)
  case object ReverseEmailBookSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.ReverseEmailBookSourceWeight,
        1d,
        0d,
        1000d)
  case object OfflineStrongTiePredictionSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.OfflineStrongTiePredictionSourceWeight,
        1d,
        0d,
        1000d)
  case object TriangularLoopsSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.TriangularLoopsSourceWeight,
        1d,
        0d,
        1000d)
  case object UserUserGraphSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.UserUserGraphSourceWeight,
        1d,
        0d,
        1000d)
  case object NewFollowingNewFollowingExpansionSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.NewFollowingNewFollowingExpansionSourceWeight,
        1d,
        0d,
        1000d)
  // Activity based
  case object NewFollowingSimilarUserSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.NewFollowingSimilarUserSourceWeight,
        1d,
        0d,
        1000d)
  case object RecentEngagementSimilarUserSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.RecentEngagementSimilarUserSourceWeight,
        1d,
        0d,
        1000d)
  case object RepeatedProfileVisitsSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.RepeatedProfileVisitsSourceWeight,
        1d,
        0d,
        1000d)
  case object RealGraphOonSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.RealGraphOonSourceWeight,
        1d,
        0d,
        1000d)
  // Geo based
  case object PopCountrySourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.PopCountrySourceWeight,
        1d,
        0d,
        1000d)
  case object PopGeohashSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.PopGeohashSourceWeight,
        1d,
        0d,
        1000d)
  case object PopCountryBackfillSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.PopCountryBackfillSourceWeight,
        1d,
        0d,
        1000d)
  case object PPMILocaleFollowSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.PPMILocaleFollowSourceWeight,
        1d,
        0d,
        1000d)
  case object TopOrganicFollowsAccountsSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.TopOrganicFollowsAccountsSourceWeight,
        1d,
        0d,
        1000d)
  case object CrowdSearchAccountSourceWeight
      extends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.CrowdSearchAccountSourceWeight,
        1d,
        0d,
        1000d)
}
