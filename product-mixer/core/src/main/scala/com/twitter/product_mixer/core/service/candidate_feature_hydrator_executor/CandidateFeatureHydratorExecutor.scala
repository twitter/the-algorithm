package com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseBulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.HydratorCand dateResult
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.M sconf guredFeatureMapFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.Executor._
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutor. nputs
 mport com.tw ter.product_m xer.core.serv ce.feature_hydrator_observer.FeatureHydratorObserver
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cand dateFeatureHydratorExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor {
  def arrow[Query <: P pel neQuery, Result <: Un versalNoun[Any]](
    hydrators: Seq[BaseCand dateFeatureHydrator[Query, Result, _]],
    context: Executor.Context
  ): Arrow[
     nputs[Query, Result],
    Cand dateFeatureHydratorExecutorResult[
      Result
    ]
  ] = {

    val observer = new FeatureHydratorObserver(statsRece ver, hydrators, context)

    val cand dateFeatureHydratorExecutorResults: Seq[Arrow[
       nputs[Query, Result],
      Cand dateFeatureHydratorExecutorResult[Result]
    ]] = hydrators.map(getCand dateHydratorArrow(_, context, observer))

    val runHydrators = Arrow.collect(cand dateFeatureHydratorExecutorResults).map {
      cand dateFeatureHydratorExecutorResult: Seq[Cand dateFeatureHydratorExecutorResult[Result]] =>
        cand dateFeatureHydratorExecutorResult.foldLeft(
          Cand dateFeatureHydratorExecutorResult[Result](
            Seq.empty,
            Map.empty
          )
        ) { (accumulator, add  onalResult) =>
          // accumulator.results and add  onalResults.results are e  r t  sa  length or one may be empty
          // c cks  n each Hydrator's Arrow  mple ntat on ensure t  order ng and length are correct
          val  rgedFeatureMaps =
             f (accumulator.results.length == add  onalResult.results.length) {
              //  rge  f t re are results for both and t y are t  sa  s ze
              // also handles both be ng empty
              accumulator.results.z p(add  onalResult.results).map {
                case (accumulatedScoredCand date, resultScoredCand date) =>
                  val updatedFeatureMap =
                    accumulatedScoredCand date.features ++ resultScoredCand date.features
                  HydratorCand dateResult(resultScoredCand date.cand date, updatedFeatureMap)
              }
            } else  f (accumulator.results. sEmpty) {
              // accumulator  s empty (t   n  al case) so use add  onal results
              add  onalResult.results
            } else {
              // empty results but non-empty accumulator due to Hydrator be ng turned off so use accumulator results
              accumulator.results
            }

          Cand dateFeatureHydratorExecutorResult(
             rgedFeatureMaps,
            accumulator. nd v dualFeatureHydratorResults ++ add  onalResult. nd v dualFeatureHydratorResults
          )
        }
    }

    Arrow. felse[ nputs[Query, Result], Cand dateFeatureHydratorExecutorResult[Result]](
      _.cand dates.nonEmpty,
      runHydrators,
      Arrow.value(Cand dateFeatureHydratorExecutorResult(Seq.empty, Map.empty)))
  }

  /** @note t  returned [[Arrow]] must have a result for every cand date passed  nto    n t  sa  order OR a completely empty result */
  pr vate def getCand dateHydratorArrow[Query <: P pel neQuery, Result <: Un versalNoun[Any]](
    hydrator: BaseCand dateFeatureHydrator[Query, Result, _],
    context: Executor.Context,
    cand dateFeatureHydratorObserver: FeatureHydratorObserver
  ): Arrow[
     nputs[Query, Result],
    Cand dateFeatureHydratorExecutorResult[Result]
  ] = {
    val componentExecutorContext = context.pushToComponentStack(hydrator. dent f er)

    val val dateFeatureMapFn: FeatureMap => FeatureMap =
      hydrator match {
        // Feature store cand date hydrators store t  result ng Pred ct onRecords and
        // not t  features, so   cannot val date t  sa  way
        case _: FeatureStoreV1Cand dateFeatureHydrator[Query, Result] =>
           dent y
        case _ =>
          val dateFeatureMap(
            hydrator.features.as nstanceOf[Set[Feature[_, _]]],
            _,
            componentExecutorContext)
      }

    val hydratorBaseArrow = hydrator match {
      case hydrator: Cand dateFeatureHydrator[Query, Result] =>
        s ngleCand dateHydratorArrow(
          hydrator,
          val dateFeatureMapFn,
          componentExecutorContext,
          parentContext = context)

      case hydrator: BaseBulkCand dateFeatureHydrator[Query, Result, _] =>
        bulkCand dateHydratorArrow(
          hydrator,
          val dateFeatureMapFn,
          componentExecutorContext,
          parentContext = context)
    }

    val cand dateFeatureHydratorArrow =
      Arrow
        .z pW hArg(hydratorBaseArrow)
        .map {
          case (
                arg: Cand dateFeatureHydratorExecutor. nputs[Query, Result],
                featureMapSeq: Seq[FeatureMap]) =>
            val cand dates = arg.cand dates.map(_.cand date)

            cand dateFeatureHydratorObserver.observeFeatureSuccessAndFa lures(
              hydrator,
              featureMapSeq)

            // Bu ld a map from cand date to FeatureMap
            val cand dateAndFeatureMaps =  f (cand dates.s ze == featureMapSeq.s ze) {
              cand dates.z p(featureMapSeq).map {
                case (cand date, featureMap) => HydratorCand dateResult(cand date, featureMap)
              }
            } else {
              throw P pel neFa lure(
                M sconf guredFeatureMapFa lure,
                s"Unexpected response length from ${hydrator. dent f er}, ensure hydrator returns feature map for all cand dates")
            }
            val  nd v dualFeatureHydratorFeatureMaps =
              Map(hydrator. dent f er ->  nd v dualFeatureHydratorResult(cand dateAndFeatureMaps))
            Cand dateFeatureHydratorExecutorResult(
              cand dateAndFeatureMaps,
               nd v dualFeatureHydratorFeatureMaps)
        }

    val cond  onallyRunArrow = hydrator match {
      case hydrator: BaseCand dateFeatureHydrator[Query, Result, _] w h Cond  onally[
            Query @unc cked
          ] =>
        Arrow. felse[ nputs[Query, Result], Cand dateFeatureHydratorExecutorResult[Result]](
          { case  nputs(query: Query @unc cked, _) => hydrator.only f(query) },
          cand dateFeatureHydratorArrow,
          Arrow.value(
            Cand dateFeatureHydratorExecutorResult(
              Seq.empty,
              Map(hydrator. dent f er -> FeatureHydratorD sabled[Result]())
            ))
        )
      case _ => cand dateFeatureHydratorArrow
    }

    wrapW hErrorHandl ng(context, hydrator. dent f er)(cond  onallyRunArrow)
  }

  pr vate def s ngleCand dateHydratorArrow[Query <: P pel neQuery, Result <: Un versalNoun[Any]](
    hydrator: Cand dateFeatureHydrator[Query, Result],
    val dateFeatureMap: FeatureMap => FeatureMap,
    componentContext: Context,
    parentContext: Context
  ): Arrow[ nputs[Query, Result], Seq[FeatureMap]] = {
    val  nputTransfor r = Arrow
      .map {  nputs:  nputs[Query, Result] =>
         nputs.cand dates.map { cand date =>
          ( nputs.query, cand date.cand date, cand date.features)
        }
      }

    val hydratorArrow = Arrow
      .flatMap[(Query, Result, FeatureMap), FeatureMap] {
        case (query, cand date, featureMap) =>
          hydrator.apply(query, cand date, featureMap)
      }

    // val date before observ ng so val dat on fa lures are caught  n t   tr cs
    val hydratorArrowW hVal dat on = hydratorArrow.map(val dateFeatureMap)

    // no trac ng  re s nce per-Component spans  s overk ll
    val observedArrow =
      wrapPerCand dateComponentW hExecutorBookkeep ngW houtTrac ng(
        parentContext,
        hydrator. dent f er
      )(hydratorArrowW hVal dat on)

    // only handle non-val dat on fa lures
    val l ftNonVal dat onFa luresToFa ledFeatures = Arrow.handle[FeatureMap, FeatureMap] {
      case NotAM sconf guredFeatureMapFa lure(e) =>
        featureMapW hFa luresForFeatures(hydrator.features, e, componentContext)
    }

    wrapComponentsW hTrac ngOnly(parentContext, hydrator. dent f er)(
       nputTransfor r.andT n(
        Arrow.sequence(observedArrow.andT n(l ftNonVal dat onFa luresToFa ledFeatures))
      )
    )
  }

  pr vate def bulkCand dateHydratorArrow[Query <: P pel neQuery, Result <: Un versalNoun[Any]](
    hydrator: BaseBulkCand dateFeatureHydrator[Query, Result, _],
    val dateFeatureMap: FeatureMap => FeatureMap,
    componentContext: Context,
    parentContext: Context
  ): Arrow[ nputs[Query, Result], Seq[FeatureMap]] = {
    val hydratorArrow: Arrow[ nputs[Query, Result], Seq[FeatureMap]] =
      Arrow.flatMap {  nputs =>
        hydrator.apply( nputs.query,  nputs.cand dates)
      }

    val val dat onArrow: Arrow[( nputs[Query, Result], Seq[FeatureMap]), Seq[FeatureMap]] = Arrow
      .map[( nputs[Query, Result], Seq[FeatureMap]), Seq[FeatureMap]] {
        case ( nputs, results) =>
          // For bulk AP s, t  ensures no cand dates are om ted and also ensures t  order  s preserved.
           f ( nputs.cand dates.length != results.length) {
            throw P pel neFa lure(
              M sconf guredFeatureMapFa lure,
              s"Unexpected response from ${hydrator. dent f er}, ensure hydrator returns features for all cand dates. M ss ng results for ${ nputs.cand dates.length - results.length} cand dates"
            )
          }

          results.map(val dateFeatureMap)
      }

    // val date before observ ng so val dat on fa lures are caught  n t   tr cs
    val hydratorArrowW hVal dat on: Arrow[ nputs[Query, Result], Seq[FeatureMap]] =
      Arrow.z pW hArg(hydratorArrow).andT n(val dat onArrow)

    val observedArrow =
      wrapComponentW hExecutorBookkeep ng(parentContext, hydrator. dent f er)(
        hydratorArrowW hVal dat on)

    // only handle non-val dat on fa lures
    val l ftNonVal dat onFa luresToFa ledFeatures =
      Arrow.map[( nputs[Query, Result], Try[Seq[FeatureMap]]), Try[Seq[FeatureMap]]] {
        case ( nputs, resultTry) =>
          resultTry.handle {
            case NotAM sconf guredFeatureMapFa lure(e) =>
              val errorFeatureMap =
                featureMapW hFa luresForFeatures(
                  hydrator.features.as nstanceOf[Set[Feature[_, _]]],
                  e,
                  componentContext)
               nputs.cand dates.map(_ => errorFeatureMap)
          }
      }

    Arrow
      .z pW hArg(observedArrow.l ftToTry)
      .andT n(l ftNonVal dat onFa luresToFa ledFeatures)
      .lo rFromTry
  }
}

object Cand dateFeatureHydratorExecutor {
  case class  nputs[+Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]])
}
