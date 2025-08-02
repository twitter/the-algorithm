package com.twitter.follow_recommendations.flows.content_recommender_flow

import com.twitter.timelines.configapi.FSBoundedParam

object ContentRecommenderFlowCandidateSourceWeightsParams {
  // Social based
  case object ForwardPhoneBookSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.ForwardPhoneBookSourceWeight,
        1d,
        0d,
        1000d)
  case object ForwardEmailBookSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.ForwardEmailBookSourceWeight,
        1d,
        0d,
        1000d)
  case object ReversePhoneBookSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.ReversePhoneBookSourceWeight,
        1d,
        0d,
        1000d)
  case object ReverseEmailBookSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.ReverseEmailBookSourceWeight,
        1d,
        0d,
        1000d)
  case object OfflineStrongTiePredictionSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.OfflineStrongTiePredictionSourceWeight,
        1d,
        0d,
        1000d)
  case object TriangularLoopsSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.TriangularLoopsSourceWeight,
        1d,
        0d,
        1000d)
  case object UserUserGraphSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.UserUserGraphSourceWeight,
        1d,
        0d,
        1000d)
  case object NewFollowingNewFollowingEtwitterpansionSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.NewFollowingNewFollowingEtwitterpansionSourceWeight,
        1d,
        0d,
        1000d)
  // Activity based
  case object NewFollowingSimilarUserSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.NewFollowingSimilarUserSourceWeight,
        1d,
        0d,
        1000d)
  case object RecentEngagementSimilarUserSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.RecentEngagementSimilarUserSourceWeight,
        1d,
        0d,
        1000d)
  case object RepeatedProfileVisitsSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.RepeatedProfileVisitsSourceWeight,
        1d,
        0d,
        1000d)
  case object RealGraphOonSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.RealGraphOonSourceWeight,
        1d,
        0d,
        1000d)
  // Geo based
  case object PopCountrySourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.PopCountrySourceWeight,
        1d,
        0d,
        1000d)
  case object PopGeohashSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.PopGeohashSourceWeight,
        1d,
        0d,
        1000d)
  case object PopCountryBackfillSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.PopCountryBackfillSourceWeight,
        1d,
        0d,
        1000d)
  case object PPMILocaleFollowSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.PPMILocaleFollowSourceWeight,
        1d,
        0d,
        1000d)
  case object TopOrganicFollowsAccountsSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.TopOrganicFollowsAccountsSourceWeight,
        1d,
        0d,
        1000d)
  case object CrowdSearchAccountSourceWeight
      etwittertends FSBoundedParam[Double](
        ContentRecommenderFlowFeatureSwitchKeys.CrowdSearchAccountSourceWeight,
        1d,
        0d,
        1000d)
}
