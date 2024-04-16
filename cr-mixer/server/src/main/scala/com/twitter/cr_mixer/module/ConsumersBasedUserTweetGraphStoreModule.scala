package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.recos.user_t et_graph.thr ftscala.Consu rsBasedRelatedT etRequest
 mport com.tw ter.recos.user_t et_graph.thr ftscala.RelatedT etResponse
 mport com.tw ter.recos.user_t et_graph.thr ftscala.UserT etGraph
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object Consu rsBasedUserT etGraphStoreModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Consu rBasedUserT etGraphStore)
  def prov desConsu rBasedUserT etGraphStore(
    userT etGraphServ ce: UserT etGraph. thodPerEndpo nt
  ): ReadableStore[Consu rsBasedRelatedT etRequest, RelatedT etResponse] = {
    new ReadableStore[Consu rsBasedRelatedT etRequest, RelatedT etResponse] {
      overr de def get(
        k: Consu rsBasedRelatedT etRequest
      ): Future[Opt on[RelatedT etResponse]] = {
        userT etGraphServ ce.consu rsBasedRelatedT ets(k).map(So (_))
      }
    }
  }
}
