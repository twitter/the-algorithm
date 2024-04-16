package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.{Durat on, Local}

/**
 * A strategy for track ng success rate, usually over a w ndow
 */
tra  SuccessRateTracker { self =>
  def record(successes:  nt, fa lures:  nt): Un 
  def successRate: Double

  /**
   * A [[Gate]] whose ava lab l y  s computed from t  success rate (SR) reported by t  tracker.
   *
   * @param ava lab l yFromSuccessRate funct on to calculate ava lab l y of gate g ven SR
   */
  def ava lab l yGate(ava lab l yFromSuccessRate: Double => Double): Gate[Un ] =
    Gate.fromAva lab l y(ava lab l yFromSuccessRate(successRate))

  /**
   * A [[Gate]] whose ava lab l y  s computed from t  success rate reported by t  tracker
   * w h stats attac d.
   */
  def observedAva lab l yGate(
    ava lab l yFromSuccessRate: Double => Double,
    stats: StatsRece ver
  ): Gate[Un ] =
    new Gate[Un ] {
      val underly ng = ava lab l yGate(ava lab l yFromSuccessRate)
      val ava lab l yGauge =
        stats.addGauge("ava lab l y") { ava lab l yFromSuccessRate(successRate).toFloat }
      overr de def apply[U](u: U)( mpl c  asT: <:<[U, Un ]): Boolean = underly ng.apply(u)
    }

  /**
   * Tracks number of successes and fa lures as counters, and success_rate as a gauge
   */
  def observed(stats: StatsRece ver) = {
    val successCounter = stats.counter("successes")
    val fa lureCounter = stats.counter("fa lures")
    new SuccessRateTracker {
      pr vate[t ] val successRateGauge = stats.addGauge("success_rate")(successRate.toFloat)
      overr de def record(successes:  nt, fa lures:  nt) = {
        self.record(successes, fa lures)
        successCounter. ncr(successes)
        fa lureCounter. ncr(fa lures)
      }
      overr de def successRate = self.successRate
    }
  }
}

object SuccessRateTracker {

  /**
   * Track success rate (SR) us ng [[RecentAverage]]
   *
   * Defaults success rate to 100% wh ch prevents early fa lures (or per ods of 0 data po nts,
   * e.g. track ng backend SR dur ng fa lover) from produc ng dramat c drops  n success rate.
   *
   * @param w ndow W ndow s ze as durat on
   */
  def recentW ndo d(w ndow: Durat on) =
    new AverageSuccessRateTracker(new RecentAverage(w ndow, defaultAverage = 1.0))

  /**
   * Track success rate us ng [[W ndo dAverage]]
   *
   *  n  al zes t  w ndo dAverage to one w ndow's worth of successes.  T  prevents
   * t  problem w re early fa lures produce dramat c drops  n t  success rate.
   *
   * @param w ndowS ze W ndow s ze  n number of data po nts
   */
  def roll ngW ndow(w ndowS ze:  nt) =
    new AverageSuccessRateTracker(new W ndo dAverage(w ndowS ze,  n  alValue = So (1.0)))
}

/**
 * Tracks success rate us ng an [[Average]]
 *
 * @param average Strategy for record ng an average, usually over a w ndow
 */
class AverageSuccessRateTracker(average: Average) extends SuccessRateTracker {
  def record(successes:  nt, fa lures:  nt): Un  =
    average.record(successes, successes + fa lures)

  def successRate: Double = average.value.getOrElse(1)
}

/**
 * EwmaSuccessRateTracker computes a fa lure rate w h exponent al decay over a t   bound.
 *
 * @param halfL fe determ nes t  rate of decay. Assum ng a hypot t cal serv ce that  s  n  ally
 * 100% successful and t n  nstantly sw c s to 50% successful,   w ll take `halfL fe` t  
 * for t  tracker to report a success rate of ~75%.
 */
class EwmaSuccessRateTracker(halfL fe: Durat on) extends SuccessRateTracker {
  // math.exp(-x) = 0.50 w n x == ln(2)
  // math.exp(-x / Tau) == math.exp(-x / halfL fe * ln(2)) t refore w n x/halfL fe == 1, t 
  // decay output  s 0.5
  pr vate[t ] val Tau: Double = halfL fe. nNanoseconds.toDouble / math.log(2.0)

  pr vate[t ] var stamp: Long = EwmaSuccessRateTracker.nanoT  ()
  pr vate[t ] var decay ngFa lureRate: Double = 0.0

  def record(successes:  nt, fa lures:  nt): Un  = {
     f (successes < 0 || fa lures < 0) return

    val total = successes + fa lures
     f (total == 0) return

    val observat on = (fa lures.toDouble / total) max 0.0 m n 1.0

    synchron zed {
      val t   = EwmaSuccessRateTracker.nanoT  ()
      val delta = ((t   - stamp) max 0L).toDouble
      val   ght = math.exp(-delta / Tau)
      decay ngFa lureRate = (decay ngFa lureRate *   ght) + (observat on * (1.0 -   ght))
      stamp = t  
    }
  }

  /**
   *  T  current success rate computed as t   nverse of t  fa lure rate.
   */
  def successRate: Double = 1.0 - fa lureRate

  def fa lureRate = synchron zed { decay ngFa lureRate }
}

pr vate[servo] tra  NanoT  Control {
  def set(nanoT  : Long): Un 
  def advance(delta: Long): Un 
  def advance(delta: Durat on): Un  = advance(delta. nNanoseconds)
}

object EwmaSuccessRateTracker {
  pr vate[EwmaSuccessRateTracker] val localNanoT   = new Local[() => Long]

  pr vate[EwmaSuccessRateTracker] def nanoT  (): Long = {
    localNanoT  () match {
      case None => System.nanoT  ()
      case So (f) => f()
    }
  }

  /**
   * Execute body w h t  t   funct on replaced by `t  Funct on`
   * WARN NG: T   s only  ant for test ng purposes.
   */
  pr vate[t ] def w hNanoT  Funct on[A](
    t  Funct on: => Long
  )(
    body: NanoT  Control => A
  ): A = {
    @volat le var tf = () => t  Funct on

    localNanoT  .let(() => tf()) {
      val t  Control = new NanoT  Control {
        def set(nanoT  : Long): Un  = {
          tf = () => nanoT  
        }
        def advance(delta: Long): Un  = {
          val newNanoT   = tf() + delta
          tf = () => newNanoT  
        }
      }

      body(t  Control)
    }
  }

  pr vate[t ] def w hNanoT  At[A](nanoT  : Long)(body: NanoT  Control => A): A =
    w hNanoT  Funct on(nanoT  )(body)

  pr vate[servo] def w hCurrentNanoT  Frozen[A](body: NanoT  Control => A): A =
    w hNanoT  At(System.nanoT  ())(body)
}
