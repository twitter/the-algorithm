package com.twitter.follow_recommendations.flows.post_nux_ml

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.Param

abstract class PostNuxMlCandidateSourceWeightParams[A](default: A) extends Param[A](default) {
  override val statName: String = "post_nux_ml/" + this.getClass.getSimpleName
}

object PostNuxMlCandidateSourceWeightParams {

  case object CandidateWeightCrowdSearch
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightCrowdSearch,
        1.0,
        0.0,
        1000.0
      )

  case object CandidateWeightTopOrganicFollow
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightTopOrganicFollow,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPPMILocaleFollow
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPPMILocaleFollow,
        1.0,
        0.0,
        1000.0
      )

  case object CandidateWeightForwardEmailBook
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightForwardEmailBook,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightForwardPhoneBook
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightForwardPhoneBook,
        1.0,
        0.0,
        1000.0
      )

  case object CandidateWeightOfflineStrongTiePrediction
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightOfflineStrongTiePrediction,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightOnlineStp
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightOnlineStp,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPopCountry
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPopCountry,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPopGeohash
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPopGeohash,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPopGeohashQualityFollow
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPopGeohashQualityFollow,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPopGeoBackfill
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPopGeoBackfill,
        1,
        0.0,
        1000.0
      )
  case object CandidateWeightRecentFollowingSimilarUsers
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRecentFollowingSimilarUsers,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightRecentEngagementDirectFollowSalsaExpansion
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRecentEngagementDirectFollowSalsaExpansion,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightRecentEngagementNonDirectFollow
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRecentEngagementNonDirectFollow,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightRecentEngagementSimilarUsers
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRecentEngagementSimilarUsers,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightRepeatedProfileVisits
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRepeatedProfileVisits,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightFollow2vecNearestNeighbors
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightFollow2vecNearestNeighbors,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightReverseEmailBook
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightReverseEmailBook,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightReversePhoneBook
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightReversePhoneBook,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightTriangularLoops
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightTriangularLoops,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightTwoHopRandomWalk
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightTwoHopRandomWalk,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightUserUserGraph
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightUserUserGraph,
        1.0,
        0.0,
        1000.0
      )

  case object CandidateWeightRealGraphOonV2
      extends FSBoundedParam[Double](
        PostNuxMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRealGraphOonV2,
        1.0,
        0.0,
        2000.0
      )
}
