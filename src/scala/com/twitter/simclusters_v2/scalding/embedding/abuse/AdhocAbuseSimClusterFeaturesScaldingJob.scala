package com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.dataset.DAL.DALS ceBu lderExtens on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossDC
 mport com.tw ter.search.common.features.ExternalT etFeature
 mport com.tw ter.search.common.features.SearchContextFeature
 mport com.tw ter.search.t et_rank ng.scald ng.datasets.T etEngage ntRawTra n ngDataDa lyJavaDataset
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocAbuseS mclusterFeaturesScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.SparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.DataS ces.NumBlocksP95
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.DataS ces.getFlockBlocksSparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.DataS ces.getUser nterested nSparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.User d
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.CassowaryJob
 mport java.ut l.T  Zone

object AdhocAbuseS mClusterFeatureKeys {
  val AbuseAuthorSearchKey = "abuseAuthorSearch"
  val AbuseUserSearchKey = "abuseUserSearch"
  val  mpress onUserSearchKey = " mpress onUserSearch"
  val  mpress onAuthorSearchKey = " mpress onAuthorSearch"
  val FlockBlocksAuthorKey = "blocksAuthorFlockDataset"
  val FlockBlocksUserKey = "blocksUserFlockDataset"
  val FavScoresAuthorKey = "favsAuthorFromFavGraph"
  val FavScoresUserKey = "favsUserFromFavGraph"
}

/**
 * Adhoc job that  s st ll  n develop nt. T  job bu lds features that are  ant to be useful for
 * search.
 *
 * Features are bu lt from ex st ng S mCluster representat ons and t   nteract on graphs.
 *
 * Example command:
 * scald ng remote run \
 * --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/abuse:abuse-adhoc \
 * --ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.AdhocAbuseS mClusterFeaturesScald ngJob \
 * --subm ter  hadoopnest1.atla.tw ter.com --user cassowary \
 * --hadoop-propert es "mapreduce.job.user.classpath.f rst=true" -- \
 * --hdfs --date 2020/11/24 2020/12/14 --part  onNa  second_run --dalEnv ron nt Prod
 */
object AdhocAbuseS mClusterFeaturesScald ngJob extends AdhocExecut onApp w h CassowaryJob {
  overr de def jobNa : Str ng = "AdhocAbuseScald ngJob"

   mport AdhocAbuseS mClusterFeatureKeys._

  val t etAuthorFeature = new Feature.D screte(ExternalT etFeature.TWEET_AUTHOR_ D.getNa )
  val searc r dFeature = new Feature.D screte(SearchContextFeature.SEARCHER_ D.getNa )
  val  sReportedFeature = new Feature.B nary(ExternalT etFeature. S_REPORTED.getNa )
  val HalfL fe nDaysForFavScore = 100

  pr vate val outputPathThr ft: Str ng = Embedd ngUt l.getHdfsPath(
     sAdhoc = false,
     sManhattanKeyVal = false,
    modelVers on = ModelVers on.Model20m145kUpdated,
    pathSuff x = "abuse_s mcluster_features"
  )

  def searchDataRecords(
  )(
     mpl c  dateRange: DateRange,
    mode: Mode
  ) = {
    DAL
      .read(T etEngage ntRawTra n ngDataDa lyJavaDataset)
      .w hRemoteReadPol cy(AllowCrossDC)
      .toDataSetP pe
      .records
  }

  def abuse nteract onSearchGraph(
  )(
     mpl c  dateRange: DateRange,
    mode: Mode
  ): SparseMatr x[User d, User d, Double] = {
    val abuseMatr xEntr es = searchDataRecords()
      .flatMap { dataRecord =>
        val sDataRecord = SR chDataRecord(dataRecord)
        val author dOpt on = sDataRecord.getFeatureValueOpt(t etAuthorFeature)
        val user dOpt on = sDataRecord.getFeatureValueOpt(searc r dFeature)
        val  sReportedOpt on = sDataRecord.getFeatureValueOpt( sReportedFeature)

        for {
           sReported <-  sReportedOpt on  f  sReported
          author d <- author dOpt on  f author d != 0
          user d <- user dOpt on  f user d != 0
        } y eld {
          (user d: User d, author d: User d, 1.0)
        }
      }
    SparseMatr x.apply[User d, User d, Double](abuseMatr xEntr es)
  }

  def  mpress on nteract onSearchGraph(
  )(
     mpl c  dateRange: DateRange,
    mode: Mode
  ): SparseMatr x[User d, User d, Double] = {
    val  mpress onMatr xEntr es = searchDataRecords
      .flatMap { dataRecord =>
        val sDataRecord = SR chDataRecord(dataRecord)
        val author dOpt on = sDataRecord.getFeatureValueOpt(t etAuthorFeature)
        val user dOpt on = sDataRecord.getFeatureValueOpt(searc r dFeature)

        for {
          author d <- author dOpt on  f author d != 0
          user d <- user dOpt on  f user d != 0
        } y eld {
          (user d: User d, author d: User d, 1.0)
        }
      }
    SparseMatr x.apply[User d, User d, Double]( mpress onMatr xEntr es)
  }

  case class S ngleS deScores(
    un althyConsu rClusterScores: TypedP pe[(User d, S mClustersEmbedd ng)],
    un althyAuthorClusterScores: TypedP pe[(User d, S mClustersEmbedd ng)],
     althyConsu rClusterScores: TypedP pe[(User d, S mClustersEmbedd ng)],
     althyAuthorClusterScores: TypedP pe[(User d, S mClustersEmbedd ng)])

  def bu ldSearchAbuseScores(
    normal zedS mClusterMatr x: SparseMatr x[User d, Cluster d, Double],
    un althyGraph: SparseMatr x[User d, User d, Double],
     althyGraph: SparseMatr x[User d, User d, Double]
  ): S ngleS deScores = {
    S ngleS deScores(
      un althyConsu rClusterScores = S ngleS de nteract onTransformat on
        .clusterScoresFromGraphs(normal zedS mClusterMatr x, un althyGraph),
      un althyAuthorClusterScores = S ngleS de nteract onTransformat on
        .clusterScoresFromGraphs(normal zedS mClusterMatr x, un althyGraph.transpose),
       althyConsu rClusterScores = S ngleS de nteract onTransformat on
        .clusterScoresFromGraphs(normal zedS mClusterMatr x,  althyGraph),
       althyAuthorClusterScores = S ngleS de nteract onTransformat on
        .clusterScoresFromGraphs(normal zedS mClusterMatr x,  althyGraph.transpose)
    )
  }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    Execut on.getMode.flatMap {  mpl c  mode =>
      val normal zedS mClusterMatr x = getUser nterested nSparseMatr x.rowL2Normal ze

      val abuseSearchGraph = abuse nteract onSearchGraph()
      val  mpress onSearchGraph =  mpress on nteract onSearchGraph()

      val searchAbuseScores = bu ldSearchAbuseScores(
        normal zedS mClusterMatr x,
        un althyGraph = abuseSearchGraph,
         althyGraph =  mpress onSearchGraph)

      // Step 2a: Read FlockBlocks for un althy  nteract ons and user-user-fav for  althy  nteract ons
      val flockBlocksSparseGraph =
        getFlockBlocksSparseMatr x(NumBlocksP95, dateRange.prepend(Years(1)))

      val favSparseGraph = SparseMatr x.apply[User d, User d, Double](
        ExternalDataS ces.getFavEdges(HalfL fe nDaysForFavScore))

      val blocksAbuseScores = bu ldSearchAbuseScores(
        normal zedS mClusterMatr x,
        un althyGraph = flockBlocksSparseGraph,
         althyGraph = favSparseGraph
      )

      // Step 3. Comb ne all scores from d fferent s ces for users
      val pa redScores = S ngleS de nteract onTransformat on.pa rScores(
        Map(
          // User cluster scores bu lt from t  search abuse reports graph
          AbuseUserSearchKey -> searchAbuseScores.un althyConsu rClusterScores,
          // Author cluster scores bu lt from t  search abuse reports graph
          AbuseAuthorSearchKey -> searchAbuseScores.un althyAuthorClusterScores,
          // User cluster scores bu lt from t  search  mpress on graph
           mpress onUserSearchKey -> searchAbuseScores. althyConsu rClusterScores,
          // Author cluster scores bu lt from t  search  mpress on graph
           mpress onAuthorSearchKey -> searchAbuseScores. althyAuthorClusterScores,
          // User cluster scores bu lt from flock blocks graph
          FlockBlocksUserKey -> blocksAbuseScores.un althyConsu rClusterScores,
          // Author cluster scores bu lt from t  flock blocks graph
          FlockBlocksAuthorKey -> blocksAbuseScores.un althyAuthorClusterScores,
          // User cluster scores bu lt from t  user-user fav graph
          FavScoresUserKey -> blocksAbuseScores. althyConsu rClusterScores,
          // Author cluster scores bu lt from t  user-user fav graph
          FavScoresAuthorKey -> blocksAbuseScores. althyAuthorClusterScores
        )
      )

      pa redScores.wr eDALSnapshotExecut on(
        AdhocAbuseS mclusterFeaturesScalaDataset,
        D.Da ly,
        D.Suff x(outputPathThr ft),
        D.Parquet,
        dateRange.`end`,
        part  ons = Set(D.Part  on("part  on", args("part  onNa "), D.Part  onType.Str ng))
      )
    }
  }
}
