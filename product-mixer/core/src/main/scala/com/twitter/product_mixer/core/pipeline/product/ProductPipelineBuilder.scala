package com.tw ter.product_m xer.core.p pel ne.product

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.f nagle.transport.Transport
 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cy
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.gate.DenyLoggedOutUsersGate
 mport com.tw ter.product_m xer.core.gate.ParamGate
 mport com.tw ter.product_m xer.core.gate.ParamGate.EnabledGateSuff x
 mport com.tw ter.product_m xer.core.gate.ParamGate.SupportedCl entGateSuff x
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.ProductP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne. nval dStepStateExcept on
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neBu lder
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureClass f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.ProductD sabled
 mport com.tw ter.product_m xer.core.p pel ne.recom ndat on.Recom ndat onP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.p pel ne.recom ndat on.Recom ndat onP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.recom ndat on.Recom ndat onP pel neResult
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorStatus
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.StoppedGateExcept on
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_execut on_logger.P pel neExecut onLogger
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_executor.P pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_executor.P pel neExecutorRequest
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_executor.P pel neExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_selector_executor.P pel neSelectorExecutor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_selector_executor.P pel neSelectorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.qual y_factor_executor.Qual yFactorExecutorResult
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.str ngcenter.cl ent.Str ngCenterRequestContext
 mport com.tw ter.str ngcenter.cl ent.st ch.Str ngCenterRequestContextLetter
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.logg ng.Logg ng
 mport org.slf4j.MDC

class ProductP pel neBu lder[TRequest <: Request, Query <: P pel neQuery, Response](
  gateExecutor: GateExecutor,
  p pel neSelectorExecutor: P pel neSelectorExecutor,
  p pel neExecutor: P pel neExecutor,
  m xerP pel neBu lderFactory: M xerP pel neBu lderFactory,
  recom ndat onP pel neBu lderFactory: Recom ndat onP pel neBu lderFactory,
  overr de val statsRece ver: StatsRece ver,
  p pel neExecut onLogger: P pel neExecut onLogger)
    extends P pel neBu lder[ProductP pel neRequest[TRequest]]
    w h Logg ng { bu lder =>

  overr de type Underly ngResultType = Response
  overr de type P pel neResultType = ProductP pel neResult[Response]

  /**
   * Query Transfor r Step  s  mple nted  nl ne  nstead of us ng an executor.
   *
   *  's a s mple, synchronous step that executes t  query transfor r.
   *
   * S nce t  output of t  transfor r  s used  n mult ple ot r steps (Gate, P pel ne Execut on),
   *  've promoted t  transfor r to a step so that  's outputs can be reused eas ly.
   */
  def p pel neQueryTransfor rStep(
    queryTransfor r: (TRequest, Params) => Query,
    context: Executor.Context
  ): Step[ProductP pel neRequest[TRequest], Query] =
    new Step[ProductP pel neRequest[TRequest], Query] {

      overr de def  dent f er: P pel neStep dent f er =
        ProductP pel neConf g.p pel neQueryTransfor rStep

      overr de def executorArrow: Arrow[ProductP pel neRequest[TRequest], Query] = {
        wrapW hErrorHandl ng(context,  dent f er)(
          Arrow.map[ProductP pel neRequest[TRequest], Query] {
            case ProductP pel neRequest(request, params) => queryTransfor r(request, params)
          }
        )
      }

      overr de def  nputAdaptor(
        query: ProductP pel neRequest[TRequest],
        prev ousResult: ProductP pel neResult[Response]
      ): ProductP pel neRequest[TRequest] = query

      overr de def resultUpdater(
        prev ousP pel neResult: ProductP pel neResult[Response],
        executorResult: Query
      ): ProductP pel neResult[Response] =
        prev ousP pel neResult.copy(transfor dQuery = So (executorResult))
    }

  def qual yFactorStep(
    qual yFactorStatus: Qual yFactorStatus
  ): Step[Query, Qual yFactorExecutorResult] = {
    new Step[Query, Qual yFactorExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er = ProductP pel neConf g.qual yFactorStep

      overr de def executorArrow: Arrow[Query, Qual yFactorExecutorResult] =
        Arrow
          .map[Query, Qual yFactorExecutorResult] { _ =>
            Qual yFactorExecutorResult(
              p pel neQual yFactors =
                qual yFactorStatus.qual yFactorByP pel ne.mapValues(_.currentValue)
            )
          }

      overr de def  nputAdaptor(
        query: ProductP pel neRequest[TRequest],
        prev ousResult: ProductP pel neResult[Response]
      ): Query = prev ousResult.transfor dQuery
        .getOrElse {
          throw  nval dStepStateExcept on( dent f er, "Transfor dQuery")
        }.as nstanceOf[Query]

      overr de def resultUpdater(
        prev ousP pel neResult: ProductP pel neResult[Response],
        executorResult: Qual yFactorExecutorResult
      ): ProductP pel neResult[Response] = {
        prev ousP pel neResult.copy(
          transfor dQuery = prev ousP pel neResult.transfor dQuery.map {
            case queryW hQual yFactor: HasQual yFactorStatus =>
              queryW hQual yFactor
                .w hQual yFactorStatus(qual yFactorStatus).as nstanceOf[Query]
            case query =>
              query
          },
          qual yFactorResult = So (executorResult)
        )
      }
    }
  }

  def gatesStep(
    gates: Seq[Gate[Query]],
    context: Executor.Context
  ): Step[Query, GateExecutorResult] = new Step[Query, GateExecutorResult] {
    overr de def  dent f er: P pel neStep dent f er = ProductP pel neConf g.gatesStep

    overr de def executorArrow: Arrow[Query, GateExecutorResult] = {
      gateExecutor.arrow(gates, context)
    }

    overr de def  nputAdaptor(
      query: ProductP pel neRequest[TRequest],
      prev ousResult: ProductP pel neResult[Response]
    ): Query = prev ousResult.transfor dQuery
      .getOrElse {
        throw  nval dStepStateExcept on( dent f er, "Transfor dQuery")
      }.as nstanceOf[Query]

    overr de def resultUpdater(
      prev ousP pel neResult: ProductP pel neResult[Response],
      executorResult: GateExecutorResult
    ): ProductP pel neResult[Response] =
      prev ousP pel neResult.copy(gateResult = So (executorResult))
  }

  def p pel neSelectorStep(
    p pel neBy dent fer: Map[Component dent f er, P pel ne[Query, Response]],
    p pel neSelector: Query => Component dent f er,
    context: Executor.Context
  ): Step[Query, P pel neSelectorExecutorResult] =
    new Step[Query, P pel neSelectorExecutorResult] {
      overr de def  dent f er: P pel neStep dent f er = ProductP pel neConf g.p pel neSelectorStep

      overr de def executorArrow: Arrow[
        Query,
        P pel neSelectorExecutorResult
      ] = p pel neSelectorExecutor.arrow(p pel neBy dent fer, p pel neSelector, context)

      overr de def  nputAdaptor(
        query: ProductP pel neRequest[TRequest],
        prev ousResult: ProductP pel neResult[Response]
      ): Query =
        prev ousResult.transfor dQuery
          .getOrElse(throw  nval dStepStateExcept on( dent f er, "Transfor dQuery")).as nstanceOf[
            Query]

      overr de def resultUpdater(
        prev ousP pel neResult: ProductP pel neResult[Response],
        executorResult: P pel neSelectorExecutorResult
      ): ProductP pel neResult[Response] =
        prev ousP pel neResult.copy(p pel neSelectorResult = So (executorResult))
    }

  def p pel neExecut onStep(
    p pel neBy dent f er: Map[Component dent f er, P pel ne[Query, Response]],
    qual yFactorObserverByP pel ne: Map[Component dent f er, Qual yFactorObserver],
    context: Executor.Context
  ): Step[P pel neExecutorRequest[Query], P pel neExecutorResult[Response]] =
    new Step[P pel neExecutorRequest[Query], P pel neExecutorResult[Response]] {
      overr de def  dent f er: P pel neStep dent f er = ProductP pel neConf g.p pel neExecut onStep

      overr de def executorArrow: Arrow[
        P pel neExecutorRequest[Query],
        P pel neExecutorResult[Response]
      ] = {
        p pel neExecutor.arrow(p pel neBy dent f er, qual yFactorObserverByP pel ne, context)
      }

      overr de def  nputAdaptor(
        request: ProductP pel neRequest[TRequest],
        prev ousResult: ProductP pel neResult[Response]
      ): P pel neExecutorRequest[Query] = {
        val query = prev ousResult.transfor dQuery
          .getOrElse {
            throw  nval dStepStateExcept on( dent f er, "Transfor dQuery")
          }.as nstanceOf[Query]

        val p pel ne dent f er = prev ousResult.p pel neSelectorResult
          .map(_.p pel ne dent f er).getOrElse {
            throw  nval dStepStateExcept on( dent f er, "P pel neSelectorResult")
          }

        P pel neExecutorRequest(query, p pel ne dent f er)
      }

      overr de def resultUpdater(
        prev ousP pel neResult: ProductP pel neResult[Response],
        executorResult: P pel neExecutorResult[Response]
      ): ProductP pel neResult[Response] = {

        val m xerP pel neResult = executorResult.p pel neResult match {
          case m xerP pel neResult: M xerP pel neResult[Response] @unc cked =>
            So (m xerP pel neResult)
          case _ =>
            None
        }

        val recom ndat onP pel neResult = executorResult.p pel neResult match {
          case recom ndat onP pel neResult: Recom ndat onP pel neResult[
                _,
                Response
              ] @unc cked =>
            So (recom ndat onP pel neResult)
          case _ =>
            None
        }

        prev ousP pel neResult.copy(
          m xerP pel neResult = m xerP pel neResult,
          recom ndat onP pel neResult = recom ndat onP pel neResult,
          trace d = Trace. dOpt on.map(_.trace d.toStr ng()),
          result = executorResult.p pel neResult.result
        )
      }
    }

  def bu ld(
    parentComponent dent f erStack: Component dent f erStack,
    conf g: ProductP pel neConf g[TRequest, Query, Response]
  ): ProductP pel ne[TRequest, Response] = {

    val p pel ne dent f er = conf g. dent f er

    val context = Executor.Context(
      P pel neFa lureClass f er(
        conf g.fa lureClass f er.orElse(StoppedGateExcept on.class f er(ProductD sabled))),
      parentComponent dent f erStack.push(p pel ne dent f er)
    )

    val denyLoggedOutUsersGate =  f (conf g.denyLoggedOutUsers) {
      So (DenyLoggedOutUsersGate(p pel ne dent f er))
    } else {
      None
    }
    val enabledGate: ParamGate =
      ParamGate(p pel ne dent f er + EnabledGateSuff x, conf g.paramConf g.EnabledDec derParam)
    val supportedCl entGate =
      ParamGate(
        p pel ne dent f er + SupportedCl entGateSuff x,
        conf g.paramConf g.SupportedCl entParam)

    /**
     * Evaluate enabled dec der gate f rst s nce  f  's off, t re  s no reason to proceed
     * Next evaluate supported cl ent feature sw ch gate, follo d by custo r conf gured gates
     */
    val allGates =
      denyLoggedOutUsersGate.toSeq ++: enabledGate +: supportedCl entGate +: conf g.gates

    val ch ldP pel nes: Seq[P pel ne[Query, Response]] =
      conf g.p pel nes.map {
        case m xerConf g: M xerP pel neConf g[Query, _, Response] =>
          m xerConf g.bu ld(context.componentStack, m xerP pel neBu lderFactory)
        case recom ndat onConf g: Recom ndat onP pel neConf g[Query, _, _, Response] =>
          recom ndat onConf g.bu ld(context.componentStack, recom ndat onP pel neBu lderFactory)
        case ot r =>
          throw new  llegalArgu ntExcept on(
            s"Product P pel nes only support M xer and Recom ndat on p pel nes, not $ot r")
      }

    val p pel neBy dent f er: Map[Component dent f er, P pel ne[Query, Response]] =
      ch ldP pel nes.map { p pel ne =>
        (p pel ne. dent f er, p pel ne)
      }.toMap

    val qual yFactorStatus: Qual yFactorStatus =
      Qual yFactorStatus.bu ld(conf g.qual yFactorConf gs)

    val qual yFactorObserverByP pel ne = qual yFactorStatus.qual yFactorByP pel ne.mapValues {
      qual yFactor =>
        qual yFactor.bu ldObserver()
    }

    bu ldGaugesForQual yFactor(p pel ne dent f er, qual yFactorStatus, statsRece ver)

    /**
     *  n  al ze MDC w h access logg ng w h everyth ng   have at request t  .   can put
     * more stuff  nto MDC later down t  p pel ne, but at r sk of except ons/errors prevent ng
     * t m from be ng added
     */
    val mdc n Arrow =
      Arrow.map[ProductP pel neRequest[TRequest], ProductP pel neRequest[TRequest]] { request =>
        val serv ce dent f er = Serv ce dent f er.fromCert f cate(Transport.peerCert f cate)
        MDC.put("product", conf g.product. dent f er.na )
        MDC.put("serv ce dent f er", Serv ce dent f er.asStr ng(serv ce dent f er))
        request
      }

    val bu ltSteps = Seq(
      p pel neQueryTransfor rStep(conf g.p pel neQueryTransfor r, context),
      qual yFactorStep(qual yFactorStatus),
      gatesStep(allGates, context),
      p pel neSelectorStep(p pel neBy dent f er, conf g.p pel neSelector, context),
      p pel neExecut onStep(p pel neBy dent f er, qual yFactorObserverByP pel ne, context)
    )

    val underly ng: Arrow[ProductP pel neRequest[TRequest], ProductP pel neResult[Response]] =
      bu ldComb nedArrowFromSteps(
        steps = bu ltSteps,
        context = context,
         n  alEmptyResult = ProductP pel neResult.empty,
        steps nOrderFromConf g = ProductP pel neConf g.steps nOrder
      )

    /**
     * Unl ke ot r components and p pel nes, [[ProductP pel ne]] must be observed  n t 
     * [[ProductP pel neBu lder]] d rectly because t  result ng [[ProductP pel ne.arrow]]
     *  s run d rectly w hout an executor so must conta n all stats.
     */
    val observed =
      wrapProductP pel neW hExecutorBookkeep ng[
        ProductP pel neRequest[TRequest],
        ProductP pel neResult[Response]
      ](context, p pel ne dent f er)(underly ng)

    val f nalArrow: Arrow[ProductP pel neRequest[TRequest], ProductP pel neResult[Response]] =
      Arrow
        .letW hArg[
          ProductP pel neRequest[TRequest],
          ProductP pel neResult[Response],
          Str ngCenterRequestContext](Str ngCenterRequestContextLetter)(request =>
          Str ngCenterRequestContext(
            request.request.cl entContext.languageCode,
            request.request.cl entContext.countryCode
          ))(
          mdc n Arrow
            .andT n(observed)
            .onSuccess(result => result.transfor dQuery.map(p pel neExecut onLogger(_, result))))

    val conf gFromBu lder = conf g
    new ProductP pel ne[TRequest, Response] {
      overr de pr vate[core] val conf g: ProductP pel neConf g[TRequest, _, Response] =
        conf gFromBu lder
      overr de val arrow: Arrow[ProductP pel neRequest[TRequest], ProductP pel neResult[Response]] =
        f nalArrow
      overr de val  dent f er: ProductP pel ne dent f er = p pel ne dent f er
      overr de val alerts: Seq[Alert] = conf g.alerts
      overr de val debugAccessPol c es: Set[AccessPol cy] = conf g.debugAccessPol c es
      overr de val ch ldren: Seq[Component] = allGates ++ ch ldP pel nes
    }
  }
}
