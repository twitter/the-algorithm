package com.twitter.simclusters_v2.scio.bq_generation
package simclusters_indetwitter_generation

import com.google.api.services.bigquery.model.TimePartitioning
import com.spotify.scio.ScioContetwittert
import com.spotify.scio.coders.Coder
import com.twitter.beam.io.dal.DAL
import com.twitter.beam.io.fs.multiformat.PathLayout
import com.twitter.beam.job.DateRangeOptions
import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.scio_internal.coders.ThriftStructLazyBinaryScroogeCoder
import com.twitter.scio_internal.job.ScioBeamJob
import com.twitter.scrooge.ThriftStruct
import com.twitter.simclusters_v2.hdfs_sources.AdsFavBasedSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.AdsFavClickBasedSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.FavBasedEvergreenContentSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.FavBasedSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.FavBasedVideoSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.ReplyBasedSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.RetweetBasedSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.VideoViewBasedSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.PushOpenBasedSimclustersClusterToTweetIndetwitterScalaDataset
import com.twitter.simclusters_v2.scio.bq_generation.common.BQGenerationUtil.buildActionTypesEngagementIndicatorString
import com.twitter.simclusters_v2.scio.bq_generation.common.BQGenerationUtil.getInterestedIn2020SQL
import com.twitter.simclusters_v2.scio.bq_generation.common.BQTableDetails
import com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.Config.AdsClickEngagementTypeIds
import com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.Config.AdsFavEngagementTypeIds
import com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.EngagementEventBasedClusterToTweetIndetwitterFromBQ.getTopKTweetsForClusterKeyBQ
import com.twitter.simclusters_v2.thriftscala.ClusterIdToTopKTweetsWithScores
import com.twitter.simclusters_v2.thriftscala.FullClusterId
import com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores
import com.twitter.tcdc.bqblaster.beam.syntatwitter._
import com.twitter.tcdc.bqblaster.core.avro.TypedProjection
import com.twitter.tcdc.bqblaster.core.transform.RootTransform
import com.twitter.unified_user_actions.thriftscala.ActionType
import java.time.Instant
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO
import org.joda.time.DateTime

trait EngagementEventBasedClusterToTweetIndetwitterGenerationJob etwittertends ScioBeamJob[DateRangeOptions] {
  // Configs to set for different type of embeddings and jobs
  val isAdhoc: Boolean
  val getConsumerEmbeddingsSQLFunc: (DateTime, Int) => String
  val outputTable: BQTableDetails
  val keyValDatasetOutputPath: String
  val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ]
  // Base configs
  val projectId = "twttr-recos-ml-prod"
  val environment: DAL.Env = if (isAdhoc) DAL.Environment.Dev else DAL.Environment.Prod

  // Point to different user tweet interaction table generation sql
  // UUA-supported events: Config.unifiedUserTweetActionPairGenerationSQLPath
  val userTweetEngagementEventPairSqlPath: String
  lazy val userTweetEngagementEventPairTemplateVariable: Map[String, String] = Map.empty

  // Enable Video-only filters and health filters (for VideoViewBased embeddings)
  val enableHealthAndVideoFilters: Boolean = Config.enableHealthAndVideoFilters

  val enableFavClusterTopKTweetsIntersection: Boolean =
    Config.enableIntersectionWithFavBasedClusterTopKTweetsIndetwitter

  // Min fav/interaction threshold
  val minInteractionCount: Int = Config.minInteractionCount
  val minFavCount: Int = Config.minFavCount

  // Tweet embeddings parameters
  val tweetEmbeddingsLength: Int = Config.tweetEmbeddingsLength
  val tweetEmbeddingsHalfLife: Int = Config.tweetEmbeddingsHalfLife

  // Clusters-to-tweet indetwitter parameters
  val clusterTopKTweets: Int = Config.clusterTopKTweets
  val matwitterTweetAgeHours: Int = Config.matwitterTweetAgeHours
  val minEngagementPerCluster: Int = Config.minEngagementPerCluster

  override implicit def scroogeCoder[T <: ThriftStruct: Manifest]: Coder[T] =
    ThriftStructLazyBinaryScroogeCoder.scroogeCoder

  override def configurePipeline(sc: ScioContetwittert, opts: DateRangeOptions): Unit = {
    // The time when the job is scheduled
    val queryTimestamp = opts.interval.getEnd

    // Read consumer embeddings SQL
    val consumerEmbeddingsSQL = getConsumerEmbeddingsSQLFunc(queryTimestamp, 21)

    // Generate SimClusters cluster-to-tweet indetwitter via BQ
    val topKtweetsForClusterKey =
      getTopKTweetsForClusterKeyBQ(
        sc,
        queryTimestamp,
        matwitterTweetAgeHours,
        consumerEmbeddingsSQL,
        userTweetEngagementEventPairSqlPath,
        userTweetEngagementEventPairTemplateVariable,
        enableHealthAndVideoFilters,
        enableFavClusterTopKTweetsIntersection,
        minInteractionCount,
        minFavCount,
        tweetEmbeddingsLength,
        tweetEmbeddingsHalfLife,
        minEngagementPerCluster,
        clusterTopKTweets
      )

    // Setup BQ writer
    val ingestionTime = opts.getDate().value.getEnd.toDate
    val bqFieldsTransform = RootTransform
      .Builder()
      .withPrependedFields("dateHour" -> TypedProjection.fromConstant(ingestionTime))
    val timePartitioning = new TimePartitioning()
      .setType("HOUR").setField("dateHour").setEtwitterpirationMs(3.days.inMilliseconds)
    val bqWriter = BigQueryIO
      .write[ClusterIdToTopKTweetsWithScores]
      .to(outputTable.toString)
      .withEtwittertendedErrorInfo()
      .withTimePartitioning(timePartitioning)
      .withLoadJobProjectId(projectId)
      .withThriftSupport(bqFieldsTransform.build(), AvroConverter.Legacy)
      .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
      .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)

    // Save SimClusters indetwitter to a BQ table
    topKtweetsForClusterKey
      .map { clusterIdToTopKTweets =>
        {
          ClusterIdToTopKTweetsWithScores(
            clusterId = clusterIdToTopKTweets.clusterId,
            topKTweetsWithScores = clusterIdToTopKTweets.topKTweetsWithScores
          )
        }
      }
      .saveAsCustomOutput(s"WriteToBQTable - ${outputTable}", bqWriter)

    // Save SimClusters indetwitter as a KeyValSnapshotDataset
    topKtweetsForClusterKey
      .map { clusterIdToTopKTweets =>
        KeyVal(clusterIdToTopKTweets.clusterId, clusterIdToTopKTweets.topKTweetsWithScores)
      }.saveAsCustomOutput(
        name = s"WriteClusterToKeyIndetwitterToKeyValDataset at ${keyValDatasetOutputPath}",
        DAL.writeVersionedKeyVal(
          clusterToTweetIndetwitterSnapshotDataset,
          PathLayout.VersionedPath(prefitwitter =
            ((if (!isAdhoc)
                Config.RootMHPath
              else
                Config.AdhocRootPath)
              + keyValDatasetOutputPath)),
          instant = Instant.ofEpochMilli(opts.interval.getEndMillis - 1L),
          environmentOverride = environment,
        )
      )
  }
}

// This abstract class is used to define parameters specific to UUA events.
abstract class UUABasedClusterToTweetIndetwitterGenerationJob
    etwittertends EngagementEventBasedClusterToTweetIndetwitterGenerationJob {
  // UUA Action types and column names
  val contributingActionTypes: Seq[String]
  val contributingActionReferenceTweetIdColumn: String = Config.actionTweetIdColumn
  val undoActionTypes: Seq[String]
  // Default undo tweet id is same as the actionTweetId (e.g. for favs these are the same tweet id)
  val undoActionReferenceTweetIdColumn: String = Config.actionTweetIdColumn

  // Get the string that represents the list of undo event ids
  lazy val undoActionTypesStr: String = {
    // Populate the action type list with a placeholder action if its empty
    val actionTypes =
      if (undoActionTypes.nonEmpty) undoActionTypes
      else Seq(Config.PlaceholderActionType)
    convertActionTypesSeqToString(actionTypes)
  }

  override lazy val userTweetEngagementEventPairTemplateVariable: Map[String, String] = {
    Map(
      "CONTRIBUTING_ACTION_TYPES_STR" -> convertActionTypesSeqToString(contributingActionTypes),
      "CONTRIBUTING_ACTION_TWEET_ID_COLUMN" -> contributingActionReferenceTweetIdColumn,
      "UNDO_ACTION_TYPES_STR" -> undoActionTypesStr,
      "UNDO_ACTION_TWEET_ID_COLUMN" -> undoActionReferenceTweetIdColumn
    )
  }

  /***
   *  Convert a list of actions to a string that could be easily used in SQLs
   *  Etwitterample input: Seq("ServerTweetFav", "ClientTweetFav")
   *          output: "ServerTweetFav","ClientTweetFav"
   *  SQL use case: SELECT * FROM table WHERE actionType IN ("ServerTweetFav","ClientTweetFav")
   */
  private def convertActionTypesSeqToString(actionTypes: Seq[String]): String = {
    actionTypes.map(action => f"""\"${action}\"""").mkString(",")
  }
}

abstract class AdsClusterToTweetIndetwitterGenerationJob
    etwittertends EngagementEventBasedClusterToTweetIndetwitterGenerationJob {
  // Ads contributing action types - fav, click, etc
  val contributingActionTypes: Seq[Int]

  override lazy val userTweetEngagementEventPairTemplateVariable: Map[String, String] = {
    Map(
      "CONTRIBUTING_ACTION_TYPES_STR" -> convertActionTypesSeqToString(contributingActionTypes)
    )
  }
  private def convertActionTypesSeqToString(actionTypes: Seq[Int]): String = {
    actionTypes.map(action => f"""${action}""").mkString(",")
  }
}

object FavBasedClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.unifiedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetFav.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetUnfav.name)
  override val minInteractionCount: Int = 8
  override val minFavCount: Int = 8
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_fav_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.FavBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    FavBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object FavBasedClusterToTweetIndetwitterGenerationBatchJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.unifiedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetFav.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetUnfav.name)
  override val minInteractionCount: Int = 8
  override val minFavCount: Int = 8
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_fav_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.FavBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    FavBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object VideoViewBasedClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.unifiedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(
    ActionType.ClientTweetVideoPlayback50.name)
  override val undoActionTypes: Seq[String] = Seq.empty
  override val enableHealthAndVideoFilters: Boolean = true
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_video_view_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.VideoViewBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    VideoViewBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object VideoViewBasedClusterToTweetIndetwitterGenerationBatchJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.unifiedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(
    ActionType.ClientTweetVideoPlayback50.name)
  override val undoActionTypes: Seq[String] = Seq.empty
  override val enableHealthAndVideoFilters: Boolean = true
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_video_view_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.VideoViewBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    VideoViewBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object RetweetBasedClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.unifiedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetRetweet.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetUnretweet.name)
  override val undoActionReferenceTweetIdColumn: String = Config.retweetTweetIdColumn
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_retweet_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.RetweetBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    RetweetBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object RetweetBasedClusterToTweetIndetwitterGenerationBatchJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.unifiedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetRetweet.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetUnretweet.name)
  override val undoActionReferenceTweetIdColumn: String = Config.retweetTweetIdColumn
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_retweet_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.RetweetBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    RetweetBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object ReplyBasedClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.combinedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetReply.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetDelete.name)
  override val undoActionReferenceTweetIdColumn: String = Config.replyTweetIdColumn
  override val minInteractionCount: Int = 8
  override val minFavCount: Int = 8
  override val minEngagementPerCluster: Int = 3
  // Add supplemental positive signals to the user tweet engagement event template
  // We bundle each reply signal with a positive signal (fav or retweet)
  val supplementalPositiveSignals: Seq[String] =
    Seq(ActionType.ServerTweetFav.name, ActionType.ServerTweetRetweet.name)
  override lazy val userTweetEngagementEventPairTemplateVariable: Map[String, String] = {
    Map(
      "CONTRIBUTING_ACTION_TYPE_STR" -> contributingActionTypes.head,
      "UNDO_ACTION_TYPES_STR" -> undoActionTypesStr,
      "UNDO_ACTION_TWEET_ID_COLUMN" -> undoActionReferenceTweetIdColumn,
      "SUPPLEMENTAL_ACTION_TYPES_ENGAGEMENT_STR" -> buildActionTypesEngagementIndicatorString(
        supplementalPositiveSignals)
    )
  }
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_reply_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.ReplyBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    ReplyBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object ReplyBasedClusterToTweetIndetwitterGenerationBatchJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.combinedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetReply.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetDelete.name)
  override val undoActionReferenceTweetIdColumn: String = Config.replyTweetIdColumn
  override val minInteractionCount: Int = 8
  override val minFavCount: Int = 8
  override val minEngagementPerCluster: Int = 3
  // Add supplemental positive signals to the user tweet engagement event template
  // We bundle each reply signal with a positive signal (fav or retweet)
  val supplementalPositiveSignals: Seq[String] =
    Seq(ActionType.ServerTweetFav.name, ActionType.ServerTweetRetweet.name)
  override lazy val userTweetEngagementEventPairTemplateVariable: Map[String, String] = {
    Map(
      "CONTRIBUTING_ACTION_TYPE_STR" -> contributingActionTypes.head,
      "UNDO_ACTION_TYPES_STR" -> undoActionTypesStr,
      "UNDO_ACTION_TWEET_ID_COLUMN" -> undoActionReferenceTweetIdColumn,
      "SUPPLEMENTAL_ACTION_TYPES_ENGAGEMENT_STR" -> buildActionTypesEngagementIndicatorString(
        supplementalPositiveSignals)
    )
  }
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_reply_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.ReplyBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    ReplyBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object PushOpenBasedClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.unifiedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ClientNotificationOpen.name)
  override val contributingActionReferenceTweetIdColumn: String = Config.pushTweetIdColumn
  override val undoActionTypes: Seq[String] = Seq.empty
  override val minInteractionCount = 1
  override val minFavCount = 0
  override val enableFavClusterTopKTweetsIntersection = true
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_push_open_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.PushOpenBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    PushOpenBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object PushOpenBasedClusterToTweetIndetwitterGenerationBatchJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.unifiedUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ClientNotificationOpen.name)
  override val contributingActionReferenceTweetIdColumn: String = Config.pushTweetIdColumn
  override val undoActionTypes: Seq[String] = Seq.empty
  override val minInteractionCount = 1
  override val minFavCount = 0
  override val enableFavClusterTopKTweetsIntersection = true
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_push_open_based_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath = Config.PushOpenBasedClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    PushOpenBasedSimclustersClusterToTweetIndetwitterScalaDataset
}

object AdsFavBasedClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends AdsClusterToTweetIndetwitterGenerationJob {
  val isAdhoc: Boolean = true
  val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val contributingActionTypes: Seq[Int] = AdsFavEngagementTypeIds // fav
  override val tweetEmbeddingsHalfLife: Int = 345600000 // 4 days
  // The earliest user tweet engagement event we consider is 7 days ago
  // The tweet could be older than 7 days
  override val matwitterTweetAgeHours: Int = 168 // 7 days
  override val minInteractionCount: Int = 3
  override val minFavCount: Int = 3
  override val minEngagementPerCluster: Int = 2
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_ads_fav_based_cluster_to_tweet_indetwitter")
  val keyValDatasetOutputPath: String = Config.AdsFavBasedClusterToTweetIndetwitterOutputPath
  val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = AdsFavBasedSimclustersClusterToTweetIndetwitterScalaDataset
  val userTweetEngagementEventPairSqlPath: String =
    Config.adsUserTweetActionPairGenerationSQLPath
}
object AdsFavBasedClusterToTweetIndetwitterGenerationBatchJob
    etwittertends AdsClusterToTweetIndetwitterGenerationJob {
  val isAdhoc: Boolean = false
  val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val contributingActionTypes: Seq[Int] = AdsFavEngagementTypeIds // fav
  override val tweetEmbeddingsHalfLife: Int = 345600000 // 4 days
  // The earliest user tweet engagement event we consider is 7 days ago
  // The tweet could be older than 7 days
  override val matwitterTweetAgeHours: Int = 168 // 7 days
  override val minInteractionCount: Int = 3
  override val minFavCount: Int = 3
  override val minEngagementPerCluster: Int = 2
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_ads_fav_based_cluster_to_tweet_indetwitter")
  val keyValDatasetOutputPath: String = Config.AdsFavBasedClusterToTweetIndetwitterOutputPath
  val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = AdsFavBasedSimclustersClusterToTweetIndetwitterScalaDataset
  val userTweetEngagementEventPairSqlPath: String =
    Config.adsUserTweetActionPairGenerationSQLPath
}

object AdsFavClickBasedClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends AdsClusterToTweetIndetwitterGenerationJob {
  val isAdhoc: Boolean = true
  val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val contributingActionTypes: Seq[Int] =
    AdsFavEngagementTypeIds ++ AdsClickEngagementTypeIds // fav + click
  override val tweetEmbeddingsHalfLife: Int = 604800000 // 7 days
  // The earliest user tweet engagement event we consider is 21 days ago
  // The tweet could be older than 21 days
  override val matwitterTweetAgeHours: Int = 504 // 21 days
  override val minInteractionCount: Int = 3
  override val minFavCount: Int = 3
  override val minEngagementPerCluster: Int = 2
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_ads_fav_click_ sbased_cluster_to_tweet_indetwitter")
  val keyValDatasetOutputPath: String = Config.AdsFavClickBasedClusterToTweetIndetwitterOutputPath
  val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = AdsFavClickBasedSimclustersClusterToTweetIndetwitterScalaDataset
  val userTweetEngagementEventPairSqlPath: String =
    Config.adsUserTweetActionPairGenerationSQLPath
}

object AdsFavClickBasedClusterToTweetIndetwitterGenerationBatchJob
    etwittertends AdsClusterToTweetIndetwitterGenerationJob {
  val isAdhoc: Boolean = false
  val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val contributingActionTypes: Seq[Int] =
    AdsFavEngagementTypeIds ++ AdsClickEngagementTypeIds // fav + click
  override val tweetEmbeddingsHalfLife: Int = 604800000 // 7 days
  // The earliest user tweet engagement event we consider is 21 days ago
  // The tweet could be older than 21 days
  override val matwitterTweetAgeHours: Int = 504 // 21 days
  override val minInteractionCount: Int = 3
  override val minFavCount: Int = 3
  override val minEngagementPerCluster: Int = 2
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_ads_fav_click_based_cluster_to_tweet_indetwitter")
  val keyValDatasetOutputPath: String = Config.AdsFavClickBasedClusterToTweetIndetwitterOutputPath
  val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = AdsFavClickBasedSimclustersClusterToTweetIndetwitterScalaDataset
  val userTweetEngagementEventPairSqlPath: String =
    Config.adsUserTweetActionPairGenerationSQLPath
}

object FavBasedEvergreenContentClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.evergreenContentUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetFav.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetUnfav.name)
  override val tweetEmbeddingsHalfLife: Int = 57600000 // 16 hours
  override val matwitterTweetAgeHours: Int = 48 // 2 days
  override val minInteractionCount: Int = 8
  override val minFavCount: Int = 0
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_fav_based_evergreen_content_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath =
    Config.FavBasedEvergreenContentClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    FavBasedEvergreenContentSimclustersClusterToTweetIndetwitterScalaDataset
}

object FavBasedEvergreenContentClusterToTweetIndetwitterGenerationBatchJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.evergreenContentUserTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetFav.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetUnfav.name)
  override val tweetEmbeddingsHalfLife: Int = 57600000 // 16 hours
  override val matwitterTweetAgeHours: Int = 48 // 2 days
  override val minInteractionCount: Int = 8
  override val minFavCount: Int = 0
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_fav_based_evergreen_content_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath =
    Config.FavBasedEvergreenContentClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    FavBasedEvergreenContentSimclustersClusterToTweetIndetwitterScalaDataset
}

object FavBasedVideoClusterToTweetIndetwitterGenerationAdhocJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.favBasedVideoTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetFav.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetUnfav.name)
  override val minInteractionCount: Int = 8
  override val minFavCount: Int = 0
  override val outputTable =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simclusters_fav_based_video_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath =
    Config.FavBasedVideoClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    FavBasedVideoSimclustersClusterToTweetIndetwitterScalaDataset
}

object FavBasedVideoClusterToTweetIndetwitterGenerationBatchJob
    etwittertends UUABasedClusterToTweetIndetwitterGenerationJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn2020SQL
  override val userTweetEngagementEventPairSqlPath: String =
    Config.favBasedVideoTweetActionPairGenerationSQLPath
  override val contributingActionTypes: Seq[String] = Seq(ActionType.ServerTweetFav.name)
  override val undoActionTypes: Seq[String] = Seq(ActionType.ServerTweetUnfav.name)
  override val minInteractionCount: Int = 8
  override val minFavCount: Int = 0
  override val outputTable =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_fav_based_video_cluster_to_tweet_indetwitter")
  override val keyValDatasetOutputPath =
    Config.FavBasedVideoClusterToTweetIndetwitterOutputPath
  override val clusterToTweetIndetwitterSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] =
    FavBasedVideoSimclustersClusterToTweetIndetwitterScalaDataset
}
