package com.twitter.frigate.pushservice.predicate.quality_model_predicate

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.params.QualityPredicateEnum
import com.twitter.frigate.pushservice.predicate.PredicatesForCandidate
import com.twitter.hermit.predicate.NamedPredicate

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
