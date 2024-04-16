package com.tw ter.cr_m xer.module.s m lar y_eng ne

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.Top cT etW hScore
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derConstants
 mport com.tw ter.cr_m xer.s m lar y_eng ne.CertoTop cT etS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.CertoTop cT etS m lar yEng ne.Query
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Eng neQuery
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Dec derConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Gat ngConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.StandardS m lar yEng ne
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.top c_recos.thr ftscala.T etW hScores
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object CertoTop cT etS m lar yEng neModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.CertoTop cT etS m lar yEng ne)
  def prov desCertoTop cT etS m lar yEng ne(
    @Na d(ModuleNa s.CertoStratoStoreNa ) certoStratoStore: ReadableStore[
      Top c d,
      Seq[T etW hScores]
    ],
    t  outConf g: T  outConf g,
    dec der: CrM xerDec der,
    statsRece ver: StatsRece ver
  ): StandardS m lar yEng ne[
    Eng neQuery[Query],
    Top cT etW hScore
  ] = {
    new StandardS m lar yEng ne[Eng neQuery[Query], Top cT etW hScore](
       mple nt ngStore = CertoTop cT etS m lar yEng ne(certoStratoStore, statsRece ver),
       dent f er = S m lar yEng neType.CertoTop cT et,
      globalStats = statsRece ver,
      eng neConf g = S m lar yEng neConf g(
        t  out = t  outConf g.top cT etEndpo ntT  out,
        gat ngConf g = Gat ngConf g(
          dec derConf g =
            So (Dec derConf g(dec der, Dec derConstants.enableTop cT etTraff cDec derKey)),
          enableFeatureSw ch = None
        )
      )
    )
  }

}
