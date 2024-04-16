package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.thr ftscala.CrM xerT etResponse
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. rm .store.common.ReadableWr ableStore
 mport com.tw ter. rm .store.common.ObservedReadableWr able mcac Store
 mport com.tw ter.s mclusters_v2.common.User d
 mport javax. nject.Na d

object T etRecom ndat onResultsStoreModule extends Tw terModule {
  @Prov des
  @S ngleton
  def prov desT etRecom ndat onResultsStore(
    @Na d(ModuleNa s.T etRecom ndat onResultsCac ) t etRecom ndat onResultsCac Cl ent:  mcac dCl ent,
    statsRece ver: StatsRece ver
  ): ReadableWr ableStore[User d, CrM xerT etResponse] = {
    ObservedReadableWr able mcac Store.fromCac Cl ent(
      cac Cl ent = t etRecom ndat onResultsCac Cl ent,
      ttl = 24.h s)(
      value nject on = B naryScalaCodec(CrM xerT etResponse),
      statsRece ver = statsRece ver.scope("T etRecom ndat onResults mcac Store"),
      keyToStr ng = { k: User d => k.toStr ng }
    )
  }
}
