package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.Backoff
 mport com.tw ter.f nagle.serv ce.{RetryBudget, RetryPol cy}
 mport com.tw ter.f nagle.stats.{Counter, StatsRece ver}
 mport com.tw ter.ut l._
 mport java.ut l.concurrent.Cancellat onExcept on
 mport scala.ut l.control.NonFatal

/**
 * A RetryHandler can wrap an arb rary Future-produc ng operat on w h retry log c, w re t 
 * operat on may cond  onally be retr ed mult ple t  s.
 */
tra  RetryHandler[-A] {

  /**
   * Executes t  g ven operat on and performs any appl cable retr es.
   */
  def apply[A2 <: A](f: => Future[A2]): Future[A2]

  /**
   * Wraps an arb rary funct on w h t  RetryHandler's retry ng log c.
   */
  def wrap[A2 <: A, B](f: B => Future[A2]): B => Future[A2] =
    b => t (f(b))
}

object RetryHandler {

  /**
   * Bu lds a RetryHandler that retr es accord ng to t  g ven RetryPol cy.  Retr es,  f any,
   * w ll be sc duled on t  g ven T  r to be executed after t  appropr ate backoff,  f any.
   * Retr es w ll be l m ed accord ng t  g ven `RetryBudget`.
   */
  def apply[A](
    pol cy: RetryPol cy[Try[A]],
    t  r: T  r,
    statsRece ver: StatsRece ver,
    budget: RetryBudget = RetryBudget()
  ): RetryHandler[A] = {
    val f rstTryCounter = statsRece ver.counter("f rst_try")
    val retr esCounter = statsRece ver.counter("retr es")
    val budgetExhausedCounter = statsRece ver.counter("budget_exhausted")

    new RetryHandler[A] {
      def apply[A2 <: A](f: => Future[A2]): Future[A2] = {
        f rstTryCounter. ncr()
        budget.depos ()
        retry[A2](pol cy, t  r, retr esCounter, budgetExhausedCounter, budget)(f)
      }
    }
  }

  /**
   * Bu lds a RetryHandler that w ll only retry on fa lures that are handled by t  g ven pol cy,
   * and does not cons der any successful future for retr es.
   */
  def fa luresOnly[A](
    pol cy: RetryPol cy[Try[Noth ng]],
    t  r: T  r,
    statsRece ver: StatsRece ver,
    budget: RetryBudget = RetryBudget()
  ): RetryHandler[A] =
    apply(fa lureOnlyRetryPol cy(pol cy), t  r, statsRece ver, budget)

  /**
   * Bu lds a RetryHandler that w ll retry any fa lure accord ng to t  g ven backoff sc dule,
   * unt l e  r e  r t  operat on succeeds or all backoffs are exhausted.
   */
  def fa luresOnly[A](
    backoffs: Stream[Durat on],
    t  r: T  r,
    stats: StatsRece ver,
    budget: RetryBudget
  ): RetryHandler[A] =
    fa luresOnly(
      RetryPol cy.backoff[Try[Noth ng]](Backoff.fromStream(backoffs)) { case Throw(_) => true },
      t  r,
      stats,
      budget
    )

  /**
   * Bu lds a RetryHandler that w ll retry any fa lure accord ng to t  g ven backoff sc dule,
   * unt l e  r e  r t  operat on succeeds or all backoffs are exhausted.
   */
  def fa luresOnly[A](
    backoffs: Stream[Durat on],
    t  r: T  r,
    stats: StatsRece ver
  ): RetryHandler[A] =
    fa luresOnly(backoffs, t  r, stats, RetryBudget())

  /**
   * Converts a RetryPol cy that only handles fa lures (Throw) to a RetryPol cy that also
   * handles successes (Return), by flagg ng that successes need not be retr ed.
   */
  def fa lureOnlyRetryPol cy[A](pol cy: RetryPol cy[Try[Noth ng]]): RetryPol cy[Try[A]] =
    RetryPol cy[Try[A]] {
      case Return(_) => None
      case Throw(ex) =>
        pol cy(Throw(ex)) map {
          case (backoff, p2) => (backoff, fa lureOnlyRetryPol cy(p2))
        }
    }

  pr vate[t ] def retry[A](
    pol cy: RetryPol cy[Try[A]],
    t  r: T  r,
    retr esCounter: Counter,
    budgetExhausedCounter: Counter,
    budget: RetryBudget
  )(
    f: => Future[A]
  ): Future[A] = {
    forceFuture(f).transform { transfor d =>
      pol cy(transfor d) match {
        case So ((backoff, nextPol cy)) =>
           f (budget.tryW hdraw()) {
            retr esCounter. ncr()
            sc dule(backoff, t  r) {
              retry(nextPol cy, t  r, retr esCounter, budgetExhausedCounter, budget)(f)
            }
          } else {
            budgetExhausedCounter. ncr()
            Future.const(transfor d)
          }
        case None =>
          Future.const(transfor d)
      }
    }
  }

  // s m lar to f nagle's RetryExcept onsF lter
  pr vate[t ] def sc dule[A](d: Durat on, t  r: T  r)(f: => Future[A]) = {
     f (d. nNanoseconds > 0) {
      val prom se = new Prom se[A]
      val task = t  r.sc dule(T  .now + d) {
         f (!prom se. sDef ned) {
          try {
            prom se.beco (f)
          } catch {
            case NonFatal(cause) =>
            //  gnore any except ons thrown by Prom se#beco (). T  usually  ans that t  prom se
            // was already def ned and cannot be transfor d.
          }
        }
      }
      prom se.set nterruptHandler {
        case cause =>
          task.cancel()
          val cancellat on = new Cancellat onExcept on
          cancellat on. n Cause(cause)
          prom se.update fEmpty(Throw(cancellat on))
      }
      prom se
    } else forceFuture(f)
  }

  // (Future { f } flatten), but w hout t  allocat on
  pr vate[t ] def forceFuture[A](f: => Future[A]) = {
    try {
      f
    } catch {
      case NonFatal(cause) =>
        Future.except on(cause)
    }
  }
}
