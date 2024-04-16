package com.tw ter.servo.cac 

 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle. mcac d.protocol.Value
 mport com.tw ter.f nagle. mcac d.GetResult
 mport com.tw ter.f nagle. mcac d.ProxyCl ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter. o.Buf
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.ut l.Future
 mport scala.collect on.breakOut

object HotKeyCach ngCac  {
  pr vate[cac ] val logger = Logger.get(getClass)
}

/**
 * Wrapper for a [[com.tw ter.f nagle. mcac d.Cl ent]] that handles  n-process cach ng for
 * values flagged for promot on ("hot keys") by a t mcac  backend.
 *
 * T   s s m lar conceptually to
 * [[com.tw ter.servo.repos ory.HotKeyCach ngKeyValueRepos ory]] but d ffers because
 *  HotKeyCach ngKeyValueRepos ory detects hot keys  n t  cl ent, wh ch requ res tun ng and
 *  beco s less effect ve as t  number of  nstances  n t  cluster grows. [[HotKey mcac Cl ent]]
 *  uses detect on  n t   mcac  server, wh ch  s central zed and has a better v ew of frequently
 *  accessed keys. T   s a custom feature  n t mcac , Tw ter's  mcac  fork, that  s not
 *  enabled by default. Consult w h t  cac  team  f   want to use  .
 *
 *  Usage:
 *  {{{
 *    new HotKey mcac Cl ent(
 *      underly ngCac  =  mcac d.cl ent. ... .newR chCl ent(dest nat on),
 *       nProcessCac  = Exp r ngLru nProcessCac (ttl = 10.seconds, max mumS ze = 100),
 *      statsRece ver = statsRece ver.scope(" nprocess")
 *    )
 *  }}}
 */
class HotKey mcac Cl ent(
  overr de val proxyCl ent: Cl ent,
   nProcessCac :  nProcessCac [Str ng, Value],
  statsRece ver: StatsRece ver,
  label: Opt on[Str ng] = None)
    extends ProxyCl ent {
   mport HotKeyCach ngCac ._

  pr vate val promot ons = statsRece ver.counter("promot ons")
  pr vate val h s = statsRece ver.counter("h s")
  pr vate val m sses = statsRece ver.counter("m sses")

  pr vate def cac  fPromoted(key: Str ng, value: Value): Un  = {
     f (value.flags.ex sts( mcac Flags.shouldPromote)) {
      logger.debug(s"Promot ng hot-key $key flagged by  mcac d backend to  n-process cac .")
      Trace.recordB nary("hot_key_cac .hot_key_promoted", s"${label.getOrElse("")},$key")
      promot ons. ncr()
       nProcessCac .set(key, value)
    }
  }

  overr de def getResult(keys:  erable[Str ng]): Future[GetResult] = {
    val resultsFrom nProcessCac : Map[Str ng, Value] =
      keys.flatMap(k =>  nProcessCac .get(k).map(v => (k, v)))(breakOut)
    val found nProcess = resultsFrom nProcessCac .keySet
    val newKeys = keys.f lterNot(found nProcess.conta ns)

    h s. ncr(found nProcess.s ze)
    m sses. ncr(newKeys.s ze)

     f (found nProcess.nonEmpty) {
      //  f t re are hot keys found  n t  cac , record a trace annotat on w h t  format:
      // hot key cac  cl ent label;t  number of h s;number of m sses;and t  set of hot keys found  n t  cac .
      Trace.recordB nary(
        "hot_key_cac ",
        s"${label.getOrElse("")};${found nProcess.s ze};${newKeys.s ze};${found nProcess.mkStr ng(",")}"
      )
    }

    proxyCl ent.getResult(newKeys).map { result =>
      result.h s.foreach { case (k, v) => cac  fPromoted(k, v) }
      result.copy(h s = result.h s ++ resultsFrom nProcessCac )
    }
  }

  /**
   * Exposes w t r or not a key was promoted to t   n-process hot key cac .  n most cases, users
   * of [[HotKey mcac Cl ent]] should not need to know t . Ho ver, t y may  f hot key cach ng
   * confl cts w h ot r layers of cach ng t y are us ng.
   */
  def  sHotKey(key: Str ng): Boolean =  nProcessCac .get(key). sDef ned
}

// TOOD: May want to turn flags  nto a value class  n com.tw ter.f nagle. mcac d
// w h  thods for t se operat ons
object  mcac Flags {
  val FrequencyBasedPromot on:  nt = 1
  val Bandw dthBasedPromot on:  nt = 1 << 1
  val Promotable:  nt = FrequencyBasedPromot on | Bandw dthBasedPromot on

  /**
   *  mcac  flags are returned as an uns gned  nteger, represented as a dec mal str ng.
   *
   * C ck w t r t  b   n pos  on 0 ([[FrequencyBasedPromot on]]) or t  b   n pos  on 1
   * ([[Bandw dthBasedPromot on]])  s set to 1 (zero- ndex from least-s gn f cant b ).
   */
  def shouldPromote(flagsBuf: Buf): Boolean = {
    val flags = flagsBuf match { case Buf.Utf8(s) => s.to nt }
    (flags & Promotable) != 0
  }
}
