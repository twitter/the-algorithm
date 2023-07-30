package com.X.home_mixer.product.scored_tweets.gate

import com.X.conversions.DurationOps._
import com.X.home_mixer.model.HomeFeatures.LastNonPollingTimeFeature
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.model.common.identifier.GateIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

/**
 * Gate continues if the amount of time passed since the previous request is greater
 * than the configured amount or if the previous request time in not available
 */
object MinTimeSinceLastRequestGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("TimeSinceLastRequest")

  private val MinTimeSinceLastRequest = 24.hours

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = Stitch.value {
    query.features.exists { features =>
      features
        .getOrElse(LastNonPollingTimeFeature, None)
        .forall(lnpt => (query.queryTime - lnpt) > MinTimeSinceLastRequest)
    }
  }
}
