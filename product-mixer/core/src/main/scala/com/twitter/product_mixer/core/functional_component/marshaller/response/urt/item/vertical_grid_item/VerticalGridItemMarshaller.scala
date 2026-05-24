package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerticalGridItemMarshaller @Inject() (
  verticalGridItemContentMarshaller: VerticalGridItemContentMarshaller) {

  def apply(verticalGridItem: VerticalGridItem): urt.TimelineItemContent =
    urt.TimelineItemContent.VerticalGridItem(
      urt.VerticalGridItem(
        content = verticalGridItemContentMarshaller(verticalGridItem)
      )
    )
}
