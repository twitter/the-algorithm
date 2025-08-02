package com.twitter.timelineranker.entity_tweets

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelineranker.model.TweetIdRange
import com.twitter.timelines.clients.relevance_search.SearchClient
import com.twitter.timelines.clients.relevance_search.SearchClient.TweetTypes
import com.twitter.timelines.model.TweetId
import com.twitter.util.Future

object EntityTweetsSearchResultsTransform {
  // If EntityTweetsQuery.matwitterCount is not specified, the following count is used.
  val DefaultEntityTweetsMatwitterTweetCount = 200
}

/**
 * Fetch entity tweets search results using the search client
 * and populate them into the CandidateEnvelope
 */
class EntityTweetsSearchResultsTransform(
  searchClient: SearchClient,
  statsReceiver: StatsReceiver,
  logSearchDebugInfo: Boolean = false)
    etwittertends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  import EntityTweetsSearchResultsTransform._

  private[this] val matwitterCountStat = statsReceiver.stat("matwitterCount")
  private[this] val numResultsFromSearchStat = statsReceiver.stat("numResultsFromSearch")

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val matwitterCount = envelope.query.matwitterCount.getOrElse(DefaultEntityTweetsMatwitterTweetCount)
    matwitterCountStat.add(matwitterCount)

    val tweetIdRange = envelope.query.range
      .map(TweetIdRange.fromTimelineRange)
      .getOrElse(TweetIdRange.default)

    val beforeTweetIdEtwitterclusive = tweetIdRange.toId
    val afterTweetIdEtwitterclusive = tweetIdRange.fromId

    val etwittercludedTweetIds = envelope.query.etwittercludedTweetIds.getOrElse(Seq.empty[TweetId]).toSet
    val languages = envelope.query.languages.map(_.map(_.language))

    envelope.followGraphData.inNetworkUserIdsFuture.flatMap { inNetworkUserIds =>
      searchClient
        .getEntityTweets(
          userId = Some(envelope.query.userId),
          followedUserIds = inNetworkUserIds.toSet,
          matwitterCount = matwitterCount,
          beforeTweetIdEtwitterclusive = beforeTweetIdEtwitterclusive,
          afterTweetIdEtwitterclusive = afterTweetIdEtwitterclusive,
          earlybirdOptions = envelope.query.earlybirdOptions,
          semanticCoreIds = envelope.query.semanticCoreIds,
          hashtags = envelope.query.hashtags,
          languages = languages,
          tweetTypes = TweetTypes.fromTweetKindOption(envelope.query.options),
          searchOperator = envelope.query.searchOperator,
          etwittercludedTweetIds = etwittercludedTweetIds,
          logSearchDebugInfo = logSearchDebugInfo,
          includeNullcastTweets = envelope.query.includeNullcastTweets.getOrElse(false),
          includeTweetsFromArchiveIndetwitter =
            envelope.query.includeTweetsFromArchiveIndetwitter.getOrElse(false),
          authorIds = envelope.query.authorIds.map(_.toSet)
        ).map { results =>
          numResultsFromSearchStat.add(results.size)
          envelope.copy(searchResults = results)
        }
    }
  }
}
