package com.tw ter.product_m xer.core.p pel ne.m xer

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.M xerP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.Fa lOpenPol cy
 mport com.tw ter.product_m xer.core.p pel ne. nval dStepStateExcept on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neBu lder
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel ne
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.cand date.DependentCand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureClass f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.ProductD sabled
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorStatus
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutorResults
 mport com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor.Cand dateP pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor.Cand dateP pel neExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.StoppedGateExcept on
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor.P pel neResultS deEffectExecutor
 mport com.tw ter.product_m xer.core.serv ce.qual y_factor_executor.Qual yFactorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.ut l.logg ng.Logg ng

/**
 * M xerP pel neBu lder bu lds [[M xerP pel ne]]s from [[M xerP pel neConf g]]s.
 *
 *   should  nject a [[M xerP pel neBu lderFactory]] and call `.get` to bu ld t se.
 *
 * @see [[M xerP pel neConf g]] for t  descr pt on of t  type para ters
 */
class M xerP pel neBu lder[Query <: P pel neQuery, Doma nResultType <: HasMarshall ng, Result](
  cand dateP pel neExecutor: Cand dateP pel neExecutor,
  gateExecutor: GateExecutor,
  selectorExecutor: SelectorExecutor,
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  doma nMarshallerExecutor: Doma nMarshallerExecutor,
  transportMarshallerExecutor: TransportMarshallerExecutor,
  p pel neResultS deEffectExecutor: P pel neResultS deEffectExecutor,
  cand dateP pel neBu lderFactory: Cand dateP pel neBu lderFactory,
  overr de val statsRece ver: StatsRece ver)
    extends P pel neBu lder[Query]
    w h Logg ng {

  overr de type Underly ngResultType = Result
  overr de type P pel neResultType = M xerP pel neResult[Result]

  def qual yFactorStep(
    qual yFactorStatus: Qual yFactorStatus
  ): Step[Query, Qual yFactorExecutorResult] =
    new Step[Query, Qual yFactorExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er = M xerP pel neConf g.qual yFactorStep

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
        prev ousResult: M xerP pel neResult[Result]
      ): Query = query

      overr de def resultUpdater(
        prev ousP pel neResult: M xerP pel neResult[Result],
        executorResult: Qual yFactorExecutorResult
      ): M xerP pel neResult[Result] =
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
    overr de def  dent f er: P pel neStep dent f er = M xerP pel neConf g.gatesStep

    overr de def executorArrow: Arrow[Query, GateExecutorResult] =
      gateExecutor.arrow(gates, context)

    overr de def  nputAdaptor(query: Query, prev ousResult: M xerP pel neResult[Result]): Query =
      query

    overr de def resultUpdater(
      prev ousP pel neResult: M xerP pel neResult[Result],
      executorResult: GateExecutorResult
    ): M xerP pel neResult[Result] =
      prev ousP pel neResult.copy(gateResult = So (executorResult))
  }

  def fetchQueryFeaturesStep(
    queryFeatureHydrators: Seq[QueryFeatureHydrator[Query]],
    step dent f er: P pel neStep dent f er,
    updater: ResultUpdater[M xerP pel neResult[Result], QueryFeatureHydratorExecutor.Result],
    context: Executor.Context
  ): Step[Query, QueryFeatureHydratorExecutor.Result] =
    new Step[Query, QueryFeatureHydratorExecutor.Result] {
      overr de def  dent f er: P pel neStep dent f er = step dent f er

      overr de def executorArrow: Arrow[Query, QueryFeatureHydratorExecutor.Result] =
        queryFeatureHydratorExecutor.arrow(
          queryFeatureHydrators,
          M xerP pel neConf g.stepsAsyncFeatureHydrat onCanBeCompletedBy,
          context)

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: M xerP pel neResult[Result]
      ): Query = query

      overr de def resultUpdater(
        prev ousP pel neResult: M xerP pel neResult[Result],
        executorResult: QueryFeatureHydratorExecutor.Result
      ): M xerP pel neResult[Result] =
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
        M xerP pel neConf g.asyncFeaturesStep(stepToHydrateFor)

      overr de def executorArrow: Arrow[AsyncFeatureMap, AsyncFeatureMapExecutorResults] =
        asyncFeatureMapExecutor.arrow(
          stepToHydrateFor,
           dent f er,
          context
        )

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: M xerP pel neResult[Result]
      ): AsyncFeatureMap =
        prev ousResult. rgedAsyncQueryFeatures
          .getOrElse(
            throw  nval dStepStateExcept on( dent f er, " rgedAsyncQueryFeatures")
          )

      overr de def resultUpdater(
        prev ousP pel neResult: M xerP pel neResult[Result],
        executorResult: AsyncFeatureMapExecutorResults
      ): M xerP pel neResult[Result] = prev ousP pel neResult.copy(
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
      overr de def  dent f er: P pel neStep dent f er = M xerP pel neConf g.cand dateP pel nesStep

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
        prev ousResult: M xerP pel neResult[Result]
      ): Cand dateP pel ne. nputs[Query] = Cand dateP pel ne. nputs[Query](query, Seq.empty)

      overr de def resultUpdater(
        prev ousP pel neResult: M xerP pel neResult[Result],
        executorResult: Cand dateP pel neExecutorResult
      ): M xerP pel neResult[Result] =
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
        M xerP pel neConf g.dependentCand dateP pel nesStep

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
        prev ousResult: M xerP pel neResult[Result]
      ): Cand dateP pel ne. nputs[Query] = {
        val prev ousCand dates = prev ousResult.cand dateP pel neResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "Cand dates")
          }.cand dateP pel neResults.flatMap(_.result.getOrElse(Seq.empty))
        Cand dateP pel ne. nputs[Query](query, prev ousCand dates)
      }

      overr de def resultUpdater(
        prev ousP pel neResult: M xerP pel neResult[Result],
        executorResult: Cand dateP pel neExecutorResult
      ): M xerP pel neResult[Result] =
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

  def resultSelectorsStep(
    selectors: Seq[Selector[Query]],
    context: Executor.Context
  ): Step[SelectorExecutor. nputs[Query], SelectorExecutorResult] =
    new Step[SelectorExecutor. nputs[Query], SelectorExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er = M xerP pel neConf g.resultSelectorsStep

      overr de def executorArrow: Arrow[SelectorExecutor. nputs[Query], SelectorExecutorResult] =
        selectorExecutor.arrow(selectors, context)

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: M xerP pel neResult[Result]
      ): SelectorExecutor. nputs[Query] = {
        val cand dates = prev ousResult.cand dateP pel neResults
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "Cand dates")
          }.cand dateP pel neResults.flatMap(_.result.getOrElse(Seq.empty))

        val dependentCand dates =
          prev ousResult.dependentCand dateP pel neResults
            .getOrElse {
              throw  nval dStepStateExcept on( dent f er, "DependentCand dates")
            }.cand dateP pel neResults.flatMap(_.result.getOrElse(Seq.empty))

        SelectorExecutor. nputs(
          query = query,
          cand datesW hDeta ls = cand dates ++ dependentCand dates
        )
      }

      overr de def resultUpdater(
        prev ousP pel neResult: M xerP pel neResult[Result],
        executorResult: SelectorExecutorResult
      ): M xerP pel neResult[Result] =
        prev ousP pel neResult.copy(resultSelectorResults = So (executorResult))
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
      overr de def  dent f er: P pel neStep dent f er = M xerP pel neConf g.doma nMarshallerStep

      overr de def executorArrow: Arrow[
        Doma nMarshallerExecutor. nputs[Query],
        Doma nMarshallerExecutor.Result[Doma nResultType]
      ] =
        doma nMarshallerExecutor.arrow(doma nMarshaller, context)

      overr de def  nputAdaptor(
        query: Query,
        prev ousResult: M xerP pel neResult[Result]
      ): Doma nMarshallerExecutor. nputs[Query] = {
        val selectorResults = prev ousResult.resultSelectorResults.getOrElse {
          throw  nval dStepStateExcept on( dent f er, "SelectorResults")
        }

        Doma nMarshallerExecutor. nputs(
          query = query,
          cand datesW hDeta ls = selectorResults.selectedCand dates
        )
      }

      overr de def resultUpdater(
        prev ousP pel neResult: M xerP pel neResult[Result],
        executorResult: Doma nMarshallerExecutor.Result[Doma nResultType]
      ): M xerP pel neResult[Result] = prev ousP pel neResult.copy(
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
    overr de def  dent f er: P pel neStep dent f er = M xerP pel neConf g.resultS deEffectsStep

    overr de def executorArrow: Arrow[
      P pel neResultS deEffect. nputs[Query, Doma nResultType],
      P pel neResultS deEffectExecutor.Result
    ] = p pel neResultS deEffectExecutor.arrow(s deEffects, context)

    overr de def  nputAdaptor(
      query: Query,
      prev ousResult: M xerP pel neResult[Result]
    ): P pel neResultS deEffect. nputs[Query, Doma nResultType] = {

      val selectorResults = prev ousResult.resultSelectorResults.getOrElse {
        throw  nval dStepStateExcept on( dent f er, "SelectorResults")
      }

      val doma nMarshallerResults = prev ousResult.doma nMarshallerResults.getOrElse {
        throw  nval dStepStateExcept on( dent f er, "Doma nMarshallerResults")
      }

      P pel neResultS deEffect. nputs[Query, Doma nResultType](
        query = query,
        selectedCand dates = selectorResults.selectedCand dates,
        rema n ngCand dates = selectorResults.rema n ngCand dates,
        droppedCand dates = selectorResults.droppedCand dates,
        response = doma nMarshallerResults.result.as nstanceOf[Doma nResultType]
      )
    }

    overr de def resultUpdater(
      prev ousP pel neResult: M xerP pel neResult[Result],
      executorResult: P pel neResultS deEffectExecutor.Result
    ): M xerP pel neResult[Result] =
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
    overr de def  dent f er: P pel neStep dent f er = M xerP pel neConf g.transportMarshallerStep

    overr de def executorArrow: Arrow[TransportMarshallerExecutor. nputs[
      Doma nResultType
    ], TransportMarshallerExecutor.Result[Result]] =
      transportMarshallerExecutor.arrow(transportMarshaller, context)

    overr de def  nputAdaptor(
      query: Query,
      prev ousResult: M xerP pel neResult[Result]
    ): TransportMarshallerExecutor. nputs[Doma nResultType] = {
      val doma nMarshall ngResults = prev ousResult.doma nMarshallerResults.getOrElse {
        throw  nval dStepStateExcept on( dent f er, "Doma nMarshallerResults")
      }

      // S nce t  P pel neResult just uses HasMarshall ng
      val doma nResult = doma nMarshall ngResults.result.as nstanceOf[Doma nResultType]

      TransportMarshallerExecutor. nputs(doma nResult)
    }

    overr de def resultUpdater(
      prev ousP pel neResult: M xerP pel neResult[Result],
      executorResult: TransportMarshallerExecutor.Result[Result]
    ): M xerP pel neResult[Result] = prev ousP pel neResult.copy(
      transportMarshallerResults = So (executorResult),
      result = So (executorResult.result)
    )
  }

  def bu ld(
    parentComponent dent f erStack: Component dent f erStack,
    conf g: M xerP pel neConf g[Query, Doma nResultType, Result]
  ): M xerP pel ne[Query, Result] = {

    val p pel ne dent f er = conf g. dent f er

    val context = Executor.Context(
      P pel neFa lureClass f er(
        conf g.fa lureClass f er.orElse(StoppedGateExcept on.class f er(ProductD sabled))),
      parentComponent dent f erStack.push(p pel ne dent f er)
    )

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

    val bu ltSteps = Seq(
      qual yFactorStep(qual yFactorStatus),
      gatesStep(conf g.gates, context),
      fetchQueryFeaturesStep(
        conf g.fetchQueryFeatures,
        M xerP pel neConf g.fetchQueryFeaturesStep,
        (prev ousP pel neResult, executorResult) =>
          prev ousP pel neResult.copy(queryFeatures = So (executorResult)),
        context
      ),
      fetchQueryFeaturesStep(
        conf g.fetchQueryFeaturesPhase2,
        M xerP pel neConf g.fetchQueryFeaturesPhase2Step,
        (prev ousP pel neResult, executorResult) =>
          prev ousP pel neResult.copy(
            queryFeaturesPhase2 = So (executorResult),
             rgedAsyncQueryFeatures = So (
              prev ousP pel neResult.queryFeatures
                .getOrElse(throw  nval dStepStateExcept on(
                  M xerP pel neConf g.fetchQueryFeaturesPhase2Step,
                  "QueryFeatures"))
                .asyncFeatureMap ++ executorResult.asyncFeatureMap)
          ),
        context
      ),
      asyncFeaturesStep(M xerP pel neConf g.cand dateP pel nesStep, context),
      cand dateP pel nesStep(
        cand dateP pel nes,
        conf g.defaultFa lOpenPol cy,
        conf g.fa lOpenPol c es,
        qual yFactorObserverByP pel ne,
        context),
      asyncFeaturesStep(M xerP pel neConf g.dependentCand dateP pel nesStep, context),
      dependentCand dateP pel nesStep(
        dependentCand dateP pel nes,
        conf g.defaultFa lOpenPol cy,
        conf g.fa lOpenPol c es,
        qual yFactorObserverByP pel ne,
        context),
      asyncFeaturesStep(M xerP pel neConf g.resultSelectorsStep, context),
      resultSelectorsStep(conf g.resultSelectors, context),
      doma nMarshall ngStep(conf g.doma nMarshaller, context),
      asyncFeaturesStep(M xerP pel neConf g.resultS deEffectsStep, context),
      resultS deEffectsStep(conf g.resultS deEffects, context),
      transportMarshall ngStep(conf g.transportMarshaller, context)
    )

    val f nalArrow = bu ldComb nedArrowFromSteps(
      steps = bu ltSteps,
      context = context,
       n  alEmptyResult = M xerP pel neResult.empty,
      steps nOrderFromConf g = M xerP pel neConf g.steps nOrder
    )

    val conf gFromBu lder = conf g
    new M xerP pel ne[Query, Result] {
      overr de pr vate[core] val conf g: M xerP pel neConf g[Query, _, Result] = conf gFromBu lder
      overr de val arrow: Arrow[Query, M xerP pel neResult[Result]] = f nalArrow
      overr de val  dent f er: M xerP pel ne dent f er = p pel ne dent f er
      overr de val alerts: Seq[Alert] = conf g.alerts
      overr de val ch ldren: Seq[Component] =
        conf g.gates ++
          conf g.fetchQueryFeatures ++
          cand dateP pel nes ++
          dependentCand dateP pel nes ++
          conf g.resultS deEffects ++
          Seq(conf g.doma nMarshaller, conf g.transportMarshaller)
    }
  }
}
