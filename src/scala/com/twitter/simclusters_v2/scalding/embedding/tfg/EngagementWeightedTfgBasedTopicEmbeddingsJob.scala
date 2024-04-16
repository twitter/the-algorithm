package com.tw ter.s mclusters_v2.scald ng.embedd ng.tfg

 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDatasetBase
 mport com.tw ter.ml.ap .DataSetP pe
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.typed.UnsortedGrouped
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.Wr eExtens on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Country
 mport com.tw ter.s mclusters_v2.common.Language
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.hdfs_s ces.FavTfgTop cEmbedd ngs2020ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserTop c  ghtedEmbedd ngScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserTop c  ghtedEmbedd ngParquetScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on._
 mport com.tw ter.t  l nes.pred ct on.common.aggregates.T  l nesAggregat onConf g
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.DateRangeExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 * Jobs to generate Fav-based engage nt   ghted Top c-Follow-Graph (TFG) top c embedd ngs
 * T  job uses fav based TFG embedd ngs and fav based engage nt to produce a new embedd ng
 */

/**
 * ./bazel bundle ...
 * scald ng workflow upload --jobs src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_  ghted_user_top c_tfg_embedd ngs_adhoc_job --autoplay
 */
object Engage nt  ghtedTfgBasedTop cEmbedd ngsAdhocJob
    extends AdhocExecut onApp
    w h Engage nt  ghtedTfgBasedTop cEmbedd ngsBaseJob {
  overr de val outputByFav =
    "/user/cassowary/adhoc/manhattan_sequence_f les/s mclusters_v2_embedd ng/user_tfgembedd ng/by_fav"
  overr de val parquetOutputByFav =
    "/user/cassowary/adhoc/processed/s mclusters_v2_embedd ng/user_tfgembedd ng/by_fav/snapshot"
}

/**
 * ./bazel bundle ...
 * scald ng workflow upload --jobs src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_  ghted_user_top c_tfg_embedd ngs_batch_job --autoplay
 */
object Engage nt  ghtedTfgBasedTop cEmbedd ngsSc duleJob
    extends Sc duledExecut onApp
    w h Engage nt  ghtedTfgBasedTop cEmbedd ngsBaseJob {
  overr de val f rstT  : R chDate = R chDate("2021-10-03")
  overr de val batch ncre nt: Durat on = Days(1)
  overr de val outputByFav =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_embedd ng/user_tfgembedd ng/by_fav"
  overr de val parquetOutputByFav =
    "/user/cassowary/processed/s mclusters_v2_embedd ng/user_tfgembedd ng/by_fav/snapshot"
}

tra  Engage nt  ghtedTfgBasedTop cEmbedd ngsBaseJob extends DateRangeExecut onApp {

  val outputByFav: Str ng
  val parquetOutputByFav: Str ng

  //root path to read aggregate data
  pr vate val aggregateFeatureRootPath =
    "/atla/proc2/user/t  l nes/processed/aggregates_v2"

  pr vate val topKTop csToKeep = 100

  pr vate val favCont nuousFeature = new Cont nuous(
    "user_top c_aggregate.pa r.recap.engage nt. s_favor ed.any_feature.50.days.count")

  pr vate val parquetDataS ce: SnapshotDALDatasetBase[UserTop c  ghtedEmbedd ng] =
    UserTop c  ghtedEmbedd ngParquetScalaDataset

  def sortedTake[K](m: Map[K, Double], keysToKeep:  nt): Map[K, Double] = {
    m.toSeq.sortBy { case (k, v) => -v }.take(keysToKeep).toMap
  }

  case class UserTop cEngage nt(
    user d: Long,
    top c d: Long,
    language: Str ng,
    country: Str ng, //f eld  s not used
    favCount: Double) {
    val userLanguageGroup: (Long, Str ng) = (user d, language)
  }

  def prepareUserToTop cEmbedd ng(
    favTfgTop cEmbedd ngs: TypedP pe[(Long, Str ng, S mClustersEmbedd ng)],
    userTop cEngage ntCount: TypedP pe[UserTop cEngage nt]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[((Long, Str ng), Map[ nt, Double])] = {
    val userTfgEmbedd ngsStat = Stat("User Tfg Embedd ngs Count")
    val userTop cTopKEngage ntStat = Stat("User Top c Top K engage nt count")
    val userEngage ntStat = Stat("User engage nt count")
    val tfgEmbedd ngsStat = Stat("TFG Embedd ng Map count")

    //get only top K top cs
    val userTopKTop cEngage ntCount: TypedP pe[UserTop cEngage nt] = userTop cEngage ntCount
      .groupBy(_.userLanguageGroup)
      .w hReducers(499)
      .w hDescr pt on("select topK top cs")
      .sortedReverseTake(topKTop csToKeep)(Order ng.by(_.favCount))
      .values
      .flatten

    //(user d, language), totalCount
    val userLanguageEngage ntCount: UnsortedGrouped[(Long, Str ng), Double] =
      userTopKTop cEngage ntCount
        .collect {
          case UserTop cEngage nt(user d, top c d, language, country, favCount) =>
            userTop cTopKEngage ntStat. nc()
            ((user d, language), favCount)
        }.sumByKey
        .w hReducers(499)
        .w hDescr pt on("fav count by user")

    //(top c d, language), (user d, fav  ght)
    val top cUserW hNormal zed  ghts: TypedP pe[((Long, Str ng), (Long, Double))] =
      userTopKTop cEngage ntCount
        .groupBy(_.userLanguageGroup)
        .jo n(userLanguageEngage ntCount)
        .w hReducers(499)
        .w hDescr pt on("jo n userTop c and user Engage ntCount")
        .collect {
          case ((user d, language), (engage ntData, totalCount)) =>
            userEngage ntStat. nc()
            (
              (engage ntData.top c d, engage ntData.language),
              (user d, engage ntData.favCount / totalCount)
            )
        }

    // (top c d, language), embedd ngMap
    val tfgEmbedd ngsMap: TypedP pe[((Long, Str ng), Map[ nt, Double])] = favTfgTop cEmbedd ngs
      .map {
        case (top c d, language, embedd ng) =>
          tfgEmbedd ngsStat. nc()
          ((top c d, language), embedd ng.embedd ng.map(a => a.cluster d -> a.score).toMap)
      }
      .w hDescr pt on("covert s m cluster embedd ng to map")

    // (user d, language), clusters
    val newUserTfgEmbedd ng = top cUserW hNormal zed  ghts
      .jo n(tfgEmbedd ngsMap)
      .w hReducers(799)
      .w hDescr pt on("jo n user | top c | fav  ght * embedd ng")
      .collect {
        case ((top c d, language), ((user d, fav  ght), embedd ngMap)) =>
          userTfgEmbedd ngsStat. nc()
          ((user d, language), embedd ngMap.mapValues(_ * fav  ght))
      }
      .sumByKey
      .w hReducers(799)
      .w hDescr pt on("aggregate embedd ng by user")

    newUserTfgEmbedd ng.toTypedP pe
  }

  def wr eOutput(
    newUserTfgEmbedd ng: TypedP pe[((Long, Str ng), Map[ nt, Double])],
    outputPath: Str ng,
    parquetOutputPath: Str ng,
    modelVers on: Str ng
  )(
     mpl c  un que D: Un que D,
    dateRange: DateRange
  ): Execut on[Un ] = {
    val outputRecordStat = Stat("output record count")
    val output = newUserTfgEmbedd ng
      .map {
        //language has been purposely  gnored because t  ent re log c  s based on t  fact that
        //user  s mapped to a language.  n future  f a user  s mapped to mult ple languages t n
        //t  f nal output needs to be keyed on (user d, language)
        case ((user d, language), embedd ngMap) =>
          outputRecordStat. nc()
          val clusterScores = embedd ngMap.map {
            case (cluster d, score) =>
              cluster d -> UserTo nterested nClusterScores(favScore = So (score))
          }
          KeyVal(user d, ClustersUser s nterested n(modelVers on, clusterScores))
      }

    val keyValExec = output
      .w hDescr pt on("wr e output keyval dataset")
      .wr eDALVers onedKeyValExecut on(
        UserTop c  ghtedEmbedd ngScalaDataset,
        D.Suff x(outputPath))

    val parquetExec = newUserTfgEmbedd ng
      .map {
        case ((user d, language), embedd ngMap) =>
          val clusterScores = embedd ngMap.map {
            case (cluster d, score) => ClustersScore(cluster d, score)
          }
          UserTop c  ghtedEmbedd ng(user d, clusterScores.toSeq)
      }
      .w hDescr pt on("wr e output parquet dataset")
      .wr eDALSnapshotExecut on(
        parquetDataS ce,
        D.Da ly,
        D.Suff x(parquetOutputPath),
        D.Parquet,
        dateRange.end
      )
    Execut on.z p(keyValExec, parquetExec).un 
  }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val end = dateRange.start
    val start = end - Days(21)
    val featureDateRange = DateRange(start, end - M ll secs(1))
    val outputPath = args.getOrElse("output_path", outputByFav)
    val parquetOutputPath = args.getOrElse("parquet_output_path", parquetOutputByFav)
    val modelVers on = ModelVers ons.Model20M145K2020

    //def ne stats counter
    val favTfgTop cEmbedd ngsStat = Stat("FavTfgTop cEmbedd ngs")
    val userTop cEngage ntStat = Stat("UserTop cEngage nt")
    val userTop csStat = Stat("UserTop cs")
    val userLangStat = Stat("UserLanguage")

    //get fav based tfg embedd ngs
    //top c can have d fferent languages and t  clusters w ll be d fferent
    //current log c  s to f lter based on user language
    // top c d, lang, embedd ng
    val favTfgTop cEmbedd ngs: TypedP pe[(Long, Str ng, S mClustersEmbedd ng)] = DAL
      .readMostRecentSnapshot(FavTfgTop cEmbedd ngs2020ScalaDataset, featureDateRange)
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .collect {
        case KeyVal(
              S mClustersEmbedd ng d(
                embedType,
                modelVers on,
                 nternal d.LocaleEnt y d(LocaleEnt y d(ent y d, language))),
              embedd ng) =>
          favTfgTop cEmbedd ngsStat. nc()
          (ent y d, language, embedd ng)
      }

    /*
     deally,  f t  t  l ne aggregate fra work prov ded data w h breakdown by language,
      could have been jo ned w h (top c, language) embedd ng. S nce,    s not poss ble
      fetch t  language of t  user from ot r s ces.
    T  returns language for t  user so that   could be jo ned w h (top c, language) embedd ng.
    `userS ce` returns 1 language per user
    ` nferredUserConsu dLanguageS ce` returns mult ple languages w h conf dence values
     */
    val userLangS ce = ExternalDataS ces.userS ce
      .map {
        case (user d, (country, language)) =>
          userLangStat. nc()
          (user d, (language, country))
      }

    //get user d, top c d, favcount as aggregated dataset
    //currently t re  s no way to get language breakdown from t  t  l ne aggregate fra work.
    val userTop cEngage ntP pe: DataSetP pe = AggregatesV2MostRecentFeatureS ce(
      rootPath = aggregateFeatureRootPath,
      storeNa  = "user_top c_aggregates",
      aggregates =
        Set(T  l nesAggregat onConf g.userTop cAggregates).flatMap(_.bu ldTypedAggregateGroups()),
    ).read

    val userTop cEngage ntCount = userTop cEngage ntP pe.records
      .flatMap { record =>
        val sR chDataRecord = SR chDataRecord(record)
        val user d: Long = sR chDataRecord.getFeatureValue(SharedFeatures.USER_ D)
        val top c d: Long = sR chDataRecord.getFeatureValue(T  l nesSharedFeatures.TOP C_ D)
        val favCount: Double = sR chDataRecord
          .getFeatureValueOpt(favCont nuousFeature).map(_.toDouble).getOrElse(0.0)
        userTop cEngage ntStat. nc()
         f (favCount > 0) {
          L st((user d, (top c d, favCount)))
        } else None
      }.jo n(userLangS ce)
      .collect {
        case (user d, ((top c d, favCount), (language, country))) =>
          userTop csStat. nc()
          UserTop cEngage nt(user d, top c d, language, country, favCount)
      }
      .w hDescr pt on("User Top c aggregated favcount")

    // comb ne user, top cs, top c_embedd ngs
    // and take   ghted aggregate of t  tfg embedd ng
    val newUserTfgEmbedd ng =
      prepareUserToTop cEmbedd ng(favTfgTop cEmbedd ngs, userTop cEngage ntCount)

    wr eOutput(newUserTfgEmbedd ng, outputPath, parquetOutputPath, modelVers on)

  }

}
