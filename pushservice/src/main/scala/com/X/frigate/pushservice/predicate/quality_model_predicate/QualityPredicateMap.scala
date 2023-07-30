package com.X.frigate.pushservice.predicate.quality_model_predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.QualityPredicateEnum
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.hermit.predicate.NamedPredicate

object QualityPredicateMap {

  def apply(
  )(
    implicit statsReceiver: StatsReceiver
  ): Map[QualityPredicateEnum.Value, NamedPredicate[PushCandidate]] = {
    Map(
      QualityPredicateEnum.WeightedOpenOrNtabClick -> WeightedOpenOrNtabClickQualityPredicate(),
      QualityPredicateEnum.ExplicitOpenOrNtabClickFilter -> ExplicitOONCFilterPredicate(),
      QualityPredicateEnum.AlwaysTrue -> PredicatesForCandidate.alwaysTruePushCandidatePredicate,
    )
  }
}
