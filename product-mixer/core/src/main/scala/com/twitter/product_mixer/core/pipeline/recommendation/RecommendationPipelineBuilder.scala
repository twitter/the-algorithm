package com.tw ter.product_m xer.core.p pel ne.recom ndat on

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseQueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.Recom ndat onP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scor ngP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emPresentat on
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.Fa lOpenPol cy
 mport com.tw ter.product_m xer.core.p pel ne. nval dStepStateExcept on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neBu lder
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel ne
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.cand date.DependentCand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.M sconf guredDecorator
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureClass f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.ProductD sabled
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel ne
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel neConf g
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorStatus
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutorResults
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor.Cand dateP pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor.Cand dateP pel neExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.StoppedGateExcept on
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor.P pel neResultS deEffectExecutor
 mport com.tw ter.product_m xer.core.serv ce.qual y_factor_executor.Qual yFactorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.scor ng_p pel ne_executor.Scor ngP pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.scor ng_p pel ne_executor.Scor ngP pel neExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor
 mport com.tw ter.st ch.Arrow

/**
 * Recom ndat onP pel neBu lder bu lds [[Recom ndat onP pel ne]]s from [[Recom ndat onP pel neConf g]]s.
 *
 *   should  nject a [[Recom ndat onP pel neBu lderFactory]] and call `.get` to bu ld t se.
 *
 * @see [[Recom ndat onP pel neConf g]] for t  descr pt on of t  type para ters.
 *
 * @note Almost a m rror of M xerP pel neBu lder
 */

class Recom ndat onP pel neBu lder[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  Doma nResultType <: HasMarshall ng,
  Result
](
  cand dateP pel neExecutor: Cand dateP pel neExecutor,
  gateExecutor: GateExecutor,
  selectorExecutor: SelectorExecutor,
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  cand dateFeatureHydratorExecutor: Cand dateFeatureHydratorExecutor,
  f lterExecutor: F lterExecutor,
  scor ngP pel neExecutor: Scor ngP pel neExecutor,
  cand dateDecoratorExecutor: Cand dateDecoratorExecutor,
  doma nMarshallerExecutor: Doma nMarshallerExecutor,
  transportMarshallerExecutor: TransportMarshallerExecutor,
  p pel neResultS deEffectExecutor: P pel neResultS deEffectExecutor,
  cand dateP pel neBu lderFactory: Cand dateP pel neBu lderFactory,
  scor ngP pel neBu lderFactory: Scor ngP pel neBu lderFactory,
  overr de val statsRece ver: StatsRece ver)
    extends P pel neBu lder[Query]
    w h Logg ng {

  overr de type Underly ngResultType = Result
  overr de type P pel neResultType = Recom ndat onP pel neResult[Cand date, Result]

  def qual yFactorStep(
    qual yFactorStatus: Qual yFactorStatus
  ): Step[Query, Qual yFactorExecutorResult] =
    new Step[Query, Qual yFactorExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er =
        Recom ndat onP pel neConf g.qual yFactorStep

      overr de def executorArrow: Arrow[Query, Qual yFactorExecutorResult] =
        Arrow
          .map[Query, Qual yFactorExecutorResult] { _ =>
            Qual yFactorExecutorResult(
              p pel neQual yFactors =
                qual yFactorStatus.qual yFactorByP pel ne.mapValues(_.currentValue)
            )
          }

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): Query = query

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: Qual yFactorExecutorResult
      ): Recom ndat onP pel neResult[Cand date, Result] =
        prev ousP pel neResult.copy(qual yFactorResult = So (executorResult))

      overr de def queryUpdater(
        query: Query,
        executorResult: Qual yFactorExecutorResult
      ): Query = {
        query match {
          case queryW hQual yFactor: HasQual yFactorStatus =>
            queryW hQual yFactor
              .w hQual yFactorStatus(
                queryW hQual yFactor.qual yFactorStatus.getOrElse(Qual yFactorStatus.empty) ++
                  qual yFactorStatus
              ).as nstanceOf[Query]
          case _ =>
            query
        }
      }
    }

  def gatesStep(
    gates: Seq[Gate[Query]],
    context: Executor.Context
  ): Step[Query, GateExecutorResult] = new Step[Query, GateExecutorResult] {
    overr de def  dent f er: P pel neStep dent f er = Recom ndat onP pel neConf g.gatesStep

    overr de def executorArrow: Arrow[Query, GateExecutorResult] =
      gateExecutor.arrow(gates, context)

    overr de def  nputAdaptor(
      query: Query,
      prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
    ): Query =
      query

    overr de def resultUpdater(
      prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
      executorResult: GateExecutorResult
    ): Recom ndat onP pel neResult[Cand date, Result] =
      prev ousP pel neResult.copy(gateResult = So (executorResult))
  }

  def fetchQueryFeaturesStep(
    queryFeatureHydrators: Seq[BaseQueryFeatureHydrator[Query, _]],
    step dent f er: P pel neStep dent f er,
    updater: ResultUpdater[
      Recom ndat onP pel neResult[Cand date, Result],
      QueryFeatureHydratorExecutor.Result
    ],
    context: Executor.Context
  ): Step[Query, QueryFeatureHydratorExecutor.Result] =
    new Step[Query, QueryFeatureHydratorExecutor.Result] {
      overr de def  dent f er: P pel neStep dent f er = step dent f er

      overr de def executorArrow: Arrow[Query, QueryFeatureHydratorExecutor.Result] =
        queryFeatureHydratorExecutor.arrow(
          queryFeatureHydrators,
          Recom ndat onP pel neConf g.stepsAsyncFeatureHydrat onCanBeCompletedBy,
          context)

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): Query = query

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: QueryFeatureHydratorExecutor.Result
      ): Recom ndat onP pel neResult[Cand date, Result] =
        updater(prev ousP pel neResult, executorResult)

      overr de def queryUpdater(
        query: Query,
        executorResult: QueryFeatureHydratorExecutor.Result
      ): Query =
        query
          .w hFeatureMap(
            query.features
              .getOrElse(FeatureMap.empty) ++ executorResult.featureMap).as nstanceOf[Query]
    }

  def asyncFeaturesStep(
    stepToHydrateFor: P pel neStep dent f er,
    context: Executor.Context
  ): Step[AsyncFeatureMap, AsyncFeatureMapExecutorResults] =
    new Step[AsyncFeatureMap, AsyncFeatureMapExecutorResults] {
      overr de def  dent f er: P pel neStep dent f er =
        Recom ndat onP pel neConf g.asyncFeaturesStep(stepToHydrateFor)

      overr de def executorArrow: Arrow[AsyncFeatureMap, AsyncFeatureMapExecutorResults] =
        asyncFeatureMapExecutor.arrow(
          stepToHydrateFor,
           dent f er,
          context
        )

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): AsyncFeatureMap =
        prev ousResult. rgedAsyncQueryFeatures
          .getOrElse(
            throw  nval dStepStateExcept on( dent f er, " rgedAsyncQueryFeatures")
          )

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: AsyncFeatureMapExecutorResults
      ): Recom ndat onP pel neResult[Cand date, Result] =
        prev ousP pel neResult.copy(
          asyncFeatureHydrat onResults = prev ousP pel neResult.asyncFeatureHydrat onResults match {
            case So (ex st ngResults) => So (ex st ngResults ++ executorResult)
            case None => So (executorResult)
          })

      overr de def queryUpdater(
        query: Query,
        executorResult: AsyncFeatureMapExecutorResults
      ): Query =
         f (executorResult.featureMapsByStep
            .getOrElse(stepToHydrateFor, FeatureMap.empty). sEmpty) {
          query
        } else {
          query
            .w hFeatureMap(
              query.features
                .getOrElse(FeatureMap.empty) ++ executorResult.featureMapsByStep(
                stepToHydrateFor)).as nstanceOf[Query]
        }
    }

  def cand dateP pel nesStep(
    cand dateP pel nes: Seq[Cand dateP pel ne[Query]],
    defaultFa lOpenPol cy: Fa lOpenPol cy,
    fa lOpenPol c es: Map[Cand dateP pel ne dent f er, Fa lOpenPol cy],
    qual yFactorObserverByP pel ne: Map[Component dent f er, Qual yFactorObserver],
    context: Executor.Context
  ): Step[Cand dateP pel ne. nputs[Query], Cand dateP pel neExecutorResult] =
    new Step[Cand dateP pel ne. nputs[Query], Cand dateP pel neExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er =
        Recom ndat onP pel neConf g.cand dateP pel nesStep

      overr de def executorArrow: Arrow[Cand dateP pel ne. nputs[
        Query
      ], Cand dateP pel neExecutorResult] =
        cand dateP pel neExecutor
          .arrow(
            cand dateP pel nes,
            defaultFa lOpenPol cy,
            fa lOpenPol c es,
            qual yFactorObserverByP pel ne,
            context
          )

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): Cand dateP pel ne. nputs[
        Query
      ] = Cand dateP pel ne. nputs(query, Seq.empty)

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: Cand dateP pel neExecutorResult
      ): Recom ndat onP pel neResult[Cand date, Result] =
        prev ousP pel neResult.copy(cand dateP pel neResults = So (executorResult))

      overr de def queryUpdater(
        query: Query,
        executorResult: Cand dateP pel neExecutorResult
      ): Query = {
        val updatedFeatureMap = query.features
          .getOrElse(FeatureMap.empty) ++ executorResult.queryFeatureMap
        query
          .w hFeatureMap(updatedFeatureMap).as nstanceOf[Query]
      }
    }

  def dependentCand dateP pel nesStep(
    cand dateP pel nes: Seq[Cand dateP pel ne[Query]],
    defaultFa lOpenPol cy: Fa lOpenPol cy,
    fa lOpenPol c es: Map[Cand dateP pel ne dent f er, Fa lOpenPol cy],
    qual yFactorObserverByP pel ne: Map[Component dent f er, Qual yFactorObserver],
    context: Executor.Context
  ): Step[Cand dateP pel ne. nputs[Query], Cand dateP pel neExecutorResult] =
    new Step[Cand dateP pel ne. nputs[Query], Cand dateP pel neExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er =
        Recom ndat onP pel neConf g.dependentCand dateP pel nesStep

      overr de def executorArrow: Arrow[Cand dateP pel ne. nputs[
        Query
      ], Cand dateP pel neExecutorResult] =
        cand dateP pel neExecutor
          .arrow(
            cand dateP pel nes,
            defaultFa lOpenPol cy,
            fa lOpenPol c es,
            qual yFactorObserverByP pel ne,
            context
          )

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): Cand dateP pel ne. nputs[
        Query
      ] = {
        val prev ousCand dates = prev ousResult.cand dateP pel neResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "Cand dates")
          }.cand dateP pel neResults.flatMap(_.result.getOrElse(Seq.empty))

        Cand dateP pel ne. nputs(query, prev ousCand dates)
      }

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: Cand dateP pel neExecutorResult
      ): Recom ndat onP pel neResult[Cand date, Result] =
        prev ousP pel neResult.copy(dependentCand dateP pel neResults = So (executorResult))

      overr de def queryUpdater(
        query: Query,
        executorResult: Cand dateP pel neExecutorResult
      ): Query = {
        val updatedFeatureMap = query.features
          .getOrElse(FeatureMap.empty) ++ executorResult.queryFeatureMap
        query
          .w hFeatureMap(updatedFeatureMap).as nstanceOf[Query]
      }
    }

  abstract class F lterStep(
    f lters: Seq[F lter[Query, Cand date]],
    context: Executor.Context,
    overr de val  dent f er: P pel neStep dent f er)
      extends Step[
        (Query, Seq[Cand dateW hFeatures[Cand date]]),
        F lterExecutorResult[Cand date]
      ] {

    def  emCand dates(
      prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
    ): Seq[Cand dateW hDeta ls]

    overr de def executorArrow: Arrow[
      (Query, Seq[Cand dateW hFeatures[Cand date]]),
      F lterExecutorResult[Cand date]
    ] =
      f lterExecutor.arrow(f lters, context)

    overr de def  nputAdaptor(
      query: Query,
      prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
    ): (Query, Seq[Cand dateW hFeatures[Cand date]]) = {

      val extracted emCand dates =  emCand dates(prev ousResult).collect {
        case  emCand date:  emCand dateW hDeta ls =>  emCand date
      }

      (query, extracted emCand dates.as nstanceOf[Seq[Cand dateW hFeatures[Cand date]]])
    }
  }

  def postCand dateP pel nesSelectorStep(
    selectors: Seq[Selector[Query]],
    context: Executor.Context
  ): Step[SelectorExecutor. nputs[Query], SelectorExecutorResult] =
    new Step[SelectorExecutor. nputs[Query], SelectorExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er =
        Recom ndat onP pel neConf g.postCand dateP pel nesSelectorsStep

      overr de def executorArrow: Arrow[SelectorExecutor. nputs[
        Query
      ], SelectorExecutorResult] =
        selectorExecutor.arrow(selectors, context)

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): SelectorExecutor. nputs[Query] = {
        val cand dateP pel neResults = prev ousResult.cand dateP pel neResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "Cand dateP pel neResults")
          }.cand dateP pel neResults.flatMap(_.result.getOrElse(Seq.empty))
        val dependentCand dateP pel neResults = prev ousResult.dependentCand dateP pel neResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "DependentCand dateP pel neResults")
          }.cand dateP pel neResults.flatMap(_.result.getOrElse(Seq.empty))

        SelectorExecutor. nputs(
          query = query,
          cand datesW hDeta ls = cand dateP pel neResults ++ dependentCand dateP pel neResults
        )
      }

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: SelectorExecutorResult
      ): Recom ndat onP pel neResult[Cand date, Result] =
        prev ousP pel neResult.copy(postCand dateP pel nesSelectorResults = So (executorResult))
    }

  def postCand dateP pel nesFeatureHydrat onStep(
    hydrators: Seq[BaseCand dateFeatureHydrator[Query, Cand date, _]],
    context: Executor.Context
  ): Step[
    Cand dateFeatureHydratorExecutor. nputs[Query, Cand date],
    Cand dateFeatureHydratorExecutorResult[Cand date]
  ] = new Step[
    Cand dateFeatureHydratorExecutor. nputs[Query, Cand date],
    Cand dateFeatureHydratorExecutorResult[Cand date]
  ] {
    overr de def  dent f er: P pel neStep dent f er =
      Recom ndat onP pel neConf g.postCand dateP pel nesFeatureHydrat onStep

    overr de def executorArrow: Arrow[
      Cand dateFeatureHydratorExecutor. nputs[Query, Cand date],
      Cand dateFeatureHydratorExecutorResult[Cand date]
    ] =
      cand dateFeatureHydratorExecutor.arrow(hydrators, context)

    overr de def  nputAdaptor(
      query: Query,
      prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
    ): Cand dateFeatureHydratorExecutor. nputs[Query, Cand date] = {
      val selectedCand datesResult =
        prev ousResult.postCand dateP pel nesSelectorResults.getOrElse {
          throw  nval dStepStateExcept on( dent f er, "PostCand dateP pel nesSelectorResults")
        }.selectedCand dates

      Cand dateFeatureHydratorExecutor. nputs(
        query,
        selectedCand datesResult.as nstanceOf[Seq[Cand dateW hFeatures[Cand date]]])
    }

    overr de def resultUpdater(
      prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
      executorResult: Cand dateFeatureHydratorExecutorResult[Cand date]
    ): Recom ndat onP pel neResult[Cand date, Result] = prev ousP pel neResult.copy(
      postCand dateP pel nesFeatureHydrat onResults = So (executorResult)
    )
  }

  def globalF ltersStep(
    f lters: Seq[F lter[Query, Cand date]],
    context: Executor.Context
  ): Step[(Query, Seq[Cand dateW hFeatures[Cand date]]), F lterExecutorResult[Cand date]] =
    new F lterStep(f lters, context, Recom ndat onP pel neConf g.globalF ltersStep) {
      overr de def  emCand dates(
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): Seq[Cand dateW hDeta ls] = {
        val cand dates = prev ousResult.postCand dateP pel nesSelectorResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "PostCand dateP pel neSelectorResults")
          }.selectedCand dates.collect {
            case  emCand date:  emCand dateW hDeta ls =>  emCand date
          }

        val featureMaps = prev ousResult.postCand dateP pel nesFeatureHydrat onResults
          .getOrElse {
            throw  nval dStepStateExcept on(
               dent f er,
              "PostCand dateP pel neFeatureHydrat onResults")
          }.results.map(_.features)
        //  f no hydrators  re run, t  l st would be empty. Ot rw se, order and card nal y  s
        // always ensured to match.
         f (featureMaps. sEmpty) {
          cand dates
        } else {
          cand dates.z p(featureMaps).map {
            case (cand date, featureMap) =>
              cand date.copy(features = cand date.features ++ featureMap)
          }
        }
      }

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: F lterExecutorResult[Cand date]
      ): Recom ndat onP pel neResult[Cand date, Result] = prev ousP pel neResult.copy(
        globalF lterResults = So (executorResult)
      )
    }

  def scor ngP pel nesStep(
    scor ngP pel nes: Seq[Scor ngP pel ne[Query, Cand date]],
    context: Executor.Context,
    defaultFa lOpenPol cy: Fa lOpenPol cy,
    fa lOpenPol c es: Map[Scor ngP pel ne dent f er, Fa lOpenPol cy],
    qual yFactorObserverByP pel ne: Map[Component dent f er, Qual yFactorObserver]
  ): Step[Scor ngP pel neExecutor. nputs[Query], Scor ngP pel neExecutorResult[
    Cand date
  ]] =
    new Step[Scor ngP pel neExecutor. nputs[Query], Scor ngP pel neExecutorResult[
      Cand date
    ]] {
      overr de def  dent f er: P pel neStep dent f er =
        Recom ndat onP pel neConf g.scor ngP pel nesStep

      overr de def executorArrow: Arrow[
        Scor ngP pel neExecutor. nputs[Query],
        Scor ngP pel neExecutorResult[Cand date]
      ] = scor ngP pel neExecutor.arrow(
        scor ngP pel nes,
        context,
        defaultFa lOpenPol cy,
        fa lOpenPol c es,
        qual yFactorObserverByP pel ne
      )

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): Scor ngP pel neExecutor. nputs[Query] = {
        val selectedCand dates =
          prev ousResult.postCand dateP pel nesSelectorResults.getOrElse {
            throw  nval dStepStateExcept on( dent f er, "PostCand dateP pel nesSelectorResults")
          }.selectedCand dates

        val  emCand dates = selectedCand dates.collect {
          case  emCand date:  emCand dateW hDeta ls =>  emCand date
        }

        val featureMaps = prev ousResult.postCand dateP pel nesFeatureHydrat onResults
          .getOrElse {
            throw  nval dStepStateExcept on(
               dent f er,
              "PostCand dateP pel neFeatureHydrat onResults")
          }.results.map(_.features)
        //  f no hydrators  re run, t  l st would be empty. Ot rw se, order and card nal y  s
        // always ensured to match.
        val updatedCand dates =  f (featureMaps. sEmpty) {
           emCand dates
        } else {
           emCand dates.z p(featureMaps).map {
            case (cand date, featureMap) =>
              cand date.copy(features = cand date.features ++ featureMap)
          }
        }

        // F lter t  or g nal l st of cand dates to keep only t  ones that  re kept from
        // f lter ng
        val f lterResults: Set[Cand date] = prev ousResult.globalF lterResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "F lterResults")
          }.result.toSet

        val f ltered emCand dates = updatedCand dates.f lter {  emCand date =>
          f lterResults.conta ns( emCand date.cand date.as nstanceOf[Cand date])
        }

        Scor ngP pel neExecutor. nputs(
          query,
          f ltered emCand dates
        )
      }

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: Scor ngP pel neExecutorResult[Cand date]
      ): Recom ndat onP pel neResult[Cand date, Result] = prev ousP pel neResult
        .copy(scor ngP pel neResults = So (executorResult))
    }

  def resultSelectorsStep(
    selectors: Seq[Selector[Query]],
    context: Executor.Context
  ): Step[SelectorExecutor. nputs[Query], SelectorExecutorResult] =
    new Step[SelectorExecutor. nputs[Query], SelectorExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er =
        Recom ndat onP pel neConf g.resultSelectorsStep

      overr de def executorArrow: Arrow[SelectorExecutor. nputs[
        Query
      ], SelectorExecutorResult] =
        selectorExecutor.arrow(selectors, context)

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): SelectorExecutor. nputs[Query] = {

        /**
         * See [[Scor ngP pel neExecutor]], scor ngP pel neResults conta ns t  fully re- rged
         * and updated FeatureMap so t re's no need to do any recompos  on. Scor ng P pel ne Results
         * has only cand dates that  re kept  n prev ous f lter ng, w h t  r f nal  rged feature
         * map.
         */
        val scorerResults = prev ousResult.scor ngP pel neResults.getOrElse {
          throw  nval dStepStateExcept on( dent f er, "Scores")
        }

        SelectorExecutor. nputs(
          query = query,
          cand datesW hDeta ls = scorerResults.result
        )
      }

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: SelectorExecutorResult
      ): Recom ndat onP pel neResult[Cand date, Result] =
        prev ousP pel neResult.copy(resultSelectorResults = So (executorResult))
    }

  def postSelect onF ltersStep(
    f lters: Seq[F lter[Query, Cand date]],
    context: Executor.Context
  ): Step[(Query, Seq[Cand dateW hFeatures[Cand date]]), F lterExecutorResult[Cand date]] =
    new F lterStep(f lters, context, Recom ndat onP pel neConf g.postSelect onF ltersStep) {

      overr de def  emCand dates(
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): Seq[Cand dateW hDeta ls] = {
        prev ousResult.resultSelectorResults.getOrElse {
          throw  nval dStepStateExcept on( dent f er, "Cand dates")
        }.selectedCand dates
      }

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: F lterExecutorResult[Cand date]
      ): Recom ndat onP pel neResult[Cand date, Result] = {
        prev ousP pel neResult.copy(postSelect onF lterResults = So (executorResult))
      }
    }

  def decoratorStep(
    decorator: Opt on[Cand dateDecorator[Query, Cand date]],
    context: Executor.Context
  ): Step[(Query, Seq[Cand dateW hFeatures[Cand date]]), Cand dateDecoratorExecutorResult] =
    new Step[(Query, Seq[Cand dateW hFeatures[Cand date]]), Cand dateDecoratorExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er = Recom ndat onP pel neConf g.decoratorStep

      overr de lazy val executorArrow: Arrow[
        (Query, Seq[Cand dateW hFeatures[Cand date]]),
        Cand dateDecoratorExecutorResult
      ] =
        cand dateDecoratorExecutor.arrow(decorator, context)

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): (Query, Seq[Cand dateW hFeatures[Cand date]]) = {

        val selectorResults = prev ousResult.resultSelectorResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "SelectorResults")
          }.selectedCand dates
          .collect { case cand date:  emCand dateW hDeta ls => cand date }

        val f lterResults = prev ousResult.postSelect onF lterResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "PostSelect onF lterResults")
          }.result.toSet

        val  emCand dateW hDeta lsPostF lter ng =
          selectorResults
            .f lter(cand dateW hDeta ls =>
              f lterResults.conta ns(
                cand dateW hDeta ls.cand date
                  .as nstanceOf[Cand date]))
            .as nstanceOf[Seq[Cand dateW hFeatures[Cand date]]]

        (query,  emCand dateW hDeta lsPostF lter ng)
      }

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: Cand dateDecoratorExecutorResult
      ): Recom ndat onP pel neResult[Cand date, Result] =
        prev ousP pel neResult.copy(
          cand dateDecoratorResult = So (executorResult)
        )
    }

  def doma nMarshall ngStep(
    doma nMarshaller: Doma nMarshaller[Query, Doma nResultType],
    context: Executor.Context
  ): Step[Doma nMarshallerExecutor. nputs[Query], Doma nMarshallerExecutor.Result[
    Doma nResultType
  ]] =
    new Step[Doma nMarshallerExecutor. nputs[Query], Doma nMarshallerExecutor.Result[
      Doma nResultType
    ]] {
      overr de def  dent f er: P pel neStep dent f er =
        Recom ndat onP pel neConf g.doma nMarshallerStep

      overr de def executorArrow: Arrow[
        Doma nMarshallerExecutor. nputs[Query],
        Doma nMarshallerExecutor.Result[Doma nResultType]
      ] =
        doma nMarshallerExecutor.arrow(doma nMarshaller, context)

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
      ): Doma nMarshallerExecutor. nputs[Query] = {
        val selectorResults = prev ousResult.resultSelectorResults.getOrElse {
          throw  nval dStepStateExcept on( dent f er, "SelectorResults")
        }

        val f lterResults = prev ousResult.postSelect onF lterResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "PostSelect onF lterResults")
          }.result.toSet

        val f lteredResults = selectorResults.selectedCand dates.collect {
          case cand date:  emCand dateW hDeta ls
               f f lterResults.conta ns(cand date.cand date.as nstanceOf[Cand date]) =>
            cand date
        }

        val decoratorResults = prev ousResult.cand dateDecoratorResult
          .getOrElse(throw  nval dStepStateExcept on( dent f er, "DecoratorStep")).result.map {
            decorat on =>
              decorat on.cand date -> decorat on.presentat on
          }.toMap

        val f nalResults = f lteredResults.map {  emW hDeta ls =>
          decoratorResults.get( emW hDeta ls.cand date) match {
            case So (presentat on:  emPresentat on) =>
               f ( emW hDeta ls.presentat on. sDef ned) {
                throw P pel neFa lure(
                  category = M sconf guredDecorator,
                  reason = " em Cand date already decorated",
                  componentStack = So (context.componentStack))
              } else {
                 emW hDeta ls.copy(presentat on = So (presentat on))
              }
            case So (_) =>
              throw P pel neFa lure(
                category = M sconf guredDecorator,
                reason = " em Cand date got back a non  emPresentat on from decorator",
                componentStack = So (context.componentStack))
            case None =>  emW hDeta ls
          }
        }
        Doma nMarshallerExecutor. nputs(
          query = query,
          cand datesW hDeta ls = f nalResults
        )
      }

      overr de def resultUpdater(
        prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
        executorResult: Doma nMarshallerExecutor.Result[Doma nResultType]
      ): Recom ndat onP pel neResult[Cand date, Result] = prev ousP pel neResult.copy(
        doma nMarshallerResults = So (executorResult)
      )
    }

  def resultS deEffectsStep(
    s deEffects: Seq[P pel neResultS deEffect[Query, Doma nResultType]],
    context: Executor.Context
  ): Step[
    P pel neResultS deEffect. nputs[Query, Doma nResultType],
    P pel neResultS deEffectExecutor.Result
  ] = new Step[
    P pel neResultS deEffect. nputs[Query, Doma nResultType],
    P pel neResultS deEffectExecutor.Result
  ] {
    overr de def  dent f er: P pel neStep dent f er =
      Recom ndat onP pel neConf g.resultS deEffectsStep

    overr de def executorArrow: Arrow[
      P pel neResultS deEffect. nputs[Query, Doma nResultType],
      P pel neResultS deEffectExecutor.Result
    ] = p pel neResultS deEffectExecutor.arrow(s deEffects, context)

    overr de def  nputAdaptor(
      query: Query,
      prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
    ): P pel neResultS deEffect. nputs[Query, Doma nResultType] = {

      // Re-apply decorat ons to t  selected results
      val resultSelectorResults = {
        val decoratorResults = prev ousResult.cand dateDecoratorResult
          .getOrElse(throw  nval dStepStateExcept on( dent f er, "DecoratorStep")).result.map {
            decorat on =>
              decorat on.cand date -> decorat on.presentat on
          }.toMap

        val prev ousSelectorResults = prev ousResult.resultSelectorResults.getOrElse {
          throw  nval dStepStateExcept on( dent f er, "SelectorResults")
        }

        val f lterResults = prev ousResult.postSelect onF lterResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "PostSelect onF lterResults")
          }.result.toSet

        val f lteredSelectorResults = prev ousSelectorResults.selectedCand dates.collect {
          case cand date:  emCand dateW hDeta ls
               f f lterResults.conta ns(cand date.cand date.as nstanceOf[Cand date]) =>
            cand date
        }

        val decoratedSelectedResults = f lteredSelectorResults.map {
          case  emW hDeta ls:  emCand dateW hDeta ls =>
            decoratorResults.get( emW hDeta ls.cand date) match {
              case So (presentat on:  emPresentat on) =>
                 f ( emW hDeta ls.presentat on. sDef ned) {
                  throw P pel neFa lure(
                    category = M sconf guredDecorator,
                    reason = " em Cand date already decorated",
                    componentStack = So (context.componentStack))
                } else {
                   emW hDeta ls.copy(presentat on = So (presentat on))
                }
              case So (_) =>
                throw P pel neFa lure(
                  category = M sconf guredDecorator,
                  reason = " em Cand date got back a non  emPresentat on from decorator",
                  componentStack = So (context.componentStack))
              case None =>  emW hDeta ls
            }
          case  em =>
            // T  branch should be  mposs ble to h  s nce   do a .collect on  emCand dateW hDeta ls
            // as part of execut ng t  cand date p pel nes.
            throw P pel neFa lure(
              category =  llegalStateFa lure,
              reason =
                s"Only  emCand dateW hDeta ls expected  n p pel ne, found: ${ em.toStr ng}",
              componentStack = So (context.componentStack)
            )
        }

        prev ousSelectorResults.copy(selectedCand dates = decoratedSelectedResults)
      }

      val doma nMarshallerResults = prev ousResult.doma nMarshallerResults.getOrElse {
        throw  nval dStepStateExcept on( dent f er, "Doma nMarshallerResults")
      }

      P pel neResultS deEffect. nputs[Query, Doma nResultType](
        query = query,
        selectedCand dates = resultSelectorResults.selectedCand dates,
        rema n ngCand dates = resultSelectorResults.rema n ngCand dates,
        droppedCand dates = resultSelectorResults.droppedCand dates,
        response = doma nMarshallerResults.result.as nstanceOf[Doma nResultType]
      )
    }

    overr de def resultUpdater(
      prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
      executorResult: P pel neResultS deEffectExecutor.Result
    ): Recom ndat onP pel neResult[Cand date, Result] =
      prev ousP pel neResult.copy(resultS deEffectResults = So (executorResult))
  }

  def transportMarshall ngStep(
    transportMarshaller: TransportMarshaller[Doma nResultType, Result],
    context: Executor.Context
  ): Step[
    TransportMarshallerExecutor. nputs[Doma nResultType],
    TransportMarshallerExecutor.Result[Result]
  ] = new Step[TransportMarshallerExecutor. nputs[
    Doma nResultType
  ], TransportMarshallerExecutor.Result[Result]] {
    overr de def  dent f er: P pel neStep dent f er =
      Recom ndat onP pel neConf g.transportMarshallerStep

    overr de def executorArrow: Arrow[TransportMarshallerExecutor. nputs[
      Doma nResultType
    ], TransportMarshallerExecutor.Result[Result]] =
      transportMarshallerExecutor.arrow(transportMarshaller, context)

    overr de def  nputAdaptor(
      query: Query,
      prev ousResult: Recom ndat onP pel neResult[Cand date, Result]
    ): TransportMarshallerExecutor. nputs[Doma nResultType] = {
      val doma nMarshall ngResults = prev ousResult.doma nMarshallerResults.getOrElse {
        throw  nval dStepStateExcept on( dent f er, "Doma nMarshallerResults")
      }

      // S nce t  P pel neResult just uses HasMarshall ng
      val doma nResult = doma nMarshall ngResults.result.as nstanceOf[Doma nResultType]

      TransportMarshallerExecutor. nputs(doma nResult)
    }

    overr de def resultUpdater(
      prev ousP pel neResult: Recom ndat onP pel neResult[Cand date, Result],
      executorResult: TransportMarshallerExecutor.Result[Result]
    ): Recom ndat onP pel neResult[Cand date, Result] = prev ousP pel neResult.copy(
      transportMarshallerResults = So (executorResult),
      result = So (executorResult.result)
    )
  }

  def bu ld(
    parentComponent dent f erStack: Component dent f erStack,
    conf g: Recom ndat onP pel neConf g[
      Query,
      Cand date,
      Doma nResultType,
      Result
    ]
  ): Recom ndat onP pel ne[Query, Cand date, Result] = {
    val p pel ne dent f er = conf g. dent f er

    val context = Executor.Context(
      P pel neFa lureClass f er(
        conf g.fa lureClass f er.orElse(StoppedGateExcept on.class f er(ProductD sabled))),
      parentComponent dent f erStack.push(p pel ne dent f er)
    )

    val decorator = conf g.decorator.map(decorator =>
      Cand dateDecorator.copyW hUpdated dent f er(decorator, p pel ne dent f er))

    val qual yFactorStatus: Qual yFactorStatus =
      Qual yFactorStatus.bu ld(conf g.qual yFactorConf gs)

    val qual yFactorObserverByP pel ne =
      qual yFactorStatus.qual yFactorByP pel ne.mapValues { qual yFactor =>
        qual yFactor.bu ldObserver()
      }

    bu ldGaugesForQual yFactor(p pel ne dent f er, qual yFactorStatus, statsRece ver)

    val cand dateP pel nes: Seq[Cand dateP pel ne[Query]] = conf g.cand dateP pel nes.map {
      p pel neConf g: Cand dateP pel neConf g[Query, _, _, _] =>
        p pel neConf g.bu ld(context.componentStack, cand dateP pel neBu lderFactory)
    }

    val dependentCand dateP pel nes: Seq[Cand dateP pel ne[Query]] =
      conf g.dependentCand dateP pel nes.map {
        p pel neConf g: DependentCand dateP pel neConf g[Query, _, _, _] =>
          p pel neConf g.bu ld(context.componentStack, cand dateP pel neBu lderFactory)
      }

    val scor ngP pel nes: Seq[Scor ngP pel ne[Query, Cand date]] = conf g.scor ngP pel nes.map {
      p pel neConf g: Scor ngP pel neConf g[Query, Cand date] =>
        p pel neConf g.bu ld(context.componentStack, scor ngP pel neBu lderFactory)
    }

    val bu ltSteps = Seq(
      qual yFactorStep(qual yFactorStatus),
      gatesStep(conf g.gates, context),
      fetchQueryFeaturesStep(
        conf g.fetchQueryFeatures,
        Recom ndat onP pel neConf g.fetchQueryFeaturesStep,
        (prev ousP pel neResult, executorResult) =>
          prev ousP pel neResult.copy(queryFeatures = So (executorResult)),
        context
      ),
      fetchQueryFeaturesStep(
        conf g.fetchQueryFeaturesPhase2,
        Recom ndat onP pel neConf g.fetchQueryFeaturesPhase2Step,
        (prev ousP pel neResult, executorResult) =>
          prev ousP pel neResult.copy(
            queryFeaturesPhase2 = So (executorResult),
             rgedAsyncQueryFeatures = So (
              prev ousP pel neResult.queryFeatures
                .getOrElse(throw  nval dStepStateExcept on(
                  Recom ndat onP pel neConf g.fetchQueryFeaturesPhase2Step,
                  "QueryFeatures"))
                .asyncFeatureMap ++ executorResult.asyncFeatureMap)
          ),
        context
      ),
      asyncFeaturesStep(Recom ndat onP pel neConf g.cand dateP pel nesStep, context),
      cand dateP pel nesStep(
        cand dateP pel nes,
        conf g.defaultFa lOpenPol cy,
        conf g.cand dateP pel neFa lOpenPol c es,
        qual yFactorObserverByP pel ne,
        context),
      asyncFeaturesStep(Recom ndat onP pel neConf g.dependentCand dateP pel nesStep, context),
      dependentCand dateP pel nesStep(
        dependentCand dateP pel nes,
        conf g.defaultFa lOpenPol cy,
        conf g.cand dateP pel neFa lOpenPol c es,
        qual yFactorObserverByP pel ne,
        context),
      asyncFeaturesStep(Recom ndat onP pel neConf g.postCand dateP pel nesSelectorsStep, context),
      postCand dateP pel nesSelectorStep(conf g.postCand dateP pel nesSelectors, context),
      asyncFeaturesStep(
        Recom ndat onP pel neConf g.postCand dateP pel nesFeatureHydrat onStep,
        context),
      postCand dateP pel nesFeatureHydrat onStep(
        conf g.postCand dateP pel nesFeatureHydrat on,
        context),
      asyncFeaturesStep(Recom ndat onP pel neConf g.globalF ltersStep, context),
      globalF ltersStep(conf g.globalF lters, context),
      asyncFeaturesStep(Recom ndat onP pel neConf g.scor ngP pel nesStep, context),
      scor ngP pel nesStep(
        scor ngP pel nes,
        context,
        conf g.defaultFa lOpenPol cy,
        conf g.scor ngP pel neFa lOpenPol c es,
        qual yFactorObserverByP pel ne
      ),
      asyncFeaturesStep(Recom ndat onP pel neConf g.resultSelectorsStep, context),
      resultSelectorsStep(conf g.resultSelectors, context),
      asyncFeaturesStep(Recom ndat onP pel neConf g.postSelect onF ltersStep, context),
      postSelect onF ltersStep(conf g.postSelect onF lters, context),
      asyncFeaturesStep(Recom ndat onP pel neConf g.decoratorStep, context),
      decoratorStep(decorator, context),
      doma nMarshall ngStep(conf g.doma nMarshaller, context),
      asyncFeaturesStep(Recom ndat onP pel neConf g.resultS deEffectsStep, context),
      resultS deEffectsStep(conf g.resultS deEffects, context),
      transportMarshall ngStep(conf g.transportMarshaller, context)
    )

    val f nalArrow = bu ldComb nedArrowFromSteps(
      steps = bu ltSteps,
      context = context,
       n  alEmptyResult = Recom ndat onP pel neResult.empty,
      steps nOrderFromConf g = Recom ndat onP pel neConf g.steps nOrder
    )

    val conf gFromBu lder = conf g
    new Recom ndat onP pel ne[Query, Cand date, Result] {
      overr de pr vate[core] val conf g: Recom ndat onP pel neConf g[
        Query,
        Cand date,
        _,
        Result
      ] =
        conf gFromBu lder
      overr de val arrow: Arrow[Query, Recom ndat onP pel neResult[Cand date, Result]] =
        f nalArrow
      overr de val  dent f er: Recom ndat onP pel ne dent f er = p pel ne dent f er
      overr de val alerts: Seq[Alert] = conf g.alerts
      overr de val ch ldren: Seq[Component] =
        conf g.gates ++
          conf g.fetchQueryFeatures ++
          cand dateP pel nes ++
          dependentCand dateP pel nes ++
          conf g.postCand dateP pel nesFeatureHydrat on ++
          conf g.globalF lters ++
          scor ngP pel nes ++
          conf g.postSelect onF lters ++
          conf g.resultS deEffects ++
          decorator.toSeq ++
          Seq(conf g.doma nMarshaller, conf g.transportMarshaller)
    }
  }
}
