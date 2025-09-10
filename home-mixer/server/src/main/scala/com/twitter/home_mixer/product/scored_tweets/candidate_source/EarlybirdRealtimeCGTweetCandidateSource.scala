package com.twitter.home_mixer.product.scored_tweets.candidate_source

import com.twitter.home_mixer.param.HomeMixerInjectionNames.EarlybirdRealtimCGEndpoint
import com.twitter.product_mixer.component_library.candidate_source.earlybird.EarlybirdTweetCandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.search.earlybird.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class EarlybirdRealtimeCGTweetCandidateSource @Inject() (
  @Named(EarlybirdRealtimCGEndpoint) earlybirdService: t.EarlybirdService.MethodPerEndpoint)
    extends EarlybirdTweetCandidateSource(earlybirdService) {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("EarlybirdRealtimeCGTweets")
}

@Singleton
class EarlybirdNsfwCGTweetCandidateSource @Inject() (
  @Named(EarlybirdRealtimCGEndpoint) earlybirdService: t.EarlybirdService.MethodPerEndpoint)
    extends EarlybirdTweetCandidateSource(earlybirdService) {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("EarlybirdNsfwCGTweets")
}
