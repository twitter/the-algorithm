package com.tw ter.product_m xer.component_l brary.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport javax. nject.S ngleton

object Soc alGraphServ ceModule
    extends Thr ft thodBu lderCl entModule[
      Soc alGraphServ ce.Serv cePerEndpo nt,
      Soc alGraphServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  val label: Str ng = "soc algraphserv ce"
  val dest: Str ng = "/s/soc algraph/soc algraph"

  @S ngleton
  @Prov des
  def prov deG zmoduckSt chCl ent(
    soc alGraphServ ce: Soc alGraphServ ce. thodPerEndpo nt
  ): Soc alGraph =
    new Soc alGraph(soc alGraphServ ce)

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder.w hT  outPerRequest(400.m ll s)
  }
}
