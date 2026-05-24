package com.twitter.product_mixer.component_library.candidate_source.interest_discovery

import com.google.inject.Inject
import com.google.inject.Singleton
import com.twitter.inject.Logging
import com.twitter.interests_discovery.{thriftscala => t}
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch

/**
 * Generate a list of related topics results from IDS getRelatedTopics (thrift) endpoint.
 * Returns related topics, given a topic, whereas [[RecommendedTopicsCandidateSource]] returns
 * recommended topics, given a user.
 */
@Singleton
class RelatedTopicsCandidateSource @Inject() (
  interestDiscoveryService: t.InterestsDiscoveryService.MethodPerEndpoint)
    extends CandidateSource[t.RelatedTopicsRequest, t.RelatedTopic]
    with Logging {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(name = "RelatedTopics")

  override def apply(
    request: t.RelatedTopicsRequest
  ): Stitch[Seq[t.RelatedTopic]] = {
    Stitch
      .callFuture(interestDiscoveryService.getRelatedTopics(request))
      .map { response: t.RelatedTopicsResponse =>
        response.topics
      }
  }
}
