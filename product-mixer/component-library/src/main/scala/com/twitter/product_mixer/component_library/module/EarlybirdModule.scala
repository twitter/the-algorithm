package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.annotat ons.Flags
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.search.earlyb rd.{thr ftscala => t}
 mport com.tw ter.ut l.Durat on
 mport org.apac .thr ft.protocol.TCompactProtocol

object Earlyb rdModule
    extends Thr ft thodBu lderCl entModule[
      t.Earlyb rdServ ce.Serv cePerEndpo nt,
      t.Earlyb rdServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  f nal val Earlyb rdT  outPerRequest = "earlyb rd.t  out_per_request"
  f nal val Earlyb rdT  outTotal = "earlyb rd.t  out_total"

  flag[Durat on](
    na  = Earlyb rdT  outPerRequest,
    default = 200.m ll seconds,
     lp = "T  out per request for Earlyb rd")

  flag[Durat on](
    na  = Earlyb rdT  outTotal,
    default = 400.m ll seconds,
     lp = "T  out total for Earlyb rd")

  overr de val dest = "/s/earlyb rd-root-superroot/root-superroot"
  overr de val label = "earlyb rd"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
    val t  OutPerRequest: Durat on =  njector
      . nstance[Durat on](Flags.na d(Earlyb rdT  outPerRequest))
    val t  OutTotal: Durat on =  njector. nstance[Durat on](Flags.na d(Earlyb rdT  outTotal))
     thodBu lder
    // See TL-14313 for load test ng deta ls that led to 200ms be ng selected as request t  out
      .w hT  outPerRequest(t  OutPerRequest)
      .w hT  outTotal(t  OutTotal)
      . dempotent(5.percent)
  }

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent =
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hProtocolFactory(new TCompactProtocol.Factory())

  overr de protected def sess onAcqu s  onT  out: Durat on = 1.seconds
}
