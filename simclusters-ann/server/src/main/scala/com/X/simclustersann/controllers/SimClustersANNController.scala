package com.X.simclustersann.controllers

import com.X.conversions.DurationOps._
import com.X.finatra.thrift.Controller
import com.X.simclustersann.thriftscala.SimClustersANNService.GetTweetCandidates
import com.X.simclustersann.thriftscala.SimClustersANNService
import com.X.simclustersann.thriftscala.Query
import com.X.simclustersann.thriftscala.SimClustersANNTweetCandidate
import com.X.scrooge.Request
import com.X.scrooge.Response
import javax.inject.Inject
import com.X.finagle.Service
import com.X.finagle.stats.StatsReceiver
import com.X.inject.annotations.Flag
import com.X.simclustersann.candidate_source.{
  SimClustersANNCandidateSource => SANNSimClustersANNCandidateSource
}
import com.X.simclustersann.common.FlagNames
import com.X.simclustersann.filters.GetTweetCandidatesResponseStatsFilter
import com.X.simclustersann.filters.SimClustersAnnVariantFilter
import com.X.util.Future
import com.X.util.JavaTimer
import com.X.util.Timer

class SimClustersANNController @Inject() (
  @Flag(FlagNames.ServiceTimeout) serviceTimeout: Int,
  variantFilter: SimClustersAnnVariantFilter,
  getTweetCandidatesResponseStatsFilter: GetTweetCandidatesResponseStatsFilter,
  sannCandidateSource: SANNSimClustersANNCandidateSource,
  globalStats: StatsReceiver)
    extends Controller(SimClustersANNService) {

  import SimClustersANNController._

  private val stats: StatsReceiver = globalStats.scope(this.getClass.getCanonicalName)
  private val timer: Timer = new JavaTimer(true)

  val filteredService: Service[Request[GetTweetCandidates.Args], Response[
    Seq[SimClustersANNTweetCandidate]
  ]] = {
    variantFilter
      .andThen(getTweetCandidatesResponseStatsFilter)
      .andThen(Service.mk(handler))
  }

  handle(GetTweetCandidates).withService(filteredService)

  private def handler(
    request: Request[GetTweetCandidates.Args]
  ): Future[Response[Seq[SimClustersANNTweetCandidate]]] = {
    val query: Query = request.args.query
    val simClustersANNCandidateSourceQuery = SANNSimClustersANNCandidateSource.Query(
      sourceEmbeddingId = query.sourceEmbeddingId,
      config = query.config
    )

    val result = sannCandidateSource
      .get(simClustersANNCandidateSourceQuery).map {
        case Some(tweetCandidatesSeq) =>
          Response(tweetCandidatesSeq.map { tweetCandidate =>
            SimClustersANNTweetCandidate(
              tweetId = tweetCandidate.tweetId,
              score = tweetCandidate.score
            )
          })
        case None =>
          DefaultResponse
      }

    result.raiseWithin(serviceTimeout.milliseconds)(timer).rescue {
      case e: Throwable =>
        stats.scope("failures").counter(e.getClass.getCanonicalName).incr()
        Future.value(DefaultResponse)
    }
  }
}

object SimClustersANNController {
  val DefaultResponse: Response[Seq[SimClustersANNTweetCandidate]] = Response(Seq.empty)
}
