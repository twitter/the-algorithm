package com.twitter.home_mixer.functional_component.gate

import com.twitter.home_mixer.model.HomeFeatures.ViewerAllowsForYouRecommendationsFeature
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * This gate disables out of network candidate pipelines when the AllowForYouRecommendations
 * user preference is set to false.
 * Defaults to true if the preference is not set.
 */
object AllowForYouRecommendationsGate extends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier("AllowForYouRecommendations")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val allowForYouRecommendations = query.features
      .flatMap(_.getOrElse(ViewerAllowsForYouRecommendationsFeature, Some(true))).getOrElse(
        true
      )
    Stitch.value(allowForYouRecommendations)
  }
}
