package com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.common.BaseCl entModule
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport javax. nject.S ngleton

object Soc alGraphModule
    extends BaseCl entModule[Soc alGraphServ ce. thodPerEndpo nt]
    w h MtlsCl ent {
  overr de val label = "soc al-graph-serv ce"
  overr de val dest = "/s/soc algraph/soc algraph"

  overr de def conf gureThr ftMuxCl ent(cl ent: Thr ftMux.Cl ent): Thr ftMux.Cl ent =
    cl ent.w hSess onQual f er.noFa lFast

  @Prov des
  @S ngleton
  def prov desSt chCl ent(future face: Soc alGraphServ ce. thodPerEndpo nt): Soc alGraph = {
    Soc alGraph(future face)
  }
}
