package com.tw ter.follow_recom ndat ons.common.cl ents.cac 

 mport com.tw ter.b ject on.B ject on
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter. o.Buf
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport java.secur y. ssageD gest

object  mcac Cl ent {
  def apply[V](
    cl ent: Cl ent,
    dest: Str ng,
    valueB ject on: B ject on[Buf, V],
    ttl: Durat on,
    statsRece ver: StatsRece ver
  ):  mcac Cl ent[V] = {
    new  mcac Cl ent(cl ent, dest, valueB ject on, ttl, statsRece ver)
  }
}

class  mcac Cl ent[V](
  cl ent: Cl ent,
  dest: Str ng,
  valueB ject on: B ject on[Buf, V],
  ttl: Durat on,
  statsRece ver: StatsRece ver) {
  val cac  = cl ent.newR chCl ent(dest).adapt[V](valueB ject on)
  val cac Ttl = T  .fromSeconds(ttl. nSeconds)

  /**
   *  f cac  conta ns key, return value from cac . Ot rw se, run t  underly ng call
   * to fetch t  value, store    n cac , and t n return t  value.
   */
  def readThrough(
    key: Str ng,
    underly ngCall: () => St ch[V]
  ): St ch[V] = {
    val cac dResult: St ch[Opt on[V]] = St ch
      .callFuture(get fPresent(key))
      .w h n(70.m ll second)(DefaultT  r)
      .rescue {
        case e: Except on =>
          statsRece ver.scope("rescued").counter(e.getClass.getS mpleNa ). ncr()
          St ch(None)
      }
    val resultSt ch = cac dResult.map { resultOpt on =>
      resultOpt on match {
        case So (cac Value) => St ch.value(cac Value)
        case None =>
          val underly ngCallSt ch = prof leSt ch(
            underly ngCall(),
            statsRece ver.scope("underly ngCall")
          )
          underly ngCallSt ch.map { result =>
            put(key, result)
            result
          }
      }
    }.flatten
    // prof le t  overall St ch, and return t  result
    prof leSt ch(resultSt ch, statsRece ver.scope("readThrough"))
  }

  def get fPresent(key: Str ng): Future[Opt on[V]] = {
    cac 
      .get(hashStr ng(key))
      .onSuccess {
        case So (value) => statsRece ver.counter("cac _h s"). ncr()
        case None => statsRece ver.counter("cac _m sses"). ncr()
      }
      .onFa lure {
        case e: Except on =>
          statsRece ver.counter("cac _m sses"). ncr()
          statsRece ver.scope("rescued").counter(e.getClass.getS mpleNa ). ncr()
      }
      .rescue {
        case _ => Future.None
      }
  }

  def put(key: Str ng, value: V): Future[Un ] = {
    cac .set(hashStr ng(key), 0, cac Ttl, value)
  }

  /**
   * Hash t   nput key str ng to a f xed length format us ng SHA-256 hash funct on.
   */
  def hashStr ng( nput: Str ng): Str ng = {
    val bytes =  ssageD gest.get nstance("SHA-256").d gest( nput.getBytes("UTF-8"))
    bytes.map("%02x".format(_)).mkStr ng
  }

  /**
   *  lper funct on for t m ng a st ch, return ng t  or g nal st ch.
   *
   * Def n ng t  prof l ng funct on  re to keep t  dependenc es of t  class
   * gener c and easy to export ( .e. copy-and-paste)  nto ot r serv ces or packages.
   */
  def prof leSt ch[T](st ch: St ch[T], stat: StatsRece ver): St ch[T] = {
    St ch
      .t  (st ch)
      .map {
        case (response, st chRunDurat on) =>
          stat.counter("requests"). ncr()
          stat.stat("latency_ms").add(st chRunDurat on. nM ll seconds)
          response
            .onSuccess { _ => stat.counter("success"). ncr() }
            .onFa lure { e =>
              stat.counter("fa lures"). ncr()
              stat.scope("fa lures").counter(e.getClass.getS mpleNa ). ncr()
            }
      }
      .lo rFromTry
  }
}
