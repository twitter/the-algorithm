package com.tw ter.s mclusters_v2.common

 mport com.tw ter.s mclusters_v2.common.S mClustersMult Embedd ng d._
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersMult Embedd ng.{ ds, Values}
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  S mClustersMult Embedd ng,
  S mClustersEmbedd ng d,
  S mClustersMult Embedd ng d
}

/**
 *  lper  thods for S mClustersMult Embedd ng
 */
object S mClustersMult Embedd ng {

  // Convert a mult Embedd ng to a l st of (embedd ng d, score)
  def toS mClustersEmbedd ng dW hScores(
    s mClustersMult Embedd ng d: S mClustersMult Embedd ng d,
    s mClustersMult Embedd ng: S mClustersMult Embedd ng
  ): Seq[(S mClustersEmbedd ng d, Double)] = {
    s mClustersMult Embedd ng match {
      case Values(values) =>
        values.embedd ngs.z pW h ndex.map {
          case (embedd ngW hScore,  ) =>
            (toEmbedd ng d(s mClustersMult Embedd ng d,  ), embedd ngW hScore.score)
        }
      case  ds( ds) =>
         ds. ds.map(_.toTuple)
    }
  }

}
