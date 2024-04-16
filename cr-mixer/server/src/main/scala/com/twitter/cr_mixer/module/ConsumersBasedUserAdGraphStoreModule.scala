package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.recos.user_ad_graph.thr ftscala.Consu rsBasedRelatedAdRequest
 mport com.tw ter.recos.user_ad_graph.thr ftscala.RelatedAdResponse
 mport com.tw ter.recos.user_ad_graph.thr ftscala.UserAdGraph
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object Consu rsBasedUserAdGraphStoreModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Consu rBasedUserAdGraphStore)
  def prov desConsu rBasedUserAdGraphStore(
    userAdGraphServ ce: UserAdGraph. thodPerEndpo nt
  ): ReadableStore[Consu rsBasedRelatedAdRequest, RelatedAdResponse] = {
    new ReadableStore[Consu rsBasedRelatedAdRequest, RelatedAdResponse] {
      overr de def get(
        k: Consu rsBasedRelatedAdRequest
      ): Future[Opt on[RelatedAdResponse]] = {
        userAdGraphServ ce.consu rsBasedRelatedAds(k).map(So (_))
      }
    }
  }
}
