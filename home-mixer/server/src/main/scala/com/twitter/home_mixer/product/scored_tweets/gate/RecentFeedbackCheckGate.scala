package com.twitter.home_mixer.product.scored_tweets.gate

import com.twitter.home_mixer.model.HomeFeatures.HasRecentFeedbackSinceCacheTtlFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableRecentFeedbackCheckParam
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Continue if a user has no don't like feedback within cached tweets ttl time (3 mins).
 * The reason is that if a user clicks don't like, tweets in the cache won't be affected
 * and it feels our system has slow response to user's negative feedback
 */

object RecentFeedbackCheckGate extends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier("RecentFeedbackCheck")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    query.features.map(_.getOrElse(HasRecentFeedbackSinceCacheTtlFeature, false)) match {
      case Some(true) =>
        if (query.params(EnableRecentFeedbackCheckParam)) Stitch.False
        else Stitch.True
      case _ => Stitch.True
    }
  }
}
