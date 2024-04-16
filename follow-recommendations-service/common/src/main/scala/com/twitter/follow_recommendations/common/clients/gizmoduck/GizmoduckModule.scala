package com.tw ter.follow_recom ndat ons.common.cl ents.g zmoduck

 mport com.google. nject.Prov des
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.common.BaseCl entModule
 mport com.tw ter.g zmoduck.thr ftscala.QueryF elds
 mport com.tw ter.g zmoduck.thr ftscala.UserServ ce
 mport com.tw ter.st ch.g zmoduck.G zmoduck
 mport javax. nject.S ngleton

object G zmoduckModule extends BaseCl entModule[UserServ ce. thodPerEndpo nt] w h MtlsCl ent {
  overr de val label = "g zmoduck"
  overr de val dest = "/s/g zmoduck/g zmoduck"

  @Prov des
  @S ngleton
  def prov deExtraG zmoduckQueryF elds: Set[QueryF elds] = Set.empty

  @Prov des
  @S ngleton
  def prov desSt chCl ent(future face: UserServ ce. thodPerEndpo nt): G zmoduck = {
    G zmoduck(future face)
  }
}
