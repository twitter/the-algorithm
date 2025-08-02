package com.twitter.home_mitwitterer.product.scored_tweets.gate

import com.twitter.home_mitwitterer.product.scored_tweets.gate.MinCachedTweetsGate.identifierSuffitwitter
import com.twitter.home_mitwitterer.util.CachedScoredTweetsHelper
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

case class MinCachedTweetsGate(
  candidatePipelineIdentifier: CandidatePipelineIdentifier,
  minCachedTweetsParam: Param[Int])
    etwittertends Gate[PipelineQuery] {

  override val identifier: GateIdentifier =
    GateIdentifier(candidatePipelineIdentifier + identifierSuffitwitter)

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val minCachedTweets = query.params(minCachedTweetsParam)
    val cachedScoredTweets =
      query.features.map(CachedScoredTweetsHelper.unseenCachedScoredTweets).getOrElse(Seq.empty)
    val numCachedTweets = cachedScoredTweets.count { tweet =>
      tweet.candidatePipelineIdentifier.etwitterists(
        CandidatePipelineIdentifier(_).equals(candidatePipelineIdentifier))
    }
    Stitch.value(numCachedTweets < minCachedTweets)
  }
}

object MinCachedTweetsGate {
  val identifierSuffitwitter = "MinCachedTweets"
}
