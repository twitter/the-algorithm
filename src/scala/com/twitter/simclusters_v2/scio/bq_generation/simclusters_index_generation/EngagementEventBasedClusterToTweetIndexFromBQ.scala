package com.twitter.simclusters_v2.scio.bq_generation
package simclusters_index_generation

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.twitter.simclusters_v2.scio.bq_generation.common.BQGenerationUtil.getNSFWTweetIdDenylistSQL
import com.twitter.simclusters_v2.scio.bq_generation.common.BQGenerationUtil.getTweetIdWithFavCountSQL
import com.twitter.simclusters_v2.scio.bq_generation.common.BQGenerationUtil.getTweetIdWithMediaAndNSFWAuthorFilterSQL
import com.twitter.simclusters_v2.scio.bq_generation.common.BQGenerationUtil.getUserTweetEngagementEventPairSQL
import com.twitter.simclusters_v2.scio.bq_generation.common.BQGenerationUtil.generateClusterTopTweetIntersectionWithFavBasedIndexSQL
import com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.Config.simclustersEngagementBasedIndexGenerationSQLPath
import com.twitter.simclusters_v2.scio.bq_generation.common.IndexGenerationUtil.TopKTweetsForClusterKey
import com.twitter.simclusters_v2.scio.bq_generation.common.IndexGenerationUtil.parseClusterTopKTweetsFn
import com.twitter.wtf.beam.bq_embedding_export.BQQueryUtils
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO
import org.joda.time.DateTime

object EngagementEventBasedClusterToTweetIndexFromBQ {

  /*
   * Reads the user-tweet-interaction table and apply tweet fav count filter
   * Returns the post processed table results in SQL string format
   *
* Input:
   *   - startTime: DateTime
   *       The earliest timestamp from the user-tweet-interaction table
   *   - endTime: DateTime
   *       The latest timestamp from the user-tweet-interaction table
   *   - minFavCount: Int
   *       Whether we want to enable tweet fav count filters
   *
* Return:
   *   String - Post processed table results in SQL string format
   */
  def getTweetInteractionTableWithFavCountFilter(
    startTime: DateTime,
    endTime: DateTime,
    minFavCount: Int
  ): String = {
    if (minFavCount > 0) {
      val tweetFavCountSQL = getTweetIdWithFavCountSQL(startTime, endTime)
      s"""
         |  WITH tweet_fav_count AS (${tweetFavCountSQL})
         |  SELECT userId, tweetId, tsMillis
         |  FROM user_tweet_interaction_with_min_interaction_count_filter
         |  JOIN tweet_fav_count
         |  USING(tweetId)
         |  WHERE tweet_fav_count.favCount >= ${minFavCount}
         |""".stripMargin
    } else {
      // Directly read from the table without applying any filters
      s"SELECT userId, tweetId, tsMillis FROM user_tweet_interaction_with_min_interaction_count_filter"
    }
  }

  /*
   * Reads the user-tweet-interaction table and apply health and video filters if specified.
   * Returns the post processed table results in SQL string format
   *
  * Input:
   *   - tableName: String
   *       Schema of the table
   *         userId: Long
   *         tweetId: Long
   *         tsMillis: Long
   *   - startTime: DateTime
   *       The earliest timestamp from the user-tweet-interaction table
   *   - endTime: DateTime
   *       The latest timestamp from the user-tweet-interaction table
   *   - enableHealthAndVideoFilters: Boolean
   *       Whether we want to enable health filters and video only filters
   *
  * Return:
   *   String - Post processed table results in SQL string format
   */
  def getTweetInteractionTableWithHealthFilter(
    startTime: DateTime,
    endTime: DateTime,
    enableHealthAndVideoFilters: Boolean,
  ): String = {
    if (enableHealthAndVideoFilters) {
      // Get SQL for tweets with media and NSFW filter
      val tweetWithMediaAndNSFWAuthorFilterSQL = getTweetIdWithMediaAndNSFWAuthorFilterSQL(
        startTime,
        endTime,
        filterMediaType = Some(3), // VideoTweets MediaType = 3
        filterNSFWAuthor = true
      )
      // Get SQL for NSFW tweet id deny list
      val nsfwTweetDenylistSQL = getNSFWTweetIdDenylistSQL(startTime, endTime)
      // Combine the health filter SQLs
      s"""
         |SELECT userId, tweetId, tsMillis FROM user_tweet_interaction_with_fav_count_filter JOIN (
         |  ${tweetWithMediaAndNSFWAuthorFilterSQL}
         |    AND tweetId NOT IN (${nsfwTweetDenylistSQL})
         |) USING(tweetId)
         |""".stripMargin
    } else {
      // Directly read from the table without applying any filters
      s"SELECT userId, tweetId, tsMillis FROM user_tweet_interaction_with_fav_count_filter"
    }
  }

  def getTopKTweetsForClusterKeyBQ(
    sc: ScioContext,
    queryTimestamp: DateTime,
    maxTweetAgeHours: Int,
    consumerEmbeddingsSQL: String,
    userTweetEngagementEventPairSqlPath: String,
    userTweetEngagementEventPairTemplateVariable: Map[String, String],
    enableHealthAndVideoFilters: Boolean,
    enableFavClusterTopKTweetsIntersection: Boolean,
    minInteractionCount: Int,
    minFavCount: Int,
    tweetEmbeddingsLength: Int,
    tweetEmbeddingsHalfLife: Int,
    minEngagementPerCluster: Int,
    clusterTopKTweets: Int
  ): SCollection[TopKTweetsForClusterKey] = {
    // Define template variables which we would like to be replaced in the corresponding sql file
    val startTime = queryTimestamp.minusHours(maxTweetAgeHours)
    val endTime = queryTimestamp

    val indexGenerationTemplateVariables =
      Map(
        "HALF_LIFE" -> tweetEmbeddingsHalfLife.toString,
        "CURRENT_TS" -> queryTimestamp.toString(),
        "START_TIME" -> startTime.toString(),
        "END_TIME" -> endTime.toString(),
        "USER_TWEET_ENGAGEMENT_TABLE_SQL" ->
          getUserTweetEngagementEventPairSQL(
            startTime,
            endTime,
            userTweetEngagementEventPairSqlPath,
            userTweetEngagementEventPairTemplateVariable
          ),
        // Min interaction count filter
        "MIN_INTERACTION_COUNT" -> minInteractionCount.toString,
        // Min fav count filter
        "TWEET_INTERACTION_WITH_FAV_COUNT_FILTER_SQL" -> getTweetInteractionTableWithFavCountFilter(
          startTime,
          endTime,
          minFavCount
        ),
        // Health filter
        "TWEET_INTERACTION_WITH_HEALTH_FILTER_SQL" -> getTweetInteractionTableWithHealthFilter(
          startTime,
          endTime,
          enableHealthAndVideoFilters),
        "CONSUMER_EMBEDDINGS_SQL" -> consumerEmbeddingsSQL,
        "TWEET_EMBEDDING_LENGTH" -> tweetEmbeddingsLength.toString,
        "MIN_ENGAGEMENT_PER_CLUSTER" -> minEngagementPerCluster.toString,
        "CLUSTER_TOP_K_TWEETS" -> clusterTopKTweets.toString
      )
    val query = BQQueryUtils.getBQQueryFromSqlFile(
      simclustersEngagementBasedIndexGenerationSQLPath,
      indexGenerationTemplateVariables)

    val postFilterQuery = if (enableFavClusterTopKTweetsIntersection) {
      generateClusterTopTweetIntersectionWithFavBasedIndexSQL(
        startTime,
        endTime,
        clusterTopKTweets,
        query)
    } else {
      query
    }
    // Generate SimClusters cluster-to-tweet index
    sc.customInput(
      s"SimClusters cluster-to-tweet index generation BQ job",
      BigQueryIO
        .read(parseClusterTopKTweetsFn(tweetEmbeddingsHalfLife))
        .fromQuery(postFilterQuery)
        .usingStandardSql()
    )
  }
}
