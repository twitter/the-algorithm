package com.tw ter.tsp.stores

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.Score nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.Scor ngAlgor hm
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  S mClustersEmbedd ngPa rScore d => Thr ftS mClustersEmbedd ngPa rScore d
}
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.s mclusters_v2.thr ftscala.{Score => Thr ftScore}
 mport com.tw ter.s mclusters_v2.thr ftscala.{Score d => Thr ftScore d}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.top c_recos.common._
 mport com.tw ter.top c_recos.common.Conf gs.DefaultModelVers on
 mport com.tw ter.tsp.stores.Top cT etsCos neS m lar yAggregateStore.ScoreKey
 mport com.tw ter.ut l.Future

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
    representat onScorerStore: ReadableStore[Thr ftScore d, Thr ftScore]
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
      val scoreFut = representat onScorerStore
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

case class Top cT etsCos neS m lar yAggregateStore(
  representat onScorerStore: ReadableStore[Thr ftScore d, Thr ftScore]
)(
  statsRece ver: StatsRece ver)
    extends ReadableStore[(Top c d, T et d, Seq[ScoreKey]), Map[ScoreKey, Double]] {
   mport Top cT etsCos neS m lar yAggregateStore._

  overr de def get(k: (Top c d, T et d, Seq[ScoreKey])): Future[Opt on[Map[ScoreKey, Double]]] = {
    statsRece ver.counter("top cT etsCos neS m lar ltyAggregateStore"). ncr()
    getRawScoresMap(k._1, k._2, k._3, representat onScorerStore).map(So (_))
  }
}
