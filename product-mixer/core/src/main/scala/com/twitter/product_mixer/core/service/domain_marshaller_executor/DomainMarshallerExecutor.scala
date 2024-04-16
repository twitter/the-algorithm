package com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor. nputs
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor.Result
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Executes a [[Doma nMarshaller]].
 *
 * @note T   s a synchronous transform, so   don't observe   d rectly. Fa lures and such
 *       can be observed at t  parent p pel ne.
 */
@S ngleton
class Doma nMarshallerExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor {
  def arrow[Query <: P pel neQuery, Doma nResponseType <: HasMarshall ng](
    marshaller: Doma nMarshaller[Query, Doma nResponseType],
    context: Executor.Context
  ): Arrow[ nputs[Query], Result[Doma nResponseType]] = {
    val arrow = Arrow
      .map[ nputs[Query], Doma nMarshallerExecutor.Result[Doma nResponseType]] {
        case  nputs(query, cand dates) =>
          Doma nMarshallerExecutor.Result(marshaller(query, cand dates))
      }

    wrapComponentW hExecutorBookkeep ng(context, marshaller. dent f er)(arrow)
  }
}

object Doma nMarshallerExecutor {
  case class  nputs[Query <: P pel neQuery](
    query: Query,
    cand datesW hDeta ls: Seq[Cand dateW hDeta ls])
  case class Result[+Doma nResponseType](result: Doma nResponseType) extends ExecutorResult
}
