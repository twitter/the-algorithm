package com.tw ter.t etyp e
package backends

 mport com.tw ter.f nagle.context.Deadl ne
 mport com.tw ter.f nagle.serv ce.RetryBudget
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.servo.ut l.RetryHandler
 mport com.tw ter.t etyp e.core.OverCapac y
 mport com.tw ter.ut l.T  r
 mport com.tw ter.ut l.T  outExcept on

object Backend {
  val log: Logger = Logger(getClass)

  /**
   * Common stuff that  s needed as part of t  conf gurat on of all
   * of t  backends.
   */
  case class Context(val t  r: T  r, val stats: StatsRece ver)

  /**
   * All backend operat ons are encapsulated  n t  FutureArrow type.  T  Bu lder type
   * represents funct ons that can decorate t  FutureArrow, typ cally by call ng t  var ous
   * comb nator  thods on FutureArrow.
   */
  type Bu lder[A, B] = FutureArrow[A, B] => FutureArrow[A, B]

  /**
   * A Pol cy def nes so  behav or to apply to a FutureArrow that wraps an endpo nt.
   */
  tra  Pol cy {

    /**
     * Us ng an endpo nt na  and Context, returns a Bu lder that does t  actual
     * appl cat on of t  pol cy to t  FutureArrow.
     */
    def apply[A, B](na : Str ng, ctx: Context): Bu lder[A, B]

    /**
     * Sequent ally comb nes pol c es, f rst apply ng t  pol cy and t n apply ng
     * t  next pol cy.  Order matters!  For example, to retry on t  outs, t  Fa lureRetryPol cy
     * needs to be appl ed after t  T  outPol cy:
     *
     *     T  outPol cy(100.m ll seconds) >>> Fa lureRetryPol cy(retryPol cy)
     */
    def andT n(next: Pol cy): Pol cy = {
      val f rst = t 
      new Pol cy {
        def apply[A, B](na : Str ng, ctx: Context): Bu lder[A, B] =
          f rst(na , ctx).andT n(next(na , ctx))

        overr de def toStr ng = s"$f rst >>> $next"
      }
    }

    /**
     * An al as for `andT n`.
     */
    def >>>(next: Pol cy): Pol cy = andT n(next)
  }

  /**
   * Appl es a t  out to t  underly ng FutureArrow.
   */
  case class T  outPol cy(t  out: Durat on) extends Pol cy {
    def apply[A, B](na : Str ng, ctx: Context): Bu lder[A, B] = {
      val stats = ctx.stats.scope(na )
      val ex = new T  outExcept on(na  + ": " + t  out)
      (_: FutureArrow[A, B]).ra seW h n(ctx.t  r, t  out, ex)
    }
  }

  /**
   * Attac s a RetryHandler w h t  g ven RetryPol cy to retry fa lures.
   */
  case class Fa lureRetryPol cy(
    retryPol cy: RetryPol cy[Try[Noth ng]],
    retryBudget: RetryBudget = RetryBudget())
      extends Pol cy {
    def apply[A, B](na : Str ng, ctx: Context): Bu lder[A, B] = {
      val stats = ctx.stats.scope(na )
      (_: FutureArrow[A, B])
        .retry(RetryHandler.fa luresOnly(retryPol cy, ctx.t  r, stats, retryBudget))
    }
  }

  /**
   * T  pol cy appl es standard zed endpo nt  tr cs.  T  should be used w h every endpo nt.
   */
  case object TrackPol cy extends Pol cy {
    def apply[A, B](na : Str ng, ctx: Context): Bu lder[A, B] = {
      val stats = ctx.stats.scope(na )
      (_: FutureArrow[A, B])
        .onFa lure(countOverCapac yExcept ons(stats))
        .trackOutco (ctx.stats, (_: A) => na )
        .trackLatency(ctx.stats, (_: A) => na )
    }
  }

  /**
   * T  default "pol cy" for t  outs, retr es, except on count ng, latency track ng, etc. to
   * apply to each backend operat on.  T  returns a Bu lder type (an endofunct on on FutureArrow),
   * wh ch can be composed w h ot r Bu lders v a s mple funct on compos  on.
   */
  def defaultPol cy[A, B](
    na : Str ng,
    requestT  out: Durat on,
    retryPol cy: RetryPol cy[Try[B]],
    ctx: Context,
    retryBudget: RetryBudget = RetryBudget(),
    totalT  out: Durat on = Durat on.Top,
    except onCategor zer: Throwable => Opt on[Str ng] = _ => None
  ): Bu lder[A, B] = {
    val scopedStats = ctx.stats.scope(na )
    val requestT  outExcept on = new T  outExcept on(
      s"$na : h  request t  out of $requestT  out"
    )
    val totalT  outExcept on = new T  outExcept on(s"$na : h  total t  out of $totalT  out")
    base =>
      base
        .ra seW h n(
          ctx.t  r,
          //   defer to a per-request deadl ne. W n t  deadl ne  s m ss ng or wasn't toggled,
          // 'requestT  out'  s used  nstead. T  m m cs t  behav or happen ng w h n a standard
          // F nagle cl ent stack and  s 'T  outF lter'.
          Deadl ne.currentToggled.fold(requestT  out)(_.rema n ng),
          requestT  outExcept on
        )
        .retry(RetryHandler(retryPol cy, ctx.t  r, scopedStats, retryBudget))
        .ra seW h n(ctx.t  r, totalT  out, totalT  outExcept on)
        .onFa lure(countOverCapac yExcept ons(scopedStats))
        .trackOutco (ctx.stats, (_: A) => na , except onCategor zer)
        .trackLatency(ctx.stats, (_: A) => na )
  }

  /**
   * An onFa lure FutureArrow callback that counts OverCapac y except ons to a spec al counter.
   * T se w ll also be counted as fa lures and by except on class na , but hav ng a spec al
   * counter for t   s eas er to use  n success rate computat ons w re   want to factor out
   * backpressure responses.
   */
  def countOverCapac yExcept ons[A](scopedStats: StatsRece ver): (A, Throwable) => Un  = {
    val overCapac yCounter = scopedStats.counter("over_capac y")

    {
      case (_, ex: OverCapac y) => overCapac yCounter. ncr()
      case _ => ()
    }
  }

  /**
   * Prov des a s mple  chan sm for apply ng a Pol cy to an endpo nt FutureArrow from
   * an underly ng serv ce  nterface.
   */
  class Pol cyAdvocate[S](backendNa : Str ng, ctx: Backend.Context, svc: S) {

    /**
     * Tacks on t  TrackPol cy to t  g ven base pol cy, and t n appl es t  pol cy to
     * a FutureArrow.  T   s more of a conven ence  thod that every Backend can use to
     * bu ld t  fully conf gured FutureArrow.
     */
    def apply[A, B](
      endpo ntNa : Str ng,
      pol cy: Pol cy,
      endpo nt: S => FutureArrow[A, B]
    ): FutureArrow[A, B] = {
      log. nfo(s"appl ng pol cy to $backendNa .$endpo ntNa : $pol cy")
      pol cy.andT n(TrackPol cy)(endpo ntNa , ctx)(endpo nt(svc))
    }
  }
}
