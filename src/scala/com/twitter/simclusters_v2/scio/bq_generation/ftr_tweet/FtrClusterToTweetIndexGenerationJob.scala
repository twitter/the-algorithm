package com.twitter.simclusters_v2
package scio.bq_generation.ftr_tweet

import com.google.api.services.bigquery.model.TimePartitioning
import com.twitter.conversions.DurationOps.richDurationFromInt
import com.spotify.scio.ScioContext
import com.spotify.scio.coders.Coder
import com.twitter.beam.io.dal.DAL
import com.twitter.beam.io.dal.DAL.PathLayout
import com.twitter.simclusters_v2.scio.bq_generation.common.IndexGenerationUtil.parseClusterTopKTweetsFn
import java.time.Instant
import com.twitter.beam.job.DateRangeOptions
import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.scio_internal.coders.ThriftStructLazyBinaryScroogeCoder
import com.twitter.scio_internal.job.ScioBeamJob
import com.twitter.scrooge.ThriftStruct
import com.twitter.simclusters_v2.scio.bq_generation.common.BQTableDetails
import com.twitter.simclusters_v2.thriftscala.ClusterIdToTopKTweetsWithScores
import com.twitter.simclusters_v2.thriftscala.FullClusterId
import com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores
import com.twitter.tcdc.bqblaster.beam.syntax._
import com.twitter.tcdc.bqblaster.core.avro.TypedProjection
import com.twitter.tcdc.bqblaster.core.transform.RootTransform
import com.twitter.wtf.beam.bq_embedding_export.BQQueryUtils
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO

trait FTRClusterToTweetIndexGenerationJob extends ScioBeamJob[DateRangeOptions] {
  val isAdhoc: Boolean

  val outputTable: BQTableDetails
  val keyValDatasetOutputPath: String
  val clusterToTweetIndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ]

  // Base configs
  val projectId = "twttr-recos-ml-prod"
  val environment: DAL.Env = if (isAdhoc) DAL.Environment.Dev else DAL.Environment.Prod

  // Variables for Tweet Embedding SQL
  val scoreKey: String
  val scoreColumn: String

  // Variables for spam treatment
  val maxTweetFTR: Double
  val maxUserFTR: Double

  // Tweet embeddings parameters
  val tweetEmbeddingsLength: Int = Config.SimClustersTweetEmbeddingsGenerationEmbeddingLength

  // Clusters-to-tweet index parameters
  val clusterTopKTweets: Int = Config.clusterTopKTweets
  val maxTweetAgeHours: Int = Config.maxTweetAgeHours

  override implicit def scroogeCoder[T <: ThriftStruct: Manifest]: Coder[T] =
    ThriftStructLazyBinaryScroogeCoder.scroogeCoder

  override def configurePipeline(sc: ScioContext, opts: DateRangeOptions): Unit = {
    // The time when the job is scheduled
    val queryTimestamp = opts.interval.getEnd

    val tweetEmbeddingTemplateVariables =
      Map(
        "START_TIME" -> queryTimestamp.minusDays(1).toString(),
        "END_TIME" -> queryTimestamp.toString(),
        "TWEET_SAMPLE_RATE" -> Config.TweetSampleRate.toString,
        "ENG_SAMPLE_RATE" -> Config.EngSampleRate.toString,
        "MIN_TWEET_FAVS" -> Config.MinTweetFavs.toString,
        "MIN_TWEET_IMPS" -> Config.MinTweetImps.toString,
        "MAX_TWEET_FTR" -> maxTweetFTR.toString,
        "MAX_USER_LOG_N_IMPS" -> Config.MaxUserLogNImps.toString,
        "MAX_USER_LOG_N_FAVS" -> Config.MaxUserLogNFavs.toString,
        "MAX_USER_FTR" -> maxUserFTR.toString,
        "TWEET_EMBEDDING_LENGTH" -> Config.SimClustersTweetEmbeddingsGenerationEmbeddingLength.toString,
        "HALFLIFE" -> Config.SimClustersTweetEmbeddingsGenerationHalfLife.toString,
        "SCORE_COLUMN" -> scoreColumn,
        "SCORE_KEY" -> scoreKey,
      )
    val tweetEmbeddingSql = BQQueryUtils.getBQQueryFromSqlFile(
      "/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/sql/ftr_tweet_embeddings.sql",
      tweetEmbeddingTemplateVariables)

    val clusterTopTweetsTemplateVariables =
      Map(
        "CLUSTER_TOP_K_TWEETS" -> Config.clusterTopKTweets.toString,
        "TWEET_EMBEDDING_SQL" -> tweetEmbeddingSql
      )

    val clusterTopTweetsSql = BQQueryUtils.getBQQueryFromSqlFile(
      "/com/twitter/simclusters_v2/scio/bq_generation/sql/cluster_top_tweets.sql",
      clusterTopTweetsTemplateVariables
    )

    // Generate SimClusters cluster-to-tweet index
    val topKtweetsForClusterKey = sc.customInput(
      s"SimClusters cluster-to-tweet index generation BQ job",
      BigQueryIO
        .read(parseClusterTopKTweetsFn(Config.TweetEmbeddingHalfLife))
        .fromQuery(clusterTopTweetsSql)
        .usingStandardSql()
    )

    // Setup BQ writer
    val ingestionTime = opts.getDate().value.getEnd.toDate
    val bqFieldsTransform = RootTransform
      .Builder()
      .withPrependedFields("dateHour" -> TypedProjection.fromConstant(ingestionTime))
    val timePartitioning = new TimePartitioning()
      .setType("HOUR").setField("dateHour").setExpirationMs(3.days.inMilliseconds)
    val bqWriter = BigQueryIO
      .write[ClusterIdToTopKTweetsWithScores]
      .to(outputTable.toString)
      .withExtendedErrorInfo()
      .withTimePartitioning(timePartitioning)
      .withLoadJobProjectId(projectId)
      .withThriftSupport(bqFieldsTransform.build(), AvroConverter.Legacy)
      .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
      .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)

    // Save SimClusters index to a BQ table
    topKtweetsForClusterKey
      .map { clusterIdToTopKTweets =>
        {
          ClusterIdToTopKTweetsWithScores(
            clusterId = clusterIdToTopKTweets.clusterId,
            topKTweetsWithScores = clusterIdToTopKTweets.topKTweetsWithScores
          )
        }
      }
      .saveAsCustomOutput(s"WriteToBQTable - $outputTable", bqWriter)

    // Save SimClusters index as a KeyValSnapshotDataset
    topKtweetsForClusterKey
      .map { clusterIdToTopKTweets =>
        KeyVal(clusterIdToTopKTweets.clusterId, clusterIdToTopKTweets.topKTweetsWithScores)
      }.saveAsCustomOutput(
        name = s"WriteClusterToKeyIndexToKeyValDataset at $keyValDatasetOutputPath",
        DAL.writeVersionedKeyVal(
          clusterToTweetIndexSnapshotDataset,
          PathLayout.VersionedPath(prefix =
            ((if (!isAdhoc)
                Config.FTRRootMHPath
              else
                Config.FTRAdhocpath)
              + keyValDatasetOutputPath)),
          instant = Instant.ofEpochMilli(opts.interval.getEndMillis - 1L),
          environmentOverride = environment,
        )
      )
  }
}

object FTRClusterToTweetIndexGenerationAdhoc extends FTRClusterToTweetIndexGenerationJob {
  override val isAdhoc: Boolean = true
  override val outputTable: BQTableDetails =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simcluster_adhoc_test_cluster_to_tweet_index")
  override val keyValDatasetOutputPath: String =
    "ftr_tweets_adhoc/ftr_cluster_to_tweet_adhoc"
  override val clusterToTweetIndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = SimclustersFtrAdhocClusterToTweetIndexScalaDataset
  override val scoreColumn = "ftrat5_decayed_pop_bias_1000_rank_decay_1_1_embedding"
  override val scoreKey = "ftrat5_decayed_pop_bias_1000_rank_decay_1_1"
  override val maxUserFTR: Double = Config.MaxUserFTR
  override val maxTweetFTR: Double = Config.MaxTweetFTR

}

object OONFTRClusterToTweetIndexGenerationAdhoc extends FTRClusterToTweetIndexGenerationJob {
  override val isAdhoc: Boolean = true
  override val outputTable: BQTableDetails =
    BQTableDetails(
      "twttr-recos-ml-prod",
      "simclusters",
      "simcluster_adhoc_test_cluster_to_tweet_index")
  override val keyValDatasetOutputPath: String =
    "oon_ftr_tweets_adhoc/oon_ftr_cluster_to_tweet_adhoc"
  override val clusterToTweetIndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = SimclustersOonFtrAdhocClusterToTweetIndexScalaDataset
  override val scoreColumn = "oon_ftrat5_decayed_pop_bias_1000_rank_decay_embedding"
  override val scoreKey = "oon_ftrat5_decayed_pop_bias_1000_rank_decay"
  override val maxUserFTR: Double = Config.MaxUserFTR
  override val maxTweetFTR: Double = Config.MaxTweetFTR
}

object FTRPop1000RankDecay11ClusterToTweetIndexGenerationBatch
    extends FTRClusterToTweetIndexGenerationJob {
  override val isAdhoc: Boolean = false
  override val outputTable: BQTableDetails =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_ftr_pop1000_rnkdecay11_cluster_to_tweet_index")
  override val keyValDatasetOutputPath: String =
    Config.FTRPop1000RankDecay11ClusterToTweetIndexOutputPath
  override val clusterToTweetIndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = SimclustersFtrPop1000Rnkdecay11ClusterToTweetIndexScalaDataset
  override val scoreColumn = "ftrat5_decayed_pop_bias_1000_rank_decay_1_1_embedding"
  override val scoreKey = "ftrat5_decayed_pop_bias_1000_rank_decay_1_1"
  override val maxUserFTR: Double = Config.MaxUserFTR
  override val maxTweetFTR: Double = Config.MaxTweetFTR
}

object FTRPop10000RankDecay11ClusterToTweetIndexGenerationBatch
    extends FTRClusterToTweetIndexGenerationJob {
  override val isAdhoc: Boolean = false
  override val outputTable: BQTableDetails =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_ftr_pop10000_rnkdecay11_cluster_to_tweet_index")
  override val keyValDatasetOutputPath: String =
    Config.FTRPop10000RankDecay11ClusterToTweetIndexOutputPath
  override val clusterToTweetIndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = SimclustersFtrPop10000Rnkdecay11ClusterToTweetIndexScalaDataset
  override val scoreColumn = "ftrat5_decayed_pop_bias_10000_rank_decay_1_1_embedding"
  override val scoreKey = "ftrat5_decayed_pop_bias_10000_rank_decay_1_1"
  override val maxUserFTR: Double = Config.MaxUserFTR
  override val maxTweetFTR: Double = Config.MaxTweetFTR
}

object OONFTRPop1000RankDecayClusterToTweetIndexGenerationBatch
    extends FTRClusterToTweetIndexGenerationJob {
  override val isAdhoc: Boolean = false
  override val outputTable: BQTableDetails =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_oon_ftr_pop1000_rnkdecay_cluster_to_tweet_index")
  override val keyValDatasetOutputPath: String =
    Config.OONFTRPop1000RankDecayClusterToTweetIndexOutputPath
  override val clusterToTweetIndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = SimclustersOonFtrPop1000RnkdecayClusterToTweetIndexScalaDataset
  override val scoreColumn = "oon_ftrat5_decayed_pop_bias_1000_rank_decay_embedding"
  override val scoreKey = "oon_ftrat5_decayed_pop_bias_1000_rank_decay"
  override val maxUserFTR: Double = Config.MaxUserFTR
  override val maxTweetFTR: Double = Config.MaxTweetFTR
}

object DecayedSumClusterToTweetIndexGenerationBatch extends FTRClusterToTweetIndexGenerationJob {
  override val isAdhoc: Boolean = false
  override val outputTable: BQTableDetails =
    BQTableDetails(
      "twttr-bq-cassowary-prod",
      "user",
      "simclusters_decayed_sum_cluster_to_tweet_index")
  override val keyValDatasetOutputPath: String =
    Config.DecayedSumClusterToTweetIndexOutputPath
  override val clusterToTweetIndexSnapshotDataset: KeyValDALDataset[
    KeyVal[FullClusterId, TopKTweetsWithScores]
  ] = SimclustersDecayedSumClusterToTweetIndexScalaDataset
  override val scoreColumn = "dec_sum_logfavScoreClusterNormalizedOnly_embedding"
  override val scoreKey = "dec_sum_logfavScoreClusterNormalizedOnly"
  override val maxUserFTR = 1.0
  override val maxTweetFTR = 1.0
}
