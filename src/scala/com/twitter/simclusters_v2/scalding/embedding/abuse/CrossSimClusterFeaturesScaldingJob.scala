package com.twitter.simclusters_v2.scalding.embedding.abuse

import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding.Args
import com.twitter.scalding.DateRange
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding.UniqueID
import com.twitter.scalding.Years
import com.twitter.simclusters_v2.scalding.common.matritwitter.SparseMatritwitter
import com.twitter.simclusters_v2.scalding.embedding.abuse.DataSources.NumBlocksP95
import com.twitter.simclusters_v2.scalding.embedding.abuse.DataSources.getFlockBlocksSparseMatritwitter
import com.twitter.simclusters_v2.scalding.embedding.abuse.DataSources.getUserInterestedInTruncatedKMatritwitter
import com.twitter.scalding_internal.dalv2.DALWrite.D
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.ClusterId
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.UserId
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil
import com.twitter.simclusters_v2.scalding.embedding.common.EtwitterternalDataSources
import com.twitter.simclusters_v2.thriftscala.AdhocCrossSimClusterInteractionScores
import com.twitter.simclusters_v2.thriftscala.ClustersScore
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.wtf.scalding.jobs.common.AdhocEtwitterecutionApp
import com.twitter.wtf.scalding.jobs.common.CassowaryJob
import com.twitter.simclusters_v2.hdfs_sources.AdhocCrossSimclusterBlockInteractionFeaturesScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.AdhocCrossSimclusterFavInteractionFeaturesScalaDataset
import java.util.TimeZone

/*
To run:
scalding remote run \
--user cassowary \
--submitter hadoopnest1.atla.twitter.com \
--target src/scala/com/twitter/simclusters_v2/scalding/embedding/abuse:cross_simcluster-adhoc \
--main-class com.twitter.simclusters_v2.scalding.embedding.abuse.CrossSimClusterFeaturesScaldingJob \
--submitter-memory 128192.megabyte --hadoop-properties "mapreduce.map.memory.mb=8192 mapreduce.map.java.opts='-twittermtwitter7618M' mapreduce.reduce.memory.mb=8192 mapreduce.reduce.java.opts='-twittermtwitter7618M'" \
-- \
--date 2021-02-07 \
--dalEnvironment Prod
 */

object CrossSimClusterFeaturesUtil {

  /**
   * To generate the interaction score for 2 simclusters c1 and c2 for all cluster combinations (I):
   * a) Get C - user interestedIn matritwitter, User * Cluster
   * b) Get INT - positive or negative interaction matritwitter, User * User
   * c) Compute C^T*INT
   * d) Finally, return C^T*INT*C
   */
  def getCrossClusterScores(
    userClusterMatritwitter: SparseMatritwitter[UserId, ClusterId, Double],
    userInteractionMatritwitter: SparseMatritwitter[UserId, UserId, Double]
  ): SparseMatritwitter[ClusterId, ClusterId, Double] = {
    // intermediate = C^T*INT
    val intermediateResult = userClusterMatritwitter.transpose.multiplySparseMatritwitter(userInteractionMatritwitter)
    // return intermediate*C
    intermediateResult.multiplySparseMatritwitter(userClusterMatritwitter)
  }
}

object CrossSimClusterFeaturesScaldingJob etwittertends AdhocEtwitterecutionApp with CassowaryJob {
  override def jobName: String = "AdhocAbuseCrossSimClusterFeaturesScaldingJob"

  private val outputPathBlocksThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = ModelVersion.Model20m145kUpdated,
    pathSuffitwitter = "abuse_cross_simcluster_block_features"
  )

  private val outputPathFavThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = ModelVersion.Model20m145kUpdated,
    pathSuffitwitter = "abuse_cross_simcluster_fav_features"
  )

  private val HalfLifeInDaysForFavScore = 100

  // Adhoc jobs which use all user interestedIn simclusters (default=50) was failing
  // Hence truncating the number of clusters
  private val MatwitterNumClustersPerUser = 20

  import CrossSimClusterFeaturesUtil._
  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {

    val normalizedUserInterestedInMatritwitter: SparseMatritwitter[UserId, ClusterId, Double] =
      getUserInterestedInTruncatedKMatritwitter(MatwitterNumClustersPerUser).rowL2Normalize

    //the below code is to get cross simcluster features from flockblocks - negative user-user interactions.
    val flockBlocksMatritwitter: SparseMatritwitter[UserId, UserId, Double] =
      getFlockBlocksSparseMatritwitter(NumBlocksP95, dateRange.prepend(Years(1)))

    val crossClusterBlockScores: SparseMatritwitter[ClusterId, ClusterId, Double] =
      getCrossClusterScores(normalizedUserInterestedInMatritwitter, flockBlocksMatritwitter)

    val blockScores: TypedPipe[AdhocCrossSimClusterInteractionScores] =
      crossClusterBlockScores.rowAsKeys
        .mapValues(List(_)).sumByKey.toTypedPipe.map {
          case (givingClusterId, receivingClustersWithScores) =>
            AdhocCrossSimClusterInteractionScores(
              clusterId = givingClusterId,
              clusterScores = receivingClustersWithScores.map {
                case (cluster, score) => ClustersScore(cluster, score)
              })
        }

    // get cross simcluster features from fav graph - positive user-user interactions
    val favGraphMatritwitter: SparseMatritwitter[UserId, UserId, Double] =
      SparseMatritwitter.apply[UserId, UserId, Double](
        EtwitterternalDataSources.getFavEdges(HalfLifeInDaysForFavScore))

    val crossClusterFavScores: SparseMatritwitter[ClusterId, ClusterId, Double] =
      getCrossClusterScores(normalizedUserInterestedInMatritwitter, favGraphMatritwitter)

    val favScores: TypedPipe[AdhocCrossSimClusterInteractionScores] =
      crossClusterFavScores.rowAsKeys
        .mapValues(List(_)).sumByKey.toTypedPipe.map {
          case (givingClusterId, receivingClustersWithScores) =>
            AdhocCrossSimClusterInteractionScores(
              clusterId = givingClusterId,
              clusterScores = receivingClustersWithScores.map {
                case (cluster, score) => ClustersScore(cluster, score)
              })
        }
    // write both block and fav interaction matrices to hdfs in thrift format
    Etwitterecution
      .zip(
        blockScores.writeDALSnapshotEtwitterecution(
          AdhocCrossSimclusterBlockInteractionFeaturesScalaDataset,
          D.Daily,
          D.Suffitwitter(outputPathBlocksThrift),
          D.Parquet,
          dateRange.`end`),
        favScores.writeDALSnapshotEtwitterecution(
          AdhocCrossSimclusterFavInteractionFeaturesScalaDataset,
          D.Daily,
          D.Suffitwitter(outputPathFavThrift),
          D.Parquet,
          dateRange.`end`)
      ).unit
  }
}
