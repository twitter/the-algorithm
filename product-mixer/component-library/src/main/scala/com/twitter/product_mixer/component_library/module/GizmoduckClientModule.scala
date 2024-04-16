package com.tw ter.product_m xer.component_l brary.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.g zmoduck.thr ftscala.UserServ ce
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.st ch.g zmoduck.G zmoduck
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

/**
 *  mple ntat on w h reasonable defaults for an  dempotent G zmoduck Thr ft and St ch cl ent.
 *
 * Note that t  per request and total t  outs conf gured  n t  module are  ant to represent a
 * reasonable start ng po nt only. T se  re selected based on common pract ce, and should not be
 * assu d to be opt mal for any part cular use case.  f   are  nterested  n furt r tun ng t 
 * sett ngs  n t  module,    s recom nded to create local copy for y  serv ce.
 */
object G zmoduckCl entModule
    extends Thr ft thodBu lderCl entModule[
      UserServ ce.Serv cePerEndpo nt,
      UserServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  overr de val label: Str ng = "g zmoduck"
  overr de val dest: Str ng = "/s/g zmoduck/g zmoduck"

  @S ngleton
  @Prov des
  def prov deG zmoduckSt chCl ent(userServ ce: UserServ ce. thodPerEndpo nt): G zmoduck =
    new G zmoduck(userServ ce)

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder =
     thodBu lder
      .w hT  outPerRequest(200.m ll seconds)
      .w hT  outTotal(400.m ll seconds)
      . dempotent(1.percent)

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
