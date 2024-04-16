package com.tw ter.cr_m xer.module.s m lar y_eng ne

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.ModelConf g
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.Tr pT etW hScore
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Consu rEmbedd ngBasedTr pS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Gat ngConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.StandardS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Tr pEng neQuery
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT et
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pDoma n
 mport javax. nject.Na d

object Consu rEmbedd ngBasedTr pS m lar yEng neModule extends Tw terModule {
  @Prov des
  @Na d(ModuleNa s.Consu rEmbedd ngBasedTr pS m lar yEng ne)
  def prov desConsu rEmbedd ngBasedTr pS m lar yEng neModule(
    @Na d(ModuleNa s.RmsUserLogFav nterested nEmbedd ngStore)
    userLogFav nterested nEmbedd ngStore: ReadableStore[User d, S mClustersEmbedd ng],
    @Na d(ModuleNa s.RmsUserFollow nterested nEmbedd ngStore)
    userFollow nterested nEmbedd ngStore: ReadableStore[User d, S mClustersEmbedd ng],
    @Na d(ModuleNa s.Tr pCand dateStore)
    tr pCand dateStore: ReadableStore[Tr pDoma n, Seq[Tr pT et]],
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): StandardS m lar yEng ne[Tr pEng neQuery, Tr pT etW hScore] = {
    val underly ngStore = ObservedReadableStore(
      Consu rEmbedd ngBasedTr pS m lar yEng ne(
        embedd ngStoreLookUpMap = Map(
          ModelConf g.Consu rLogFavBased nterested nEmbedd ng -> userLogFav nterested nEmbedd ngStore,
          ModelConf g.Consu rFollowBased nterested nEmbedd ng -> userFollow nterested nEmbedd ngStore,
        ),
        tr pCand dateS ce = tr pCand dateStore,
        statsRece ver
      ))(statsRece ver.scope("Tr pS m lar yEng ne"))

    new StandardS m lar yEng ne[Tr pEng neQuery, Tr pT etW hScore](
       mple nt ngStore = underly ngStore,
       dent f er = S m lar yEng neType.ExploreTr pOffl neS mClustersT ets,
      globalStats = statsRece ver,
      eng neConf g = S m lar yEng neConf g(
        t  out = t  outConf g.s m lar yEng neT  out,
        gat ngConf g = Gat ngConf g(
          dec derConf g = None,
          enableFeatureSw ch = None
        )
      )
    )
  }
}
