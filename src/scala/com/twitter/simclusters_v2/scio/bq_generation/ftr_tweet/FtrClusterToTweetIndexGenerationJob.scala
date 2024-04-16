package com.tw ter.s mclusters_v2
package sc o.bq_generat on.ftr_t et

 mport com.google.ap .serv ces.b gquery.model.T  Part  on ng
 mport com.tw ter.convers ons.Durat onOps.r chDurat onFrom nt
 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.coders.Coder
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.dal.DAL.PathLa t
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common. ndexGenerat onUt l.parseClusterTopKT etsFn
 mport java.t  . nstant
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.sc o_ nternal.coders.Thr ftStructLazyB naryScroogeCoder
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQTableDeta ls
 mport com.tw ter.s mclusters_v2.thr ftscala.Cluster dToTopKT etsW hScores
 mport com.tw ter.s mclusters_v2.thr ftscala.FullCluster d
 mport com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores
 mport com.tw ter.tcdc.bqblaster.beam.syntax._
 mport com.tw ter.tcdc.bqblaster.core.avro.TypedProject on
 mport com.tw ter.tcdc.bqblaster.core.transform.RootTransform
 mport com.tw ter.wtf.beam.bq_embedd ng_export.BQQueryUt ls
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O

tra  FTRClusterToT et ndexGenerat onJob extends Sc oBeamJob[DateRangeOpt ons] {
  val  sAdhoc: Boolean

  val outputTable: BQTableDeta ls
  val keyValDatasetOutputPath: Str ng
  val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ]

  // Base conf gs
  val project d = "twttr-recos-ml-prod"
  val env ron nt: DAL.Env =  f ( sAdhoc) DAL.Env ron nt.Dev else DAL.Env ron nt.Prod

  // Var ables for T et Embedd ng SQL
  val scoreKey: Str ng
  val scoreColumn: Str ng

  // Var ables for spam treat nt
  val maxT etFTR: Double
  val maxUserFTR: Double

  // T et embedd ngs para ters
  val t etEmbedd ngsLength:  nt = Conf g.S mClustersT etEmbedd ngsGenerat onEmbedd ngLength

  // Clusters-to-t et  ndex para ters
  val clusterTopKT ets:  nt = Conf g.clusterTopKT ets
  val maxT etAgeH s:  nt = Conf g.maxT etAgeH s

  overr de  mpl c  def scroogeCoder[T <: Thr ftStruct: Man fest]: Coder[T] =
    Thr ftStructLazyB naryScroogeCoder.scroogeCoder

  overr de def conf gureP pel ne(sc: Sc oContext, opts: DateRangeOpt ons): Un  = {
    // T  t   w n t  job  s sc duled
    val queryT  stamp = opts. nterval.getEnd

    val t etEmbedd ngTemplateVar ables =
      Map(
        "START_T ME" -> queryT  stamp.m nusDays(1).toStr ng(),
        "END_T ME" -> queryT  stamp.toStr ng(),
        "TWEET_SAMPLE_RATE" -> Conf g.T etSampleRate.toStr ng,
        "ENG_SAMPLE_RATE" -> Conf g.EngSampleRate.toStr ng,
        "M N_TWEET_FAVS" -> Conf g.M nT etFavs.toStr ng,
        "M N_TWEET_ MPS" -> Conf g.M nT et mps.toStr ng,
        "MAX_TWEET_FTR" -> maxT etFTR.toStr ng,
        "MAX_USER_LOG_N_ MPS" -> Conf g.MaxUserLogN mps.toStr ng,
        "MAX_USER_LOG_N_FAVS" -> Conf g.MaxUserLogNFavs.toStr ng,
        "MAX_USER_FTR" -> maxUserFTR.toStr ng,
        "TWEET_EMBEDD NG_LENGTH" -> Conf g.S mClustersT etEmbedd ngsGenerat onEmbedd ngLength.toStr ng,
        "HALFL FE" -> Conf g.S mClustersT etEmbedd ngsGenerat onHalfL fe.toStr ng,
        "SCORE_COLUMN" -> scoreColumn,
        "SCORE_KEY" -> scoreKey,
      )
    val t etEmbedd ngSql = BQQueryUt ls.getBQQueryFromSqlF le(
      "/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/sql/ftr_t et_embedd ngs.sql",
      t etEmbedd ngTemplateVar ables)

    val clusterTopT etsTemplateVar ables =
      Map(
        "CLUSTER_TOP_K_TWEETS" -> Conf g.clusterTopKT ets.toStr ng,
        "TWEET_EMBEDD NG_SQL" -> t etEmbedd ngSql
      )

    val clusterTopT etsSql = BQQueryUt ls.getBQQueryFromSqlF le(
      "/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/cluster_top_t ets.sql",
      clusterTopT etsTemplateVar ables
    )

    // Generate S mClusters cluster-to-t et  ndex
    val topKt etsForClusterKey = sc.custom nput(
      s"S mClusters cluster-to-t et  ndex generat on BQ job",
      B gQuery O
        .read(parseClusterTopKT etsFn(Conf g.T etEmbedd ngHalfL fe))
        .fromQuery(clusterTopT etsSql)
        .us ngStandardSql()
    )

    // Setup BQ wr er
    val  ngest onT   = opts.getDate().value.getEnd.toDate
    val bqF eldsTransform = RootTransform
      .Bu lder()
      .w hPrependedF elds("dateH " -> TypedProject on.fromConstant( ngest onT  ))
    val t  Part  on ng = new T  Part  on ng()
      .setType("HOUR").setF eld("dateH ").setExp rat onMs(3.days. nM ll seconds)
    val bqWr er = B gQuery O
      .wr e[Cluster dToTopKT etsW hScores]
      .to(outputTable.toStr ng)
      .w hExtendedError nfo()
      .w hT  Part  on ng(t  Part  on ng)
      .w hLoadJobProject d(project d)
      .w hThr ftSupport(bqF eldsTransform.bu ld(), AvroConverter.Legacy)
      .w hCreateD spos  on(B gQuery O.Wr e.CreateD spos  on.CREATE_ F_NEEDED)
      .w hWr eD spos  on(B gQuery O.Wr e.Wr eD spos  on.WR TE_APPEND)

    // Save S mClusters  ndex to a BQ table
    topKt etsForClusterKey
      .map { cluster dToTopKT ets =>
        {
          Cluster dToTopKT etsW hScores(
            cluster d = cluster dToTopKT ets.cluster d,
            topKT etsW hScores = cluster dToTopKT ets.topKT etsW hScores
          )
        }
      }
      .saveAsCustomOutput(s"Wr eToBQTable - $outputTable", bqWr er)

    // Save S mClusters  ndex as a KeyValSnapshotDataset
    topKt etsForClusterKey
      .map { cluster dToTopKT ets =>
        KeyVal(cluster dToTopKT ets.cluster d, cluster dToTopKT ets.topKT etsW hScores)
      }.saveAsCustomOutput(
        na  = s"Wr eClusterToKey ndexToKeyValDataset at $keyValDatasetOutputPath",
        DAL.wr eVers onedKeyVal(
          clusterToT et ndexSnapshotDataset,
          PathLa t.Vers onedPath(pref x =
            (( f (! sAdhoc)
                Conf g.FTRRootMHPath
              else
                Conf g.FTRAdhocpath)
              + keyValDatasetOutputPath)),
           nstant =  nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          env ron ntOverr de = env ron nt,
        )
      )
  }
}

object FTRClusterToT et ndexGenerat onAdhoc extends FTRClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc: Boolean = true
  overr de val outputTable: BQTableDeta ls =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mcluster_adhoc_test_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath: Str ng =
    "ftr_t ets_adhoc/ftr_cluster_to_t et_adhoc"
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = S mclustersFtrAdhocClusterToT et ndexScalaDataset
  overr de val scoreColumn = "ftrat5_decayed_pop_b as_1000_rank_decay_1_1_embedd ng"
  overr de val scoreKey = "ftrat5_decayed_pop_b as_1000_rank_decay_1_1"
  overr de val maxUserFTR: Double = Conf g.MaxUserFTR
  overr de val maxT etFTR: Double = Conf g.MaxT etFTR

}

object OONFTRClusterToT et ndexGenerat onAdhoc extends FTRClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc: Boolean = true
  overr de val outputTable: BQTableDeta ls =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mcluster_adhoc_test_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath: Str ng =
    "oon_ftr_t ets_adhoc/oon_ftr_cluster_to_t et_adhoc"
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = S mclustersOonFtrAdhocClusterToT et ndexScalaDataset
  overr de val scoreColumn = "oon_ftrat5_decayed_pop_b as_1000_rank_decay_embedd ng"
  overr de val scoreKey = "oon_ftrat5_decayed_pop_b as_1000_rank_decay"
  overr de val maxUserFTR: Double = Conf g.MaxUserFTR
  overr de val maxT etFTR: Double = Conf g.MaxT etFTR
}

object FTRPop1000RankDecay11ClusterToT et ndexGenerat onBatch
    extends FTRClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc: Boolean = false
  overr de val outputTable: BQTableDeta ls =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_ftr_pop1000_rnkdecay11_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath: Str ng =
    Conf g.FTRPop1000RankDecay11ClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = S mclustersFtrPop1000Rnkdecay11ClusterToT et ndexScalaDataset
  overr de val scoreColumn = "ftrat5_decayed_pop_b as_1000_rank_decay_1_1_embedd ng"
  overr de val scoreKey = "ftrat5_decayed_pop_b as_1000_rank_decay_1_1"
  overr de val maxUserFTR: Double = Conf g.MaxUserFTR
  overr de val maxT etFTR: Double = Conf g.MaxT etFTR
}

object FTRPop10000RankDecay11ClusterToT et ndexGenerat onBatch
    extends FTRClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc: Boolean = false
  overr de val outputTable: BQTableDeta ls =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_ftr_pop10000_rnkdecay11_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath: Str ng =
    Conf g.FTRPop10000RankDecay11ClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = S mclustersFtrPop10000Rnkdecay11ClusterToT et ndexScalaDataset
  overr de val scoreColumn = "ftrat5_decayed_pop_b as_10000_rank_decay_1_1_embedd ng"
  overr de val scoreKey = "ftrat5_decayed_pop_b as_10000_rank_decay_1_1"
  overr de val maxUserFTR: Double = Conf g.MaxUserFTR
  overr de val maxT etFTR: Double = Conf g.MaxT etFTR
}

object OONFTRPop1000RankDecayClusterToT et ndexGenerat onBatch
    extends FTRClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc: Boolean = false
  overr de val outputTable: BQTableDeta ls =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_oon_ftr_pop1000_rnkdecay_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath: Str ng =
    Conf g.OONFTRPop1000RankDecayClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = S mclustersOonFtrPop1000RnkdecayClusterToT et ndexScalaDataset
  overr de val scoreColumn = "oon_ftrat5_decayed_pop_b as_1000_rank_decay_embedd ng"
  overr de val scoreKey = "oon_ftrat5_decayed_pop_b as_1000_rank_decay"
  overr de val maxUserFTR: Double = Conf g.MaxUserFTR
  overr de val maxT etFTR: Double = Conf g.MaxT etFTR
}

object DecayedSumClusterToT et ndexGenerat onBatch extends FTRClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc: Boolean = false
  overr de val outputTable: BQTableDeta ls =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_decayed_sum_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath: Str ng =
    Conf g.DecayedSumClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = S mclustersDecayedSumClusterToT et ndexScalaDataset
  overr de val scoreColumn = "dec_sum_logfavScoreClusterNormal zedOnly_embedd ng"
  overr de val scoreKey = "dec_sum_logfavScoreClusterNormal zedOnly"
  overr de val maxUserFTR = 1.0
  overr de val maxT etFTR = 1.0
}
