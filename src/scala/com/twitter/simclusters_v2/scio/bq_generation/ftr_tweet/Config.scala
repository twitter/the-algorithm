package com.tw ter.s mclusters_v2.sc o.bq_generat on.ftr_t et

object Conf g {
  //   Var ables for MH output path
  val FTRRootMHPath: Str ng = "manhattan_sequence_f les/ftr_t et_embedd ng/"
  val FTRAdhocpath: Str ng = "adhoc/ftr_t et_embedd ng/"
  val   KFFTRAdhocANNOutputPath: Str ng = "ftr_t ets_test/y _ldap_test"
  val   KFFTRAt5Pop1000ANNOutputPath: Str ng = "ftr_t ets/ftr_at_5_pop_b ased_1000"
  val   KFFTRAt5Pop10000ANNOutputPath: Str ng = "ftr_t ets/ftr_at_5_pop_b ased_10000"
  val   KFDecayedSumANNOutputPath: Str ng = "ftr_t ets/decayed_sum"

  val DecayedSumClusterToT et ndexOutputPath = "ftr_cluster_to_t et/decayed_sum"

  val FTRPop1000RankDecay11ClusterToT et ndexOutputPath =
    "ftr_cluster_to_t et/ftr_pop1000_rnkdecay11"
  val FTRPop10000RankDecay11ClusterToT et ndexOutputPath =
    "ftr_cluster_to_t et/ftr_pop10000_rnkdecay11"
  val OONFTRPop1000RankDecayClusterToT et ndexOutputPath =
    "oon_ftr_cluster_to_t et/oon_ftr_pop1000_rnkdecay"

  //  Var ables for t et embedd ngs generat on
  val T etSampleRate = 1 // 100% sample rate
  val EngSampleRate = 1 // engage nt from 50% of users
  val M nT etFavs = 8 // m n favs for t ets
  val M nT et mps = 50 // m n  mpress ons for t ets
  val MaxT etFTR = 0.5 // max mum t et FTR, a way to combat spam  t ets
  val MaxUserLogN mps = 5 // max mum number of  mpress ons 1e5 for users
  val MaxUserLogNFavs = 4 // max mum number of favs 1e4 for users
  val MaxUserFTR = 0.3 // max mum user FTR, a way to combat accounts that fav everyth ng

  val S mClustersT etEmbedd ngsGenerat onHalfL fe:  nt = 28800000 // 8hrs  n ms
  val S mClustersT etEmbedd ngsGenerat onEmbedd ngLength = 15

  //  Var ables for BQ ANN
  val S mClustersANNTopNClustersPerS ceEmbedd ng:  nt = 20
  val S mClustersANNTopMT etsPerCluster:  nt = 50
  val S mClustersANNTopKT etsPerUserRequest:  nt = 200

  //  Cluster-to-t et  ndex conf gs
  val clusterTopKT ets:  nt = 2000
  val maxT etAgeH s:  nt = 24
  val T etEmbedd ngHalfL fe:  nt = 28800000 // for usage  n DecayedValue struct
}
