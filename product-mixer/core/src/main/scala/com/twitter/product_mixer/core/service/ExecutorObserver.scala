package com.tw ter.product_m xer.core.serv ce

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.ProductP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.Executor.Context
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.Observer
 mport com.tw ter.product_m xer.shared_l brary.observer.ResultsStatsObserver.ResultsStatsObserver
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

pr vate[core] object ExecutorObserver {

  /** Make a [[ExecutorObserver]] w h stats for t  [[Component dent f er]] and relat ve to t  parent  n t  [[Context.componentStack]] */
  def executorObserver[T](
    context: Context,
    currentComponent dent f er: Component dent f er,
    statsRece ver: StatsRece ver
  ): ExecutorObserver[T] = new ExecutorObserver[T](
    Executor.broadcastStatsRece ver(context, currentComponent dent f er, statsRece ver))

  /** Make a [[ExecutorObserverW hS ze]] w h stats for t  [[Component dent f er]] and relat ve to t  parent  n t  [[Context.componentStack]] */
  def executorObserverW hS ze(
    context: Context,
    currentComponent dent f er: Component dent f er,
    statsRece ver: StatsRece ver
  ): ExecutorObserverW hS ze = new ExecutorObserverW hS ze(
    Executor.broadcastStatsRece ver(context, currentComponent dent f er, statsRece ver))

  /** Make a [[P pel neExecutorObserver]] w h stats for t  [[Component dent f er]] and relat ve to t  parent  n t  [[Context.componentStack]] */
  def p pel neExecutorObserver[T <: P pel neResult[_]](
    context: Context,
    currentComponent dent f er: Component dent f er,
    statsRece ver: StatsRece ver
  ): P pel neExecutorObserver[T] = new P pel neExecutorObserver[T](
    Executor.broadcastStatsRece ver(context, currentComponent dent f er, statsRece ver))

  /**
   * Make a [[P pel neExecutorObserver]] spec f cally for a [[com.tw ter.product_m xer.core.p pel ne.product.ProductP pel ne]]
   * w h no relat ve stats
   */
  def productP pel neExecutorObserver[T <: P pel neResult[_]](
    currentComponent dent f er: ProductP pel ne dent f er,
    statsRece ver: StatsRece ver
  ): P pel neExecutorObserver[T] =
    new P pel neExecutorObserver[T](statsRece ver.scope(currentComponent dent f er.toScopes: _*))

  /**
   * Make a [[P pel neExecutorObserver]] w h only stats relat ve to t  parent p pel ne
   * for [[com.tw ter.product_m xer.core.p pel ne.P pel neBu lder.Step]]s
   */
  def stepExecutorObserver(
    context: Context,
    currentComponent dent f er: P pel neStep dent f er,
    statsRece ver: StatsRece ver
  ): ExecutorObserver[Un ] = {
    new ExecutorObserver[Un ](
      statsRece ver.scope(
        Executor.bu ldScopes(context, currentComponent dent f er).relat veScope: _*))
  }
}

/**
 * An [[Observer]] wh ch  s called as a s de effect. Unl ke t  ot r observers wh ch wrap a computat on,
 * t  [[Observer]] expects t  caller to prov de t  latency value and w re    n
 */
pr vate[core] sealed class ExecutorObserver[T](
  overr de val statsRece ver: StatsRece ver)
    extends {

  /**
   * always empty because   expect an already scoped [[com.tw ter.f nagle.stats.BroadcastStatsRece ver]] to be passed  n
   * @note uses early def n  ons [[https://docs.scala-lang.org/tutor als/FAQ/ n  al zat on-order.html]] to avo d null values for `scopes`  n [[Observer]]
   */
  overr de val scopes: Seq[Str ng] = Seq.empty
} w h Observer[T] {

  /**
   * Ser al ze t  prov ded [[Throwable]], pref x ng [[P pel neFa lure]]s w h t  r
   * [[com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureCategory.categoryNa ]] and
   * [[com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureCategory.fa lureNa ]]
   */
  overr de def ser al zeThrowable(throwable: Throwable): Seq[Str ng] = {
    throwable match {
      case P pel neFa lure(category, _, None, _) =>
        Seq(category.categoryNa , category.fa lureNa )
      case P pel neFa lure(category, _, So (underly ng), _) =>
        Seq(category.categoryNa , category.fa lureNa ) ++ ser al zeThrowable(underly ng)
      case throwable: Throwable => super.ser al zeThrowable(throwable)
    }
  }

  /** record success, fa lure, and latency stats based on `t` and `latency` */
  def apply(t: Try[T], latency: Durat on): Un  = observe(t, latency)
}

/**
 * Sa  as [[ExecutorObserver]] but records a s ze stat for [[P pel neResult]]s and
 * records a fa lure counter for t  cause of t  fa lure under `fa lures/$p pel neFa lureCategory/$componentType/$componentNa `.
 *
 * @example  f `Gate dent f er(" Gate")`  s at t  top of t  [[P pel neFa lure.componentStack]] t n
 *          t  result ng  tr c `fa lures/Cl entFa lure/Gate/ Gate` w ll be  ncre nted.
 */
pr vate[core] f nal class P pel neExecutorObserver[T <: P pel neResult[_]](
  overr de val statsRece ver: StatsRece ver)
    extends ExecutorObserver[T](statsRece ver)
    w h ResultsStatsObserver[T] {
  overr de val s ze: T =>  nt = _.resultS ze()

  overr de def apply(t: Try[T], latency: Durat on): Un  = {
    super.apply(t, latency)
    t match {
      case Return(result) => observeResults(result)
      case Throw(P pel neFa lure(category, _, _, So (component dent f erStack))) =>
        statsRece ver
          .counter(
            Seq(
              Observer.Fa lures,
              category.categoryNa ,
              category.fa lureNa ) ++ component dent f erStack.peek.toScopes: _*). ncr()
      case _ =>
    }
  }
}

/** Sa  as [[ExecutorObserver]] but records a s ze stat */
pr vate[core] f nal class ExecutorObserverW hS ze(
  overr de val statsRece ver: StatsRece ver)
    extends ExecutorObserver[ nt](statsRece ver)
    w h ResultsStatsObserver[ nt] {
  overr de val s ze:  nt =>  nt =  dent y

  overr de def apply(t: Try[ nt], latency: Durat on): Un  = {
    super.apply(t, latency)
    t match {
      case Return(result) => observeResults(result)
      case _ =>
    }
  }
}
