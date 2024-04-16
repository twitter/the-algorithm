package com.tw ter.representat onscorer.scorestore

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.representat onscorer.scorestore.Top cT etsCos neS m lar yAggregateStore.ScoreKey
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.score.AggregatedScoreStore
 mport com.tw ter.s mclusters_v2.thr ftscala.Score nternal d.Gener cPa rScore d
 mport com.tw ter.s mclusters_v2.thr ftscala.Scor ngAlgor hm.CortexTop cT etLabel
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Embedd ngType,
   nternal d,
  ModelVers on,
  Score nternal d,
  Scor ngAlgor hm,
  S mClustersEmbedd ng d,
  Top c d,
  Score => Thr ftScore,
  Score d => Thr ftScore d,
  S mClustersEmbedd ngPa rScore d => Thr ftS mClustersEmbedd ngPa rScore d
}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.top c_recos.common.Conf gs.{DefaultModelVers on, M nCos neS m lar yScore}
 mport com.tw ter.top c_recos.common._
 mport com.tw ter.ut l.Future

/**
 * Calculates t  cos ne s m lar y scores of arb rary comb nat ons of Top cEmbedd ngs and
 * T etEmbedd ngs.
 * T  class has 2 uses:
 * 1. For  nternal uses. TSP w ll call t  store to fetch t  raw scores for (top c, t et) w h
 * all ava lable embedd ng types.   calculate all t  scores  re, so t  caller can do f lter ng
 * & score cach ng on t  r s de. T  w ll make   poss ble to DDG d fferent embedd ng scores.
 *
 * 2. For external calls from Cortex.   return true (or 1.0) for any g ven (top c, t et)  f t  r
 * cos ne s m lar y passes t  threshold for any of t  embedd ng types.
 * T  expected  nput type  s
 * Score d(
 *  Pa rEmbedd ngCos neS m lar y,
 *  Gener cPa rScore d(Top c d, T et d)
 * )
 */
case class Top cT etsCos neS m lar yAggregateStore(
  scoreKeys: Seq[ScoreKey],
  statsRece ver: StatsRece ver)
    extends AggregatedScoreStore {

  def toCortexScore(scoresMap: Map[ScoreKey, Double]): Double = {
    val passThreshold = scoresMap.ex sts {
      case (_, score) => score >= M nCos neS m lar yScore
    }
     f (passThreshold) 1.0 else 0.0
  }

  /**
   * To be called by Cortex through Un f ed Score AP  ONLY. Calculates all poss ble (top c, t et),
   * return 1.0  f any of t  embedd ng scores passes t  m n mum threshold.
   *
   * Expect a Gener cPa rScore d(Pa rEmbedd ngCos neS m lar y, (Top c d, T et d)) as  nput
   */
  overr de def get(k: Thr ftScore d): Future[Opt on[Thr ftScore]] = {
    StatsUt l.trackOpt onStats(statsRece ver) {
      (k.algor hm, k. nternal d) match {
        case (CortexTop cT etLabel, Gener cPa rScore d(gener cPa rScore d)) =>
          (gener cPa rScore d. d1, gener cPa rScore d. d2) match {
            case ( nternal d.Top c d(top c d),  nternal d.T et d(t et d)) =>
              Top cT etsCos neS m lar yAggregateStore
                .getRawScoresMap(top c d, t et d, scoreKeys, scoreFacadeStore)
                .map { scoresMap => So (Thr ftScore(toCortexScore(scoresMap))) }
            case ( nternal d.T et d(t et d),  nternal d.Top c d(top c d)) =>
              Top cT etsCos neS m lar yAggregateStore
                .getRawScoresMap(top c d, t et d, scoreKeys, scoreFacadeStore)
                .map { scoresMap => So (Thr ftScore(toCortexScore(scoresMap))) }
            case _ =>
              Future.None
            // Do not accept ot r  nternal d comb nat ons
          }
        case _ =>
          // Do not accept ot r  d types for now
          Future.None
      }
    }
  }
}

object Top cT etsCos neS m lar yAggregateStore {

  val Top cEmbedd ngTypes: Seq[Embedd ngType] =
    Seq(
      Embedd ngType.FavTfgTop c,
      Embedd ngType.LogFavBasedKgoApeTop c
    )

  // Add t  new embedd ng types  f want to test t  new T et embedd ng performance.
  val T etEmbedd ngTypes: Seq[Embedd ngType] = Seq(Embedd ngType.LogFavBasedT et)

  val ModelVers ons: Seq[ModelVers on] =
    Seq(DefaultModelVers on)

  val DefaultScoreKeys: Seq[ScoreKey] = {
    for {
      modelVers on <- ModelVers ons
      top cEmbedd ngType <- Top cEmbedd ngTypes
      t etEmbedd ngType <- T etEmbedd ngTypes
    } y eld {
      ScoreKey(
        top cEmbedd ngType = top cEmbedd ngType,
        t etEmbedd ngType = t etEmbedd ngType,
        modelVers on = modelVers on
      )
    }
  }
  case class ScoreKey(
    top cEmbedd ngType: Embedd ngType,
    t etEmbedd ngType: Embedd ngType,
    modelVers on: ModelVers on)

  def getRawScoresMap(
    top c d: Top c d,
    t et d: T et d,
    scoreKeys: Seq[ScoreKey],
    un formScor ngStore: ReadableStore[Thr ftScore d, Thr ftScore]
  ): Future[Map[ScoreKey, Double]] = {
    val scoresMapFut = scoreKeys.map { key =>
      val score nternal d = Score nternal d.S mClustersEmbedd ngPa rScore d(
        Thr ftS mClustersEmbedd ngPa rScore d(
          bu ldTop cEmbedd ng(top c d, key.top cEmbedd ngType, key.modelVers on),
          S mClustersEmbedd ng d(
            key.t etEmbedd ngType,
            key.modelVers on,
             nternal d.T et d(t et d))
        ))
      val scoreFut = un formScor ngStore
        .get(
          Thr ftScore d(
            algor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y, // Hard code as cos ne s m
             nternal d = score nternal d
          ))
      key -> scoreFut
    }.toMap

    Future
      .collect(scoresMapFut).map(_.collect {
        case (key, So (Thr ftScore(score))) =>
          (key, score)
      })
  }
}
