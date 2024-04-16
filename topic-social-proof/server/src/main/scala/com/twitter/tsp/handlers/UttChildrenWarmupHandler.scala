package com.tw ter.tsp.handlers

 mport com.tw ter. nject.ut ls.Handler
 mport com.tw ter.top cl st ng.FollowableTop cProduct d
 mport com.tw ter.top cl st ng.Product d
 mport com.tw ter.top cl st ng.Top cL st ngV e rContext
 mport com.tw ter.top cl st ng.utt.UttLocal zat on
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton

/** *
 *   conf gure War r to  lp warm up t  cac  h  rate under `Cac dUttCl ent/get_utt_taxono /cac _h _rate`
 *  n uttLocal zat on.getRecom ndableTop cs,   fetch all top cs ex st  n UTT, and yet t  process
 *  s  n fact fetch ng t  complete UTT tree struct (by call ng getUttCh ldren recurs vely), wh ch could take 1 sec
 * Once   have t  top cs,   stored t m  n  n- mory cac , and t  cac  h  rate  s > 99%
 *
 */
@S ngleton
class UttCh ldrenWarmupHandler @ nject() (uttLocal zat on: UttLocal zat on)
    extends Handler
    w h Logg ng {

  /** Executes t  funct on of t  handler. *   */
  overr de def handle(): Un  = {
    uttLocal zat on
      .getRecom ndableTop cs(
        product d = Product d.Followable,
        v e rContext = Top cL st ngV e rContext(languageCode = So ("en")),
        enable nternat onalTop cs = true,
        followableTop cProduct d = FollowableTop cProduct d.AllFollowable
      )
      .onSuccess { result =>
        logger. nfo(s"successfully war d up UttCh ldren. Top c d length = ${result.s ze}")
      }
      .onFa lure { throwable =>
        logger. nfo(s"fa led to warm up UttCh ldren. Throwable = ${throwable}")
      }
  }
}
