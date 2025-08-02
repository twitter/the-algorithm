package com.twitter.follow_recommendations.flows.post_nutwitter_ml

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.Param

abstract class PostNutwitterMlCandidateSourceWeightParams[A](default: A) etwittertends Param[A](default) {
  override val statName: String = "post_nutwitter_ml/" + this.getClass.getSimpleName
}

object PostNutwitterMlCandidateSourceWeightParams {

  case object CandidateWeightCrowdSearch
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightCrowdSearch,
        1.0,
        0.0,
        1000.0
      )

  case object CandidateWeightTopOrganicFollow
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightTopOrganicFollow,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPPMILocaleFollow
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPPMILocaleFollow,
        1.0,
        0.0,
        1000.0
      )

  case object CandidateWeightForwardEmailBook
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightForwardEmailBook,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightForwardPhoneBook
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightForwardPhoneBook,
        1.0,
        0.0,
        1000.0
      )

  case object CandidateWeightOfflineStrongTiePrediction
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightOfflineStrongTiePrediction,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightOnlineStp
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightOnlineStp,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPopCountry
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPopCountry,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPopGeohash
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPopGeohash,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPopGeohashQualityFollow
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPopGeohashQualityFollow,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightPopGeoBackfill
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightPopGeoBackfill,
        1,
        0.0,
        1000.0
      )
  case object CandidateWeightRecentFollowingSimilarUsers
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRecentFollowingSimilarUsers,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightRecentEngagementDirectFollowSalsaEtwitterpansion
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRecentEngagementDirectFollowSalsaEtwitterpansion,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightRecentEngagementNonDirectFollow
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRecentEngagementNonDirectFollow,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightRecentEngagementSimilarUsers
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRecentEngagementSimilarUsers,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightRepeatedProfileVisits
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRepeatedProfileVisits,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightFollow2vecNearestNeighbors
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightFollow2vecNearestNeighbors,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightReverseEmailBook
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightReverseEmailBook,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightReversePhoneBook
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightReversePhoneBook,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightTriangularLoops
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightTriangularLoops,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightTwoHopRandomWalk
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightTwoHopRandomWalk,
        1.0,
        0.0,
        1000.0
      )
  case object CandidateWeightUserUserGraph
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightUserUserGraph,
        1.0,
        0.0,
        1000.0
      )

  case object CandidateWeightRealGraphOonV2
      etwittertends FSBoundedParam[Double](
        PostNutwitterMlFlowCandidateSourceWeightsFeatureSwitchKeys.CandidateWeightRealGraphOonV2,
        1.0,
        0.0,
        2000.0
      )
}
