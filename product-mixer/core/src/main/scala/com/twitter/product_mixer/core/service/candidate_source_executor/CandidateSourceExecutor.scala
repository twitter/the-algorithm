package com.tw ter.product_m xer.core.serv ce.cand date_s ce_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.BaseCand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateS cePos  on
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateS ces
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Execut onFa led
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.UnexpectedCand dateResult
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_transfor r_executor.Cand dateFeatureTransfor rExecutor
 mport com.tw ter.product_m xer.core.serv ce.transfor r_executor.PerCand dateTransfor rExecutor
 mport com.tw ter.product_m xer.core.serv ce.transfor r_executor.Transfor rExecutor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on. mmutable.L stSet

/**
 * [[Cand dateS ceExecutor]]:
 *   - Executes a [[BaseCand dateS ce]], us ng a [[BaseCand dateP pel neQueryTransfor r]] and a [[Cand dateP pel neResultsTransfor r]]
 *   -  n parallel, uses a [[Cand dateFeatureTransfor r]] to opt onally extract [[com.tw ter.product_m xer.core.feature.Feature]]s from t  result
 *   - Handles [[UnexpectedCand dateResult]] [[P pel neFa lure]]s returned from [[Cand dateP pel neResultsTransfor r]] fa lures by remov ng those cand dates from t  result
 */
@S ngleton
class Cand dateS ceExecutor @ nject() (
  overr de val statsRece ver: StatsRece ver,
  cand dateFeatureTransfor rExecutor: Cand dateFeatureTransfor rExecutor,
  transfor rExecutor: Transfor rExecutor,
  perCand dateTransfor rExecutor: PerCand dateTransfor rExecutor)
    extends Executor
    w h Logg ng {

  def arrow[
    Query <: P pel neQuery,
    Cand dateS ceQuery,
    Cand dateS ceResult,
    Cand date <: Un versalNoun[Any]
  ](
    cand dateS ce: BaseCand dateS ce[Cand dateS ceQuery, Cand dateS ceResult],
    queryTransfor r: BaseCand dateP pel neQueryTransfor r[
      Query,
      Cand dateS ceQuery
    ],
    resultTransfor r: Cand dateP pel neResultsTransfor r[Cand dateS ceResult, Cand date],
    resultFeaturesTransfor rs: Seq[Cand dateFeatureTransfor r[Cand dateS ceResult]],
    context: Executor.Context
  ): Arrow[Query, Cand dateS ceExecutorResult[Cand date]] = {

    val cand dateS ceArrow: Arrow[Cand dateS ceQuery, Cand datesW hS ceFeatures[
      Cand dateS ceResult
    ]] =
      cand dateS ce match {
        case regularCand dateS ce: Cand dateS ce[Cand dateS ceQuery, Cand dateS ceResult] =>
          Arrow.flatMap(regularCand dateS ce.apply).map { cand dates =>
            Cand datesW hS ceFeatures(cand dates, FeatureMap.empty)
          }
        case cand dateS ceW hExtractedFeatures: Cand dateS ceW hExtractedFeatures[
              Cand dateS ceQuery,
              Cand dateS ceResult
            ] =>
          Arrow.flatMap(cand dateS ceW hExtractedFeatures.apply)
      }

    val resultsTransfor rArrow: Arrow[Seq[Cand dateS ceResult], Seq[Try[Cand date]]] =
      perCand dateTransfor rExecutor.arrow(resultTransfor r, context)

    val featureMapTransfor rsArrow: Arrow[
      Seq[Cand dateS ceResult],
      Seq[FeatureMap]
    ] =
      cand dateFeatureTransfor rExecutor
        .arrow(resultFeaturesTransfor rs, context).map(_.featureMaps)

    val cand datesResultArrow: Arrow[Cand datesW hS ceFeatures[Cand dateS ceResult], Seq[
      (Cand date, FeatureMap)
    ]] = Arrow
      .map[Cand datesW hS ceFeatures[Cand dateS ceResult], Seq[Cand dateS ceResult]](
        _.cand dates)
      .andT n(Arrow
        .jo nMap(resultsTransfor rArrow, featureMapTransfor rsArrow) {
          case (transfor d, features) =>
             f (transfor d.length != features.length)
              throw P pel neFa lure(
                Execut onFa led,
                s"Found ${transfor d.length} cand dates and ${features.length} FeatureMaps, expected t  r lengths to be equal")
            transfor d. erator
              .z p(features. erator)
              .collect { case ErrorHandl ng(result) => result }
              .toSeq
        })

    // Bu ld t  f nal Cand dateS ceExecutorResult
    val executorResultArrow: Arrow[
      (FeatureMap, Seq[(Cand date, FeatureMap)]),
      Cand dateS ceExecutorResult[
        Cand date
      ]
    ] = Arrow.map {
      case (queryFeatures: FeatureMap, results: Seq[(Cand date, FeatureMap)]) =>
        val cand datesW hFeatures: Seq[Fetc dCand dateW hFeatures[Cand date]] =
          results.z pW h ndex.map {
            case ((cand date, featureMap),  ndex) =>
              Fetc dCand dateW hFeatures(
                cand date,
                featureMap + (Cand dateS cePos  on,  ndex) + (Cand dateS ces, L stSet(
                  cand dateS ce. dent f er))
              )
          }
        Cand dateS ceExecutorResult(
          cand dates = cand datesW hFeatures,
          cand dateS ceFeatureMap = queryFeatures
        )
    }

    val queryTransfor rArrow =
      transfor rExecutor.arrow[Query, Cand dateS ceQuery](queryTransfor r, context)

    val comb nedArrow =
      queryTransfor rArrow
        .andT n(cand dateS ceArrow)
        .andT n(
          Arrow
            .jo n(
              Arrow.map[Cand datesW hS ceFeatures[Cand dateS ceResult], FeatureMap](
                _.features),
              cand datesResultArrow
            ))
        .andT n(executorResultArrow)

    wrapComponentW hExecutorBookkeep ngW hS ze[Query, Cand dateS ceExecutorResult[Cand date]](
      context,
      cand dateS ce. dent f er,
      result => result.cand dates.s ze
    )(comb nedArrow)
  }

  object ErrorHandl ng {

    /** S lently drop [[UnexpectedCand dateResult]] */
    def unapply[Cand date](
      cand dateTryAndFeatureMap: (Try[Cand date], FeatureMap)
    ): Opt on[(Cand date, FeatureMap)] = {
      val (cand dateTry, featureMap) = cand dateTryAndFeatureMap
      val cand dateOpt = cand dateTry match {
        case Throw(P pel neFa lure(UnexpectedCand dateResult, _, _, _)) => None
        case Throw(ex) => throw ex
        case Return(r) => So (r)
      }

      cand dateOpt.map { cand date => (cand date, featureMap) }
    }
  }
}

case class Fetc dCand dateW hFeatures[Cand date <: Un versalNoun[Any]](
  cand date: Cand date,
  features: FeatureMap)
    extends Cand dateW hFeatures[Cand date]
