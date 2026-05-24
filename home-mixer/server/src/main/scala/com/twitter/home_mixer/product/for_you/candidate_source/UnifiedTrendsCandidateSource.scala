package com.twitter.home_mixer.product.for_you.candidate_source

import com.twitter.events.recos.{thriftscala => t}
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

case class TrendCandidate(candidate: t.TrendCandidate, rank: Option[Int])

@Singleton
class UnifiedTrendsCandidateSource @Inject() (
  unifiedCandidatesService: t.EventsRecosService.MethodPerEndpoint)
    extends CandidateSource[
      t.GetUnfiedCandidatesRequest,
      TrendCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("UnifiedTrends")

  override def apply(request: t.GetUnfiedCandidatesRequest): Stitch[Seq[TrendCandidate]] = {
    Stitch.callFuture(unifiedCandidatesService.getUnifiedCandidates(request)).map {
      unifiedCandidateResponse =>
        val trends: Seq[t.TrendCandidate] = unifiedCandidateResponse.candidates.collect {
          case t.UnifiedCandidate.Trend(trend) => trend
        }

        trends.zipWithIndex.map {
          case (trend, index) => TrendCandidate(trend, Some(index + 1))
        }
    }
  }
}
