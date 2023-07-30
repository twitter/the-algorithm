package com.X.frigate.pushservice.predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams.QualityPredicateIdParam
import com.X.frigate.pushservice.predicate.quality_model_predicate._
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.util.Future

object JointDauAndQualityModelPredicate {

  val name = "JointDauAndQualityModelPredicate"

  def apply()(implicit statsReceiver: StatsReceiver): NamedPredicate[PushCandidate] = {
    val stats = statsReceiver.scope(s"predicate_$name")

    val defaultPred = WeightedOpenOrNtabClickQualityPredicate()
    val qualityPredicateMap = QualityPredicateMap()

    Predicate
      .fromAsync { candidate: PushCandidate =>
        if (!candidate.target.skipModelPredicate) {

          val modelPredicate =
            qualityPredicateMap.getOrElse(
              candidate.target.params(QualityPredicateIdParam),
              defaultPred)

          val modelPredicateResultFut =
            modelPredicate.apply(Seq(candidate)).map(_.headOption.getOrElse(false))

          modelPredicateResultFut
        } else Future.True
      }
      .withStats(stats)
      .withName(name)
  }
}
