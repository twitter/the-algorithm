package com.ExTwitter.home_mixer.product.scored_tweets.candidate_source

import com.ExTwitter.home_mixer.util.CachedScoredTweetsHelper
import com.ExTwitter.home_mixer.{thriftscala => hmt}
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachedScoredTweetsCandidateSource @Inject() ()
    extends CandidateSource[PipelineQuery, hmt.ScoredTweet] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("CachedScoredTweets")

  override def apply(request: PipelineQuery): Stitch[Seq[hmt.ScoredTweet]] = {
    Stitch.value(
      request.features.map(CachedScoredTweetsHelper.unseenCachedScoredTweets).getOrElse(Seq.empty))
  }
}
