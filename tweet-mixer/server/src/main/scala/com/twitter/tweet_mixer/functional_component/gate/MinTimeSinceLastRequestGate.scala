package com.twitter.tweet_mixer.functional_component.gate

import com.twitter.tweet_mixer.functional_component.hydrator.LastNonPollingTimeFeature
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationMinHoursSinceLastRequestParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LastNonPollingTimeFeatureHydratorEnabled

/**
 * Gate continues if the amount of time passed since the previous request is greater
 * than the configured amount or if the previous request time in not available
 */
object MinTimeSinceLastRequestGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("TimeSinceLastRequest")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = Stitch.value {
    // Always continue if the LastNonPollingTimeFeatureHydrator is not enabled
    if (!query.params(LastNonPollingTimeFeatureHydratorEnabled)) {
      return Stitch.value(true)
    }
    val minTimeSinceLastRequest = query.params(ContentExplorationMinHoursSinceLastRequestParam)
    query.features.exists { features =>
      features
        .getOrElse(LastNonPollingTimeFeature, None)
        .forall(lnpt => (query.queryTime - lnpt) > minTimeSinceLastRequest)
    }
  }
}
