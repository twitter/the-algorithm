package com.twitter.follow_recommendations.flows.content_recommender_flow

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

abstract class ContentRecommenderParams[A](default: A) extends Param[A](default) {
  override val statName: String = "content_recommender/" + this.getClass.getSimpleName
}

object ContentRecommenderParams {

  case object TargetEligibility
      extends FSParam[Boolean](ContentRecommenderFlowFeatureSwitchKeys.TargetUserEligible, true)

  case object ResultSizeParam
      extends FSBoundedParam[Int](ContentRecommenderFlowFeatureSwitchKeys.ResultSize, 15, 1, 500)
  case object BatchSizeParam
      extends FSBoundedParam[Int](ContentRecommenderFlowFeatureSwitchKeys.BatchSize, 15, 1, 500)
  case object RecentFollowingPredicateBudgetInMillisecond
      extends FSBoundedParam[Int](
        ContentRecommenderFlowFeatureSwitchKeys.RecentFollowingPredicateBudgetInMillisecond,
        8,
        1,
        50)
  case object FetchCandidateSourceBudgetInMillisecond
      extends FSBoundedParam[Int](
        ContentRecommenderFlowFeatureSwitchKeys.CandidateGenerationBudgetInMillisecond,
        60,
        1,
        80)
  case object EnableRecentFollowingPredicate
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableRecentFollowingPredicate,
        true)
  case object EnableGizmoduckPredicate
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableGizmoduckPredicate,
        false)
  case object EnableInactivePredicate
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableInactivePredicate,
        false)
  case object EnableInvalidTargetCandidateRelationshipPredicate
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableInvalidTargetCandidateRelationshipPredicate,
        false)
  case object IncludeActivityBasedCandidateSource
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeActivityBasedCandidateSource,
        true)
  case object IncludeSocialBasedCandidateSource
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeSocialBasedCandidateSource,
        true)
  case object IncludeGeoBasedCandidateSource
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeGeoBasedCandidateSource,
        true)
  case object IncludeHomeTimelineTweetRecsCandidateSource
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeHomeTimelineTweetRecsCandidateSource,
        false)
  case object IncludeSocialProofEnforcedCandidateSource
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeSocialProofEnforcedCandidateSource,
        false)
  case object IncludeNewFollowingNewFollowingExpansionCandidateSource
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeNewFollowingNewFollowingExpansionCandidateSource,
        false)

  case object IncludeMoreGeoBasedCandidateSource
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.IncludeMoreGeoBasedCandidateSource,
        false)

  case object GetFollowersFromSgs
      extends FSParam[Boolean](ContentRecommenderFlowFeatureSwitchKeys.GetFollowersFromSgs, false)

  case object EnableInvalidRelationshipPredicate
      extends FSParam[Boolean](
        ContentRecommenderFlowFeatureSwitchKeys.EnableInvalidRelationshipPredicate,
        false)
}
