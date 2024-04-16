package com.tw ter.s mclusters_v2.score

 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng d, Score d => Thr ftScore d}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

object S mClustersEmbedd ngPa rScoreStore {

  /**
   *  nternal  nstance of a S mClusters Embedd ng based Pa r Score store.
   */
  pr vate case class S mClustersEmbedd ng nternalPa rScoreStore(
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng],
    score: (S mClustersEmbedd ng, S mClustersEmbedd ng) => Future[Opt on[Double]])
      extends Pa rScoreStore[
        S mClustersEmbedd ngPa rScore d,
        S mClustersEmbedd ng d,
        S mClustersEmbedd ng d,
        S mClustersEmbedd ng,
        S mClustersEmbedd ng
      ] {

    overr de val compos eKey1: S mClustersEmbedd ngPa rScore d => S mClustersEmbedd ng d =
      _.embedd ng d1
    overr de val compos eKey2: S mClustersEmbedd ngPa rScore d => S mClustersEmbedd ng d =
      _.embedd ng d2

    overr de def underly ngStore1: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] =
      s mClustersEmbedd ngStore

    overr de def underly ngStore2: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] =
      s mClustersEmbedd ngStore

    overr de def fromThr ftScore d: Thr ftScore d => S mClustersEmbedd ngPa rScore d =
      S mClustersEmbedd ngPa rScore d.fromThr ftScore d
  }

  def bu ldDotProductStore(
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ): Pa rScoreStore[
    S mClustersEmbedd ngPa rScore d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ] = {

    def dotProduct: (S mClustersEmbedd ng, S mClustersEmbedd ng) => Future[Opt on[Double]] = {
      case (embedd ng1, embedd ng2) =>
        Future.value(So (embedd ng1.dotProduct(embedd ng2)))
    }

    S mClustersEmbedd ng nternalPa rScoreStore(
      s mClustersEmbedd ngStore,
      dotProduct
    )
  }

  def bu ldCos neS m lar yStore(
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ): Pa rScoreStore[
    S mClustersEmbedd ngPa rScore d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ] = {

    def cos neS m lar y: (S mClustersEmbedd ng, S mClustersEmbedd ng) => Future[Opt on[Double]] = {
      case (embedd ng1, embedd ng2) =>
        Future.value(So (embedd ng1.cos neS m lar y(embedd ng2)))
    }

    S mClustersEmbedd ng nternalPa rScoreStore(
      s mClustersEmbedd ngStore,
      cos neS m lar y
    )
  }

  def bu ldLogCos neS m lar yStore(
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ): Pa rScoreStore[
    S mClustersEmbedd ngPa rScore d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ] = {

    def logNormCos neS m lar y: (
      S mClustersEmbedd ng,
      S mClustersEmbedd ng
    ) => Future[Opt on[Double]] = {
      case (embedd ng1, embedd ng2) =>
        Future.value(So (embedd ng1.logNormCos neS m lar y(embedd ng2)))
    }

    S mClustersEmbedd ng nternalPa rScoreStore(
      s mClustersEmbedd ngStore,
      logNormCos neS m lar y
    )
  }

  def bu ldExpScaledCos neS m lar yStore(
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ): Pa rScoreStore[
    S mClustersEmbedd ngPa rScore d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ] = {

    def expScaledCos neS m lar y: (
      S mClustersEmbedd ng,
      S mClustersEmbedd ng
    ) => Future[Opt on[Double]] = {
      case (embedd ng1, embedd ng2) =>
        Future.value(So (embedd ng1.expScaledCos neS m lar y(embedd ng2)))
    }

    S mClustersEmbedd ng nternalPa rScoreStore(
      s mClustersEmbedd ngStore,
      expScaledCos neS m lar y
    )
  }

  def bu ldJaccardS m lar yStore(
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ): Pa rScoreStore[
    S mClustersEmbedd ngPa rScore d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ] = {

    def jaccardS m lar y: (
      S mClustersEmbedd ng,
      S mClustersEmbedd ng
    ) => Future[Opt on[Double]] = {
      case (embedd ng1, embedd ng2) =>
        Future.value(So (embedd ng1.jaccardS m lar y(embedd ng2)))
    }

    S mClustersEmbedd ng nternalPa rScoreStore(
      s mClustersEmbedd ngStore,
      jaccardS m lar y
    )
  }

  def bu ldEucl deanD stanceStore(
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ): Pa rScoreStore[
    S mClustersEmbedd ngPa rScore d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ] = {

    def eucl deanD stance: (
      S mClustersEmbedd ng,
      S mClustersEmbedd ng
    ) => Future[Opt on[Double]] = {
      case (embedd ng1, embedd ng2) =>
        Future.value(So (embedd ng1.eucl deanD stance(embedd ng2)))
    }

    S mClustersEmbedd ng nternalPa rScoreStore(
      s mClustersEmbedd ngStore,
      eucl deanD stance
    )
  }

  def bu ldManhattanD stanceStore(
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ): Pa rScoreStore[
    S mClustersEmbedd ngPa rScore d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ] = {

    def manhattanD stance: (
      S mClustersEmbedd ng,
      S mClustersEmbedd ng
    ) => Future[Opt on[Double]] = {
      case (embedd ng1, embedd ng2) =>
        Future.value(So (embedd ng1.manhattanD stance(embedd ng2)))
    }

    S mClustersEmbedd ng nternalPa rScoreStore(
      s mClustersEmbedd ngStore,
      manhattanD stance
    )
  }

}
