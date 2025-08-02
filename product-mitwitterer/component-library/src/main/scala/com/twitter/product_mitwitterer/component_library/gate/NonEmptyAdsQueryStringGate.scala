package com.twitter.product_mitwitterer.component_library.gate

import com.twitter.product_mitwitterer.component_library.model.query.ads.AdsQuery
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object NonEmptyAdsQueryStringGate etwittertends Gate[PipelineQuery with AdsQuery] {
  override val identifier: GateIdentifier = GateIdentifier("NonEmptyAdsQueryString")

  override def shouldContinue(query: PipelineQuery with AdsQuery): Stitch[Boolean] = {
    val queryString = query.searchRequestContetwittert.flatMap(_.queryString)
    Stitch.value(queryString.etwitterists(_.trim.nonEmpty))
  }
}
