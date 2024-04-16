package com.tw ter.s mclusters_v2.sc o.bq_generat on
package s mclusters_ ndex_generat on

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
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdsFavBasedS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdsFavCl ckBasedS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.FavBasedEvergreenContentS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.FavBasedS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.FavBasedV deoS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.ReplyBasedS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Ret etBasedS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.V deoV ewBasedS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.PushOpenBasedS mclustersClusterToT et ndexScalaDataset
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.bu ldAct onTypesEngage nt nd catorStr ng
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.get nterested n2020SQL
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQTableDeta ls
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.s mclusters_ ndex_generat on.Conf g.AdsCl ckEngage ntType ds
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.s mclusters_ ndex_generat on.Conf g.AdsFavEngage ntType ds
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.s mclusters_ ndex_generat on.Engage ntEventBasedClusterToT et ndexFromBQ.getTopKT etsForClusterKeyBQ
 mport com.tw ter.s mclusters_v2.thr ftscala.Cluster dToTopKT etsW hScores
 mport com.tw ter.s mclusters_v2.thr ftscala.FullCluster d
 mport com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores
 mport com.tw ter.tcdc.bqblaster.beam.syntax._
 mport com.tw ter.tcdc.bqblaster.core.avro.TypedProject on
 mport com.tw ter.tcdc.bqblaster.core.transform.RootTransform
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport java.t  . nstant
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O
 mport org.joda.t  .DateT  

tra  Engage ntEventBasedClusterToT et ndexGenerat onJob extends Sc oBeamJob[DateRangeOpt ons] {
  // Conf gs to set for d fferent type of embedd ngs and jobs
  val  sAdhoc: Boolean
  val getConsu rEmbedd ngsSQLFunc: (DateT  ,  nt) => Str ng
  val outputTable: BQTableDeta ls
  val keyValDatasetOutputPath: Str ng
  val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ]
  // Base conf gs
  val project d = "twttr-recos-ml-prod"
  val env ron nt: DAL.Env =  f ( sAdhoc) DAL.Env ron nt.Dev else DAL.Env ron nt.Prod

  // Po nt to d fferent user t et  nteract on table generat on sql
  // UUA-supported events: Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  val userT etEngage ntEventPa rSqlPath: Str ng
  lazy val userT etEngage ntEventPa rTemplateVar able: Map[Str ng, Str ng] = Map.empty

  // Enable V deo-only f lters and  alth f lters (for V deoV ewBased embedd ngs)
  val enable althAndV deoF lters: Boolean = Conf g.enable althAndV deoF lters

  val enableFavClusterTopKT ets ntersect on: Boolean =
    Conf g.enable ntersect onW hFavBasedClusterTopKT ets ndex

  // M n fav/ nteract on threshold
  val m n nteract onCount:  nt = Conf g.m n nteract onCount
  val m nFavCount:  nt = Conf g.m nFavCount

  // T et embedd ngs para ters
  val t etEmbedd ngsLength:  nt = Conf g.t etEmbedd ngsLength
  val t etEmbedd ngsHalfL fe:  nt = Conf g.t etEmbedd ngsHalfL fe

  // Clusters-to-t et  ndex para ters
  val clusterTopKT ets:  nt = Conf g.clusterTopKT ets
  val maxT etAgeH s:  nt = Conf g.maxT etAgeH s
  val m nEngage ntPerCluster:  nt = Conf g.m nEngage ntPerCluster

  overr de  mpl c  def scroogeCoder[T <: Thr ftStruct: Man fest]: Coder[T] =
    Thr ftStructLazyB naryScroogeCoder.scroogeCoder

  overr de def conf gureP pel ne(sc: Sc oContext, opts: DateRangeOpt ons): Un  = {
    // T  t   w n t  job  s sc duled
    val queryT  stamp = opts. nterval.getEnd

    // Read consu r embedd ngs SQL
    val consu rEmbedd ngsSQL = getConsu rEmbedd ngsSQLFunc(queryT  stamp, 21)

    // Generate S mClusters cluster-to-t et  ndex v a BQ
    val topKt etsForClusterKey =
      getTopKT etsForClusterKeyBQ(
        sc,
        queryT  stamp,
        maxT etAgeH s,
        consu rEmbedd ngsSQL,
        userT etEngage ntEventPa rSqlPath,
        userT etEngage ntEventPa rTemplateVar able,
        enable althAndV deoF lters,
        enableFavClusterTopKT ets ntersect on,
        m n nteract onCount,
        m nFavCount,
        t etEmbedd ngsLength,
        t etEmbedd ngsHalfL fe,
        m nEngage ntPerCluster,
        clusterTopKT ets
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
      .saveAsCustomOutput(s"Wr eToBQTable - ${outputTable}", bqWr er)

    // Save S mClusters  ndex as a KeyValSnapshotDataset
    topKt etsForClusterKey
      .map { cluster dToTopKT ets =>
        KeyVal(cluster dToTopKT ets.cluster d, cluster dToTopKT ets.topKT etsW hScores)
      }.saveAsCustomOutput(
        na  = s"Wr eClusterToKey ndexToKeyValDataset at ${keyValDatasetOutputPath}",
        DAL.wr eVers onedKeyVal(
          clusterToT et ndexSnapshotDataset,
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

// T  abstract class  s used to def ne para ters spec f c to UUA events.
abstract class UUABasedClusterToT et ndexGenerat onJob
    extends Engage ntEventBasedClusterToT et ndexGenerat onJob {
  // UUA Act on types and column na s
  val contr but ngAct onTypes: Seq[Str ng]
  val contr but ngAct onReferenceT et dColumn: Str ng = Conf g.act onT et dColumn
  val undoAct onTypes: Seq[Str ng]
  // Default undo t et  d  s sa  as t  act onT et d (e.g. for favs t se are t  sa  t et  d)
  val undoAct onReferenceT et dColumn: Str ng = Conf g.act onT et dColumn

  // Get t  str ng that represents t  l st of undo event  ds
  lazy val undoAct onTypesStr: Str ng = {
    // Populate t  act on type l st w h a placeholder act on  f  s empty
    val act onTypes =
       f (undoAct onTypes.nonEmpty) undoAct onTypes
      else Seq(Conf g.PlaceholderAct onType)
    convertAct onTypesSeqToStr ng(act onTypes)
  }

  overr de lazy val userT etEngage ntEventPa rTemplateVar able: Map[Str ng, Str ng] = {
    Map(
      "CONTR BUT NG_ACT ON_TYPES_STR" -> convertAct onTypesSeqToStr ng(contr but ngAct onTypes),
      "CONTR BUT NG_ACT ON_TWEET_ D_COLUMN" -> contr but ngAct onReferenceT et dColumn,
      "UNDO_ACT ON_TYPES_STR" -> undoAct onTypesStr,
      "UNDO_ACT ON_TWEET_ D_COLUMN" -> undoAct onReferenceT et dColumn
    )
  }

  /***
   *  Convert a l st of act ons to a str ng that could be eas ly used  n SQLs
   *  Example  nput: Seq("ServerT etFav", "Cl entT etFav")
   *          output: "ServerT etFav","Cl entT etFav"
   *  SQL use case: SELECT * FROM table WHERE act onType  N ("ServerT etFav","Cl entT etFav")
   */
  pr vate def convertAct onTypesSeqToStr ng(act onTypes: Seq[Str ng]): Str ng = {
    act onTypes.map(act on => f"""\"${act on}\"""").mkStr ng(",")
  }
}

abstract class AdsClusterToT et ndexGenerat onJob
    extends Engage ntEventBasedClusterToT et ndexGenerat onJob {
  // Ads contr but ng act on types - fav, cl ck, etc
  val contr but ngAct onTypes: Seq[ nt]

  overr de lazy val userT etEngage ntEventPa rTemplateVar able: Map[Str ng, Str ng] = {
    Map(
      "CONTR BUT NG_ACT ON_TYPES_STR" -> convertAct onTypesSeqToStr ng(contr but ngAct onTypes)
    )
  }
  pr vate def convertAct onTypesSeqToStr ng(act onTypes: Seq[ nt]): Str ng = {
    act onTypes.map(act on => f"""${act on}""").mkStr ng(",")
  }
}

object FavBasedClusterToT et ndexGenerat onAdhocJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etFav.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etUnfav.na )
  overr de val m n nteract onCount:  nt = 8
  overr de val m nFavCount:  nt = 8
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_fav_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.FavBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    FavBasedS mclustersClusterToT et ndexScalaDataset
}

object FavBasedClusterToT et ndexGenerat onBatchJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etFav.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etUnfav.na )
  overr de val m n nteract onCount:  nt = 8
  overr de val m nFavCount:  nt = 8
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_fav_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.FavBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    FavBasedS mclustersClusterToT et ndexScalaDataset
}

object V deoV ewBasedClusterToT et ndexGenerat onAdhocJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(
    Act onType.Cl entT etV deoPlayback50.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq.empty
  overr de val enable althAndV deoF lters: Boolean = true
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_v deo_v ew_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.V deoV ewBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    V deoV ewBasedS mclustersClusterToT et ndexScalaDataset
}

object V deoV ewBasedClusterToT et ndexGenerat onBatchJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(
    Act onType.Cl entT etV deoPlayback50.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq.empty
  overr de val enable althAndV deoF lters: Boolean = true
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_v deo_v ew_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.V deoV ewBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    V deoV ewBasedS mclustersClusterToT et ndexScalaDataset
}

object Ret etBasedClusterToT et ndexGenerat onAdhocJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etRet et.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etUnret et.na )
  overr de val undoAct onReferenceT et dColumn: Str ng = Conf g.ret etT et dColumn
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_ret et_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.Ret etBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    Ret etBasedS mclustersClusterToT et ndexScalaDataset
}

object Ret etBasedClusterToT et ndexGenerat onBatchJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etRet et.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etUnret et.na )
  overr de val undoAct onReferenceT et dColumn: Str ng = Conf g.ret etT et dColumn
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_ret et_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.Ret etBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    Ret etBasedS mclustersClusterToT et ndexScalaDataset
}

object ReplyBasedClusterToT et ndexGenerat onAdhocJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.comb nedUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etReply.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etDelete.na )
  overr de val undoAct onReferenceT et dColumn: Str ng = Conf g.replyT et dColumn
  overr de val m n nteract onCount:  nt = 8
  overr de val m nFavCount:  nt = 8
  overr de val m nEngage ntPerCluster:  nt = 3
  // Add supple ntal pos  ve s gnals to t  user t et engage nt event template
  //   bundle each reply s gnal w h a pos  ve s gnal (fav or ret et)
  val supple ntalPos  veS gnals: Seq[Str ng] =
    Seq(Act onType.ServerT etFav.na , Act onType.ServerT etRet et.na )
  overr de lazy val userT etEngage ntEventPa rTemplateVar able: Map[Str ng, Str ng] = {
    Map(
      "CONTR BUT NG_ACT ON_TYPE_STR" -> contr but ngAct onTypes. ad,
      "UNDO_ACT ON_TYPES_STR" -> undoAct onTypesStr,
      "UNDO_ACT ON_TWEET_ D_COLUMN" -> undoAct onReferenceT et dColumn,
      "SUPPLEMENTAL_ACT ON_TYPES_ENGAGEMENT_STR" -> bu ldAct onTypesEngage nt nd catorStr ng(
        supple ntalPos  veS gnals)
    )
  }
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_reply_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.ReplyBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    ReplyBasedS mclustersClusterToT et ndexScalaDataset
}

object ReplyBasedClusterToT et ndexGenerat onBatchJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.comb nedUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etReply.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etDelete.na )
  overr de val undoAct onReferenceT et dColumn: Str ng = Conf g.replyT et dColumn
  overr de val m n nteract onCount:  nt = 8
  overr de val m nFavCount:  nt = 8
  overr de val m nEngage ntPerCluster:  nt = 3
  // Add supple ntal pos  ve s gnals to t  user t et engage nt event template
  //   bundle each reply s gnal w h a pos  ve s gnal (fav or ret et)
  val supple ntalPos  veS gnals: Seq[Str ng] =
    Seq(Act onType.ServerT etFav.na , Act onType.ServerT etRet et.na )
  overr de lazy val userT etEngage ntEventPa rTemplateVar able: Map[Str ng, Str ng] = {
    Map(
      "CONTR BUT NG_ACT ON_TYPE_STR" -> contr but ngAct onTypes. ad,
      "UNDO_ACT ON_TYPES_STR" -> undoAct onTypesStr,
      "UNDO_ACT ON_TWEET_ D_COLUMN" -> undoAct onReferenceT et dColumn,
      "SUPPLEMENTAL_ACT ON_TYPES_ENGAGEMENT_STR" -> bu ldAct onTypesEngage nt nd catorStr ng(
        supple ntalPos  veS gnals)
    )
  }
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_reply_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.ReplyBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    ReplyBasedS mclustersClusterToT et ndexScalaDataset
}

object PushOpenBasedClusterToT et ndexGenerat onAdhocJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.Cl entNot f cat onOpen.na )
  overr de val contr but ngAct onReferenceT et dColumn: Str ng = Conf g.pushT et dColumn
  overr de val undoAct onTypes: Seq[Str ng] = Seq.empty
  overr de val m n nteract onCount = 1
  overr de val m nFavCount = 0
  overr de val enableFavClusterTopKT ets ntersect on = true
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_push_open_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.PushOpenBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    PushOpenBasedS mclustersClusterToT et ndexScalaDataset
}

object PushOpenBasedClusterToT et ndexGenerat onBatchJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.un f edUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.Cl entNot f cat onOpen.na )
  overr de val contr but ngAct onReferenceT et dColumn: Str ng = Conf g.pushT et dColumn
  overr de val undoAct onTypes: Seq[Str ng] = Seq.empty
  overr de val m n nteract onCount = 1
  overr de val m nFavCount = 0
  overr de val enableFavClusterTopKT ets ntersect on = true
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_push_open_based_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath = Conf g.PushOpenBasedClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    PushOpenBasedS mclustersClusterToT et ndexScalaDataset
}

object AdsFavBasedClusterToT et ndexGenerat onAdhocJob
    extends AdsClusterToT et ndexGenerat onJob {
  val  sAdhoc: Boolean = true
  val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val contr but ngAct onTypes: Seq[ nt] = AdsFavEngage ntType ds // fav
  overr de val t etEmbedd ngsHalfL fe:  nt = 345600000 // 4 days
  // T  earl est user t et engage nt event   cons der  s 7 days ago
  // T  t et could be older than 7 days
  overr de val maxT etAgeH s:  nt = 168 // 7 days
  overr de val m n nteract onCount:  nt = 3
  overr de val m nFavCount:  nt = 3
  overr de val m nEngage ntPerCluster:  nt = 2
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_ads_fav_based_cluster_to_t et_ ndex")
  val keyValDatasetOutputPath: Str ng = Conf g.AdsFavBasedClusterToT et ndexOutputPath
  val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = AdsFavBasedS mclustersClusterToT et ndexScalaDataset
  val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.adsUserT etAct onPa rGenerat onSQLPath
}
object AdsFavBasedClusterToT et ndexGenerat onBatchJob
    extends AdsClusterToT et ndexGenerat onJob {
  val  sAdhoc: Boolean = false
  val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val contr but ngAct onTypes: Seq[ nt] = AdsFavEngage ntType ds // fav
  overr de val t etEmbedd ngsHalfL fe:  nt = 345600000 // 4 days
  // T  earl est user t et engage nt event   cons der  s 7 days ago
  // T  t et could be older than 7 days
  overr de val maxT etAgeH s:  nt = 168 // 7 days
  overr de val m n nteract onCount:  nt = 3
  overr de val m nFavCount:  nt = 3
  overr de val m nEngage ntPerCluster:  nt = 2
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_ads_fav_based_cluster_to_t et_ ndex")
  val keyValDatasetOutputPath: Str ng = Conf g.AdsFavBasedClusterToT et ndexOutputPath
  val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = AdsFavBasedS mclustersClusterToT et ndexScalaDataset
  val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.adsUserT etAct onPa rGenerat onSQLPath
}

object AdsFavCl ckBasedClusterToT et ndexGenerat onAdhocJob
    extends AdsClusterToT et ndexGenerat onJob {
  val  sAdhoc: Boolean = true
  val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val contr but ngAct onTypes: Seq[ nt] =
    AdsFavEngage ntType ds ++ AdsCl ckEngage ntType ds // fav + cl ck
  overr de val t etEmbedd ngsHalfL fe:  nt = 604800000 // 7 days
  // T  earl est user t et engage nt event   cons der  s 21 days ago
  // T  t et could be older than 21 days
  overr de val maxT etAgeH s:  nt = 504 // 21 days
  overr de val m n nteract onCount:  nt = 3
  overr de val m nFavCount:  nt = 3
  overr de val m nEngage ntPerCluster:  nt = 2
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_ads_fav_cl ck_ sbased_cluster_to_t et_ ndex")
  val keyValDatasetOutputPath: Str ng = Conf g.AdsFavCl ckBasedClusterToT et ndexOutputPath
  val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = AdsFavCl ckBasedS mclustersClusterToT et ndexScalaDataset
  val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.adsUserT etAct onPa rGenerat onSQLPath
}

object AdsFavCl ckBasedClusterToT et ndexGenerat onBatchJob
    extends AdsClusterToT et ndexGenerat onJob {
  val  sAdhoc: Boolean = false
  val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val contr but ngAct onTypes: Seq[ nt] =
    AdsFavEngage ntType ds ++ AdsCl ckEngage ntType ds // fav + cl ck
  overr de val t etEmbedd ngsHalfL fe:  nt = 604800000 // 7 days
  // T  earl est user t et engage nt event   cons der  s 21 days ago
  // T  t et could be older than 21 days
  overr de val maxT etAgeH s:  nt = 504 // 21 days
  overr de val m n nteract onCount:  nt = 3
  overr de val m nFavCount:  nt = 3
  overr de val m nEngage ntPerCluster:  nt = 2
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_ads_fav_cl ck_based_cluster_to_t et_ ndex")
  val keyValDatasetOutputPath: Str ng = Conf g.AdsFavCl ckBasedClusterToT et ndexOutputPath
  val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] = AdsFavCl ckBasedS mclustersClusterToT et ndexScalaDataset
  val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.adsUserT etAct onPa rGenerat onSQLPath
}

object FavBasedEvergreenContentClusterToT et ndexGenerat onAdhocJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.evergreenContentUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etFav.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etUnfav.na )
  overr de val t etEmbedd ngsHalfL fe:  nt = 57600000 // 16 h s
  overr de val maxT etAgeH s:  nt = 48 // 2 days
  overr de val m n nteract onCount:  nt = 8
  overr de val m nFavCount:  nt = 0
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_fav_based_evergreen_content_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath =
    Conf g.FavBasedEvergreenContentClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    FavBasedEvergreenContentS mclustersClusterToT et ndexScalaDataset
}

object FavBasedEvergreenContentClusterToT et ndexGenerat onBatchJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.evergreenContentUserT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etFav.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etUnfav.na )
  overr de val t etEmbedd ngsHalfL fe:  nt = 57600000 // 16 h s
  overr de val maxT etAgeH s:  nt = 48 // 2 days
  overr de val m n nteract onCount:  nt = 8
  overr de val m nFavCount:  nt = 0
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_fav_based_evergreen_content_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath =
    Conf g.FavBasedEvergreenContentClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    FavBasedEvergreenContentS mclustersClusterToT et ndexScalaDataset
}

object FavBasedV deoClusterToT et ndexGenerat onAdhocJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = true
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.favBasedV deoT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etFav.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etUnfav.na )
  overr de val m n nteract onCount:  nt = 8
  overr de val m nFavCount:  nt = 0
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-recos-ml-prod",
      "s mclusters",
      "s mclusters_fav_based_v deo_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath =
    Conf g.FavBasedV deoClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    FavBasedV deoS mclustersClusterToT et ndexScalaDataset
}

object FavBasedV deoClusterToT et ndexGenerat onBatchJob
    extends UUABasedClusterToT et ndexGenerat onJob {
  overr de val  sAdhoc = false
  overr de val getConsu rEmbedd ngsSQLFunc = get nterested n2020SQL
  overr de val userT etEngage ntEventPa rSqlPath: Str ng =
    Conf g.favBasedV deoT etAct onPa rGenerat onSQLPath
  overr de val contr but ngAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etFav.na )
  overr de val undoAct onTypes: Seq[Str ng] = Seq(Act onType.ServerT etUnfav.na )
  overr de val m n nteract onCount:  nt = 8
  overr de val m nFavCount:  nt = 0
  overr de val outputTable =
    BQTableDeta ls(
      "twttr-bq-cassowary-prod",
      "user",
      "s mclusters_fav_based_v deo_cluster_to_t et_ ndex")
  overr de val keyValDatasetOutputPath =
    Conf g.FavBasedV deoClusterToT et ndexOutputPath
  overr de val clusterToT et ndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullCluster d, TopKT etsW hScores]
  ] =
    FavBasedV deoS mclustersClusterToT et ndexScalaDataset
}
