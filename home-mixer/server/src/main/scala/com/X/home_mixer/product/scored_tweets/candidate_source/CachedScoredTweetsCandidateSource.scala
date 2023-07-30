package com.X.home_mixer.product.scored_tweets.candidate_source

import com.X.home_mixer.util.CachedScoredTweetsHelper
import com.X.home_mixer.{thriftscala => hmt}
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

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
