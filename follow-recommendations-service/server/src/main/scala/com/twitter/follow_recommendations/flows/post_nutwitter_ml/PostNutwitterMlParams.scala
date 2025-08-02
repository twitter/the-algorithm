package com.twitter.follow_recommendations.flows.post_nutwitter_ml

import com.twitter.conversions.DurationOps._
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker.CandidateShuffler
import com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker.EtwitterponentialShuffler
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

abstract class PostNutwitterMlParams[A](default: A) etwittertends Param[A](default) {
  override val statName: String = "post_nutwitter_ml/" + this.getClass.getSimpleName
}

object PostNutwitterMlParams {

  // infra params:
  case object FetchCandidateSourceBudget etwittertends PostNutwitterMlParams[Duration](90.millisecond)

  // WTF Impression Store has very high tail latency (p9990 or p9999), but p99 latency is pretty good (~100ms)
  // set the time budget for this step to be 200ms to make the performance of service more predictable
  case object FatigueRankerBudget etwittertends PostNutwitterMlParams[Duration](200.millisecond)

  case object MlRankerBudget
      etwittertends FSBoundedParam[Duration](
        name = PostNutwitterMlFlowFeatureSwitchKeys.MLRankerBudget,
        default = 400.millisecond,
        min = 100.millisecond,
        matwitter = 800.millisecond)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMillis
  }

  // product params:
  case object TargetEligibility etwittertends PostNutwitterMlParams[Boolean](true)

  case object ResultSizeParam etwittertends PostNutwitterMlParams[Int](3)
  case object BatchSizeParam etwittertends PostNutwitterMlParams[Int](12)

  case object CandidateShuffler
      etwittertends PostNutwitterMlParams[CandidateShuffler[CandidateUser]](
        new EtwitterponentialShuffler[CandidateUser])
  case object LogRandomRankerId etwittertends PostNutwitterMlParams[Boolean](false)

  // whether or not to use the ml ranker at all (feature hydration + ranker)
  case object UseMlRanker
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.UseMlRanker, false)

  // whether or not to enable candidate param hydration in postnutwitter_ml_flow
  case object EnableCandidateParamHydration
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EnableCandidateParamHydration, false)

  // Whether or not OnlineSTP candidates are considered in the final pool of candidates.
  // If set to `false`, the candidate source will be removed *after* all other considerations.
  case object OnlineSTPEnabled
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.OnlineSTPEnabled, false)

  // Whether or not the candidates are sampled from a Plackett-Luce model
  case object SamplingTransformEnabled
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.SamplingTransformEnabled, false)

  // Whether or not Follow2Vec candidates are considered in the final pool of candidates.
  // If set to `false`, the candidate source will be removed *after* all other considerations.
  case object Follow2VecLinearRegressionEnabled
      etwittertends FSParam[Boolean](
        PostNutwitterMlFlowFeatureSwitchKeys.Follow2VecLinearRegressionEnabled,
        false)

  // Whether or not to enable AdhocRanker to allow adhoc, non-ML, score modifications.
  case object EnableAdhocRanker
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EnableAdhocRanker, false)

  // Whether the impression-based fatigue ranker is enabled or not.
  case object EnableFatigueRanker
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EnableFatigueRanker, true)

  // whether or not to enable InterleaveRanker for producer-side etwitterperiments.
  case object EnableInterleaveRanker
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EnableInterleaveRanker, false)

  // whether to etwitterclude users in near zero user state
  case object EtwittercludeNearZeroCandidates
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EtwittercludeNearZeroCandidates, false)

  case object EnablePPMILocaleFollowSourceInPostNutwitter
      etwittertends FSParam[Boolean](
        PostNutwitterMlFlowFeatureSwitchKeys.EnablePPMILocaleFollowSourceInPostNutwitter,
        false)

  case object EnableInterestsOptOutPredicate
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EnableInterestsOptOutPredicate, false)

  case object EnableInvalidRelationshipPredicate
      etwittertends FSParam[Boolean](
        PostNutwitterMlFlowFeatureSwitchKeys.EnableInvalidRelationshipPredicate,
        false)

  // Totally disabling SGS predicate need to disable EnableInvalidRelationshipPredicate as well
  case object EnableSGSPredicate
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EnableSGSPredicate, true)

  case object EnableHssPredicate
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EnableHssPredicate, true)

  // Whether or not to include RepeatedProfileVisits as one of the candidate sources in the PostNutwitterMlFlow. If false,
  // RepeatedProfileVisitsSource would not be run for the users in candidate_generation.
  case object IncludeRepeatedProfileVisitsCandidateSource
      etwittertends FSParam[Boolean](
        PostNutwitterMlFlowFeatureSwitchKeys.IncludeRepeatedProfileVisitsCandidateSource,
        false)

  case object EnableRealGraphOonV2
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.EnableRealGraphOonV2, false)

  case object GetFollowersFromSgs
      etwittertends FSParam[Boolean](PostNutwitterMlFlowFeatureSwitchKeys.GetFollowersFromSgs, false)

  case object EnableRemoveAccountProofTransform
      etwittertends FSParam[Boolean](
        PostNutwitterMlFlowFeatureSwitchKeys.EnableRemoveAccountProofTransform,
        false)

  // quality factor threshold to turn off ML ranker completely
  object TurnoffMLScorerQFThreshold
      etwittertends FSBoundedParam[Double](
        name = PostNutwitterMlFlowFeatureSwitchKeys.TurnOffMLScorerQFThreshold,
        default = 0.3,
        min = 0.1,
        matwitter = 1.0)
}
