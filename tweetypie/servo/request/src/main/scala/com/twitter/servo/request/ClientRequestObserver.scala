package com.tw ter.servo.request

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future

object Cl entRequestObserver {
  pr vate[request] val noCl ent dKey = "no_cl ent_ d"
}

/**
 * Prov des per-request stats based on F nagle Cl ent d.
 *
 * @param statsRece ver t  StatsRece ver used for count ng
 * @param observeAuthor zat onAttempts:  f true (t  default), observe all attempts.  f false,
 *   only fa lures (unauthor zed attempts) are observed.
 */
class Cl entRequestObserver(
  statsRece ver: StatsRece ver,
  observeAuthor zat onAttempts: Boolean = true)
    extends ((Str ng, Opt on[Seq[Str ng]]) => Future[Un ]) {
   mport Cl entRequestObserver.noCl ent dKey

  protected[t ] val scopedRece ver = statsRece ver.scope("cl ent_request")
  protected[t ] val unauthor zedRece ver = scopedRece ver.scope("unauthor zed")
  protected[t ] val unauthor zedCounter = scopedRece ver.counter("unauthor zed")

  /**
   * @param  thodNa  t  na  of t  Serv ce  thod be ng called
   * @param cl ent dScopesOpt opt onal sequence of scope str ngs represent ng t 
   *   or g nat ng request's Cl ent d
   */
  overr de def apply( thodNa : Str ng, cl ent dScopesOpt: Opt on[Seq[Str ng]]): Future[Un ] = {
     f (observeAuthor zat onAttempts) {
      scopedRece ver.counter( thodNa ). ncr()
      cl ent dScopesOpt match {
        case So (cl ent dScopes) =>
          scopedRece ver.scope( thodNa ).counter(cl ent dScopes: _*). ncr()

        case None =>
          scopedRece ver.scope( thodNa ).counter(noCl ent dKey). ncr()
      }
    }
    Future.Done
  }

  /**
   *  ncre nts a counter for unauthor zed requests.
   */
  def unauthor zed( thodNa : Str ng, cl ent dStr: Str ng): Un  = {
    unauthor zedCounter. ncr()
    unauthor zedRece ver.scope( thodNa ).counter(cl ent dStr). ncr()
  }

  def author zed( thodNa : Str ng, cl ent dStr: Str ng): Un  = {}
}

object NullCl entRequestObserver extends Cl entRequestObserver(NullStatsRece ver)
