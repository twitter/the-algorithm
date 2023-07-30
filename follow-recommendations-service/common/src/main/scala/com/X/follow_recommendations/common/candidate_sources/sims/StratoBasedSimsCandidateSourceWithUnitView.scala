package com.X.follow_recommendations.common.candidate_sources.sims

import com.X.hermit.candidate.thriftscala.Candidates
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.client.Fetcher

abstract class StratoBasedSimsCandidateSourceWithUnitView(
  fetcher: Fetcher[Long, Unit, Candidates],
  override val identifier: CandidateSourceIdentifier)
    extends StratoBasedSimsCandidateSource[Unit](fetcher, Unit, identifier)
