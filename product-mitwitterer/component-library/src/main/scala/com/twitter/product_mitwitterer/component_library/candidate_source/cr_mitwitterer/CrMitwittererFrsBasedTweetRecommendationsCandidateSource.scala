package com.twitter.product_mitwitterer.component_library.candidate_source.cr_mitwitterer

import com.twitter.cr_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Returns out-of-network Tweet recommendations by using user recommendations
 * from FollowRecommendationService as an input seed-set to Earlybird
 */
@Singleton
class CrMitwittererFrsBasedTweetRecommendationsCandidateSource @Inject() (
  crMitwittererClient: t.CrMitwitterer.MethodPerEndpoint)
    etwittertends CandidateSource[t.FrsTweetRequest, t.FrsTweet] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("CrMitwittererFrsBasedTweetRecommendations")

  override def apply(request: t.FrsTweetRequest): Stitch[Seq[t.FrsTweet]] = Stitch
    .callFuture(crMitwittererClient.getFrsBasedTweetRecommendations(request))
    .map(_.tweets)
}
