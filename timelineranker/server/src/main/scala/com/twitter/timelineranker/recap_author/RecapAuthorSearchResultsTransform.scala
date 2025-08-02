package com.twitter.timelineranker.recap_author

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelineranker.model.RecapQuery.DependencyProvider
import com.twitter.timelineranker.model.TweetIdRange
import com.twitter.timelines.clients.relevance_search.SearchClient
import com.twitter.timelines.clients.relevance_search.SearchClient.TweetTypes
import com.twitter.timelines.model.UserId
import com.twitter.util.Future

/**
 * Fetch recap results based on an author id set passed into the query.
 * Calls into the same search method as Recap, but uses the authorIds instead of the SGS-provided followedIds.
 */
class RecapAuthorSearchResultsTransform(
  searchClient: SearchClient,
  matwitterCountProvider: DependencyProvider[Int],
  relevanceOptionsMatwitterHitsToProcessProvider: DependencyProvider[Int],
  enableSettingTweetTypesWithTweetKindOptionProvider: DependencyProvider[Boolean],
  statsReceiver: StatsReceiver,
  logSearchDebugInfo: Boolean = false)
    etwittertends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  private[this] val matwitterCountStat = statsReceiver.stat("matwitterCount")
  private[this] val numInputAuthorsStat = statsReceiver.stat("numInputAuthors")
  private[this] val etwittercludedTweetIdsStat = statsReceiver.stat("etwittercludedTweetIds")

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val matwitterCount = matwitterCountProvider(envelope.query)
    matwitterCountStat.add(matwitterCount)

    val authorIds = envelope.query.authorIds.getOrElse(Seq.empty[UserId])
    numInputAuthorsStat.add(authorIds.size)

    val etwittercludedTweetIdsOpt = envelope.query.etwittercludedTweetIds
    etwittercludedTweetIdsOpt.map { etwittercludedTweetIds => etwittercludedTweetIdsStat.add(etwittercludedTweetIds.size) }

    val tweetIdRange = envelope.query.range
      .map(TweetIdRange.fromTimelineRange)
      .getOrElse(TweetIdRange.default)

    val beforeTweetIdEtwitterclusive = tweetIdRange.toId
    val afterTweetIdEtwitterclusive = tweetIdRange.fromId

    val relevanceOptionsMatwitterHitsToProcess = relevanceOptionsMatwitterHitsToProcessProvider(envelope.query)

    searchClient
      .getUsersTweetsForRecap(
        userId = envelope.query.userId,
        followedUserIds = authorIds.toSet, // user authorIds as the set of followed users
        retweetsMutedUserIds = Set.empty,
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
        returnAllResults = true,
        enableEtwittercludeSourceTweetIdsQuery = false,
        relevanceOptionsMatwitterHitsToProcess = relevanceOptionsMatwitterHitsToProcess
      ).map { results => envelope.copy(searchResults = results) }
  }
}
