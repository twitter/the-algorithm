package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.scor ng

 mport com.tw ter.cortex.deepb rd.thr ftjava.Deepb rdPred ct onServ ce
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

/**
 * T  scorer ass gns random values bet en 0 and 1 to each cand date as scores.
 */

@S ngleton
class RandomScorer @ nject() (
  @Na d(Gu ceNa dConstants.WTF_PROD_DEEPB RDV2_CL ENT)
  overr de val deepb rdCl ent: Deepb rdPred ct onServ ce.Serv ceToCl ent,
  overr de val baseStats: StatsRece ver)
    extends Deepb rdScorer {
  overr de val  d = Ranker d.RandomRanker
  pr vate val rnd = new scala.ut l.Random(System.currentT  M ll s())

  overr de def pred ct(dataRecords: Seq[DataRecord]): Future[Seq[Opt on[Double]]] = {
     f (dataRecords. sEmpty) {
      Future.N l
    } else {
      // All cand dates are ass gned a random value bet en 0 and 1 as score.
      Future.value(dataRecords.map(_ => Opt on(rnd.nextDouble())))
    }
  }

  overr de val modelNa  = "PostNuxRandomRanker"

  // T   s not needed s nce   are overr d ng t  `pred ct` funct on, but   have to overr de
  // `pred ct onFeature` anyway.
  overr de val pred ct onFeature: Feature.Cont nuous =
    new Feature.Cont nuous("pred ct on.pfollow_pengage nt")
}
