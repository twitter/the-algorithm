package com.tw ter.product_m xer.core.p pel ne.cand date

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseQueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.BaseCand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.gate.ParamGate
 mport com.tw ter.product_m xer.core.gate.ParamGate.EnabledGateSuff x
 mport com.tw ter.product_m xer.core.gate.ParamGate.SupportedCl entGateSuff x
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne. nval dStepStateExcept on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neBu lder
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.ClosedGate
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureClass f er
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutorResults
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_s ce_executor.Cand dateS ceExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_s ce_executor.Cand dateS ceExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_s ce_executor.Fetc dCand dateW hFeatures
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.StoppedGateExcept on
 mport com.tw ter.product_m xer.core.serv ce.group_results_executor.GroupResultsExecutor
 mport com.tw ter.product_m xer.core.serv ce.group_results_executor.GroupResultsExecutor nput
 mport com.tw ter.product_m xer.core.serv ce.group_results_executor.GroupResultsExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject

class Cand dateP pel neBu lder[
  Query <: P pel neQuery,
  Cand dateS ceQuery,
  Cand dateS ceResult,
  Result <: Un versalNoun[Any]] @ nject() (
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  cand dateDecoratorExecutor: Cand dateDecoratorExecutor,
  cand dateFeatureHydratorExecutor: Cand dateFeatureHydratorExecutor,
  cand dateS ceExecutor: Cand dateS ceExecutor,
  groupResultsExecutor: GroupResultsExecutor,
  f lterExecutor: F lterExecutor,
  gateExecutor: GateExecutor,
  overr de val statsRece ver: StatsRece ver)
    extends P pel neBu lder[Cand dateP pel ne. nputs[Query]]
    w h Logg ng {

  overr de type Underly ngResultType = Seq[Cand dateW hDeta ls]
  overr de type P pel neResultType =  nter d ateCand dateP pel neResult[Result]

  def bu ld(
    parentComponent dent f erStack: Component dent f erStack,
    conf g: BaseCand dateP pel neConf g[
      Query,
      Cand dateS ceQuery,
      Cand dateS ceResult,
      Result
    ]
  ): Cand dateP pel ne[Query] = {

    val p pel ne dent f er = conf g. dent f er
    val cand dateS ce dent f er = conf g.cand dateS ce. dent f er

    val context = Executor.Context(
      P pel neFa lureClass f er(
        conf g.fa lureClass f er.orElse(StoppedGateExcept on.class f er(ClosedGate))),
      parentComponent dent f erStack.push(p pel ne dent f er)
    )

    val enabledGateOpt = conf g.enabledDec derParam.map { dec derParam =>
      ParamGate(p pel ne dent f er + EnabledGateSuff x, dec derParam)
    }
    val supportedCl entGateOpt = conf g.supportedCl entParam.map { param =>
      ParamGate(p pel ne dent f er + SupportedCl entGateSuff x, param)
    }

    /**
     * Evaluate enabled dec der gate f rst s nce  f  's off, t re  s no reason to proceed
     * Next evaluate supported cl ent feature sw ch gate, follo d by custo r conf gured gates
     */
    val allGates = enabledGateOpt.toSeq ++ supportedCl entGateOpt.toSeq ++ conf g.gates

    // Dynam cally replace t   dent f er of both transfor rs  f conf g used t   nl ne constructor
    // wh ch sets a default  dent f er.   need to do t  to ensure un queness of  dent f ers.
    val queryTransfor r = BaseCand dateP pel neQueryTransfor r.copyW hUpdated dent f er(
      conf g.queryTransfor r,
      p pel ne dent f er)

    val resultsTransfor r = Cand dateP pel neResultsTransfor r.copyW hUpdated dent f er(
      conf g.resultTransfor r,
      p pel ne dent f er)

    val decorator = conf g.decorator.map(decorator =>
      Cand dateDecorator.copyW hUpdated dent f er(decorator, p pel ne dent f er))

    val GatesStep = new Step[Query, GateExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er = Cand dateP pel neConf g.gatesStep

      overr de def executorArrow: Arrow[Query, GateExecutorResult] = {
        gateExecutor.arrow(allGates, context)
      }

      overr de def  nputAdaptor(
        query: Cand dateP pel ne. nputs[Query],
        prev ousResult:  nter d ateCand dateP pel neResult[Result]
      ): Query =
        query.query

      overr de def resultUpdater(
        prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
        executorResult: GateExecutorResult
      ):  nter d ateCand dateP pel neResult[Result] =
        prev ousP pel neResult.copy(underly ngResult =
          prev ousP pel neResult.underly ngResult.copy(gateResult = So (executorResult)))
    }

    def queryFeatureHydrat onStep(
      queryFeatureHydrators: Seq[BaseQueryFeatureHydrator[Query, _]],
      step dent f er: P pel neStep dent f er,
      updater: ResultUpdater[Cand dateP pel neResult, QueryFeatureHydratorExecutor.Result]
    ): Step[Query, QueryFeatureHydratorExecutor.Result] =
      new Step[Query, QueryFeatureHydratorExecutor.Result] {
        overr de def  dent f er: P pel neStep dent f er = step dent f er

        overr de def executorArrow: Arrow[Query, QueryFeatureHydratorExecutor.Result] =
          queryFeatureHydratorExecutor.arrow(
            queryFeatureHydrators,
            Cand dateP pel neConf g.stepsAsyncFeatureHydrat onCanBeCompletedBy,
            context)

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): Query = query.query

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: QueryFeatureHydratorExecutor.Result
        ):  nter d ateCand dateP pel neResult[Result] =
          prev ousP pel neResult.copy(
            underly ngResult = updater(prev ousP pel neResult.underly ngResult, executorResult))

        overr de def queryUpdater(
          query: Cand dateP pel ne. nputs[Query],
          executorResult: QueryFeatureHydratorExecutor.Result
        ): Cand dateP pel ne. nputs[Query] =
          Cand dateP pel ne. nputs(
            query.query
              .w hFeatureMap(
                query.query.features.getOrElse(
                  FeatureMap.empty) ++ executorResult.featureMap).as nstanceOf[Query],
            query.ex st ngCand dates)
      }

    def asyncFeaturesStep(
      stepToHydrateFor: P pel neStep dent f er,
      context: Executor.Context
    ): Step[AsyncFeatureMap, AsyncFeatureMapExecutorResults] =
      new Step[AsyncFeatureMap, AsyncFeatureMapExecutorResults] {
        overr de def  dent f er: P pel neStep dent f er =
          Cand dateP pel neConf g.asyncFeaturesStep(stepToHydrateFor)

        overr de def executorArrow: Arrow[AsyncFeatureMap, AsyncFeatureMapExecutorResults] =
          asyncFeatureMapExecutor.arrow(stepToHydrateFor,  dent f er, context)

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): AsyncFeatureMap =
          prev ousResult.underly ngResult. rgedAsyncQueryFeatures
            .getOrElse(
              throw  nval dStepStateExcept on( dent f er, " rgedAsyncQueryFeatures")
            )

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: AsyncFeatureMapExecutorResults
        ):  nter d ateCand dateP pel neResult[Result] =
          prev ousP pel neResult.copy(
            underly ngResult =
              prev ousP pel neResult.underly ngResult.copy(asyncFeatureHydrat onResults =
                prev ousP pel neResult.underly ngResult.asyncFeatureHydrat onResults match {
                  case So (ex st ngResults) => So (ex st ngResults ++ executorResult)
                  case None => So (executorResult)
                }))

        overr de def queryUpdater(
          query: Cand dateP pel ne. nputs[Query],
          executorResult: AsyncFeatureMapExecutorResults
        ): Cand dateP pel ne. nputs[Query] =
           f (executorResult.featureMapsByStep
              .getOrElse(stepToHydrateFor, FeatureMap.empty). sEmpty) {
            query
          } else {
            val updatedQuery = query.query
              .w hFeatureMap(
                query.query.features
                  .getOrElse(FeatureMap.empty) ++ executorResult.featureMapsByStep(
                  stepToHydrateFor)).as nstanceOf[Query]
            Cand dateP pel ne. nputs(updatedQuery, query.ex st ngCand dates)
          }
      }

    val Cand dateS ceStep =
      new Step[Query, Cand dateS ceExecutorResult[Result]] {
        overr de def  dent f er: P pel neStep dent f er =
          Cand dateP pel neConf g.cand dateS ceStep

        overr de def executorArrow: Arrow[
          Query,
          Cand dateS ceExecutorResult[Result]
        ] =
          cand dateS ceExecutor
            .arrow(
              conf g.cand dateS ce,
              queryTransfor r,
              resultsTransfor r,
              conf g.featuresFromCand dateS ceTransfor rs,
              context
            )

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): Query =
          query.query

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: Cand dateS ceExecutorResult[Result]
        ):  nter d ateCand dateP pel neResult[Result] =
          prev ousP pel neResult.copy(underly ngResult =
            prev ousP pel neResult.underly ngResult.copy(
              cand dateS ceResult =
                So (executorResult.as nstanceOf[Cand dateS ceExecutorResult[Un versalNoun[Any]]])
            ))

        overr de def queryUpdater(
          query: Cand dateP pel ne. nputs[Query],
          executorResult: Cand dateS ceExecutorResult[Result]
        ): Cand dateP pel ne. nputs[Query] = {
          val updatedFeatureMap =
            query.query.features
              .getOrElse(FeatureMap.empty) ++ executorResult.cand dateS ceFeatureMap
          val updatedQuery = query.query
            .w hFeatureMap(updatedFeatureMap).as nstanceOf[Query]
          Cand dateP pel ne. nputs(updatedQuery, query.ex st ngCand dates)
        }
      }

    val PreF lterFeatureHydrat onPhase1Step =
      new Step[
        Cand dateFeatureHydratorExecutor. nputs[Query, Result],
        Cand dateFeatureHydratorExecutorResult[Result]
      ] {
        overr de def  dent f er: P pel neStep dent f er =
          Cand dateP pel neConf g.preF lterFeatureHydrat onPhase1Step

        overr de def executorArrow: Arrow[
          Cand dateFeatureHydratorExecutor. nputs[Query, Result],
          Cand dateFeatureHydratorExecutorResult[Result]
        ] =
          cand dateFeatureHydratorExecutor.arrow(conf g.preF lterFeatureHydrat onPhase1, context)

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): Cand dateFeatureHydratorExecutor. nputs[Query, Result] = {
          val cand dateS ceExecutorResult =
            prev ousResult.underly ngResult.cand dateS ceResult.getOrElse {
              throw  nval dStepStateExcept on( dent f er, "Cand dateS ceResult")
            }
          Cand dateFeatureHydratorExecutor. nputs(
            query.query,
            cand dateS ceExecutorResult.cand dates
              .as nstanceOf[Seq[Cand dateW hFeatures[Result]]])
        }

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: Cand dateFeatureHydratorExecutorResult[Result]
        ):  nter d ateCand dateP pel neResult[Result] = {
          val cand dateS ceExecutorResult =
            prev ousP pel neResult.underly ngResult.cand dateS ceResult.getOrElse {
              throw  nval dStepStateExcept on( dent f er, "Cand dateS ceResult")
            }

          val featureMapsFromPreF lter = executorResult.results.map { result =>
            result.cand date -> result.features
          }.toMap

          val  rgedFeatureMaps = cand dateS ceExecutorResult.cand dates.map { cand date =>
            val cand dateFeatureMap = cand date.features
            val preF lterFeatureMap =
              featureMapsFromPreF lter.getOrElse(
                cand date.cand date.as nstanceOf[Result],
                FeatureMap.empty)

            cand date.cand date.as nstanceOf[Result] -> (cand dateFeatureMap ++ preF lterFeatureMap)
          }.toMap

          prev ousP pel neResult.copy(
            underly ngResult = prev ousP pel neResult.underly ngResult.copy(
              preF lterHydrat onResult = So (
                executorResult
                  .as nstanceOf[Cand dateFeatureHydratorExecutorResult[Un versalNoun[Any]]])
            ),
            featureMaps = So ( rgedFeatureMaps)
          )
        }
      }

    val PreF lterFeatureHydrat onPhase2Step =
      new Step[
        Cand dateFeatureHydratorExecutor. nputs[Query, Result],
        Cand dateFeatureHydratorExecutorResult[Result]
      ] {
        overr de def  dent f er: P pel neStep dent f er =
          Cand dateP pel neConf g.preF lterFeatureHydrat onPhase2Step

        overr de def executorArrow: Arrow[
          Cand dateFeatureHydratorExecutor. nputs[Query, Result],
          Cand dateFeatureHydratorExecutorResult[Result]
        ] =
          cand dateFeatureHydratorExecutor.arrow(conf g.preF lterFeatureHydrat onPhase2, context)

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): Cand dateFeatureHydratorExecutor. nputs[Query, Result] = {
          val cand dates = prev ousResult.underly ngResult.preF lterHydrat onResult.getOrElse {
            throw  nval dStepStateExcept on( dent f er, "PreF lterHydrat onResult")
          }.results
          Cand dateFeatureHydratorExecutor. nputs(
            query.query,
            cand dates.as nstanceOf[Seq[Cand dateW hFeatures[Result]]]
          )
        }

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: Cand dateFeatureHydratorExecutorResult[Result]
        ):  nter d ateCand dateP pel neResult[Result] = {

          val featureMapsFromPreF lterPhase2 = executorResult.results.map { result =>
            result.cand date -> result.features
          }.toMap

          val  rgedFeatureMaps = prev ousP pel neResult.featureMaps
            .getOrElse(throw  nval dStepStateExcept on( dent f er, "FeatureMaps"))
            .map {
              case (cand date, featureMap) =>
                val preF lterPhase2FeatureMap =
                  featureMapsFromPreF lterPhase2.getOrElse(cand date, FeatureMap.empty)

                cand date -> (featureMap ++ preF lterPhase2FeatureMap)
            }

          prev ousP pel neResult.copy(
            underly ngResult = prev ousP pel neResult.underly ngResult.copy(
              preF lterHydrat onResultPhase2 = So (
                executorResult
                  .as nstanceOf[Cand dateFeatureHydratorExecutorResult[Un versalNoun[Any]]])
            ),
            featureMaps = So ( rgedFeatureMaps)
          )
        }
      }

    val F ltersStep =
      new Step[(Query, Seq[Cand dateW hFeatures[Result]]), F lterExecutorResult[Result]] {
        overr de def  dent f er: P pel neStep dent f er = Cand dateP pel neConf g.f ltersStep

        overr de def executorArrow: Arrow[
          (Query, Seq[Cand dateW hFeatures[Result]]),
          F lterExecutorResult[
            Result
          ]
        ] =
          f lterExecutor.arrow(conf g.f lters, context)

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): (Query, Seq[Cand dateW hFeatures[Result]]) = {
          val cand dates =
            prev ousResult.underly ngResult.cand dateS ceResult
              .getOrElse {
                throw  nval dStepStateExcept on( dent f er, "Cand dateS ceResult")
              }.cand dates.map(_.cand date).as nstanceOf[Seq[Result]]

          val featureMaps = prev ousResult.featureMaps
            .getOrElse(throw  nval dStepStateExcept on( dent f er, "FeatureMaps"))

          (
            query.query,
            cand dates.map(cand date =>
              Cand dateW hFeatures mpl(
                cand date,
                featureMaps.getOrElse(cand date, FeatureMap.empty))))
        }

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: F lterExecutorResult[Result]
        ):  nter d ateCand dateP pel neResult[Result] =
          prev ousP pel neResult.copy(underly ngResult =
            prev ousP pel neResult.underly ngResult.copy(
              f lterResult =
                So (executorResult.as nstanceOf[F lterExecutorResult[Un versalNoun[Any]]])
            ))
      }

    val PostF lterFeatureHydrat onStep =
      new Step[
        Cand dateFeatureHydratorExecutor. nputs[Query, Result],
        Cand dateFeatureHydratorExecutorResult[Result]
      ] {
        overr de def  dent f er: P pel neStep dent f er =
          Cand dateP pel neConf g.postF lterFeatureHydrat onStep

        overr de def executorArrow: Arrow[
          Cand dateFeatureHydratorExecutor. nputs[Query, Result],
          Cand dateFeatureHydratorExecutorResult[Result]
        ] =
          cand dateFeatureHydratorExecutor.arrow(conf g.postF lterFeatureHydrat on, context)

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): Cand dateFeatureHydratorExecutor. nputs[Query, Result] = {
          val f lterResult = prev ousResult.underly ngResult.f lterResult
            .getOrElse(
              throw  nval dStepStateExcept on( dent f er, "F lterResult")
            ).result.as nstanceOf[Seq[Result]]

          val featureMaps = prev ousResult.featureMaps.getOrElse(
            throw  nval dStepStateExcept on( dent f er, "FeatureMaps")
          )

          val f lteredCand dates = f lterResult.map { cand date =>
            Cand dateW hFeatures mpl(cand date, featureMaps.getOrElse(cand date, FeatureMap.empty))
          }
          Cand dateFeatureHydratorExecutor. nputs(query.query, f lteredCand dates)
        }

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: Cand dateFeatureHydratorExecutorResult[Result]
        ):  nter d ateCand dateP pel neResult[Result] = {
          val f lterResult = prev ousP pel neResult.underly ngResult.f lterResult
            .getOrElse(
              throw  nval dStepStateExcept on( dent f er, "F lterResult")
            ).result.as nstanceOf[Seq[Result]]

          val featureMaps = prev ousP pel neResult.featureMaps.getOrElse(
            throw  nval dStepStateExcept on( dent f er, "FeatureMaps")
          )

          val postF lterFeatureMaps = executorResult.results.map { result =>
            result.cand date -> result.features
          }.toMap

          val  rgedFeatureMaps = f lterResult.map { cand date =>
            cand date ->
              (featureMaps
                .getOrElse(cand date, FeatureMap.empty) ++ postF lterFeatureMaps.getOrElse(
                cand date,
                FeatureMap.empty))
          }.toMap

          prev ousP pel neResult.copy(
            underly ngResult = prev ousP pel neResult.underly ngResult.copy(
              postF lterHydrat onResult = So (
                executorResult
                  .as nstanceOf[Cand dateFeatureHydratorExecutorResult[Un versalNoun[Any]]])
            ),
            featureMaps = So ( rgedFeatureMaps)
          )
        }
      }

    val ScorersStep =
      new Step[
        Cand dateFeatureHydratorExecutor. nputs[Query, Result],
        Cand dateFeatureHydratorExecutorResult[Result]
      ] {
        overr de def  dent f er: P pel neStep dent f er = Cand dateP pel neConf g.scorersStep

        overr de def executorArrow: Arrow[
          Cand dateFeatureHydratorExecutor. nputs[Query, Result],
          Cand dateFeatureHydratorExecutorResult[Result]
        ] =
          cand dateFeatureHydratorExecutor.arrow(conf g.scorers, context)

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): Cand dateFeatureHydratorExecutor. nputs[Query, Result] = {
          val f lterResult = prev ousResult.underly ngResult.f lterResult
            .getOrElse(
              throw  nval dStepStateExcept on( dent f er, "F lterResult")
            ).result.as nstanceOf[Seq[Result]]

          val featureMaps = prev ousResult.featureMaps.getOrElse(
            throw  nval dStepStateExcept on( dent f er, "FeatureMaps")
          )

          val f lteredCand dates = f lterResult.map { cand date =>
            Cand dateW hFeatures mpl(cand date, featureMaps.getOrElse(cand date, FeatureMap.empty))
          }
          Cand dateFeatureHydratorExecutor. nputs(query.query, f lteredCand dates)
        }

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: Cand dateFeatureHydratorExecutorResult[Result]
        ):  nter d ateCand dateP pel neResult[Result] = {
          val f lterResult = prev ousP pel neResult.underly ngResult.f lterResult
            .getOrElse(
              throw  nval dStepStateExcept on( dent f er, "F lterResult")
            ).result.as nstanceOf[Seq[Result]]

          val featureMaps = prev ousP pel neResult.featureMaps.getOrElse(
            throw  nval dStepStateExcept on( dent f er, "FeatureMaps")
          )

          val scor ngFeatureMaps = executorResult.results.map { result =>
            result.cand date -> result.features
          }.toMap

          val  rgedFeatureMaps = f lterResult.map { cand date =>
            cand date ->
              (featureMaps
                .getOrElse(cand date, FeatureMap.empty) ++ scor ngFeatureMaps.getOrElse(
                cand date,
                FeatureMap.empty))
          }.toMap

          prev ousP pel neResult.copy(
            underly ngResult = prev ousP pel neResult.underly ngResult.copy(
              scorersResult = So (
                executorResult
                  .as nstanceOf[Cand dateFeatureHydratorExecutorResult[Un versalNoun[Any]]])
            ),
            featureMaps = So ( rgedFeatureMaps)
          )
        }
      }

    val Decorat onStep =
      new Step[(Query, Seq[Cand dateW hFeatures[Result]]), Cand dateDecoratorExecutorResult] {
        overr de def  dent f er: P pel neStep dent f er = Cand dateP pel neConf g.decoratorStep

        overr de def executorArrow: Arrow[
          (Query, Seq[Cand dateW hFeatures[Result]]),
          Cand dateDecoratorExecutorResult
        ] =
          cand dateDecoratorExecutor.arrow(decorator, context)

        overr de def  nputAdaptor(
          query: Cand dateP pel ne. nputs[Query],
          prev ousResult:  nter d ateCand dateP pel neResult[Result]
        ): (Query, Seq[Cand dateW hFeatures[Result]]) = {
          val keptCand dates = prev ousResult.underly ngResult.f lterResult
            .getOrElse {
              throw  nval dStepStateExcept on( dent f er, "F lterResult")
            }.result.as nstanceOf[Seq[Result]]

          val featureMaps = prev ousResult.featureMaps.getOrElse {
            throw  nval dStepStateExcept on( dent f er, "FeatureMaps")
          }

          (
            query.query,
            keptCand dates.map(cand date =>
              Cand dateW hFeatures mpl(
                cand date,
                featureMaps.getOrElse(cand date, FeatureMap.empty))))
        }

        overr de def resultUpdater(
          prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
          executorResult: Cand dateDecoratorExecutorResult
        ):  nter d ateCand dateP pel neResult[Result] =
          prev ousP pel neResult.copy(underly ngResult =
            prev ousP pel neResult.underly ngResult.copy(
              cand dateDecoratorResult = So (executorResult)
            ))
      }

    /**
     * ResultStep  s a synchronous step that bas cally takes t  outputs from t  ot r steps, groups modules,
     * and puts th ngs  nto t  f nal result object
     */
    val ResultStep = new Step[GroupResultsExecutor nput[Result], GroupResultsExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er = Cand dateP pel neConf g.resultStep

      overr de def executorArrow: Arrow[
        GroupResultsExecutor nput[Result],
        GroupResultsExecutorResult
      ] = groupResultsExecutor.arrow(p pel ne dent f er, cand dateS ce dent f er, context)

      overr de def  nputAdaptor(
        query: Cand dateP pel ne. nputs[Query],
        prev ousResult:  nter d ateCand dateP pel neResult[Result]
      ): GroupResultsExecutor nput[Result] = {

        val underly ng = prev ousResult.underly ngResult

        val keptCand dates = underly ng.f lterResult
          .getOrElse(
            throw  nval dStepStateExcept on( dent f er, "F lterResult")
          ).result.as nstanceOf[Seq[Result]]

        val decorat ons = underly ng.cand dateDecoratorResult
          .getOrElse(
            throw  nval dStepStateExcept on( dent f er, "Decorat onResult")
          ).result.map(decorat on => decorat on.cand date -> decorat on.presentat on).toMap

        val comb nedFeatureMaps: Map[Result, FeatureMap] = prev ousResult.featureMaps.getOrElse(
          throw  nval dStepStateExcept on( dent f er, "FeatureMaps"))

        val f lteredCand dates = keptCand dates.map { cand date =>
          val updatedMap = comb nedFeatureMaps
            .get(cand date).getOrElse(FeatureMap.empty)
          Fetc dCand dateW hFeatures(cand date, updatedMap)
        }

        GroupResultsExecutor nput(
          cand dates = f lteredCand dates,
          decorat ons = decorat ons
        )
      }

      overr de def resultUpdater(
        prev ousP pel neResult:  nter d ateCand dateP pel neResult[Result],
        executorResult: GroupResultsExecutorResult
      ):  nter d ateCand dateP pel neResult[Result] =
        prev ousP pel neResult.copy(underly ngResult = prev ousP pel neResult.underly ngResult
          .copy(result = So (executorResult.cand datesW hDeta ls)))
    }

    val bu ltSteps = Seq(
      GatesStep,
      queryFeatureHydrat onStep(
        conf g.queryFeatureHydrat on,
        Cand dateP pel neConf g.fetchQueryFeaturesStep,
        (p pel neResult, executorResult) =>
          p pel neResult.copy(queryFeatures = So (executorResult))
      ),
      queryFeatureHydrat onStep(
        conf g.queryFeatureHydrat onPhase2,
        Cand dateP pel neConf g.fetchQueryFeaturesPhase2Step,
        (p pel neResult, executorResult) =>
          p pel neResult.copy(
            queryFeaturesPhase2 = So (executorResult),
             rgedAsyncQueryFeatures = So (
              p pel neResult.queryFeatures
                .getOrElse(
                  throw  nval dStepStateExcept on(
                    Cand dateP pel neConf g.fetchQueryFeaturesPhase2Step,
                    "QueryFeatures")
                ).asyncFeatureMap ++ executorResult.asyncFeatureMap)
          )
      ),
      asyncFeaturesStep(Cand dateP pel neConf g.cand dateS ceStep, context),
      Cand dateS ceStep,
      asyncFeaturesStep(Cand dateP pel neConf g.preF lterFeatureHydrat onPhase1Step, context),
      PreF lterFeatureHydrat onPhase1Step,
      asyncFeaturesStep(Cand dateP pel neConf g.preF lterFeatureHydrat onPhase2Step, context),
      PreF lterFeatureHydrat onPhase2Step,
      asyncFeaturesStep(Cand dateP pel neConf g.f ltersStep, context),
      F ltersStep,
      asyncFeaturesStep(Cand dateP pel neConf g.postF lterFeatureHydrat onStep, context),
      PostF lterFeatureHydrat onStep,
      asyncFeaturesStep(Cand dateP pel neConf g.scorersStep, context),
      ScorersStep,
      asyncFeaturesStep(Cand dateP pel neConf g.decoratorStep, context),
      Decorat onStep,
      ResultStep
    )

    /** T  ma n execut on log c for t  Cand date P pel ne. */
    val f nalArrow: Arrow[Cand dateP pel ne. nputs[Query], Cand dateP pel neResult] =
      bu ldComb nedArrowFromSteps(
        steps = bu ltSteps,
        context = context,
         n  alEmptyResult =
           nter d ateCand dateP pel neResult.empty[Result](conf g.cand dateS ce. dent f er),
        steps nOrderFromConf g = Cand dateP pel neConf g.steps nOrder
      ).map(_.underly ngResult)

    val conf gFromBu lder = conf g
    new Cand dateP pel ne[Query] {
      overr de pr vate[core] val conf g: BaseCand dateP pel neConf g[Query, _, _, _] =
        conf gFromBu lder
      overr de val arrow: Arrow[Cand dateP pel ne. nputs[Query], Cand dateP pel neResult] =
        f nalArrow
      overr de val  dent f er: Cand dateP pel ne dent f er = p pel ne dent f er
      overr de val alerts: Seq[Alert] = conf g.alerts
      overr de val ch ldren: Seq[Component] =
        allGates ++
          conf g.queryFeatureHydrat on ++
          Seq(queryTransfor r, conf g.cand dateS ce, resultsTransfor r) ++
          conf g.featuresFromCand dateS ceTransfor rs ++
          decorator.toSeq ++
          conf g.preF lterFeatureHydrat onPhase1 ++
          conf g.f lters ++
          conf g.postF lterFeatureHydrat on ++
          conf g.scorers
    }
  }

  pr vate case class Cand dateW hFeatures mpl(cand date: Result, features: FeatureMap)
      extends Cand dateW hFeatures[Result]
}
