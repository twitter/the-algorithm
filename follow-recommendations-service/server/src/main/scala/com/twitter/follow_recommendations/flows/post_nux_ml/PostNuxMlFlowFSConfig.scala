package com.twitter.follow_recommendations.flows.post_nux_ml

import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker.NoShuffle
import com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker.RandomShuffler
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostNuxMlFlowFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    PostNuxMlParams.OnlineSTPEnabled,
    PostNuxMlParams.SamplingTransformEnabled,
    PostNuxMlParams.Follow2VecLinearRegressionEnabled,
    PostNuxMlParams.UseMlRanker,
    PostNuxMlParams.EnableCandidateParamHydration,
    PostNuxMlParams.EnableInterleaveRanker,
    PostNuxMlParams.EnableAdhocRanker,
    PostNuxMlParams.ExcludeNearZeroCandidates,
    PostNuxMlParams.IncludeRepeatedProfileVisitsCandidateSource,
    PostNuxMlParams.EnableInterestsOptOutPredicate,
    PostNuxMlParams.EnableSGSPredicate,
    PostNuxMlParams.EnableInvalidRelationshipPredicate,
    PostNuxMlParams.EnableRemoveAccountProofTransform,
    PostNuxMlParams.EnablePPMILocaleFollowSourceInPostNux,
    PostNuxMlParams.EnableRealGraphOonV2,
    PostNuxMlParams.GetFollowersFromSgs,
    PostNuxMlRequestBuilderParams.EnableInvalidRelationshipPredicate
  )

  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    PostNuxMlCandidateSourceWeightParams.CandidateWeightCrowdSearch,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightTopOrganicFollow,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightPPMILocaleFollow,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightForwardEmailBook,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightForwardPhoneBook,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightOfflineStrongTiePrediction,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightOnlineStp,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightPopCountry,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightPopGeohash,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightPopGeohashQualityFollow,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightPopGeoBackfill,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightRecentFollowingSimilarUsers,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightRecentEngagementDirectFollowSalsaExpansion,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightRecentEngagementNonDirectFollow,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightRecentEngagementSimilarUsers,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightRepeatedProfileVisits,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightFollow2vecNearestNeighbors,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightReverseEmailBook,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightReversePhoneBook,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightTriangularLoops,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightTwoHopRandomWalk,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightUserUserGraph,
    PostNuxMlCandidateSourceWeightParams.CandidateWeightRealGraphOonV2,
    PostNuxMlParams.TurnoffMLScorerQFThreshold
  )

  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    PostNuxMlParams.MlRankerBudget,
    PostNuxMlRequestBuilderParams.TopicIdFetchBudget,
    PostNuxMlRequestBuilderParams.DismissedIdScanBudget,
    PostNuxMlRequestBuilderParams.WTFImpressionsScanBudget
  )

  override val gatedOverridesMap = Map(
    PostNuxMlFlowFeatureSwitchKeys.EnableRandomDataCollection -> Seq(
      PostNuxMlParams.CandidateShuffler := new RandomShuffler[CandidateUser],
      PostNuxMlParams.LogRandomRankerId := true
    ),
    PostNuxMlFlowFeatureSwitchKeys.EnableNoShuffler -> Seq(
      PostNuxMlParams.CandidateShuffler := new NoShuffle[CandidateUser]
    ),
  )
}
