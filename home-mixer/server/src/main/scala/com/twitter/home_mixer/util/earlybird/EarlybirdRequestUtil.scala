package com.twitter.home_mixer.util.earlybird

import com.twitter.conversions.DurationOps._
import com.twitter.search.common.query.thriftjava.{thriftscala => scq}
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.util.Duration

object EarlybirdRequestUtil {

  // If no EarlybirdOptions.maxNumHitsPerShard is set then default to this value.
  val DefaultMaxHitsToProcess = 1000
  val DefaultSearchProcessingTimeout: Duration = 200.milliseconds
  val DefaultMaxNumResultsPerShard = 100
  val DeafultCollectorParams = scq.CollectorParams(
    // numResultsToReturn defines how many results each EB shard will return to search root
    numResultsToReturn = DefaultMaxNumResultsPerShard,
    // terminationParams.maxHitsToProcess is used for early terminating per shard results fetching.
    terminationParams = Some(
      scq.CollectorTerminationParams(
        maxHitsToProcess = Some(DefaultMaxHitsToProcess),
        timeoutMs = DefaultSearchProcessingTimeout.inMilliseconds.toInt
      ))
  )

  def getTweetsEBFeaturesRequest(
    userId: Option[Long],
    tweetIds: Option[Seq[Long]],
    clientId: Option[String],
    getTweetsFromArchiveIndex: Boolean = false,
    getOnlyProtectedTweets: Boolean = false,
  ): eb.EarlybirdRequest = {

    val candidateSize = tweetIds.getOrElse(Seq.empty).size
    val thriftQuery = eb.ThriftSearchQuery(
      numResults = candidateSize,
      collectConversationId = true,
      rankingMode = eb.ThriftSearchRankingMode.Relevance,
      relevanceOptions = Some(RelevanceSearchUtil.RelevanceOptions),
      collectorParams = Some(DeafultCollectorParams),
      facetFieldNames = Some(RelevanceSearchUtil.FacetsToFetch),
      resultMetadataOptions = Some(RelevanceSearchUtil.MetadataOptions),
      searcherId = userId,
      searchStatusIds = tweetIds.map(_.toSet),
    )

    eb.EarlybirdRequest(
      searchQuery = thriftQuery,
      clientId = clientId,
      getOlderResults = Some(getTweetsFromArchiveIndex),
      getProtectedTweetsOnly = Some(getOnlyProtectedTweets),
      timeoutMs = DefaultSearchProcessingTimeout.inMilliseconds.toInt,
      skipVeryRecentTweets = true,
      // This param decides # of tweets to return from search superRoot and realtime/protected/Archive roots.
      // It takes higher precedence than ThriftSearchQuery.numResults
      numResultsToReturnAtRoot = Some(candidateSize)
    )
  }
}
