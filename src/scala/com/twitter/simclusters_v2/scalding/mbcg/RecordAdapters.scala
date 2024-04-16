package com.tw ter.s mclusters_v2.scald ng.mbcg

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .embedd ng.Embedd ng
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .FloatTensor
 mport com.tw ter.ml.ap .GeneralTensor
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.Pers stentS mClustersEmbedd ng
 mport scala.collect on.JavaConverters._

/*
Adapters to convert data from MBCG  nput s ces  nto DataRecords
 */
object T etS mclusterRecordAdapter
    extends  RecordOneToOneAdapter[(Long, Pers stentS mClustersEmbedd ng, Embedd ng[Float])] {
  overr de def getFeatureContext: FeatureContext = T etAllFeatures.featureContext

  overr de def adaptToDataRecord(
    t etFeatures: (Long, Pers stentS mClustersEmbedd ng, Embedd ng[Float])
  ) = {
    val dataRecord = new DataRecord()
    val t et d = t etFeatures._1
    val t etEmbedd ng = t etFeatures._2
    val f2vEmbedd ng = t etFeatures._3
    val s mclusterW hScores = t etEmbedd ng.embedd ng.embedd ng
      .map { s mclusterW hScore =>
        // Cluster  D and score for that cluster
        (s mclusterW hScore._1.toStr ng, s mclusterW hScore._2)
      }.toMap.asJava

    dataRecord.setFeatureValue(T etAllFeatures.t et d, t et d)
    dataRecord.setFeatureValue(T etAllFeatures.t etS mclusters, s mclusterW hScores)
    dataRecord.setFeatureValue(
      T etAllFeatures.authorF2vProducerEmbedd ng,
      GeneralTensor.floatTensor(
        new FloatTensor(f2vEmbedd ng.map(Double.box(_)).asJava)
      )
    )

    dataRecord
  }
}

object UserS mclusterRecordAdapter
    extends  RecordOneToOneAdapter[(Long, ClustersUser s nterested n, Embedd ng[Float])] {
  overr de def getFeatureContext: FeatureContext = T etAllFeatures.featureContext

  overr de def adaptToDataRecord(
    userS mclusterEmbedd ng: (Long, ClustersUser s nterested n, Embedd ng[Float])
  ) = {
    val dataRecord = new DataRecord()
    val user d = userS mclusterEmbedd ng._1
    val userEmbedd ng = userS mclusterEmbedd ng._2
    val s mclusterW hScores = userEmbedd ng.cluster dToScores
      .f lter {
        case (_, score) =>
          score.logFavScore.map(_ >= 0.0).getOrElse(false)
      }
      .map {
        case (cluster d, score) =>
          (cluster d.toStr ng, score.logFavScore.get)
      }.toMap.asJava
    val f2vEmbedd ng = userS mclusterEmbedd ng._3

    dataRecord.setFeatureValue(UserAllFeatures.user d, user d)
    dataRecord.setFeatureValue(UserAllFeatures.userS mclusters, s mclusterW hScores)
    dataRecord.setFeatureValue(
      UserAllFeatures.userF2vConsu rEmbedd ng,
      GeneralTensor.floatTensor(
        new FloatTensor(f2vEmbedd ng.map(Double.box(_)).asJava)
      )
    )

    dataRecord
  }
}
