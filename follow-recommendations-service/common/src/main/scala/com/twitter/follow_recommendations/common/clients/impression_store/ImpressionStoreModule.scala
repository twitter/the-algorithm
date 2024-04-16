package com.tw ter.follow_recom ndat ons.common.cl ents. mpress on_store

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.thr ftscala.D splayLocat on
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.strato.catalog.Scan.Sl ce
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

object  mpress onStoreModule extends Tw terModule {

  val columnPath: Str ng = "onboard ng/userrecs/wtf mpress onCountsStore"

  type PKey = (Long, D splayLocat on)
  type LKey = Long
  type Value = (Long,  nt)

  @Prov des
  @S ngleton
  def prov des mpress onStore(stratoCl ent: Cl ent): Wtf mpress onStore = {
    new Wtf mpress onStore(
      stratoCl ent.scanner[
        (PKey, Sl ce[LKey]),
        Un ,
        (PKey, LKey),
        Value
      ](columnPath)
    )
  }
}
