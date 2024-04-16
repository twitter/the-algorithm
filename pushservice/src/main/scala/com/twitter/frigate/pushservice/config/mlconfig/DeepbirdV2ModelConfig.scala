package com.tw ter.fr gate.pushserv ce.conf g.mlconf g

 mport com.tw ter.cortex.deepb rd.thr ftjava.Deepb rdPred ct onServ ce
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ml.pred ct on.Deepb rdPred ct onEng neServ ceStore
 mport com.tw ter.nrel. avyranker.PushDBv2Pred ct onServ ceStore

object Deepb rdV2ModelConf g {
  def bu ldPred ct onServ ceScoreStore(
    pred ct onServ ceCl ent: Deepb rdPred ct onServ ce.Serv ceToCl ent,
    serv ceNa : Str ng
  )(
     mpl c  statsRece ver: StatsRece ver
  ): PushDBv2Pred ct onServ ceStore = {

    val stats = statsRece ver.scope(serv ceNa )
    val serv ceStats = statsRece ver.scope("dbv2Pred ct onServ ceStore")

    new PushDBv2Pred ct onServ ceStore(
      Deepb rdPred ct onEng neServ ceStore(pred ct onServ ceCl ent, batchS ze = So (32))(stats)
    )(serv ceStats)
  }
}
