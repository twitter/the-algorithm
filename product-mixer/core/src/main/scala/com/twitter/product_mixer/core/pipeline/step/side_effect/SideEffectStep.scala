package com.tw ter.product_m xer.core.p pel ne.step.s de_effect

 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.state.HasExecutorResults
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor.P pel neResultS deEffectExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A s de effect step,   takes t   nput l st of s de effects and and executes t m.
 *
 * @param s deEffectExecutor S de Effect Executor
 *
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam Doma nResultType Doma n Marshaller result type
 * @tparam State T  p pel ne state doma n model.
 */
case class S deEffectStep[
  Query <: P pel neQuery,
  Doma nResultType <: HasMarshall ng,
  State <: HasQuery[Query, State] w h HasExecutorResults[State]] @ nject() (
  s deEffectExecutor: P pel neResultS deEffectExecutor)
    extends Step[
      State,
      P pel neStepConf g[Query, Doma nResultType],
      P pel neResultS deEffect. nputs[
        Query,
        Doma nResultType
      ],
      P pel neResultS deEffectExecutor.Result
    ] {
  overr de def  sEmpty(conf g: P pel neStepConf g[Query, Doma nResultType]): Boolean =
    conf g.s deEffects. sEmpty

  overr de def adapt nput(
    state: State,
    conf g: P pel neStepConf g[Query, Doma nResultType]
  ): P pel neResultS deEffect. nputs[Query, Doma nResultType] = {
    val selectorResults = state.executorResultsByP pel neStep
      .getOrElse(
        conf g.selectorStep dent f er,
        throw P pel neFa lure(
           llegalStateFa lure,
          "M ss ng Selector Result  n S de Effect Step")).as nstanceOf[SelectorExecutorResult]

    val doma nMarshallerResult = state.executorResultsByP pel neStep
      .getOrElse(
        conf g.doma nMarshallerStep dent f er,
        throw P pel neFa lure(
           llegalStateFa lure,
          "M ss ng Doma n Marshaller Result  n S de Effect Step")).as nstanceOf[
        Doma nMarshallerExecutor.Result[Doma nResultType]]

    P pel neResultS deEffect. nputs(
      query = state.query,
      selectedCand dates = selectorResults.selectedCand dates,
      rema n ngCand dates = selectorResults.rema n ngCand dates,
      droppedCand dates = selectorResults.droppedCand dates,
      response = doma nMarshallerResult.result
    )
  }

  overr de def arrow(
    conf g: P pel neStepConf g[Query, Doma nResultType],
    context: Executor.Context
  ): Arrow[
    P pel neResultS deEffect. nputs[Query, Doma nResultType],
    P pel neResultS deEffectExecutor.Result
  ] = s deEffectExecutor.arrow(conf g.s deEffects, context)

  overr de def updateState(
    state: State,
    executorResult: P pel neResultS deEffectExecutor.Result,
    conf g: P pel neStepConf g[Query, Doma nResultType]
  ): State = state
}

/**
 * Wrapper case class conta n ng s de effects to be executed and ot r  nformat on needed to execute
 * @param s deEffects T  s de effects to execute.
 * @param selectorStep dent f er T   dent f er of t  selector step  n t  parent
 *                               p pel ne to get select on results from.
 * @param doma nMarshallerStep dent f er T   dent f er of t  doma n marshaller step  n t  parent
 *                                       p pel ne to get doma n marshalled results from.
 *
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam Doma nResultType Doma n Marshaller result type
 */
case class P pel neStepConf g[Query <: P pel neQuery, Doma nResultType <: HasMarshall ng](
  s deEffects: Seq[P pel neResultS deEffect[Query, Doma nResultType]],
  selectorStep dent f er: P pel neStep dent f er,
  doma nMarshallerStep dent f er: P pel neStep dent f er)
