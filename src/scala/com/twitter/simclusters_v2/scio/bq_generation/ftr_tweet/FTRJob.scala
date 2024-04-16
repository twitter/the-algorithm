package com.tw ter.s mclusters_v2.sc o.bq_generat on
package ftr_t et

 mport com.google.ap .serv ces.b gquery.model.T  Part  on ng
 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.coders.Coder
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport com.tw ter.convers ons.Durat onOps.r chDurat onFrom nt
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.sc o_ nternal.coders.Thr ftStructLazyB naryScroogeCoder
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQTableDeta ls
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.get nterested n2020SQL
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT ets
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT etsL st
 mport com.tw ter.tcdc.bqblaster.beam.syntax._
 mport com.tw ter.tcdc.bqblaster.core.avro.TypedProject on
 mport com.tw ter.tcdc.bqblaster.core.transform.RootTransform
 mport java.t  . nstant
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT et
 mport org.apac .avro.gener c.Gener cData
 mport scala.collect on.mutable.L stBuffer
 mport org.apac .beam.sdk. o.gcp.b gquery.Sc maAndRecord
 mport org.apac .beam.sdk.transforms.Ser al zableFunct on
 mport org.apac .avro.gener c.Gener cRecord
 mport com.tw ter.wtf.beam.bq_embedd ng_export.BQQueryUt ls

tra  FTRJob extends Sc oBeamJob[DateRangeOpt ons] {
  // Conf gs to set for d fferent type of embedd ngs and jobs
  val  sAdhoc: Boolean
  val outputTable: BQTableDeta ls
  val keyValDatasetOutputPath: Str ng
  val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[KeyVal[Long, Cand dateT etsL st]]
  val scoreKey: Str ng
  val scoreColumn: Str ng

  // Base conf gs
  val project d = "twttr-recos-ml-prod"
  val env ron nt: DAL.Env =  f ( sAdhoc) DAL.Env ron nt.Dev else DAL.Env ron nt.Prod

  overr de  mpl c  def scroogeCoder[T <: Thr ftStruct: Man fest]: Coder[T] =
    Thr ftStructLazyB naryScroogeCoder.scroogeCoder

  overr de def conf gureP pel ne(sc: Sc oContext, opts: DateRangeOpt ons): Un  = {
    // T  t   w n t  job  s sc duled
    val queryT  stamp = opts. nterval.getEnd

    // Parse t et d cand dates column
    def parseT et dColumn(
      gener cRecord: Gener cRecord,
      columnNa : Str ng
    ): L st[Cand dateT et] = {
      val t et ds: Gener cData.Array[Gener cRecord] =
        gener cRecord.get(columnNa ).as nstanceOf[Gener cData.Array[Gener cRecord]]
      val results: L stBuffer[Cand dateT et] = new L stBuffer[Cand dateT et]()
      t et ds.forEach((sc: Gener cRecord) => {
        results += Cand dateT et(
          t et d = sc.get("t et d").toStr ng.toLong,
          score = So (sc.get("cos neS m lar yScore").toStr ng.toDouble)
        )
      })
      results.toL st
    }

    //Funct on that parses t  Gener cRecord results   read from BQ
    val parseUserToT etRecom ndat onsFunc =
      new Ser al zableFunct on[Sc maAndRecord, UserToT etRecom ndat ons] {
        overr de def apply(record: Sc maAndRecord): UserToT etRecom ndat ons = {
          val gener cRecord: Gener cRecord = record.getRecord
          UserToT etRecom ndat ons(
            user d = gener cRecord.get("user d").toStr ng.toLong,
            t etCand dates = parseT et dColumn(gener cRecord, "t ets"),
          )
        }
      }

    val t etEmbedd ngTemplateVar ables =
      Map(
        "START_T ME" -> queryT  stamp.m nusDays(1).toStr ng(),
        "END_T ME" -> queryT  stamp.toStr ng(),
        "TWEET_SAMPLE_RATE" -> Conf g.T etSampleRate.toStr ng,
        "ENG_SAMPLE_RATE" -> Conf g.EngSampleRate.toStr ng,
        "M N_TWEET_FAVS" -> Conf g.M nT etFavs.toStr ng,
        "M N_TWEET_ MPS" -> Conf g.M nT et mps.toStr ng,
        "MAX_TWEET_FTR" -> Conf g.MaxT etFTR.toStr ng,
        "MAX_USER_LOG_N_ MPS" -> Conf g.MaxUserLogN mps.toStr ng,
        "MAX_USER_LOG_N_FAVS" -> Conf g.MaxUserLogNFavs.toStr ng,
        "MAX_USER_FTR" -> Conf g.MaxUserFTR.toStr ng,
        "TWEET_EMBEDD NG_LENGTH" -> Conf g.S mClustersT etEmbedd ngsGenerat onEmbedd ngLength.toStr ng,
        "HALFL FE" -> Conf g.S mClustersT etEmbedd ngsGenerat onHalfL fe.toStr ng,
        "SCORE_COLUMN" -> scoreColumn,
        "SCORE_KEY" -> scoreKey,
      )

    val t etEmbedd ngSql = BQQueryUt ls.getBQQueryFromSqlF le(
      "/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/sql/ftr_t et_embedd ngs.sql",
      t etEmbedd ngTemplateVar ables)
    val consu rEmbedd ngSql = get nterested n2020SQL(queryT  stamp, 14)

    val t etRecom ndat onsTemplateVar ables =
      Map(
        "CONSUMER_EMBEDD NGS_SQL" -> consu rEmbedd ngSql,
        "TWEET_EMBEDD NGS_SQL" -> t etEmbedd ngSql,
        "TOP_N_CLUSTER_PER_SOURCE_EMBEDD NG" -> Conf g.S mClustersANNTopNClustersPerS ceEmbedd ng.toStr ng,
        "TOP_M_TWEETS_PER_CLUSTER" -> Conf g.S mClustersANNTopMT etsPerCluster.toStr ng,
        "TOP_K_TWEETS_PER_USER_REQUEST" -> Conf g.S mClustersANNTopKT etsPerUserRequest.toStr ng,
      )
    val t etRecom ndat onsSql = BQQueryUt ls.getBQQueryFromSqlF le(
      "/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/t ets_ann.sql",
      t etRecom ndat onsTemplateVar ables)

    val t etRecom ndat ons = sc.custom nput(
      s"S mClusters FTR BQ ANN",
      B gQuery O
        .read(parseUserToT etRecom ndat onsFunc)
        .fromQuery(t etRecom ndat onsSql)
        .us ngStandardSql()
    )

    //Setup BQ wr er
    val  ngest onT   = opts.getDate().value.getEnd.toDate
    val bqF eldsTransform = RootTransform
      .Bu lder()
      .w hPrependedF elds(" ngest onT  " -> TypedProject on.fromConstant( ngest onT  ))
    val t  Part  on ng = new T  Part  on ng()
      .setType("HOUR").setF eld(" ngest onT  ").setExp rat onMs(3.days. nM ll seconds)
    val bqWr er = B gQuery O
      .wr e[Cand dateT ets]
      .to(outputTable.toStr ng)
      .w hExtendedError nfo()
      .w hT  Part  on ng(t  Part  on ng)
      .w hLoadJobProject d(project d)
      .w hThr ftSupport(bqF eldsTransform.bu ld(), AvroConverter.Legacy)
      .w hCreateD spos  on(B gQuery O.Wr e.CreateD spos  on.CREATE_ F_NEEDED)
      .w hWr eD spos  on(B gQuery O.Wr e.Wr eD spos  on.WR TE_APPEND)

    // Save T et ANN results to BQ
    t etRecom ndat ons
      .map { userToT etRecom ndat ons =>
        {
          Cand dateT ets(
            targetUser d = userToT etRecom ndat ons.user d,
            recom ndedT ets = userToT etRecom ndat ons.t etCand dates)
        }
      }
      .saveAsCustomOutput(s"Wr eToBQTable - $outputTable", bqWr er)

    val RootMHPath: Str ng = Conf g.FTRRootMHPath
    val AdhocRootPath = Conf g.FTRAdhocpath

    // Save T et ANN results as KeyValSnapshotDataset
    t etRecom ndat ons
      .map { userToT etRecom ndat ons =>
        KeyVal(
          userToT etRecom ndat ons.user d,
          Cand dateT etsL st(userToT etRecom ndat ons.t etCand dates))
      }.saveAsCustomOutput(
        na  = "Wr eFtrT etRecom ndat onsToKeyValDataset",
        DAL.wr eVers onedKeyVal(
          t etRecom ntat onsSnapshotDataset,
          PathLa t.Vers onedPath(pref x =
            (( f (! sAdhoc)
                RootMHPath
              else
                AdhocRootPath)
              + keyValDatasetOutputPath)),
           nstant =  nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          env ron ntOverr de = env ron nt,
        )
      )
  }

}

object FTRAdhocJob extends FTRJob {
  overr de val  sAdhoc = true
  overr de val outputTable: BQTableDeta ls =
    BQTableDeta ls("twttr-recos-ml-prod", "s mclusters", "offl ne_t et_recom ndat ons_ftr_adhoc")
  overr de val keyValDatasetOutputPath = Conf g.  KFFTRAdhocANNOutputPath

  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFtrAdhocScalaDataset
  overr de val scoreColumn = "ftrat5_decayed_pop_b as_1000_rank_decay_1_1_embedd ng"
  overr de val scoreKey = "ftrat5_decayed_pop_b as_1000_rank_decay_1_1"
}

object   KF2020DecayedSumBatchJobProd extends FTRJob {
  overr de val  sAdhoc = false
  overr de val outputTable: BQTableDeta ls = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_decayed_sum"
  )
  overr de val keyValDatasetOutputPath = Conf g.  KFDecayedSumANNOutputPath
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsDecayedSumScalaDataset
  overr de val scoreColumn = "dec_sum_logfavScoreClusterNormal zedOnly_embedd ng"
  overr de val scoreKey = "dec_sum_logfavScoreClusterNormal zedOnly"
}

object   KF2020FTRAt5Pop1000batchJobProd extends FTRJob {
  overr de val  sAdhoc = false
  overr de val outputTable: BQTableDeta ls = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_ftrat5_pop_b ased_1000")
  overr de val keyValDatasetOutputPath = Conf g.  KFFTRAt5Pop1000ANNOutputPath
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFtrat5PopB ased1000ScalaDataset
  overr de val scoreColumn = "ftrat5_decayed_pop_b as_1000_rank_decay_1_1_embedd ng"
  overr de val scoreKey = "ftrat5_decayed_pop_b as_1000_rank_decay_1_1"
}

object   KF2020FTRAt5Pop10000batchJobProd extends FTRJob {
  overr de val  sAdhoc = false
  overr de val outputTable: BQTableDeta ls = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_ftrat5_pop_b ased_10000")
  overr de val keyValDatasetOutputPath = Conf g.  KFFTRAt5Pop10000ANNOutputPath
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFtrat5PopB ased10000ScalaDataset
  overr de val scoreColumn = "ftrat5_decayed_pop_b as_10000_rank_decay_1_1_embedd ng"
  overr de val scoreKey = "ftrat5_decayed_pop_b as_10000_rank_decay_1_1"
}

case class UserToT etRecom ndat ons(
  user d: Long,
  t etCand dates: L st[Cand dateT et])
