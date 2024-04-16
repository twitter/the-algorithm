package com.tw ter.cr_m xer.module

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.representat on_manager.thr ftscala.S mClustersEmbedd ngV ew
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport javax. nject.Na d
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}

object Representat onManagerModule extends Tw terModule {
  pr vate val ColPathPref x = "recom ndat ons/representat on_manager/"
  pr vate val S mclustersT etColPath = ColPathPref x + "s mClustersEmbedd ng.T et"
  pr vate val S mclustersUserColPath = ColPathPref x + "s mClustersEmbedd ng.User"

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.RmsT etLogFavLongestL2Embedd ngStore)
  def prov desRepresentat onManagerT etStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[T et d, S mClustersEmbedd ng] = {
    ObservedReadableStore(
      StratoFetchableStore
        .w hV ew[Long, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
          stratoCl ent,
          S mclustersT etColPath,
          S mClustersEmbedd ngV ew(
            Embedd ngType.LogFavLongestL2Embedd ngT et,
            ModelVers on.Model20m145k2020))
        .mapValues(S mClustersEmbedd ng(_)))(
      statsRece ver.scope("rms_t et_log_fav_longest_l2_store"))
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.RmsUserFavBasedProducerEmbedd ngStore)
  def prov desRepresentat onManagerUserFavBasedProducerEmbedd ngStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[User d, S mClustersEmbedd ng] = {
    ObservedReadableStore(
      StratoFetchableStore
        .w hV ew[Long, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
          stratoCl ent,
          S mclustersUserColPath,
          S mClustersEmbedd ngV ew(
            Embedd ngType.FavBasedProducer,
            ModelVers on.Model20m145k2020
          )
        )
        .mapValues(S mClustersEmbedd ng(_)))(
      statsRece ver.scope("rms_user_fav_based_producer_store"))
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.RmsUserLogFav nterested nEmbedd ngStore)
  def prov desRepresentat onManagerUserLogFavConsu rEmbedd ngStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[User d, S mClustersEmbedd ng] = {
    ObservedReadableStore(
      StratoFetchableStore
        .w hV ew[Long, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
          stratoCl ent,
          S mclustersUserColPath,
          S mClustersEmbedd ngV ew(
            Embedd ngType.LogFavBasedUser nterested n,
            ModelVers on.Model20m145k2020
          )
        )
        .mapValues(S mClustersEmbedd ng(_)))(
      statsRece ver.scope("rms_user_log_fav_ nterested n_store"))
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.RmsUserFollow nterested nEmbedd ngStore)
  def prov desRepresentat onManagerUserFollow nterested nEmbedd ngStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[User d, S mClustersEmbedd ng] = {
    ObservedReadableStore(
      StratoFetchableStore
        .w hV ew[Long, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
          stratoCl ent,
          S mclustersUserColPath,
          S mClustersEmbedd ngV ew(
            Embedd ngType.FollowBasedUser nterested n,
            ModelVers on.Model20m145k2020
          )
        )
        .mapValues(S mClustersEmbedd ng(_)))(
      statsRece ver.scope("rms_user_follow_ nterested n_store"))
  }
}
