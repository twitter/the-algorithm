package com.ExTwitter.product_mixer.component_library.candidate_source.cr_mixer

import com.ExTwitter.cr_mixer.{thriftscala => t}
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrMixerTweetRecommendationsCandidateSource @Inject() (
  crMixerClient: t.CrMixer.MethodPerEndpoint)
    extends CandidateSource[t.CrMixerTweetRequest, t.TweetRecommendation] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("CrMixerTweetRecommendations")

  override def apply(request: t.CrMixerTweetRequest): Stitch[Seq[t.TweetRecommendation]] = Stitch
    .callFuture(crMixerClient.getTweetRecommendations(request))
    .map(_.tweets)
}
