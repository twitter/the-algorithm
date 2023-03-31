package com.twitter.product_mixer.component_library.candidate_source.topics

import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherSeqSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.interests.FollowedTopicsGetterClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowedTopicsCandidateSource @Inject() (
  column: FollowedTopicsGetterClientColumn)
    extends StratoKeyViewFetcherSeqSource[
      Long,
      Unit,
      Long
    ] {
  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("FollowedTopics")

  override val fetcher: Fetcher[Long, Unit, Seq[Long]] = column.fetcher
}
