package com.twitter.product_mitwitterer.component_library.candidate_source.cr_mitwitterer

import com.twitter.cr_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CrMitwittererTweetRecommendationsCandidateSource @Inject() (
  crMitwittererClient: t.CrMitwitterer.MethodPerEndpoint)
    etwittertends CandidateSource[t.CrMitwittererTweetRequest, t.TweetRecommendation] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("CrMitwittererTweetRecommendations")

  override def apply(request: t.CrMitwittererTweetRequest): Stitch[Seq[t.TweetRecommendation]] = Stitch
    .callFuture(crMitwittererClient.getTweetRecommendations(request))
    .map(_.tweets)
}
