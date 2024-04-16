package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.StandardS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.LookupS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.Gat ngConf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport javax. nject.S ngleton

/**
 *  n t  example   bu ld a [[StandardS m lar yEng ne]] to wrap a dum  store
 */
object S mpleS m lar yEng neModule extends Tw terModule {
  @Prov des
  @S ngleton
  def prov desS mpleS m lar yEng ne(
    t  outConf g: T  outConf g,
    globalStats: StatsRece ver
  ): StandardS m lar yEng ne[User d, (T et d, Double)] = {
    //  nject y  readableStore  mple ntat on  re
    val dum Store = ReadableStore.fromMap(
      Map(
        1L -> Seq((100L, 1.0), (101L, 1.0)),
        2L -> Seq((200L, 2.0), (201L, 2.0)),
        3L -> Seq((300L, 3.0), (301L, 3.0))
      ))

    new StandardS m lar yEng ne[User d, (T et d, Double)](
       mple nt ngStore = dum Store,
       dent f er = S m lar yEng neType.EnumUnknownS m lar yEng neType(9997),
      globalStats = globalStats,
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

/**
 *  n t  example   bu ld a [[LookupS m lar yEng ne]] to wrap a dum  store w h 2 vers ons
 */
object LookupS m lar yEng neModule extends Tw terModule {
  @Prov des
  @S ngleton
  def prov desLookupS m lar yEng ne(
    t  outConf g: T  outConf g,
    globalStats: StatsRece ver
  ): LookupS m lar yEng ne[User d, (T et d, Double)] = {
    //  nject y  readableStore  mple ntat on  re
    val dum StoreV1 = ReadableStore.fromMap(
      Map(
        1L -> Seq((100L, 1.0), (101L, 1.0)),
        2L -> Seq((200L, 2.0), (201L, 2.0)),
      ))

    val dum StoreV2 = ReadableStore.fromMap(
      Map(
        1L -> Seq((100L, 1.0), (101L, 1.0)),
        2L -> Seq((200L, 2.0), (201L, 2.0)),
      ))

    new LookupS m lar yEng ne[User d, (T et d, Double)](
      vers onedStoreMap = Map(
        "V1" -> dum StoreV1,
        "V2" -> dum StoreV2
      ),
       dent f er = S m lar yEng neType.EnumUnknownS m lar yEng neType(9998),
      globalStats = globalStats,
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
