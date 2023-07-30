package com.X.home_mixer.product.scored_tweets.gate

import com.X.home_mixer.product.scored_tweets.gate.MinCachedTweetsGate.identifierSuffix
import com.X.home_mixer.util.CachedScoredTweetsHelper
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.common.identifier.GateIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelines.configapi.Param

case class MinCachedTweetsGate(
  candidatePipelineIdentifier: CandidatePipelineIdentifier,
  minCachedTweetsParam: Param[Int])
    extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier =
    GateIdentifier(candidatePipelineIdentifier + identifierSuffix)

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val minCachedTweets = query.params(minCachedTweetsParam)
    val cachedScoredTweets =
      query.features.map(CachedScoredTweetsHelper.unseenCachedScoredTweets).getOrElse(Seq.empty)
    val numCachedTweets = cachedScoredTweets.count { tweet =>
      tweet.candidatePipelineIdentifier.exists(
        CandidatePipelineIdentifier(_).equals(candidatePipelineIdentifier))
    }
    Stitch.value(numCachedTweets < minCachedTweets)
  }
}

object MinCachedTweetsGate {
  val identifierSuffix = "MinCachedTweets"
}
