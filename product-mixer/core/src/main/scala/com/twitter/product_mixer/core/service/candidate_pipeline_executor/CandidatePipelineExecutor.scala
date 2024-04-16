package com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.p pel ne.Cand dateP pel neResults
 mport com.tw ter.product_m xer.core.p pel ne.Fa lOpenPol cy
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel ne
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neResult
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.ut l.logg ng.Logg ng

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cand dateP pel neExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor
    w h Logg ng {

  def arrow[Query <: P pel neQuery](
    cand dateP pel nes: Seq[Cand dateP pel ne[Query]],
    defaultFa lOpenPol cy: Fa lOpenPol cy,
    fa lOpenPol c es: Map[Cand dateP pel ne dent f er, Fa lOpenPol cy],
    qual yFactorObserverByP pel ne: Map[Component dent f er, Qual yFactorObserver],
    context: Executor.Context
  ): Arrow[Cand dateP pel ne. nputs[Query], Cand dateP pel neExecutorResult] = {

    // Get t  `.arrow` of each Cand date P pel ne, and wrap    n a ResultObserver
    val observedArrows: Seq[Arrow[Cand dateP pel ne. nputs[Query], Cand dateP pel neResult]] =
      cand dateP pel nes.map { p pel ne =>
        wrapP pel neW hExecutorBookkeep ng(
          context = context,
          currentComponent dent f er = p pel ne. dent f er,
          qual yFactorObserver = qual yFactorObserverByP pel ne.get(p pel ne. dent f er),
          fa lOpenPol cy = fa lOpenPol c es.getOrElse(p pel ne. dent f er, defaultFa lOpenPol cy)
        )(p pel ne.arrow)
      }

    // Collect t  results from all t  cand date p pel nes toget r
    Arrow.z pW hArg(Arrow.collect(observedArrows)).map {
      case ( nput: Cand dateP pel ne. nputs[Query], results: Seq[Cand dateP pel neResult]) =>
        val cand dateW hDeta ls = results.flatMap(_.result.getOrElse(Seq.empty))
        val prev ousCand dateW hDeta ls =  nput.query.features
          .map(_.getOrElse(Cand dateP pel neResults, Seq.empty))
          .getOrElse(Seq.empty)

        val featureMapW hCand dates = FeatureMapBu lder()
          .add(Cand dateP pel neResults, prev ousCand dateW hDeta ls ++ cand dateW hDeta ls)
          .bu ld()

        //  rge t  query feature hydrator and cand date s ce query features back  n. Wh le t 
        //  s done  nternally  n t  p pel ne,   have to pass   back s nce   don't expose t 
        // updated p pel ne query today.
        val queryFeatureHydratorFeatureMaps =
          results
            .flatMap(result => Seq(result.queryFeatures, result.queryFeaturesPhase2))
            .collect { case So (result) => result.featureMap }
        val asyncFeatureHydratorFeatureMaps =
          results
            .flatMap(_.asyncFeatureHydrat onResults)
            .flatMap(_.featureMapsByStep.values)

        val cand dateS ceFeatureMaps =
          results
            .flatMap(_.cand dateS ceResult)
            .map(_.cand dateS ceFeatureMap)

        val featureMaps =
          (featureMapW hCand dates +: queryFeatureHydratorFeatureMaps) ++ asyncFeatureHydratorFeatureMaps ++ cand dateS ceFeatureMaps
        val  rgedFeatureMap = FeatureMap. rge(featureMaps)
        Cand dateP pel neExecutorResult(
          cand dateP pel neResults = results,
          queryFeatureMap =  rgedFeatureMap)
    }
  }
}
