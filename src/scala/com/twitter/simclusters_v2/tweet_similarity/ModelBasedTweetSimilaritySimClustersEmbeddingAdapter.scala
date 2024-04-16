package com.tw ter.s mclusters_v2.t et_s m lar y

 mport com.tw ter.ml.ap .{DataRecord, DataRecord rger}
 mport com.tw ter.s mclusters_v2.common.ml.{
  S mClustersEmbedd ngAdapter,
  Normal zedS mClustersEmbedd ngAdapter
}
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng

object ModelBasedT etS m lar yS mClustersEmbedd ngAdapter {
  val QueryEmbAdapter = new S mClustersEmbedd ngAdapter(T etS m lar yFeatures.QueryT etEmbedd ng)
  val Cand dateEmbAdapter = new S mClustersEmbedd ngAdapter(
    T etS m lar yFeatures.Cand dateT etEmbedd ng)

  val Normal zedQueryEmbAdapter = new Normal zedS mClustersEmbedd ngAdapter(
    T etS m lar yFeatures.QueryT etEmbedd ng,
    T etS m lar yFeatures.QueryT etEmbedd ngNorm)
  val Normal zedCand dateEmbAdapter = new Normal zedS mClustersEmbedd ngAdapter(
    T etS m lar yFeatures.Cand dateT etEmbedd ng,
    T etS m lar yFeatures.Cand dateT etEmbedd ngNorm)

  def adaptEmbedd ngPa rToDataRecord(
    queryEmbedd ng: S mClustersEmbedd ng,
    cand dateEmbedd ng: S mClustersEmbedd ng,
    normal zed: Boolean
  ): DataRecord = {
    val DataRecord rger = new DataRecord rger()
    val queryAdapter =  f (normal zed) Normal zedQueryEmbAdapter else QueryEmbAdapter
    val cand dateAdapter =  f (normal zed) Normal zedCand dateEmbAdapter else Cand dateEmbAdapter

    val featureDataRecord = queryAdapter.adaptToDataRecord(queryEmbedd ng)
    DataRecord rger. rge(
      featureDataRecord,
      cand dateAdapter.adaptToDataRecord(cand dateEmbedd ng))
    featureDataRecord
  }
}
