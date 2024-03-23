package com.ExTwitter.follow_recommendations.common.candidate_sources.sims

import com.ExTwitter.hermit.candidate.thriftscala.Candidates
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.client.Fetcher

abstract class StratoBasedSimsCandidateSourceWithUnitView(
  fetcher: Fetcher[Long, Unit, Candidates],
  override val identifier: CandidateSourceIdentifier)
    extends StratoBasedSimsCandidateSource[Unit](fetcher, Unit, identifier)
