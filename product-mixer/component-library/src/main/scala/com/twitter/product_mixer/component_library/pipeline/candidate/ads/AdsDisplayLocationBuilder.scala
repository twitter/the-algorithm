package com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads

import com.ExTwitter.adserver.{thriftscala => ads}
import com.ExTwitter.product_mixer.component_library.model.query.ads.AdsQuery
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

trait AdsDisplayLocationBuilder[-Query <: PipelineQuery with AdsQuery] {

  def apply(query: Query): ads.DisplayLocation
}

case class StaticAdsDisplayLocationBuilder(displayLocation: ads.DisplayLocation)
    extends AdsDisplayLocationBuilder[PipelineQuery with AdsQuery] {

  def apply(query: PipelineQuery with AdsQuery): ads.DisplayLocation = displayLocation
}
