package com.tw ter.s mclusters_v2.score

 mport com.tw ter.s mclusters_v2.thr ftscala.{Score => Thr ftScore, Score d => Thr ftScore d}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

/**
 * A Score Store  s a readableStore w h Score d as Key and Score as t  Value.
 *   also needs to  nclude t  algor hm type.
 * A algor hm type should only be used by one Score Store  n t  appl cat on.
 */
tra  ScoreStore[K <: Score d] extends ReadableStore[K, Score] {

  def fromThr ftScore d: Thr ftScore d => K

  // Convert to a Thr ft vers on.
  def toThr ftStore: ReadableStore[Thr ftScore d, Thr ftScore] = {
    t 
      .composeKeyMapp ng[Thr ftScore d](fromThr ftScore d)
      .mapValues(_.toThr ft)
  }
}

/**
 * A gener c Pa rw se Score store.
 * Requ res prov de both left and r ght s de feature hydrat on.
 */
tra  Pa rScoreStore[K <: Pa rScore d, K1, K2, V1, V2] extends ScoreStore[K] {

  def compos eKey1: K => K1
  def compos eKey2: K => K2

  // Left s de feature hydrat on
  def underly ngStore1: ReadableStore[K1, V1]

  // R ght s de feature hydrat on
  def underly ngStore2: ReadableStore[K2, V2]

  def score: (V1, V2) => Future[Opt on[Double]]

  overr de def get(k: K): Future[Opt on[Score]] = {
    for {
      vs <-
        Future.jo n(underly ngStore1.get(compos eKey1(k)), underly ngStore2.get(compos eKey2(k)))
      v <- vs match {
        case (So (v1), So (v2)) =>
          score(v1, v2)
        case _ =>
          Future.None
      }
    } y eld {
      v.map(bu ldScore)
    }
  }

  overr de def mult Get[KK <: K](ks: Set[KK]): Map[KK, Future[Opt on[Score]]] = {

    val v1Map = underly ngStore1.mult Get(ks.map { k => compos eKey1(k) })
    val v2Map = underly ngStore2.mult Get(ks.map { k => compos eKey2(k) })

    ks.map { k =>
      k -> Future.jo n(v1Map(compos eKey1(k)), v2Map(compos eKey2(k))).flatMap {
        case (So (v1), So (v2)) =>
          score(v1, v2).map(_.map(bu ldScore))
        case _ =>
          Future.value(None)
      }
    }.toMap
  }

  pr vate def bu ldScore(v: Double): Score = Score(v)
}
