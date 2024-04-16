package com.tw ter.product_m xer.core.p pel ne.step.async_feature_map

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasAsyncFeatureMap
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutorResults
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * Async Feature Hydrator Step,   takes an ex st ng asyn feature map and executes any hydrat on
 * needed before t  next step. T  state object  s respons ble for keep ng t  updated query
 * w h t  updated feature map.
 *
 * @param asyncFeatureMapExecutor Async feature map executor
 *
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam State T  p pel ne state doma n model.
 */
case class AsyncFeatureMapStep[
  Query <: P pel neQuery,
  State <: HasQuery[Query, State] w h HasAsyncFeatureMap[State]] @ nject() (
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor)
    extends Step[
      State,
      AsyncFeatureMapStepConf g,
      AsyncFeatureMap,
      AsyncFeatureMapExecutorResults
    ] {
  overr de def  sEmpty(conf g: AsyncFeatureMapStepConf g): Boolean = false

  overr de def adapt nput(
    state: State,
    conf g: AsyncFeatureMapStepConf g
  ): AsyncFeatureMap = state.asyncFeatureMap

  overr de def arrow(
    conf g: AsyncFeatureMapStepConf g,
    context: Executor.Context
  ): Arrow[AsyncFeatureMap, AsyncFeatureMapExecutorResults] =
    asyncFeatureMapExecutor.arrow(conf g.stepToHydrateFor, conf g.currentStep, context)

  overr de def updateState(
    state: State,
    executorResult: AsyncFeatureMapExecutorResults,
    conf g: AsyncFeatureMapStepConf g
  ): State = {
    val hydratedFeatureMap =
      executorResult.featureMapsByStep.getOrElse(conf g.stepToHydrateFor, FeatureMap.empty)
     f (hydratedFeatureMap. sEmpty) {
      state
    } else {
      val updatedFeatureMap = state.query.features
        .getOrElse(FeatureMap.empty) ++ hydratedFeatureMap
      state.updateQuery(
        state.query
          .w hFeatureMap(updatedFeatureMap).as nstanceOf[Query])
    }
  }
}

case class AsyncFeatureMapStepConf g(
  stepToHydrateFor: P pel neStep dent f er,
  currentStep: P pel neStep dent f er)
