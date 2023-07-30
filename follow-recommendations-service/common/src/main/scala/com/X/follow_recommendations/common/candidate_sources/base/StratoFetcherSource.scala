package com.X.follow_recommendations.common.candidate_sources.base

import com.X.follow_recommendations.common.models.CandidateUser
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.stitch.Stitch
import com.X.strato.client.Fetcher

abstract class StratoFetcherSource[K, U, V](
  fetcher: Fetcher[K, U, V],
  view: U,
  override val identifier: CandidateSourceIdentifier)
    extends CandidateSource[K, CandidateUser] {

  def map(user: K, v: V): Seq[CandidateUser]

  override def apply(target: K): Stitch[Seq[CandidateUser]] = {
    fetcher
      .fetch(target, view)
      .map { result =>
        result.v
          .map { candidates => map(target, candidates) }
          .getOrElse(Nil)
          .map(_.withCandidateSource(identifier))
      }
  }
}
