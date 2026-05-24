package com.twitter.simclusters_v2.scio.bq_generation
package tweets_ann

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.twitter.simclusters_v2.thriftscala.CandidateTweet
import com.twitter.wtf.beam.bq_embedding_export.BQQueryUtils
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO
import org.apache.beam.sdk.io.gcp.bigquery.SchemaAndRecord
import org.apache.beam.sdk.transforms.SerializableFunction
import org.joda.time.DateTime
import scala.collection.mutable.ListBuffer

object TweetsANNFromBQ {
  // Default ANN config variables
  val topNClustersPerSourceEmbedding = Config.SimClustersANNTopNClustersPerSourceEmbedding
  val topMTweetsPerCluster = Config.SimClustersANNTopMTweetsPerCluster
  val topKTweetsPerUserRequest = Config.SimClustersANNTopKTweetsPerUserRequest

  // SQL file paths
  val tweetsANNSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/tweets_ann.sql"
  val tweetsEmbeddingGenerationSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/tweet_embeddings_generation.sql"

  // Function that parses the GenericRecord results we read from BQ
  val parseUserToTweetRecommendationsFunc =
    new SerializableFunction[SchemaAndRecord, UserToTweetRecommendations] {
      override def apply(record: SchemaAndRecord): UserToTweetRecommendations = {
        val genericRecord: GenericRecord = record.getRecord()
        UserToTweetRecommendations(
          userId = genericRecord.get("userId").toString.toLong,
          tweetCandidates = parseTweetIdColumn(genericRecord, "tweets"),
        )
      }
    }

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
        score = Some(sc.get("logCosineSimilarityScore").toString.toDouble)
      )
    })
    results.toList
  }

  def getTweetEmbeddingsSQL(
    queryDate: DateTime,
    consumerEmbeddingsSQL: String,
    tweetEmbeddingsSQLPath: String,
    tweetEmbeddingsHalfLife: Int,
    tweetEmbeddingsLength: Int
  ): String = {
    // We read one day of fav events to construct our tweet embeddings
    val templateVariables =
      Map(
        "CONSUMER_EMBEDDINGS_SQL" -> consumerEmbeddingsSQL,
        "QUERY_DATE" -> queryDate.toString(),
        "START_TIME" -> queryDate.minusDays(1).toString(),
        "END_TIME" -> queryDate.toString(),
        "MIN_SCORE_THRESHOLD" -> 0.0.toString,
        "HALF_LIFE" -> tweetEmbeddingsHalfLife.toString,
        "TWEET_EMBEDDING_LENGTH" -> tweetEmbeddingsLength.toString,
        "NO_OLDER_TWEETS_THAN_DATE" -> queryDate.minusDays(1).toString(),
      )
    BQQueryUtils.getBQQueryFromSqlFile(tweetEmbeddingsSQLPath, templateVariables)
  }

  def getTweetRecommendationsBQ(
    sc: ScioContext,
    queryTimestamp: DateTime,
    consumerEmbeddingsSQL: String,
    tweetEmbeddingsHalfLife: Int,
    tweetEmbeddingsLength: Int
  ): SCollection[UserToTweetRecommendations] = {
    // Get the tweet embeddings SQL string based on the provided consumerEmbeddingsSQL
    val tweetEmbeddingsSQL =
      getTweetEmbeddingsSQL(
        queryTimestamp,
        consumerEmbeddingsSQL,
        tweetsEmbeddingGenerationSQLPath,
        tweetEmbeddingsHalfLife,
        tweetEmbeddingsLength
      )

    // Define template variables which we would like to be replaced in the corresponding sql file
    val templateVariables =
      Map(
        "CONSUMER_EMBEDDINGS_SQL" -> consumerEmbeddingsSQL,
        "TWEET_EMBEDDINGS_SQL" -> tweetEmbeddingsSQL,
        "TOP_N_CLUSTER_PER_SOURCE_EMBEDDING" -> topNClustersPerSourceEmbedding.toString,
        "TOP_M_TWEETS_PER_CLUSTER" -> topMTweetsPerCluster.toString,
        "TOP_K_TWEETS_PER_USER_REQUEST" -> topKTweetsPerUserRequest.toString
      )
    val query = BQQueryUtils.getBQQueryFromSqlFile(tweetsANNSQLPath, templateVariables)

    // Run SimClusters ANN on BQ and parse the results
    sc.customInput(
      s"SimClusters BQ ANN",
      BigQueryIO
        .read(parseUserToTweetRecommendationsFunc)
        .fromQuery(query)
        .usingStandardSql()
    )
  }

  case class UserToTweetRecommendations(
    userId: Long,
    tweetCandidates: List[CandidateTweet])
}
