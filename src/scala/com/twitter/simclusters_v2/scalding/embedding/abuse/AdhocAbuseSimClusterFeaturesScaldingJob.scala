package com.twitter.simclusters_v2.scalding.embedding.abuse

import com.twitter.ml.api.Feature
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.scalding.Args
import com.twitter.scalding.DateRange
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding.UniqueID
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite.D
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.dalv2.dataset.DAL.DALSourceBuilderEtwittertension
import com.twitter.scalding_internal.dalv2.remote_access.AllowCrossDC
import com.twitter.search.common.features.EtwitterternalTweetFeature
import com.twitter.search.common.features.SearchContetwittertFeature
import com.twitter.search.tweet_ranking.scalding.datasets.TweetEngagementRawTrainingDataDailyJavaDataset
import com.twitter.simclusters_v2.common.ClusterId
import com.twitter.simclusters_v2.hdfs_sources.AdhocAbuseSimclusterFeaturesScalaDataset
import com.twitter.simclusters_v2.scalding.common.matritwitter.SparseMatritwitter
import com.twitter.simclusters_v2.scalding.embedding.abuse.DataSources.NumBlocksP95
import com.twitter.simclusters_v2.scalding.embedding.abuse.DataSources.getFlockBlocksSparseMatritwitter
import com.twitter.simclusters_v2.scalding.embedding.abuse.DataSources.getUserInterestedInSparseMatritwitter
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.UserId
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil
import com.twitter.simclusters_v2.scalding.embedding.common.EtwitterternalDataSources
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding
import com.twitter.wtf.scalding.jobs.common.AdhocEtwitterecutionApp
import com.twitter.wtf.scalding.jobs.common.CassowaryJob
import java.util.TimeZone

object AdhocAbuseSimClusterFeatureKeys {
  val AbuseAuthorSearchKey = "abuseAuthorSearch"
  val AbuseUserSearchKey = "abuseUserSearch"
  val ImpressionUserSearchKey = "impressionUserSearch"
  val ImpressionAuthorSearchKey = "impressionAuthorSearch"
  val FlockBlocksAuthorKey = "blocksAuthorFlockDataset"
  val FlockBlocksUserKey = "blocksUserFlockDataset"
  val FavScoresAuthorKey = "favsAuthorFromFavGraph"
  val FavScoresUserKey = "favsUserFromFavGraph"
}

/**
 * Adhoc job that is still in development. The job builds features that are meant to be useful for
 * search.
 *
 * Features are built from etwitteristing SimCluster representations and the interaction graphs.
 *
 * Etwitterample command:
 * scalding remote run \
 * --target src/scala/com/twitter/simclusters_v2/scalding/embedding/abuse:abuse-adhoc \
 * --main-class com.twitter.simclusters_v2.scalding.embedding.abuse.AdhocAbuseSimClusterFeaturesScaldingJob \
 * --submitter  hadoopnest1.atla.twitter.com --user cassowary \
 * --hadoop-properties "mapreduce.job.user.classpath.first=true" -- \
 * --hdfs --date 2020/11/24 2020/12/14 --partitionName second_run --dalEnvironment Prod
 */
object AdhocAbuseSimClusterFeaturesScaldingJob etwittertends AdhocEtwitterecutionApp with CassowaryJob {
  override def jobName: String = "AdhocAbuseScaldingJob"

  import AdhocAbuseSimClusterFeatureKeys._

  val tweetAuthorFeature = new Feature.Discrete(EtwitterternalTweetFeature.TWEET_AUTHOR_ID.getName)
  val searcherIdFeature = new Feature.Discrete(SearchContetwittertFeature.SEARCHER_ID.getName)
  val isReportedFeature = new Feature.Binary(EtwitterternalTweetFeature.IS_REPORTED.getName)
  val HalfLifeInDaysForFavScore = 100

  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = ModelVersion.Model20m145kUpdated,
    pathSuffitwitter = "abuse_simcluster_features"
  )

  def searchDataRecords(
  )(
    implicit dateRange: DateRange,
    mode: Mode
  ) = {
    DAL
      .read(TweetEngagementRawTrainingDataDailyJavaDataset)
      .withRemoteReadPolicy(AllowCrossDC)
      .toDataSetPipe
      .records
  }

  def abuseInteractionSearchGraph(
  )(
    implicit dateRange: DateRange,
    mode: Mode
  ): SparseMatritwitter[UserId, UserId, Double] = {
    val abuseMatritwitterEntries = searchDataRecords()
      .flatMap { dataRecord =>
        val sDataRecord = SRichDataRecord(dataRecord)
        val authorIdOption = sDataRecord.getFeatureValueOpt(tweetAuthorFeature)
        val userIdOption = sDataRecord.getFeatureValueOpt(searcherIdFeature)
        val isReportedOption = sDataRecord.getFeatureValueOpt(isReportedFeature)

        for {
          isReported <- isReportedOption if isReported
          authorId <- authorIdOption if authorId != 0
          userId <- userIdOption if userId != 0
        } yield {
          (userId: UserId, authorId: UserId, 1.0)
        }
      }
    SparseMatritwitter.apply[UserId, UserId, Double](abuseMatritwitterEntries)
  }

  def impressionInteractionSearchGraph(
  )(
    implicit dateRange: DateRange,
    mode: Mode
  ): SparseMatritwitter[UserId, UserId, Double] = {
    val impressionMatritwitterEntries = searchDataRecords
      .flatMap { dataRecord =>
        val sDataRecord = SRichDataRecord(dataRecord)
        val authorIdOption = sDataRecord.getFeatureValueOpt(tweetAuthorFeature)
        val userIdOption = sDataRecord.getFeatureValueOpt(searcherIdFeature)

        for {
          authorId <- authorIdOption if authorId != 0
          userId <- userIdOption if userId != 0
        } yield {
          (userId: UserId, authorId: UserId, 1.0)
        }
      }
    SparseMatritwitter.apply[UserId, UserId, Double](impressionMatritwitterEntries)
  }

  case class SingleSideScores(
    unhealthyConsumerClusterScores: TypedPipe[(UserId, SimClustersEmbedding)],
    unhealthyAuthorClusterScores: TypedPipe[(UserId, SimClustersEmbedding)],
    healthyConsumerClusterScores: TypedPipe[(UserId, SimClustersEmbedding)],
    healthyAuthorClusterScores: TypedPipe[(UserId, SimClustersEmbedding)])

  def buildSearchAbuseScores(
    normalizedSimClusterMatritwitter: SparseMatritwitter[UserId, ClusterId, Double],
    unhealthyGraph: SparseMatritwitter[UserId, UserId, Double],
    healthyGraph: SparseMatritwitter[UserId, UserId, Double]
  ): SingleSideScores = {
    SingleSideScores(
      unhealthyConsumerClusterScores = SingleSideInteractionTransformation
        .clusterScoresFromGraphs(normalizedSimClusterMatritwitter, unhealthyGraph),
      unhealthyAuthorClusterScores = SingleSideInteractionTransformation
        .clusterScoresFromGraphs(normalizedSimClusterMatritwitter, unhealthyGraph.transpose),
      healthyConsumerClusterScores = SingleSideInteractionTransformation
        .clusterScoresFromGraphs(normalizedSimClusterMatritwitter, healthyGraph),
      healthyAuthorClusterScores = SingleSideInteractionTransformation
        .clusterScoresFromGraphs(normalizedSimClusterMatritwitter, healthyGraph.transpose)
    )
  }

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    Etwitterecution.getMode.flatMap { implicit mode =>
      val normalizedSimClusterMatritwitter = getUserInterestedInSparseMatritwitter.rowL2Normalize

      val abuseSearchGraph = abuseInteractionSearchGraph()
      val impressionSearchGraph = impressionInteractionSearchGraph()

      val searchAbuseScores = buildSearchAbuseScores(
        normalizedSimClusterMatritwitter,
        unhealthyGraph = abuseSearchGraph,
        healthyGraph = impressionSearchGraph)

      // Step 2a: Read FlockBlocks for unhealthy interactions and user-user-fav for healthy interactions
      val flockBlocksSparseGraph =
        getFlockBlocksSparseMatritwitter(NumBlocksP95, dateRange.prepend(Years(1)))

      val favSparseGraph = SparseMatritwitter.apply[UserId, UserId, Double](
        EtwitterternalDataSources.getFavEdges(HalfLifeInDaysForFavScore))

      val blocksAbuseScores = buildSearchAbuseScores(
        normalizedSimClusterMatritwitter,
        unhealthyGraph = flockBlocksSparseGraph,
        healthyGraph = favSparseGraph
      )

      // Step 3. Combine all scores from different sources for users
      val pairedScores = SingleSideInteractionTransformation.pairScores(
        Map(
          // User cluster scores built from the search abuse reports graph
          AbuseUserSearchKey -> searchAbuseScores.unhealthyConsumerClusterScores,
          // Author cluster scores built from the search abuse reports graph
          AbuseAuthorSearchKey -> searchAbuseScores.unhealthyAuthorClusterScores,
          // User cluster scores built from the search impression graph
          ImpressionUserSearchKey -> searchAbuseScores.healthyConsumerClusterScores,
          // Author cluster scores built from the search impression graph
          ImpressionAuthorSearchKey -> searchAbuseScores.healthyAuthorClusterScores,
          // User cluster scores built from flock blocks graph
          FlockBlocksUserKey -> blocksAbuseScores.unhealthyConsumerClusterScores,
          // Author cluster scores built from the flock blocks graph
          FlockBlocksAuthorKey -> blocksAbuseScores.unhealthyAuthorClusterScores,
          // User cluster scores built from the user-user fav graph
          FavScoresUserKey -> blocksAbuseScores.healthyConsumerClusterScores,
          // Author cluster scores built from the user-user fav graph
          FavScoresAuthorKey -> blocksAbuseScores.healthyAuthorClusterScores
        )
      )

      pairedScores.writeDALSnapshotEtwitterecution(
        AdhocAbuseSimclusterFeaturesScalaDataset,
        D.Daily,
        D.Suffitwitter(outputPathThrift),
        D.Parquet,
        dateRange.`end`,
        partitions = Set(D.Partition("partition", args("partitionName"), D.PartitionType.String))
      )
    }
  }
}
