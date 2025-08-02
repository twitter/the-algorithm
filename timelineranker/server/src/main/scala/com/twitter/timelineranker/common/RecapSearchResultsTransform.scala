package com.twitter.timelineranker.common

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelineranker.model.RecapQuery.DependencyProvider
import com.twitter.timelineranker.model.TweetIdRange
import com.twitter.timelineranker.parameters.recap.RecapParams
import com.twitter.timelines.clients.relevance_search.SearchClient
import com.twitter.timelines.clients.relevance_search.SearchClient.TweetTypes
import com.twitter.util.Future

/**
 * Fetch recap/recycled search results using the search client
 * and populate them into the CandidateEnvelope
 */
class RecapSearchResultsTransform(
  searchClient: SearchClient,
  matwitterCountProvider: DependencyProvider[Int],
  returnAllResultsProvider: DependencyProvider[Boolean],
  relevanceOptionsMatwitterHitsToProcessProvider: DependencyProvider[Int],
  enableEtwittercludeSourceTweetIdsProvider: DependencyProvider[Boolean],
  enableSettingTweetTypesWithTweetKindOptionProvider: DependencyProvider[Boolean],
  perRequestSearchClientIdProvider: DependencyProvider[Option[String]],
  relevanceSearchProvider: DependencyProvider[Boolean] =
    DependencyProvider.from(RecapParams.EnableRelevanceSearchParam),
  statsReceiver: StatsReceiver,
  logSearchDebugInfo: Boolean = true)
    etwittertends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  private[this] val matwitterCountStat = statsReceiver.stat("matwitterCount")
  private[this] val numResultsFromSearchStat = statsReceiver.stat("numResultsFromSearch")
  private[this] val etwittercludedTweetIdsStat = statsReceiver.stat("etwittercludedTweetIds")

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val matwitterCount = matwitterCountProvider(envelope.query)
    matwitterCountStat.add(matwitterCount)

    val etwittercludedTweetIdsOpt = envelope.query.etwittercludedTweetIds
    etwittercludedTweetIdsOpt.foreach { etwittercludedTweetIds =>
      etwittercludedTweetIdsStat.add(etwittercludedTweetIds.size)
    }

    val tweetIdRange = envelope.query.range
      .map(TweetIdRange.fromTimelineRange)
      .getOrElse(TweetIdRange.default)

    val beforeTweetIdEtwitterclusive = tweetIdRange.toId
    val afterTweetIdEtwitterclusive = tweetIdRange.fromId

    val returnAllResults = returnAllResultsProvider(envelope.query)
    val relevanceOptionsMatwitterHitsToProcess = relevanceOptionsMatwitterHitsToProcessProvider(envelope.query)

    Future
      .join(
        envelope.followGraphData.followedUserIdsFuture,
        envelope.followGraphData.retweetsMutedUserIdsFuture
      ).flatMap {
        case (followedIds, retweetsMutedIds) =>
          val followedIdsIncludingSelf = followedIds.toSet + envelope.query.userId

          searchClient
            .getUsersTweetsForRecap(
              userId = envelope.query.userId,
              followedUserIds = followedIdsIncludingSelf,
              retweetsMutedUserIds = retweetsMutedIds,
              matwitterCount = matwitterCount,
              tweetTypes = TweetTypes.fromTweetKindOption(envelope.query.options),
              searchOperator = envelope.query.searchOperator,
              beforeTweetIdEtwitterclusive = beforeTweetIdEtwitterclusive,
              afterTweetIdEtwitterclusive = afterTweetIdEtwitterclusive,
              enableSettingTweetTypesWithTweetKindOption =
                enableSettingTweetTypesWithTweetKindOptionProvider(envelope.query),
              etwittercludedTweetIds = etwittercludedTweetIdsOpt,
              earlybirdOptions = envelope.query.earlybirdOptions,
              getOnlyProtectedTweets = false,
              logSearchDebugInfo = logSearchDebugInfo,
              returnAllResults = returnAllResults,
              enableEtwittercludeSourceTweetIdsQuery =
                enableEtwittercludeSourceTweetIdsProvider(envelope.query),
              relevanceSearch = relevanceSearchProvider(envelope.query),
              searchClientId = perRequestSearchClientIdProvider(envelope.query),
              relevanceOptionsMatwitterHitsToProcess = relevanceOptionsMatwitterHitsToProcess
            ).map { results =>
              numResultsFromSearchStat.add(results.size)
              envelope.copy(searchResults = results)
            }
      }
  }
}
