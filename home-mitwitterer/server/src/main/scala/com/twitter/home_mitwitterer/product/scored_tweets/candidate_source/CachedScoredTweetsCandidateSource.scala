package com.twitter.home_mitwitterer.product.scored_tweets.candidate_source

import com.twitter.home_mitwitterer.util.CachedScoredTweetsHelper
import com.twitter.home_mitwitterer.{thriftscala => hmt}
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CachedScoredTweetsCandidateSource @Inject() ()
    etwittertends CandidateSource[PipelineQuery, hmt.ScoredTweet] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("CachedScoredTweets")

  override def apply(request: PipelineQuery): Stitch[Seq[hmt.ScoredTweet]] = {
    Stitch.value(
      request.features.map(CachedScoredTweetsHelper.unseenCachedScoredTweets).getOrElse(Seq.empty))
  }
}
