package com.X.follow_recommendations.common.candidate_sources.base

import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.client.Fetcher

abstract class StratoFetcherWithUnitViewSource[K, V](
  fetcher: Fetcher[K, Unit, V],
  override val identifier: CandidateSourceIdentifier)
    extends StratoFetcherSource[K, Unit, V](fetcher, Unit, identifier)
