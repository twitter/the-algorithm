package com.twitter.follow_recommendations.common.candidate_sources.sims

import com.twitter.hermit.candidate.thriftscala.Candidates
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher

abstract class StratoBasedSimsCandidateSourceWithUnitView(
  fetcher: Fetcher[Long, Unit, Candidates],
  override val identifier: CandidateSourceIdentifier)
    extends StratoBasedSimsCandidateSource[Unit](fetcher, Unit, identifier)
