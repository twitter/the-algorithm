package com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann

object Conf g {
  /*
   * Common root path
   */
  val RootMHPath: Str ng = "manhattan_sequence_f les/offl ne_sann/"
  val RootThr ftPath: Str ng = "processed/offl ne_sann/"
  val AdhocRootPath = "adhoc/offl ne_sann/"

  /*
   * Var ables for MH output path
   */
  val   KFANNOutputPath: Str ng = "t ets_ann/  kf"
  val   KFHL0EL15ANNOutputPath: Str ng = "t ets_ann/  kf_hl_0_el_15"
  val   KFHL2EL15ANNOutputPath: Str ng = "t ets_ann/  kf_hl_2_el_15"
  val   KFHL2EL50ANNOutputPath: Str ng = "t ets_ann/  kf_hl_2_el_50"
  val   KFHL8EL50ANNOutputPath: Str ng = "t ets_ann/  kf_hl_8_el_50"
  val MTSConsu rEmbedd ngsANNOutputPath: Str ng = "t ets_ann/mts_consu r_embedd ngs"

  /*
   * Var ables for t et embedd ngs generat on
   */
  val S mClustersT etEmbedd ngsGenerat onHalfL fe:  nt = 28800000 // 8hrs  n ms
  val S mClustersT etEmbedd ngsGenerat onEmbedd ngLength:  nt = 15

  /*
   * Var ables for ANN
   */
  val S mClustersANNTopNClustersPerS ceEmbedd ng:  nt = 20
  val S mClustersANNTopMT etsPerCluster:  nt = 50
  val S mClustersANNTopKT etsPerUserRequest:  nt = 200
}
