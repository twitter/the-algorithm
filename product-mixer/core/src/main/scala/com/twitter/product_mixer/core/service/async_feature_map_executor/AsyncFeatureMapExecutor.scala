package com.tw ter.product_m xer.core.serv ce.async_feature_map_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.Executor._
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AsyncFeatureMapExecutor @ nject() (
  overr de val statsRece ver: StatsRece ver)
    extends Executor {

  /**
   * Forces an [[AsyncFeatureMap]] to hydrate and resolve  nto a [[FeatureMap]]
   * conta n ng all [[com.tw ter.product_m xer.core.feature.Feature]]s that are
   * supposed to be hydrated before `stepToHydrateBefore`.
   */
  def arrow(
    stepToHydrateFor: P pel neStep dent f er,
    currentStep: P pel neStep dent f er,
    context: Context
  ): Arrow[AsyncFeatureMap, AsyncFeatureMapExecutorResults] = {
    Arrow
      .map[AsyncFeatureMap, Opt on[St ch[FeatureMap]]](_.hydrate(stepToHydrateFor))
      .andT n(
        Arrow.choose(
          Arrow.Cho ce. fDef nedAt(
            { case So (st chOfFeatureMap) => st chOfFeatureMap },
            // only stat  f t re's so th ng to hydrate
            wrapComponentW hExecutorBookkeep ng(context, currentStep)(
              Arrow
                .flatMap[St ch[FeatureMap], FeatureMap]( dent y)
                .map(featureMap =>
                  AsyncFeatureMapExecutorResults(Map(stepToHydrateFor -> featureMap)))
            )
          ),
          Arrow.Cho ce.ot rw se(Arrow.value(AsyncFeatureMapExecutorResults(Map.empty)))
        )
      )
  }
}

case class AsyncFeatureMapExecutorResults(
  featureMapsByStep: Map[P pel neStep dent f er, FeatureMap])
    extends ExecutorResult {
  def ++(
    asyncFeatureMapExecutorResults: AsyncFeatureMapExecutorResults
  ): AsyncFeatureMapExecutorResults =
    AsyncFeatureMapExecutorResults(
      featureMapsByStep ++ asyncFeatureMapExecutorResults.featureMapsByStep)
}
