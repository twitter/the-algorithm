package com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.side_effect.EtwitterecuteSynchronously
import com.twitter.product_mitwitterer.core.functional_component.side_effect.FailOpen
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect.Inputs
import com.twitter.product_mitwitterer.core.model.common.Conditionally
import com.twitter.product_mitwitterer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor.PipelineResultSideEffectEtwitterecutor._
import com.twitter.stitch.Arrow
import com.twitter.util.Return
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class PipelineResultSideEffectEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {
  def arrow[Query <: PipelineQuery, MitwittererDomainResultType <: HasMarshalling](
    sideEffects: Seq[PipelineResultSideEffect[Query, MitwittererDomainResultType]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Inputs[Query, MitwittererDomainResultType], PipelineResultSideEffectEtwitterecutor.Result] = {

    val individualArrows: Seq[
      Arrow[Inputs[Query, MitwittererDomainResultType], (SideEffectIdentifier, SideEffectResultType)]
    ] = sideEffects.map {
      case synchronousSideEffect: EtwitterecuteSynchronously =>
        val failsRequestIfThrows = {
          wrapComponentWithEtwitterecutorBookkeeping(contetwittert, synchronousSideEffect.identifier)(
            Arrow.flatMap(synchronousSideEffect.apply))
        }
        synchronousSideEffect match {
          case failOpen: FailOpen =>
            // lift the failure
            failsRequestIfThrows.liftToTry.map(t =>
              (failOpen.identifier, SynchronousSideEffectResult(t)))
          case _ =>
            // don't encapsulate the failure
            failsRequestIfThrows.map(_ =>
              (synchronousSideEffect.identifier, SynchronousSideEffectResult(Return.Unit)))
        }

      case sideEffect =>
        Arrow
          .async(
            wrapComponentWithEtwitterecutorBookkeeping(contetwittert, sideEffect.identifier)(
              Arrow.flatMap(sideEffect.apply)))
          .andThen(Arrow.value((sideEffect.identifier, SideEffectResult)))
    }

    val conditionallyRunArrows = sideEffects.zip(individualArrows).map {
      case (
            sideEffect: Conditionally[
              PipelineResultSideEffect.Inputs[Query, MitwittererDomainResultType] @unchecked
            ],
            arrow) =>
        Arrow.ifelse[
          Inputs[Query, MitwittererDomainResultType],
          (SideEffectIdentifier, SideEffectResultType)](
          input => sideEffect.onlyIf(input),
          arrow,
          Arrow.value((sideEffect.identifier, TurnedOffByConditionally)))
      case (_, arrow) => arrow
    }

    Arrow
      .collect(conditionallyRunArrows)
      .map(results => Result(results))
  }
}

object PipelineResultSideEffectEtwitterecutor {
  case class Result(sideEffects: Seq[(SideEffectIdentifier, SideEffectResultType)])
      etwittertends EtwitterecutorResult

  sealed trait SideEffectResultType

  /** The [[PipelineResultSideEffect]] was etwitterecuted asynchronously in a fire-and-forget way so no result will be available */
  case object SideEffectResult etwittertends SideEffectResultType

  /** The result of the [[PipelineResultSideEffect]] that was etwitterecuted with [[EtwitterecuteSynchronously]] */
  case class SynchronousSideEffectResult(result: Try[Unit]) etwittertends SideEffectResultType

  /** The result for when a [[PipelineResultSideEffect]] is turned off by [[Conditionally]]'s [[Conditionally.onlyIf]] */
  case object TurnedOffByConditionally etwittertends SideEffectResultType
}
