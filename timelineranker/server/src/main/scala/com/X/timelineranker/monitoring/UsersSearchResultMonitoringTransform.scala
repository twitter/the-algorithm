package com.X.timelineranker.monitoring

import com.X.finagle.stats.StatsReceiver
import com.X.search.earlybird.thriftscala.ThriftSearchResult
import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelineranker.model.RecapQuery.DependencyProvider
import com.X.util.Future

/**
 * Captures tweet counts pre and post transformation for a list of users
 */
class UsersSearchResultMonitoringTransform(
  name: String,
  underlyingTransformer: FutureArrow[CandidateEnvelope, CandidateEnvelope],
  statsReceiver: StatsReceiver,
  debugAuthorListDependencyProvider: DependencyProvider[Seq[Long]])
    extends FutureArrow[CandidateEnvelope, CandidateEnvelope] {

  private val scopedStatsReceiver = statsReceiver.scope(name)
  private val preTransformCounter = (userId: Long) =>
    scopedStatsReceiver
      .scope("pre_transform").scope(userId.toString).counter("debug_author_list")
  private val postTransformCounter = (userId: Long) =>
    scopedStatsReceiver
      .scope("post_transform").scope(userId.toString).counter("debug_author_list")

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val debugAuthorList = debugAuthorListDependencyProvider.apply(envelope.query)
    envelope.searchResults
      .filter(isTweetFromDebugAuthorList(_, debugAuthorList))
      .flatMap(_.metadata)
      .foreach(metadata => preTransformCounter(metadata.fromUserId).incr())

    underlyingTransformer
      .apply(envelope)
      .map { result =>
        envelope.searchResults
          .filter(isTweetFromDebugAuthorList(_, debugAuthorList))
          .flatMap(_.metadata)
          .foreach(metadata => postTransformCounter(metadata.fromUserId).incr())
        result
      }
  }

  private def isTweetFromDebugAuthorList(
    searchResult: ThriftSearchResult,
    debugAuthorList: Seq[Long]
  ): Boolean =
    searchResult.metadata.exists(metadata => debugAuthorList.contains(metadata.fromUserId))

}
