package com.tw ter.servo.cac 

 mport com.tw ter.f nagle.stats.{Stat, StatsRece ver}
 mport com.tw ter.logg ng.{Level, Logger}
 mport com.tw ter.servo.ut l.{Except onCounter, W ndo dAverage}
 mport com.tw ter.ut l._

/**
 * track h s and m sses  n cac s, t   reads and wr es
 */
tra  Cac Observer {

  /**
   * reg ster a h 
   */
  def h (key: Str ng): Un 

  /**
   * reg ster a m ss
   */
  def m ss(key: Str ng): Un 

  /**
   * t   t  read, and automat cally handle h s and m sses from t  KeyValueResult
   */
  def read[K, T](
    na : Str ng,
    keys: Seq[K]
  )(
    f: => Future[KeyValueResult[K, T]]
  ): Future[KeyValueResult[K, T]]

  /**
   * t   t  wr e
   */
  def wr e[K, T](na : Str ng, key: K)(f: => Future[T]): Future[T]

  /**
   * t   t   ncr, and record t  success/fa lure
   */
  def  ncr[K](na : Str ng, key: Seq[K])(f: => Future[Opt on[Long]]): Future[Opt on[Long]]

  /**
   * produce a new Cac Observer w h a nested scope
   */
  def scope(s: Str ng*): Cac Observer

  /**
   *  ncre nt a counter track ng t  number of exp rat ons.
   */
  def exp red(delta:  nt = 1): Un 

  /**
   *  ncre nt a counter track ng t  number of fa lures.
   */
  def fa lure(delta:  nt = 1): Un 

  /**
   *  ncre nt a counter track ng t  number of tombstones.
   */
  def tombstone(delta:  nt = 1): Un 

  /**
   *  ncre nt a counter track ng t  number of not cac d.
   */
  def noCac (delta:  nt = 1): Un 
}

object NullCac Observer extends Cac Observer {
  overr de def h (key: Str ng) = ()
  overr de def m ss(key: Str ng) = ()
  overr de def read[K, T](na : Str ng, keys: Seq[K])(f: => Future[KeyValueResult[K, T]]) = f
  overr de def wr e[K, T](na : Str ng, key: K)(f: => Future[T]) = f
  overr de def  ncr[K](na : Str ng, key: Seq[K])(f: => Future[Opt on[Long]]) = f
  overr de def scope(s: Str ng*) = t 
  overr de def exp red(delta:  nt = 1) = ()
  overr de def fa lure(delta:  nt = 1): Un  = {}
  overr de def tombstone(delta:  nt = 1): Un  = {}
  overr de def noCac (delta:  nt = 1): Un  = {}
}

/**
 * A Cac Observer that wr es to a StatsRece ver
 */
class StatsRece verCac Observer(
  stats: StatsRece ver,
  w ndowS ze: Long,
  log: Logger,
  d sableLogg ng: Boolean = false)
    extends Cac Observer {

  def t (
    statsRece ver: StatsRece ver,
    w ndowS ze: Long,
    scope: Str ng
  ) =
    t (
      statsRece ver.scope(scope),
      w ndowS ze,
      Logger.get(scope.replaceAll("([a-z]+)([A-Z])", "$1_$2").toLo rCase)
    )

  def t (
    statsRece ver: StatsRece ver,
    w ndowS ze: Long,
    scope: Str ng,
    d sableLogg ng: Boolean
  ) =
    t (
      statsRece ver.scope(scope),
      w ndowS ze,
      Logger.get(scope.replaceAll("([a-z]+)([A-Z])", "$1_$2").toLo rCase),
      d sableLogg ng
    )

  protected[t ] val exp rat onCounter = stats.counter("exp rat ons")

  // needed to make sure   hand out t  sa  observer for each scope,
  // so that t  h  rates are properly calculated
  protected[t ] val ch ldren =  mo ze {
    new StatsRece verCac Observer(stats, w ndowS ze, _: Str ng, d sableLogg ng)
  }

  protected[t ] val except onCounter = new Except onCounter(stats)
  pr vate[t ] val h Counter = stats.counter("h s")
  pr vate[t ] val m ssCounter = stats.counter("m sses")
  pr vate[t ] val fa luresCounter = stats.counter("fa lures")
  pr vate[t ] val tombstonesCounter = stats.counter("tombstones")
  pr vate[t ] val noCac Counter = stats.counter("noCac ")

  pr vate[t ] val w ndo dH Rate = new W ndo dAverage(w ndowS ze)
  pr vate[t ] val w ndo d ncrH Rate = new W ndo dAverage(w ndowS ze)

  pr vate[t ] val h RateGauge = stats.addGauge("h _rate") {
    w ndo dH Rate.value.getOrElse(1.0).toFloat
  }

  pr vate[t ] val  ncrH RateGauge = stats.addGauge(" ncr_h _rate") {
    w ndo d ncrH Rate.value.getOrElse(1.0).toFloat
  }

  protected[t ] def handleThrowable[K](na : Str ng, t: Throwable, key: Opt on[K]): Un  = {
    stats.counter(na  + "_fa lures"). ncr()
    except onCounter(t)
     f (!d sableLogg ng) {
      lazy val suff x = key
        .map { k =>
          "(" + k.toStr ng + ")"
        }
        .getOrElse("")
      log.warn ng("%s%s caught: %s", na , suff x, t.getClass.getNa )
      log.trace(t, "stack trace was: ")
    }
  }

  overr de def h (key: Str ng): Un  = {
    h s(1)
     f (!d sableLogg ng)
      log.trace("cac  h : %s", key)
  }

  pr vate[t ] def h s(n:  nt): Un  = {
    w ndo dH Rate.record(n.toDouble, n.toDouble)
    h Counter. ncr(n)
  }

  overr de def m ss(key: Str ng): Un  = {
    m sses(1)
     f (!d sableLogg ng)
      log.trace("cac  m ss: %s", key)
  }

  pr vate[t ] def m sses(n:  nt): Un  = {
    w ndo dH Rate.record(0.0F, n.toDouble)
    m ssCounter. ncr(n)
  }

  overr de def read[K, T](
    na : Str ng,
    keys: Seq[K]
  )(
    f: => Future[KeyValueResult[K, T]]
  ): Future[KeyValueResult[K, T]] =
    Stat
      .t  Future(stats.stat(na )) {
        stats.counter(na ). ncr()
        f
      }
      .respond {
        case Return(lr) =>
           f (log. sLoggable(Level.TRACE)) {
            lr.found.keys.foreach { k =>
              h (k.toStr ng)
            }
            lr.notFound.foreach { k =>
              m ss(k.toStr ng)
            }
          } else {
            h s(lr.found.keys.s ze)
            m sses(lr.notFound.s ze)
          }
          lr.fa led foreach {
            case (k, t) =>
              handleThrowable(na , t, So (k))
              // count fa lures as m sses
              m ss(k.toStr ng)
              fa luresCounter. ncr()
          }
        case Throw(t) =>
          handleThrowable(na , t, None)
          // count fa lures as m sses
          keys.foreach { k =>
            m ss(k.toStr ng)
          }
          fa luresCounter. ncr()
      }

  overr de def wr e[K, T](na : Str ng, key: K)(f: => Future[T]): Future[T] =
    Stat.t  Future(stats.stat(na )) {
      stats.counter(na ). ncr()
      f
    } onFa lure {
      handleThrowable(na , _, So (key))
    }

  overr de def  ncr[K](na : Str ng, key: Seq[K])(f: => Future[Opt on[Long]]) =
    Stat.t  Future(stats.stat(na )) {
      stats.counter(na ). ncr()
      f
    } onSuccess { optVal =>
      val h  = optVal. sDef ned
      w ndo d ncrH Rate.record( f (h ) 1F else 0F)
      stats.counter(na  + ( f (h ) "_h s" else "_m sses")). ncr()
    }

  overr de def scope(s: Str ng*) =
    s.toL st match {
      case N l => t 
      case  ad :: ta l => ch ldren( ad).scope(ta l: _*)
    }

  overr de def exp red(delta:  nt = 1): Un  = { exp rat onCounter. ncr(delta) }
  overr de def fa lure(delta:  nt = 1): Un  = { fa luresCounter. ncr(delta) }
  overr de def tombstone(delta:  nt = 1): Un  = { tombstonesCounter. ncr(delta) }
  overr de def noCac (delta:  nt = 1): Un  = { noCac Counter. ncr(delta) }

}

/**
 * Wraps an underly ng cac  w h calls to a Cac Observer
 */
class ObservableReadCac [K, V](underly ngCac : ReadCac [K, V], observer: Cac Observer)
    extends ReadCac [K, V] {
  overr de def get(keys: Seq[K]): Future[KeyValueResult[K, V]] = {
    observer.read("get", keys) {
      underly ngCac .get(keys)
    }
  }

  overr de def getW hC cksum(keys: Seq[K]): Future[CsKeyValueResult[K, V]] = {
    observer.read[K, (Try[V], C cksum)]("get_w h_c cksum", keys) {
      underly ngCac .getW hC cksum(keys)
    }
  }

  overr de def release() = underly ngCac .release()
}

object ObservableCac  {
  def apply[K, V](
    underly ngCac : Cac [K, V],
    statsRece ver: StatsRece ver,
    w ndowS ze: Long,
    na : Str ng
  ): Cac [K, V] =
    new ObservableCac (
      underly ngCac ,
      new StatsRece verCac Observer(statsRece ver, w ndowS ze, na )
    )

  def apply[K, V](
    underly ngCac : Cac [K, V],
    statsRece ver: StatsRece ver,
    w ndowS ze: Long,
    na : Str ng,
    d sableLogg ng: Boolean
  ): Cac [K, V] =
    new ObservableCac (
      underly ngCac ,
      new StatsRece verCac Observer(
        statsRece ver = statsRece ver,
        w ndowS ze = w ndowS ze,
        scope = na ,
        d sableLogg ng = d sableLogg ng)
    )

  def apply[K, V](
    underly ngCac : Cac [K, V],
    statsRece ver: StatsRece ver,
    w ndowS ze: Long,
    log: Logger
  ): Cac [K, V] =
    new ObservableCac (
      underly ngCac ,
      new StatsRece verCac Observer(statsRece ver, w ndowS ze, log)
    )
}

/**
 * Wraps an underly ng Cac  w h calls to a Cac Observer
 */
class ObservableCac [K, V](underly ngCac : Cac [K, V], observer: Cac Observer)
    extends ObservableReadCac (underly ngCac , observer)
    w h Cac [K, V] {
  overr de def add(key: K, value: V): Future[Boolean] =
    observer.wr e("add", key) {
      underly ngCac .add(key, value)
    }

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum): Future[Boolean] =
    observer.wr e("c ck_and_set", key) {
      underly ngCac .c ckAndSet(key, value, c cksum)
    }

  overr de def set(key: K, value: V): Future[Un ] =
    observer.wr e("set", key) {
      underly ngCac .set(key, value)
    }

  overr de def replace(key: K, value: V): Future[Boolean] =
    observer.wr e("replace", key) {
      underly ngCac .replace(key, value)
    }

  overr de def delete(key: K): Future[Boolean] =
    observer.wr e("delete", key) {
      underly ngCac .delete(key)
    }
}

object ObservableTtlCac  {
  def apply[K, V](
    underly ngCac : TtlCac [K, V],
    statsRece ver: StatsRece ver,
    w ndowS ze: Long,
    na : Str ng
  ): TtlCac [K, V] =
    new ObservableTtlCac (
      underly ngCac ,
      new StatsRece verCac Observer(statsRece ver, w ndowS ze, na )
    )
}

/**
 * Wraps an underly ng TtlCac  w h calls to a Cac Observer
 */
class ObservableTtlCac [K, V](underly ngCac : TtlCac [K, V], observer: Cac Observer)
    extends ObservableReadCac (underly ngCac , observer)
    w h TtlCac [K, V] {
  overr de def add(key: K, value: V, ttl: Durat on): Future[Boolean] =
    observer.wr e("add", key) {
      underly ngCac .add(key, value, ttl)
    }

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum, ttl: Durat on): Future[Boolean] =
    observer.wr e("c ck_and_set", key) {
      underly ngCac .c ckAndSet(key, value, c cksum, ttl)
    }

  overr de def set(key: K, value: V, ttl: Durat on): Future[Un ] =
    observer.wr e("set", key) {
      underly ngCac .set(key, value, ttl)
    }

  overr de def replace(key: K, value: V, ttl: Durat on): Future[Boolean] =
    observer.wr e("replace", key) {
      underly ngCac .replace(key, value, ttl)
    }

  overr de def delete(key: K): Future[Boolean] =
    observer.wr e("delete", key) {
      underly ngCac .delete(key)
    }
}

case class Observable mcac Factory( mcac Factory:  mcac Factory, cac Observer: Cac Observer)
    extends  mcac Factory {

  overr de def apply() =
    new Observable mcac ( mcac Factory(), cac Observer)
}

@deprecated("use Observable mcac Factory or Observable mcac  d rectly", "0.1.2")
object Observable mcac  {
  def apply(
    underly ngCac :  mcac ,
    statsRece ver: StatsRece ver,
    w ndowS ze: Long,
    na : Str ng
  ):  mcac  =
    new Observable mcac (
      underly ngCac ,
      new StatsRece verCac Observer(statsRece ver, w ndowS ze, na )
    )
}

class Observable mcac (underly ngCac :  mcac , observer: Cac Observer)
    extends ObservableTtlCac [Str ng, Array[Byte]](underly ngCac , observer)
    w h  mcac  {
  def  ncr(key: Str ng, delta: Long = 1): Future[Opt on[Long]] =
    observer. ncr(" ncr", key) {
      underly ngCac . ncr(key, delta)
    }

  def decr(key: Str ng, delta: Long = 1): Future[Opt on[Long]] =
    observer. ncr("decr", key) {
      underly ngCac .decr(key, delta)
    }
}
