package com.tw ter.s mclusters_v2.common.ml

 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .Feature.SparseCont nuous
 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng

class S mClustersEmbedd ngAdapter(embedd ngFeature: SparseCont nuous)
    extends  RecordOneToOneAdapter[S mClustersEmbedd ng] {

  overr de def getFeatureContext: FeatureContext = new FeatureContext(embedd ngFeature)

  overr de def adaptToDataRecord(embedd ng: S mClustersEmbedd ng): DataRecord = {
    val embedd ngMap = embedd ng.embedd ng.map {
      case (cluster d, score) =>
        (cluster d.toStr ng, score)
    }.toMap

    new DataRecord().setFeatureValue(embedd ngFeature, embedd ngMap)
  }
}

class Normal zedS mClustersEmbedd ngAdapter(
  embedd ngFeature: SparseCont nuous,
  normFeature: Cont nuous)
    extends  RecordOneToOneAdapter[S mClustersEmbedd ng] {

  overr de def getFeatureContext: FeatureContext = new FeatureContext(embedd ngFeature, normFeature)

  overr de def adaptToDataRecord(embedd ng: S mClustersEmbedd ng): DataRecord = {

    val normal zedEmbedd ng = Map(
      embedd ng.sortedCluster ds.map(_.toStr ng).z p(embedd ng.normal zedSortedScores): _*)

    val dataRecord = new DataRecord().setFeatureValue(embedd ngFeature, normal zedEmbedd ng)
    dataRecord.setFeatureValue(normFeature, embedd ng.l2norm)
  }
}
