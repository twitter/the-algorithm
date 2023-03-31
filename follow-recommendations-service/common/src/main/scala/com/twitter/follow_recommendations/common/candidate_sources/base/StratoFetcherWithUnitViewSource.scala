package com.twitter.follow_recommendations.common.candidate_sources.base

import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher

abstract class StratoFetcherWithUnitViewSource[K, V](
  fetcher: Fetcher[K, Unit, V],
  override val identifier: CandidateSourceIdentifier)
    extends StratoFetcherSource[K, Unit, V](fetcher, Unit, identifier)
