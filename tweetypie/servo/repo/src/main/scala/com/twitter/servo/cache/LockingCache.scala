package com.tw ter.servo.cac 

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.part  on ng.Fa lureAccrualExcept on
 mport com.tw ter.f nagle.Backoff
 mport com.tw ter.f nagle.stats.{NullStatsRece ver, Stat, StatsRece ver}
 mport com.tw ter.logg ng.{Level, Logger}
 mport com.tw ter.servo.ut l.{Except onCounter, RateL m  ngLogger}
 mport com.tw ter.ut l._
 mport scala.ut l.control.NoStackTrace

object Lock ngCac  {

  /**
   * f rst argu nt  s value to store, second argu nt  s value  n cac ,
   * returns an Opt on of t  value to be stored. None should be  nterpreted
   * as "don't store anyth ng"
   */
  type P cker[V] = (V, V) => Opt on[V]

  /**
   * argu nt  s value,  f any,  n cac .
   * return type  s value,  f any, to be stored  n cac .
   * return ng None  ans noth ng w ll be done.
   */
  type Handler[V] = Opt on[V] => Opt on[V]

  case class AlwaysSetHandler[V](value: Opt on[V]) extends Handler[V] {
    overr de def apply( gnored: Opt on[V]) = value
  }

  case class P ck ngHandler[V](newValue: V, p ck: P cker[V]) extends Handler[V] {
    overr de def apply( nCac : Opt on[V]): Opt on[V] =
       nCac  match {
        case None =>
          //  f noth ng  n cac , go a ad and store!
          So (newValue)
        case So (oldValue) =>
          //  f so th ng  n cac , store a p cked value based on
          // what's  n cac  and what's be ng stored
          p ck(newValue, oldValue)
      }

    // apparently case classes that extend funct ons don't get pretty toStr ng  thods
    overr de lazy val toStr ng = "P ck ngHandler(%s, %s)".format(newValue, p ck)
  }

  case class UpdateOnlyP ck ngHandler[V](newValue: V, p ck: P cker[V]) extends Handler[V] {
    overr de def apply( nCac : Opt on[V]): Opt on[V] =
       nCac  match {
        case None =>
          //  f noth ng  n cac , do not update
          None
        case So (oldValue) =>
          //  f so th ng  n cac , store a p cked value based on
          // what's  n cac  and what's be ng stored
          p ck(newValue, oldValue)
      }

    // apparently case classes that extend funct ons don't get pretty toStr ng  thods
    overr de lazy val toStr ng = "UpdateOnlyP ck ngHandler(%s, %s)".format(newValue, p ck)
  }
}

tra  Lock ngCac Factory {
  def apply[K, V](cac : Cac [K, V]): Lock ngCac [K, V]
  def scope(scopes: Str ng*): Lock ngCac Factory
}

/**
 * A cac  that enforces a cons stent v ew of values bet en t  t   w n a set
 *  s  n  ated and w n t  value  s actually updated  n cac .
 */
tra  Lock ngCac [K, V] extends Cac [K, V] {

  /**
   * Look up a value and d spatch based on t  result. T  part cular lock ng
   * approach  s def ned by t   mple nt ng class. May call handler mult ple
   * t  s as part of more elaborate lock ng and retry loop ng.
   *
   * Overv ew of semant cs:
   *   `handler(None)`  s called  f no value  s present  n cac .
   *   `handler(So (value))`  s called  f a value  s present.
   *   `handler(x)` should return None  f noth ng should be done and `So (value)`
   *    f a value should be set.
   *
   * @return t  value that was actually set
   */
  def lockAndSet(key: K, handler: Lock ngCac .Handler[V]): Future[Opt on[V]]
}

class Opt m st cLock ngCac Observer(statsRece ver: StatsRece ver) {
   mport Opt m st cLock ngCac ._

  pr vate[t ] val scopedRece ver = statsRece ver.scope("lock ng_cac ")

  pr vate[t ] val successCounter = scopedRece ver.counter("success")
  pr vate[t ] val fa lureCounter = scopedRece ver.counter("fa lure")
  pr vate[t ] val except onCounter = new Except onCounter(scopedRece ver)
  pr vate[t ] val lockAndSetStat = scopedRece ver.stat("lockAndSet")

  def t  [V](f: => Future[Opt on[V]]): Future[Opt on[V]] = {
    Stat.t  Future(lockAndSetStat) {
      f
    }
  }

  def success(attempts: Seq[Fa ledAttempt]): Un  = {
    successCounter. ncr()
    countAttempts(attempts)
  }

  def fa lure(attempts: Seq[Fa ledAttempt]): Un  = {
    fa lureCounter. ncr()
    countAttempts(attempts)
  }

  def scope(s: Str ng*): Opt m st cLock ngCac Observer =
    s.toL st match {
      case N l => t 
      case  ad :: ta l =>
        new Opt m st cLock ngCac Observer(statsRece ver.scope( ad)).scope(ta l: _*)
    }

  pr vate[t ] def countAttempts(attempts: Seq[Fa ledAttempt]): Un  = {
    attempts foreach { attempt =>
      val na  = attempt.getClass.getS mpleNa 
      scopedRece ver.counter(na ). ncr()
      attempt.maybeThrowable foreach { t =>
        except onCounter(t)
        scopedRece ver.scope(na ).counter(t.getClass.getNa ). ncr()
      }
    }
  }
}

case class Opt m st cLock ngCac Factory(
  backoffs: Backoff,
  observer: Opt m st cLock ngCac Observer = new Opt m st cLock ngCac Observer(NullStatsRece ver),
  t  r: T  r = new NullT  r,
  // Enabl ng key logg ng may un ntent onally cause  nclus on of sens  ve data
  //  n serv ce logs and any accompany ng log s nks such as Splunk. By default, t   s d sabled,
  // ho ver may be opt onally enabled for t  purpose of debugg ng. Caut on  s warranted.
  enableKeyLogg ng: Boolean = false)
    extends Lock ngCac Factory {
  def t (
    backoffs: Backoff,
    statsRece ver: StatsRece ver,
    t  r: T  r,
    enableKeyLogg ng: Boolean
  ) = t (backoffs, new Opt m st cLock ngCac Observer(statsRece ver), t  r, enableKeyLogg ng)

  overr de def apply[K, V](cac : Cac [K, V]): Lock ngCac [K, V] = {
    new Opt m st cLock ngCac (cac , backoffs, observer, t  r, enableKeyLogg ng)
  }

  overr de def scope(scopes: Str ng*): Lock ngCac Factory = {
    new Opt m st cLock ngCac Factory(backoffs, observer.scope(scopes: _*), t  r)
  }
}

object Opt m st cLock ngCac  {
  pr vate[t ] val FutureNone = Future.value(None)

  def emptyFutureNone[V] = FutureNone.as nstanceOf[Future[Opt on[V]]]

  sealed abstract class Fa ledAttempt(val maybeThrowable: Opt on[Throwable])
      extends Except on
      w h NoStackTrace
  case class GetW hC cksumExcept on(t: Throwable) extends Fa ledAttempt(So (t))
  case object GetW hC cksumEmpty extends Fa ledAttempt(None)
  case object C ckAndSetFa led extends Fa ledAttempt(None)
  case class C ckAndSetExcept on(t: Throwable) extends Fa ledAttempt(So (t))
  case class AddExcept on(t: Throwable) extends Fa ledAttempt(So (t))

  case class LockAndSetFa lure(str: Str ng, attempts: Seq[Fa ledAttempt])
      extends Except on(
        str,
        //  f t  last except on was an RPC except on, try to recover t  stack trace
        attempts.lastOpt on.flatMap(_.maybeThrowable).orNull
      )

  pr vate def retryPol cy(backoffs: Backoff): RetryPol cy[Try[Noth ng]] =
    RetryPol cy.backoff(backoffs) {
      case Throw(_: Fa lureAccrualExcept on) => false
      case _ => true
    }
}

/**
 *  mple ntat on of a Lock ngCac  us ng add/getW hC cksum/c ckAndSet.
 */
class Opt m st cLock ngCac [K, V](
  overr de val underly ngCac : Cac [K, V],
  retryPol cy: RetryPol cy[Try[Noth ng]],
  observer: Opt m st cLock ngCac Observer,
  t  r: T  r,
  enableKeyLogg ng: Boolean)
    extends Lock ngCac [K, V]
    w h Cac Wrapper[K, V] {
   mport Lock ngCac ._
   mport Opt m st cLock ngCac ._

  def t (
    underly ngCac : Cac [K, V],
    retryPol cy: RetryPol cy[Try[Noth ng]],
    observer: Opt m st cLock ngCac Observer,
    t  r: T  r,
  ) =
    t (
      underly ngCac : Cac [K, V],
      retryPol cy: RetryPol cy[Try[Noth ng]],
      observer: Opt m st cLock ngCac Observer,
      t  r: T  r,
      false
    )

  def t (
    underly ngCac : Cac [K, V],
    backoffs: Backoff,
    observer: Opt m st cLock ngCac Observer,
    t  r: T  r
  ) =
    t (
      underly ngCac ,
      Opt m st cLock ngCac .retryPol cy(backoffs),
      observer,
      t  r,
      false
    )

  def t (
    underly ngCac : Cac [K, V],
    backoffs: Backoff,
    observer: Opt m st cLock ngCac Observer,
    t  r: T  r,
    enableKeyLogg ng: Boolean
  ) =
    t (
      underly ngCac ,
      Opt m st cLock ngCac .retryPol cy(backoffs),
      observer,
      t  r,
      enableKeyLogg ng
    )

  pr vate[t ] val log = Logger.get("Opt m st cLock ngCac ")
  pr vate[t ] val rateL m edLogger = new RateL m  ngLogger(logger = log)

  @deprecated("use RetryPol cy-based constructor", "0.1.2")
  def t (underly ngCac : Cac [K, V], maxTr es:  nt = 10, enableKeyLogg ng: Boolean) = {
    t (
      underly ngCac ,
      Backoff.const(0.m ll seconds).take(maxTr es),
      new Opt m st cLock ngCac Observer(NullStatsRece ver),
      new NullT  r,
      enableKeyLogg ng
    )
  }

  overr de def lockAndSet(key: K, handler: Handler[V]): Future[Opt on[V]] = {
    observer.t   {
      d spatch(key, handler, retryPol cy, N l)
    }
  }

  /**
   * @param key
   *   T  key to look up  n cac 
   * @param handler
   *   T  handler that  s appl ed to values from cac 
   * @param retryPol cy
   *   Used to determ ne  f more attempts should be made.
   * @param attempts
   *   Conta ns representat ons of t  causes of prev ous d spatch fa lures
   */
  protected[t ] def retry(
    key: K,
    fa lure: Try[Noth ng],
    handler: Handler[V],
    retryPol cy: RetryPol cy[Try[Noth ng]],
    attempts: Seq[Fa ledAttempt]
  ): Future[Opt on[V]] =
    retryPol cy(fa lure) match {
      case None =>
        observer.fa lure(attempts)
         f (enableKeyLogg ng) {
          rateL m edLogger.log(
            s"fa led attempts for ${key}:\n ${attempts.mkStr ng("\n ")}",
            level = Level. NFO)
          Future.except on(LockAndSetFa lure("lockAndSet fa led for " + key, attempts))
        } else {
          Future.except on(LockAndSetFa lure("lockAndSet fa led", attempts))
        }

      case So ((backoff, ta lPol cy)) =>
        t  r
          .doLater(backoff) {
            d spatch(key, handler, ta lPol cy, attempts)
          }
          .flatten
    }

  /**
   * @param key
   *   T  key to look up  n cac 
   * @param handler
   *   T  handler that  s appl ed to values from cac 
   * @param retryPol cy
   *   Used to determ ne  f more attempts should be made.
   * @param attempts
   *   Conta ns representat ons of t  causes of prev ous d spatch fa lures
   */
  protected[t ] def d spatch(
    key: K,
    handler: Handler[V],
    retryPol cy: RetryPol cy[Try[Noth ng]],
    attempts: Seq[Fa ledAttempt]
  ): Future[Opt on[V]] = {
    // get t  value  f noth ng's t re
    handler(None) match {
      case None =>
        //  f noth ng should be done w n m ss ng, go stra ght to getAndCond  onallySet,
        // s nce t re's noth ng to attempt an add w h
        getAndCond  onallySet(key, handler, retryPol cy, attempts)

      case so  @ So (value) =>
        // ot rw se, try to do an atom c add, wh ch w ll return false  f so th ng's t re
        underly ngCac .add(key, value) transform {
          case Return(added) =>
             f (added) {
              //  f added, return t  value
              observer.success(attempts)
              Future.value(so )
            } else {
              // ot rw se, do a c ckAndSet based on t  current value
              getAndCond  onallySet(key, handler, retryPol cy, attempts)
            }

          case Throw(t) =>
            // count except on aga nst retr es
             f (enableKeyLogg ng)
              rateL m edLogger.logThrowable(t, s"add($key) returned except on. w ll retry")
            retry(key, Throw(t), handler, retryPol cy, attempts :+ AddExcept on(t))
        }
    }
  }

  /**
   * @param key
   *   T  key to look up  n cac 
   * @param handler
   *   T  handler that  s appl ed to values from cac 
   * @param retryPol cy
   *   Used to determ ne  f more attempts should be made.
   * @param attempts
   *   Conta ns representat ons of t  causes of prev ous d spatch fa lures
   */
  protected[t ] def getAndCond  onallySet(
    key: K,
    handler: Handler[V],
    retryPol cy: RetryPol cy[Try[Noth ng]],
    attempts: Seq[Fa ledAttempt]
  ): Future[Opt on[V]] = {
    // look  n t  cac  to see what's t re
    underly ngCac .getW hC cksum(Seq(key)) handle {
      case t =>
        // treat global fa lure as key-based fa lure
        KeyValueResult(fa led = Map(key -> t))
    } flatMap { lr =>
      lr(key) match {
        case Return.None =>
          handler(None) match {
            case So (_) =>
              //  f t re's noth ng  n t  cac  now, but handler(None) return So ,
              // that  ans so th ng has changed s nce   attempted t  add, so try aga n
              val fa lure = GetW hC cksumEmpty
              retry(key, Throw(fa lure), handler, retryPol cy, attempts :+ fa lure)

            case None =>
              //  f t re's noth ng  n t  cac  now, but handler(None) returns None,
              // that  ans   don't want to store anyth ng w n t re's noth ng already
              //  n cac , so return None
              observer.success(attempts)
              emptyFutureNone
          }

        case Return(So ((Return(current), c cksum))) =>
          // t  cac  entry  s present
          d spatchC ckAndSet(So (current), c cksum, key, handler, retryPol cy, attempts)

        case Return(So ((Throw(t), c cksum))) =>
          // t  cac  entry fa led to deser al ze; treat   as a None and overwr e.
           f (enableKeyLogg ng)
            rateL m edLogger.logThrowable(
              t,
              s"getW hC cksum(${key}) returned a bad value. overwr  ng.")
          d spatchC ckAndSet(None, c cksum, key, handler, retryPol cy, attempts)

        case Throw(t) =>
          // lookup fa lure counts aga nst numTr es
           f (enableKeyLogg ng)
            rateL m edLogger.logThrowable(
              t,
              s"getW hC cksum(${key}) returned except on. w ll retry.")
          retry(key, Throw(t), handler, retryPol cy, attempts :+ GetW hC cksumExcept on(t))
      }
    }
  }

  /**
   * @param current
   *   T  value currently cac d under key `key`,  f any
   * @param c cksum
   *   T  c cksum of t  currently-cac d value
   * @param key
   *   T  key mapp ng to `current`
   * @param handler
   *   T  handler that  s appl ed to values from cac 
   * @param retryPol cy
   *   Used to determ ne  f more attempts should be made.
   * @param attempts
   *   Conta ns representat ons of t  causes of prev ous d spatch fa lures
   */
  protected[t ] def d spatchC ckAndSet(
    current: Opt on[V],
    c cksum: C cksum,
    key: K,
    handler: Handler[V],
    retryPol cy: RetryPol cy[Try[Noth ng]],
    attempts: Seq[Fa ledAttempt]
  ): Future[Opt on[V]] = {
    handler(current) match {
      case None =>
        //  f noth ng should be done based on t  current value, don't do anyth ng
        observer.success(attempts)
        emptyFutureNone

      case so  @ So (value) =>
        // ot rw se, try a c ck and set w h t  c cksum
        underly ngCac .c ckAndSet(key, value, c cksum) transform {
          case Return(added) =>
             f (added) {
              //  f added, return t  value
              observer.success(attempts)
              Future.value(so )
            } else {
              // ot rw se, so th ng has changed, try aga n
              val fa lure = C ckAndSetFa led
              retry(key, Throw(fa lure), handler, retryPol cy, attempts :+ fa lure)
            }

          case Throw(t) =>
            // count except on aga nst retr es
             f (enableKeyLogg ng)
              rateL m edLogger.logThrowable(
                t,
                s"c ckAndSet(${key}) returned except on. w ll retry.")
            retry(key, Throw(t), handler, retryPol cy, attempts :+ C ckAndSetExcept on(t))
        }
    }
  }
}

object NonLock ngCac Factory extends Lock ngCac Factory {
  overr de def apply[K, V](cac : Cac [K, V]): Lock ngCac [K, V] = new NonLock ngCac (cac )
  overr de def scope(scopes: Str ng*) = t 
}

class NonLock ngCac [K, V](overr de val underly ngCac : Cac [K, V])
    extends Lock ngCac [K, V]
    w h Cac Wrapper[K, V] {
  overr de def lockAndSet(key: K, handler: Lock ngCac .Handler[V]): Future[Opt on[V]] = {
    handler(None) match {
      case None =>
        //  f noth ng should be done w n noth ng's t re, don't do anyth ng
        Future.value(None)

      case so  @ So (value) =>
        set(key, value) map { _ =>
          so 
        }
    }
  }
}
