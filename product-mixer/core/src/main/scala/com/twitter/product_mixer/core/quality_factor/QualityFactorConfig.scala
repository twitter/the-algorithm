package com.tw ter.product_m xer.core.qual y_factor

 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Cl entFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorConf g.default gnorableFa lures
 mport com.tw ter.servo.ut l.CancelledExcept onExtractor
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.convers ons.Durat onOps.R chDurat on

/**
 * Qual y factor  s an abstract number that enables a feedback loop to control operat on costs and ult mately
 * ma nta n t  operat on success rate. Abstractly,  f operat ons/calls are too expens ve (such as h gh
 * latenc es), t  qual y factor should go down, wh ch  lps future calls to ease t  r demand/load (such as
 * reduc ng request w dth);  f ops/calls are fast, t  qual y factor should go up, so   can  ncur more load.
 */
sealed tra  Qual yFactorConf g {

  /**
   * spec f es t  qual y factor m n and max bounds and default value.
   */
  def qual yFactorBounds: BoundsW hDefault[Double]

  /**
   *  n  alDelay Spec f es how much delay   should have before t  qual y factor calculat on start to k ck  n. T   s
   * mostly to ease t  load dur ng t   n  al warmup/startup.
   */
  def  n  alDelay: Durat on

  /**
   * [[Throwable]]s that should be  gnored w n calculat ng
   * t  [[Qual yFactor]]  f t   s [[Part alFunct on. sDef nedAt]]
   */
  def  gnorableFa lures: Part alFunct on[Throwable, Un ] = default gnorableFa lures
}

object Qual yFactorConf g {

  /**
   * Default value for [[Qual yFactorConf g. gnorableFa lures]] that  gnores any
   * Cancelled requests and [[Cl entFa lure]]
   */
  val default gnorableFa lures: Part alFunct on[Throwable, Un ] = {
    case P pel neFa lure(_: Cl entFa lure, _, _, _) => ()
    case CancelledExcept onExtractor(_) => ()
  }
}

/**
 * T   s a l near qual y factor  mple ntat on, a  d to ach eve and ma nta n a percent le latency target.
 *
 *  f   call qual y factor q, target latency t and target percent le p,
 *   t n t  q (qual y factor) formula should be:
 *   q += delta                      for each request w h latency <= t
 *   q -= delta * p / (100 - p)      for each request w h latency > t ms or a t  out.
 *
 *   W n percent le p latency stays at target latency t, t n based on t  formula above, q w ll
 *   stay constant (fluctuates around a constant value).
 *
 *   For example, assu  t = 100ms, p = p99, and q = 0.5
 *   let's say, p99 latency stays at 100ms w n q = 0.5. p99  ans that out of every 100 latenc es,
 *   99 t  s t  latency  s below 100ms and 1 t      s above 100ms. So based on t  formula above,
 *   q w ll  ncrease by "delta" 99 t  s and   w ll decrease by delta * p / (100 - p) = delta * 99 once,
 *   wh ch results  n t  sa  q = 0.5.
 *
 * @param targetLatency T   s t  latency target, calls w h latenc es above wh ch w ll cause qual y
 * factor to go down, and v ce versa. e.g. 500ms.
 * @param targetLatencyPercent le T  t  percent le w re t  target latency  s a  d at. e.g. 95.0.
 * @param delta t  step for adjust ng qual y factor.   should be a pos  ve double.  f delta  s
 *              too large, t n qual y factor w ll fluctuate more, and  f    s too small, t 
 *              respons veness w ll be reduced.
 */
case class L nearLatencyQual yFactorConf g(
  overr de val qual yFactorBounds: BoundsW hDefault[Double],
  overr de val  n  alDelay: Durat on,
  targetLatency: Durat on,
  targetLatencyPercent le: Double,
  delta: Double,
  overr de val  gnorableFa lures: Part alFunct on[Throwable, Un ] =
    Qual yFactorConf g.default gnorableFa lures)
    extends Qual yFactorConf g {
  requ re(
    targetLatencyPercent le >= 50.0 && targetLatencyPercent le < 100.0,
    s" nval d targetLatencyPercent le value: ${targetLatencyPercent le}.\n" +
      s"Correct sample values: 95.0, 99.9.  ncorrect sample values: 0.95, 0.999."
  )
}

/**
 * A qual y factor prov des component capac y state based on sampl ng component
 * Quer es Per Second (qps) at local host level.
 *
 *  f   call qual y factor q, max qps R:
 *   t n t  q (qual y factor) formula should be:
 *   q = Math.m n([[qual yFactorBounds.bounds.max nclus ve]], q + delta)      for each request that observed qps <= R on local host
 *   q -= delta                                      for each request that observed qps > R on local host
 *
 *   W n qps r stays below R, q w ll stay as constant (value at [[qual yFactorBounds.bounds.max nclus ve]]).
 *   W n qps r starts to  ncrease above R, q w ll decrease by delta per request,
 *   w h delta be ng an add  ve factor that controls how sens  ve q  s w n max qps R  s exceeded.
 *
 *   @param  n  alDelay Spec f es an  n  al delay t   to allow query rate counter warm up to start reflect ng actual traff c load.
 *                       Qf value would only start to update after t   n  al delay.
 *   @param maxQuer esPerSecond T  max qps t  underly ng component can take. Requests go above t  qps threshold w ll cause qual y factor to go down.
 *   @param quer esPerSecondSampleW ndow T  w ndow of underly ng query rate counter count ng w h and calculate an average qps over t  w ndow,
 *                                 default to count w h 10 seconds t   w ndow ( .e. qps = total requests over last 10 secs / 10).
 *                                 Note: underly ng query rate counter has a sl d ng w ndow w h 10 f xed sl ces. T refore a larger
 *                                 w ndow would lead to a coarser qps calculat on. (e.g. w h 60 secs t   w ndow,   sl d ng over 6 seconds sl ce (60 / 10 = 6 secs)).
 *                                 A larger t   w ndow also lead to a slo r react on to sudden qps burst, but more robust to flaky qps pattern.
 *   @param delta T  step for adjust ng qual y factor.   should be a pos  ve double.  f t  delta  s large, t  qual y factor
 *                w ll fluctuate more and be more respons ve to exceed ng max qps, and  f    s small, t  qual y factor w ll be less respons ve.
 */
case class Quer esPerSecondBasedQual yFactorConf g(
  overr de val qual yFactorBounds: BoundsW hDefault[Double],
  overr de val  n  alDelay: Durat on,
  maxQuer esPerSecond:  nt,
  quer esPerSecondSampleW ndow: Durat on = 10.seconds,
  delta: Double = 0.001,
  overr de val  gnorableFa lures: Part alFunct on[Throwable, Un ] =
    Qual yFactorConf g.default gnorableFa lures)
    extends Qual yFactorConf g
