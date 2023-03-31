package com.twitter.cr_mixer.filter

import com.twitter.cr_mixer.model.CandidateGeneratorQuery
import com.twitter.cr_mixer.model.InitialCandidate
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.util.Future

import javax.inject.Inject
import javax.inject.Singleton

/***
 *
 * Run filters sequentially for UTEG candidate generator. The structure is copied from PreRankFilterRunner.
 */
@Singleton
class UtegFilterRunner @Inject() (
  inNetworkFilter: InNetworkFilter,
  utegHealthFilter: UtegHealthFilter,
  retweetFilter: RetweetFilter,
  globalStats: StatsReceiver) {

  private val scopedStats = globalStats.scope(this.getClass.getCanonicalName)

  val orderedFilters: Seq[FilterBase] = Seq(
    inNetworkFilter,
    utegHealthFilter,
    retweetFilter
  )

  def runSequentialFilters[CGQueryType <: CandidateGeneratorQuery](
    request: CGQueryType,
    candidates: Seq[Seq[InitialCandidate]],
  ): Future[Seq[Seq[InitialCandidate]]] = {
    UtegFilterRunner.runSequentialFilters(
      request,
      candidates,
      orderedFilters,
      scopedStats
    )
  }

}

object UtegFilterRunner {
  private def recordCandidateStatsBeforeFilter(
    candidates: Seq[Seq[InitialCandidate]],
    statsReceiver: StatsReceiver
  ): Unit = {
    statsReceiver
      .counter("empty_sources", "before").incr(
        candidates.count {
          _.isEmpty
        }
      )
    candidates.foreach { candidate =>
      statsReceiver.counter("candidates", "before").incr(candidate.size)
    }
  }

  private def recordCandidateStatsAfterFilter(
    candidates: Seq[Seq[InitialCandidate]],
    statsReceiver: StatsReceiver
  ): Unit = {
    statsReceiver
      .counter("empty_sources", "after").incr(
        candidates.count {
          _.isEmpty
        }
      )
    candidates.foreach { candidate =>
      statsReceiver.counter("candidates", "after").incr(candidate.size)
    }
  }

  /*
  Helper function for running some candidates through a sequence of filters
   */
  private[filter] def runSequentialFilters[CGQueryType <: CandidateGeneratorQuery](
    request: CGQueryType,
    candidates: Seq[Seq[InitialCandidate]],
    filters: Seq[FilterBase],
    statsReceiver: StatsReceiver
  ): Future[Seq[Seq[InitialCandidate]]] =
    filters.foldLeft(Future.value(candidates)) {
      case (candsFut, filter) =>
        candsFut.flatMap { cands =>
          recordCandidateStatsBeforeFilter(cands, statsReceiver.scope(filter.name))
          filter
            .filter(cands, filter.requestToConfig(request))
            .map { filteredCands =>
              recordCandidateStatsAfterFilter(filteredCands, statsReceiver.scope(filter.name))
              filteredCands
            }
        }
    }
}
