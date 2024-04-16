package com.tw ter.cr_m xer.module
package s m lar y_eng ne

 mport com.google. nject.Prov des
 mport com.tw ter.ann.common.thr ftscala.AnnQueryServ ce
 mport com.tw ter.cr_m xer.model.ModelConf g
 mport com.tw ter.cr_m xer.module.Embedd ngStoreModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.AnnQueryServ ceCl entModule
 mport com.tw ter.cr_m xer.s m lar y_eng ne.HnswANNS m lar yEng ne
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport javax. nject.Na d
 mport com.tw ter.ml.ap .{thr ftscala => ap }
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Gat ngConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType

object Consu rEmbedd ngBasedTwoTo rS m lar yEng neModule extends Tw terModule {
  @Prov des
  @Na d(ModuleNa s.Consu rEmbedd ngBasedTwoTo rANNS m lar yEng ne)
  def prov desConsu rEmbedd ngBasedTwoTo rANNS m lar yEng ne(
    @Na d(Embedd ngStoreModule.TwoTo rFavConsu rEmbedd ngMhStoreNa )
    twoTo rFavConsu rEmbedd ngMhStore: ReadableStore[ nternal d, ap .Embedd ng],
    @Na d(AnnQueryServ ceCl entModule.TwoTo rFavAnnServ ceCl entNa )
    twoTo rFavAnnServ ce: AnnQueryServ ce. thodPerEndpo nt,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver
  ): HnswANNS m lar yEng ne = {
    new HnswANNS m lar yEng ne(
      embedd ngStoreLookUpMap = Map(
        ModelConf g.TwoTo rFavALL20220808 -> twoTo rFavConsu rEmbedd ngMhStore,
      ),
      annServ ceLookUpMap = Map(
        ModelConf g.TwoTo rFavALL20220808 -> twoTo rFavAnnServ ce,
      ),
      globalStats = statsRece ver,
       dent f er = S m lar yEng neType.Consu rEmbedd ngBasedTwoTo rANN,
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
