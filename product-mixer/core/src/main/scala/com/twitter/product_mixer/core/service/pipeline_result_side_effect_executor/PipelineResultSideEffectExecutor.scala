package com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.ExecuteSynchronously
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.Fa lOpen
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect. nputs
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor.P pel neResultS deEffectExecutor._
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class P pel neResultS deEffectExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor {
  def arrow[Query <: P pel neQuery, M xerDoma nResultType <: HasMarshall ng](
    s deEffects: Seq[P pel neResultS deEffect[Query, M xerDoma nResultType]],
    context: Executor.Context
  ): Arrow[ nputs[Query, M xerDoma nResultType], P pel neResultS deEffectExecutor.Result] = {

    val  nd v dualArrows: Seq[
      Arrow[ nputs[Query, M xerDoma nResultType], (S deEffect dent f er, S deEffectResultType)]
    ] = s deEffects.map {
      case synchronousS deEffect: ExecuteSynchronously =>
        val fa lsRequest fThrows = {
          wrapComponentW hExecutorBookkeep ng(context, synchronousS deEffect. dent f er)(
            Arrow.flatMap(synchronousS deEffect.apply))
        }
        synchronousS deEffect match {
          case fa lOpen: Fa lOpen =>
            // l ft t  fa lure
            fa lsRequest fThrows.l ftToTry.map(t =>
              (fa lOpen. dent f er, SynchronousS deEffectResult(t)))
          case _ =>
            // don't encapsulate t  fa lure
            fa lsRequest fThrows.map(_ =>
              (synchronousS deEffect. dent f er, SynchronousS deEffectResult(Return.Un )))
        }

      case s deEffect =>
        Arrow
          .async(
            wrapComponentW hExecutorBookkeep ng(context, s deEffect. dent f er)(
              Arrow.flatMap(s deEffect.apply)))
          .andT n(Arrow.value((s deEffect. dent f er, S deEffectResult)))
    }

    val cond  onallyRunArrows = s deEffects.z p( nd v dualArrows).map {
      case (
            s deEffect: Cond  onally[
              P pel neResultS deEffect. nputs[Query, M xerDoma nResultType] @unc cked
            ],
            arrow) =>
        Arrow. felse[
           nputs[Query, M xerDoma nResultType],
          (S deEffect dent f er, S deEffectResultType)](
           nput => s deEffect.only f( nput),
          arrow,
          Arrow.value((s deEffect. dent f er, TurnedOffByCond  onally)))
      case (_, arrow) => arrow
    }

    Arrow
      .collect(cond  onallyRunArrows)
      .map(results => Result(results))
  }
}

object P pel neResultS deEffectExecutor {
  case class Result(s deEffects: Seq[(S deEffect dent f er, S deEffectResultType)])
      extends ExecutorResult

  sealed tra  S deEffectResultType

  /** T  [[P pel neResultS deEffect]] was executed asynchronously  n a f re-and-forget way so no result w ll be ava lable */
  case object S deEffectResult extends S deEffectResultType

  /** T  result of t  [[P pel neResultS deEffect]] that was executed w h [[ExecuteSynchronously]] */
  case class SynchronousS deEffectResult(result: Try[Un ]) extends S deEffectResultType

  /** T  result for w n a [[P pel neResultS deEffect]]  s turned off by [[Cond  onally]]'s [[Cond  onally.only f]] */
  case object TurnedOffByCond  onally extends S deEffectResultType
}
