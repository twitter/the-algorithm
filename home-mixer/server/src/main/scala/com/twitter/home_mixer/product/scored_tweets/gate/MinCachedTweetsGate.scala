package com.twitter.home_mixer.product.scored_tweets.gate

import com.twitter.home_mixer.model.HomeFeatures.HasRecentFeedbackSinceCacheTtlFeature
import com.twitter.home_mixer.product.scored_tweets.gate.MinCachedTweetsGate.identifierSuffix
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableRecentFeedbackCheckParam
import com.twitter.home_mixer.util.CachedScoredTweetsHelper
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

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

    val hasMinCachedTweets = numCachedTweets < minCachedTweets
    query.features.map(_.getOrElse(HasRecentFeedbackSinceCacheTtlFeature, false)) match {
      case Some(true) =>
        if (query.params(EnableRecentFeedbackCheckParam)) Stitch.True
        else Stitch.value(hasMinCachedTweets)
      case _ => Stitch.value(hasMinCachedTweets)
    }
  }
}

object MinCachedTweetsGate {
  val identifierSuffix = "MinCachedTweets"
}
