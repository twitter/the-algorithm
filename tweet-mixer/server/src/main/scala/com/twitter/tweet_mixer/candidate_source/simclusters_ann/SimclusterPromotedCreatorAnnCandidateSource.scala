package com.twitter.tweet_mixer.candidate_source.simclusters_ann

import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.simclustersann.thriftscala.SimClustersANNService
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimclusterPromotedCreatorAnnCandidateSource @Inject() (
  simClustersANNServiceNameToClientMapper: Map[String, SimClustersANNService.MethodPerEndpoint],
  memcacheClient: MemcacheStitchClient)
    extends SimClustersAnnCandidateSource(
      simClustersANNServiceNameToClientMapper,
      memcacheClient
    ) {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "SimclusterPromotedCreatorAnnCandidateSource"
  )
}
