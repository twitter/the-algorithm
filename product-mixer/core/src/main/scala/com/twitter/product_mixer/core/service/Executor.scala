package com.tw ter.product_m xer.core.serv ce

 mport com.tw ter.f nagle.stats.BroadcastStatsRece ver
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.DefaultStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.Annotat on
 mport com.tw ter.f nagle.trac ng.Record
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.f nagle.trac ng.Trace d
 mport com.tw ter.f nagle.trac ng.TraceServ ceNa 
 mport com.tw ter.f nagle.trac ng.Trac ng.LocalBeg nAnnotat on
 mport com.tw ter.f nagle.trac ng.Trac ng.LocalEndAnnotat on
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.ProductP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.Fa lOpenPol cy
 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.FeatureHydrat onFa led
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.M sconf guredFeatureMapFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureClass f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Uncategor zedServerFa lure
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.product_m xer.core.serv ce.Executor.AlwaysFa lOpen nclud ngProgram rErrors
 mport com.tw ter.product_m xer.core.serv ce.Executor.Context
 mport com.tw ter.product_m xer.core.serv ce.Executor.Trac ngConf g
 mport com.tw ter.product_m xer.core.serv ce.Executor.toP pel neFa lureW hComponent dent f erStack
 mport com.tw ter.servo.ut l.CancelledExcept onExtractor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.St ch.Letter
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try

/**
 * Base tra  that all executors  mple nt
 *
 * All executors should:
 *   -  mple nt a `def arrow` or `def apply` w h t  relevant types for t  r use case
 *     and take  n an  mpl c  [[P pel neFa lureClass f er]] and [[Component dent f erStack]].
 *   - add a `@s ngleton` annotat on to t  class and `@ nject` annotat on to t  argu nt l st
 *   - take  n a [[StatsRece ver]]
 *
 * @example {{{
 *   @S ngleton class  Executor @ nject() (
 *     overr de val statsRece ver: StatsRece ver
 *   ) extends Executor {
 *     def arrow(
 *       arg:  Arg,
 *       ...,
 *       context: Context
 *     ): Arrow[ n,Out] = ???
 *   }
 * }}}
 */
pr vate[core] tra  Executor {
  val statsRece ver: StatsRece ver

  /**
   * Appl es t  `p pel neFa lureClass f er` to t  output of t  `arrow`
   * and adds t  `componentStack` to t  [[P pel neFa lure]]
   */
  def wrapW hErrorHandl ng[ n, Out](
    context: Context,
    currentComponent dent f er: Component dent f er
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {
    arrow.mapFa lure(
      toP pel neFa lureW hComponent dent f erStack(context, currentComponent dent f er))
  }

  /**
   * Cha n a `Seq` of [[Arrow. so]], only pass ng successful results to t  next [[Arrow. so]]
   *
   * @note t  result ng [[Arrow]] runs t  passed  n [[Arrow]]s one after t  ot r,
   *       as an ordered execut on, t   ans that each [[Arrow]]  s dependent
   *       on all prev ous [[Arrow]]s  n t  `Seq` so no `St ch` batch ng can occur
   *       bet en t m.
   */
  def  soArrowsSequent ally[T](arrows: Seq[Arrow. so[T]]): Arrow. so[T] = {
    // avo d excess Arrow complex y w n t re  s only a s ngle Arrow
    arrows match {
      case Seq() => Arrow. dent y
      case Seq(onlyOneArrow) => onlyOneArrow
      case Seq( ad, ta l @ _*) =>
        ta l.foldLeft( ad) {
          case (comb nedArrow, nextArrow) => comb nedArrow.flatMapArrow(nextArrow)
        }
    }
  }

  /**
   * Start runn ng t  [[Arrow]]  n t  background return ng a [[St ch.Ref]] wh ch w ll complete
   * w n t  background task  s f n s d
   */
  def startArrowAsync[ n, Out](arrow: Arrow[ n, Out]): Arrow[ n, St ch[Out]] = {
    Arrow
      .map { arg:  n =>
        // wrap  n a `ref` so   only compute  's value once
        St ch.ref(arrow(arg))
      }
      .andT n(
        Arrow.z pW hArg(
          // sat sfy t  `ref` async
          Arrow.async(Arrow.flatMap[St ch[Out], Out]( dent y))))
      .map { case (ref, _) => ref }
  }

  /**
   * for [[com.tw ter.product_m xer.core.model.common.Component]]s wh ch
   * are executed per-cand date or wh ch   don't want to record stats for.
   * T  performs Trac ng but does not record Stats
   *
   * @note T  should be used around t  computat on that  ncludes t  execut on of t 
   *       underly ng Component over all t  Cand dates, not around each execut on
   *        of t  component around each cand date for per-cand date Components.
   *
   * @note w n us ng t    should only use [[wrapPerCand dateComponentW hExecutorBookkeep ngW houtTrac ng]]
   *       for handl ng Stats.
   */
  def wrapComponentsW hTrac ngOnly[ n, Out](
    context: Context,
    currentComponent dent f er: Component dent f er
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {
    Executor.wrapArrowW hLocalTrac ngSpan(
      Arrow
        .t  (arrow)
        .map {
          case (result, latency) =>
            Executor.recordTraceData(
              componentStack = context.componentStack,
              component dent f er = currentComponent dent f er,
              result = result,
              latency = latency,
              s ze = None)
            result
        }.lo rFromTry)
  }

  /**
   * for [[com.tw ter.product_m xer.core.model.common.Component]]s wh ch
   * are executed per-cand date. Records Stats but does not do Trac ng.
   *
   * @note w n us ng t    should only use [[wrapPerCand dateComponentsW hTrac ngOnly]]
   *       for handl ng Trac ng
   */
  def wrapPerCand dateComponentW hExecutorBookkeep ngW houtTrac ng[ n, Out](
    context: Context,
    currentComponent dent f er: Component dent f er
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {
    val observerS deEffect =
      ExecutorObserver.executorObserver[Out](context, currentComponent dent f er, statsRece ver)

    Executor.wrapW hExecutorBookkeep ng[ n, Out, Out](
      context = context,
      currentComponent dent f er = currentComponent dent f er,
      executorResultS deEffect = observerS deEffect,
      transfor r = Return(_),
      trac ngConf g = Trac ngConf g.NoTrac ng
    )(arrow)
  }

  /** for [[com.tw ter.product_m xer.core.model.common.Component]]s */
  def wrapComponentW hExecutorBookkeep ng[ n, Out](
    context: Context,
    currentComponent dent f er: Component dent f er
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {
    val observerS deEffect =
      ExecutorObserver.executorObserver[Out](context, currentComponent dent f er, statsRece ver)

    Executor.wrapW hExecutorBookkeep ng[ n, Out, Out](
      context = context,
      currentComponent dent f er = currentComponent dent f er,
      executorResultS deEffect = observerS deEffect,
      transfor r = Return(_)
    )(arrow)
  }

  /**
   * for [[com.tw ter.product_m xer.core.model.common.Component]]s wh ch an `onSuccess`
   * to add custom stats or logg ng of results
   */
  def wrapComponentW hExecutorBookkeep ng[ n, Out](
    context: Context,
    currentComponent dent f er: Component dent f er,
    onSuccess: Out => Un 
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {
    val observerS deEffect =
      ExecutorObserver.executorObserver[Out](context, currentComponent dent f er, statsRece ver)

    Executor.wrapW hExecutorBookkeep ng[ n, Out, Out](
      context = context,
      currentComponent dent f er = currentComponent dent f er,
      executorResultS deEffect = observerS deEffect,
      transfor r = Return(_),
      onComplete = (transfor d: Try[Out]) => transfor d.onSuccess(onSuccess)
    )(arrow)
  }

  /** for [[com.tw ter.product_m xer.core.p pel ne.P pel ne]]s */
  def wrapP pel neW hExecutorBookkeep ng[ n, Out <: P pel neResult[_]](
    context: Context,
    currentComponent dent f er: Component dent f er,
    qual yFactorObserver: Opt on[Qual yFactorObserver],
    fa lOpenPol cy: Fa lOpenPol cy = Fa lOpenPol cy.Never
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {
    val observerS deEffect =
      ExecutorObserver
        .p pel neExecutorObserver[Out](context, currentComponent dent f er, statsRece ver)

    Executor.wrapW hExecutorBookkeep ng[ n, Out, Out](
      context = context,
      currentComponent dent f er = currentComponent dent f er,
      executorResultS deEffect = observerS deEffect,
      transfor r = (result: Out) => result.toTry,
      s ze = So (_.resultS ze()),
      fa lOpenPol cy = fa lOpenPol cy,
      qual yFactorObserver = qual yFactorObserver
    )(arrow)
  }

  /** for [[com.tw ter.product_m xer.core.p pel ne.product.ProductP pel ne]]s */
  def wrapProductP pel neW hExecutorBookkeep ng[ n, Out <: P pel neResult[_]](
    context: Context,
    currentComponent dent f er: ProductP pel ne dent f er
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {

    val observerS deEffect =
      ExecutorObserver
        .productP pel neExecutorObserver[Out](currentComponent dent f er, statsRece ver)

    Executor.wrapW hExecutorBookkeep ng[ n, Out, Out](
      context = context,
      currentComponent dent f er = currentComponent dent f er,
      executorResultS deEffect = observerS deEffect,
      transfor r = _.toTry,
      s ze = So (_.resultS ze()),
      fa lOpenPol cy =
        // always save Fa lures  n t  Result object  nstead of fa l ng t  request
        AlwaysFa lOpen nclud ngProgram rErrors
    )(arrow)
  }

  /** for [[com.tw ter.product_m xer.core.model.common.Component]]s wh ch need a result s ze stat */
  def wrapComponentW hExecutorBookkeep ngW hS ze[ n, Out](
    context: Context,
    currentComponent dent f er: Cand dateS ce dent f er,
    s ze: Out =>  nt
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {
    val observerS deEffect =
      ExecutorObserver.executorObserverW hS ze(context, currentComponent dent f er, statsRece ver)

    Executor.wrapW hExecutorBookkeep ng[ n, Out,  nt](
      context = context,
      currentComponent dent f er = currentComponent dent f er,
      executorResultS deEffect = observerS deEffect,
      transfor r = (out: Out) => Try(s ze(out)),
      s ze = So ( dent y)
    )(arrow)
  }

  /** for [[com.tw ter.product_m xer.core.p pel ne.P pel neBu lder.Step]]s */
  def wrapStepW hExecutorBookkeep ng[ n, Out](
    context: Context,
     dent f er: P pel neStep dent f er,
    arrow: Arrow[ n, Out],
    transfor r: Out => Try[Un ]
  ): Arrow[ n, Out] = {
    val observerS deEffect =
      ExecutorObserver.stepExecutorObserver(context,  dent f er, statsRece ver)

    Executor.wrapW hExecutorBookkeep ng[ n, Out, Un ](
      context = context,
      currentComponent dent f er =  dent f er,
      executorResultS deEffect = observerS deEffect,
      transfor r = transfor r,
      fa lOpenPol cy = AlwaysFa lOpen nclud ngProgram rErrors
    )(arrow)
  }
}

pr vate[core] object Executor {

  pr vate[serv ce] object Trac ngConf g {

    /** Used to spec fy w t r a wrapped Arrow should be Traced  n [[wrapW hExecutorBookkeep ng]] */
    sealed tra  Trac ngConf g
    case object NoTrac ng extends Trac ngConf g
    case object WrapW hSpanAndTrac ngData extends Trac ngConf g
  }

  /**
   * Always fa l-open and return t  [[com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neResult]]
   * conta n ng t  except on, t  d ffers from [[Fa lOpenPol cy.Always]]  n that t  w ll st ll
   * fa l-open and return t  overall result object even  f t  underly ng fa lure  s t  result
   * of program r error.
   */
  pr vate val AlwaysFa lOpen nclud ngProgram rErrors: Fa lOpenPol cy = _ => true

  /**
   * Wraps an [[Arrow]] so that bookkeep ng around t  execut on occurs un formly.
   *
   * @note should __never__ be called d rectly!
   *
   *   - For successful results, apply t  `transfor r`
   *   - convert any except ons to P pel neFa lures
   *   - record stats and update [[Qual yFactorObserver]]
   *   - wraps t  execut on  n a Trace span and record Trace data (can be turned off by [[Trac ngConf g]])
   *   - appl es a trace span and records  tadata to t  prov ded `arrow`
   *   - determ ne w t r to fa l-open based on `result.flatMap(transfor r)`
   *     -  f fa l ng-open, always return t  or g nal result
   *     -  f fa l ng-closed and successful, return t  or g nal result
   *     - ot rw se, return t  fa lure (from `result.flatMap(transfor r)`)
   *
   * @param context                    t  [[Executor.Context]]
   * @param currentComponent dent f er t  current component's [[Component dent f er]]
   * @param executorResultS deEffect   t  [[ExecutorObserver]] used to record stats
   * @param transfor r                funct on to convert a successful result  nto poss bly a fa led result
   * @param fa lOpenPol cy             [[Fa lOpenPol cy]] to apply to t  results of `result.flatMap(transfor r)`
   * @param qual yFactorObserver      [[Qual yFactorObserver]] to update based on t  results of `result.flatMap(transfor r)`
   * @param trac ngConf g               nd cates w t r t  [[Arrow]] should be wrapped w h Trac ng
   * @param onComplete                 runs t  funct on for  s s de effects w h t  result of `result.flatMap(transfor r)`
   * @param arrow                      an  nput [[Arrow]] to wrap so that after  's execut on,   perform all t se operat ons
   *
   * @return t  wrapped [[Arrow]]
   */
  pr vate[serv ce] def wrapW hExecutorBookkeep ng[ n, Out, Transfor d](
    context: Context,
    currentComponent dent f er: Component dent f er,
    executorResultS deEffect: ExecutorObserver[Transfor d],
    transfor r: Out => Try[Transfor d],
    s ze: Opt on[Transfor d =>  nt] = None,
    fa lOpenPol cy: Fa lOpenPol cy = Fa lOpenPol cy.Never,
    qual yFactorObserver: Opt on[Qual yFactorObserver] = None,
    trac ngConf g: Trac ngConf g.Trac ngConf g = Trac ngConf g.WrapW hSpanAndTrac ngData,
    onComplete: Try[Transfor d] => Un  = { _: Try[Transfor d] => () }
  )(
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] = {

    val fa lureClass f er =
      toP pel neFa lureW hComponent dent f erStack(context, currentComponent dent f er)

    /** transform t  results, mapp ng all except ons to [[P pel neFa lure]]s, and tuple w h or g nal result */
    val transformResultAndClass fyFa lures: Arrow[Out, (Out, Try[Transfor d])] =
      Arrow.jo n(
        Arrow.mapFa lure(fa lureClass f er),
        Arrow
          .transformTry[Out, Transfor d](result =>
            result
              .flatMap(transfor r)
              .rescue { case t => Throw(fa lureClass f er(t)) })
          .l ftToTry
      )

    /** Only record trac ng data  f [[Trac ngConf g.WrapW hSpanAndTrac ngData]] */
    val maybeRecordTrac ngData: (Try[Transfor d], Durat on) => Un  = trac ngConf g match {
      case Trac ngConf g.NoTrac ng => (_, _) => ()
      case Trac ngConf g.WrapW hSpanAndTrac ngData =>
        (transfor dAndClass f edResult, latency) =>
          recordTraceData(
            context.componentStack,
            currentComponent dent f er,
            transfor dAndClass f edResult,
            latency,
            transfor dAndClass f edResult.toOpt on.flatMap(result => s ze.map(_.apply(result)))
          )
    }

    /** W ll never be  n a fa led state so   can do a s mple [[Arrow.map]] */
    val recordStatsAndUpdateQual yFactor =
      Arrow
        .map[(Try[(Out, Try[Transfor d])], Durat on), Un ] {
          case (tryResultAndTryTransfor d, latency) =>
            val transfor dAndClass f edResult = tryResultAndTryTransfor d.flatMap {
              case (_, transfor d) => transfor d
            }
            executorResultS deEffect(transfor dAndClass f edResult, latency)
            qual yFactorObserver.foreach(_.apply(transfor dAndClass f edResult, latency))
            onComplete(transfor dAndClass f edResult)
            maybeRecordTrac ngData(transfor dAndClass f edResult, latency)
        }.un 

    /**
     * Appl es t  prov ded [[Fa lOpenPol cy]] based on t  [[transfor r]]'s results,
     * return ng t  or g nal result or an except on
     */
    val applyFa lOpenPol cyBasedOnTransfor dResult: Arrow[
      (Try[(Out, Try[Transfor d])], Durat on),
      Out
    ] =
      Arrow
        .map[(Try[(Out, Try[Transfor d])], Durat on), Try[(Out, Try[Transfor d])]] {
          case (tryResultAndTryTransfor d, _) => tryResultAndTryTransfor d
        }
        .lo rFromTry
        .map {
          case (result, Throw(p pel neFa lure: P pel neFa lure))
               f fa lOpenPol cy(p pel neFa lure.category) =>
            Return(result)
          case (_, t: Throw[_]) => t.as nstanceOf[Throw[Out]]
          case (result, _) => Return(result)
        }.lo rFromTry

    /** T  complete Arrow m nus a Local span wrapp ng */
    val arrowW hT m ngExecutorS deEffects = Arrow
      .t  (arrow.andT n(transformResultAndClass fyFa lures))
      .applyEffect(recordStatsAndUpdateQual yFactor)
      .andT n(applyFa lOpenPol cyBasedOnTransfor dResult)

    /** Dont wrap w h a span  f   are not trac ng */
    trac ngConf g match {
      case Trac ngConf g.WrapW hSpanAndTrac ngData =>
        wrapArrowW hLocalTrac ngSpan(arrowW hT m ngExecutorS deEffects)
      case Trac ngConf g.NoTrac ng =>
        arrowW hT m ngExecutorS deEffects
    }
  }

  /** Let-scopes a [[Trace d]] around a computat on */
  pr vate[t ] object Trac ngLetter extends Letter[Trace d] {
    overr de def let[S](trace d: Trace d)(s: => S): S = Trace.let d(trace d)(s)
  }

  /**
   * Wraps t  Arrow's execut on  n a new trace span as a ch ld of t  current parent span
   *
   * @note Should __never__ be called d rectly!
   *
   *  's expected that t  conta ned `arrow` w ll  nvoke [[recordTraceData]] exactly ONCE
   * dur ng  's execut on.
   *
   * @note t  does not record any data about t  trace,   only sets t  [[Trace]] Span
   *       for t  execut on of `arrow`
   */
  pr vate[serv ce] def wrapArrowW hLocalTrac ngSpan[ n, Out](
    arrow: Arrow[ n, Out]
  ): Arrow[ n, Out] =
    Arrow. felse(
      _ => Trace. sAct velyTrac ng,
      Arrow.let(Trac ngLetter)(Trace.next d)(arrow),
      arrow
    )

  pr vate[t ] object Trac ng {

    /**
     * Dupl cate of [[com.tw ter.f nagle.trac ng.Trac ng]]'s `localSpans` wh ch
     * uses an un-scoped [[StatsRece ver]]
     *
     * S nce   needed to roll- -own latency  asure nt   are unable to  ncre nt t 
     * `local_spans`  tr c automat cally, t   s  mportant  n t  event a serv ce  s
     * unexpectedly not record ng spans or unexpectedly record ng too many, so   manually
     *  ncre nt  
     */
    val localSpans: Counter = DefaultStatsRece ver.counter("trac ng", "local_spans")

    /** Local Component f eld of a span  n t  U  */
    val localComponentTag = "lc"
    val s zeTag = "product_m xer.result.s ze"
    val successTag = "product_m xer.result.success"
    val successValue = "success"
    val cancelledTag = "product_m xer.result.cancelled"
    val fa lureTag = "product_m xer.result.fa lure"
  }

  /**
   * Records  tadata onto t  current [[Trace]] Span
   *
   * @note Should __never__ be called d rectly!
   *
   * T  should be called exactly ONCE  n t  Arrow passed  nto [[wrapArrowW hLocalTrac ngSpan]]
   * to record data for t  Span.
   */
  pr vate[serv ce] def recordTraceData[T](
    componentStack: Component dent f erStack,
    component dent f er: Component dent f er,
    result: Try[T],
    latency: Durat on,
    s ze: Opt on[ nt] = None
  ): Un  = {
     f (Trace. sAct velyTrac ng) {
      Trac ng.localSpans. ncr()
      val trace d = Trace. d
      val endT   = T  .nowNanoPrec s on

      // T se annotat ons are needed for t  Z pk n U  to d splay t  span properly
      TraceServ ceNa ().foreach(Trace.recordServ ceNa )
      Trace.recordRpc(component dent f er.snakeCase) // na  of t  span  n t  U 
      Trace.recordB nary(
        Trac ng.localComponentTag,
        componentStack.peek.toStr ng + "/" + component dent f er.toStr ng)
      Trace.record(Record(trace d, endT   - latency, Annotat on. ssage(LocalBeg nAnnotat on)))
      Trace.record(Record(trace d, endT  , Annotat on. ssage(LocalEndAnnotat on)))

      // product m xer spec f c z pk n data
      s ze.foreach(s ze => Trace.recordB nary(Trac ng.s zeTag, s ze))
      result match {
        case Return(_) =>
          Trace.recordB nary(Trac ng.successTag, Trac ng.successValue)
        case Throw(CancelledExcept onExtractor(e)) =>
          Trace.recordB nary(Trac ng.cancelledTag, e)
        case Throw(e) =>
          Trace.recordB nary(Trac ng.fa lureTag, e)
      }
    }
  }

  /**
   * Returns a tuple of t  stats scopes for t  current component and t  relat ve scope for
   * t  parent component and t  current component toget r
   *
   * T   s useful w n record ng stats for a component by  self as  ll as stats for calls to that component from  's parent.
   *
   * @example  f t  current component has a scope of "currentComponent" and t  parent component has a scope of "parentComponent"
   *          t n t  w ll return `(Seq("currentComponent"), Seq("parentComponent", "currentComponent"))`
   */
  def bu ldScopes(
    context: Context,
    currentComponent dent f er: Component dent f er
  ): Executor.Scopes = {
    val parentScopes = context.componentStack.peek.toScopes
    val componentScopes = currentComponent dent f er.toScopes
    val relat veScopes = parentScopes ++ componentScopes
    Executor.Scopes(componentScopes, relat veScopes)
  }

  /**
   * Makes a [[BroadcastStatsRece ver]] that w ll broadcast stats to t  correct
   * current component's scope and to t  scope relat ve to t  parent.
   */
  def broadcastStatsRece ver(
    context: Context,
    currentComponent dent f er: Component dent f er,
    statsRece ver: StatsRece ver
  ): StatsRece ver = {
    val Executor.Scopes(componentScopes, relat veScopes) =
      Executor.bu ldScopes(context, currentComponent dent f er)

    BroadcastStatsRece ver(
      Seq(statsRece ver.scope(relat veScopes: _*), statsRece ver.scope(componentScopes: _*)))
  }

  /**
   * Returns a feature map conta n ng all t  [[com.tw ter.product_m xer.core.feature.Feature]]s
   * stored as fa lures us ng t  except on prov ded w h as t  reason wrapped  n a P pel neFa lure.
   * e.g, for features A & B that threw an ExampleExcept on b, t  w ll return:
   * { A -> Throw(P pel neFa lure(...)), B -> Throw(P pel neFa lure(...)) }
   */
  def featureMapW hFa luresForFeatures(
    features: Set[Feature[_, _]],
    error: Throwable,
    context: Executor.Context
  ): FeatureMap = {
    val bu lder = FeatureMapBu lder()
    features.foreach { feature =>
      val p pel neFa lure = P pel neFa lure(
        FeatureHydrat onFa led,
        s"Feature hydrat on fa led for ${feature.toStr ng}",
        So (error),
        So (context.componentStack))
      bu lder.addFa lure(feature, p pel neFa lure)
    }
    bu lder.bu ld()
  }

  /**
   * Val dates and returns back t  passed feature map  f   passes val dat on. A feature map
   *  s cons dered val d  f   conta ns only t  passed `reg steredFeatures` features  n  ,
   * noth ng else and noth ng m ss ng.
   */
  @throws(classOf[P pel neFa lure])
  def val dateFeatureMap(
    reg steredFeatures: Set[Feature[_, _]],
    featureMap: FeatureMap,
    context: Executor.Context
  ): FeatureMap = {
    val hydratedFeatures = featureMap.getFeatures
     f (hydratedFeatures == reg steredFeatures) {
      featureMap
    } else {
      val m ss ngFeatures = reg steredFeatures -- hydratedFeatures
      val unreg steredFeatures = hydratedFeatures -- reg steredFeatures
      throw P pel neFa lure(
        M sconf guredFeatureMapFa lure,
        s"Unreg stered features $unreg steredFeatures and m ss ng features $m ss ngFeatures",
        None,
        So (context.componentStack)
      )
    }
  }

  object NotAM sconf guredFeatureMapFa lure {

    /**
     * W ll return any except on that  sn't a [[M sconf guredFeatureMapFa lure]] [[P pel neFa lure]]
     * Allows for easy [[Arrow.handle]] ng all except ons that aren't [[M sconf guredFeatureMapFa lure]]s
     */
    def unapply(e: Throwable): Opt on[Throwable] = e match {
      case p pel neFa lure: P pel neFa lure
           f p pel neFa lure.category == M sconf guredFeatureMapFa lure =>
        None
      case e => So (e)
    }
  }

  /**
   * conta ns t  scopes for record ng  tr cs for t  component by  self and
   * t  relat ve scope of that component w h n  's parent component scope
   *
   * @see [[Executor.bu ldScopes]]
   */
  case class Scopes(componentScopes: Seq[Str ng], relat veScope: Seq[Str ng])

  /**
   * Wrap t  [[Throwable]]  n a [[Uncategor zedServerFa lure]] [[P pel neFa lure]] w h t  or g nal
   * [[Throwable]] as t  cause, even  f  's already a [[P pel neFa lure]].
   *
   * T  ensures that any access to t  stored feature w ll result  n a  an ngful [[Uncategor zedServerFa lure]]
   * [[com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureCategory]]  n stats wh ch  s more useful
   * for custo rs components wh ch access a fa led [[Feature]] than t  or g nal [[com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureCategory]].
   */
  def uncategor zedServerFa lure(
    componentStack: Component dent f erStack,
    throwable: Throwable
  ): P pel neFa lure = {
    P pel neFa lure(
      Uncategor zedServerFa lure,
      reason = "Unclass f ed Fa lure  n P pel ne",
      So (throwable),
      So (componentStack)
    )
  }

  /**
   * [[Part alFunct on]] that converts any [[Throwable]]  nto a
   * [[P pel neFa lure]] based on t  prov ded `fa lureClass f er`
   */
  def toP pel neFa lureW hComponent dent f erStack(
    context: Context,
    currentComponent dent f er: Component dent f er
  ): P pel neFa lureClass f er = {
    //  f g ven a `currentComponent dent f er` t n ensure   correctly handle `BasedOnParentComponent`  dent f er types
    val contextW hCurrentComponent dent f er =
      context.pushToComponentStack(currentComponent dent f er)
    P pel neFa lureClass f er(
      contextW hCurrentComponent dent f er.p pel neFa lureClass f er
        .orElse[Throwable, P pel neFa lure] {
          case CancelledExcept onExtractor(throwable) => throw throwable
          case p pel neFa lure: P pel neFa lure => p pel neFa lure
          case throwable =>
            uncategor zedServerFa lure(
              contextW hCurrentComponent dent f er.componentStack,
              throwable)
        }.andT n { p pel neFa lure =>
          p pel neFa lure.componentStack match {
            case _: So [_] => p pel neFa lure
            case None =>
              p pel neFa lure.copy(componentStack =
                So (contextW hCurrentComponent dent f er.componentStack))
          }
        }
    )
  }

  /**
   *  nformat on used by an [[Executor]] that prov des context around execut on
   */
  case class Context(
    p pel neFa lureClass f er: P pel neFa lureClass f er,
    componentStack: Component dent f erStack) {

    def pushToComponentStack(newComponent dent f er: Component dent f er): Context =
      copy(componentStack = componentStack.push(newComponent dent f er))
  }
}
