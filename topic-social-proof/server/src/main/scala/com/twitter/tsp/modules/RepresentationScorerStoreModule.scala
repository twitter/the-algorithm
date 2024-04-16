package com.tw ter.tsp.modules

 mport com.google. nject.Module
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.app.Flag
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.thr ftscala.Score
 mport com.tw ter.s mclusters_v2.thr ftscala.Score d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.tsp.stores.Representat onScorerStore

object Representat onScorerStoreModule extends Tw terModule {
  overr de def modules: Seq[Module] = Seq(Un f edCac Cl ent)

  pr vate val tspRepresentat onScor ngColumnPath: Flag[Str ng] = flag[Str ng](
    na  = "tsp.representat onScor ngColumnPath",
    default = "recom ndat ons/representat on_scorer/score",
     lp = "Strato column path for Representat on Scorer Store"
  )

  @Prov des
  @S ngleton
  def prov desRepresentat onScorerStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
    tspUn f edCac Cl ent:  mCl ent
  ): ReadableStore[Score d, Score] = {
    val underly ngStore =
      Representat onScorerStore(stratoCl ent, tspRepresentat onScor ngColumnPath(), statsRece ver)
    Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = underly ngStore,
      cac Cl ent = tspUn f edCac Cl ent,
      ttl = 2.h s
    )(
      value nject on = B naryScalaCodec(Score),
      statsRece ver = statsRece ver.scope("Representat onScorerStore"),
      keyToStr ng = { k: Score d => s"rsx/$k" }
    )
  }
}
