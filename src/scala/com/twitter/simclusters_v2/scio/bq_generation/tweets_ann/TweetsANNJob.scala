package com.tw ter.s mclusters_v2.sc o.bq_generat on
package t ets_ann

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
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.getMTSConsu rEmbedd ngsFav90P20MSQL
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.get nterested n2020SQL
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.t ets_ann.T etsANNFromBQ.getT etRecom ndat onsBQ
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Offl neT etRecom ndat onsFrom nterested n20M145K2020ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl0El15ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl2El15ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl2El50ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl8El50ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Offl neT etRecom ndat onsFromMtsConsu rEmbedd ngsScalaDataset
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQTableDeta ls
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT ets
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT etsL st
 mport com.tw ter.tcdc.bqblaster.beam.syntax.B gQuery O lpers
 mport com.tw ter.tcdc.bqblaster.beam.BQBlaster O.AvroConverter
 mport com.tw ter.tcdc.bqblaster.core.avro.TypedProject on
 mport com.tw ter.tcdc.bqblaster.core.transform.RootTransform
 mport java.t  . nstant
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O
 mport org.joda.t  .DateT  

tra  T etsANNJob extends Sc oBeamJob[DateRangeOpt ons] {
  // Conf gs to set for d fferent type of embedd ngs and jobs
  val  sAdhoc: Boolean
  val getConsu rEmbedd ngsSQLFunc: (DateT  ,  nt) => Str ng
  val outputTable: BQTableDeta ls
  val keyValDatasetOutputPath: Str ng
  val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[KeyVal[Long, Cand dateT etsL st]]
  val t etEmbedd ngsGenerat onHalfL fe:  nt = Conf g.S mClustersT etEmbedd ngsGenerat onHalfL fe
  val t etEmbedd ngsGenerat onEmbedd ngLength:  nt =
    Conf g.S mClustersT etEmbedd ngsGenerat onEmbedd ngLength

  // Base conf gs
  val project d = "twttr-recos-ml-prod"
  val env ron nt: DAL.Env =  f ( sAdhoc) DAL.Env ron nt.Dev else DAL.Env ron nt.Prod

  overr de  mpl c  def scroogeCoder[T <: Thr ftStruct: Man fest]: Coder[T] =
    Thr ftStructLazyB naryScroogeCoder.scroogeCoder

  overr de def conf gureP pel ne(sc: Sc oContext, opts: DateRangeOpt ons): Un  = {
    // T  t   w n t  job  s sc duled
    val queryT  stamp = opts. nterval.getEnd

    // Read consu r embedd ngs SQL
    val consu rEmbedd ngsSQL = getConsu rEmbedd ngsSQLFunc(queryT  stamp, 14)

    // Generate t et embedd ngs and t et ANN results
    val t etRecom ndat ons =
      getT etRecom ndat onsBQ(
        sc,
        queryT  stamp,
        consu rEmbedd ngsSQL,
        t etEmbedd ngsGenerat onHalfL fe,
        t etEmbedd ngsGenerat onEmbedd ngLength
      )

    // Setup BQ wr er
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
      .saveAsCustomOutput(s"Wr eToBQTable - ${outputTable}", bqWr er)

    // Save T et ANN results as KeyValSnapshotDataset
    t etRecom ndat ons
      .map { userToT etRecom ndat ons =>
        KeyVal(
          userToT etRecom ndat ons.user d,
          Cand dateT etsL st(userToT etRecom ndat ons.t etCand dates))
      }.saveAsCustomOutput(
        na  = "Wr eT etRecom ndat onsToKeyValDataset",
        DAL.wr eVers onedKeyVal(
          t etRecom ntat onsSnapshotDataset,
          PathLa t.Vers onedPath(pref x =
            (( f (! sAdhoc)
                Conf g.RootMHPath
              else
                Conf g.AdhocRootPath)
              + keyValDatasetOutputPath)),
           nstant =  nstant.ofEpochM ll (opts. nterval.getEndM ll s - 1L),
          env ron ntOverr de = env ron nt,
        )
      )
  }

}

/**
 * Sc o job for adhoc run for t et recom ndat ons from   KF 2020
 */
object   KF2020T etsANNBQAdhocJob extends T etsANNJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-recos-ml-prod",
    "mult _type_s mclusters",
    "offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_adhoc")
  overr de val keyValDatasetOutputPath = Conf g.  KFANNOutputPath
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFrom nterested n20M145K2020ScalaDataset
}

/**
 * Sc o job for adhoc run for t et recom ndat ons from   KF 2020 w h
 * - Half l fe = 8hrs
 * - Embedd ng Length = 50
 */
object   KF2020Hl8El50T etsANNBQAdhocJob extends T etsANNJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-recos-ml-prod",
    "mult _type_s mclusters",
    "offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_8_EL_50_adhoc")
  overr de val keyValDatasetOutputPath = Conf g.  KFHL8EL50ANNOutputPath
  overr de val t etEmbedd ngsGenerat onEmbedd ngLength:  nt = 50
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] = {
    Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl8El50ScalaDataset
  }
}

/**
 * Sc o job for adhoc run for t et recom ndat ons from MTS Consu r Embedd ngs
 */
object MTSConsu rEmbedd ngsT etsANNBQAdhocJob extends T etsANNJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = getMTSConsu rEmbedd ngsFav90P20MSQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-recos-ml-prod",
    "mult _type_s mclusters",
    "offl ne_t et_recom ndat ons_from_mts_consu r_embedd ngs_adhoc")
  overr de val keyValDatasetOutputPath = Conf g.MTSConsu rEmbedd ngsANNOutputPath
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFromMtsConsu rEmbedd ngsScalaDataset
}

/**
Sc o job for batch run for t et recom ndat ons from   KF 2020
T  sc dule cmd needs to be run only  f t re  s any change  n t  conf g
 */
object   KF2020T etsANNBQBatchJob extends T etsANNJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020")
  overr de val keyValDatasetOutputPath = Conf g.  KFANNOutputPath
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFrom nterested n20M145K2020ScalaDataset
}

/**
Sc o job for batch run for t et recom ndat ons from   KF 2020 w h para ter setup:
 - Half L fe: None, no decay, d rect sum
 - Embedd ng Length: 15
T  sc dule cmd needs to be run only  f t re  s any change  n t  conf g
 */
object   KF2020Hl0El15T etsANNBQBatchJob extends T etsANNJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_0_EL_15")
  overr de val keyValDatasetOutputPath = Conf g.  KFHL0EL15ANNOutputPath
  overr de val t etEmbedd ngsGenerat onHalfL fe:  nt = -1
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl0El15ScalaDataset
}

/**
Sc o job for batch run for t et recom ndat ons from   KF 2020 w h para ter setup:
 - Half L fe: 2hrs
 - Embedd ng Length: 15
T  sc dule cmd needs to be run only  f t re  s any change  n t  conf g
 */
object   KF2020Hl2El15T etsANNBQBatchJob extends T etsANNJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_2_EL_15")
  overr de val keyValDatasetOutputPath = Conf g.  KFHL2EL15ANNOutputPath
  overr de val t etEmbedd ngsGenerat onHalfL fe:  nt = 7200000 // 2hrs  n ms
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl2El15ScalaDataset
}

/**
Sc o job for batch run for t et recom ndat ons from   KF 2020 w h para ter setup:
 - Half L fe: 2hrs
 - Embedd ng Length: 50
T  sc dule cmd needs to be run only  f t re  s any change  n t  conf g
 */
object   KF2020Hl2El50T etsANNBQBatchJob extends T etsANNJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_2_EL_50")
  overr de val keyValDatasetOutputPath = Conf g.  KFHL2EL50ANNOutputPath
  overr de val t etEmbedd ngsGenerat onHalfL fe:  nt = 7200000 // 2hrs  n ms
  overr de val t etEmbedd ngsGenerat onEmbedd ngLength:  nt = 50
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl2El50ScalaDataset
}

/**
Sc o job for batch run for t et recom ndat ons from   KF 2020 w h para ter setup:
 - Half L fe: 8hrs
 - Embedd ng Length: 50
T  sc dule cmd needs to be run only  f t re  s any change  n t  conf g
 */
object   KF2020Hl8El50T etsANNBQBatchJob extends T etsANNJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_from_ nterested_ n_20M_145K_2020_HL_8_EL_50")
  overr de val keyValDatasetOutputPath = Conf g.  KFHL8EL50ANNOutputPath
  overr de val t etEmbedd ngsGenerat onEmbedd ngLength:  nt = 50
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFrom nterested n20M145K2020Hl8El50ScalaDataset
}

/**
Sc o job for batch run for t et recom ndat ons from MTS Consu r Embedd ngs
T  sc dule cmd needs to be run only  f t re  s any change  n t  conf g
 */
object MTSConsu rEmbedd ngsT etsANNBQBatchJob extends T etsANNJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = getMTSConsu rEmbedd ngsFav90P20MSQL
  overr de val outputTable = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "offl ne_t et_recom ndat ons_from_mts_consu r_embedd ngs")
  overr de val keyValDatasetOutputPath = Conf g.MTSConsu rEmbedd ngsANNOutputPath
  overr de val t etRecom ntat onsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, Cand dateT etsL st]
  ] =
    Offl neT etRecom ndat onsFromMtsConsu rEmbedd ngsScalaDataset
}
