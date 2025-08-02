package com.twitter.follow_recommendations.flows.post_nutwitter_ml

import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker.NoShuffle
import com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker.RandomShuffler
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class PostNutwitterMlFlowFSConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    PostNutwitterMlParams.OnlineSTPEnabled,
    PostNutwitterMlParams.SamplingTransformEnabled,
    PostNutwitterMlParams.Follow2VecLinearRegressionEnabled,
    PostNutwitterMlParams.UseMlRanker,
    PostNutwitterMlParams.EnableCandidateParamHydration,
    PostNutwitterMlParams.EnableInterleaveRanker,
    PostNutwitterMlParams.EnableAdhocRanker,
    PostNutwitterMlParams.EtwittercludeNearZeroCandidates,
    PostNutwitterMlParams.IncludeRepeatedProfileVisitsCandidateSource,
    PostNutwitterMlParams.EnableInterestsOptOutPredicate,
    PostNutwitterMlParams.EnableSGSPredicate,
    PostNutwitterMlParams.EnableInvalidRelationshipPredicate,
    PostNutwitterMlParams.EnableRemoveAccountProofTransform,
    PostNutwitterMlParams.EnablePPMILocaleFollowSourceInPostNutwitter,
    PostNutwitterMlParams.EnableRealGraphOonV2,
    PostNutwitterMlParams.GetFollowersFromSgs,
    PostNutwitterMlRequestBuilderParams.EnableInvalidRelationshipPredicate
  )

  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightCrowdSearch,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightTopOrganicFollow,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightPPMILocaleFollow,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightForwardEmailBook,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightForwardPhoneBook,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightOfflineStrongTiePrediction,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightOnlineStp,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightPopCountry,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightPopGeohash,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightPopGeohashQualityFollow,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightPopGeoBackfill,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightRecentFollowingSimilarUsers,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightRecentEngagementDirectFollowSalsaEtwitterpansion,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightRecentEngagementNonDirectFollow,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightRecentEngagementSimilarUsers,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightRepeatedProfileVisits,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightFollow2vecNearestNeighbors,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightReverseEmailBook,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightReversePhoneBook,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightTriangularLoops,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightTwoHopRandomWalk,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightUserUserGraph,
    PostNutwitterMlCandidateSourceWeightParams.CandidateWeightRealGraphOonV2,
    PostNutwitterMlParams.TurnoffMLScorerQFThreshold
  )

  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    PostNutwitterMlParams.MlRankerBudget,
    PostNutwitterMlRequestBuilderParams.TopicIdFetchBudget,
    PostNutwitterMlRequestBuilderParams.DismissedIdScanBudget,
    PostNutwitterMlRequestBuilderParams.WTFImpressionsScanBudget
  )

  override val gatedOverridesMap = Map(
    PostNutwitterMlFlowFeatureSwitchKeys.EnableRandomDataCollection -> Seq(
      PostNutwitterMlParams.CandidateShuffler := new RandomShuffler[CandidateUser],
      PostNutwitterMlParams.LogRandomRankerId := true
    ),
    PostNutwitterMlFlowFeatureSwitchKeys.EnableNoShuffler -> Seq(
      PostNutwitterMlParams.CandidateShuffler := new NoShuffle[CandidateUser]
    ),
  )
}
