package com.twitter.follow_recommendations.flows.content_recommender_flow

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRecommenderFlowFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(
      ContentRecommenderParams.IncludeActivityBasedCandidateSource,
      ContentRecommenderParams.IncludeSocialBasedCandidateSource,
      ContentRecommenderParams.IncludeGeoBasedCandidateSource,
      ContentRecommenderParams.IncludeHomeTimelineTweetRecsCandidateSource,
      ContentRecommenderParams.IncludeSocialProofEnforcedCandidateSource,
      ContentRecommenderParams.EnableRecentFollowingPredicate,
      ContentRecommenderParams.EnableGizmoduckPredicate,
      ContentRecommenderParams.EnableInactivePredicate,
      ContentRecommenderParams.EnableInvalidTargetCandidateRelationshipPredicate,
      ContentRecommenderParams.IncludeNewFollowingNewFollowingExpansionCandidateSource,
      ContentRecommenderParams.IncludeMoreGeoBasedCandidateSource,
      ContentRecommenderParams.TargetEligibility,
      ContentRecommenderParams.GetFollowersFromSgs,
      ContentRecommenderParams.EnableInvalidRelationshipPredicate,
    )

  override val intFSParams: Seq[FSBoundedParam[Int]] =
    Seq(
      ContentRecommenderParams.ResultSizeParam,
      ContentRecommenderParams.BatchSizeParam,
      ContentRecommenderParams.FetchCandidateSourceBudgetInMillisecond,
      ContentRecommenderParams.RecentFollowingPredicateBudgetInMillisecond,
    )

  override val doubleFSParams: Seq[FSBoundedParam[Double]] =
    Seq(
      ContentRecommenderFlowCandidateSourceWeightsParams.ForwardPhoneBookSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.ForwardEmailBookSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.ReversePhoneBookSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.ReverseEmailBookSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.OfflineStrongTiePredictionSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.TriangularLoopsSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.UserUserGraphSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.NewFollowingNewFollowingExpansionSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.NewFollowingSimilarUserSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.RecentEngagementSimilarUserSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.RepeatedProfileVisitsSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.RealGraphOonSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.PopCountrySourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.PopGeohashSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.PopCountryBackfillSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.PPMILocaleFollowSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.TopOrganicFollowsAccountsSourceWeight,
      ContentRecommenderFlowCandidateSourceWeightsParams.CrowdSearchAccountSourceWeight,
    )
}
