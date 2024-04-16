package com.tw ter.s mclusters_v2.scald ng.mbcg

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cortex.deepb rd.runt  .pred ct on_eng ne.TensorflowPred ct onEng neConf g
 mport com.tw ter.cortex.ml.embedd ngs.common.UserK nd
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.ml.ap .FeatureUt l
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .embedd ng.Embedd ng
 mport com.tw ter.ml.ap .thr ftscala
 mport com.tw ter.ml.ap .thr ftscala.{GeneralTensor => Thr ftGeneralTensor}
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.ml.ap .ut l.ScalaToJavaDataRecordConvers ons
 mport com.tw ter.ml.featurestore.l b.embedd ng.Embedd ngW hEnt y
 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateParser
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossDC
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut onArgs
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchDescr pt on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchF rstT  
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Batch ncre nt
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchW dth
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Tw terSc duledExecut onApp
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.ExploreMbcgUserEmbedd ngsKvScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.twml.runt  .scald ng.TensorflowBatchPred ctor
 mport com.tw ter.twml.runt  .scald ng.TensorflowBatchPred ctor.Scald ngThread ngConf g
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport com.tw ter.users ce.snapshot.flat.thr ftscala.FlatUser
 mport java.ut l.T  Zone

/*
T  class does t  follow ng:
1) Get user   APE S mcluster features that use LogFav scores
2) F lter t m down to users whose accounts are not deact vated or suspended
3) Convert t  rema n ng user S mclusters  nto DataRecords us ng UserS mclusterRecordAdapter
4) Run  nference us ng a TF model exported w h a DataRecord compat ble serv ng s gnature
5) Wr e to MH us ng a KeyVal format
 */
tra  UserEmbedd ngGenerat onTra  {
   mpl c  val tz: T  Zone = DateOps.UTC
   mpl c  val dp: DateParser = DateParser.default
   mpl c  val updateH s = 12

  pr vate val  nputNodeNa  = "request:0"
  pr vate val outputNodeNa  = "response:0"
  pr vate val funct onS gnatureNa  = "serve"
  pr vate val pred ct onRequestT  out = 5.seconds
  pr vate val   APEHdfsPath: Str ng =
    "/atla/proc3/user/cassowary/manhattan_sequence_f les/ nterested_ n_from_ape/Model20m145k2020"

  pr vate val DEFAULT_F2V_VECTOR: Embedd ng[Float] = Embedd ng(Array.f ll[Float](200)(0.0f))

  def getPred ct onEng ne(modelNa : Str ng, modelPath: Str ng): TensorflowBatchPred ctor = {
    val conf g = TensorflowPred ct onEng neConf g(
      modelNa  = modelNa ,
      modelS ce = modelPath,
      thread ngConf g = So (Scald ngThread ngConf g),
      default nputNode =  nputNodeNa ,
      defaultOutputNode = outputNodeNa ,
      funct onS gnatureNa  = funct onS gnatureNa ,
      statsRece ver = NullStatsRece ver
    )
    TensorflowBatchPred ctor(conf g, pred ct onRequestT  out)
  }

  def getEmbedd ngW hEnt y(userEmbedd ngTensor: Thr ftGeneralTensor, user d: Long) = {
    userEmbedd ngTensor match {
      case Thr ftGeneralTensor.RawTypedTensor(rawTensor) =>
        val embedd ng =
          thr ftscala.Embedd ng(So (rawTensor))
        KeyVal(user d, embedd ng)
      case _ => throw new  llegalArgu ntExcept on("tensor  s wrong type!")
    }
  }

  def wr eUserEmbedd ng(
    result: TypedP pe[KeyVal[Long, thr ftscala.Embedd ng]],
    args: Args
  ): Execut on[Un ] = {
    result.wr eDALVers onedKeyValExecut on(
      ExploreMbcgUserEmbedd ngsKvScalaDataset,
      D.Suff x(
        args.getOrElse("kvs_output_path", "/user/cassowary/explore_mbcg/user_kvs_store/test")
      )
    )
  }

  def getUserS mclusterFeatures(
    args: Args
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(Long, ClustersUser s nterested n)] = {
    val userS mclusterEmbedd ngTypedP pe = TypedP pe
      .from(AdhocKeyValS ces. nterested nS ce(  APEHdfsPath))
      .collect {
        case (
              user d,
                APE: ClustersUser s nterested n
            ) =>
          (user d.toLong,   APE)
      }

    userS mclusterEmbedd ngTypedP pe
  }

  def getUserS ce()( mpl c  dateRange: DateRange): TypedP pe[FlatUser] = {
    val userS ce =
      DAL
        .readMostRecentSnapshotNoOlderThan(Users ceFlatScalaDataset, Days(7))
        .w hRemoteReadPol cy(AllowCrossDC)
        .toTypedP pe

    userS ce
  }

  def run(args: Args)( mpl c  dateRange: DateRange,  d: Un que D) = {
    val userS mclusterDataset = getUserS mclusterFeatures(args)
    val userS ceDataset = getUserS ce()

    val  nputEmbedd ngFormat = UserK nd.parser
      .getEmbedd ngFormat(args, "f2v_ nput", So (dateRange.prepend(Days(14))))
    val f2vConsu rEmbedd ngs =  nputEmbedd ngFormat.getEmbedd ngs
      .map {
        case Embedd ngW hEnt y(user d, embedd ng) => (user d.user d, embedd ng)
      }

    val f lteredUserP pe = userS mclusterDataset
      .groupBy(_._1)
      .jo n(userS ceDataset.groupBy(_. d.getOrElse(-1L)))
      .map {
        case (user d, ((_, s mclusterEmbedd ng), user nfo)) =>
          (user d, s mclusterEmbedd ng, user nfo)
      }
      .f lter {
        case (_, _, user nfo) =>
          !user nfo.deact vated.conta ns(true) && !user nfo.suspended
            .conta ns(true)
      }
      .map {
        case (user d, s mclusterEmbedd ng, _) =>
          (user d, s mclusterEmbedd ng)
      }

    val dataRecordsP pe = f lteredUserP pe
      .groupBy(_._1)
      .leftJo n(f2vConsu rEmbedd ngs.groupBy(_._1))
      .values
      .map {
        case ((user d1, s mclusterEmbedd ng), So ((user d2, f2vEmbedd ng))) =>
          UserS mclusterRecordAdapter.adaptToDataRecord(
            (user d1, s mclusterEmbedd ng, f2vEmbedd ng))
        case ((user d, s mclusterEmbedd ng), None) =>
          UserS mclusterRecordAdapter.adaptToDataRecord(
            (user d, s mclusterEmbedd ng, DEFAULT_F2V_VECTOR))
      }

    val modelPath = args.getOrElse("model_path", "")
    val batchPred ctor = getPred ct onEng ne(modelNa  = "t et_model", modelPath = modelPath)
    val user dFeature = SharedFeatures.USER_ D
    val userEmbedd ngNa  = args.getOrElse("user_embedd ng_na ", "output")

    val outputP pe = batchPred ctor.pred ct(dataRecordsP pe).map {
      case (or g nalDataRecord, pred ctedDataRecord) =>
        val user d = or g nalDataRecord.getFeatureValue(user dFeature)
        val scalaPred ctedDataRecord =
          ScalaToJavaDataRecordConvers ons.javaDataRecord2ScalaDataRecord(pred ctedDataRecord)
        val userEmbedd ngTensor =
          scalaPred ctedDataRecord.tensors.get(FeatureUt l.feature dForNa (userEmbedd ngNa ))
        val userEmbedd ngW hEnt y = getEmbedd ngW hEnt y(userEmbedd ngTensor, user d)
        userEmbedd ngW hEnt y
    }

    Ut l.pr ntCounters(wr eUserEmbedd ng(outputP pe, args))
  }
}

object UserEmbedd ngGenerat onAdhocJob
    extends Tw terExecut onApp
    w h UserEmbedd ngGenerat onTra  {

  overr de def job: Execut on[Un ] =
    Execut on.w h d {  mpl c  u d =>
      Execut on.w hArgs { args =>
         mpl c  val dateRange: DateRange = DateRange.parse(args.l st("dateRange"))
        run(args)
      }
    }
}

object UserEmbedd ngGenerat onBatchJob
    extends Tw terSc duledExecut onApp
    w h UserEmbedd ngGenerat onTra  {

  overr de def sc duledJob: Execut on[Un ] =
    Execut on.w h d {  mpl c  u d =>
      Execut on.w hArgs { args =>
         mpl c  val tz: T  Zone = DateOps.UTC
        val batchF rstT   = BatchF rstT  (R chDate("2021-12-04")(tz, DateParser.default))
        val analyt csArgs = Analyt csBatchExecut onArgs(
          batchDesc = BatchDescr pt on(getClass.getNa ),
          f rstT   = batchF rstT  ,
          batch ncre nt = Batch ncre nt(H s(updateH s)),
          batchW dth = So (BatchW dth(H s(updateH s)))
        )

        Analyt csBatchExecut on(analyt csArgs) {  mpl c  dateRange =>
          run(args)
        }
      }
    }
}

object UserEmbedd ngGenerat onBatchJobAlternate
    extends Tw terSc duledExecut onApp
    w h UserEmbedd ngGenerat onTra  {

  overr de def sc duledJob: Execut on[Un ] =
    Execut on.w h d {  mpl c  u d =>
      Execut on.w hArgs { args =>
         mpl c  val tz: T  Zone = DateOps.UTC
        val batchF rstT   = BatchF rstT  (R chDate("2022-03-28")(tz, DateParser.default))
        val analyt csArgs = Analyt csBatchExecut onArgs(
          batchDesc = BatchDescr pt on(getClass.getNa ),
          f rstT   = batchF rstT  ,
          batch ncre nt = Batch ncre nt(H s(updateH s)),
          batchW dth = So (BatchW dth(H s(updateH s)))
        )

        Analyt csBatchExecut on(analyt csArgs) {  mpl c  dateRange =>
          run(args)
        }
      }
    }
}

object UserEmbedd ngGenerat onBatchJobExper  ntal
    extends Tw terSc duledExecut onApp
    w h UserEmbedd ngGenerat onTra  {

  overr de def sc duledJob: Execut on[Un ] =
    Execut on.w h d {  mpl c  u d =>
      Execut on.w hArgs { args =>
         mpl c  val tz: T  Zone = DateOps.UTC
        val batchF rstT   = BatchF rstT  (R chDate("2021-12-12")(tz, DateParser.default))
        val analyt csArgs = Analyt csBatchExecut onArgs(
          batchDesc = BatchDescr pt on(getClass.getNa ),
          f rstT   = batchF rstT  ,
          batch ncre nt = Batch ncre nt(H s(updateH s)),
          batchW dth = So (BatchW dth(H s(updateH s)))
        )

        Analyt csBatchExecut on(analyt csArgs) {  mpl c  dateRange =>
          run(args)
        }
      }
    }
}
