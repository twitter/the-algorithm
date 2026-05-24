package com.twitter.product_mixer.component_library.gate

import com.twitter.product_mixer.component_library.model.query.ads.AdsQuery
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object NonEmptyAdsQueryStringGate extends Gate[PipelineQuery with AdsQuery] {
  override val identifier: GateIdentifier = GateIdentifier("NonEmptyAdsQueryString")

  override def shouldContinue(query: PipelineQuery with AdsQuery): Stitch[Boolean] = {
    val queryString = query.searchRequestContext.flatMap(_.queryString)
    Stitch.value(queryString.exists(_.trim.nonEmpty))
  }
}
