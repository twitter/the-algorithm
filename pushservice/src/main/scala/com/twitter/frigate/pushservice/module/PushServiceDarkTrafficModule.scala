package com.tw ter.fr gate.pushserv ce.module

 mport com.google. nject.S ngleton
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushServ ce
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.ReqRepDarkTraff cF lterModule

/**
 * T  darkTraff c f lter sample all requests by default
  and set t  d ffy dest to n l for non prod env ron nts
 */
@S ngleton
object PushServ ceDarkTraff cModule
    extends ReqRepDarkTraff cF lterModule[PushServ ce.ReqRepServ cePerEndpo nt]
    w h MtlsCl ent {

  overr de def label: Str ng = "fr gate-pushserv ce-d ffy-proxy"

  /**
   * Funct on to determ ne  f t  request should be "sampled", e.g.
   * sent to t  dark serv ce.
   *
   * @param  njector t  [[com.tw ter. nject. njector]] for use  n determ n ng  f a g ven request
   *                 should be forwarded or not.
   */
  overr de protected def enableSampl ng( njector:  njector): Any => Boolean = {
    val dec der =  njector. nstance[Dec der]
    _ => dec der. sAva lable("fr gate_pushserv ce_dark_traff c_percent", So (RandomRec p ent))
  }
}
