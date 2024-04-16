package com.tw ter.t etyp e
package backends

 mport com.tw ter.f nagle.Backoff
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.flockdb.cl ent.{thr ftscala => flockdb, _}
 mport com.tw ter.servo
 mport com.tw ter.servo.ut l.RetryHandler
 mport com.tw ter.t etyp e.core.OverCapac y
 mport com.tw ter.t etyp e.ut l.RetryPol cyBu lder
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  outExcept on

object TFlock {
  val log = Logger(t .getClass)

  case class Conf g(
    requestT  out: Durat on,
    t  outBackoffs: Stream[Durat on],
    flockExcept onBackoffs: Stream[Durat on],
    overCapac yBackoffs: Stream[Durat on],
    defaultPageS ze:  nt = 1000) {
    def apply(svc: flockdb.FlockDB. thodPerEndpo nt, ctx: Backend.Context): TFlockCl ent = {
      val retryHandler =
        RetryHandler[Any](
          retryPol cy(t  outBackoffs, flockExcept onBackoffs, overCapac yBackoffs),
          ctx.t  r,
          ctx.stats
        )
      val rescueHandler = translateExcept ons.andT n(Future.except on)
      val except onCounter = new servo.ut l.Except onCounter(ctx.stats, "fa lures")
      val t  outExcept on = new T  outExcept on(s"tflock: $requestT  out")
      val wrapper =
        new Wrapp ngFunct on {
          def apply[T](f: => Future[T]): Future[T] =
            retryHandler {
              except onCounter {
                f.ra seW h n(ctx.t  r, requestT  out, t  outExcept on)
                  .onFa lure(logFlockExcept ons)
                  .rescue(rescueHandler)
              }
            }
        }

      val wrappedCl ent = new Wrapp ngFlockCl ent(svc, wrapper, wrapper)
      val statsCl ent = new StatsCollect ngFlockServ ce(wrappedCl ent, ctx.stats)
      new TFlockCl ent(statsCl ent, defaultPageS ze)
    }
  }

  def  sOverCapac y(ex: flockdb.FlockExcept on): Boolean =
    ex.errorCode match {
      case So (flockdb.Constants.READ_OVERCAPAC TY_ERROR) => true
      case So (flockdb.Constants.WR TE_OVERCAPAC TY_ERROR) => true
      case _ => false
    }

  /**
   * Bu lds a RetryPol cy for tflock operat ons that w ll retry t  outs w h t  spec f ed
   * t  out backoffs, and w ll retry non-overcapac y FlockExcept ons w h t 
   * spec f ed flockExcept onBackoffs backoffs, and w ll retry over-capac y except ons w h
   * t  spec f ed overCapac yBackoffs.
   */
  def retryPol cy(
    t  outBackoffs: Stream[Durat on],
    flockExcept onBackoffs: Stream[Durat on],
    overCapac yBackoffs: Stream[Durat on]
  ): RetryPol cy[Try[Any]] =
    RetryPol cy.comb ne[Try[Any]](
      RetryPol cyBu lder.t  outs[Any](t  outBackoffs),
      RetryPol cy.backoff(Backoff.fromStream(flockExcept onBackoffs)) {
        case Throw(ex: flockdb.FlockExcept on)  f ! sOverCapac y(ex) => true
        case Throw(_: flockdb.FlockQuotaExcept on) => false
      },
      RetryPol cy.backoff(Backoff.fromStream(overCapac yBackoffs)) {
        case Throw(ex: flockdb.FlockExcept on)  f  sOverCapac y(ex) => true
        case Throw(_: flockdb.FlockQuotaExcept on) => true
        case Throw(_: OverCapac y) => true
      }
    )

  val logFlockExcept ons: Throwable => Un  = {
    case t: flockdb.FlockExcept on => {
      log. nfo("FlockExcept on from TFlock", t)
    }
    case _ =>
  }

  /**
   * Converts FlockExcept ons w h overcapac y codes  nto t etyp e's OverCapac y.
   */
  val translateExcept ons: Part alFunct on[Throwable, Throwable] = {
    case t: flockdb.FlockQuotaExcept on =>
      OverCapac y(s"tflock: throttled ${t.descr pt on}")
    case t: flockdb.FlockExcept on  f  sOverCapac y(t) =>
      OverCapac y(s"tflock: ${t.descr pt on}")
  }
}
