package com.twitter.simclusters_v2.scio.bq_generation
package ftr_tweet

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
import com.twitter.simclusters_v2.scio.bq_generation.common.BQTableDetails
import com.twitter.simclusters_v2.scio.bq_generation.common.BQGenerationUtil.getInterestedIn2020SQL
import com.twitter.simclusters_v2.thriftscala.CandidateTweets
import com.twitter.simclusters_v2.thriftscala.CandidateTweetsList
import com.twitter.tcdc.bqblaster.beam.syntax._
import com.twitter.tcdc.bqblaster.core.avro.TypedProjection
import com.twitter.tcdc.bqblaster.core.transform.RootTransform
import java.time.Instant
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO
import com.twitter.simclusters_v2.thriftscala.CandidateTweet
import org.apache.avro.generic.GenericData
import scala.collection.mutable.ListBuffer
import org.apache.beam.sdk.io.gcp.bigquery.SchemaAndRecord
import org.apache.beam.sdk.transforms.SerializableFunction
import org.apache.avro.generic.GenericRecord
import com.twitter.wtf.beam.bq_embedding_export.BQQueryUtils

trait FTRJob extends ScioBeamJob[DateRangeOptions] {
  // Configs to set for different type of embeddings and jobs
  val isAdhoc: Boolean
  val outputTable: BQTableDetails
  val keyValDatasetOutputPath: String
  val tweetRecommentationsSnapshotDataset: KeyValDALDataset[KeyVal[Long, CandidateTweetsList]]
  val scoreKey: String
  val scoreColumn: String

  // Base configs
  val projectId = "twttr-recos-ml-prod"
  val environment: DAL.Env = if (isAdhoc) DAL.Environment.Dev else DAL.Environment.Prod

  override implicit def scroogeCoder[T <: ThriftStruct: Manifest]: Coder[T] =
    ThriftStructLazyBinaryScroogeCoder.scroogeCoder

  override def configurePipeline(sc: ScioContext, opts: DateRangeOptions): Unit = {
    // The time when the job is scheduled
    val queryTimestamp = opts.interval.getEnd

    // Parse tweetId candidates column
    def parseTweetIdColumn(
      genericRecord: GenericRecord,
      columnName: String
    ): List[CandidateTweet] = {
      val tweetIds: GenericData.Array[GenericRecord] =
        genericRecord.get(columnName).asInstanceOf[GenericData.Array[GenericRecord]]
      val results: ListBuffer[CandidateTweet] = new ListBuffer[CandidateTweet]()
      tweetIds.forEach((sc: GenericRecord) => {
        results += CandidateTweet(
          tweetId = sc.get("tweetId").toString.toLong,
          score = Some(sc.get("cosineSimilarityScore").toString.toDouble)
        )
      })
      results.toList
    }

    //Function that parses the GenericRecord results we read from BQ
    val parseUserToTweetRecommendationsFunc =
      new SerializableFunction[SchemaAndRecord, UserToTweetRecommendations] {
        override def apply(record: SchemaAndRecord): UserToTweetRecommendations = {
          val genericRecord: GenericRecord = record.getRecord
          UserToTweetRecommendations(
            userId = genericRecord.get("userId").toString.toLong,
            tweetCandidates = parseTweetIdColumn(genericRecord, "tweets"),
          )
        }
      }

    val tweetEmbeddingTemplateVariables =
      Map(
        "START_TIME" -> queryTimestamp.minusDays(1).toString(),
        "END_TIME" -> queryTimestamp.toString(),
        "TWEET_SAMPLE_RATE" -> Config.TweetSampleRate.toString,
        "ENG_SAMPLE_RATE" -> Config.EngSampleRate.toString,
        "MIN_TWEET_FAVS" -> Config.MinTweetFavs.toString,
        "MIN_TWEET_IMPS" -> Config.MinTweetImps.toString,
        "MAX_TWEET_FTR" -> Config.MaxTweetFTR.toString,
        "MAX_USER_LOG_N_IMPS" -> Config.MaxUserLogNImps.toString,
        "MAX_USER_LOG_N_FAVS" -> Config.MaxUserLogNFavs.toString,
        "MAX_USER_FTR" -> Config.MaxUserFTR.toString,
        "TWEET_EMBEDDING_LENGTH" -> Config.SimClustersTweetEmbeddingsGenerationEmbeddingLength.toString,
        "HALFLIFE" -> Config.SimClustersTweetEmbeddingsGenerationHalfLife.toString,
        "SCORE_COLUMN" -> scoreColumn,
        "SCORE_KEY" -> scoreKey,
      )

    val tweetEmbeddingSql = BQQueryUtils.getBQQueryFromSqlFile(
      "/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/sql/ftr_tweet_embeddings.sql",
      tweetEmbeddingTemplateVariables)
    val consumerEmbeddingSql = getInterestedIn2020SQL(queryTimestamp, 14)

    val tweetRecommendationsTemplateVariables =
      Map(
        "CONSUMER_EMBEDDINGS_SQL" -> consumerEmbeddingSql,
        "TWEET_EMBEDDINGS_SQL" -> tweetEmbeddingSql,
        "TOP_N_CLUSTER_PER_SOURCE_EMBEDDING" -> Config.SimClustersANNTopNClustersPerSourceEmbedding.toString,
        "TOP_M_TWEETS_PER_CLUSTER" -> Config.SimClustersANNTopMTweetsPerCluster.toString,
        "TOP_K_TWEETS_PER_USER_REQUEST" -> Config.SimClustersANNTopKTweetsPerUserRequest.toString,
      )
    val tweetRecommendationsSql = BQQueryUtils.getBQQueryFromSqlFile(
      "/com/twitter/simclusters_v2/scio/bq_generation/sql/tweets_ann.sql",
      tweetRecommendationsTemplateVariables)

    val tweetRecommendations = sc.customInput(
      s"SimClusters FTR BQ ANN",
      BigQueryIO
        .read(parseUserToTweetRecommendationsFunc)
        .fromQuery(tweetRecommendationsSql)
        .usingStandardSql()
    )

    //Setup BQ writer
    val ingestionTime = opts.getDate().value.getEnd.toDate
    val bqFieldsTransform = RootTransform
      .Builder()
      .withPrependedFields("ingestionTime" -> TypedProjection.fromConstant(ingestionTime))
    val timePartitioning = new TimePartitioning()
      .setType("HOUR").setField("ingestionTime").setExpirationMs(3.days.inMilliseconds)
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
      .saveAsCustomOutput(s"WriteToBQTable - $outputTable", bqWriter)

    val RootMHPath: String = Config.FTRRootMHPath
    val AdhocRootPath = Config.FTRAdhocpath

    // Save Tweet ANN results as KeyValSnapshotDataset
    tweetRecommendations
      .map { userToTweetRecommendations =>
        KeyVal(
          userToTweetRecommendations.userId,
          CandidateTweetsList(userToTweetRecommendations.tweetCandidates))
      }.saveAsCustomOutput(
        name = "WriteFtrTweetRecommendationsToKeyValDataset",
        DAL.writeVersionedKeyVal(
          tweetRecommentationsSnapshotDataset,
          PathLayout.VersionedPath(prefix =
            ((if (!isAdhoc)
                RootMHPath
              else
                AdhocRootPath)
              + keyValDatasetOutputPath)),
          instant = Instant.ofEpochMilli(opts.interval.getEndMillis - 1L),
          environmentOverride = environment,
        )
      )
  }

}

object FTRAdhocJob extends FTRJob {
  override val isAdhoc = true
  override val outputTable: BQTableDetails =
    BQTableDetails("twttr-recos-ml-prod", "simclusters", "offline_tweet_recommendations_ftr_adhoc")
  override val keyValDatasetOutputPath = Config.IIKFFTRAdhocANNOutputPath

  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFtrAdhocScalaDataset
  override val scoreColumn = "ftrat5_decayed_pop_bias_1000_rank_decay_1_1_embedding"
  override val scoreKey = "ftrat5_decayed_pop_bias_1000_rank_decay_1_1"
}

object IIKF2020DecayedSumBatchJobProd extends FTRJob {
  override val isAdhoc = false
  override val outputTable: BQTableDetails = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_decayed_sum"
  )
  override val keyValDatasetOutputPath = Config.IIKFDecayedSumANNOutputPath
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsDecayedSumScalaDataset
  override val scoreColumn = "dec_sum_logfavScoreClusterNormalizedOnly_embedding"
  override val scoreKey = "dec_sum_logfavScoreClusterNormalizedOnly"
}

object IIKF2020FTRAt5Pop1000batchJobProd extends FTRJob {
  override val isAdhoc = false
  override val outputTable: BQTableDetails = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_ftrat5_pop_biased_1000")
  override val keyValDatasetOutputPath = Config.IIKFFTRAt5Pop1000ANNOutputPath
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFtrat5PopBiased1000ScalaDataset
  override val scoreColumn = "ftrat5_decayed_pop_bias_1000_rank_decay_1_1_embedding"
  override val scoreKey = "ftrat5_decayed_pop_bias_1000_rank_decay_1_1"
}

object IIKF2020FTRAt5Pop10000batchJobProd extends FTRJob {
  override val isAdhoc = false
  override val outputTable: BQTableDetails = BQTableDetails(
    "twttr-bq-cassowary-prod",
    "user",
    "offline_tweet_recommendations_ftrat5_pop_biased_10000")
  override val keyValDatasetOutputPath = Config.IIKFFTRAt5Pop10000ANNOutputPath
  override val tweetRecommentationsSnapshotDataset: KeyValDALDataset[
    KeyVal[Long, CandidateTweetsList]
  ] =
    OfflineTweetRecommendationsFtrat5PopBiased10000ScalaDataset
  override val scoreColumn = "ftrat5_decayed_pop_bias_10000_rank_decay_1_1_embedding"
  override val scoreKey = "ftrat5_decayed_pop_bias_10000_rank_decay_1_1"
}

case class UserToTweetRecommendations(
  userId: Long,
  tweetCandidates: List[CandidateTweet])
