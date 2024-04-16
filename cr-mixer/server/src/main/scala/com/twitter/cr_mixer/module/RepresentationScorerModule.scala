package com.tw ter.cr_m xer.module

 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.s mclusters_v2.thr ftscala.Scor ngAlgor hm
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport javax. nject.Na d
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.representat onscorer.thr ftscala.L stScore d

object Representat onScorerModule extends Tw terModule {

  pr vate val rsxColumnPath = "recom ndat ons/representat on_scorer/l stScore"

  pr vate f nal val S mClusterModelVers on = ModelVers on.Model20m145k2020
  pr vate f nal val T etEmbedd ngType = Embedd ngType.LogFavBasedT et

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.RsxStore)
  def prov desRepresentat onScorerStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[(User d, T et d), Double] = {
    ObservedReadableStore(
      StratoFetchableStore
        .w hUn V ew[L stScore d, Double](stratoCl ent, rsxColumnPath).composeKeyMapp ng[(
          User d,
          T et d
        )] { key =>
          representat onScorerStoreKeyMapp ng(key._1, key._2)
        }
    )(statsRece ver.scope("rsx_store"))
  }

  pr vate def representat onScorerStoreKeyMapp ng(t1: T et d, t2: T et d): L stScore d = {
    L stScore d(
      algor hm = Scor ngAlgor hm.Pa rEmbedd ngLogCos neS m lar y,
      modelVers on = S mClusterModelVers on,
      targetEmbedd ngType = T etEmbedd ngType,
      target d =  nternal d.T et d(t1),
      cand dateEmbedd ngType = T etEmbedd ngType,
      cand date ds = Seq( nternal d.T et d(t2))
    )
  }
}
