package com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.GridCarouselMetadata
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GridCarouselMetadataMarshaller @Inject() () {

  def apply(gridCarouselMetadata: GridCarouselMetadata): urt.GridCarouselMetadata =
    urt.GridCarouselMetadata(numRows = gridCarouselMetadata.numRows)
}
