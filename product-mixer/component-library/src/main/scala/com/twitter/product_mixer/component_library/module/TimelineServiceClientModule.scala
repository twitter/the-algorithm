package com.tw ter.product_m xer.component_l brary.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce
 mport com.tw ter.t  l neserv ce.{thr ftscala => t}
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

object T  l neServ ceCl entModule
    extends Thr ft thodBu lderCl entModule[
      t.T  l neServ ce.Serv cePerEndpo nt,
      t.T  l neServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "t  l neserv ce"
  overr de val dest = "/s/t  l neserv ce/t  l neserv ce"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(1200.m ll s)
      .w hT  outTotal(2400.m ll s)
      . dempotent(1.percent)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds

  @S ngleton
  @Prov des
  def prov desT  l neServ ceSt chCl ent(
    cl ent: t.T  l neServ ce. thodPerEndpo nt
  ): T  l neServ ce = {
    new T  l neServ ce(cl ent)
  }
}
