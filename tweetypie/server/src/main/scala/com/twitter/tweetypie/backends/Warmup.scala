package com.tw ter.t etyp e
package backends

 mport com.tw ter.concurrent.AsyncSemaphore
 mport com.tw ter.ut l.T  r
 mport com.tw ter.ut l.Prom se
 mport scala.ut l.control.NoStackTrace

/**
 * Tools for bu ld ng warmup act ons on backend cl ents. T  bas c
 *  dea  s to make requests to backends repeatedly unt l t y succeed.
 */
object Warmup {

  /**
   * S gnals that a warmup act on was aborted because warmup  s
   * complete.
   */
  object WarmupComplete extends Except on w h NoStackTrace

  /**
   * Conf gurat on for warmup act ons.
   *
   * @param maxOutstand ngRequests: L m  on total number of outstand ng warmup requests.
   * @param maxWarmupDurat on: Total amount of t   warmup  s allo d to take.
   * @param requestT  outs: T   l m  for  nd v dual warmup act ons.
   * @param rel ab l y: Cr er a for how many t  s each warmup should be run.
   */
  case class Sett ngs(
    maxOutstand ngRequests:  nt,
    maxWarmupDurat on: Durat on,
    requestT  outs: Map[Str ng, Durat on],
    rel ab l y: Rel ably) {
    def toRunner(logger: Logger, t  r: T  r): Runner =
      new W hT  outs(requestT  outs, t  r)
        .w h n(new Logged(logger))
        .w h n(new L m edConcurrency(maxOutstand ngRequests))
        .w h n(rel ab l y)

    def apply[A: Warmup](value: A, logger: Logger, t  r: T  r): Future[Un ] =
      toRunner(logger, t  r)
        .run(value)
        .ra seW h n(maxWarmupDurat on)(t  r)
        .handle { case _ => }
  }

  /**
   * Strategy for runn ng Warmup act ons.
   */
  tra  Runner { self =>

    /**
     * Run one s ngle warmup act on.
     */
    def runOne(na : Str ng, act on: => Future[Un ]): Future[Un ]

    /**
     * Compose t se two Runners by call ng t  Runner's runOne
     *  ns de of ot r's runOne.
     */
    f nal def w h n(ot r: Runner): Runner =
      new Runner {
        overr de def runOne(na : Str ng, act on: => Future[Un ]): Future[Un ] =
          ot r.runOne(na , self.runOne(na , act on))
      }

    /**
     * Execute all of t  warmup act ons for t  g ven value us ng
     * t  runner.
     */
    f nal def run[T](t: T)( mpl c  w: Warmup[T]): Future[Un ] =
      Future.jo n(w.act ons.toSeq.map { case (na , f) => runOne(na , f(t).un ) })
  }

  /**
   * Set a ce l ng on t  amount of t   each k nd of warmup act on  s
   * allo d to take.
   */
  class W hT  outs(t  outs: Map[Str ng, Durat on], t  r: T  r) extends Runner {
    overr de def runOne(na : Str ng, act on: => Future[Un ]): Future[Un ] =
      t  outs.get(na ).map(act on.ra seW h n(_)(t  r)).getOrElse(act on)
  }

  /**
   * Execute each act on unt l  s rel ab l y  s est mated to be
   * above t  g ven threshold. T  rel ab l y  s  n  ally assu d
   * to be zero. T  rel ab l y  s est mated as an exponent al mov ng
   * average, w h t  new data po nt g ven t  appropr ate   ght so
   * that a s ngle data po nt w ll no longer be able to push t 
   * average below t  threshold.
   *
   * T  warmup act on  s cons dered successful  f   does not throw
   * an except on. No t  outs are appl ed.
   *
   * T  threshold must be  n t   nterval [0, 1).
   *
   * T  concurrency level determ nes how many outstand ng requests
   * to ma nta n unt l t  threshold  s reac d. T  allows warmup
   * to happen more rap dly w n  nd v dual requests have h gh
   * latency.
   *
   * maxAttempts l m s t  total number of tr es that   are allo d
   * to try to reach t  rel ab l y threshold. T   s a safety
   *  asure to prevent overload ng whatever subsystem   are
   * attempt ng to warm up.
   */
  case class Rel ably(rel ab l yThreshold: Double, concurrency:  nt, maxAttempts:  nt)
      extends Runner {
    requ re(rel ab l yThreshold < 1)
    requ re(rel ab l yThreshold >= 0)
    requ re(concurrency > 0)
    requ re(maxAttempts > 0)

    // F nd t    ght at wh ch one fa lure w ll not push us under t 
    // rel ab l yThreshold.
    val   ght: Double = 1 - math.pow(
      1 - rel ab l yThreshold,
      (1 - rel ab l yThreshold) / rel ab l yThreshold
    )

    // Make sure that round ng error d d not cause   ght to beco  zero.
    requ re(  ght > 0)
    requ re(  ght <= 1)

    // On each  erat on,   d scount t  current rel ab l y by t 
    // factor before add ng  n t  new rel ab l y data po nt.
    val decay: Double = 1 -   ght

    // Make sure that round ng error d d not cause decay to be zero.
    requ re(decay < 1)

    overr de def runOne(na : Str ng, act on: => Future[Un ]): Future[Un ] = {
      def go(attempts:  nt, rel ab l y: Double, outstand ng: Seq[Future[Un ]]): Future[Un ] =
         f (rel ab l y >= rel ab l yThreshold || (attempts == 0 && outstand ng. sEmpty)) {
          //   h  t  threshold or ran out of tr es.  Don't cancel any
          // outstand ng requests, just wa  for t m all to complete.
          Future.jo n(outstand ng.map(_.handle { case _ => }))
        } else  f (attempts > 0 && outstand ng.length < concurrency) {
          //   have not yet h  t  rel ab l y threshold, and  
          // st ll have ava lable concurrency, so make a new request.
          go(attempts - 1, rel ab l y, act on +: outstand ng)
        } else {
          val sel = Future.select(outstand ng)

          //   need t  prom se wrapper because  f t  select  s
          //  nterrupted,   relays t   nterrupt to t  outstand ng
          // requests but does not  self return w h a
          // fa lure. Wrapp ng  n a prom se lets us d fferent ate
          // bet en an  nterrupt com ng from above and t  created
          // Future fa l ng for anot r reason.
          val p = new Prom se[(Try[Un ], Seq[Future[Un ]])]
          p.set nterruptHandler {
            case e =>
              //  nterrupt t  outstand ng requests.
              sel.ra se(e)
              // Halt t  computat on w h a fa lure.
              p.update fEmpty(Throw(e))
          }

          // W n t  select f n s s, update t  prom se w h t  value.
          sel.respond(p.update fEmpty)
          p.flatMap {
            case (tryRes, rema n ng) =>
              val delta =  f (tryRes. sReturn)   ght else 0
              go(attempts, rel ab l y * decay + delta, rema n ng)
          }
        }

      go(maxAttempts, 0, Seq.empty)
    }
  }

  /**
   * Wr e a log  ssage record ng each  nvocat on of each warmup
   * act on. T  log  ssage  s comma-separated, w h t  follow ng
   * f elds:
   *
   *     na :
   *         T  suppl ed na .
   *
   *     start t  :
   *         T  number of m ll seconds s nce t  start of t  Un x
   *         epoch.
   *
   *     durat on:
   *         How long t  warmup act on took,  n m ll seconds.
   *
   *     result:
   *         "passed" or "fa led" depend ng on w t r t  Future
   *         returned an except on.
   *
   *     except on type:
   *          f t  result "fa led", t n t  w ll be t  na  of
   *         t  except on that casued t  fa lure.  f   "passed",
   *           w ll be t  empty str ng.
   *
   * T se  ssages should be suff c ent to get a p cture of how
   * warmup proceeded, s nce t y allow t   ssages to be ordered
   * and sorted by type.   can use t   nformat on to tune t 
   * warmup para ters.
   */
  class Logged(logger: Logger) extends Runner {
    overr de def runOne(na : Str ng, act on: => Future[Un ]): Future[Un ] = {
      val start = T  .now
      val startStr = start.s nceEpoch. nM ll seconds.toStr ng

      act on.respond {
        case Throw(WarmupComplete) =>
        // Don't log anyth ng for computat ons that   abandoned
        // because warmup  s complete.

        case r =>
          val durat on = (T  .now - start). nM ll seconds
          val result = r match {
            case Throw(e) => "fa led," + e.toStr ng.takeWh le(_ != '\n')
            case _ => "passed,"
          }
          logger. nfo(s"$na ,${startStr}ms,${durat on}ms,$result")
      }
    }
  }

  /**
   * Ensure that no more than t  spec f ed number of  nvocat ons of a
   * warmup act on are happen ng at one t  .
   */
  class L m edConcurrency(l m :  nt) extends Runner {
    pr vate[t ] val sem = new AsyncSemaphore(l m )
    overr de def runOne(na : Str ng, act on: => Future[Un ]): Future[Un ] =
      sem.acqu reAndRun(act on)
  }

  /**
   * Create a new Warmup that performs t  s ngle act on.
   */
  def apply[A](na : Str ng)(f: A => Future[_]): Warmup[A] = new Warmup(Map(na  -> f))

  /**
   * Create a Warmup that does noth ng. T   s useful  n concert w h
   * warmF eld.
   */
  def empty[A]: Warmup[A] = new Warmup[A](Map.empty)
}

/**
 * A set of  ndependent warmup act ons. Each act on should be t 
 * m n mum work that can be done  n order to exerc se a code
 * path. Runners can be used to e.g. run t  act ons repeatedly or
 * w h t  outs.
 */
class Warmup[A](val act ons: Map[Str ng, A => Future[_]]) {
  def ++(ot r: Warmup[A]) = new Warmup[A](act ons ++ ot r.act ons)

  /**
   * T  na s of t   nd v dual warmup act ons that t  warmup  s
   * composed of.
   */
  def na s: Set[Str ng] = act ons.keySet

  /**
   * Create a new Warmup that does all of t  act ons of t  warmup
   * and add  onally does warmup on t  value spec f ed by `f`.
   */
  def warmF eld[B](f: A => B)( mpl c  w: Warmup[B]): Warmup[A] =
    new Warmup[A](act ons ++ (w.act ons.mapValues(f.andT n)))
}
