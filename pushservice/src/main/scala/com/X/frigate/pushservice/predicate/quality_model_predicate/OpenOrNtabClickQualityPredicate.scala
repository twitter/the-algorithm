package com.X.frigate.pushservice.predicate.quality_model_predicate

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.util.Future

object ExplicitOONCFilterPredicate extends QualityPredicateBase {
  override lazy val name = "open_or_ntab_click_explicit_threshold"

  override lazy val thresholdExtractor = (t: Target) =>
    Future.value(t.params(PushFeatureSwitchParams.QualityPredicateExplicitThresholdParam))

  override def scoreExtractor = (candidate: PushCandidate) =>
    candidate.mrWeightedOpenOrNtabClickRankingProbability
}

object WeightedOpenOrNtabClickQualityPredicate extends QualityPredicateBase {
  override lazy val name = "weighted_open_or_ntab_click_model"

  override lazy val thresholdExtractor = (t: Target) => {
    Future.value(0.0)
  }

  override def scoreExtractor =
    (candidate: PushCandidate) => candidate.mrWeightedOpenOrNtabClickFilteringProbability
}
