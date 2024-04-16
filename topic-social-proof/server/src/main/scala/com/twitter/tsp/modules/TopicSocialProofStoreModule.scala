package com.tw ter.tsp.modules

 mport com.google. nject.Module
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.Score
 mport com.tw ter.s mclusters_v2.thr ftscala.Score d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.tsp.stores.Semant cCoreAnnotat onStore
 mport com.tw ter.tsp.stores.Top cSoc alProofStore
 mport com.tw ter.tsp.stores.Top cSoc alProofStore.Top cSoc alProof
 mport com.tw ter.tsp.ut ls.LZ4 nject on
 mport com.tw ter.tsp.ut ls.SeqObject nject on

object Top cSoc alProofStoreModule extends Tw terModule {
  overr de def modules: Seq[Module] = Seq(Un f edCac Cl ent)

  @Prov des
  @S ngleton
  def prov desTop cSoc alProofStore(
    representat onScorerStore: ReadableStore[Score d, Score],
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
    tspUn f edCac Cl ent:  mCl ent,
  ): ReadableStore[Top cSoc alProofStore.Query, Seq[Top cSoc alProof]] = {
    val semant cCoreAnnotat onStore: ReadableStore[T et d, Seq[
      Semant cCoreAnnotat onStore.Top cAnnotat on
    ]] = ObservedReadableStore(
      Semant cCoreAnnotat onStore(Semant cCoreAnnotat onStore.getStratoStore(stratoCl ent))
    )(statsRece ver.scope("Semant cCoreAnnotat onStore"))

    val underly ngStore = Top cSoc alProofStore(
      representat onScorerStore,
      semant cCoreAnnotat onStore
    )(statsRece ver.scope("Top cSoc alProofStore"))

    val  mcac dStore = Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = underly ngStore,
      cac Cl ent = tspUn f edCac Cl ent,
      ttl = 15.m nutes,
      asyncUpdate = true
    )(
      value nject on = LZ4 nject on.compose(SeqObject nject on[Top cSoc alProof]()),
      statsRece ver = statsRece ver.scope(" mCac dTop cSoc alProofStore"),
      keyToStr ng = { k: Top cSoc alProofStore.Query => s"tsps/${k.cac ableQuery}" }
    )

    val  n moryCac dStore =
      ObservedCac dReadableStore.from[Top cSoc alProofStore.Query, Seq[Top cSoc alProof]](
         mcac dStore,
        ttl = 10.m nutes,
        maxKeys = 16777215, // ~ avg 160B, < 3000MB
        cac Na  = "top c_soc al_proof_cac ",
        w ndowS ze = 10000L
      )(statsRece ver.scope(" n moryCac dTop cSoc alProofStore"))

     n moryCac dStore
  }
}
