package com.ExTwitter.product_mixer.component_library.gate

import com.ExTwitter.product_mixer.component_library.model.query.ads.AdsQuery
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

object NonEmptyAdsQueryStringGate extends Gate[PipelineQuery with AdsQuery] {
  override val identifier: GateIdentifier = GateIdentifier("NonEmptyAdsQueryString")

  override def shouldContinue(query: PipelineQuery with AdsQuery): Stitch[Boolean] = {
    val queryString = query.searchRequestContext.flatMap(_.queryString)
    Stitch.value(queryString.exists(_.trim.nonEmpty))
  }
}
