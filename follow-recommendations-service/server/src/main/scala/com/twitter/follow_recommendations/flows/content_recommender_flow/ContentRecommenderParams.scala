package com.twitter.follow_recommendations.flows.content_recommender_flow

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

abstract class ContentRecommenderParams[A](default: A) etwittertends Param[A](default) {
  override val statName: String = "content_recommender/" + this.getClass.getSimpleName
}

object ContentRecommenderParams {

  case object TargetEligibility
      etwittertends FSParam[Boolean](ContentRecommenderFlowFeatureSwitchKeys.TargetUserEligible, true)

  case object ResultSizeParam
      etwittertends FSBoundedParam[Int](ContentRecommenderFlowFeatureSwitchKeys.ResultSize, 15, 1, 500)
  case object BatchSizeParam
      etwittertends FSBoundedParam[Int](ContentRecommenderFlowFeatureSwitchKeys.BatchSize, 15, 1, 500)
  case object RecentFollowingPredicateBudgetInMillisecond
      etwittertends FSBoundedParam[Int](
        ContentRecommenderFlowFeatureSwitchKeys.RecentFollowingPredicateBudgetInMillisecond,
        8,
        1,
        50)
  case object FetchCandidateSourceBudgetInMillisecond
      etwittertends FSBoundedParam[Int](
        ContentRecommenderFlowFeatureSwitchKeys.CandidateGenerationBudgetInMillisecond,
        60,
        1,
        80)
  case object EnableRecentFollowingPredicate
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableRecentFollowingPredicate,
        true)
  case object EnableGizmoduckPredicate
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableGizmoduckPredicate,
        false)
  case object EnableInactivePredicate
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableInactivePredicate,
        false)
  case object EnableInvalidTargetCandidateRelationshipPredicate
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableInvalidTargetCandidateRelationshipPredicate,
        false)
  case object IncludeActivityBasedCandidateSource
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeActivityBasedCandidateSource,
        true)
  case object IncludeSocialBasedCandidateSource
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeSocialBasedCandidateSource,
        true)
  case object IncludeGeoBasedCandidateSource
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeGeoBasedCandidateSource,
        true)
  case object IncludeHomeTimelineTweetRecsCandidateSource
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeHomeTimelineTweetRecsCandidateSource,
        false)
  case object IncludeSocialProofEnforcedCandidateSource
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeSocialProofEnforcedCandidateSource,
        false)
  case object IncludeNewFollowingNewFollowingEtwitterpansionCandidateSource
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeNewFollowingNewFollowingEtwitterpansionCandidateSource,
        false)

  case object IncludeMoreGeoBasedCandidateSource
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeMoreGeoBasedCandidateSource,
        false)

  case object GetFollowersFromSgs
      etwittertends FSParam[Boolean](ContentRecommenderFlowFeatureSwitchKeys.GetFollowersFromSgs, false)

  case object EnableInvalidRelationshipPredicate
      etwittertends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableInvalidRelationshipPredicate,
        false)
}
