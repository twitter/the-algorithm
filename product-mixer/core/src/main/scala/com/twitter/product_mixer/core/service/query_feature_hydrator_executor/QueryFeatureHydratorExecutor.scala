package com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor

 mport com.fasterxml.jackson.datab nd.annotat on.JsonSer al ze
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.AsyncHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseQueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.Executor._
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.feature_hydrator_observer.FeatureHydratorObserver
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor.Async nd v dualFeatureHydratorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor.Base nd v dualFeatureHydratorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor.FeatureHydratorD sabled
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor. nd v dualFeatureHydratorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor.val dateAsyncQueryFeatureHydrator
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class QueryFeatureHydratorExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor {

  def arrow[Query <: P pel neQuery](
    hydrators: Seq[BaseQueryFeatureHydrator[Query, _]],
    val dP pel neSteps: Set[P pel neStep dent f er],
    context: Executor.Context
  ): Arrow[Query, QueryFeatureHydratorExecutor.Result] = {

    val observer = new FeatureHydratorObserver(statsRece ver, hydrators, context)
    val hydratorsW hErrorHandl ng =
      hydrators.map { hydrator =>
        val queryFeatureHydratorArrow =
          getQueryHydratorArrow(hydrator, context, observer)
        val wrappedW hAsyncHandl ng =
          handleAsyncHydrator(hydrator, val dP pel neSteps, queryFeatureHydratorArrow)
        handleCond  onally(hydrator, wrappedW hAsyncHandl ng)
      }

    Arrow
      .collect(hydratorsW hErrorHandl ng)
      .map {
        results: Seq[
          (FeatureHydrator dent f er, Base nd v dualFeatureHydratorResult)
        ] =>
          val comb nedFeatureMap = FeatureMap. rge(results.collect {
            case (_,  nd v dualFeatureHydratorResult(featureMap)) => featureMap
          })

          val asyncFeatureMaps = results.collect {
            case (
                  hydrator dent f er,
                  Async nd v dualFeatureHydratorResult(hydrateBefore, featuresToHydrate, ref)) =>
              (hydrator dent f er, hydrateBefore, featuresToHydrate, ref)
          }

          QueryFeatureHydratorExecutor.Result(
             nd v dualFeatureMaps = results.toMap,
            featureMap = comb nedFeatureMap,
            asyncFeatureMap = AsyncFeatureMap.fromFeatureMaps(asyncFeatureMaps)
          )
      }
  }

  def handleCond  onally[Query <: P pel neQuery](
    hydrator: BaseQueryFeatureHydrator[Query, _],
    arrow: Arrow[
      Query,
      Base nd v dualFeatureHydratorResult
    ]
  ): Arrow[
    Query,
    (FeatureHydrator dent f er, Base nd v dualFeatureHydratorResult)
  ] = {
    val cond  onallyRunArrow = hydrator match {
      case hydrator: BaseQueryFeatureHydrator[Query, _] w h Cond  onally[Query @unc cked] =>
        Arrow. felse[Query, Base nd v dualFeatureHydratorResult](
          hydrator.only f,
          arrow,
          Arrow.value(FeatureHydratorD sabled)
        )
      case _ => arrow
    }

    Arrow.jo n(
      Arrow.value(hydrator. dent f er),
      cond  onallyRunArrow
    )
  }

  def handleAsyncHydrator[Query <: P pel neQuery](
    hydrator: BaseQueryFeatureHydrator[Query, _],
    val dP pel neSteps: Set[P pel neStep dent f er],
    arrow: Arrow[
      Query,
       nd v dualFeatureHydratorResult
    ]
  ): Arrow[Query, Base nd v dualFeatureHydratorResult] = {
    hydrator match {
      case hydrator: BaseQueryFeatureHydrator[
            Query,
            _
          ] w h AsyncHydrator =>
        val dateAsyncQueryFeatureHydrator(hydrator, val dP pel neSteps)

        startArrowAsync(arrow.map(_.featureMap))
          .map { ref =>
            Async nd v dualFeatureHydratorResult(
              hydrator.hydrateBefore,
              hydrator.features.as nstanceOf[Set[Feature[_, _]]],
              ref
            )
          }

      case _ => arrow
    }
  }

  def getQueryHydratorArrow[Query <: P pel neQuery](
    hydrator: BaseQueryFeatureHydrator[Query, _],
    context: Executor.Context,
    queryFeatureHydratorObserver: FeatureHydratorObserver
  ): Arrow[Query,  nd v dualFeatureHydratorResult] = {

    val componentExecutorContext = context.pushToComponentStack(hydrator. dent f er)
    val hydratorArrow: Arrow[Query, FeatureMap] =
      Arrow.flatMap { query: Query => hydrator.hydrate(query) }

    val val dat onFn: FeatureMap => FeatureMap = hydrator match {
      // Feature store query hydrators store t  result ng Pred ct onRecord and not
      // t  features, so   cannot val date t  sa  way
      case _: FeatureStoreV1QueryFeatureHydrator[Query] =>
         dent y
      case _ =>
        val dateFeatureMap(
          hydrator.features.as nstanceOf[Set[Feature[_, _]]],
          _,
          componentExecutorContext)
    }

    // record t  component-level stats
    val observedArrow =
      wrapComponentW hExecutorBookkeep ng[Query, FeatureMap](
        context,
        hydrator. dent f er
      )(hydratorArrow.map(val dat onFn))

    // store non-conf gurat on errors  n t  FeatureMap
    val l ftNonVal dat onFa luresToFa ledFeatures = Arrow.handle[FeatureMap, FeatureMap] {
      case NotAM sconf guredFeatureMapFa lure(e) =>
        featureMapW hFa luresForFeatures(
          hydrator.features.as nstanceOf[Set[Feature[_, _]]],
          e,
          componentExecutorContext)
    }

    val observedL ftedAndWrapped = observedArrow
      .andT n(l ftNonVal dat onFa luresToFa ledFeatures)
      .applyEffect(Arrow.map[FeatureMap, Un ](featureMap =>
        // record per-feature stats, t   s separate from t  component stats handled by `wrapW hExecutorBookkeep ng`
        queryFeatureHydratorObserver.observeFeatureSuccessAndFa lures(hydrator, Seq(featureMap))))
      .map( nd v dualFeatureHydratorResult)

    observedL ftedAndWrapped
  }
}

object QueryFeatureHydratorExecutor {
  case class Result(
     nd v dualFeatureMaps: Map[
      FeatureHydrator dent f er,
      Base nd v dualFeatureHydratorResult
    ],
    featureMap: FeatureMap,
    asyncFeatureMap: AsyncFeatureMap)
      extends ExecutorResult

  sealed tra  Base nd v dualFeatureHydratorResult

  case object FeatureHydratorD sabled extends Base nd v dualFeatureHydratorResult
  case class  nd v dualFeatureHydratorResult(featureMap: FeatureMap)
      extends Base nd v dualFeatureHydratorResult

  /** Async result, ser al zes w hout t  [[St ch]] f eld s nce  's not ser al zable */
  @JsonSer al ze(us ng = classOf[Async nd v dualFeatureHydratorResultSer al zer])
  case class Async nd v dualFeatureHydratorResult(
    hydrateBefore: P pel neStep dent f er,
    features: Set[Feature[_, _]],
    ref: St ch[FeatureMap])
      extends Base nd v dualFeatureHydratorResult

  /**
   * Val dates w t r t  [[AsyncHydrator.hydrateBefore]] [[P pel neStep dent f er]]  s val d
   *
   * @param asyncQueryFeatureHydrator t  hydrator to val date
   * @param val dP pel neSteps        a Set of [[P pel neStep dent f er]]s wh ch are val d places to populate async
   *                                  [[Feature]]s  n a [[com.tw ter.product_m xer.core.p pel ne.P pel ne]]
   */
  def val dateAsyncQueryFeatureHydrator(
    asyncQueryFeatureHydrator: AsyncHydrator,
    val dP pel neSteps: Set[P pel neStep dent f er]
  ): Un  =
    requ re(
      val dP pel neSteps.conta ns(asyncQueryFeatureHydrator.hydrateBefore),
      s"`AsyncHydrator.hydrateBefore` conta ned ${asyncQueryFeatureHydrator.hydrateBefore} wh ch was not  n t  parent p pel ne's " +
        s"`P pel neConf g` Compan on object f eld `stepsAsyncFeatureHydrat onCanBeCompletedBy = $val dP pel neSteps`."
    )
}
