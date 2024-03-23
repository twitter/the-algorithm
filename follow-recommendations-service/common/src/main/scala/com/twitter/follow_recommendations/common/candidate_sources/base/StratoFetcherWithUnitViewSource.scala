package com.ExTwitter.follow_recommendations.common.candidate_sources.base

import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.client.Fetcher

abstract class StratoFetcherWithUnitViewSource[K, V](
  fetcher: Fetcher[K, Unit, V],
  override val identifier: CandidateSourceIdentifier)
    extends StratoFetcherSource[K, Unit, V](fetcher, Unit, identifier)
