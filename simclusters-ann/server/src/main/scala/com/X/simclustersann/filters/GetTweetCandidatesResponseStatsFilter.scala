package com.X.simclustersann.filters

import com.X.finagle.Service
import com.X.finagle.SimpleFilter
import com.X.finagle.stats.StatsReceiver
import com.X.scrooge.Request
import com.X.scrooge.Response
import com.X.simclustersann.thriftscala.SimClustersANNService
import com.X.util.Future
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTweetCandidatesResponseStatsFilter @Inject() (
  statsReceiver: StatsReceiver)
    extends SimpleFilter[Request[SimClustersANNService.GetTweetCandidates.Args], Response[
      SimClustersANNService.GetTweetCandidates.SuccessType
    ]] {

  private[this] val stats = statsReceiver.scope("method_response_stats").scope("getTweetCandidates")
  private[this] val candidateScoreStats = stats.stat("candidate_score_x1000")
  private[this] val emptyResponseCounter = stats.counter("empty")
  private[this] val nonEmptyResponseCounter = stats.counter("non_empty")
  override def apply(
    request: Request[SimClustersANNService.GetTweetCandidates.Args],
    service: Service[Request[SimClustersANNService.GetTweetCandidates.Args], Response[
      SimClustersANNService.GetTweetCandidates.SuccessType
    ]]
  ): Future[Response[SimClustersANNService.GetTweetCandidates.SuccessType]] = {
    val response = service(request)

    response.onSuccess { successResponse =>
      if (successResponse.value.size == 0)
        emptyResponseCounter.incr()
      else
        nonEmptyResponseCounter.incr()
      successResponse.value.foreach { candidate =>
        candidateScoreStats.add(candidate.score.toFloat * 1000)
      }
    }
    response
  }
}
