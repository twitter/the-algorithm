package com.twitter.home_mixer.product.for_you.gate

import com.twitter.home_mixer.model.HomeFeatures.CurrentDisplayedGrokTopicFeature
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object TuneFeedModuleGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("TuneFeed")

  override def shouldContinue(
    query: PipelineQuery
  ): Stitch[Boolean] = {
    val grokTopicToDisplay =
      query.features.get.getOrElse(CurrentDisplayedGrokTopicFeature, None)

    Stitch.value(grokTopicToDisplay.isDefined)
  }
}
