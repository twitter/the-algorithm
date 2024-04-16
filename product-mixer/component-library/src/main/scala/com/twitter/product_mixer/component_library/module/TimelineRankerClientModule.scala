package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.t  l neranker.{thr ftscala => t}
 mport com.tw ter.ut l.Durat on
 mport org.apac .thr ft.protocol.TCompactProtocol

object T  l neRankerCl entModule
    extends Thr ft thodBu lderCl entModule[
      t.T  l neRanker.Serv cePerEndpo nt,
      t.T  l neRanker. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "t  l ne-ranker"
  overr de val dest = "/s/t  l neranker/t  l neranker:compactthr ft"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(750.m ll s)
      .w hT  outTotal(750.m ll s)
  }

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent = {
    val serv ce dent f er =  njector. nstance[Serv ce dent f er]
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hProtocolFactory(new TCompactProtocol.Factory())
      .w hMutualTls(serv ce dent f er)
      .w hPerEndpo ntStats
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
