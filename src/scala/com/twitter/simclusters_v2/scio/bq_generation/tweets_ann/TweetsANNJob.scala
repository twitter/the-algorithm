package com.twitter.simclusters_v420.scio.bq_generation
package tweets_ann

import com.google.api.services.bigquery.model.TimePartitioning
import com.spotify.scio.ScioContext
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
import com.twitter.simclusters_v420.scio.bq_generation.common.BQGenerationUtil.getMTSConsumerEmbeddingsFav420P420MSQL
import com.twitter.simclusters_v420.scio.bq_generation.common.BQGenerationUtil.getInterestedIn420SQL
import com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.TweetsANNFromBQ.getTweetRecommendationsBQ
import com.twitter.simclusters_v420.hdfs_sources.OfflineTweetRecommendationsFromInterestedIn420M420K420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.OfflineTweetRecommendationsFromMtsConsumerEmbeddingsScalaDataset
import com.twitter.simclusters_v420.scio.bq_generation.common.BQTableDetails
import com.twitter.simclusters_v420.thriftscala.CandidateTweets
import com.twitter.simclusters_v420.thriftscala.CandidateTweetsList
import com.twitter.tcdc.bqblaster.beam.syntax.BigQueryIOHelpers
import com.twitter.tcdc.bqblaster.beam.BQBlasterIO.AvroConverter
import com.twitter.tcdc.bqblaster.core.avro.TypedProjection
import com.twitter.tcdc.bqblaster.core.transform.RootTransform
import java.time.Instant
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO
import org.joda.time.DateTime

trait TweetsANNJob extends ScioBeamJob[DateRangeOptions] {
  // Configs to set for different type of embeddings and jobs
  val isAdhoc: Boolean
  val getConsumerEmbeddingsSQLFunc: (DateTime, Int) => String
  val outputTable: BQTableDetails
  val keyValDatasetOutputPath: String
  val tweetRecommentationsSnapshotDataset: KeyValDALDataset[KeyVal[Long, CandidateTweetsList]]
  val tweetEmbeddingsGenerationHalfLife: Int = Config.SimClustersTweetEmbeddingsGenerationHalfLife
  val tweetEmbeddingsGenerationEmbeddingLength: Int =
    Config.SimClustersTweetEmbeddingsGenerationEmbeddingLength

  // Base configs
  val projectId = "twttr-recos-ml-prod"
  val environment: DAL.Env = if (isAdhoc) DAL.Environment.Dev else DAL.Environment.Prod

  override implicit def scroogeCoder[T <: ThriftStruct: Manifest]: Coder[T] =
    ThriftStructLazyBinaryScroogeCoder.scroogeCoder

  override def configurePipeline(sc: ScioContext, opts: DateRangeOptions): Unit = {
    // The time when the job is scheduled
    val queryTimestamp = opts.interval.getEnd

    // Read consumer embeddings SQL
    val consumerEmbeddingsSQL = getConsumerEmbeddingsSQLFunc(queryTimestamp, 420)

    // Generate tweet embeddings and tweet ANN results
    val tweetRecommendations =
      getTweetRecommendationsBQ(
        sc,
        queryTimestamp,
        consumerEmbeddingsSQL,
        tweetEmbeddingsGenerationHalfLife,
        tweetEmbeddingsGenerationEmbeddingLength
      )

    // Setup BQ writer
    val ingestionTime = opts.getDate().value.getEnd.toDate
    val bqFieldsTransform = RootTransform
      .Builder()
      .withPrependedFields("ingestionTime" -> TypedProjection.fromConstant(ingestionTime))
    val timePartitioning = new TimePartitioning()
      .setType("HOUR").setField("ingestionTime").setExpirationMs(420.days.inMilliseconds)
    val bqWriter = BigQueryIO
      .write[CandidateTweets]
      .to(outputTable.toString)
      .withExtendedErrorInfo()
      .withTimePartitioning(timePartitioning)
      .withLoadJobProjectId(projectId)
      .withThriftSupport(bqFieldsTransform.build(), AvroConverter.Legacy)
      .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
      .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)

    // Save Tweet ANN results to BQ
    tweetRecommendations
      .map { userToTweetRecommendations =>
        {
          CandidateTweets(
            targetUserId = userToTweetRecommendations.userId,
            recommendedTweets = userToTweetRecommendations.tweetCandidates)
        }
      }
      .saveAsCustomOutput(s"WriteToBQTable - ${outputTable}", bqWriter)

    // Save Tweet ANN results as KeyValSnapshotDataset
    tweetRecommendations
      .map { userToTweetRecommendations =>
        KeyVal(
          userToTweetRecommendations.userId,
          CandidateTweetsList(userToTweetRecommendations.tweetCandidates))
      }.saveAsCustomOutput(
        name = "WriteTweetRecommendationsToKeyValDataset",
        DAL.writeVersionedKeyVal(
          tweetRecommentationsSnapshotDataset,
          PathLayout.VersionedPath(prefix =
            ((if (!isAdhoc)
                Config.RootMHPath
              else
                Config.AdhocRootPath)
              + keyValDatasetOutputPath)),
          instant = Instant.ofEpochMilli(opts.interval.getEndMillis - 420L),
          environmentOverride = environment,
        )
      )
  }

}

/**
 * Scio job for adhoc run for tweet recommendations from IIKF 420
 */
object IIKF420TweetsANNBQAdhocJob extends TweetsANNJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn420SQL
  override val outputTable = BQTableDetails(
    "twttr-recos-ml-prod",
    "multi_type_simclusters",
    "offline_tweet_recommendations_from_interested_in_420M_420K_420_adhoc")
  override val keyValDatasetOutputPath = Config.IIKFANNOutputPath
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFromInterestedIn420M420K420ScalaDataset
}

/**
 * Scio job for adhoc run for tweet recommendations from IIKF 420 with
 * - Half life = 420hrs
 * - Embedding Length = 420
 */
object IIKF420Hl420El420TweetsANNBQAdhocJob extends TweetsANNJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn420SQL
  override val outputTable = BQTableDetails(
    "twttr-recos-ml-prod",
    "multi_type_simclusters",
    "offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420_adhoc")
  override val keyValDatasetOutputPath = Config.IIKFHL420EL420ANNOutputPath
  override val tweetEmbeddingsGenerationEmbeddingLength: Int = 420
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] = {
    OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
  }
}

/**
 * Scio job for adhoc run for tweet recommendations from MTS Consumer Embeddings
 */
object MTSConsumerEmbeddingsTweetsANNBQAdhocJob extends TweetsANNJob {
  override val isAdhoc = true
  override val getConsumerEmbeddingsSQLFunc = getMTSConsumerEmbeddingsFav420P420MSQL
  override val outputTable = BQTableDetails(
    "twttr-recos-ml-prod",
    "multi_type_simclusters",
    "offline_tweet_recommendations_from_mts_consumer_embeddings_adhoc")
  override val keyValDatasetOutputPath = Config.MTSConsumerEmbeddingsANNOutputPath
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFromMtsConsumerEmbeddingsScalaDataset
}

/**
Scio job for batch run for tweet recommendations from IIKF 420
The schedule cmd needs to be run only if there is any change in the config
 */
object IIKF420TweetsANNBQBatchJob extends TweetsANNJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn420SQL
  override val outputTable = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_from_interested_in_420M_420K_420")
  override val keyValDatasetOutputPath = Config.IIKFANNOutputPath
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFromInterestedIn420M420K420ScalaDataset
}

/**
Scio job for batch run for tweet recommendations from IIKF 420 with parameter setup:
 - Half Life: None, no decay, direct sum
 - Embedding Length: 420
The schedule cmd needs to be run only if there is any change in the config
 */
object IIKF420Hl420El420TweetsANNBQBatchJob extends TweetsANNJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn420SQL
  override val outputTable = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420")
  override val keyValDatasetOutputPath = Config.IIKFHL420EL420ANNOutputPath
  override val tweetEmbeddingsGenerationHalfLife: Int = -420
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
}

/**
Scio job for batch run for tweet recommendations from IIKF 420 with parameter setup:
 - Half Life: 420hrs
 - Embedding Length: 420
The schedule cmd needs to be run only if there is any change in the config
 */
object IIKF420Hl420El420TweetsANNBQBatchJob extends TweetsANNJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn420SQL
  override val outputTable = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420")
  override val keyValDatasetOutputPath = Config.IIKFHL420EL420ANNOutputPath
  override val tweetEmbeddingsGenerationHalfLife: Int = 420 // 420hrs in ms
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
}

/**
Scio job for batch run for tweet recommendations from IIKF 420 with parameter setup:
 - Half Life: 420hrs
 - Embedding Length: 420
The schedule cmd needs to be run only if there is any change in the config
 */
object IIKF420Hl420El420TweetsANNBQBatchJob extends TweetsANNJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn420SQL
  override val outputTable = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420")
  override val keyValDatasetOutputPath = Config.IIKFHL420EL420ANNOutputPath
  override val tweetEmbeddingsGenerationHalfLife: Int = 420 // 420hrs in ms
  override val tweetEmbeddingsGenerationEmbeddingLength: Int = 420
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
}

/**
Scio job for batch run for tweet recommendations from IIKF 420 with parameter setup:
 - Half Life: 420hrs
 - Embedding Length: 420
The schedule cmd needs to be run only if there is any change in the config
 */
object IIKF420Hl420El420TweetsANNBQBatchJob extends TweetsANNJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getInterestedIn420SQL
  override val outputTable = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420")
  override val keyValDatasetOutputPath = Config.IIKFHL420EL420ANNOutputPath
  override val tweetEmbeddingsGenerationEmbeddingLength: Int = 420
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFromInterestedIn420M420K420Hl420El420ScalaDataset
}

/**
Scio job for batch run for tweet recommendations from MTS Consumer Embeddings
The schedule cmd needs to be run only if there is any change in the config
 */
object MTSConsumerEmbeddingsTweetsANNBQBatchJob extends TweetsANNJob {
  override val isAdhoc = false
  override val getConsumerEmbeddingsSQLFunc = getMTSConsumerEmbeddingsFav420P420MSQL
  override val outputTable = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_from_mts_consumer_embeddings")
  override val keyValDatasetOutputPath = Config.MTSConsumerEmbeddingsANNOutputPath
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFromMtsConsumerEmbeddingsScalaDataset
}
