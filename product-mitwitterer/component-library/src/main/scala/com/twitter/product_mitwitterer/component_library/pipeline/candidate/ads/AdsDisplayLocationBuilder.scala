package com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.product_mitwitterer.component_library.model.query.ads.AdsQuery
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait AdsDisplayLocationBuilder[-Query <: PipelineQuery with AdsQuery] {

  def apply(query: Query): ads.DisplayLocation
}

case class StaticAdsDisplayLocationBuilder(displayLocation: ads.DisplayLocation)
    etwittertends AdsDisplayLocationBuilder[PipelineQuery with AdsQuery] {

  def apply(query: PipelineQuery with AdsQuery): ads.DisplayLocation = displayLocation
}
