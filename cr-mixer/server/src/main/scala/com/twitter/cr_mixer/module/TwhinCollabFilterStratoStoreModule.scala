package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Twh nCollabF lterS m lar yEng ne.Twh nCollabF lterV ew
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.storehaus.ReadableStore
 mport javax. nject.Na d

object Twh nCollabF lterStratoStoreModule extends Tw terModule {

  val stratoColumnPath: Str ng = "cuad/twh n/getCollabF lterT etCand datesProd.User"

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Twh nCollabF lterStratoStoreForFollow)
  def prov desTwh nCollabF lterStratoStoreForFollow(
    stratoCl ent: StratoCl ent
  ): ReadableStore[Long, Seq[T et d]] = {
    StratoFetchableStore.w hV ew[Long, Twh nCollabF lterV ew, Seq[T et d]](
      stratoCl ent,
      column = stratoColumnPath,
      v ew = Twh nCollabF lterV ew("follow_2022_03_10_c_500K")
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Twh nCollabF lterStratoStoreForEngage nt)
  def prov desTwh nCollabF lterStratoStoreForEngage nt(
    stratoCl ent: StratoCl ent
  ): ReadableStore[Long, Seq[T et d]] = {
    StratoFetchableStore.w hV ew[Long, Twh nCollabF lterV ew, Seq[T et d]](
      stratoCl ent,
      column = stratoColumnPath,
      v ew = Twh nCollabF lterV ew("engage nt_2022_04_10_c_500K"))
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Twh nMult ClusterStratoStoreForFollow)
  def prov desTwh nMult ClusterStratoStoreForFollow(
    stratoCl ent: StratoCl ent
  ): ReadableStore[Long, Seq[T et d]] = {
    StratoFetchableStore.w hV ew[Long, Twh nCollabF lterV ew, Seq[T et d]](
      stratoCl ent,
      column = stratoColumnPath,
      v ew = Twh nCollabF lterV ew("mult clusterFollow20220921")
    )
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Twh nMult ClusterStratoStoreForEngage nt)
  def prov desTwh nMult ClusterStratoStoreForEngage nt(
    stratoCl ent: StratoCl ent
  ): ReadableStore[Long, Seq[T et d]] = {
    StratoFetchableStore.w hV ew[Long, Twh nCollabF lterV ew, Seq[T et d]](
      stratoCl ent,
      column = stratoColumnPath,
      v ew = Twh nCollabF lterV ew("mult clusterEng20220921"))
  }
}
