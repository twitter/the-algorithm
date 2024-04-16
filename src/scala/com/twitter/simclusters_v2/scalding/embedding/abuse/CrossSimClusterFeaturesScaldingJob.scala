package com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse

 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng.Years
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.SparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.DataS ces.NumBlocksP95
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.DataS ces.getFlockBlocksSparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.DataS ces.getUser nterested nTruncatedKMatr x
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.Cluster d
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.User d
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.AdhocCrossS mCluster nteract onScores
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersScore
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.CassowaryJob
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocCrossS mclusterBlock nteract onFeaturesScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocCrossS mclusterFav nteract onFeaturesScalaDataset
 mport java.ut l.T  Zone

/*
To run:
scald ng remote run \
--user cassowary \
--subm ter hadoopnest1.atla.tw ter.com \
--target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/abuse:cross_s mcluster-adhoc \
--ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.abuse.CrossS mClusterFeaturesScald ngJob \
--subm ter- mory 128192. gabyte --hadoop-propert es "mapreduce.map. mory.mb=8192 mapreduce.map.java.opts='-Xmx7618M' mapreduce.reduce. mory.mb=8192 mapreduce.reduce.java.opts='-Xmx7618M'" \
-- \
--date 2021-02-07 \
--dalEnv ron nt Prod
 */

object CrossS mClusterFeaturesUt l {

  /**
   * To generate t   nteract on score for 2 s mclusters c1 and c2 for all cluster comb nat ons ( ):
   * a) Get C - user  nterested n matr x, User * Cluster
   * b) Get  NT - pos  ve or negat ve  nteract on matr x, User * User
   * c) Compute C^T* NT
   * d) F nally, return C^T* NT*C
   */
  def getCrossClusterScores(
    userClusterMatr x: SparseMatr x[User d, Cluster d, Double],
    user nteract onMatr x: SparseMatr x[User d, User d, Double]
  ): SparseMatr x[Cluster d, Cluster d, Double] = {
    //  nter d ate = C^T* NT
    val  nter d ateResult = userClusterMatr x.transpose.mult plySparseMatr x(user nteract onMatr x)
    // return  nter d ate*C
     nter d ateResult.mult plySparseMatr x(userClusterMatr x)
  }
}

object CrossS mClusterFeaturesScald ngJob extends AdhocExecut onApp w h CassowaryJob {
  overr de def jobNa : Str ng = "AdhocAbuseCrossS mClusterFeaturesScald ngJob"

  pr vate val outputPathBlocksThr ft: Str ng = Embedd ngUt l.getHdfsPath(
     sAdhoc = false,
     sManhattanKeyVal = false,
    modelVers on = ModelVers on.Model20m145kUpdated,
    pathSuff x = "abuse_cross_s mcluster_block_features"
  )

  pr vate val outputPathFavThr ft: Str ng = Embedd ngUt l.getHdfsPath(
     sAdhoc = false,
     sManhattanKeyVal = false,
    modelVers on = ModelVers on.Model20m145kUpdated,
    pathSuff x = "abuse_cross_s mcluster_fav_features"
  )

  pr vate val HalfL fe nDaysForFavScore = 100

  // Adhoc jobs wh ch use all user  nterested n s mclusters (default=50) was fa l ng
  //  nce truncat ng t  number of clusters
  pr vate val MaxNumClustersPerUser = 20

   mport CrossS mClusterFeaturesUt l._
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val normal zedUser nterested nMatr x: SparseMatr x[User d, Cluster d, Double] =
      getUser nterested nTruncatedKMatr x(MaxNumClustersPerUser).rowL2Normal ze

    //t  below code  s to get cross s mcluster features from flockblocks - negat ve user-user  nteract ons.
    val flockBlocksMatr x: SparseMatr x[User d, User d, Double] =
      getFlockBlocksSparseMatr x(NumBlocksP95, dateRange.prepend(Years(1)))

    val crossClusterBlockScores: SparseMatr x[Cluster d, Cluster d, Double] =
      getCrossClusterScores(normal zedUser nterested nMatr x, flockBlocksMatr x)

    val blockScores: TypedP pe[AdhocCrossS mCluster nteract onScores] =
      crossClusterBlockScores.rowAsKeys
        .mapValues(L st(_)).sumByKey.toTypedP pe.map {
          case (g v ngCluster d, rece v ngClustersW hScores) =>
            AdhocCrossS mCluster nteract onScores(
              cluster d = g v ngCluster d,
              clusterScores = rece v ngClustersW hScores.map {
                case (cluster, score) => ClustersScore(cluster, score)
              })
        }

    // get cross s mcluster features from fav graph - pos  ve user-user  nteract ons
    val favGraphMatr x: SparseMatr x[User d, User d, Double] =
      SparseMatr x.apply[User d, User d, Double](
        ExternalDataS ces.getFavEdges(HalfL fe nDaysForFavScore))

    val crossClusterFavScores: SparseMatr x[Cluster d, Cluster d, Double] =
      getCrossClusterScores(normal zedUser nterested nMatr x, favGraphMatr x)

    val favScores: TypedP pe[AdhocCrossS mCluster nteract onScores] =
      crossClusterFavScores.rowAsKeys
        .mapValues(L st(_)).sumByKey.toTypedP pe.map {
          case (g v ngCluster d, rece v ngClustersW hScores) =>
            AdhocCrossS mCluster nteract onScores(
              cluster d = g v ngCluster d,
              clusterScores = rece v ngClustersW hScores.map {
                case (cluster, score) => ClustersScore(cluster, score)
              })
        }
    // wr e both block and fav  nteract on matr ces to hdfs  n thr ft format
    Execut on
      .z p(
        blockScores.wr eDALSnapshotExecut on(
          AdhocCrossS mclusterBlock nteract onFeaturesScalaDataset,
          D.Da ly,
          D.Suff x(outputPathBlocksThr ft),
          D.Parquet,
          dateRange.`end`),
        favScores.wr eDALSnapshotExecut on(
          AdhocCrossS mclusterFav nteract onFeaturesScalaDataset,
          D.Da ly,
          D.Suff x(outputPathFavThr ft),
          D.Parquet,
          dateRange.`end`)
      ).un 
  }
}
