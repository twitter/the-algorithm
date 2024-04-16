package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.t etconvosvc.thr ftscala.Conversat onServ ce
 mport com.tw ter.ut l.Durat on
 mport org.apac .thr ft.protocol.TCompactProtocol

object Conversat onServ ceModule
    extends Thr ft thodBu lderCl entModule[
      Conversat onServ ce.Serv cePerEndpo nt,
      Conversat onServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label: Str ng = "t etconvosvc"
  overr de val dest: Str ng = "/s/t etconvosvc/t etconvosvc"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder =
     thodBu lder
      .w hT  outTotal(200.m ll seconds)
      .w hT  outPerRequest(100.m ll seconds)
      . dempotent(1.percent)

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent =
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hProtocolFactory(new TCompactProtocol.Factory())

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
