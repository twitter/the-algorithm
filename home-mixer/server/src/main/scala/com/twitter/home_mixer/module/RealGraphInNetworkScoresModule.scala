package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.na .Na d
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.RealGraph nNetworkScores
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.RealGraphManhattanEndpo nt
 mport com.tw ter.ho _m xer.store.RealGraph nNetworkScoresStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo nt
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.ut l.CommonTypes.V e r d
 mport com.tw ter.wtf.cand date.thr ftscala.Cand date

 mport javax. nject.S ngleton

object RealGraph nNetworkScoresModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(RealGraph nNetworkScores)
  def prov desRealGraph nNetworkScoresFeaturesStore(
    @Na d(RealGraphManhattanEndpo nt) realGraph nNetworkScoresManhattanKVEndpo nt: ManhattanKVEndpo nt
  ): ReadableStore[V e r d, Seq[Cand date]] = {
    new RealGraph nNetworkScoresStore(realGraph nNetworkScoresManhattanKVEndpo nt)
  }
}
